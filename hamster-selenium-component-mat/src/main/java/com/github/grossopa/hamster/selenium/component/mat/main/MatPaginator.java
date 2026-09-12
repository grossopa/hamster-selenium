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
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatPaginator(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "paginator");
    }

    /**
     * Gets the page size range label text.
     *
     * @return the range label text (e.g. "1 – 10 of 50")
     */
    public String getRangeLabel() {
        return this.findComponent(By.className(config.getCssPrefix() + "paginator-range-label")).getText();
    }

    /**
     * Gets the page size select element.
     *
     * @return the page size select element
     */
    public WebComponent getPageSizeSelect() {
        return this.findComponent(By.className(config.getCssPrefix() + "paginator-page-size"));
    }

    /**
     * Clicks the next page button.
     */
    public void nextPage() {
        this.findComponent(By.className(config.getCssPrefix() + "paginator-navigation-next")).click();
    }

    /**
     * Clicks the previous page button.
     */
    public void previousPage() {
        this.findComponent(By.className(config.getCssPrefix() + "paginator-navigation-previous")).click();
    }

    /**
     * Clicks the first page button.
     */
    public void firstPage() {
        this.findComponent(By.className(config.getCssPrefix() + "paginator-navigation-first")).click();
    }

    /**
     * Clicks the last page button.
     */
    public void lastPage() {
        this.findComponent(By.className(config.getCssPrefix() + "paginator-navigation-last")).click();
    }

    /**
     * Gets the available page size options.
     *
     * @return the list of page size option texts
     */
    public List<String> getPageSizeOptions() {
        return this.findComponents(By.className(config.getCssPrefix() + "paginator-page-size-label"))
                .stream().map(WebComponent::getText).toList();
    }
}
