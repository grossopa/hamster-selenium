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

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static java.util.logging.Level.WARNING;
import static org.apache.commons.lang3.ObjectUtils.getIfNull;

/**
 * Prints log before, after and when exception happens with time spent in millisecond.
 *
 * @author Jack Yin
 * @since 1.12
 */
public class LoggingHandler implements InterceptingHandler {

    private final long thresholdInMillis;
    private final Logger log;

    /**
     * Constructs and instance with threshold milliseconds for printing log.
     *
     * @param thresholdInMillis only when the time spent is longer than the threshold then log will be printed. 0 for
     * printing all actions.
     */
    public LoggingHandler(long thresholdInMillis) {
        this(thresholdInMillis, Logger.getLogger(LoggingHandler.class.getName()));
    }

    /**
     * Constructs and instance with threshold milliseconds for printing log.
     *
     * @param thresholdInMillis only when the time spent is longer than the threshold then log will be printed. 0 for
     * printing all actions.
     * @param log the customized logger
     */
    public LoggingHandler(long thresholdInMillis, Logger log) {
        this.thresholdInMillis = thresholdInMillis;
        this.log = requireNonNull(log);
    }

    @Override
    public void onBefore(MethodInfo<?> methodInfo) {
        // do nothing on before
    }

    @Override
    public void onAfter(MethodInfo<?> methodInfo, Object resultValue) {
        if (getIfNull(methodInfo.getTimeElapsedInMillis(), 0L) >= thresholdInMillis) {
            log.log(Level.INFO, () -> format("Method {0} with params {1} on source {2} finished in {3} ms with result {4}",
                    methodInfo.getName(), methodInfo.getParams(), methodInfo.getSource(),
                    methodInfo.getTimeElapsedInMillis(), resultValue));
        }
    }

    @Override
    public void onException(MethodInfo<?> methodInfo, Exception exception) {
        log.log(WARNING, exception, () -> format("Method {0} with params {1} on source {2} failed after {3} ms with exception {4}",
                        methodInfo.getName(), methodInfo.getParams(), methodInfo.getSource(),
                        methodInfo.getTimeElapsedInMillis(), exception.getMessage()));
    }
}