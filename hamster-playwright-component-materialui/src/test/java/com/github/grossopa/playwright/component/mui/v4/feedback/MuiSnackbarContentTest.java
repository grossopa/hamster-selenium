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
package com.github.grossopa.playwright.component.mui.v4.feedback;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiSnackbarContentTest {
    MuiSnackbarContent testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiSnackbarContent(locator, driver, config); }

    @Test void getComponentName() { assertEquals("SnackbarContent", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private Locator mockFindComponent() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        return firstLocator;
    }

    // getMessage - findComponent returns non-null, so messageWrapper.innerText() is called
    @Test void getMessage() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.innerText()).thenReturn("Snackbar message");
        assertEquals("Snackbar message", testSubject.getMessage());
    }

    // getAction - findComponent returns non-null wrapper
    @Test void getAction() {
        mockFindComponent();
        WebComponent result = testSubject.getAction();
        assertNotNull(result);
    }

    // hasAction - getAction() returns non-null
    @Test void hasAction() {
        mockFindComponent();
        assertTrue(testSubject.hasAction());
    }

    // getIcon - findComponent returns non-null wrapper
    @Test void getIcon() {
        mockFindComponent();
        WebComponent result = testSubject.getIcon();
        assertNotNull(result);
    }

    // hasIcon - getIcon() returns non-null
    @Test void hasIcon() {
        mockFindComponent();
        assertTrue(testSubject.hasIcon());
    }

    // clickAction - getAction() returns non-null, then action.findComponent("button") also non-null
    @Test void clickAction() {
        // First call: findComponent(".MuiSnackbarContent-action") → actionWrapper
        Locator actionChildLocator = mock(Locator.class);
        Locator actionFirstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(actionChildLocator);
        when(actionChildLocator.first()).thenReturn(actionFirstLocator);

        // action.findComponent(BUTTON) → actionFirstLocator.locator("button").first() → buttonLocator
        Locator buttonChildLocator = mock(Locator.class);
        Locator buttonFirstLocator = mock(Locator.class);
        when(actionFirstLocator.locator(anyString())).thenReturn(buttonChildLocator);
        when(buttonChildLocator.first()).thenReturn(buttonFirstLocator);

        testSubject.clickAction();
        verify(buttonFirstLocator).click();
    }
}
