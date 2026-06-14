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
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * A Material UI Checkbox component wrapper for Playwright.
 *
 * <p>This class represents a Material UI Checkbox component which allows users to select/unselect options.
 * It provides methods to check the selection state of the checkbox.</p>
 *
 * <p>Key features:
 * <ul>
 *   <li>Selection state detection</li>
 *   <li>Component validation through the standard Material UI component interface</li>
 *   <li>Integration with Material UI configuration for styling checks</li>
 * </ul>
 * </p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/checkboxes/">
 * https://material-ui.com/components/checkboxes/</a>
 * @since 1.12
 */
public class MuiCheckbox extends AbstractMuiComponent {

    /**
     * The component name used for identification and validation.
     */
    public static final String COMPONENT_NAME = "Checkbox";

    /**
     * Constructs a MuiCheckbox instance with the specified locator, driver, and configuration.
     *
     * @param locator the Locator representing the checkbox in the DOM
     * @param driver the ComponentDriver for browser interactions
     * @param config the Material UI configuration for styling and behavior
     */
    public MuiCheckbox(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    /**
     * Checks if the checkbox is currently selected (checked).
     *
     * <p>A checkbox is considered selected if it's in the "checked" state, which is typically
     * indicated by specific CSS classes or attributes in the DOM element.</p>
     *
     * @return true if the checkbox is selected/checked, false otherwise
     */
    @Override
    public boolean isChecked() {
        return config.isChecked(this);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }
}
