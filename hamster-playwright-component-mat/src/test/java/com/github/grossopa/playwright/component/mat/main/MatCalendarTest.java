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
package com.github.grossopa.playwright.component.mat.main;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatCalendarTest {

    MatCalendar testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        testSubject = new MatCalendar(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Calendar", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-mdc-calendar");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-other");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(locator.toString()).thenReturn("inner-locator");
        assertEquals("MatCalendar{locator=inner-locator}", testSubject.toString());
    }

    @Test
    void componentConstructor() {
        WebComponent component = mock(WebComponent.class);
        when(component.locator()).thenReturn(locator);
        assertInstanceOf(MatCalendar.class, new MatCalendar(component, driver, config));
    }

    @Test
    void getPeriodLabel() {
        Locator periodLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-calendar-period-label")).thenReturn(periodLocator);
        when(periodLocator.first()).thenReturn(firstLocator);
        when(firstLocator.innerText()).thenReturn("SEP 2023");
        assertEquals("SEP 2023", testSubject.getPeriodLabel());
    }

    @Test
    void previousMonth() {
        Locator prevLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-calendar-previous-button")).thenReturn(prevLocator);
        when(prevLocator.first()).thenReturn(firstLocator);
        testSubject.previousMonth();
        verify(firstLocator).click();
    }

    @Test
    void nextMonth() {
        Locator nextLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-calendar-next-button")).thenReturn(nextLocator);
        when(nextLocator.first()).thenReturn(firstLocator);
        testSubject.nextMonth();
        verify(firstLocator).click();
    }

    @Test
    void getDateCells() {
        Locator cellsLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);
        Locator cell2 = mock(Locator.class);
        when(locator.locator(".mat-mdc-calendar-body-cell")).thenReturn(cellsLocator);
        when(cellsLocator.all()).thenReturn(List.of(cell1, cell2));
        assertEquals(2, testSubject.getDateCells().size());
    }

    @Test
    void selectDate() {
        Locator cellsLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);
        Locator cell2 = mock(Locator.class);
        when(locator.locator(".mat-mdc-calendar-body-cell")).thenReturn(cellsLocator);
        when(cellsLocator.all()).thenReturn(List.of(cell1, cell2));
        when(cell1.innerText()).thenReturn("14");
        when(cell2.innerText()).thenReturn("15");
        testSubject.selectDate("15");
        verify(cell2).click();
    }

    @Test
    void selectDateNoMatch() {
        Locator cellsLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);
        when(locator.locator(".mat-mdc-calendar-body-cell")).thenReturn(cellsLocator);
        when(cellsLocator.all()).thenReturn(List.of(cell1));
        when(cell1.innerText()).thenReturn("14");
        testSubject.selectDate("20");
        verify(cell1, never()).click();
    }

    @Test
    void isDateSelectedTrue() {
        Locator cellsLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);
        when(locator.locator(".mat-mdc-calendar-body-cell")).thenReturn(cellsLocator);
        when(cellsLocator.all()).thenReturn(List.of(cell1));
        when(cell1.innerText()).thenReturn("15");
        when(cell1.getAttribute("class")).thenReturn("mat-mdc-calendar-body-cell mat-mdc-calendar-body-selected");
        assertTrue(testSubject.isDateSelected("15"));
    }

    @Test
    void isDateSelectedFalse() {
        Locator cellsLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);
        when(locator.locator(".mat-mdc-calendar-body-cell")).thenReturn(cellsLocator);
        when(cellsLocator.all()).thenReturn(List.of(cell1));
        when(cell1.innerText()).thenReturn("15");
        when(cell1.getAttribute("class")).thenReturn("mat-mdc-calendar-body-cell");
        assertFalse(testSubject.isDateSelected("15"));
    }

    @Test
    void isDateSelectedNotFound() {
        Locator cellsLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);
        when(locator.locator(".mat-mdc-calendar-body-cell")).thenReturn(cellsLocator);
        when(cellsLocator.all()).thenReturn(List.of(cell1));
        when(cell1.innerText()).thenReturn("14");
        assertFalse(testSubject.isDateSelected("20"));
    }
}
