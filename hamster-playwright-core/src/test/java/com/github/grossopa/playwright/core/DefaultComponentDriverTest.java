/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.core;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultComponentDriverTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    void setUp() {
        playwright = mock(Playwright.class);
        browser = mock(Browser.class);
        context = mock(BrowserContext.class);
        page = mock(Page.class);
    }

    @Test
    void testConstructorWithAllParams() {
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser, context, page);
        assertEquals(playwright, driver.playwright());
        assertEquals(browser, driver.browser());
        assertEquals(context, driver.context());
        assertEquals(page, driver.page());
    }

    @Test
    void testConstructorWithPlaywrightBrowserContext() {
        when(context.newPage()).thenReturn(page);
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser, context);
        assertEquals(playwright, driver.playwright());
        assertEquals(page, driver.page());
    }

    @Test
    void testConstructorWithPlaywrightBrowser() {
        when(browser.newContext()).thenReturn(context);
        when(context.newPage()).thenReturn(page);
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser);
        assertEquals(playwright, driver.playwright());
    }

    @Test
    void testMapLocatorWithWebComponent() {
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser, context, page);
        WebComponent wc = mock(WebComponent.class);
        WebComponent result = driver.mapLocator(wc);
        assertSame(wc, result);
    }

    @Test
    void testMapLocatorWithLocator() {
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser, context, page);
        Locator locator = mock(Locator.class);
        WebComponent result = driver.mapLocator(locator);
        assertNotNull(result);
        assertInstanceOf(DefaultWebComponent.class, result);
    }
}
