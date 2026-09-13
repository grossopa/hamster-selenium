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
 * Tests for {@link HtmlCheckbox}
 *
 * @author Jack Yin
 * @since 1.15
 */
class HtmlCheckboxTest {

    HtmlCheckbox testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);

    @BeforeEach
    void setUp() {
        testSubject = new HtmlCheckbox(locator, driver);
    }

    @Test
    void getComponentTagName() {
        assertEquals("input", testSubject.getComponentTagName());
    }

    @Test
    void validateTrue() {
        when(locator.evaluate("el => el.tagName")).thenReturn("INPUT");
        when(locator.evaluate("el => el.type")).thenReturn("checkbox");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalseWrongTag() {
        when(locator.evaluate("el => el.tagName")).thenReturn("div");
        when(locator.evaluate("el => el.type")).thenReturn("checkbox");
        assertFalse(testSubject.validate());
    }

    @Test
    void validateFalseWrongType() {
        when(locator.evaluate("el => el.tagName")).thenReturn("INPUT");
        when(locator.evaluate("el => el.type")).thenReturn("text");
        assertFalse(testSubject.validate());
    }

    @Test
    void isCheckedTrue() {
        when(locator.isChecked()).thenReturn(true);
        assertTrue(testSubject.isChecked());
    }

    @Test
    void isCheckedFalse() {
        when(locator.isChecked()).thenReturn(false);
        assertFalse(testSubject.isChecked());
    }

    @Test
    void check() {
        testSubject.check();
        verify(locator).check();
    }

    @Test
    void uncheck() {
        testSubject.uncheck();
        verify(locator).uncheck();
    }

    @Test
    void toggleWhenChecked() {
        when(locator.isChecked()).thenReturn(true);
        testSubject.toggle();
        verify(locator).uncheck();
    }

    @Test
    void toggleWhenUnchecked() {
        when(locator.isChecked()).thenReturn(false);
        testSubject.toggle();
        verify(locator).check();
    }
}
