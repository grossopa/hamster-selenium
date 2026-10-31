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
package com.github.grossopa.utils.intercepting;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Abstract base class for {@link InterceptingHandler} implementations that provides common logging infrastructure
 * including threshold-based logging and configurable logger instances.
 *
 * <p>
 * Subclasses should implement the {@link #onBefore(MethodInfo)}, {@link #onAfter(MethodInfo, Object)} and
 * {@link #onException(MethodInfo, Exception)} methods to define framework-specific logging behavior.
 * </p>
 *
 * <pre>
 * <code>
 * public class MyLoggingHandler extends AbstractLoggingHandler {
 *     public MyLoggingHandler(long thresholdInMillis) {
 *         super(thresholdInMillis);
 *     }
 *
 *     {@literal @}Override
 *     public void onBefore(MethodInfo&lt;?&gt; methodInfo) {
 *         // custom logging
 *     }
 *
 *     // ... other overrides
 * }
 * </code>
 * </pre>
 *
 * @author Jack Yin
 * @since 1.19
 * @see InterceptingHandler
 * @see MethodInfo
 */
public abstract class AbstractLoggingHandler implements InterceptingHandler {

    /**
     * The threshold in milliseconds. Only actions taking longer than this value will be logged.
     */
    protected final long thresholdInMillis;

    /**
     * The logger instance used for outputting log messages.
     */
    protected final Logger log;

    /**
     * Constructs an instance with threshold milliseconds for printing log using a default logger named after this
     * class.
     *
     * @param thresholdInMillis only when the time spent is longer than the threshold then log will be printed. 0 for
     * printing all actions.
     */
    protected AbstractLoggingHandler(long thresholdInMillis) {
        this(thresholdInMillis, Logger.getLogger(AbstractLoggingHandler.class.getName()));
    }

    /**
     * Constructs an instance with threshold milliseconds for printing log.
     *
     * @param thresholdInMillis only when the time spent is longer than the threshold then log will be printed. 0 for
     * printing all actions.
     * @param log the customized logger
     */
    protected AbstractLoggingHandler(long thresholdInMillis, Logger log) {
        this.thresholdInMillis = thresholdInMillis;
        this.log = requireNonNull(log);
    }

    /**
     * Gets the threshold for logging.
     *
     * @return the threshold for logging in milliseconds
     */
    public long getThresholdInMillis() {
        return thresholdInMillis;
    }

    /**
     * Gets the logger instance.
     *
     * @return the logger instance
     */
    public Logger getLog() {
        return log;
    }
}
