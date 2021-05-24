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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiAccordion}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiAccordionTest {

    MuiAccordion testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.getRootCss(any())).then(answer -> {
            String componentName = answer.getArgument(0);
            return "Mui" + componentName + "-root";
        });
        testSubject = new MuiAccordion(element, driver, config);
    }

    @Test
    void getAccordionSummary() {
        WebElement accordionSummary = mock(WebElement.class);
        when(element.findElements(By.className("MuiAccordionSummary-root"))).thenReturn(newArrayList(accordionSummary));
        assertEquals(accordionSummary, requireNonNull(testSubject.getAccordionSummary()).getWrappedElement());
    }

    @Test
    void getAccordionSummaryNull() {
        assertNull(testSubject.getAccordionSummary());
    }

    @Test
    void getAccordionDetails() {
        WebElement accordionDetails = mock(WebElement.class);
        when(element.findElements(By.className("MuiAccordionDetails-root"))).thenReturn(newArrayList(accordionDetails));
        assertEquals(accordionDetails, requireNonNull(testSubject.getAccordionDetails()).getWrappedElement());
    }

    @Test
    void getAccordionDetailsNull() {
        assertNull(testSubject.getAccordionDetails());
    }

    @Test
    void getAccordionActions() {
        WebElement accordionActions = mock(WebElement.class);
        when(element.findElements(By.className("MuiAccordionActions-root"))).thenReturn(newArrayList(accordionActions));
        assertEquals(accordionActions, requireNonNull(testSubject.getAccordionActions()).getWrappedElement());
    }

    @Test
    void getAccordionActionsNull() {
        assertNull(testSubject.getAccordionActions());
    }

    @Test
    void isExpand() {
        WebElement accordionSummary = mock(WebElement.class);
        when(element.findElements(By.className("MuiAccordionSummary-root"))).thenReturn(newArrayList(accordionSummary));
        when(accordionSummary.getAttribute("aria-expanded")).thenReturn("true");
        assertTrue(testSubject.isExpand());
    }

    @Test
    void isExpandFalse() {
        assertFalse(testSubject.isExpand());
    }

    @Test
    void isExpandFalse2() {
        WebElement accordionSummary = mock(WebElement.class);
        when(element.findElements(By.className("MuiAccordionSummary-root"))).thenReturn(newArrayList(accordionSummary));
        when(accordionSummary.getAttribute("aria-expanded")).thenReturn("false");
        assertFalse(testSubject.isExpand());
    }

    @Test
    void expand() {
        WebElement accordionSummary = mock(WebElement.class);
        when(element.findElements(By.className("MuiAccordionSummary-root"))).thenReturn(newArrayList(accordionSummary));
        WebElement expandButton = mock(WebElement.class);
        when(accordionSummary.findElement(By.className("MuiAccordionSummary-expandIcon"))).thenReturn(expandButton);
        testSubject.expand();
        verify(expandButton, times(1)).click();
    }

    @Test
    void expandFallback() {
        testSubject.expand();
        verify(element, times(1)).click();
    }

    @Test
    void isEnabled() {
        when(element.getAttribute("class")).thenReturn("vbb Mui- ccc");
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalse() {
        when(element.getAttribute("class")).thenReturn("vbb Mui-disabled ccc");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void getComponentName() {
        assertEquals("Accordion", testSubject.getComponentName());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiAccordion{element=element-toString}", testSubject.toString());
    }
}
