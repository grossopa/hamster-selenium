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
package com.github.grossopa.selenium.examples.recorder;

import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;
import java.util.List;

/**
 * The example of recording a local plain HTML page with the html component framework. It writes an embedded login
 * page to {@code target/recorder-examples}, launches Chrome, scans the page, selects a few elements and generates
 * the page object into {@code target/recorder-generated}.
 *
 * <p>The browser driver executable is resolved automatically by Selenium Manager. Run this class as a normal Java
 * application.</p>
 *
 * @author Jack Yin
 * @since 1.15
 * @see RecorderSession
 */
public class RecorderLocalHtmlShowCase {

    /**
     * Runs the example.
     *
     * @param args the command line arguments, not used
     */
    public static void main(String[] args) {
        String loginUrl = RecorderExampleSupport.writeSamplePages();
        RecorderConfig config = RecorderConfig.builder()
                .framework(ComponentFramework.of("html"))
                .keyAttribute("data-testid")
                .extraSelector("table")
                .outputDir(Path.of("target", "recorder-generated"))
                .basePackage("com.example.pageobjects")
                .build();
        WebDriver driver = RecorderExampleSupport.createDriver();
        try (RecorderSession session = new RecorderSession(driver, config)) {
            session.getDriver().get(loginUrl);

            List<ScannedElement> elements = session.scan();
            RecorderExampleSupport.printScannedElements(elements);

            session.select(0, "username");
            session.select(1, "password");
            session.select(2, "countrySelect");
            session.select(3, "loginButton");
            session.select(4, "resetButton");

            RecorderExampleSupport.printGeneratedFiles(session.generate());
        }
    }
}
