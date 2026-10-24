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
package com.github.grossopa.selenium.component.mui.v4.inputs;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiNumberField}
 *
 * @author Jack Yin
 * @since 1.18
 */
class MuiNumberFieldTest {

    MuiNumberField testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement inputElement = mock(WebElement.class);
    WebElement incrementButton = mock(WebElement.class);
    WebElement decrementButton = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(element.findElement(By.className("MuiNumberField-input"))).thenReturn(inputElement);
        testSubject = new MuiNumberField(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("NumberField", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V6}, testSubject.versions().toArray());
    }

    @Test
    void getValue() {
        when(inputElement.getAttribute("value")).thenReturn("42");
        assertEquals("42", testSubject.getValue());
    }

    @Test
    void getValueEmpty() {
        when(inputElement.getAttribute("value")).thenReturn("");
        assertEquals("", testSubject.getValue());
    }

    @Test
    void setValue() {
        testSubject.setValue("100");
        verify(inputElement).clear();
        verify(inputElement).sendKeys("100");
    }

    @Test
    void increment() {
        when(element.findElement(By.className("MuiNumberField-incrementButton"))).thenReturn(incrementButton);
        testSubject.increment();
        verify(incrementButton).click();
    }

    @Test
    void decrement() {
        when(element.findElement(By.className("MuiNumberField-decrementButton"))).thenReturn(decrementButton);
        testSubject.decrement();
        verify(decrementButton).click();
    }

    @Test
    void getMin() {
        when(inputElement.getAttribute("min")).thenReturn("0");
        assertEquals("0", testSubject.getMin());
    }

    @Test
    void getMinNull() {
        when(inputElement.getAttribute("min")).thenReturn(null);
        assertNull(testSubject.getMin());
    }

    @Test
    void getMax() {
        when(inputElement.getAttribute("max")).thenReturn("100");
        assertEquals("100", testSubject.getMax());
    }

    @Test
    void getMaxNull() {
        when(inputElement.getAttribute("max")).thenReturn(null);
        assertNull(testSubject.getMax());
    }

    @Test
    void getStep() {
        when(inputElement.getAttribute("step")).thenReturn("1");
        assertEquals("1", testSubject.getStep());
    }

    @Test
    void getStepNull() {
        when(inputElement.getAttribute("step")).thenReturn(null);
        assertNull(testSubject.getStep());
    }

    @Test
    void isError() {
        when(element.getDomAttribute("class")).thenReturn("MuiNumberField-root MuiNumberField-error");
        assertTrue(testSubject.isError());
    }

    @Test
    void isErrorNegative() {
        when(element.getDomAttribute("class")).thenReturn("MuiNumberField-root");
        assertFalse(testSubject.isError());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiNumberField{element=element-toString}", testSubject.toString());
    }
}
