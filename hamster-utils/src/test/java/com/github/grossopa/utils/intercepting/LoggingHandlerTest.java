/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.grossopa.utils.intercepting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link LoggingHandler}
 *
 * @author Jack Yin
 * @since 1.4
 */
@SuppressWarnings("unchecked")
class LoggingHandlerTest {

    LoggingHandler testSubject;
    long thresholdInMillis = 10L;
    Logger log = mock(Logger.class);
    MethodInfo<String> info = MethodInfo.create("source-object", "someMethodName", "paramsA", "paramsB");

    @BeforeEach
    void setUp() {
        testSubject = new LoggingHandler(thresholdInMillis, log);
    }

    @Test
    void constructor() {
        assertEquals(Logger.getLogger(LoggingHandler.class.getName()), new LoggingHandler(thresholdInMillis).getLog());
    }

    @Test
    void constructorWithLogger() {
        assertEquals(log, new LoggingHandler(thresholdInMillis, log).getLog());
    }

    @Test
    void onBefore() {
        testSubject.onBefore(info);
        verify(log, times(1)).fine((Supplier<String>) argThat(arg -> {
            Supplier<String> supplier = (Supplier<String>) arg;
            assertEquals("someMethodName(paramsA, paramsB)\n      Source: source-object\n", supplier.get());
            return true;
        }));
    }

    @Test
    void onAfter() throws InterruptedException {
        MethodInfo<String> info = MethodInfo.create("source-object", "someMethodName", "paramsA", "paramsB");
        testSubject = new LoggingHandler(thresholdInMillis, log);
        Thread.sleep(12L);
        testSubject.onAfter(info, "some-result");
        verify(log, times(1)).info((Supplier<String>) argThat(arg -> {
            Supplier<String> supplier = (Supplier<String>) arg;
            assertEquals(
                    "someMethodName(paramsA, paramsB)      Time spent ms.\n      Source: source-object\n      Result: some-result\n",
                    supplier.get().replaceAll("[0-9]", ""));
            return true;
        }));
    }

    @Test
    void onAfterNullResult() throws InterruptedException {
        MethodInfo<String> info = MethodInfo.create("source-object", "someMethodName", "paramsA", "paramsB");
        testSubject = new LoggingHandler(thresholdInMillis, log);
        Thread.sleep(12L);
        testSubject.onAfter(info, null);
        verify(log, times(1)).info((Supplier<String>) argThat(arg -> {
            Supplier<String> supplier = (Supplier<String>) arg;
            assertEquals(
                    "someMethodName(paramsA, paramsB)      Time spent ms.\n      Source: source-object\n      Result: null\n",
                    supplier.get().replaceAll("[0-9]", ""));
            return true;
        }));
    }

    @Test
    void onAfterMapResult() throws InterruptedException {
        MethodInfo<String> info = MethodInfo.create("source-object", "someMethodName", "paramsA", "paramsB");
        testSubject = new LoggingHandler(thresholdInMillis, log);
        Thread.sleep(12L);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("aaa", "bbb");
        result.put("ccc", "ddd");
        testSubject.onAfter(info, result);

        verify(log, times(1)).info((Supplier<String>) argThat(arg -> {
            Supplier<String> supplier = (Supplier<String>) arg;
            assertEquals(
                    "someMethodName(paramsA, paramsB)      Time spent ms.\n      Source: source-object\n      Result: count: , {aaa=bbb, ccc=ddd}\n",
                    supplier.get().replaceAll("[0-9]", ""));
            return true;
        }));
    }

