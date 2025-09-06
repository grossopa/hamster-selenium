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

package com.github.grossopa.playwright.core.intercepting;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.github.grossopa.playwright.core.intercepting.InterceptingMethods.*;
import static com.github.grossopa.playwright.core.intercepting.InterceptingTestHelper.afterEachVerify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link InterceptingComponentDriver}
 *
 * @author Jack Yin
 * @since 1.0
 */
class InterceptingComponentDriverTest {

    InterceptingComponentDriver testSubject;
    ComponentDriver driver = mock(ComponentDriver.class);
    InterceptingHandler handler = mock(InterceptingHandler.class);

    WebComponent component = mock(WebComponent.class);
    List<WebComponent> components = Collections.singletonList(component);

    @BeforeEach
    void setUp() {
        when(handler.execute(any(), any())).thenCallRealMethod();
        testSubject = new InterceptingComponentDriver(driver, handler);
    }

    @Test
    void findComponents() {
        when(driver.findComponents("selector")).thenReturn(components);
        List<WebComponent> result = testSubject.findComponents("selector");
        verify(driver, times(1)).findComponents("selector");
        assertTrue(result.get(0) instanceof InterceptingWebComponent);
        afterEachVerify(handler, driver, DRIVER_FIND_COMPONENTS, result, "selector");
    }

    @Test
    void findComponentAs() {
        Function<WebComponent, String> function = c -> "test";
        when(driver.findComponentAs("selector", function)).thenReturn("test");
        String result = testSubject.findComponentAs("selector", function);
        verify(driver, times(1)).findComponentAs("selector", function);
        assertEquals("test", result);
        afterEachVerify(handler, driver, DRIVER_FIND_COMPONENT_AS, result, "selector", function);
    }

    @Test
    void findComponentsAs() {
        Function<WebComponent, String> function = c -> "test";
        when(driver.findComponentsAs("selector", function)).thenReturn(Collections.singletonList("test"));
        List<String> result = testSubject.findComponentsAs("selector", function);
        verify(driver, times(1)).findComponentsAs("selector", function);
        assertEquals("test", result.get(0));
        afterEachVerify(handler, driver, DRIVER_FIND_COMPONENTS_AS, result, "selector", function);
    }

    @Test
    void findComponent() {
        when(driver.findComponent("selector")).thenReturn(component);
        WebComponent result = testSubject.findComponent("selector");
        verify(driver, times(1)).findComponent("selector");
        assertTrue(result instanceof InterceptingWebComponent);
        afterEachVerify(handler, driver, DRIVER_FIND_COMPONENT, result, "selector");
    }

    @Test
    void mapLocator() {
        Object locator = new Object();
        when(driver.mapLocator(locator)).thenReturn(component);
        WebComponent result = testSubject.mapLocator(locator);
        verify(driver, times(1)).mapLocator(locator);
        assertEquals(component, result);
    }

    @Test
    void playwright() {
        Playwright playwright = mock(Playwright.class);
        when(driver.playwright()).thenReturn(playwright);
        Playwright result = testSubject.playwright();
        verify(driver, times(1)).playwright();
        assertEquals(playwright, result);
    }

    @Test
    void browser() {
        Browser browser = mock(Browser.class);
        when(driver.browser()).thenReturn(browser);
        Browser result = testSubject.browser();
        verify(driver, times(1)).browser();
        assertEquals(browser, result);
    }

    @Test
    void context() {
        BrowserContext context = mock(BrowserContext.class);
        when(driver.context()).thenReturn(context);
        BrowserContext result = testSubject.context();
        verify(driver, times(1)).context();
        assertEquals(context, result);
    }

    @Test
    void page() {
        Page page = mock(Page.class);
        when(driver.page()).thenReturn(page);
        Page result = testSubject.page();
        verify(driver, times(1)).page();
        assertEquals(page, result);
    }

    @Test
    void navigate() {
        testSubject.navigate("http://www.example.com");
        verify(driver, times(1)).navigate("http://www.example.com");
        afterEachVerify(handler, driver, DRIVER_NAVIGATE, null, "http://www.example.com");
    }
}