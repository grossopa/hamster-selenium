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
package com.github.grossopa.selenium.recorder.component;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatComponentDetector}
 *
 * @author Jack Yin
 * @since 1.17
 */
class MatComponentDetectorTest {

    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    WebElement element = mock(WebElement.class);
    MatComponentDetector testSubject = new MatComponentDetector();

    @Test
    void testDetectMdcButtonByElementOwnClass() {
        when(driver.executeScript(anyString(), any(), any()))
                .thenReturn(List.of("mat-mdc-button-base mat-mdc-button", ""));
        Optional<DetectedComponent> result = testSubject.detect(element, driver);
        assertTrue(result.isPresent());
        assertEquals("Button", result.get().getComponentName());
        assertEquals("MatButton", result.get().getTypeName());
        assertEquals("toButton", result.get().getFactoryMethodName());
        assertEquals(ComponentFramework.MAT, result.get().getFramework());
    }

    @Test
    void testDetectNonMdcAccordionByAncestorClass() {
        when(driver.executeScript(anyString(), any(), any()))
                .thenReturn(List.of("mat-expansion-panel-header", "", "mat-accordion"));
        Optional<DetectedComponent> result = testSubject.detect(element, driver);
        assertTrue(result.isPresent());
        assertEquals("Accordion", result.get().getComponentName());
        assertEquals("MatAccordion", result.get().getTypeName());
    }

    @Test
    void testDetectMdcFormFieldByAncestorClass() {
        when(driver.executeScript(anyString(), any(), any()))
                .thenReturn(List.of("mat-mdc-input-element", "", "mat-mdc-form-field"));
        Optional<DetectedComponent> result = testSubject.detect(element, driver);
        assertTrue(result.isPresent());
        // Should match Input first (element's own class) before FormField (ancestor)
        assertEquals("Input", result.get().getComponentName());
    }

    @Test
    void testDetectNonMdcSidenav() {
        when(driver.executeScript(anyString(), any(), any())).thenReturn(List.of("mat-drawer-inner-container", "",
                "mat-drawer"));
        Optional<DetectedComponent> result = testSubject.detect(element, driver);
        assertTrue(result.isPresent());
        assertEquals("Sidenav", result.get().getComponentName());
    }

    @Test
    void testDetectNoMatch() {
        when(driver.executeScript(anyString(), any(), any())).thenReturn(List.of("foo bar", "baz"));
        assertTrue(testSubject.detect(element, driver).isEmpty());
    }

    @Test
    void testDetectWithNonListResult() {
        when(driver.executeScript(anyString(), any(), any())).thenReturn("invalid");
        assertTrue(testSubject.detect(element, driver).isEmpty());
    }

    @Test
    void testDetectWithEmptyClass() {
        when(driver.executeScript(anyString(), any(), any())).thenReturn(List.of("", "", ""));
        assertTrue(testSubject.detect(element, driver).isEmpty());
    }
}
