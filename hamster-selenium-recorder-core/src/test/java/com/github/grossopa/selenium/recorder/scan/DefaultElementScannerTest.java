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

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorType;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.scan.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link DefaultElementScanner}
 *
 * @author Jack Yin
 * @since 1.15
 */
class DefaultElementScannerTest {

    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    RecorderConfig config = RecorderConfig.builder().keyAttribute("data-testid").extraSelector("[data-x]").build();
    DefaultElementScanner testSubject = new DefaultElementScanner(config);

    @BeforeEach
    void setUp() {
        when(driver.executeScript(anyString(), any(), any(), any(), any())).thenReturn(
                List.of(Map.of("tagName", "button", "text", "Login", "attributes",
                                Map.of("id", "login-btn", "data-testid", "login")),
                        Map.of("tagName", "input", "text", "", "attributes", Map.of("name", "password"))));
    }

    @Test
    void testScan() {
        List<ScannedElement> result = testSubject.scan(driver);
        assertEquals(2, result.size());

        ScannedElement first = result.get(0);
        assertEquals(0, first.getIndex());
        assertEquals("button", first.getTagName());
        assertEquals("Login", first.getText());
        assertEquals("login-btn", first.getAttributes().get("id"));

        // id > custom attribute > marker (text strategy is not in default list)
        List<LocatorCandidate> candidates = first.getLocatorCandidates();
        assertEquals(3, candidates.size());
        assertEquals(LocatorType.ID, candidates.get(0).getType());
        assertEquals(LocatorType.CSS_SELECTOR, candidates.get(1).getType());
        assertEquals("[data-testid=\"login\"]", candidates.get(1).getValue());
        assertEquals(LocatorCandidate.PRIORITY_MARKER, candidates.get(2).getPriority());

        ScannedElement second = result.get(1);
        assertEquals(LocatorType.NAME, second.getBestLocator().getType());
        assertEquals("password", second.getBestLocator().getValue());
    }

    @Test
    void testScanWithNonListResult() {
        when(driver.executeScript(anyString(), any(), any(), any(), any())).thenReturn("invalid");
        assertTrue(testSubject.scan(driver).isEmpty());
    }

    @Test
    void testScanExcludesElementWithNoMatchingStrategy() {
        when(driver.executeScript(anyString(), any(), any(), any(), any())).thenReturn(
                List.of(Map.of("tagName", "div", "text", "", "attributes", Map.of())));
        List<ScannedElement> result = testSubject.scan(driver);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateDefaultLocatorCandidateStrategies() {
        List<LocatorCandidateStrategy> strategies = DefaultElementScanner.createDefaultLocatorCandidateStrategies(config);
        assertEquals(3, strategies.size());
        assertInstanceOf(IdLocatorCandidateStrategy.class, strategies.get(0));
        assertInstanceOf(NameLocatorCandidateStrategy.class, strategies.get(1));
        assertInstanceOf(CustomAttributeLocatorCandidateStrategy.class, strategies.get(2));
    }

    @Test
    void testGetStrategies() {
        List<LocatorCandidateStrategy> strategies = testSubject.getStrategies();
        assertEquals(3, strategies.size());
    }

    @Test
    void testMarkerLocator() {
        assertEquals(By.xpath("*[@data-hamster-rec-idx=\"3\"]"), DefaultElementScanner.markerLocator(3));
    }

    @Test
    void testClearMarkers() {
        DefaultElementScanner.clearMarkers(driver);
        verify(driver).executeScript(anyString());
    }
}
