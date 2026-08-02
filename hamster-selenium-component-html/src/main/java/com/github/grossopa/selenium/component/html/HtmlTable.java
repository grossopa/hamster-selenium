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

package com.github.grossopa.selenium.component.html;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import com.github.grossopa.selenium.core.component.api.Table;
import com.github.grossopa.selenium.core.component.api.TableRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Represents the native HTML table element.
 *
 * <p>This component provides a high-level abstraction for interacting with HTML table elements
 * (&lt;table&gt;). It implements the {@link Table} interface to provide standardized access to
 * table headers, body rows, and individual cells.</p>
 *
 * <p>The component automatically handles different table structures:
 * <ul>
 *   <li>Tables with explicit &lt;thead&gt; and &lt;tbody&gt; sections</li>
 *   <li>Tables with implicit header rows (rows containing &lt;th&gt; elements)</li>
 *   <li>Tables with mixed header and body row structures</li>
 * </ul>
 *
 * <p>Key features:
 * <ul>
 *   <li><strong>Header Access:</strong> Retrieve header rows and column labels with {@link #getHeaderRow()}, {@link #getHeaderRows()}, and {@link #getHeaderLabels()}</li>
 *   <li><strong>Body Access:</strong> Retrieve body rows with {@link #getBodyRow(int)} and {@link #getBodyRows()}</li>
 *   <li><strong>Customizable Locators:</strong> Override default XPath locators for specific table structures</li>
 *   <li><strong>Validation:</strong> Built-in validation to ensure the component wraps a valid table element</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * // Find a table component
 * HtmlTable table = driver.findComponentAs(By.tagName("table"), HtmlComponents.html()::toTable);
 * 
 * // Access header information
 * List<String> headers = table.getHeaderLabels();
 * 
 * // Access body rows
 * List<TableRow> rows = table.getBodyRows();
 * for (TableRow row : rows) {
 *     List<String> cellValues = row.getColumnValues();
 *     // Process cell values
 * }
 * 
 * // Access specific row and cell
 * TableRow firstRow = table.getBodyRow(0);
 * String cellValue = firstRow.getColumnValue(1); // Second column
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.0
 * @see Table
 * @see HtmlTableRow
 * @see HtmlComponents
 */
public class HtmlTable extends DefaultWebComponent implements Table {

    /**
     * Constructs an html table instance with element and driver.
     *
     * @param element the table element
     * @param driver the root driver
     */
    public HtmlTable(WebElement element, ComponentWebDriver driver) {
        super(element, driver);
    }

    @Override
    public boolean validate() {
        return "table".equalsIgnoreCase(element.getTagName());
    }

    @Override
    public TableRow getHeaderRow() {
        return getHeaderRows().get(0);
    }

    @Override
    public List<TableRow> getHeaderRows() {
        List<WebElement> headerContainers = element.findElements(getHeaderRowsLocator());
        if (headerContainers.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> header = headerContainers.get(0).findElements(getHeaderColsLocator()).stream()
                .map(WebElement::getText).collect(toList());
        return headerContainers.stream().map(e -> new HtmlTableRow(e, driver, getHeaderColsLocator(), header))
                .collect(toList());
    }

    @Override
    public List<String> getHeaderLabels() {
        List<WebElement> headerContainers = element.findElements(getHeaderRowsLocator());
        if (headerContainers.isEmpty()) {
            return new ArrayList<>();
        }

        return headerContainers.get(0).findElements(getHeaderColsLocator()).stream().map(WebElement::getText)
                .collect(toList());
    }

    @Override
    public TableRow getBodyRow(int rowIndex) {
        return getBodyRows().get(rowIndex);
    }

    @Override
    public List<TableRow> getBodyRows() {
        return element.findElements(getRowsLocator()).stream()
                .map(e -> new HtmlTableRow(e, driver, getColsLocator(), getHeaderLabels())).collect(toList());
    }

    /**
     * Gets the new locator instance for locating the header rows
     *
     * @return the new locator instance for locating the header rows
     */
    public By getHeaderRowsLocator() {
        return By.xpath(".//tr[./th]");
    }

    /**
     * Gets the new locator instance for locating the header columns within a row
     *
     * @return the new locator instance for locating the header columns within a row
     */
    public By getHeaderColsLocator() {
        return By.xpath("./th");
    }

    /**
     * Gets the new locator instance for locating the body rows
     *
     * @return the new locator instance for locating the body rows
     */
    public By getRowsLocator() {
        return By.xpath(".//tr[./td]");
    }

    /**
     * Gets the new locator instance for locating the body columns within a row
     *
     * @return the new locator instance for locating the body  columns within a row
     */
    public By getColsLocator() {
        return By.xpath("./td");
    }

    @Override
    public String toString() {
        return "HtmlTable{" + "element=" + element + '}';
    }
}
