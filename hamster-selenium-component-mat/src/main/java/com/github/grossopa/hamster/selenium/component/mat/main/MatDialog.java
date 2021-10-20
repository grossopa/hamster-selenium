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
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;


/**
 * The MatDialog service can be used to open modal dialogs with Material Design styling and animations.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/dialog/overview">
 * https://material.angular.io/components/dialog/overview</a>
 * @since 1.6
 */
public class MatDialog extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Dialog";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatDialog(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "dialog-container");
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Finds [Attr] Dialog title, applied to a heading element (e.g., {@code <h1>, <h2>}).
     *
     * @return the dialog title container
     */
    public WebComponent getDialogTitle() {
        return this.findComponent(By.className(config.getCssPrefix() + "dialog-title"));
    }

    /**
     * Finds the primary scrollable content of the dialog.
     *
     * @return the primary scrollable content of the dialog.
     */
    public WebComponent getDialogContent() {
        return this.findComponent(By.className(config.getCssPrefix() + "dialog-content"));
    }

    /**
     * Finds the container for action buttons at the bottom of the dialog. Button alignment can be controlled via the
     * align attribute which can be set to end and center.
     *
     * @return the container for action buttons at the bottom of the dialog
     */
    public WebComponent getDialogActions() {
        return this.findComponent(By.className(config.getCssPrefix() + "dialog-actions"));
    }
}
