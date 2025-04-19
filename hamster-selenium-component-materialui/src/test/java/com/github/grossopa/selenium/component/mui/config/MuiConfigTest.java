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

package com.github.grossopa.selenium.component.mui.config;

import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiConfig}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiConfigTest {

    MuiConfig testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MuiConfig();
    }

    @Test
    void buttonLocator() {
        assertEquals("By.xpath: .//*[contains(@class,\"MuiButton-root\")]", testSubject.buttonLocator().toString());
    }

    @Test
    void radioLocator() {
        assertEquals("By.className: MuiRadio-root", testSubject.radioLocator().toString());
    }

    @Test
    void sliderThumbLocator() {
        assertEquals("By.className: MuiSlider-thumb", testSubject.sliderThumbLocator().toString());
    }

    @Test
    void menuPagerLocator() {
        assertEquals("By.className: MuiMenu-pager", testSubject.menuPagerLocator().toString());
    }

    @Test
    void isChecked() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("some-other some-thing Muiabc Mui-checked");
        assertTrue(testSubject.isChecked(component));
    }

    @Test
    void isCheckedNegative() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("some-other some-thing Muiabc Mui-some");
        assertFalse(testSubject.isChecked(component));
    }

    @Test
    void isSelected() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("some-other some-thing Muiabc Mui-selected");
        assertTrue(testSubject.isSelected(component));
    }

    @Test
    void isSelectedNegative() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("some-other some-thing Muiabc Mui-some");
        assertFalse(testSubject.isSelected(component));
    }

    @Test
    void isDisabled() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("some-other some-thing Muiabc Mui-disabled");
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isDisabledNegative() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("some-other some-thing Muiabc Mui-some");
        assertFalse(testSubject.isDisabled(component));
    }

    @Test
    void isGridContainer() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("MuiGrid-container Muiabc Mui-some");
        assertTrue(testSubject.isGridContainer(component));
    }

    @Test
    void isGridItem() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("MuiGrid-item Muiabc Mui-some");
        assertTrue(testSubject.isGridItem(component));
    }

    @Test
    void getIsCheckedCss() {
        assertEquals("Mui-checked", testSubject.getIsCheckedCss());
    }

    @Test
    void getIsSelectedCss() {
        assertEquals("Mui-selected", testSubject.getIsSelectedCss());
    }

    @Test
    void getIsDisabledCss() {
        assertEquals("Mui-disabled", testSubject.getIsDisabledCss());
    }

    @Test
    void getRootCss() {
        assertEquals("MuiCheckbox-root", testSubject.getRootCss("Checkbox"));
    }

    @Test
    void validateComponentByCss() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("MuiPager-root MuiSomeOther");
        assertTrue(testSubject.validateComponentByCss(component, "Pager"));
    }


    @Test
    void validateComponentByCssNegative() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("MuiSelect-root MuiSomeOther");
        assertFalse(testSubject.validateComponentByCss(component, "Pager"));
    }

    @Test
    void validateByCss() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("MuiPager-root MuiSomeOther");
        assertTrue(testSubject.validateByCss(component, "MuiSomeOther"));
    }

    @Test
    void validateByCssNegative() {
        WebComponent component = mock(WebComponent.class);
        when(component.getDomAttribute(CLASS)).thenReturn("MuiSelect-root MuiSomeOther");
        assertFalse(testSubject.validateComponentByCss(component, "Pager"));
    }

    @Test
    void setCssPrefix() {
        testSubject.setCssPrefix("some-other-prefix");
        assertEquals("some-other-prefix", testSubject.getCssPrefix());
    }

    @Test
    void setOverlayAbsolutePath() {
        testSubject.setOverlayAbsolutePath("/some/absolute/path");
        assertEquals("/some/absolute/path", testSubject.getOverlayAbsolutePath());
    }

    @Test
    void getOverlayAbsolutePath() {
        assertEquals("/html/body", testSubject.getOverlayAbsolutePath());
    }

    @Test
    void getModalClasses() {
        assertEquals(5, testSubject.getModalClasses().size());
    }

    @Test
    void testEquals() {
        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new MuiConfig(), new MuiConfig());
        MuiConfig config2 = new MuiConfig();
        config2.setCssPrefix("Mui2");
        tester.addEqualityGroup(config2);

        MuiConfig config3 = new MuiConfig();
        config3.setOverlayAbsolutePath("/other/path");
        tester.addEqualityGroup(config3);

        MuiConfig config4 = new MuiConfig();
        config4.setCssPrefix("Mui2");
        config4.setOverlayAbsolutePath("/other/path");
        tester.addEqualityGroup(config4);

        tester.testEquals();
    }

    @Test
    void testToString() {
        assertEquals("MuiConfig{version=V4, cssPrefix='Mui', overlayAbsolutePath='/html/body'}",
                testSubject.toString());
    }
}
