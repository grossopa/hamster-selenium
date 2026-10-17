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
package com.github.grossopa.selenium.recorder.scan.strategy;

import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorType;
import com.github.grossopa.selenium.recorder.scan.DefaultElementScanner;

import java.util.List;
import java.util.Map;

import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;

/**
 * A strategy that builds a locator candidate from the visible text of the scanned element using an xpath with
 * {@code normalize-space()}.
 *
 * <p>The text must be non-blank, must not contain both single and double quotes simultaneously, must not contain
 * newline or carriage return characters, and must not exceed the configured maximum length
 * {@link DefaultElementScanner#MAX_TEXT_LENGTH}.</p>
 *
 * <p>The xpath is built via {@link com.github.grossopa.selenium.core.locator.By2#xpathBuilder()} which handles quote
 * escaping automatically through
 * {@link com.github.grossopa.selenium.core.util.SeleniumUtils#enrichQuote(String)}.</p>
 *
 * @author Jack Yin
 * @since 1.15
 * @see LocatorCandidate#PRIORITY_TEXT
 */
public class TextLocatorCandidateStrategy implements LocatorCandidateStrategy {

    @Override
    public List<LocatorCandidate> toCandidates(int index, String tagName, Map<String, String> attributes, String text) {
        if (!text.isBlank() && !(text.contains("'") && text.contains("\"")) && !text.contains("\n")
                && !text.contains("\r") && text.length() <= DefaultElementScanner.MAX_TEXT_LENGTH) {
            String xpathValue = xpathBuilder().anywhere(tagName).normalizeSpace().exact(text).xpathString();
            return List.of(new LocatorCandidate(LocatorType.XPATH, xpathValue, LocatorCandidate.PRIORITY_TEXT,
                    "by text \"" + text + "\""));
        }
        return List.of();
    }
}
