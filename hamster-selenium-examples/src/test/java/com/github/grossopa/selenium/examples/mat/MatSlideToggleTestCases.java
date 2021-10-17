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

import com.github.grossopa.hamster.selenium.component.mat.main.MatCheckbox;
import com.github.grossopa.hamster.selenium.component.mat.main.MatSlideToggle;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.By.className;

/**
 * @author Jack Yin
 * @since 1.0
 */
public class MatSlideToggleTestCases extends AbstractBrowserSupport {

    public void testSliderConfiguration() {
        WebComponent container = driver.findComponent(By.id("slide-toggle-configurable"));

        MatSlideToggle slideToggle = container.findComponent(className("mat-slide-toggle")).as(mat()).toSlideToggle();
        assertFalse(slideToggle.isSelected());
        assertTrue(slideToggle.isEnabled());
        assertTrue(slideToggle.validate());
        assertEquals("Slide me!", slideToggle.getLabel().getText());

        MatCheckbox checkedCheckBox = container.findComponent(By.id("mat-checkbox-1")).as(mat()).toCheckbox();
        MatCheckbox disabledCheckBox = container.findComponent(By.id("mat-checkbox-2")).as(mat()).toCheckbox();

        slideToggle.click();
        assertTrue(slideToggle.isSelected());
        assertTrue(slideToggle.isEnabled());

        slideToggle.click();
        checkedCheckBox.click();
        assertTrue(slideToggle.isSelected());
        assertTrue(slideToggle.isEnabled());

        disabledCheckBox.click();
        assertTrue(slideToggle.isSelected());
        assertFalse(slideToggle.isEnabled());
    }

    public static void main(String[] args) {
        MatSlideToggleTestCases test = new MatSlideToggleTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://material.angular.io/components/slide-toggle/examples");
        test.testSliderConfiguration();
    }
}
