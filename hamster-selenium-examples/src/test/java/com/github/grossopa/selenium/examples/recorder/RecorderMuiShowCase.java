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

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;
import java.util.List;

/**
 * The example of recording the official Material UI website with the mui component framework. It opens the text
 * field documentation page of mui.com, scans the page, selects the demo text fields by their ids and generates the
 * page object into {@code target/recorder-generated}.
 *
 * <p>This example requires internet access. Requires the Edge Driver Service running on port 38383 (start via
 * {@code StartDriverServiceEdge}). Run this class as a normal Java application.</p>
 *
 * @author Jack Yin
 * @since 1.15
 * @see RecorderSession
 */
public class RecorderMuiShowCase {

    private static final String TEXT_FIELD_PAGE_URL = "https://mui.com/material-ui/react-text-field/";

    /**
     * Runs the example.
     *
     * @param args the command line arguments, not used
     */
    public static void main(String[] args) {
        RecorderConfig config = RecorderConfig.builder()
                .framework(ComponentFramework.of("mui"))
                .muiVersion(MuiVersion.V5)
                .outputDir(Path.of("target", "recorder-generated"))
                .basePackage("com.example.pageobjects")
                .build();
        WebDriver driver = RecorderExampleSupport.createDriver();
        try (RecorderSession session = new RecorderSession(driver, config)) {
            session.getDriver().get(TEXT_FIELD_PAGE_URL);

            // scroll to top to ensure the sticky header (including product selector) is fully visible
            session.getDriver().executeScript("window.scrollTo(0, 0)");

            List<ScannedElement> elements = session.scan();
            RecorderExampleSupport.printScannedElements(elements);

            selectById(session, elements, "outlined-basic", "basicTextField");
            selectById(session, elements, "outlined-password-input", "passwordField");
            selectById(session, elements, "mui-product-selector", "productSelector");

            RecorderExampleSupport.printGeneratedFiles(session.generate());
        }
    }

    private static void selectById(RecorderSession session, List<ScannedElement> elements, String id,
            String fieldName) {
        elements.stream().filter(element -> id.equals(element.getAttributes().get("id"))).findFirst().ifPresentOrElse(
                element -> session.select(element.getIndex(), fieldName),
                () -> System.out.println("Element with id " + id + " not found on the page, skipped."));
    }
}
