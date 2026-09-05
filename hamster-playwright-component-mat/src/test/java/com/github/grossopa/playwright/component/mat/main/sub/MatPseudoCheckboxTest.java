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
 * Tests for {@link MatPseudoCheckbox}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatPseudoCheckboxTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    MatPseudoCheckbox testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MatPseudoCheckbox(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("PseudoCheckbox", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("PseudoCheckbox", MatPseudoCheckbox.COMPONENT_NAME);
    }

    @Test
    void componentConstructor() {
        WebComponent component = mock(WebComponent.class);
        when(component.locator()).thenReturn(locator);
        assertInstanceOf(MatPseudoCheckbox.class, new MatPseudoCheckbox(component, driver, config));
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-pseudo-checkbox");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-checkbox");
        assertFalse(testSubject.validate());
    }

    @Test
    void isSelected() {
        when(locator.getAttribute("class")).thenReturn("mat-pseudo-checkbox mat-pseudo-checkbox-checked");
        assertTrue(testSubject.isSelected());
    }

    @Test
    void isSelectedFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-pseudo-checkbox");
        assertFalse(testSubject.isSelected());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatPseudoCheckbox"));
    }
}
