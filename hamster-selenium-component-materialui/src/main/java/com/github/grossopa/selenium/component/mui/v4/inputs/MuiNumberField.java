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
 * <p>The NumberField is an input component for capturing numeric values. It is not a native MUI component but
 * rather a <strong>component recipe</strong> composed from Base UI's NumberField primitive styled with standard
 * MUI building blocks: {@code FormControl}, {@code OutlinedInput}, {@code InputLabel}, {@code FormHelperText},
 * and {@code IconButton} for increment/decrement adornments.</p>
 *
 * <p>The root element is a {@code FormControl} wrapper. The internal structure typically contains:
 * <ul>
 *   <li>An input element with CSS class {@code MuiOutlinedInput-input}</li>
 *   <li>Increment and decrement buttons using {@code MuiIconButton-root} inside {@code InputAdornment}</li>
 *   <li>Error state indicated by {@code Mui-error} CSS class on the FormControl root</li>
 * </ul>
 *
 * <p>Key features:
 * <ul>
 *   <li>Get and set numeric values via {@link #getValue()} and {@link #setValue(String)}</li>
 *   <li>Increment and decrement via {@link #increment()} and {@link #decrement()}</li>
 *   <li>Access to min, max and step constraints from the underlying input element</li>
 *   <li>Error state detection via {@link #isError()}</li>
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

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "FormControl-root");
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
     * Clicks the increment button to increase the value. The increment button is the first
     * {@code MuiIconButton} within the number field adornments.
     */
    public void increment() {
        WebComponent button = findIncrementButton();
        button.click();
    }

    /**
     * Clicks the decrement button to decrease the value. The decrement button is the last
     * {@code MuiIconButton} within the number field adornments.
     */
    public void decrement() {
        WebComponent button = findDecrementButton();
        button.click();
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
     * Checks whether the number field is in an error state. The error state is indicated by the
     * {@code Mui-error} CSS class on the FormControl root element.
     *
     * @return true if the component has the error CSS class
     */
    public boolean isError() {
        return attributeContains(CLASS, config.getCssPrefix() + "-error");
    }

    /**
     * Finds the input element within this number field. Uses the standard MUI OutlinedInput
     * input CSS class.
     *
     * @return the input web component
     */
    private WebComponent findInput() {
        return findComponent(By.className(config.getCssPrefix() + "OutlinedInput-input"));
    }

    /**
     * Finds the increment button (first IconButton in the number field adornments,
     * with aria-label "Increment").
     *
     * @return the increment button web component
     */
    private WebComponent findIncrementButton() {
        return findComponent(By.className(config.getCssPrefix() + "IconButton-root"));
    }

    /**
     * Finds the decrement button (last IconButton in the number field adornments,
     * with aria-label "Decrement").
     *
     * @return the decrement button web component
     */
    private WebComponent findDecrementButton() {
        var buttons = findComponents(By.className(config.getCssPrefix() + "IconButton-root"));
        return buttons.get(buttons.size() - 1);
    }
}
