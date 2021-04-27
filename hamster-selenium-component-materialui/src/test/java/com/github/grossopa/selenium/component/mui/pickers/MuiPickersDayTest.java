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

package com.github.grossopa.selenium.component.mui.pickers;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiPickersDay}
 *
 * @author Jack Yin
 * @since 1.2
 */
class MuiPickersDayTest {

    MuiPickersDay testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("ddd");
        testSubject = new MuiPickersDay(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("PickersDay-day", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(config.getRootCss("IconButton")).thenReturn("dddIconButton-root");
        when(config.validateByCss(testSubject, "dddIconButton-root")).thenReturn(true);
        assertTrue(testSubject.validate());
        verify(config, times(1)).validateByCss(any(), any());
    }

    @Test
    void validateFalse() {
        when(config.validateByCss(testSubject, "dddPickersDay-day")).thenReturn(false);
        assertFalse(testSubject.validate());
        verify(config, times(1)).validateByCss(any(), any());
    }

    @Test
    void isSelected() {
        when(element.getAttribute("class")).thenReturn("dddPickersDay-daySelected");
        assertTrue(testSubject.isSelected());
    }

    @Test
    void isSelectedFalse() {
        when(element.getAttribute("class")).thenReturn("dddPickersDay-daySelectedfff");
        assertFalse(testSubject.isSelected());
    }
}
