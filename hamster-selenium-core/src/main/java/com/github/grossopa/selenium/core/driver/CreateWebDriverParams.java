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
import org.openqa.selenium.remote.service.DriverService;

/**
 * the parameter for creating the {@link org.openqa.selenium.WebDriver}
 *
 * @author Jack Yin
 * @since 1.0
 */
public class CreateWebDriverParams {
    private final Capabilities options;
    private final DriverService driverService;

    /**
     * Constructs an instance with options and given driver service
     *
     * @param options the options
     * @param driverService the driver service
     */
    public CreateWebDriverParams(Capabilities options, DriverService driverService) {
        this.options = options;
        this.driverService = driverService;
    }

    /**
     * Gets the options
     *
     * @return the options
     */
    public Capabilities getOptions() {
        return options;
    }

    /**
     * Gets the driver service
     *
     * @return the driver service
     */
    public DriverService getDriverService() {
        return driverService;
    }
}
