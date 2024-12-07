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

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiFab;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiFab}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiFabTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basics.
     *
     * @see <a href="https://mui.com/material-ui/react-floating-action-button/#basic-fab">
     * https://mui.com/material-ui/react-floating-action-button/#basic-fab</a>
     */
    public void testBasicFab() {
        List<MuiFab> fabList = driver.findComponent(By.id("FloatingActionButtons.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiFab-root"), c -> c.as(muiV5()).toFab());
        fabList.forEach(fab -> assertTrue(fab.validate()));

        assertEquals(4, fabList.size());
        assertEquals("add", fabList.get(0).getAttribute("aria-label"));
        assertEquals("edit", fabList.get(1).getAttribute("aria-label"));
        assertEquals("navigate", fabList.get(2).getText().toLowerCase());
        assertEquals("like", fabList.get(3).getAttribute("aria-label"));

        assertTrue(fabList.get(0).isEnabled());
        assertTrue(fabList.get(1).isEnabled());
        assertTrue(fabList.get(2).isEnabled());
        assertFalse(fabList.get(3).isEnabled());

        assertDoesNotThrow(() -> fabList.get(0).click());
        assertDoesNotThrow(() -> fabList.get(1).click());
        assertDoesNotThrow(() -> fabList.get(2).click());
    }

    public static void main(String[] args) {
        MuiFabTestCases test = new MuiFabTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-floating-action-button/");

        test.testBasicFab();
    }
}
