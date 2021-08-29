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

package com.github.grossopa.hamster.selenium.component.mat.main.sub;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatChip}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatChipTest {

    MatChip testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    WebElement removeIconElement = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("mat-");
        when(config.getTagPrefix()).thenReturn("mat-");
        when(element.findElement(By.xpath(".//mat-icon[contains(@class,\"mat-chip-remove\")]"))).thenReturn(
                removeIconElement);
        when(element.findElements(By.xpath(".//mat-icon[contains(@class,\"mat-chip-remove\")]"))).thenReturn(
                newArrayList(removeIconElement));
        testSubject = new MatChip(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("Chip", testSubject.getComponentName());
    }

    @Test
    void getRemoveIcon() {
        assertEquals(removeIconElement, testSubject.getRemoveIcon().getWrappedElement());
    }

    @Test
    void getText() {
        when(element.getText()).thenReturn("Lemon\ncancel");
        when(removeIconElement.getText()).thenReturn("cancel");
        assertEquals("Lemon", testSubject.getText());
    }

    @Test
    void getTextNotMatch() {
        when(element.getText()).thenReturn("Lemon");
        when(removeIconElement.getText()).thenReturn("cancel");
        assertEquals("Lemon", testSubject.getText());
    }

    @Test
    void getTextNoRemoveIcon() {
        when(element.getText()).thenReturn("Lemon");
        when(element.findElements(By.xpath(".//mat-icon[contains(@class,\"mat-chip-remove\")]"))).thenReturn(
                newArrayList());
        assertEquals("Lemon", testSubject.getText());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatChip{element=inner-element}", testSubject.toString());
    }

    @Test
    void validate() {
        when(element.getAttribute("class")).thenReturn("mat-chip");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getAttribute("class")).thenReturn("mat-chip-333");
        assertFalse(testSubject.validate());
    }
}
