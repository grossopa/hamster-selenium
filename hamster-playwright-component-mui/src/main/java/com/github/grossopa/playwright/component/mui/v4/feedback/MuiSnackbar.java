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

package com.github.grossopa.playwright.component.mui.v4.feedback;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Snackbars provide brief messages about app processes at the bottom of the screen.
 *
 * <p>Snackbars inform users of a process that an app has performed or will perform. 
 * They appear temporarily, towards the bottom of the screen, and don't disrupt the user experience.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/snackbars/">
 * https://material-ui.com/components/snackbars/</a>
 * @since 1.12
 */
public class MuiSnackbar extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Snackbar";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiSnackbar(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the snackbar content/message.
     *
     * @return the snackbar message text
     */
    public String getMessage() {
        WebComponent content = findComponent("." + config.getCssPrefix() + "SnackbarContent-message");
        return content != null ? content.innerText() : null;
    }

    /**
     * Gets the snackbar action button (if present).
     *
     * @return the action button WebComponent, or null if no action
     */
    public WebComponent getAction() {
        return findComponent("." + config.getCssPrefix() + "SnackbarContent-action");
    }

    /**
     * Clicks the action button in the snackbar.
     *
     * @throws IllegalStateException if no action button exists
     */
    public void clickAction() {
        WebComponent action = getAction();
        if (action == null) {
            throw new IllegalStateException("This snackbar does not have an action button");
        }
        action.click();
    }

    /**
     * Checks if the snackbar has an action button.
     *
     * @return true if action button exists, false otherwise
     */
    public boolean hasAction() {
        return getAction() != null;
    }

    /**
     * Checks if the snackbar is currently visible/open.
     *
     * @return true if snackbar is visible, false otherwise
     */
    public boolean isOpen() {
        return isVisible();
    }

    /**
     * Closes the snackbar by clicking the close button (if available).
     */
    public void close() {
        WebComponent closeButton = findComponent("[aria-label=\"close\"]");
        if (closeButton != null) {
            closeButton.click();
        }
    }

    /**
     * Gets the anchor origin position of the snackbar.
     *
     * @return the anchor position (e.g., "bottom-left", "top-right")
     */
    public String getAnchorOrigin() {
        // Check CSS classes for positioning
        String className = getAttribute(CLASS);
        if (className.contains("bottom")) {
            if (className.contains("left")) return "bottom-left";
            if (className.contains("right")) return "bottom-right";
            if (className.contains("center")) return "bottom-center";
        } else if (className.contains("top")) {
            if (className.contains("left")) return "top-left";
            if (className.contains("right")) return "top-right";
            if (className.contains("center")) return "top-center";
        }
        return "bottom-left"; // default
    }
}
