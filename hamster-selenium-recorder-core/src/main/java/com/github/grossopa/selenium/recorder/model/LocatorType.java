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

import static java.util.Objects.requireNonNull;

/**
 * The type of a locator candidate, it determines how the locator value is translated into a {@link By} instance and
 * how the corresponding code is generated in the page object.
 *
 * @author Jack Yin
 * @since 1.15
 * @see LocatorCandidate
 */
public enum LocatorType {

    /**
     * Locates by the {@code id} attribute, translated to {@link By#id(String)}.
     */
    ID,

    /**
     * Locates by the {@code name} attribute, translated to {@link By#name(String)}.
     */
    NAME,

    /**
     * Locates by a CSS selector, translated to {@link By#cssSelector(String)}.
     */
    CSS_SELECTOR,

    /**
     * Locates by an xpath, translated to {@link By#xpath(String)}.
     */
    XPATH;

    /**
     * Builds the {@link By} instance from this type and the given locator value.
     *
     * @param value the locator value
     * @return the built {@link By} instance
     */
    public By toBy(String value) {
        requireNonNull(value);
        return switch (this) {
            case ID -> By.id(value);
            case NAME -> By.name(value);
            case CSS_SELECTOR -> By.cssSelector(value);
            case XPATH -> By.xpath(value);
        };
    }
}
