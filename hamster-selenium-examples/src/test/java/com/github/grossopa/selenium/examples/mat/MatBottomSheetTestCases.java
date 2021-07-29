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
import com.github.grossopa.hamster.selenium.component.mat.main.MatBottomSheet;
import com.github.grossopa.hamster.selenium.component.mat.main.MatOverlayContainer;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the actual features of {@link MatBottomSheet}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatBottomSheetTestCases extends AbstractBrowserSupport {

    public void testBottomSheet() {
        driver.navigate().to("https://material.angular.io/components/bottom-sheet/examples");
        WebComponent openFileButton = driver.findComponent(
                xpathBuilder().anywhere("p").text().exact("You have received a file called \"cat-picture.jpeg\".")
                        .parent().build()).findComponent(By.tagName("button"));

        assertEquals("Open file", openFileButton.getText());
        openFileButton.click();

        MatOverlayFinder overlayFinder = new MatOverlayFinder(driver, new MatConfig());
        MatOverlayContainer container = overlayFinder.findTopVisibleContainer();
        MatBottomSheet bottomSheet = requireNonNull(container).findComponent(By.className("mat-bottom-sheet-container"))
                .as(mat()).toBottomSheet();

        List<WebComponent> hrefList = bottomSheet.findComponents(By.tagName("a"));
        assertEquals(4, hrefList.size());
    }

    public static void main(String[] args) {
        MatBottomSheetTestCases test = new MatBottomSheetTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testBottomSheet();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            test.stopDriver();
        }
    }
}
