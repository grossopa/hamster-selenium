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
package com.github.grossopa.selenium.examples.mat;

import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.hamster.selenium.component.mat.main.MatSnackbar;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;

import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the actual features of {@link MatSnackbar}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MatSnackbarTestCases extends MatTestSupport {

    public void testSliderConfiguration() {
        navigateToExamples(baseUrl() + "snack-bar/examples", "snack-bar-overview-example");
        WebComponent button = driver.findComponent(By.tagName("snack-bar-overview-example"))
                .findComponent(By.tagName("button"));
        button.click();

        // wait for the snackbar animation to settle before reading its content
        driver.threadSleep(1000L);

        MatOverlayFinder finder = new MatOverlayFinder(driver, matConfig());
        WebComponent overlayContainer = finder.findTopVisibleContainer();
        assertNotNull(overlayContainer);
        MatSnackbar snackbar = overlayContainer.findComponent(By.tagName("simple-snack-bar"))
                .as(matComponents()).toSnackbar();
        assertEquals("Disco party!", awaitText(snackbar.getLabel()));
        assertEquals("Dance", awaitText(snackbar.getActionButton()));

        snackbar.getActionButton().click();

        // poll until the snackbar is removed from the overlay after the dismiss animation;
        // the first click may be missed on the slow doc site, so click once more on timeout
        boolean dismissed = false;
        for (int i = 0; i < 80 && !dismissed; i++) {
            dismissed = overlayContainer.findComponents(By.tagName("simple-snack-bar")).isEmpty();
            if (!dismissed) {
                driver.threadSleep(250L);
                if (i == 39) {
                    // re-locate the action button as the previous reference may be stale
                    overlayContainer.findComponent(By.tagName("simple-snack-bar")).as(matComponents())
                            .toSnackbar().getActionButton().click();
                }
            }
        }

        assertTrue(dismissed, "the snackbar should be dismissed after clicking the action button");
    }

    private String awaitText(WebComponent component) {
        for (int i = 0; i < 40; i++) {
            String text = component.getText();
            if (text != null && !text.isBlank()) {
                return text;
            }
            driver.threadSleep(250L);
        }
        return component.getText();
    }

    public static void main(String[] args) {
        MatSnackbarTestCases test = new MatSnackbarTestCases();
        test.setUpDriver(EDGE);
        test.navigateToExamples(test.baseUrl() + "snack-bar/examples", "snack-bar-overview-example");
        test.testSliderConfiguration();
    }
}
