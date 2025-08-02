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

package com.github.grossopa.selenium.component.mui.v4.navigation;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static java.util.stream.Collectors.toList;

/**
 * The Material UI Drawer implementation
 *
 * <p>The navigation drawers (or "sidebars") provide ergonomic access to destinations
 * in a site or app functionality such as switching accounts.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/drawers/">
 * https://material-ui.com/components/drawers/</a>
 * @since 1.0
 */
public class MuiDrawer extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Drawer";

    /**
     * Constructs an MuiDrawer instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiDrawer(WebElement element, ComponentWebDriver driver, MuiConfig config) {
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
     * Checks if the drawer is currently open.
     *
     * @return true if the drawer is open, false otherwise
     */
    public boolean isOpen() {
        // Check visibility using CSS or aria attributes
        String visibility = element.getCssValue("visibility");
        String ariaHidden = element.getAttribute("aria-hidden");
        
        return !"hidden".equals(visibility) && !"true".equals(ariaHidden);
    }

    /**
     * Opens the drawer if it's currently closed.
     */
    public void open() {
        if (!isOpen()) {
            toggle();
        }
    }

    /**
     * Closes the drawer if it's currently open.
     */
    public void close() {
        if (isOpen()) {
            toggle();
        }
    }

    /**
     * Toggles the drawer state (open/close).
     */
    public void toggle() {
        // Click on the backdrop if it exists to close
        // or implement specific toggle logic based on how the drawer is controlled
        element.click();
    }

    /**
     * Gets the list of navigation items in the drawer.
     *
     * @return list of navigation item components
     */
    public List<WebComponent> getNavigationItems() {
        try {
            WebComponent list = this.findComponent(By.className(config.getCssPrefix() + "List-root"));
            return list.findComponents(By.tagName("li"));
        } catch (Exception e) {
            return findComponents(By.tagName("li"));
        }
    }

    /**
     * Gets the drawer variant type.
     *
     * @return the variant type (e.g. "permanent", "persistent", "temporary")
     */
    public String getVariant() {
        String className = element.getAttribute("class");
        String cssPrefix = config.getCssPrefix();

        if (className.contains(cssPrefix + "Drawer-docked")) {
            return "permanent";
        } else if (className.contains(cssPrefix + "Drawer-paperAnchorDockedLeft") ||
                   className.contains(cssPrefix + "Drawer-paperAnchorDockedRight") ||
                   className.contains(cssPrefix + "Drawer-paperAnchorDockedTop") ||
                   className.contains(cssPrefix + "Drawer-paperAnchorDockedBottom")) {
            return "persistent";
        } else {
            return "temporary"; // default
        }
    }
}