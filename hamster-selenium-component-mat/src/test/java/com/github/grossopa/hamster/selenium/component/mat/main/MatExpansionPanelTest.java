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
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MatExpansionPanel}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatExpansionPanelTest {

    MatExpansionPanel testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    WebElement headerElement = mock(WebElement.class);
    WebElement bodyElement = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("mat-");
        when(config.getTagPrefix()).thenReturn("mat-");

        when(element.findElement(By.className("mat-expansion-panel-header"))).thenReturn(headerElement);
        when(element.findElement(By.className("mat-expansion-panel-body"))).thenReturn(bodyElement);

        testSubject = new MatExpansionPanel(element, driver, config);
    }


    @Test
    void validate() {
        when(element.getAttribute("class")).thenReturn("mat-expansion-panel");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getAttribute("class")).thenReturn("mat-expansion-panel-333");
        assertFalse(testSubject.validate());
    }


    @Test
    void isEnabled() {
        when(element.getAttribute("disabled")).thenReturn(null);
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalse() {
        when(element.getAttribute("disabled")).thenReturn("");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void getComponentName() {
        assertEquals("ExpansionPanel", testSubject.getComponentName());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatExpansionPanel{element=inner-element}", testSubject.toString());
    }

    @Test
    void isExpanded() {
        when(element.getAttribute("class")).thenReturn("mat-expanded");
        assertTrue(testSubject.isExpanded());
    }

    @Test
    void isExpandedFalse() {
        when(element.getAttribute("class")).thenReturn("");
        assertFalse(testSubject.isExpanded());
    }

    @Test
    void expand() {
        when(element.getAttribute("class")).thenReturn("");
        doAnswer(a -> {
            when(element.getAttribute("class")).thenReturn("mat-expanded");
            return null;
        }).when(headerElement).click();

        assertFalse(testSubject.isExpanded());
        testSubject.expand();
        verify(headerElement, times(1)).click();
        assertTrue(testSubject.isExpanded());
    }

    @Test
    void expandAlready() {
        when(element.getAttribute("class")).thenReturn("mat-expanded");
        assertTrue(testSubject.isExpanded());
        testSubject.expand();
        verify(headerElement, never()).click();
        assertTrue(testSubject.isExpanded());
    }

    @Test
    void collapse() {
        when(element.getAttribute("class")).thenReturn("mat-expanded");
        doAnswer(a -> {
            when(element.getAttribute("class")).thenReturn("");
            return null;
        }).when(headerElement).click();

        assertTrue(testSubject.isExpanded());
        testSubject.collapse();
        verify(headerElement, times(1)).click();
        assertFalse(testSubject.isExpanded());
    }

    @Test
    void collapseAlready() {
        when(element.getAttribute("class")).thenReturn("");
        assertFalse(testSubject.isExpanded());
        testSubject.collapse();
        verify(headerElement, never()).click();
        assertFalse(testSubject.isExpanded());
    }

    @Test
    void getExpansionPanelHeader() {
        assertEquals(headerElement, testSubject.getExpansionPanelHeader().getWrappedElement());
    }

    @Test
    void getExpansionPanelBody() {
        assertEquals(bodyElement, testSubject.getExpansionPanelBody().getWrappedElement());
    }
}
