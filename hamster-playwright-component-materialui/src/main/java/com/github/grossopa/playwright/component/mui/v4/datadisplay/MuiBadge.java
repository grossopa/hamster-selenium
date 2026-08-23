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
package com.github.grossopa.playwright.component.mui.v4.datadisplay;

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
 * Badge generates a small badge to the top-right of its child(ren).
 *
 * <p>Badges are used to highlight an item's status for quick recognition. They can display
 * a number, text, or just a dot indicator.</p>
 *
 * @see <a href="https://material-ui.com/components/badges/">https://material-ui.com/components/badges/</a>
 * @author Jack Yin
 * @since 1.12
 */
public class MuiBadge extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Badge";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiBadge(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the badge content/text.
     *
     * @return the badge text content
     */
    public String getBadgeContent() {
        WebComponent badge = findComponent("." + config.getCssPrefix() + "Badge-badge");
        return badge != null ? badge.innerText() : null;
    }

    /**
     * Checks if the badge is visible (not hidden).
     *
     * @return true if the badge is visible, false if it has the invisible class
     */
    @Override
    public boolean isVisible() {
        WebComponent badge = findComponent("." + config.getCssPrefix() + "Badge-badge");
        if (badge == null) {
            return false;
        }
        String className = badge.getAttribute(CLASS);
        return className == null || !className.contains(config.getCssPrefix() + "Badge-invisible");
    }

    /**
     * Checks if the badge has a dot variant (just a dot, no text).
     *
     * @return true if it's a dot badge, false otherwise
     */
    public boolean isDotVariant() {
        String className = getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "Badge-dot");
    }
}
