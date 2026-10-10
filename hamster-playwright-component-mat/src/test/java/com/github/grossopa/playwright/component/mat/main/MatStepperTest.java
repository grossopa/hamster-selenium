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
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatStepperTest {

    MatStepper testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-");
        when(config.getTagPrefix()).thenReturn("mat-");
        testSubject = new MatStepper(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Stepper", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-stepper");
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
        assertEquals("MatStepper{locator=inner-locator}", testSubject.toString());
    }

    @Test
    void componentConstructor() {
        WebComponent component = mock(WebComponent.class);
        when(component.locator()).thenReturn(locator);
        assertInstanceOf(MatStepper.class, new MatStepper(component, driver, config));
    }

    @Test
    void getSteps() {
        Locator stepLocator = mock(Locator.class);
        when(locator.locator("mat-step.mat-step")).thenReturn(stepLocator);
        when(stepLocator.all()).thenReturn(List.of(stepLocator));
        assertEquals(1, testSubject.getSteps().size());
    }

    @Test
    void next() {
        Locator nextLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-stepper-next")).thenReturn(nextLocator);
        when(nextLocator.first()).thenReturn(firstLocator);
        testSubject.next();
        verify(firstLocator).click();
    }

    @Test
    void previous() {
        Locator prevLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-stepper-previous")).thenReturn(prevLocator);
        when(prevLocator.first()).thenReturn(firstLocator);
        testSubject.previous();
        verify(firstLocator).click();
    }

    @Test
    void isLinearTrue() {
        when(locator.getAttribute("class")).thenReturn("mat-stepper mat-stepper-linear");
        assertTrue(testSubject.isLinear());
    }

    @Test
    void isLinearFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-stepper");
        assertFalse(testSubject.isLinear());
    }

    @Test
    void isVerticalTrue() {
        when(locator.getAttribute("class")).thenReturn("mat-stepper mat-stepper-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test
    void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-stepper");
        assertFalse(testSubject.isVertical());
    }

    @Test
    void getHeader() {
        Locator headerLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-stepper-header")).thenReturn(headerLocator);
        when(headerLocator.first()).thenReturn(firstLocator);
        assertNotNull(testSubject.getHeader());
    }
}
