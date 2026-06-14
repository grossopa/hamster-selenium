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
 * Represents a thumb (handle) element within a Material UI Slider component.
 *
 * <p>A slider thumb is the draggable handle that users interact with to change the slider value.
 * This class provides methods to query the thumb's current value and position.</p>
 *
 * @author Jack Yin
 * @see MuiSlider
 * @since 1.12
 */
public class MuiSliderThumb extends AbstractMuiComponent {

    /**
     * The component name used for identification.
     */
    public static final String COMPONENT_NAME = "SliderThumb";

    /**
     * Constructs a MuiSliderThumb instance.
     *
     * @param locator the Locator representing the thumb element
     * @param driver the ComponentDriver for browser interactions
     * @param config the Material UI configuration for styling and behavior
     */
    public MuiSliderThumb(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the current value of this thumb as a String.
     *
     * <p>The value is typically stored in an aria-valuenow attribute or similar data attribute.</p>
     *
     * @return the current thumb value as a String
     */
    public String getValue() {
        String value = locator.getAttribute("aria-valuenow");
        if (value == null) {
            value = locator.getAttribute("data-value");
        }
        return value != null ? value : "0";
    }

    /**
     * Gets the minimum value for this thumb as a String.
     *
     * <p>The minimum value is typically stored in an aria-valuemin attribute.</p>
     *
     * @return the minimum thumb value as a String
     */
    public String getMinValue() {
        String value = locator.getAttribute("aria-valuemin");
        return value != null ? value : "0";
    }

    /**
     * Gets the maximum value for this thumb as a String.
     *
     * <p>The maximum value is typically stored in an aria-valuemax attribute.</p>
     *
     * @return the maximum thumb value as a String
     */
    public String getMaxValue() {
        String value = locator.getAttribute("aria-valuemax");
        return value != null ? value : "100";
    }
}
