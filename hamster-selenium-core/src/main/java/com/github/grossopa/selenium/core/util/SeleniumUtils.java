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

package com.github.grossopa.selenium.core.util;

import org.openqa.selenium.StaleElementReferenceException;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * The Selenium framework utils
 *
 * @author Jack Yin
 * @since 1.1
 */
public class SeleniumUtils {

    /**
     * private constructor
     */
    private SeleniumUtils() {
        throw new AssertionError();
    }

    /**
     * Executes a method with allowance of throwing the {@link org.openqa.selenium.StaleElementReferenceException}.
     *
     * @param <T> the return type
     * @param function the function to execute, if {@link StaleElementReferenceException} is thrown then will return the
     * default Value.
     * @param defaultValue the default value to return
     * @return result or defaultValue if function throws {@link StaleElementReferenceException}
     */
    @Nullable
    public static <T> T executeIgnoringStaleElementReference(Supplier<T> function, T defaultValue) {
        try {
            return function.get();
        } catch (StaleElementReferenceException exception) {
            return defaultValue;
        }
    }
}
