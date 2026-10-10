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
package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatDatepickerTest {

    MatDatepicker testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);
    MatOverlayFinder overlayFinder = mock(MatOverlayFinder.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        testSubject = new MatDatepicker(element, driver, config, overlayFinder);
    }

    @Test
    void getComponentName() {
        assertEquals("Datepicker", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-datepicker");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-other");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(overlayFinder.toString()).thenReturn("overlayFinder");
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatDatepicker{overlayFinder=overlayFinder, element=inner-element}", testSubject.toString());
    }

    @Test
    void equalsSameInstance() {
        assertEquals(testSubject, testSubject);
    }

    @Test
    void equalsDifferentInstance() {
        // Two instances with same element and overlayFinder are equal
        MatDatepicker other = new MatDatepicker(element, driver, config, overlayFinder);
        assertEquals(testSubject, other);
    }

    @Test
    void equalsDifferentOverlayFinder() {
        MatOverlayFinder differentOverlayFinder = mock(MatOverlayFinder.class);
        MatDatepicker other = new MatDatepicker(element, driver, config, differentOverlayFinder);
        assertNotEquals(testSubject, other);
    }

    @Test
    void equalsNull() {
        assertNotEquals(null, testSubject);
    }

    @Test
    void equalsDifferentType() {
        assertNotEquals("string", testSubject);
    }

    @Test
    void hashCodeSameValues() {
        MatDatepicker other = new MatDatepicker(element, driver, config, overlayFinder);
        assertEquals(testSubject.hashCode(), other.hashCode());
    }

    @Test
    void constructor1() {
        assertDoesNotThrow(() -> new MatDatepicker(element, driver, config));
    }

    @Test
    void getInput() {
        WebElement inputEl = mock(WebElement.class);
        when(element.findElement(By.tagName("input"))).thenReturn(inputEl);
        assertNotNull(testSubject.getInput());
    }

    @Test
    void openCalendarNotOpen() {
        when(overlayFinder.findTopVisibleContainer()).thenReturn(null);
        WebElement toggleEl = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-datepicker-toggle"))).thenReturn(toggleEl);
        testSubject.openCalendar();
        verify(toggleEl).click();
    }

    @Test
    void openCalendarAlreadyOpen() {
        MatOverlayContainer container = mock(MatOverlayContainer.class);
        WebComponent panel = mock(WebComponent.class);
        when(container.findComponents(By.className("mat-mdc-datepicker-content"))).thenReturn(newArrayList(panel));
        when(overlayFinder.findTopVisibleContainer()).thenReturn(container);
        testSubject.openCalendar();
        verify(element, never()).findElement(any(By.class));
    }

    @Test
    void closeCalendar() {
        testSubject.closeCalendar();
        verify(element).sendKeys(Keys.ESCAPE);
    }

    @Test
    void getCalendarOpen() {
        MatOverlayContainer container = mock(MatOverlayContainer.class);
        WebComponent panel = mock(WebComponent.class);
        WebElement calendarEl = mock(WebElement.class);
        when(container.findComponents(By.className("mat-mdc-datepicker-content"))).thenReturn(newArrayList(panel));
        when(overlayFinder.findTopVisibleContainer()).thenReturn(container);
        when(panel.findElement(By.className("mat-mdc-calendar"))).thenReturn(calendarEl);
        WebComponent calendarComponent = new DefaultWebComponent(calendarEl, driver);
        when(panel.findComponent(By.className("mat-mdc-calendar"))).thenReturn(calendarComponent);

        MatCalendar result = testSubject.getCalendar();
        assertNotNull(result);
    }

    @Test
    void getCalendarNotOpen() {
        when(overlayFinder.findTopVisibleContainer()).thenReturn(null);
        assertThrows(IllegalStateException.class, () -> testSubject.getCalendar());
    }

    @Test
    void openCalendarContainerFoundButPanelsEmpty() {
        MatOverlayContainer container = mock(MatOverlayContainer.class);
        when(container.findComponents(By.className("mat-mdc-datepicker-content"))).thenReturn(newArrayList());
        when(overlayFinder.findTopVisibleContainer()).thenReturn(container);
        WebElement toggleEl = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-datepicker-toggle"))).thenReturn(toggleEl);
        testSubject.openCalendar();
        verify(toggleEl).click();
    }

    @Test
    void equalsDifferentElement() {
        WebElement otherElement = mock(WebElement.class);
        MatDatepicker other = new MatDatepicker(otherElement, driver, config, overlayFinder);
        assertNotEquals(testSubject, other);
    }
}
