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
package com.github.grossopa.selenium.examples.mui.v5.navigation;

import com.github.grossopa.selenium.component.mui.v4.navigation.MuiMenubar;
import com.github.grossopa.selenium.component.mui.v4.navigation.MuiMenuItem;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiMenubar}.
 *
 * <p>The Menubar is a component recipe composed from Base UI's Menubar primitive styled with MUI building blocks.
 * The root element uses {@code role="menubar"} and the trigger buttons use {@code MuiButton-root}.</p>
 *
 * @author Jack Yin
 * @since 1.18
 * @see <a href="https://mui.com/material-ui/react-menubar/">https://mui.com/material-ui/react-menubar/</a>
 */
public class MuiMenubarTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic menubar. Verifies that the component can be located via {@code role="menubar"} and that the
     * trigger buttons are correctly identified.
     *
     * @see <a href="https://mui.com/material-ui/react-menubar/#basic-menubar">
     * https://mui.com/material-ui/react-menubar/#basic-menubar</a>
     */
    public void testBasicMenubar() {
        MuiMenubar menubar = driver.findComponent(By.id("BasicMenubar.js")).findComponent(By2.parent())
                .findComponent(By.cssSelector("[role='menubar']")).as(mui()).toMenubar();
        assertTrue(menubar.validate());

        List<MuiMenuItem> triggers = menubar.getMenuItems();
        assertEquals(2, triggers.size());
        assertEquals("File", triggers.get(0).getText());
        assertEquals("Help", triggers.get(1).getText());
    }

    /**
     * Tests the shortcut hints menubar. Verifies the triggers are correctly identified.
     *
     * @see <a href="https://mui.com/material-ui/react-menubar/#shortcut-hints">
     * https://mui.com/material-ui/react-menubar/#shortcut-hints</a>
     */
    public void testShortcutHints() {
        MuiMenubar menubar = driver.findComponent(By.id("ShortcutHintsMenubar.js")).findComponent(By2.parent())
                .findComponent(By.cssSelector("[role='menubar']")).as(mui()).toMenubar();
        assertTrue(menubar.validate());

        List<MuiMenuItem> triggers = menubar.getMenuItems();
        assertEquals(2, triggers.size());
        assertEquals("File", triggers.get(0).getText());
        assertEquals("Edit", triggers.get(1).getText());
    }

    /**
     * The main function for running the test cases.
     *
     * @param args not necessary
     */
    public static void main(String[] args) {
        MuiMenubarTestCases test = new MuiMenubarTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-menubar/");

        test.testBasicMenubar();
        test.testShortcutHints();
    }
}
