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
import com.github.grossopa.selenium.recorder.model.LocatorType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link NameLocatorCandidateStrategy}
 *
 * @author Jack Yin
 * @since 1.15
 */
class NameLocatorCandidateStrategyTest {

    NameLocatorCandidateStrategy testSubject = new NameLocatorCandidateStrategy();

    @Test
    void testToCandidatesWithName() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "input", Map.of("name", "password"), "");
        assertEquals(1, result.size());
        assertEquals(LocatorType.NAME, result.get(0).getType());
        assertEquals("password", result.get(0).getValue());
        assertEquals(LocatorCandidate.PRIORITY_NAME, result.get(0).getPriority());
    }

    @Test
    void testToCandidatesWithoutName() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "input", Map.of("id", "field"), "");
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithBlankName() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "input", Map.of("name", "  "), "");
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithDuplicateName() {
        LocatorContext context = new LocatorContext(null, 3, Map.of("gender", 3));
        List<LocatorCandidate> result = testSubject.toCandidates(0, "input", Map.of("name", "gender"), "", context);
        assertEquals(1, result.size());
        assertEquals(LocatorType.NAME, result.get(0).getType());
        assertEquals("gender", result.get(0).getValue());
        assertEquals(NameLocatorCandidateStrategy.PRIORITY_DUPLICATE_NAME, result.get(0).getPriority());
        assertTrue(result.get(0).isList());
    }

    @Test
    void testToCandidatesWithUniqueName() {
        LocatorContext context = new LocatorContext(null, 3, Map.of("username", 1));
        List<LocatorCandidate> result = testSubject.toCandidates(0, "input", Map.of("name", "username"), "", context);
        assertEquals(1, result.size());
        assertEquals(LocatorCandidate.PRIORITY_NAME, result.get(0).getPriority());
        assertFalse(result.get(0).isList());
    }

    @Test
    void testToCandidatesWithContextButNoName() {
        LocatorContext context = new LocatorContext(null, 3, Map.of());
        List<LocatorCandidate> result = testSubject.toCandidates(0, "input", Map.of("id", "field"), "", context);
        assertTrue(result.isEmpty());
    }
}
