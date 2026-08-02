/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.component.mui.config;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.core.WebComponent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The root configuration for Material UI components in Playwright.
 *
 * <p>This class provides configuration options for customizing the behavior of Material UI components,
 * including CSS prefixes, version settings, and overlay paths.</p>
 *
 * @since 1.12
 */
public class MuiConfig {

    /**
     * The MUI version
     */
    private MuiVersion version = MuiVersion.V4;

    /**
     * Default CSS prefix by Material UI framework
     */
    private String cssPrefix = "Mui";

    @SuppressWarnings("java:S1075")
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
     * Builds the root component CSS name from the component name.
     *
     * @param componentName the component name in camel format
     * @return the built CSS class name
     */
    public String getRootCss(String componentName) {
        return cssPrefix + componentName + "-root";
    }

    /**
     * Gets the modals class list: Drawer, Dialog, Popover, Pager, and Menu.
     *
     * @return the modals class list
     */
    public Set<String> getModalClasses() {
        Set<String> classes = new HashSet<>();
        classes.add(getRootCss("Drawer"));
        classes.add(getRootCss("Dialog"));
        classes.add(getRootCss("Popover"));
        classes.add(getRootCss("Pager"));
        classes.add(getRootCss("Menu"));
        return classes;
    }

    /**
     * Gets the CSS prefix
     *
     * @return the CSS prefix
     */
    public String getCssPrefix() {
        return cssPrefix;
    }

    /**
     * Sets the global CSS prefix
     *
     * @param cssPrefix the global CSS prefix
     */
    public void setCssPrefix(String cssPrefix) {
        this.cssPrefix = cssPrefix;
    }

    /**
     * Gets the MUI version
     *
     * @return the MUI version
     */
    public MuiVersion getVersion() {
        return version;
    }

    /**
     * Sets the MUI version
     *
     * @param version the MUI version
     */
    public void setVersion(MuiVersion version) {
        this.version = version;
    }

    /**
     * For locating the thumb element of the Slider.
     *
     * @return the CSS selector for Slider thumb
     */
    public String sliderThumbLocator() {
        return "." + cssPrefix + "Slider-thumb";
    }

    /**
     * Checks whether the component has the checked CSS class present.
     *
     * @param component the component to check
     * @return whether the checked CSS presents
     */
    public boolean isChecked(WebComponent component) {
        if (component == null) {
            return false;
        }
        String className = component.getAttribute(CLASS);
        return className != null && className.contains(getIsCheckedCss());
    }

    /**
     * Checks whether the component has the selected CSS class present.
     *
     * @param component the component to check
     * @return whether the selected CSS presents
     */
    public boolean isSelected(WebComponent component) {
        if (component == null) {
            return false;
        }
        String className = component.getAttribute(CLASS);
        return className != null && className.contains(getIsSelectedCss());
    }

    /**
     * Checks whether the component has the disabled CSS class present.
     *
     * @param component the component to check
     * @return whether the disabled CSS presents
     */
    public boolean isDisabled(WebComponent component) {
        if (component == null) {
            return true;
        }
        String className = component.getAttribute(CLASS);
        return className != null && className.contains(getIsDisabledCss());
    }

    /**
     * Gets the isChecked CSS. Default value is "Mui-checked"
     *
     * @return the isChecked CSS
     */
    public String getIsCheckedCss() {
        return cssPrefix + "-checked";
    }

    /**
     * Gets the isSelected CSS. Default value is "Mui-selected"
     *
     * @return the isSelected CSS
     */
    public String getIsSelectedCss() {
        return cssPrefix + "-selected";
    }

    /**
     * Gets the isDisabled CSS. Default value is "Mui-disabled"
     *
     * @return the isDisabled CSS
     */
    public String getIsDisabledCss() {
        return cssPrefix + "-disabled";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiConfig)) {
            return false;
        }
        MuiConfig muiConfig = (MuiConfig) o;
        return version == muiConfig.version &&
                Objects.equals(cssPrefix, muiConfig.cssPrefix) &&
                Objects.equals(overlayAbsolutePath, muiConfig.overlayAbsolutePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, cssPrefix, overlayAbsolutePath);
    }

    @Override
    public String toString() {
        return "MuiConfig{" +
                "version=" + version +
                ", cssPrefix='" + cssPrefix + '\'' +
                ", overlayAbsolutePath='" + overlayAbsolutePath + '\'' +
                '}';
    }
}
