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
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MatCard}
 *
 * @author Jack Yin
 * @since 1.16
 */
class MatCardTest {

    MatCard testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        testSubject = new MatCard(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Card", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-card");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-button");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatCard{element=inner-element}", testSubject.toString());
    }

    @Test
    void getTitle() {
        WebElement titleElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-card-title"))).thenReturn(titleElement);
        WebComponent result = testSubject.getTitle();
        assertNotNull(result);
        assertEquals(titleElement, result.getWrappedElement());
    }

    @Test
    void getSubtitle() {
        WebElement subtitleElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-card-subtitle"))).thenReturn(subtitleElement);
        WebComponent result = testSubject.getSubtitle();
        assertNotNull(result);
        assertEquals(subtitleElement, result.getWrappedElement());
    }

    @Test
    void getContent() {
        WebElement contentElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-card-content"))).thenReturn(contentElement);
        WebComponent result = testSubject.getContent();
        assertNotNull(result);
        assertEquals(contentElement, result.getWrappedElement());
    }

    @Test
    void getActions() {
        WebElement actionsElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-card-actions"))).thenReturn(actionsElement);
        WebComponent result = testSubject.getActions();
        assertNotNull(result);
        assertEquals(actionsElement, result.getWrappedElement());
    }

    @Test
    void getHeader() {
        WebElement headerElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-card-header"))).thenReturn(headerElement);
        WebComponent result = testSubject.getHeader();
        assertNotNull(result);
        assertEquals(headerElement, result.getWrappedElement());
    }

    @Test
    void getFooter() {
        WebElement footerElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-card-footer"))).thenReturn(footerElement);
        WebComponent result = testSubject.getFooter();
        assertNotNull(result);
        assertEquals(footerElement, result.getWrappedElement());
    }
}
