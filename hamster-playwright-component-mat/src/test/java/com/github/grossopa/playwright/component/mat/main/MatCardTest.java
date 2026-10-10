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
 * Tests for {@link MatCard}
 *
 * @author Jack Yin
 * @since 1.16
 */
class MatCardTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    MatCard testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MatCard(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Card", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("Card", MatCard.COMPONENT_NAME);
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-mdc-card");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-mdc-button");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatCard"));
    }

    @Test
    void getTitle() {
        Locator titleLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-card-title")).thenReturn(titleLocator);
        when(titleLocator.first()).thenReturn(titleLocator);
        assertNotNull(testSubject.getTitle());
    }

    @Test
    void getSubtitle() {
        Locator subtitleLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-card-subtitle")).thenReturn(subtitleLocator);
        when(subtitleLocator.first()).thenReturn(subtitleLocator);
        assertNotNull(testSubject.getSubtitle());
    }

    @Test
    void getContent() {
        Locator contentLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-card-content")).thenReturn(contentLocator);
        when(contentLocator.first()).thenReturn(contentLocator);
        assertNotNull(testSubject.getContent());
    }

    @Test
    void getActions() {
        Locator actionsLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-card-actions")).thenReturn(actionsLocator);
        when(actionsLocator.first()).thenReturn(actionsLocator);
        assertNotNull(testSubject.getActions());
    }

    @Test
    void getHeader() {
        Locator headerLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-card-header")).thenReturn(headerLocator);
        when(headerLocator.first()).thenReturn(headerLocator);
        assertNotNull(testSubject.getHeader());
    }

    @Test
    void getFooter() {
        Locator footerLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-card-footer")).thenReturn(footerLocator);
        when(footerLocator.first()).thenReturn(footerLocator);
        assertNotNull(testSubject.getFooter());
    }

}
