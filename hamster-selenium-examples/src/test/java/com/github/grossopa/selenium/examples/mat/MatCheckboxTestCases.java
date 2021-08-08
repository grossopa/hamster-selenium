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
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the actual features of {@link MatCheckbox}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatCheckboxTestCases extends AbstractBrowserSupport {

    public void testCheckbox() {
        driver.navigate().to("https://material.angular.io/components/checkbox/examples");
        List<MatCheckbox> checkboxList = driver.findComponent(By.id("checkbox-configurable"))
                .findComponentsAs(By.tagName("mat-checkbox"), c -> c.as(mat()).toCheckbox());

        assertEquals(4, checkboxList.size());
        assertTrue(checkboxList.stream().allMatch(MatCheckbox::validate));
        assertTrue(checkboxList.stream().allMatch(MatCheckbox::isEnabled));
        assertTrue(checkboxList.stream().noneMatch(MatCheckbox::isSelected));

        assertEquals("Checked", checkboxList.get(0).getText());
        assertEquals("Indeterminate", checkboxList.get(1).getText());
        assertEquals("Disabled", checkboxList.get(2).getText());
        assertEquals("I'm a checkbox", checkboxList.get(3).getText());

        checkboxList.get(0).click();
        assertTrue(checkboxList.get(0).isSelected());

        checkboxList.get(2).click();
        assertFalse(checkboxList.get(3).isEnabled());
    }

    public static void main(String[] args) {
        MatCheckboxTestCases test = new MatCheckboxTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testCheckbox();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            test.stopDriver();
        }
    }
}