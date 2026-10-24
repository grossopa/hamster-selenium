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
package com.github.grossopa.selenium.examples.mui.v5.inputs;

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiNumberField;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiNumberField}.
 *
 * <p>The NumberField is a component recipe composed from Base UI's NumberField primitive styled with MUI building
 * blocks (FormControl, OutlinedInput, IconButton).</p>
 *
 * @author Jack Yin
 * @since 1.18
 * @see <a href="https://mui.com/material-ui/react-number-field/">
 *     https://mui.com/material-ui/react-number-field/</a>
 */
public class MuiNumberFieldTestCases extends AbstractBrowserSupport {

    /**
     * Tests the outlined number field. Verifies that the component can be located and its value can be read and
     * modified.
     *
     * @see <a href="https://mui.com/material-ui/react-number-field/#outlined-field">
     * https://mui.com/material-ui/react-number-field/#outlined-field</a>
     */
    public void testOutlinedField() {
        MuiNumberField nf = driver.findComponent(By.id("FieldDemo.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiFormControl-root")).as(mui()).toNumberField();
        assertTrue(nf.validate());

        // verify not in error state
        assertFalse(nf.isError());

        // set and verify value
        nf.setValue("25");
        assertEquals("25", nf.getValue());
    }

    /**
     * Tests the outlined number field variants. Verifies that multiple NumberField components can be located within
     * the demo section.
     *
     * @see <a href="https://mui.com/material-ui/react-number-field/#outlined-field">
     * https://mui.com/material-ui/react-number-field/#outlined-field</a>
     */
    public void testOutlinedFieldVariants() {
        // the demo contains multiple FormControl-based NumberField variants
        var formControls = driver.findComponent(By.id("FieldDemo.js")).findComponent(By2.parent())
                .findComponents(By.className("MuiFormControl-root"));
        assertTrue(formControls.size() >= 2, "Demo should contain at least 2 NumberField variants");

        // verify the first NumberField can be wrapped and validated
        MuiNumberField nf = formControls.get(0).as(mui()).toNumberField();
        assertTrue(nf.validate());
        assertFalse(nf.isError());
    }

    /**
     * The main function for running the test cases.
     *
     * @param args not necessary
     */
    public static void main(String[] args) {
        MuiNumberFieldTestCases test = new MuiNumberFieldTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-number-field/");

        test.testOutlinedField();
        test.testOutlinedFieldVariants();
    }
}
