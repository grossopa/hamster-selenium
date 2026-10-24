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
package com.github.grossopa.selenium.examples.mui.v5.datadisplay;

import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiTable;
import com.github.grossopa.selenium.core.component.api.TableRow;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiTable}.
 *
 * @author Jack Yin
 * @since 1.18
 * @see <a href="https://mui.com/material-ui/react-table/">https://mui.com/material-ui/react-table/</a>
 */
public class MuiTableTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic table.
     *
     * @see <a href="https://mui.com/material-ui/react-table/#basic-table">
     * https://mui.com/material-ui/react-table/#basic-table</a>
     */
    public void testBasicTable() {
        MuiTable table = driver.findComponent(By.id("BasicTable.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiTable-root")).as(muiV5()).toTable();
        assertTrue(table.validate());

        // verify header labels
        List<String> headers = table.getHeaderLabels();
        assertEquals(5, headers.size());
        assertEquals("Dessert (100g serving)", headers.get(0));
        assertEquals("Calories", headers.get(1));

        // verify body rows
        List<TableRow> bodyRows = table.getBodyRows();
        assertEquals(5, bodyRows.size());

        // verify first row data - note: first cell is <th scope="row">, not <td>
        // so we verify the Calories (first <td>) instead
        List<String> firstRowCells = bodyRows.get(0).getCells().stream().map(c -> c.getText()).toList();
        assertEquals(4, firstRowCells.size()); // 4 <td> cells (excluding the <th> row header)
        assertEquals("159", firstRowCells.get(0)); // Calories
    }

    /**
     * Tests the dense table.
     *
     * @see <a href="https://mui.com/material-ui/react-table/#dense-table">
     * https://mui.com/material-ui/react-table/#dense-table</a>
     */
    public void testDenseTable() {
        MuiTable table = driver.findComponent(By.id("DenseTable.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiTable-root")).as(muiV5()).toTable();
        assertTrue(table.validate());

        List<String> headers = table.getHeaderLabels();
        assertEquals(5, headers.size());

        List<TableRow> bodyRows = table.getBodyRows();
        assertEquals(5, bodyRows.size());
    }

    /**
     * The main function for running the test cases.
     *
     * @param args not necessary
     */
    public static void main(String[] args) {
        MuiTableTestCases test = new MuiTableTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-table/");

        test.testBasicTable();
        test.testDenseTable();
    }
}
