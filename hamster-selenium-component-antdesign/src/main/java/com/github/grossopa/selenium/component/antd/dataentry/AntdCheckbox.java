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

/**
 * Checkbox component. It could wrap either the checkbox container {@code div} with class {@code ant-checkbox-wrapper}
 * or the checkbox itself with class {@code ant-checkbox}.
 *
 * @author Jack Yin
 * @see <a href="https://ant.design/components/checkbox">https://ant.design/components/checkbox</a>
 * @since 1.15
 */
public class AntdCheckbox extends AbstractAntdComponent {

    /**
     * The component name
     */
    public static final String NAME = "checkbox";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver root driver
     * @param config the global Antd configuration
     */
    public AntdCheckbox(WebElement element, ComponentWebDriver driver, AntdConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getPrefixCls() + "-" + NAME + "-wrapper") || attributeContains(CLASS,
                config.getPrefixCls() + "-" + NAME);
    }

    /**
     * Whether the checkbox is checked.
     *
     * @return true if the checkbox is checked
     */
    @Override
    public boolean isSelected() {
        return getCheckbox().attributeContains(CLASS, config.getPrefixCls() + "-" + NAME + "-checked");
    }

    /**
     * Whether the checkbox is under indeterminate status, which means the checkbox is usually used for the partial
     * selection of a group of options.
     *
     * @return true if the checkbox is under indeterminate status
     */
    public boolean isIndeterminate() {
        return getCheckbox().attributeContains(CLASS, config.getPrefixCls() + "-" + NAME + "-indeterminate");
    }

    /**
     * Whether the checkbox is disabled.
     *
     * @return true if the checkbox is disabled
     */
    @Override
    public boolean isEnabled() {
        return !getCheckbox().attributeContains(CLASS, config.getPrefixCls() + "-" + NAME + "-disabled");
    }

    /**
     * Gets the checkbox container which carries the status related css classes.
     *
     * @return the checkbox container which carries the status related css classes
     */
    public WebComponent getCheckbox() {
        if (attributeContains(CLASS, config.getPrefixCls() + "-" + NAME)) {
            return this;
        }
        return findComponent(By.className(config.getPrefixCls() + "-" + NAME));
    }
}
