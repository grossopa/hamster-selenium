/*
 * Copyright © 2023 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.playwright.examples.mui;

import com.github.grossopa.playwright.component.mui.MuiComponents;
import com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiChip;
import com.github.grossopa.playwright.component.mui.v4.feedback.MuiDialog;
import com.github.grossopa.playwright.component.mui.v4.inputs.*;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiTabs;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Advanced tests for Material UI components with Playwright
 * Demonstrates more complex interactions and component combinations
 *
 * @author Jack Yin
 * @since 1.12
 */
@SuppressWarnings("all")
public class MuiAdvancedShowCase extends AbstractBrowserSupport {

    private static final MuiComponents mui = MuiComponents.mui();

    /**
     * Test MUI Button variants and states
     */
    public void testButtonVariants() {
        driver.navigate("https://mui.com/material-ui/react-button/", 600_000);
        System.out.println("Testing MUI Button Variants");
        
        // Find different button types
        List<WebComponent> containedButtons = driver.findComponents(".MuiButton-contained");
        List<WebComponent> outlinedButtons = driver.findComponents(".MuiButton-outlined");
        List<WebComponent> textButtons = driver.findComponents(".MuiButton-text");
        
        System.out.println("Found " + containedButtons.size() + " contained buttons");
        System.out.println("Found " + outlinedButtons.size() + " outlined buttons");
        System.out.println("Found " + textButtons.size() + " text buttons");
        
        // Test a contained button
        if (!containedButtons.isEmpty()) {
            mui.setContext(containedButtons.get(0), driver);
            MuiButton button = mui.toButton();
            assertTrue(button.isEnabled());
            System.out.println("Contained button: " + button.innerText());
        }
    }

    /**
     * Test MUI TextField with different states
     */
    public void testTextFieldStates() {
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        System.out.println("Testing MUI TextField States");
        
        // Find standard text fields
        List<WebComponent> standardFields = driver.findComponents(".MuiTextField-standard");
        List<WebComponent> filledFields = driver.findComponents(".MuiTextField-filled");
        List<WebComponent> outlinedFields = driver.findComponents(".MuiTextField-outlined");
        
        System.out.println("Found " + standardFields.size() + " standard text fields");
        System.out.println("Found " + filledFields.size() + " filled text fields");
        System.out.println("Found " + outlinedFields.size() + " outlined text fields");
        
        // Test filling an outlined text field
        if (!outlinedFields.isEmpty()) {
            mui.setContext(outlinedFields.get(0), driver);
            MuiTextField textField = mui.toTextField();
            textField.fill("Test input value");
            assertEquals("Test input value", textField.inputValue());
            System.out.println("Successfully filled outlined text field");
        }
    }

    /**
     * Test MUI Checkbox and Radio components
     */
    public void testCheckboxAndRadio() {
        driver.navigate("https://mui.com/material-ui/react-checkbox/", 600_000);
        System.out.println("Testing MUI Checkbox and Radio");
        
        // Find checkboxes
        List<WebComponent> checkboxes = driver.findComponents(".MuiCheckbox-root");
        System.out.println("Found " + checkboxes.size() + " checkboxes");
        
        if (!checkboxes.isEmpty()) {
            mui.setContext(checkboxes.get(0), driver);
            MuiCheckbox checkbox = mui.toCheckbox();
            boolean initialState = checkbox.isChecked();
            System.out.println("Initial checkbox state: " + initialState);
            
            // Toggle checkbox
            checkbox.click();
            boolean newState = checkbox.isChecked();
            System.out.println("New checkbox state: " + newState);
            assertNotEquals(initialState, newState);
        }
        
        // Navigate to radio page
        driver.navigate("https://mui.com/material-ui/react-radio/", 600_000);
        List<WebComponent> radios = driver.findComponents(".MuiRadio-root");
        System.out.println("Found " + radios.size() + " radio buttons");
        
        if (!radios.isEmpty()) {
            mui.setContext(radios.get(0), driver);
            MuiRadio radio = mui.toRadio();
            System.out.println("Radio checked: " + radio.isChecked());
        }
    }

    /**
     * Test MUI Switch component
     */
    public void testSwitch() {
        driver.navigate("https://mui.com/material-ui/react-switch/", 600_000);
        System.out.println("Testing MUI Switch");
        
        List<WebComponent> switches = driver.findComponents(".MuiSwitch-root");
        System.out.println("Found " + switches.size() + " switches");
        
        if (!switches.isEmpty()) {
            mui.setContext(switches.get(0), driver);
            MuiSwitch switchComponent = mui.toSwitch();
            boolean initialState = switchComponent.isChecked();
            System.out.println("Initial switch state: " + initialState);
            
            // Toggle switch
            switchComponent.click();
            boolean newState = switchComponent.isChecked();
            System.out.println("New switch state: " + newState);
        }
    }

