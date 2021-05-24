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
 * Tests for {@link MuiPickersCalendarHeaderDaysHeader}
 *
 * @author Jack Yin
 * @since 1.2
 */
class MuiPickersCalendarHeaderDaysHeaderTest {

    MuiPickersCalendarHeaderDaysHeader testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiPickersCalendarHeaderDaysHeader(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("PickersCalendarHeader-daysHeader", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(config.getCssPrefix()).thenReturn("ddd");
        when(config.validateByCss(testSubject, "dddPickersCalendarHeader-daysHeader")).thenReturn(true);
        assertTrue(testSubject.validate());
        verify(config, times(1)).validateByCss(any(), any());
    }

    @Test
    void validateFalse() {
        when(config.getCssPrefix()).thenReturn("ddd");
        when(config.validateByCss(testSubject, "dddPickersCalendarHeader-daysHeader")).thenReturn(false);
        assertFalse(testSubject.validate());
        verify(config, times(1)).validateByCss(any(), any());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiPickersCalendarHeaderDaysHeader{element=element-toString}", testSubject.toString());
    }
}
