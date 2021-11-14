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

package com.github.grossopa.selenium.examples.mui.v4;

import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiAvatar;
import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiBadge;
import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiChip;
import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiList;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;
import java.util.function.Supplier;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for Data Display components.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiDataDisplayTestCases extends AbstractBrowserSupport {

    public void testAvatar() {
        driver.navigate().to("https://v4.mui.com/components/avatars/");

        List<MuiAvatar> avatars = driver.findComponent(By.id("ImageAvatars.js")).findComponent(By.xpath("parent::*"))
                .findComponents(By2.attrContains("class", "MuiAvatar-root")).stream().map(c -> c.as(mui()).toAvatar())
                .collect(toList());

        assertEquals(3, avatars.size());
        assertEquals("Remy Sharp", avatars.get(0).getAlt());
        assertEquals("/static/images/avatar/1.jpg", avatars.get(0).getSrc());

        List<MuiAvatar> letterAvatars = driver.findComponent(By.id("LetterAvatars.js"))
                .findComponent(By.xpath("parent::*")).findComponents(By2.attrContains("class", "MuiAvatar-root"))
                .stream().map(c -> c.as(mui()).toAvatar()).collect(toList());

        assertEquals(3, letterAvatars.size());
        assertEquals("H", letterAvatars.get(0).getText());
        assertEquals("N", letterAvatars.get(1).getText());
        assertEquals("OP", letterAvatars.get(2).getText());
    }

    public void testBadge() {
        driver.navigate().to("https://v4.mui.com/components/badges/");

        List<MuiBadge> badges = driver.findComponent(By.id("SimpleBadge.js")).findComponent(By.xpath("parent::*"))
                .findComponents(By2.className("MuiBadge-root")).stream().map(c -> c.as(mui()).toBadge())
                .collect(toList());

        assertEquals(3, badges.size());
        assertEquals(4, badges.get(1).getBadgeNumber());
        assertEquals(4, badges.get(2).getBadgeNumber());
        assertFalse(badges.get(2).isDotDisplayed());

        List<MuiBadge> badges2 = driver.findComponent(By.id("BadgeVisibility.js")).findComponent(By.xpath("parent::*"))
                .findComponents(By2.className("MuiBadge-root")).stream().map(c -> c.as(mui()).toBadge())
                .collect(toList());

        assertEquals(2, badges2.size());
        assertTrue(badges2.get(1).isDotDisplayed());

        List<MuiBadge> badges3 = driver.findComponent(By.id("ShowZeroBadge.js")).findComponent(By.xpath("parent::*"))
                .findComponents(By2.className("MuiBadge-root")).stream().map(c -> c.as(mui()).toBadge())
                .collect(toList());

        assertEquals(2, badges3.size());
        assertFalse(badges3.get(0).isBadgeDisplayed());
        assertTrue(badges3.get(1).isBadgeDisplayed());
    }

    public void testChip() {
        driver.navigate().to("https://v4.mui.com/components/chips/");
        List<MuiChip> chips = driver.findComponent(By.id("Chips.js")).findComponent(By.xpath("parent::*"))
                .findComponents(By2.className("MuiChip-root")).stream().map(c -> c.as(mui()).toChip())
                .collect(toList());

        assertTrue(chips.size() > 9);

        // first element without avatar or deleteIcon.
        validateChip(chips.get(0), false, false, false, false, "Basic");

        validateChip(chips.get(1), false, false, false, false, "Disabled");
        assertFalse(chips.get(1).isEnabled());

        validateChip(chips.get(2), true, false, false, true, "Clickable");
        assertEquals("M", chips.get(2).getAvatar().getText());

        validateChip(chips.get(3), true, false, true, false, "Deletable");
        assertEquals("Natacha", chips.get(3).getAvatar().as(mui()).toAvatar().getAlt());

        validateChip(chips.get(4), false, true, true, true, "Clickable deletable");
        assertTrue(chips.get(4).getIcon().attributeContains("class", "MuiSvgIcon-root"));

        validateChip(chips.get(5), false, false, true, true, "Custom delete icon");

        validateChip(chips.get(6), false, false, false, true, "Clickable Link");

        Supplier<List<MuiChip>> arrayChipsSupplier = () -> driver.findComponent(By.id("ChipsArray.js"))
                .findComponent(By.xpath("parent::*")).findComponents(By2.className("MuiChip-root")).stream()
                .map(c -> c.as(mui()).toChip()).collect(toList());

        List<MuiChip> arrayChips = arrayChipsSupplier.get();
        assertEquals(5, arrayChips.size());
        arrayChips.get(0).getDeleteIcon().click();
        List<MuiChip> deletedArrayChips = arrayChipsSupplier.get();
        assertEquals(4, deletedArrayChips.size());
    }

    private void validateChip(MuiChip chip, boolean expectedHasAvatar, boolean expectedHasIcon,
            boolean expectedHasDeleteIcon, boolean expectedIsClickable, String expectedLabel) {
        assertEquals(expectedHasAvatar, chip.hasAvatar());
        assertEquals(expectedHasIcon, chip.hasIcon());
        assertEquals(expectedHasDeleteIcon, chip.hasDeleteIcon());
        assertEquals(expectedHasDeleteIcon, chip.isDeletable());
        assertEquals(expectedIsClickable, chip.isClickable());
        assertEquals(expectedLabel, chip.getLabel().getText());
    }

    public void testList() {
        driver.navigate().to("https://v4.mui.com/components/lists/");

        List<MuiList> simpleLists = driver.findComponent(By.id("SimpleList.js")).findComponent(By.xpath("parent::*"))
                .findComponents(By2.className("MuiList-root")).stream().map(c -> c.as(mui()).toList())
                .collect(toList());

        assertEquals(2, simpleLists.size());
        assertEquals(2, simpleLists.get(0).getListItems().size());
        assertEquals(2, simpleLists.get(1).getListItems().size());
    }

    public static void main(String[] args) {
        MuiDataDisplayTestCases test = new MuiDataDisplayTestCases();
        test.setUpDriver(EDGE);
        test.testAvatar();
        test.testBadge();
        test.testChip();
        test.testList();
    }
}
