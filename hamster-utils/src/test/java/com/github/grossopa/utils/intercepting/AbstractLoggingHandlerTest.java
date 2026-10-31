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

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link AbstractLoggingHandler}
 *
 * @author Jack Yin
 * @since 1.19
 */
@SuppressWarnings("NullableProblems")
class AbstractLoggingHandlerTest {

    @Test
    void testConstructorWithThreshold() {
        AbstractLoggingHandler handler = new TestLoggingHandler(100L);
        assertEquals(100L, handler.getThresholdInMillis());
        assertNotNull(handler.getLog());
        assertEquals(Logger.getLogger(AbstractLoggingHandler.class.getName()), handler.getLog());
    }

    @Test
    void testConstructorWithThresholdAndLogger() {
        Logger logger = Logger.getLogger("test-logger");
        AbstractLoggingHandler handler = new TestLoggingHandler(200L, logger);
        assertEquals(200L, handler.getThresholdInMillis());
        assertEquals(logger, handler.getLog());
    }

    @Test
    void testConstructorWithNullLogger() {
        assertThrows(NullPointerException.class, () -> new TestLoggingHandler(0L, null));
    }

    @Test
    void testGetThresholdInMillis() {
        AbstractLoggingHandler handler = new TestLoggingHandler(50L);
        assertEquals(50L, handler.getThresholdInMillis());
    }

    @Test
    void testGetLog() {
        Logger logger = Logger.getLogger("custom-logger");
        AbstractLoggingHandler handler = new TestLoggingHandler(0L, logger);
        assertEquals(logger, handler.getLog());
    }

    @Test
    void testImplementsInterceptingHandler() {
        AbstractLoggingHandler handler = new TestLoggingHandler(0L);
        assertInstanceOf(InterceptingHandler.class, handler);
    }

    /**
     * Concrete test implementation of {@link AbstractLoggingHandler} for testing purposes.
     */
    private static class TestLoggingHandler extends AbstractLoggingHandler {

        TestLoggingHandler(long thresholdInMillis) {
            super(thresholdInMillis);
        }

        TestLoggingHandler(long thresholdInMillis, Logger log) {
            super(thresholdInMillis, log);
        }

        @Override
        public void onBefore(MethodInfo<?> methodInfo) {
            // no-op for testing
        }

        @Override
        public void onAfter(MethodInfo<?> methodInfo, Object resultValue) {
            // no-op for testing
        }

        @Override
        public void onException(MethodInfo<?> methodInfo, Exception exception) {
            // no-op for testing
        }
    }
}
