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

package org.hamster.selenium.examples.mui;

import org.hamster.selenium.component.mui.MuiButton;
import org.hamster.selenium.component.mui.MuiButtonGroup;
import org.hamster.selenium.component.mui.MuiComponents;
import org.hamster.selenium.component.mui.datadisplay.MuiBadge;
import org.hamster.selenium.core.component.WebComponent;
import org.hamster.selenium.core.locator.By2;
import org.hamster.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.hamster.selenium.component.mui.MuiComponents.mui;
import static org.hamster.selenium.core.driver.WebDriverType.CHROME;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for Data Display components.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiDataDisplayTestCases extends AbstractBrowserSupport {

    public void testBadge() {
        driver.navigate().to("https://material-ui.com/components/badges/");

        List<MuiBadge> badges = driver.findComponent(By.id("SimpleBadge.js")).findComponent(By.xpath("parent::*"))
                .findComponents(By2.attr("class", "MuiBadge-root").contains().anyDepthChild().build()).stream()
                .map(c -> c.as(mui()).toBadge()).collect(toList());

        assertEquals(3, badges.size());
        assertEquals(4, badges.get(1).getBadgeNumber());
        assertEquals(4, badges.get(2).getBadgeNumber());
        assertFalse(badges.get(2).isDotDisplayed());

        List<MuiBadge> badges2 = driver.findComponent(By.id("BadgeVisibility.js")).findComponent(By.xpath("parent::*"))
                .findComponents(By2.attr("class", "MuiBadge-root").contains().anyDepthChild().build()).stream()
                .map(c -> c.as(mui()).toBadge()).collect(toList());

        assertEquals(2, badges2.size());
        assertTrue(badges2.get(1).isDotDisplayed());

        List<MuiBadge> badges3 = driver.findComponent(By.id("ShowZeroBadge.js")).findComponent(By.xpath("parent::*"))
                .findComponents(By2.attr("class", "MuiBadge-root").contains().anyDepthChild().build()).stream()
                .map(c -> c.as(mui()).toBadge()).collect(toList());

        assertEquals(2, badges3.size());
        assertFalse(badges3.get(0).isBadgeDisplayed());
        assertTrue(badges3.get(1).isBadgeDisplayed());

    }

    public static void main(String[] args) {
        MuiDataDisplayTestCases test = new MuiDataDisplayTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testBadge();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            test.stopDriver();
        }
    }
}