    @Test
    void onAfterListResult() throws InterruptedException {
        MethodInfo<String> info = MethodInfo.create("source-object", "someMethodName", "paramsA", "paramsB");
        testSubject = new LoggingHandler(thresholdInMillis, log);
        Thread.sleep(12L);
        List<String> array = new ArrayList<>();
        array.add("aaa");
        array.add("bbb");
        testSubject.onAfter(info, array);

        verify(log, times(1)).info((Supplier<String>) argThat(arg -> {
            Supplier<String> supplier = (Supplier<String>) arg;
            assertEquals(
                    "someMethodName(paramsA, paramsB)      Time spent ms.\n      Source: source-object\n      Result: count: , [aaa, bbb]\n",
                    supplier.get().replaceAll("[0-9]", ""));
            return true;
        }));
    }

    @Test
    void onAfterAlreadyEnds() throws InterruptedException {
        MethodInfo<String> info = MethodInfo.create("source-object", "someMethodName", "paramsA", "paramsB");
        testSubject = new LoggingHandler(thresholdInMillis, log);
        Thread.sleep(12L);
        info.executionDone();
        testSubject.onAfter(info, "some-result");
        assertTrue(10L < info.getTimeElapsedInMillis());
        verify(log, times(1)).info((Supplier<String>) argThat(arg -> {
            Supplier<String> supplier = (Supplier<String>) arg;
            assertEquals(
                    "someMethodName(paramsA, paramsB)      Time spent ms.\n      Source: source-object\n      Result: some-result\n",
                    supplier.get().replaceAll("[0-9]", ""));
            return true;
        }));
    }

    @Test
    void onAfterThresholdNoLog() {
        MethodInfo<String> methodInfo = MethodInfo.create("source-object", "someMethodName", "paramsA", "paramsB");
        testSubject = new LoggingHandler(thresholdInMillis, log);
        methodInfo.executionDone();
        testSubject.onAfter(methodInfo, "some-result");
        assertTrue(10L > methodInfo.getTimeElapsedInMillis());
        verify(log, times(0)).info(any(Supplier.class));
    }

    @Test
    void onAfterNullElapsedTime() {
        MethodInfo<Object> nullTimeInfo = MethodInfo.create(new Object(), "test");
        LoggingHandler zeroThresholdHandler = new LoggingHandler(0L, log);
        assertDoesNotThrow(() -> zeroThresholdHandler.onAfter(nullTimeInfo, "result"));
        verify(log, times(1)).info(any(Supplier.class));
    }

    @Test
    void onException() {
        RuntimeException exception = new RuntimeException("Test exception.");
        testSubject.onException(info, exception);
        verify(log, times(1)).log(eq(Level.WARNING), eq(exception), argThat(arg -> {
            assertEquals("someMethodName(paramsA, paramsB)      Time spent ms.\n      Source: source-object\n",
                    arg.get().replaceAll("[0-9]", ""));
            return true;
        }));
    }

    @Test
    void onExceptionAlreadyEnds() {
        RuntimeException exception = new RuntimeException("Test exception.");
        info.executionDone();
        testSubject.onException(info, exception);
        verify(log, times(1)).log(eq(Level.WARNING), eq(exception), argThat(arg -> {
            assertEquals("someMethodName(paramsA, paramsB)      Time spent ms.\n      Source: source-object\n",
                    arg.get().replaceAll("[0-9]", ""));
            return true;
        }));
    }

    @Test
    void onExceptionNullElapsedTime() {
        MethodInfo<Object> nullTimeInfo = MethodInfo.create(new Object(), "test");
        Exception exception = new RuntimeException("test error");
        LoggingHandler zeroThresholdHandler = new LoggingHandler(0L, log);
        assertDoesNotThrow(() -> zeroThresholdHandler.onException(nullTimeInfo, exception));
        verify(log, times(1)).log(eq(Level.WARNING), eq(exception), any(Supplier.class));
    }

    @Test
    void getThresholdInMillis() {
        assertEquals(thresholdInMillis, testSubject.getThresholdInMillis());
    }

    @Test
    void getLog() {
        assertEquals(log, testSubject.getLog());
    }

    @Test
    void implementsInterceptingHandler() {
        assertInstanceOf(InterceptingHandler.class, testSubject);
    }
}
