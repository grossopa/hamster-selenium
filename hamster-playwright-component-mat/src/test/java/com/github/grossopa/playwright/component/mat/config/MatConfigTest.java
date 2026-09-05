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
package com.github.grossopa.playwright.component.mat.config;

import com.github.grossopa.playwright.core.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatConfig}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatConfigTest {

    MatConfig testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MatConfig();
    }

    @Test
    void getTagPrefix() {
        assertEquals("mat-", testSubject.getTagPrefix());
    }

    @Test
    void setTagPrefix() {
        testSubject.setTagPrefix("abc-");
        assertEquals("abc-", testSubject.getTagPrefix());
    }

    @Test
    void setTagPrefixNull() {
        assertThrows(NullPointerException.class, () -> testSubject.setTagPrefix(null));
    }

    @Test
    void getCssPrefix() {
        assertEquals("mat-", testSubject.getCssPrefix());
    }

    @Test
    void setCssPrefix() {
        testSubject.setCssPrefix("abc-");
        assertEquals("abc-", testSubject.getCssPrefix());
    }

    @Test
    void setCssPrefixNull() {
        assertThrows(NullPointerException.class, () -> testSubject.setCssPrefix(null));
    }

    @Test
    void getCdkPrefix() {
        assertEquals("cdk-", testSubject.getCdkPrefix());
    }

    @Test
    void setCdkPrefix() {
        testSubject.setCdkPrefix("abc-");
        assertEquals("abc-", testSubject.getCdkPrefix());
    }

    @Test
    void setCdkPrefixNull() {
        assertThrows(NullPointerException.class, () -> testSubject.setCdkPrefix(null));
    }

    @Test
    void getOverlayAbsolutePath() {
        assertEquals("/html/body", testSubject.getOverlayAbsolutePath());
    }

    @Test
    void setOverlayAbsolutePath() {
        testSubject.setOverlayAbsolutePath("/html/body/div/div/div");
        assertEquals("/html/body/div/div/div", testSubject.getOverlayAbsolutePath());
    }

    @Test
    void setOverlayAbsolutePathNull() {
        assertThrows(NullPointerException.class, () -> testSubject.setOverlayAbsolutePath(null));
    }

    @Test
    void getIsCheckedCss() {
        assertEquals("mat-checked", testSubject.getIsCheckedCss());
    }

    @Test
    void getIsSelectedCss() {
        assertEquals("mat-selected", testSubject.getIsSelectedCss());
    }

    @Test
    void getIsDisabledCss() {
        assertEquals("mat-disabled", testSubject.getIsDisabledCss());
    }

    @Test
    void isCheckedTrue() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("mat-checkbox mat-checked");
        assertTrue(testSubject.isChecked(component));
    }

    @Test
    void isCheckedFalse() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("mat-checkbox");
        assertFalse(testSubject.isChecked(component));
    }

    @Test
    void isCheckedNullClass() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isChecked(component));
    }

    @Test
    void isSelectedTrue() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("mat-option mat-selected");
        assertTrue(testSubject.isSelected(component));
    }

    @Test
    void isSelectedFalse() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("mat-option");
        assertFalse(testSubject.isSelected(component));
    }

    @Test
    void isDisabledByClass() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("mat-button mat-disabled");
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isDisabledByAria() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("mat-button");
        when(component.getAttribute("aria-disabled")).thenReturn("true");
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isDisabledFalse() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("mat-button");
        when(component.getAttribute("aria-disabled")).thenReturn("false");
        assertFalse(testSubject.isDisabled(component));
    }

    @Test
    void isDisabledByNativeDisabled() {
        WebComponent component = mock(WebComponent.class);
        when(component.isDisabled()).thenReturn(true);
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void create() {
        MatConfig config = MatConfig.create("tag-", "css-", "cdk-", "/html/body/div");
        assertEquals("tag-", config.getTagPrefix());
        assertEquals("css-", config.getCssPrefix());
        assertEquals("cdk-", config.getCdkPrefix());
        assertEquals("/html/body/div", config.getOverlayAbsolutePath());
    }

    @Test
    void equalsSameInstance() {
        assertEquals(testSubject, testSubject);
    }

    @Test
    void equalsSameValues() {
        assertEquals(testSubject, new MatConfig());
    }

    @Test
    void equalsSameValuesHashCode() {
        assertEquals(testSubject.hashCode(), new MatConfig().hashCode());
    }

    @Test
    void equalsDifferentTagPrefix() {
        assertNotEquals(testSubject, MatConfig.create("tag-", "mat-", "cdk-", "/html/body"));
    }

    @Test
    void equalsDifferentCssPrefix() {
        assertNotEquals(testSubject, MatConfig.create("mat-", "css-", "cdk-", "/html/body"));
    }

    @Test
    void equalsDifferentCdkPrefix() {
        assertNotEquals(testSubject, MatConfig.create("mat-", "mat-", "cdk2-", "/html/body"));
    }

    @Test
    void equalsDifferentOverlayAbsolutePath() {
        assertNotEquals(testSubject, MatConfig.create("mat-", "mat-", "cdk-", "/html/body/div"));
    }

    @Test
    void equalsNull() {
        assertNotEquals(null, testSubject);
    }

    @Test
    void equalsDifferentType() {
        assertNotEquals("string", testSubject);
    }

    @Test
    void testToString() {
        assertEquals("MatConfig{tagPrefix='mat-', cssPrefix='mat-', cdkPrefix='cdk-', "
                + "overlayAbsolutePath='/html/body'}", testSubject.toString());
    }
}
