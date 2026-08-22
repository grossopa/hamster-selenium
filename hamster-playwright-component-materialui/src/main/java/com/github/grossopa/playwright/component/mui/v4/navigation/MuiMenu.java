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
import java.util.List;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * A Menu displays a list of choices on a temporary surface.
 *
 * <p>It appears when the user interacts with a button, or other control. Menus are used for 
 * dropdown selections, context menus, and navigation options.</p>
 *
 * @author Jack Yin
 * @since 1.12
 */
public class MuiMenu extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Menu";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiMenu(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Finds all menu items within the menu.
     *
     * @return list of menu item WebComponents
     */
    public List<MuiMenuItem> getMenuItems() {
        List<com.github.grossopa.playwright.core.WebComponent> items = 
                findComponents("[role=\"menuitem\"]");
        return items.stream()
                .map(item -> new MuiMenuItem(item.locator(), driver, config))
                .toList();
    }

    /**
     * Gets the count of menu items.
     *
     * @return the number of menu items
     */
    public int getItemCount() {
        return getMenuItems().size();
    }

    /**
     * Clicks a menu item by its text.
     *
     * @param itemText the text of the menu item to click
     * @throws IllegalArgumentException if item not found
     */
    public void clickItem(String itemText) {
        List<MuiMenuItem> items = getMenuItems();
        MuiMenuItem targetItem = items.stream()
                .filter(item -> itemText.equals(item.getText()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Menu item with text '" + itemText + "' not found"));
        targetItem.click();
    }

    /**
     * Clicks a menu item by its index.
     *
     * @param index the zero-based index of the menu item
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void clickItem(int index) {
        List<MuiMenuItem> items = getMenuItems();
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException(
                    "Menu item index " + index + " is out of bounds. Available items: " + items.size());
        }
        items.get(index).click();
    }

    /**
     * Checks if the menu is currently open/visible.
     *
     * @return true if menu is visible, false otherwise
     */
    public boolean isOpen() {
        return isVisible();
    }

    /**
     * Closes the menu by pressing Escape key.
     */
    public void close() {
        locator.press("Escape");
    }
}
