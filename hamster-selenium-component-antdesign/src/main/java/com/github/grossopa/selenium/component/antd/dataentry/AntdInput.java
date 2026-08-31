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
package com.github.grossopa.selenium.component.antd.dataentry;

import com.github.grossopa.selenium.component.antd.AbstractAntdComponent;
import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;
import static com.github.grossopa.utils.consts.HtmlConstants.INPUT;

/**
 * A basic widget for getting one line of text input. It could wrap either the inner {@code input} element with class
 * {@code ant-input} or an outer wrapper such as {@code span} with class {@code ant-input-affix-wrapper}.
 *
 * @author Jack Yin
 * @see <a href="https://ant.design/components/input">https://ant.design/components/input</a>
 * @since 1.15
 */
public class AntdInput extends AbstractAntdComponent {

    /**
     * The component name
     */
    public static final String NAME = "input";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver root driver
     * @param config the global Antd configuration
     */
    public AntdInput(WebElement element, ComponentWebDriver driver, AntdConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getPrefixCls() + "-" + NAME) || attributeContains(CLASS,
                config.getPrefixCls() + "-" + NAME + "-affix-wrapper");
    }

    /**
     * Gets the inner input or textarea element.
     *
     * @return the inner input or textarea element
     */
    public WebComponent getInput() {
        if (INPUT.equalsIgnoreCase(element.getTagName())) {
            return this;
        }
        return findComponent(By.className(config.getPrefixCls() + "-" + NAME));
    }

    /**
     * Gets the current value of the input.
     *
     * @return the current value of the input
     */
    public String getValue() {
        return getInput().getDomAttribute("value");
    }

    /**
     * Sends the text to the inner input by {@link #sendKeys(CharSequence...)}.
     *
     * @param text the text to send to inner input
     */
    public void sendText(CharSequence text) {
        getInput().sendKeys(text);
    }

    /**
     * Clears the current value of the inner input.
     */
    public void clearText() {
        getInput().clear();
    }
}
