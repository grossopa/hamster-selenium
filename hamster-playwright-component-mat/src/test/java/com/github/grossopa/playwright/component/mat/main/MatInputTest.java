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
 * Tests for {@link MatInput}
 *
 * @author Jack Yin
 * @since 1.16
 */
class MatInputTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    MatInput testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MatInput(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Input", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("Input", MatInput.COMPONENT_NAME);
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-input-element mat-form-field-autocomplete-trigger");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-select");
        assertFalse(testSubject.validate());
    }

    @Test
    void isEnabled() {
        when(locator.getAttribute("class")).thenReturn("mat-input-element");
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-input-element mat-input-element-disabled");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void getValue() {
        when(locator.inputValue()).thenReturn("hello");
        assertEquals("hello", testSubject.getValue());
    }

    @Test
    void getPlaceholder() {
        when(locator.getAttribute("placeholder")).thenReturn("Enter text");
        assertEquals("Enter text", testSubject.getPlaceholder());
    }

    @Test
    void getType() {
        when(locator.getAttribute("type")).thenReturn("text");
        assertEquals("text", testSubject.getType());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatInput"));
    }
}
