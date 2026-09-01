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
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatGridList}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatGridListTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    Locator tilesLocator = mock(Locator.class);

    MatGridList testSubject;

    @BeforeEach
    void setUp() {
        when(locator.locator(".mat-grid-tile")).thenReturn(tilesLocator);
        testSubject = new MatGridList(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("GridList", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("GridList", MatGridList.COMPONENT_NAME);
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-grid-list");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-list");
        assertFalse(testSubject.validate());
    }

    @Test
    void getCols() {
        when(locator.getAttribute("cols")).thenReturn("4");
        assertEquals(4, testSubject.getCols());
    }

    @Test
    void getGridTiles() {
        when(tilesLocator.all()).thenReturn(List.of(mock(Locator.class), mock(Locator.class)));
        assertEquals(2, testSubject.getGridTiles().size());
    }

    @Test
    void getGridTilesEmpty() {
        when(tilesLocator.all()).thenReturn(List.of());
        assertTrue(testSubject.getGridTiles().isEmpty());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatGridList"));
    }
}
