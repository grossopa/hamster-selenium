/*
 * Copyright © 2020 the original author or authors.
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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriverBuilder;

import static org.openqa.selenium.remote.RemoteWebDriver.builder;

/**
 * Creates the {@link WebDriver} action. This method requires the {@link org.openqa.selenium.remote.service.DriverService}
 * to be created together.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class CreateWebDriverAction implements WebDriverType.WebDriverTypeFunction<CreateWebDriverParams, WebDriver> {

    @Override
    public WebDriver applyChrome(CreateWebDriverParams input) {
        return doBuild(getBuilder(), input);
    }

    @Override
    public WebDriver applyEdge(CreateWebDriverParams input) {
        return doBuild(getBuilder(), input);
    }

    @Override
    public WebDriver applyFirefox(CreateWebDriverParams input) {
        return doBuild(getBuilder(), input);
    }

    @Override
    public WebDriver applyIE(CreateWebDriverParams input) {
        return doBuild(getBuilder(), input);
    }

    @Override
    public WebDriver applyOpera(CreateWebDriverParams input) {
        return doBuild(getBuilder(), input);
    }

    @Override
    public WebDriver applySafari(CreateWebDriverParams input) {
        return doBuild(getBuilder(), input);
    }

    protected WebDriver doBuild(RemoteWebDriverBuilder builder, CreateWebDriverParams input) {
        return builder.addAlternative(input.getOptions()).withDriverService(input.getDriverService()).build();
    }

    /**
     * For Unit testing purpose
     *
     * @return the instance of the builder
     */
    protected RemoteWebDriverBuilder getBuilder() {
        return builder();
    }
}
