/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.component.html;

import org.hamster.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests for {@link HtmlSelect}
 *
 * @author Jack Yin
 * @since 1.0
 */
class HtmlSelectTest {

    HtmlSelect testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    List<WebElement> options;

    private WebElement createOption(String value, String label, Integer index, boolean selected) {
        WebElement element = mock(WebElement.class);
        when(element.getAttribute("value")).thenReturn(value);
        when(element.getText()).thenReturn(label);
        when(element.getAttribute("index")).thenReturn(String.valueOf(index));
        when(element.isSelected()).thenReturn(selected);
        doAnswer(a -> {
            when(element.isSelected()).thenReturn(!selected);
            return null;
        }).when(element).click();
        return element;
    }

    @BeforeEach
    void setUp() {
        options = asList(createOption("val1", "Label 1", 0, false), createOption("val2", "Label 2", 1, true),
                createOption("val3", "Label 3", 2, false));
        when(element.getTagName()).thenReturn("select");
        when(element.findElements(eq(By.tagName("option")))).thenReturn(options);
        when(element.getAttribute("multiple")).thenReturn("true");
        testSubject = new HtmlSelect(element, driver);
    }

    @Test
    void isMultiple() {
        assertTrue(testSubject.isMultiple());
    }

    @Test
    void getOptions() {
        assertEquals(3, testSubject.getOptions().size());
    }

    @Test
    void getAllSelectedOptions() {
        assertEquals(1, testSubject.getAllSelectedOptions().size());
    }

    @Test
    void getFirstSelectedOption() {
        assertEquals("Label 2", testSubject.getFirstSelectedOption().getText());
    }

    @Test
    void selectByVisibleText() {
        when(element.findElements(eq(By.xpath(".//option[normalize-space(.) = " + Quotes.escape("Label 1") + "]"))))
                .thenReturn(options);
        testSubject.selectByVisibleText("Label 1");
        assertEquals("Label 1", testSubject.getFirstSelectedOption().getText());
    }

    @Test
    void selectByIndex() {
        testSubject.selectByIndex(0);
        assertEquals("Label 1", testSubject.getFirstSelectedOption().getText());
    }

    @Test
    void selectByValue() {
        when(element.findElements(eq(By.xpath(".//option[@value = " + Quotes.escape("val1") + "]"))))
                .thenReturn(options);
        testSubject.selectByValue("val1");
        assertEquals("Label 1", testSubject.getFirstSelectedOption().getText());
    }

    @Test
    void deselectAll() {
        testSubject.deselectAll();
        assertEquals(0, testSubject.getAllSelectedOptions().size());
    }

    @Test
    void deselectByValue() {
        when(element.findElements(eq(By.xpath(".//option[@value = " + Quotes.escape("val1") + "]"))))
                .thenReturn(options);
        testSubject.deselectByValue("val1");
        assertEquals(0, testSubject.getAllSelectedOptions().size());
    }

    @Test
    void deselectByIndex() {
        testSubject.deselectByIndex(1);
        assertEquals(0, testSubject.getAllSelectedOptions().size());
    }

    @Test
    void deselectByVisibleText() {
        when(element.findElements(eq(By.xpath(".//option[normalize-space(.) = " + Quotes.escape("Label 1") + "]"))))
                .thenReturn(options);
        testSubject.deselectByVisibleText("Label 1");
        assertEquals(0, testSubject.getAllSelectedOptions().size());
    }
}
