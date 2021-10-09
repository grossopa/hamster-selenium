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

package com.github.grossopa.selenium.examples.mui.v5;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.finder.MuiModalFinder;
import com.github.grossopa.selenium.component.mui.v4.lab.MuiAutocomplete;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.logging.Logger;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.util.SeleniumUtils.cleanText;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for Mui V5 inputs.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MuiV5InputTestCases extends AbstractBrowserSupport {

    private static final Logger log = Logger.getLogger("MuiV5InputTestCases");

    public AutocompleteTests createAutocompleteTests() {
        return new AutocompleteTests();
    }

    public class AutocompleteTests {

        public AutocompleteTests() {
            driver.navigate().to("https://mui.com/components/autocomplete/");
        }

        /**
         * <a href="https://mui.com/components/autocomplete/#combo-box">
         * https://mui.com/components/autocomplete/#combo-box</a>
         */
        public void testComboBox() {
            MuiAutocomplete autocomplete = driver.findComponent(By.id("ComboBox.js")).findComponent(By2.parent())
                    .findComponent(By.className("MuiAutocomplete-root")).as(muiV5()).toAutocomplete();

            autocomplete.getInput().sendKeys("se");

            List<String> options = autocomplete.getOptions2().stream().map(WebElement::getText).collect(toList());

            assertTrue(options.contains("Apocalypse Now"));
            assertTrue(options.contains("Seven Samurai"));
            assertTrue(options.contains("Witness for the Prosecution"));

            // test auto complete search
            autocomplete.selectByVisibleText("Witness for the Prosecution");
            assertEquals("Witness for the Prosecution", autocomplete.getInput().getAttribute("value"));
            cleanText(autocomplete.getInput());

            // test directly select
            autocomplete.selectByValue("Eternal Sunshine of the Spotless Mind");
            assertEquals("Eternal Sunshine of the Spotless Mind", autocomplete.getInput().getAttribute("value"));
            cleanText(autocomplete.getInput());
        }

        /**
         * <a href="https://mui.com/components/autocomplete/#playground">
         * https://mui.com/components/autocomplete/#playground</a>
         */
        public void testPlayground() {
            WebComponent container = driver.findComponent(By.id("Playground.js")).findComponent(By2.parent());

            List<MuiAutocomplete> autocompleteList = container.findComponentsAs(By.className("MuiAutocomplete-root"),
                    c -> c.as(muiV5()).toAutocomplete());

            autocompleteList.forEach(autocomplete -> {
                log.info("Testing MuiAutocomplete" + autocomplete.getInput().getAttribute("value"));

                if (!autocomplete.isEnabled()) {
                    log.info("Skipping disabled autocomplete component");
                    return;
                }

                autocomplete.getInput().sendKeys("se");

                List<String> options = autocomplete.getOptions2().stream().map(WebElement::getText).collect(toList());

                assertTrue(options.contains("Apocalypse Now"));
                assertTrue(options.contains("Seven Samurai"));
                assertTrue(options.contains("Witness for the Prosecution"));

                // test auto complete search
                autocomplete.selectByVisibleText("Witness for the Prosecution");
                assertEquals("Witness for the Prosecution", autocomplete.getInput().getAttribute("value"));
                cleanText(autocomplete.getInput());

                // test directly select
                autocomplete.selectByValue("Eternal Sunshine of the Spotless Mind");
                assertEquals("Eternal Sunshine of the Spotless Mind", autocomplete.getInput().getAttribute("value"));
                cleanText(autocomplete.getInput());
            });
        }

        /**
         * <a href="https://mui.com/components/autocomplete/#country-select">
         * https://mui.com/components/autocomplete/#country-select</a>
         */
        public void testCountry() {
            MuiAutocomplete autocomplete = driver.findComponent(By.id("CountrySelect.js")).findComponent(By2.parent())
                    .findComponent(By.className("MuiAutocomplete-root")).as(muiV5()).toAutocomplete();

            assertEquals(248, autocomplete.getOptions2().size());
            autocomplete.getInput().sendKeys("china");
            assertEquals(2, autocomplete.getOptions2().size());
            assertEquals("China (CN) +86", autocomplete.getOptions2().get(0).getText());
            cleanText(autocomplete.getInput());

            autocomplete.closeOptions();
            autocomplete.getPopupButton().click();

            driver.threadSleep(200L);

            MuiModalFinder finder = new MuiModalFinder(driver, new MuiConfig());
            List<WebComponent> options = driver.findComponent(
                            By.xpath("/html/body/div[contains(@class,'MuiAutocomplete-popper')]"))
                    .findComponents(By.className("MuiAutocomplete-option"));

            assertEquals(248, options.size());
            autocomplete.getPopupButton().click();

            assertNull(finder.findTopVisibleOverlay("Paper"));
        }
    }

    public static void main(String[] args) {
        MuiV5InputTestCases test = new MuiV5InputTestCases();
        test.setUpDriver(EDGE);

        AutocompleteTests autocompleteTests = test.createAutocompleteTests();
        autocompleteTests.testComboBox();
        autocompleteTests.testPlayground();
        autocompleteTests.testCountry();
    }
}
