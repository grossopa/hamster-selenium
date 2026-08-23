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
package com.github.grossopa.playwright.core.intercepting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MethodInfoTest {

    @Test
    void testConstructor() {
        Object source = new Object();
        MethodInfo<Object> info = new MethodInfo<>(source, "testMethod", "param1", "param2");

        assertEquals(source, info.getSource());
        assertEquals("testMethod", info.getName());
        assertArrayEquals(new Object[]{"param1", "param2"}, info.getParams());
        assertNotNull(info.getStartTimeInMillis());
        assertNull(info.getEndTimeInMillis());
    }

    @Test
    void testConstructorWithNullSource() {
        assertThrows(NullPointerException.class, () -> new MethodInfo<>(null, "test"));
    }

    @Test
    void testConstructorWithNullName() {
        var object = new Object();
        assertThrows(NullPointerException.class, () -> new MethodInfo<>(object, null));
    }

    @Test
    void testConstructorWithNullParams() {
        var object  =new Object();
        assertThrows(NullPointerException.class, () -> new MethodInfo<>(object, "test", (Object[]) null));
    }

    @Test
    void testExecutionDone() {
        MethodInfo<Object> info = new MethodInfo<>(new Object(), "test");
        assertNull(info.getEndTimeInMillis());

        info.executionDone();
        assertNotNull(info.getEndTimeInMillis());
    }

    @Test
    void testGetTimeElapsedInMillis() {
        MethodInfo<Object> info = new MethodInfo<>(new Object(), "test");
        assertNull(info.getTimeElapsedInMillis());

        info.executionDone();
        assertNotNull(info.getTimeElapsedInMillis());
        assertTrue(info.getTimeElapsedInMillis() >= 0);
    }

    @Test
    void testCreate() {
        Object source = new Object();
        MethodInfo<Object> info = MethodInfo.create(source, "method", "arg1");

        assertEquals(source, info.getSource());
        assertEquals("method", info.getName());
        assertArrayEquals(new Object[]{"arg1"}, info.getParams());
    }
}
