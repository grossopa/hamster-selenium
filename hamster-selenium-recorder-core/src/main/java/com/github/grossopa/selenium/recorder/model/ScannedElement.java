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

import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;

/**
 * An element scanned from the current page, containing the tag name, the key attribute snapshot, the visible text,
 * the locator candidates sorted by priority and optionally the detected component.
 *
 * @author Jack Yin
 * @since 1.15
 * @see LocatorCandidate
 * @see DetectedComponent
 */
public class ScannedElement {

    private final int index;
    private final String tagName;
    private final Map<String, String> attributes;
    private final String text;
    private final List<LocatorCandidate> locatorCandidates;
    private DetectedComponent detectedComponent;

    /**
     * Constructs an instance with the scanned information.
     *
     * @param index the index of this element in the scan result, also used by the temporary marker attribute
     * @param tagName the tag name of the element, must not be null
     * @param attributes the snapshot of the key attributes (only the present ones), must not be null
     * @param text the visible text of the element, must not be null
     * @param locatorCandidates the locator candidates sorted by priority ascending, must not be null
     */
    public ScannedElement(int index, String tagName, Map<String, String> attributes, String text,
            List<LocatorCandidate> locatorCandidates) {
        this.index = index;
        this.tagName = requireNonNull(tagName);
        this.attributes = new LinkedHashMap<>(requireNonNull(attributes));
        this.text = requireNonNull(text);
        this.locatorCandidates = new ArrayList<>(requireNonNull(locatorCandidates));
    }

    /**
     * Gets the index of this element in the scan result.
     *
     * @return the index of this element in the scan result
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets the tag name of the element.
     *
     * @return the tag name of the element
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Gets the snapshot of the key attributes, only the present ones are included.
     *
     * @return the snapshot of the key attributes
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Gets the visible text of the element.
     *
     * @return the visible text of the element
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the locator candidates sorted by priority ascending, the first one is the most stable.
     *
     * @return the locator candidates
     */
    public List<LocatorCandidate> getLocatorCandidates() {
        return locatorCandidates;
    }

    /**
     * Gets the best locator candidate, i.e. the one with the lowest priority value.
     *
     * @return the best locator candidate, or null if no candidate exists
     */
    @Nullable
    public LocatorCandidate getBestLocator() {
        return locatorCandidates.stream().min(comparingInt(LocatorCandidate::getPriority)).orElse(null);
    }

    /**
     * Gets the detected component, null if no component is detected for this element.
     *
     * @return the detected component, null if not detected
     */
    @Nullable
    public DetectedComponent getDetectedComponent() {
        return detectedComponent;
    }

    /**
     * Sets the detected component for this element.
     *
     * @param detectedComponent the detected component to set
     */
    public void setDetectedComponent(DetectedComponent detectedComponent) {
        this.detectedComponent = detectedComponent;
    }

    @Override
    public String toString() {
        return "ScannedElement{" + "index=" + index + ", tagName='" + tagName + '\'' + ", attributes=" + attributes
                + ", text='" + text + '\'' + ", detectedComponent=" + detectedComponent + '}';
    }
}
