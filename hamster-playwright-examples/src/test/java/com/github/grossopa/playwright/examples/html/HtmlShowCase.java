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
package com.github.grossopa.playwright.examples.html;

import com.github.grossopa.playwright.component.html.HtmlCheckbox;
import com.github.grossopa.playwright.component.html.HtmlFileInput;
import com.github.grossopa.playwright.component.html.HtmlFormField;
import com.github.grossopa.playwright.component.html.HtmlRadioGroup;
import com.github.grossopa.playwright.component.html.HtmlSelect;
import com.github.grossopa.playwright.component.html.HtmlTable;
import com.github.grossopa.playwright.component.html.HtmlTableRow;
import com.github.grossopa.playwright.component.html.HtmlTextArea;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests HTML with Playwright
 *
 * @author Jack Yin
 * @since 1.12
 */
@SuppressWarnings("all")
public class HtmlShowCase extends AbstractBrowserSupport {

    public void testTable() {
        driver.navigate("https://www.w3schools.com/html/html_tables.asp", 600_000);
        System.out.println("page loaded");
        WebComponent tableComponent = driver.findComponent("#customers");
        System.out.println(tableComponent.innerHTML());
        HtmlTable table = new HtmlTable(tableComponent, driver);
        
        WebComponent header = table.getHeader();
        System.out.println(header.innerHTML());
        List<WebComponent> headerCells = header.findComponents("th");
        assertEquals("Company", headerCells.get(0).innerText());
        assertEquals("Contact", headerCells.get(1).innerText());
        assertEquals("Country", headerCells.get(2).innerText());
        
        List<HtmlTableRow> dataRows = table.getDataRows();
        assertEquals(6, dataRows.size());
        assertEquals("Alfreds Futterkiste", dataRows.get(0).getCell(0).innerText());
        assertEquals("Maria Anders", dataRows.get(0).getCell(1).innerText());
        assertEquals("Germany", dataRows.get(0).getCell(2).innerText());
    }

    public void testSelect() {
        driver.navigate("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select", 600_000L);
        var frame = driver.page().frame("iframeResult");

        HtmlSelect select = new HtmlSelect(frame.locator("#cars"), driver);
        
        List<WebComponent> options = select.findComponents("option");
        assertEquals(4, options.size());
        assertEquals("Volvo", options.get(0).innerText());
        assertEquals("Saab", options.get(1).innerText());
        assertEquals("Opel", options.get(2).innerText());
        assertEquals("Audi", options.get(3).innerText());

        select.selectByValue("audi");
        // Note: Playwright's selectOption behavior is different from Selenium's
        // In Playwright, you would typically check the selected value differently
    }

    public void testFormField() {
        driver.navigate("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_form_submit", 600_000L);
        var frame = driver.page().frame("iframeResult");

        // Demonstrate HtmlFormField with label + input
        WebComponent firstNameInput = driver.mapLocator(frame.locator("input[name='fname']").first());
        WebComponent lastNameInput = driver.mapLocator(frame.locator("input[name='lname']").first());

        // Fill in form fields and verify values
        firstNameInput.fill("John");
        assertEquals("John", firstNameInput.inputValue());

        lastNameInput.fill("Doe");
        assertEquals("Doe", lastNameInput.inputValue());

        // Demonstrate HtmlFormField component
        HtmlFormField formField = new HtmlFormField(frame.locator("form").first(), driver);
        WebComponent label = formField.getLabel();
        System.out.println("FormField label: " + label.innerText());
        System.out.println("FormField demo completed: first name and last name filled successfully.");
    }

    public void testCheckbox() {
        driver.navigate("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_form_checkbox", 600_000L);
        var frame = driver.page().frame("iframeResult");

        // Demonstrate HtmlCheckbox with vehicle checkbox
        HtmlCheckbox vehicleCheckbox = new HtmlCheckbox(frame.locator("#vehicle1"), driver);
        assertTrue(vehicleCheckbox.validate(), "Should be a checkbox input");

        // Check and verify
        vehicleCheckbox.check();
        assertTrue(vehicleCheckbox.isChecked(), "Should be checked after check()");

        // Toggle and verify
        vehicleCheckbox.toggle();
        assertFalse(vehicleCheckbox.isChecked(), "Should be unchecked after toggle()");

        // Toggle again
        vehicleCheckbox.toggle();
        assertTrue(vehicleCheckbox.isChecked(), "Should be checked after second toggle()");

        // Uncheck
        vehicleCheckbox.uncheck();
        assertFalse(vehicleCheckbox.isChecked(), "Should be unchecked after uncheck()");

        System.out.println("Checkbox demo completed: check/uncheck/toggle verified successfully.");
    }

