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

import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiChip;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiChip}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiChipTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic chip.
     *
     * @see <a href="https://mui.com/components/chips/#basic-chip">
     * https://mui.com/components/chips/#basic-chip</a>
     */
    public void testBasicChip() {
        List<MuiChip> chipList = driver.findComponent(By.id("BasicChips.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiChip-root"), c -> c.as(muiV5()).toChip());
        assertEquals(2, chipList.size());

        assertEquals("Chip Filled", chipList.get(0).getLabel().getText());
        assertEquals("Chip Outlined", chipList.get(1).getLabel().getText());
        chipList.forEach(chip -> {
            assertFalse(chip.hasDeleteIcon());
            assertFalse(chip.hasAvatar());
            assertFalse(chip.hasIcon());
        });
    }

    /**
     * Tests the deletable chips.
     *
     * @see <a href="https://mui.com/components/chips/#deletable">
     * https://mui.com/components/chips/#deletable</a>
     */
    public void testDeletable() {
        List<MuiChip> chipList = driver.findComponent(By.id("DeletableChips.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiChip-root"), c -> c.as(muiV5()).toChip());
        assertEquals(2, chipList.size());

        assertEquals("Deletable", chipList.get(0).getLabel().getText());
        assertEquals("Deletable", chipList.get(1).getLabel().getText());
        chipList.forEach(chip -> {
            assertTrue(chip.hasDeleteIcon());
            assertNotNull(chip.getDeleteIcon());
            assertFalse(chip.hasAvatar());
            assertFalse(chip.hasIcon());
        });
    }

    /**
     * Tests the avatar chips.
     *
     * @see <a href="https://mui.com/components/chips/#avatar-chip">
     * https://mui.com/components/chips/#avatar-chip</a>
     */
    public void testAvatarChips() {
        List<MuiChip> chipList = driver.findComponent(By.id("AvatarChips.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiChip-root"), c -> c.as(muiV5()).toChip());
        assertEquals(2, chipList.size());

        assertEquals("Avatar", chipList.get(0).getLabel().getText());
        assertEquals("Avatar", chipList.get(1).getLabel().getText());
        chipList.forEach(chip -> {
            assertFalse(chip.hasDeleteIcon());
            assertTrue(chip.hasAvatar());
            assertNotNull(chip.getAvatar());
            assertFalse(chip.hasIcon());
        });
    }

    /**
     * Tests the icon chips.
     *
     * @see <a href="https://mui.com/components/chips/#icon-chip">
     * https://mui.com/components/chips/#icon-chip</a>
     */
    public void testIconChips() {
        List<MuiChip> chipList = driver.findComponent(By.id("IconChips.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiChip-root"), c -> c.as(muiV5()).toChip());
        assertEquals(2, chipList.size());

        assertEquals("With Icon", chipList.get(0).getLabel().getText());
        assertEquals("With Icon", chipList.get(1).getLabel().getText());
        chipList.forEach(chip -> {
            assertFalse(chip.hasDeleteIcon());
            assertFalse(chip.hasAvatar());
            assertTrue(chip.hasIcon());
            assertNotNull(chip.getIcon());
        });
    }

    public static void main(String[] args) {
        MuiChipTestCases test = new MuiChipTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/chips/");

        test.testBasicChip();
        test.testAvatarChips();
        test.testDeletable();
        test.testIconChips();
    }
}
