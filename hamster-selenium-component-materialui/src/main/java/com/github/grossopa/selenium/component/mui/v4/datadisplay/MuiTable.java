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
package com.github.grossopa.selenium.component.mui.v4.datadisplay;

import com.github.grossopa.selenium.component.html.HtmlTableRow;
import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.api.Table;
import com.github.grossopa.selenium.core.component.api.TableRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;

/**
 * Represents a Material UI Table component.
 *
 * <p>MUI Table is built on top of the native HTML {@code <table>} element with additional MUI styling. The root
 * element has the CSS class {@code MuiTable-root}. The internal structure uses standard HTML table elements
 * ({@code <tr>}, {@code <th>}, {@code <td>}) with MUI-specific CSS classes such as {@code MuiTableRow-root},
 * {@code MuiTableCell-root}, and {@code MuiTableHead-root}.</p>
 *
 * <p>This component implements the {@link Table} interface and reuses {@link HtmlTableRow} for row representation,
 * since the underlying DOM structure is identical to native HTML tables. The XPath-based row and cell locators are
 * the same as those used by {@code HtmlTable}. The key difference is the MUI-specific validation via
 * {@link #validate()} which checks for the {@code MuiTable-root} CSS class.</p>
 *
 * <p>Key features:
 * <ul>
 *   <li>Implements {@link Table} interface for standardized table access</li>
 *   <li>Reuses {@link HtmlTableRow} from the HTML component module for row operations</li>
 *   <li>Header access via {@link #getHeaderRow()}, {@link #getHeaderRows()}, and {@link #getHeaderLabels()}</li>
 *   <li>Body access via {@link #getBodyRow(int)} and {@link #getBodyRows()}</li>
 *   <li>MUI-specific validation through {@code MuiTable-root} CSS class</li>
 *   <li>Support for MUI versions V4, V5, and V6</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.18
 * @see Table
 * @see HtmlTableRow
 * @see <a href="https://mui.com/material-ui/react-table/">https://mui.com/material-ui/react-table/</a>
 */
public class MuiTable extends AbstractMuiComponent implements Table {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Table";

    /**
     * Constructs a MuiTable instance with the delegated element and root driver.
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiTable(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
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

        List<String> header = getHeaderLabels();
        return headerContainers.stream()
                .<TableRow>map(e -> new HtmlTableRow(e, driver, getHeaderColsLocator(), header)).toList();
    }

    @Override
    public List<String> getHeaderLabels() {
        List<WebElement> headerContainers = element.findElements(getHeaderRowsLocator());
        if (headerContainers.isEmpty()) {
            return new ArrayList<>();
        }

        return headerContainers.get(0).findElements(getHeaderColsLocator()).stream().map(WebElement::getText)
                .toList();
    }

    @Override
    public TableRow getBodyRow(int rowIndex) {
        return getBodyRows().get(rowIndex);
    }

    @Override
    public List<TableRow> getBodyRows() {
        List<String> headerLabels = getHeaderLabels();
        return element.findElements(getRowsLocator()).stream()
                .<TableRow>map(e -> new HtmlTableRow(e, driver, getColsLocator(), headerLabels)).toList();
    }

    /**
     * Gets the locator for locating the header rows.
     *
     * @return the locator for locating the header rows
     */
    public By getHeaderRowsLocator() {
        return By.xpath(".//tr[./th]");
    }

    /**
     * Gets the locator for locating the header columns within a row.
     *
     * @return the locator for locating the header columns within a row
     */
    public By getHeaderColsLocator() {
        return By.xpath("./th");
    }

    /**
     * Gets the locator for locating the body rows.
     *
     * @return the locator for locating the body rows
     */
    public By getRowsLocator() {
        return By.xpath(".//tr[./td]");
    }

    /**
     * Gets the locator for locating the body columns within a row.
     *
     * @return the locator for locating the body columns within a row
     */
    public By getColsLocator() {
        return By.xpath("./td");
    }
}
