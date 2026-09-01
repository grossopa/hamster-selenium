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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatSnackbar}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatSnackbarTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    Locator labelLocator = mock(Locator.class);
    Locator buttonLocator = mock(Locator.class);

    MatSnackbar testSubject;

    @BeforeEach
    void setUp() {
        when(locator.locator("span")).thenReturn(labelLocator);
        when(locator.locator("button")).thenReturn(buttonLocator);
        when(labelLocator.first()).thenReturn(mock(Locator.class));
        when(buttonLocator.first()).thenReturn(mock(Locator.class));
        testSubject = new MatSnackbar(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Snackbar", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("Snackbar", MatSnackbar.COMPONENT_NAME);
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-simple-snackbar");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-snack-bar-container");
        assertFalse(testSubject.validate());
    }

    @Test
    void getLabel() {
        assertNotNull(testSubject.getLabel());
    }

    @Test
    void getActionButton() {
        assertInstanceOf(MatButton.class, testSubject.getActionButton());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatSnackbar"));
    }
}
