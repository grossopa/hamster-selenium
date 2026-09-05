/*
 * Copyright © 2021 the original author or authors.
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
import com.github.grossopa.playwright.component.mui.v4.inputs.*;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstrates testing a complete form scenario with MUI components
 * This example simulates filling out a registration/login form
 *
 * @author Jack Yin
 * @since 1.12
 */
@SuppressWarnings("all")
public class MuiFormExample extends AbstractBrowserSupport {

    private static final MuiComponents mui = MuiComponents.mui();

    /**
     * Test a login form scenario
     * Demonstrates filling username, password, and clicking login button
     */
    public void testLoginForm() {
        System.out.println("=== Testing Login Form Scenario ===");
        
        // Navigate to a page with a login form
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        System.out.println("Page loaded");
        
        // Find text fields for username and password
        List<WebComponent> textFields = driver.findComponents(".MuiTextField-root");
        System.out.println("Found " + textFields.size() + " text fields");
        
        if (textFields.size() >= 2) {
            // Fill username field
            mui.setContext(textFields.get(0), driver);
            MuiTextField usernameField = mui.toTextField();
            usernameField.fill("testuser@example.com");
            System.out.println("Username filled: " + usernameField.inputValue());
            
            // Fill password field
            mui.setContext(textFields.get(1), driver);
            MuiTextField passwordField = mui.toTextField();
            passwordField.fill("password123");
            System.out.println("Password filled: " + (passwordField.inputValue().isEmpty() ? "[HIDDEN]" : "[SET]"));
        }
        
        // Find and click login button
        List<WebComponent> buttons = driver.findComponents(".MuiButton-contained");
        if (!buttons.isEmpty()) {
            mui.setContext(buttons.get(0), driver);
            MuiButton loginButton = mui.toButton();
            System.out.println("Login button found: " + loginButton.innerText());
            
            // In a real scenario, you would click the button
            // loginButton.click();
        }
    }

    /**
     * Test a registration form with various input types
     */
    public void testRegistrationForm() {
        System.out.println("\n=== Testing Registration Form Scenario ===");
        
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        System.out.println("Page loaded");
        
        // Simulate filling different form fields
        List<WebComponent> allInputs = driver.findComponents("input");
        System.out.println("Found " + allInputs.size() + " input elements");
        
        // Find checkboxes (e.g., "I agree to terms")
        List<WebComponent> checkboxes = driver.findComponents(".MuiCheckbox-root");
        if (!checkboxes.isEmpty()) {
            mui.setContext(checkboxes.get(0), driver);
            MuiCheckbox termsCheckbox = mui.toCheckbox();
            System.out.println("Terms checkbox found");
            
            if (!termsCheckbox.isChecked()) {
                termsCheckbox.click();
                System.out.println("Terms checkbox checked: " + termsCheckbox.isChecked());
            }
        }
        
        // Find switches (e.g., "Subscribe to newsletter")
        List<WebComponent> switches = driver.findComponents(".MuiSwitch-root");
        if (!switches.isEmpty()) {
            mui.setContext(switches.get(0), driver);
            MuiSwitch newsletterSwitch = mui.toSwitch();
            System.out.println("Newsletter switch found");
            
            // Toggle switch
            newsletterSwitch.click();
            System.out.println("Newsletter subscription: " + (newsletterSwitch.isChecked() ? "Enabled" : "Disabled"));
        }
        
        // Find select dropdowns (e.g., country selection)
        List<WebComponent> selects = driver.findComponents(".MuiSelect-root");
        if (!selects.isEmpty()) {
            mui.setContext(selects.get(0), driver);
            var countrySelect = mui.toSelect();
            System.out.println("Country select found");
            
            // Get available options
            List<WebComponent> options = countrySelect.findComponents("option");
            System.out.println("Available countries: " + options.size());
        }
    }

    /**
     * Test form validation scenarios
     */
    public void testFormValidation() {
        System.out.println("\n=== Testing Form Validation ===");
        
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        System.out.println("Page loaded");
        
        List<WebComponent> textFields = driver.findComponents(".MuiTextField-root");
        if (!textFields.isEmpty()) {
            mui.setContext(textFields.get(0), driver);
            MuiTextField textField = mui.toTextField();
            
            // Test empty field
            textField.fill("");
            System.out.println("Empty field value length: " + textField.inputValue().length());
            
            // Test with valid input
            textField.fill("Valid Input");
            assertEquals("Valid Input", textField.inputValue());
            System.out.println("Valid input set successfully");
            
            // Check for error state (if applicable)
            String errorClass = textField.getAttribute("class");
            boolean hasError = errorClass != null && errorClass.contains("Mui-error");
            System.out.println("Has error state: " + hasError);
        }
    }

