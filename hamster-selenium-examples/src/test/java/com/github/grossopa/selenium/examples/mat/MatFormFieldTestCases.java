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

import com.github.grossopa.hamster.selenium.component.mat.main.MatFormField;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the actual features of {@link MatFormField}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatFormFieldTestCases extends AbstractBrowserSupport {

    public void navigate() {
        driver.navigate().to("https://material.angular.io/components/form-field/examples");
    }

    public void testAppearance() {
        List<MatFormField> appearanceFormFields = driver.findComponent(By.id("form-field-appearance"))
                .findComponentsAs(By.tagName("mat-form-field"), c -> c.as(mat()).toFormField());

        assertEquals(4, appearanceFormFields.size());
        assertTrue(appearanceFormFields.stream().allMatch(MatFormField::validate));
        assertTrue(appearanceFormFields.stream().allMatch(MatFormField::isEnabled));

        assertEquals("Hint", appearanceFormFields.get(0).getHint().getText());

        assertDoesNotThrow(() -> appearanceFormFields.get(1).getSuffix()
                .findComponent(xpathBuilder().relative("mat-icon").build()));
    }

    public void testError() {
        MatFormField errorFormField = driver.findComponent(By.id("form-field-error"))
                .findComponent(By.tagName("form-field-error-example")).findComponent(By.tagName("mat-form-field"))
                .as(mat()).toFormField();

        errorFormField.getInput().sendKeys("ddddd");
        // send Tab key to make the input to lose focus, and trigger the error check
        errorFormField.getInput().sendKeys(Keys.TAB);
        assertEquals("Enter your email", errorFormField.getLabel().getText());
        assertEquals("Not a valid email", errorFormField.getError().getText());
    }

    public void testHints() {
        List<MatFormField> hintsFormFields = driver.findComponent(By.id("form-field-hint"))
                .findComponentsAs(By.tagName("mat-form-field"), c -> c.as(mat()).toFormField());

        assertEquals("Max 10 characters", hintsFormFields.get(0).getHint().getText());
        assertEquals("Here's the dropdown arrow ^", hintsFormFields.get(1).getHint().getText());
    }

    public void testPrefixSuffix() {
        List<MatFormField> prefixSuffixFormFields = driver.findComponent(By.id("form-field-prefix-suffix"))
                .findComponentsAs(By.tagName("mat-form-field"), c -> c.as(mat()).toFormField());

        assertEquals("$", prefixSuffixFormFields.get(1).getPrefix().getText().trim());
        assertEquals(".00", prefixSuffixFormFields.get(1).getSuffix().getText());
    }

    public static void main(String[] args) {
        MatFormFieldTestCases test = new MatFormFieldTestCases();
        try {
            test.setUpDriver(EDGE);
            test.navigate();
            test.testAppearance();
            test.testError();
            test.testHints();
            test.testPrefixSuffix();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
