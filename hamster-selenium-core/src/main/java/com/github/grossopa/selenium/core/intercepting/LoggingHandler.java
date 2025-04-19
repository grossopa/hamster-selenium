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

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Prints log before, after and when exception happens with time spent in millisecond.
 *
 * @author Jack Yin
 * @since 1.4
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
        this(thresholdInMillis, Logger.getLogger("com.github.grossopa.selenium.core.intercepting.LoggingHandler"));
    }

    /**
     * Constructs and instance with threshold milliseconds for printing log.
     *
     * @param thresholdInMillis only when the time spent is longer than the threshold then log will be printed. 0 for
     * printing all actions.
     * @param log the customized logger
     */
    public LoggingHandler(long thresholdInMillis, Logger log) {
        requireNonNull(log);
        this.thresholdInMillis = thresholdInMillis;
        this.log = log;
    }

    @Override
    public void onBefore(MethodInfo<?> methodInfo) {
        log.fine(() -> format("{0}({1})\n      Source: {2}\n", methodInfo.getName(), buildParamsString(methodInfo),
                methodInfo.getSource()));
    }

    @Override
    public void onAfter(MethodInfo<?> methodInfo, Object resultValue) {
        if (methodInfo.getEndTimeInMillis() == null) {
            methodInfo.executionDone();
        }
        if (methodInfo.getTimeElapsedInMillis() > thresholdInMillis) {
            log.info(() -> buildLoggingString(methodInfo) + buildResultString(resultValue));
        }
    }

    @Override
    public void onException(MethodInfo<?> methodInfo, Exception exception) {
        if (methodInfo.getEndTimeInMillis() == null) {
            methodInfo.executionDone();
        }
        log.log(Level.SEVERE, exception, () -> buildLoggingString(methodInfo));
    }

    private String buildParamsString(MethodInfo<?> methodInfo) {
        return defaultIfBlank(join(methodInfo.getParams(), ", "), "");
    }

    private String buildLoggingString(MethodInfo<?> methodInfo) {
        return format("{0}({1})      Time spent {2}ms.\n      Source: {3}\n", methodInfo.getName(),
                buildParamsString(methodInfo), methodInfo.getTimeElapsedInMillis(), methodInfo.getSource());
    }

    @SuppressWarnings({"rawtypes", "java:S6212"})
    private String buildResultString(Object resultValue) {
        String result = "      Result: {0}\n";
        if (resultValue instanceof Map) {
            result = "      Result: count: " + ((Map) resultValue).size() + ", {0}\n";
        } else if (resultValue instanceof Collection) {
            result = "      Result: count: " + ((Collection) resultValue).size() + ", {0}\n";
        }
        return format(result, resultValue);
    }

    /**
     * Gets the threshold for logging.
     *
     * @return the threshold for logging.
     */
    public long getThresholdInMillis() {
        return thresholdInMillis;
    }

    /**
     * Gets the logger instance
     *
     * @return the logger instance
     */
    public Logger getLog() {
        return log;
    }
}
