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

package com.github.grossopa.selenium.examples.antd;

import com.github.grossopa.selenium.component.antd.general.AntdButton;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.github.grossopa.selenium.component.antd.AntdComponents.antd;
import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for general components.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class AntdGeneralTestCases extends AbstractBrowserSupport {

    public void testButton() {
        driver.navigate().to("https://ant.design/components/button/");

        AntdButton primaryButton = driver.findComponent(By.id("components-button-demo-basic"))
                .findComponent(By2.textExact("Primary Button")).findComponent(By2.parent()).as(antd()).toButton();

        assertTrue(primaryButton.validate());
        assertFalse(primaryButton.isLoading());

        AntdButton loadingButton = driver.findComponent(By.id("components-button-demo-loading"))
                .findComponent(By2.textExact("Loading")).findComponent(By2.parent()).as(antd()).toButton();
        assertTrue(loadingButton.validate());
        assertTrue(loadingButton.isLoading());

        AntdButton disabledButton = driver.findComponent(By.id("components-button-demo-disabled"))
                .findComponent(By2.textExact("Primary(disabled)")).findComponent(By2.parent()).as(antd()).toButton();

        assertTrue(disabledButton.validate());
        assertFalse(disabledButton.isEnabled());
    }

    public static void main(String[] args) {
        AntdGeneralTestCases test = new AntdGeneralTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testButton();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            test.stopDriver();
        }
    }
}
