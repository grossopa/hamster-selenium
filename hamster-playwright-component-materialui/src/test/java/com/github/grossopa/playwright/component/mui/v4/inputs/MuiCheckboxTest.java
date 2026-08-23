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
package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiCheckbox}
 *
 * @author Jack Yin
 * @since 1.12
 */
class MuiCheckboxTest {

    MuiCheckbox testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiCheckbox(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Checkbox", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void isCheckedTrue() {
        // MuiCheckbox.isChecked delegates to config.isChecked(this) which checks getAttribute("class")
        // Since this is the component itself, we need to mock the locator to return class with Mui-checked
        Locator innerLocator = mock(Locator.class);
        when(locator.locator(".MuiIconButton-root")).thenReturn(innerLocator);
        when(innerLocator.first()).thenReturn(innerLocator);
        when(innerLocator.getAttribute("class")).thenReturn("MuiIconButton-root Mui-checked");

        // The isChecked method calls config.isChecked(this) which calls this.getAttribute("class")
        // But since the checkbox delegates to config.isChecked which checks the component's class attribute
        // We need to mock getAttribute on the testSubject itself - but it delegates to locator
        when(locator.getAttribute("class")).thenReturn("MuiCheckbox-root Mui-checked");
        assertTrue(testSubject.isChecked());
    }

    @Test
    void isCheckedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiCheckbox-root");
        assertFalse(testSubject.isChecked());
    }
}
