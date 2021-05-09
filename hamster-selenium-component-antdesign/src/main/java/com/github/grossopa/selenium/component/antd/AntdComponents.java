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

package com.github.grossopa.selenium.component.antd;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.component.antd.general.AntdButton;
import com.github.grossopa.selenium.core.component.AbstractComponents;
import com.github.grossopa.selenium.core.component.WebComponent;

import static java.util.Objects.requireNonNull;

/**
 * This class contains all Antd components that a {@link com.github.grossopa.selenium.core.component.WebComponent} could
 * be converted to.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class AntdComponents extends AbstractComponents {

    AntdConfig config;

    /**
     * Constructs an instance with default {@link AntdConfig}.
     */
    public AntdComponents() {
        this(new AntdConfig());
    }

    /**
     * Constructs an instance with provided {@link AntdConfig}.
     *
     * @param config the Antd configuration instance
     */
    public AntdComponents(AntdConfig config) {
        this.config = requireNonNull(config);
    }

    /**
     * Creates an instance of {@link AntdComponents} with default {@link AntdConfig}.
     *
     * @return the newly created instance with default {@link AntdConfig}.
     */
    public static AntdComponents antd() {
        return new AntdComponents();
    }

    /**
     * Creates an instance of {@link AntdComponents} with given {@link AntdConfig}.
     *
     * @param config the config instance
     * @return the instance of {@link AntdComponents} with given {@link AntdConfig}.
     */
    public static AntdComponents antd(AntdConfig config) {
        return new AntdComponents(config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdButton} instance.
     *
     * @return wrapped {@link AntdButton} instance on the given component
     */
    public AntdButton toButton() {
        return new AntdButton(component, driver, config);
    }
}