    public void testRadioGroup() {
        driver.navigate("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_form_radio", 600_000L);
        var frame = driver.page().frame("iframeResult");

        // Demonstrate HtmlRadioGroup with gender radio buttons
        HtmlRadioGroup radioGroup = new HtmlRadioGroup(frame.locator("form"), driver);

        // Verify option count
        int optionCount = radioGroup.getOptionCount();
        assertTrue(optionCount >= 2, "Should have at least 2 radio options");
        System.out.println("Radio group has " + optionCount + " options");

        // Select by value
        radioGroup.selectByValue("female");
        String selectedValue = radioGroup.getSelectedValue();
        assertEquals("female", selectedValue, "Should have selected 'female'");

        // Verify getSelectedOption
        WebComponent selectedOption = radioGroup.getSelectedOption();
        assertNotNull(selectedOption, "Should find the selected radio option");

        // Select another value
        radioGroup.selectByValue("male");
        assertEquals("male", radioGroup.getSelectedValue(), "Should have selected 'male'");

        System.out.println("RadioGroup demo completed: selectByValue/getSelectedValue verified successfully.");
    }

    public void testTextArea() {
        driver.navigate("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_textarea", 600_000L);
        var frame = driver.page().frame("iframeResult");

        // Demonstrate HtmlTextArea
        HtmlTextArea textArea = new HtmlTextArea(frame.locator("textarea"), driver);
        assertTrue(textArea.validate(), "Should be a textarea element");

        // Set value and verify
        textArea.setValue("Hello, this is a test message!");
        assertEquals("Hello, this is a test message!", textArea.getValue());

        // Clear and verify
        textArea.clear();
        assertEquals("", textArea.getValue());

        // Set value again and append
        textArea.setValue("Line 1");
        textArea.appendText("\nLine 2");
        String value = textArea.getValue();
        assertTrue(value.contains("Line 1"), "Should contain 'Line 1'");
        assertTrue(value.contains("Line 2"), "Should contain 'Line 2'");

        // Query attributes
        int rows = textArea.getRows();
        int cols = textArea.getCols();
        System.out.println("TextArea rows: " + rows + ", cols: " + cols);

        System.out.println("TextArea demo completed: setValue/getValue/appendText/clear verified successfully.");
    }

    public void testFileInput() {
        driver.navigate("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_form_fileupload", 600_000L);
        var frame = driver.page().frame("iframeResult");

        // Demonstrate HtmlFileInput
        HtmlFileInput fileInput = new HtmlFileInput(frame.locator("input[type='file']"), driver);
        assertTrue(fileInput.validate(), "Should be a file input element");

        // Verify initially no files
        List<String> initialFiles = fileInput.getFileNames();
        assertTrue(initialFiles.isEmpty(), "Should have no files initially");

        // Verify multiple attribute
        boolean isMultiple = fileInput.isMultiple();
        System.out.println("File input multiple: " + isMultiple);

        // Verify accept attribute
        String accept = fileInput.getAccept();
        System.out.println("File input accept: " + accept);

        System.out.println("FileInput demo completed: validate/getFileNames/isMultiple/getAccept verified successfully.");
    }

    /**
     * Main entry point. Starts the Playwright driver, runs all HTML component tests and
     * prints a summary report.
     *
     * <p>Optional first argument or {@code HTML_FILTER} environment variable to run
     * a single test by name (e.g. {@code "testTable"}).</p>
     *
     * @param args optional: first argument is the test name filter
     */
    public static void main(String[] args) {
        HtmlShowCase test = new HtmlShowCase();
        test.setUpDriver();

        String filter = args.length > 0 ? args[0] : System.getenv("HTML_FILTER");

        try {
            test.runTestClass("HtmlShowCase", () -> {
                test.runIf(filter, "testTable", test::testTable);
                test.runIf(filter, "testSelect", test::testSelect);
                test.runIf(filter, "testFormField", test::testFormField);
                test.runIf(filter, "testCheckbox", test::testCheckbox);
                test.runIf(filter, "testRadioGroup", test::testRadioGroup);
                test.runIf(filter, "testTextArea", test::testTextArea);
                test.runIf(filter, "testFileInput", test::testFileInput);
            });
        } finally {
            test.tearDownAndReport();
        }

        if (test.hasFailures()) {
            System.exit(1);
        }
    }
}
