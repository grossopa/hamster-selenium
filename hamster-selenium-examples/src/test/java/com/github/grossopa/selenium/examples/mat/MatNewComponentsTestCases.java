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
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the new Angular Material components added since 1.16.
 *
 * @author Jack Yin
 * @since 1.16
 */
public class MatNewComponentsTestCases extends AbstractBrowserSupport {

    public void testInput() {
        navigateToExamples("https://v12.material.angular.io/components/input/examples");
        WebComponent formField = driver.findComponent(By.id("input-overview-example"))
                .findComponent(By.tagName("mat-form-field"));
        MatInput input = formField.findComponent(By.tagName("input")).as(mat()).toInput();
        assertTrue(input.validate());
        // the v12 input has a pre-filled value; clear it first then type new value
        input.clear();
        input.sendKeys("Hello");
        assertEquals("Hello", input.getValue());
        System.out.println("Verified input value set and get");
    }

    public void testSelect() {
        navigateToExamples("https://v12.material.angular.io/components/select/examples");
        WebComponent formField = driver.findComponent(By.id("select-overview-example"))
                .findComponent(By.tagName("mat-form-field"));
        MatSelect select = formField.findComponent(By.tagName("mat-select")).as(mat()).toSelect();
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
        navigateToExamples("https://v12.material.angular.io/components/radio/examples");
        MatRadioGroup group = driver.findComponent(By.id("radio-overview-example"))
                .findComponent(By.tagName("mat-radio-group")).as(mat()).toRadioGroup();
        assertTrue(group.validate());
        var buttons = group.getRadioButtons();
        assertEquals(2, buttons.size());
        group.selectByIndex(1);
        assertTrue(buttons.get(1).isSelected());
        System.out.println("Verified radio group selection");
    }

    public void testCard() {
        navigateToExamples("https://v12.material.angular.io/components/card/examples");
        MatCard card = driver.findComponent(By.tagName("card-overview-example"))
                .findComponent(By.tagName("mat-card")).as(mat()).toCard();
        assertTrue(card.validate());
        assertNotNull(card.getTitle());
        System.out.println("Verified card title: " + card.getTitle().getText());
    }

    public void testTabs() {
        navigateToExamples("https://v12.material.angular.io/components/tabs/examples");
        MatTabGroup tabGroup = driver.findComponent(By.tagName("tab-group-basic-example"))
                .findComponent(By.tagName("mat-tab-group")).as(mat()).toTabGroup();
        assertTrue(tabGroup.validate());
        var labels = tabGroup.getTabLabels();
        assertTrue(labels.size() >= 2);
        tabGroup.selectTab(1);
        assertEquals(1, tabGroup.getSelectedTabIndex());
        System.out.println("Verified tab group selection");
    }

    public void testStepper() {
        navigateToExamples("https://v12.material.angular.io/components/stepper/examples");
        MatStepper stepper = driver.findComponent(By.id("stepper-overview-example"))
                .findComponent(By.tagName("mat-stepper")).as(mat()).toStepper();
        assertTrue(stepper.validate());
        // the v12 stepper renders step headers (mat-step-header) rather than mat-step elements;
        // verify the step labels via the header container
        var headers = stepper.findComponents(By.className("mat-step-header"));
        assertTrue(headers.size() >= 2);
        stepper.next();
        System.out.println("Verified stepper with " + headers.size() + " steps");
    }

    public void testTable() {
        navigateToExamples("https://v12.material.angular.io/components/table/examples");
        // the v12 overview example renders a <table class="mat-table"> rather than <mat-table>
        MatTable table = driver.findComponent(By.id("table-overview-example"))
                .findComponent(By.className("mat-table")).as(mat()).toTable();
        assertTrue(table.validate());
        var headerCells = table.getHeaderCells();
        assertTrue(headerCells.size() >= 2);
        var rows = table.getRows();
        assertTrue(rows.size() >= 1);
        System.out.println("Verified table with " + rows.size() + " rows and " + headerCells.size() + " columns");
    }

    public void testPaginator() {
        navigateToExamples("https://v12.material.angular.io/components/paginator/examples");
        MatPaginator paginator = driver.findComponent(By.id("paginator-overview-example"))
                .findComponent(By.tagName("mat-paginator")).as(mat()).toPaginator();
        assertTrue(paginator.validate());
        assertNotNull(paginator.getRangeLabel());
        System.out.println("Verified paginator range label: " + paginator.getRangeLabel());
    }

    public void testSidenav() {
        navigateToExamples("https://v12.material.angular.io/components/sidenav/examples");
        // the v12 sidenav-overview-example does not render inline;
        // use sidenav-drawer-overview-example which uses <mat-drawer-container>/<mat-drawer>
        MatSidenavContainer container = driver.findComponent(By.tagName("sidenav-drawer-overview-example"))
                .findComponent(By.tagName("mat-drawer-container")).as(mat()).toSidenavContainer();
        assertTrue(container.validate());
        MatSidenav sidenav = container.getSidenav();
        assertTrue(sidenav.validate());
        assertNotNull(container.getContent());
        System.out.println("Verified sidenav container and drawer");
    }

    public void testTree() {
        navigateToExamples("https://v12.material.angular.io/components/tree/examples");
        // the v12 tree page uses tree-flat-overview-example instead of tree-overview-example
        MatTree tree = driver.findComponent(By.tagName("tree-flat-overview-example"))
                .findComponent(By.tagName("mat-tree")).as(mat()).toTree();
        assertTrue(tree.validate());
        var nodes = tree.getNodes();
        assertTrue(nodes.size() >= 1);
        System.out.println("Verified tree with " + nodes.size() + " nodes");
    }
}
