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

package com.github.grossopa.selenium.component.mui.v4.pickers;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiPickersYearSelectionContainer}
 *
 * @author Jack Yin
 * @since 1.2
 */
class MuiPickersYearSelectionContainerTest {

    MuiPickersYearSelectionContainer testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement year1 = mock(WebElement.class);
    WebElement year2 = mock(WebElement.class);
    WebElement year3 = mock(WebElement.class);
    WebElement year4 = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.getRootCss(MuiPickersYear.COMPONENT_NAME)).thenReturn("MuiPickersYear-root");
        when(element.findElements(By.className("MuiPickersYear-root")))
                .thenReturn(newArrayList(year1, year2, year3, year4));

        when(year1.getAttribute("class")).thenReturn("MuiPickersYear-dd");
        when(year2.getAttribute("class")).thenReturn("MuiPickersYear-dd");
        when(year3.getAttribute("class")).thenReturn("MuiPickersYear-dd");
        when(year4.getAttribute("class")).thenReturn("MuiPickersYear-dd");

        testSubject = new MuiPickersYearSelectionContainer(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("PickersYearSelection-container", testSubject.getComponentName());
    }

    @Test
    void getYearList() {
        assertEquals(4, testSubject.getYearList().size());
    }

    @Test
    void getSelectedYear() {
        when(year4.getAttribute("class")).thenReturn("MuiPickersYear-yearSelected");
        assertEquals(year4, requireNonNull(testSubject.getSelectedYear()).getWrappedElement());
    }

    @Test
    void getSelectedYearNull() {
        assertNull(testSubject.getSelectedYear());
    }

    @Test
    void select() {
        WebElement yearMock = mock(WebElement.class);
        when(element.findElement(By2.textExact("2020"))).thenReturn(yearMock);
        testSubject.select("2020");
        verify(driver, times(1)).scrollTo(any());
        verify(driver, times(1)).moveTo(any());
        verify(yearMock, times(1)).click();
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiPickersYearSelectionContainer{element=element-toString}", testSubject.toString());
    }
}
