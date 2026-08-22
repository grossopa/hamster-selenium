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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiButtonGroupTest {
    MuiButtonGroup testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiButtonGroup(locator, driver, config); }

    @Test void getComponentName() { assertEquals("ButtonGroup", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindButtons(Locator... btnLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator("button")).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(btnLocators));
    }

    // getButtons
    @Test void getButtonsEmpty() {
        mockFindButtons();
        assertTrue(testSubject.getButtons().isEmpty());
    }

    @Test void getButtonsTwo() {
        mockFindButtons(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getButtons().size());
    }

    // getButtonCount
    @Test void getButtonCount() {
        mockFindButtons(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getButtonCount());
    }

    // clickButton(int)
    @Test void clickButtonByIndex() {
        Locator btnLocator = mock(Locator.class);
        mockFindButtons(btnLocator);
        testSubject.clickButton(0);
        verify(btnLocator).click();
    }

    @Test void clickButtonByIndexOutOfBounds() {
        mockFindButtons();
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.clickButton(0));
    }

    @Test void clickButtonByIndexNegative() {
        mockFindButtons(mock(Locator.class));
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.clickButton(-1));
    }

    // clickButton(String)
    @Test void clickButtonByText() {
        Locator btnLocator = mock(Locator.class);
        when(btnLocator.innerText()).thenReturn("Save");
        mockFindButtons(btnLocator);
        testSubject.clickButton("Save");
        verify(btnLocator).click();
    }

    @Test void clickButtonByTextNotFound() {
        mockFindButtons();
        assertThrows(IllegalArgumentException.class, () -> testSubject.clickButton("Save"));
    }

    // isVertical
    @Test void isVerticalTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiButtonGroup-root MuiButtonGroup-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiButtonGroup-root");
        assertFalse(testSubject.isVertical());
    }

    @Test void isVerticalNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isVertical());
    }
}
