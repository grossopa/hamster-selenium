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
package com.github.grossopa.playwright.component.html;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link HtmlFormField}
 *
 * @author Jack Yin
 * @since 1.12
 */
class HtmlFormFieldTest {

    HtmlFormField testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);

    @BeforeEach
    void setUp() {
        testSubject = new HtmlFormField(locator, driver);
    }

    @Test
    void getComponentTagName() {
        assertEquals("form-field", testSubject.getComponentTagName());
    }

    @Test
    void testConstructor() {
        HtmlFormField instance = new HtmlFormField(locator, driver);
        assertEquals("form-field", instance.getComponentTagName());
    }

    @Test
    void getLabel() {
        Locator labelLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        
        when(locator.locator("label")).thenReturn(labelLocator);
        when(labelLocator.first()).thenReturn(firstLocator);
        
        WebComponent label = testSubject.getLabel();
        assertNotNull(label);
    }

    @Test
    void getInput() {
        Locator inputLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        
        when(locator.locator("input")).thenReturn(inputLocator);
        when(inputLocator.first()).thenReturn(firstLocator);
        
        WebComponent input = testSubject.getInput();
        assertNotNull(input);
    }
}
