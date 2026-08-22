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

package com.github.grossopa.playwright.component.mui.v4.core;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiContainerTest {
    MuiContainer testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiContainer(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Container", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getMaxWidthXs() {
        when(locator.getAttribute("class")).thenReturn("MuiContainer-maxWidthXs");
        assertEquals("xs", testSubject.getMaxWidth());
    }

    @Test void getMaxWidthSm() {
        when(locator.getAttribute("class")).thenReturn("MuiContainer-maxWidthSm");
        assertEquals("sm", testSubject.getMaxWidth());
    }

    @Test void getMaxWidthMd() {
        when(locator.getAttribute("class")).thenReturn("MuiContainer-maxWidthMd");
        assertEquals("md", testSubject.getMaxWidth());
    }

    @Test void getMaxWidthLg() {
        when(locator.getAttribute("class")).thenReturn("MuiContainer-maxWidthLg");
        assertEquals("lg", testSubject.getMaxWidth());
    }

    @Test void getMaxWidthXl() {
        when(locator.getAttribute("class")).thenReturn("MuiContainer-maxWidthXl");
        assertEquals("xl", testSubject.getMaxWidth());
    }

    @Test void getMaxWidthDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("md", testSubject.getMaxWidth());
    }

    @Test void isFixedTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiContainer-fixed");
        assertTrue(testSubject.isFixed());
    }

    @Test void isFixedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiContainer-root");
        assertFalse(testSubject.isFixed());
    }

    @Test void isDisableGuttersTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiContainer-disableGutters");
        assertTrue(testSubject.isDisableGutters());
    }

    @Test void isDisableGuttersFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiContainer-root");
        assertFalse(testSubject.isDisableGutters());
    }

    @Test void getText() {
        when(locator.innerText()).thenReturn("Container content");
        assertEquals("Container content", testSubject.getText());
    }
}
