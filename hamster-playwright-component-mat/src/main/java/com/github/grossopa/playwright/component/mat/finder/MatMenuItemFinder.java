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
package com.github.grossopa.playwright.component.mat.finder;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.exception.MenuItemNotFoundException;
import com.github.grossopa.playwright.component.mat.main.MatMenu;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;

import java.util.List;

/**
 * Finds the menus that are displayed within the CDK overlay bounding boxes.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class MatMenuItemFinder extends MatOverlayFinder {

    /**
     * Constructs an instance with the root driver and configuration.
     *
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatMenuItemFinder(ComponentDriver driver, MatConfig config) {
        super(driver, config);
    }

    /**
     * Finds the top menu which is always the latest displayed menu.
     *
     * @return the top menu instance
     * @throws MenuItemNotFoundException if no visible menu can be found
     */
    public MatMenu findTopMenu() {
        List<WebComponent> boxes = driver.findComponents(
                "." + config.getCdkPrefix() + "overlay-connected-position-bounding-box");
        for (int i = boxes.size() - 1; i >= 0; i--) {
            WebComponent box = boxes.get(i);
            if (box.isVisible()) {
                WebComponent panel = box.findComponent("." + config.getCssPrefix() + "menu-panel");
                return new MatMenu(panel, driver, config);
            }
        }
        throw new MenuItemNotFoundException("no menu found in the overlay bounding boxes.");
    }

    /**
     * Finds all menus within the overlay bounding boxes.
     *
     * @return the list of found menus
     */
    public List<MatMenu> findMenus() {
        return driver.findComponentsAs("." + config.getCdkPrefix() + "overlay-connected-position-bounding-box ."
                + config.getCssPrefix() + "menu-panel", c -> new MatMenu(c, driver, config));
    }

    @Override
    public String toString() {
        return "MatMenuItemFinder{" + "driver=" + driver + ", config=" + config + '}';
    }
}
