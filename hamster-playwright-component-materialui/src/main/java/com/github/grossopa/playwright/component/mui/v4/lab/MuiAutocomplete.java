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

package com.github.grossopa.playwright.component.mui.v4.lab;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;
import static com.github.grossopa.utils.consts.HtmlConstants.INPUT;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * The autocomplete is a normal text input enhanced by a panel of suggested options.
 *
 * <p>Autocomplete provides intelligent suggestions as users type, supporting both single 
 * and multiple selection modes with customizable options.</p>
 *
 * @see <a href="https://material-ui.com/components/autocomplete/">
 * https://material-ui.com/components/autocomplete/</a>
 * @author Jack Yin
 * @since 1.12
 */
public class MuiAutocomplete extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Autocomplete";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiAutocomplete(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Gets the input field element.
     *
     * @return the input WebComponent
     */
    public WebComponent getInput() {
        return findComponent(INPUT);
    }

    /**
     * Types text into the autocomplete input field.
     *
     * @param text the text to type
     */
    public void typeInput(String text) {
        WebComponent input = getInput();
        if (input != null) {
            input.fill(text);
        }
    }

    /**
     * Gets the list of available options.
     *
     * @return list of option WebComponents
     */
    public List<WebComponent> getOptions() {
        return driver.findComponents("[role=\"option\"]");
    }

    /**
     * Gets the count of available options.
     *
     * @return the number of options
     */
    public int getOptionCount() {
        return getOptions().size();
    }

    /**
     * Selects an option by its text.
     *
     * @param optionText the text of the option to select
     * @return the option text list
     * @throws IllegalArgumentException if option not found
     */
    @Override
    public List<String> selectOption(String optionText) {
        List<WebComponent> options = getOptions();
        WebComponent targetOption = options.stream()
                .filter(option -> optionText.equals(option.innerText()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Option with text '" + optionText + "' not found"));
        targetOption.click();
        return List.of(targetOption.textContent());
    }

    /**
     * Gets the currently selected value(s).
     *
     * @return list of selected option texts
     */
    public List<String> getSelectedValues() {
        // For single select
        WebComponent input = getInput();
        if (input != null) {
            String value = input.getAttribute("value");
            if (value != null && !value.isEmpty()) {
                return List.of(value);
            }
        }
        
        // For multiple select - look for chips/tags
        List<WebComponent> chips = findComponents("." + config.getCssPrefix() + "Chip-root");
        return chips.stream()
                .map(Locator::innerText)
                .toList();
    }

    /**
     * Checks if the autocomplete allows multiple selections.
     *
     * @return true if multiple selection is enabled
     */
    public boolean isMultiple() {
        return getAttribute("aria-multiselectable") != null || 
               !findComponents("." + config.getCssPrefix() + "Chip-root").isEmpty();
    }

    /**
     * Checks if the autocomplete is in loading state.
     *
     * @return true if loading, false otherwise
     */
    public boolean isLoading() {
        String className = getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "Autocomplete-loading");
    }

    /**
     * Checks if the autocomplete is read-only.
     *
     * @return true if read-only, false otherwise
     */
    public boolean isReadOnly() {
        WebComponent input = getInput();
        return input != null && "true".equals(input.getAttribute("readonly"));
    }

    /**
     * Clears the selected value(s).
     */
    @Override
    public void clear() {
        WebComponent input = getInput();
        if (input != null) {
            input.fill("");
        }
    }

    /**
     * Opens the options dropdown.
     */
    public void open() {
        WebComponent input = getInput();
        if (input != null) {
            input.click();
        }
    }

    /**
     * Closes the options dropdown.
     */
    public void close() {
        locator.press("Escape");
    }

    /**
     * Checks if the options dropdown is open.
     *
     * @return true if options are visible, false otherwise
     */
    public boolean isOpen() {
        return !getOptions().isEmpty();
    }
}
