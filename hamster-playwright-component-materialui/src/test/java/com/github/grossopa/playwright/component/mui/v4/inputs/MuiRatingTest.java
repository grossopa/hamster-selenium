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
package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.BoundingBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiRatingTest {
    MuiRating testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiRating(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Rating", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void isReadOnlyTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiRating-root Mui-readOnly");
        assertTrue(testSubject.isReadOnly());
    }

    @Test
    void isReadOnlyFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiRating-root");
        assertFalse(testSubject.isReadOnly());
    }

    @Test
    void isReadOnlyNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isReadOnly());
    }

    private void mockStars(Locator... starLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(starLocators));
        // editable ratings expose the value via the checked input, default to no checked input
        Locator checkedInputsLocator = mock(Locator.class);
        when(checkedInputsLocator.all()).thenReturn(List.of());
        when(locator.locator("input:checked")).thenReturn(checkedInputsLocator);
    }

    /**
     * Mocks a 2-stars rating with precision 0.5 (4 icons in 2 decimal containers).
     *
     * @param filledIcons the number of icons with the MuiRating-iconFilled class
     * @return the mocked icon locators
     */
    private List<Locator> mockDecimalRating(int filledIcons) {
        List<Locator> icons = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Locator icon = mock(Locator.class);
            String className = i < filledIcons ? "MuiRating-icon MuiRating-iconFilled" : "MuiRating-icon";
            when(icon.getAttribute("class")).thenReturn(className);
            icons.add(icon);
        }
        Locator starsLocator = mock(Locator.class);
        when(starsLocator.all()).thenReturn(icons);
        Locator decimalsLocator = mock(Locator.class);
        when(decimalsLocator.all()).thenReturn(List.of(mock(Locator.class), mock(Locator.class)));
        Locator checkedInputsLocator = mock(Locator.class);
        when(checkedInputsLocator.all()).thenReturn(List.of());
        when(locator.locator(".MuiRating-icon")).thenReturn(starsLocator);
        when(locator.locator(".MuiRating-decimal")).thenReturn(decimalsLocator);
        when(locator.locator("input:checked")).thenReturn(checkedInputsLocator);
        return icons;
    }

    @Test
    void getStars() {
        Locator star1 = mock(Locator.class);
        Locator star2 = mock(Locator.class);
        mockStars(star1, star2);
        List<WebComponent> stars = testSubject.getStars();
        assertEquals(2, stars.size());
    }

    @Test
    void getMaxValue() {
        mockStars(mock(Locator.class), mock(Locator.class), mock(Locator.class));
        assertEquals(3, testSubject.getMaxValue());
    }

    @Test
    void getValueReturnsThree() {
        Locator star1 = mock(Locator.class);
        Locator star2 = mock(Locator.class);
        Locator star3 = mock(Locator.class);
        when(star1.getAttribute("class")).thenReturn("MuiRating-icon MuiRating-iconFilled");
        when(star2.getAttribute("class")).thenReturn("MuiRating-icon MuiRating-iconFilled");
        when(star3.getAttribute("class")).thenReturn("MuiRating-icon MuiRating-iconFilled");
        mockStars(star1, star2, star3);
        assertEquals(3.0, testSubject.getValue());
    }

    @Test
    void getValueReturnsZeroWhenNoFilled() {
        Locator star1 = mock(Locator.class);
        Locator star2 = mock(Locator.class);
        when(star1.getAttribute("class")).thenReturn("MuiRating-icon");
        when(star2.getAttribute("class")).thenReturn("MuiRating-icon");
        mockStars(star1, star2);
        assertEquals(0.0, testSubject.getValue());
    }

    @Test
    void getValueReturnsZeroWhenNoStars() {
        mockStars();
        assertEquals(0.0, testSubject.getValue());
    }

    @Test
    void getValueFromCheckedInput() {
        mockStars();
        Locator checkedInput = mock(Locator.class);
        when(checkedInput.getAttribute("value")).thenReturn("2.5");
        Locator checkedInputsLocator = mock(Locator.class);
        when(checkedInputsLocator.all()).thenReturn(List.of(checkedInput));
        when(locator.locator("input:checked")).thenReturn(checkedInputsLocator);
        assertEquals(2.5, testSubject.getValue());
    }

    @Test
    void getValueFromCheckedEmptyInput() {
        mockStars();
        Locator checkedInput = mock(Locator.class);
        when(checkedInput.getAttribute("value")).thenReturn("");
        Locator checkedInputsLocator = mock(Locator.class);
        when(checkedInputsLocator.all()).thenReturn(List.of(checkedInput));
        when(locator.locator("input:checked")).thenReturn(checkedInputsLocator);
        assertEquals(0.0, testSubject.getValue());
    }

    @Test
    void getValueReadOnlyWithPrecision() {
        mockDecimalRating(3);
        assertEquals(1.5, testSubject.getValue());
    }

    @Test
    void getPrecision() {
        mockStars(mock(Locator.class), mock(Locator.class));
        assertEquals(1.0, testSubject.getPrecision());
    }

    @Test
    void getPrecisionWithDecimals() {
        mockDecimalRating(0);
        assertEquals(0.5, testSubject.getPrecision());
    }

    @Test
    void getMaxValueWithDecimals() {
        mockDecimalRating(0);
        assertEquals(2, testSubject.getMaxValue());
    }

    @Test
    void setValueClicksStar() {
        Locator star1 = mock(Locator.class);
        Locator star2 = mock(Locator.class);
        when(star1.getAttribute("class")).thenReturn("MuiRating-icon");
        when(star2.getAttribute("class")).thenReturn("MuiRating-icon");
        mockStars(star1, star2);
        testSubject.setValue(2);
        verify(star2).click();
    }

    @Test
    void setValueZeroDoesNotClick() {
        Locator star1 = mock(Locator.class);
        mockStars(star1);
        testSubject.setValue(0);
        verify(star1, never()).click();
    }

    @Test
    void setValueTooLargeThrows() {
        mockStars(mock(Locator.class));
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(5));
    }

    @Test
    void setValueNegativeThrows() {
        mockStars(mock(Locator.class));
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(-1));
    }

    @Test
    void setValueDoubleClicksStar() {
        Locator star1 = mock(Locator.class);
        Locator star2 = mock(Locator.class);
        mockStars(star1, star2);
        testSubject.setValue(2.0);
        verify(star2).click();
    }

    @Test
    void setValueDoubleWithPrecision() {
        List<Locator> icons = mockDecimalRating(3);
        Locator label = mock(Locator.class);
        when(icons.get(3).locator("xpath=..")).thenReturn(label);
        BoundingBox boundingBox = new BoundingBox();
        boundingBox.x = 0;
        boundingBox.y = 0;
        boundingBox.width = 20;
        boundingBox.height = 10;
        when(label.boundingBox()).thenReturn(boundingBox);

        testSubject.setValue(1.5);

        ArgumentCaptor<Locator.ClickOptions> captor = ArgumentCaptor.forClass(Locator.ClickOptions.class);
        verify(label).click(captor.capture());
        assertEquals(7.5, captor.getValue().position.x);
        assertEquals(5.0, captor.getValue().position.y);
    }

    @Test
    void setValueDoubleTooLargeThrows() {
        mockStars(mock(Locator.class));
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(1.5));
    }

    @Test
    void setValueDoubleNegativeThrows() {
        mockStars(mock(Locator.class));
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(-0.5));
    }
}
