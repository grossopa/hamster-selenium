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

package com.github.grossopa.selenium.core.component;

import static com.github.grossopa.selenium.core.component.util.WebComponentUtils.attributeContains;
import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;

/**
 * The root configuration of one front-end framework. e.g. Material UI.
 *
 * <p>
 * The exposed configurations are either configurable in the framework (e.g. css prefix) or customizable by front-end.
 * (e.g. overlay position is always under the root HTML node but not necessarily of /html/body)
 * </p>
 *
 * @author Jack Yin
 * @since 1.7
 */
public interface ComponentConfig {

    /**
     * The overlays are displayed in the root level of React applications, this attribute helps to locate the container
     * of the overlays such as Modal, Dialog, etc.
     *
     * <p>The default value is {@code /html/body}.</p>
     *
     * @return the overlay absolute xpath.
     */
    String getOverlayAbsolutePath();

    /**
     * Checks whether the component has the checked css present.
     *
     * @param component the component to check
     * @return whether the checked css presents
     * @see #getIsCheckedCss()
     */
    default boolean isChecked(WebComponent component) {
        return attributeContains(component, CLASS, getIsCheckedCss());
    }

    /**
     * Checks whether the component has the selected css present.
     *
     * @param component the component to check
     * @return whether the selected css presents
     * @see #getIsSelectedCss()
     */
    default boolean isSelected(WebComponent component) {
        return attributeContains(component, CLASS, getIsSelectedCss());
    }

    /**
     * Checks whether the component has the disabled css present.
     *
     * @param component the component to check
     * @return whether the disabled css presents
     * @see #getIsDisabledCss()
     */
    default boolean isDisabled(WebComponent component) {
        return !component.getWrappedElement().isEnabled() || attributeContains(component, CLASS, getIsDisabledCss());
    }

    /**
     * Gets the isChecked CSS. default value is "Mui-checked"
     *
     * @return the isChecked CSS
     */
    default String getIsCheckedCss() {
        return getCssPrefix() + "-checked";
    }

    /**
     * Gets the isSelected CSS. default value is "Mui-selected"
     *
     * @return the isSelected CSS
     */
    default String getIsSelectedCss() {
        return getCssPrefix() + "-selected";
    }

    /**
     * Gets the isDisabled CSS. default value is "Mui-disabled"
     *
     * @return the isDisabled CSS
     */
    default String getIsDisabledCss() {
        return getCssPrefix() + "-disabled";
    }

    /**
     * Gets the css prefix of the framework
     *
     * @return the css prefix of the framework
     */
    String getCssPrefix();

}
