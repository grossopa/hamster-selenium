/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.component.mui.navigation;

import org.hamster.selenium.component.mui.MuiButton;
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

/**
 * The action button item of bottom navigation bars allow movement between primary destinations in an app.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/bottom-navigation/">
 * https://material-ui.com/components/bottom-navigation/</a>
 * @since 1.0
 */
public class MuiBottomNavigationAction extends MuiButton {
    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element
     *         the delegated element
     * @param driver
     *         the root driver
     * @param config
     *         the Material UI configuration
     */
    public MuiBottomNavigationAction(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "BottomNavigationAction";
    }

    @Override
    public boolean isSelected() {
        return config.isSelected(this);
    }
}