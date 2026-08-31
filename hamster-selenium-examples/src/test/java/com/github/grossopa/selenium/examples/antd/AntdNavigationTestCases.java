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
package com.github.grossopa.selenium.examples.antd;

import com.github.grossopa.selenium.component.antd.navigation.*;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.github.grossopa.selenium.component.antd.AntdComponents.antd;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for navigation components based on the official Ant Design documentation page.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class AntdNavigationTestCases extends AbstractBrowserSupport {

    /**
     * Waits for the lazy-loaded demo container to be rendered and returns it.
     *
     * @param id the demo container element id
     * @return the demo container component
     */
    private WebComponent demo(String id) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        return driver.findComponent(By.id(id));
    }

    public void testBreadcrumb() {
        driver.navigate().to("https://ant.design/components/breadcrumb/");

        AntdBreadcrumb breadcrumb = demo("breadcrumb-demo-basic")
                .findComponent(By.className("ant-breadcrumb")).as(antd()).toBreadcrumb();

        assertTrue(breadcrumb.validate());
        assertFalse(breadcrumb.getItems().isEmpty());
    }

    public void testMenu() {
        driver.navigate().to("https://ant.design/components/menu/");

        AntdMenu menu = demo("menu-demo-inline")
                .findComponent(By.className("ant-menu")).as(antd()).toMenu();

        assertTrue(menu.validate());
        assertFalse(menu.getMenuItems().isEmpty());
        assertEquals(1, menu.getSelectedItems().size());

        AntdMenuItem selectedItem = menu.getSelectedItems().get(0);
        assertTrue(selectedItem.isSelected());
    }

    public void testPagination() {
        driver.navigate().to("https://ant.design/components/pagination/");

        AntdPagination pagination = demo("pagination-demo-basic")
                .findComponent(By.className("ant-pagination")).as(antd()).toPagination();

        assertTrue(pagination.validate());
        assertEquals(1, pagination.getCurrentPageIndex());

        pagination.setPageIndex(3);
        assertEquals(3, pagination.getCurrentPageIndex());
    }

    public void testSteps() {
        driver.navigate().to("https://ant.design/components/steps/");

        AntdSteps steps = demo("steps-demo-simple")
                .findComponent(By.className("ant-steps")).as(antd()).toSteps();

        assertTrue(steps.validate());
        assertEquals(3, steps.getSteps().size());
        assertEquals(1, steps.getCurrentStep());
    }

    public static void main(String[] args) {
        AntdNavigationTestCases test = new AntdNavigationTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testBreadcrumb();
            test.testMenu();
            test.testPagination();
            test.testSteps();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
