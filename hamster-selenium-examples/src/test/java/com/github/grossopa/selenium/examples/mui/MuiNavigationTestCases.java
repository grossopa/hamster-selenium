/*
 * Copyright © 2020 the original author or authors.
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

package com.github.grossopa.selenium.examples.mui;

import com.github.grossopa.selenium.component.mui.navigation.*;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for Navigation components.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiNavigationTestCases extends AbstractBrowserSupport {

    public void testBreadcrumbs() {
        driver.navigate().to("https://material-ui.com/components/breadcrumbs/");

        List<MuiBreadcrumbs> breadcrumbsList = driver.findComponents(By.className("MuiBreadcrumbs-root")).stream()
                .map(component -> component.as(mui()).toBreadcrumbs()).collect(toList());

        MuiBreadcrumbs breadcrumbs1 = breadcrumbsList.get(0);
        assertEquals(3, breadcrumbs1.getItems().size());
        assertEquals("Material-UI", breadcrumbs1.getItemAt(0).as(mui()).toLink().getText());
        assertEquals("Core", breadcrumbs1.getItemAt(1).as(mui()).toLink().getText());
        assertEquals("Breadcrumb", breadcrumbs1.getItemAt(2).getText());
        assertEquals("/", breadcrumbs1.getSeparators().get(0).getText());
        assertFalse(breadcrumbs1.isCollapsed());

        MuiBreadcrumbs collapsedBreadcrumbs = breadcrumbsList.get(6);
        assertEquals(2, collapsedBreadcrumbs.getItems().size());
        assertEquals("Home", collapsedBreadcrumbs.getItemAt(0).getText());
        assertEquals("Belts", collapsedBreadcrumbs.getItemAt(1).getText());
        assertTrue(collapsedBreadcrumbs.isCollapsed());

        collapsedBreadcrumbs.expand();
        assertEquals(5, collapsedBreadcrumbs.getItems().size());
        assertEquals("Home", collapsedBreadcrumbs.getItemAt(0).getText());
        assertEquals("Catalog", collapsedBreadcrumbs.getItemAt(1).getText());
        assertEquals("Accessories", collapsedBreadcrumbs.getItemAt(2).getText());
        assertEquals("New Collection", collapsedBreadcrumbs.getItemAt(3).getText());
        assertEquals("Belts", collapsedBreadcrumbs.getItemAt(4).getText());
        assertFalse(collapsedBreadcrumbs.isCollapsed());
    }

    public void testBottomNavigation() {
        driver.navigate().to("https://material-ui.com/components/bottom-navigation/");

        List<MuiBottomNavigation> bottomNavigationList = driver.findComponents(By.className("MuiBottomNavigation-root"))
                .stream().map(component -> component.as(mui()).toBottomNavigation()).collect(toList());
        MuiBottomNavigation bn1 = bottomNavigationList.get(0);
        List<MuiBottomNavigationAction> actions = bn1.getActions();
        assertEquals(3, actions.size());
        assertEquals("Recents", actions.get(0).getText());
        assertEquals("Favorites", actions.get(1).getText());
        assertEquals("Nearby", actions.get(2).getText());

        assertTrue(actions.get(0).isSelected());
    }

    @SuppressWarnings("squid:S2925")
    public void testTabs() throws InterruptedException {
        driver.navigate().to("https://material-ui.com/components/tabs/");

        List<MuiTabs> tabsList = driver.findComponents(By.className("MuiTabs-root")).stream()
                .map(component -> component.as(mui()).toTabs()).collect(toList());
        MuiTabs tabs1 = tabsList.get(0);
        List<MuiTab> actions = tabs1.getTabs();
        assertEquals(3, actions.size());
        assertEquals("ITEM ONE", actions.get(0).getText());
        assertEquals("ITEM TWO", actions.get(1).getText());
        assertEquals("ITEM THREE", actions.get(2).getText());

        assertTrue(actions.get(0).isSelected());

        MuiTabs automaticScrollTabs = tabsList.get(5);
        driver.moveTo(automaticScrollTabs);
        assertEquals(7, automaticScrollTabs.getTabs().size());
        assertTrue(automaticScrollTabs.getNextScrollButton().isPresent());
        assertTrue(automaticScrollTabs.getPreviousScrollButton().isPresent());

        automaticScrollTabs.getTabs().get(3).click();
        Thread.sleep(600L);
        //driver.moveTo(driver.findComponent(By.id("scrollable-force-tabpanel-3")));
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-3")).isDisplayed());
        automaticScrollTabs.getTabs().get(5).click();
        Thread.sleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-5")).isDisplayed());
        automaticScrollTabs.getTabs().get(6).click();
        Thread.sleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-6")).isDisplayed());
        automaticScrollTabs.getTabs().get(0).click();
        Thread.sleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-0")).isDisplayed());
    }


    public static void main(String[] args) {
        MuiNavigationTestCases test = new MuiNavigationTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testBottomNavigation();
            test.testBreadcrumbs();
            test.testTabs();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            test.stopDriver();
        }
    }
}
