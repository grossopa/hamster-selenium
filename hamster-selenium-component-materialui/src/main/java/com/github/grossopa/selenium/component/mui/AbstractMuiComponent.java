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

package com.github.grossopa.selenium.component.mui;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import org.openqa.selenium.WebElement;

import static java.util.Objects.requireNonNull;

/**
 * The abstract Material UI component definition contains a list of methods that could be used commonly for all Material
 * UI components.
 *
 * @author Jack Yin
 * @since 1.0
 */
public abstract class AbstractMuiComponent extends DefaultWebComponent implements MuiComponent {

    protected final MuiConfig config;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    protected AbstractMuiComponent(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver);
        this.config = requireNonNull(config);
    }

    @Override
    public MuiConfig config() {
        return config;
    }

    @Override
    public boolean isEnabled() {
        return !config.isDisabled(this);
    }

    /**
     * Validates whether the component is the expected type.
     *
     * @return true if the component matches the java type.
     */
    public boolean validate() {
        return config.validateComponentByCss(this, getComponentName());
    }

    /**
     * Gets the current component name
     *
     * @return the current component name
     */
    public abstract String getComponentName();


}
