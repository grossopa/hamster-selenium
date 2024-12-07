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

import com.github.grossopa.selenium.component.mui.v4.navigation.MuiBreadcrumbs;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link com.github.grossopa.selenium.component.mui.v4.navigation.MuiBreadcrumbs}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiBreadcrumbsTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic features.
     * <a href="https://mui.com/material-ui/react-breadcrumbs/#basic-breadcrumbs">
     * https://mui.com/material-ui/react-breadcrumbs/#basic-breadcrumbs</a>
     */
    public void testBasicBreadcrumbs() {
        MuiBreadcrumbs breadcrumbs = driver.findComponent(By.id("BasicBreadcrumbs.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiBreadcrumbs-root")).as(muiV5()).toBreadcrumbs();
        assertTrue(breadcrumbs.validate());
        assertFalse(breadcrumbs.isCollapsed());

        List<WebComponent> componentList = breadcrumbs.getItems();
        assertEquals("MUI", componentList.get(0).getText());
        assertEquals("Core", componentList.get(1).getText());
        assertEquals("Breadcrumbs", componentList.get(2).getText());
    }

    /**
     * Tests the collapsed feature.
     * <a href="https://mui.com/material-ui/react-breadcrumbs/#collapsed-breadcrumbs">
     * https://mui.com/material-ui/react-breadcrumbs/#collapsed-breadcrumbs</a>
     */
    public void testCollapsedBreadcrumbs() {
        MuiBreadcrumbs breadcrumbs = driver.findComponent(By.id("CollapsedBreadcrumbs.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiBreadcrumbs-root")).as(muiV5()).toBreadcrumbs();
        assertTrue(breadcrumbs.validate());

        List<WebComponent> componentList = breadcrumbs.getItems();
        assertEquals("Home", componentList.get(0).getText());
        assertEquals("Belts", componentList.get(1).getText());

        assertTrue(breadcrumbs.isCollapsed());
        breadcrumbs.expand();

        componentList = breadcrumbs.getItems();
        assertEquals("Home", componentList.get(0).getText());
        assertEquals("Catalog", componentList.get(1).getText());
        assertEquals("Accessories", componentList.get(2).getText());
        assertEquals("New Collection", componentList.get(3).getText());
        assertEquals("Belts", componentList.get(4).getText());
    }

    public static void main(String[] args) {
        MuiBreadcrumbsTestCases test = new MuiBreadcrumbsTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-breadcrumbs/");

        test.testBasicBreadcrumbs();
        test.testCollapsedBreadcrumbs();
    }

}