    /**
     * Test MUI Slider component
     */
    public void testSlider() {
        driver.navigate("https://mui.com/material-ui/react-slider/", 600_000);
        System.out.println("Testing MUI Slider");
        
        List<WebComponent> sliders = driver.findComponents(".MuiSlider-root");
        System.out.println("Found " + sliders.size() + " sliders");
        
        if (!sliders.isEmpty()) {
            mui.setContext(sliders.get(0), driver);
            MuiSlider slider = mui.toSlider();
            System.out.println("Slider found and accessible");
            
            // Get current value (if available)
            try {
                String ariaValue = slider.getAttribute("aria-valuenow");
                if (ariaValue != null) {
                    System.out.println("Slider value: " + ariaValue);
                }
            } catch (Exception e) {
                System.out.println("Could not get slider value: " + e.getMessage());
            }
        }
    }

    /**
     * Test MUI Chip component
     */
    public void testChip() {
        driver.navigate("https://mui.com/material-ui/react-chip/", 600_000);
        System.out.println("Testing MUI Chip");
        
        List<WebComponent> chips = driver.findComponents(".MuiChip-root");
        System.out.println("Found " + chips.size() + " chips");
        
        if (!chips.isEmpty()) {
            mui.setContext(chips.get(0), driver);
            MuiChip chip = mui.toChip();
            System.out.println("Chip label: " + chip.innerText());
            
            // Check if chip has delete action
            List<WebComponent> deleteIcons = chip.findComponents(".MuiChip-deleteIcon");
            System.out.println("Chip has delete icon: " + !deleteIcons.isEmpty());
        }
    }

    /**
     * Test MUI Tabs component
     */
    public void testTabs() {
        driver.navigate("https://mui.com/material-ui/react-tabs/", 600_000);
        System.out.println("Testing MUI Tabs");
        
        List<WebComponent> tabContainers = driver.findComponents(".MuiTabs-root");
        System.out.println("Found " + tabContainers.size() + " tab containers");
        
        if (!tabContainers.isEmpty()) {
            mui.setContext(tabContainers.get(0), driver);
            MuiTabs tabs = mui.toTabs();
            System.out.println("Tabs container found");
            
            // Get individual tabs
            List<WebComponent> tabList = tabs.findComponents(".MuiTab-root");
            System.out.println("Found " + tabList.size() + " tabs");
            
            if (!tabList.isEmpty()) {
                System.out.println("First tab: " + tabList.get(0).innerText());
            }
        }
    }

    /**
     * Test MUI Dialog component
     */
    public void testDialog() {
        driver.navigate("https://mui.com/material-ui/react-dialog/", 600_000);
        System.out.println("Testing MUI Dialog");
        
        // Find dialog trigger buttons
        List<WebComponent> openButtons = driver.findComponents("button");
        System.out.println("Found " + openButtons.size() + " buttons");
        
        // Look for dialog elements
        List<WebComponent> dialogs = driver.findComponents(".MuiDialog-root");
        System.out.println("Found " + dialogs.size() + " dialog containers");
        
        if (!dialogs.isEmpty()) {
            mui.setContext(dialogs.get(0), driver);
            MuiDialog dialog = mui.toDialog();
            System.out.println("Dialog component found");
        }
    }

    /**
     * Test MUI Rating component
     */
    public void testRating() {
        driver.navigate("https://mui.com/material-ui/react-rating/", 600_000);
        System.out.println("Testing MUI Rating");
        
        List<WebComponent> ratings = driver.findComponents(".MuiRating-root");
        System.out.println("Found " + ratings.size() + " rating components");
        
        if (!ratings.isEmpty()) {
            mui.setContext(ratings.get(0), driver);
            var rating = mui.toRating();
            System.out.println("Rating component found");
            
            // Get current rating value
            try {
                String value = rating.getAttribute("aria-valuenow");
                if (value != null) {
                    System.out.println("Current rating: " + value);
                }
            } catch (Exception e) {
                System.out.println("Could not get rating value");
            }
        }
    }

    /**
     * Test MUI Autocomplete component
     */
    public void testAutocomplete() {
        driver.navigate("https://mui.com/material-ui/react-autocomplete/", 600_000);
        System.out.println("Testing MUI Autocomplete");
        
        List<WebComponent> autocompleteComponents = driver.findComponents(".MuiAutocomplete-root");
        System.out.println("Found " + autocompleteComponents.size() + " autocomplete components");
        
        if (!autocompleteComponents.isEmpty()) {
            mui.setContext(autocompleteComponents.get(0), driver);
            var autocomplete = mui.toAutocomplete();
            System.out.println("Autocomplete component found");
        }
    }

    public static void main(String[] args) {
        MuiAdvancedShowCase test = new MuiAdvancedShowCase();
        test.setUpDriver();

        test.runTestClass("MuiAdvancedShowCase", () -> {
            test.runTest("testButtonVariants", test::testButtonVariants);
            test.runTest("testTextFieldStates", test::testTextFieldStates);
            test.runTest("testCheckboxAndRadio", test::testCheckboxAndRadio);
            test.runTest("testSwitch", test::testSwitch);
            test.runTest("testSlider", test::testSlider);
            test.runTest("testChip", test::testChip);
            test.runTest("testTabs", test::testTabs);
            test.runTest("testDialog", test::testDialog);
            test.runTest("testRating", test::testRating);
            test.runTest("testAutocomplete", test::testAutocomplete);
        });

        // Keep browser open for manual inspection
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
