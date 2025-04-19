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

package com.github.grossopa.selenium.examples.mui.v5.feedback;

import com.github.grossopa.selenium.component.mui.v4.feedback.MuiDialog;
import com.github.grossopa.selenium.component.mui.v4.finder.MuiModalFinder;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiDialog}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiDialogTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic dialog
     *
     * @see <a href="https://mui.com/material-ui/react-dialog/#basic-dialog">
     * https://mui.com/material-ui/react-dialog/#basic-dialog</a>
     */
    public void testBasicDialog() {
        MuiButton showDialogButton = driver.findComponent(By.id("SimpleDialogDemo.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiButton-root")).as(muiV5()).toButton();

        showDialogButton.click();
        MuiModalFinder modalFinder = new MuiModalFinder(driver, muiV5().getConfig());
        MuiDialog dialog = requireNonNull(modalFinder.findTopVisibleOverlay()).findComponent(
                By.className("MuiDialog-container")).as(muiV5()).toDialog();
        assertEquals("Set backup account", dialog.getDialogTitle().getText());
        assertTrue(dialog.isDisplayed());
        dialog.close(1000L);
        assertThrows(StaleElementReferenceException.class, dialog::getDialogTitle);
    }

    /**
     * Tests the alerts
     *
     * @see <a href="https://mui.com/material-ui/react-dialog/#alerts">
     * https://mui.com/material-ui/react-dialog/#alerts</a>
     */
    public void testAlerts() {
        MuiButton openDialogButton = driver.findComponent(By.id("AlertDialog.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiButton-root")).as(muiV5()).toButton();

        openDialogButton.click();
        MuiModalFinder modalFinder = new MuiModalFinder(driver, muiV5().getConfig());
        MuiDialog dialog = requireNonNull(modalFinder.findTopVisibleOverlay()).findComponent(
                By.className("MuiDialog-container")).as(muiV5()).toDialog();

        assertEquals("Use Google's location service?", dialog.getDialogTitle().getText());
        assertEquals("Let Google help apps determine location. This means sending "
                        + "anonymous location data to Google, even when no apps are running.",
                dialog.getDialogContent().getText());
        assertEquals(2, dialog.getDialogActions().findComponents(By.className("MuiButton-root")).size());

        assertTrue(dialog.isDisplayed());
        dialog.getDialogActions().findComponents(By.className("MuiButton-root")).get(0).click();
        driver.threadSleep(1000L);
        assertThrows(StaleElementReferenceException.class, dialog::getDialogTitle);
    }

    public static void main(String[] args) {
        MuiDialogTestCases test = new MuiDialogTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-dialog/");

        test.testBasicDialog();
        test.testAlerts();
    }
}
