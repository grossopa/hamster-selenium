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

package com.github.grossopa.selenium.component.mui.feedback;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.core.MuiModal;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Dialogs inform users about a task and can contain critical information, require decisions, or involve multiple
 * tasks.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/dialogs/">
 * https://material-ui.com/components/dialogs/</a>
 * @since 1.0
 */
public class MuiDialog extends MuiModal {
    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material-UI configuration
     */
    public MuiDialog(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "Dialog";
    }

    /**
     * Finds the title container, throw {@link org.openqa.selenium.NoSuchElementException} if it doesn't exist.
     *
     * @return the title container
     */
    public WebComponent getDialogTitle() {
        return this.findComponent(By.className(config.getCssPrefix() + "DialogTitle-root"));
    }

    /**
     * Finds the dialog content container, throw {@link org.openqa.selenium.NoSuchElementException} if it doesn't
     * exist.
     *
     * @return the dialog content container
     */
    public WebComponent getDialogContent() {
        return this.findComponent(By.className(config.getCssPrefix() + "DialogContent-root"));
    }

    /**
     * Finds the dialog actions container, throw {@link org.openqa.selenium.NoSuchElementException} if it doesn't
     * exist.
     *
     * @return the dialog actions container
     */
    public WebComponent getDialogActions() {
        return this.findComponent(By.className(config.getCssPrefix() + "DialogActions-root"));
    }

}
