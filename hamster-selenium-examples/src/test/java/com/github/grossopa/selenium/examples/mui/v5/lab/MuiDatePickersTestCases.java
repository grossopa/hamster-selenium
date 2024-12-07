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

package com.github.grossopa.selenium.examples.mui.v5.lab;

import com.github.grossopa.selenium.component.mui.v5.datetime.MuiCalendarPicker;
import com.github.grossopa.selenium.component.mui.v5.datetime.MuiDatePickerFormField;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiCalendarView;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiMonthPicker;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiPickersDay;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiYearPicker;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.component.mui.v5.datetime.MuiCalendarPicker.ViewType.*;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.parent;
import static com.github.grossopa.selenium.core.locator.By2.textExact;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for Mui Pickers.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiDatePickersTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic date picker.
     *
     * @see <a href="https://mui.com/x/react-date-pickers/date-picker/#basic-usage">
     * https://mui.com/x/react-date-pickers/date-picker/#basic-usage</a>
     */
    public void testBasicDatePicker() {
        MuiDatePickerFormField datePickerFormField = driver.findComponent(By.id("BasicDatePicker.js"))
                .findComponent(parent()).findComponent(By.className("MuiTextField-root")).as(muiV5())
                .toDatePickerFormField();

        MuiCalendarPicker calendarPicker = datePickerFormField.openCalendarPicker(1200L);
        calendarPicker.setDate(LocalDate.of(2020, Month.JANUARY, 13), 500L);
        assertEquals("01/13/2020", datePickerFormField.getInput().getAttribute("value"));
    }

    /**
     * Tests the sub-component {@link MuiCalendarPicker}.
     *
     * @see <a href="https://mui.com/x/react-date-pickers/date-picker/#sub-components">
     * https://mui.com/x/react-date-pickers/date-picker/#sub-components</a>
     */
    public void testSubComponentsPickersCalendarPicker() {
        MuiCalendarPicker calendarPicker = driver.findComponent(By.id("SubComponentsPickers.js"))
                .findComponent(parent()).findComponent(By.className("MuiCalendarPicker-root")).as(muiV5())
                .toCalendarPicker(List.of(YEAR, DAY));
        assertTrue(calendarPicker.validate());
        driver.moveTo(calendarPicker);

        while (!"March".equalsIgnoreCase(calendarPicker.getMonthLabel().getText()) || !"2021".equalsIgnoreCase(
                calendarPicker.getYearLabel().getText())) {
            // for animation
            driver.threadSleep(500L);
            calendarPicker.getPreviousMonthButton().click();
        }

        calendarPicker.getPreviousMonthButton().click();
        calendarPicker.getNextMonthButton().click();

        driver.threadSleep(1000L);

        MuiCalendarView calendarView = calendarPicker.getCalendarView();

        List<MuiPickersDay> visibleDayList = calendarView.getDayButtons();
        assertEquals(31, visibleDayList.size());
        for (int i = 0; i < 31; i++) {
            assertEquals(String.valueOf(i + 1), visibleDayList.get(i).getText());
        }

        calendarView.select(15);
        assertTrue(visibleDayList.get(14).isSelected());

        calendarView.select(31);
        assertTrue(visibleDayList.get(30).isSelected());

        // testing year view
        calendarPicker.changeView(YEAR, 500);

        MuiYearPicker yearPicker = calendarPicker.getYearPicker();
        assertTrue(yearPicker.getYearButtons().size() > 100);
        yearPicker.select(2047);
        // back
        calendarPicker.changeView(DAY, 500);

        assertEquals("2047", calendarPicker.getYearLabel().getText());
        driver.threadSleep(500L);

        calendarPicker.setDate(LocalDate.of(2020, Month.JANUARY, 18), 500);
        assertEquals("2020", calendarPicker.getYearLabel().getText());
        assertEquals("January", calendarPicker.getMonthLabel().getText());
        assertEquals("18", calendarPicker.getCalendarView().getFirstSelectedDay().getText());
    }

    /**
     * Tests the sub-component {@link MuiMonthPicker}.
     *
     * @see <a href="https://mui.com/x/react-date-pickers/date-picker/#sub-components">
     * https://mui.com/x/react-date-pickers/date-picker/#sub-components</a>
     */
    public void testSubComponentsPickersMonthPicker() {
        MuiMonthPicker monthPicker = driver.findComponent(By.id("SubComponentsPickers.js")).findComponent(parent())
                .findComponent(By.className("MuiMonthPicker-root")).as(muiV5()).toMonthPicker();
        driver.moveTo(monthPicker);

        assertEquals(12, monthPicker.getMonthButtons().size());
        monthPicker.select(Month.NOVEMBER);
        assertEquals("Nov", monthPicker.getFirstSelectedMonthButton().getText());

        monthPicker.select(Month.JANUARY);
        assertEquals("Jan", monthPicker.getFirstSelectedMonthButton().getText());
    }

    /**
     * Tests the selection views of year, month and date
     *
     * @see <a href="https://mui.com/x/react-date-pickers/date-picker/#views-playground">
     * https://mui.com/x/react-date-pickers/date-picker/#views-playground</a>
     */
    public void testViewsPlayground() {
        WebComponent container = driver.findComponent(By.id("ViewsDatePicker.js")).findComponent(parent());

        // year only
        MuiDatePickerFormField yearOnly = container.findComponent(textExact("Year only")).findComponent(parent())
                .as(muiV5()).toDatePickerFormField(YEAR);
        assertTrue(yearOnly.validate());
        driver.moveTo(yearOnly);

        yearOnly.openCalendarPicker(500L).getYearPicker().select(2047);
        assertEquals("2047", yearOnly.getInput().getAttribute("value"));

        driver.threadSleep(500L);

        yearOnly.openCalendarPicker(500L).getYearPicker().select(2099);
        assertEquals("2099", yearOnly.getInput().getAttribute("value"));

        driver.threadSleep(500L);

        yearOnly.openCalendarPicker(500L).getYearPicker().select(1900);
        assertEquals("1900", yearOnly.getInput().getAttribute("value"));

        driver.threadSleep(500L);

        yearOnly.setDate(LocalDate.of(2030, 1, 1), 500L);
        assertEquals("2030", yearOnly.getInput().getAttribute("value"));

        yearOnly.setDate(LocalDate.of(2041, 1, 1), 500L);
        assertEquals("2041", yearOnly.getInput().getAttribute("value"));

        MuiDatePickerFormField yearMonth = container.findComponent(textExact("Year and Month")).findComponent(parent())
                .as(muiV5()).toDatePickerFormField(YEAR, MONTH);
        assertTrue(yearMonth.validate());

        driver.threadSleep(500L);

        MuiCalendarPicker calendarPicker = yearMonth.openCalendarPicker(500L);
        calendarPicker.getYearPicker().select(2018);

        driver.threadSleep(500L);

        calendarPicker = yearMonth.openCalendarPicker(500L);
        calendarPicker.getMonthPicker().select(Month.DECEMBER);
        assertEquals("December 2018", yearMonth.getInput().getAttribute("value"));

        driver.threadSleep(500L);

        yearMonth.setDate(LocalDate.of(2021, Month.OCTOBER, 1), 500L);
        assertEquals("October 2021", yearMonth.getInput().getAttribute("value"));

        yearMonth.setDate(LocalDate.of(2021, Month.FEBRUARY, 1), 500L);
        assertEquals("February 2021", yearMonth.getInput().getAttribute("value"));

        yearMonth.setDate(LocalDate.of(2023, Month.FEBRUARY, 1), 500L);
        assertEquals("February 2023", yearMonth.getInput().getAttribute("value"));

        MuiDatePickerFormField yearMonthDate = container.findComponent(textExact("Year, month and date"))
                .findComponent(parent()).as(muiV5()).toDatePickerFormField(YEAR, MONTH, DAY);

        yearMonthDate.setDate(LocalDate.of(2013, Month.OCTOBER, 3), 500L);
        assertEquals("10/03/2013", yearMonthDate.getInput().getAttribute("value"));

        MuiDatePickerFormField dayMonthYear = container.findComponent(textExact("Invert the order of views"))
                .findComponent(parent()).as(muiV5()).toDatePickerFormField(DAY, MONTH, YEAR);
        dayMonthYear.setDate(LocalDate.of(2014, Month.JANUARY, 15), 500L);
        assertEquals("01/15/2014", dayMonthYear.getInput().getAttribute("value"));

        MuiDatePickerFormField justDate = container.findComponent(textExact("Just date")).findComponent(parent())
                .as(muiV5()).toDatePickerFormField(DAY);
        justDate.setDate(LocalDate.of(2014, Month.JANUARY, 18), 500L);
        assertEquals("01/18/2014", justDate.getInput().getAttribute("value"));
    }

    public static void main(String[] args) {
        MuiDatePickersTestCases test = new MuiDatePickersTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/x/react-date-pickers/date-picker/");

        test.testBasicDatePicker();
        test.testSubComponentsPickersCalendarPicker();
        test.testSubComponentsPickersMonthPicker();
        test.testViewsPlayground();
    }
}
