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

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.MuiVersion;
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
 * An alert displays a short, important message in a way that attracts
 * the user's attention without interrupting the user's task.
 *
 * <p>Alerts support different severity levels (success, info, warning, error)
 * and can include icons, messages, and close buttons.</p>
 *
 * @see <a href="https://material-ui.com/components/alert/">
 * https://material-ui.com/components/alert/</a>
 * @author Jack Yin
 * @since 1.12
 */
public class MuiAlert extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Alert";

    /**
     * Constructs an MuiAlert instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiAlert(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the severity level of the alert.
     *
     * @return the severity level ("success", "info", "warning", or "error")
     */
    public String getSeverity() {
        String className = getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();

        if (className.contains(cssPrefix + "Alert-standardSuccess") || 
            className.contains(cssPrefix + "Alert-filledSuccess") || 
            className.contains(cssPrefix + "Alert-outlinedSuccess")) {
            return "success";
        } else if (className.contains(cssPrefix + "Alert-standardInfo") || 
                   className.contains(cssPrefix + "Alert-filledInfo") || 
                   className.contains(cssPrefix + "Alert-outlinedInfo")) {
            return "info";
        } else if (className.contains(cssPrefix + "Alert-standardWarning") || 
                   className.contains(cssPrefix + "Alert-filledWarning") || 
                   className.contains(cssPrefix + "Alert-outlinedWarning")) {
            return "warning";
        } else if (className.contains(cssPrefix + "Alert-standardError") || 
                   className.contains(cssPrefix + "Alert-filledError") || 
                   className.contains(cssPrefix + "Alert-outlinedError")) {
            return "error";
        }

        return "info"; // default severity
    }

    /**
     * Gets the message text of the alert.
     *
     * @return the alert message text
     */
    public String getMessage() {
        WebComponent messageWrapper = findComponent("." + config.getCssPrefix() + "Alert-message");
        return messageWrapper != null ? messageWrapper.innerText() : null;
    }

    /**
     * Closes the alert if it has a close button.
     *
     * @throws UnsupportedOperationException if the alert doesn't have a close button
     */
    public void close() {
        WebComponent closeButton = findComponent("." + config.getCssPrefix() + "Alert-closeButton");
        if (closeButton == null) {
            throw new UnsupportedOperationException("This alert does not have a close button");
        }
        closeButton.click();
    }

    /**
     * Checks if the alert has a close button.
     *
     * @return true if the alert has a close button, false otherwise
     */
    public boolean hasCloseButton() {
        return findComponent("." + config.getCssPrefix() + "Alert-closeButton") != null;
    }

    /**
     * Checks if the alert has an icon.
     *
     * @return true if the alert has an icon, false otherwise
     */
    public boolean hasIcon() {
        return findComponent("." + config.getCssPrefix() + "Alert-icon") != null;
    }

    /**
     * Checks if the alert is dismissible (has close button).
     *
     * @return true if dismissible, false otherwise
     */
    public boolean isDismissible() {
        return hasCloseButton();
    }
}
