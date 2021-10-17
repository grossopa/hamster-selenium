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

package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.exception.MenuItemNotFoundException;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;
import static org.openqa.selenium.Keys.ESCAPE;

/**
 * {@code <mat-menu>} is a floating panel containing list of options.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/menu/overview">
 * https://material.angular.io/components/menu/overview</a>
 * @since 1.6
 */
public class MatMenu extends AbstractMatComponent {
    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Menu";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatMenu(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "menu-panel");
    }

    /**
     * Gets the menu items
     *
     * @return the menu items.
     */
    public List<MatMenuItem> getMenuItems() {
        return this.findComponentsAs(By.className(config.getCssPrefix() + "menu-item"),
                c -> new MatMenuItem(c, driver, config));
    }

    /**
     * Expands the menu item of this menu by index.
     *
     * @param index the index of the {@link MatMenuItem} to be expanded
     * @param animationInMillis wait for each selection due to animation
     * @param topMenuDelayInMillis top menu finding delays in milliseconds
     * @return the next expanded menu item
     */
    public MatMenu expandItemByIndex(int index, long animationInMillis, long topMenuDelayInMillis) {
        return this.getMenuItems().get(index).expand(animationInMillis, topMenuDelayInMillis);
    }

    /**
     * Selects the menu item by index
     *
     * @param index the index of the {@link MatMenuItem} to be selected
     */
    public void selectItemByIndex(int index) {
        this.getMenuItems().get(index).click();
    }

    /**
     * Expands the menu item of this menu by text
     *
     * @param text the index of the {@link MatMenuItem} to match
     * @param animationInMillis wait for each selection due to animation
     * @param topMenuDelayInMillis top menu finding delays in milliseconds
     * @return the next expanded menu item
     * @throws MenuItemNotFoundException when no matching menu items
     */
    public MatMenu expandItemByText(String text, long animationInMillis, long topMenuDelayInMillis) {
        return actionBy(menuItem -> StringUtils.equals(text, menuItem.getText()),
                menuItem -> menuItem.expand(animationInMillis, topMenuDelayInMillis));
    }

    /**
     * Selects the menu item by text
     *
     * @param text the index of the {@link MatMenuItem} to match
     * @throws MenuItemNotFoundException when no matching menu items
     */
    public void selectItemByText(String text) {
        actionBy(menuItem -> StringUtils.equals(text, menuItem.getText()), menuItem -> {
            menuItem.click();
            return null;
        });
    }

    /**
     * Closes the current menu.
     */
    public void close() {
        this.sendKeys(ESCAPE);
    }

    @Override
    public String toString() {
        return "MatMenu{" + "element=" + element + '}';
    }

    private <T> T actionBy(Predicate<MatMenuItem> menuItemToSelectPredicate, Function<MatMenuItem, T> actionConsumer) {
        List<MatMenuItem> menuItems = this.getMenuItems();
        MatMenuItem item = menuItems.stream().filter(menuItemToSelectPredicate).findAny()
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found by predicate."));
        return actionConsumer.apply(item);
    }

}
