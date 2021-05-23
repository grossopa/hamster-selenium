/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.selenium.core.intercepting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link LoggingHandler}
 *
 * @author Jack Yin
 * @since 1.4
 */
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
        assertEquals(Logger.getLogger("com.github.grossopa.selenium.core.intercepting.LoggingHandler"),
                new LoggingHandler(thresholdInMillis).getLog());
    }

    @Test
    @SuppressWarnings("unchecked")
    void onBefore() {
        testSubject.onBefore(info);
        verify(log, times(1)).fine((Supplier<String>) argThat(arg -> {
            Supplier<String> supplier = (Supplier<String>) arg;
            assertEquals("someMethodName(paramsA, paramsB)\n      Source: source-object\n", supplier.get());
            return true;
        }));
    }

    @Test
    @SuppressWarnings("all")
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
    @SuppressWarnings("all")
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
    @SuppressWarnings("all")
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
    @SuppressWarnings("all")
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
    @SuppressWarnings("all")
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
    @SuppressWarnings("unchecked")
    void onAfterThresholdNoLog() {
        MethodInfo<String> info = MethodInfo.create("source-object", "someMethodName", "paramsA", "paramsB");
        testSubject = new LoggingHandler(thresholdInMillis, log);
        info.executionDone();
        testSubject.onAfter(info, "some-result");
        assertTrue(10L > info.getTimeElapsedInMillis());
        verify(log, times(0)).info(any(Supplier.class));
    }

    @Test
    void onException() {
        NoSuchElementException exception = new NoSuchElementException("No such element.");
        testSubject.onException(info, exception);
        verify(log, times(1)).log(eq(Level.SEVERE), eq(exception), argThat(arg -> {
            assertEquals("someMethodName(paramsA, paramsB)      Time spent ms.\n      Source: source-object\n",
                    arg.get().replaceAll("[0-9]", ""));
            return true;
        }));
    }

    @Test
    void onExceptionAlreadyEnds() {
        NoSuchElementException exception = new NoSuchElementException("No such element.");
        info.executionDone();
        testSubject.onException(info, exception);
        verify(log, times(1)).log(eq(Level.SEVERE), eq(exception), argThat(arg -> {
            assertEquals("someMethodName(paramsA, paramsB)      Time spent ms.\n      Source: source-object\n",
                    arg.get().replaceAll("[0-9]", ""));
            return true;
        }));
    }

    @Test
    void getThresholdInMillis() {
        assertEquals(thresholdInMillis, testSubject.getThresholdInMillis());
    }

    @Test
    void getLog() {
        assertEquals(log, testSubject.getLog());
    }
}
