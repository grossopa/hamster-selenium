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

package com.github.grossopa.selenium.examples.mui;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.finder.MuiModalFinder;
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.inputs.MuiCheckbox;
import com.github.grossopa.selenium.component.mui.navigation.*;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static java.util.Objects.requireNonNull;
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
    public void testTabs() {
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
        assertFalse(automaticScrollTabs.isVertical());
        assertTrue(automaticScrollTabs.getNextScrollButton().isPresent());
        assertTrue(automaticScrollTabs.getPreviousScrollButton().isPresent());

        automaticScrollTabs.getTabs().get(3).click();
        driver.threadSleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-3")).isDisplayed());
        automaticScrollTabs.getTabs().get(5).click();
        driver.threadSleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-5")).isDisplayed());
        automaticScrollTabs.getTabs().get(6).click();
        driver.threadSleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-6")).isDisplayed());
        automaticScrollTabs.getTabs().get(0).click();
        driver.threadSleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-0")).isDisplayed());

        MuiTabs verticalTabs = driver.findComponent(By.id("VerticalTabs.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiTabs-root")).as(mui()).toTabs();

        driver.moveTo(verticalTabs);
        assertTrue(verticalTabs.isVertical());
    }

    @SuppressWarnings("squid:S2925")
    public void testMenu() {
        driver.navigate().to("https://material-ui.com/components/menus/");
        MuiModalFinder modalFinder = new MuiModalFinder(driver, new MuiConfig());
        // 6 Menu has keepMounted properties
        assertEquals(6, modalFinder.findOverlays("Popover").size());
        assertEquals(0, modalFinder.findVisibleOverlays("Popover").size());

        // Simple Menu
        MuiButton button = driver.findComponent(By.id("SimpleMenu.js")).findComponent(By.xpath("parent::*"))
                .findComponent(By.className("MuiButton-root")).as(mui()).toButton();
        button.click();
        driver.threadSleep(500L);
        List<WebComponent> components = modalFinder.findVisibleOverlays("Popover");
        assertEquals(1, components.size());
        MuiMenu menu = components.get(0).findComponent(By.className("MuiMenu-list")).as(mui()).toMenu();
        assertEquals(3, menu.getMenuItems().size());
    }

    @SuppressWarnings("squid:S2925")
    public void testAccordion() {
        driver.navigate().to("https://material-ui.com/components/accordion/");

        List<MuiAccordion> simpleAccordionList = driver.findComponent(By.id("SimpleAccordion.js"))
                .findComponent(By2.parent()).findComponents(By.className("MuiAccordion-root")).stream()
                .map(component -> component.as(mui()).toAccordion()).collect(toList());
        assertEquals(3, simpleAccordionList.size());

        assertEquals("Accordion 1", requireNonNull(simpleAccordionList.get(0).getAccordionSummary()).getText());
        assertEquals("", requireNonNull(simpleAccordionList.get(0).getAccordionDetails()).getText());

        assertEquals("Accordion 2", requireNonNull(simpleAccordionList.get(1).getAccordionSummary()).getText());
        assertEquals("", requireNonNull(simpleAccordionList.get(1).getAccordionDetails()).getText());

        simpleAccordionList.get(0).expand();
        simpleAccordionList.get(1).expand();
        driver.threadSleep(400L);

        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                        + "Suspendisse malesuada lacus ex, sit amet blandit leo lobortis eget.",
                requireNonNull(simpleAccordionList.get(0).getAccordionDetails()).getText());

        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                        + "Suspendisse malesuada lacus ex, sit amet blandit leo lobortis eget.",
                requireNonNull(simpleAccordionList.get(1).getAccordionDetails()).getText());

        assertFalse(simpleAccordionList.get(2).isEnabled());
        assertEquals("Disabled Accordion", simpleAccordionList.get(2).getText());

        //action
        List<MuiAccordion> actionAccordionList = driver.findComponent(By.id("ActionsInAccordionSummary.js"))
                .findComponent(By2.parent()).findComponents(By.className("MuiAccordion-root")).stream()
                .map(component -> component.as(mui()).toAccordion()).collect(toList());

        MuiAccordion actionAccordion1 = actionAccordionList.get(0);
        assertFalse(actionAccordion1.isExpand());
        driver.moveTo(actionAccordion1);
        actionAccordion1.expand();
        driver.threadSleep(400L);
        assertTrue(actionAccordion1.isExpand());
        MuiCheckbox checkbox1 = requireNonNull(actionAccordion1.getAccordionSummary())
                .findComponent(By.className("MuiCheckbox-root")).as(mui()).toCheckbox();
        assertFalse(checkbox1.isSelected());
        checkbox1.click();
        assertTrue(checkbox1.isSelected());
        assertTrue(actionAccordion1.isExpand());
    }

    public static void main(String[] args) {
        MuiNavigationTestCases test = new MuiNavigationTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testBottomNavigation();
            test.testBreadcrumbs();
            test.testTabs();
            test.testMenu();
            test.testAccordion();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            test.stopDriver();
        }
    }
}
