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

package com.github.grossopa.selenium.examples.helper;

import com.github.grossopa.selenium.core.driver.*;
import com.github.grossopa.selenium.examples.StartDriverService;
import lombok.SneakyThrows;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.DefaultComponentWebDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;


/**
 * The parent class of managing the driver
 *
 * @author Jack Yin
 * @since 1.0
 */
public abstract class AbstractBrowserSupport {

    public static final String EXECUTABLE_PATH = "D://software/drivers/chromedriver-84.exe";

    protected ComponentWebDriver driver;

    @SneakyThrows
    public void setUpDriver(WebDriverType type) {
        DriverConfig config = new DriverConfig();
        config.setDriverExecutablePath(EXECUTABLE_PATH);
        config.setDriverVersion("85");
        config.setType(WebDriverType.CHROME);

        Capabilities options = config.getType().apply(new CreateOptionsAction(), null);
        WebDriver temp = config.getType()
                .apply(new CreateWebDriverFromRunningServiceAction(), new RunningServiceParams(options,
                        "http://localhost:" + StartDriverService.PORT));

        driver = new DefaultComponentWebDriver(temp);
    }

    public void stopDriver() {
        // driver.close();
    }

}