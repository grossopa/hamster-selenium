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
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;

/**
 * The Material UI Alert implementation
 *
 * <p>An alert displays a short, important message in a way that attracts
 * the user's attention without interrupting the user's task.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/alert/">
 * https://material-ui.com/components/alert/</a>
 * @since 1.0
 */
public class MuiAlert extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Alert";

    /**
     * Constructs an MuiAlert instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiAlert(WebElement element, ComponentWebDriver driver, MuiConfig config) {
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
     * Gets the severity level of the alert.
     *
     * @return the severity level (e.g. "success", "info", "warning", "error")
     */
    public String getSeverity() {
        String className = element.getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();

        if (className.contains(cssPrefix + "Alert-standardSuccess") || className.contains(cssPrefix + "Alert-filledSuccess") || className.contains(cssPrefix + "Alert-outlinedSuccess")) {
            return "success";
        } else if (className.contains(cssPrefix + "Alert-standardInfo") || className.contains(cssPrefix + "Alert-filledInfo") || className.contains(cssPrefix + "Alert-outlinedInfo")) {
            return "info";
        } else if (className.contains(cssPrefix + "Alert-standardWarning") || className.contains(cssPrefix + "Alert-filledWarning") || className.contains(cssPrefix + "Alert-outlinedWarning")) {
            return "warning";
        } else if (className.contains(cssPrefix + "Alert-standardError") || className.contains(cssPrefix + "Alert-filledError") || className.contains(cssPrefix + "Alert-outlinedError")) {
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
        WebComponent messageWrapper = this.findComponent(By.className(config.getCssPrefix() + "Alert-message"));
        return messageWrapper.getText();
    }

    /**
     * Closes the alert if it has a close button.
     */
    public void close() {
        try {
            WebComponent closeButton = this.findComponent(By.className(config.getCssPrefix() + "Alert-closeButton"));
            closeButton.click();
        } catch (Exception e) {
            throw new UnsupportedOperationException("This alert does not have a close button", e);
        }
    }

    /**
     * Checks if the alert has an icon.
     *
     * @return true if the alert has an icon, false otherwise
     */
    public boolean hasIcon() {
        try {
            this.findComponent(By.className(config.getCssPrefix() + "Alert-icon"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}