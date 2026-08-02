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

package com.github.grossopa.selenium.core.driver;

import javax.annotation.Nullable;

/**
 * Available Web Driver types.
 *
 * <p>This enum represents the different types of web browsers that can be automated
 * using Selenium WebDriver. Each type provides a specific implementation for
 * browser-specific operations through the {@link WebDriverTypeFunction} interface.</p>
 *
 * <p>The enum uses a visitor pattern implementation where each browser type
 * implements the {@link #apply(WebDriverTypeFunction, Object)} method to delegate
 * execution to the appropriate method in the function interface. This design allows
 * for type-specific behavior without using instanceof checks.</p>
 *
 * <p>Supported browser types:
 * <ul>
 *   <li>{@link #CHROME} - Google Chrome</li>
 *   <li>{@link #EDGE} - Microsoft Edge</li>
 *   <li>{@link #FIREFOX} - Mozilla Firefox</li>
 *   <li>{@link #IE} - Internet Explorer</li>
 *   <li>{@link #SAFARI} - Apple Safari</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * WebDriverType.CHROME.apply(new WebDriverTypeFunction<WebDriverOptions, WebDriver>() {
 *     public WebDriver applyChrome(WebDriverOptions options) {
 *         // Chrome-specific implementation
 *         return new ChromeDriver(options.getChromeOptions());
 *     }
 *     
 *     // ... other browser implementations
 * }, webDriverOptions);
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.0
 * @see WebDriverTypeFunction
 */
public enum WebDriverType {

    /**
     * Represents the Chrome browser
     *
     * <p>Google Chrome is a widely-used web browser developed by Google. When using
     * this driver type, the {@link WebDriverTypeFunction#applyChrome(Object)} method
     * will be invoked.</p>
     */
    CHROME {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applyChrome(input);
        }
    },
    
    /**
     * Represents the Microsoft Edge browser
     *
     * <p>Microsoft Edge is a web browser developed by Microsoft. When using
     * this driver type, the {@link WebDriverTypeFunction#applyEdge(Object)} method
     * will be invoked.</p>
     */
    EDGE {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applyEdge(input);
        }
    },
    
    /**
     * Represents the Firefox browser
     *
     * <p>Mozilla Firefox is a free and open-source web browser developed by Mozilla.
     * When using this driver type, the {@link WebDriverTypeFunction#applyFirefox(Object)} method
     * will be invoked.</p>
     */
    FIREFOX {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applyFirefox(input);
        }
    },
    
    /**
     * Represents the Internet Explorer browser
     *
     * <p>Internet Explorer is a web browser developed by Microsoft. Note that
     * IE support in Selenium is deprecated and this option should only be
     * used for legacy applications. When using this driver type, the
     * {@link WebDriverTypeFunction#applyIE(Object)} method will be invoked.</p>
     */
    IE {
        @Override
        public <T, R> R apply(WebDriverTypeFunction<T, R> function, T input) {
            return function.applyIE(input);
        }
    },
    
    /**
     * Represents the Safari browser
     *
     * <p>Safari is a web browser developed by Apple Inc. It is the default
     * browser on macOS and iOS devices. When using this driver type, the
     * {@link WebDriverTypeFunction#applySafari(Object)} method will be invoked.</p>
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
