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

package com.github.grossopa.playwright.component.mui.v4.navigation;

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
 * Drawers provide access to destinations and key functionality in your application.
 *
 * <p>Drawers slide in from the edge of the screen and can be dismissed by clicking outside 
 * or pressing Escape. They're commonly used for navigation menus.</p>
 *
 * @author Jack Yin
 * @since 1.12
 */
public class MuiDrawer extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Drawer";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiDrawer(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Checks if the drawer is currently open/visible.
     *
     * @return true if drawer is open, false if closed
     */
    public boolean isOpen() {
        return isVisible();
    }

    /**
     * Closes the drawer by pressing Escape key.
     */
    public void close() {
        locator.press("Escape");
    }

    /**
     * Gets the anchor position of the drawer.
     *
     * @return the anchor position ("left", "right", "top", or "bottom")
     */
    public String getAnchor() {
        String className = getAttribute("class");
        if (className != null) {
            if (className.contains("anchorLeft")) return "left";
            if (className.contains("anchorRight")) return "right";
            if (className.contains("anchorTop")) return "top";
            if (className.contains("anchorBottom")) return "bottom";
        }
        return "left"; // default
    }

    /**
     * Checks if the drawer is modal (has backdrop).
     *
     * @return true if modal, false otherwise
     */
    public boolean isModal() {
        String className = getAttribute("class");
        return className != null && className.contains(config.getCssPrefix() + "Drawer-modal");
    }

    /**
     * Gets the drawer content text.
     *
     * @return the text content of the drawer
     */
    public String getContentText() {
        return locator.innerText();
    }
}
