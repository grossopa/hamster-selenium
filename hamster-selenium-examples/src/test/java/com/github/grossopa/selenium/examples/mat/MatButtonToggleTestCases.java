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

import com.github.grossopa.hamster.selenium.component.mat.main.MatButtonToggle;
import com.github.grossopa.hamster.selenium.component.mat.main.MatButtonToggleGroup;
import com.github.grossopa.selenium.core.locator.By2;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the actual features of {@link MatButtonToggle} and {@link MatButtonToggleGroup}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatButtonToggleTestCases extends MatTestSupport {

    public void testButtonToggleGroup() {
        navigateToExamples(baseUrl() + "button-toggle/examples", "button-toggle-overview-example");
        // the latest page uses #button-toggle-overview with 3 toggles (bold, italic, underline)
        MatButtonToggleGroup buttonToggleGroup = driver.findComponent(By.id("button-toggle-overview"))
                .findComponent(By.tagName("button-toggle-overview-example"))
                .findComponent(By2.xpathBuilder().relative("mat-button-toggle-group").build()).as(matComponents())
                .toButtonToggleGroup();

        buttonToggleGroup.validate();

        List<MatButtonToggle> buttonToggles = buttonToggleGroup.getButtonToggles();
        assertEquals(3, buttonToggles.size());
        assertTrue(buttonToggles.stream().allMatch(MatButtonToggle::validate));

        // scroll the group into the center of the viewport to avoid the floating popup overlay
        driver.executeScript("arguments[0].scrollIntoView({block: 'center'});", buttonToggleGroup);
        buttonToggles.get(0).click();
        assertTrue(buttonToggles.get(0).isSelected());

        buttonToggles.get(1).click();
        assertTrue(buttonToggles.get(1).isSelected());
    }

    public static void main(String[] args) {
        MatButtonToggleTestCases test = new MatButtonToggleTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testButtonToggleGroup();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
