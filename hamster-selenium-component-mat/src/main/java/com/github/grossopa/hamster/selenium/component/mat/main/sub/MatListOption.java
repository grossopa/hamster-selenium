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
package com.github.grossopa.hamster.selenium.component.mat.main.sub;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jakarta.annotation.Nullable;
import java.util.List;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * Component for list-options of selection-list. Each list-option can automatically generate a checkbox and can put
 * current item into the selectionModel of selection-list if the current item is selected.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/list/api#MatListOption">
 * https://material.angular.io/components/list/api#MatListOption</a>
 * @since 1.6
 */
public class MatListOption extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "ListOption";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatListOption(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getComponentCssPrefix() + "list-option");
    }

    @Override
    public boolean isSelected() {
        String ariaSelected = this.getDomAttribute("aria-selected");
        if ("true".equalsIgnoreCase(ariaSelected)) {
            return true;
        }
        // MDC selection list uses native <input type="checkbox"> instead of pseudo-checkbox
        List<WebElement> nativeCheckboxes = element.findElements(By.cssSelector("input[type=checkbox]"));
        if (!nativeCheckboxes.isEmpty()) {
            return nativeCheckboxes.get(0).isSelected();
        }
        return false;
    }

    @Override
    public boolean isEnabled() {
        return !"true".equalsIgnoreCase(this.getDomAttribute("aria-disabled"));
    }

    /**
     * Finds the inner pseudo checkbox. Return null if not found.
     *
     * @return the checkbox or null if not found.
     */
    @Nullable
    public MatPseudoCheckbox getCheckbox() {
        List<MatPseudoCheckbox> checkboxList = this.findComponentsAs(
                By.className(config.getComponentCssPrefix() + "pseudo-checkbox"),
                c -> new MatPseudoCheckbox(c, driver, config));
        if (!checkboxList.isEmpty()) {
            return checkboxList.get(0);
        }
        // MDC uses native <div class="mdc-checkbox"> instead of pseudo-checkbox
        List<WebElement> mdcCheckboxes = element.findElements(By.className("mdc-checkbox"));
        if (!mdcCheckboxes.isEmpty()) {
            return new MatPseudoCheckbox(new DefaultWebComponent(mdcCheckboxes.get(0), driver), driver, config);
        }
        return null;
    }
}
