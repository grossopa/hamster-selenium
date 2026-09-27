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
import com.github.grossopa.selenium.recorder.model.LocatorContext;
import com.github.grossopa.selenium.recorder.model.LocatorContext.AncestorInfo;
import com.github.grossopa.selenium.recorder.model.LocatorType;

import java.util.List;
import java.util.Map;

/**
 * A strategy that builds a locator candidate from the ancestor anchor information. When the scanned element has no
 * {@code id} or {@code name} but one of its ancestors has an {@code id} or a configured anchor attribute (e.g.
 * {@code data-testid}), this strategy generates a CSS selector combining the ancestor anchor with the child path.
 *
 * <p>Examples of generated locators:
 * <ul>
 * <li>Ancestor with id: {@code #formSection .MuiButton-root}</li>
 * <li>Ancestor with test id: {@code [data-testid="header"] .MuiTextField-root}</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.16
 * @see LocatorCandidate#PRIORITY_ANCESTOR
 * @see LocatorContext
 */
public class AncestorLocatorCandidateStrategy implements LocatorCandidateStrategy {

    @Override
    public List<LocatorCandidate> toCandidates(int index, String tagName, Map<String, String> attributes, String text) {
        return List.of();
    }

    @Override
    public List<LocatorCandidate> toCandidates(int index, String tagName, Map<String, String> attributes, String text,
            LocatorContext context) {
        AncestorInfo ancestor = context.getAncestor();
        if (ancestor == null) {
            return List.of();
        }

        String cssValue;
        String description;
        if (ancestor.id() != null) {
            cssValue = "#" + ancestor.id() + " " + ancestor.childPath();
            description = "by ancestor #" + ancestor.id() + " -> " + ancestor.childPath();
        } else if (ancestor.testId() != null) {
            cssValue = "[data-testid=\"" + ancestor.testId() + "\"] " + ancestor.childPath();
            description = "by ancestor data-testid \"" + ancestor.testId() + "\" -> " + ancestor.childPath();
        } else {
            return List.of();
        }

        return List.of(new LocatorCandidate(LocatorType.CSS_SELECTOR, cssValue, LocatorCandidate.PRIORITY_ANCESTOR,
                description));
    }
}
