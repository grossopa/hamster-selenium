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

import org.openqa.selenium.Capabilities;

/**
 * The parameters for {@link CreateWebDriverFromRunningServiceAction}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class RunningServiceParams {
    private final Capabilities options;
    private final String url;

    /**
     * Constructs an instance with options an url.
     *
     * @param options the options of the web driver service
     * @param url the target url of the running web driver service
     */
    public RunningServiceParams(Capabilities options, String url) {
        this.options = options;
        this.url = url;
    }

    /**
     * Gets the options.
     *
     * @return the options
     */
    public Capabilities getOptions() {
        return options;
    }

    /**
     * Gets the url of the running web driver service.
     *
     * @return the url of the running web driver service.
     */
    public String getUrl() {
        return url;
    }
}
