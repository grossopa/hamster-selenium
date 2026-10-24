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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;

/**
 * Represents a Material UI Menubar component introduced in MUI v9.
 *
 * <p>A Menubar is a horizontal bar that contains a list of menu triggers. Each trigger can open a dropdown popup
 * containing menu items. It is commonly used in application headers and toolbars for navigation and command
 * access.</p>
 *
 * <p>The Menubar is <strong>not a standard MUI component</strong> but rather a <strong>component recipe</strong>
 * composed from Base UI's {@code Menubar} primitive styled with standard MUI building blocks. The root element
 * is a custom styled {@code <div>} with {@code role="menubar"}. The trigger buttons use {@code MuiButton-root}
 * CSS class, and the dropdown menu items use {@code MuiListItemButton-root} CSS class.</p>
 *
 * <p>The component structure typically contains:
 * <ul>
 *   <li>Trigger buttons with CSS class {@code MuiButton-root} for each menu (e.g., File, Help)</li>
 *   <li>Dropdown popups rendered via Base UI Portal containing {@code MuiList-root} with
 *       {@code MuiListItemButton-root} items</li>
 * </ul>
 *
 * <p>Key features:
 * <ul>
 *   <li>Access trigger buttons via {@link #getMenuItems()}</li>
 *   <li>Open a specific menu trigger by index or label via {@link #openMenuItem(int)} and
 *       {@link #openMenuItem(String)}</li>
 *   <li>Validation via {@code role="menubar"} attribute</li>
 *   <li>Supports MUI version V6+</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.18
 * @see MuiMenu
 * @see MuiMenuItem
 * @see <a href="https://mui.com/material-ui/react-menubar/">https://mui.com/material-ui/react-menubar/</a>
 */
public class MuiMenubar extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Menubar";

    /**
     * Constructs a MuiMenubar instance with the delegated element and root driver.
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiMenubar(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V6);
    }

    @Override
    public boolean validate() {
        return "menubar".equals(element.getAttribute("role"));
    }

    /**
     * Gets all menu trigger buttons within this menubar. Each trigger is a {@code MuiButton} that opens a dropdown
     * popup when clicked.
     *
     * @return the list of trigger button components
     */
    public List<MuiMenuItem> getMenuItems() {
        return this.findComponents(By.className(config.getCssPrefix() + "Button-root")).stream()
                .map(component -> new MuiMenuItem(component, driver, config)).toList();
    }

    /**
     * Opens the menu trigger at the specified index by clicking it.
     *
     * @param index the zero-based index of the menu trigger to open
     */
    public void openMenuItem(int index) {
        List<MuiMenuItem> items = getMenuItems();
        items.get(index).click();
    }

    /**
     * Opens the menu trigger with the specified label text by clicking it.
     *
     * @param label the text label of the menu trigger to open
     */
    public void openMenuItem(String label) {
        List<MuiMenuItem> items = getMenuItems();
        items.stream().filter(item -> label.equals(item.getText())).findFirst()
                .ifPresentOrElse(MuiMenuItem::click, () -> {
                    throw new IllegalArgumentException("No menu item found with label: " + label);
                });
    }
}
