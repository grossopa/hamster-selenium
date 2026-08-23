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
package com.github.grossopa.playwright.component.mui.v4.feedback;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.*;
import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * Backdrop provides emphasis to a particular UI element by dimming the rest of the screen.
 *
 * <p>Backdrop is commonly used with modals, dialogs, and drawers to create visual separation
 * and focus user attention on the overlay content.</p>
 *
 * @see <a href="https://material-ui.com/components/backdrop/">
 * https://material-ui.com/components/backdrop/</a>
 * @author Jack Yin
 * @since 1.12
 */
public class MuiBackdrop extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Backdrop";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiBackdrop(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Checks if the backdrop is currently visible.
     *
     * @return true if backdrop is visible, false otherwise
     */
    @Override
    public boolean isVisible() {
        String className = getAttribute(CLASS);
        return className != null && !className.contains(config.getCssPrefix() + "Backdrop-invisible");
    }

    /**
     * Clicks on the backdrop (typically to close the associated modal/dialog).
     */
    @Override
    public void click() {
        locator.click();
    }

    /**
     * Checks if the backdrop is transparent/invisible.
     *
     * @return true if backdrop has invisible class, false otherwise
     */
    public boolean isInvisible() {
        String className = getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "Backdrop-invisible");
    }
}
