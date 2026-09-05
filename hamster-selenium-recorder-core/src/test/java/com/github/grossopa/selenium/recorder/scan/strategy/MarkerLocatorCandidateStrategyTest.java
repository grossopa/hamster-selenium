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
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link MarkerLocatorCandidateStrategy}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MarkerLocatorCandidateStrategyTest {

    MarkerLocatorCandidateStrategy testSubject = new MarkerLocatorCandidateStrategy();

    @Test
    void testToCandidates() {
        List<LocatorCandidate> result = testSubject.toCandidates(5, "button", Map.of(), "Login");
        assertEquals(1, result.size());
        assertEquals(LocatorType.CSS_SELECTOR, result.get(0).getType());
        assertEquals("[data-hamster-rec-idx=\"5\"]", result.get(0).getValue());
        assertEquals(LocatorCandidate.PRIORITY_MARKER, result.get(0).getPriority());
        assertEquals("by scan index 5", result.get(0).getDescription());
    }

    @Test
    void testToCandidatesAlwaysReturns() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "div", Map.of(), "");
        assertEquals(1, result.size());
    }
}
