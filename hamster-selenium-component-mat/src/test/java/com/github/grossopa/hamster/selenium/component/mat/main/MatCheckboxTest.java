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

package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatCheckbox}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatCheckboxTest {

    MatCheckbox testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    WebElement inputElement = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("mat-");
        when(element.getDomAttribute("class")).thenReturn("mat-checkbox");
        when(element.findElement(By.className("mat-checkbox-input"))).thenReturn(inputElement);

        testSubject = new MatCheckbox(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("Checkbox", testSubject.getComponentName());
    }

    @Test
    void validate() {
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-checkbox-333");
        assertFalse(testSubject.validate());
    }

    @Test
    void isSelected() {
        when(inputElement.isSelected()).thenReturn(true);
        assertTrue(testSubject.isSelected());
    }

    @Test
    void isSelectedFalse() {
        when(inputElement.isSelected()).thenReturn(false);
        assertFalse(testSubject.isSelected());
    }

    @Test
    void isEnabled() {
        when(element.getDomAttribute("class")).thenReturn("mat-checkbox");
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-checkbox mat-checkbox-disabled");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void getInput() {
        assertEquals(inputElement, testSubject.getInput().getWrappedElement());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatCheckbox{element=inner-element}", testSubject.toString());
    }
}
