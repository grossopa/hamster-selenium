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

package com.github.grossopa.selenium.component.mui.core;

import com.github.grossopa.selenium.component.mui.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

/**
 * The grid creates visual consistency between layouts while allowing flexibility across a wide variety of designs.
 * Material Design’s responsive UI is based on a 12-column grid layout.
 *
 * @author Elena Wang
 * @see <a href="https://material-ui.com/components/grid/">
 * https://material-ui.com/components/grid/</a>
 * @since 1.6
 */
public class MuiGrid extends AbstractMuiComponent {

    /**
     * the component name
     */
    public static final String COMPONENT_NAME = "Grid";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver  the root driver
     * @param config  the Material UI configuration
     */
    public MuiGrid(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * There are two types of layout: containers and items. This function is to check if the tested element is a container.
     * An element can both have 2 types: container and item.
     *
     * @return true if it is a Grid container component
     */
    public boolean isContainer() { return config.isGridContainer(this); }

    /**
     * There are two types of layout: containers and items. This function is to check if the tested element is an item.
     * An element can both have 2 types: container and item.
     *
     * @return true if it is a Grid container component
     */
    public boolean isItem() { return config.isGridItem(this); }

    /**
     * By default, the spacing between two grid items follows a linear function: output(spacing) = spacing * 8px, e.g. spacing={2} creates a 16px wide gap.
     * So the padding spacing for each item  follows a linear function: output(spacing) = spacing * 4px
     *
     * @param num the number defined in front
     * @return the padding value for item grid.
     */
    public Integer gridItemSpacingValue(int num) {
        return num * 4;
    }

    @Override
    public String toString() {
        return "MuiGrid{" + "element=" + element + '}';
    }

}
