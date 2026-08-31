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
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * Select component to select value from options. It wraps the select container {@code div} with class
 * {@code ant-select}, the options container is rendered in the popup container configured by
 * {@link AntdConfig#getPopupContainerLocator()}.
 *
 * @author Jack Yin
 * @see <a href="https://ant.design/components/select">https://ant.design/components/select</a>
 * @since 1.15
 */
public class AntdSelect extends AbstractAntdComponent {

    /**
     * The component name
     */
    public static final String NAME = "select";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver root driver
     * @param config the global Antd configuration
     */
    public AntdSelect(WebElement element, ComponentWebDriver driver, AntdConfig config) {
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
     * Whether the options container is currently open.
     *
     * @return true if the options container is currently open
     */
    public boolean isOptionsOpen() {
        return attributeContains(CLASS, config.getPrefixCls() + "-" + NAME + "-open");
    }

    /**
     * Whether the select is disabled.
     *
     * @return true if the select is disabled
     */
    @Override
    public boolean isEnabled() {
        return !attributeContains(CLASS, config.getPrefixCls() + "-" + NAME + "-disabled");
    }

    /**
     * Gets the displayed text of the current selected item. This is only applicable for the single mode select.
     *
     * @return the displayed text of the current selected item
     * @throws NoSuchElementException if the selection item element is not found
     */
    public String getSelectText() {
        return findComponent(By.className(config.getPrefixCls() + "-" + NAME + "-selection-item")).getText();
    }

    /**
     * Opens the options container if it is not open and returns the options container component.
     *
     * @return the options container component
     * @throws NoSuchElementException if the options container can not be found
     */
    public WebComponent openOptions() {
        if (!isOptionsOpen()) {
            click();
        }
        String dropdownClass = config.getPrefixCls() + "-" + NAME + "-dropdown";
        return driver.findComponent(config.getPopupContainerLocator()).findComponents(By.className(dropdownClass))
                .stream().filter(component -> !component.attributeContains(CLASS, dropdownClass + "-hidden"))
                .findFirst().orElseThrow(() -> new NoSuchElementException("No options container found"));
    }

    /**
     * Closes the options container if it is currently open by pressing the Escape key.
     */
    public void closeOptions() {
        if (isOptionsOpen()) {
            sendKeys(Keys.ESCAPE);
        }
    }

    /**
     * Gets the list of currently displayed options, the options container will be opened if it is not open.
     *
     * @return the list of currently displayed options
     */
    public List<AntdSelectOption> getOptions() {
        return openOptions().findComponentsAs(By.className(config.getPrefixCls() + "-" + NAME + "-item-option"),
                component -> new AntdSelectOption(component, driver, config));
    }

    /**
     * Selects the option by its displayed text, the options container will be opened if it is not open.
     *
     * @param text the displayed text of the option to select
     * @throws NoSuchElementException if no option with the given text is found
     */
    public void selectOption(String text) {
        AntdSelectOption option = getOptions().stream().filter(item -> text.equals(item.getText())).findFirst()
                .orElseThrow(() -> new NoSuchElementException("No option found with text: " + text));
        option.click();
    }
}
