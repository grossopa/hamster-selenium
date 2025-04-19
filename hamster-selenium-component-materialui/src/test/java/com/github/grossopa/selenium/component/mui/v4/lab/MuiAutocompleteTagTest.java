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

package com.github.grossopa.selenium.component.mui.v4.lab;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiAutocompleteTag}
 *
 * @author Jack Yin
 * @since 1.3
 */
class MuiAutocompleteTagTest {

    MuiAutocompleteTag testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);
    MuiAutocompleteTagLocators locators = mock(MuiAutocompleteTagLocators.class);
    WebElement button = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(locators.getLabelFinder()).thenReturn(tag -> "labelFinder");
        when(locators.getValueFinder()).thenReturn(tag -> "valueFinder");
        when(locators.getDeleteButtonLocator()).thenReturn(By.id("aaa"));

        when(element.findElement(By.id("aaa"))).thenReturn(button);
        testSubject = new MuiAutocompleteTag(element, driver, config, locators);
    }

    @Test
    void constructor1() {
        MuiAutocompleteTag tag = new MuiAutocompleteTag(element, driver, config);
        assertEquals(By.className("MuiChip-deleteIcon"), tag.getLocators().getDeleteButtonLocator());
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        assertEquals("Autocomplete-tag", testSubject.getComponentName());
    }

    @Test
    void getLabel() {
        assertEquals("labelFinder", testSubject.getLabel());
    }

    @Test
    void getValue() {
        assertEquals("valueFinder", testSubject.getValue());
    }

    @Test
    void getDeleteButton() {
        assertEquals(button, testSubject.getDeleteButton().getWrappedElement());
    }

    @Test
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        MuiAutocompleteTagLocators locators1 = mock(MuiAutocompleteTagLocators.class);
        WebElement element2 = mock(WebElement.class);
        MuiAutocompleteTagLocators locators2 = mock(MuiAutocompleteTagLocators.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new MuiAutocompleteTag(element1, driver, config, locators1),
                new MuiAutocompleteTag(element1, driver, config, locators1));
        tester.addEqualityGroup(new MuiAutocompleteTag(element1, driver, config, locators2));
        tester.addEqualityGroup(new MuiAutocompleteTag(element2, driver, config, locators1));
        tester.addEqualityGroup(new MuiAutocompleteTag(element2, driver, config, locators2));
        tester.testEquals();
    }

    @Test
    void testToString() {
        WebElement element = mock(WebElement.class);
        when(element.toString()).thenReturn("element-toString");
        MuiAutocompleteTagLocators locators = mock(MuiAutocompleteTagLocators.class);
        when(locators.toString()).thenReturn("locators-toString");
        testSubject = new MuiAutocompleteTag(element, driver, config, locators);
        assertEquals("MuiAutocompleteTag{locators=locators-toString, element=element-toString}",
                testSubject.toString());
    }
}
