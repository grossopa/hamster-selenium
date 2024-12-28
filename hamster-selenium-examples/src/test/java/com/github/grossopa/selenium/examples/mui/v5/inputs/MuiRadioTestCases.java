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

package com.github.grossopa.selenium.examples.mui.v5.inputs;

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiRadio;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiRadioGroup;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.axesBuilder;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiRadio}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiRadioTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic radio group feature.
     *
     * @see <a href="https://mui.com/material-ui/react-radio-button/#radio-group">
     * https://mui.com/material-ui/react-radio-button/#radio-group</a>
     */
    public void testRadioGroup() {
        MuiRadioGroup radioGroup = driver.findComponent(By.id("RadioButtonsGroup.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiFormGroup-root")).as(muiV5()).toRadioGroup();
        assertTrue(radioGroup.validate());

        List<MuiRadio> radioList = radioGroup.getRadios();
        radioList.forEach(radio -> assertTrue(radio.validate()));

        assertEquals("Female", radioList.get(0).findComponent(axesBuilder().followingSibling().build()).getText());
        assertEquals("Male", radioList.get(1).findComponent(axesBuilder().followingSibling().build()).getText());
        assertEquals("Other", radioList.get(2).findComponent(axesBuilder().followingSibling().build()).getText());

        assertTrue(radioList.get(0).isSelected());
        assertFalse(radioList.get(1).isSelected());
        assertFalse(radioList.get(2).isSelected());
    }

    /**
     * Tests of the enabled & disabled feature.
     *
     * @see <a href="https://mui.com/material-ui/react-radio-button/#direction">
     * https://mui.com/material-ui/react-radio-button/#direction</a>
     */
    public void testDisabled() {
        MuiRadioGroup radioGroup = driver.findComponent(By.id("RowRadioButtonsGroup.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiFormGroup-root")).as(muiV5()).toRadioGroup();
        assertTrue(radioGroup.validate());

        List<MuiRadio> radioList = radioGroup.getRadios();
        radioList.forEach(radio -> assertTrue(radio.validate()));

        assertTrue(radioList.get(0).isEnabled());
        assertTrue(radioList.get(1).isEnabled());
        assertTrue(radioList.get(2).isEnabled());
        assertFalse(radioList.get(3).isEnabled());
    }

    public static void main(String[] args) {
        MuiRadioTestCases test = new MuiRadioTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-radio-button/");

        test.testRadioGroup();
        test.testDisabled();
    }
}
