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
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiTabsTest {
    MuiTabs testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiTabs(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Tabs", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindTabs(Locator... tabLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(tabLocators));
    }

    @Test void getTabCount() {
        mockFindTabs(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getTabCount());
    }

    @Test void isVerticalTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiTabs-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiTabs-root");
        assertFalse(testSubject.isVertical());
    }

    @Test void selectTabByIndex() {
        Locator tabLocator = mock(Locator.class);
        mockFindTabs(tabLocator);
        testSubject.selectTab(0);
        verify(tabLocator).click();
    }

    @Test void selectTabByIndexOutOfBounds() {
        mockFindTabs();
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.selectTab(0));
    }

    @Test void selectTabByLabel() {
        Locator tabLocator = mock(Locator.class);
        when(tabLocator.innerText()).thenReturn("Tab 1");
        mockFindTabs(tabLocator);
        testSubject.selectTab("Tab 1");
        verify(tabLocator).click();
    }

    @Test void selectTabByLabelNotFound() {
        mockFindTabs();
        assertThrows(IllegalArgumentException.class, () -> testSubject.selectTab("Tab 1"));
    }

    @Test void getSelectedTabReturnsSelected() {
        Locator tab1 = mock(Locator.class);
        Locator tab2 = mock(Locator.class);
        when(tab1.getAttribute("aria-selected")).thenReturn("false");
        when(tab2.getAttribute("aria-selected")).thenReturn("true");
        when(tab1.innerText()).thenReturn("Tab 1");
        when(tab2.innerText()).thenReturn("Tab 2");
        mockFindTabs(tab1, tab2);
        MuiTab selected = testSubject.getSelectedTab();
        assertNotNull(selected);
    }

    @Test void getSelectedTabReturnsNullWhenNoneSelected() {
        Locator tab1 = mock(Locator.class);
        when(tab1.getAttribute("aria-selected")).thenReturn("false");
        when(tab1.innerText()).thenReturn("Tab 1");
        mockFindTabs(tab1);
        assertNull(testSubject.getSelectedTab());
    }

    @Test void isVerticalNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isVertical());
    }

    @Test void selectTabByIndexNegative() {
        Locator tab1 = mock(Locator.class);
        mockFindTabs(tab1);
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.selectTab(-1));
    }
}
