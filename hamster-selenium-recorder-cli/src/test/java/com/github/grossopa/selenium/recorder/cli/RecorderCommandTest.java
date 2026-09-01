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

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link RecorderCommand}
 *
 * @author Jack Yin
 * @since 1.15
 */
class RecorderCommandTest {

    @Test
    void testDefaultConfig() {
        RecorderCommand testSubject = new RecorderCommand();
        RecorderConfig config = testSubject.buildConfig();
        assertEquals(ComponentFramework.MUI, config.getFramework());
        assertEquals(MuiVersion.V4, config.getMuiVersion());
        assertEquals(Path.of("generated-pageobjects"), config.getOutputDir());
        assertEquals(RecorderConfig.DEFAULT_BASE_PACKAGE, config.getBasePackage());
        assertEquals(List.of("id", "name"), config.getKeyAttributes());
    }

    @Test
    void testParsedOptions() {
        RecorderCommand testSubject = new RecorderCommand();
        new CommandLine(testSubject).parseArgs("--framework", "html", "--mui-version", "v5", "-a",
                "data-testid,foo", "--output-dir", "out", "--package", "com.example.po");
        RecorderConfig config = testSubject.buildConfig();
        assertEquals(ComponentFramework.HTML, config.getFramework());
        assertEquals(MuiVersion.V5, config.getMuiVersion());
        assertTrue(config.getKeyAttributes().containsAll(List.of("id", "name", "data-testid", "foo")));
        assertEquals(Path.of("out"), config.getOutputDir());
        assertEquals("com.example.po", config.getBasePackage());
    }

    @Test
    void testParsedSelectors() {
        RecorderCommand testSubject = new RecorderCommand();
        new CommandLine(testSubject).parseArgs("-s", "table,form");
        RecorderConfig config = testSubject.buildConfig();
        assertEquals(List.of("table", "form"), config.getExtraSelectors());
    }

    @Test
    void testInvalidFramework() {
        RecorderCommand testSubject = new RecorderCommand();
        new CommandLine(testSubject).parseArgs("--framework", "unknown");
        assertThrows(IllegalArgumentException.class, testSubject::buildConfig);
    }

    @Test
    void testHelpReturnsZeroWithoutLaunchingBrowser() {
        assertEquals(0, new CommandLine(new RecorderCommand()).execute("--help"));
    }
}
