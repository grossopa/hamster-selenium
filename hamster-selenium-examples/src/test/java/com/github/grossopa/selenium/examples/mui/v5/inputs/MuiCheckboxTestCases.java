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

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiCheckbox;
import com.github.grossopa.selenium.component.mui.v5.inputs.MuiCheckboxV5;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.axesBuilder;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiCheckbox}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiCheckboxTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basics.
     *
     * @see <a href="https://mui.com/material-ui/react-checkbox/#basic-checkboxes">
     * https://mui.com/material-ui/react-checkbox/#basic-checkboxes</a>
     */
    public void testBasicCheckboxes() {
        List<MuiCheckbox> checkboxList = driver.findComponent(By.id("Checkboxes.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiCheckbox-root"), c -> c.as(muiV5()).toCheckbox());
        checkboxList.forEach(checkbox -> assertTrue(checkbox.validate()));

        assertEquals(4, checkboxList.size());
        assertTrue(checkboxList.get(0).isSelected());
        assertFalse(checkboxList.get(1).isSelected());
        assertFalse(checkboxList.get(2).isSelected());
        assertTrue(checkboxList.get(3).isSelected());

        assertTrue(checkboxList.get(0).isEnabled());
        assertTrue(checkboxList.get(1).isEnabled());
        assertFalse(checkboxList.get(2).isEnabled());
        assertFalse(checkboxList.get(3).isEnabled());
    }

    /**
     * Tests the label of the checkbox
     *
     * @see <a href="https://mui.com/material-ui/react-checkbox/#label">
     * https://mui.com/material-ui/react-checkbox/#label</a>
     */
    public void testLabel() {
        List<MuiCheckbox> checkboxList = driver.findComponent(By.id("CheckboxLabels.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiCheckbox-root"), c -> c.as(muiV5()).toCheckbox());
        checkboxList.forEach(checkbox -> assertTrue(checkbox.validate()));

        assertEquals(3, checkboxList.size());
        assertTrue(checkboxList.get(0).isSelected());
        assertFalse(checkboxList.get(1).isSelected());
        assertFalse(checkboxList.get(2).isSelected());

        assertTrue(checkboxList.get(0).isEnabled());
        assertTrue(checkboxList.get(1).isEnabled());
        assertFalse(checkboxList.get(2).isEnabled());

        assertEquals("Label", checkboxList.get(0).findComponent(axesBuilder().followingSibling().build()).getText());
        assertEquals("Required *", checkboxList.get(1).findComponent(axesBuilder().followingSibling().build()).getText());
        assertEquals("Disabled", checkboxList.get(2).findComponent(axesBuilder().followingSibling().build()).getText());
    }

    /**
     * Tests the Indeterminate feature
     *
     * @see <a href="https://mui.com/material-ui/react-checkbox/#indeterminate">
     * https://mui.com/material-ui/react-checkbox/#indeterminate</a>
     */
    public void testIndeterminateCheckbox() {
        List<MuiCheckbox> checkboxList = driver.findComponent(By.id("IndeterminateCheckbox.js"))
                .findComponent(By2.parent())
                .findComponentsAs(By.className("MuiCheckbox-root"), c -> c.as(muiV5()).toCheckbox());
        checkboxList.forEach(checkbox -> assertTrue(checkbox.validate()));

        assertEquals(3, checkboxList.size());
        // Indeterminate is not considered as checked
        assertFalse(checkboxList.get(0).isSelected());
        assertTrue(((MuiCheckboxV5) checkboxList.get(0)).isIndeterminate());
        assertTrue(checkboxList.get(1).isSelected());
        assertFalse(checkboxList.get(2).isSelected());

        checkboxList.get(2).click();
        assertTrue(checkboxList.get(0).isSelected());
        assertFalse(((MuiCheckboxV5) checkboxList.get(0)).isIndeterminate());
        assertTrue(checkboxList.get(1).isSelected());
        assertTrue(checkboxList.get(2).isSelected());

        checkboxList.get(0).click();
        assertFalse(checkboxList.get(0).isSelected());
        assertFalse(((MuiCheckboxV5) checkboxList.get(0)).isIndeterminate());
        assertFalse(checkboxList.get(1).isSelected());
        assertFalse(checkboxList.get(2).isSelected());
    }

    public static void main(String[] args) {
        MuiCheckboxTestCases test = new MuiCheckboxTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-checkbox/");

        test.testBasicCheckboxes();
        test.testLabel();
        test.testIndeterminateCheckbox();
    }
}
