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
import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Paper is a basic surface component that displays content with elevation.
 *
 * <p>Paper implements Material Design's concept of digital paper. It can have different 
 * elevation levels (shadows) and variants (outlined, filled).</p>
 *
 * @see <a href="https://material-ui.com/components/paper/">
 * https://material-ui.com/components/paper/</a>
 * @author Jack Yin
 * @since 1.12
 */
public class MuiPaper extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Paper";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiPaper(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the elevation level of the Paper (shadow depth).
     *
     * @return the elevation level (0-24), or 0 if not set
     */
    public int getElevation() {
        String className = getAttribute(CLASS);
        if (className != null) {
            for (int i = 0; i <= 24; i++) {
                if (className.contains(config.getCssPrefix() + "elevation" + i)) {
                    return i;
                }
            }
        }
        return 0; // default
    }

    /**
     * Gets the variant type of the Paper.
     *
     * @return the variant ("elevation", "outlined", or "default")
     */
    public String getVariant() {
        String className = getAttribute(CLASS);
        if (className != null) {
            if (className.contains(config.getCssPrefix() + "Paper-outlined")) return "outlined";
            if (className.contains(config.getCssPrefix() + "Paper-elevation")) return "elevation";
        }
        return "default";
    }

    /**
     * Checks if the Paper has rounded corners.
     *
     * @return true if rounded, false if square
     */
    public boolean isRounded() {
        String className = getAttribute(CLASS);
        return className == null || !className.contains(config.getCssPrefix() + "Paper-rounded");
    }

    /**
     * Checks if the Paper is squared (no border radius).
     *
     * @return true if squared, false if rounded
     */
    public boolean isSquared() {
        String className = getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "Paper-rounded");
    }

    /**
     * Gets the Paper content text.
     *
     * @return the text content
     */
    public String getText() {
        return locator.innerText();
    }
}
