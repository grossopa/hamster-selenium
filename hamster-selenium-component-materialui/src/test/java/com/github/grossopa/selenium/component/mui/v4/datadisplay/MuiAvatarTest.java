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

package com.github.grossopa.selenium.component.mui.v4.datadisplay;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiAvatar}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiAvatarTest {

    MuiAvatar testSubject;
    WebElement element = mock(WebElement.class);
    WebElement imgElement = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(element.findElement(By.tagName("img"))).thenReturn(imgElement);
        when(imgElement.getAttribute("src")).thenReturn("some-src-value");
        when(imgElement.getAttribute("alt")).thenReturn("some-alt-value");
        testSubject = new MuiAvatar(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Avatar", testSubject.getComponentName());
    }

    @Test
    void getImg() {
        assertEquals(imgElement, testSubject.getImg().getWrappedElement());
    }

    @Test
    void getAlt() {
        assertEquals("some-alt-value", testSubject.getAlt());
    }

    @Test
    void getSrc() {
        assertEquals("some-src-value", testSubject.getSrc());
    }


    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiAvatar{element=element-toString}", testSubject.toString());
    }
}
