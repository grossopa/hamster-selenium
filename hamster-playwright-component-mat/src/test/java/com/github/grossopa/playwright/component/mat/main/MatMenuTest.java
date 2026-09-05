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
import com.github.grossopa.playwright.component.mat.exception.MenuItemNotFoundException;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MatMenu}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatMenuTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    Locator itemsLocator = mock(Locator.class);
    Locator item1 = mock(Locator.class);
    Locator item2 = mock(Locator.class);

    MatMenu testSubject;

    @BeforeEach
    void setUp() {
        when(locator.locator(".mat-menu-item")).thenReturn(itemsLocator);
        when(itemsLocator.all()).thenReturn(List.of(item1, item2));
        testSubject = new MatMenu(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Menu", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("Menu", MatMenu.COMPONENT_NAME);
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-menu-panel");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-dialog-container");
        assertFalse(testSubject.validate());
    }

    @Test
    void getMenuItems() {
        assertEquals(2, testSubject.getMenuItems().size());
    }

    @Test
    void selectItemByIndex() {
        testSubject.selectItemByIndex(1);
        verify(item2).click();
    }

    @Test
    void selectItemByText() {
        when(item1.textContent()).thenReturn("One");
        when(item2.textContent()).thenReturn("Two");
        testSubject.selectItemByText("Two");
        verify(item2).click();
    }

    @Test
    void selectItemByTextNotFound() {
        when(item1.textContent()).thenReturn("One");
        when(item2.textContent()).thenReturn("Two");
        assertThrows(MenuItemNotFoundException.class, () -> testSubject.selectItemByText("Three"));
    }

    @Test
    void expandItemByIndexNotExpandable() {
        when(item1.getAttribute("class")).thenReturn("mat-menu-item");
        assertThrows(MenuItemNotFoundException.class, () -> testSubject.expandItemByIndex(0));
    }

    @Test
    void expandItemByTextNotExpandable() {
        when(item1.textContent()).thenReturn("One");
        when(item2.textContent()).thenReturn("Two");
        when(item2.getAttribute("class")).thenReturn("mat-menu-item");
        assertThrows(MenuItemNotFoundException.class, () -> testSubject.expandItemByText("Two"));
    }

    @Test
    void expandItemByIndex() {
        when(item1.getAttribute("class")).thenReturn("mat-menu-item mat-menu-item-submenu-trigger");
        WebComponent box = mock(WebComponent.class);
        WebComponent panel = mock(WebComponent.class);
        when(driver.findComponents(".cdk-overlay-connected-position-bounding-box")).thenReturn(List.of(box));
        when(box.isVisible()).thenReturn(true);
        when(box.findComponent(".mat-menu-panel")).thenReturn(panel);
        when(panel.locator()).thenReturn(mock(Locator.class));

        MatMenu childMenu = testSubject.expandItemByIndex(0);

        verify(item1).hover();
        assertEquals("Menu", childMenu.getComponentName());
    }

    @Test
    void close() {
        testSubject.close();
        verify(locator).press("Escape");
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatMenu"));
    }
}
