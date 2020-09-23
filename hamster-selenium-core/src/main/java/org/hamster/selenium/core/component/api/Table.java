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

package org.hamster.selenium.core.component.api;

import org.hamster.selenium.core.component.WebComponent;

import java.util.List;

/**
 * a table should contain one or more header rows and a list of body rows.
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface Table extends WebComponent {

    /**
     * Gets the first header row
     *
     * @return the first header row
     */
    TableRow getHeaderRow();

    /**
     * Gets the list of header rows
     *
     * @return the list of header rows
     */
    List<TableRow> getHeaderRows();

    /**
     * Gets and collects the labels of the first line of header.
     *
     * @return the label list
     */
    List<String> getHeaderLabels();

    /**
     * Gets the list of {@link TableRow}s not including the header row.
     *
     * @return the list of {@link TableRow}s without header row.
     */
    List<TableRow> getBodyRows();

    /**
     * Gets the body row by row index.
     *
     * @param rowIndex
     *         the index of the row
     * @return the found table row
     */
    TableRow getBodyRow(int rowIndex);
}
