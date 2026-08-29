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

package com.github.grossopa.selenium.component.mui.v4.inputs;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiRating}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiRatingTest {

    MuiRating testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebComponent starComponent1 = mock(WebComponent.class);
    WebComponent starComponent2 = mock(WebComponent.class);
    WebComponent starComponent3 = mock(WebComponent.class);
    WebComponent starComponent4 = mock(WebComponent.class);
    WebComponent starComponent5 = mock(WebComponent.class);
    WebComponent starComponentParent1 = mock(WebComponent.class);
    WebComponent starComponentParent2 = mock(WebComponent.class);
    WebComponent starComponentParent3 = mock(WebComponent.class);
    WebComponent starComponentParent4 = mock(WebComponent.class);
    WebComponent starComponentParent5 = mock(WebComponent.class);

    List<WebComponent> starComponents = asList(starComponent1, starComponent2, starComponent3, starComponent4,
            starComponent5);

    @BeforeEach
    void setUp() {
        WebElement starElement1 = mock(WebElement.class);
        WebElement starElement2 = mock(WebElement.class);
        WebElement starElement3 = mock(WebElement.class);
        WebElement starElement4 = mock(WebElement.class);
        WebElement starElement5 = mock(WebElement.class);
        List<WebElement> starElements = asList(starElement1, starElement2, starElement3, starElement4, starElement5);

        when(element.findElements(By.className("MuiRating-icon"))).thenReturn(starElements);
        when(driver.mapElement(starElement1)).thenReturn(starComponent1);
        when(driver.mapElement(starElement2)).thenReturn(starComponent2);
        when(driver.mapElement(starElement3)).thenReturn(starComponent3);
        when(driver.mapElement(starElement4)).thenReturn(starComponent4);
        when(driver.mapElement(starElement5)).thenReturn(starComponent5);

        when(starComponent1.findComponent(By2.parent())).thenReturn(starComponentParent1);
        when(starComponent2.findComponent(By2.parent())).thenReturn(starComponentParent2);
        when(starComponent3.findComponent(By2.parent())).thenReturn(starComponentParent3);
        when(starComponent4.findComponent(By2.parent())).thenReturn(starComponentParent4);
        when(starComponent5.findComponent(By2.parent())).thenReturn(starComponentParent5);


        testSubject = new MuiRating(element, driver, config);
        when(config.getCssPrefix()).thenReturn("Mui");
    }

    /**
     * Mocks a rating with fractional precision, e.g. decimalCount 2 and iconCount 4 simulates a 2-stars rating
     * with precision 0.5.
     *
     * @param decimalCount the number of MuiRating-decimal spans
     * @param iconCount the number of MuiRating-icon spans
     */
    private void mockDecimalRating(int decimalCount, int iconCount) {
        List<WebElement> decimals = new ArrayList<>();
        for (int i = 0; i < decimalCount; i++) {
            decimals.add(mock(WebElement.class));
        }
        when(element.findElements(By.className("MuiRating-decimal"))).thenReturn(decimals);

        List<WebElement> icons = new ArrayList<>();
        for (int i = 0; i < iconCount; i++) {
            icons.add(mock(WebElement.class));
        }
        when(element.findElements(By.className("MuiRating-icon"))).thenReturn(icons);
        for (int i = 0; i < iconCount; i++) {
            when(driver.mapElement(icons.get(i))).thenReturn(starComponents.get(i));
        }
    }

    @Test
    void getComponentName() {
        assertEquals("Rating", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertTrue(testSubject.versions().contains(V4));
        assertTrue(testSubject.versions().contains(V5));
        assertTrue(testSubject.versions().contains(V6));
    }

    @Test
    void getValue() {
        when(starComponent3.attributeContains(eq("class"), any())).thenReturn(true);
        assertEquals(3.0, testSubject.getValue());
    }

    @Test
    void getValueFromCheckedInput() {
        WebElement checkedInput = mock(WebElement.class);
        when(checkedInput.getAttribute("value")).thenReturn("2.5");
        when(element.findElements(By.cssSelector("input:checked"))).thenReturn(List.of(checkedInput));
        assertEquals(2.5, testSubject.getValue());
    }

    @Test
    void getValueFromCheckedEmptyInput() {
        WebElement checkedInput = mock(WebElement.class);
        when(checkedInput.getAttribute("value")).thenReturn("");
        when(element.findElements(By.cssSelector("input:checked"))).thenReturn(List.of(checkedInput));
        assertEquals(0.0, testSubject.getValue());
    }

    @Test
    void getValueReadOnlyWithPrecision() {
        mockDecimalRating(2, 4);
        when(starComponent1.attributeContains("class", "MuiRating-iconFilled")).thenReturn(true);
        when(starComponent2.attributeContains("class", "MuiRating-iconFilled")).thenReturn(true);
        when(starComponent3.attributeContains("class", "MuiRating-iconFilled")).thenReturn(true);
        assertEquals(1.5, testSubject.getValue());
    }

    @Test
    void setValue() {
        testSubject.setValue(2);
        verify(starComponentParent2).click();
    }

    @Test
    void setValue_invalid() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(6));
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(-1));
    }

    @Test
    void setValueDoubleWithPrecision() {
        mockDecimalRating(2, 4);
        when(starComponentParent4.getSize()).thenReturn(new Dimension(20, 10));
        Actions actions = mock(Actions.class);
        when(driver.createActions()).thenReturn(actions);
        when(actions.moveToElement(starComponentParent4, -2, 5)).thenReturn(actions);
        when(actions.click()).thenReturn(actions);

        testSubject.setValue(1.5);

        verify(actions).perform();
    }

    @Test
    void setValueDouble_invalid() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(5.5));
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(-0.5));
    }

    @Test
    void setValueDoubleZeroDoesNotClick() {
        testSubject.setValue(0d);
        verify(starComponentParent1, never()).click();
        verify(starComponentParent2, never()).click();
    }

    @Test
    void getPrecision() {
        assertEquals(1.0, testSubject.getPrecision());
    }

    @Test
    void getPrecisionWithDecimals() {
        mockDecimalRating(2, 4);
        assertEquals(0.5, testSubject.getPrecision());
    }

    @Test
    void getMaxValue() {
        assertEquals(5, testSubject.getMaxValue());
    }

    @Test
    void getMaxValueWithDecimals() {
        mockDecimalRating(2, 4);
        assertEquals(2, testSubject.getMaxValue());
    }

    @Test
    void getStars() {
        List<WebComponent> stars = testSubject.getStars();
        assertEquals(5, stars.size());
        assertEquals(starComponent1, stars.get(0));
        assertEquals(starComponent2, stars.get(1));
        assertEquals(starComponent3, stars.get(2));
        assertEquals(starComponent4, stars.get(3));
        assertEquals(starComponent5, stars.get(4));
    }

    @Test
    void isReadOnly() {
        when(element.getDomAttribute("class")).thenReturn("MuiRating-root Mui-readOnly");
        assertTrue(testSubject.isReadOnly());
        
        when(element.getDomAttribute("class")).thenReturn("MuiRating-root");
        assertFalse(testSubject.isReadOnly());
    }
}