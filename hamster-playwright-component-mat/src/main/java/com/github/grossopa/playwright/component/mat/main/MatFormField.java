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
import com.microsoft.playwright.Locator;

/**
 * {@code <mat-form-field>} is a component used to wrap several Angular Material components and apply common text
 * field styles such as the underline, floating label, and hint messages.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/form-field/overview">
 * https://material.angular.io/components/form-field/overview</a>
 * @since 1.15
 */
public class MatFormField extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "FormField";

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatFormField(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "form-field");
    }

    /**
     * Finds the prefix component.
     *
     * @return the prefix component
     */
    public WebComponent getPrefix() {
        return this.findComponent("." + config.getCssPrefix() + "form-field-prefix");
    }

    /**
     * Finds the infix component which contains the input element.
     *
     * @return the infix component
     */
    public WebComponent getInfix() {
        return this.findComponent("." + config.getCssPrefix() + "form-field-infix");
    }

    /**
     * Finds the suffix component.
     *
     * @return the suffix component
     */
    public WebComponent getSuffix() {
        return this.findComponent("." + config.getCssPrefix() + "form-field-suffix");
    }

    /**
     * Finds the hint component.
     *
     * @return the hint component
     */
    public WebComponent getHint() {
        return this.findComponent("." + config.getCssPrefix() + "hint");
    }

    /**
     * Finds the inner input element within the infix.
     *
     * @return the inner input element
     */
    public WebComponent getInput() {
        return this.getInfix().findComponent("input");
    }

    /**
     * Finds the label element.
     *
     * @return the label element
     */
    public WebComponent getLabel() {
        return this.findComponent("." + config.getCssPrefix() + "form-field-label");
    }

    /**
     * Finds the error component.
     *
     * @return the error component
     */
    public WebComponent getError() {
        return this.findComponent("." + config.getCssPrefix() + "form-field-subscript-wrapper ."
                + config.getCssPrefix() + "error");
    }
}
