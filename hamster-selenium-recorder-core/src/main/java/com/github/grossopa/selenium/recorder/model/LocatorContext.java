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
package com.github.grossopa.selenium.recorder.model;

import jakarta.annotation.Nullable;

import java.util.Map;

/**
 * The DOM context information collected by the scanner for each scanned element, providing ancestor anchor information
 * and sibling counts that help locator strategies build more stable and unique locators.
 *
 * @author Jack Yin
 * @since 1.16
 * @see com.github.grossopa.selenium.recorder.scan.strategy.LocatorCandidateStrategy
 */
public class LocatorContext {

    /**
     * An empty context with no ancestor information and zero sibling count.
     */
    public static final LocatorContext EMPTY = new LocatorContext(null, 0, Map.of());

    @Nullable
    private final AncestorInfo ancestor;
    private final int siblingCount;
    private final Map<String, Integer> nameSiblingCounts;

    /**
     * Constructs an instance with the given DOM context information.
     *
     * @param ancestor the ancestor anchor information, may be null if no anchor is found
     * @param siblingCount the number of siblings with the same tag name under the same parent
     * @param nameSiblingCounts the count of each {@code name} attribute value among siblings, may be empty
     */
    public LocatorContext(@Nullable AncestorInfo ancestor, int siblingCount, Map<String, Integer> nameSiblingCounts) {
        this.ancestor = ancestor;
        this.siblingCount = siblingCount;
        this.nameSiblingCounts = Map.copyOf(nameSiblingCounts);
    }

    /**
     * Gets the ancestor anchor information, null if no ancestor with id or test id is found.
     *
     * @return the ancestor anchor information, or null
     */
    @Nullable
    public AncestorInfo getAncestor() {
        return ancestor;
    }

    /**
     * Gets the number of siblings with the same tag name under the same parent, including the element itself.
     *
     * @return the sibling count, 0 if unknown
     */
    public int getSiblingCount() {
        return siblingCount;
    }

    /**
     * Gets the count of each {@code name} attribute value among siblings. For example, if three radio buttons share
     * {@code name="gender"}, the map would contain {@code {"gender" -> 3}}.
     *
     * @return the name sibling counts, empty if no name information is available
     */
    public Map<String, Integer> getNameSiblingCounts() {
        return nameSiblingCounts;
    }

    /**
     * The ancestor anchor information found by traversing up the DOM tree.
     *
     * @param id the {@code id} of the ancestor, may be null
     * @param testId the {@code data-testid} (or configured anchor attribute) value of the ancestor, may be null
     * @param childPath the CSS path from the ancestor to the target element's class, e.g. {@code .MuiButton-root}
     */
    public record AncestorInfo(@Nullable String id, @Nullable String testId, String childPath) {
    }
}
