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
import com.github.grossopa.selenium.component.mui.feedback.MuiDialog;
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.locator.MuiDialogLocator;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for Feedback component.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiFeedbackTestCases extends AbstractBrowserSupport {

    @SuppressWarnings("squid:S2925")
    public void testDialog() {
        driver.navigate().to("https://material-ui.com/components/dialogs/");
        MuiButton openSimpleDialogButton = driver.findComponent(By.xpath("//*[contains(text(), 'Open simple dialog')]"))
                .findComponent(By.xpath("parent::*")).as(mui()).toButton();
        openSimpleDialogButton.click();
        List<MuiDialog> visibleDialogs = new MuiDialogLocator(driver, new MuiConfig()).findVisibleDialogs();
        assertEquals(1, visibleDialogs.size());
        driver.threadSleep(500L);
        visibleDialogs.get(0).close();
        driver.threadSleep(800L);
        visibleDialogs = new MuiDialogLocator(driver, new MuiConfig()).findVisibleDialogs();
        assertTrue(visibleDialogs.isEmpty());
    }

    public static void main(String[] args) {
        MuiFeedbackTestCases test = new MuiFeedbackTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testDialog();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            test.stopDriver();
        }
    }
}
