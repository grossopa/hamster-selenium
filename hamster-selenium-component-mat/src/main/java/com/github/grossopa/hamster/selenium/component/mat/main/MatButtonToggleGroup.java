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

package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.config.MatConfig.ATTR_CLASS;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;

/**
 * The group container of {@link MatButtonToggle}.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/button-toggle/overview">
 * https://material.angular.io/components/button-toggle/overview</a>
 * @since 1.6
 */
public class MatButtonToggleGroup extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "ButtonToggleGroup";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatButtonToggleGroup(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(ATTR_CLASS, config.getCssPrefix() + "button-toggle-group");
    }

    /**
     * Finds the contained {@link MatButtonToggle} list.
     *
     * @return the contained {@link MatButtonToggle} list.
     */
    public List<MatButtonToggle> getButtonToggles() {
        return this.findComponentsAs(xpathBuilder().relative(config.getTagPrefix() + "button-toggle").attr(ATTR_CLASS)
                        .contains(config.getCssPrefix() + "button-toggle").build(),
                c -> new MatButtonToggle(c, driver, config));
    }

    @Override
    public String toString() {
        return "MatButtonToggleGroup{" + "element=" + element + '}';
    }
}
