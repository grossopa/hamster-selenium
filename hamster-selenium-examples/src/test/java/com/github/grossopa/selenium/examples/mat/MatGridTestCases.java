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

package com.github.grossopa.selenium.examples.mat;

import com.github.grossopa.hamster.selenium.component.mat.main.MatGridList;
import com.github.grossopa.hamster.selenium.component.mat.main.MatGridTile;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the actual features of {@link MatGridList}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatGridTestCases extends AbstractBrowserSupport {

    public void testGrid() {
        driver.navigate().to("https://material.angular.io/components/grid-list/examples");

        WebComponent container = driver.findComponent(By.tagName("grid-list-dynamic-example"));
        MatGridList gridList = container.findComponent(By.className("mat-grid-list")).as(mat()).toGridList();
        assertTrue(gridList.validate());

        List<MatGridTile> tiles = gridList.getGridTiles();
        assertEquals(4, tiles.size());
        assertEquals(4, gridList.getCols());

        tiles.forEach(tile -> assertTrue(tile.validate()));

        assertEquals("One", tiles.get(0).getText());
        assertEquals("Two", tiles.get(1).getText());
        assertEquals("Three", tiles.get(2).getText());
        assertEquals("Four", tiles.get(3).getText());

        assertEquals(1, tiles.get(0).getRowSpan());
        assertEquals(3, tiles.get(0).getColSpan());
        assertEquals(2, tiles.get(1).getRowSpan());
        assertEquals(1, tiles.get(1).getColSpan());
        assertEquals(1, tiles.get(2).getRowSpan());
        assertEquals(1, tiles.get(2).getColSpan());
        assertEquals(1, tiles.get(3).getRowSpan());
        assertEquals(2, tiles.get(3).getColSpan());
    }

    public static void main(String[] args) {
        MatGridTestCases test = new MatGridTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testGrid();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            test.stopDriver();
        }
    }
}
