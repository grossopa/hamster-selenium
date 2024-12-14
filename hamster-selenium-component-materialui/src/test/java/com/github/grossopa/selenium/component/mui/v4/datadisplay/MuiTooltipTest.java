/*
 * Copyright © 2024 the original author or authors.
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
package com.github.grossopa.selenium.component.mui.v4.datadisplay;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiTooltip}
 *
 * @author Jack Yin
 * @since 1.12.0
 */
class MuiTooltipTest {
    MuiTooltip testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiTooltip(element, driver, config);
    }

    @Test
    void versions() {
        Assertions.assertArrayEquals(new MuiVersion[]{V4, V5}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        Assertions.assertEquals("Tooltip", testSubject.getComponentName());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        Assertions.assertEquals("MuiTooltip{element=element-toString}", testSubject.toString());
    }

    @Test
    void validate() {
        when(element.getAttribute("role")).thenReturn("tooltip");
        when(config.validateComponentByCss(testSubject, "Popper")).thenReturn(true);
        Assertions.assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getAttribute("role")).thenReturn("dialog");
        when(config.validateComponentByCss(testSubject, "Popper")).thenReturn(true);
        Assertions.assertFalse(testSubject.validate());
    }
}