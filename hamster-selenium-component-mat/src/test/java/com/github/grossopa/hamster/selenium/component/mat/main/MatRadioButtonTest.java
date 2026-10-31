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
package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatRadioButtonTest {

    MatRadioButton testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        when(config.getInternalSelector("radio-label")).thenReturn("mdc-form-field");
        when(config.getInternalSelector("radio-input")).thenReturn("mdc-radio__native-control");
        testSubject = new MatRadioButton(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("RadioButton", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-radio-button");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-other");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatRadioButton{element=inner-element}", testSubject.toString());
    }

    @Test
    void isSelectedTrue() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-radio-button mat-mdc-radio-checked");
        assertTrue(testSubject.isSelected());
    }

    @Test
    void isSelectedFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-radio-button");
        assertFalse(testSubject.isSelected());
    }

    @Test
    void isEnabledTrue() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-radio-button");
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-radio-button mat-mdc-radio-disabled");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void getLabel() {
        WebElement labelEl = mock(WebElement.class);
        when(labelEl.getText()).thenReturn("Option A");
        when(element.findElement(By.className("mdc-form-field"))).thenReturn(labelEl);
        assertEquals("Option A", testSubject.getLabel());
    }

    @Test
    void getInput() {
        WebElement inputEl = mock(WebElement.class);
        when(element.findElement(By.className("mdc-radio__native-control"))).thenReturn(inputEl);
        assertNotNull(testSubject.getInput());
    }
}
