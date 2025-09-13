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
     * Gets all the rows as a list
     *
     * @return all the rows as a list
     */
    public List<HtmlTableRow> getRows() {
        return this.locator.locator("tr").all().stream().map(l -> new HtmlTableRow(l, driver)).collect(toList());
    }

    /**
     * Gets the header row
     *
     * @return the header row
     */
    public HtmlTableRow getHeader() {
        return new HtmlTableRow(this.locator.locator("tbody > tr, tr, thead > tr").first(), driver);
    }

    /**
     * Gets the data rows
     *
     * @return the data rows
     */
    public List<HtmlTableRow> getDataRows() {
        return this.locator.locator("tbody > tr:has(td)").all().stream().map(l -> new HtmlTableRow(l, driver)).collect(
                toList());
    }
}