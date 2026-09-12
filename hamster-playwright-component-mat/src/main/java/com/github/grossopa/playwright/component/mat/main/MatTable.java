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
 * {@code <mat-table>} provides a data table for displaying rows of data in a tabular format with Material Design
 * styling.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/table/overview">
 * https://material.angular.io/components/table/overview</a>
 * @since 1.16
 */
public class MatTable extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Table";

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatTable(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "table");
    }

    /**
     * Gets the header cells.
     *
     * @return the list of header cell elements
     */
    public List<WebComponent> getHeaderCells() {
        return this.findComponents("." + config.getCssPrefix() + "header-cell");
    }

    /**
     * Gets all data rows.
     *
     * @return the list of row elements
     */
    public List<WebComponent> getRows() {
        return this.findComponents("." + config.getCssPrefix() + "row");
    }

    /**
     * Gets the cells of a specific row.
     *
     * @param rowIndex the zero-based row index
     * @return the list of cell elements in the row
     */
    public List<WebComponent> getRowCells(int rowIndex) {
        return getRows().get(rowIndex).findComponents("." + config.getCssPrefix() + "cell");
    }

    /**
     * Gets the text value of a specific cell.
     *
     * @param rowIndex the zero-based row index
     * @param cellIndex the zero-based cell index
     * @return the cell text content
     */
    public String getCellValue(int rowIndex, int cellIndex) {
        return getRowCells(rowIndex).get(cellIndex).innerText();
    }
}
