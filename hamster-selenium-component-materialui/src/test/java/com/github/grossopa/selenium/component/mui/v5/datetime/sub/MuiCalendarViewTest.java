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

package com.github.grossopa.selenium.component.mui.v5.datetime.sub;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiCalendarView}
 *
 * @author Jack Yin
 * @since 1.8
 */
class MuiCalendarViewTest {

    MuiCalendarView testSubject;

    public WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    List<WebElement> dayButtons = newArrayList();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 30; i++) {
            WebElement dayButtonElement = mock(WebElement.class);
            when(dayButtonElement.getText()).thenReturn(String.valueOf(i + 1));
            dayButtons.add(dayButtonElement);
        }

        when(config.getCssPrefix()).thenReturn("Mui");
        when(element.findElements(
                By.xpath(".//button[contains(@class,'MuiPickersDay-root') and count(text())>0]"))).thenReturn(
                dayButtons);
        when(element.findElements(
                By.xpath(".//button[contains(@class,'MuiPickersDay-root') and contains(@class,'Mui-selected')]"))).then(
                a -> dayButtons.stream().filter(WebElement::isSelected).collect(toList()));

        when(element.findElement(
                By.xpath(".//button[contains(@class,'MuiPickersDay-root') and contains(@class,'Mui-selected')]"))).then(
                a -> dayButtons.stream().filter(WebElement::isSelected).findFirst().orElseThrow());

        when(element.findElement(
                By.xpath(".//button[contains(@class,'MuiPickersDay-root') and text()=\"16\"]"))).thenReturn(
                dayButtons.get(15));

        testSubject = new MuiCalendarView(element, driver, config);
    }

    @Test
    void version() {
        assertArrayEquals(new Object[]{V5}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        assertEquals("", testSubject.getComponentName());
    }

    @Test
    void validate() {
        assertTrue(testSubject.validate());
    }

    @Test
    void getDayButtons() {
        assertEquals(30, testSubject.getDayButtons().size());
    }

    @Test
    void getFirstSelectedDay() {
        when(dayButtons.get(10).isSelected()).thenReturn(true);
        assertEquals(10, dayButtons.indexOf(testSubject.getFirstSelectedDay().getWrappedElement()));
    }

    @Test
    void getSelectedDays() {
        when(dayButtons.get(10).isSelected()).thenReturn(true);
        when(dayButtons.get(15).isSelected()).thenReturn(true);
        when(dayButtons.get(20).isSelected()).thenReturn(true);

        assertEquals(3, testSubject.getSelectedDays().size());
    }

    @Test
    void select() {
        testSubject.select(16);
        verify(dayButtons.get(15), times(1)).click();
    }

    @Test
    void testSelect() {
        testSubject.select("16");
        verify(dayButtons.get(15), times(1)).click();
    }
}
