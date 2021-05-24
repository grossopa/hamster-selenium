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
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiPickersCalendarHeaderSwitchHeader}
 *
 * @author Jack Yin
 * @since 1.2
 */
class MuiPickersCalendarHeaderSwitchHeaderTest {

    MuiPickersCalendarHeaderSwitchHeader testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("ddd");
        testSubject = new MuiPickersCalendarHeaderSwitchHeader(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("PickersCalendarHeader-switchHeader", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(config.validateByCss(testSubject, "dddPickersCalendarHeader-switchHeader")).thenReturn(true);
        assertTrue(testSubject.validate());
        verify(config, times(1)).validateByCss(any(), any());
    }

    @Test
    void validateFalse() {
        when(config.validateByCss(testSubject, "dddPickersCalendarHeader-switchHeader")).thenReturn(false);
        assertFalse(testSubject.validate());
        verify(config, times(1)).validateByCss(any(), any());
    }

    @Test
    void getLeftButton() {
        WebElement leftButton = mock(WebElement.class);
        when(element.findElement(By.className("dddPickersCalendarHeader-iconButton"))).thenReturn(leftButton);
        MuiButton button = testSubject.getLeftButton();

        assertEquals(leftButton, button.getWrappedElement());
    }


    @Test
    void clickLeftButton() {
        WebElement leftButton = mock(WebElement.class);
        when(element.findElement(By.className("dddPickersCalendarHeader-iconButton"))).thenReturn(leftButton);

        testSubject.clickLeftButton(100);
        verify(leftButton, times(1)).click();
        verify(driver, times(1)).threadSleep(100);
    }

    @Test
    void getMiddleHeader() {
        WebElement middleHeader = mock(WebElement.class);
        when(element.findElement(By.className("dddPickersCalendarHeader-transitionContainer")))
                .thenReturn(middleHeader);
        assertEquals(middleHeader, testSubject.getMiddleHeader().getWrappedElement());
    }

    @Test
    void getRightButton() {
        WebElement leftButton = mock(WebElement.class);
        WebElement rightButton = mock(WebElement.class);
        when(element.findElements(By.className("dddPickersCalendarHeader-iconButton")))
                .thenReturn(newArrayList(leftButton, rightButton));
        MuiButton button = testSubject.getRightButton();

        assertEquals(rightButton, button.getWrappedElement());
    }

    @Test
    void clickRightButton() {
        WebElement leftButton = mock(WebElement.class);
        WebElement rightButton = mock(WebElement.class);
        when(element.findElements(By.className("dddPickersCalendarHeader-iconButton")))
                .thenReturn(newArrayList(leftButton, rightButton));

        testSubject.clickRightButton(100);
        verify(rightButton, times(1)).click();
        verify(leftButton, never()).click();
        verify(driver, times(1)).threadSleep(100);
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiPickersCalendarHeaderSwitchHeader{element=element-toString}", testSubject.toString());
    }
}
