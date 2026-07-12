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
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link HtmlTable}
 *
 * @author Jack Yin
 * @since 1.12
 */
class HtmlTableTest {

    HtmlTable testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);

    @BeforeEach
    void setUp() {
        testSubject = new HtmlTable(locator, driver);
    }

    @Test
    void getComponentTagName() {
        assertEquals("table", testSubject.getComponentTagName());
    }

    @Test
    void validateTrue() {
        when(locator.evaluate("el => el.tagName")).thenReturn("TABLE");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.evaluate("el => el.tagName")).thenReturn("div");
        assertFalse(testSubject.validate());
    }

    @Test
    void getRows() {
        Locator trLocator = mock(Locator.class);
        Locator row1 = mock(Locator.class);
        Locator row2 = mock(Locator.class);

        when(locator.locator("tr")).thenReturn(trLocator);
        when(trLocator.all()).thenReturn(Arrays.asList(row1, row2));

        List<HtmlTableRow> rows = testSubject.getRows();
        assertEquals(2, rows.size());
        assertInstanceOf(HtmlTableRow.class, rows.get(0));
        assertInstanceOf(HtmlTableRow.class, rows.get(1));
    }

    @Test
    void getRowsEmpty() {
        Locator trLocator = mock(Locator.class);
        when(locator.locator("tr")).thenReturn(trLocator);
        when(trLocator.all()).thenReturn(Collections.emptyList());

        List<HtmlTableRow> rows = testSubject.getRows();
        assertTrue(rows.isEmpty());
    }

    @Test
    void getHeaderLabels() {
        Locator headerRowLocator = mock(Locator.class);
        Locator headerRow = mock(Locator.class);
        Locator thLocator = mock(Locator.class);
        Locator th1 = mock(Locator.class);
        Locator th2 = mock(Locator.class);

        when(locator.locator("tr:has(th)")).thenReturn(headerRowLocator);
        when(headerRowLocator.all()).thenReturn(List.of(headerRow));
        when(headerRow.locator("th")).thenReturn(thLocator);
        when(thLocator.all()).thenReturn(Arrays.asList(th1, th2));
        when(th1.innerText()).thenReturn("Name");
        when(th2.innerText()).thenReturn("Age");

        List<String> labels = testSubject.getHeaderLabels();
        assertEquals(2, labels.size());
        assertEquals("Name", labels.get(0));
        assertEquals("Age", labels.get(1));
    }

    @Test
    void getHeaderLabelsEmpty() {
        Locator headerRowLocator = mock(Locator.class);
        when(locator.locator("tr:has(th)")).thenReturn(headerRowLocator);
        when(headerRowLocator.all()).thenReturn(Collections.emptyList());

        List<String> labels = testSubject.getHeaderLabels();
        assertTrue(labels.isEmpty());
    }

    @Test
    void getHeaderRow() {
        Locator headerRowLocator = mock(Locator.class);
        Locator headerRow = mock(Locator.class);
        Locator thLocator = mock(Locator.class);
        Locator th1 = mock(Locator.class);

        when(locator.locator("tr:has(th)")).thenReturn(headerRowLocator);
        when(headerRowLocator.all()).thenReturn(List.of(headerRow));
        when(headerRow.locator("th")).thenReturn(thLocator);
        when(thLocator.all()).thenReturn(List.of(th1));
        when(th1.innerText()).thenReturn("Header");

        HtmlTableRow headerRowResult = testSubject.getHeaderRow();
        assertNotNull(headerRowResult);
    }

    @Test
    void getHeaderRows() {
        Locator headerRowLocator = mock(Locator.class);
        Locator headerRow1 = mock(Locator.class);
        Locator headerRow2 = mock(Locator.class);
        Locator thLocator = mock(Locator.class);
        Locator th1 = mock(Locator.class);

        when(locator.locator("tr:has(th)")).thenReturn(headerRowLocator);
        when(headerRowLocator.all()).thenReturn(Arrays.asList(headerRow1, headerRow2));
        when(headerRow1.locator("th")).thenReturn(thLocator);
        when(thLocator.all()).thenReturn(List.of(th1));
        when(th1.innerText()).thenReturn("Header");

        List<HtmlTableRow> headerRows = testSubject.getHeaderRows();
        assertEquals(2, headerRows.size());
    }

    @Test
    void getHeader() {
        Locator headerRowLocator = mock(Locator.class);
        Locator headerRow = mock(Locator.class);
        Locator thLocator = mock(Locator.class);
        Locator th1 = mock(Locator.class);

        when(locator.locator("tr:has(th)")).thenReturn(headerRowLocator);
        when(headerRowLocator.all()).thenReturn(List.of(headerRow));
        when(headerRow.locator("th")).thenReturn(thLocator);
        when(thLocator.all()).thenReturn(List.of(th1));
        when(th1.innerText()).thenReturn("Header");

        HtmlTableRow header = testSubject.getHeader();
        assertNotNull(header);
    }

    @Test
    void getBodyRow() {
        Locator headerRowLocator = mock(Locator.class);
        Locator headerRow = mock(Locator.class);
        Locator thLocator = mock(Locator.class);
        Locator th1 = mock(Locator.class);
        
        Locator bodyRowLocator = mock(Locator.class);
        Locator bodyRow1 = mock(Locator.class);
        Locator bodyRow2 = mock(Locator.class);

        when(locator.locator("tr:has(th)")).thenReturn(headerRowLocator);
        when(headerRowLocator.all()).thenReturn(List.of(headerRow));
        when(headerRow.locator("th")).thenReturn(thLocator);
        when(thLocator.all()).thenReturn(List.of(th1));
        when(th1.innerText()).thenReturn("Header");
        
        when(locator.locator("tr:has(td)")).thenReturn(bodyRowLocator);
        when(bodyRowLocator.all()).thenReturn(Arrays.asList(bodyRow1, bodyRow2));

        HtmlTableRow bodyRow = testSubject.getBodyRow(0);
        assertNotNull(bodyRow);
    }

    @Test
    void getBodyRows() {
        Locator headerRowLocator = mock(Locator.class);
        Locator headerRow = mock(Locator.class);
        Locator thLocator = mock(Locator.class);
        Locator th1 = mock(Locator.class);
        
        Locator bodyRowLocator = mock(Locator.class);
        Locator bodyRow1 = mock(Locator.class);
        Locator bodyRow2 = mock(Locator.class);
        Locator bodyRow3 = mock(Locator.class);

        when(locator.locator("tr:has(th)")).thenReturn(headerRowLocator);
        when(headerRowLocator.all()).thenReturn(List.of(headerRow));
        when(headerRow.locator("th")).thenReturn(thLocator);
        when(thLocator.all()).thenReturn(List.of(th1));
        when(th1.innerText()).thenReturn("Header");
        
        when(locator.locator("tr:has(td)")).thenReturn(bodyRowLocator);
        when(bodyRowLocator.all()).thenReturn(Arrays.asList(bodyRow1, bodyRow2, bodyRow3));

        List<HtmlTableRow> bodyRows = testSubject.getBodyRows();
        assertEquals(3, bodyRows.size());
    }

    @Test
    void getDataRows() {
        Locator headerRowLocator = mock(Locator.class);
        Locator headerRow = mock(Locator.class);
        Locator thLocator = mock(Locator.class);
        Locator th1 = mock(Locator.class);
        
        Locator bodyRowLocator = mock(Locator.class);
        Locator bodyRow1 = mock(Locator.class);

        when(locator.locator("tr:has(th)")).thenReturn(headerRowLocator);
        when(headerRowLocator.all()).thenReturn(List.of(headerRow));
        when(headerRow.locator("th")).thenReturn(thLocator);
        when(thLocator.all()).thenReturn(List.of(th1));
        when(th1.innerText()).thenReturn("Header");
        
        when(locator.locator("tr:has(td)")).thenReturn(bodyRowLocator);
        when(bodyRowLocator.all()).thenReturn(List.of(bodyRow1));

        List<HtmlTableRow> dataRows = testSubject.getDataRows();
        assertEquals(1, dataRows.size());
    }

    @Test
    void getDataRowsEmpty() {
        Locator headerRowLocator = mock(Locator.class);
        when(locator.locator("tr:has(th)")).thenReturn(headerRowLocator);
        when(headerRowLocator.all()).thenReturn(Collections.emptyList());
        
        Locator bodyRowLocator = mock(Locator.class);
        when(locator.locator("tr:has(td)")).thenReturn(bodyRowLocator);
        when(bodyRowLocator.all()).thenReturn(Collections.emptyList());

        List<HtmlTableRow> dataRows = testSubject.getDataRows();
        assertTrue(dataRows.isEmpty());
    }
}
