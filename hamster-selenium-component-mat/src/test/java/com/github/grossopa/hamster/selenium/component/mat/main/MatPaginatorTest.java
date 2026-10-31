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

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatPaginatorTest {

    MatPaginator testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        testSubject = new MatPaginator(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Paginator", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-paginator");
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
        assertEquals("MatPaginator{element=inner-element}", testSubject.toString());
    }

    @Test
    void getRangeLabel() {
        WebElement labelEl = mock(WebElement.class);
        when(labelEl.getText()).thenReturn("1 – 10 of 50");
        when(element.findElement(By.className("mat-mdc-paginator-range-label"))).thenReturn(labelEl);
        assertEquals("1 – 10 of 50", testSubject.getRangeLabel());
    }

    @Test
    void getPageSizeSelect() {
        WebElement selectEl = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-paginator-page-size"))).thenReturn(selectEl);
        assertNotNull(testSubject.getPageSizeSelect());
    }

    @Test
    void nextPage() {
        WebElement btn = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-paginator-navigation-next"))).thenReturn(btn);
        testSubject.nextPage();
        verify(btn).click();
    }

    @Test
    void previousPage() {
        WebElement btn = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-paginator-navigation-previous"))).thenReturn(btn);
        testSubject.previousPage();
        verify(btn).click();
    }

    @Test
    void firstPage() {
        WebElement btn = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-paginator-navigation-first"))).thenReturn(btn);
        testSubject.firstPage();
        verify(btn).click();
    }

    @Test
    void lastPage() {
        WebElement btn = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-paginator-navigation-last"))).thenReturn(btn);
        testSubject.lastPage();
        verify(btn).click();
    }

    @Test
    void getPageSizeOptions() {
        WebElement opt1 = mock(WebElement.class);
        when(opt1.getText()).thenReturn("5");
        WebElement opt2 = mock(WebElement.class);
        when(opt2.getText()).thenReturn("10");
        when(element.findElements(By.className("mat-mdc-paginator-page-size-label"))).thenReturn(newArrayList(opt1, opt2));
        assertEquals(java.util.List.of("5", "10"), testSubject.getPageSizeOptions());
    }
}
