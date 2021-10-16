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

import com.github.grossopa.selenium.component.mui.v4.finder.MuiModalFinder;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.v4.navigation.MuiMenu;
import com.github.grossopa.selenium.component.mui.v4.navigation.MuiMenuItem;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for {@link MuiMenu}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiMenuTestCases extends AbstractBrowserSupport {

    /**
     * Tests for the basic menu.
     *
     * @see <a href="https://mui.com/components/menus/#basic-menu">https://mui.com/components/menus/#basic-menu</a>
     */
    public void testBasicMenu() {
        MuiButton dashboardButton = driver.findComponent(By.id("BasicMenu.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiButton-root")).as(muiV5()).toButton();

        dashboardButton.click();

        MuiModalFinder modalFinder = new MuiModalFinder(driver, muiV5().getConfig());
        MuiMenu menu = requireNonNull(modalFinder.findTopVisibleOverlay()).as(muiV5()).toMenu();
        assertTrue(menu.validate());

        List<MuiMenuItem> menuItems = menu.getMenuItems();
        menuItems.forEach(menuItem -> assertTrue(menuItem.validate()));

        assertEquals(3, menuItems.size());
        assertEquals("Profile", menuItems.get(0).getText());
        assertEquals("My account", menuItems.get(1).getText());
        assertEquals("Logout", menuItems.get(2).getText());
    }

    public static void main(String[] args) {
        MuiMenuTestCases test = new MuiMenuTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/menus/");

        test.testBasicMenu();
    }
}
