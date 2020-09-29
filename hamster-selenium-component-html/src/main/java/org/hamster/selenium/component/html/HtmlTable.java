/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.component.html;

import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.DefaultWebComponent;
import org.hamster.selenium.core.component.api.Table;
import org.hamster.selenium.core.component.api.TableRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Jack Yin
 * @since 1.0
 */
public class HtmlTable extends DefaultWebComponent implements Table {

    public HtmlTable(WebElement element, ComponentWebDriver driver) {
        super(element, driver);
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

    public By getHeaderRowsLocator() {
        return By.xpath(".//tr[./th]");
    }

    public By getHeaderColsLocator() {
        return By.xpath("./th");
    }

    public By getRowsLocator() {
        return By.xpath(".//tr[./td]");
    }

    public By getColsLocator() {
        return By.xpath("./td");
    }
}
