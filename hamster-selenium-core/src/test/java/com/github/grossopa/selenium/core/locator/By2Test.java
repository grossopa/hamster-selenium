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

package com.github.grossopa.selenium.core.locator;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link By2}
 *
 * @author Jack Yin
 * @since 1.0
 */
class By2Test {

    @Test
    void constructor() {
        assertEquals(By2.class, new By2() {

            @Override
            public List<WebElement> findElements(SearchContext context) {
                return null;
            }
        }.getClass().getSuperclass());
    }

    @Test
    void id() {
        By result = By2.id("some-id");
        assertEquals("By.id: some-id", result.toString());
    }

    @Test
    void linkText() {
        By result = By2.linkText("some-link-text");
        assertEquals("By.linkText: some-link-text", result.toString());
    }

    @Test
    void partialLinkText() {
        By result = By2.partialLinkText("some-partial-link-text");
        assertEquals("By.partialLinkText: some-partial-link-text", result.toString());
    }

    @Test
    void name() {
        By result = By2.name("some-name");
        assertEquals("By.name: some-name", result.toString());
    }

    @Test
    void tagName() {
        By result = By2.tagName("some-tag-name");
        assertEquals("By.tagName: some-tag-name", result.toString());
    }

    @Test
    void xpath() {
        By result = By2.xpath("/div/div/span");
        assertEquals("By.xpath: /div/div/span", result.toString());
    }

    @Test
    void className() {
        By result = By2.className("some-class-name");
        assertEquals("By.className: some-class-name", result.toString());
    }

    @Test
    void cssSelector() {
        By result = By2.cssSelector("some-css");
        assertEquals("By.cssSelector: some-css", result.toString());
    }

    @Test
    void exact() {
        By result = By2.attrExact("some-name", "some-attr");
        assertEquals("By.xpath: .//*[@some-name=\"some-attr\"]", result.toString());
    }

    @Test
    void exactWithTag() {
        By result = By2.attrExact("some-name", "some-attr", "div");
        assertEquals("By.xpath: .//div[@some-name=\"some-attr\"]", result.toString());
    }

    @Test
    void contains() {
        By result = By2.attrContains("some-name", "some-attr");
        assertEquals("By.xpath: .//*[contains(@some-name,\"some-attr\")]", result.toString());
    }

    @Test
    void containsWithTag() {
        By result = By2.attrContains("some-name", "some-attr", "div");
        assertEquals("By.xpath: .//div[contains(@some-name,\"some-attr\")]", result.toString());
    }

    @Test
    void attrContains() {
        By result = By2.attrContains("some-name", "some-attr");
        assertEquals("By.xpath: .//*[contains(@some-name,\"some-attr\")]", result.toString());
    }

    @Test
    void textContains() {
        By result = By2.textContains("some-test ''sbc");
        assertEquals("By.xpath: .//*[contains(text(),\"some-test ''sbc\")]", result.toString());
    }

    @Test
    void textExact() {
        By result = By2.textExact("some-test ''sbc");
        assertEquals("By.xpath: .//*[text()=\"some-test ''sbc\"]", result.toString());
    }

    @Test
    void parent() {
        By result = By2.parent();
        assertEquals("By.xpath: parent::*", result.toString());
    }

    @Test
    void axesBuilder() {
        By result = By2.axesBuilder().parent().build();
        assertEquals("By.xpath: ./parent::*", result.toString());
    }

    @Test
    void visible() {
        By baseLocator = By.tagName("div");
        By result = By2.visible(baseLocator);
        assertEquals("By.xpath: .//*[local-name()='div' and not(@hidden) and not(contains(@style,'display:none'))]", result.toString());
    }

    @Test
    void enabled() {
        By baseLocator = By.tagName("input");
        By result = By2.enabled(baseLocator);
        assertEquals("By.xpath: .//*[local-name()='input' and not(@disabled)]", result.toString());
    }

    @Test
    void selected() {
        By baseLocator = By.tagName("option");
        By result = By2.selected(baseLocator);
        assertEquals("By.xpath: .//*[local-name()='option' and (@selected or @checked)]", result.toString());
    }

    @Test
    void index() {
        By baseLocator = By.tagName("li");
        By result = By2.index(baseLocator, 2);
        assertEquals("By.xpath: .//*[local-name()='li'][3]", result.toString());
    }

    @Test
    void cssPropertyValue() {
        By baseLocator = By.className("text");
        By result = By2.cssPropertyValue(baseLocator, "color", "red");
        assertEquals("By.xpath: .//*[contains(@class, 'text') and contains(@style, 'color:red')]", result.toString());
    }

    @Test
    void and() {
        By locator1 = By.tagName("div");
        By locator2 = By.className("container");
        By result = By2.and(locator1, locator2);
        assertEquals("By.xpath: .//*[local-name()='div' and contains(@class, 'container')]", result.toString());
    }

    @Test
    void andWithSingleLocator() {
        By locator1 = By.tagName("div");
        By result = By2.and(locator1);
        assertEquals("By.xpath: .//*[local-name()='div']", result.toString());
    }

    @Test
    void andWithNoLocators() {
        assertThrows(IllegalArgumentException.class, () -> By2.and());
    }

    @Test
    void xpathFromById() {
        By baseLocator = By.id("test-id");
        By result = By2.visible(baseLocator);
        assertEquals("By.xpath: .*[@id='test-id' and not(@hidden) and not(contains(@style,'display:none'))]", result.toString());
    }

    @Test
    void xpathFromByClassName() {
        By baseLocator = By.className("test-class");
        By result = By2.enabled(baseLocator);
        assertEquals("By.xpath: .*[contains(@class, 'test-class') and not(@disabled)]", result.toString());
    }

    @Test
    void xpathFromByName() {
        By baseLocator = By.name("test-name");
        By result = By2.selected(baseLocator);
        assertEquals("By.xpath: .*[@name='test-name' and (@selected or @checked)]", result.toString());
    }

    @Test
    void xpathFromByLinkText() {
        By baseLocator = By.linkText("Click me");
        By result = By2.index(baseLocator, 0);
        assertEquals("By.xpath: .*[text()='Click me'][1]", result.toString());
    }

    @Test
    void xpathFromByPartialLinkText() {
        By baseLocator = By.partialLinkText("Click");
        By result = By2.index(baseLocator, 0);
        assertEquals("By.xpath: .*[contains(text(), 'Click')][1]", result.toString());
    }
}