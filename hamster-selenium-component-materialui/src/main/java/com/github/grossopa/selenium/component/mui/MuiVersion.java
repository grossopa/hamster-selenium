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

package com.github.grossopa.selenium.component.mui;

/**
 * The supported Material UI versions
 *
 * @author Jack Yin
 * @since 1.7
 */
public enum MuiVersion {
    /**
     * Version 4, see <a href="https://v4.mui.com/">https://v4.mui.com/</a>
     */
    V4 {
        @Override
        public <T, R> R apply(Func<T, R> func, T input) {
            return func.applyV4(input);
        }
    },
    /**
     * Version 5, current version as of year 2021
     */
    V5 {
        @Override
        public <T, R> R apply(Func<T, R> func, T input) {
            return func.applyV5(input);
        }
    };

    /**
     * Executes a function by current type.
     *
     * @param func the function to execute
     * @param input the input parameter to be passed in
     * @param <T> the input type
     * @param <R> the result type
     * @return execution result
     */
    public abstract <T, R> R apply(Func<T, R> func, T input);

    /**
     * The function for {@link MuiVersion} enums.
     *
     * @param <T> the input type
     * @param <R> the result type
     * @author Jack Yin
     * @since 1.6
     */
    public interface Func<T, R> {

        /**
         * applies to {@link #V4}.
         *
         * @param input the input params
         * @return the execution result
         */
        R applyV4(T input);

        /**
         * applies to {@link #V5}.
         *
         * @param input the input params
         * @return the execution result
         */
        R applyV5(T input);
    }
}