    /**
     * Test interactive form with radio buttons
     */
    public void testRadioGroupForm() {
        System.out.println("\n=== Testing Radio Group Form ===");
        
        driver.navigate("https://mui.com/material-ui/react-radio/", 600_000);
        System.out.println("Page loaded");
        
        List<WebComponent> radios = driver.findComponents(".MuiRadio-root");
        System.out.println("Found " + radios.size() + " radio buttons");
        
        if (radios.size() >= 2) {
            // Select first radio option
            mui.setContext(radios.get(0), driver);
            MuiRadio firstRadio = mui.toRadio();
            System.out.println("First radio initial state: " + firstRadio.isChecked());
            
            if (!firstRadio.isChecked()) {
                firstRadio.click();
                System.out.println("First radio selected: " + firstRadio.isChecked());
            }
            
            // Verify second radio is not selected
            mui.setContext(radios.get(1), driver);
            MuiRadio secondRadio = mui.toRadio();
            System.out.println("Second radio state: " + secondRadio.isChecked());
        }
    }

    /**
     * Test form with slider controls
     */
    public void testSliderForm() {
        System.out.println("\n=== Testing Slider Form Controls ===");
        
        driver.navigate("https://mui.com/material-ui/react-slider/", 600_000);
        System.out.println("Page loaded");
        
        List<WebComponent> sliders = driver.findComponents(".MuiSlider-root");
        System.out.println("Found " + sliders.size() + " slider controls");
        
        if (!sliders.isEmpty()) {
            mui.setContext(sliders.get(0), driver);
            MuiSlider slider = mui.toSlider();
            System.out.println("Slider control found");
            
            // Get current slider value
            try {
                String currentValue = slider.getAttribute("aria-valuenow");
                if (currentValue != null) {
                    System.out.println("Current slider value: " + currentValue);
                }
            } catch (Exception e) {
                System.out.println("Could not retrieve slider value");
            }
        }
    }

    /**
     * Complete end-to-end form submission example
     */
    public void testCompleteFormSubmission() {
        System.out.println("\n=== Complete Form Submission Example ===");
        
        // Step 1: Navigate to form page
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        System.out.println("Step 1: Navigated to form page");
        
        // Step 2: Fill text inputs
        List<WebComponent> textFields = driver.findComponents(".MuiTextField-root");
        if (!textFields.isEmpty()) {
            mui.setContext(textFields.get(0), driver);
            MuiTextField input = mui.toTextField();
            input.fill("John Doe");
            System.out.println("Step 2: Filled name field");
        }
        
        // Step 3: Interact with checkboxes
        List<WebComponent> checkboxes = driver.findComponents(".MuiCheckbox-root");
        if (!checkboxes.isEmpty()) {
            mui.setContext(checkboxes.get(0), driver);
            MuiCheckbox checkbox = mui.toCheckbox();
            if (!checkbox.isChecked()) {
                checkbox.click();
                System.out.println("Step 3: Checked agreement checkbox");
            }
        }
        
        // Step 4: Click submit button
        List<WebComponent> buttons = driver.findComponents(".MuiButton-contained");
        if (!buttons.isEmpty()) {
            mui.setContext(buttons.get(0), driver);
            MuiButton submitButton = mui.toButton();
            System.out.println("Step 4: Found submit button: " + submitButton.innerText());
            // submitButton.click(); // Uncomment to actually submit
        }
        
        System.out.println("Form submission workflow completed!");
    }

    /**
     * Main entry point. Starts the Playwright driver, runs all MUI form tests and
     * prints a summary report.
     *
     * <p>Optional first argument or {@code MUI_FORM_FILTER} environment variable to run
     * a single test by name (e.g. {@code "testLoginForm"}).</p>
     *
     * @param args optional: first argument is the test name filter
     */
    public static void main(String[] args) {
        MuiFormExample test = new MuiFormExample();
        test.setUpDriver();

        String filter = args.length > 0 ? args[0] : System.getenv("MUI_FORM_FILTER");

        try {
            test.runTestClass("MuiFormExample", () -> {
                test.runIf(filter, "testLoginForm", test::testLoginForm);
                test.runIf(filter, "testRegistrationForm", test::testRegistrationForm);
                test.runIf(filter, "testFormValidation", test::testFormValidation);
                test.runIf(filter, "testRadioGroupForm", test::testRadioGroupForm);
                test.runIf(filter, "testSliderForm", test::testSliderForm);
                test.runIf(filter, "testCompleteFormSubmission", test::testCompleteFormSubmission);
            });
        } finally {
            test.tearDownAndReport();
        }

        if (test.hasFailures()) {
            System.exit(1);
        }
    }
}
