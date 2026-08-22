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
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * The Material UI Switch implementation for Playwright.
 *
 * <p>Switches toggle the state of a single setting on or off. They are typically used for binary
 * settings like enabling/disabling features or turning something on/off.</p>
 *
 * @see <a href="https://material-ui.com/components/switches/">
 * https://material-ui.com/components/switches/</a>
 * @author Jack Yin
 * @since 1.12
 */
public class MuiSwitch extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Switch";

    /**
     * Constructs an MuiSwitch instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiSwitch(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Checks if the switch is currently turned on (checked).
     *
     * @return true if the switch is checked/on, false otherwise
     */
    @Override
    public boolean isChecked() {
        return config.isChecked(getButton());
    }

    /**
     * Checks if the switch is enabled (not disabled).
     *
     * @return true if the switch is enabled, false if disabled
     */
    @Override
    public boolean isEnabled() {
        return !config.isDisabled(getButton());
    }

    /**
     * Toggles the switch state (on/off).
     */
    public void toggle() {
        locator.click();
    }

    /**
     * Turns the switch on (checked state).
     */
    public void turnOn() {
        if (!isChecked()) {
            toggle();
        }
    }

    /**
     * Turns the switch off (unchecked state).
     */
    public void turnOff() {
        if (isChecked()) {
            toggle();
        }
    }

    /**
     * Gets the internal button element of the switch.
     *
     * @return the button WebComponent
     */
    private com.github.grossopa.playwright.core.WebComponent getButton() {
        return findComponent("." + config.getCssPrefix() + "IconButton-root");
    }
}
