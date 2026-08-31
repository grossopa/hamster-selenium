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
package com.github.grossopa.selenium.component.antd.datadisplay;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AntdTag}
 *
 * @author Jack Yin
 * @since 1.15
 */
class AntdTagTest {

    AntdTag testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    AntdConfig config = mock(AntdConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getPrefixCls()).thenReturn("sss");
        testSubject = new AntdTag(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("tag", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("sss-tag");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getDomAttribute("class")).thenReturn("sss-ddd");
        assertFalse(testSubject.validate());
    }

    @Test
    void isClosableTrue() {
        when(element.findElements(By.className("sss-tag-close-icon"))).thenReturn(List.of(mock(WebElement.class)));
        assertTrue(testSubject.isClosable());
    }

    @Test
    void isClosableFalse() {
        when(element.findElements(By.className("sss-tag-close-icon"))).thenReturn(List.of());
        assertFalse(testSubject.isClosable());
    }

    @Test
    void getColor() {
        when(element.getDomAttribute("class")).thenReturn("sss-tag sss-tag-red");
        assertEquals("red", testSubject.getColor());
    }

    @Test
    void getColorIgnoresCloseIcon() {
        when(element.getDomAttribute("class")).thenReturn("sss-tag sss-tag-close-icon");
        assertNull(testSubject.getColor());
    }

    @Test
    void getColorIgnoresHasColor() {
        when(element.getDomAttribute("class")).thenReturn("sss-tag sss-tag-has-color");
        assertNull(testSubject.getColor());
    }

    @Test
    void getColorReturnsNullWithoutColorClass() {
        when(element.getDomAttribute("class")).thenReturn("sss-tag");
        assertNull(testSubject.getColor());
    }

    @Test
    void getColorReturnsNullWithoutClassAttribute() {
        when(element.getDomAttribute("class")).thenReturn(null);
        assertNull(testSubject.getColor());
    }
}
