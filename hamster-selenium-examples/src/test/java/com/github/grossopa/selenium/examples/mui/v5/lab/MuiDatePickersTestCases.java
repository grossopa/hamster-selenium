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
import com.github.grossopa.selenium.component.mui.v5.datetime.MuiYearPicker;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiCalendarView;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiPickersDay;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

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
     */
    public void testBasicDatePicker() {

    }

    /**
     * Tests the sub-component {@link MuiCalendarPicker}.
     *
     * @see <a href="https://mui.com/components/date-picker/#sub-components">
     * https://mui.com/components/date-picker/#sub-components</a>
     */
    public void testSubComponentsPickersCalendarPicker() {
        MuiCalendarPicker calendarPicker = driver.findComponent(By.id("SubComponentsPickers.js"))
                .findComponent(By2.parent()).findComponent(By.className("MuiCalendarPicker-root")).as(muiV5())
                .toCalendarPicker();
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
        calendarPicker.getSwitchButton().click();

        MuiYearPicker yearPicker = calendarPicker.getYearPicker();
        assertTrue(yearPicker.getYearButtons().size() > 100);
        yearPicker.select(2047);
    }

    public static void main(String[] args) {
        MuiDatePickersTestCases test = new MuiDatePickersTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/date-picker/");

        test.testSubComponentsPickersCalendarPicker();
    }
}
