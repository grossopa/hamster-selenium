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
 * Tests for {@link MatSelectionList}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatSelectionListTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    Locator optionsLocator = mock(Locator.class);

    MatSelectionList testSubject;

    @BeforeEach
    void setUp() {
        when(locator.locator(".mat-list-option")).thenReturn(optionsLocator);
        testSubject = new MatSelectionList(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("SelectionList", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("SelectionList", MatSelectionList.COMPONENT_NAME);
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-selection-list");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-list");
        assertFalse(testSubject.validate());
    }

    @Test
    void getListOptions() {
        when(optionsLocator.all()).thenReturn(List.of(mock(Locator.class), mock(Locator.class)));
        assertEquals(2, testSubject.getListOptions().size());
    }

    @Test
    void getListOptionsEmpty() {
        when(optionsLocator.all()).thenReturn(List.of());
        assertTrue(testSubject.getListOptions().isEmpty());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatSelectionList"));
    }
}
