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

package com.github.grossopa.selenium.examples.mui.v5.inputs;

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiSelect;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.core.util.SeleniumUtils;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.join;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiSelect}
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiSelectTestCases extends AbstractBrowserSupport {

    /**
     * Test the basics of the select.
     *
     * @see <a href="https://mui.com/material-ui/react-select/#basic-select">
     * https://mui.com/material-ui/react-select/#basic-select</a>
     */
    public void testBasicSelect() {
        MuiSelect select = driver.findComponent(By.id("BasicSelect.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiSelect-select")).as(muiV5()).toSelect(By.className("MuiMenuItem-root"),
                        builder -> builder.optionValueAttribute("data-value").build());
        //assertTrue(select.validate());

        final long animationMs = 800L;

        List<WebComponent> options = select.getOptions2(animationMs);

        assertEquals(3, options.size());
        assertEquals("Ten", options.get(0).getText());
        assertEquals("Twenty", options.get(1).getText());
        assertEquals("Thirty", options.get(2).getText());

        select.selectByValue("10", animationMs);
        assertEquals("Ten", select.getText());
        driver.threadSleep(animationMs);

        options = select.getOptions2(animationMs);
        assertEquals("true", options.get(0).getAttribute("aria-selected"));
        assertFalse(SeleniumUtils.isTrueAttribute(options.get(1), "aria-selected"));
        assertFalse(SeleniumUtils.isTrueAttribute(options.get(2), "aria-selected"));

        select.selectByIndex(1, animationMs);
        assertEquals("Twenty", select.getText());
        driver.threadSleep(animationMs);

        options = select.getOptions2(animationMs);
        assertFalse(SeleniumUtils.isTrueAttribute(options.get(0), "aria-selected"));
        assertEquals("true", options.get(1).getAttribute("aria-selected"));
        assertFalse(SeleniumUtils.isTrueAttribute(options.get(2), "aria-selected"));

        select.selectByVisibleText("Thirty", animationMs);
        assertEquals("Thirty", select.getText());
        driver.threadSleep(animationMs);

        options = select.getOptions2(animationMs);
        assertFalse(SeleniumUtils.isTrueAttribute(options.get(0), "aria-selected"));
        assertFalse(SeleniumUtils.isTrueAttribute(options.get(1), "aria-selected"));
        assertEquals("true", options.get(2).getAttribute("aria-selected"));

        select.closeOptions(animationMs);
    }

    /**
     * Tests other properties
     *
     * @see <a href="https://mui.com/material-ui/react-select/#other-props">
     * https://mui.com/material-ui/react-select/#other-props</a>
     */
    public void testOtherProps() {
        List<MuiSelect> selectList = driver.findComponent(By.id("SelectOtherProps.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiSelect-select"), c -> c.as(muiV5())
                        .toSelect(By.className("MuiMenuItem-root"),
                                builder -> builder.optionValueAttribute("data-value").build()));
        //selectList.forEach(select -> assertTrue(select.validate()));

        assertFalse(selectList.get(0).isEnabled());
    }

    /**
     * Tests for Multiple Select default features.
     *
     * @see <a href="https://mui.com/material-ui/react-select/#multiple-select">
     * https://mui.com/material-ui/react-select/#multiple-select</a>
     */
    public void testMultipleSelectDefault() {
        MuiSelect select = driver.findComponent(By.id("MultipleSelect.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiSelect-select")).as(muiV5()).toSelect(By.className("MuiMenuItem-root"),
                        builder -> builder.multiple(true).optionValueAttribute("data-value").build());
        //assertTrue(select.validate());

        final long animationMs = 1200L;

        select.selectByVisibleText("Omar Alexander", animationMs);
        select.selectByVisibleText("Bradley Wilkerson", animationMs);
        select.selectByValue("Kelly Snyder", animationMs);
        assertEquals("Omar Alexander, Bradley Wilkerson, Kelly Snyder", select.getText());

        select.deselectByVisibleText("Bradley Wilkerson", animationMs);
        assertEquals("Omar Alexander, Kelly Snyder", select.getText());

        select.deselectAll(animationMs);
        assertEquals("", select.getText());

        select.closeOptions(animationMs);
    }

    /**
     * Tests for Multiple Select default checkmarks features. the behaviours should be consistent with default one.
     *
     * @see <a href="https://mui.com/material-ui/react-select/#checkmarks">
     * https://mui.com/material-ui/react-select/#checkmarks</a>
     */
    public void testMultipleSelectCheckmarks() {
        MuiSelect select = driver.findComponent(By.id("MultipleSelectCheckmarks.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiSelect-select")).as(muiV5()).toSelect(By.className("MuiMenuItem-root"),
                        builder -> builder.multiple(true).optionValueAttribute("data-value").build());
        //assertTrue(select.validate());

        final long animationMs = 800L;

        select.selectByVisibleText("Omar Alexander", animationMs);
        select.selectByVisibleText("Bradley Wilkerson", animationMs);
        select.selectByValue("Kelly Snyder", animationMs);
        assertEquals("Omar Alexander, Bradley Wilkerson, Kelly Snyder", select.getText());

        select.deselectByVisibleText("Bradley Wilkerson", animationMs);
        assertEquals("Omar Alexander, Kelly Snyder", select.getText());

        select.deselectAll(animationMs);
        assertEquals("", select.getText());

        select.closeOptions(animationMs);
    }

    /**
     * Tests for Multiple Select default Chip features. as the chip is customized by developer so this framework will
     * not provide additional support for it.
     *
     * @see <a href="https://mui.com/material-ui/react-select/#chip">
     * https://mui.com/material-ui/react-select/#chip</a>
     */
    public void testMultipleSelectChips() {
        MuiSelect select = driver.findComponent(By.id("MultipleSelectChip.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiSelect-select")).as(muiV5()).toSelect(By.className("MuiMenuItem-root"),
                        builder -> builder.multiple(true).optionValueAttribute("data-value").build());
        // assertTrue(select.validate());

        final long animationMs = 800L;

        select.selectByVisibleText("Omar Alexander", animationMs);
        select.selectByVisibleText("Bradley Wilkerson", animationMs);
        select.selectByVisibleText("Kelly Snyder", animationMs);

        // move to next anchor to ensure the selected chip to display on the screen
        driver.moveTo(driver.findComponent(By.id("placeholder")));
        driver.threadSleep(animationMs);
        List<String> selectedValues = select.findComponents(By.className("MuiChip-label")).stream()
                .map(WebElement::getText).collect(toList());
        assertEquals("Omar Alexander, Bradley Wilkerson, Kelly Snyder", join(selectedValues, ", "));
        driver.threadSleep(animationMs);

        select.deselectByVisibleText("Bradley Wilkerson", animationMs);
        driver.threadSleep(animationMs);

        selectedValues = select.findComponents(By.className("MuiChip-label")).stream().map(WebElement::getText)
                .filter(StringUtils::isNotBlank).collect(toList());
        assertEquals("Omar Alexander, Kelly Snyder", join(selectedValues, ", "));

        select.deselectAll(animationMs);
        driver.threadSleep(animationMs);
        assertEquals("", select.getText());

        select.closeOptions(animationMs);
    }

    public static void main(String[] args) {
        MuiSelectTestCases test = new MuiSelectTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-select/");

        test.testBasicSelect();
        test.testOtherProps();
        test.testMultipleSelectDefault();
        test.testMultipleSelectCheckmarks();
        test.testMultipleSelectChips();
    }
}
