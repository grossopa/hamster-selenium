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

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiAutocompleteTagLocators}
 *
 * @author Jack Yin
 * @since 1.3
 */
class MuiAutocompleteTagLocatorsTest {

    MuiAutocompleteTagLocators testSubject;
    Function<MuiAutocompleteTag, String> labelFinder = tag -> "labelFinder";
    Function<MuiAutocompleteTag, String> valueFinder = tag -> "valueFinder";
    By deleteButtonLocator = By.className("ccc");

    @BeforeEach
    void setUp() {
        testSubject = new MuiAutocompleteTagLocators(labelFinder, valueFinder, deleteButtonLocator);
    }


    @Test
    void getLabelFinder() {
        assertEquals("labelFinder", testSubject.getLabelFinder().apply(null));
    }

    @Test
    void getValueFinder() {
        assertEquals("valueFinder", testSubject.getValueFinder().apply(null));
    }

    @Test
    void getDeleteButtonLocator() {
        assertEquals(By.className("ccc"), testSubject.getDeleteButtonLocator());
    }

    @Test
    void chipLocators() {
        MuiConfig config = mock(MuiConfig.class);
        when(config.getCssPrefix()).thenReturn("Mui");
        MuiAutocompleteTag tag = mock(MuiAutocompleteTag.class);
        WebComponent labelComponent = mock(WebComponent.class);
        when(tag.findComponent(By.tagName("span"))).thenReturn(labelComponent);
        when(labelComponent.getText()).thenReturn("abc");
        MuiAutocompleteTagLocators locators = MuiAutocompleteTagLocators.chipLocators(config);

        assertEquals(By.className(config.getCssPrefix() + "Chip-deleteIcon"), locators.getDeleteButtonLocator());
        assertEquals(locators.getLabelFinder(), locators.getValueFinder());
        assertEquals("abc", locators.getLabelFinder().apply(tag));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testEquals() {
        Function<MuiAutocompleteTag, String> labelFinder1 = mock(Function.class);
        Function<MuiAutocompleteTag, String> labelFinder2 = mock(Function.class);
        Function<MuiAutocompleteTag, String> valueFinder1 = mock(Function.class);
        Function<MuiAutocompleteTag, String> valueFinder2 = mock(Function.class);
        By deleteButtonLocator1 = mock(By.class);
        By deleteButtonLocator2 = mock(By.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new MuiAutocompleteTagLocators(labelFinder1, valueFinder1, deleteButtonLocator1),
                new MuiAutocompleteTagLocators(labelFinder1, valueFinder1, deleteButtonLocator1));
        tester.addEqualityGroup(new MuiAutocompleteTagLocators(labelFinder2, valueFinder1, deleteButtonLocator1));
        tester.addEqualityGroup(new MuiAutocompleteTagLocators(labelFinder1, valueFinder2, deleteButtonLocator1));
        tester.addEqualityGroup(new MuiAutocompleteTagLocators(labelFinder1, valueFinder1, deleteButtonLocator2));

        tester.testEquals();
    }

    @Test
    @SuppressWarnings("unchecked")
    void testToString() {
        Function<MuiAutocompleteTag, String> labelFinder = mock(Function.class);
        when(labelFinder.toString()).thenReturn("labelFinder-toString");
        Function<MuiAutocompleteTag, String> valueFinder = mock(Function.class);
        when(valueFinder.toString()).thenReturn("valueFinder-toString");
        testSubject = new MuiAutocompleteTagLocators(labelFinder, valueFinder, deleteButtonLocator);
        assertEquals("MuiAutocompleteTagLocators{labelFinder=labelFinder-toString, "
                + "valueFinder=valueFinder-toString, deleteButtonLocator=By.className: ccc}", testSubject.toString());
    }
}
