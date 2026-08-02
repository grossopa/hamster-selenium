/*
 * Copyright © 2023 the original author or authors.
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiSwitch}
 *
 * @author Jack Yin
 * @since 1.12
 */
class MuiSwitchTest {

    MuiSwitch testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiSwitch(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Switch", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void isCheckedTrue() {
        Locator buttonLocator = mock(Locator.class);
        when(locator.locator(".MuiIconButton-root")).thenReturn(buttonLocator);
        when(buttonLocator.first()).thenReturn(buttonLocator);
        when(buttonLocator.getAttribute("class")).thenReturn("MuiIconButton-root Mui-checked");
        assertTrue(testSubject.isChecked());
    }

    @Test
    void isCheckedFalse() {
        Locator buttonLocator = mock(Locator.class);
        when(locator.locator(".MuiIconButton-root")).thenReturn(buttonLocator);
        when(buttonLocator.first()).thenReturn(buttonLocator);
        when(buttonLocator.getAttribute("class")).thenReturn("MuiIconButton-root");
        assertFalse(testSubject.isChecked());
    }

    @Test
    void isEnabledTrue() {
        Locator buttonLocator = mock(Locator.class);
        when(locator.locator(".MuiIconButton-root")).thenReturn(buttonLocator);
        when(buttonLocator.first()).thenReturn(buttonLocator);
        when(buttonLocator.getAttribute("class")).thenReturn("MuiIconButton-root");
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalse() {
        Locator buttonLocator = mock(Locator.class);
        when(locator.locator(".MuiIconButton-root")).thenReturn(buttonLocator);
        when(buttonLocator.first()).thenReturn(buttonLocator);
        when(buttonLocator.getAttribute("class")).thenReturn("MuiIconButton-root Mui-disabled");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void toggle() {
        assertDoesNotThrow(() -> testSubject.toggle());
        // just verify no exception
    }

    @Test
    void turnOnWhenOff() {
        Locator buttonLocator = mock(Locator.class);
        when(locator.locator(".MuiIconButton-root")).thenReturn(buttonLocator);
        when(buttonLocator.first()).thenReturn(buttonLocator);
        when(buttonLocator.getAttribute("class")).thenReturn("MuiIconButton-root");
        testSubject.turnOn();
        verify(locator).click();
    }

    @Test
    void turnOnWhenAlreadyOn() {
        Locator buttonLocator = mock(Locator.class);
        when(locator.locator(".MuiIconButton-root")).thenReturn(buttonLocator);
        when(buttonLocator.first()).thenReturn(buttonLocator);
        when(buttonLocator.getAttribute("class")).thenReturn("MuiIconButton-root Mui-checked");
        testSubject.turnOn();
        verify(locator, never()).click();
    }

    @Test
    void turnOffWhenOn() {
        Locator buttonLocator = mock(Locator.class);
        when(locator.locator(".MuiIconButton-root")).thenReturn(buttonLocator);
        when(buttonLocator.first()).thenReturn(buttonLocator);
        when(buttonLocator.getAttribute("class")).thenReturn("MuiIconButton-root Mui-checked");
        testSubject.turnOff();
        verify(locator).click();
    }

    @Test
    void turnOffWhenAlreadyOff() {
        Locator buttonLocator = mock(Locator.class);
        when(locator.locator(".MuiIconButton-root")).thenReturn(buttonLocator);
        when(buttonLocator.first()).thenReturn(buttonLocator);
        when(buttonLocator.getAttribute("class")).thenReturn("MuiIconButton-root");
        testSubject.turnOff();
        verify(locator, never()).click();
    }
}
