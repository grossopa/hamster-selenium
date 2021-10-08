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
import com.github.grossopa.hamster.selenium.component.mat.main.MatMenu;
import com.github.grossopa.hamster.selenium.component.mat.main.MatOverlayContainer;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import javax.annotation.Nullable;
import java.util.List;

import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static com.google.common.collect.Lists.newArrayList;

/**
 * The finder to locate the menu items in overlay.
 *
 * @author Jack Yin
 * @see MatMenu
 * @see MatMenuItem
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
     * Finds the top layer menu panel. It assumes the overlay has been expanded.
     *
     * @param delayInMillis the delay in milliseconds
     * @return the found menu panel
     * @throws TimeoutException if no top menus found.
     */
    public MatMenu findTopMenu(long delayInMillis) {
        return driver.createWait(delayInMillis).until(d -> findTopMenu());
    }

    /**
     * Try finds the top layer menu panel, return null if no overlays found.
     *
     * @return the menu panel instance or null if not found
     */
    @Nullable
    public MatMenu findTopMenu() {
        MatOverlayContainer container = findTopVisibleContainer();
        if (container != null) {
            List<WebComponent> boxes = container.findComponents(
                    By.className(config.getCdkPrefix() + "overlay-connected-position-bounding-box"));
            if (!boxes.isEmpty()) {
                WebComponent topVisibleBox = boxes.get(boxes.size() - 1);
                WebComponent panel = topVisibleBox.findComponent(By.className(config.getCssPrefix() + "menu-panel"));
                return new MatMenu(panel, driver, config);
            }
        }
        return null;
    }

    /**
     * Finds the menu panels. It assumes the overlay has been expanded.
     *
     * @param delayInMillis the delay in milliseconds
     * @return the found menu panel
     */
    public List<MatMenu> findMenus(long delayInMillis) {
        return driver.createWait(delayInMillis).until(d -> findMenus());
    }

    public List<MatMenu> findMenus() {
        WebComponent container = findTopVisibleContainer();
        if (container != null) {
            return container.findComponentsAs(xpathBuilder().anywhereRelative().attr(CLASS)
                            .contains(config.getCdkPrefix() + "overlay-connected-position-bounding-box").descendant()
                            .attr(CLASS).contains(config.getCssPrefix() + "menu-panel").build(),
                    c -> new MatMenu(c, driver, config));
        }
        return newArrayList();
    }

    @Override
    public String toString() {
        return "MatMenuItemFinder{" + "driver=" + driver + ", config=" + config + '}';
    }
}
