/*
 * Copyright © 2021 the original author or authors.
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

package com.github.grossopa.selenium.component.mui.v5.inputs;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiSliderThumb;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.core.locator.By2.axesBuilder;

/**
 * The Thumb element of {@link MuiSliderV5}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiSliderThumbV5 extends MuiSliderThumb {
    /**
     * Constructs {@link MuiSliderThumb} instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the mui configuration instance
     */
    public MuiSliderThumbV5(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V5);
    }

    /**
     * Gets the orientation of current thumb from attribute "aria-orientation". possible values are "horizontal" and
     * "vertical".
     *
     * @return the orientation of current thumb. possible values are "horizontal" and "vertical".
     */
    @Override
    public String getOrientation() {
        return getInputElement().getAttribute("aria-orientation");
    }

    /**
     * Gets the value text.
     *
     * @return the value text
     */
    @Override
    public String getValueText() {
        return getInputElement().getAttribute("aria-valuetext");
    }

    /**
     * Gets the raw max value.
     *
     * @return the raw min value.
     */
    @Override
    public String getMaxValue() {
        return getInputElement().getAttribute("aria-valuemax");
    }

    /**
     * Gets the raw min value.
     *
     * @return the raw min value.
     */
    @Override
    public String getMinValue() {
        return getInputElement().getAttribute("aria-valuemin");
    }

    /**
     * Gets the raw value.
     *
     * @return the raw value.
     */
    @Override
    public String getValue() {
        return getInputElement().getAttribute("aria-valuenow");
    }

    /**
     * In Material UI V5 all the related attributes of the Thumb have been moved from the top level to inner input.
     *
     * @return the inner input element
     */
    protected WebElement getInputElement() {
        return this.findComponent(axesBuilder().child("input").build());
    }

    @Override
    public String toString() {
        return "MuiSliderThumbV5{" + "element=" + element + '}';
    }
}
