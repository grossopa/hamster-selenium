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

package com.github.grossopa.selenium.examples.mui.v5.datadisplay;

import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiDivider;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.By.className;

/**
 * Test cases for {@link MuiDivider}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiDividerTestCases extends AbstractBrowserSupport {

    /**
     * Tests the list dividers.
     *
     * @see <a href="https://mui.com/components/dividers/#list-dividers">
     * https://mui.com/components/dividers/#list-dividers</a>
     */
    public void testListDividers() {
        List<MuiDivider> dividerList = driver.findComponent(By.id("ListDividers.js")).findComponent(By2.parent())
                .findComponentsAs(className("MuiDivider-root"), c -> c.as(muiV5()).toDivider());
        assertEquals(3, dividerList.size());

        dividerList.forEach(divider -> {
            assertTrue(divider.validate());
            assertFalse(divider.isVertical());
        });
    }

    /**
     * Tests the dividers with text, introduced in V5.
     *
     * @see <a href="https://mui.com/components/dividers/#dividers-with-text">
     * https://mui.com/components/dividers/#dividers-with-text</a>
     */
    public void testDividersWithText() {
        List<MuiDivider> dividerList = driver.findComponent(By.id("DividerText.js")).findComponent(By2.parent())
                .findComponentsAs(className("MuiDivider-root"), c -> c.as(muiV5()).toDivider());
        assertEquals(4, dividerList.size());

        dividerList.forEach(divider -> {
            assertTrue(divider.validate());
            assertFalse(divider.isVertical());
        });

        assertEquals("CENTER", dividerList.get(0).getText());
        assertEquals("LEFT", dividerList.get(1).getText());
        assertEquals("RIGHT", dividerList.get(2).getText());
        assertEquals("Chip",
                dividerList.get(3).findComponent(className("MuiChip-root")).as(muiV5()).toChip().getLabel().getText());
    }

    /**
     * Tests the vertical divider.
     *
     * @see <a href="https://mui.com/components/dividers/#vertical-divider">
     * https://mui.com/components/dividers/#vertical-divider</a>
     */
    public void testVerticalDivider() {
        List<MuiDivider> dividerList = driver.findComponent(By.id("VerticalDividers.js")).findComponent(By2.parent())
                .findComponentsAs(className("MuiDivider-root"), c -> c.as(muiV5()).toDivider());
        assertEquals(1, dividerList.size());
        MuiDivider divider = dividerList.get(0);

        assertTrue(divider.validate());
        assertTrue(divider.isVertical());
    }

    public static void main(String[] args) {
        MuiDividerTestCases test = new MuiDividerTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/dividers/");

        test.testListDividers();
        test.testDividersWithText();
        test.testVerticalDivider();
    }
}
