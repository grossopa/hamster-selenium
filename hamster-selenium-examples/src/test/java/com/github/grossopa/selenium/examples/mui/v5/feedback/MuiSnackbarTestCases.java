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

import com.github.grossopa.selenium.component.mui.v4.feedback.MuiSnackbar;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.axesBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for {@link MuiSnackbar}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiSnackbarTestCases extends AbstractBrowserSupport {

    /**
     * Tests the simple Snackbars.
     *
     * @see <a href="https://mui.com/material-ui/react-snackbar/#simple-snackbars">
     * https://mui.com/material-ui/react-snackbar/#simple-snackbars</a>
     */
    public void testSimpleSnackbars() {
        MuiButton simpleSnackbarButton = driver.findComponent(By.id("SimpleSnackbar.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiButton-root")).as(muiV5()).toButton();

        simpleSnackbarButton.click();
        MuiSnackbar snackbar = simpleSnackbarButton.findComponent(axesBuilder().followingSibling().build()).as(muiV5())
                .toSnackbar();

        assertEquals("Note archived", snackbar.getContent().getMessage().getText());
        assertEquals("UNDO", snackbar.getContent().getAction().findComponent(By.className("MuiButton-root")).getText());

        driver.threadSleep(1000L);
        snackbar.getContent().getAction().findComponent(By.className("MuiIconButton-root")).click();

        driver.threadSleep(1000L);
        assertThrows(StaleElementReferenceException.class, snackbar::isDisplayed);

        // test simple Snackbar auto hide solution
        simpleSnackbarButton.click();
        snackbar = simpleSnackbarButton.findComponent(axesBuilder().followingSibling().build()).as(muiV5())
                .toSnackbar(7000L);
        snackbar.startAutoHideCheck();
        assertThrows(StaleElementReferenceException.class, snackbar::isDisplayed);
    }

    public static void main(String[] args) {
        MuiSnackbarTestCases test = new MuiSnackbarTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-snackbar/");

        test.testSimpleSnackbars();
    }
}
