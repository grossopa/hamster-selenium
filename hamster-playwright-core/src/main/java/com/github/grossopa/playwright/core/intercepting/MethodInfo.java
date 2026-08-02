/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.core.intercepting;

import static java.util.Objects.requireNonNull;

/**
 * The method information of current invoking
 *
 * @since 1.12
 */
public class MethodInfo<T> {

    private final String name;
    private final Object[] params;
    private final T source;
    private final Long startTimeInMillis;
    private Long endTimeInMillis;

    /**
     * Constructs an instance with method name, method params and the element that invokes the method.
     *
     * @param source the source that invokes the method
     * @param name the method name that currently being invoked
     * @param params the method params
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
     * Gets the start time in milliseconds
     *
     * @return the start time in milliseconds
     */
    public Long getStartTimeInMillis() {
        return startTimeInMillis;
    }

    /**
     * Gets the end time in milliseconds
     *
     * @return the end time in milliseconds, or null if not yet set
     */
    public Long getEndTimeInMillis() {
        return endTimeInMillis;
    }

    /**
     * Marks the execution as done.
     */
    public void executionDone() {
        this.endTimeInMillis = System.currentTimeMillis();
    }

    /**
     * Gets the time elapsed in milliseconds
     *
     * @return the time elapsed in milliseconds, or null if end time is not yet set
     */
    public Long getTimeElapsedInMillis() {
        if (endTimeInMillis == null) {
            return null;
        }
        return endTimeInMillis - startTimeInMillis;
    }

    /**
     * Creates a new instance
     *
     * @param source the source that invokes the method
     * @param name the method name that currently being invoked
     * @param params the method params
     * @param <T> the source type
     * @return the created instance
     */
    public static <T> MethodInfo<T> create(T source, String name, Object... params) {
        return new MethodInfo<>(source, name, params);
    }
}