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
package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * {@code <mat-tab-group>} organizes content into separate views where only one view is visible at a time.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/tabs/overview">
 * https://material.angular.io/components/tabs/overview</a>
 * @since 1.16
 */
public class MatTabGroup extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "TabGroup";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatTabGroup(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "tab-group");
    }

    /**
     * Gets all tab labels from the tab header.
     *
     * @return the list of tab label elements
     */
    public List<WebComponent> getTabLabels() {
        return this.findComponents(By.className(config.getCssPrefix() + "tab-label"));
    }

    /**
     * Gets all tabs from the tab body.
     *
     * @return the list of tab elements
     */
    public List<MatTab> getTabs() {
        return this.findComponentsAs(By.className(config.getCssPrefix() + "tab"),
                c -> new MatTab(c, driver, config));
    }

    /**
     * Selects a tab by index.
     *
     * @param index the zero-based tab index
     */
    public void selectTab(int index) {
        getTabLabels().get(index).click();
    }

    /**
     * Gets the index of the currently selected tab.
     *
     * @return the selected tab index
     */
    public int getSelectedTabIndex() {
        List<WebComponent> labels = getTabLabels();
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).getAttribute(CLASS).contains(config.getCssPrefix() + "tab-label-active")) {
                return i;
            }
        }
        return -1;
    }
}
