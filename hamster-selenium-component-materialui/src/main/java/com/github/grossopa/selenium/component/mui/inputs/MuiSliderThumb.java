/*
 * Copyright © 2020 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.selenium.component.mui.inputs;

import com.github.grossopa.selenium.component.mui.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

/**
 * The Thumb element of {@link MuiSlider}
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiSliderThumb extends AbstractMuiComponent {

    /**
     * the value of orientation when the slider is horizontal
     */
    public static final String ORIENTATION_HORIZONTAL = "horizontal";

    /**
     * the value of orientation when the slider is vertical
     */
    public static final String ORIENTATION_VERTICAL = "vertical";

    /**
     * Constructs {@link MuiSliderThumb} instance with the delegated element and root driver
     *
     * @param element
     *         the delegated element
     * @param driver
     *         the root driver
     * @param config
     *         the mui configuration instance
     */
    public MuiSliderThumb(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "Slider-thumb";
    }

    @Override
    public boolean validate() {
        return config.validateByCss(this, config.getCssPrefix() + getComponentName());
    }

    /**
     * Gets the orientation of current thumb from attribute "aria-orientation". possible values are "horizontal" and
     * "vertical".
     *
     * @return the orientation of current thumb. possible values are "horizontal" and "vertical".
     */
    public String getOrientation() {
        return element.getAttribute("aria-orientation");
    }

    /**
     * Gets the value text.
     *
     * @return the value text
     */
    public String getValueText() {
        return element.getAttribute("aria-valuetext");
    }

    /**
     * Gets the raw max value.
     *
     * @return the raw min value.
     */
    public String getMaxValue() {
        return element.getAttribute("aria-valuemax");
    }

    /**
     * Gets the raw min value.
     *
     * @return the raw min value.
     */
    public String getMinValue() {
        return element.getAttribute("aria-valuemin");
    }

    /**
     * Gets the raw value.
     *
     * @return the raw value.
     */
    public String getValue() {
        return element.getAttribute("aria-valuenow");
    }

    /**
     * Gets the percentage in string.
     *
     * @return the percentage in string
     */
    public String getPercentage() {
        String orientation = getOrientation();
        if (ORIENTATION_VERTICAL.equals(orientation)) {
            return element.getCssValue("bottom");
        } else if (ORIENTATION_HORIZONTAL.equals(orientation)) {
            return element.getCssValue("left");
        }
        throw new IllegalStateException("Unknown orientation " + orientation);
    }
}
