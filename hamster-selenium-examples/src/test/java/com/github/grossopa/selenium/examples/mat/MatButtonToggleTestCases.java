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

import com.github.grossopa.hamster.selenium.component.mat.main.MatButtonToggle;
import com.github.grossopa.hamster.selenium.component.mat.main.MatButtonToggleGroup;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the actual features of {@link MatButtonToggle} and {@link MatButtonToggleGroup}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatButtonToggleTestCases extends AbstractBrowserSupport {

    public void testButtonToggleGroup() {
        driver.navigate().to("https://material.angular.io/components/button-toggle/examples");

        MatButtonToggleGroup buttonToggleGroup = driver.findComponent(By.id("button-toggle-exclusive"))
                .findComponent(By.tagName("button-toggle-exclusive-example"))
                .findComponent(By2.xpathBuilder().relative("mat-button-toggle-group").build()).as(mat())
                .toButtonToggleGroup();

        buttonToggleGroup.validate();

        List<MatButtonToggle> buttonToggles = buttonToggleGroup.getButtonToggles();

        assertEquals(4, buttonToggles.size());
        assertTrue(buttonToggles.get(0).validate());
        assertTrue(buttonToggles.get(1).validate());
        assertTrue(buttonToggles.get(2).validate());
        assertTrue(buttonToggles.get(3).validate());

        assertTrue(buttonToggles.get(0).isEnabled());
        assertTrue(buttonToggles.get(1).isEnabled());
        assertTrue(buttonToggles.get(2).isEnabled());
        assertFalse(buttonToggles.get(3).isEnabled());

        buttonToggles.get(1).click();
        assertFalse(buttonToggles.get(0).isSelected());
        assertTrue(buttonToggles.get(1).isSelected());
        assertFalse(buttonToggles.get(2).isSelected());
        assertFalse(buttonToggles.get(3).isSelected());

        buttonToggles.get(2).click();
        assertFalse(buttonToggles.get(0).isSelected());
        assertFalse(buttonToggles.get(1).isSelected());
        assertTrue(buttonToggles.get(2).isSelected());
        assertFalse(buttonToggles.get(3).isSelected());
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
