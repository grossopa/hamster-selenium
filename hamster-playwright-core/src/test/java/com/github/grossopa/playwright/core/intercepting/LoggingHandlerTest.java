/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.core.intercepting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class LoggingHandlerTest {

    private Logger logger;
    private LoggingHandler handler;

    @BeforeEach
    void setUp() {
        logger = mock(Logger.class);
        handler = new LoggingHandler(0L, logger);
    }

    @Test
    void testConstructorWithThreshold() {
        LoggingHandler h = new LoggingHandler(100L);
        assertNotNull(h);
    }

    @Test
    void testConstructorWithThresholdAndLogger() {
        LoggingHandler h = new LoggingHandler(100L, logger);
        assertNotNull(h);
    }

    @Test
    void testOnBefore() {
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        assertDoesNotThrow(() -> handler.onBefore(info));
    }

    @Test
    void testOnAfterWithThresholdMet() {
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        info.executionDone();
        handler.onAfter(info, "result");
        verify(logger).log(eq(Level.INFO), any(java.util.function.Supplier.class));
    }

    @Test
    void testOnAfterWithThresholdNotMet() {
        LoggingHandler highThresholdHandler = new LoggingHandler(10000L, logger);
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        info.executionDone();
        highThresholdHandler.onAfter(info, "result");
        verify(logger, never()).log(eq(Level.INFO), any(java.util.function.Supplier.class));
    }

    @Test
    void testOnAfterWithNullElapsedTime() {
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        // do not call executionDone(), so getTimeElapsedInMillis() returns null
        assertDoesNotThrow(() -> handler.onAfter(info, "result"));
        verify(logger).log(eq(Level.INFO), any(java.util.function.Supplier.class));
    }

    @Test
    void testOnException() {
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        info.executionDone();
        Exception ex = new RuntimeException("test error");
        handler.onException(info, ex);
        verify(logger).log(eq(Level.WARNING),  eq(ex), any(Supplier.class));
    }
}
