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

package com.github.grossopa.selenium.component.mui.pickers;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.feedback.MuiDialog;
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * The default Pickers Dialog implementation
 *
 * @author Jack Yin
 * @since 1.2
 */
@SuppressWarnings("java:S110")
public class MuiPickersDialog extends MuiDialog {

    private final By okButtonLocator;
    private final By cancelButtonLocator;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiPickersDialog(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, By2.textContains("OK"), By2.textContains("Cancel"));
    }

    /**
     * Constructs an instance with the delegated element and root driver and customized locators for ok and cancel
     * buttons.
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param okButtonLocator the locator for locating ok button within the actions container
     * @param cancelButtonLocator the locator for locating cancel button within the actions container
     */
    public MuiPickersDialog(WebElement element, ComponentWebDriver driver, MuiConfig config, By okButtonLocator,
            By cancelButtonLocator) {
        super(element, driver, config);
        this.okButtonLocator = okButtonLocator;
        this.cancelButtonLocator = cancelButtonLocator;
    }

    /**
     * Gets the pickers container with the toolbar (for year / day selection model switch) and the container for year /
     * day selection.
     *
     * @return the inner element of {@link MuiPickersBasePickerContainer}
     */
    public MuiPickersBasePickerContainer getPickersContainer() {
        WebComponent component = this.getDialogContent()
                .findComponent(By.className(config.getCssPrefix() + MuiPickersBasePickerContainer.NAME));
        return new MuiPickersBasePickerContainer(component, driver, config);
    }

    /**
     * Finds the ok button
     *
     * @return the ok button instance
     */
    public MuiButton getOkButton() {
        WebComponent actionsContainer = this.getDialogActions();
        WebComponent okButton = actionsContainer.findComponent(getOkButtonLocator());
        return new MuiButton(okButton, driver, config);
    }

    /**
     * Finds the cancel button
     *
     * @return the cancel button instance
     */
    public MuiButton getCancelButton() {
        WebComponent actionsContainer = this.getDialogActions();
        WebComponent cancelButton = actionsContainer.findComponent(getCancelButtonLocator());
        return new MuiButton(cancelButton, driver, config);
    }

    /**
     * Gets the locator for locating ok button in the action container
     *
     * @return the locator for locating ok button in the action container
     */
    public By getOkButtonLocator() {
        return okButtonLocator;
    }

    /**
     * Gets the locator for locating cancel button in the action container
     *
     * @return the locator for locating cancel button in the action container
     */
    public By getCancelButtonLocator() {
        return cancelButtonLocator;
    }
}
