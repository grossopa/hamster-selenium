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

package com.github.grossopa.selenium.core.intercepting;

import java.util.function.Supplier;

/**
 * The handler triggering before, after and when exceptions occurs.
 *
 * @author Jack Yin
 * @since 1.4
 */
public interface InterceptingHandler {

    /**
     * Before the action handler.
     *
     * @param methodInfo the method invoking information
     */
    void onBefore(MethodInfo<?> methodInfo);

    /**
     * After the action handler.
     *
     * @param methodInfo the method invoking information
     * @param resultValue the result value
     */
    void onAfter(MethodInfo<?> methodInfo, Object resultValue);

    /**
     * When on exception handler.
     *
     * @param methodInfo the method invoking information
     * @param exception the thrown exception
     */
    void onException(MethodInfo<?> methodInfo, Exception exception);

    /**
     * Executes the actual super method with try catch for handler to be invoked properly.
     *
     * @param supplier the supplier to be executed
     * @param methodInfo the method information
     * @param <T> the result type
     * @return the execution result
     */
    default <T, R> R execute(Supplier<R> supplier, MethodInfo<T> methodInfo) {
        try {
            this.onBefore(methodInfo);
            var result = supplier.get();
            methodInfo.executionDone();
            this.onAfter(methodInfo, result);
            return result;
        } catch (RuntimeException runtimeException) {
            this.onException(methodInfo, runtimeException);
            throw runtimeException;
        }
    }
}
