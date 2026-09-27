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
package com.github.grossopa.selenium.recorder.scan.strategy;

import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorContext;
import com.github.grossopa.selenium.recorder.model.LocatorContext.AncestorInfo;
import com.github.grossopa.selenium.recorder.model.LocatorType;

import java.util.List;
import java.util.Map;

/**
 * A strategy that builds a list locator candidate from sibling elements when multiple siblings of the same type exist
 * under the same parent. This strategy is only effective when the element has no {@code id}, {@code name}, custom
 * attribute or ancestor anchor, but has multiple siblings.
 *
 * <p>The generated locator uses a CSS selector pointing to all siblings under a parent context. The parent context is
 * derived from the ancestor anchor if available, otherwise the raw tag name is used. The resulting locator is marked as
 * a list locator, meaning the generated page object method will return a {@code List} of components.</p>
 *
 * <p>Example of a generated locator:
 * <ul>
 * <li>With ancestor: {@code #formSection .MuiButton-root} (list)</li>
 * <li>Without ancestor: {@code .MuiButton-root} (list)</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.16
 * @see LocatorCandidate#PRIORITY_SIBLING_LIST
 * @see LocatorContext
 */
public class SiblingListLocatorCandidateStrategy implements LocatorCandidateStrategy {

    private static final String ELEMENTS_SUFFIX = " elements)";

    private final String rootCssClass;

    /**
     * Constructs an instance without a specific root CSS class. The tag name of the scanned element will be used as the
     * selector.
     */
    public SiblingListLocatorCandidateStrategy() {
        this(null);
    }

    /**
     * Constructs an instance with a specific root CSS class to use in the selector.
     *
     * @param rootCssClass the CSS class name to use as the selector, e.g. {@code MuiButton-root}. If null, the tag name
     * of the scanned element will be used.
     */
    public SiblingListLocatorCandidateStrategy(String rootCssClass) {
        this.rootCssClass = rootCssClass;
    }

    @Override
    public List<LocatorCandidate> toCandidates(int index, String tagName, Map<String, String> attributes, String text) {
        return List.of();
    }

    @Override
    public List<LocatorCandidate> toCandidates(int index, String tagName, Map<String, String> attributes, String text,
            LocatorContext context) {
        if (context.getSiblingCount() <= 1) {
            return List.of();
        }

        String elementSelector = rootCssClass != null ? "." + rootCssClass : tagName;
        AncestorInfo ancestor = context.getAncestor();

        String cssValue;
        String description;
        if (ancestor != null && ancestor.id() != null) {
            cssValue = "#" + ancestor.id() + " > " + elementSelector;
            description = "by sibling list under #" + ancestor.id() + " (" + context.getSiblingCount() + ELEMENTS_SUFFIX;
        } else if (ancestor != null && ancestor.testId() != null) {
            cssValue = "[data-testid=\"" + ancestor.testId() + "\"] > " + elementSelector;
            description = "by sibling list under data-testid \"" + ancestor.testId() + "\" ("
                    + context.getSiblingCount() + ELEMENTS_SUFFIX;
        } else {
            cssValue = elementSelector;
            description = "by sibling list " + elementSelector + " (" + context.getSiblingCount() + ELEMENTS_SUFFIX;
        }

        return List.of(new LocatorCandidate(LocatorType.CSS_SELECTOR, cssValue, LocatorCandidate.PRIORITY_SIBLING_LIST,
                description, true));
    }
}
