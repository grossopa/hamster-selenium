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

package com.github.grossopa.selenium.examples.mui.v4;

import com.github.grossopa.selenium.component.mui.v4.core.MuiGrid;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for Core components.
 *
 * @author Chenyu Wang
 * @since 1.6
 */
public class MuiCoreTestCases extends AbstractBrowserSupport {

    public void testGrid() {
        driver.navigate().to("https://v4.mui.com/components/grid/");
        WebComponent divForLocate = driver.findComponents(By.id("SpacingGrid.js")).get(0).findComponent(By2.parent());
        WebComponent testGridParentDiv = divForLocate.findComponents(By.tagName("div")).get(0)
                .findComponent(By.tagName("div"));
        MuiGrid testGridParent = testGridParentDiv.findComponents(By.className("MuiGrid-root")).get(0).as(mui())
                .toGrid();
        assertTrue(testGridParent.isItem());
        MuiGrid testGrid = testGridParent.findComponent(By.className("MuiGrid-root")).as(mui()).toGrid();
        assertTrue(testGrid.isContainer());
        List<MuiGrid> testedItemGrids = testGrid.findComponents(By.className("MuiGrid-root")).stream()
                .map(c -> c.as(mui()).toGrid()).collect(toList());
        List<MuiGrid> testItemGrids = testedItemGrids.stream().filter(MuiGrid::isItem).collect(toList());
        assertEquals(3, testItemGrids.toArray().length);
        testItemGrids.forEach(testItemGrid -> assertEquals(8, testItemGrid.gridItemSpacingValue(2)));
    }

    public static void main(String[] args) {
        MuiCoreTestCases test = new MuiCoreTestCases();
        test.setUpDriver(EDGE);
        test.testGrid();
    }
}
