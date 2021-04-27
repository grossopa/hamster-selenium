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
import com.github.grossopa.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiPickersDialog}
 *
 * @author Jack Yin
 * @since 1.2
 */
class MuiPickersDialogTest {

    MuiPickersDialog testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement dialogContent = mock(WebElement.class);
    WebElement dialogActions = mock(WebElement.class);

    WebElement okButton = mock(WebElement.class);
    WebElement cancelButton = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");

        when(element.findElement(By.className("MuiDialogContent-root"))).thenReturn(dialogContent);
        when(element.findElement(By.className("MuiDialogActions-root"))).thenReturn(dialogActions);

        testSubject = new MuiPickersDialog(element, driver, config);
        when(dialogActions.findElement(testSubject.getOkButtonLocator())).thenReturn(okButton);
        when(dialogActions.findElement(testSubject.getCancelButtonLocator())).thenReturn(cancelButton);
    }


    @Test
    void getPickersContainer() {
        WebElement pickersContainer = mock(WebElement.class);
        when(dialogContent.findElement(By.className("Mui" + MuiPickersBasePickerContainer.NAME)))
                .thenReturn(pickersContainer);
        assertEquals(pickersContainer, testSubject.getPickersContainer().getWrappedElement());
    }

    @Test
    void getOkButton() {
        assertEquals(okButton, testSubject.getOkButton().getWrappedElement());
    }

    @Test
    void getCancelButton() {
        assertEquals(cancelButton, testSubject.getCancelButton().getWrappedElement());
    }

    @Test
    void getOkButtonLocator() {
        assertEquals(By2.textContains("OK"), testSubject.getOkButtonLocator());
    }

    @Test
    void getCancelButtonLocator() {
        assertEquals(By2.textContains("Cancel"), testSubject.getCancelButtonLocator());
    }

    @Test
    void getOkButtonLocator2() {
        testSubject = new MuiPickersDialog(element, driver, config, By.className("333"), By.className("444"));
        assertEquals(By2.className("333"), testSubject.getOkButtonLocator());
    }

    @Test
    void getCancelButtonLocator2() {
        testSubject = new MuiPickersDialog(element, driver, config, By.className("333"), By.className("444"));
        assertEquals(By2.className("444"), testSubject.getCancelButtonLocator());
    }
}
