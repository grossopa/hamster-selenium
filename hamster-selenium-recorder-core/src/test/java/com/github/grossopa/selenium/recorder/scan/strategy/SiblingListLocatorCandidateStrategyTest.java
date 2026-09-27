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
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link SiblingListLocatorCandidateStrategy}
 *
 * @author Jack Yin
 * @since 1.16
 */
class SiblingListLocatorCandidateStrategyTest {

    @Test
    void testToCandidatesWithMultipleSiblings() {
        SiblingListLocatorCandidateStrategy testSubject = new SiblingListLocatorCandidateStrategy();
        LocatorContext context = new LocatorContext(null, 3, Map.of());

        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Click", context);
        assertEquals(1, result.size());
        assertEquals(LocatorType.CSS_SELECTOR, result.get(0).getType());
        assertEquals("button", result.get(0).getValue());
        assertEquals(LocatorCandidate.PRIORITY_SIBLING_LIST, result.get(0).getPriority());
        assertTrue(result.get(0).isList());
    }

    @Test
    void testToCandidatesWithSingleSibling() {
        SiblingListLocatorCandidateStrategy testSubject = new SiblingListLocatorCandidateStrategy();
        LocatorContext context = new LocatorContext(null, 1, Map.of());

        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Click", context);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithRootCssClass() {
        SiblingListLocatorCandidateStrategy testSubject = new SiblingListLocatorCandidateStrategy("MuiButton-root");
        AncestorInfo ancestor = new AncestorInfo("form1", null, ".MuiButton-root");
        LocatorContext context = new LocatorContext(ancestor, 4, Map.of());

        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Click", context);
        assertEquals(1, result.size());
        assertEquals("#form1 > .MuiButton-root", result.get(0).getValue());
        assertTrue(result.get(0).isList());
    }

    @Test
    void testToCandidatesWithAncestorTestId() {
        SiblingListLocatorCandidateStrategy testSubject = new SiblingListLocatorCandidateStrategy("MuiChip-root");
        AncestorInfo ancestor = new AncestorInfo(null, "chips", ".MuiChip-root");
        LocatorContext context = new LocatorContext(ancestor, 5, Map.of());

        List<LocatorCandidate> result = testSubject.toCandidates(0, "span", Map.of(), "", context);
        assertEquals(1, result.size());
        assertEquals("[data-testid=\"chips\"] > .MuiChip-root", result.get(0).getValue());
    }

    @Test
    void testToCandidatesWithNoAncestor() {
        SiblingListLocatorCandidateStrategy testSubject = new SiblingListLocatorCandidateStrategy("MuiTab-root");
        LocatorContext context = new LocatorContext(null, 3, Map.of());

        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Tab", context);
        assertEquals(1, result.size());
        assertEquals(".MuiTab-root", result.get(0).getValue());
    }

    @Test
    void testToCandidatesFourParamReturnsEmpty() {
        SiblingListLocatorCandidateStrategy testSubject = new SiblingListLocatorCandidateStrategy();
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Click");
        assertTrue(result.isEmpty());
    }
}
