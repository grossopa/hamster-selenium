/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.component.html;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.DefaultWebComponent;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The HTML table row component.
 *
 * @author Jack Yin
 * @since 1.12
 */
public class HtmlTableRow extends DefaultWebComponent {

    private final List<String> headerLabels;

    /**
     * Constructs an instance with the given locator and driver
     *
     * @param locator the locator instance
     * @param driver the driver instance
     */
    public HtmlTableRow(Locator locator, ComponentDriver driver) {
        this(locator, driver, Collections.emptyList());
    }

    /**
     * Constructs an instance with the given locator, driver and header labels
     *
     * @param locator the locator instance
     * @param driver the driver instance
     * @param headerLabels the header labels for column lookup
     */
    public HtmlTableRow(Locator locator, ComponentDriver driver, List<String> headerLabels) {
        super(locator, driver);
        this.headerLabels = headerLabels != null ? headerLabels : Collections.emptyList();
    }

    @Override
    public String getComponentTagName() {
        return "tr";
    }

    /**
     * Gets all the cells in current row
     *
     * @return all the cells in current row
     */
    public List<WebComponent> getCells() {
        return this.findComponents("td, th");
    }

    /**
     * Gets the cell by index
     *
     * @param index the cell index
     * @return the cell at given index
     */
    public WebComponent getCell(int index) {
        return this.findComponents("td, th").get(index);
    }

    /**
     * Gets the cell by header label
     *
     * @param headerLabel the header label to look up
     * @return the cell at the column matching the header label
     * @throws NoSuchElementException if the header label is not found
     */
    public WebComponent getCell(String headerLabel) {
        int index = headerLabels.indexOf(headerLabel);
        if (index == -1) {
            throw new NoSuchElementException("No such column with header label: " + headerLabel);
        }
        return getCell(index);
    }

    /**
     * Gets the header labels associated with this row
     *
     * @return the header labels
     */
    public List<String> getHeaderLabels() {
        return headerLabels;
    }
}