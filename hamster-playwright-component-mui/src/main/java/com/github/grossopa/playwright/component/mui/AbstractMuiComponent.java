/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.component.mui;

import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.DefaultWebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static java.util.Objects.requireNonNull;

/**
 * The abstract Material UI component definition for Playwright.
 *
 * <p>This class provides common functionality for all Material UI components in the Playwright-based
 * automation framework. It extends {@link DefaultWebComponent} and implements {@link MuiComponent}
 * to provide version support and configuration access.</p>
 *
 * @author Jack Yin
 * @since 1.12
 */
public abstract class AbstractMuiComponent extends DefaultWebComponent implements MuiComponent {

    protected final MuiConfig config;

    /**
     * Constructs an instance with the delegated locator and driver.
     *
     * @param locator the Playwright locator for the component
     * @param driver  the component driver
     * @param config  the Material UI configuration
     */
    protected AbstractMuiComponent(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver);
        this.config = requireNonNull(config);
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4);
    }

    /**
     * Gets the current component name.
     *
     * @return the current component name
     */
    @Override
    public abstract String getComponentName();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractMuiComponent)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AbstractMuiComponent that = (AbstractMuiComponent) o;
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
