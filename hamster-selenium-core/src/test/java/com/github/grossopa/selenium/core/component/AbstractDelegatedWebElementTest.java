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

package com.github.grossopa.selenium.core.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AbstractDelegatedWebElement}
 *
 * @author Jack Yin
 * @since 1.0
 */
class AbstractDelegatedWebElementTest {

    AbstractDelegatedWebElement testSubject;

    RemoteWebElement element;

    @BeforeEach
    void setUp() {
        element = mock(RemoteWebElement.class);
        testSubject = new AbstractDelegatedWebElement(element) {

        };
    }

    @Test
    void constructor() {
        WebComponent mockComponent = mock(WebComponent.class);
        when(mockComponent.getWrappedElement()).thenReturn(element);
        testSubject = new AbstractDelegatedWebElement(mockComponent) {
        };

        assertSame(testSubject.element, element);
    }

    @Test
    void click() {
        testSubject.click();
        verify(element, only()).click();
    }

    @Test
    void submit() {
        testSubject.submit();
        verify(element, only()).submit();
    }

    @Test
    void sendKeys() {
        testSubject.sendKeys(Keys.END);
        verify(element, only()).sendKeys(Keys.END);
    }

    @Test
    void clear() {
        testSubject.clear();
        verify(element, only()).clear();
    }

    @Test
    void getTagName() {
        when(element.getTagName()).thenReturn("tag-name");
        assertEquals("tag-name", testSubject.getTagName());
    }

    @Test
    void getAttribute() {
        when(element.getAttribute("attribute-1")).thenReturn("some-value");
        assertEquals("some-value", testSubject.getAttribute("attribute-1"));
    }

    @Test
    void isSelected() {
        when(element.isSelected()).thenReturn(true);
        assertTrue(testSubject.isSelected());
    }

    @Test
    void isEnabled() {
        when(element.isEnabled()).thenReturn(true);
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void getText() {
        when(element.getText()).thenReturn("some-text");
        assertEquals("some-text", testSubject.getText());
    }

    @Test
    void findElements() {
        testSubject.findElements(mock(By.class));
        verify(element, only()).findElements(any());
    }

    @Test
    void findElement() {
        testSubject.findElement(mock(By.class));
        verify(element, only()).findElement(any());
    }

    @Test
    void isDisplayed() {
        testSubject.isDisplayed();
        verify(element, only()).isDisplayed();
    }

    @Test
    void getLocation() {
        testSubject.getLocation();
        verify(element, only()).getLocation();
    }

    @Test
    void getSize() {
        testSubject.getSize();
        verify(element, only()).getSize();
    }

    @Test
    void getRect() {
        testSubject.getRect();
        verify(element, only()).getRect();
    }

    @Test
    void getCssValue() {
        testSubject.getCssValue("some-property");
        verify(element, only()).getCssValue("some-property");
    }

    @Test
    void getScreenshotAs() {
        testSubject.getScreenshotAs(OutputType.BASE64);
        verify(element, only()).getScreenshotAs(OutputType.BASE64);
    }

    @Test
    void getCoordinates() {
        testSubject.getCoordinates();
        verify(element, only()).getCoordinates();
    }

    @Test
    void getWrappedDriver() {
        testSubject.getWrappedDriver();
        verify(element, only()).getWrappedDriver();
    }

    @Test
    void testToString() {
        assertEquals("AbstractDelegatedWebElement{element=" + element.toString() + "}", testSubject.toString());
    }

    @Test
    @SuppressWarnings("all")
    void testEqualsSame() {
        assertTrue(testSubject.equals(testSubject));
    }

    @Test
    @SuppressWarnings("all")
    void testEqualsNull() {
        assertFalse(testSubject.equals(null));
    }

    @Test
    @SuppressWarnings("all")
    void testEqualsOther() {
        assertFalse(testSubject.equals(new Exception()));
    }
}
