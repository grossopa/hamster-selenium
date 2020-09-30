/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.component.mui.config;

import org.hamster.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals("By.xpath: *[contains(@class, 'MuiButton-root')]", testSubject.buttonLocator().toString());
    }

    @Test
    void popoverLocator() {
        assertEquals("By.xpath: /html/body/div[contains(@class, 'MuiPopover-root')]",
                testSubject.popoverLocator().toString());
    }

    @Test
    void isChecked() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute(MuiConfig.ATTR_CLASS)).thenReturn("some-other some-thing Muiabc Mui-checked");
        assertTrue(testSubject.isChecked(component));
    }

    @Test
    void isCheckedNegative() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute(MuiConfig.ATTR_CLASS)).thenReturn("some-other some-thing Muiabc Mui-some");
        assertFalse(testSubject.isChecked(component));
    }

    @Test
    void isSelected() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute(MuiConfig.ATTR_CLASS)).thenReturn("some-other some-thing Muiabc Mui-selected");
        assertTrue(testSubject.isSelected(component));
    }

    @Test
    void isSelectedNegative() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute(MuiConfig.ATTR_CLASS)).thenReturn("some-other some-thing Muiabc Mui-some");
        assertFalse(testSubject.isSelected(component));
    }

    @Test
    void isDisabled() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute(MuiConfig.ATTR_CLASS)).thenReturn("some-other some-thing Muiabc Mui-disabled");
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isDisabledNegative() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute(MuiConfig.ATTR_CLASS)).thenReturn("some-other some-thing Muiabc Mui-some");
        assertFalse(testSubject.isDisabled(component));
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
    void validateByCss() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute(MuiConfig.ATTR_CLASS)).thenReturn("MuiPager-root MuiSomeOther");
        assertTrue(testSubject.validateByCss(component, "Pager"));
    }


    @Test
    void validateByCssNegative() {
        WebComponent component = mock(WebComponent.class);
        when(component.getAttribute(MuiConfig.ATTR_CLASS)).thenReturn("MuiSelect-root MuiSomeOther");
        assertFalse(testSubject.validateByCss(component, "Pager"));
    }

    @Test
    void setCssPrefix() {
        testSubject.setCssPrefix("some-other-prefix");
        assertEquals("some-other-prefix", testSubject.getCssPrefix());
    }
}
