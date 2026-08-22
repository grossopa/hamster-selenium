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

package com.github.grossopa.playwright.component.mui.v4.surfaces;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiPaperTest {
    MuiPaper testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiPaper(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Paper", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getElevation() {
        when(locator.getAttribute("class")).thenReturn("Muielevation4");
        assertEquals(4, testSubject.getElevation());
    }

    @Test void getElevationDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals(0, testSubject.getElevation());
    }

    @Test void getVariantOutlined() {
        when(locator.getAttribute("class")).thenReturn("MuiPaper-outlined");
        assertEquals("outlined", testSubject.getVariant());
    }

    @Test void getVariantElevation() {
        when(locator.getAttribute("class")).thenReturn("MuiPaper-elevation");
        assertEquals("elevation", testSubject.getVariant());
    }

    @Test void getVariantDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("default", testSubject.getVariant());
    }

    @Test void isRoundedTrue() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertTrue(testSubject.isRounded());
    }

    @Test void isRoundedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiPaper-rounded");
        assertFalse(testSubject.isRounded());
    }

    @Test void isSquaredTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiPaper-rounded");
        assertTrue(testSubject.isSquared());
    }

    @Test void isSquaredFalse() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isSquared());
    }

    @Test void getText() {
        when(locator.innerText()).thenReturn("Paper content");
        assertEquals("Paper content", testSubject.getText());
    }
}
