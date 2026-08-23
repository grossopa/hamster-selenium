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
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiAlertTest {
    MuiAlert testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiAlert(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Alert", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    // getSeverity tests
    @Test void getSeveritySuccessStandard() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-standardSuccess");
        assertEquals("success", testSubject.getSeverity());
    }

    @Test void getSeveritySuccessFilled() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-filledSuccess");
        assertEquals("success", testSubject.getSeverity());
    }

    @Test void getSeveritySuccessOutlined() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-outlinedSuccess");
        assertEquals("success", testSubject.getSeverity());
    }

    @Test void getSeverityInfoStandard() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-standardInfo");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test void getSeverityInfoFilled() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-filledInfo");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test void getSeverityInfoOutlined() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-outlinedInfo");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test void getSeverityWarningStandard() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-standardWarning");
        assertEquals("warning", testSubject.getSeverity());
    }

    @Test void getSeverityWarningFilled() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-filledWarning");
        assertEquals("warning", testSubject.getSeverity());
    }

    @Test void getSeverityWarningOutlined() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-outlinedWarning");
        assertEquals("warning", testSubject.getSeverity());
    }

    @Test void getSeverityErrorStandard() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-standardError");
        assertEquals("error", testSubject.getSeverity());
    }

    @Test void getSeverityErrorFilled() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-filledError");
        assertEquals("error", testSubject.getSeverity());
    }

    @Test void getSeverityErrorOutlined() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-outlinedError");
        assertEquals("error", testSubject.getSeverity());
    }

    @Test void getSeverityDefault() {
        when(locator.getAttribute("class")).thenReturn("MuiAlert-root");
        assertEquals("info", testSubject.getSeverity());
    }

    // getMessage tests
    @Test
    void getMessage() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        when(firstLocator.innerText()).thenReturn("Alert message text");
        assertEquals("Alert message text", testSubject.getMessage());
    }

    // close test
    @Test
    void close() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        testSubject.close();
        verify(firstLocator).click();
    }

    // hasCloseButton - findComponent always returns non-null
    @Test
    void hasCloseButton() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        assertTrue(testSubject.hasCloseButton());
    }

    // hasIcon - findComponent always returns non-null
    @Test
    void hasIcon() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        assertTrue(testSubject.hasIcon());
    }

    // isDismissible delegates to hasCloseButton
    @Test
    void isDismissible() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        assertTrue(testSubject.isDismissible());
    }
}
