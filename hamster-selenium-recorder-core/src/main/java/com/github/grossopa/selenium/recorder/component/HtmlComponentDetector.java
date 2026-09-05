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
package com.github.grossopa.selenium.recorder.component;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import org.openqa.selenium.WebElement;

import java.util.Locale;
import java.util.Optional;

/**
 * Detects plain HTML components by their tag names, mapping {@code select} to {@code HtmlSelect} and {@code table} to
 * {@code HtmlTable}.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class HtmlComponentDetector implements ComponentDetector {

    private static final String HTML_PACKAGE = "com.github.grossopa.selenium.component.html.";

    @Override
    public Optional<DetectedComponent> detect(WebElement element, ComponentWebDriver driver) {
        String tagName = element.getTagName().toLowerCase(Locale.ROOT);
        return switch (tagName) {
            case "select" -> Optional.of(new DetectedComponent(ComponentFramework.HTML, "Select", "HtmlSelect",
                    HTML_PACKAGE + "HtmlSelect", "toSelect", false));
            case "table" -> Optional.of(new DetectedComponent(ComponentFramework.HTML, "Table", "HtmlTable",
                    HTML_PACKAGE + "HtmlTable", "toTable", false));
            default -> Optional.empty();
        };
    }
}
