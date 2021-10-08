/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.selenium.component.mui.v4.datadisplay;

import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Chips are compact elements that represent an input, attribute, or action.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/chips/">https://material-ui.com/components/chips/</a>
 * @since 1.0
 */
public class MuiChip extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Chip";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiChip(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Whether the Chip component has avatar component.
     *
     * @return whether the Chip component has avatar component
     */
    public boolean hasAvatar() {
        return !this.findComponents(avatarLocator()).isEmpty();
    }

    /**
     * Gets the avatar element. Throws {@link org.openqa.selenium.NoSuchElementException} if not found.
     *
     * @return the avatar element
     */
    public WebComponent getAvatar() {
        return this.findComponent(avatarLocator());
    }

    /**
     * Whether the Chip component has icon component.
     *
     * @return whether the Chip component has icon component
     */
    public boolean hasIcon() {
        return !this.findComponents(iconLocator()).isEmpty();
    }

    /**
     * Gets the icon element. Throws {@link org.openqa.selenium.NoSuchElementException} if not found.
     *
     * @return the icon element
     */
    public WebComponent getIcon() {
        return this.findComponent(iconLocator());
    }

    /**
     * Gets the label element.
     *
     * @return the label element
     */
    public WebComponent getLabel() {
        return this.findComponent(By.className(config.getCssPrefix() + "Chip-label"));
    }

    /**
     * Whether the Chip component has deleteIcon component.
     *
     * @return whether the Chip component has deleteIcon component
     */
    public boolean hasDeleteIcon() {
        return !this.findComponents(deleteIconLocator()).isEmpty();
    }

    /**
     * Gets the delete icon element. Throws {@link org.openqa.selenium.NoSuchElementException} if not found.
     *
     * @return the delete icon element.
     */
    public WebComponent getDeleteIcon() {
        return this.findComponent(deleteIconLocator());
    }

    protected By avatarLocator() {
        return By.className(config.getCssPrefix() + "Chip-avatar");
    }

    protected By iconLocator() {
        return By.className(config.getCssPrefix() + "Chip-icon");
    }

    protected By deleteIconLocator() {
        return By.className(config.getCssPrefix() + "Chip-deleteIcon");
    }

    /**
     * Whether the Chip component is clickable.
     *
     * @return whether the Chip component is clickable.
     */
    public boolean isClickable() {
        return this.attributeContains("class", config.getCssPrefix() + "Chip-clickable");
    }

    /**
     * Whether the Chip component is deletable. it's the alias of {@link #hasDeleteIcon()}.
     *
     * @return whether the Chip component is deletable.
     */
    public boolean isDeletable() {
        return hasDeleteIcon();
    }

    @Override
    public String toString() {
        return "MuiChip{" + "element=" + element + '}';
    }
}
