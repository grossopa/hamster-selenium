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

import com.github.grossopa.hamster.selenium.component.mat.main.MatList;
import com.github.grossopa.hamster.selenium.component.mat.main.MatSelectionList;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatListOption;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the actual features of {@link MatList} and {@link MatSelectionList}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatListTestCases extends AbstractBrowserSupport {

    public void testList() {
        WebComponent container = driver.findComponent(By.tagName("list-overview-example"));
        MatList list = container.findComponent(By.className("mat-list")).as(mat()).toList();
        assertTrue(list.validate());

        List<WebComponent> listItems = list.getListItems();
        assertEquals(3, listItems.size());
        assertEquals("Item 1", listItems.get(0).getText());
        assertEquals("Item 2", listItems.get(1).getText());
        assertEquals("Item 3", listItems.get(2).getText());
    }

    public void testListWithSelection() {
        WebComponent container = driver.findComponent(By.tagName("list-selection-example"));
        MatSelectionList selectionList = container.findComponent(By.className("mat-selection-list")).as(mat())
                .toSelectionList();
        assertTrue(selectionList.validate());

        List<MatListOption> options = selectionList.getListOptions();
        assertEquals(5, options.size());

        assertArrayEquals(new String[]{"Boots", "Clogs", "Loafers", "Moccasins", "Sneakers"},
                options.stream().map(WebComponent::getText).toArray());

        options.forEach(option -> assertFalse(option.isSelected()));

        options.get(3).click();
        assertTrue(options.get(3).isSelected());

        options.get(2).click();
        assertTrue(options.get(2).isSelected());
        assertTrue(options.get(3).isSelected());
        assertTrue(requireNonNull(options.get(2).getCheckbox()).isSelected());
        assertTrue(requireNonNull(options.get(3).getCheckbox()).isSelected());
    }

    public void testListWithSingleSelection() {
        WebComponent container = driver.findComponent(By.tagName("list-single-selection-example"));
        MatSelectionList selectionList = container.findComponent(By.className("mat-selection-list")).as(mat())
                .toSelectionList();
        assertTrue(selectionList.validate());

        List<MatListOption> options = selectionList.getListOptions();
        assertEquals(5, options.size());

        assertArrayEquals(new String[]{"Boots", "Clogs", "Loafers", "Moccasins", "Sneakers"},
                options.stream().map(WebComponent::getText).toArray());

        options.forEach(option -> assertFalse(option.isSelected()));

        options.get(3).click();
        assertTrue(options.get(3).isSelected());

        options.get(2).click();
        assertTrue(options.get(2).isSelected());
        assertFalse(options.get(3).isSelected());
        assertNull(options.get(2).getCheckbox());
        assertNull(options.get(3).getCheckbox());
    }

    public static void main(String[] args) {
        MatListTestCases test = new MatListTestCases();
        try {
            test.setUpDriver(EDGE);
            test.driver.navigate().to("https://material.angular.io/components/list/examples");
            test.testList();
            test.testListWithSelection();
            test.testListWithSingleSelection();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
