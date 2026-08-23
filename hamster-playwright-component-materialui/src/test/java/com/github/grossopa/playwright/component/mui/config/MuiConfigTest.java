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
package com.github.grossopa.playwright.component.mui.config;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.core.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiConfig}
 *
 * @author Jack Yin
 * @since 1.12
 */
class MuiConfigTest {

    MuiConfig testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MuiConfig();
    }

    @Test
    void defaultValues() {
        assertEquals(MuiVersion.V4, testSubject.getVersion());
        assertEquals("Mui", testSubject.getCssPrefix());
        assertEquals("/html/body", testSubject.getOverlayAbsolutePath());
    }

    @Test
    void setAndGetVersion() {
        testSubject.setVersion(MuiVersion.V5);
        assertEquals(MuiVersion.V5, testSubject.getVersion());
    }

    @Test
    void setAndGetCssPrefix() {
        testSubject.setCssPrefix("My");
        assertEquals("My", testSubject.getCssPrefix());
    }

    @Test
    void setAndGetOverlayAbsolutePath() {
        testSubject.setOverlayAbsolutePath("/custom/path");
        assertEquals("/custom/path", testSubject.getOverlayAbsolutePath());
    }

    @Test
    void getRootCss() {
        assertEquals("MuiButton-root", testSubject.getRootCss("Button"));
        assertEquals("MuiTextField-root", testSubject.getRootCss("TextField"));
    }

    @Test
    void getRootCssCustomPrefix() {
        testSubject.setCssPrefix("My");
        assertEquals("MyButton-root", testSubject.getRootCss("Button"));
    }

    @Test
    void getModalClasses() {
        Set<String> classes = testSubject.getModalClasses();
        assertEquals(5, classes.size());
        assertTrue(classes.contains("MuiDrawer-root"));
        assertTrue(classes.contains("MuiDialog-root"));
        assertTrue(classes.contains("MuiPopover-root"));
        assertTrue(classes.contains("MuiPager-root"));
        assertTrue(classes.contains("MuiMenu-root"));
    }

    @Test
    void sliderThumbLocator() {
        assertEquals(".MuiSlider-thumb", testSubject.sliderThumbLocator());
    }

    @Test
    void sliderThumbLocatorCustomPrefix() {
        testSubject.setCssPrefix("My");
        assertEquals(".MySlider-thumb", testSubject.sliderThumbLocator());
    }

    @Test
    void isCheckedCss() {
        assertEquals("Mui-checked", testSubject.getIsCheckedCss());
    }

    @Test
    void isSelectedCss() {
        assertEquals("Mui-selected", testSubject.getIsSelectedCss());
    }

    @Test
    void isDisabledCss() {
        assertEquals("Mui-disabled", testSubject.getIsDisabledCss());
    }

    @Test
    void isCheckedTrue() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("MuiButton-root Mui-checked");
        assertTrue(testSubject.isChecked(component));
    }

    @Test
    void isCheckedFalse() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("MuiButton-root");
        assertFalse(testSubject.isChecked(component));
    }

    @Test
    void isCheckedNullComponent() {
        assertFalse(testSubject.isChecked(null));
    }

    @Test
    void isCheckedNullClassName() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isChecked(component));
    }

    @Test
    void isSelectedTrue() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("MuiTab-root Mui-selected");
        assertTrue(testSubject.isSelected(component));
    }

    @Test
    void isSelectedFalse() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("MuiTab-root");
        assertFalse(testSubject.isSelected(component));
    }

    @Test
    void isSelectedNullComponent() {
        assertFalse(testSubject.isSelected(null));
    }

    @Test
    void isSelectedNullClassName() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isSelected(component));
    }

    @Test
    void isDisabledTrue() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("MuiButton-root Mui-disabled");
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isDisabledFalse() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn("MuiButton-root");
        assertFalse(testSubject.isDisabled(component));
    }

    @Test
    void isDisabledNullComponent() {
        assertTrue(testSubject.isDisabled(null));
    }

    @Test
    void isDisabledNullClassName() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isDisabled(component));
    }

    @Test
    void equalsSame() {
        assertEquals(testSubject, testSubject);
    }

    @Test
    void equalsEqual() {
        MuiConfig other = new MuiConfig();
        assertEquals(testSubject, other);
        assertEquals(testSubject.hashCode(), other.hashCode());
    }

    @Test
    void equalsDifferentVersion() {
        MuiConfig other = new MuiConfig();
        other.setVersion(MuiVersion.V5);
        assertNotEquals(testSubject, other);
    }

    @Test
    void equalsDifferentPrefix() {
        MuiConfig other = new MuiConfig();
        other.setCssPrefix("My");
        assertNotEquals(testSubject, other);
    }

    @Test
    void equalsDifferentOverlay() {
        MuiConfig other = new MuiConfig();
        other.setOverlayAbsolutePath("/custom");
        assertNotEquals(testSubject, other);
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
    void toStringContainsFields() {
        String str = testSubject.toString();
        assertTrue(str.contains("V4"));
        assertTrue(str.contains("Mui"));
        assertTrue(str.contains("/html/body"));
    }
}
