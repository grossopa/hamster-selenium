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
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;

/**
 * The Material UI Skeleton implementation
 *
 * <p>Display a placeholder preview of your content before the data gets loaded
 * to reduce load-time frustration.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/skeleton/">
 * https://material-ui.com/components/skeleton/</a>
 * @since 1.0
 */
public class MuiSkeleton extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Skeleton";

    /**
     * Constructs an MuiSkeleton instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiSkeleton(WebElement element, ComponentWebDriver driver, MuiConfig config) {
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
     * Gets the variant type of the skeleton.
     *
     * @return the variant type (e.g. "text", "rectangular", "circular")
     */
    public String getVariant() {
        String className = element.getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();

        if (className.contains(cssPrefix + "Skeleton-rectangular")) {
            return "rectangular";
        } else if (className.contains(cssPrefix + "Skeleton-circular")) {
            return "circular";
        } else {
            return "text"; // default variant
        }
    }

    /**
     * Gets the animation type of the skeleton.
     *
     * @return the animation type (e.g. "pulse", "wave", "false" for no animation)
     */
    public String getAnimation() {
        String className = element.getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();

        if (className.contains(cssPrefix + "Skeleton-pulse")) {
            return "pulse";
        } else if (className.contains(cssPrefix + "Skeleton-wave")) {
            return "wave";
        } else {
            return "false"; // no animation
        }
    }

    /**
     * Gets the width of the skeleton.
     *
     * @return the width in pixels
     */
    public String getWidth() {
        return element.getCssValue("width");
    }

    /**
     * Gets the height of the skeleton.
     *
     * @return the height in pixels
     */
    public String getHeight() {
        return element.getCssValue("height");
    }
}
