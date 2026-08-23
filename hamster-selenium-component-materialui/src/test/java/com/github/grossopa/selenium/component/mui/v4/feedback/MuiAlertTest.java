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

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiAlertTest {

    MuiAlert testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        testSubject = new MuiAlert(element, driver, config);
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        assertEquals("Alert", testSubject.getComponentName());
    }

    @Test
    void getSeveritySuccess() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardSuccess");
        assertEquals("success", testSubject.getSeverity());
    }

    @Test
    void getSeveritySuccessFilled() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-filledSuccess");
        assertEquals("success", testSubject.getSeverity());
    }

    @Test
    void getSeveritySuccessOutlined() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-outlinedSuccess");
        assertEquals("success", testSubject.getSeverity());
    }

    @Test
    void getSeverityInfo() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardInfo");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test
    void getSeverityInfoFilled() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-filledInfo");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test
    void getSeverityInfoOutlined() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-outlinedInfo");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test
    void getSeverityWarning() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardWarning");
        assertEquals("warning", testSubject.getSeverity());
    }

    @Test
    void getSeverityWarningFilled() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-filledWarning");
        assertEquals("warning", testSubject.getSeverity());
    }

    @Test
    void getSeverityWarningOutlined() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-outlinedWarning");
        assertEquals("warning", testSubject.getSeverity());
    }

    @Test
    void getSeverityError() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardError");
        assertEquals("error", testSubject.getSeverity());
    }

    @Test
    void getSeverityErrorFilled() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-filledError");
        assertEquals("error", testSubject.getSeverity());
    }

    @Test
    void getSeverityErrorOutlined() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-outlinedError");
        assertEquals("error", testSubject.getSeverity());
    }

    @Test
    void getSeverityDefault() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-root");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test
    void getMessage() {
        WebElement messageElement = mock(WebElement.class);
        when(messageElement.getText()).thenReturn("Alert message");
        when(element.findElement(By.className("MuiAlert-message"))).thenReturn(messageElement);
        assertEquals("Alert message", testSubject.getMessage());
    }

    @Test
    void close() {
        WebElement closeElement = mock(WebElement.class);
        when(element.findElement(By.className("MuiAlert-closeButton"))).thenReturn(closeElement);
        testSubject.close();
        verify(closeElement).click();
    }

    @Test
    void closeWithoutButton() {
        when(element.findElement(By.className("MuiAlert-closeButton"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        assertThrows(UnsupportedOperationException.class, () -> testSubject.close());
    }

    @Test
    void hasIcon() {
        WebElement iconElement = mock(WebElement.class);
        when(element.findElement(By.className("MuiAlert-icon"))).thenReturn(iconElement);
        assertTrue(testSubject.hasIcon());
    }

    @Test
    void hasIconNegative() {
        when(element.findElement(By.className("MuiAlert-icon"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        assertFalse(testSubject.hasIcon());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiAlert{element=element-toString}", testSubject.toString());
    }
}
