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

package com.github.grossopa.selenium.examples.mui.v5.surfaces;

import com.github.grossopa.selenium.component.mui.v4.surfaces.MuiAppBar;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for {@link MuiAppBar}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiAppBarTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic features
     *
     * @see <a href="https://mui.com/components/app-bar/#basic-app-bar">
     * https://mui.com/components/app-bar/#basic-app-bar</a>
     */
    public void testBasicAppBar() {
        MuiAppBar appBar = driver.findComponent(By.id("ButtonAppBar.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiAppBar-root")).as(muiV5()).toAppBar();
        assertTrue(appBar.validate());

        assertDoesNotThrow(() -> appBar.findComponent(By2.textContains("News")));
    }

    public static void main(String[] args) {
        MuiAppBarTestCases test = new MuiAppBarTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/app-bar/");

        test.testBasicAppBar();
    }
}
