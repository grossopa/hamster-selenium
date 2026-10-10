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
package com.github.grossopa.selenium.core.driver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link CreateDriverServiceAction}
 *
 * @author Jack Yin
 * @since 1.0
 */
class CreateDriverServiceActionTest {

    CreateDriverServiceAction testSubject;

    @TempDir
    Path tempDir;

    File driverExecutable;
    File logFile;

    @BeforeEach
    void setUp() throws IOException {
        testSubject = new CreateDriverServiceAction();
        driverExecutable = new File(tempDir.toFile(), "driver");
        assertTrue(driverExecutable.createNewFile());
        assertTrue(driverExecutable.setExecutable(true));

        logFile = new File(tempDir.toFile(), "driver.log");
    }

    private DriverConfig createConfig(Integer port, File logFile, Map<String, String> environment) {
        DriverConfig config = new DriverConfig();
        config.setDriverExecutablePath(driverExecutable.getAbsolutePath());
        config.setPort(port);
        if (logFile != null) {
            config.setLogFilePath(logFile.getAbsolutePath());
        }
        if (environment != null) {
            config.setEnvironment(environment);
        }
        return config;
    }

    @Test
    void applyChromeWithFreePort() {
        DriverConfig config = createConfig(null, null, null);
        DriverService result = testSubject.applyChrome(config);
        assertNotNull(result);
        assertTrue(result.getUrl() != null);
    }

    @Test
    void applyChromeWithSpecificPort() {
        DriverConfig config = createConfig(19876, null, null);
        DriverService result = testSubject.applyChrome(config);
        assertNotNull(result);
        assertEquals(19876, result.getUrl().getPort());
    }

    @Test
    void applyChromeWithLogAndEnvironment() {
        Map<String, String> env = new HashMap<>();
        env.put("TEST_KEY", "TEST_VALUE");
        DriverConfig config = createConfig(null, logFile, env);
        DriverService result = testSubject.applyChrome(config);
        assertNotNull(result);
    }

    @Test
    void applyEdgeWithFreePort() {
        DriverConfig config = createConfig(null, null, null);
        DriverService result = testSubject.applyEdge(config);
        assertNotNull(result);
    }

    @Test
    void applyFirefoxWithFreePort() {
        DriverConfig config = createConfig(null, null, null);
        DriverService result = testSubject.applyFirefox(config);
        assertNotNull(result);
    }

    @Test
    void applyIEWithFreePort() {
        DriverConfig config = createConfig(null, null, null);
        DriverService result = testSubject.applyIE(config);
        assertNotNull(result);
    }

    @Test
    void applySafariWithFreePort() {
        DriverConfig config = createConfig(null, null, null);
        DriverService result = testSubject.applySafari(config);
        assertNotNull(result);
    }

    @Test
    void applyWithZeroPortUsesFreePort() {
        DriverConfig config = createConfig(0, null, null);
        DriverService result = testSubject.applyEdge(config);
        assertNotNull(result);
        assertTrue(result.getUrl().getPort() > 0);
    }
}
