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

package com.github.grossopa.hamster.selenium.component.mat.main.sub;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.exception.MenuItemNotExpandableException;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatMenuItemFinder;
import com.github.grossopa.hamster.selenium.component.mat.main.MatMenu;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;


/**
 * Single item inside a mat-menu. Provides the menu item styling and accessibility treatment.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/menu/api#MatMenuItem">
 * https://material.angular.io/components/menu/api#MatMenuItem</a>
 * @since 1.6
 */
public class MatMenuItem extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "MenuItem";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatMenuItem(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "menu-item");
    }

    /**
     * Whether the menu item could be expanded.
     *
     * @return whether the menu item could be expanded
     */
    public boolean isExpandable() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "menu-item-submenu-trigger");
    }

    /**
     * Whether the menu item is expanded
     *
     * @return whether the menu item is expanded
     */
    public boolean isExpanded() {
        return this.attributeContains("aria-expanded", "true");
    }

    /**
     * Hovers to expand the menu item.
     *
     * @param animationInMillis wait for each selection due to animation
     * @param topMenuDelayInMillis top menu finding delays in milliseconds
     * @return the found nested menu items.
     * @throws MenuItemNotExpandableException if {@link #isExpandable()} returns false.
     */
    public MatMenu expand(long animationInMillis, long topMenuDelayInMillis) {
        if (!isExpandable()) {
            throw new MenuItemNotExpandableException("the menu item is not expandable.");
        }

        driver.moveTo(this);
        if (animationInMillis > 0) {
            driver.threadSleep(animationInMillis);
        }

        return new MatMenuItemFinder(driver, config).findTopMenu(topMenuDelayInMillis);
    }

}
