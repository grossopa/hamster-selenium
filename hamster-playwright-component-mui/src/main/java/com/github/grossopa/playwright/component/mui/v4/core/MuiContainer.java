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

package com.github.grossopa.playwright.component.mui.v4.core;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Container centers your content horizontally. It's the most basic layout element.
 *
 * <p>Containers provide a means to center and horizontally pad your site's contents. 
 * They can be fixed width or fluid (max-width based on viewport).</p>
 *
 * @author Jack Yin
 * @since 1.12
 */
public class MuiContainer extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Container";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiContainer(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<com.github.grossopa.playwright.component.mui.MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Gets the maximum width breakpoint of the container.
     *
     * @return the max width ("xs", "sm", "md", "lg", "xl", or "false" for full width)
     */
    public String getMaxWidth() {
        String className = getAttribute("class");
        if (className != null) {
            if (className.contains(config.getCssPrefix() + "Container-maxWidthXs")) return "xs";
            if (className.contains(config.getCssPrefix() + "Container-maxWidthSm")) return "sm";
            if (className.contains(config.getCssPrefix() + "Container-maxWidthMd")) return "md";
            if (className.contains(config.getCssPrefix() + "Container-maxWidthLg")) return "lg";
            if (className.contains(config.getCssPrefix() + "Container-maxWidthXl")) return "xl";
        }
        return "md"; // default
    }

    /**
     * Checks if the container uses fixed width (instead of max-width).
     *
     * @return true if fixed width, false if max-width
     */
    public boolean isFixed() {
        String className = getAttribute("class");
        return className != null && className.contains(config.getCssPrefix() + "Container-fixed");
    }

    /**
     * Checks if the container disables gutters (horizontal padding).
     *
     * @return true if gutters are disabled, false otherwise
     */
    public boolean isDisableGutters() {
        String className = getAttribute("class");
        return className != null && className.contains(config.getCssPrefix() + "Container-disableGutters");
    }

    /**
     * Gets the container content text.
     *
     * @return the text content
     */
    public String getText() {
        return locator.innerText();
    }
}
