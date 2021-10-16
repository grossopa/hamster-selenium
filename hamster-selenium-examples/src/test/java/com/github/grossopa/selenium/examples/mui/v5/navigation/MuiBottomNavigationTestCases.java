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

import com.github.grossopa.selenium.component.mui.v4.navigation.MuiBottomNavigation;
import com.github.grossopa.selenium.component.mui.v4.navigation.MuiBottomNavigationAction;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for {@link MuiBottomNavigation}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiBottomNavigationTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic bottom navigation.
     *
     * @see <a href="https://mui.com/components/bottom-navigation/#bottom-navigation">
     * https://mui.com/components/bottom-navigation/#bottom-navigation</a>
     */
    public void testBottomNavigation() {
        MuiBottomNavigation bottomNavigation = driver.findComponent(By.id("SimpleBottomNavigation.js"))
                .findComponent(By2.parent()).findComponent(By.className("MuiBottomNavigation-root")).as(muiV5())
                .toBottomNavigation();
        assertTrue(bottomNavigation.validate());

        List<MuiBottomNavigationAction> actions = bottomNavigation.getActions();
        assertEquals(3, actions.size());
        actions.forEach(action -> assertTrue(action.validate()));

        assertEquals("Recents", actions.get(0).getText());
        assertEquals("Favorites", actions.get(1).getText());
        assertEquals("Nearby", actions.get(2).getText());

    }

    public static void main(String[] args) {
        MuiBottomNavigationTestCases test = new MuiBottomNavigationTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/bottom-navigation/");

        test.testBottomNavigation();
    }
}
