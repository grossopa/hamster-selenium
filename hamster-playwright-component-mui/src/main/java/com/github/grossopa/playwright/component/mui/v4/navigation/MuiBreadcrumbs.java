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
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Breadcrumbs allow users to make selections from a range of values and show their location in a hierarchy.
 *
 * <p>Breadcrumbs provide navigation links showing the user's current location within a site or application.
 * They typically display a path like: Home > Category > Current Page</p>
 *
 * @author Jack Yin
 * @see MuiLink
 * @since 1.12
 */
public class MuiBreadcrumbs extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Breadcrumbs";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiBreadcrumbs(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets all breadcrumb items/links.
     *
     * @return list of breadcrumb link WebComponents
     */
    public List<WebComponent> getItems() {
        return findComponents("a, [role=\"link\"]");
    }

    /**
     * Gets the count of breadcrumb items.
     *
     * @return the number of breadcrumb items
     */
    public int getItemCount() {
        return getItems().size();
    }

    /**
     * Gets the text of all breadcrumb items as a list.
     *
     * @return list of breadcrumb item texts
     */
    public List<String> getItemTexts() {
        return getItems().stream()
                .map(WebComponent::innerText)
                .collect(Collectors.toList());
    }

    /**
     * Clicks a breadcrumb item by its text.
     *
     * @param itemText the text of the breadcrumb item to click
     * @throws IllegalArgumentException if item not found
     */
    public void clickItem(String itemText) {
        List<WebComponent> items = getItems();
        WebComponent targetItem = items.stream()
                .filter(item -> itemText.equals(item.innerText()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Breadcrumb item with text '" + itemText + "' not found"));
        targetItem.click();
    }

    /**
     * Gets the separator element between breadcrumb items.
     *
     * @return the separator WebComponent
     */
    public WebComponent getSeparator() {
        return findComponent("." + config.getCssPrefix() + "Breadcrumbs-separator");
    }
}
