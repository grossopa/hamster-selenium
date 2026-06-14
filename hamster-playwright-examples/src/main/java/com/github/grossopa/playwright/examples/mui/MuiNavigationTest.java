/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.examples.mui;

import com.github.grossopa.playwright.component.mui.MuiComponents;
import com.github.grossopa.playwright.component.mui.v4.navigation.*;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MUI Navigation components
 * Covers Tabs, Menu, Drawer, Accordion, Breadcrumbs, Pagination, etc.
 *
 * @author Jack Yin
 * @since 1.12
 */
public class MuiNavigationTest extends AbstractBrowserSupport {

    private static final MuiComponents mui = MuiComponents.mui();

    /**
     * Test MUI Tabs component with multiple tabs
     */
    public void testTabs() {
        System.out.println("=== Testing MUI Tabs ===");
        driver.navigate("https://mui.com/material-ui/react-tabs/", 600_000);
        
        List<WebComponent> tabContainers = driver.findComponents(".MuiTabs-root");
        System.out.println("Found " + tabContainers.size() + " tab containers");
        
        if (!tabContainers.isEmpty()) {
            mui.setContext(tabContainers.get(0), driver);
            MuiTabs tabs = mui.toTabs();
            System.out.println("Tabs container found");
            
            // Get individual tabs
            List<WebComponent> tabList = tabs.findComponents(".MuiTab-root");
            System.out.println("Found " + tabList.size() + " tabs");
            
            // Print tab labels
            for (int i = 0; i < Math.min(tabList.size(), 5); i++) {
                System.out.println("  Tab " + (i + 1) + ": " + tabList.get(i).innerText());
            }
            
            // Click second tab if available
            if (tabList.size() >= 2) {
                System.out.println("Clicking second tab...");
                tabList.get(1).click();
            }
        }
    }

    /**
     * Test MUI Menu component
     */
    public void testMenu() {
        System.out.println("\n=== Testing MUI Menu ===");
        driver.navigate("https://mui.com/material-ui/react-menu/", 600_000);
        
        // Find menu trigger buttons
        List<WebComponent> menuButtons = driver.findComponents("button[aria-haspopup='true']");
        System.out.println("Found " + menuButtons.size() + " menu trigger buttons");
        
        // Look for menu components
        List<WebComponent> menus = driver.findComponents(".MuiMenu-root");
        System.out.println("Found " + menus.size() + " menu components");
        
        if (!menus.isEmpty()) {
            mui.setContext(menus.get(0), driver);
            MuiMenu menu = mui.toMenu();
            System.out.println("Menu component found");
            
            // Get menu items
            List<WebComponent> menuItems = menu.findComponents(".MuiMenuItem-root");
            System.out.println("Menu contains " + menuItems.size() + " items");
            
            if (!menuItems.isEmpty()) {
                System.out.println("First menu item: " + menuItems.get(0).innerText());
            }
        }
    }

    /**
     * Test MUI MenuItem component
     */
    public void testMenuItem() {
        System.out.println("\n=== Testing MUI MenuItem ===");
        driver.navigate("https://mui.com/material-ui/react-menu/", 600_000);
        
        List<WebComponent> menuItems = driver.findComponents(".MuiMenuItem-root");
        System.out.println("Found " + menuItems.size() + " menu items");
        
        if (!menuItems.isEmpty()) {
            mui.setContext(menuItems.get(0), driver);
            var menuItem = mui.toMenuItem();
            System.out.println("MenuItem component found");
            System.out.println("MenuItem text: " + menuItem.innerText());
            
            // Check if disabled
            boolean disabled = menuItem.getAttribute("aria-disabled") != null && 
                              menuItem.getAttribute("aria-disabled").equals("true");
            System.out.println("MenuItem disabled: " + disabled);
        }
    }

    /**
     * Test MUI Drawer component
     */
    public void testDrawer() {
        System.out.println("\n=== Testing MUI Drawer ===");
        driver.navigate("https://mui.com/material-ui/react-drawer/", 600_000);
        
        // Find drawer trigger buttons
        List<WebComponent> openButtons = driver.findComponents("button");
        System.out.println("Found " + openButtons.size() + " buttons");
        
        // Look for drawer components
        List<WebComponent> drawers = driver.findComponents(".MuiDrawer-root");
        System.out.println("Found " + drawers.size() + " drawer components");
        
        if (!drawers.isEmpty()) {
            mui.setContext(drawers.get(0), driver);
            MuiDrawer drawer = mui.toDrawer();
            System.out.println("Drawer component found");
            
            // Check drawer anchor
            String anchor = drawer.getAttribute("data-anchor");
            System.out.println("Drawer anchor: " + (anchor != null ? anchor : "left"));
        }
    }

    /**
     * Test MUI Accordion component
     */
    public void testAccordion() {
        System.out.println("\n=== Testing MUI Accordion ===");
        driver.navigate("https://mui.com/material-ui/react-accordion/", 600_000);
        
        List<WebComponent> accordions = driver.findComponents(".MuiAccordion-root");
        System.out.println("Found " + accordions.size() + " accordions");
        
        if (!accordions.isEmpty()) {
            mui.setContext(accordions.get(0), driver);
            MuiAccordion accordion = mui.toAccordion();
            System.out.println("Accordion component found");
            
            // Check if expanded
            boolean expanded = accordion.getAttribute("aria-expanded") != null && 
                              accordion.getAttribute("aria-expanded").equals("true");
            System.out.println("Accordion expanded: " + expanded);
            
            // Get accordion summary
            List<WebComponent> summaries = accordion.findComponents(".MuiAccordionSummary-root");
            if (!summaries.isEmpty()) {
                System.out.println("Accordion summary: " + summaries.get(0).innerText());
            }
        }
    }

