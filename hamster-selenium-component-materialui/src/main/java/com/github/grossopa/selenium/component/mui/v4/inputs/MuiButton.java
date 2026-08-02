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

package com.github.grossopa.selenium.component.mui.v4.inputs;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;

/**
 * A Material UI Button component wrapper that provides button-specific functionality.
 * 
 * <p>This class represents a Material UI Button component which can be used for user interactions.
 * It supports various button states and provides methods to check if the button is selected.</p>
 * 
 * <p>Key features:
 * <ul>
 *   <li>Support for multiple Material UI versions (V4, V5, V6)</li>
 *   <li>Selection state detection</li>
 *   <li>Component validation through the standard Material UI component interface</li>
 * </ul>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/buttons/">
 * https://material-ui.com/components/buttons/</a>
 * @since 1.0
 */
public class MuiButton extends AbstractMuiComponent {

    /**
     * The component name used for identification and validation.
     */
    public static final String COMPONENT_NAME = "Button";

    /**
     * Constructs a MuiButton instance with the specified element, driver, and configuration.
     *
     * @param element the WebElement representing the button in the DOM
     * @param driver the ComponentWebDriver for browser interactions
     * @param config the Material UI configuration for styling and behavior
     */
    public MuiButton(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Returns the set of Material UI versions that this component supports.
     * 
     * <p>This implementation supports Material UI versions 4, 5, and 6.</p>
     *
     * @return a Set containing V4, V5, and V6 enum values
     */
    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    /**
     * Checks if the button is currently selected.
     * 
     * <p>A button is considered selected based on the styling configuration provided in the MuiConfig.
     * This is typically determined by checking CSS classes or other styling attributes.</p>
     *
     * @return true if the button is selected, false otherwise
     */
    @Override
    public boolean isSelected() {
        return config.isSelected(this);
    }
}