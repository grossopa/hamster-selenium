/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.component.mui.config;

import lombok.Getter;
import lombok.Setter;
import org.hamster.selenium.core.component.WebComponent;
import org.hamster.selenium.core.locator.By2;
import org.openqa.selenium.By;

import static org.hamster.selenium.core.component.util.WebComponentUtils.attributeContains;

/**
 * The root configuration for material UI components
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiConfig {

    /**
     * class attribute name
     */
    public static final String ATTR_CLASS = "class";
    /**
     * Default css prefix by Material UI framework
     */
    @Setter
    @Getter
    private String cssPrefix = "Mui";

    /**
     * For locating the button from direct parent container
     *
     * @return the instance of button locator
     */
    public By buttonLocator() {
        return By2.attr(ATTR_CLASS, getRootCss("Button")).contains().build();
    }

    /**
     * For locating the popover layer of current page.
     * <p>
     * Default xpath: /html/body/div[contains(@class, 'MuiPopover')]
     * </p>
     *
     * @return the instance of popover locator
     */
    public By popoverLocator() {
        return By.xpath("/html/body/div[contains(@class, '" + getRootCss("Popover") + "')]");
    }

    /**
     * Checks whether the component has the checked css present.
     *
     * @param component
     *         the component to check
     * @return whether the checked css presents
     * @see #getIsCheckedCss()
     */
    public boolean isChecked(WebComponent component) {
        return attributeContains(component, ATTR_CLASS, getIsCheckedCss());
    }

    /**
     * Checks whether the component has the selected css present.
     *
     * @param component
     *         the component to check
     * @return whether the selected css presents
     * @see #getIsSelectedCss()
     */
    public boolean isSelected(WebComponent component) {
        return attributeContains(component, ATTR_CLASS, getIsSelectedCss());
    }

    /**
     * Checks whether the component has the disabled css present.
     *
     * @param component
     *         the component to check
     * @return whether the disabled css presents
     * @see #getIsDisabledCss()
     */
    public boolean isDisabled(WebComponent component) {
        return attributeContains(component, ATTR_CLASS, getIsDisabledCss());
    }

    /**
     * Gets the isChecked CSS. default value is "Mui-checked"
     *
     * @return the isChecked CSS
     */
    public String getIsCheckedCss() {
        return cssPrefix + "-checked";
    }

    /**
     * Gets the isSelected CSS. default value is "Mui-selected"
     *
     * @return the isSelected CSS
     */
    public String getIsSelectedCss() {
        return cssPrefix + "-selected";
    }

    /**
     * Gets the isDisabled CSS. default value is "Mui-disabled"
     *
     * @return the isDisabled CSS
     */
    public String getIsDisabledCss() {
        return cssPrefix + "-disabled";
    }

    /**
     * Builds the root component css name from the component name, it is used for determining the root node of all MUI
     * component.
     *
     * @param componentName
     *         the component name in camel format
     * @return the built css
     */
    public String getRootCss(String componentName) {
        return cssPrefix + componentName + "-root";
    }

    /**
     * Validates whether the given component is the desired component by checking the css present.
     *
     * @param component
     *         the target component to check
     * @param componentName
     *         the component name
     * @return true for the {@link WebComponent} is the desired component type
     * @see #getRootCss(String)
     */
    public boolean validateByCss(WebComponent component, String componentName) {
        return attributeContains(component, ATTR_CLASS, getRootCss(componentName));
    }
}
