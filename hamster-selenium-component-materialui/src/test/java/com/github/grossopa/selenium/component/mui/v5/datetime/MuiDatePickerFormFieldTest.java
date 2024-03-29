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

package com.github.grossopa.selenium.component.mui.v5.datetime;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.exception.DatePickerNotClosedException;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.v5.datetime.MuiCalendarPicker.ViewType.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.time.Month.DECEMBER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiDatePickerFormField}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiDatePickerFormFieldTest {

    MuiDatePickerFormField testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement dateButtonElement = mock(WebElement.class);
    WebElement pickersDialogElement = mock(WebElement.class);

    WebDriverWait webDriverWait = mock(WebDriverWait.class);

    MuiCalendarPickerTest calendarPickerTest;

    @BeforeEach
    @SuppressWarnings({"unchecked", "rawtypes"})
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.getOverlayAbsolutePath()).thenReturn("/html/body");

        when(pickersDialogElement.isDisplayed()).thenReturn(true);

        when(driver.createWait(500L)).thenReturn(webDriverWait);
        when(webDriverWait.until(any())).then(a -> {
            Function function = a.getArgument(0);
            return function.apply(driver);
        });

        when(element.findElement(
                By.xpath(".//div[contains(@class,\"MuiInputAdornment-root\")]/child::button"))).thenReturn(
                dateButtonElement);

        mockNoPickersDialog();

        calendarPickerTest = new MuiCalendarPickerTest();
        calendarPickerTest.element = pickersDialogElement;
        calendarPickerTest.setUp();
        when(calendarPickerTest.monthLabelElement.getText()).thenReturn("October");

        testSubject = new MuiDatePickerFormField(element, driver, config);
    }

    private void mockHasPickersDialog() {
        when(element.findElements(
                By.xpath("/html/body/div[@role='dialog']//div[contains(@class,'MuiCalendarPicker-root')]"))).thenReturn(
                List.of(pickersDialogElement));
    }

    private void mockNoPickersDialog() {
        when(element.findElements(
                By.xpath("/html/body/div[@role='dialog']//div[contains(@class,'MuiCalendarPicker-root')]"))).thenReturn(
                newArrayList());
    }


    @Test
    void version() {
        assertArrayEquals(new Object[]{V5}, testSubject.versions().toArray());
    }

    @Test
    void getDateButton() {
        assertEquals(dateButtonElement, testSubject.getDateButton().getWrappedElement());
    }

    @Test
    void openCalendarPicker1() {
        mockNoPickersDialog();
        doAnswer(a -> {
            mockHasPickersDialog();
            return null;
        }).when(dateButtonElement).click();

        assertEquals(pickersDialogElement, testSubject.openCalendarPicker().getWrappedElement());
    }

    @Test
    void openCalendarPicker2() {
        mockNoPickersDialog();
        doAnswer(a -> {
            mockHasPickersDialog();
            return null;
        }).when(dateButtonElement).click();

        assertEquals(pickersDialogElement, testSubject.openCalendarPicker(500L).getWrappedElement());
    }

    @Test
    void openCalendarPicker3() {
        mockHasPickersDialog();

        assertEquals(pickersDialogElement, testSubject.openCalendarPicker(500L).getWrappedElement());
        verify(dateButtonElement, never()).click();
    }

    @Test
    void closePicker1() {
        mockHasPickersDialog();

        doAnswer(a -> {
            mockNoPickersDialog();
            return null;
        }).when(pickersDialogElement).sendKeys(Keys.ESCAPE);

        testSubject.closePicker();
        verify(pickersDialogElement, times(1)).sendKeys(Keys.ESCAPE);
    }

    @Test
    void closePicker2() {
        mockHasPickersDialog();

        doAnswer(a -> {
            mockNoPickersDialog();
            return null;
        }).when(pickersDialogElement).sendKeys(Keys.ESCAPE);

        testSubject.closePicker(500L);
        verify(pickersDialogElement, times(1)).sendKeys(Keys.ESCAPE);
    }

    @Test
    void closePicker3() {
        mockHasPickersDialog();

        doAnswer(a -> {
            when(pickersDialogElement.isDisplayed()).thenReturn(false);
            return null;
        }).when(pickersDialogElement).sendKeys(Keys.ESCAPE);

        testSubject.closePicker(500L);
        verify(pickersDialogElement, times(1)).sendKeys(Keys.ESCAPE);
    }

    @Test
    void closePicker4() {
        mockNoPickersDialog();

        testSubject.closePicker(500L);
        verify(pickersDialogElement, never()).sendKeys(Keys.ESCAPE);
    }

    @Test
    void closePicker5() {
        mockHasPickersDialog();
        when(pickersDialogElement.isDisplayed()).thenReturn(false);

        testSubject.closePicker(500L);
        verify(pickersDialogElement, never()).sendKeys(Keys.ESCAPE);
    }

    @Test
    void closePicker6() {
        mockHasPickersDialog();

        assertThrows(DatePickerNotClosedException.class, () -> testSubject.closePicker());
    }

    @Test
    @SuppressWarnings("all")
    void constructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new MuiDatePickerFormField(element, driver, config, newArrayList()));
    }

    @Test
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        List<MuiCalendarPicker.ViewType> views1 = newArrayList(YEAR, MONTH);
        List<MuiCalendarPicker.ViewType> views2 = newArrayList(MONTH, YEAR);
        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new MuiDatePickerFormField(element1, driver, config, views1),
                new MuiDatePickerFormField(element1, driver, config, newArrayList(YEAR, MONTH)));
        tester.addEqualityGroup(new MuiDatePickerFormField(element1, driver, config, views2));
        tester.addEqualityGroup(new MuiDatePickerFormField(element2, driver, config, views1));
        tester.addEqualityGroup(new MuiDatePickerFormField(element2, driver, config, views2));

        tester.testEquals();
    }


    @Test
    void setDateNoDelays() {
        mockNoPickersDialog();
        doAnswer(a -> {
            mockHasPickersDialog();
            return null;
        }).when(dateButtonElement).click();

        testSubject.setDate(LocalDate.of(2012, DECEMBER, 1));

        verify(calendarPickerTest.nextMonthButtonElement, times(2)).click();
        verify(calendarPickerTest.previousMonthButtonElement, never()).click();
        assertEquals("2012", calendarPickerTest.currentYear);
        verify(calendarPickerTest.dayButtons.get(0), times(1)).click();
        verify(driver, times(4)).threadSleep(0L);
    }

    @Test
    void testSetDate() {
        mockNoPickersDialog();
        doAnswer(a -> {
            mockHasPickersDialog();
            return null;
        }).when(dateButtonElement).click();

        when(driver.createWait(876L)).thenReturn(webDriverWait);

        testSubject.setDate(LocalDate.of(2012, DECEMBER, 1), 876L);

        verify(calendarPickerTest.nextMonthButtonElement, times(2)).click();
        verify(calendarPickerTest.previousMonthButtonElement, never()).click();
        assertEquals("2012", calendarPickerTest.currentYear);
        verify(calendarPickerTest.dayButtons.get(0), times(1)).click();
        verify(driver, times(4)).threadSleep(876L);
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element");
        assertEquals("MuiDatePickerFormField{views=YEAR,DAY, element=element}", testSubject.toString());
    }

    @Test
    void getViews() {
        assertArrayEquals(new Object[]{YEAR, DAY}, testSubject.getViews().toArray());
    }
}
