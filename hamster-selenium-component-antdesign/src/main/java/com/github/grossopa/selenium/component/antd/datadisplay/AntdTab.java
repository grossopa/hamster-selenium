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
package com.github.grossopa.selenium.component.antd.datadisplay;

import com.github.grossopa.selenium.component.antd.AbstractAntdComponent;
import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * A tab of the {@link AntdTabs} component.
 *
 * @author Jack Yin
 * @see <a href="https://ant.design/components/tabs">https://ant.design/components/tabs</a>
 * @since 1.15
 */
public class AntdTab extends AbstractAntdComponent {

    /**
     * The component name
     */
    public static final String NAME = "tabs-tab";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver root driver
     * @param config the global Antd configuration
     */
    public AntdTab(WebElement element, ComponentWebDriver driver, AntdConfig config) {
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
     * Whether the tab is currently activated.
     *
     * @return true if the tab is currently activated
     */
    public boolean isActivated() {
        return attributeContains(CLASS, config.getPrefixCls() + "-" + NAME + "-active");
    }

    /**
     * Whether the tab is disabled.
     *
     * @return true if the tab is disabled
     */
    public boolean isDisabled() {
        return attributeContains(CLASS, config.getPrefixCls() + "-" + NAME + "-disabled");
    }

    /**
     * Gets the tab label text.
     *
     * @return the tab label text
     */
    public String getLabel() {
        return getText();
    }
}
