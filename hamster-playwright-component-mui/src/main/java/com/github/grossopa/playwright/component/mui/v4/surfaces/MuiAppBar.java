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

package com.github.grossopa.playwright.component.mui.v4.surfaces;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

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
 * The App Bar displays information and actions relating to the current screen.
 *
 * <p>The AppBar is typically used at the top of the application and contains branding, 
 * navigation, and action items. It can be positioned statically, fixed, or sticky.</p>
 *
 * @author Jack Yin
 * @since 1.12
 */
public class MuiAppBar extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "AppBar";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiAppBar(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the position type of the AppBar.
     *
     * @return the position ("fixed", "absolute", "sticky", or "static")
     */
    public String getPosition() {
        String className = getAttribute(CLASS);
        if (className != null) {
            if (className.contains(config.getCssPrefix() + "AppBar-positionFixed")) return "fixed";
            if (className.contains(config.getCssPrefix() + "AppBar-positionAbsolute")) return "absolute";
            if (className.contains(config.getCssPrefix() + "AppBar-positionSticky")) return "sticky";
        }
        return "static"; // default
    }

    /**
     * Gets the color variant of the AppBar.
     *
     * @return the color variant ("primary", "secondary", "inherit", "transparent", or "default")
     */
    public String getColor() {
        String className = getAttribute(CLASS);
        if (className != null) {
            if (className.contains(config.getCssPrefix() + "AppBar-colorPrimary")) return "primary";
            if (className.contains(config.getCssPrefix() + "AppBar-colorSecondary")) return "secondary";
            if (className.contains(config.getCssPrefix() + "AppBar-colorInherit")) return "inherit";
            if (className.contains(config.getCssPrefix() + "AppBar-colorTransparent")) return "transparent";
        }
        return "default";
    }

    /**
     * Checks if the AppBar is elevated (has shadow).
     *
     * @return true if AppBar has elevation, false otherwise
     */
    public boolean hasElevation() {
        String className = getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "elevation");
    }

    /**
     * Gets the AppBar title text.
     *
     * @return the title text, or null if no title
     */
    public String getTitle() {
        com.github.grossopa.playwright.core.WebComponent toolbar = findComponent("." + config.getCssPrefix() + "Toolbar-root");
        return toolbar != null ? toolbar.innerText() : null;
    }
}
