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

import org.openqa.selenium.WebDriver;

import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * The method information of current invoking
 *
 * @author Jack Yin
 * @since 1.4
 */
public class MethodInfo<T> {

    private final String name;
    private final Object[] params;
    private final T source;
    private final Long startTimeInMillis;
    private Long endTimeInMillis;

    /**
     * Constructs an instance with method name, method params and the element that invokes the method, null if it's
     * {@link org.openqa.selenium.WebDriver}.
     *
     * @param name the method name that currently being invoked
     * @param params the method params
     * @param source the source that invokes the method
     */
    public MethodInfo(T source, String name, Object... params) {
        requireNonNull(source);
        requireNonNull(name);
        requireNonNull(params);
        this.source = source;
        this.name = name;
        this.params = params;
        this.startTimeInMillis = System.currentTimeMillis();
    }

    /**
     * Gets the method name
     *
     * @return the method name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the method params
     *
     * @return the method params
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * Gets the source
     *
     * @return the source
     */
    public T getSource() {
        return source;
    }

    /**
     * Gets the timestamp when the function is invoked.
     *
     * @return the timestamp when the function is invoked.
     */
    public Long getStartTimeInMillis() {
        return startTimeInMillis;
    }

    /**
     * Gets the end time in milliseconds
     *
     * @return the end time in milliseconds
     */
    @Nullable
    public Long getEndTimeInMillis() {
        return endTimeInMillis;
    }

    /**
     * Flags that the execution is completed and record current timestamp.
     */
    public void executionDone() {
        endTimeInMillis = System.currentTimeMillis();
    }

    /**
     * Calculates and gets the time elapsed in milliseconds.
     *
     * @return the time elapsed in milliseconds.
     */
    public Long getTimeElapsedInMillis() {
        return endTimeInMillis - startTimeInMillis;
    }

    /**
     * Creates an instance for representing {@link WebDriver} method.
     *
     * @param methodName the current invoked driver method name
     * @param params the parameters of the invoked method
     * @param source the source object
     * @param <T> the source type
     * @return the created method info instance for driver
     */
    public static <T> MethodInfo<T> create(T source, String methodName, Object... params) {
        return new MethodInfo<>(source, methodName, params);
    }
}
