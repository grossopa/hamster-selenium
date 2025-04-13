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

package org.hamster.selenium.core.driver;

import org.openqa.selenium.remote.service.DriverService;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Map;

/**
 * The overall configuration for building an instance of {@link org.openqa.selenium.WebDriver} instance.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class DriverConfig {
    private WebDriverType type;
    private String driverVersion;
    private String driverExecutablePath;
    private Integer port;
    private String logFilePath;
    private Map<String, String> environment;

    /**
     * Gets the driver type
     *
     * @return the driver type
     */
    public WebDriverType getType() {
        return type;
    }

    /**
     * Sets the driver type
     *
     * @param type
     *         the driver type to set
     */
    public void setType(WebDriverType type) {
        this.type = type;
    }

    /**
     * Optional, gets the driver version (specially for Chrome Driver)
     *
     * @return the driver version
     */
    @Nullable
    public String getDriverVersion() {
        return driverVersion;
    }

    /**
     * Optional, sets the driver version (specially for Chrome Driver)
     *
     * @param driverVersion
     *         the driver version to set
     */
    public void setDriverVersion(@Nullable String driverVersion) {
        this.driverVersion = driverVersion;
    }

    /**
     * Gets the driver executable file path
     *
     * @return the driver executable file path
     * @see org.openqa.selenium.remote.service.DriverService.Builder#usingDriverExecutable(File)
     */
    public String getDriverExecutablePath() {
        return driverExecutablePath;
    }

    /**
     * Sets the driver executable file path
     *
     * @param driverExecutablePath
     *         the driver executable file path to set
     * @see org.openqa.selenium.remote.service.DriverService.Builder#usingDriverExecutable(File)
     */
    public void setDriverExecutablePath(String driverExecutablePath) {
        this.driverExecutablePath = driverExecutablePath;
    }

    /**
     * Optional, when the port is less than 1 or null, it will invoke {@link DriverService.Builder#usingAnyFreePort()},
     * else if will invoke {@link DriverService.Builder#usingPort(int)}.
     *
     * @return the port number
     * @see DriverService.Builder#usingAnyFreePort()
     * @see DriverService.Builder#usingPort(int)
     */
    @Nullable
    public Integer getPort() {
        return port;
    }

    /**
     * Optional, when the port is less than 1 or null, it will invoke {@link DriverService.Builder#usingAnyFreePort()},
     * else if will invoke {@link DriverService.Builder#usingPort(int)}.
     *
     * @param port
     *         port number to set
     * @see DriverService.Builder#usingAnyFreePort()
     * @see DriverService.Builder#usingPort(int)
     */
    public void setPort(@Nullable Integer port) {
        this.port = port;
    }

    /**
     * Optional, delegates {@link DriverService.Builder#withLogFile(File)}.
     *
     * @return the log file path
     * @see DriverService.Builder#withLogFile(File)
     */
    @Nullable
    public String getLogFilePath() {
        return logFilePath;
    }

    /**
     * Optional, delegates {@link DriverService.Builder#withLogFile(File)}.
     *
     * @param logFilePath
     *         the log file path to set
     * @see DriverService.Builder#withLogFile(File)
     */
    public void setLogFilePath(@Nullable String logFilePath) {
        this.logFilePath = logFilePath;
    }

    /**
     * Optional, delegates {@link DriverService.Builder#withEnvironment(Map)}
     *
     * @return the environment
     * @see DriverService.Builder#withEnvironment(Map)
     */
    @Nullable
    public Map<String, String> getEnvironment() {
        return environment;
    }

    /**
     * Optional, delegates {@link DriverService.Builder#withEnvironment(Map)}
     *
     * @param environment
     *         the environment to set
     * @see DriverService.Builder#withEnvironment(Map)
     */
    public void setEnvironment(@Nullable Map<String, String> environment) {
        this.environment = environment;
    }
}
