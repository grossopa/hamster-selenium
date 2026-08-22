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

package com.github.grossopa.playwright.component.mui.v4.datadisplay;

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

class MuiTooltipTest {
    MuiTooltip testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiTooltip(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Tooltip", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    // getTooltipText - driver.findComponent("[role=\"tooltip\"]") returns non-null from driver
    @Test void getTooltipText() {
        WebComponent tooltipComponent = mock(WebComponent.class);
        when(driver.findComponent("[role=\"tooltip\"]")).thenReturn(tooltipComponent);
        when(tooltipComponent.innerText()).thenReturn("Tooltip text");
        assertEquals("Tooltip text", testSubject.getTooltipText());
    }

    @Test void getTooltipTextNull() {
        when(driver.findComponent("[role=\"tooltip\"]")).thenReturn(null);
        assertNull(testSubject.getTooltipText());
    }

    // show - hover
    @Test void show() {
        testSubject.show();
        verify(locator).hover();
    }

    // hide - blur
    @Test void hide() {
        testSubject.hide();
        verify(locator).blur();
    }

    // isVisible - driver.findComponent("[role=\"tooltip\"]:visible") returns non-null
    @Test void isVisibleTrue() {
        WebComponent tooltipComponent = mock(WebComponent.class);
        when(driver.findComponent("[role=\"tooltip\"]:visible")).thenReturn(tooltipComponent);
        assertTrue(testSubject.isVisible());
    }

    @Test void isVisibleFalse() {
        when(driver.findComponent("[role=\"tooltip\"]:visible")).thenReturn(null);
        assertFalse(testSubject.isVisible());
    }

    // getPlacement
    @Test void getPlacement() {
        when(locator.getAttribute("data-placement")).thenReturn("top");
        assertEquals("top", testSubject.getPlacement());
    }

    @Test void getPlacementNull() {
        when(locator.getAttribute("data-placement")).thenReturn(null);
        assertNull(testSubject.getPlacement());
    }
}
