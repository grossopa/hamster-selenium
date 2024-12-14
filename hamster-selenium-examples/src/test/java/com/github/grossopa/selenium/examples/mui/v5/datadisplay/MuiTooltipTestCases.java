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

package com.github.grossopa.selenium.examples.mui.v5.datadisplay;

import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiTooltip;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for {@link MuiTooltip}.
 *
 * @author Jack Yin
 * @since 1.12.0
 */
public class MuiTooltipTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic lists.
     *
     * @see <a href="https://mui.com/material-ui/react-divider/#basic-list">
     * https://mui.com/material-ui/react-divider/#basic-list</a>
     */
    public void testBasicList() {
        List<MuiButton> buttons = driver.findComponent(By.id("BasicTooltip.js")).findComponent(By2.parent())
                .findComponentsAs(By2.attrExact("aria-label", "Delete"), c -> c.as(muiV5()).toButton());
        assertEquals(1, buttons.size());

        driver.moveTo(buttons.get(0));
        driver.threadSleep(1000L);

        MuiTooltip tooltip = driver.findComponent(By2.attrExact("role", "tooltip")).as(muiV5()).toTooltip();

        assertTrue(tooltip.validate());
        assertEquals("Delete", tooltip.getText());
    }

    public static void main(String[] args) {
        MuiTooltipTestCases test = new MuiTooltipTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-tooltip/");

        test.testBasicList();
    }
}