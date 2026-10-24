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
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.api.TableRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiTable}
 *
 * @author Jack Yin
 * @since 1.18
 */
class MuiTableTest {

    MuiTable testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    // header elements
    WebElement headerRow = mock(WebElement.class);
    WebElement headerCol1 = mock(WebElement.class);
    WebElement headerCol2 = mock(WebElement.class);

    // body elements
    WebElement bodyRow1 = mock(WebElement.class);
    WebElement bodyRow1Col1 = mock(WebElement.class);
    WebElement bodyRow1Col2 = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        testSubject = new MuiTable(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Table", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }

    @Test
    void getHeaderRowsLocator() {
        assertEquals(By.xpath(".//tr[./th]"), testSubject.getHeaderRowsLocator());
    }

    @Test
    void getHeaderColsLocator() {
        assertEquals(By.xpath("./th"), testSubject.getHeaderColsLocator());
    }

    @Test
    void getRowsLocator() {
        assertEquals(By.xpath(".//tr[./td]"), testSubject.getRowsLocator());
    }

    @Test
    void getColsLocator() {
        assertEquals(By.xpath("./td"), testSubject.getColsLocator());
    }

    @Test
    void getHeaderRowsEmpty() {
        when(element.findElements(By.xpath(".//tr[./th]"))).thenReturn(emptyList());
        assertTrue(testSubject.getHeaderRows().isEmpty());
    }

    @Test
    void getHeaderRows() {
        when(element.findElements(By.xpath(".//tr[./th]"))).thenReturn(List.of(headerRow));
        when(headerRow.findElements(By.xpath("./th"))).thenReturn(List.of(headerCol1, headerCol2));
        when(headerCol1.getText()).thenReturn("Name");
        when(headerCol2.getText()).thenReturn("Age");

        List<TableRow> result = testSubject.getHeaderRows();
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof HtmlTableRow);
    }

    @Test
    void getHeaderLabels() {
        when(element.findElements(By.xpath(".//tr[./th]"))).thenReturn(List.of(headerRow));
        when(headerRow.findElements(By.xpath("./th"))).thenReturn(List.of(headerCol1, headerCol2));
        when(headerCol1.getText()).thenReturn("Name");
        when(headerCol2.getText()).thenReturn("Age");

        assertEquals(List.of("Name", "Age"), testSubject.getHeaderLabels());
    }

    @Test
    void getHeaderLabelsEmpty() {
        when(element.findElements(By.xpath(".//tr[./th]"))).thenReturn(emptyList());
        assertTrue(testSubject.getHeaderLabels().isEmpty());
    }

    @Test
    void getHeaderRow() {
        when(element.findElements(By.xpath(".//tr[./th]"))).thenReturn(List.of(headerRow));
        when(headerRow.findElements(By.xpath("./th"))).thenReturn(List.of(headerCol1, headerCol2));
        when(headerCol1.getText()).thenReturn("Name");
        when(headerCol2.getText()).thenReturn("Age");

        TableRow result = testSubject.getHeaderRow();
        assertEquals("Name", result.getCell("Name").getText());
    }

    @Test
    void getBodyRows() {
        // mock header labels
        when(element.findElements(By.xpath(".//tr[./th]"))).thenReturn(List.of(headerRow));
        when(headerRow.findElements(By.xpath("./th"))).thenReturn(List.of(headerCol1, headerCol2));
        when(headerCol1.getText()).thenReturn("Name");
        when(headerCol2.getText()).thenReturn("Age");

        // mock body rows
        when(element.findElements(By.xpath(".//tr[./td]"))).thenReturn(List.of(bodyRow1));
        when(bodyRow1.findElements(By.xpath("./td"))).thenReturn(List.of(bodyRow1Col1, bodyRow1Col2));

        List<TableRow> result = testSubject.getBodyRows();
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof HtmlTableRow);
    }

    @Test
    void getBodyRow() {
        // mock header labels
        when(element.findElements(By.xpath(".//tr[./th]"))).thenReturn(List.of(headerRow));
        when(headerRow.findElements(By.xpath("./th"))).thenReturn(List.of(headerCol1, headerCol2));
        when(headerCol1.getText()).thenReturn("Name");
        when(headerCol2.getText()).thenReturn("Age");

        // mock body rows
        when(element.findElements(By.xpath(".//tr[./td]"))).thenReturn(List.of(bodyRow1));
        when(bodyRow1.findElements(By.xpath("./td"))).thenReturn(List.of(bodyRow1Col1, bodyRow1Col2));

        TableRow result = testSubject.getBodyRow(0);
        assertTrue(result instanceof HtmlTableRow);
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiTable{element=element-toString}", testSubject.toString());
    }
}
