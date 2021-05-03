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

package com.github.grossopa.selenium.component.mui.feedback;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiDialog}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiDialogTest {

    MuiDialog testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        testSubject = new MuiDialog(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("Dialog", testSubject.getComponentName());
    }

    @Test
    void getDialogTitle() {
        WebElement titleElement = mock(WebElement.class);
        when(element.findElement(By.className("MuiDialogTitle-root"))).thenReturn(titleElement);
        assertEquals(titleElement, testSubject.getDialogTitle().getWrappedElement());
    }

    @Test
    void getDialogContent() {
        WebElement titleElement = mock(WebElement.class);
        when(element.findElement(By.className("MuiDialogContent-root"))).thenReturn(titleElement);
        assertEquals(titleElement, testSubject.getDialogContent().getWrappedElement());
    }

    @Test
    void getDialogActions() {
        WebElement titleElement = mock(WebElement.class);
        when(element.findElement(By.className("MuiDialogActions-root"))).thenReturn(titleElement);
        assertEquals(titleElement, testSubject.getDialogActions().getWrappedElement());
    }

    @Test
    void closeDialog() {
        Actions actions = mock(Actions.class);
        when(actions.sendKeys(any())).thenReturn(actions);
        when(driver.createActions()).thenReturn(actions);
        testSubject.close();
        verify(actions, times(1)).sendKeys(Keys.ESCAPE);
        verify(actions, times(1)).perform();
    }
}
