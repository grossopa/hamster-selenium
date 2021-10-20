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

import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;

/**
 * {@code <mat-expansion-panel>} provides an expandable details-summary view.
 *
 * <p>
 * Use {@link MatAccordion} for parent {@code <mat-accordion />}.
 * </p>
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/expansion/overview">
 * https://material.angular.io/components/expansion/overview</a>
 * @since 1.6
 */
public class MatExpansionPanel extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "ExpansionPanel";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatExpansionPanel(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "expansion-panel");
    }

    @Override
    public boolean isEnabled() {
        return element.getAttribute("disabled") == null;
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Whether the expansion panel has expanded
     *
     * @return true if the expansion panel has expanded
     */
    public boolean isExpanded() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "expanded");
    }

    /**
     * Expands the expansion panel by clicking the header.
     */
    public void expand() {
        if (!isExpanded()) {
            this.getExpansionPanelHeader().click();
        }
    }

    /**
     * Collapses the expansion panel by clicking the header
     */
    public void collapse() {
        if (isExpanded()) {
            this.getExpansionPanelHeader().click();
        }
    }

    /**
     * Finds the expansion panel header element.
     *
     * @return the expansion panel header element.
     */
    public WebComponent getExpansionPanelHeader() {
        return this.findComponent(By.className(config.getCssPrefix() + "expansion-panel-header"));
    }

    /**
     * Finds the expansion panel body element.
     *
     * @return the expansion panel body element.
     */
    public WebComponent getExpansionPanelBody() {
        return this.findComponent(By.className(config.getCssPrefix() + "expansion-panel-body"));
    }
}
