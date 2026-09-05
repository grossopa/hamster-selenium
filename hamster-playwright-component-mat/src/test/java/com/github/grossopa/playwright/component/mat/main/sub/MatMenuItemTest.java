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
package com.github.grossopa.playwright.component.mat.main.sub;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.exception.MenuItemNotExpandableException;
import com.github.grossopa.playwright.component.mat.main.MatMenu;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MatMenuItem}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatMenuItemTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    MatMenuItem testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MatMenuItem(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("MenuItem", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("MenuItem", MatMenuItem.COMPONENT_NAME);
    }

    @Test
    void componentConstructor() {
        WebComponent component = mock(WebComponent.class);
        when(component.locator()).thenReturn(locator);
        assertInstanceOf(MatMenuItem.class, new MatMenuItem(component, driver, config));
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-menu-item");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-menu-panel");
        assertFalse(testSubject.validate());
    }

    @Test
    void isExpandable() {
        when(locator.getAttribute("class")).thenReturn("mat-menu-item mat-menu-item-submenu-trigger");
        assertTrue(testSubject.isExpandable());
    }

    @Test
    void isExpandableFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-menu-item");
        assertFalse(testSubject.isExpandable());
    }

    @Test
    void isExpanded() {
        when(locator.getAttribute("aria-expanded")).thenReturn("true");
        assertTrue(testSubject.isExpanded());
    }

    @Test
    void isExpandedFalse() {
        when(locator.getAttribute("aria-expanded")).thenReturn("false");
        assertFalse(testSubject.isExpanded());
    }

    @Test
    void expandNotExpandable() {
        when(locator.getAttribute("class")).thenReturn("mat-menu-item");
        assertThrows(MenuItemNotExpandableException.class, () -> testSubject.expand());
    }

    @Test
    void expand() {
        when(locator.getAttribute("class")).thenReturn("mat-menu-item mat-menu-item-submenu-trigger");
        WebComponent box = mock(WebComponent.class);
        WebComponent panel = mock(WebComponent.class);
        when(driver.findComponents(".cdk-overlay-connected-position-bounding-box")).thenReturn(List.of(box));
        when(box.isVisible()).thenReturn(true);
        when(box.findComponent(".mat-menu-panel")).thenReturn(panel);
        when(panel.locator()).thenReturn(mock(Locator.class));

        MatMenu childMenu = testSubject.expand();

        verify(locator).hover();
        assertEquals("Menu", childMenu.getComponentName());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatMenuItem"));
    }
}
