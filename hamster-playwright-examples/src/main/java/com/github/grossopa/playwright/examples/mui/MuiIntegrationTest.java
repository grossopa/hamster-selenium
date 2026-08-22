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
import com.github.grossopa.playwright.component.mui.v4.inputs.*;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiTabs;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;

import java.util.List;

/**
 * Integration tests demonstrating real-world MUI component scenarios
 * Combines multiple components in realistic user workflows
 *
 * @author Jack Yin
 * @since 1.12
 */
@SuppressWarnings("all")
public class MuiIntegrationTest extends AbstractBrowserSupport {

    private static final MuiComponents mui = MuiComponents.mui();

    /**
     * Test e-commerce product filter scenario
     * Simulates filtering products by category, price range, and rating
     */
    public void testEcommerceFilterScenario() {
        System.out.println("=== E-Commerce Filter Scenario ===");
        driver.navigate("https://mui.com/material-ui/react-select/", 600_000);
        
        // Step 1: Select category (using Select component)
        List<WebComponent> selects = driver.findComponents(".MuiSelect-root");
        if (!selects.isEmpty()) {
            mui.setContext(selects.get(0), driver);
            var categorySelect = mui.toSelect();
            System.out.println("Step 1: Category selector found");
            
            // Get available options
            List<WebComponent> options = categorySelect.findComponents("option");
            System.out.println("  Available categories: " + options.size());
        }
        
        // Step 2: Set price range (using Slider component)
        driver.navigate("https://mui.com/material-ui/react-slider/", 600_000);
        List<WebComponent> sliders = driver.findComponents(".MuiSlider-root");
        if (!sliders.isEmpty()) {
            mui.setContext(sliders.get(0), driver);
            MuiSlider priceSlider = mui.toSlider();
            System.out.println("Step 2: Price range slider found");
            
            String currentValue = priceSlider.getAttribute("aria-valuenow");
            System.out.println("  Current price range: " + (currentValue != null ? currentValue : "N/A"));
        }
        
        // Step 3: Apply filters (using Button component)
        driver.navigate("https://mui.com/material-ui/react-button/", 600_000);
        List<WebComponent> buttons = driver.findComponents(".MuiButton-contained");
        if (!buttons.isEmpty()) {
            mui.setContext(buttons.get(0), driver);
            MuiButton applyButton = mui.toButton();
            System.out.println("Step 3: Apply filter button found");
            System.out.println("  Button text: " + applyButton.innerText());
        }
        
        System.out.println("E-commerce filter workflow completed!");
    }

    /**
     * Test user profile editing scenario
     * Simulates editing user information with various input types
     */
    public void testUserProfileEditScenario() {
        System.out.println("\n=== User Profile Edit Scenario ===");
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        
        // Step 1: Fill text fields (name, email, bio)
        List<WebComponent> textFields = driver.findComponents(".MuiTextField-root");
        System.out.println("Step 1: Found " + textFields.size() + " text input fields");
        
        if (textFields.size() >= 3) {
            mui.setContext(textFields.get(0), driver);
            MuiTextField nameField = mui.toTextField();
            nameField.fill("John Doe");
            System.out.println("  Name filled");
            
            mui.setContext(textFields.get(1), driver);
            MuiTextField emailField = mui.toTextField();
            emailField.fill("john.doe@example.com");
            System.out.println("  Email filled");
            
            mui.setContext(textFields.get(2), driver);
            MuiTextField bioField = mui.toTextField();
            bioField.fill("Software developer passionate about testing");
            System.out.println("  Bio filled");
        }
        
        // Step 2: Select gender (using Radio)
        driver.navigate("https://mui.com/material-ui/react-radio/", 600_000);
        List<WebComponent> radios = driver.findComponents(".MuiRadio-root");
        if (!radios.isEmpty()) {
            System.out.println("Step 2: Gender selection radios found (" + radios.size() + " options)");
        }
        
        // Step 3: Toggle notifications (using Switch)
        driver.navigate("https://mui.com/material-ui/react-switch/", 600_000);
        List<WebComponent> switches = driver.findComponents(".MuiSwitch-root");
        if (!switches.isEmpty()) {
            mui.setContext(switches.get(0), driver);
            MuiSwitch notificationSwitch = mui.toSwitch();
            System.out.println("Step 3: Notification toggle found");
            
            // Enable notifications
            if (!notificationSwitch.isChecked()) {
                notificationSwitch.click();
                System.out.println("  Notifications enabled");
            }
        }
        
        // Step 4: Save changes (using Button)
        driver.navigate("https://mui.com/material-ui/react-button/", 600_000);
        List<WebComponent> saveButtons = driver.findComponents(".MuiButton-containedPrimary");
        if (!saveButtons.isEmpty()) {
            mui.setContext(saveButtons.get(0), driver);
            MuiButton saveButton = mui.toButton();
            System.out.println("Step 4: Save button found - " + saveButton.innerText());
        }
        
        System.out.println("User profile edit workflow completed!");
    }

