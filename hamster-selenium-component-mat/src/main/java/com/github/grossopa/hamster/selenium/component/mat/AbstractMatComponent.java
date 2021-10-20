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

package com.github.grossopa.hamster.selenium.component.mat;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import org.openqa.selenium.WebElement;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * The abstract Material UI component definition contains a list of methods that could be used commonly for all Material
 * UI Angular components.
 *
 * @author Jack Yin
 * @since 1.6
 */
public abstract class AbstractMatComponent extends DefaultWebComponent implements MatComponent {

    protected final MatConfig config;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    protected AbstractMatComponent(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver);
        requireNonNull(config);
        this.config = config;
    }

    @Override
    public MatConfig getConfig() {
        return config;
    }

    @Override
    public boolean isSelected() {
        return config.isChecked(this);
    }

    @Override
    public boolean isEnabled() {
        return !config.isDisabled(this);
    }

    /**
     * Returns the component name.
     *
     * @return the component name
     */
    public abstract String getComponentName();

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
        return String.format("%s{element=%s}", getClass().getName(), element);
    }
}
