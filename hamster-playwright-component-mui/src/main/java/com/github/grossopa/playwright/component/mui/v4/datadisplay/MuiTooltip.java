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

package com.github.grossopa.playwright.component.mui.v4.datadisplay;

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
 * Tooltips display informative text when users hover over, focus on, or tap an element.
 *
 * <p>Tooltips are used to provide additional information about an element. They appear
 * on user interaction (hover, focus, or tap) and disappear when the interaction ends.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/tooltips/">https://material-ui.com/components/tooltips/</a>
 * @since 1.12
 */
public class MuiTooltip extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Tooltip";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiTooltip(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the tooltip text/content.
     *
     * <p>Note: The tooltip must be visible (triggered by hover/focus) to read its content.</p>
     *
     * @return the tooltip text content
     */
    public String getTooltipText() {
        // Try to find the tooltip popup
        WebComponent tooltip = driver.findComponent("[role=\"tooltip\"]");
        return tooltip != null ? tooltip.innerText() : null;
    }

    /**
     * Triggers the tooltip to show by hovering over it.
     */
    public void show() {
        locator.hover();
    }

    /**
     * Hides the tooltip by moving mouse away.
     */
    public void hide() {
        // Move mouse away from the element
        locator.blur();
    }

    /**
     * Checks if the tooltip is currently visible.
     *
     * @return true if tooltip popup is visible, false otherwise
     */
    public boolean isVisible() {
        WebComponent tooltip = driver.findComponent("[role=\"tooltip\"]:visible");
        return tooltip != null;
    }

    /**
     * Gets the placement position of the tooltip.
     *
     * @return the placement attribute value (e.g., "top", "bottom", "left", "right")
     */
    public String getPlacement() {
        return getAttribute("data-placement");
    }
}