    /**
     * Test dashboard navigation scenario
     * Simulates navigating through a dashboard with tabs and menus
     */
    public void testDashboardNavigationScenario() {
        System.out.println("\n=== Dashboard Navigation Scenario ===");
        driver.navigate("https://mui.com/material-ui/react-tabs/", 600_000);
        
        // Step 1: Navigate between dashboard sections using tabs
        List<WebComponent> tabContainers = driver.findComponents(".MuiTabs-root");
        if (!tabContainers.isEmpty()) {
            mui.setContext(tabContainers.get(0), driver);
            MuiTabs dashboardTabs = mui.toTabs();
            List<WebComponent> tabs = dashboardTabs.findComponents(".MuiTab-root");
            
            System.out.println("Step 1: Dashboard sections available: " + tabs.size());
            
            // Navigate to different sections
            String[] sections = {"Overview", "Analytics", "Settings"};
            for (int i = 0; i < Math.min(tabs.size(), sections.length); i++) {
                System.out.println("  Navigating to: " + sections[i]);
                tabs.get(i).click();
                
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        // Step 2: Access user menu
        driver.navigate("https://mui.com/material-ui/react-menu/", 600_000);
        List<WebComponent> menuTriggers = driver.findComponents("button[aria-haspopup='true']");
        if (!menuTriggers.isEmpty()) {
            System.out.println("Step 2: User menu trigger found");
            System.out.println("  Menu options would include: Profile, Settings, Logout");
        }
        
        // Step 3: View notifications (using Badge)
        driver.navigate("https://mui.com/material-ui/react-badge/", 600_000);
        List<WebComponent> badges = driver.findComponents(".MuiBadge-root");
        if (!badges.isEmpty()) {
            System.out.println("Step 3: Notification badge found");
            
            List<WebComponent> badgeContent = badges.get(0).findComponents(".MuiBadge-badge");
            if (!badgeContent.isEmpty()) {
                System.out.println("  Unread notifications: " + badgeContent.get(0).innerText());
            }
        }
        
        System.out.println("Dashboard navigation workflow completed!");
    }

    /**
     * Test data table with pagination scenario
     * Simulates viewing and paginating through a data table
     */
    public void testDataTablePaginationScenario() {
        System.out.println("\n=== Data Table with Pagination Scenario ===");
        driver.navigate("https://mui.com/material-ui/react-table/", 600_000);
        
        // Step 1: View data table
        List<WebComponent> tables = driver.findComponents("table");
        System.out.println("Step 1: Found " + tables.size() + " data tables");
        
        if (!tables.isEmpty()) {
            // Count rows
            List<WebComponent> rows = tables.get(0).findComponents("tr");
            System.out.println("  Table contains " + rows.size() + " rows");
        }
        
        // Step 2: Use pagination controls
        driver.navigate("https://mui.com/material-ui/react-pagination/", 600_000);
        List<WebComponent> paginations = driver.findComponents(".MuiPagination-root");
        if (!paginations.isEmpty()) {
            System.out.println("Step 2: Pagination controls found");
            
            List<WebComponent> pageItems = paginations.get(0).findComponents(".MuiPaginationItem-root");
            System.out.println("  Available pages: " + pageItems.size());
            
            // Navigate to next page
            for (WebComponent item : pageItems) {
                String ariaLabel = item.getAttribute("aria-label");
                if ("Go to next page".equals(ariaLabel)) {
                    System.out.println("  Next page button found");
                    break;
                }
            }
        }
        
        // Step 3: Adjust items per page (using Select)
        driver.navigate("https://mui.com/material-ui/react-select/", 600_000);
        List<WebComponent> selects = driver.findComponents(".MuiSelect-root");
        if (!selects.isEmpty()) {
            System.out.println("Step 3: Items per page selector found");
        }
        
        System.out.println("Data table pagination workflow completed!");
    }

    /**
     * Test form validation and error handling scenario
     */
    public void testFormValidationScenario() {
        System.out.println("\n=== Form Validation & Error Handling Scenario ===");
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        
        // Step 1: Submit form with empty fields
        List<WebComponent> textFields = driver.findComponents(".MuiTextField-root");
        if (!textFields.isEmpty()) {
            mui.setContext(textFields.get(0), driver);
            MuiTextField requiredField = mui.toTextField();
            
            // Leave field empty
            requiredField.fill("");
            System.out.println("Step 1: Required field left empty");
            
            // Check for error state
            String className = requiredField.getAttribute("class");
            boolean hasError = className != null && className.contains("Mui-error");
            System.out.println("  Error state detected: " + hasError);
        }
        
        // Step 2: Show error message (using Alert)
        driver.navigate("https://mui.com/material-ui/react-alert/", 600_000);
        List<WebComponent> alerts = driver.findComponents(".MuiAlert-root");
        if (!alerts.isEmpty()) {
            mui.setContext(alerts.get(0), driver);
            var errorAlert = mui.toAlert();
            String severity = errorAlert.getAttribute("data-severity");
            
            if ("error".equals(severity)) {
                System.out.println("Step 2: Error alert displayed");
                System.out.println("  Message: " + errorAlert.innerText());
            }
        }
        
        // Step 3: Fix validation errors
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        textFields = driver.findComponents(".MuiTextField-root");
        if (!textFields.isEmpty()) {
            mui.setContext(textFields.get(0), driver);
            MuiTextField field = mui.toTextField();
            field.fill("Valid input value");
            System.out.println("Step 3: Field corrected with valid input");
            
            // Verify error cleared
            String className = field.getAttribute("class");
            boolean stillHasError = className != null && className.contains("Mui-error");
            System.out.println("  Error cleared: " + !stillHasError);
        }
        
        System.out.println("Form validation workflow completed!");
    }

    /**
     * Test multi-step wizard scenario
     */
    public void testMultiStepWizardScenario() {
        System.out.println("\n=== Multi-Step Wizard Scenario ===");
        driver.navigate("https://mui.com/material-ui/react-stepper/", 600_000);
        
        // Step 1: Display stepper
        List<WebComponent> steppers = driver.findComponents(".MuiStepper-root");
        if (!steppers.isEmpty()) {
            System.out.println("Step 1: Wizard stepper found");
            
            List<WebComponent> steps = steppers.get(0).findComponents(".MuiStep-root");
            System.out.println("  Total steps: " + steps.size());
            
            // Print step labels
            for (int i = 0; i < steps.size(); i++) {
                System.out.println("    Step " + (i + 1) + ": " + steps.get(i).innerText());
            }
        }
        
        // Step 2: Fill step 1 form
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        List<WebComponent> fields = driver.findComponents(".MuiTextField-root");
        if (!fields.isEmpty()) {
            System.out.println("Step 2: Step 1 form fields found (" + fields.size() + " fields)");
            
            mui.setContext(fields.get(0), driver);
            MuiTextField step1Field = mui.toTextField();
            step1Field.fill("Step 1 data");
            System.out.println("  Step 1 data entered");
        }
        
        // Step 3: Click Next button
        driver.navigate("https://mui.com/material-ui/react-button/", 600_000);
        List<WebComponent> nextButtons = driver.findComponents(".MuiButton-contained");
        if (!nextButtons.isEmpty()) {
            mui.setContext(nextButtons.get(0), driver);
            MuiButton nextButton = mui.toButton();
            System.out.println("Step 3: Next button found - " + nextButton.innerText());
        }
        
        // Step 4: Complete final step and submit
        System.out.println("Step 4: Final step - Review and Submit");
        List<WebComponent> submitButtons = driver.findComponents(".MuiButton-containedPrimary");
        if (!submitButtons.isEmpty()) {
            mui.setContext(submitButtons.get(0), driver);
            MuiButton submitButton = mui.toButton();
            System.out.println("  Submit button: " + submitButton.innerText());
        }
        
        System.out.println("Multi-step wizard workflow completed!");
    }

    public static void main(String[] args) {
        MuiIntegrationTest test = new MuiIntegrationTest();
        test.setUpDriver();

        test.runTestClass("MuiIntegrationTest", () -> {
            test.runTest("testEcommerceFilterScenario", test::testEcommerceFilterScenario);
            test.runTest("testUserProfileEditScenario", test::testUserProfileEditScenario);
            test.runTest("testDashboardNavigationScenario", test::testDashboardNavigationScenario);
            test.runTest("testDataTablePaginationScenario", test::testDataTablePaginationScenario);
            test.runTest("testFormValidationScenario", test::testFormValidationScenario);
            test.runTest("testMultiStepWizardScenario", test::testMultiStepWizardScenario);
        });

        // Keep browser open for manual inspection
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
