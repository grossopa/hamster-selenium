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
package com.github.grossopa.selenium.recorder.model;

import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link ScannedElement}
 *
 * @author Jack Yin
 * @since 1.15
 */
class ScannedElementTest {

    LocatorCandidate marker = new LocatorCandidate(LocatorType.CSS_SELECTOR, "[data-hamster-rec-idx=\"0\"]",
            LocatorCandidate.PRIORITY_MARKER, "by scan index 0");
    LocatorCandidate id = new LocatorCandidate(LocatorType.ID, "login", LocatorCandidate.PRIORITY_ID,
            "by id \"login\"");

    ScannedElement testSubject = new ScannedElement(0, "button", Map.of("id", "login"), "Login",
            List.of(marker, id));

    @Test
    void testGetters() {
        assertEquals(0, testSubject.getIndex());
        assertEquals("button", testSubject.getTagName());
        assertEquals(Map.of("id", "login"), testSubject.getAttributes());
        assertEquals("Login", testSubject.getText());
        assertEquals(2, testSubject.getLocatorCandidates().size());
        assertNull(testSubject.getDetectedComponent());
    }

    @Test
    void testGetBestLocator() {
        assertEquals(id, testSubject.getBestLocator());
        assertNull(new ScannedElement(1, "a", Map.of(), "", List.of()).getBestLocator());
    }

    @Test
    void testSetDetectedComponent() {
        DetectedComponent detected = new DetectedComponent(ComponentFramework.MUI, "Button", "MuiButton",
                "com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton", "toButton", false);
        testSubject.setDetectedComponent(detected);
        assertEquals(detected, testSubject.getDetectedComponent());
        assertTrue(testSubject.toString().contains("MuiButton"));
    }
}
