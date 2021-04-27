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

package com.github.grossopa.selenium.component.mui.pickers;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiPickersCalendarTransitionContainer}
 *
 * @author Jack Yin
 * @since 1.2
 */
class MuiPickersCalendarTransitionContainerTest {

    MuiPickersCalendarTransitionContainer testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement pickersDay1 = mock(WebElement.class);
    WebElement pickersDay2 = mock(WebElement.class);
    WebElement pickersDay3 = mock(WebElement.class);
    WebElement pickersDay4 = mock(WebElement.class);
    WebElement pickersDay5 = mock(WebElement.class);

    @BeforeEach
    void setUp() {

        when(element.findElements(By.className("eee" + MuiPickersDay.NAME)))
                .thenReturn(newArrayList(pickersDay1, pickersDay2, pickersDay3, pickersDay4, pickersDay5));

        Consumer<WebElement> mockFunc = element -> when(element.getAttribute("class"))
                .thenReturn("eeePickersDay-daySelectedabc");

        mockFunc.accept(pickersDay1);
        mockFunc.accept(pickersDay2);
        mockFunc.accept(pickersDay3);
        mockFunc.accept(pickersDay4);
        mockFunc.accept(pickersDay5);

        when(config.getCssPrefix()).thenReturn("eee");
        testSubject = new MuiPickersCalendarTransitionContainer(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("PickersCalendar-transitionContainer", testSubject.getComponentName());
    }

    @Test
    void getDayList() {
        assertEquals(5, testSubject.getDayList().size());
    }

    @Test
    void getSelectedDay() {
        when(pickersDay3.getAttribute("class")).thenReturn("eeePickersDay-daySelected");
        assertEquals(pickersDay3, requireNonNull(testSubject.getSelectedDay()).getWrappedElement());
    }

    @Test
    void getSelectedDayNull() {
        assertNull(testSubject.getSelectedDay());
    }

    @Test
    void select() {
        WebElement element1 = mock(WebElement.class);
        when(element.findElement(By2.textExact("27"))).thenReturn(element1);

        WebElement element2 = mock(WebElement.class);
        when(element1.findElement(By2.parent())).thenReturn(element2);

        WebElement element3 = mock(WebElement.class);
        when(element2.findElement(By2.parent())).thenReturn(element3);

        testSubject.select("27");

        verify(element3, times(1)).click();

    }
}
