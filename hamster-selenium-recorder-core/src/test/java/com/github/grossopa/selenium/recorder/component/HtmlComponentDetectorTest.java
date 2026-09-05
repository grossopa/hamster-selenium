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
package com.github.grossopa.selenium.recorder.component;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link HtmlComponentDetector}
 *
 * @author Jack Yin
 * @since 1.15
 */
class HtmlComponentDetectorTest {

    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    WebElement element = mock(WebElement.class);
    HtmlComponentDetector testSubject = new HtmlComponentDetector();

    @Test
    void testDetectSelect() {
        when(element.getTagName()).thenReturn("SELECT");
        Optional<DetectedComponent> result = testSubject.detect(element, driver);
        assertTrue(result.isPresent());
        assertEquals("HtmlSelect", result.get().getTypeName());
        assertEquals("toSelect", result.get().getFactoryMethodName());
        assertEquals(ComponentFramework.HTML, result.get().getFramework());
    }

    @Test
    void testDetectTable() {
        when(element.getTagName()).thenReturn("table");
        Optional<DetectedComponent> result = testSubject.detect(element, driver);
        assertTrue(result.isPresent());
        assertEquals("HtmlTable", result.get().getTypeName());
        assertEquals("toTable", result.get().getFactoryMethodName());
    }

    @Test
    void testDetectUnknownTag() {
        when(element.getTagName()).thenReturn("div");
        assertTrue(testSubject.detect(element, driver).isEmpty());
    }
}
