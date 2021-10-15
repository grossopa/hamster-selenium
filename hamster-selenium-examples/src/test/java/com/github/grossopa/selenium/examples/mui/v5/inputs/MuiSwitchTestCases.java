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

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiSwitch;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.axesBuilder;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiSwitch}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiSwitchTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic Switches.
     *
     * @see <a href="https://mui.com/components/switches/#basic-switches">
     * https://mui.com/components/switches/#basic-switches</a>
     */
    public void testBasicSwitches() {
        List<MuiSwitch> switchList = driver.findComponent(By.id("BasicSwitches.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiSwitch-root"), c -> c.as(muiV5()).toSwitch());
        assertEquals(4, switchList.size());
        switchList.forEach(s -> assertTrue(s.validate()));

        assertTrue(switchList.get(0).isEnabled());
        assertTrue(switchList.get(1).isEnabled());
        assertFalse(switchList.get(2).isEnabled());
        assertFalse(switchList.get(3).isEnabled());

        assertTrue(switchList.get(0).isSelected());
        assertFalse(switchList.get(1).isSelected());
        assertTrue(switchList.get(2).isSelected());
        assertFalse(switchList.get(3).isSelected());

        switchList.get(0).click();
        assertFalse(switchList.get(0).isSelected());
    }

    /**
     * Tests the labels.
     *
     * @see <a href="https://mui.com/components/switches/#label">
     * https://mui.com/components/switches/#label</a>
     */
    public void testLabel() {
        List<MuiSwitch> switchList = driver.findComponent(By.id("SwitchLabels.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiSwitch-root"), c -> c.as(muiV5()).toSwitch());
        assertEquals(2, switchList.size());
        switchList.forEach(s -> assertTrue(s.validate()));

        assertEquals("Label", switchList.get(0).findComponent(axesBuilder().followingSibling().build()).getText());
        assertEquals("Disabled", switchList.get(1).findComponent(axesBuilder().followingSibling().build()).getText());
    }

    public static void main(String[] args) {
        MuiSwitchTestCases test = new MuiSwitchTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/switches/");

        test.testBasicSwitches();
        test.testLabel();
    }
}
