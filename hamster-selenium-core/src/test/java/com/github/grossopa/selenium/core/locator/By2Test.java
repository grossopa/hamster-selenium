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

package com.github.grossopa.selenium.core.locator;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        By result = By2.exact("some-name", "some-attr");
        assertEquals("By.xpath: *[@some-name='some-attr']", result.toString());
    }

    @Test
    void exactWithTag() {
        By result = By2.exact("some-name", "some-attr", "div");
        assertEquals("By.xpath: div[@some-name='some-attr']", result.toString());
    }

    @Test
    void contains() {
        By result = By2.contains("some-name", "some-attr");
        assertEquals("By.xpath: *[contains(@some-name, 'some-attr')]", result.toString());
    }

    @Test
    void containsWithTag() {
        By result = By2.contains("some-name", "some-attr", "div");
        assertEquals("By.xpath: div[contains(@some-name, 'some-attr')]", result.toString());
    }

    @Test
    void attr() {
        By result = By2.attr("some-name", "some-attr").build();
        assertEquals("By.xpath: *[@some-name='some-attr']", result.toString());
    }

    @Test
    void attrWithTag() {
        By result = By2.attr("some-name", "some-attr").tag("span").build();
        assertEquals("By.xpath: span[@some-name='some-attr']", result.toString());
    }

    @Test
    void attrWithAnyDepthAbsolute() {
        By result = By2.attr("some-name", "some-attr").anyDepthAbsolute().build();
        assertEquals("By.xpath: //*[@some-name='some-attr']", result.toString());
    }

    @Test
    void attrWithAnyDepthChild() {
        By result = By2.attr("some-name", "some-attr").anyDepthChild().build();
        assertEquals("By.xpath: .//*[@some-name='some-attr']", result.toString());
    }

    @Test
    void attrWithAnyDepthRelative() {
        By result = By2.attr("some-name", "some-attr").depthRelative().build();
        assertEquals("By.xpath: *[@some-name='some-attr']", result.toString());
    }

    @Test
    void attrContains() {
        By result = By2.attr("some-name", "some-attr").contains().build();
        assertEquals("By.xpath: *[contains(@some-name, 'some-attr')]", result.toString());
    }

    @Test
    void attrExact() {
        By result = By2.attr("some-name", "some-attr").exact().build();
        assertEquals("By.xpath: *[@some-name='some-attr']", result.toString());
    }

    @Test
    void attrFull() {
        By result = By2.attr("some-name", "some-attr").contains().anyDepthChild().tag("span").build();
        assertEquals("By.xpath: .//span[contains(@some-name, 'some-attr')]", result.toString());
    }

    @Test
    void textContains() {
        By result = By2.textContains("some-test ''sbc");
        assertEquals("By.xpath: .//*[contains(text(), 'some-test \\'\\'sbc')]", result.toString());
    }

    @Test
    void textExact() {
        By result = By2.textExact("some-test ''sbc");
        assertEquals("By.xpath: .//*[text()='some-test \\'\\'sbc']", result.toString());
    }

    @Test
    void parent() {
        By result = By2.parent();
        assertEquals("By.xpath: parent::*", result.toString());
    }
}
