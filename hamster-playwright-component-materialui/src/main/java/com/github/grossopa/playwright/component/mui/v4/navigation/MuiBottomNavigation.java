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
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.*;

/**
 * Bottom navigation bars allow movement between primary destinations in an app.
 *
 * <p>Bottom navigation typically contains three to five actions at the bottom of a screen.
 * Each action includes an icon and an optional text label.</p>
 *
 * @see MuiBottomNavigationAction
 * @since 1.12
 */
public class MuiBottomNavigation extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "BottomNavigation";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiBottomNavigation(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets all navigation actions/buttons.
     *
     * @return list of navigation action WebComponents
     */
    public List<WebComponent> getActions() {
        return findComponents("[role=\"tab\"], button");
    }

    /**
     * Gets the count of navigation actions.
     *
     * @return the number of actions
     */
    public int getActionCount() {
        return getActions().size();
    }

    /**
     * Clicks a navigation action by its index.
     *
     * @param index the zero-based index of the action
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void clickAction(int index) {
        List<WebComponent> actions = getActions();
        if (index < 0 || index >= actions.size()) {
            throw new IndexOutOfBoundsException(
                    "Action index " + index + " is out of bounds. Available actions: " + actions.size());
        }
        actions.get(index).click();
    }

    /**
     * Clicks a navigation action by its label text.
     *
     * @param label the label text of the action
     * @throws IllegalArgumentException if action not found
     */
    public void clickAction(String label) {
        List<WebComponent> actions = getActions();
        WebComponent targetAction = actions.stream()
                .filter(action -> label.equals(action.innerText()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Navigation action with label '" + label + "' not found"));
        targetAction.click();
    }

    /**
     * Gets the currently selected/active action index.
     *
     * @return the zero-based index of the selected action
     */
    public int getSelectedIndex() {
        List<WebComponent> actions = getActions();
        for (int i = 0; i < actions.size(); i++) {
            String ariaSelected = actions.get(i).getAttribute("aria-selected");
            if ("true".equals(ariaSelected)) {
                return i;
            }
        }
        return -1; // No selection
    }
}
