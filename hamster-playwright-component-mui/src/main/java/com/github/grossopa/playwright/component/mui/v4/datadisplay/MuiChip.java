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

import static com.github.grossopa.utils.consts.HtmlConstants.BUTTON;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.MuiVersion;
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
 * Chips represent complex entities in small blocks, such as a contact.
 *
 * <p>Chips can contain avatars, icons, text, and have clickable actions like delete.
 * They are commonly used for filters, tags, or selectable items.</p>
 *
 * @see <a href="https://material-ui.com/components/chips/">https://material-ui.com/components/chips/</a>
 * @since 1.12
 */
public class MuiChip extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Chip";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiChip(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the chip label/text content.
     *
     * @return the chip text
     */
    public String getLabel() {
        return locator.innerText();
    }

    /**
     * Clicks on the chip.
     */
    public void click() {
        locator.click();
    }

    /**
     * Clicks the delete icon/button on the chip (if present).
     *
     * @throws IllegalStateException if the chip doesn't have a delete button
     */
    public void clickDelete() {
        WebComponent deleteButton = findComponent("." + config.getCssPrefix() + "Chip-deleteIcon");
        if (deleteButton == null) {
            throw new IllegalStateException("This chip does not have a delete button");
        }
        deleteButton.click();
    }

    /**
     * Checks if the chip has a delete button.
     *
     * @return true if the chip has a delete icon, false otherwise
     */
    public boolean hasDeleteButton() {
        return findComponent("." + config.getCssPrefix() + "Chip-deleteIcon") != null;
    }

    /**
     * Checks if the chip is clickable.
     *
     * @return true if the chip has click handler, false otherwise
     */
    public boolean isClickable() {
        String role = getAttribute("role");
        return BUTTON.equals(role) || getAttribute("tabindex") != null;
    }

    /**
     * Gets the avatar element within the chip (if present).
     *
     * @return the avatar WebComponent, or null if no avatar
     */
    public WebComponent getAvatar() {
        return findComponent("." + config.getCssPrefix() + "Chip-avatar");
    }

    /**
     * Checks if the chip has an avatar.
     *
     * @return true if the chip contains an avatar, false otherwise
     */
    public boolean hasAvatar() {
        return getAvatar() != null;
    }
}
