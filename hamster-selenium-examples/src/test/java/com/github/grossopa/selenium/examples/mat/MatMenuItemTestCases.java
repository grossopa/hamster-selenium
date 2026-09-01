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

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatMenuItemFinder;
import com.github.grossopa.hamster.selenium.component.mat.main.MatButton;
import com.github.grossopa.hamster.selenium.component.mat.main.MatMenu;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the actual features of {@link MatMenuItem}
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatMenuItemTestCases extends AbstractBrowserSupport {

    public void testMenuWithIcons() {
        MatButton iconButton = driver.findComponent(By.tagName("menu-icons-example"))
                .findComponent(By2.xpathBuilder().relative("button").build()).as(mat()).toButton();
        iconButton.click();

        MatMenuItemFinder finder = new MatMenuItemFinder(driver, new MatConfig());
        MatMenu menu = finder.findTopMenu(300L);

        List<MatMenuItem> menuItems = menu.getMenuItems();

        assertEquals(3, menuItems.size());
        assertTrue(menuItems.get(0).isEnabled());
        assertFalse(menuItems.get(1).isEnabled());
        assertTrue(menuItems.get(2).isEnabled());

        // the archived doc site is slow; poll until the menu item texts are rendered
        assertEquals("Redial", awaitText(menuItems.get(0).findComponent(By.tagName("span"))));
        assertEquals("Check voice mail", awaitText(menuItems.get(1).findComponent(By.tagName("span"))));
        assertEquals("Disable alerts", awaitText(menuItems.get(2).findComponent(By.tagName("span"))));

        menu.close();
        driver.threadSleep(1500L);
    }

    public void testNestedMenu() {
        MatButton animalIndexButton = driver.findComponent(By.tagName("menu-nested-example"))
                .findComponent(By2.xpathBuilder().relative("button").build()).as(mat()).toButton();

        animalIndexButton.click();

        MatMenuItemFinder finder = new MatMenuItemFinder(driver, new MatConfig());
        MatMenu menu = finder.findTopMenu(300L);
        expandAfterTextRendered(menu, "Vertebrates").expandItemByText("Amphibians", 300L, 300L);

        List<MatMenu> menus = finder.findMenus(0L);

        assertEquals(3, menus.size());
        menus.forEach(container -> assertTrue(container.validate()));

        assertEquals("Vertebrates", awaitText(menus.get(0).findComponentsAs(By.className("mat-menu-item"),
                c -> c.as(mat()).toMenuItem()).stream().peek(item -> assertTrue(item::validate))
                .filter(MatMenuItem::isExpanded).findFirst().orElseThrow()));

        assertEquals("Amphibians", awaitText(menus.get(1).findComponentsAs(By.className("mat-menu-item"),
                c -> c.as(mat()).toMenuItem()).stream().peek(item -> assertTrue(item::validate))
                .filter(MatMenuItem::isExpanded).findFirst().orElseThrow()));

        MatMenu menuToClose;
        while ((menuToClose = finder.findTopMenu()) != null) {
            menuToClose.close();
            driver.threadSleep(1500L);
        }
    }

    public void testNestedMenuComplexActions() {
        MatButton animalIndexButton = driver.findComponent(By.tagName("menu-nested-example"))
                .findComponent(By2.xpathBuilder().relative("button").build()).as(mat()).toButton();
        animalIndexButton.click();

        MatMenuItemFinder finder = new MatMenuItemFinder(driver, new MatConfig());
        MatMenu menu1 = finder.findTopMenu(300L);
        menu1.expandItemByIndex(1, 300L, 300L);
        menu1.expandItemByIndex(0, 300L, 300L);
        assertTrue(menu1.getMenuItems().get(0).isExpanded());

        MatMenu menuToClose;
        while ((menuToClose = finder.findTopMenu()) != null) {
            menuToClose.close();
            driver.threadSleep(1500L);
        }
    }

    public void testSelection() {
        MatButton animalIndexButton = driver.findComponent(By.tagName("menu-nested-example"))
                .findComponent(By2.xpathBuilder().relative("button").build()).as(mat()).toButton();

        animalIndexButton.click();

        MatMenuItemFinder finder = new MatMenuItemFinder(driver, new MatConfig());
        MatMenu menu = finder.findTopMenu(300L);
        MatMenu menu3 = expandAfterTextRendered(menu, "Vertebrates").expandItemByText("Amphibians", 300L, 300L);

        menu3.selectItemByIndex(0);
        driver.threadSleep(1500L);
        assertNull(finder.findTopMenu());

        animalIndexButton.click();

        MatMenu menu11 = finder.findTopMenu(300L);
        MatMenu menu31 = menu11.expandItemByText("Vertebrates", 300L, 300L).expandItemByText("Amphibians", 300L, 300L);

        menu31.selectItemByText("Arroyo toad");
        driver.threadSleep(1500L);
        assertNull(finder.findTopMenu());
    }

    /**
     * Waits until the menu item texts are rendered on the slow archived site, then expands the item by text.
     */
    private MatMenu expandAfterTextRendered(MatMenu menu, String text) {
        // the archived doc site is slow; poll until any menu item text is rendered before matching by text
        for (int i = 0; i < 40; i++) {
            MatMenuItem rendered = menu.getMenuItems().stream()
                    .filter(item -> item.getText() != null && !item.getText().isBlank()).findFirst().orElse(null);
            if (rendered != null) {
                break;
            }
            driver.threadSleep(250L);
        }
        return menu.expandItemByText(text, 300L, 300L);
    }

    private String awaitText(WebComponent component) {
        for (int i = 0; i < 40; i++) {
            String text = component.getText();
            if (text != null && !text.isBlank()) {
                return text;
            }
            driver.threadSleep(250L);
        }
        return component.getText();
    }

    public static void main(String[] args) {
        MatMenuItemTestCases test = new MatMenuItemTestCases();
        try {
            test.setUpDriver(EDGE);
            test.navigateToExamples("https://v12.material.angular.io/components/menu/examples");
            test.testMenuWithIcons();
            test.testNestedMenu();
            test.testNestedMenuComplexActions();
            test.testSelection();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
