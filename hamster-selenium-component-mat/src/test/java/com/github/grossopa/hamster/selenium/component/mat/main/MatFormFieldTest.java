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
 * Tests for {@link MatFormField}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatFormFieldTest {

    MatFormField testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("mat-");
        when(config.getTagPrefix()).thenReturn("mat-");
        testSubject = new MatFormField(element, driver, config);
    }

    @Test
    void validate() {
        when(element.getAttribute("class")).thenReturn("mat-form-field");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getAttribute("class")).thenReturn("mat-form-field-333");
        assertFalse(testSubject.validate());
    }


    @Test
    void getComponentName() {
        assertEquals("FormField", testSubject.getComponentName());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatFormField{element=inner-element}", testSubject.toString());
    }

    @Test
    void getPrefix() {
        WebElement prefixElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-form-field-prefix"))).thenReturn(prefixElement);
        assertEquals(prefixElement, testSubject.getPrefix().getWrappedElement());
    }

    @Test
    void getInfix() {
        WebElement infixElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-form-field-infix"))).thenReturn(infixElement);
        assertEquals(infixElement, testSubject.getInfix().getWrappedElement());
    }

    @Test
    void getSuffix() {
        WebElement suffixElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-form-field-suffix"))).thenReturn(suffixElement);
        assertEquals(suffixElement, testSubject.getSuffix().getWrappedElement());
    }

    @Test
    void getHint() {
        WebElement hintElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-hint"))).thenReturn(hintElement);
        assertEquals(hintElement, testSubject.getHint().getWrappedElement());
    }

    @Test
    void getInput() {
        WebElement infixElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-form-field-infix"))).thenReturn(infixElement);

        WebElement inputElement = mock(WebElement.class);
        when(infixElement.findElement(By.xpath("./input"))).thenReturn(inputElement);
        assertEquals(inputElement, testSubject.getInput().getWrappedElement());
    }

    @Test
    void getLabel() {
        WebElement infixElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-form-field-infix"))).thenReturn(infixElement);

        WebElement inputElement = mock(WebElement.class);
        when(infixElement.findElement(By.xpath(
                ".//*[contains(@class,\"mat-form-field-label-wrapper\")]/child::label/child::mat-label"))).thenReturn(
                inputElement);
        assertEquals(inputElement, testSubject.getLabel().getWrappedElement());
    }

    @Test
    void getError() {
        WebElement wrapperElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-form-field-subscript-wrapper"))).thenReturn(wrapperElement);
        WebElement errorElement = mock(WebElement.class);
        when(wrapperElement.findElement(By.className("mat-error"))).thenReturn(errorElement);
        assertEquals(errorElement, testSubject.getError().getWrappedElement());
    }

}
