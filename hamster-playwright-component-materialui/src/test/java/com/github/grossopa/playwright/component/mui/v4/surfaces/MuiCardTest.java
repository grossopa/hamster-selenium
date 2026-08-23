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
package com.github.grossopa.playwright.component.mui.v4.surfaces;

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

class MuiCardTest {
    MuiCard testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiCard(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Card", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    /**
     * Mocks findComponent chain: locator.locator(any).first() → firstLocator
     */
    private Locator mockFindComponent() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        return firstLocator;
    }

    /**
     * Mocks the locator chain for getActions():
     * 1. findComponent(".MuiCardActions-root") → locator.locator(any).first() → always non-null
     * 2. actionsContainer.findComponents("button") → wrappedLocator.locator("button").all()
     */
    private void mockFindActions(Locator... buttonLocators) {
        Locator actionsRootLocator = mock(Locator.class);
        Locator actionsFirstLocator = mock(Locator.class);
        Locator buttonChildLocator = mock(Locator.class);

        when(locator.locator(anyString())).thenReturn(actionsRootLocator);
        when(actionsRootLocator.first()).thenReturn(actionsFirstLocator);
        when(actionsFirstLocator.locator(anyString())).thenReturn(buttonChildLocator);
        when(buttonChildLocator.all()).thenReturn(List.of(buttonLocators));
    }

    // getTitle
    @Test void getTitleFromCardHeader() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.innerText()).thenReturn("Card Title");
        assertEquals("Card Title", testSubject.getTitle());
    }

    // getSubtitle
    @Test void getSubtitle() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.innerText()).thenReturn("Card Subtitle");
        assertEquals("Card Subtitle", testSubject.getSubtitle());
    }

    // getContent
    @Test void getContent() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.innerText()).thenReturn("Card content text");
        assertEquals("Card content text", testSubject.getContent());
    }

    // getActions
    @Test void getActionCount() {
        mockFindActions(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getActionCount());
    }

    @Test void clickAction() {
        Locator btnLocator = mock(Locator.class);
        when(btnLocator.innerText()).thenReturn("Save");
        mockFindActions(btnLocator);
        testSubject.clickAction("Save");
        verify(btnLocator).click();
    }

    @Test void clickActionNotFound() {
        mockFindActions();
        assertThrows(IllegalArgumentException.class, () -> testSubject.clickAction("Save"));
    }

    // hasMedia - findComponent always returns non-null
    @Test void hasMedia() {
        mockFindComponent();
        assertTrue(testSubject.hasMedia());
    }

    // getMediaSrc - findComponent → media, then media.findComponent("img") → img
    @Test void getMediaSrc() {
        // First findComponent(".MuiCardMedia-root")
        Locator mediaChildLocator = mock(Locator.class);
        Locator mediaFirstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(mediaChildLocator);
        when(mediaChildLocator.first()).thenReturn(mediaFirstLocator);

        // Then media.findComponent("img") → mediaFirstLocator.locator("img").first()
        Locator imgChildLocator = mock(Locator.class);
        Locator imgFirstLocator = mock(Locator.class);
        when(mediaFirstLocator.locator(anyString())).thenReturn(imgChildLocator);
        when(imgChildLocator.first()).thenReturn(imgFirstLocator);
        when(imgFirstLocator.getAttribute("src")).thenReturn("https://example.com/image.png");

        assertEquals("https://example.com/image.png", testSubject.getMediaSrc());
    }

    // hasHeader - findComponent always returns non-null
    @Test void hasHeader() {
        mockFindComponent();
        assertTrue(testSubject.hasHeader());
    }
}
