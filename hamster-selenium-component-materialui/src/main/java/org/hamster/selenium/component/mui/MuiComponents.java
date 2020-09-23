/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.component.mui;

import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.component.AbstractComponents;
import org.openqa.selenium.By;

import static java.util.Objects.requireNonNull;

/**
 * Contains the definition of Material UI components.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiComponents extends AbstractComponents {

    private final MuiConfig config;

    /**
     * Constructs an instance with default {@link MuiConfig}.
     */
    public MuiComponents() {
        this(new MuiConfig());
    }

    /**
     * Constructs an instance with provided {@link MuiConfig}.
     *
     * @param config
     *         the MUI configuration instance
     */
    public MuiComponents(MuiConfig config) {
        this.config = requireNonNull(config);
    }

    public static MuiComponents mui() {
        return new MuiComponents();
    }

    public static MuiComponents mui(MuiConfig config) {
        return new MuiComponents(config);
    }

    public MuiButton toButton() {
        return new MuiButton(component, driver, config);
    }

    public MuiButtonGroup toButtonGroup() {
        return new MuiButtonGroup(component, driver, config);
    }

    public MuiCheckbox toCheckbox() {
        return new MuiCheckbox(component, driver, config);
    }

    public MuiSelect toSelect(By optionLocator) {
        requireNonNull(optionLocator);
        return new MuiSelect(component, driver, config, optionLocator);
    }

    public MuiSelect toSelect(By optionLocator, String optionValueAttribute) {
        requireNonNull(optionLocator);
        requireNonNull(optionValueAttribute);
        return new MuiSelect(component, driver, config, optionLocator, optionValueAttribute);
    }
}
