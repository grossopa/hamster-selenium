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
package com.github.grossopa.playwright.component.mat;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.DefaultWebComponent;
import com.microsoft.playwright.Locator;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * The abstract Material UI Angular component definition for Playwright, contains a list of methods that could be used
 * commonly for all Angular Material components.
 *
 * @author Jack Yin
 * @since 1.15
 */
public abstract class AbstractMatComponent extends DefaultWebComponent implements MatComponent {

    /**
     * The class attribute name
     */
    public static final String CLASS = "class";

    protected final MatConfig config;

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    protected AbstractMatComponent(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver);
        this.config = requireNonNull(config);
    }

    @Override
    public MatConfig getConfig() {
        return config;
    }

    /**
     * Checks whether the component is selected by its class attribute.
     *
     * @return true if the component is selected
     */
    public boolean isSelected() {
        return attributeContains(CLASS, config.getIsSelectedCss());
    }

    @Override
    public boolean isEnabled() {
        return !config.isDisabled(this);
    }

    /**
     * Checks whether the given attribute value contains the given string.
     *
     * @param attributeName the attribute name
     * @param value the value to check
     * @return true if the attribute value contains the given string
     */
    protected boolean attributeContains(String attributeName, String value) {
        String attributeValue = getAttribute(attributeName);
        return attributeValue != null && attributeValue.contains(value);
    }

    @Override
    public String getComponentTagName() {
        Object tagName = locator.evaluate("el => el.tagName");
        return tagName == null ? null : tagName.toString().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractMatComponent)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AbstractMatComponent that = (AbstractMatComponent) o;
        return Objects.equals(config, that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), config);
    }

    @Override
    public String toString() {
        return String.format("%s{locator=%s}", getClass().getSimpleName(), locator());
    }
}
