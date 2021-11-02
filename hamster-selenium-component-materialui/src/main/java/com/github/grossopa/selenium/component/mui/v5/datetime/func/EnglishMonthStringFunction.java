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
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiMonthPicker;

import java.time.Month;
import java.util.Arrays;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

/**
 * Converts the English date string to month.
 *
 * @author Jack Yin
 * @since 1.8
 */
public class EnglishMonthStringFunction implements MonthStringFunction {
    /**
     * The short string of the months
     */
    private static final String[] MONTHS = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};

    @Override
    public Month stringToMonth(String monthString) {
        for (int i = 0; i < MONTHS.length; i++) {
            if (startsWithIgnoreCase(monthString, MONTHS[i])) {
                return Month.of(i + 1);
            }
        }
        throw new NoSuchMonthException("Failed to find month by string " + monthString);
    }

    /**
     * Converts the {@link Month} to month label for {@link MuiMonthPicker} to locate the month button.
     *
     * @param month the month to convert to string
     * @return the converted string of the month
     */
    @Override
    public String monthToString(Month month) {
        return capitalize(month.toString().toLowerCase(Locale.ROOT).substring(0, 3));
    }

    /**
     * Gets the singleton instance.
     *
     * @return the singleton instance.
     */
    public static EnglishMonthStringFunction getInstance() {
        return Singleton.instance;
    }

    @Override
    public String toString() {
        return String.format("EnglishStringToMonthFunction{MONTHS=%s}", Arrays.toString(MONTHS));
    }


    /**
     * the singleton holder of {@link EnglishMonthStringFunction}.
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
         * The singleton instance of {@link EnglishMonthStringFunction}
         */
        public static final EnglishMonthStringFunction instance = new EnglishMonthStringFunction();
    }

}
