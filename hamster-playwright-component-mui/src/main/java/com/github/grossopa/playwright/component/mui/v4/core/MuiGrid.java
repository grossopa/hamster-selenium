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

package com.github.grossopa.playwright.component.mui.v4.core;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * The grid creates visual consistency between layouts while allowing flexibility across a wide variety of designs.
 *
 * <p>Material Design's responsive UI is based on a 12-column grid layout. Grid supports both container 
 * and item modes for flexible layout construction.</p>
 *
 * @author Elena Wang
 * @see <a href="https://material-ui.com/components/grid/">
 * https://material-ui.com/components/grid/</a>
 * @since 1.12
 */
public class MuiGrid extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Grid";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiGrid(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<com.github.grossopa.playwright.component.mui.MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Checks if the grid element is a container.
     *
     * <p>An element can be both a container and an item.</p>
     *
     * @return true if it is a Grid container component
     */
    public boolean isContainer() {
        String className = getAttribute("class");
        return className != null && className.contains(config.getCssPrefix() + "Grid-container");
    }

    /**
     * Checks if the grid element is an item.
     *
     * <p>An element can be both a container and an item.</p>
     *
     * @return true if it is a Grid item component
     */
    public boolean isItem() {
        String className = getAttribute("class");
        return className != null && className.contains(config.getCssPrefix() + "Grid-item");
    }

    /**
     * Calculates the padding spacing value for grid items.
     *
     * <p>By default, spacing follows: output(spacing) = spacing * 8px for gap, 
     * and spacing * 4px for padding on each item.</p>
     *
     * @param num the spacing number defined in props
     * @return the padding value in pixels for item grid
     */
    public int gridItemSpacingValue(int num) {
        return num * 4;
    }

    /**
     * Gets the spacing value from the grid container.
     *
     * @return the spacing value, or 0 if not set
     */
    public int getSpacing() {
        String className = getAttribute("class");
        if (className != null) {
            for (int i = 0; i <= 10; i++) {
                if (className.contains("spacing-" + i)) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * Checks if the grid uses wrap layout.
     *
     * @return true if wrap is enabled, false otherwise
     */
    public boolean isWrap() {
        String className = getAttribute("class");
        return className == null || !className.contains("nowrap");
    }

    /**
     * Gets the direction of the grid layout.
     *
     * @return the direction ("row", "column", "row-reverse", or "column-reverse")
     */
    public String getDirection() {
        String className = getAttribute("class");
        if (className != null) {
            if (className.contains("direction-column")) return "column";
            if (className.contains("direction-row-reverse")) return "row-reverse";
            if (className.contains("direction-column-reverse")) return "column-reverse";
        }
        return "row"; // default
    }

    /**
     * Gets the justification alignment.
     *
     * @return the justification ("flex-start", "center", "flex-end", "space-between", etc.)
     */
    public String getJustifyContent() {
        String className = getAttribute("class");
        if (className != null) {
            if (className.contains("justify-content-center")) return "center";
            if (className.contains("justify-content-flex-end")) return "flex-end";
            if (className.contains("justify-content-space-between")) return "space-between";
            if (className.contains("justify-content-space-around")) return "space-around";
        }
        return "flex-start"; // default
    }

    /**
     * Gets the align items property.
     *
     * @return the alignment ("flex-start", "center", "flex-end", "stretch", or "baseline")
     */
    public String getAlignItems() {
        String className = getAttribute("class");
        if (className != null) {
            if (className.contains("align-items-center")) return "center";
            if (className.contains("align-items-flex-end")) return "flex-end";
            if (className.contains("align-items-stretch")) return "stretch";
            if (className.contains("align-items-baseline")) return "baseline";
        }
        return "flex-start"; // default
    }
}
