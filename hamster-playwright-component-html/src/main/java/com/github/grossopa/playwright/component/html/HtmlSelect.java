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

package com.github.grossopa.playwright.component.html;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.DefaultWebComponent;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;

import java.util.List;

/**
 * The HTML select component.
 *
 * @author Jack Yin
 * @since 1.12
 */
public class HtmlSelect extends DefaultWebComponent {

    /**
     * Consructs an instance.
     *
     * @param locator the locator
     * @param driver  the driver
     */
    public HtmlSelect(Locator locator, ComponentDriver driver) {
        super(locator, driver);
    }

    @Override
    public String getComponentTagName() {
        return "select";
    }

    /**
     * Selects the option by value
     *
     * @param value the option value to select
     */
    public void selectByValue(String value) {
        locator.selectOption(new String[]{value});
    }

    /**
     * Selects the option by visible text
     *
     * @param text the option text to select
     */
    public void selectByVisibleText(String text) {
        locator.selectOption(new SelectOption().setLabel(text));
    }

    /**
     * Gets the first selected option text
     *
     * @return the first selected option text
     */
    public String getFirstSelectedOptionText() {
        return this.locator.innerText();
    }

    /**
     * Checks whether this select element supports multiple selections
     *
     * @return true if multiple selections are supported
     */
    public boolean isMultiple() {
        String multiple = this.locator.getAttribute("multiple");
        return multiple != null;
    }

    /**
     * Gets all options in this select element
     *
     * @return list of all option components
     */
    public List<WebComponent> getOptions() {
        return findComponents("option");
    }

    /**
     * Gets all selected options
     *
     * @return list of selected option components
     */
    public List<WebComponent> getAllSelectedOptions() {
        return findComponents("option:checked");
    }

    /**
     * Gets the first selected option component
     *
     * @return the first selected option component
     */
    public WebComponent getFirstSelectedOption() {
        return findComponent("option:checked");
    }

    /**
     * Selects the option by index
     *
     * @param index the index of the option to select
     */
    public void selectByIndex(int index) {
        String value = locator.evaluate(
                "el => el.options[arguments[0]].value",
                index
        ).toString();
        locator.selectOption(new String[]{value});
    }

    /**
     * Deselects all options (only for multi-select)
     */
    public void deselectAll() {
        locator.selectOption(new String[]{});
    }

    /**
     * Deselects the option by value
     *
     * @param value the option value to deselect
     */
    @SuppressWarnings("unused")
    public void deselectByValue(String value) {
        // Playwright doesn't have direct deselect, re-select with remaining options
        Object result = locator.evaluate(
                "el => Array.from(el.selectedOptions).map(o => o.value).filter(v => v !== arguments[0])",
                value
        );
        @SuppressWarnings("unchecked")
        List<String> currentValues = result instanceof List ? (List<String>) result : List.of();
        locator.selectOption(currentValues.toArray(new String[0]));
    }

    /**
     * Deselects the option by index
     *
     * @param index the index of the option to deselect
     */
    @SuppressWarnings("unused")
    public void deselectByIndex(int index) {
        Object result = locator.evaluate(
                "el => Array.from(el.options).map((o, i) => o.selected && i !== arguments[0] ? o.value : null).filter(v => v !== null)",
                index
        );
        @SuppressWarnings("unchecked")
        List<String> currentValues = result instanceof List ? (List<String>) result : List.of();
        locator.selectOption(currentValues.toArray(new String[0]));
    }

    /**
     * Deselects the option by visible text
     *
     * @param text the option text to deselect
     */
    @SuppressWarnings("unused")
    public void deselectByVisibleText(String text) {
        Object result = locator.evaluate(
                "el => Array.from(el.selectedOptions).filter(o => o.text !== arguments[0]).map(o => o.value)",
                text
        );
        @SuppressWarnings("unchecked")
        List<String> currentValues = result instanceof List ? (List<String>) result : List.of();
        locator.selectOption(currentValues.toArray(new String[0]));
    }
}