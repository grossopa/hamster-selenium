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

import java.util.List;
import java.util.Map;

/**
 * A strategy for building locator candidates from the scanned element data. Each strategy focuses on one specific
 * locator source, e.g. the {@code id} attribute, the {@code name} attribute, a customized key attribute, the visible
 * text or the temporary marker attribute.
 *
 * <p>Multiple strategies are composed via
 * {@link com.github.grossopa.selenium.recorder.scan.DefaultElementScanner#createDefaultLocatorCandidateStrategies(
 * com.github.grossopa.selenium.recorder.config.RecorderConfig)} to form the full set of locator candidates for a
 * scanned element. If none of the strategies produces a candidate, the element is excluded from the scan result.</p>
 *
 * @author Jack Yin
 * @since 1.15
 * @see LocatorCandidate
 * @see com.github.grossopa.selenium.recorder.scan.DefaultElementScanner
 */
public interface LocatorCandidateStrategy {

    /**
     * Builds locator candidates from the given scanned element data.
     *
     * @param index the scan index of the element
     * @param tagName the tag name of the element in lower case
     * @param attributes the snapshot of the key attributes (only the present ones)
     * @param text the visible text of the element
     * @return the list of locator candidates produced by this strategy, empty if this strategy does not match
     */
    List<LocatorCandidate> toCandidates(int index, String tagName, Map<String, String> attributes, String text);
}
