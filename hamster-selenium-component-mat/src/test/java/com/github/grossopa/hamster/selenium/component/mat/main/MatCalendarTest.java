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
package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatCalendarTest {

    MatCalendar testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        testSubject = new MatCalendar(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Calendar", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-calendar");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-other");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatCalendar{element=inner-element}", testSubject.toString());
    }

    @Test
    void getPeriodLabel() {
        WebElement labelElement = mock(WebElement.class);
        when(labelElement.getText()).thenReturn("SEP 2023");
        when(element.findElement(By.className("mat-mdc-calendar-period-label"))).thenReturn(labelElement);
        assertEquals("SEP 2023", testSubject.getPeriodLabel());
    }

    @Test
    void previousMonth() {
        WebElement button = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-calendar-previous-button"))).thenReturn(button);
        testSubject.previousMonth();
        verify(button).click();
    }

    @Test
    void nextMonth() {
        WebElement button = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-calendar-next-button"))).thenReturn(button);
        testSubject.nextMonth();
        verify(button).click();
    }

    @Test
    void getDateCells() {
        WebElement cell1 = mock(WebElement.class);
        WebElement cell2 = mock(WebElement.class);
        when(element.findElements(By.className("mat-mdc-calendar-body-cell"))).thenReturn(newArrayList(cell1, cell2));
        List<WebComponent> result = testSubject.getDateCells();
        assertEquals(2, result.size());
    }

    @Test
    void selectDateFound() {
        WebElement cell1 = mock(WebElement.class);
        when(cell1.getText()).thenReturn("15");
        WebElement cell2 = mock(WebElement.class);
        when(cell2.getText()).thenReturn("20");
        when(element.findElements(By.className("mat-mdc-calendar-body-cell"))).thenReturn(newArrayList(cell1, cell2));

        testSubject.selectDate("20");
        verify(cell1, never()).click();
        verify(cell2).click();
    }

    @Test
    void selectDateNotFound() {
        WebElement cell1 = mock(WebElement.class);
        when(cell1.getText()).thenReturn("15");
        when(element.findElements(By.className("mat-mdc-calendar-body-cell"))).thenReturn(newArrayList(cell1));

        testSubject.selectDate("25");
        verify(cell1, never()).click();
    }

    @Test
    void isDateSelectedTrue() {
        WebElement cell = mock(WebElement.class);
        when(cell.getText()).thenReturn("15");
        when(cell.getAttribute("class")).thenReturn("mat-mdc-calendar-body-cell mat-mdc-calendar-body-selected");
        when(element.findElements(By.className("mat-mdc-calendar-body-cell"))).thenReturn(newArrayList(cell));

        assertTrue(testSubject.isDateSelected("15"));
    }

    @Test
    void isDateSelectedFalse() {
        WebElement cell = mock(WebElement.class);
        when(cell.getText()).thenReturn("15");
        when(cell.getAttribute("class")).thenReturn("mat-mdc-calendar-body-cell");
        when(element.findElements(By.className("mat-mdc-calendar-body-cell"))).thenReturn(newArrayList(cell));

        assertFalse(testSubject.isDateSelected("15"));
    }

    @Test
    void isDateSelectedNotFound() {
        WebElement cell = mock(WebElement.class);
        when(cell.getText()).thenReturn("15");
        when(element.findElements(By.className("mat-mdc-calendar-body-cell"))).thenReturn(newArrayList(cell));

        assertFalse(testSubject.isDateSelected("25"));
    }
}
