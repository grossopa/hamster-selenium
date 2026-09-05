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
package com.github.grossopa.selenium.recorder.scan;

import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorType;
import com.github.grossopa.selenium.recorder.scan.strategy.CustomAttributeLocatorCandidateStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link CustomAttributeLocatorCandidateStrategy}
 *
 * @author Jack Yin
 * @since 1.15
 */
class CustomAttributeLocatorCandidateStrategyTest {

    CustomAttributeLocatorCandidateStrategy testSubject = new CustomAttributeLocatorCandidateStrategy(
            List.of("id", "name", "data-testid", "aria-label"));

    @Test
    void testToCandidatesWithCustomAttribute() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button",
                Map.of("data-testid", "submit-btn"), "Submit");
        assertEquals(1, result.size());
        assertEquals(LocatorType.CSS_SELECTOR, result.get(0).getType());
        assertEquals("[data-testid=\"submit-btn\"]", result.get(0).getValue());
        assertEquals(LocatorCandidate.PRIORITY_CUSTOM_ATTRIBUTE, result.get(0).getPriority());
    }

    @Test
    void testToCandidatesSkipsIdAndName() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button",
                Map.of("id", "btn", "name", "submit"), "Submit");
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithMultipleCustomAttributes() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button",
                Map.of("data-testid", "btn", "aria-label", "Submit"), "Submit");
        assertEquals(2, result.size());
    }

    @Test
    void testToCandidatesWithBlankValue() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button",
                Map.of("data-testid", "  "), "Submit");
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithValueContainingQuotes() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button",
                Map.of("data-testid", "has\"quote"), "Submit");
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithNoMatchingAttributes() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button",
                Map.of("class", "primary"), "Submit");
        assertTrue(result.isEmpty());
    }
}
