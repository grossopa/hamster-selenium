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
package com.github.grossopa.selenium.recorder.monitor;

import jakarta.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * An event emitted by the runtime monitor when the user interacts with the page or the page changes.
 *
 * @author Jack Yin
 * @since 1.15
 * @see RecorderEventType
 * @see RecorderEventListener
 */
public class RecorderEvent {

    private final RecorderEventType type;
    private final String methodName;
    private final String url;
    private final Exception exception;
    private final long timestampInMillis;

    /**
     * Constructs an instance with the event details.
     *
     * @param type the event type, must not be null
     * @param methodName the intercepted method name, e.g. "element.click", must not be null
     * @param url the url when the event happens, null if not available
     * @param exception the exception, only present for {@link RecorderEventType#EXCEPTION}
     */
    public RecorderEvent(RecorderEventType type, String methodName, @Nullable String url,
            @Nullable Exception exception) {
        this.type = requireNonNull(type);
        this.methodName = requireNonNull(methodName);
        this.url = url;
        this.exception = exception;
        this.timestampInMillis = System.currentTimeMillis();
    }

    /**
     * Gets the event type.
     *
     * @return the event type
     */
    public RecorderEventType getType() {
        return type;
    }

    /**
     * Gets the intercepted method name, e.g. "element.click".
     *
     * @return the intercepted method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Gets the url when the event happens, null if not available.
     *
     * @return the url when the event happens
     */
    @Nullable
    public String getUrl() {
        return url;
    }

    /**
     * Gets the exception, only present for {@link RecorderEventType#EXCEPTION}.
     *
     * @return the exception, null for non-exception events
     */
    @Nullable
    public Exception getException() {
        return exception;
    }

    /**
     * Gets the timestamp when the event is created.
     *
     * @return the timestamp in milliseconds
     */
    public long getTimestampInMillis() {
        return timestampInMillis;
    }

    @Override
    public String toString() {
        return "RecorderEvent{" + "type=" + type + ", methodName='" + methodName + '\'' + ", url='" + url + '\''
                + ", timestampInMillis=" + timestampInMillis + '}';
    }
}
