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

import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatStepperTest {

    MatStepper testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-");
        testSubject = new MatStepper(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Stepper", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-stepper-horizontal");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateVertical() {
        when(element.getDomAttribute("class")).thenReturn("mat-stepper-vertical");
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
        assertEquals("MatStepper{element=inner-element}", testSubject.toString());
    }

    @Test
    void validateHorizontalLegacy() {
        when(element.getDomAttribute("class")).thenReturn("mat-horizontal-stepper");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateVerticalLegacy() {
        when(element.getDomAttribute("class")).thenReturn("mat-vertical-stepper");
        assertTrue(testSubject.validate());
    }

    @Test
    void getSteps() {
        WebElement stepEl = mock(WebElement.class);
        when(element.findElements(By.className("mat-step"))).thenReturn(newArrayList(stepEl));
        assertEquals(1, testSubject.getSteps().size());
    }

    @Test
    void next() {
        WebElement btn = mock(WebElement.class);
        when(element.findElement(By.className("mat-stepper-next"))).thenReturn(btn);
        testSubject.next();
        verify(btn).click();
    }

    @Test
    void previous() {
        WebElement btn = mock(WebElement.class);
        when(element.findElement(By.className("mat-stepper-previous"))).thenReturn(btn);
        testSubject.previous();
        verify(btn).click();
    }

    @Test
    void isLinearTrue() {
        when(element.getDomAttribute("class")).thenReturn("mat-stepper mat-stepper-linear");
        assertTrue(testSubject.isLinear());
    }

    @Test
    void isLinearFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-stepper");
        assertFalse(testSubject.isLinear());
    }

    @Test
    void isVerticalTrue() {
        when(element.getDomAttribute("class")).thenReturn("mat-stepper mat-stepper-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test
    void isVerticalFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-stepper");
        assertFalse(testSubject.isVertical());
    }

    @Test
    void getHeader() {
        WebElement headerEl = mock(WebElement.class);
        when(element.findElement(By.className("mat-stepper-header"))).thenReturn(headerEl);
        assertNotNull(testSubject.getHeader());
    }
}
