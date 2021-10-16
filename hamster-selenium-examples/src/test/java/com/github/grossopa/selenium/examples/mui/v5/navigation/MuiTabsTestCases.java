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

import com.github.grossopa.selenium.component.mui.v4.navigation.MuiTabs;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiTabs}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiTabsTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basics.
     *
     * @see <a href="https://mui.com/components/tabs/#basic-tabs">https://mui.com/components/tabs/#basic-tabs</a>
     */
    public void testBasicTabs() {
        MuiTabs tabs = driver.findComponent(By.id("BasicTabs.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiTabs-root")).as(muiV5()).toTabs();
        assertTrue(tabs.validate());
        assertEquals(3, tabs.getTabs().size());
        tabs.getTabs().forEach(tab -> assertTrue(tab.validate()));

        assertEquals("ITEM ONE", tabs.getTabs().get(0).getText());
        assertEquals("ITEM TWO", tabs.getTabs().get(1).getText());
        assertEquals("ITEM THREE", tabs.getTabs().get(2).getText());

        tabs.getTabs().get(1).click();
        assertFalse(tabs.getTabs().get(0).isSelected());
        assertTrue(tabs.getTabs().get(1).isSelected());
        assertFalse(tabs.getTabs().get(0).isSelected());
    }

    /**
     * Tests the wrapped labels.
     *
     * @see <a href="https://mui.com/components/tabs/#wrapped-labels">
     * https://mui.com/components/tabs/#wrapped-labels</a>
     */
    public void testWrappedLabels() {
        MuiTabs tabs = driver.findComponent(By.id("TabsWrappedLabel.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiTabs-root")).as(muiV5()).toTabs();
        assertTrue(tabs.validate());
        assertEquals(3, tabs.getTabs().size());
        tabs.getTabs().forEach(tab -> assertTrue(tab.validate()));

        assertEquals("NEW ARRIVALS IN THE LONGEST TEXT OF NONFICTION THAT SHOULD APPEAR IN THE NEXT LINE",
                tabs.getTabs().get(0).getText());
        assertEquals("ITEM TWO", tabs.getTabs().get(1).getText());
        assertEquals("ITEM THREE", tabs.getTabs().get(2).getText());
    }


    /**
     * Tests the disabled tab.
     *
     * @see <a href="https://mui.com/components/tabs/#disabled-tab">https://mui.com/components/tabs/#disabled-tab</a>
     */
    public void testDisabledTab() {
        MuiTabs tabs = driver.findComponent(By.id("DisabledTabs.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiTabs-root")).as(muiV5()).toTabs();
        assertTrue(tabs.validate());
        assertTrue(tabs.getTabs().get(0).isEnabled());
        assertFalse(tabs.getTabs().get(1).isEnabled());
        assertTrue(tabs.getTabs().get(2).isEnabled());
    }

    /**
     * Tests the scrollable tab.
     *
     * @see <a href="https://mui.com/components/tabs/#scrollable-tabs">
     * https://mui.com/components/tabs/#scrollable-tabs</a>
     */
    public void testScrollableTabs() {
        MuiTabs tabs = driver.findComponent(By.id("ScrollableTabsButtonAuto.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiTabs-root")).as(muiV5()).toTabs();
        assertTrue(tabs.validate());
        assertEquals(7, tabs.getTabs().size());

        assertEquals("ITEM ONE", tabs.getTabs().get(0).getText());
        assertEquals("ITEM TWO", tabs.getTabs().get(1).getText());
        assertEquals("ITEM THREE", tabs.getTabs().get(2).getText());
        assertEquals("ITEM FOUR", tabs.getTabs().get(3).getText());
        assertEquals("ITEM FIVE", tabs.getTabs().get(4).getText());
        assertEquals("ITEM SIX", tabs.getTabs().get(5).getText());
        assertEquals("ITEM SEVEN", tabs.getTabs().get(6).getText());

        assertDoesNotThrow(() -> tabs.getNextScrollButton().orElseThrow().click());
        assertDoesNotThrow(() -> tabs.getPreviousScrollButton().orElseThrow().click());
    }

    /**
     * Tests the vertical tab.
     *
     * @see <a href="https://mui.com/components/tabs/#vertical-tabs">
     * https://mui.com/components/tabs/#vertical-tabs</a>
     */
    public void testVerticalTabs() {
        MuiTabs tabs = driver.findComponent(By.id("VerticalTabs.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiTabs-root")).as(muiV5()).toTabs();
        assertTrue(tabs.validate());

        tabs.getNextScrollButton().orElseThrow().click();
        tabs.getPreviousScrollButton().orElseThrow().click();
    }

    public static void main(String[] args) {
        MuiTabsTestCases test = new MuiTabsTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/tabs/");

        test.testBasicTabs();
        test.testWrappedLabels();
        test.testDisabledTab();
        test.testScrollableTabs();
        test.testVerticalTabs();
    }
}
