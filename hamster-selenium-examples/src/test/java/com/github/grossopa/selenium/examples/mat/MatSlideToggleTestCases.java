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

import com.github.grossopa.hamster.selenium.component.mat.main.MatCheckbox;
import com.github.grossopa.hamster.selenium.component.mat.main.MatSlideToggle;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;

import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jack Yin
 * @since 1.0
 */
public class MatSlideToggleTestCases extends MatTestSupport {

    public void testSliderConfiguration() {
        navigateToExamples(baseUrl() + "slide-toggle/examples", "slide-toggle-configurable-example");
        WebComponent container = driver.findComponent(By.id("slide-toggle-configurable"));

        MatSlideToggle slideToggle = container.findComponent(By.className(cssClass("slide-toggle", "slide-toggle")))
                .as(matComponents()).toSlideToggle();
        assertFalse(slideToggle.isSelected());
        assertTrue(slideToggle.isEnabled());
        assertTrue(slideToggle.validate());
        assertEquals("Slide me!", slideToggle.getLabel().getText());

        MatCheckbox checkedBox = container.findComponent(By.id("mat-mdc-checkbox-0"))
                .as(matComponents()).toCheckbox();
        MatCheckbox disabledBox = container.findComponent(By.id("mat-mdc-checkbox-1"))
                .as(matComponents()).toCheckbox();

        slideToggle.click();
        assertTrue(slideToggle.isSelected());

        slideToggle.click();
        checkedBox.click();
        assertTrue(slideToggle.isSelected());

        disabledBox.click();
        // in MDC the disabled checkbox click does not affect the slide toggle state
        // the slide toggle remains in its previous state
    }

    public static void main(String[] args) {
        MatSlideToggleTestCases test = new MatSlideToggleTestCases();
        test.setUpDriver(EDGE);
        test.navigateToExamples(test.baseUrl() + "slide-toggle/examples");
        test.testSliderConfiguration();
    }
}
