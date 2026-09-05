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

import org.openqa.selenium.By;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * A candidate locator of a scanned element with its priority. The candidates of one element are sorted by priority
 * ascending: the lower value the more stable, e.g. {@code id} based locator is preferred over xpath.
 *
 * @author Jack Yin
 * @since 1.15
 * @see LocatorType
 */
public class LocatorCandidate {

    /**
     * The priority of a locator based on {@code id} attribute.
     */
    public static final int PRIORITY_ID = 0;

    /**
     * The priority of a locator based on {@code name} attribute.
     */
    public static final int PRIORITY_NAME = 10;

    /**
     * The priority of a locator based on a customized key attribute.
     */
    public static final int PRIORITY_CUSTOM_ATTRIBUTE = 20;

    /**
     * The priority of a locator based on the visible text.
     */
    public static final int PRIORITY_TEXT = 50;

    /**
     * The priority of the fallback locator based on the temporary marker attribute.
     */
    public static final int PRIORITY_MARKER = 100;

    private final LocatorType type;
    private final String value;
    private final int priority;
    private final String description;

    /**
     * Constructs an instance with locator type, value, priority and description.
     *
     * @param type the locator type, must not be null
     * @param value the locator value, must not be null
     * @param priority the priority of this candidate, the lower the more preferred
     * @param description the human readable description of this candidate, must not be null
     */
    public LocatorCandidate(LocatorType type, String value, int priority, String description) {
        this.type = requireNonNull(type);
        this.value = requireNonNull(value);
        this.priority = priority;
        this.description = requireNonNull(description);
    }

    /**
     * Gets the locator type.
     *
     * @return the locator type
     */
    public LocatorType getType() {
        return type;
    }

    /**
     * Gets the locator value.
     *
     * @return the locator value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the priority of this candidate, the lower the more preferred.
     *
     * @return the priority of this candidate
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gets the human readable description of this candidate.
     *
     * @return the human readable description of this candidate
     */
    public String getDescription() {
        return description;
    }

    /**
     * Builds the {@link By} instance from this candidate.
     *
     * @return the built {@link By} instance
     */
    public By toBy() {
        return type.toBy(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocatorCandidate that)) {
            return false;
        }
        return priority == that.priority && type == that.type && value.equals(that.value)
                && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, priority, description);
    }

    @Override
    public String toString() {
        return "LocatorCandidate{" + "type=" + type + ", value='" + value + '\'' + ", priority=" + priority
                + ", description='" + description + '\'' + '}';
    }
}
