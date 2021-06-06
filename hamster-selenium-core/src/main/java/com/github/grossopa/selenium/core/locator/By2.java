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

package com.github.grossopa.selenium.core.locator;

import org.openqa.selenium.By;

/**
 * Delegates the {@link By} static methods and with additional helper methods
 *
 * @author Jack Yin
 * @since 1.0
 */
public abstract class By2 extends By {

    /**
     * Finds by id.
     *
     * @param id The value of the "id" attribute to search for.
     * @return A By which locates elements by the value of the "id" attribute.
     */
    public static By id(String id) {
        return By.id(id);
    }

    /**
     * Finds by link text.
     *
     * @param linkText The exact text to match against.
     * @return A By which locates A elements by the exact text it displays.
     */
    public static By linkText(String linkText) {
        return By.linkText(linkText);
    }

    /**
     * Finds by partial link text.
     *
     * @param partialLinkText The partial text to match against
     * @return a By which locates elements that contain the given link text.
     */
    public static By partialLinkText(String partialLinkText) {
        return By.partialLinkText(partialLinkText);
    }

    /**
     * Finds by attribute "name".
     *
     * @param name The value of the "name" attribute to search for.
     * @return A By which locates elements by the value of the "name" attribute.
     */
    public static By name(String name) {
        return By.name(name);
    }

    /**
     * Finds by the div tag name.
     *
     * @param tagName The element's tag name.
     * @return A By which locates elements by their tag name.
     */
    public static By tagName(String tagName) {
        return By.tagName(tagName);
    }

    /**
     * Finds by XPath.
     *
     * @param xpathExpression The XPath to use.
     * @return A By which locates elements via XPath.
     */
    public static By xpath(String xpathExpression) {
        return By.xpath(xpathExpression);
    }

    /**
     * Finds elements based on the value of the "class" attribute. If an element has multiple classes, then this will
     * match against each of them. For example, if the value is "one two onone", then the class names "one" and "two"
     * will match.
     *
     * @param className The value of the "class" attribute to search for.
     * @return A By which locates elements by the value of the "class" attribute.
     */
    public static By className(String className) {
        return By.className(className);
    }

    /**
     * Finds elements via the driver's underlying W3C Selector engine. If the browser does not implement the Selector
     * API, a best effort is made to emulate the API. In this case, we strive for at least CSS2 support, but offer no
     * guarantees.
     *
     * @param cssSelector CSS expression.
     * @return A By which locates elements by CSS.
     */
    public static By cssSelector(String cssSelector) {
        return By.cssSelector(cssSelector);
    }

    /**
     * Finds elements by exact attribute value match.
     *
     * @param attributeName the attribute name to find
     * @param attributeValue the attribute value to find
     * @return A By which locates elements by exact attribute value match.
     */
    public static By attrExact(String attributeName, String attributeValue) {
        return attrExact(attributeName, attributeValue, "*");
    }

    /**
     * Finds elements by exact attribute value and tag name match
     *
     * @param attributeName the attribute name to find
     * @param attributeValue the attribute value to find
     * @param tag the tag name to find
     * @return A By which locates elements by exact attribute value and tag name match.
     */
    public static By attrExact(String attributeName, String attributeValue, String tag) {
        return xpathBuilder().anywhereRelative(tag).attr(attributeName).exact(attributeValue).build();
    }

    /**
     * Finds elements by contains attribute value
     *
     * @param attributeName the attribute name to find
     * @param attributeValue the attribute value to find
     * @return A By which locates elements by contains attribute value
     */
    public static By attrContains(String attributeName, String attributeValue) {
        return attrContains(attributeName, attributeValue, "*");
    }

    /**
     * Finds elements by contains attribute value and tag match
     *
     * @param attributeName the attribute name to find
     * @param attributeValue the attribute value to find
     * @param tag the tag name to find
     * @return A By which locates elements by contains attribute value and tag name match.
     */
    public static By attrContains(String attributeName, String attributeValue, String tag) {
        return xpathBuilder().anywhereRelative(tag).attr(attributeName).contains(attributeValue).build();
    }

    /**
     * Starts to build xpath.
     *
     * @return the prefix builder to start the building
     */
    public static SimpleXpathBuilder.PrefixBuilder xpathBuilder() {
        return new SimpleXpathBuilder.PrefixBuilder();
    }

    /**
     * Finds the element by text contains match and anywhere relative to the current element.
     *
     * @param text the text to find that contains
     * @return A By which locates the elements by xpath {@code .//*[contains(text(), the_text)]}
     */
    public static By textContains(String text) {
        return xpathBuilder().anywhereRelative().text().contains(text).build();
    }

    /**
     * Finds the element by text exact match and anywhere relative to the current element.
     *
     * @param text the text to find
     * @return A By which locates the elements by xpath {@code .//*[text()="%s"]}
     */
    public static By textExact(String text) {
        return xpathBuilder().anywhereRelative().text().exact(text).build();
    }

    /**
     * Finds the direct parent element by xpath {@code "parent::*"}.
     *
     * @return A By which locates elements by xpath {@code "parent::*"}
     */
    public static By parent() {
        return By.xpath("parent::*");
    }
}
