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

package com.github.grossopa.selenium.component.mui.v5.datetime.func;

import com.github.grossopa.selenium.component.mui.exception.NoSuchMonthException;

import java.time.Month;
import java.util.Arrays;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

/**
 * Converts the English date string to month.
 *
 * @author Jack Yin
 * @since 1.8
 */
public class EnglishStringToMonthFunction implements Function<String, Month> {
    /**
     * The short string of the months
     */
    private static final String[] MONTHS = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};

    /**
     * Applies with the month string
     *
     * @param s the month string to find
     * @return the found month
     */
    @Override
    public Month apply(String s) {
        for (int i = 0; i < MONTHS.length; i++) {
            if (startsWithIgnoreCase(s, MONTHS[i])) {
                return Month.of(i + 1);
            }
        }
        throw new NoSuchMonthException("Failed to find month by string " + s);
    }

    /**
     * Gets the singleton instance.
     *
     * @return the singleton instance.
     */
    public static EnglishStringToMonthFunction getInstance() {
        return Singleton.instance;
    }

    @Override
    public String toString() {
        return String.format("EnglishStringToMonthFunction{MONTHS=%s}", Arrays.toString(MONTHS));
    }

    /**
     * the singleton holder of {@link EnglishStringToMonthFunction}.
     *
     * @author Jack Yin
     * @since 1.8
     */
    static class Singleton {

        /**
         * Private constructor
         */
        private Singleton() {
            throw new AssertionError();
        }

        /**
         * The singleton instance of {@link EnglishStringToMonthFunction}
         */
        public static final EnglishStringToMonthFunction instance = new EnglishStringToMonthFunction();
    }

}
