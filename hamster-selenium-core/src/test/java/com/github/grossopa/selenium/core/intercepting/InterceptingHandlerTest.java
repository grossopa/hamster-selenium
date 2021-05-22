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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link InterceptingHandler}
 *
 * @author Jack Yin
 * @since 1.0
 */
class InterceptingHandlerTest {

    InterceptingHandler testSubject;
    boolean beforeCalled;
    boolean afterCalled;
    boolean exceptionCalled;

    @BeforeEach
    void setUp() {
        testSubject = new InterceptingHandler() {
            @Override
            public void onBefore(MethodInfo<?> methodInfo) {
                beforeCalled = true;
            }

            @Override
            public void onAfter(MethodInfo<?> methodInfo, Object resultValue) {
                afterCalled = true;
            }

            @Override
            public void onException(MethodInfo<?> methodInfo, Exception exception) {
                exceptionCalled = true;
            }
        };
    }

    @Test
    void execute() {
        testSubject.execute(() -> "", MethodInfo.create("", ""));
        assertTrue(beforeCalled);
        assertTrue(afterCalled);
        assertFalse(exceptionCalled);
    }

    @Test
    @SuppressWarnings("all")
    void executeException() {
        assertThrows(NoSuchElementException.class, () -> testSubject.execute(() -> {
            throw new NoSuchElementException("");
        }, MethodInfo.create("", "")));
        assertTrue(beforeCalled);
        assertFalse(afterCalled);
        assertTrue(exceptionCalled);
    }
}
