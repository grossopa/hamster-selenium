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
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Material UI Angular - Autocomplete related cases.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatAutocompleteTestCases extends AbstractBrowserSupport {

    public void testAutocomplete() {
        driver.navigate().to("https://material.angular.io/components/autocomplete/examples");

        MatAutocomplete autocomplete = driver.findComponent(By2.id("autocomplete-auto-active-first-option"))
                .findComponent(By.tagName("mat-form-field")).as(mat()).toAutocomplete();
        List<WebComponent> options = autocomplete.getOptions2();

        assertEquals(3, options.size());
        assertArrayEquals(new String[]{"One", "Two", "Three"}, options.stream().map(WebComponent::getText).toArray());
        autocomplete.closeOptions();

        autocomplete.selectByIndex(0, 100L);
        assertEquals("One", autocomplete.getFirstSelectedOption().getText());
        assertEquals("One", autocomplete.getInput().getDomAttribute("value"));

        autocomplete.deselectAll();
        assertEquals("", autocomplete.getInput().getDomAttribute("value"));
        autocomplete.closeOptions(100L);
    }

    public static void main(String[] args) {
        MatAutocompleteTestCases test = new MatAutocompleteTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testAutocomplete();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
