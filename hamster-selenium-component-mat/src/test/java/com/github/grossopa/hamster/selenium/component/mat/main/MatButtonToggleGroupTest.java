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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatButtonToggleGroup}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatButtonToggleGroupTest {

    MatButtonToggleGroup testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    WebElement buttonToggle1 = mock(WebElement.class);
    WebElement buttonToggle2 = mock(WebElement.class);


    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("mat-");
        when(config.getTagPrefix()).thenReturn("mat-");
        when(element.findElements(By.xpath("./mat-button-toggle[contains(@class,\"mat-button-toggle\")]"))).thenReturn(
                newArrayList(buttonToggle1, buttonToggle2));
        testSubject = new MatButtonToggleGroup(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("ButtonToggleGroup", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-button-toggle-group 123");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-button-toggle-group-123 123");
        assertFalse(testSubject.validate());
    }

    @Test
    void getButtonToggles() {
        assertEquals(2, testSubject.getButtonToggles().size());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatButtonToggleGroup{element=inner-element}", testSubject.toString());
    }
}
