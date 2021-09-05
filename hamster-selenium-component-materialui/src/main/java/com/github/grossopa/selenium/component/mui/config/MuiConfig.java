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

package com.github.grossopa.selenium.component.mui.config;

import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;

import java.util.Objects;
import java.util.Set;

import static com.github.grossopa.selenium.core.component.util.WebComponentUtils.attributeContains;
import static com.google.common.collect.Sets.newHashSet;

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

    @SuppressWarnings("squid:S1075")
    private String overlayAbsolutePath = "/html/body";

    /**
     * The overlays are displayed in the root level of React applications, this attribute helps to locate the container
     * of the overlays such as Modal, Dialog, etc.
     *
     * <p>The default value is {@code /html/body}.</p>
     *
     * @param overlayAbsolutePath the new overlay absolute path to set
     */
    public void setOverlayAbsolutePath(String overlayAbsolutePath) {
        this.overlayAbsolutePath = overlayAbsolutePath;
    }

    /**
     * The overlays are displayed in the root level of React applications, this attribute helps to locate the container
     * of the overlays such as Modal, Dialog, etc.
     *
     * <p>The default value is {@code /html/body}.</p>
     *
     * @return the overlay absolute xpath.
     */
    public String getOverlayAbsolutePath() {
        return overlayAbsolutePath;
    }

    /**
     * For locating the button from direct parent container
     *
     * @return the instance of button locator
     */
    public By buttonLocator() {
        return By2.attrContains(ATTR_CLASS, getRootCss("Button"));
    }

    /**
     * For locating the radio from direct parent container
     *
     * @return the instance of radio locator
     */
    public By radioLocator() {
        return By.className(getRootCss("Radio"));
    }

    /**
     * Gets the Menu Pager elements inner the MuiPopover-root which is bound to a MuiMenu component.
     *
     * @return the Menu Pager elements inner the MuiPopover-root which is bound to a MuiMenu component.
     */
    public By menuPagerLocator() {
        return By.className(cssPrefix + "Menu-pager");
    }

    /**
     * For locating the thumb element of the Slider.
     *
     * @return the instance of Slider thumb locator
     */
    public By sliderThumbLocator() {
        return By.className(cssPrefix + "Slider-thumb");
    }

    /**
     * Checks whether the component has the checked css present.
     *
     * @param component the component to check
     * @return whether the checked css presents
     * @see #getIsCheckedCss()
     */
    public boolean isChecked(WebComponent component) {
        return attributeContains(component, ATTR_CLASS, getIsCheckedCss());
    }

    /**
     * Checks whether the component has the selected css present.
     *
     * @param component the component to check
     * @return whether the selected css presents
     * @see #getIsSelectedCss()
     */
    public boolean isSelected(WebComponent component) {
        return attributeContains(component, ATTR_CLASS, getIsSelectedCss());
    }

    /**
     * Checks whether the component has the disabled css present.
     *
     * @param component the component to check
     * @return whether the disabled css presents
     * @see #getIsDisabledCss()
     */
    public boolean isDisabled(WebComponent component) {
        return attributeContains(component, ATTR_CLASS, getIsDisabledCss());
    }

    /**
     * Checks whether the Grid has the container css present.
     *
     * @param component the component to check
     * @return whether the container css presents
     */
    public boolean isGridContainer(WebComponent component) {
        return attributeContains(component, ATTR_CLASS, cssPrefix + "Grid-container");
    }

    /**
     * Checks whether the Grid has the item css present.
     *
     * @param component the component to check
     * @return whether the item css presents
     */
    public boolean isGridItem(WebComponent component) {
        return attributeContains(component, ATTR_CLASS, cssPrefix + "Grid-item");
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
     * @param componentName the component name in camel format
     * @return the built css
     */
    public String getRootCss(String componentName) {
        return cssPrefix + componentName + "-root";
    }

    /**
     * Validates whether the given component is the desired component by checking the css present.
     *
     * @param component the target component to check
     * @param componentName the component name
     * @return true for the {@link WebComponent} is the desired component type
     * @see #getRootCss(String)
     */
    public boolean validateComponentByCss(WebComponent component, String componentName) {
        return validateByCss(component, getRootCss(componentName));
    }

    /**
     * Validates whether the given component / item is the desired component by checking the css present.
     *
     * @param component the target component to check
     * @param cssName the expected css name
     * @return true for the {@link WebComponent} is the desired component/ item type
     * @see #getRootCss(String)
     */
    public boolean validateByCss(WebComponent component, String cssName) {
        return attributeContains(component, ATTR_CLASS, cssName);
    }

    /**
     * Gets the modals class list, they are Drawer, Dialog, Popover and Pager.
     *
     * @return the modals class list
     */
    public Set<String> getModalClasses() {
        return newHashSet(getRootCss("Drawer"), getRootCss("Dialog"), getRootCss("Popover"), getRootCss("Pager"),
                getRootCss("Menu"));
    }

    @Override
    @SuppressWarnings("java:S6212")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiConfig)) {
            return false;
        }
        MuiConfig muiConfig = (MuiConfig) o;
        return cssPrefix.equals(muiConfig.cssPrefix) && overlayAbsolutePath.equals(muiConfig.overlayAbsolutePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cssPrefix, overlayAbsolutePath);
    }

    @Override
    public String toString() {
        return "MuiConfig{" + "cssPrefix='" + cssPrefix + '\'' + ", overlayAbsolutePath='" + overlayAbsolutePath + '\''
                + '}';
    }
}
