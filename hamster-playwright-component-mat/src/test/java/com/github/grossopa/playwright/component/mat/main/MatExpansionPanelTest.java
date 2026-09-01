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

/**
 * Tests for {@link MatExpansionPanel}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatExpansionPanelTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    Locator headerLocator = mock(Locator.class);
    Locator headerFirst = mock(Locator.class);
    Locator bodyLocator = mock(Locator.class);

    MatExpansionPanel testSubject;

    @BeforeEach
    void setUp() {
        when(locator.locator(".mat-expansion-panel-header")).thenReturn(headerLocator);
        when(headerLocator.first()).thenReturn(headerFirst);
        when(locator.locator(".mat-expansion-panel-body")).thenReturn(bodyLocator);
        when(bodyLocator.first()).thenReturn(mock(Locator.class));
        testSubject = new MatExpansionPanel(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("ExpansionPanel", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("ExpansionPanel", MatExpansionPanel.COMPONENT_NAME);
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-expansion-panel");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-accordion");
        assertFalse(testSubject.validate());
    }

    @Test
    void isEnabled() {
        when(locator.getAttribute("disabled")).thenReturn(null);
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalse() {
        when(locator.getAttribute("disabled")).thenReturn("true");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void isExpanded() {
        when(locator.getAttribute("class")).thenReturn("mat-expansion-panel mat-expanded");
        assertTrue(testSubject.isExpanded());
    }

    @Test
    void isExpandedFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-expansion-panel");
        assertFalse(testSubject.isExpanded());
    }

    @Test
    void expand() {
        when(locator.getAttribute("class")).thenReturn("mat-expansion-panel");
        testSubject.expand();
        verify(headerFirst).click();
    }

    @Test
    void expandAlreadyExpanded() {
        when(locator.getAttribute("class")).thenReturn("mat-expansion-panel mat-expanded");
        testSubject.expand();
        verify(headerFirst, never()).click();
    }

    @Test
    void collapse() {
        when(locator.getAttribute("class")).thenReturn("mat-expansion-panel mat-expanded");
        testSubject.collapse();
        verify(headerFirst).click();
    }

    @Test
    void collapseNotExpanded() {
        when(locator.getAttribute("class")).thenReturn("mat-expansion-panel");
        testSubject.collapse();
        verify(headerFirst, never()).click();
    }

    @Test
    void getExpansionPanelHeader() {
        assertNotNull(testSubject.getExpansionPanelHeader());
    }

    @Test
    void getExpansionPanelBody() {
        assertNotNull(testSubject.getExpansionPanelBody());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatExpansionPanel"));
    }
}
