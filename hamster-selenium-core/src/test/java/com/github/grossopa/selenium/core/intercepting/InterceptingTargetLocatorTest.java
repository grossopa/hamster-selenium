/*
 * Copyright © 2021 the original author or authors.
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

package com.github.grossopa.selenium.core.intercepting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static com.github.grossopa.selenium.core.intercepting.InterceptingTestHelper.afterEachVerify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link InterceptingTargetLocator}
 *
 * @author Jack Yin
 * @since 1.4
 */
class InterceptingTargetLocatorTest {

    InterceptingTargetLocator testSubject;
    WebDriver.TargetLocator targetLocator = mock(WebDriver.TargetLocator.class);
    InterceptingHandler handler = mock(InterceptingHandler.class);

    WebDriver webDriver = mock(WebDriver.class);
    WebElement element = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(handler.execute(any(), any())).thenCallRealMethod();
        testSubject = new InterceptingTargetLocator(targetLocator, handler);
    }

    @Test
    void frameByIndex() {
        when(targetLocator.frame(anyInt())).thenReturn(webDriver);
        assertEquals(webDriver, testSubject.frame(1));
        verify(targetLocator, times(1)).frame(1);
        afterEachVerify(handler, targetLocator, TARGETLOCATOR_FRAME, webDriver, 1);
    }

    @Test
    void frameByNameOrId() {
        when(targetLocator.frame(anyString())).thenReturn(webDriver);
        assertEquals(webDriver, testSubject.frame("name"));
        verify(targetLocator, times(1)).frame("name");
        afterEachVerify(handler, targetLocator, TARGETLOCATOR_FRAME, webDriver, "name");
    }

    @Test
    void frameByElement() {
        when(targetLocator.frame(element)).thenReturn(webDriver);
        assertEquals(webDriver, testSubject.frame(element));
        verify(targetLocator, times(1)).frame(element);
        afterEachVerify(handler, targetLocator, TARGETLOCATOR_FRAME, webDriver, element);
    }

    @Test
    void parentFrame() {
        when(targetLocator.parentFrame()).thenReturn(webDriver);
        assertEquals(webDriver, testSubject.parentFrame());
        verify(targetLocator, times(1)).parentFrame();
        afterEachVerify(handler, targetLocator, TARGETLOCATOR_PARENT_FRAME, webDriver);
    }

    @Test
    void window() {
        when(targetLocator.window("any")).thenReturn(webDriver);
        assertEquals(webDriver, testSubject.window("any"));
        verify(targetLocator, times(1)).window("any");
        afterEachVerify(handler, targetLocator, TARGETLOCATOR_WINDOW, webDriver, "any");
    }

    @Test
    void newWindow() {
        when(targetLocator.newWindow(WindowType.WINDOW)).thenReturn(webDriver);
        assertEquals(webDriver, testSubject.newWindow(WindowType.WINDOW));
        verify(targetLocator, times(1)).newWindow(WindowType.WINDOW);
        afterEachVerify(handler, targetLocator, TARGETLOCATOR_NEW_WINDOW, webDriver, WindowType.WINDOW);
    }

    @Test
    void defaultContent() {
        when(targetLocator.defaultContent()).thenReturn(webDriver);
        assertEquals(webDriver, testSubject.defaultContent());
        verify(targetLocator, times(1)).defaultContent();
        afterEachVerify(handler, targetLocator, TARGETLOCATOR_DEFAULT_CONTENT, webDriver);
    }

    @Test
    void activeElement() {
        when(targetLocator.activeElement()).thenReturn(element);
        assertEquals(element, testSubject.activeElement());
        verify(targetLocator, times(1)).activeElement();
        afterEachVerify(handler, targetLocator, TARGETLOCATOR_ACTIVE_ELEMENT, element);
    }

    @Test
    void alert() {
        Alert alert = mock(Alert.class);
        when(targetLocator.alert()).thenReturn(alert);
        assertTrue(testSubject.alert() instanceof InterceptingAlert);
    }

}
