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
package com.github.grossopa.selenium.component.antd.dataentry;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AntdInput}
 *
 * @author Jack Yin
 * @since 1.15
 */
class AntdInputTest {

    AntdInput testSubject;
    WebElement element = mock(WebElement.class);
    WebElement input = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    AntdConfig config = mock(AntdConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getPrefixCls()).thenReturn("sss");
        testSubject = new AntdInput(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("input", testSubject.getComponentName());
    }

    @Test
    void validateWithInputElement() {
        when(element.getDomAttribute("class")).thenReturn("sss-input");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateWithWrapperElement() {
        when(element.getDomAttribute("class")).thenReturn("sss-input-affix-wrapper");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getDomAttribute("class")).thenReturn("sss-ddd");
        assertFalse(testSubject.validate());
    }

    @Test
    void getInputWithInputElement() {
        when(element.getTagName()).thenReturn("input");
        assertSame(element, testSubject.getInput().getWrappedElement());
    }

    @Test
    void getInputWithWrapperElement() {
        when(element.getTagName()).thenReturn("span");
        when(element.findElement(By.className("sss-input"))).thenReturn(input);
        assertSame(input, testSubject.getInput().getWrappedElement());
    }

    @Test
    void getValue() {
        when(element.getTagName()).thenReturn("input");
        when(element.getDomAttribute("value")).thenReturn("some-value");
        assertEquals("some-value", testSubject.getValue());
    }

    @Test
    void sendText() {
        when(element.getTagName()).thenReturn("input");
        testSubject.sendText("hello");
        verify(element).sendKeys("hello");
    }

    @Test
    void clearText() {
        when(element.getTagName()).thenReturn("input");
        testSubject.clearText();
        verify(element).clear();
    }
}
