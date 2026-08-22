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

package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.*;

/**
 * A Material UI Select wrapper which supports the Popover-based options.
 *
 * <p>This component represents a Material UI Select dropdown which displays options in a popover overlay.
 * The options are not direct children of the select element, but are rendered in a separate layer that
 * appears in front of the page when the select is activated.</p>
 *
 * <p>Key features:
 * <ul>
 *   <li>Popover-based option display with animation support</li>
 *   <li>Single selection support</li>
 *   <li>Various selection methods (by index, value, visible text)</li>
 *   <li>Option management (select, get selected options)</li>
 * </ul>
 *
 * <p><strong>Usage notes:</strong>
 * <ul>
 *   <li>Options show and hide usually requires a wait time for the animation</li>
 *   <li>Once the options are displayed, subsequent operations can be called immediately</li>
 * </ul>
 *
 * @see <a href="https://material-ui.com/components/selects/">
 * https://material-ui.com/components/selects/</a>
 * @since 1.12
 */
public class MuiSelect extends AbstractMuiComponent {

    /**
     * The component name used for identification and validation.
     */
    public static final String COMPONENT_NAME = "Select";

    /**
     * The CSS selector for option elements.
     */
    private static final String OPTION_SELECTOR = "[role=\"option\"]";

    /**
     * Constructs a MuiSelect instance with the specified locator, driver, and configuration.
     *
     * @param locator the Locator representing the select component in the DOM
     * @param driver the ComponentDriver for browser interactions
     * @param config the Material UI configuration for styling and behavior
     */
    public MuiSelect(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the currently selected value as text.
     *
     * @return the selected option text, or null if no option is selected
     */
    public String getSelectedValue() {
        return locator.innerText();
    }

    /**
     * Selects an option by its value attribute.
     *
     * @param value the value attribute of the option to select
     */
    public void selectByValue(String value) {
        locator.selectOption(value);
    }

    /**
     * Selects an option by its visible text.
     *
     * @param text the visible text of the option to select
     */
    public void selectByVisibleText(String text) {
        List<WebComponent> options = getOptions();
        WebComponent targetOption = options.stream()
                .filter(option -> text.equals(option.innerText()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Option with text '" + text + "' not found"));
        targetOption.click();
    }

    /**
     * Selects an option by its index position.
     *
     * @param index the zero-based index of the option to select
     */
    public void selectByIndex(int index) {
        List<WebComponent> options = getOptions();
        if (index < 0 || index >= options.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for options size " + options.size());
        }
        options.get(index).click();
    }

    /**
     * Gets all available options in the select.
     *
     * <p>Note: For MUI Select, you may need to click/open the select first to make options visible.</p>
     *
     * @return list of all option components
     */
    public List<WebComponent> getOptions() {
        // Try to find options within the select first
        List<WebComponent> options = findComponents(OPTION_SELECTOR);
        if (!options.isEmpty()) {
            return options;
        }
        
        // If not found, look for options in the popover/menu
        options = driver.findComponents(OPTION_SELECTOR);
        return options;
    }

    /**
     * Checks if the select is currently open (showing options).
     *
     * @return true if options are visible, false otherwise
     */
    public boolean isOpen() {
        List<WebComponent> options = driver.findComponents(OPTION_SELECTOR);
        return !options.isEmpty() && options.get(0).isVisible();
    }

    /**
     * Opens the select dropdown to show options.
     */
    public void open() {
        if (!isOpen()) {
            locator.click();
        }
    }

    /**
     * Closes the select dropdown.
     */
    public void close() {
        if (isOpen()) {
            // Press Escape to close
            locator.press("Escape");
        }
    }
}
