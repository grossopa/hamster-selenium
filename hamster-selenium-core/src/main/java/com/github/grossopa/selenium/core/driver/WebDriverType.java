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

package com.github.grossopa.selenium.core.driver;

import javax.annotation.Nullable;

/**
 * Available Web Driver types.
 *
 * @author Jack Yin
 * @since 1.0
 */
public enum WebDriverType {

    /**
     * Represents the Chrome browser
     */
    CHROME {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applyChrome(input);
        }
    },
    /**
     * Represents the Microsoft Edge browser
     */
    EDGE {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applyEdge(input);
        }
    },
    /**
     * Represents the Firefox browser
     */
    FIREFOX {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applyFirefox(input);
        }
    },
    /**
     * Represents the Internet Explorer browser
     */
    IE {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applyIE(input);
        }
    },
    /**
     * Represents the Opera browser
     */
    OPERA {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applyOpera(input);
        }
    },
    /**
     * Represents the Safari browser
     */
    SAFARI {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applySafari(input);
        }
    };

    /**
     * Invokes the corresponding type of action of the given function instance.
     *
     * @param function
     *         the function with all types of actions to invoke
     * @param input
     *         the input parameter for function to invoke with
     * @param <T>
     *         the input parameter type
     * @param <R>
     *         the return type
     * @return the function execution result
     */
    @Nullable
    public abstract <T, R> R apply(WebDriverTypeFunction<T, R> function, @Nullable T input);

    /**
     * The browser actions definition.
     *
     * @param <T>
     *         the input parameter type
     * @param <R>
     *         the return type
     */
    public interface WebDriverTypeFunction<T, R> {

        /**
         * Executes when the enum type is {@link WebDriverType#CHROME}.
         *
         * @param input
         *         the input parameter
         * @return the execution result
         */
        @Nullable
        R applyChrome(@Nullable T input);

        /**
         * Executes when the enum type is {@link WebDriverType#EDGE}.
         *
         * @param input
         *         the input parameter
         * @return the execution result
         */
        @Nullable
        R applyEdge(@Nullable T input);

        /**
         * Executes when the enum type is {@link WebDriverType#FIREFOX}.
         *
         * @param input
         *         the input parameter
         * @return the execution result
         */
        @Nullable
        R applyFirefox(@Nullable T input);

        /**
         * Executes when the enum type is {@link WebDriverType#IE}.
         *
         * @param input
         *         the input parameter
         * @return the execution result
         */
        @Nullable
        R applyIE(@Nullable T input);

        /**
         * Executes when the enum type is {@link WebDriverType#OPERA}.
         *
         * @param input
         *         the input parameter
         * @return the execution result
         */
        @Nullable
        R applyOpera(@Nullable T input);

        /**
         * Executes when the enum type is {@link WebDriverType#SAFARI}.
         *
         * @param input
         *         the input parameter
         * @return the execution result
         */
        @Nullable
        R applySafari(@Nullable T input);
    }
}
