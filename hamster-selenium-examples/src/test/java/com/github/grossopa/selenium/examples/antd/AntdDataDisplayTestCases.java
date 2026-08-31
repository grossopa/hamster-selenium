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

import com.github.grossopa.selenium.component.antd.datadisplay.*;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.github.grossopa.selenium.component.antd.AntdComponents.antd;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for data display components based on the official Ant Design documentation page.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class AntdDataDisplayTestCases extends AbstractBrowserSupport {

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

    public void testAvatar() {
        driver.navigate().to("https://ant.design/components/avatar/");

        AntdAvatar avatar = demo("avatar-demo-basic")
                .findComponent(By.className("ant-avatar")).as(antd()).toAvatar();

        assertTrue(avatar.validate());
        assertFalse(avatar.hasImage());
    }

    public void testBadge() {
        driver.navigate().to("https://ant.design/components/badge/");

        AntdBadge badge = demo("badge-demo-basic")
                .findComponent(By.className("ant-badge")).as(antd()).toBadge();

        assertTrue(badge.validate());
        assertFalse(badge.isDot());
        assertEquals("5", badge.getBadgeContent().getText());
    }

    public void testCard() {
        driver.navigate().to("https://ant.design/components/card/");

        AntdCard card = demo("card-demo-basic")
                .findComponent(By.className("ant-card")).as(antd()).toCard();

        assertTrue(card.validate());
        assertTrue(card.hasHead());
        assertEquals("Default size card", card.getHead().findComponent(By.className("ant-card-head-title")).getText());
        assertFalse(card.getBody().getText().isEmpty());
    }

    public void testCollapse() {
        driver.navigate().to("https://ant.design/components/collapse/");

        AntdCollapse collapse = demo("collapse-demo-basic")
                .findComponent(By.className("ant-collapse")).as(antd()).toCollapse();

        assertTrue(collapse.validate());
        assertEquals(3, collapse.getPanels().size());
        assertEquals(1, collapse.getExpandedPanels().size());

        AntdCollapsePanel collapsedPanel = collapse.getPanels().get(1);
        assertFalse(collapsedPanel.isExpanded());
        // scroll into view to avoid the click being intercepted by the floating site toolbar
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", collapsedPanel);
        collapsedPanel.toggle();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> collapsedPanel.isExpanded());
        assertTrue(collapsedPanel.isExpanded());
    }

    public void testList() {
        driver.navigate().to("https://ant.design/components/list/");

        AntdList list = demo("list-demo-basic")
                .findComponent(By.className("ant-list")).as(antd()).toList();

        assertTrue(list.validate());
        assertEquals(4, list.getItems().size());
        assertFalse(list.isLoading());
    }

    public void testTabs() {
        driver.navigate().to("https://ant.design/components/tabs/");

        AntdTabs tabs = demo("tabs-demo-basic")
                .findComponent(By.className("ant-tabs")).as(antd()).toTabs();

        assertTrue(tabs.validate());
        assertEquals(3, tabs.getTabs().size());
        assertEquals("Tab 1", tabs.getSelectedTab().getLabel());
    }

    public void testTag() {
        driver.navigate().to("https://ant.design/components/tag/");

        AntdTag tag = demo("tag-demo-basic")
                .findComponent(By.className("ant-tag")).as(antd()).toTag();

        assertTrue(tag.validate());
        assertEquals("Tag 1", tag.getText());
        assertFalse(tag.isClosable());
    }

    public void testEmpty() {
        driver.navigate().to("https://ant.design/components/empty/");

        AntdEmpty empty = demo("empty-demo-basic")
                .findComponent(By.className("ant-empty")).as(antd()).toEmpty();

        assertTrue(empty.validate());
        assertEquals("No data", empty.getDescription().getText());
    }

    public static void main(String[] args) {
        AntdDataDisplayTestCases test = new AntdDataDisplayTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testAvatar();
            test.testBadge();
            test.testCard();
            test.testCollapse();
            test.testList();
            test.testTabs();
            test.testTag();
            test.testEmpty();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
