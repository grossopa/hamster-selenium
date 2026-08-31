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
package com.github.grossopa.selenium.component.antd.navigation;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AntdPagination}
 *
 * @author Jack Yin
 * @since 1.15
 */
class AntdPaginationTest {

    AntdPagination testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    AntdConfig config = mock(AntdConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getPrefixCls()).thenReturn("sss");
        testSubject = new AntdPagination(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("pagination", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("sss-pagination");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getDomAttribute("class")).thenReturn("sss-ddd");
        assertFalse(testSubject.validate());
    }

    @Test
    void getPageItems() {
        WebElement item = mock(WebElement.class);
        when(element.findElements(By.className("sss-pagination-item"))).thenReturn(List.of(item));
        assertEquals(1, testSubject.getPageItems().size());
    }

    @Test
    void previousAndNextButton() {
        WebElement prev = mock(WebElement.class);
        WebElement next = mock(WebElement.class);
        when(element.findElement(By.className("sss-pagination-prev"))).thenReturn(prev);
        when(element.findElement(By.className("sss-pagination-next"))).thenReturn(next);
        assertSame(prev, testSubject.previousButton().getWrappedElement());
        assertSame(next, testSubject.nextButton().getWrappedElement());
    }

    @Test
    void getCurrentPageIndex() {
        WebElement item1 = mock(WebElement.class);
        WebElement item2 = mock(WebElement.class);
        when(item1.getDomAttribute("class")).thenReturn("sss-pagination-item");
        when(item1.getDomAttribute("title")).thenReturn("1");
        when(item2.getDomAttribute("class")).thenReturn("sss-pagination-item sss-pagination-item-active");
        when(item2.getDomAttribute("title")).thenReturn("2");
        when(element.findElements(By.className("sss-pagination-item"))).thenReturn(List.of(item1, item2));
        assertEquals(2, testSubject.getCurrentPageIndex());
    }

    @Test
    void getCurrentPageIndexNegative() {
        when(element.findElements(By.className("sss-pagination-item"))).thenReturn(List.of());
        assertEquals(-1, testSubject.getCurrentPageIndex());
    }

    @Test
    void setPageIndexDisplayed() {
        WebElement item = mock(WebElement.class);
        when(item.getDomAttribute("class")).thenReturn("sss-pagination-item");
        when(item.getDomAttribute("title")).thenReturn("3");
        when(element.findElements(By.className("sss-pagination-item"))).thenReturn(List.of(item));
        testSubject.setPageIndex(3);
        verify(item).click();
    }

    @Test
    void setPageIndexAlreadyCurrent() {
        WebElement item = mock(WebElement.class);
        when(item.getDomAttribute("class")).thenReturn("sss-pagination-item sss-pagination-item-active");
        when(item.getDomAttribute("title")).thenReturn("3");
        when(element.findElements(By.className("sss-pagination-item"))).thenReturn(List.of(item));
        testSubject.setPageIndex(3);
        verify(item, never()).click();
    }

    @Test
    void setPageIndexNotReachable() {
        WebElement item = mock(WebElement.class);
        WebElement next = mock(WebElement.class);
        when(item.getDomAttribute("class")).thenReturn("sss-pagination-item");
        when(item.getDomAttribute("title")).thenReturn("1");
        when(element.findElements(By.className("sss-pagination-item"))).thenReturn(List.of(item));
        when(element.findElement(By.className("sss-pagination-next"))).thenReturn(next);
        assertThrows(NoSuchElementException.class, () -> testSubject.setPageIndex(99));
    }
}
