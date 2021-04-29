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

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.safari.SafariDriverService;

import java.io.File;

/**
 * Creates the {@link DriverService} action from the {@link DriverConfig}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class CreateDriverServiceAction implements WebDriverType.WebDriverTypeFunction<DriverConfig, DriverService> {

    @Override
    public DriverService applyChrome(DriverConfig config) {
        ChromeDriverService.Builder builder = new ChromeDriverService.Builder();
        enrichCommons(config, builder);
        // withVerbose
        // withSilent
        return builder.build();
    }

    @Override
    public DriverService applyEdge(DriverConfig config) {
        EdgeDriverService.Builder builder = new EdgeDriverService.Builder();
        enrichCommons(config, builder);
        // nothing to add for Edge
        return builder.build();
    }

    @Override
    public DriverService applyFirefox(DriverConfig config) {
        GeckoDriverService.Builder builder = new GeckoDriverService.Builder();
        enrichCommons(config, builder);
        // usingFirefoxBinary
        return builder.build();
    }

    @Override
    public DriverService applyIE(DriverConfig config) {
        InternetExplorerDriverService.Builder builder = new InternetExplorerDriverService.Builder();
        enrichCommons(config, builder);
        // withLogLevel
        // withHost
        // withExtractPath
        // withSilent
        return builder.build();
    }

    @Override
    public DriverService applyOpera(DriverConfig config) {
        OperaDriverService.Builder builder = new OperaDriverService.Builder();
        enrichCommons(config, builder);
        // withVerbose
        // withSilent
        return builder.build();
    }

    @Override
    public DriverService applySafari(DriverConfig config) {
        SafariDriverService.Builder builder = new SafariDriverService.Builder();
        enrichCommons(config, builder);
        // usingTechnologyPreview
        return builder.build();
    }

    private void enrichCommons(DriverConfig config, DriverService.Builder<?, ?> builder) {
        builder.usingDriverExecutable(new File(config.getDriverExecutablePath()));
        if (config.getPort() != null && config.getPort() > 0) {
            builder.usingPort(config.getPort());
        } else {
            builder.usingAnyFreePort();
        }
        if (config.getLogFilePath() != null) {
            builder.withLogFile(new File(config.getLogFilePath()));
        }
        if (config.getEnvironment() != null) {
            builder.withEnvironment(config.getEnvironment());
        }
    }
}
