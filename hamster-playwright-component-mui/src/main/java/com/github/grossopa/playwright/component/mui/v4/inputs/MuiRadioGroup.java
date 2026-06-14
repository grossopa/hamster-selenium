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
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * The Material UI RadioGroup component implementation for Playwright.
 *
 * <p>RadioGroup is a helper component that groups Radio buttons together and manages their state.
 * It ensures that only one radio button can be selected at a time within the group.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/radio-buttons/#radiogroup">
 * https://material-ui.com/components/radio-buttons/#radiogroup</a>
 * @since 1.12
 */
public class MuiRadioGroup extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "RadioGroup";

    /**
     * Constructs an MuiRadioGroup instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiRadioGroup(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<com.github.grossopa.playwright.component.mui.MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Gets all radio buttons within this group.
     *
     * @return list of MuiRadio components
     */
    public List<MuiRadio> getRadios() {
        List<WebComponent> radioElements = findComponents("[type=\"radio\"]");
        return radioElements.stream()
                .map(radio -> new MuiRadio(radio.locator(), driver, config))
                .collect(Collectors.toList());
    }

    /**
     * Gets the count of radio buttons in this group.
     *
     * @return the number of radio buttons
     */
    public int getRadioCount() {
        return getRadios().size();
    }

    /**
     * Gets the currently selected radio button's value.
     *
     * @return the value of the selected radio button, or null if none is selected
     */
    public String getSelectedValue() {
        List<MuiRadio> radios = getRadios();
        for (MuiRadio radio : radios) {
            if (radio.isChecked()) {
                return radio.getAttribute("value");
            }
        }
        return null;
    }

    /**
     * Selects a radio button by its value attribute.
     *
     * @param value the value attribute of the radio button to select
     * @throws IllegalArgumentException if no radio button with the specified value is found
     */
    public void selectByValue(String value) {
        List<MuiRadio> radios = getRadios();
        MuiRadio targetRadio = radios.stream()
                .filter(radio -> value.equals(radio.getAttribute("value")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Radio button with value '" + value + "' not found in group"));
        targetRadio.select();
    }

    /**
     * Selects a radio button by its index position.
     *
     * @param index the zero-based index of the radio button to select
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void selectByIndex(int index) {
        List<MuiRadio> radios = getRadios();
        if (index < 0 || index >= radios.size()) {
            throw new IndexOutOfBoundsException(
                    "Radio index " + index + " is out of bounds. Available radios: " + radios.size());
        }
        radios.get(index).select();
    }

    /**
     * Checks if any radio button in the group is selected.
     *
     * @return true if at least one radio is selected, false otherwise
     */
    public boolean hasSelection() {
        return getSelectedValue() != null;
    }
}
