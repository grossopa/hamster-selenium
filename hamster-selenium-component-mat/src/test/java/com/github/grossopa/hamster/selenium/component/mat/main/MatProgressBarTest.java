/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatProgressBar}
 *
 * @author Jack Yin
 * @since 1.7
 */
class MatProgressBarTest {

    MatProgressBar testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getTagPrefix()).thenReturn("mat-");
        when(config.getCssPrefix()).thenReturn("mat-");
        testSubject = new MatProgressBar(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("ProgressBar", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-progress-bar");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getDomAttribute("class")).thenReturn("mat-progress-bar-23");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element");
        assertEquals("MatProgressBar{element=element}", testSubject.toString());
    }


    @Test
    void getMinValue() {
        when(element.getDomAttribute("aria-valuemin")).thenReturn("233");
        assertEquals("233", testSubject.getMinValue());
    }

    @Test
    void getMaxValue() {
        when(element.getDomAttribute("aria-valuemax")).thenReturn("3333");
        assertEquals("3333", testSubject.getMaxValue());
    }

    @Test
    void getValue() {
        when(element.getDomAttribute("aria-valuenow")).thenReturn("4");
        assertEquals("4", testSubject.getValue());
    }

    @Test
    void getMode() {
        when(element.getDomAttribute("mode")).thenReturn("query");
        assertEquals(MatProgressBar.Mode.QUERY, testSubject.getMode());
    }
}
