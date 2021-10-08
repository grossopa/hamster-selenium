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

package com.github.grossopa.selenium.component.mui.v4.datadisplay;

import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Avatars are found throughout material design with uses in everything from tables to dialog menus.
 *
 * <ul>
 *     <li>Image Avatar APIs: {@link #getImg()}, {@link #getAlt()}, {@link #getSrc()}</li>
 *     <li>Letter Avatar API: {@link #getText()}</li>
 *     <li>No APIs for Icon Avatar</li>
 * </ul>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/avatars/">https://material-ui.com/components/avatars/</a>
 * @since 1.0
 */
public class MuiAvatar extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Avatar";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiAvatar(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Finds the Img Html element.Only applicable for Image Avatar with {@code src} and {@code alt} attribute set and
     * will throw {@link org.openqa.selenium.NoSuchElementException} if it's Letter or Icon Avatar.
     *
     * @return the Img Html element.
     */
    public WebComponent getImg() {
        return this.findComponent(By.tagName("img"));
    }

    /**
     * Return the alt attribute of the image element. Only applicable for Image Avatar with {@code src} and {@code alt}
     * attribute set and will throw {@link org.openqa.selenium.NoSuchElementException} if it's Letter or Icon Avatar.
     *
     * @return the alt attribute of the img element.
     */
    public String getAlt() {
        return getImg().getAttribute("alt");
    }

    /**
     * Return the src attribute of the image element. Only applicable for Image Avatar with {@code src} and {@code alt}
     * attribute set and will throw {@link org.openqa.selenium.NoSuchElementException} if it's Letter or Icon Avatar.
     *
     * @return the src attribute of the img element.
     */
    public String getSrc() {
        return getImg().getAttribute("src");
    }

    @Override
    public String toString() {
        return "MuiAvatar{" + "element=" + element + '}';
    }
}
