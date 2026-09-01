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
 * Tests for {@link MatProgressBar}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatProgressBarTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    MatProgressBar testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MatProgressBar(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("ProgressBar", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("ProgressBar", MatProgressBar.COMPONENT_NAME);
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-progress-bar");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-slider");
        assertFalse(testSubject.validate());
    }

    @Test
    void getMinValue() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("0");
        assertEquals("0", testSubject.getMinValue());
    }

    @Test
    void getMaxValue() {
        when(locator.getAttribute("aria-valuemax")).thenReturn("100");
        assertEquals("100", testSubject.getMaxValue());
    }

    @Test
    void getValue() {
        when(locator.getAttribute("aria-valuenow")).thenReturn("40");
        assertEquals("40", testSubject.getValue());
    }

    @Test
    void getModeDeterminate() {
        when(locator.getAttribute("mode")).thenReturn("determinate");
        assertEquals(MatProgressBar.Mode.DETERMINATE, testSubject.getMode());
    }

    @Test
    void getModeIndeterminate() {
        when(locator.getAttribute("mode")).thenReturn("indeterminate");
        assertEquals(MatProgressBar.Mode.INDETERMINATE, testSubject.getMode());
    }

    @Test
    void getModeBuffer() {
        when(locator.getAttribute("mode")).thenReturn("buffer");
        assertEquals(MatProgressBar.Mode.BUFFER, testSubject.getMode());
    }

    @Test
    void getModeQuery() {
        when(locator.getAttribute("mode")).thenReturn("query");
        assertEquals(MatProgressBar.Mode.QUERY, testSubject.getMode());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatProgressBar"));
    }
}
