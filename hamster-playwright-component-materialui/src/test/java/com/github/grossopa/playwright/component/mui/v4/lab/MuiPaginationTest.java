/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.component.mui.v4.lab;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiPaginationTest {
    MuiPagination testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiPagination(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Pagination", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindPages(Locator... pageLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(pageLocators));
    }

    /**
     * Mocks findComponent chain: locator.locator(any).first() → firstLocator
     */
    private Locator mockFindComponent() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        return firstLocator;
    }

    @Test void getPageCount() {
        mockFindPages(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getPageCount());
    }

    @Test void getCurrentPage() {
        Locator page1 = mock(Locator.class);
        Locator page2 = mock(Locator.class);
        when(page1.getAttribute("aria-current")).thenReturn(null);
        when(page2.getAttribute("aria-current")).thenReturn("true");
        mockFindPages(page1, page2);
        assertEquals(2, testSubject.getCurrentPage());
    }

    @Test void getCurrentPageDefault() {
        Locator page1 = mock(Locator.class);
        when(page1.getAttribute("aria-current")).thenReturn(null);
        mockFindPages(page1);
        assertEquals(1, testSubject.getCurrentPage());
    }

    @Test void goToPage() {
        Locator page1 = mock(Locator.class);
        mockFindPages(page1);
        testSubject.goToPage(1);
        verify(page1).click();
    }

    @Test void goToPageOutOfBounds() {
        mockFindPages();
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.goToPage(1));
    }

    @Test void goToPageZero() {
        mockFindPages(mock(Locator.class));
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.goToPage(0));
    }

    @Test void isCircularTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-circular");
        assertTrue(testSubject.isCircular());
    }

    @Test void isCircularFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-root");
        assertFalse(testSubject.isCircular());
    }

    @Test void isCircularNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isCircular());
    }

    @Test void getVariantOutlined() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-outlined");
        assertEquals("outlined", testSubject.getVariant());
    }

    @Test void getVariantText() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-text");
        assertEquals("text", testSubject.getVariant());
    }

    @Test void getVariantDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("default", testSubject.getVariant());
    }

    @Test void getSizeSmall() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-sizeSmall");
        assertEquals("small", testSubject.getSize());
    }

    @Test void getSizeLarge() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-sizeLarge");
        assertEquals("large", testSubject.getSize());
    }

    @Test void getSizeDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("medium", testSubject.getSize());
    }

    // nextPage - findComponent returns non-null wrapper
    @Test void nextPage() {
        Locator firstLocator = mockFindComponent();
        testSubject.nextPage();
        verify(firstLocator).click();
    }

    // previousPage
    @Test void previousPage() {
        Locator firstLocator = mockFindComponent();
        testSubject.previousPage();
        verify(firstLocator).click();
    }

    // firstPage
    @Test void firstPage() {
        Locator firstLocator = mockFindComponent();
        testSubject.firstPage();
        verify(firstLocator).click();
    }

    // lastPage
    @Test void lastPage() {
        Locator firstLocator = mockFindComponent();
        testSubject.lastPage();
        verify(firstLocator).click();
    }

    // hasNextPage - findComponent returns non-null, check disabled attribute
    @Test void hasNextPageTrue() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.getAttribute("disabled")).thenReturn(null);
        assertTrue(testSubject.hasNextPage());
    }

    @Test void hasNextPageFalseDisabled() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.getAttribute("disabled")).thenReturn("true");
        assertFalse(testSubject.hasNextPage());
    }

    // hasPreviousPage
    @Test void hasPreviousPageTrue() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.getAttribute("disabled")).thenReturn(null);
        assertTrue(testSubject.hasPreviousPage());
    }

    @Test void hasPreviousPageFalseDisabled() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.getAttribute("disabled")).thenReturn("true");
        assertFalse(testSubject.hasPreviousPage());
    }
}
