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
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.github.grossopa.playwright.core.intercepting.InterceptingMethods.*;
import static com.github.grossopa.playwright.core.intercepting.InterceptingTestHelper.afterEachVerify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link InterceptingWebComponent}
 *
 * @author Jack Yin
 * @since 1.0
 */
class InterceptingWebComponentTest {

    InterceptingWebComponent testSubject;
    WebComponent component = mock(WebComponent.class);
    InterceptingHandler handler = mock(InterceptingHandler.class);

    @BeforeEach
    void setUp() {
        when(handler.execute(any(), any())).thenCallRealMethod();
        testSubject = new InterceptingWebComponent(component, handler);
    }

    @Test
    void findComponents() {
        WebComponent childComponent = mock(WebComponent.class);
        when(component.findComponents("selector")).thenReturn(Collections.singletonList(childComponent));
        List<WebComponent> result = testSubject.findComponents("selector");
        verify(component, times(1)).findComponents("selector");
        assertTrue(result.get(0) instanceof InterceptingWebComponent);
        afterEachVerify(handler, component, COMPONENT_FIND_COMPONENTS, result, "selector");
    }

    @Test
    void findComponent() {
        WebComponent childComponent = mock(WebComponent.class);
        when(component.findComponent("selector")).thenReturn(childComponent);
        WebComponent result = testSubject.findComponent("selector");
        verify(component, times(1)).findComponent("selector");
        assertTrue(result instanceof InterceptingWebComponent);
        afterEachVerify(handler, component, COMPONENT_FIND_COMPONENT, result, "selector");
    }

    @Test
    void as() {
        Object result = new Object();
        when(component.as(any())).thenReturn(result);
        assertEquals(result, testSubject.as(c -> result));
        verify(component, times(1)).as(any());
    }

    @Test
    void locator() {
        Locator locator = mock(Locator.class);
        when(component.locator()).thenReturn(locator);
        assertEquals(locator, testSubject.locator());
        verify(component, times(1)).locator();
    }

    @Test
    void click() {
        testSubject.click();
        verify(component, times(1)).click();
        afterEachVerify(handler, component, COMPONENT_CLICK, null);
    }

    @Test
    void hover() {
        testSubject.hover();
        verify(component, times(1)).hover();
        afterEachVerify(handler, component, COMPONENT_HOVER, null);
    }

    @Test
    void textContent() {
        when(component.textContent()).thenReturn("text");
        assertEquals("text", testSubject.textContent());
        verify(component, times(1)).textContent();
        afterEachVerify(handler, component, COMPONENT_TEXT_CONTENT, "text");
    }

    @Test
    void innerText() {
        when(component.innerText()).thenReturn("inner");
        assertEquals("inner", testSubject.innerText());
        verify(component, times(1)).innerText();
        afterEachVerify(handler, component, COMPONENT_INNER_TEXT, "inner");
    }

    @Test
    void innerHTML() {
        when(component.innerHTML()).thenReturn("html");
        assertEquals("html", testSubject.innerHTML());
        verify(component, times(1)).innerHTML();
        afterEachVerify(handler, component, COMPONENT_INNER_HTML, "html");
    }

    @Test
    void getAttribute() {
        when(component.getAttribute("name")).thenReturn("value");
        assertEquals("value", testSubject.getAttribute("name"));
        verify(component, times(1)).getAttribute("name");
        afterEachVerify(handler, component, COMPONENT_GET_ATTRIBUTE, "value", "name");
    }

    @Test
    void isVisible() {
        when(component.isVisible()).thenReturn(true);
        assertTrue(testSubject.isVisible());
        verify(component, times(1)).isVisible();
        afterEachVerify(handler, component, COMPONENT_IS_VISIBLE, true);
    }

    @Test
    void isEnabled() {
        when(component.isEnabled()).thenReturn(true);
        assertTrue(testSubject.isEnabled());
        verify(component, times(1)).isEnabled();
        afterEachVerify(handler, component, COMPONENT_IS_ENABLED, true);
    }

    @Test
    void isDisabled() {
        when(component.isDisabled()).thenReturn(false);
        assertFalse(testSubject.isDisabled());
        verify(component, times(1)).isDisabled();
        afterEachVerify(handler, component, COMPONENT_IS_DISABLED, false);
    }

    @Test
    void fill() {
        testSubject.fill("value");
        verify(component, times(1)).fill("value");
        afterEachVerify(handler, component, COMPONENT_FILL, null, "value");
    }

    @Test
    void driver() {
        ComponentDriver driver = mock(ComponentDriver.class);
        when(component.driver()).thenReturn(driver);
        ComponentDriver result = testSubject.driver();
        assertTrue(result instanceof InterceptingComponentDriver);
        verify(component, times(1)).driver();
    }
}