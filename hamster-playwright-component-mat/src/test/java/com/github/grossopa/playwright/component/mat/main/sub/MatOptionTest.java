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
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatOption}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatOptionTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    MatOption testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MatOption(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Option", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("Option", MatOption.COMPONENT_NAME);
    }

    @Test
    void componentConstructor() {
        WebComponent component = mock(WebComponent.class);
        when(component.locator()).thenReturn(locator);
        assertInstanceOf(MatOption.class, new MatOption(component, driver, config));
    }

    @Test
    void validate() {
        when(locator.evaluate("el => el.tagName")).thenReturn("mat-option");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateCaseInsensitive() {
        when(locator.evaluate("el => el.tagName")).thenReturn("MAT-OPTION");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.evaluate("el => el.tagName")).thenReturn("div");
        assertFalse(testSubject.validate());
    }

    @Test
    void isSelected() {
        when(locator.getAttribute("class")).thenReturn("mat-option mat-selected");
        assertTrue(testSubject.isSelected());
    }

    @Test
    void isSelectedFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-option");
        assertFalse(testSubject.isSelected());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatOption"));
    }
}
