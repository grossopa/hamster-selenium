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

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiButton}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiButtonTestCases extends AbstractBrowserSupport {

    /**
     * Test the basic features
     *
     * @see <a href="https://mui.com/components/buttons/#basic-button">
     * https://mui.com/components/buttons/#basic-button</a>
     */
    public void testBasicButtons() {
        List<MuiButton> buttonList = driver.findComponent(By.id("BasicButtons.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiButton-root"), c -> c.as(muiV5()).toButton());

        assertEquals(3, buttonList.size());
        assertEquals("text", lowerCase(buttonList.get(0).getText()));
        assertEquals("contained", lowerCase(buttonList.get(1).getText()));
        assertEquals("outlined", lowerCase(buttonList.get(2).getText()));
    }

    /**
     * Test the text button feature
     *
     * @see <a href="https://mui.com/components/buttons/#text-button">
     * https://mui.com/components/buttons/#text-button</a>
     */
    public void testTextButtons() {
        List<MuiButton> buttonList = driver.findComponent(By.id("TextButtons.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiButton-root"), c -> c.as(muiV5()).toButton());

        assertEquals(3, buttonList.size());
        assertEquals("primary", lowerCase(buttonList.get(0).getText()));
        assertEquals("disabled", lowerCase(buttonList.get(1).getText()));
        assertEquals("link", lowerCase(buttonList.get(2).getText()));

        assertTrue(buttonList.get(0).isEnabled());
        assertFalse(buttonList.get(1).isEnabled());
        assertTrue(buttonList.get(2).isEnabled());
    }

    public static void main(String[] args) {
        MuiButtonTestCases test = new MuiButtonTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/buttons/");

        test.testTextButtons();
    }


}
