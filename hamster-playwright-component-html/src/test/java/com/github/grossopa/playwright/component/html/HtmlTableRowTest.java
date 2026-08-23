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
package com.github.grossopa.playwright.component.html;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link HtmlTableRow}
 *
 * @author Jack Yin
 * @since 1.12
 */
class HtmlTableRowTest {

    HtmlTableRow testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    List<String> headerLabels = Arrays.asList("Name", "Age", "City");

    @BeforeEach
    void setUp() {
        testSubject = new HtmlTableRow(locator, driver, headerLabels);
    }

    @Test
    void getComponentTagName() {
        assertEquals("tr", testSubject.getComponentTagName());
    }

    @Test
    void constructorWithoutHeaderLabels() {
        HtmlTableRow row = new HtmlTableRow(locator, driver);
        assertNotNull(row.getHeaderLabels());
        assertTrue(row.getHeaderLabels().isEmpty());
    }

    @Test
    void constructorWithNullHeaderLabels() {
        HtmlTableRow row = new HtmlTableRow(locator, driver, null);
        assertNotNull(row.getHeaderLabels());
        assertTrue(row.getHeaderLabels().isEmpty());
    }

    @Test
    void getCells() {
        Locator cellLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);
        Locator cell2 = mock(Locator.class);
        Locator cell3 = mock(Locator.class);

        when(locator.locator("td, th")).thenReturn(cellLocator);
        when(cellLocator.all()).thenReturn(Arrays.asList(cell1, cell2, cell3));

        List<WebComponent> cells = testSubject.getCells();
        assertEquals(3, cells.size());
    }

    @Test
    void getCellsEmpty() {
        Locator cellLocator = mock(Locator.class);
        when(locator.locator("td, th")).thenReturn(cellLocator);
        when(cellLocator.all()).thenReturn(Collections.emptyList());

        List<WebComponent> cells = testSubject.getCells();
        assertTrue(cells.isEmpty());
    }

    @Test
    void getCellByIndex() {
        Locator cellLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);
        Locator cell2 = mock(Locator.class);

        when(locator.locator("td, th")).thenReturn(cellLocator);
        when(cellLocator.all()).thenReturn(Arrays.asList(cell1, cell2));

        WebComponent cell = testSubject.getCell(0);
        assertNotNull(cell);

        WebComponent cell2Result = testSubject.getCell(1);
        assertNotNull(cell2Result);
    }

    @Test
    void getCellByHeaderLabel() {
        Locator cellLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);
        Locator cell2 = mock(Locator.class);
        Locator cell3 = mock(Locator.class);

        when(locator.locator("td, th")).thenReturn(cellLocator);
        when(cellLocator.all()).thenReturn(Arrays.asList(cell1, cell2, cell3));

        WebComponent cell = testSubject.getCell("Name");
        assertNotNull(cell);

        WebComponent ageCell = testSubject.getCell("Age");
        assertNotNull(ageCell);

        WebComponent cityCell = testSubject.getCell("City");
        assertNotNull(cityCell);
    }

    @Test
    void getCellByHeaderLabelNotFound() {
        assertThrows(NoSuchElementException.class, () -> testSubject.getCell("InvalidHeader"));
    }

    @Test
    void getCellIndexOutOfBounds() {
        Locator cellLocator = mock(Locator.class);
        Locator cell1 = mock(Locator.class);

        when(locator.locator("td, th")).thenReturn(cellLocator);
        when(cellLocator.all()).thenReturn(List.of(cell1));

        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.getCell(5));
    }

    @Test
    void getHeaderLabels() {
        List<String> labels = testSubject.getHeaderLabels();
        assertEquals(3, labels.size());
        assertEquals("Name", labels.get(0));
        assertEquals("Age", labels.get(1));
        assertEquals("City", labels.get(2));
    }
}
