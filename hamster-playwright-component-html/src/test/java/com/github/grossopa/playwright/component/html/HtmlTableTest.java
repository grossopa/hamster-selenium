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
    void getHeader() {
        Locator headerLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);

        when(locator.locator("tbody > tr, tr, thead > tr")).thenReturn(headerLocator);
        when(headerLocator.first()).thenReturn(firstLocator);

        HtmlTableRow header = testSubject.getHeader();
        assertNotNull(header);
        assertInstanceOf(HtmlTableRow.class, header);
    }

    @Test
    void getDataRows() {
        Locator tbodyLocator = mock(Locator.class);
        Locator dataRow1 = mock(Locator.class);
        Locator dataRow2 = mock(Locator.class);
        Locator dataRow3 = mock(Locator.class);

        when(locator.locator("tbody > tr:has(td)")).thenReturn(tbodyLocator);
        when(tbodyLocator.all()).thenReturn(Arrays.asList(dataRow1, dataRow2, dataRow3));

        List<HtmlTableRow> dataRows = testSubject.getDataRows();
        assertEquals(3, dataRows.size());
        assertInstanceOf(HtmlTableRow.class, dataRows.get(0));
        assertInstanceOf(HtmlTableRow.class, dataRows.get(1));
        assertInstanceOf(HtmlTableRow.class, dataRows.get(2));
    }

    @Test
    void getDataRowsEmpty() {
        Locator tbodyLocator = mock(Locator.class);
        when(locator.locator("tbody > tr:has(td)")).thenReturn(tbodyLocator);
        when(tbodyLocator.all()).thenReturn(Collections.emptyList());

        List<HtmlTableRow> dataRows = testSubject.getDataRows();
        assertTrue(dataRows.isEmpty());
    }
}
