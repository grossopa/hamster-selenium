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

package com.github.grossopa.selenium.component.mui.v4.feedback;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;

/**
 * The Material UI LinearProgress implementation.
 *
 * <p>Progress indicators commonly known as spinners, express an unspecified wait time or display the length of a
 * process.</p>
 *
 * @author Jack Yin
 * @see <a href="https://mui.com/material-ui/react-progress/">
 * https://mui.com/material-ui/react-progress/</a>
 * @since 1.15
 */
public class MuiLinearProgress extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "LinearProgress";

    /**
     * Constructs an MuiLinearProgress instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiLinearProgress(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    /**
     * Gets the variant of the linear progress.
     *
     * @return the variant ("determinate", "indeterminate", "buffer" or "query")
     */
    public String getVariant() {
        String className = element.getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();

        if (className.contains(cssPrefix + "LinearProgress-determinate")) {
            return "determinate";
        } else if (className.contains(cssPrefix + "LinearProgress-buffer")) {
            return "buffer";
        } else if (className.contains(cssPrefix + "LinearProgress-query")) {
            return "query";
        } else {
            return "indeterminate"; // default variant
        }
    }

    /**
     * Checks whether the linear progress is indeterminate, meaning it does not expose a concrete progress value.
     *
     * @return true if the linear progress variant is indeterminate or query, false otherwise
     */
    public boolean isIndeterminate() {
        return "indeterminate".equals(getVariant()) || "query".equals(getVariant());
    }

    /**
     * Gets the current progress value between 0 and 1 as exposed by the {@code aria-valuenow} attribute.
     *
     * <p>The value is only available for determinate and buffer linear progress. Indeterminate and query progress
     * indicators do not expose a value.</p>
     *
     * @return the current progress value, or {@code null} if the linear progress is indeterminate or query
     */
    public Double getValue() {
        String value = element.getAttribute("aria-valuenow");
        return StringUtils.isEmpty(value) ? null : Double.parseDouble(value);
    }

    /**
     * Gets the color of the linear progress.
     *
     * @return the color (e.g. "primary", "secondary", "success", "error", "info", "warning", "inherit")
     */
    public String getColor() {
        String className = element.getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();
        String[] colors = {"primary", "secondary", "success", "error", "info", "warning", "inherit"};
        for (String color : colors) {
            if (className.contains(cssPrefix + "LinearProgress-color"
                    + StringUtils.capitalize(color))) {
                return color;
            }
        }
        return "primary"; // default color
    }
}
