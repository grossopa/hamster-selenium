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

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.finder.MuiModalFinder;
import com.github.grossopa.selenium.component.mui.v4.lab.MuiAutocomplete;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.logging.Logger;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.util.SeleniumUtils.cleanText;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link com.github.grossopa.selenium.component.mui.v4.lab.MuiAutocomplete}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiAutocompleteTestCases extends AbstractBrowserSupport {

    private static final Logger log = Logger.getLogger("MuiAutocompleteTests");

    /**
     * Simple combobox testing
     *
     * @see <a href="https://mui.com/components/autocomplete/#combo-box">
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
     * With multiple components with different options - but their behaviours is expected to be consistent
     *
     * @see <a href="https://mui.com/components/autocomplete/#playground">
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
     * A country list
     *
     * @see <a href="https://mui.com/components/autocomplete/#country-select">
     * https://mui.com/components/autocomplete/#country-select</a>
     */
    public void testCountry() {
        MuiAutocomplete autocomplete = driver.findComponent(By.id("CountrySelect.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiAutocomplete-root")).as(muiV5()).toAutocomplete();

        assertTrue(autocomplete.getOptions2().size() >= 100);
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

        assertTrue(options.size() >= 100);
        autocomplete.getPopupButton().click();

        assertNull(finder.findTopVisibleOverlay("Paper"));
    }

    /**
     * Controlled states testing
     *
     * @see <a href="https://mui.com/components/autocomplete/#controlled-states">
     * https://mui.com/components/autocomplete/#controlled-states</a>
     */
    public void testControlledStates() {
        MuiAutocomplete autocomplete = driver.findComponent(By.id("ControllableStates.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiAutocomplete-root")).as(muiV5()).toAutocomplete();

        autocomplete.getInput().sendKeys("ffffdddd");
        // The "no options" selection should not be caught by #getOptions2()
        assertEquals(0, autocomplete.getOptions2().size());
        // but it should be present
        assertDoesNotThrow(() -> driver.findComponent(
                By.xpath("/html/body/div[contains(@class,'MuiAutocomplete-popper')]/div/div[text()='No options']")));

        assertTrue(autocomplete.isNoOptions());
        cleanText(autocomplete.getInput());
        assertFalse(autocomplete.isNoOptions());
        assertEquals(2, autocomplete.getOptions2().size());
    }

    /**
     * free solo will not display the "No Options" for none matches
     *
     * @see <a href="https://mui.com/components/autocomplete/#free-solo">
     * https://mui.com/components/autocomplete/#free-solo</a>
     */
    @SuppressWarnings("all")
    public void testFreeSolo() {
        MuiAutocomplete autocomplete = driver.findComponent(By.id("FreeSolo.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiAutocomplete-root")).as(muiV5()).toAutocomplete();

        autocomplete.getInput().sendKeys("abcdefg");
        assertThrows(NoSuchElementException.class, autocomplete::getOptions2);
        assertThrows(NoSuchElementException.class, () -> driver.findComponent(
                By.xpath("/html/body/div[contains(@class,'MuiAutocomplete-popper')]/div/div[text()='No options']")));
        autocomplete.getClearButton().click();
        assertEquals("", autocomplete.getInput().getAttribute("value"));
        autocomplete.closeOptions();
    }

    /**
     * option 0 is disabled and 1 is enabled.
     *
     * @see <a href="https://mui.com/components/autocomplete/#disabled-options">
     * https://mui.com/components/autocomplete/#disabled-options</a>
     */
    public void testDisabledOptions() {
        MuiAutocomplete autocomplete = driver.findComponent(By.id("DisabledOptions.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiAutocomplete-root")).as(muiV5()).toAutocomplete();

        List<WebComponent> components = autocomplete.getOptions2();
        // tricky - the option does have aria-disabled as true but nothing else
        assertEquals("true", components.get(0).getAttribute("aria-disabled"));
        assertEquals("false", components.get(1).getAttribute("aria-disabled"));
        autocomplete.closeOptions();
    }

    /**
     * Multiple values testing
     *
     * @see <a href="https://mui.com/components/autocomplete/#multiple-values">
     * https://mui.com/components/autocomplete/#multiple-values</a>
     */
    public void testMultipleValues() {
        MuiAutocomplete autocomplete = driver.findComponent(By.id("Tags.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiAutocomplete-root")).as(muiV5()).toAutocomplete();

        assertEquals(1, autocomplete.getAllSelectedOptions2().size());
        autocomplete.deselectByValue("Inception");

        autocomplete.selectByVisibleText("The Dark Knight");
        assertEquals(1, autocomplete.getAllSelectedOptions2().size());
        assertEquals("The Dark Knight", autocomplete.getAllSelectedOptions2().get(0).getText());

        autocomplete.selectByVisibleText("Star Wars: Episode V - The Empire Strikes Back");
        assertEquals(2, autocomplete.getAllSelectedOptions2().size());

        autocomplete.deselectByValue("The Dark Knight");
        assertEquals(1, autocomplete.getAllSelectedOptions2().size());
        assertEquals("Star Wars: Episode V - The Empire Strikes Back",
                autocomplete.getAllSelectedOptions2().get(0).getText());

        autocomplete.selectByVisibleText("Raiders of the Lost Ark");
        assertEquals(2, autocomplete.getAllSelectedOptions2().size());
        assertEquals("Star Wars: Episode V - The Empire Strikes Back",
                autocomplete.getAllSelectedOptions2().get(0).getText());
        assertEquals("Raiders of the Lost Ark", autocomplete.getAllSelectedOptions2().get(1).getText());

        autocomplete.deselectAll();
        assertEquals(0, autocomplete.getAllSelectedOptions2().size());
    }


    /**
     * Fixed options testing with first one is disabled
     *
     * @see <a href="https://mui.com/components/autocomplete/#fixed-options">
     * https://mui.com/components/autocomplete/#fixed-options</a>
     */
    public void testFixedOptions() {
        MuiAutocomplete autocomplete = driver.findComponent(By.id("FixedTags.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiAutocomplete-root")).as(muiV5()).toAutocomplete();

        assertFalse(autocomplete.getAllSelectedOptions2().get(0).isEnabled());
        assertEquals(2, autocomplete.getAllSelectedOptions2().size());

        // deselectAll should not be impacted by disabled one
        autocomplete.deselectAll();
        assertEquals(1, autocomplete.getAllSelectedOptions2().size());
    }

    public static void main(String[] args) {
        MuiAutocompleteTestCases test = new MuiAutocompleteTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/autocomplete/");

        test.testComboBox();
        test.testPlayground();
        test.testCountry();
        test.testControlledStates();
        test.testFreeSolo();
        test.testDisabledOptions();
        test.testMultipleValues();
        test.testFixedOptions();
    }

}
