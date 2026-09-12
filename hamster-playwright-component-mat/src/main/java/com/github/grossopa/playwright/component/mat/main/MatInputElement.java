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
package com.github.grossopa.playwright.component.mat.main;

import com.github.grossopa.playwright.component.mat.AbstractMatComponent;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.utils.component.HasInput;
import com.microsoft.playwright.Locator;

/**
 * {@code <mat-input>} is a normal text input enhanced with Material Design styling. It is typically used within a
 * {@code <mat-form-field>} wrapper.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/input/overview">
 * https://material.angular.io/components/input/overview</a>
 * @since 1.16
 */
public class MatInputElement extends AbstractMatComponent implements HasInput<WebComponent> {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Input";

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatInputElement(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "input-element");
    }

    /**
     * Gets the value of the input.
     *
     * <p>This method reads the DOM property {@code value} rather than the HTML attribute,
     * so it reflects the current input value after user interaction or programmatic updates.</p>
     *
     * @return the current value of the input
     */
    public String getValue() {
        return this.inputValue();
    }

    /**
     * Gets the placeholder text of the input.
     *
     * @return the placeholder text
     */
    public String getPlaceholder() {
        return this.getAttribute("placeholder");
    }

    /**
     * Gets the type attribute of the input.
     *
     * @return the input type (e.g. "text", "number", "email")
     */
    public String getType() {
        return this.getAttribute("type");
    }

    @Override
    public WebComponent getInput() {
        return this.findComponent("input");
    }

    @Override
    public boolean isEnabled() {
        return !attributeContains(CLASS, config.getCssPrefix() + "input-element-disabled");
    }
}
