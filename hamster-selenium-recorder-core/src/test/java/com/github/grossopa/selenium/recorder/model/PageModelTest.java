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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link PageModel}
 *
 * @author Jack Yin
 * @since 1.15
 */
class PageModelTest {

    PageModel testSubject = new PageModel("UserLogin", "/user/login");

    LocatorCandidate locator = new LocatorCandidate(LocatorType.ID, "login", LocatorCandidate.PRIORITY_ID,
            "by id \"login\"");

    @Test
    void testGetters() {
        assertEquals("UserLogin", testSubject.getName());
        assertEquals("/user/login", testSubject.getPageKey());
        assertTrue(testSubject.getElements().isEmpty());
    }

    @Test
    void testSetName() {
        testSubject.setName("Login");
        assertEquals("Login", testSubject.getName());
    }

    @Test
    void testAddElementAndHasField() {
        assertTrue(testSubject.addElement(new PageElementModel("username", locator, null)));
        assertTrue(testSubject.hasField("username"));
        assertFalse(testSubject.hasField("other"));
        assertEquals(1, testSubject.getElements().size());
    }

    @Test
    void testAddElementWithDuplicateFieldName() {
        testSubject.addElement(new PageElementModel("username", locator, null));
        assertFalse(testSubject.addElement(new PageElementModel("username", locator, null)));
        assertEquals(1, testSubject.getElements().size());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("UserLogin"));
    }
}
