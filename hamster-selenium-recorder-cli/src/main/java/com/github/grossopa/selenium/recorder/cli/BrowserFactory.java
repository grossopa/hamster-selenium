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
package com.github.grossopa.selenium.recorder.cli;

import com.github.grossopa.selenium.core.driver.CreateOptionsAction;
import com.github.grossopa.selenium.core.driver.WebDriverType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * Creates the {@link WebDriver} instance for the CLI based on the user selected browser, reusing the driver factory
 * of hamster selenium core. The browser driver executable is managed automatically by Selenium Manager.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class BrowserFactory {

    /**
     * private constructor
     */
    private BrowserFactory() {
        throw new AssertionError();
    }

    /**
     * Creates a new {@link WebDriver} of the given browser.
     *
     * @param browser the browser name such as "chrome", "edge" or "firefox", case insensitive, must not be null
     * @return the created web driver
     * @throws IllegalArgumentException if the browser name is not supported
     */
    public static WebDriver create(String browser) {
        requireNonNull(browser);
        WebDriverType type;
        try {
            type = WebDriverType.valueOf(browser.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unsupported browser: " + browser, exception);
        }
        Capabilities options = type.apply(new CreateOptionsAction(), null);
        return RemoteWebDriver.builder().addAlternative(options).build();
    }
}
