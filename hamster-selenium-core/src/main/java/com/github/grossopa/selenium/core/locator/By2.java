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

import org.openqa.selenium.By;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Delegates the {@link By} static methods and with additional helper methods
 *
 * @author Jack Yin
 * @since 1.0
 */
public abstract class By2 extends By {

    /**
     * @param id
     *         The value of the "id" attribute to search for.
     * @return A By which locates elements by the value of the "id" attribute.
     */
    public static By id(String id) {
        return By.id(id);
    }

    /**
     * @param linkText
     *         The exact text to match against.
     * @return A By which locates A elements by the exact text it displays.
     */
    public static By linkText(String linkText) {
        return By.linkText(linkText);
    }

    /**
     * @param partialLinkText
     *         The partial text to match against
     * @return a By which locates elements that contain the given link text.
     */
    public static By partialLinkText(String partialLinkText) {
        return By.partialLinkText(partialLinkText);
    }

    /**
     * @param name
     *         The value of the "name" attribute to search for.
     * @return A By which locates elements by the value of the "name" attribute.
     */
    public static By name(String name) {
        return By.name(name);
    }

    /**
     * @param tagName
     *         The element's tag name.
     * @return A By which locates elements by their tag name.
     */
    public static By tagName(String tagName) {
        return By.tagName(tagName);
    }

    /**
     * @param xpathExpression
     *         The XPath to use.
     * @return A By which locates elements via XPath.
     */
    public static By xpath(String xpathExpression) {
        return By.xpath(xpathExpression);
    }

    /**
     * Find elements based on the value of the "class" attribute. If an element has multiple classes, then this will
     * match against each of them. For example, if the value is "one two onone", then the class names "one" and "two"
     * will match.
     *
     * @param className
     *         The value of the "class" attribute to search for.
     * @return A By which locates elements by the value of the "class" attribute.
     */
    public static By className(String className) {
        return By.className(className);
    }

    /**
     * Find elements via the driver's underlying W3C Selector engine. If the browser does not implement the Selector
     * API, a best effort is made to emulate the API. In this case, we strive for at least CSS2 support, but offer no
     * guarantees.
     *
     * @param cssSelector
     *         CSS expression.
     * @return A By which locates elements by CSS.
     */
    public static By cssSelector(String cssSelector) {
        return By.cssSelector(cssSelector);
    }

    public static By exact(String attributeName, String attributeValue) {
        return exact(attributeName, attributeValue, "*");
    }

    public static By exact(String attributeName, String attributeValue, String tag) {
        return attr(attributeName, attributeValue).exact().depthRelative().tag(tag).build();
    }

    public static By contains(String attributeName, String attributeValue) {
        return contains(attributeName, attributeValue, "*");
    }

    public static By contains(String attributeName, String attributeValue, String tag) {
        return attr(attributeName, attributeValue).contains().depthRelative().tag(tag).build();
    }

    public static ByAttributeBuilder attr(String attributeName, String attributeValue) {
        return new ByAttributeBuilder(attributeName, attributeValue);
    }

    /**
     * Builds the xpath for finding by attributes.
     *
     * @author Jack Yin
     * @since 1.0
     */
    public static class ByAttributeBuilder implements By2Builder {
        public static final String EXACT_TEMPLATE = "{0}[@{1}=''{2}'']";
        public static final String METHOD_TEMPLATE = "{0}[contains(@{1}, ''{2}'')]";

        private String depth = "";
        private String method;
        private String tagName;
        private final String attributeName;
        private final String attributeValue;

        /**
         * Constructs an instance with target searching attribute name and the desired value\
         *
         * @param attributeName
         *         the attribute name to search
         * @param attributeValue
         *         the attribute value to match
         */
        public ByAttributeBuilder(String attributeName, String attributeValue) {
            this.attributeName = requireNonNull(attributeName);
            this.attributeValue = requireNonNull(attributeValue);
        }

        /**
         * Adds the criteria to search any depth child
         *
         * @return this builder instance
         */
        public ByAttributeBuilder anyDepthChild() {
            this.depth = ".//";
            return this;
        }

        /**
         * Adds the criteria to search any depth element absolutely
         *
         * @return this builder instance
         */
        public ByAttributeBuilder anyDepthAbsolute() {
            this.depth = "//";
            return this;
        }

        /**
         * Adds the criteria to search relative elements
         *
         * @return this builder instance
         */
        public ByAttributeBuilder depthRelative() {
            this.depth = "";
            return this;
        }

        /**
         * Changes the search method to "contains".
         *
         * @return this builder instance.
         */
        public ByAttributeBuilder contains() {
            method = "contains";
            return this;
        }

        /**
         * Changes the search method to "exactly match".
         *
         * @return this builder instance.
         */
        public ByAttributeBuilder exact() {
            method = null;
            return this;
        }

        /**
         * Specifies the target tag name, by default it is "*" to match any tags.
         *
         * @param tagName
         *         the tag name to search
         * @return this builder instance
         */
        public ByAttributeBuilder tag(String tagName) {
            this.tagName = tagName;
            return this;
        }

        @Override
        public By build() {
            String template = isBlank(method) ? EXACT_TEMPLATE : METHOD_TEMPLATE;
            return By.xpath(depth + format(template, defaultIfBlank(tagName, "*"), attributeName, attributeValue));
        }
    }


}
