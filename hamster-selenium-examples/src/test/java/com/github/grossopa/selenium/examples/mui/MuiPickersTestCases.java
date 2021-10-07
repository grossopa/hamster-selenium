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

package com.github.grossopa.selenium.examples.mui;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.inputs.MuiTextField;
import com.github.grossopa.selenium.component.mui.locator.MuiDialogLocator;
import com.github.grossopa.selenium.component.mui.pickers.MuiPickersBasicPickerViewComponents;
import com.github.grossopa.selenium.component.mui.pickers.MuiPickersDialog;
import com.github.grossopa.selenium.component.mui.pickers.MuiPickersYearSelectionContainer;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.Calendar;
import java.util.Locale;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The Mui Date and Date time pickers test cases
 *
 * @author Jack Yin
 * @since 1.2
 */
public class MuiPickersTestCases extends AbstractBrowserSupport {

    public void testYearSelectionPopup() {
        driver.navigate().to("https://material-ui-pickers.dev/demo/datepicker");

        MuiTextField inputField = driver.findComponent(By2.xpath("//label[text()='Year only']"))
                .findComponent(By2.parent()).findComponent(By.className("MuiInput-root")).as(mui()).toTextField();

        assertEquals("2021", inputField.getInput().getAttribute("value"));
        inputField.click();

        MuiDialogLocator locator = new MuiDialogLocator(driver, new MuiConfig());
        MuiPickersDialog dialog = locator.findVisibleDialogs().get(0).as(mui()).toPickersDialog();

        MuiPickersYearSelectionContainer yearSelectionContainer = dialog.getPickersContainer().getAsYearSelection();
        assertEquals("2021", requireNonNull(yearSelectionContainer.getSelectedYear()).getText());
        yearSelectionContainer.select("1905");

        assertEquals("1905", requireNonNull(yearSelectionContainer.getSelectedYear()).getText());

        dialog.getOkButton().click();

        assertEquals("1905", inputField.getInput().getAttribute("value"));
    }

    public void testBasicExample() {
        driver.navigate().to("https://material-ui-pickers.dev/demo/datepicker");

        MuiTextField inputField = driver.findComponent(By2.xpath("//label[text()='Basic example']"))
                .findComponent(By2.parent()).findComponent(By.className("MuiInput-root")).as(mui()).toTextField();

        assertEquals(getCurrentDayInString(), inputField.getInput().getAttribute("value"));

        driver.moveTo(inputField);
        inputField.click();

        MuiDialogLocator locator = new MuiDialogLocator(driver, new MuiConfig());
        MuiPickersDialog dialog = locator.findVisibleDialogs().get(0).as(mui()).toPickersDialog();
        MuiPickersBasicPickerViewComponents basicPickerViewComponents = dialog.getPickersContainer().getAsBasic();

        assertNotNull(basicPickerViewComponents.getDaysHeader());
        assertNotNull(basicPickerViewComponents.getTransitionContainer());
        assertNotNull(basicPickerViewComponents.getSwitchHeader());

        Calendar calendar = Calendar.getInstance();

        assertTrue(basicPickerViewComponents.getTransitionContainer().getDayList().size() >= 28);
        assertEquals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                requireNonNull(basicPickerViewComponents.getTransitionContainer().getSelectedDay()).getText());

        basicPickerViewComponents.getSwitchHeader().clickLeftButton(1000);

        basicPickerViewComponents.getTransitionContainer().select("14");

        assertEquals("14", basicPickerViewComponents.getTransitionContainer().getSelectedDay().getText());
        dialog.getOkButton().click();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.MONTH, calendar2.get(Calendar.MONTH) - 1);
        calendar2.set(Calendar.DAY_OF_MONTH, 14);
        assertEquals(getDayInString(calendar2), inputField.getInput().getAttribute("value"));
    }

    private String getCurrentDayInString() {
        return getDayInString(Calendar.getInstance());
    }

    private String getDayInString(Calendar calendar) {
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        String day = getDisplayDay(calendar.get(Calendar.DAY_OF_MONTH));
        return month + " " + day;
    }

    private String getDisplayDay(int day) {
        String suffix = "th";
        if (day % 20 == 1 || day == 31) {
            suffix = "st";
        } else if (day % 20 == 2) {
            suffix = "nd";
        } else if (day % 20 == 3) {
            suffix = "rd";
        }
        return day + suffix;
    }

    public static void main(String[] args) {
        MuiPickersTestCases test = new MuiPickersTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testYearSelectionPopup();
            test.testBasicExample();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
