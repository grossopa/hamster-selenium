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
import static org.mockito.Mockito.*;

class MatStepTest {

    MatStep testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-");
        testSubject = new MatStep(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Step", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-step");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-other");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(locator.toString()).thenReturn("inner-locator");
        assertEquals("MatStep{locator=inner-locator}", testSubject.toString());
    }

    @Test
    void getLabel() {
        Locator labelLocator = mock(Locator.class);
        when(locator.locator(".mat-step-label")).thenReturn(labelLocator);
        when(labelLocator.first()).thenReturn(labelLocator);
        assertNotNull(testSubject.getLabel());
    }

    @Test
    void isActiveTrue() {
        when(locator.getAttribute("class")).thenReturn("mat-step-active");
        assertTrue(testSubject.isActive());
    }

    @Test
    void isActiveFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-step");
        assertFalse(testSubject.isActive());
    }

    @Test
    void isCompletedTrue() {
        when(locator.getAttribute("class")).thenReturn("mat-step-completed");
        assertTrue(testSubject.isCompleted());
    }

    @Test
    void isCompletedFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-step");
        assertFalse(testSubject.isCompleted());
    }

    @Test
    void getContent() {
        Locator contentLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-step-content")).thenReturn(contentLocator);
        when(contentLocator.first()).thenReturn(firstLocator);
        assertNotNull(testSubject.getContent());
    }

}
