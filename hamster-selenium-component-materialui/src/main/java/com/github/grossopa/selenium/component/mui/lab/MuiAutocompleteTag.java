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

package com.github.grossopa.selenium.component.mui.lab;

import com.github.grossopa.selenium.component.mui.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The tags in multiple values feature of {@link MuiAutocomplete}.
 *
 * @author Jack Yin
 * @since 1.3
 */
public class MuiAutocompleteTag extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String NAME = "Autocomplete-tag";

    private final MuiAutocompleteTagLocators locators;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiAutocompleteTag(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, null);
    }


    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param locators optional, locators for label, value and delete button, defaulted to be created from {@link
     * MuiAutocompleteTagLocators#chipLocators(MuiConfig)}
     */
    public MuiAutocompleteTag(WebElement element, ComponentWebDriver driver, MuiConfig config,
            @Nullable MuiAutocompleteTagLocators locators) {
        super(element, driver, config);
        this.locators = defaultIfNull(locators, MuiAutocompleteTagLocators.chipLocators(config));
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    /**
     * Gets the label string.
     *
     * @return the label string.
     */
    public String getLabel() {
        return this.locators.getLabelFinder().apply(this);
    }

    /**
     * Gets the value string
     *
     * @return the value string
     */
    public String getValue() {
        return this.locators.getValueFinder().apply(this);
    }

    /**
     * Finds the delete button.
     *
     * @return the delete button.
     */
    public MuiButton getDeleteButton() {
        WebComponent component = this.findComponent(locators.getDeleteButtonLocator());
        return new MuiButton(component, driver, config);
    }

}
