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

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

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
     * Builds the prefix and tag as the first step.
     *
     * @author Jack Yin
     * @since 1.5
     */
    public static class PrefixBuilder {

        /**
         * Adds the prefix ".//*" to search any depth child. For example:
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
         * Adds the prefix ".//tag" to search any depth child. For example:
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
         * Adds the prefix "//*" to search any depth element absolutely.
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
         * Adds the prefix "//tag" to search any depth element absolutely.
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
         * Adds the prefix "./*" to  to search relative elements
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
         * Adds the prefix "./tag" to  to search relative elements
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
         * Builds with tag as * and empty prefix.
         *
         * @return created next builder {@link SourceBuilder} instance
         */
        public SourceBuilder empty() {
            return empty(null);
        }

        /**
         * Builds with tag and empty prefix.
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
    public static class SourceBuilder {
        private final String prefix;

        /**
         * Constructs an instance with non-null prefix.
         *
         * @param prefix the prefix generated by previous {@link PrefixBuilder}.
         */
        SourceBuilder(String prefix) {
            this.prefix = prefix;
        }

        /**
         * Builds the source as {@code text()}
         *
         * @return created next build {@link MethodBuilder} instance
         */
        public MethodBuilder text() {
            return new MethodBuilder(prefix, "text()");
        }

        /**
         * Builds the source as {@code name()}
         *
         * @return created next build {@link MethodBuilder} instance
         */
        public MethodBuilder name() {
            return new MethodBuilder(prefix, "name()");
        }

        /**
         * Builds the source as attribute
         *
         * @param attr the attribute to match
         * @return created next build {@link MethodBuilder} instance
         */
        public MethodBuilder attr(String attr) {
            String source = attr.startsWith("@") ? attr : "@" + attr;
            return new MethodBuilder(prefix, source);
        }
    }

    /**
     * Builds the method as the last step.
     *
     * @author Jack Yin
     * @since 1.5
     */
    public static class MethodBuilder {

        private final String prefix;
        private final String source;

        /**
         * Builds the source as the last step.
         *
         * @author Jack Yin
         * @since 1.5
         */
        MethodBuilder(String prefix, String source) {
            requireNonNull(prefix);
            requireNonNull(source);
            this.prefix = prefix;
            this.source = source;
        }

        /**
         * Finalizes the query with equals search.
         *
         * @param term the term to search
         * @return the generated by xpath
         */
        public By exact(String term) {
            return By.xpath(prefix + String.format("[%s=%s]", source, enrichQuote(term)));
        }

        /**
         * Finalizes the query with contains function.
         *
         * @param term the term to search
         * @return the generated by xpath
         */
        public By contains(String term) {
            return By.xpath(prefix + String.format("[contains(%s,%s)]", source, enrichQuote(term)));
        }

        /**
         * Finalizes the query with starts-with function.
         *
         * @param term the term to search
         * @return the generated by xpath
         */
        public By startsWith(String term) {
            return By.xpath(prefix + String.format("[starts-with(%s,%s)]", source, enrichQuote(term)));
        }

        /**
         * Finalizes the query with matches function.
         *
         * @param patternString the regex to match
         * @return the generated by xpath
         */
        public By matches(String patternString) {
            return By.xpath(prefix + String.format("[matches(%s,%s)]", source, enrichQuote(patternString)));
        }

        private String enrichQuote(String term) {
            if (StringUtils.contains(term, '\"')) {
                return '\'' + term + '\'';
            } else {
                return '\"' + term + '\"';
            }
        }

    }
}
