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

package com.github.grossopa.selenium.core.util;

import com.github.grossopa.selenium.core.element.TextNodeElement;
import com.github.grossopa.selenium.core.element.TextNodeType;
import com.github.grossopa.selenium.core.element.UnknownTextNodeTypeException;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

/**
 * Tests for {@link SeleniumUtils}
 *
 * @author Jack Yin
 * @since 1.0
 */
class SeleniumUtilsTest {

    @Test
    void testConstructor() {
        boolean asserted = false;
        Constructor<?> constructor = SeleniumUtils.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            assertEquals(AssertionError.class, e.getCause().getClass());
            asserted = true;
        }

        assertTrue(asserted);
    }

    @Test
    void executeIgnoringStaleElementReference() {
        Object result1 = new Object();
        Object result2 = SeleniumUtils.executeIgnoringStaleElementReference(() -> result1, null);
        assertEquals(result1, result2);
    }

    @Test
    void executeIgnoringStaleElementReferenceThrow() {
        Object defaultValue = new Object();
        Object result2 = SeleniumUtils.executeIgnoringStaleElementReference(() -> {
            throw new StaleElementReferenceException("ddd");
        }, defaultValue);
        assertEquals(defaultValue, result2);
    }

    @Test
    void executeIgnoringStaleElementReferenceThrowOther() {
        Object defaultValue = new Object();
        assertThrows(IllegalArgumentException.class, () -> SeleniumUtils.executeIgnoringStaleElementReference(() -> {
            throw new IllegalArgumentException("ddd");
        }, defaultValue));

    }

    @Test
    void cleanText() {
        WebElement element = mock(WebElement.class);
        when(element.getAttribute("value")).thenReturn("aaaaaaaaaa");
        SeleniumUtils.cleanText(element);
        verify(element, times(10)).sendKeys(BACK_SPACE);
    }

    @Test
    void cleanTextEmpty() {
        WebElement element = mock(WebElement.class);
        when(element.getAttribute("value")).thenReturn("");
        SeleniumUtils.cleanText(element);
        verify(element, never()).sendKeys(BACK_SPACE);
    }

    @Test
    void isNotDisplayedNotDisplayed() {
        WebElement element = mock(WebElement.class);
        when(element.isDisplayed()).thenReturn(false);
        assertTrue(SeleniumUtils.isNotDisplayed(element));
    }

    @Test
    void isNotDisplayedNull() {
        assertTrue(SeleniumUtils.isNotDisplayed(null));
    }

    @Test
    void isNotDisplayedStale() {
        WebElement element = mock(WebElement.class);
        when(element.isDisplayed()).thenThrow(new StaleElementReferenceException(""));
        assertTrue(SeleniumUtils.isNotDisplayed(element));
    }

    @Test
    void isNotDisplayedFalse() {
        WebElement element = mock(WebElement.class);
        when(element.isDisplayed()).thenReturn(true);
        assertFalse(SeleniumUtils.isNotDisplayed(element));
    }

    JavascriptExecutor driver = mock(JavascriptExecutor.class);
    WebElement element = mock(WebElement.class);
    List<Object> childNodesResult = newArrayList();

    @Test
    void findChildNodes() {
        //@formatter:off
        when(driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({nodeName:nodes[i].nodeName, nodeType:nodes[i].nodeType, "
                + "nodeValue:nodes[i].nodeValue, textContent:nodes[i].textContent, "
                + "wholeText:nodes[i].wholeText, data:nodes[i].data});"
                + "  } else {"
                + "    result.push(nodes[i]);"
                + "  }"
                + "}"
                + "return result;", element)).thenReturn(childNodesResult);
        //@formatter:on

        List<Object> executionResult = SeleniumUtils.findChildNodes(driver, element);
        assertEquals(0, executionResult.size());
        assertSame(childNodesResult, executionResult);
    }

    @Test
    void findChildNodesWithProperties() {
        //@formatter:off
        when(driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({property1:nodes[i].property1, property2:nodes[i].property2});"
                + "  } else {"
                + "    result.push(nodes[i]);"
                + "  }"
                + "}"
                + "return result;", element)).thenReturn(childNodesResult);
        //@formatter:on

        List<Object> executionResult = SeleniumUtils.findChildNodes(driver, element, "property1", "property2");
        assertEquals(0, executionResult.size());
        assertSame(childNodesResult, executionResult);
    }

    @Test
    void findChildTextNodes() {
        //@formatter:off
        when(driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({nodeName:nodes[i].nodeName, nodeType:nodes[i].nodeType, "
                + "nodeValue:nodes[i].nodeValue, textContent:nodes[i].textContent, "
                + "wholeText:nodes[i].wholeText, data:nodes[i].data});"
                + "  }"
                + "}"
                + "return result;", element)).thenReturn(childNodesResult);
        //@formatter:on

        Map<String, Object> map1 = ImmutableMap.of("nodeName", "#text", "nodeType", 3, "nodeValue", "some value",
                "textContent", "some value", "wholeText", "some value", "data", "some value");

        Map<String, Object> map2 = ImmutableMap.of("nodeName", "#comment", "nodeType", 8, "nodeValue", "some comment\n",
                "textContent", "some comment\n", "wholeText", "some comment\n", "data", "some comment\n");

        childNodesResult.add(map1);
        childNodesResult.add(map2);

        List<TextNodeElement> textNodeElements = SeleniumUtils.findChildTextNodes(driver, element);
        assertEquals(TextNodeType.TEXT, textNodeElements.get(0).getType());
        assertEquals("some value", textNodeElements.get(0).getText());

        assertEquals(TextNodeType.COMMENT, textNodeElements.get(1).getType());
        assertEquals("some comment", textNodeElements.get(1).getText());
    }

    @Test
    void findChildTextNodesNoStrip() {
        //@formatter:off
        when(driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({nodeName:nodes[i].nodeName, nodeType:nodes[i].nodeType, "
                + "nodeValue:nodes[i].nodeValue, textContent:nodes[i].textContent, "
                + "wholeText:nodes[i].wholeText, data:nodes[i].data});"
                + "  }"
                + "}"
                + "return result;", element)).thenReturn(childNodesResult);
        //@formatter:on

        Map<String, Object> map1 = ImmutableMap.of("nodeName", "#text", "nodeType", 3, "nodeValue", "some value",
                "textContent", "some value", "wholeText", "some value", "data", "some value");

        Map<String, Object> map2 = ImmutableMap.of("nodeName", "#comment", "nodeType", 8, "nodeValue", "some comment\n",
                "textContent", "some comment\n", "wholeText", "some comment\n", "data", "some comment\n");

        childNodesResult.add(map1);
        childNodesResult.add(map2);

        List<TextNodeElement> textNodeElements = SeleniumUtils.findChildTextNodes(driver, element, false);
        assertEquals(TextNodeType.TEXT, textNodeElements.get(0).getType());
        assertEquals("some value", textNodeElements.get(0).getText());

        assertEquals(TextNodeType.COMMENT, textNodeElements.get(1).getType());
        assertEquals("some comment\n", textNodeElements.get(1).getText());
    }

    @Test
    void findChildTextNodesSkip() {
        //@formatter:off
        when(driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({nodeName:nodes[i].nodeName, nodeType:nodes[i].nodeType, "
                + "nodeValue:nodes[i].nodeValue, textContent:nodes[i].textContent, "
                + "wholeText:nodes[i].wholeText, data:nodes[i].data});"
                + "  }"
                + "}"
                + "return result;", element)).thenReturn(childNodesResult);
        //@formatter:on

        Map<String, Object> map1 = ImmutableMap.of("nodeName", "#text", "nodeType", 3, "nodeValue", "some value",
                "textContent", "some value", "wholeText", "some value", "data", "some value");

        Map<String, Object> map2 = ImmutableMap.of("nodeName", "#comment", "nodeType", 8, "nodeValue", "some comment\n",
                "textContent", "some comment\n", "wholeText", "some comment\n", "data", "some comment\n");

        WebElement ignoredElement = mock(WebElement.class);

        childNodesResult.add(ignoredElement);
        childNodesResult.add(map1);
        childNodesResult.add(map2);

        List<TextNodeElement> textNodeElements = SeleniumUtils.findChildTextNodes(driver, element);

        assertEquals(2, textNodeElements.size());
        assertEquals(TextNodeType.TEXT, textNodeElements.get(0).getType());
        assertEquals("some value", textNodeElements.get(0).getText());

        assertEquals(TextNodeType.COMMENT, textNodeElements.get(1).getType());
        assertEquals("some comment", textNodeElements.get(1).getText());
    }

    @Test
    void findChildTextNodesIllegalNodes() {
        //@formatter:off
        when(driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({nodeName:nodes[i].nodeName, nodeType:nodes[i].nodeType, "
                + "nodeValue:nodes[i].nodeValue, textContent:nodes[i].textContent, "
                + "wholeText:nodes[i].wholeText, data:nodes[i].data});"
                + "  }"
                + "}"
                + "return result;", element)).thenReturn(childNodesResult);
        //@formatter:on

        Map<String, Object> map1 = ImmutableMap.of("nodeName", "#text333", "nodeType", 3, "nodeValue", "some value",
                "textContent", "some value", "wholeText", "some value", "data", "some value");
        childNodesResult.add(map1);

        assertThrows(UnknownTextNodeTypeException.class, () -> SeleniumUtils.findChildTextNodes(driver, element));
    }

    @Test
    void findChildTextNodesIllegalNodesStrip() {
        //@formatter:off
        when(driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({nodeName:nodes[i].nodeName, nodeType:nodes[i].nodeType, "
                + "nodeValue:nodes[i].nodeValue, textContent:nodes[i].textContent, "
                + "wholeText:nodes[i].wholeText, data:nodes[i].data});"
                + "  }"
                + "}"
                + "return result;", element)).thenReturn(childNodesResult);
        //@formatter:on

        Map<String, Object> map1 = ImmutableMap.of("nodeName", "#text333", "nodeType", 3, "nodeValue", "some value",
                "textContent", "some value", "wholeText", "some value", "data", "some value");
        childNodesResult.add(map1);

        assertThrows(UnknownTextNodeTypeException.class, () -> SeleniumUtils.findChildTextNodes(driver, element));
    }

    @Test
    void findChildTextNodesEmptyValue() {
        //@formatter:off
        when(driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({nodeName:nodes[i].nodeName, nodeType:nodes[i].nodeType, "
                + "nodeValue:nodes[i].nodeValue, textContent:nodes[i].textContent, "
                + "wholeText:nodes[i].wholeText, data:nodes[i].data});"
                + "  }"
                + "}"
                + "return result;", element)).thenReturn(childNodesResult);
        //@formatter:on

        Map<String, Object> map1 = ImmutableMap.of("nodeName", "#text", "nodeType", 3);
        childNodesResult.add(map1);

        List<TextNodeElement> textNodeElements = SeleniumUtils.findChildTextNodes(driver, element);
        assertEquals("", textNodeElements.get(0).getText());
    }

    @Test
    void enrichQuoteSingle() {
        assertEquals("'ddd\"ddd'", SeleniumUtils.enrichQuote("ddd\"ddd"));
    }

    @Test
    void enrichQuoteDouble() {
        assertEquals("\"ddd'ddd\"", SeleniumUtils.enrichQuote("ddd'ddd"));
    }
}
