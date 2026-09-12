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
import com.microsoft.playwright.Locator;

import java.util.List;

/**
 * {@code <mat-paginator>} provides navigation for paged data, displaying the current page range and allowing
 * users to change pages.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/paginator/overview">
 * https://material.angular.io/components/paginator/overview</a>
 * @since 1.16
 */
public class MatPaginator extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Paginator";

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatPaginator(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "paginator");
    }

    /**
     * Gets the page size range label text.
     *
     * @return the range label text (e.g. "1 – 10 of 50")
     */
    public String getRangeLabel() {
        return this.findComponent("." + config.getCssPrefix() + "paginator-range-label").innerText();
    }

    /**
     * Gets the page size select element.
     *
     * @return the page size select element
     */
    public WebComponent getPageSizeSelect() {
        return this.findComponent("." + config.getCssPrefix() + "paginator-page-size");
    }

    /**
     * Clicks the next page button.
     */
    public void nextPage() {
        this.findComponent("." + config.getCssPrefix() + "paginator-navigation-next").click();
    }

    /**
     * Clicks the previous page button.
     */
    public void previousPage() {
        this.findComponent("." + config.getCssPrefix() + "paginator-navigation-previous").click();
    }

    /**
     * Clicks the first page button.
     */
    public void firstPage() {
        this.findComponent("." + config.getCssPrefix() + "paginator-navigation-first").click();
    }

    /**
     * Clicks the last page button.
     */
    public void lastPage() {
        this.findComponent("." + config.getCssPrefix() + "paginator-navigation-last").click();
    }

    /**
     * Gets the available page size options.
     *
     * @return the list of page size option texts
     */
    public List<String> getPageSizeOptions() {
        return this.findComponents("." + config.getCssPrefix() + "paginator-page-size-label")
                .stream().map(WebComponent::innerText).toList();
    }
}
