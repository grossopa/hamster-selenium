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
import com.github.grossopa.playwright.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.playwright.component.mui.v4.inputs.MuiTextField;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests Material UI components with Playwright
 *
 * @author Jack Yin
 * @since 1.12
 */
@SuppressWarnings("all")
public class MuiShowCase extends AbstractBrowserSupport {

    private static final MuiComponents mui = MuiComponents.mui();

    /**
     * Test MUI Button component
     * Demonstrates finding and interacting with Material UI buttons
     */
    public void testButton() {
        driver.navigate("https://mui.com/material-ui/react-button/", 600_000);
        System.out.println("MUI Button page loaded");
        
        // Find all buttons on the page
        List<WebComponent> buttonComponents = driver.findComponents(".MuiButton-root");
        System.out.println("Found " + buttonComponents.size() + " buttons");
        
        if (!buttonComponents.isEmpty()) {
            // Convert first button to MuiButton
            mui.setContext(buttonComponents.get(0), driver);
            MuiButton button = mui.toButton();
            System.out.println("Button text: " + button.innerText());
            System.out.println("Button enabled: " + button.isEnabled());
            
            // Verify button properties
            assertNotNull(button);
            assertTrue(button.isEnabled());
        }
    }

    /**
     * Test MUI TextField component
     * Demonstrates working with Material UI text fields
     */
    public void testTextField() {
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        System.out.println("MUI TextField page loaded");
        
        // Find text field components
        List<WebComponent> textFieldComponents = driver.findComponents(".MuiTextField-root");
        System.out.println("Found " + textFieldComponents.size() + " text fields");
        
        if (!textFieldComponents.isEmpty()) {
            // Convert to MuiTextField
            mui.setContext(textFieldComponents.get(0), driver);
            MuiTextField textField = mui.toTextField();
            System.out.println("Text field found");
            
            // Find the actual input element inside the TextField wrapper
            List<WebComponent> inputs = textField.findComponents("input");
            if (!inputs.isEmpty()) {
                // Fill the input element directly
                inputs.get(0).fill("Hello MUI with Playwright!");
                System.out.println("Text field filled");
                
                // Verify the value
                String value = inputs.get(0).inputValue();
                assertEquals("Hello MUI with Playwright!", value);
                System.out.println("Text field value: " + value);
            } else {
                System.out.println("Warning: No input element found in TextField");
            }
        }
    }

    /**
     * Test multiple MUI components together
     * Demonstrates working with various MUI components in a real scenario
     */
    public void testMultipleComponents() {
        driver.navigate("https://mui.com/material-ui/getting-started/templates/", 600_000);
        System.out.println("MUI Templates page loaded");
        
        // Find buttons
        List<WebComponent> buttons = driver.findComponents(".MuiButton-root");
        System.out.println("Found " + buttons.size() + " buttons");
        
        // Find text fields
        List<WebComponent> textFields = driver.findComponents(".MuiTextField-root");
        System.out.println("Found " + textFields.size() + " text fields");
        
        // Find input elements
        List<WebComponent> inputs = driver.findComponents("input");
        System.out.println("Found " + inputs.size() + " input elements");
        
        // Interact with first available button
        if (!buttons.isEmpty()) {
            mui.setContext(buttons.get(0), driver);
            MuiButton button = mui.toButton();
            System.out.println("First button: " + button.innerText());
        }
    }

    /**
     * Test MUI Select component
     * Demonstrates working with Material UI select dropdowns
     */
    public void testSelect() {
        driver.navigate("https://mui.com/material-ui/react-select/", 600_000);
        System.out.println("MUI Select page loaded");
        
        // Find select components
        List<WebComponent> selectComponents = driver.findComponents(".MuiSelect-root");
        System.out.println("Found " + selectComponents.size() + " select components");
        
        if (!selectComponents.isEmpty()) {
            mui.setContext(selectComponents.get(0), driver);
            var select = mui.toSelect();
            System.out.println("Select component found");
            
            // Get options
            List<WebComponent> options = select.findComponents("option");
            System.out.println("Found " + options.size() + " options");
            
            if (!options.isEmpty()) {
                System.out.println("First option: " + options.get(0).innerText());
            }
        }
    }

    /**
     * Test MUI Checkbox component
     * Demonstrates working with Material UI checkboxes
     */
    public void testCheckbox() {
        driver.navigate("https://mui.com/material-ui/react-checkbox/", 600_000);
        System.out.println("MUI Checkbox page loaded");
        
        // Find checkbox components
        List<WebComponent> checkboxComponents = driver.findComponents(".MuiCheckbox-root");
        System.out.println("Found " + checkboxComponents.size() + " checkbox components");
        
        if (!checkboxComponents.isEmpty()) {
            mui.setContext(checkboxComponents.get(0), driver);
            var checkbox = mui.toCheckbox();
            System.out.println("Checkbox found");
            System.out.println("Checkbox checked: " + checkbox.isChecked());
        }
    }

    public static void main(String[] args) {
        MuiShowCase test = new MuiShowCase();
        test.setUpDriver();
        
        try {
            System.out.println("=== Testing MUI Button ===");
            test.testButton();
            
            System.out.println("\n=== Testing MUI TextField ===");
            test.testTextField();
            
            System.out.println("\n=== Testing Multiple Components ===");
            test.testMultipleComponents();
            
            System.out.println("\n=== Testing MUI Select ===");
            test.testSelect();
            
            System.out.println("\n=== Testing MUI Checkbox ===");
            test.testCheckbox();
            
            System.out.println("\n=== All tests completed successfully! ===");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Keep browser open for manual inspection
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
