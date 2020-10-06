/*
 * Copyright © 2020 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.hamster.selenium.component.mui;

import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests for {@link MuiSliderThumb}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiSliderThumbTest {

    MuiSliderThumb testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Muiabc");
        when(element.getAttribute("aria-valuetext")).thenReturn("The value is 30");
        when(element.getAttribute("aria-valuemin")).thenReturn("20");
        when(element.getAttribute("aria-valuemax")).thenReturn("800");
        when(element.getAttribute("aria-valuenow")).thenReturn("30");
        testSubject = new MuiSliderThumb(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("Slider-thumb", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(config.validateByCss(any(), eq("MuiabcSlider-thumb"))).thenReturn(true);
        assertTrue(testSubject.validate());
    }

    @Test
    void getPercentageHorizontal() {
        when(element.getAttribute("aria-orientation")).thenReturn("horizontal");
        when(element.getCssValue("left")).thenReturn("30%");
        assertEquals("30%", testSubject.getPercentage());
    }

    @Test
    void getPercentageVertical() {
        when(element.getAttribute("aria-orientation")).thenReturn("vertical");
        when(element.getCssValue("bottom")).thenReturn("30%");
        assertEquals("30%", testSubject.getPercentage());
    }

    @Test
    void getPercentageInvalid() {
        when(element.getAttribute("orientation")).thenReturn("333");
        assertThrows(IllegalStateException.class, () -> testSubject.getPercentage());
    }

    @Test
    void getValueText() {
        assertEquals("The value is 30", testSubject.getValueText());
    }

    @Test
    void getMaxValue() {
        assertEquals("800", testSubject.getMaxValue());
    }

    @Test
    void getMinValue() {
        assertEquals("20", testSubject.getMinValue());
    }

    @Test
    void getValue() {
        assertEquals("30", testSubject.getValue());
    }
}
