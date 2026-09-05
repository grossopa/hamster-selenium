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
package com.github.grossopa.selenium.recorder.monitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link RecorderEvent}
 *
 * @author Jack Yin
 * @since 1.15
 */
class RecorderEventTest {

    @Test
    void testGetters() {
        Exception exception = new IllegalStateException("boom");
        RecorderEvent testSubject = new RecorderEvent(RecorderEventType.EXCEPTION, "element.click",
                "http://localhost/a", exception);
        assertEquals(RecorderEventType.EXCEPTION, testSubject.getType());
        assertEquals("element.click", testSubject.getMethodName());
        assertEquals("http://localhost/a", testSubject.getUrl());
        assertEquals(exception, testSubject.getException());
        assertTrue(testSubject.getTimestampInMillis() > 0);
    }

    @Test
    void testNullUrlAndException() {
        RecorderEvent testSubject = new RecorderEvent(RecorderEventType.INTERACTION, "element.click", null, null);
        assertNull(testSubject.getUrl());
        assertNull(testSubject.getException());
        assertTrue(testSubject.toString().contains("INTERACTION"));
    }
}
