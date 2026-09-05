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
package com.github.grossopa.playwright.component.mui.v4.feedback;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.*;
import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * Progress indicators commonly known as spinners, express an unspecified wait time or display the length of a
 * process.
 *
 * <p>CircularProgress is a circular variant of the progress indicator, it can either be determinate
 * (exposing a concrete progress value) or indeterminate.</p>
 *
 * @see <a href="https://mui.com/material-ui/react-progress/">
 * https://mui.com/material-ui/react-progress/</a>
 * @author Jack Yin
 * @since 1.15
 */
public class MuiCircularProgress extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "CircularProgress";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiCircularProgress(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Checks whether the circular progress is indeterminate, meaning it does not expose a concrete progress value.
     *
     * @return true if the circular progress is indeterminate, false if it is determinate
     */
    public boolean isIndeterminate() {
        String className = getAttribute(CLASS);
        return className.contains(config.getCssPrefix() + "CircularProgress-indeterminate");
    }

    /**
     * Gets the current progress value between 0 and 100 as exposed by the {@code aria-valuenow} attribute.
     *
     * <p>The value is only available for determinate circular progress. Indeterminate progress indicators do not
     * expose a value.</p>
     *
     * @return the current progress value, or {@code null} if the circular progress is indeterminate
     */
    public Double getValue() {
        String value = getAttribute("aria-valuenow");
        return StringUtils.isEmpty(value) ? null : Double.parseDouble(value);
    }

    /**
     * Gets the color of the circular progress.
     *
     * @return the color (e.g. "primary", "secondary", "success", "error", "info", "warning", "inherit")
     */
    public String getColor() {
        String className = getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();
        String[] colors = {"primary", "secondary", "success", "error", "info", "warning", "inherit"};
        for (String color : colors) {
            if (className.contains(cssPrefix + "CircularProgress-color"
                    + StringUtils.capitalize(color))) {
                return color;
            }
        }
        return "primary"; // default color
    }
}
