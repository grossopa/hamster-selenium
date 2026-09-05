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
package com.github.grossopa.playwright.component.mat.finder;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.exception.MenuItemNotFoundException;
import com.github.grossopa.playwright.component.mat.main.MatMenu;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MatMenuItemFinder}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatMenuItemFinderTest {

    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    MatMenuItemFinder testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MatMenuItemFinder(driver, config);
    }

    @Test
    void findTopMenu() {
        WebComponent box = mock(WebComponent.class);
        WebComponent panel = mock(WebComponent.class);
        Locator panelLocator = mock(Locator.class);
        when(driver.findComponents(".cdk-overlay-connected-position-bounding-box")).thenReturn(List.of(box));
        when(box.isVisible()).thenReturn(true);
        when(box.findComponent(".mat-menu-panel")).thenReturn(panel);
        when(panel.locator()).thenReturn(panelLocator);

        MatMenu result = testSubject.findTopMenu();

        assertEquals("Menu", result.getComponentName());
    }

    @Test
    void findTopMenuSkipsInvisibleBox() {
        WebComponent invisibleBox = mock(WebComponent.class);
        WebComponent visibleBox = mock(WebComponent.class);
        WebComponent panel = mock(WebComponent.class);
        Locator panelLocator = mock(Locator.class);
        when(driver.findComponents(".cdk-overlay-connected-position-bounding-box")).thenReturn(
                List.of(invisibleBox, visibleBox));
        when(invisibleBox.isVisible()).thenReturn(false);
        when(visibleBox.isVisible()).thenReturn(true);
        when(visibleBox.findComponent(".mat-menu-panel")).thenReturn(panel);
        when(panel.locator()).thenReturn(panelLocator);

        MatMenu result = testSubject.findTopMenu();

        assertEquals("Menu", result.getComponentName());
    }

    @Test
    void findTopMenuNotFound() {
        when(driver.findComponents(".cdk-overlay-connected-position-bounding-box")).thenReturn(List.of());
        assertThrows(MenuItemNotFoundException.class, () -> testSubject.findTopMenu());
    }

    @Test
    void findMenus() {
        MatMenu menu = mock(MatMenu.class);
        doReturn(List.of(menu)).when(driver).findComponentsAs(anyString(), any());
        assertEquals(List.of(menu), testSubject.findMenus());
    }

    @Test
    void findMenusSelector() {
        doReturn(List.of()).when(driver).findComponentsAs(anyString(), any());
        testSubject.findMenus();
        verify(driver).findComponentsAs(eq(".cdk-overlay-connected-position-bounding-box .mat-menu-panel"), any());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatMenuItemFinder"));
    }
}
