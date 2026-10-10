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

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatTabGroupTest {

    MatTabGroup testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        testSubject = new MatTabGroup(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("TabGroup", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-tab-group");
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
        assertEquals("MatTabGroup{element=inner-element}", testSubject.toString());
    }

    @Test
    void getTabLabelsMdc() {
        WebElement tab1 = mock(WebElement.class);
        WebElement tab2 = mock(WebElement.class);
        when(element.findElements(By.className("mat-mdc-tab"))).thenReturn(newArrayList(tab1, tab2));

        List<WebComponent> result = testSubject.getTabLabels();
        assertEquals(2, result.size());
    }

    @Test
    void getTabLabelsLegacy() {
        WebElement label1 = mock(WebElement.class);
        when(element.findElements(By.className("mat-mdc-tab"))).thenReturn(newArrayList());
        when(element.findElements(By.className("mat-mdc-tab-label"))).thenReturn(newArrayList(label1));

        List<WebComponent> result = testSubject.getTabLabels();
        assertEquals(1, result.size());
    }

    @Test
    void selectTab() {
        WebElement tab1 = mock(WebElement.class);
        WebElement tab2 = mock(WebElement.class);
        when(element.findElements(By.className("mat-mdc-tab"))).thenReturn(newArrayList(tab1, tab2));

        testSubject.selectTab(1);
        verify(tab2).click();
        verify(tab1, never()).click();
    }

    @Test
    void getSelectedTabIndexMdc() {
        WebElement tab1 = mock(WebElement.class);
        when(tab1.getAttribute("class")).thenReturn("mat-mdc-tab");
        WebElement tab2 = mock(WebElement.class);
        when(tab2.getAttribute("class")).thenReturn("mat-mdc-tab mdc-tab--active");
        when(element.findElements(By.className("mat-mdc-tab"))).thenReturn(newArrayList(tab1, tab2));

        assertEquals(1, testSubject.getSelectedTabIndex());
    }

    @Test
    void getSelectedTabIndexLegacy() {
        WebElement tab1 = mock(WebElement.class);
        when(tab1.getAttribute("class")).thenReturn("mat-mdc-tab-label mat-mdc-tab-label-active");
        WebElement tab2 = mock(WebElement.class);
        when(tab2.getAttribute("class")).thenReturn("mat-mdc-tab-label");
        when(element.findElements(By.className("mat-mdc-tab"))).thenReturn(newArrayList(tab1, tab2));

        assertEquals(0, testSubject.getSelectedTabIndex());
    }

    @Test
    void getSelectedTabIndexNone() {
        WebElement tab1 = mock(WebElement.class);
        when(tab1.getAttribute("class")).thenReturn("mat-mdc-tab");
        when(element.findElements(By.className("mat-mdc-tab"))).thenReturn(newArrayList(tab1));

        assertEquals(-1, testSubject.getSelectedTabIndex());
    }

    @Test
    void getTabs() {
        WebElement tabEl = mock(WebElement.class);
        when(element.findElements(By.className("mat-mdc-tab"))).thenReturn(newArrayList(tabEl));
        assertEquals(1, testSubject.getTabs().size());
    }
}
