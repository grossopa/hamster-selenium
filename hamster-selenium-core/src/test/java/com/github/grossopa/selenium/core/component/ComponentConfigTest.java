/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.selenium.core.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ComponentConfig}
 *
 * @author Jack Yin
 * @since 1.6
 */
class ComponentConfigTest {

    ComponentConfig testSubject;
    WebComponent component = mock(WebComponent.class);
    WebElement element = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(element.isEnabled()).thenReturn(true);
        when(component.getWrappedElement()).thenReturn(element);

        when(component.getAttribute(CLASS)).thenReturn("prefix-selected prefix-checked prefix-disabled");

        testSubject = new ComponentConfig() {

            @Override
            public String getOverlayAbsolutePath() {
                return "/html/body";
            }

            @Override
            public String getCssPrefix() {
                return "prefix";
            }
        };
    }


    @Test
    void isChecked() {
        assertTrue(testSubject.isChecked(component));
    }

    @Test
    void isSelected() {
        assertTrue(testSubject.isSelected(component));
    }

    @Test
    void isDisabled() {
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isDisabled2() {
        when(element.isEnabled()).thenReturn(false);
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isCheckedFalse() {
        when(component.getAttribute(CLASS)).thenReturn("");
        assertFalse(testSubject.isChecked(component));
    }

    @Test
    void isSelectedFalse() {
        when(component.getAttribute(CLASS)).thenReturn("");
        assertFalse(testSubject.isSelected(component));
    }

    @Test
    void isDisabledFalse() {
        when(component.getAttribute(CLASS)).thenReturn("");
        assertFalse(testSubject.isDisabled(component));
    }

    @Test
    void getIsCheckedCss() {
        assertEquals("prefix-checked", testSubject.getIsCheckedCss());
    }

    @Test
    void getIsSelectedCss() {
        assertEquals("prefix-selected", testSubject.getIsSelectedCss());
    }

    @Test
    void getIsDisabledCss() {
        assertEquals("prefix-disabled", testSubject.getIsDisabledCss());
    }
}
