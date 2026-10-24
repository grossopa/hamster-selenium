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
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * Represents a Material UI NumberField component introduced in MUI v9.
 *
 * <p>The NumberField is an input component for capturing numeric values, built on top of Base UI's NumberField
 * primitive and styled to align with Material Design specifications. It provides increment and decrement buttons
 * for adjusting numeric input.</p>
 *
 * <p>The root element has the CSS class {@code MuiNumberField-root}. The component structure typically contains:
 * <ul>
 *   <li>An input element with CSS class {@code MuiNumberField-input}</li>
 *   <li>An increment button with CSS class {@code MuiNumberField-incrementButton}</li>
 *   <li>A decrement button with CSS class {@code MuiNumberField-decrementButton}</li>
 * </ul>
 *
 * <p>Key features:
 * <ul>
 *   <li>Get and set numeric values via {@link #getValue()} and {@link #setValue(String)}</li>
 *   <li>Increment and decrement via {@link #increment()} and {@link #decrement()}</li>
 *   <li>Access to min, max and step constraints</li>
 *   <li>Supports MUI version V6+</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.18
 * @see <a href="https://mui.com/material-ui/react-number-field/">
 *     https://mui.com/material-ui/react-number-field/</a>
 */
public class MuiNumberField extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "NumberField";

    /**
     * Constructs a MuiNumberField instance with the delegated element and root driver.
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiNumberField(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V6);
    }

    /**
     * Gets the current numeric value of the input.
     *
     * @return the current value as string, or empty string if not set
     */
    public String getValue() {
        WebComponent input = findInput();
        return input.getAttribute("value");
    }

    /**
     * Sets the numeric value by clearing the input and typing the new value.
     *
     * @param value the value to set
     */
    public void setValue(String value) {
        WebComponent input = findInput();
        input.clear();
        input.sendKeys(value);
    }

    /**
     * Clicks the increment button to increase the value.
     */
    public void increment() {
        findComponent(By.className(config.getCssPrefix() + "NumberField-incrementButton")).click();
    }

    /**
     * Clicks the decrement button to decrease the value.
     */
    public void decrement() {
        findComponent(By.className(config.getCssPrefix() + "NumberField-decrementButton")).click();
    }

    /**
     * Gets the minimum allowed value from the input element.
     *
     * @return the min attribute value, or null if not set
     */
    public String getMin() {
        return findInput().getAttribute("min");
    }

    /**
     * Gets the maximum allowed value from the input element.
     *
     * @return the max attribute value, or null if not set
     */
    public String getMax() {
        return findInput().getAttribute("max");
    }

    /**
     * Gets the step value from the input element.
     *
     * @return the step attribute value, or null if not set
     */
    public String getStep() {
        return findInput().getAttribute("step");
    }

    /**
     * Checks whether the number field is in an error state.
     *
     * @return true if the component has the error CSS class
     */
    public boolean isError() {
        return attributeContains(CLASS, config.getCssPrefix() + "NumberField-error");
    }

    /**
     * Finds the input element within this number field.
     *
     * @return the input web component
     */
    private WebComponent findInput() {
        return findComponent(By.className(config.getCssPrefix() + "NumberField-input"));
    }
}
