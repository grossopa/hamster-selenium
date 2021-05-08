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

package com.github.grossopa.selenium.component.antd.config;

import org.openqa.selenium.By;

/**
 * The global configuration that is used across all components
 *
 * @author Jack Yin
 * @since 1.4
 */
public class AntdConfig {

    private By popupContainerLocator = By.xpath("/html/body[/div]");

    private String iconPrefixCls = "anticon";

    private String prefixCls = "ant";

    /**
     * Gets the locator for popup container. this is aligned with {@code getPopupContainer} config of Antd: To set the
     * container of the popup element. The default is to create a div element in body
     *
     * @return the locator for popup container
     * @see <a href="https://ant.design/components/config-provider/#API">getPopupContainer</a>
     */
    public By getPopupContainerLocator() {
        return popupContainerLocator;
    }

    /**
     * Sets the locator for popup container. this is aligned with {@code getPopupContainer} config of Antd: To set the
     * container of the popup element. The default is to create a div element in body
     *
     * @param popupContainerLocator the locator for popup container to set
     * @see <a href="https://ant.design/components/config-provider/#API">getPopupContainer</a>
     */
    public void setPopupContainerLocator(By popupContainerLocator) {
        this.popupContainerLocator = popupContainerLocator;
    }

    /**
     * Gets the icon prefix className.
     *
     * @return the icon prefix className.
     * @see <a href="https://ant.design/components/config-provider/#API">iconPrefixCls</a>
     */
    public String getIconPrefixCls() {
        return iconPrefixCls;
    }

    /**
     * Sets the icon prefix className.
     *
     * @param iconPrefixCls the icon prefix className to set
     * @see <a href="https://ant.design/components/config-provider/#API">iconPrefixCls</a>
     */
    public void setIconPrefixCls(String iconPrefixCls) {
        this.iconPrefixCls = iconPrefixCls;
    }

    /**
     * Gets the prefix className.
     *
     * @return the prefix className.
     * @see <a href="https://ant.design/components/config-provider/#API">iconPrefixCls</a>
     */
    public String getPrefixCls() {
        return prefixCls;
    }

    /**
     * Sets the icon prefix className.
     *
     * @param prefixCls the icon prefix className to set
     * @see <a href="https://ant.design/components/config-provider/#API">prefixCls</a>
     */
    public void setPrefixCls(String prefixCls) {
        this.prefixCls = prefixCls;
    }
}
