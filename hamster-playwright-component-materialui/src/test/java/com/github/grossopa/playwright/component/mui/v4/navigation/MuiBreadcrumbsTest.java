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

package com.github.grossopa.playwright.component.mui.v4.navigation;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiBreadcrumbsTest {
    MuiBreadcrumbs testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiBreadcrumbs(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Breadcrumbs", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindItems(Locator... itemLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(itemLocators));
    }

    // getItems
    @Test void getItemsEmpty() {
        mockFindItems();
        assertTrue(testSubject.getItems().isEmpty());
    }

    @Test void getItemsTwo() {
        mockFindItems(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getItems().size());
    }

    // getItemCount
    @Test void getItemCount() {
        mockFindItems(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getItemCount());
    }

    @Test void getItemCountZero() {
        mockFindItems();
        assertEquals(0, testSubject.getItemCount());
    }

    // getItemTexts
    @Test void getItemTexts() {
        Locator item1 = mock(Locator.class);
        Locator item2 = mock(Locator.class);
        when(item1.innerText()).thenReturn("Home");
        when(item2.innerText()).thenReturn("Products");
        mockFindItems(item1, item2);
        assertEquals(List.of("Home", "Products"), testSubject.getItemTexts());
    }

    @Test void getItemTextsEmpty() {
        mockFindItems();
        assertTrue(testSubject.getItemTexts().isEmpty());
    }

    // clickItem
    @Test void clickItem() {
        Locator item1 = mock(Locator.class);
        when(item1.innerText()).thenReturn("Home");
        mockFindItems(item1);
        testSubject.clickItem("Home");
        verify(item1).click();
    }

    @Test void clickItemNotFound() {
        mockFindItems();
        assertThrows(IllegalArgumentException.class, () -> testSubject.clickItem("Home"));
    }

    // getSeparator - findComponent returns non-null wrapper
    @Test void getSeparator() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        WebComponent separator = testSubject.getSeparator();
        assertNotNull(separator);
    }
}
