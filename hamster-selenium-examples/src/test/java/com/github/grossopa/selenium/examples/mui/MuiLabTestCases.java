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

package com.github.grossopa.selenium.examples.mui;

import com.github.grossopa.selenium.component.mui.lab.MuiAutocomplete;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for lab components
 *
 * @author Jack Yin
 * @since 1.3
 */
public class MuiLabTestCases extends AbstractBrowserSupport {

    public void testAutocompleteComboBox() {
        driver.navigate().to("https://material-ui.com/components/autocomplete/");

        MuiAutocomplete autoComplete = driver.findComponent(By.id("combo-box-demo-label")).findComponent(By2.parent())
                .findComponent(By2.parent()).as(mui()).toAutocomplete();

        assertTrue(autoComplete.validate());

        assertEquals("", autoComplete.getInput().getAttribute("value"));
        autoComplete.selectByIndex(0);
        driver.threadSleep(200L);
        assertEquals("The Shawshank Redemption", autoComplete.getInput().getAttribute("value"));
        autoComplete.closeOptions();

        autoComplete.getClearButton().click();
        assertEquals("", autoComplete.getInput().getAttribute("value"));

        autoComplete.getInput().sendKeys("Godfat");
        assertEquals("The Godfather", autoComplete.getOptions2().get(0).getText());
        autoComplete.selectByValue("The Godfather");
        autoComplete.closeOptions();

        autoComplete.getClearButton().click();
        autoComplete.selectByVisibleText("3 Idiots");
        assertEquals("3 Idiots", autoComplete.getInput().getAttribute("value"));
    }


    public static void main(String[] args) {
        MuiLabTestCases test = new MuiLabTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testAutocompleteComboBox();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            test.stopDriver();
        }
    }
}
