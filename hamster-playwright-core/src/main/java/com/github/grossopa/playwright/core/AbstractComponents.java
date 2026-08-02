/*
 * Copyright © 2025 the original author or authors.
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

package com.github.grossopa.playwright.core;

import static java.util.Objects.requireNonNull;

/**
 * The parent components class
 *
 * @since 1.12
 */
public abstract class AbstractComponents implements Components {
    protected WebComponent component;
    protected ComponentDriver driver;

    @Override
    public void setContext(WebComponent component, ComponentDriver driver) {
        this.component = requireNonNull(component);
        this.driver = requireNonNull(driver);
    }

    /**
     * Gets the target component to be converted
     *
     * @return the target component to be converted
     */
    public WebComponent getComponent() {
        return component;
    }

    /**
     * Gets the current root driver
     *
     * @return the current root driver
     */
    public ComponentDriver getDriver() {
        return driver;
    }
}
