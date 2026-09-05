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
package com.github.grossopa.playwright.component.mat;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AbstractMatComponent}
 *
 * @author Jack Yin
 * @since 1.15
 */
class AbstractMatComponentTest {

    static class TestMatComponent extends AbstractMatComponent {
        TestMatComponent(Locator locator, ComponentDriver driver, MatConfig config) {
            super(locator, driver, config);
        }

        @Override
        public String getComponentName() {
            return "TestComponent";
        }

        @Override
        public boolean validate() {
            return true;
        }
    }

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    TestMatComponent testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new TestMatComponent(locator, driver, config);
    }

    @Test
    void getConfig() {
        assertSame(config, testSubject.getConfig());
    }

    @Test
    void configNotNull() {
        assertThrows(NullPointerException.class, () -> new TestMatComponent(locator, driver, null));
    }

    @Test
    void getComponentName() {
        assertEquals("TestComponent", testSubject.getComponentName());
    }

    @Test
    void isSelectedTrue() {
        when(locator.getAttribute("class")).thenReturn("mat-option mat-selected");
        assertTrue(testSubject.isSelected());
    }

    @Test
    void isSelectedFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-option");
        assertFalse(testSubject.isSelected());
    }

    @Test
    void isSelectedNullClass() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isSelected());
    }

    @Test
    void isEnabledTrue() {
        when(locator.getAttribute("class")).thenReturn("mat-option");
        when(locator.getAttribute("aria-disabled")).thenReturn("false");
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalseByClass() {
        when(locator.getAttribute("class")).thenReturn("mat-option mat-disabled");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalseByAria() {
        when(locator.getAttribute("class")).thenReturn("mat-option");
        when(locator.getAttribute("aria-disabled")).thenReturn("true");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalseByNativeDisabled() {
        when(locator.isDisabled()).thenReturn(true);
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void getComponentTagName() {
        when(locator.evaluate("el => el.tagName")).thenReturn("MAT-OPTION");
        assertEquals("mat-option", testSubject.getComponentTagName());
    }

    @Test
    void getComponentTagNameNull() {
        when(locator.evaluate("el => el.tagName")).thenReturn(null);
        assertNull(testSubject.getComponentTagName());
    }

    @Test
    void equalsSameInstance() {
        assertEquals(testSubject, testSubject);
    }

    @Test
    void equalsDifferentInstance() {
        // super.equals() uses identity comparison, so different instances are never equal
        TestMatComponent other = new TestMatComponent(locator, driver, config);
        assertNotEquals(testSubject, other);
    }

    @Test
    void equalsNull() {
        assertNotEquals(null, testSubject);
    }

    @Test
    void equalsDifferentType() {
        assertNotEquals("string", testSubject);
    }

    @Test
    void hashCodeConsistent() {
        assertEquals(testSubject.hashCode(), testSubject.hashCode());
    }

    @Test
    void toStringContainsClassName() {
        assertTrue(testSubject.toString().contains("TestMatComponent"));
    }

    @Test
    void toStringContainsLocator() {
        assertTrue(testSubject.toString().contains("locator="));
    }
}
