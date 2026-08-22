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

package com.github.grossopa.playwright.component.mui.v4.datadisplay;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiAvatarTest {
    MuiAvatar testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiAvatar(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Avatar", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getText() {
        when(locator.innerText()).thenReturn("AB");
        assertEquals("AB", testSubject.getText());
    }

    // findComponent("img") always returns non-null DefaultWebComponent
    @Test void isImageAvatarAlwaysTrue() {
        Locator imgChildLocator = mock(Locator.class);
        when(locator.locator("img")).thenReturn(imgChildLocator);
        when(imgChildLocator.first()).thenReturn(imgChildLocator);
        assertTrue(testSubject.isImageAvatar());
    }

    // getImg - findComponent("img") returns non-null wrapper
    @Test void getImg() {
        Locator imgChildLocator = mock(Locator.class);
        when(locator.locator("img")).thenReturn(imgChildLocator);
        when(imgChildLocator.first()).thenReturn(imgChildLocator);
        WebComponent img = testSubject.getImg();
        assertNotNull(img);
    }

    // getAlt - getImg() returns non-null, then getAttribute("alt")
    @Test void getAlt() {
        Locator imgChildLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator("img")).thenReturn(imgChildLocator);
        when(imgChildLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("alt")).thenReturn("User avatar");
        assertEquals("User avatar", testSubject.getAlt());
    }

    // getSrc - getImg() returns non-null, then getAttribute("src")
    @Test void getSrc() {
        Locator imgChildLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator("img")).thenReturn(imgChildLocator);
        when(imgChildLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("src")).thenReturn("https://example.com/avatar.png");
        assertEquals("https://example.com/avatar.png", testSubject.getSrc());
    }
}
