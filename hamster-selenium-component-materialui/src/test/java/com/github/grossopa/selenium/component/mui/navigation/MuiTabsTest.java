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
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiTabs}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiTabsTest {

    MuiTabs testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getRootCss("Tab")).thenReturn("MuiTab-root");
        testSubject = new MuiTabs(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("Tabs", testSubject.getComponentName());
    }

    @Test
    void getTabs() {
        WebElement tab1 = mock(WebElement.class);
        WebElement tab2 = mock(WebElement.class);
        when(element.findElements(By.className("MuiTab-root"))).thenReturn(asList(tab1, tab2));
        assertEquals(2, testSubject.getTabs().size());
    }

    @Test
    void getPreviousScrollButton() {
        WebElement previousButtonElement = mock(WebElement.class);
        WebElement nextButtonElement = mock(WebElement.class);

        List<WebElement> buttons = asList(previousButtonElement, nextButtonElement);

        when(element.findElements(
                By2.attr("class", config.getCssPrefix() + "TabScrollButton-root").depthRelative().contains().build()))
                .thenReturn(buttons);

        Optional<MuiTabScrollButton> buttonOptional = testSubject.getPreviousScrollButton();
        assertTrue(buttonOptional.isPresent());
        assertEquals(previousButtonElement, buttonOptional.get().getWrappedElement());
    }

    @Test
    void getNextScrollButton() {
        WebElement previousButtonElement = mock(WebElement.class);
        WebElement nextButtonElement = mock(WebElement.class);

        List<WebElement> buttons = asList(previousButtonElement, nextButtonElement);

        when(element.findElements(
                By2.attr("class", config.getCssPrefix() + "TabScrollButton-root").depthRelative().contains().build()))
                .thenReturn(buttons);

        Optional<MuiTabScrollButton> buttonOptional = testSubject.getNextScrollButton();
        assertTrue(buttonOptional.isPresent());
        assertEquals(nextButtonElement, buttonOptional.get().getWrappedElement());
    }


    @Test
    void getPreviousScrollButtonNotPresent() {
        List<WebElement> buttons = newArrayList();

        when(element.findElements(
                By2.attr("class", config.getCssPrefix() + "TabScrollButton-root").depthRelative().contains().build()))
                .thenReturn(buttons);

        Optional<MuiTabScrollButton> buttonOptional = testSubject.getPreviousScrollButton();
        assertTrue(buttonOptional.isEmpty());
    }

    @Test
    void getNextScrollButtonNotPresent() {
        List<WebElement> buttons = newArrayList();

        when(element.findElements(
                By2.attr("class", config.getCssPrefix() + "TabScrollButton-root").depthRelative().contains().build()))
                .thenReturn(buttons);

        Optional<MuiTabScrollButton> buttonOptional = testSubject.getNextScrollButton();
        assertTrue(buttonOptional.isEmpty());
    }

    @Test
    void isVertical() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(element.getAttribute("class")).thenReturn("MuiTabs-vertical MuiTabs-other");
        assertTrue(testSubject.isVertical());
    }

    @Test
    void isVerticalNegative() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(element.getAttribute("class")).thenReturn("MuiTabs-some MuiTabs-other");
        assertFalse(testSubject.isVertical());
    }
}
