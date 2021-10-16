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

package com.github.grossopa.selenium.examples.mui.v5.navigation;

import com.github.grossopa.selenium.component.mui.v4.exception.PaginationNotFoundException;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.v4.lab.MuiPagination;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link MuiPagination}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiPaginationTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic pagination.
     *
     * <a href="https://mui.com/components/pagination/#basic-pagination">
     * https://mui.com/components/pagination/#basic-pagination</a>
     */
    public void testBasicPagination() {
        List<MuiPagination> paginationList = driver.findComponent(By.id("BasicPagination.js"))
                .findComponent(By2.parent())
                .findComponentsAs(By.className("MuiPagination-root"), c -> c.as(muiV5()).toPagination());
        assertEquals(4, paginationList.size());
        paginationList.forEach(pagination -> {
            assertTrue(pagination.validate());
            assertEquals(1, pagination.getCurrentPageIndex());
            List<MuiButton> buttons = pagination.pageButtons();
            assertEquals("1", buttons.get(0).getText());
            assertEquals("10", buttons.get(buttons.size() - 1).getText());
        });

        assertTrue(paginationList.get(0).isEnabled());
        assertTrue(paginationList.get(1).isEnabled());
        assertTrue(paginationList.get(2).isEnabled());
        assertFalse(paginationList.get(3).isEnabled());

        MuiPagination pagination = paginationList.get(0);
        pagination.setPageIndex(2);
        assertEquals(2, pagination.getCurrentPageIndex());

        pagination.setPageIndex(7);
        assertEquals(7, pagination.getCurrentPageIndex());

        pagination.setPageIndex(1);
        pagination.nextButton().click();
        assertEquals(2, pagination.getCurrentPageIndex());

        pagination.nextButton().click();
        assertEquals(3, pagination.getCurrentPageIndex());

        pagination.previousButton().click();
        assertEquals(2, pagination.getCurrentPageIndex());

        pagination.previousButton().click();
        assertEquals(1, pagination.getCurrentPageIndex());

        assertThrows(PaginationNotFoundException.class, () -> pagination.setPageIndex(15));
    }

    /**
     * Tests the buttons of the pagination.
     *
     * <a href="https://mui.com/components/pagination/#buttons">
     * https://mui.com/components/pagination/#buttons</a>
     */
    public void testButtons() {
        List<MuiPagination> paginationList = driver.findComponent(By.id("PaginationButtons.js"))
                .findComponent(By2.parent())
                .findComponentsAs(By.className("MuiPagination-root"), c -> c.as(muiV5()).toPagination());
        assertEquals(2, paginationList.size());
        paginationList.forEach(pagination -> {
            assertTrue(pagination.validate());
            assertEquals(1, pagination.getCurrentPageIndex());
            assertEquals(6, pagination.pageButtons().size());
            List<MuiButton> buttons = pagination.pageButtons();
            assertEquals("1", buttons.get(0).getText());
            assertEquals("10", buttons.get(buttons.size() - 1).getText());
        });

        MuiPagination pagination1 = paginationList.get(0);

        assertFalse(pagination1.firstButton().isEnabled());
        assertFalse(pagination1.previousButton().isEnabled());
        assertTrue(pagination1.nextButton().isEnabled());
        assertTrue(pagination1.lastButton().isEnabled());

        pagination1.nextButton().click();
        assertTrue(pagination1.firstButton().isEnabled());
        assertTrue(pagination1.previousButton().isEnabled());
        assertTrue(pagination1.nextButton().isEnabled());
        assertTrue(pagination1.lastButton().isEnabled());

        pagination1.lastButton().click();
        assertTrue(pagination1.firstButton().isEnabled());
        assertTrue(pagination1.previousButton().isEnabled());
        assertFalse(pagination1.nextButton().isEnabled());
        assertFalse(pagination1.lastButton().isEnabled());

    }

    public static void main(String[] args) {
        MuiPaginationTestCases test = new MuiPaginationTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/pagination/");

        test.testBasicPagination();
        test.testButtons();
    }
}
