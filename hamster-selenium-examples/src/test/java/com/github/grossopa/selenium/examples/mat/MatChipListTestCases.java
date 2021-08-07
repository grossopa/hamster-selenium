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

import com.github.grossopa.hamster.selenium.component.mat.main.MatAutocomplete;
import com.github.grossopa.hamster.selenium.component.mat.main.MatChipList;
import com.github.grossopa.hamster.selenium.component.mat.main.MatFormField;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the actual features of {@link MatChipList}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatChipListTestCases extends AbstractBrowserSupport {

    public void testChipList() {
        driver.navigate().to("https://material.angular.io/components/chips/examples");

        MatFormField matFormField = driver.findComponent(By.tagName("chips-autocomplete-example"))
                .findComponent(By.tagName("mat-form-field")).as(mat()).toFormField();

        MatChipList chipList = matFormField.getInfix().findComponent(By.tagName("mat-chip-list")).as(mat())
                .toChipList();

        assertEquals(1, chipList.getChips().size());
        assertEquals("Lemon", chipList.getChips().get(0).getText());
        chipList.getChips().get(0).getRemoveIcon().click();
        assertTrue(chipList.getChips().isEmpty());

        MatAutocomplete autocomplete = matFormField.as(mat()).toAutocomplete();
        autocomplete.selectByVisibleText("Apple");
        autocomplete.selectByVisibleText("Orange");
        autocomplete.selectByVisibleText("Strawberry");

        MatChipList newChipList = matFormField.getInfix().findComponent(By.tagName("mat-chip-list")).as(mat())
                .toChipList();

        assertEquals(3, newChipList.getChips().size());
        assertEquals("Apple", newChipList.getChips().get(0).getText());
        assertEquals("Orange", newChipList.getChips().get(1).getText());
        assertEquals("Strawberry", newChipList.getChips().get(2).getText());
    }

    public static void main(String[] args) {
        MatChipListTestCases test = new MatChipListTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testChipList();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            test.stopDriver();
        }
    }
}
