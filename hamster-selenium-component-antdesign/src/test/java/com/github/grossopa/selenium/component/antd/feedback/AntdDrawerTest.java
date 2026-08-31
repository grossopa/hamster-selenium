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
package com.github.grossopa.selenium.component.antd.feedback;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AntdDrawer}
 *
 * @author Jack Yin
 * @since 1.15
 */
class AntdDrawerTest {

    AntdDrawer testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    AntdConfig config = mock(AntdConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getPrefixCls()).thenReturn("sss");
        testSubject = new AntdDrawer(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("drawer", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("sss-drawer sss-drawer-left");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getDomAttribute("class")).thenReturn("sss-ddd");
        assertFalse(testSubject.validate());
    }

    @Test
    void isOpenTrue() {
        when(element.getDomAttribute("class")).thenReturn("sss-drawer sss-drawer-open");
        assertTrue(testSubject.isOpen());
    }

    @Test
    void isOpenFalse() {
        when(element.getDomAttribute("class")).thenReturn("sss-drawer");
        assertFalse(testSubject.isOpen());
    }

    @Test
    void getTitle() {
        WebElement title = mock(WebElement.class);
        when(element.findElement(By.className("sss-drawer-title"))).thenReturn(title);
        assertSame(title, testSubject.getTitle().getWrappedElement());
    }

    @Test
    void getBody() {
        WebElement body = mock(WebElement.class);
        when(element.findElement(By.className("sss-drawer-body"))).thenReturn(body);
        assertSame(body, testSubject.getBody().getWrappedElement());
    }

    @Test
    void getFooter() {
        WebElement footer = mock(WebElement.class);
        when(element.findElement(By.className("sss-drawer-footer"))).thenReturn(footer);
        assertSame(footer, testSubject.getFooter().getWrappedElement());
    }

    @Test
    void getCloseButton() {
        WebElement closeButton = mock(WebElement.class);
        when(element.findElement(By.className("sss-drawer-close"))).thenReturn(closeButton);
        assertSame(closeButton, testSubject.getCloseButton().getWrappedElement());
    }

    @Test
    void close() {
        WebElement closeButton = mock(WebElement.class);
        when(element.findElement(By.className("sss-drawer-close"))).thenReturn(closeButton);
        testSubject.close();
        verify(closeButton).click();
    }
}
