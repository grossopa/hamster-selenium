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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Contains utility method for testing.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class InterceptingTestHelper {

    /**
     * For verifying same intercepting logic within different classes
     *
     * @param handler the mock handler
     * @param source the source object (should be the inner mock)
     * @param resultValue the expected result value
     * @param methodName method name to verify
     * @param params method params to verify
     */
    public static void afterEachVerify(InterceptingHandler handler, Object source, String methodName,
            Object resultValue, Object... params) {
        verify(handler, times(1)).onBefore(argThat(methodInfo -> {
            assertEquals(methodName, methodInfo.getName());
            assertEquals(source, methodInfo.getSource());
            assertArrayEquals(params, methodInfo.getParams());
            assertTrue(methodInfo.getTimeElapsedInMillis() >= 0);
            return true;
        }));
        verify(handler, times(1)).onAfter(any(), argThat(r -> {
            assertEquals(resultValue, r);
            return true;
        }));
    }
}
