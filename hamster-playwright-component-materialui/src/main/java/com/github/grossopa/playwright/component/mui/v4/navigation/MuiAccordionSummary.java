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
 * The accordion summary is the header section that displays when the accordion is collapsed.
 *
 * <p>It typically contains a title and an expand/collapse icon. Clicking on it toggles the accordion state.</p>
 *
 * @see MuiAccordion
 * @since 1.12
 */
public class MuiAccordionSummary extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "AccordionSummary";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiAccordionSummary(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Checks if the accordion is currently expanded.
     *
     * @return true if expanded, false if collapsed
     */
    public boolean isExpanded() {
        String ariaExpanded = getAttribute("aria-expanded");
        return "true".equals(ariaExpanded);
    }

    /**
     * Gets the expand/collapse icon element.
     *
     * @return the expand icon WebComponent
     */
    public com.github.grossopa.playwright.core.WebComponent getExpandIcon() {
        return findComponent("." + config.getCssPrefix() + "AccordionSummary-expandIconWrapper");
    }

    /**
     * Clicks the summary to toggle the accordion state.
     */
    @Override
    public void click() {
        locator.click();
    }

    /**
     * Gets the summary text content.
     *
     * @return the text displayed in the summary
     */
    public String getText() {
        return locator.innerText();
    }
}
