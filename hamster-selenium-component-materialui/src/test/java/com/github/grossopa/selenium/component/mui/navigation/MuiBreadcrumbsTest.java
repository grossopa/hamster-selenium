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

package com.github.grossopa.selenium.component.mui.navigation;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.exception.BreadcrumbsAlreadyExpandedException;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiBreadcrumbs}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiBreadcrumbsTest {

    MuiBreadcrumbs testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement touchRippleParent = mock(WebElement.class);
    WebElement touchRipple = mock(WebElement.class);

    private WebElement createItemContainer(String text) {
        WebElement itemContainer = mock(WebElement.class);
        WebElement item = mock(WebElement.class);
        when(item.getText()).thenReturn(text);
        when(itemContainer.findElement(eq(By.xpath(".//*")))).thenReturn(item);
        return itemContainer;
    }

    private WebElement createSeparator(String separator) {
        WebElement separatorElement = mock(WebElement.class);
        when(separatorElement.getText()).thenReturn(separator);
        return separatorElement;
    }

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        List<WebElement> itemContainers = asList(createItemContainer("Home"), createItemContainer("b1"),
                createItemContainer("b2"));
        when(element.findElements(By2.className("MuiBreadcrumbs-li"))).thenReturn(itemContainers);
        when(element.findElements(By.className("MuiTouchRipple-root"))).thenReturn(new ArrayList<>());
        List<WebElement> separators = asList(createSeparator("->"), createSeparator("->"));
        when(element.findElements(By2.className("MuiBreadcrumbs-separator"))).thenReturn(separators);
        when(touchRipple.findElement(By.xpath("parent::*"))).thenReturn(touchRippleParent);

        testSubject = new MuiBreadcrumbs(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("Breadcrumbs", testSubject.getComponentName());
    }

    @Test
    void getItemAt() {
        assertEquals(3, testSubject.getItems().size());
        assertEquals("Home", testSubject.getItemAt(0).getText());
        assertEquals("b1", testSubject.getItemAt(1).getText());
        assertEquals("b2", testSubject.getItemAt(2).getText());
    }

    @Test
    void getItems() {
        assertEquals(3, testSubject.getItems().size());
    }

    @Test
    void isCollapsed() {
        when(element.findElements(By.className("MuiTouchRipple-root"))).thenReturn(singletonList(touchRipple));
        assertTrue(testSubject.isCollapsed());
    }

    @Test
    void isCollapsedNegative() {
        assertFalse(testSubject.isCollapsed());
    }

    @Test
    void expand() {
        when(element.findElements(By.className("MuiTouchRipple-root"))).thenReturn(singletonList(touchRipple));
        testSubject.expand();
        verify(touchRippleParent, only()).click();
    }

    @Test
    void expandNegative() {
        assertThrows(BreadcrumbsAlreadyExpandedException.class, () -> testSubject.expand());
    }

    @Test
    void getSeparators() {
        assertEquals(2, testSubject.getSeparators().size());
        assertEquals("->", testSubject.getSeparators().get(0).getText());
        assertEquals("->", testSubject.getSeparators().get(1).getText());
    }
}
