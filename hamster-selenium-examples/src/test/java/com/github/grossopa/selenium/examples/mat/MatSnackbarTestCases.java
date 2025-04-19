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

package com.github.grossopa.selenium.examples.mat;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.hamster.selenium.component.mat.main.MatSnackbar;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Jack Yin
 * @since 1.0
 */
public class MatSnackbarTestCases extends AbstractBrowserSupport {

    public void testSliderConfiguration() {
        WebComponent button = driver.findComponent(By.id("snack-bar-overview"))
                .findComponent(xpathBuilder().anywhereRelative().text().exact("Show snack-bar").build());
        button.click();

        MatOverlayFinder finder = new MatOverlayFinder(driver, new MatConfig());
        WebComponent overlayContainer = finder.findTopVisibleContainer();
        assertNotNull(overlayContainer);
        MatSnackbar snackbar = overlayContainer.findComponent(By.tagName("simple-snack-bar")).as(mat()).toSnackbar();
        assertEquals("Disco party!", snackbar.getLabel().getText());
        assertEquals("Dance", snackbar.getActionButton().getText());

        snackbar.getActionButton().click();

        // wait for the animation to disappear
        driver.threadSleep(1000L);

        assertEquals(0, overlayContainer.findComponents(By.tagName("simple-snack-bar")).size());
    }

    public static void main(String[] args) {
        MatSnackbarTestCases test = new MatSnackbarTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://material.angular.io/components/snack-bar/examples");
        test.testSliderConfiguration();
    }
}
