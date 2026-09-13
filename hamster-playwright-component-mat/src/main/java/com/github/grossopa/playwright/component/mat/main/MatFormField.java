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
import com.github.grossopa.playwright.core.DefaultWebComponent;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.utils.component.HasInput;
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
public class MatFormField extends AbstractMatComponent implements HasInput<WebComponent> {

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
        return attributeContains(CLASS, config.getComponentCssPrefix() + "form-field");
    }

    /**
     * Finds the prefix component.
     *
     * @return the prefix component
     */
    public WebComponent getPrefix() {
        // MDC uses .mat-mdc-form-field-text-prefix; legacy uses .mat-form-field-prefix
        Locator mdcPrefix = locator.locator("." + config.getComponentCssPrefix()
                + "form-field-text-prefix").first();
        if (mdcPrefix.count() > 0) {
            return new DefaultWebComponent(mdcPrefix, driver);
        }
        return this.findComponent("." + config.getComponentCssPrefix() + "form-field-prefix");
    }

    /**
     * Finds the infix component which contains the input element.
     *
     * @return the infix component
     */
    public WebComponent getInfix() {
        return this.findComponent("." + config.getComponentCssPrefix() + "form-field-infix");
    }

    /**
     * Finds the suffix component.
     *
     * @return the suffix component
     */
    public WebComponent getSuffix() {
        // MDC uses .mat-mdc-form-field-text-suffix; legacy uses .mat-form-field-suffix
        Locator mdcSuffix = locator.locator("." + config.getComponentCssPrefix()
                + "form-field-text-suffix").first();
        if (mdcSuffix.count() > 0) {
            return new DefaultWebComponent(mdcSuffix, driver);
        }
        return this.findComponent("." + config.getComponentCssPrefix() + "form-field-suffix");
    }

    /**
     * Finds the hint component.
     *
     * @return the hint component
     */
    public WebComponent getHint() {
        // MDC uses .mat-mdc-form-field-hint; legacy uses .mat-hint
        Locator mdcHint = locator.locator("." + config.getComponentCssPrefix()
                + "form-field-hint").first();
        if (mdcHint.count() > 0) {
            return new DefaultWebComponent(mdcHint, driver);
        }
        return this.findComponent("." + config.getComponentCssPrefix() + "hint");
    }

    /**
     * Finds the inner input element within the infix.
     *
     * @return the inner input element
     */
    @Override
    public WebComponent getInput() {
        return this.getInfix().findComponent("input");
    }

    /**
     * Finds the label element.
     *
     * @return the label element
     */
    public WebComponent getLabel() {
        // MDC structure uses .mat-mdc-floating-label; legacy uses .mat-form-field-label
        Locator legacy = locator.locator(
                "." + config.getComponentCssPrefix() + "form-field-label");
        Locator mdc = locator.locator(
                "." + config.getComponentCssPrefix() + "floating-label");
        return new DefaultWebComponent(legacy.or(mdc).first(), driver);
    }

    /**
     * Finds the error component.
     *
     * @return the error component
     */
    public WebComponent getError() {
        // MDC uses .mat-mdc-form-field-error; legacy uses .mat-error
        String mdcSelector = "." + config.getComponentCssPrefix()
                + "form-field-subscript-wrapper ." + config.getComponentCssPrefix() + "form-field-error";
        String legacySelector = "." + config.getComponentCssPrefix()
                + "form-field-subscript-wrapper ." + config.getComponentCssPrefix() + "error";
        Locator mdcLocator = locator.locator(mdcSelector);
        if (mdcLocator.count() > 0) {
            return new DefaultWebComponent(mdcLocator.first(), driver);
        }
        return new DefaultWebComponent(locator.locator(legacySelector).first(), driver);
    }
}
