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
 * Tests for {@link MatSelectionList}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatSelectionListTest {

    MatSelectionList testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getTagPrefix()).thenReturn("mat-");
        when(config.getCssPrefix()).thenReturn("mat-");

        testSubject = new MatSelectionList(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("SelectionList", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-selection-list");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getDomAttribute("class")).thenReturn("mat-selection-list-23");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element");
        assertEquals("MatSelectionList{element=element}", testSubject.toString());
    }

    @Test
    void getListOptions() {
        WebElement listItem1 = mock(WebElement.class);
        WebElement listItem2 = mock(WebElement.class);
        when(element.findElements(By.className("mat-list-option"))).thenReturn(newArrayList(listItem1, listItem2));

        assertEquals(2, testSubject.getListOptions().size());
    }

}
