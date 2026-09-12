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
package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * {@code <mat-sidenav>} is a sidebar panel that slides in from the side of the screen.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/sidenav/overview">
 * https://material.angular.io/components/sidenav/overview</a>
 * @since 1.16
 */
public class MatSidenav extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Sidenav";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatSidenav(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "drawer");
    }

    /**
     * Whether the sidenav is currently open.
     *
     * @return true if the sidenav is open
     */
    public boolean isOpen() {
        return !this.attributeContains(CLASS, config.getCssPrefix() + "drawer-closed");
    }

    /**
     * Opens the sidenav. This is a no-op if already open.
     */
    public void open() {
        if (!isOpen()) {
            toggle();
        }
    }

    /**
     * Closes the sidenav. This is a no-op if already closed.
     */
    public void close() {
        if (isOpen()) {
            toggle();
        }
    }

    /**
     * Toggles the sidenav open/closed state.
     */
    public void toggle() {
        // the sidenav is toggled via the MatDrawer toggle method which is typically triggered
        // from a button click in the app; here we simply click the drawer itself as a fallback
        this.click();
    }

    /**
     * Gets the sidenav mode (over, side, or push).
     *
     * @return the mode string
     */
    public String getMode() {
        String cls = this.getAttribute(CLASS);
        if (cls != null) {
            if (cls.contains(config.getCssPrefix() + "drawer-over")) {
                return "over";
            }
            if (cls.contains(config.getCssPrefix() + "drawer-side")) {
                return "side";
            }
            if (cls.contains(config.getCssPrefix() + "drawer-push")) {
                return "push";
            }
        }
        return "over";
    }
}
