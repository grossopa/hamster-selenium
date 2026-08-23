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
package com.github.grossopa.playwright.component.mui.v4.navigation;

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
 * BottomNavigationAction represents a single action button within a BottomNavigation bar.
 *
 * <p>Each action typically includes an icon and a label, and can be selected to navigate 
 * to different sections of the application.</p>
 *
 * @see MuiBottomNavigation
 * @author Jack Yin
 * @since 1.12
 */
public class MuiBottomNavigationAction extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "BottomNavigationAction";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiBottomNavigationAction(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the action label text.
     *
     * @return the displayed label
     */
    public String getLabel() {
        return locator.innerText();
    }

    /**
     * Checks if the action is currently selected/active.
     *
     * @return true if selected, false otherwise
     */
    public boolean isSelected() {
        String ariaSelected = getAttribute("aria-selected");
        return "true".equals(ariaSelected);
    }

    /**
     * Checks if the action is disabled.
     *
     * @return true if disabled, false if enabled
     */
    @Override
    public boolean isDisabled() {
        String className = getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();
        return className != null && className.contains(cssPrefix + "disabled");
    }

    /**
     * Checks if the action is enabled.
     *
     * @return true if enabled, false if disabled
     */
    @Override
    public boolean isEnabled() {
        return !isDisabled();
    }

    /**
     * Clicks the action to select it and navigate.
     *
     * @throws IllegalStateException if the action is disabled
     */
    @Override
    public void click() {
        if (isDisabled()) {
            throw new IllegalStateException("Cannot click disabled navigation action");
        }
        locator.click();
    }
}
