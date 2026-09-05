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
package com.github.grossopa.playwright.component.mat.main;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Mouse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MatSlider}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatSliderTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    Locator thumbLocator = mock(Locator.class);
    Locator thumbFirst = mock(Locator.class);

    MatSlider testSubject;

    @BeforeEach
    void setUp() {
        when(locator.locator(".mat-slider-thumb")).thenReturn(thumbLocator);
        when(thumbLocator.first()).thenReturn(thumbFirst);
        testSubject = new MatSlider(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Slider", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("Slider", MatSlider.COMPONENT_NAME);
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-slider");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-slide-toggle");
        assertFalse(testSubject.validate());
    }

    @Test
    void getValue() {
        when(locator.getAttribute("aria-valuenow")).thenReturn("30");
        assertEquals("30", testSubject.getValue());
    }

    @Test
    void getValueInteger() {
        when(locator.getAttribute("aria-valuenow")).thenReturn("30");
        assertEquals(30, testSubject.getValueInteger());
    }

    @Test
    void getValueLong() {
        when(locator.getAttribute("aria-valuenow")).thenReturn("30");
        assertEquals(30L, testSubject.getValueLong());
    }

    @Test
    void getValueDouble() {
        when(locator.getAttribute("aria-valuenow")).thenReturn("30.5");
        assertEquals(30.5, testSubject.getValueDouble());
    }

    @Test
    void getMinValue() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("0");
        assertEquals("0", testSubject.getMinValue());
    }

    @Test
    void getMinValueInteger() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("0");
        assertEquals(0, testSubject.getMinValueInteger());
    }

    @Test
    void getMinValueLong() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("0");
        assertEquals(0L, testSubject.getMinValueLong());
    }

    @Test
    void getMinValueDouble() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("0.5");
        assertEquals(0.5, testSubject.getMinValueDouble());
    }

    @Test
    void getMaxValue() {
        when(locator.getAttribute("aria-valuemax")).thenReturn("100");
        assertEquals("100", testSubject.getMaxValue());
    }

    @Test
    void getMaxValueInteger() {
        when(locator.getAttribute("aria-valuemax")).thenReturn("100");
        assertEquals(100, testSubject.getMaxValueInteger());
    }

    @Test
    void getMaxValueLong() {
        when(locator.getAttribute("aria-valuemax")).thenReturn("100");
        assertEquals(100L, testSubject.getMaxValueLong());
    }

    @Test
    void getMaxValueDouble() {
        when(locator.getAttribute("aria-valuemax")).thenReturn("100.5");
        assertEquals(100.5, testSubject.getMaxValueDouble());
    }

    @Test
    void getFirstThumb() {
        assertNotNull(testSubject.getFirstThumb());
    }

    @Test
    void getAllThumbs() {
        assertEquals(1, testSubject.getAllThumbs().size());
    }

    @Test
    void isVertical() {
        when(locator.getAttribute("class")).thenReturn("mat-slider mat-slider-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test
    void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-slider");
        assertFalse(testSubject.isVertical());
    }

    @Test
    void isInverted() {
        when(locator.getAttribute("class")).thenReturn("mat-slider mat-slider-axis-inverted");
        assertTrue(testSubject.isInverted());
    }

    @Test
    void isInvertedFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-slider");
        assertFalse(testSubject.isInverted());
    }

    @Test
    void setValueHorizontal() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("0");
        when(locator.getAttribute("aria-valuemax")).thenReturn("100");
        when(locator.getAttribute("class")).thenReturn("mat-slider");
        when(locator.boundingBox()).thenReturn(boundingBox(0, 0, 200, 20));
        Page page = mock(Page.class);
        Mouse mouse = mock(Mouse.class);
        when(driver.page()).thenReturn(page);
        when(page.mouse()).thenReturn(mouse);

        testSubject.setValue(50);

        verify(mouse).click(100, 10);
    }

    @Test
    void setValueVertical() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("0");
        when(locator.getAttribute("aria-valuemax")).thenReturn("100");
        when(locator.getAttribute("class")).thenReturn("mat-slider mat-slider-vertical");
        when(locator.boundingBox()).thenReturn(boundingBox(0, 0, 20, 200));
        Page page = mock(Page.class);
        Mouse mouse = mock(Mouse.class);
        when(driver.page()).thenReturn(page);
        when(page.mouse()).thenReturn(mouse);

        testSubject.setValue(75);

        verify(mouse).click(10, 50);
    }

    @Test
    void setValueInteger() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("0");
        when(locator.getAttribute("aria-valuemax")).thenReturn("100");
        when(locator.getAttribute("class")).thenReturn("mat-slider");
        when(locator.boundingBox()).thenReturn(boundingBox(0, 0, 100, 10));
        Page page = mock(Page.class);
        Mouse mouse = mock(Mouse.class);
        when(driver.page()).thenReturn(page);
        when(page.mouse()).thenReturn(mouse);

        testSubject.setValue(Integer.valueOf(100));

        verify(mouse).click(100, 5);
    }

    @Test
    void setValueLong() {
        when(locator.getAttribute("aria-valuemin")).thenReturn("0");
        when(locator.getAttribute("aria-valuemax")).thenReturn("100");
        when(locator.getAttribute("class")).thenReturn("mat-slider");
        when(locator.boundingBox()).thenReturn(boundingBox(0, 0, 100, 10));
        Page page = mock(Page.class);
        Mouse mouse = mock(Mouse.class);
        when(driver.page()).thenReturn(page);
        when(page.mouse()).thenReturn(mouse);

        testSubject.setValue(Long.valueOf(0));

        verify(mouse).click(0, 5);
    }

    private BoundingBox boundingBox(double x, double y, double width, double height) {
        BoundingBox box = new BoundingBox();
        box.x = x;
        box.y = y;
        box.width = width;
        box.height = height;
        return box;
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatSlider"));
    }
}
