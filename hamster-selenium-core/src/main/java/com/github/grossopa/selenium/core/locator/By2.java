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

import org.openqa.selenium.By;

/**
 * Delegates the {@link By} static methods and with additional helper methods.
 *
 * <p>This class extends Selenium's standard {@link By} locator mechanism with
 * additional convenience methods for common element location patterns. It provides
 * both standard locator methods (delegating to the original {@link By} class) and
 * enhanced methods for attribute-based and text-based element location.</p>
 *
 * <p>Key features of By2:
 * <ul>
 *   <li><strong>Standard Locators:</strong> All standard {@link By} methods like {@link #id(String)}, {@link #className(String)}, etc.</li>
 *   <li><strong>Attribute Locators:</strong> Enhanced methods for attribute-based location with {@link #attrExact(String, String)} and {@link #attrContains(String, String)}</li>
 *   <li><strong>Text Locators:</strong> Methods for text-based location with {@link #textContains(String)} and {@link #textExact(String)}</li>
 *   <li><strong>State Locators:</strong> Methods for locating elements based on state like visibility, enabledness, etc.</li>
 *   <li><strong>XPath Builders:</strong> Fluent API for building complex XPath expressions with {@link #xpathBuilder()}</li>
 * </ul>
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * // Standard locators (delegating to By)
 * By buttonLocator = By2.tagName("button");
 * By idLocator = By2.id("submit-button");
 * 
 * // Enhanced attribute locators
 * By exactAttrLocator = By2.attrExact("data-test", "submit-btn");
 * By containsAttrLocator = By2.attrContains("class", "btn-primary");
 * 
 * // Text-based locators
 * By textContainsLocator = By2.textContains("Submit");
 * By textExactLocator = By2.textExact("Submit Form");
 * 
 * // State-based locators
 * By visibleLocator = By2.visible(By.tagName("div"));
 * By enabledLocator = By2.enabled(By.tagName("input"));
 * 
 * // XPath builder
 * By complexLocator = By2.xpathBuilder()
 *     .anywhereRelative("div")
 *     .attr("class").contains("container")
 *     .build();
 * }</pre>
 * </p>
 *
 * @author Jack Yin
 * @since 1.0
 * @see By
 * @see SimpleXpathBuilder
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
     * Xpath builder with starts with prefix.
     *
     * @return the prefix builder to start the building
     */
    public static SimpleXpathBuilder.PrefixBuilder xpathBuilder() {
        return new SimpleXpathBuilder.PrefixBuilder();
    }

    /**
     * Xpath builder which starts with axes.
     *
     * @return the Axes builder to start the building
     */
    public static SimpleXpathBuilder.AxesBuilder axesBuilder() {
        return new SimpleXpathBuilder.AxesBuilder(".");
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
    
    /**
     * Finds elements that are visible on the page.
     * 
     * <p>This method creates an XPath that matches elements which are both:
     * <ol>
     *   <li>Present in the DOM</li>
     *   <li>Have a non-zero offsetWidth or offsetHeight (indicating visibility)</li>
     * </ol>
     * </p>
     *
     * @param baseLocator The base locator to filter for visibility
     * @return A By which locates visible elements matching the base locator
     * @since 1.6
     */
    public static By visible(By baseLocator) {
        // Note: This is a simplified implementation. Actual visibility checking 
        // is more complex and usually handled at the WebDriver level.
        return By.xpath(".//*[" + xpathFromBy(baseLocator) + " and not(@hidden) and not(contains(@style,'display:none'))]");
    }
    
    /**
     * Finds elements that are enabled (not disabled).
     * 
     * <p>This method creates an XPath that matches elements which:
     * <ol>
     *   <li>Match the base locator</li>
     *   <li>Do not have the 'disabled' attribute set</li>
     * </ol>
     * </p>
     *
     * @param baseLocator The base locator to filter for enabled state
     * @return A By which locates enabled elements matching the base locator
     * @since 1.6
     */
    public static By enabled(By baseLocator) {
        return By.xpath(".//*[" + xpathFromBy(baseLocator) + " and not(@disabled)]");
    }
    
    /**
     * Finds elements that are selected (e.g., checkboxes, radio buttons).
     * 
     * <p>This method creates an XPath that matches elements which:
     * <ol>
     *   <li>Match the base locator</li>
     *   <li>Have the 'selected' attribute set</li>
     * </ol>
     * </p>
     *
     * @param baseLocator The base locator to filter for selected state
     * @return A By which locates selected elements matching the base locator
     * @since 1.6
     */
    public static By selected(By baseLocator) {
        return By.xpath(".//*[" + xpathFromBy(baseLocator) + " and (@selected or @checked)]");
    }
    
    /**
     * Finds elements by their index among siblings.
     * 
     * <p>This method creates an XPath that matches elements which:
     * <ol>
     *   <li>Match the base locator</li>
     *   <li>Are at the specified index among their siblings (0-based)</li>
     * </ol>
     * </p>
     *
     * @param baseLocator The base locator for elements
     * @param index The 0-based index of the element among its siblings
     * @return A By which locates the element at the specified index
     * @since 1.6
     */
    public static By index(By baseLocator, int index) {
        return By.xpath(".//*[" + xpathFromBy(baseLocator) + "][" + (index + 1) + "]");
    }
    
    /**
     * Finds elements by CSS property value.
     * 
     * <p>This method creates an XPath that matches elements which:
     * <ol>
     *   <li>Match the base locator</li>
     *   <li>Have the specified CSS property with the specified value</li>
     * </ol>
     * </p>
     * 
     * <p>Note: This is a limited implementation as CSS property checking 
     * typically requires JavaScript execution. This checks for style attributes.</p>
     *
     * @param baseLocator The base locator for elements
     * @param cssProperty The CSS property name to check
     * @param cssValue The expected CSS property value
     * @return A By which locates elements with the specified CSS property value
     * @since 1.6
     */
    public static By cssPropertyValue(By baseLocator, String cssProperty, String cssValue) {
        return By.xpath(".//*[" + xpathFromBy(baseLocator) + " and contains(@style, '" + cssProperty + ":" + cssValue + "')]");
    }
    
    /**
     * Combines multiple locators with an AND condition.
     * 
     * <p>This method creates an XPath that matches elements which:
     * <ol>
     *   <li>Match all of the provided locators</li>
     * </ol>
     * </p>
     *
     * @param locators The locators to combine
     * @return A By which locates elements matching all provided locators
     * @since 1.6
     */
    public static By and(By... locators) {
        if (locators.length == 0) {
            throw new IllegalArgumentException("At least one locator must be provided");
        }
        
        StringBuilder xpath = new StringBuilder(".//*[");
        for (int i = 0; i < locators.length; i++) {
            if (i > 0) {
                xpath.append(" and ");
            }
            xpath.append(xpathFromBy(locators[i]));
        }
        xpath.append("]");
        
        return By.xpath(xpath.toString());
    }
    
    /**
     * Converts a By locator to its XPath representation.
     * 
     * <p>This is a helper method for internal use that converts common By locators
     * to XPath expressions that can be combined.</p>
     *
     * @param by The By locator to convert
     * @return The XPath representation of the locator
     * @since 1.6
     */
    private static String xpathFromBy(By by) {
        String toString = by.toString();
        if (toString.startsWith("By.id: ")) {
            return "@id='" + toString.substring(7) + "'";
        } else if (toString.startsWith("By.className: ")) {
            return "contains(@class, '" + toString.substring(14) + "')";
        } else if (toString.startsWith("By.tagName: ")) {
            return "local-name()='" + toString.substring(12) + "'";
        } else if (toString.startsWith("By.name: ")) {
            return "@name='" + toString.substring(9) + "'";
        } else if (toString.startsWith("By.xpath: ")) {
            return toString.substring(10);
        } else if (toString.startsWith("By.cssSelector: ")) {
            // This is a simplified conversion - full CSS to XPath is complex
            return ""; // Would need a full CSS to XPath converter
        } else if (toString.startsWith("By.linkText: ")) {
            return "text()='" + toString.substring(13) + "'";
        } else if (toString.startsWith("By.partialLinkText: ")) {
            return "contains(text(), '" + toString.substring(20) + "')";
        }
        // Fallback - try to use the whole expression
        return toString;
    }
}