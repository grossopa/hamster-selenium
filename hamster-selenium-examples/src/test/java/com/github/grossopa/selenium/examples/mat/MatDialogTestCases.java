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
import com.github.grossopa.hamster.selenium.component.mat.main.MatButton;
import com.github.grossopa.hamster.selenium.component.mat.main.MatChipList;
import com.github.grossopa.hamster.selenium.component.mat.main.MatDialog;
import com.github.grossopa.hamster.selenium.component.mat.main.MatOverlayContainer;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the actual features of {@link MatChipList}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatDialogTestCases extends AbstractBrowserSupport {

    public void testDialog() {
        driver.navigate().to("https://material.angular.io/components/dialog/examples");

        MatOverlayFinder overlayFinder = new MatOverlayFinder(driver, new MatConfig());
        MatButton openDialogButton1 = driver.findComponent(By.tagName("dialog-content-example"))
                .findComponent(By.tagName("button")).as(mat()).toButton();
        openDialogButton1.click();
        MatOverlayContainer overlayContainer = overlayFinder.findTopVisibleContainer();
        assertNotNull(overlayContainer);
        MatDialog dialog = overlayContainer.findComponent(By.tagName("mat-dialog-container")).as(mat()).toDialog();
        assertTrue(dialog.validate());
        assertEquals("Install Angular", dialog.getDialogTitle().getText());
        assertTrue(dialog.getDialogContent().getText().startsWith("Develop across all platforms"));

        List<MatButton> buttons = dialog.getDialogActions()
                .findComponentsAs(By.tagName("button"), c -> c.as(mat()).toButton());
        assertEquals("Cancel", buttons.get(0).getText());
        assertEquals("Install", buttons.get(1).getText());

        buttons.get(0).click();
        // wait for animation to end
        driver.threadSleep(1000L);
        assertNull(overlayFinder.findTopVisibleContainer());
    }

    public static void main(String[] args) {
        MatDialogTestCases test = new MatDialogTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testDialog();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            test.stopDriver();
        }
    }
}
