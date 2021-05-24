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
import com.github.grossopa.selenium.core.component.api.TableRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link HtmlTable}
 *
 * @author Jack Yin
 * @since 1.0
 */
class HtmlTableTest {

    HtmlTable testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);

    WebElement headerRow1 = mock(WebElement.class);
    WebElement headerRow2 = mock(WebElement.class);
    WebElement header1 = mock(WebElement.class);
    WebElement header2 = mock(WebElement.class);
    WebElement header3 = mock(WebElement.class);

    private WebElement createBodyRow(Integer index) {
        WebElement container = mock(WebElement.class);
        WebElement cellElement1 = mock(WebElement.class);
        WebElement cellElement2 = mock(WebElement.class);
        WebElement cellElement3 = mock(WebElement.class);
        when(cellElement1.getText()).thenReturn(index + "," + "cell 1 some text 123");
        when(cellElement2.getText()).thenReturn(index + "," + "cell 2 some text 223");
        when(cellElement3.getText()).thenReturn(index + "," + "cell 3");
        when(container.findElements(testSubject.getColsLocator()))
                .thenReturn(asList(cellElement1, cellElement2, cellElement3));
        return container;
    }

    @BeforeEach
    void setUp() {
        when(element.getTagName()).thenReturn("table");
        testSubject = new HtmlTable(element, driver);

        // header row
        when(header1.getText()).thenReturn("header 1");
        when(header2.getText()).thenReturn("header 2");
        when(header3.getText()).thenReturn("header 3");

        when(headerRow1.findElements(testSubject.getHeaderColsLocator())).thenReturn(asList(header1, header2, header3));
        when(element.findElements(eq(testSubject.getHeaderRowsLocator()))).thenReturn(asList(headerRow1, headerRow2));

        List<WebElement> bodyContainers = asList(createBodyRow(0), createBodyRow(1), createBodyRow(2), createBodyRow(3),
                createBodyRow(4));
        // body row
        when(element.findElements(eq(testSubject.getRowsLocator()))).thenReturn(bodyContainers);
    }

    @Test
    void constructorNotTable() {
        WebElement element = mock(WebElement.class);
        when(element.getTagName()).thenReturn("div");
        assertFalse(new HtmlTable(element, driver).validate());
    }

    @Test
    void getHeaderRow() {
        TableRow row = testSubject.getHeaderRow();
        assertNotNull(row);
        assertEquals(3, row.getCells().size());
        assertEquals(header1, row.getCells().get(0).getWrappedElement());
        assertEquals(header2, row.getCells().get(1).getWrappedElement());
        assertEquals(header3, row.getCells().get(2).getWrappedElement());
    }

    @Test
    void getHeaderRows() {
        List<TableRow> rows = testSubject.getHeaderRows();
        assertNotNull(rows);
        assertEquals(2, rows.size());
        assertEquals(headerRow1, rows.get(0).getWrappedElement());
        assertEquals(headerRow2, rows.get(1).getWrappedElement());
    }

    @Test
    void getHeaderRowsEmpty() {
        when(element.findElements(eq(testSubject.getHeaderRowsLocator()))).thenReturn(emptyList());
        List<TableRow> rows = testSubject.getHeaderRows();
        assertNotNull(rows);
        assertEquals(0, rows.size());
    }

    @Test
    void getHeaderLabels() {
        List<String> labels = testSubject.getHeaderLabels();
        assertEquals(3, labels.size());
        assertTrue(labels.contains("header 1"));
        assertTrue(labels.contains("header 2"));
        assertTrue(labels.contains("header 3"));
    }

    @Test
    void getHeaderLabelsEmpty() {
        when(element.findElements(eq(testSubject.getHeaderRowsLocator()))).thenReturn(emptyList());
        List<String> labels = testSubject.getHeaderLabels();
        assertNotNull(labels);
        assertEquals(0, labels.size());
    }

    @Test
    void getBodyRow() {
        List<TableRow> bodyRows = testSubject.getBodyRows();
        assertEquals(5, bodyRows.size());
    }

    @Test
    void getBodyRows() {
        TableRow row = testSubject.getBodyRow(2);
        assertEquals("2,cell 1 some text 123", row.getCell("header 1").getText());
    }

    @Test
    void getBodyRowsNoSuchHeader() {
        TableRow row = testSubject.getBodyRow(2);
        assertThrows(NoSuchElementException.class, () -> row.getCell("header 888"));
    }

    @Test
    void getHeaderRowsLocator() {
        assertEquals("By.xpath: .//tr[./th]", testSubject.getHeaderRowsLocator().toString());
    }

    @Test
    void getHeaderColsLocator() {
        assertEquals("By.xpath: ./th", testSubject.getHeaderColsLocator().toString());
    }

    @Test
    void getRowsLocator() {
        assertEquals("By.xpath: .//tr[./td]", testSubject.getRowsLocator().toString());
    }

    @Test
    void getColsLocator() {
        assertEquals("By.xpath: ./td", testSubject.getColsLocator().toString());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("HtmlTable{element=element-toString}", testSubject.toString());
    }
}
