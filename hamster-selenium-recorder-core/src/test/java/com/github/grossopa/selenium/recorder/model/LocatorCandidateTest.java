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
package com.github.grossopa.selenium.recorder.model;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link LocatorCandidate}
 *
 * @author Jack Yin
 * @since 1.15
 */
class LocatorCandidateTest {

    LocatorCandidate testSubject = new LocatorCandidate(LocatorType.ID, "login", LocatorCandidate.PRIORITY_ID,
            "by id \"login\"");

    @Test
    void testGetters() {
        assertEquals(LocatorType.ID, testSubject.getType());
        assertEquals("login", testSubject.getValue());
        assertEquals(LocatorCandidate.PRIORITY_ID, testSubject.getPriority());
        assertEquals("by id \"login\"", testSubject.getDescription());
    }

    @Test
    void testToBy() {
        assertEquals(By.id("login"), testSubject.toBy());
    }

    @Test
    void testEqualsHashCodeToString() {
        LocatorCandidate same = new LocatorCandidate(LocatorType.ID, "login", LocatorCandidate.PRIORITY_ID,
                "by id \"login\"");
        assertEquals(testSubject, testSubject);
        assertEquals(testSubject, same);
        assertEquals(testSubject.hashCode(), same.hashCode());
        assertNotEquals(testSubject, new LocatorCandidate(LocatorType.NAME, "login",
                LocatorCandidate.PRIORITY_NAME, "by name \"login\""));
        assertNotEquals(testSubject, new Object());
        assertTrue(testSubject.toString().contains("login"));
    }
}
