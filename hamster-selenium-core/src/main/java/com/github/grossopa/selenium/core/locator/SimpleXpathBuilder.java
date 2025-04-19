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

import javax.annotation.Nullable;

import static com.github.grossopa.selenium.core.util.SeleniumUtils.enrichQuote;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * A simple builder for creating Selenium simple xpath in a readable way.
 *
 * @author Jack Yin
 * @since 1.5
 */
public class SimpleXpathBuilder {

    public static final String ANY_TAG = "*";

    /**
     * private constructor
     */
    private SimpleXpathBuilder() {
        throw new AssertionError();
    }

    /**
     * Builds the xpath and tag as the first step.
     *
     * @author Jack Yin
     * @since 1.5
     */
    public static class PrefixBuilder {

        /**
         * Adds the xpath ".//*" to search any depth child. For example:
         *
         * <p>
         * div (current) -&lt; span  -&lt; span -&lt; <b>li (target)</b>
         * </p>
         *
         * @return created next builder {@link SourceBuilder} instance
         */
        public SourceBuilder anywhereRelative() {
            return anywhereRelative(null);
        }

        /**
         * Adds the xpath ".//tag" to search any depth child. For example:
         *
         * <p>
         * div (current) -&lt; span  -&lt; span -&lt; <b>li (target)</b>
         * </p>
         *
         * @param tag the target tag name to search, defaulted to * if blank
         * @return created next builder {@link SourceBuilder} instance
         */
        public SourceBuilder anywhereRelative(@Nullable String tag) {
            return new SourceBuilder(".//" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Adds the xpath "//*" to search any depth element absolutely.
         *
         * <p>
         * div (current)<br> div (another) -&lt; span -&lt; span -&lt; <b>li (target)</b>
         * </p>
         *
         * @return created next builder {@link SourceBuilder} instance
         */
        public SourceBuilder anywhere() {
            return anywhere(null);
        }

        /**
         * Adds the xpath "//tag" to search any depth element absolutely.
         *
         * <p>
         * div (current)<br> div (another) -&lt; span -&lt; span -&lt; <b>li (target)</b>
         * </p>
         *
         * @param tag the target tag name to search, defaulted to * if blank
         * @return created next builder {@link SourceBuilder} instance
         */
        public SourceBuilder anywhere(String tag) {
            return new SourceBuilder("//" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Adds the xpath "./*" to  to search relative elements
         *
         * <p>
         * div (current) -&lt; <b>li (target)</b>
         * </p>
         *
         * @return created next builder {@link SourceBuilder} instance
         */
        public SourceBuilder relative() {
            return relative(null);
        }

        /**
         * Adds the xpath "./tag" to  to search relative elements
         *
         * <p>
         * div (current) -&lt; <b>li (target)</b>
         * </p>
         *
         * @param tag the target tag name to search, defaulted to * if blank
         * @return created next builder {@link SourceBuilder} instance
         */
        public SourceBuilder relative(String tag) {
            return new SourceBuilder("./" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Builds with tag as * and empty xpath.
         *
         * @return created next builder {@link SourceBuilder} instance
         */
        public SourceBuilder empty() {
            return empty(null);
        }

        /**
         * Builds with tag and empty xpath.
         *
         * @param tag the target tag name to search, defaulted to * if blank
         * @return created next builder {@link SourceBuilder} instance
         */
        public SourceBuilder empty(String tag) {
            return new SourceBuilder(defaultIfBlank(tag, ANY_TAG));
        }
    }

    /**
     * Builds the source as the second step.
     *
     * @author Jack Yin
     * @since 1.5
     */
    public static class SourceBuilder implements By2Builder {
        private final String xpath;
        @Nullable
        private final OperatorType operator;

        /**
         * Constructs an instance with non-null xpath.
         *
         * @param xpath the xpath generated by previous {@link PrefixBuilder}.
         */
        SourceBuilder(String xpath) {
            this(xpath, null);
        }


        /**
         * Constructs an instance with non-null xpath.
         *
         * @param xpath the xpath generated by previous {@link PrefixBuilder}.
         * @param operator the and / or operator, could be null
         */
        SourceBuilder(String xpath, @Nullable OperatorType operator) {
            this.xpath = xpath;
            this.operator = operator;
        }

        /**
         * Builds the source as {@code text()}
         *
         * @return created next build {@link WithNotMethodBuilder} instance
         */
        public WithNotMethodBuilder text() {
            return new WithNotMethodBuilder(xpath, "text()", operator);
        }

        /**
         * Builds the source as {@code name()}
         *
         * @return created next build {@link WithNotMethodBuilder} instance
         */
        public WithNotMethodBuilder name() {
            return new WithNotMethodBuilder(xpath, "name()", operator);
        }

        /**
         * Builds the source as attribute
         *
         * @param attr the attribute to match
         * @return created next build {@link WithNotMethodBuilder} instance
         */
        public WithNotMethodBuilder attr(String attr) {
            String source = attr.startsWith("@") ? attr : "@" + attr;
            return new WithNotMethodBuilder(xpath, source, operator);
        }

        @Override
        public By build() {
            return By.xpath(xpath);
        }

        @Override
        public String xpathString() {
            return xpath;
        }

        /**
         * To next axes builder.
         *
         * @return the next created {@link AxesBuilder} builder instance
         */
        public AxesBuilder axes() {
            return new AxesBuilder(xpath);
        }
    }

    /**
     * Method Builder with not() function.
     *
     * @author Jack Yin
     * @since 1.11
     */
    public static class WithNotMethodBuilder extends MethodBuilder {

        WithNotMethodBuilder(String xpath, String source, OperatorType operator) {
            super(xpath, source, operator);
        }

        /**
         * Wraps the next method with not()
         *
         * @return the created next build {@link MethodBuilder} instance
         */
        public MethodBuilder not() {
            return new MethodBuilder(xpath, source, true, operator);
        }
    }

    /**
     * Builds the method as the last step. Note this is not a {@link By2Builder}.
     *
     * @author Jack Yin
     * @since 1.5
     */
    public static class MethodBuilder {

        protected final String xpath;
        protected final String source;
        protected final boolean notOperator;
        protected final OperatorType operator;

        MethodBuilder(String xpath, String source, @Nullable OperatorType operator) {
            this(xpath, source, false, operator);
        }

        MethodBuilder(String xpath, String source, boolean notOperator, @Nullable OperatorType operator) {
            requireNonNull(xpath);
            requireNonNull(source);
            this.xpath = xpath;
            this.source = source;
            this.notOperator = notOperator;
            this.operator = operator;
        }

        /**
         * Finalizes the query with equals search.
         *
         * @param term the term to search
         * @return the generated by xpath
         */
        public AxesBuilder exact(String term) {
            return new AxesBuilder(buildXpath("%s=%s", term));
        }

        /**
         * Finalizes the query with contains function.
         *
         * @param term the term to search
         * @return the generated by xpath
         */
        public AxesBuilder contains(String term) {
            return new AxesBuilder(buildXpath("contains(%s,%s)", term));
        }

        /**
         * Finalizes the query with starts-with function.
         *
         * @param term the term to search
         * @return the generated by xpath
         */
        public AxesBuilder startsWith(String term) {
            return new AxesBuilder(buildXpath("starts-with(%s,%s)", term));
        }

        /**
         * Finalizes the query with matches function.
         *
         * @param patternString the regex to match
         * @return the generated by xpath
         */
        public AxesBuilder matches(String patternString) {
            return new AxesBuilder(buildXpath("matches(%s,%s)", patternString));
        }

        private String buildXpath(String template, String value) {
            if (notOperator) {
                template = "not(" + template + ")";
            }

            String formatted = String.format(template, source, enrichQuote(value));

            if (operator != null) {
                return xpath.substring(0, xpath.length() - 1) + operator.getValue() + formatted + "]";
            } else {
                return xpath + "[" + formatted + "]";
            }
        }
    }

    public static class AxesBuilder implements By2Builder {

        private final String xpath;

        /**
         * Constructs an instance with previous built xpath.
         *
         * @param xpath the current xpath built in previous step.
         */
        public AxesBuilder(String xpath) {
            requireNonNull(xpath);
            this.xpath = xpath;
        }

        /**
         * Builds the child axis. the child axis contains the children of the context node.
         *
         * @return the next created {@link SourceBuilder} builder instance.
         */
        public SourceBuilder child() {
            return child(null);
        }

        /**
         * Builds the child axis with target tag name. the child axis contains the children of the context node.
         *
         * @param tag the tag of the children to find
         * @return the next created {@link SourceBuilder} builder instance.
         */
        public SourceBuilder child(String tag) {
            return new SourceBuilder(xpath + "/child::" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Builds the descendant axis. The descendant axis contains the descendants of the context node; a descendant is
         * a child or a child of a child and so on
         *
         * @return the next created {@link SourceBuilder} builder instance.
         */
        public SourceBuilder descendant() {
            return descendant(null);
        }

        /**
         * Builds the descendant axis with target tag name. The descendant axis contains the descendants of the context
         * node; a descendant is a child or a child of a child and so on
         *
         * @param tag the tag of the descendants to find
         * @return the next created {@link SourceBuilder} builder instance.
         */
        public SourceBuilder descendant(String tag) {
            return new SourceBuilder(xpath + "/descendant::" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Builds the parent axis. The parent axis contains the parent of the context node, if there is one
         *
         * @return the next created {@link SourceBuilder} builder instance.
         */
        public SourceBuilder parent() {
            return parent(null);
        }

        /**
         * Builds the parent axis with target tag name. The parent axis contains the parent of the context node, if
         * there is one
         *
         * @param tag the tag of the parents to find
         * @return the next created {@link SourceBuilder} builder instance.
         */
        public SourceBuilder parent(String tag) {
            return new SourceBuilder(xpath + "/parent::" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Builds the ancestor axis.  An ancestor contains the ancestors of the context node; the ancestors of the
         * context node consist of the parent of context node and the parent's parent and so on; thus, the ancestor axis
         * will always include the root node, unless the context node is the root node
         *
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder ancestor() {
            return ancestor(null);
        }

        /**
         * Builds the ancestor axis with tag. An ancestor contains the ancestors of the context node; the ancestors of
         * the context node consist of the parent of context node and the parent's parent and so on; thus, the ancestor
         * axis will always include the root node, unless the context node is the root node
         *
         * @param tag the tag of the ancestor to find
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder ancestor(String tag) {
            return new SourceBuilder(xpath + "/ancestor::" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Builds the preceding-sibling axis. The following-sibling axis contains all the preceding siblings of the
         * context node
         *
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder precedingSibling() {
            return precedingSibling(null);
        }

        /**
         * Builds the preceding-sibling axis with tag name. The following-sibling axis contains all the preceding
         * siblings of the context node
         *
         * @param tag the tag of the preceding siblings to find
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder precedingSibling(String tag) {
            return new SourceBuilder(xpath + "/preceding-sibling::" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Builds the following-sibling axis. The following-sibling axis contains all the following siblings of the
         * context node
         *
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder followingSibling() {
            return followingSibling(null);
        }

        /**
         * Builds the following-sibling axis with tag name. The following-sibling axis contains all the following
         * siblings of the context node
         *
         * @param tag the tag of the following siblings to find
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder followingSibling(String tag) {
            return new SourceBuilder(xpath + "/following-sibling::" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Builds the following axis. The following axis contains all nodes in the same document as the context node
         * that are after the context node in document order,
         *
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder following() {
            return following(null);
        }

        /**
         * Builds the following axis with tag. The following axis contains all nodes in the same document as the context
         * node that are after the context node in document order,
         *
         * @param tag the tag of the followings to find
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder following(String tag) {
            return new SourceBuilder(xpath + "/following::" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Builds the preceding axis. The preceding axis contains all nodes in the same document as the context node
         * that are before the context node in document order,
         *
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder preceding() {
            return preceding(null);
        }

        /**
         * Builds the preceding axis. The preceding axis contains all nodes in the same document as the context node
         * that are before the context node in document order,
         *
         * @param tag the tag of the preceding to find
         * @return the next created {@link SourceBuilder} instance.
         */
        public SourceBuilder preceding(String tag) {
            return new SourceBuilder(xpath + "/preceding::" + defaultIfBlank(tag, ANY_TAG));
        }

        /**
         * Insert next expression with and logical operator. [@value="aa" and contains(text(), "bb")]
         *
         * @return the next created {@link SourceBuilder} instance with {@link OperatorType#AND} operator.
         */
        public SourceBuilder and() {
            return new SourceBuilder(xpath, OperatorType.AND);
        }

        /**
         * Insert next expression with and logical operator. [@value="aa" or contains(text(), "bb")]
         *
         * @return the next created {@link SourceBuilder} instance with {@link OperatorType#OR} operator.
         */
        public SourceBuilder or() {
            return new SourceBuilder(xpath, OperatorType.OR);
        }

        @Override
        public By build() {
            return By.xpath(xpath);
        }

        @Override
        public String xpathString() {
            return xpath;
        }


    }
}
