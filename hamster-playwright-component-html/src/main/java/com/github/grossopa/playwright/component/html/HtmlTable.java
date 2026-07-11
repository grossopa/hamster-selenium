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
import com.microsoft.playwright.Locator;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The HTML table component.
 *
 * @author Jack Yin
 * @since 1.12
 */
public class HtmlTable extends DefaultWebComponent {

    /**
     * Constructs with the given locator and driver
     *
     * @param locator the locator instance
     * @param driver  the driver instance
     */
    public HtmlTable(Locator locator, ComponentDriver driver) {
        super(locator, driver);
    }

    @Override
    public String getComponentTagName() {
        return "table";
    }

    /**
     * Validates that the current locator points to a {@code <table>} element.
     *
     * @return true if the element tag name is "table" (case-insensitive)
     */
    public boolean validate() {
        return "table".equalsIgnoreCase(locator.evaluate("el => el.tagName").toString());
    }

    /**
     * Gets all the rows as a list
     *
     * @return all the rows as a list
     */
    public List<HtmlTableRow> getRows() {
        return this.locator.locator("tr").all().stream().map(l -> new HtmlTableRow(l, driver)).collect(toList());
    }

    /**
     * Gets the header row (first row containing th elements)
     *
     * @return the header row
     */
    public HtmlTableRow getHeaderRow() {
        return getHeaderRows().get(0);
    }

    /**
     * Gets all header rows (rows containing th elements)
     *
     * @return list of header rows
     */
    public List<HtmlTableRow> getHeaderRows() {
        return this.locator.locator("tr:has(th)").all().stream()
                .map(l -> new HtmlTableRow(l, driver, getHeaderLabels())).collect(toList());
    }

    /**
     * Gets the header labels from the first header row
     *
     * @return list of header label texts, or empty list if no header rows found
     */
    public List<String> getHeaderLabels() {
        List<Locator> headerRows = this.locator.locator("tr:has(th)").all();
        if (headerRows.isEmpty()) {
            return new ArrayList<>();
        }
        return headerRows.get(0).locator("th").all().stream()
                .map(Locator::innerText).collect(toList());
    }

    /**
     * Gets the header row (alias for {@link #getHeaderRow()})
     *
     * @return the header row
     */
    public HtmlTableRow getHeader() {
        return getHeaderRow();
    }

    /**
     * Gets the body row at the specified index
     *
     * @param rowIndex the row index
     * @return the body row at the given index
     */
    public HtmlTableRow getBodyRow(int rowIndex) {
        return getBodyRows().get(rowIndex);
    }

    /**
     * Gets the data rows (rows containing td elements)
     *
     * @return the data rows
     */
    public List<HtmlTableRow> getBodyRows() {
        List<String> headerLabels = getHeaderLabels();
        return this.locator.locator("tr:has(td)").all().stream()
                .map(l -> new HtmlTableRow(l, driver, headerLabels)).collect(toList());
    }

    /**
     * Gets the data rows (alias for {@link #getBodyRows()})
     *
     * @return the data rows
     */
    public List<HtmlTableRow> getDataRows() {
        return getBodyRows();
    }
}