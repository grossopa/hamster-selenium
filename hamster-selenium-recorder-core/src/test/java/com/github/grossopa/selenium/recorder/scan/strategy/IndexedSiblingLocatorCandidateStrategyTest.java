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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link IndexedSiblingLocatorCandidateStrategy}
 *
 * @author Jack Yin
 * @since 1.17
 */
class IndexedSiblingLocatorCandidateStrategyTest {

    IndexedSiblingLocatorCandidateStrategy testSubject = new IndexedSiblingLocatorCandidateStrategy();

    @Test
    void testToCandidatesWithAncestorId() {
        AncestorInfo ancestor = new AncestorInfo("form1", null, "//*[contains(@class,' MuiButton-root ')]");
        LocatorContext context = new LocatorContext(ancestor, 3, 2, Map.of());

        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Submit", context);
        assertEquals(1, result.size());
        assertEquals(LocatorType.XPATH, result.get(0).getType());
        assertEquals("//*[@id='form1']/child::*[local-name()='button'][2]", result.get(0).getValue());
        assertEquals(LocatorCandidate.PRIORITY_INDEXED_SIBLING, result.get(0).getPriority());
        assertFalse(result.get(0).isList());
    }

    @Test
    void testToCandidatesWithAncestorTestId() {
        AncestorInfo ancestor = new AncestorInfo(null, "actions", "//*[contains(@class,' MuiButton-root ')]");
        LocatorContext context = new LocatorContext(ancestor, 3, 1, Map.of());

        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Save", context);
        assertEquals(1, result.size());
        assertEquals("//*[@data-testid='actions']/child::*[local-name()='button'][1]",
                result.get(0).getValue());
    }

    @Test
    void testToCandidatesWithoutAncestor() {
        LocatorContext context = new LocatorContext(null, 3, 3, Map.of());

        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Delete", context);
        assertEquals(1, result.size());
        assertEquals("//*[local-name()='button'][3]", result.get(0).getValue());
        assertEquals(LocatorCandidate.PRIORITY_INDEXED_SIBLING, result.get(0).getPriority());
    }

    @Test
    void testToCandidatesWithSingleSibling() {
        LocatorContext context = new LocatorContext(null, 1, 1, Map.of());
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Click", context);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithZeroSiblingIndex() {
        LocatorContext context = new LocatorContext(null, 3, 0, Map.of());
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Click", context);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithAncestorBothNull() {
        AncestorInfo ancestor = new AncestorInfo(null, null, "//*[local-name()='span']");
        LocatorContext context = new LocatorContext(ancestor, 3, 2, Map.of());

        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Submit", context);
        assertEquals(1, result.size());
        assertEquals("//*[local-name()='button'][2]", result.get(0).getValue());
    }

    @Test
    void testToCandidatesFourParamReturnsEmpty() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Submit");
        assertTrue(result.isEmpty());
    }
}
