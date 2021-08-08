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
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.hamster.selenium.component.mat.config.MatConfig.ATTR_CLASS;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;

/**
 * {@code <mat-form-field>} is a component used to wrap several Angular Material components and apply common Text field
 * styles such as the underline, floating label, and hint messages.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/form-field/overview">
 * https://material.angular.io/components/form-field/overview</a>
 * @since 1.6
 */
public class MatFormField extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "FormField";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MatFormField(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(ATTR_CLASS, config.getCssPrefix() + "form-field");
    }

    /**
     * Gets the prefix container that before the input tag.
     *
     * @return the prefix container that before the input tag.
     */
    public WebComponent getPrefix() {
        return this.findComponent(By.className(config.getCssPrefix() + "form-field-prefix"));
    }

    /**
     * Gets the primary container that contains the input tag.
     *
     * @return the primary container that contains the input tag.
     */
    public WebComponent getInfix() {
        return this.findComponent(By.className(config.getCssPrefix() + "form-field-infix"));
    }

    /**
     * Gets the prefix container that after the input tag.
     *
     * @return the prefix container that after the input tag.
     */
    public WebComponent getSuffix() {
        return this.findComponent(By.className(config.getCssPrefix() + "form-field-suffix"));
    }

    /**
     * Gets the hint label.
     *
     * @return the hint label.
     */
    public WebComponent getHint() {
        return this.findComponent(By.className(config.getCssPrefix() + "hint"));
    }

    /**
     * Gets the inner input element
     *
     * @return the inner input element
     */
    public WebComponent getInput() {
        return this.getInfix().findComponent(By.xpath("./input"));
    }

    /**
     * Gets the label element
     *
     * @return the label element
     */
    public WebComponent getLabel() {
        return this.getInfix().findComponent(xpathBuilder().anywhereRelative().attr(ATTR_CLASS)
                .contains(config.getCssPrefix() + "form-field-label-wrapper").child("label").axes().child("mat-label")
                .build());
    }

    /**
     * Gets the error label element
     *
     * @return the error label element
     */
    public WebComponent getError() {
        return this.findComponent(By.className(config.getCssPrefix() + "form-field-subscript-wrapper"))
                .findComponent(By.className(config.getCssPrefix() + "error"));
    }

    @Override
    public String toString() {
        return "MatFormField{" + "element=" + element + '}';
    }
}