    /**
     * Test MUI Breadcrumbs component
     */
    public void testBreadcrumbs() {
        System.out.println("\n=== Testing MUI Breadcrumbs ===");
        driver.navigate("https://mui.com/material-ui/react-breadcrumbs/", 600_000);
        
        List<WebComponent> breadcrumbs = driver.findComponents(".MuiBreadcrumbs-root");
        System.out.println("Found " + breadcrumbs.size() + " breadcrumb navigations");
        
        if (!breadcrumbs.isEmpty()) {
            System.out.println("Breadcrumbs component found");
            
            // Get breadcrumb items
            List<WebComponent> breadcrumbItems = breadcrumbs.get(0).findComponents(".MuiBreadcrumbs-li");
            System.out.println("Breadcrumb contains " + breadcrumbItems.size() + " items");
            
            // Print breadcrumb path
            StringBuilder path = new StringBuilder();
            for (int i = 0; i < breadcrumbItems.size(); i++) {
                if (i > 0) path.append(" > ");
                path.append(breadcrumbItems.get(i).innerText());
            }
            System.out.println("Breadcrumb path: " + path.toString());
        }
    }

    /**
     * Test MUI Pagination component
     */
    public void testPagination() {
        System.out.println("\n=== Testing MUI Pagination ===");
        driver.navigate("https://mui.com/material-ui/react-pagination/", 600_000);
        
        List<WebComponent> paginations = driver.findComponents(".MuiPagination-root");
        System.out.println("Found " + paginations.size() + " pagination components");
        
        if (!paginations.isEmpty()) {
            mui.setContext(paginations.get(0), driver);
            var pagination = mui.toPagination();
            System.out.println("Pagination component found");
            
            // Get pagination items
            List<WebComponent> paginationItems = pagination.findComponents(".MuiPaginationItem-root");
            System.out.println("Pagination has " + paginationItems.size() + " items");
            
            // Find current page
            for (WebComponent item : paginationItems) {
                String ariaCurrent = item.getAttribute("aria-current");
                if ("page".equals(ariaCurrent)) {
                    System.out.println("Current page: " + item.innerText());
                    break;
                }
            }
        }
    }

    /**
     * Test MUI Stepper component (if available)
     */
    public void testStepper() {
        System.out.println("\n=== Testing MUI Stepper ===");
        driver.navigate("https://mui.com/material-ui/react-stepper/", 600_000);
        
        List<WebComponent> steppers = driver.findComponents(".MuiStepper-root");
        System.out.println("Found " + steppers.size() + " stepper components");
        
        if (!steppers.isEmpty()) {
            System.out.println("Stepper component found");
            
            // Get steps
            List<WebComponent> steps = steppers.get(0).findComponents(".MuiStep-root");
            System.out.println("Stepper contains " + steps.size() + " steps");
            
            // Print step labels
            for (int i = 0; i < steps.size(); i++) {
                System.out.println("  Step " + (i + 1) + ": " + steps.get(i).innerText());
            }
        }
    }

    /**
     * Test combined navigation scenario - Tabs with content
     */
    public void testTabsWithContent() {
        System.out.println("\n=== Testing Tabs with Content Switching ===");
        driver.navigate("https://mui.com/material-ui/react-tabs/", 600_000);
        
        List<WebComponent> tabContainers = driver.findComponents(".MuiTabs-root");
        if (!tabContainers.isEmpty()) {
            // Get tabs
            List<WebComponent> tabs = tabContainers.get(0).findComponents(".MuiTab-root");
            System.out.println("Found " + tabs.size() + " tabs");
            
            // Click each tab and observe content change
            for (int i = 0; i < Math.min(tabs.size(), 3); i++) {
                System.out.println("\nClicking tab " + (i + 1) + "...");
                tabs.get(i).click();
                
                // Wait a bit for content to update
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                System.out.println("Tab " + (i + 1) + " activated: " + tabs.get(i).innerText());
            }
        }
    }

    /**
     * Test Menu interaction flow
     */
    public void testMenuInteractionFlow() {
        System.out.println("\n=== Testing Menu Interaction Flow ===");
        driver.navigate("https://mui.com/material-ui/react-menu/", 600_000);
        
        // Find and click menu trigger
        List<WebComponent> triggers = driver.findComponents("button[aria-haspopup='true']");
        if (!triggers.isEmpty()) {
            System.out.println("Found menu trigger button");
            
            // In a real scenario, you would:
            // 1. Click trigger to open menu
            // triggers.get(0).click();
            
            // 2. Wait for menu to appear
            // Thread.sleep(500);
            
            // 3. Select menu item
            // List<WebComponent> items = driver.findComponents(".MuiMenuItem-root");
            // if (!items.isEmpty()) {
            //     items.get(0).click();
            // }
            
            System.out.println("Menu interaction flow documented");
        }
    }

    public static void main(String[] args) {
        MuiNavigationTest test = new MuiNavigationTest();
        test.setUpDriver();
        
        try {
            test.testTabs();
            test.testMenu();
            test.testMenuItem();
            test.testDrawer();
            test.testAccordion();
            test.testBreadcrumbs();
            test.testPagination();
            test.testStepper();
            test.testTabsWithContent();
            test.testMenuInteractionFlow();
            
            System.out.println("\n=== All Navigation tests completed! ===");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Keep browser open for manual inspection
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
