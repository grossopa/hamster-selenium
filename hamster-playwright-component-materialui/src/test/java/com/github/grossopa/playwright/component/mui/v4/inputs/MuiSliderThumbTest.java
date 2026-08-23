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
package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiSliderThumb}
 *
 * @author Jack Yin
 * @since 1.12
 */
class MuiSliderThumbTest {

    MuiSliderThumb testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiSliderThumb(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("SliderThumb", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void getValueFromAriaValueNow() {
        when(locator.getAttribute("aria-valuenow")).thenReturn("50");
        assertEquals("50", testSubject.getValue());
    }

    @Test
    void getValueFromDataValue() {
        when(locator.getAttribute("aria-valuenow")).thenReturn(null);
        when(locator.getAttribute("data-value")).thenReturn("75");
        assertEquals("75", testSubject.getValue());
    }

    @Test
    void getValueDefault() {
        when(locator.getAttribute("aria-valuenow")).thenReturn(null);
        when(locator.getAttribute("data-value")).thenReturn(null);
        assertEquals("0", testSubject.getValue());
    }

    @Test
    void getMinValue() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("10");
        assertEquals("10", testSubject.getMinValue());
    }

    @Test
    void getMinValueDefault() {
        when(locator.getAttribute("aria-valuemin")).thenReturn(null);
        assertEquals("0", testSubject.getMinValue());
    }

    @Test
    void getMaxValue() {
        when(locator.getAttribute("aria-valuemax")).thenReturn("200");
        assertEquals("200", testSubject.getMaxValue());
    }

    @Test
    void getMaxValueDefault() {
        when(locator.getAttribute("aria-valuemax")).thenReturn(null);
        assertEquals("100", testSubject.getMaxValue());
    }
}
