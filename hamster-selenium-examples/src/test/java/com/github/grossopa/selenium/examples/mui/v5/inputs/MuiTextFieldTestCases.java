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

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiTextField;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.util.SeleniumUtils.cleanText;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiTextField}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiTextFieldTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basics.
     *
     * @see <a href="https://mui.com/components/text-fields/#basic-textfield">
     * https://mui.com/components/text-fields/#basic-textfield</a>
     */
    public void testBasicTextField() {
        List<MuiTextField> textFieldList = driver.findComponent(By.id("BasicTextFields.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiTextField-root"), c -> c.as(muiV5()).toTextField());
        textFieldList.forEach(textField -> assertTrue(textField.validate()));
        assertEquals(3, textFieldList.size());

        String[] labels = new String[]{"Outlined", "Filled", "Standard"};
        for (int i = 0; i < textFieldList.size(); i++) {
            MuiTextField textField = textFieldList.get(i);
            assertEquals(labels[i], textField.getLabel().getText());
            textField.sendText("abc");
            assertEquals(labels[i], textField.getLabel().getText());
            assertEquals("abc", textField.getInput().getAttribute("value"));
            cleanText(textField.getInput());
        }
    }

    /**
     * Tests the form properties
     *
     * @see <a href="https://mui.com/components/text-fields/#form-props">
     * https://mui.com/components/text-fields/#form-props</a>
     */
    public void testFormProps() {
        List<MuiTextField> textFieldList = driver.findComponent(By.id("FormPropsTextFields.js"))
                .findComponent(By2.parent())
                .findComponentsAs(By.className("MuiTextField-root"), c -> c.as(muiV5()).toTextField());
        textFieldList.forEach(textField -> assertTrue(textField.validate()));

        MuiTextField required = textFieldList.get(0);
        assertTrue(required.getLabel().getText().startsWith("Required"));

        MuiTextField disabled = textFieldList.get(1);
        assertFalse(disabled.isEnabled());

        MuiTextField number = textFieldList.get(4);
        number.sendText("aa");
        assertEquals("", number.getInput().getAttribute("value"));
        number.sendText("12345");
        assertEquals("12345", number.getInput().getAttribute("value"));
    }

    public static void main(String[] args) {
        MuiTextFieldTestCases test = new MuiTextFieldTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/text-fields/");

        test.testBasicTextField();
        test.testFormProps();
    }
}
