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
package com.github.grossopa.selenium.recorder.page;

import com.github.grossopa.selenium.recorder.model.PageModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link PageIdentification}
 *
 * @author Jack Yin
 * @since 1.15
 */
class PageIdentificationTest {

    PageModel page = new PageModel("UserLogin", "/user/login");

    @Test
    void testMatched() {
        PageIdentification testSubject = PageIdentification.matched(page);
        assertTrue(testSubject.isMatched());
        assertEquals(page, testSubject.getMatchedPage());
        assertNull(testSubject.getSuggestedKey());
        assertNull(testSubject.getSuggestedName());
    }

    @Test
    void testNewPage() {
        PageIdentification testSubject = PageIdentification.newPage("/order/list", "OrderList");
        assertFalse(testSubject.isMatched());
        assertNull(testSubject.getMatchedPage());
        assertEquals("/order/list", testSubject.getSuggestedKey());
        assertEquals("OrderList", testSubject.getSuggestedName());
    }

    @Test
    void testEqualsHashCodeToString() {
        PageIdentification same = PageIdentification.newPage("/order/list", "OrderList");
        PageIdentification testSubject = PageIdentification.newPage("/order/list", "OrderList");
        assertEquals(testSubject, testSubject);
        assertEquals(testSubject, same);
        assertEquals(testSubject.hashCode(), same.hashCode());
        assertNotEquals(testSubject, PageIdentification.matched(page));
        assertNotEquals(testSubject, new Object());
        assertTrue(testSubject.toString().contains("OrderList"));
    }
}
