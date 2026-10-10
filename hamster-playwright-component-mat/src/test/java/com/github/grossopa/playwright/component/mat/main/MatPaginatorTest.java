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
package com.github.grossopa.playwright.component.mat.main;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatPaginatorTest {

    MatPaginator testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        testSubject = new MatPaginator(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Paginator", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-mdc-paginator");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-other");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(locator.toString()).thenReturn("inner-locator");
        assertEquals("MatPaginator{locator=inner-locator}", testSubject.toString());
    }

    @Test
    void componentConstructor() {
        WebComponent component = mock(WebComponent.class);
        when(component.locator()).thenReturn(locator);
        assertInstanceOf(MatPaginator.class, new MatPaginator(component, driver, config));
    }

    @Test
    void getRangeLabel() {
        Locator rangeLabelLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-paginator-range-label")).thenReturn(rangeLabelLocator);
        when(rangeLabelLocator.first()).thenReturn(firstLocator);
        when(firstLocator.innerText()).thenReturn("1 – 10 of 50");
        assertEquals("1 – 10 of 50", testSubject.getRangeLabel());
    }

    @Test
    void getPageSizeSelect() {
        Locator pageSizeLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-paginator-page-size")).thenReturn(pageSizeLocator);
        when(pageSizeLocator.first()).thenReturn(firstLocator);
        assertNotNull(testSubject.getPageSizeSelect());
    }

    @Test
    void nextPage() {
        Locator nextLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-paginator-navigation-next")).thenReturn(nextLocator);
        when(nextLocator.first()).thenReturn(firstLocator);
        testSubject.nextPage();
        verify(firstLocator).click();
    }

    @Test
    void previousPage() {
        Locator prevLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-paginator-navigation-previous")).thenReturn(prevLocator);
        when(prevLocator.first()).thenReturn(firstLocator);
        testSubject.previousPage();
        verify(firstLocator).click();
    }

    @Test
    void firstPage() {
        Locator firstNavLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-paginator-navigation-first")).thenReturn(firstNavLocator);
        when(firstNavLocator.first()).thenReturn(firstLocator);
        testSubject.firstPage();
        verify(firstLocator).click();
    }

    @Test
    void lastPage() {
        Locator lastLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-paginator-navigation-last")).thenReturn(lastLocator);
        when(lastLocator.first()).thenReturn(firstLocator);
        testSubject.lastPage();
        verify(firstLocator).click();
    }

    @Test
    void getPageSizeOptions() {
        Locator optionsLocator = mock(Locator.class);
        Locator option1 = mock(Locator.class);
        Locator option2 = mock(Locator.class);
        when(locator.locator(".mat-mdc-paginator-page-size-label")).thenReturn(optionsLocator);
        when(optionsLocator.all()).thenReturn(List.of(option1, option2));
        when(option1.innerText()).thenReturn("5");
        when(option2.innerText()).thenReturn("10");
        assertEquals(List.of("5", "10"), testSubject.getPageSizeOptions());
    }
}
