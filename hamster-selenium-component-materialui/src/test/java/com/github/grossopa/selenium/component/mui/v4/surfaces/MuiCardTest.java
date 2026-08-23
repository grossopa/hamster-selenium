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

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiCardTest {

    MuiCard testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        testSubject = new MuiCard(element, driver, config);
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        assertEquals("Card", testSubject.getComponentName());
    }

    @Test
    void getTitle() {
        WebElement titleElement = mock(WebElement.class);
        when(titleElement.getText()).thenReturn("Card Title");
        when(element.findElement(By.className("MuiCardHeader-title"))).thenReturn(titleElement);
        assertEquals("Card Title", testSubject.getTitle());
    }

    @Test
    void getTitleFallbackH1() {
        when(element.findElement(By.className("MuiCardHeader-title"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        WebElement h1Element = mock(WebElement.class);
        when(h1Element.getText()).thenReturn("H1 Title");
        when(element.findElement(By.tagName("h1"))).thenReturn(h1Element);
        assertEquals("H1 Title", testSubject.getTitle());
    }

    @Test
    void getTitleFallbackH2() {
        when(element.findElement(By.className("MuiCardHeader-title"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        when(element.findElement(By.tagName("h1"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        WebElement h2Element = mock(WebElement.class);
        when(h2Element.getText()).thenReturn("H2 Title");
        when(element.findElement(By.tagName("h2"))).thenReturn(h2Element);
        assertEquals("H2 Title", testSubject.getTitle());
    }

    @Test
    void getTitleNotFound() {
        when(element.findElement(By.className("MuiCardHeader-title"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        when(element.findElement(By.tagName("h1"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        when(element.findElement(By.tagName("h2"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        assertNull(testSubject.getTitle());
    }

    @Test
    void getContent() {
        WebElement contentElement = mock(WebElement.class);
        when(contentElement.getText()).thenReturn("Card Content");
        when(element.findElement(By.className("MuiCardContent-root"))).thenReturn(contentElement);
        assertEquals("Card Content", testSubject.getContent());
    }

    @Test
    void getContentFallback() {
        when(element.findElement(By.className("MuiCardContent-root"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        when(element.getText()).thenReturn("Fallback text");
        assertEquals("Fallback text", testSubject.getContent());
    }

    @Test
    void getActions() {
        WebElement actionsContainer = mock(WebElement.class);
        WebElement button1 = mock(WebElement.class);
        WebElement button2 = mock(WebElement.class);
        when(element.findElement(By.className("MuiCardActions-root"))).thenReturn(actionsContainer);
        when(actionsContainer.findElements(By.tagName("button"))).thenReturn(Arrays.asList(button1, button2));
        List<WebComponent> actions = testSubject.getActions();
        assertEquals(2, actions.size());
    }

    @Test
    void getActionsFallback() {
        when(element.findElement(By.className("MuiCardActions-root"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        WebElement button1 = mock(WebElement.class);
        when(element.findElements(By.tagName("button"))).thenReturn(List.of(button1));
        List<WebComponent> actions = testSubject.getActions();
        assertEquals(1, actions.size());
    }

    @Test
    void hasMedia() {
        WebElement mediaElement = mock(WebElement.class);
        when(element.findElement(By.className("MuiCardMedia-root"))).thenReturn(mediaElement);
        assertTrue(testSubject.hasMedia());
    }

    @Test
    void hasMediaNegative() {
        when(element.findElement(By.className("MuiCardMedia-root"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        assertFalse(testSubject.hasMedia());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiCard{element=element-toString}", testSubject.toString());
    }
}
