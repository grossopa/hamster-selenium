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
 * Tabs make it easy to explore and switch between different views.
 *
 * <p>Tabs organize content across different screens, data sets, and other interactions.
 * They work together with Tab components to create tabbed interfaces.</p>
 *
 * @see MuiTab
 * @author Jack Yin
 * @since 1.12
 */
public class MuiTabs extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Tabs";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiTabs(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets all tabs within the tabs container.
     *
     * @return list of tab WebComponents
     */
    public List<MuiTab> getTabs() {
        List<WebComponent> tabElements = findComponents("[role=\"tab\"]");
        return tabElements.stream()
                .map(tab -> new MuiTab(tab.locator(), driver, config))
                .toList();
    }

    /**
     * Gets the count of tabs.
     *
     * @return the number of tabs
     */
    public int getTabCount() {
        return getTabs().size();
    }

    /**
     * Gets the currently selected tab.
     *
     * @return the selected MuiTab, or null if none selected
     */
    public MuiTab getSelectedTab() {
        List<MuiTab> tabs = getTabs();
        return tabs.stream()
                .filter(MuiTab::isSelected)
                .findFirst()
                .orElse(null);
    }

    /**
     * Selects a tab by its index.
     *
     * @param index the zero-based index of the tab to select
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void selectTab(int index) {
        List<MuiTab> tabs = getTabs();
        if (index < 0 || index >= tabs.size()) {
            throw new IndexOutOfBoundsException(
                    "Tab index " + index + " is out of bounds. Available tabs: " + tabs.size());
        }
        tabs.get(index).click();
    }

    /**
     * Selects a tab by its label text.
     *
     * @param label the label text of the tab to select
     * @throws IllegalArgumentException if tab not found
     */
    public void selectTab(String label) {
        List<MuiTab> tabs = getTabs();
        MuiTab targetTab = tabs.stream()
                .filter(tab -> label.equals(tab.getLabel()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tab with label '" + label + "' not found"));
        targetTab.click();
    }

    /**
     * Checks if tabs are displayed vertically.
     *
     * @return true if vertical orientation, false if horizontal
     */
    public boolean isVertical() {
        String className = getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "Tabs-vertical");
    }
}
