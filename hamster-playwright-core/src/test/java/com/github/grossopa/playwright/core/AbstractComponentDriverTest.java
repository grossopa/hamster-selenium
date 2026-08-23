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
package com.github.grossopa.playwright.core;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AbstractComponentDriverTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private TestComponentDriver driver;

    static class TestComponentDriver extends AbstractComponentDriver {
        TestComponentDriver(Playwright playwright, Browser browser, BrowserContext context, Page page) {
            super(playwright, browser, context, page);
        }

        @Override
        public WebComponent mapLocator(Object locator) {
            return new DefaultWebComponent((Locator) locator, this);
        }
    }

    @BeforeEach
    void setUp() {
        playwright = mock(Playwright.class);
        browser = mock(Browser.class);
        context = mock(BrowserContext.class);
        page = mock(Page.class);
        driver = new TestComponentDriver(playwright, browser, context, page);
    }

    @Test
    void testFindComponents() {
        Locator pageLocator = mock(Locator.class);
        Locator locator1 = mock(Locator.class);
        Locator locator2 = mock(Locator.class);
        when(page.locator(".item")).thenReturn(pageLocator);
        when(pageLocator.all()).thenReturn(List.of(locator1, locator2));

        List<WebComponent> result = driver.findComponents(".item");
        assertEquals(2, result.size());
    }

    @Test
    void testFindComponentAs() {
        Locator pageLocator = mock(Locator.class);
        when(page.locator(".btn")).thenReturn(pageLocator);

        String result = driver.findComponentAs(".btn", wc -> "converted");
        assertEquals("converted", result);
    }

    @Test
    void testFindComponentsAs() {
        Locator pageLocator = mock(Locator.class);
        Locator locator1 = mock(Locator.class);
        Locator locator2 = mock(Locator.class);
        when(page.locator(".item")).thenReturn(pageLocator);
        when(pageLocator.all()).thenReturn(List.of(locator1, locator2));

        List<String> result = driver.findComponentsAs(".item", wc -> "item");
        assertEquals(2, result.size());
        assertEquals("item", result.get(0));
    }

    @Test
    void testFindComponent() {
        Locator pageLocator = mock(Locator.class);
        when(page.locator(".elem")).thenReturn(pageLocator);

        WebComponent result = driver.findComponent(".elem");
        assertNotNull(result);
    }

    @Test
    void testPlaywright() {
        assertEquals(playwright, driver.playwright());
    }

    @Test
    void testBrowser() {
        assertEquals(browser, driver.browser());
    }

    @Test
    void testContext() {
        assertEquals(context, driver.context());
    }

    @Test
    void testPage() {
        assertEquals(page, driver.page());
    }

    @Test
    void testNavigate() {
        driver.navigate("https://example.com");
        verify(page).navigate("https://example.com");
    }

    @Test
    void testNavigateWithTimeout() {
        driver.navigate("https://example.com", 5000L);
        verify(page).navigate(eq("https://example.com"), any(Page.NavigateOptions.class));
    }
}
