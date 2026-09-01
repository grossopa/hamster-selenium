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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link PageRegistry}
 *
 * @author Jack Yin
 * @since 1.15
 */
class PageRegistryTest {

    PageRegistry testSubject = new PageRegistry(new ContextPathPageStrategy());

    @Test
    void testClassifyCreatesNewPage() {
        PageModel page = testSubject.classify("http://localhost/user/login");
        assertEquals("UserLogin", page.getName());
        assertEquals("/user/login", page.getPageKey());
        assertEquals(page, testSubject.getCurrentPage());
        assertEquals(1, testSubject.getPages().size());
    }

    @Test
    void testClassifyReusesExistingPage() {
        PageModel first = testSubject.classify("http://localhost/user/login");
        PageModel second = testSubject.classify("http://localhost/user/login?x=2");
        assertEquals(first, second);
        assertEquals(1, testSubject.getPages().size());
    }

    @Test
    void testNewPage() {
        PageModel page = testSubject.newPage("Custom", "/custom");
        assertEquals("Custom", page.getName());
        assertEquals(page, testSubject.getCurrentPage());
        assertTrue(testSubject.findPage("Custom").isPresent());
    }

    @Test
    void testUsePage() {
        PageModel page = testSubject.newPage("Custom", "/custom");
        testSubject.classify("http://localhost/other");
        assertEquals(page, testSubject.usePage("Custom"));
        assertEquals(page, testSubject.getCurrentPage());
    }

    @Test
    void testUsePageNotFound() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.usePage("unknown"));
    }

    @Test
    void testSetStrategy() {
        PageIdentificationStrategy strategy = mock(PageIdentificationStrategy.class);
        testSubject.setStrategy(strategy);
        assertEquals(strategy, testSubject.getStrategy());
    }
}
