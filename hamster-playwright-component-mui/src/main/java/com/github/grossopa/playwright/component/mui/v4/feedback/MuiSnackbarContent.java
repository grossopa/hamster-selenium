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

import static com.github.grossopa.utils.consts.HtmlConstants.BUTTON;

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
 * SnackbarContent is the inner content component of a Snackbar.
 *
 * <p>This component displays the actual message and optional action within a snackbar.
 * It can include text, icons, and action buttons.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/snackbars/">
 * https://material-ui.com/components/snackbars/</a>
 * @since 1.12
 */
public class MuiSnackbarContent extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "SnackbarContent";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiSnackbarContent(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the message text content.
     *
     * @return the message text
     */
    public String getMessage() {
        WebComponent messageWrapper = findComponent("." + config.getCssPrefix() + "SnackbarContent-message");
        return messageWrapper != null ? messageWrapper.innerText() : locator.innerText();
    }

    /**
     * Gets the action element/container.
     *
     * @return the action WebComponent, or null if no action
     */
    public WebComponent getAction() {
        return findComponent("." + config.getCssPrefix() + "SnackbarContent-action");
    }

    /**
     * Checks if there is an action button present.
     *
     * @return true if action exists, false otherwise
     */
    public boolean hasAction() {
        return getAction() != null;
    }

    /**
     * Gets the icon element (if present).
     *
     * @return the icon WebComponent, or null if no icon
     */
    public WebComponent getIcon() {
        return findComponent("." + config.getCssPrefix() + "SnackbarContent-icon");
    }

    /**
     * Checks if there is an icon present.
     *
     * @return true if icon exists, false otherwise
     */
    public boolean hasIcon() {
        return getIcon() != null;
    }

    /**
     * Clicks the action button (if present).
     *
     * @throws IllegalStateException if no action button exists
     */
    public void clickAction() {
        WebComponent action = getAction();
        if (action == null) {
            throw new IllegalStateException("No action button found in snackbar content");
        }
        
        WebComponent button = action.findComponent(BUTTON);
        if (button == null) {
            throw new IllegalStateException("No button found in action container");
        }
        button.click();
    }
}
