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

package com.github.grossopa.selenium.examples.mui.v5.datadisplay;

import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiBadge;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiBadge}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiBadgeTestCases extends AbstractBrowserSupport {

    /**
     * Tests for basic badge
     *
     * @see <a href="https://mui.com/components/badges/#basic-badge">https://mui.com/components/badges/#basic-badge</a>
     */
    public void testBasicBadge() {
        MuiBadge badge = driver.findComponent(By.id("SimpleBadge.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiBadge-root")).as(muiV5()).toBadge();
        assertTrue(badge.validate());

        assertEquals(4, badge.getBadgeNumber());
        assertTrue(badge.isBadgeDisplayed());
        assertFalse(badge.isDotDisplayed());
    }

    /**
     * Tests for badge visibility
     *
     * @see <a href="https://mui.com/components/badges/#badge-visibility">
     * https://mui.com/components/badges/#badge-visibility</a>
     */
    public void testBadgeVisibility() {
        List<MuiBadge> badgeList = driver.findComponent(By.id("BadgeVisibility.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiBadge-root"), c -> c.as(muiV5()).toBadge());
        assertEquals(2, badgeList.size());

        assertEquals(1, badgeList.get(0).getBadgeNumber());
        assertTrue(badgeList.get(0).isBadgeDisplayed());
        assertFalse(badgeList.get(0).isDotDisplayed());

        assertTrue(badgeList.get(1).isBadgeDisplayed());
        assertTrue(badgeList.get(1).isDotDisplayed());
    }

    /**
     * Tests the show / hide badge when it's zero
     *
     * @see <a href="https://mui.com/components/badges/#badge-visibility">
     * https://mui.com/components/badges/#badge-visibility</a>
     */
    public void testShowZeroBadge() {
        List<MuiBadge> badgeList = driver.findComponent(By.id("ShowZeroBadge.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiBadge-root"), c -> c.as(muiV5()).toBadge());
        assertEquals(2, badgeList.size());

        assertFalse(badgeList.get(0).isBadgeDisplayed());
        assertFalse(badgeList.get(0).isDotDisplayed());

        assertEquals(0, badgeList.get(1).getBadgeNumber());
        assertTrue(badgeList.get(1).isBadgeDisplayed());
        assertFalse(badgeList.get(1).isDotDisplayed());
    }

    /**
     * Tests the maximum value
     *
     * @see <a href="https://mui.com/components/badges/#maximum-value">
     * https://mui.com/components/badges/#maximum-value</a>
     */
    public void testMaximumValue() {
        List<MuiBadge> badgeList = driver.findComponent(By.id("BadgeMax.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiBadge-root"), c -> c.as(muiV5()).toBadge());
        assertEquals(3, badgeList.size());

        assertEquals("99", badgeList.get(0).getBadge().getText());
        assertEquals(99, badgeList.get(0).getBadgeNumber());
        assertEquals("99+", badgeList.get(1).getBadge().getText());
        assertEquals(99, badgeList.get(1).getBadgeNumber());
        assertEquals("999+", badgeList.get(2).getBadge().getText());
        assertEquals(999, badgeList.get(2).getBadgeNumber());
    }


    public static void main(String[] args) {
        MuiBadgeTestCases test = new MuiBadgeTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/badges/");

        test.testBasicBadge();
        test.testBadgeVisibility();
        test.testShowZeroBadge();
        test.testMaximumValue();
    }
}
