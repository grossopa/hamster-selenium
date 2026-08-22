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

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Accordions contain creation flows and allow lightweight editing of an element.
 *
 * <p>Accordions are expandable panels that can show/hide content. They consist of three parts:
 * Summary (header), Details (content), and optional Actions (footer buttons).</p>
 *
 * @see <a href="https://material-ui.com/components/accordion/">
 * https://material-ui.com/components/accordion/</a>
 * @author Jack Yin
 * @since 1.12
 */
public class MuiAccordion extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Accordion";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiAccordion(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the accordion summary component (header).
     *
     * @return the accordion summary component, or null if not defined
     */
    public MuiAccordionSummary getAccordionSummary() {
        List<WebComponent> result = findComponents("." + config.getRootCss("AccordionSummary"));
        return result.isEmpty() ? null : new MuiAccordionSummary(result.get(0).locator(), driver, config);
    }

    /**
     * Gets the accordion details component (content area).
     *
     * @return the accordion details component, or null if not defined
     */
    public MuiAccordionDetails getAccordionDetails() {
        List<WebComponent> result = findComponents("." + config.getRootCss("AccordionDetails"));
        return result.isEmpty() ? null : new MuiAccordionDetails(result.get(0).locator(), driver, config);
    }

    /**
     * Gets the accordion actions component (footer buttons).
     *
     * @return the accordion actions component, or null if not defined
     */
    public MuiAccordionActions getAccordionActions() {
        List<WebComponent> result = findComponents("." + config.getRootCss("AccordionActions"));
        return result.isEmpty() ? null : new MuiAccordionActions(result.get(0).locator(), driver, config);
    }

    /**
     * Determines whether the accordion is expanded.
     *
     * @return true if expanded, false if collapsed
     */
    public boolean isExpanded() {
        MuiAccordionSummary summary = getAccordionSummary();
        return summary != null && summary.isExpanded();
    }

    /**
     * Expands the accordion to show details.
     */
    public void expand() {
        MuiAccordionSummary summary = getAccordionSummary();
        if (summary == null) {
            // Try to click the accordion itself
            locator.click();
        } else {
            summary.click();
        }
    }

    /**
     * Collapses the accordion to hide details.
     */
    public void collapse() {
        if (isExpanded()) {
            expand(); // Toggle to collapse
        }
    }

    /**
     * Checks if the accordion is enabled.
     *
     * @return true if enabled, false if disabled
     */
    @Override
    public boolean isEnabled() {
        String className = getAttribute(CLASS);
        return className == null || !className.contains(config.getCssPrefix() + "-disabled");
    }
}
