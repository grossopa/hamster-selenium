/*
 * Copyright © 2021 the original author or authors.
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

package com.github.grossopa.selenium.component.mui.v5.inputs;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiSliderThumbV5}
 *
 * @author Jack Yin
 * @since 1.7
 */
class MuiSliderThumbV5Test {

    MuiSliderThumbV5 testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement inputElement = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Muiabc");
        when(element.findElement(By.xpath("./child::input"))).thenReturn(inputElement);
        when(inputElement.getAttribute("aria-orientation")).thenReturn("horizontal");
        when(inputElement.getAttribute("aria-valuetext")).thenReturn("The value is 30");
        when(inputElement.getAttribute("aria-valuemin")).thenReturn("20");
        when(inputElement.getAttribute("aria-valuemax")).thenReturn("800");
        when(inputElement.getAttribute("aria-valuenow")).thenReturn("30");
        testSubject = new MuiSliderThumbV5(element, driver, config);
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V5}, testSubject.versions().toArray());
    }

    @Test
    void getOrientation() {
        assertEquals("horizontal", testSubject.getOrientation());
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

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiSliderThumbV5{element=element-toString}", testSubject.toString());
    }


}
