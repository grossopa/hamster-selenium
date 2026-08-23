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

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterceptingPlaywrightTest {

    private Playwright playwright;
    private InterceptingHandler handler;
    private InterceptingPlaywright interceptingPlaywright;

    @BeforeEach
    void setUp() {
        playwright = mock(Playwright.class);
        handler = mock(InterceptingHandler.class);
        when(handler.execute(any(), any())).thenAnswer(invocation -> {
            java.util.function.Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        interceptingPlaywright = new InterceptingPlaywright(playwright, handler);
    }

    @Test
    void testConstructorWithNullPlaywright() {
        assertThrows(NullPointerException.class, () -> new InterceptingPlaywright(null, handler));
    }

    @Test
    void testConstructorWithNullHandler() {
        assertThrows(NullPointerException.class, () -> new InterceptingPlaywright(playwright, null));
    }

    @Test
    void testCreate() {
        InterceptingPlaywright created = InterceptingPlaywright.create(playwright);
        assertNotNull(created);
    }

    @Test
    void testChromium() {
        BrowserType browserType = mock(BrowserType.class);
        when(playwright.chromium()).thenReturn(browserType);
        assertEquals(browserType, interceptingPlaywright.chromium());
    }

    @Test
    void testFirefox() {
        BrowserType browserType = mock(BrowserType.class);
        when(playwright.firefox()).thenReturn(browserType);
        assertEquals(browserType, interceptingPlaywright.firefox());
    }

    @Test
    void testWebkit() {
        BrowserType browserType = mock(BrowserType.class);
        when(playwright.webkit()).thenReturn(browserType);
        assertEquals(browserType, interceptingPlaywright.webkit());
    }

    @Test
    void testRequest() {
        APIRequest apiRequest = mock(APIRequest.class);
        when(playwright.request()).thenReturn(apiRequest);
        assertEquals(apiRequest, interceptingPlaywright.request());
    }

    @Test
    void testSelectors() {
        Selectors selectors = mock(Selectors.class);
        when(playwright.selectors()).thenReturn(selectors);
        assertEquals(selectors, interceptingPlaywright.selectors());
    }

    @Test
    void testClose() {
        interceptingPlaywright.close();
        verify(playwright).close();
    }

    @Test
    void testToString() {
        when(playwright.toString()).thenReturn("Playwright@123");
        assertEquals("Playwright@123", interceptingPlaywright.toString());
    }
}
