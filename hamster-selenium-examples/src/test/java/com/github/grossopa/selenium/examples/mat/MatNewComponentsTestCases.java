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
package com.github.grossopa.selenium.examples.mat;

import com.github.grossopa.hamster.selenium.component.mat.main.*;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the new Angular Material components added since 1.16.
 *
 * @author Jack Yin
 * @since 1.16
 */
public class MatNewComponentsTestCases extends MatTestSupport {

    public void testInput() {
        navigateToExamples(baseUrl() + "input/examples", "input-overview-example");
        WebComponent formField = driver.findComponent(By.tagName("input-overview-example"))
                .findComponent(By.tagName("mat-form-field"));
        MatInput input = formField.findComponent(By.tagName("input")).as(matComponents()).toInput();
        assertTrue(input.validate());
        // the input has a pre-filled value; clear it first then type new value
        input.clear();
        input.sendKeys("Hello");
        assertEquals("Hello", input.getValue());
        System.out.println("Verified input value set and get");
    }

    public void testSelect() {
        navigateToExamples(baseUrl() + "select/examples", "select-overview-example");
        WebComponent formField = driver.findComponent(By.tagName("select-overview-example"))
                .findComponent(By.tagName("mat-form-field"));
        MatSelect select = formField.findComponent(By.tagName("mat-select")).as(matComponents()).toSelect();
        assertTrue(select.validate());
        // click the select to open the dropdown panel
        select.click();
        driver.threadSleep(500L);
        var options = select.getOptions();
        assertTrue(options.size() >= 3);
        select.selectByIndex(0);
        assertNotNull(select.getSelectedValue());
        System.out.println("Verified select options and selection");
    }

    public void testRadioGroup() {
        navigateToExamples(baseUrl() + "radio/examples", "radio-overview-example");
        MatRadioGroup group = driver.findComponent(By.tagName("radio-overview-example"))
                .findComponent(By.tagName("mat-radio-group")).as(matComponents()).toRadioGroup();
        assertTrue(group.validate());
        var buttons = group.getRadioButtons();
        assertEquals(2, buttons.size());
        group.selectByIndex(1);
        assertTrue(buttons.get(1).isSelected());
        System.out.println("Verified radio group selection");
    }

    public void testCard() {
        navigateToExamples(baseUrl() + "card/examples", "card-overview-example");
        MatCard card = driver.findComponent(By.tagName("card-overview-example"))
                .findComponent(By.tagName("mat-card")).as(matComponents()).toCard();
        assertTrue(card.validate());
        assertNotNull(card.getTitle());
        System.out.println("Verified card title: " + card.getTitle().getText());
    }

    public void testTabs() {
        navigateToExamples(baseUrl() + "tabs/examples", "tab-group-basic-example");
        MatTabGroup tabGroup = driver.findComponent(By.tagName("tab-group-basic-example"))
                .findComponent(By.tagName("mat-tab-group")).as(matComponents()).toTabGroup();
        assertTrue(tabGroup.validate());
        var labels = tabGroup.getTabLabels();
        assertTrue(labels.size() >= 2);
        tabGroup.selectTab(1);
        assertEquals(1, tabGroup.getSelectedTabIndex());
        System.out.println("Verified tab group selection");
    }

    public void testStepper() {
        navigateToExamples(baseUrl() + "stepper/examples", "stepper-overview-example");
        MatStepper stepper = driver.findComponent(By.tagName("stepper-overview-example"))
                .findComponent(By.tagName("mat-stepper")).as(matComponents()).toStepper();
        assertTrue(stepper.validate());
        // the stepper renders step headers (mat-step-header) rather than mat-step elements;
        // verify the step labels via the header container
        var headers = stepper.findComponents(By.className("mat-step-header"));
        assertTrue(headers.size() >= 2);
        stepper.next();
        System.out.println("Verified stepper with " + headers.size() + " steps");
    }

    public void testTable() {
        navigateToExamples(baseUrl() + "table/examples", "table-overview-example");
        // the overview example renders a <table class="mat-mdc-table"> rather than <mat-table>
        MatTable table = driver.findComponent(By.tagName("table-overview-example"))
                .findComponent(By.className(cssClass("table", "table"))).as(matComponents()).toTable();
        assertTrue(table.validate());
        var headerCells = table.getHeaderCells();
        assertTrue(headerCells.size() >= 2);
        var rows = table.getRows();
        assertTrue(rows.size() >= 1);
        System.out.println("Verified table with " + rows.size() + " rows and " + headerCells.size() + " columns");
    }

    public void testPaginator() {
        navigateToExamples(baseUrl() + "paginator/examples", "paginator-overview-example");
        MatPaginator paginator = driver.findComponent(By.tagName("paginator-overview-example"))
                .findComponent(By.tagName("mat-paginator")).as(matComponents()).toPaginator();
        assertTrue(paginator.validate());
        assertNotNull(paginator.getRangeLabel());
        System.out.println("Verified paginator range label: " + paginator.getRangeLabel());
    }

    public void testSidenav() {
        navigateToExamples(baseUrl() + "sidenav/examples", "sidenav-drawer-overview-example");
        // the sidenav-overview-example does not render inline;
        // use sidenav-drawer-overview-example which uses <mat-drawer-container>/<mat-drawer>
        MatSidenavContainer container = driver.findComponent(By.tagName("sidenav-drawer-overview-example"))
                .findComponent(By.tagName("mat-drawer-container")).as(matComponents()).toSidenavContainer();
        assertTrue(container.validate());
        MatSidenav sidenav = container.getSidenav();
        assertTrue(sidenav.validate());
        assertNotNull(container.getContent());
        System.out.println("Verified sidenav container and drawer");
    }

    public void testTree() {
        navigateToExamples(baseUrl() + "tree/examples", "tree-flat-overview-example");
        // the tree page uses tree-flat-overview-example instead of tree-overview-example
        MatTree tree = driver.findComponent(By.tagName("tree-flat-overview-example"))
                .findComponent(By.tagName("mat-tree")).as(matComponents()).toTree();
        assertTrue(tree.validate());
        var nodes = tree.getNodes();
        assertTrue(nodes.size() >= 1);
        System.out.println("Verified tree with " + nodes.size() + " nodes");
    }
}
