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

package com.github.grossopa.selenium.core.util;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.StaleElementReferenceException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link SeleniumUtils}
 *
 * @author Jack Yin
 * @since 1.0
 */
class SeleniumUtilsTest {

    @Test
    void testConstructor() {
        boolean asserted = false;
        Constructor<?> constructor = SeleniumUtils.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            assertEquals(AssertionError.class, e.getCause().getClass());
            asserted = true;
        }

        assertTrue(asserted);
    }

    @Test
    void executeIgnoringStaleElementReference() {
        Object result1 = new Object();
        Object result2 = SeleniumUtils.executeIgnoringStaleElementReference(() -> result1, null);
        assertEquals(result1, result2);
    }

    @Test
    void executeIgnoringStaleElementReferenceThrow() {
        Object defaultValue = new Object();
        Object result2 = SeleniumUtils.executeIgnoringStaleElementReference(() -> {
            throw new StaleElementReferenceException("ddd");
        }, defaultValue);
        assertEquals(defaultValue, result2);
    }

    @Test
    void executeIgnoringStaleElementReferenceThrowOther() {
        Object defaultValue = new Object();
        assertThrows(IllegalArgumentException.class, () -> SeleniumUtils.executeIgnoringStaleElementReference(() -> {
            throw new IllegalArgumentException("ddd");
        }, defaultValue));

    }
}
