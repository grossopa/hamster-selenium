/*
 * Copyright © 2021 the original author or authors.
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

package com.github.grossopa.selenium.component.html;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.component.api.TableRow;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * The HTML table row element
 *
 * @author Jack Yin
 * @since 1.0
 */
public class HtmlTableRow extends DefaultWebComponent implements TableRow {

    private final By colsLocator;
    private final List<String> headerLabels;

    /**
     * Constructs an instance with element, columns locator and the found header labels
     *
     * @param element the current row element
     * @param driver the root driver
     * @param colsLocator the columns locator
     * @param headerLabels the found header labels
     */
    public HtmlTableRow(WebElement element, ComponentWebDriver driver, By colsLocator, List<String> headerLabels) {
        super(element, driver);
        this.colsLocator = requireNonNull(colsLocator);
        this.headerLabels = requireNonNull(headerLabels);
    }

    @Override
    public boolean validate() {
        return "tr".equalsIgnoreCase(element.getTagName());
    }

    @Override
    public List<WebComponent> getCells() {
        return element.findElements(colsLocator).stream().map(element -> new DefaultWebComponent(element, driver))
                .collect(toList());
    }

    @Override
    public WebComponent getCell(String headerLabel) {
        int index = headerLabels.indexOf(headerLabel);
        if (index == -1) {
            throw new NoSuchElementException("No such column with header label: " + headerLabel);
        }
        return getCells().get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HtmlTableRow)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        HtmlTableRow that = (HtmlTableRow) o;
        return colsLocator.equals(that.colsLocator) && headerLabels.equals(that.headerLabels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), colsLocator, headerLabels);
    }

    @Override
    public String toString() {
        return "HtmlTableRow{" + "colsLocator=" + colsLocator + ", headerLabels=" + headerLabels + ", element="
                + element + '}';
    }
}
