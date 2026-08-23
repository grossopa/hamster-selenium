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
package com.github.grossopa.playwright.component.mui.v4.datadisplay;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Avatars are found throughout material design with uses in everything from tables to dialog menus.
 *
 * <p>This component supports three types of avatars:
 * <ul>
 *     <li>Image Avatar - displays an image with {@code src} and {@code alt} attributes</li>
 *     <li>Letter Avatar - displays text/initials</li>
 *     <li>Icon Avatar - displays an icon component</li>
 * </ul>
 *
 * @see <a href="https://material-ui.com/components/avatars/">https://material-ui.com/components/avatars/</a>
 * @author Jack Yin
 * @since 1.12
 */
public class MuiAvatar extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Avatar";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiAvatar(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Finds the img element. Only applicable for Image Avatar with {@code src} and {@code alt}
     * attribute set and will throw exception if it's Letter or Icon Avatar.
     *
     * @return the img WebComponent
     */
    public WebComponent getImg() {
        return findComponent("img");
    }

    /**
     * Returns the alt attribute of the image element. Only applicable for Image Avatar.
     *
     * @return the alt attribute of the img element, or null if not an image avatar
     */
    public String getAlt() {
        WebComponent img = getImg();
        return img != null ? img.getAttribute("alt") : null;
    }

    /**
     * Returns the src attribute of the image element. Only applicable for Image Avatar.
     *
     * @return the src attribute of the img element, or null if not an image avatar
     */
    public String getSrc() {
        WebComponent img = getImg();
        return img != null ? img.getAttribute("src") : null;
    }

    /**
     * Gets the text content of the avatar (for letter avatars).
     *
     * @return the text displayed in the avatar
     */
    public String getText() {
        return locator.innerText();
    }

    /**
     * Checks if this is an image avatar (has an img child element).
     *
     * @return true if this is an image avatar, false otherwise
     */
    public boolean isImageAvatar() {
        return findComponent("img") != null;
    }
}
