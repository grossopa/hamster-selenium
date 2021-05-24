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

package com.github.grossopa.selenium.component.html;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link HtmlTableRow}
 *
 * @author Jack Yin
 * @since 1.0
 */
class HtmlTableRowTest {

    HtmlTableRow testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);

    @BeforeEach
    void setUp() {
        testSubject = new HtmlTableRow(element, driver, By.tagName("td"), asList("aa", "bb"));
    }

    @Test
    void validate() {
        when(element.getTagName()).thenReturn("tr");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getTagName()).thenReturn("td");
        assertFalse(testSubject.validate());
    }

    @Test
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new HtmlTableRow(element1, driver, By.tagName("td"), asList("aa", "bb")));
        tester.addEqualityGroup(new HtmlTableRow(element1, driver, By.tagName("td1"), asList("aa", "bb")));
        tester.addEqualityGroup(new HtmlTableRow(element1, driver, By.tagName("td"), asList("aa1", "bb")));
        tester.addEqualityGroup(new HtmlTableRow(element2, driver, By.tagName("td"), asList("aa1", "bb")));
        tester.testEquals();
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("HtmlTableRow{colsLocator=By.tagName: td, headerLabels=[aa, bb], element=element-toString}",
                testSubject.toString());
    }
}
