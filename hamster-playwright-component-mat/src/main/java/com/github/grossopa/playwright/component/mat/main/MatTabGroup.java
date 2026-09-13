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
package com.github.grossopa.playwright.component.mat.main;

import com.github.grossopa.playwright.component.mat.AbstractMatComponent;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.core.DefaultWebComponent;
import com.microsoft.playwright.Locator;

import java.util.List;

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
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatTabGroup(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getComponentCssPrefix() + "tab-group");
    }

    /**
     * Gets all tab labels from the tab header.
     *
     * @return the list of tab label elements
     */
    public List<WebComponent> getTabLabels() {
        // MDC uses .mat-mdc-tab on <div role="tab">; legacy uses .mat-tab-label
        Locator mdcLabels = locator.locator("." + config.getComponentCssPrefix() + "tab");
        if (mdcLabels.count() > 0) {
            return mdcLabels.all().stream()
                    .map(l -> (WebComponent) new DefaultWebComponent(l, driver)).toList();
        }
        return this.findComponents("." + config.getComponentCssPrefix() + "tab-label");
    }

    /**
     * Gets all tabs.
     *
     * @return the list of tab elements
     */
    public List<MatTab> getTabs() {
        return this.findComponents(config.getTagPrefix() + "tab." + config.getComponentCssPrefix() + "tab")
                .stream().map(c -> new MatTab(c, driver, config)).toList();
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
            String cls = labels.get(i).getAttribute(CLASS);
            // MDC uses .mdc-tab--active; legacy uses .mat-tab-label-active
            if (cls != null && (cls.contains("mdc-tab--active")
                    || cls.contains(config.getComponentCssPrefix() + "tab-label-active"))) {
                return i;
            }
        }
        return -1;
    }
}
