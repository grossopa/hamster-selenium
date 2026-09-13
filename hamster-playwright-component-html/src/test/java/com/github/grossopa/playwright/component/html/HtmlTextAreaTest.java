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
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link HtmlTextArea}
 *
 * @author Jack Yin
 * @since 1.15
 */
class HtmlTextAreaTest {

    HtmlTextArea testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);

    @BeforeEach
    void setUp() {
        testSubject = new HtmlTextArea(locator, driver);
    }

    @Test
    void getComponentTagName() {
        assertEquals("textarea", testSubject.getComponentTagName());
    }

    @Test
    void validateTrue() {
        when(locator.evaluate("el => el.tagName")).thenReturn("TEXTAREA");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.evaluate("el => el.tagName")).thenReturn("input");
        assertFalse(testSubject.validate());
    }

    @Test
    void getValue() {
        when(locator.inputValue()).thenReturn("Hello World");
        assertEquals("Hello World", testSubject.getValue());
    }

    @Test
    void setValue() {
        testSubject.setValue("New Value");
        verify(locator).fill("New Value");
    }

    @Test
    void appendText() {
        testSubject.appendText(" more text");
        verify(locator).pressSequentially(" more text");
    }

    @Test
    void clear() {
        testSubject.clear();
        verify(locator).clear();
    }

    @Test
    void getPlaceholder() {
        when(locator.getAttribute("placeholder")).thenReturn("Enter text...");
        assertEquals("Enter text...", testSubject.getPlaceholder());
    }

    @Test
    void getPlaceholderNull() {
        when(locator.getAttribute("placeholder")).thenReturn(null);
        assertNull(testSubject.getPlaceholder());
    }

    @Test
    void getRows() {
        when(locator.getAttribute("rows")).thenReturn("5");
        assertEquals(5, testSubject.getRows());
    }

    @Test
    void getRowsNotSet() {
        when(locator.getAttribute("rows")).thenReturn(null);
        assertEquals(-1, testSubject.getRows());
    }

    @Test
    void getCols() {
        when(locator.getAttribute("cols")).thenReturn("40");
        assertEquals(40, testSubject.getCols());
    }

    @Test
    void getColsNotSet() {
        when(locator.getAttribute("cols")).thenReturn(null);
        assertEquals(-1, testSubject.getCols());
    }

    @Test
    void getMaxLength() {
        when(locator.getAttribute("maxlength")).thenReturn("500");
        assertEquals(500, testSubject.getMaxLength());
    }

    @Test
    void getMaxLengthNotSet() {
        when(locator.getAttribute("maxlength")).thenReturn(null);
        assertEquals(-1, testSubject.getMaxLength());
    }

    @Test
    void isReadOnlyTrue() {
        when(locator.getAttribute("readonly")).thenReturn("");
        assertTrue(testSubject.isReadOnly());
    }

    @Test
    void isReadOnlyFalse() {
        when(locator.getAttribute("readonly")).thenReturn(null);
        assertFalse(testSubject.isReadOnly());
    }

    @Test
    void isRequiredTrue() {
        when(locator.getAttribute("required")).thenReturn("");
        assertTrue(testSubject.isRequired());
    }

    @Test
    void isRequiredFalse() {
        when(locator.getAttribute("required")).thenReturn(null);
        assertFalse(testSubject.isRequired());
    }
}
