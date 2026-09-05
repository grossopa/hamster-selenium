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
package com.github.grossopa.playwright.component.mat.main.sub;

import com.github.grossopa.playwright.component.mat.AbstractMatComponent;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import jakarta.annotation.Nullable;

import java.util.List;

/**
 * An option of the {@link com.github.grossopa.playwright.component.mat.main.MatSelectionList}.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class MatListOption extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "ListOption";

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatListOption(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    /**
     * Constructs an instance with the delegated web component and root driver.
     *
     * @param component the delegated web component
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatListOption(WebComponent component, ComponentDriver driver, MatConfig config) {
        super(component.locator(), driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "list-option");
    }

    @Override
    public boolean isSelected() {
        return "true".equalsIgnoreCase(getAttribute("aria-selected"));
    }

    @Override
    public boolean isEnabled() {
        return !"true".equalsIgnoreCase(getAttribute("aria-disabled"));
    }

    /**
     * Finds the inner pseudo checkbox. Returns null if not found.
     *
     * @return the checkbox or null if not found
     */
    @Nullable
    public MatPseudoCheckbox getCheckbox() {
        List<MatPseudoCheckbox> checkboxList = this.findComponents(
                "." + config.getCssPrefix() + "pseudo-checkbox").stream().map(
                c -> new MatPseudoCheckbox(c, driver, config)).toList();
        return checkboxList.isEmpty() ? null : checkboxList.get(0);
    }
}
