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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link ContextPathPageStrategy}
 *
 * @author Jack Yin
 * @since 1.15
 */
class ContextPathPageStrategyTest {

    ContextPathPageStrategy testSubject = new ContextPathPageStrategy();

    @Test
    void testToPageKeyWithQueryAndFragment() {
        assertEquals("/user/login", testSubject.toPageKey("http://localhost:8080/user/login?redirect=%2F#top"));
    }

    @Test
    void testToPageKeyFoldsNumberSegments() {
        assertEquals("/user/{id}", testSubject.toPageKey("http://localhost/user/123"));
        assertEquals("/user/{id}/order/{id}", testSubject.toPageKey("http://localhost/user/123/order/456"));
    }

    @Test
    void testToPageKeyTrimsTrailingSlash() {
        assertEquals("/user/login", testSubject.toPageKey("http://localhost/user/login/"));
    }

    @Test
    void testToPageKeyOfRoot() {
        assertEquals("/", testSubject.toPageKey("http://localhost/"));
        assertEquals("/", testSubject.toPageKey("http://localhost"));
    }

    @Test
    void testToPageName() {
        assertEquals("UserLogin", testSubject.toPageName("/user/login"));
        assertEquals("Home", testSubject.toPageName("/"));
        assertEquals("User", testSubject.toPageName("/user/{id}"));
    }

    @Test
    void testIdentifyMatchesExistingPage() {
        PageModel page = new PageModel("UserLogin", "/user/login");
        PageIdentification result = testSubject.identify("http://localhost/user/login?x=1", List.of(page));
        assertTrue(result.isMatched());
        assertEquals(page, result.getMatchedPage());
    }

    @Test
    void testIdentifySuggestsNewPage() {
        PageIdentification result = testSubject.identify("http://localhost/order/list", List.of());
        assertFalse(result.isMatched());
        assertEquals("/order/list", result.getSuggestedKey());
        assertEquals("OrderList", result.getSuggestedName());
    }
}
