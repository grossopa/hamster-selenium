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

import static java.util.Objects.requireNonNull;

/**
 * An element selected by the user to be part of a {@link PageModel}, with the user defined field name, the chosen
 * locator and optionally the detected component.
 *
 * @author Jack Yin
 * @since 1.15
 * @see PageModel
 */
public class PageElementModel {

    private final String fieldName;
    private final LocatorCandidate locator;
    private final DetectedComponent detectedComponent;

    /**
     * Constructs an instance with field name, chosen locator and optionally the detected component.
     *
     * @param fieldName the field/method name in the generated page object, must not be null
     * @param locator the chosen locator candidate, must not be null
     * @param detectedComponent the detected component, null if the element is treated as plain web component
     */
    public PageElementModel(String fieldName, LocatorCandidate locator, @Nullable DetectedComponent detectedComponent) {
        this.fieldName = requireNonNull(fieldName);
        this.locator = requireNonNull(locator);
        this.detectedComponent = detectedComponent;
    }

    /**
     * Gets the field/method name in the generated page object.
     *
     * @return the field/method name in the generated page object
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Gets the chosen locator candidate.
     *
     * @return the chosen locator candidate
     */
    public LocatorCandidate getLocator() {
        return locator;
    }

    /**
     * Gets the detected component, null if the element is treated as plain web component.
     *
     * @return the detected component, null if not a component
     */
    @Nullable
    public DetectedComponent getDetectedComponent() {
        return detectedComponent;
    }

    @Override
    public String toString() {
        return "PageElementModel{" + "fieldName='" + fieldName + '\'' + ", locator=" + locator
                + ", detectedComponent=" + detectedComponent + '}';
    }
}
