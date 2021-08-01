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

import static com.github.grossopa.selenium.component.mui.config.MuiConfig.ATTR_CLASS;

/**
 * The grid creates visual consistency between layouts while allowing flexibility across a wide variety of designs.
 * Material Design’s responsive UI is based on a 12-column grid layout.
 *
 * @author Elena Wang
 * @since 1.6
 * @see <a href="https://material-ui.com/components/grid/">
 * https://material-ui.com/components/grid/</a>
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
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    protected MuiGrid(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * TODO add comments for public method please
     *
     * @return
     */
    public boolean isContainer() {
        return this.attributeContains(ATTR_CLASS, config.getCssPrefix() + "Grid-container");
    }

    /**
     * TODO add comments for public method please
     *
     * @return
     */
    public boolean isItem() {
        return this.attributeContains(ATTR_CLASS, config.getCssPrefix() + "Grid-item");
    }

    // Temp comment as building error
    //    /**
    //     * @param num the number defined in front
    //     * @return true if it is a Grid component
    //     */
    //    public Boolean checkGridItemSpacingValue(int num) {
    //        int spaceNumber = num * 4;
    //
    //    }

    @Override
    public String toString() {
        return "MuiGrid{" + "element=" + element + '}';
    }

    /**
     * Returns true if it is a Grid component
     *
     * @return true if it is a Grid component
     */
    @Override
    public boolean validate() {
        return config.validateByCss(this, config.getCssPrefix() + COMPONENT_NAME);
    }
}
