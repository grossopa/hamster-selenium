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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.openqa.selenium.Keys.BACK_SPACE;

/**
 * The Selenium framework utils
 *
 * @author Jack Yin
 * @since 1.1
 */
public class SeleniumUtils {

    /**
     * private constructor
     */
    private SeleniumUtils() {
        throw new AssertionError();
    }

    /**
     * Executes a method with allowance of throwing the {@link StaleElementReferenceException}.
     *
     * @param <T> the return type
     * @param function the function to execute, if {@link StaleElementReferenceException} is thrown then will return the
     * default Value.
     * @param defaultValue the default value to return
     * @return result or defaultValue if function throws {@link StaleElementReferenceException}
     */
    @Nullable
    public static <T> T executeIgnoringStaleElementReference(Supplier<T> function, T defaultValue) {
        try {
            return function.get();
        } catch (StaleElementReferenceException exception) {
            return defaultValue;
        }
    }

    /**
     * Safely determines whether a given element is displayed.
     *
     * <ul>
     *   <li>element is not displayed.</li>
     *   <li>element is stale.</li>
     *   <li>element is null.</li>
     * </ul>
     *
     * @param element the nullable to check
     * @return true if the element is not displayed.
     */
    public static boolean isNotDisplayed(@Nullable WebElement element) {
        try {
            return element == null || !element.isDisplayed();
        } catch (StaleElementReferenceException exception) {
            return true;
        }
    }

    /**
     * Cleans the text of an input element by simulating to press backspace key.
     *
     * @param inputElement the input element to clean
     * @since 1.3
     */
    @SuppressWarnings("java:S6212")
    public static void cleanText(WebElement inputElement) {
        String text = inputElement.getDomAttribute("value");
        for (int i = 0; i < text.length(); i++) {
            inputElement.sendKeys(BACK_SPACE);
        }
    }

    /**
     * Wraps the term with single or double quotes for xpath query. The function most likely will NOT work if the term
     * contains both single and double quotes.
     *
     * @param term the term to be wrapped
     * @return the wrapped result
     */
    public static String enrichQuote(String term) {
        if (contains(term, '\"')) {
            return '\'' + term + '\'';
        } else {
            return '\"' + term + '\"';
        }
    }

    /**
     * Gets the child text contents, including elements, texts and comments.
     *
     * <p>
     * {@code
     * <div id="the-parent-element">
     *   <!-- comment -->
     *   SOME TEXT
     *   <span id="child-span">some child span text</span>
     *   some other text
     * </div>
     * }</p>
     *
     * @param driver the web driver
     * @param element the parent element to find the child node in
     * @param textNodeProperties text node and comment node including properties
     * @return found result
     */
    @SuppressWarnings("unchecked")
    public static List<Object> findChildNodes(JavascriptExecutor driver, WebElement element,
            String... textNodeProperties) {
        if (textNodeProperties.length == 0) {
            textNodeProperties = TEXT_NODE_PROPERTIES;
        }
        String propertyString = stream(textNodeProperties).map(p -> p + ":nodes[i]." + p).collect(joining(", "));
        //@formatter:off
        return (List<Object>) driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({" + propertyString + "});"
                + "  } else {"
                + "    result.push(nodes[i]);"
                + "  }"
                + "}"
                + "return result;", element);
        //@formatter:on
    }

    /**
     * Finds the child text and comment nodes. the text will be stripped to remove the potential leading and tailing
     * line breaks.
     *
     * @param driver the root driver to execute the scripts
     * @param element the element to find child text nodes from
     * @return the found result
     */
    public static List<TextNodeElement> findChildTextNodes(JavascriptExecutor driver, WebElement element) {
        return findChildTextNodes(driver, element, true);
    }

    /**
     * Finds the child text and comment nodes.
     *
     * @param driver the root driver to execute the scripts
     * @param element the element to find child text nodes from
     * @param stripText true to strip the text to remove the potential leading and tailing line breaks
     * @return the found result
     */
    @SuppressWarnings("unchecked")
    public static List<TextNodeElement> findChildTextNodes(JavascriptExecutor driver, WebElement element,
            boolean stripText) {
        String propertyString = stream(TEXT_NODE_PROPERTIES).map(p -> p + ":nodes[i]." + p).collect(joining(", "));

        //@formatter:off
        List<Object> lists = (List<Object>) driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({" + propertyString + "});"
                + "  }"
                + "}"
                + "return result;", element);
        //@formatter:on

        List<TextNodeElement> results = new ArrayList<>();

        for (Object item : lists) {
            if (item instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) item;
                TextNodeType textNodeType = findTextNodeType(map);
                String textNodeText = findTextNodeText(map);

                if (stripText) {
                    textNodeText = textNodeText.strip();
                }

                results.add(new TextNodeElement(textNodeType, textNodeText));
            }
        }
        return results;
    }

    private static final String[] TEXT_NODE_PROPERTIES = new String[]{"nodeName", "nodeType", "nodeValue",
            "textContent", "wholeText", "data"};

    private static TextNodeType findTextNodeType(Map<String, Object> map) {
        String nodeTypeString = (String) map.get("nodeName");
        TextNodeType nodeType;
        if ("#comment".equals(nodeTypeString)) {
            nodeType = TextNodeType.COMMENT;
        } else if ("#text".equals(nodeTypeString)) {
            nodeType = TextNodeType.TEXT;
        } else {
            throw new UnknownTextNodeTypeException(nodeTypeString);
        }
        return nodeType;
    }

    private static String findTextNodeText(Map<String, Object> map) {
        // following the order by best property to find the text from
        String[] candidateProperties = new String[]{"data", "nodeValue", "textContent", "wholeText"};
        for (String property : candidateProperties) {
            String value = (String) map.get(property);
            if (!isBlank(value)) {
                return value;
            }
        }
        return "";
    }

    /**
     * This method is helpful when there are multiple different values for same meaning of attribute especially for
     * boolean attributes below.
     * <p>
     * async, autofocus, autoplay, checked, compact, complete, controls, declare, defaultchecked, defaultselected,
     * defer, disabled, draggable, ended, formnovalidate, hidden, indeterminate, iscontenteditable, ismap, itemscope,
     * loop, multiple, muted, nohref, noresize, noshade, novalidate, nowrap, open, paused, pubdate, readonly, required,
     * reversed, scoped, seamless, seeking, selected, truespeed, willvalidate
     * </p>
     *
     * @param element the element to find the attribute from
     * @param attributeName the attribute to find from the element
     * @return true if the value is not blank and not false
     */
    public static boolean isTrueAttribute(WebElement element, String attributeName) {
        String value = element.getDomAttribute(attributeName);
        return !("false".equalsIgnoreCase(value) || isBlank(value));
    }

}
