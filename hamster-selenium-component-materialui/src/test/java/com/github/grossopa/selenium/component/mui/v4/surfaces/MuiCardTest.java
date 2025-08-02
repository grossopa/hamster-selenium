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

package com.github.grossopa.selenium.component.mui.v4.surfaces;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiCard}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiCardTest {

    MuiCard testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiCard(element, driver, config);
        when(config.getCssPrefix()).thenReturn("Mui");
    }

    @Test
    void getComponentName() {
        assertEquals("Card", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertTrue(testSubject.versions().contains(V4));
        assertTrue(testSubject.versions().contains(V5));
        assertTrue(testSubject.versions().contains(V6));
    }

    @Test
    void getTitle() {
        // Test when CardHeader-title is found
        WebComponent titleElement = mock(WebComponent.class);
        when(titleElement.getText()).thenReturn("Card Title");
        when(testSubject.findComponent(By.className("MuiCardHeader-title"))).thenReturn(titleElement);
        
        assertEquals("Card Title", testSubject.getTitle());
    }

    @Test
    void getTitle_fallback() {
        // Test fallback to h1 when CardHeader-title is not found
        when(testSubject.findComponent(By.className("MuiCardHeader-title"))).thenThrow(new RuntimeException());
        
        WebComponent h1Element = mock(WebComponent.class);
        when(h1Element.getText()).thenReturn("H1 Title");
        when(testSubject.findComponent(By.tagName("h1"))).thenReturn(h1Element);
        
        assertEquals("H1 Title", testSubject.getTitle());
    }

    @Test
    void getTitle_null() {
        // Test when no title elements are found
        when(testSubject.findComponent(By.className("MuiCardHeader-title"))).thenThrow(new RuntimeException());
        when(testSubject.findComponent(By.tagName("h1"))).thenThrow(new RuntimeException());
        when(testSubject.findComponent(By.tagName("h2"))).thenThrow(new RuntimeException());
        
        assertNull(testSubject.getTitle());
    }

    @Test
    void getContent() {
        WebComponent contentElement = mock(WebComponent.class);
        when(contentElement.getText()).thenReturn("Card Content");
        when(testSubject.findComponent(By.className("MuiCardContent-root"))).thenReturn(contentElement);
        
        assertEquals("Card Content", testSubject.getContent());
    }

    @Test
    void getContent_fallback() {
        when(testSubject.findComponent(By.className("MuiCardContent-root"))).thenThrow(new RuntimeException());
        when(element.getText()).thenReturn("Fallback Content");
        
        assertEquals("Fallback Content", testSubject.getContent());
    }

    @Test
    void getActions() {
        WebComponent actionsContainer = mock(WebComponent.class);
        WebComponent button1 = mock(WebComponent.class);
        WebComponent button2 = mock(WebComponent.class);
        List<WebComponent> buttons = asList(button1, button2);
        
        when(actionsContainer.findComponents(By.tagName("button"))).thenReturn(buttons);
        when(testSubject.findComponent(By.className("MuiCardActions-root"))).thenReturn(actionsContainer);
        
        assertEquals(buttons, testSubject.getActions());
    }

    @Test
    void getActions_fallback() {
        when(testSubject.findComponent(By.className("MuiCardActions-root"))).thenThrow(new RuntimeException());
        
        WebComponent button1 = mock(WebComponent.class);
        WebComponent button2 = mock(WebComponent.class);
        List<WebComponent> buttons = asList(button1, button2);
        
        when(testSubject.findComponents(By.tagName("button"))).thenReturn(buttons);
        
        assertEquals(buttons, testSubject.getActions());
    }

    @Test
    void hasMedia() {
        WebComponent mediaElement = mock(WebComponent.class);
        when(testSubject.findComponent(By.className("MuiCardMedia-root"))).thenReturn(mediaElement);
        
        assertTrue(testSubject.hasMedia());
    }

    @Test
    void hasMedia_false() {
        when(testSubject.findComponent(By.className("MuiCardMedia-root"))).thenThrow(new RuntimeException());
        
        assertFalse(testSubject.hasMedia());
    }
}