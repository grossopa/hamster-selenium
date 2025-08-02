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

package com.github.grossopa.selenium.component.mui.v4.feedback;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiAlert}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiAlertTest {

    MuiAlert testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiAlert(element, driver, config);
        when(config.getCssPrefix()).thenReturn("Mui");
    }

    @Test
    void getComponentName() {
        assertEquals("Alert", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertTrue(testSubject.versions().contains(V4));
        assertTrue(testSubject.versions().contains(V5));
        assertTrue(testSubject.versions().contains(V6));
    }

    @Test
    void getSeverity() {
        // Test success severity
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardSuccess");
        assertEquals("success", testSubject.getSeverity());
        
        // Test info severity
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardInfo");
        assertEquals("info", testSubject.getSeverity());
        
        // Test warning severity
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardWarning");
        assertEquals("warning", testSubject.getSeverity());
        
        // Test error severity
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardError");
        assertEquals("error", testSubject.getSeverity());
        
        // Test default severity
        when(element.getAttribute("class")).thenReturn("");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test
    void getMessage() {
        WebComponent messageWrapper = mock(WebComponent.class);
        when(messageWrapper.getText()).thenReturn("Test message");
        when(testSubject.findComponent(By.className("MuiAlert-message"))).thenReturn(messageWrapper);
        
        assertEquals("Test message", testSubject.getMessage());
    }

    @Test
    void close() {
        WebComponent closeButton = mock(WebComponent.class);
        when(testSubject.findComponent(By.className("MuiAlert-closeButton"))).thenReturn(closeButton);
        
        testSubject.close();
        
        verify(closeButton).click();
    }

    @Test
    void close_unsupported() {
        when(testSubject.findComponent(By.className("MuiAlert-closeButton"))).thenThrow(new RuntimeException());
        
        assertThrows(UnsupportedOperationException.class, testSubject::close);
    }

    @Test
    void hasIcon() {
        WebComponent icon = mock(WebComponent.class);
        when(testSubject.findComponent(By.className("MuiAlert-icon"))).thenReturn(icon);
        
        assertTrue(testSubject.hasIcon());
    }

    @Test
    void hasIcon_false() {
        when(testSubject.findComponent(By.className("MuiAlert-icon"))).thenThrow(new RuntimeException());
        
        assertFalse(testSubject.hasIcon());
    }
}