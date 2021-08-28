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

package com.github.grossopa.hamster.selenium.component.mat.finder;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.exception.MenuItemNotFoundException;
import com.github.grossopa.hamster.selenium.component.mat.main.MatMenu;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The finder to locate the menu items in overlay.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatMenuItemFinder extends MatOverlayFinder {
    /**
     * Constructs an instance with given config.
     *
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatMenuItemFinder(ComponentWebDriver driver, MatConfig config) {
        super(driver, config);
    }

    /**
     * Finds the top layer menu items. It assumes the overlay has been expanded.
     *
     * @param delayInMillis the delay in milliseconds
     * @return the found menu item list
     */
    public MatMenu findTopMenu(long delayInMillis) {
        return driver.createWait(delayInMillis).until(d -> {
            WebComponent container = findTopVisibleContainer();
            if (container != null) {
                List<WebComponent> boxes = container.findComponents(
                        By.className(config.getCdkPrefix() + "overlay-connected-position-bounding-box"));
                if (!boxes.isEmpty()) {
                    WebComponent topVisibleBox = boxes.get(boxes.size() - 1);

                    WebComponent panel = topVisibleBox.findComponent(
                            By.className(config.getCssPrefix() + "menu-panel"));
                    return new MatMenu(panel, driver, config);
                }
            }
            return null;
        });
    }

    /**
     * Navigates the sub menus by menu item text
     *
     * @param gapTimeInMillis wait for each selection, e.g. animation
     * @param delayInMillis delay time for each top menu findings, please check {@link #findTopMenu(long)}
     * @param menuTexts the text to match
     */
    public void navigateMenuItems(long gapTimeInMillis, long delayInMillis, String... menuTexts) {
        navigateMenuItems(gapTimeInMillis, delayInMillis, Arrays.stream(menuTexts)
                .<Predicate<MatMenuItem>>map(s -> menuItem -> StringUtils.equals(s, menuItem.getText()))
                .collect(Collectors.toList()));
    }

    /**
     * Navigates the sub menus.
     *
     * @param gapTimeInMillis wait for each selection, e.g. animation
     * @param delayInMillis delay time for each top menu findings, please check {@link #findTopMenu(long)}
     * @param menuItemToSelectPredicates the predicates for each layer to return true for the menu item to be select
     */
    public void navigateMenuItems(long gapTimeInMillis, long delayInMillis,
            List<Predicate<MatMenuItem>> menuItemToSelectPredicates) {
        for (Predicate<MatMenuItem> predicate : menuItemToSelectPredicates) {
            List<MatMenuItem> menuItems = findTopMenu(delayInMillis).getMenuItems();
            MatMenuItem item = menuItems.stream().filter(predicate).findAny()
                    .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found by predicate."));
            driver.moveTo(item);
            driver.threadSleep(gapTimeInMillis);
        }
    }
}
