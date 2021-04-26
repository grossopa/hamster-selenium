/*
 * Copyright © 2020 the original author or authors.
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
import com.github.grossopa.selenium.component.mui.pickers.MuiPickersDialog;
import com.github.grossopa.selenium.component.mui.pickers.MuiPickersYearSelectionContainer;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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


    public static void main(String[] args) {
        MuiPickersTestCases test = new MuiPickersTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testYearSelectionPopup();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            test.stopDriver();
        }
    }
}
