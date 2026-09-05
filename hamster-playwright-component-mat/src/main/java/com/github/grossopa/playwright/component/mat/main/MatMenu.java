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
package com.github.grossopa.playwright.component.mat.main;

import com.github.grossopa.playwright.component.mat.AbstractMatComponent;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.exception.MenuItemNotFoundException;
import com.github.grossopa.playwright.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;

import java.util.List;
import java.util.Objects;

/**
 * {@code <mat-menu>} is a floating panel containing list of options.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/menu/overview">
 * https://material.angular.io/components/menu/overview</a>
 * @since 1.15
 */
public class MatMenu extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Menu";

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatMenu(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "menu-panel");
    }

    /**
     * Finds the contained {@link MatMenuItem} list.
     *
     * @return the contained {@link MatMenuItem} list
     */
    public List<MatMenuItem> getMenuItems() {
        return this.findComponents("." + config.getCssPrefix() + "menu-item").stream().map(
                c -> new MatMenuItem(c, driver, config)).toList();
    }

    /**
     * Expands the menu item by index.
     *
     * @param index the menu item index
     * @return the expanded child menu
     */
    public MatMenu expandItemByIndex(int index) {
        MatMenuItem menuItem = getMenuItems().get(index);
        if (!menuItem.isExpandable()) {
            throw new MenuItemNotFoundException("the menu item with index " + index + " is not expandable.");
        }
        return menuItem.expand();
    }

    /**
     * Selects the menu item by index.
     *
     * @param index the menu item index
     */
    public void selectItemByIndex(int index) {
        getMenuItems().get(index).click();
    }

    /**
     * Expands the menu item by its text.
     *
     * @param text the menu item text
     * @return the expanded child menu
     */
    public MatMenu expandItemByText(String text) {
        MatMenuItem menuItem = findMenuItemByText(text);
        if (!menuItem.isExpandable()) {
            throw new MenuItemNotFoundException("the menu item with text " + text + " is not expandable.");
        }
        return menuItem.expand();
    }

    /**
     * Selects the menu item by its text.
     *
     * @param text the menu item text
     */
    public void selectItemByText(String text) {
        findMenuItemByText(text).click();
    }

    /**
     * Closes the menu by pressing the Escape key.
     */
    public void close() {
        this.press("Escape");
    }

    private MatMenuItem findMenuItemByText(String text) {
        return getMenuItems().stream().filter(item -> Objects.equals(text, item.textContent())).findFirst().orElseThrow(
                () -> new MenuItemNotFoundException("failed to find menu item with text " + text));
    }
}
