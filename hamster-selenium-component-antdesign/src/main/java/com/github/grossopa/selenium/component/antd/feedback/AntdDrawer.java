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
package com.github.grossopa.selenium.component.antd.feedback;

import com.github.grossopa.selenium.component.antd.AbstractAntdComponent;
import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * A panel which slides in from the edge of the screen. It wraps the {@code div} element with class
 * {@code ant-drawer}.
 *
 * @author Jack Yin
 * @see <a href="https://ant.design/components/drawer">https://ant.design/components/drawer</a>
 * @since 1.15
 */
public class AntdDrawer extends AbstractAntdComponent {

    /**
     * The component name
     */
    public static final String NAME = "drawer";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver root driver
     * @param config the global Antd configuration
     */
    public AntdDrawer(WebElement element, ComponentWebDriver driver, AntdConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getPrefixCls() + "-" + NAME);
    }

    /**
     * Whether the drawer is currently open.
     *
     * @return true if the drawer is currently open
     */
    public boolean isOpen() {
        return attributeContains(CLASS, config.getPrefixCls() + "-" + NAME + "-open");
    }

    /**
     * Finds the title element, throw {@link org.openqa.selenium.NoSuchElementException} if it doesn't exist.
     *
     * @return the title element
     */
    public WebComponent getTitle() {
        return findComponent(By.className(config.getPrefixCls() + "-" + NAME + "-title"));
    }

    /**
     * Finds the body container, throw {@link org.openqa.selenium.NoSuchElementException} if it doesn't exist.
     *
     * @return the body container
     */
    public WebComponent getBody() {
        return findComponent(By.className(config.getPrefixCls() + "-" + NAME + "-body"));
    }

    /**
     * Finds the footer container, throw {@link org.openqa.selenium.NoSuchElementException} if it doesn't exist.
     *
     * @return the footer container
     */
    public WebComponent getFooter() {
        return findComponent(By.className(config.getPrefixCls() + "-" + NAME + "-footer"));
    }

    /**
     * Finds the close button, throw {@link org.openqa.selenium.NoSuchElementException} if it doesn't exist.
     *
     * @return the close button
     */
    public WebComponent getCloseButton() {
        return findComponent(By.className(config.getPrefixCls() + "-" + NAME + "-close"));
    }

    /**
     * Closes the drawer by clicking the close button.
     */
    public void close() {
        getCloseButton().click();
    }
}
