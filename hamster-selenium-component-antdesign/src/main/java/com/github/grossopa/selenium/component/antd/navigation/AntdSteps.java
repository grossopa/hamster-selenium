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
package com.github.grossopa.selenium.component.antd.navigation;

import com.github.grossopa.selenium.component.antd.AbstractAntdComponent;
import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * A navigation bar that guides users through the steps of a task.
 *
 * @author Jack Yin
 * @see <a href="https://ant.design/components/steps">https://ant.design/components/steps</a>
 * @since 1.15
 */
public class AntdSteps extends AbstractAntdComponent {

    /**
     * The component name
     */
    public static final String NAME = "steps";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver root driver
     * @param config the global Antd configuration
     */
    public AntdSteps(WebElement element, ComponentWebDriver driver, AntdConfig config) {
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
     * Gets all step item components in the displayed order.
     *
     * @return all step item components in the displayed order
     */
    public List<WebComponent> getSteps() {
        return findComponents(By.className(config.getPrefixCls() + "-" + NAME + "-item"));
    }

    /**
     * Gets the index of the current active step, the step with the {@code wait} status before the current step is
     * {@code finish} and the step after the current step is {@code wait}.
     *
     * @return the 0-based index of the current active step, -1 if no step is under {@code process} status.
     */
    public int getCurrentStep() {
        String processClass = config.getPrefixCls() + "-" + NAME + "-item-process";
        List<WebComponent> steps = getSteps();
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).attributeContains(CLASS, processClass)) {
                return i;
            }
        }
        return -1;
    }
}
