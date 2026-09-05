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
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link TextLocatorCandidateStrategy}
 *
 * @author Jack Yin
 * @since 1.15
 */
class TextLocatorCandidateStrategyTest {

    TextLocatorCandidateStrategy testSubject = new TextLocatorCandidateStrategy();

    @Test
    void testToCandidatesWithText() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "Login");
        assertEquals(1, result.size());
        assertEquals(LocatorType.XPATH, result.get(0).getType());
        assertEquals("//button[normalize-space()='Login']", result.get(0).getValue());
        assertEquals(LocatorCandidate.PRIORITY_TEXT, result.get(0).getPriority());
    }

    @Test
    void testToCandidatesWithBlankText() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "  ");
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithTextContainingSingleQuote() {
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), "it's");
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithTextExceedingMaxLength() {
        String longText = "a".repeat(DefaultElementScanner.MAX_TEXT_LENGTH + 1);
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), longText);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToCandidatesWithTextAtMaxLength() {
        String exactText = "a".repeat(DefaultElementScanner.MAX_TEXT_LENGTH);
        List<LocatorCandidate> result = testSubject.toCandidates(0, "button", Map.of(), exactText);
        assertEquals(1, result.size());
    }
}
