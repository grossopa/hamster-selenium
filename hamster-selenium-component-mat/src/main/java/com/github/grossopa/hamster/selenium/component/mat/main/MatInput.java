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
package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.utils.component.HasInput;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * {@code <mat-input>} is a normal text input enhanced with Material Design styling. It is typically used within a
 * {@code <mat-form-field>} wrapper.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/input/overview">
 * https://material.angular.io/components/input/overview</a>
 * @since 1.16
 */
public class MatInput extends AbstractMatComponent implements HasInput<WebComponent> {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Input";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatInput(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "input-element");
    }

    /**
     * Gets the value of the input.
     *
     * @return the current value of the input
     */
    public String getValue() {
        return this.getAttribute("value");
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
        return this.findComponent(By.tagName("input"));
    }

    @Override
    public boolean isEnabled() {
        return !this.attributeContains(CLASS, config.getCssPrefix() + "input-element-disabled");
    }
}
