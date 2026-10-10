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
import com.github.grossopa.playwright.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Keyboard;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatDatepickerTest {

    MatDatepicker testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = mock(MatConfig.class);
    MatOverlayFinder overlayFinder = mock(MatOverlayFinder.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        testSubject = new MatDatepicker(locator, driver, config, overlayFinder);
    }

    @Test
    void getComponentName() {
        assertEquals("Datepicker", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-mdc-datepicker");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-other");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(overlayFinder.toString()).thenReturn("overlayFinder");
        when(locator.toString()).thenReturn("inner-locator");
        assertEquals("MatDatepicker{overlayFinder=overlayFinder, locator=inner-locator}", testSubject.toString());
    }

    @Test
    void componentConstructor() {
        WebComponent component = mock(WebComponent.class);
        when(component.locator()).thenReturn(locator);
        assertInstanceOf(MatDatepicker.class, new MatDatepicker(component, driver, config, overlayFinder));
    }

    @Test
    void getInput() {
        Locator inputLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator("input")).thenReturn(inputLocator);
        when(inputLocator.first()).thenReturn(firstLocator);
        assertNotNull(testSubject.getInput());
    }

    @Test
    void openCalendarOverlayNotFound() {
        when(overlayFinder.findTopVisibleContainer()).thenThrow(new NoSuchElementException("no overlay"));
        assertThrows(NoSuchElementException.class, () -> testSubject.openCalendar());
    }

    @Test
    void openCalendarAlreadyOpen() {
        MatOverlayContainer container = mock(MatOverlayContainer.class);
        Locator panelLocator = mock(Locator.class);
        when(container.findComponents(".mat-mdc-datepicker-content"))
                .thenReturn(List.of(new com.github.grossopa.playwright.core.DefaultWebComponent(panelLocator, driver)));
        when(panelLocator.isVisible()).thenReturn(true);
        when(overlayFinder.findTopVisibleContainer()).thenReturn(container);
        testSubject.openCalendar();
        verify(locator, never()).locator(".mat-mdc-datepicker-toggle");
    }

    @Test
    void closeCalendar() {
        Page page = mock(Page.class);
        Keyboard keyboard = mock(Keyboard.class);
        when(driver.page()).thenReturn(page);
        when(page.keyboard()).thenReturn(keyboard);
        testSubject.closeCalendar();
        verify(keyboard).press("Escape");
    }

    @Test
    void getCalendar() {
        MatOverlayContainer container = mock(MatOverlayContainer.class);
        Locator panelLocator = mock(Locator.class);
        when(container.findComponents(".mat-mdc-datepicker-content"))
                .thenReturn(List.of(new com.github.grossopa.playwright.core.DefaultWebComponent(panelLocator, driver)));
        when(panelLocator.isVisible()).thenReturn(true);
        when(overlayFinder.findTopVisibleContainer()).thenReturn(container);

        Locator calendarInner = mock(Locator.class);
        Locator calendarFirst = mock(Locator.class);
        when(panelLocator.locator(".mat-mdc-calendar")).thenReturn(calendarInner);
        when(calendarInner.first()).thenReturn(calendarFirst);

        MatCalendar calendar = testSubject.getCalendar();
        assertNotNull(calendar);
    }

    @Test
    void getCalendarNotOpen() {
        when(overlayFinder.findTopVisibleContainer()).thenThrow(new NoSuchElementException("no overlay"));
        assertThrows(NoSuchElementException.class, () -> testSubject.getCalendar());
    }

    @Test
    void equalsSameInstance() {
        assertEquals(testSubject, testSubject);
    }

    @Test
    void equalsDifferentInstance() {
        MatDatepicker other = new MatDatepicker(locator, driver, config, overlayFinder);
        assertNotEquals(testSubject, other);
    }

    @Test
    void hashCodeConsistent() {
        assertEquals(testSubject.hashCode(), testSubject.hashCode());
    }

    @Test
    void openCalendarOverlayContainerFoundButNoContent() {
        MatOverlayContainer container = mock(MatOverlayContainer.class);
        when(container.findComponents(".mat-mdc-datepicker-content")).thenReturn(List.of());
        when(overlayFinder.findTopVisibleContainer()).thenReturn(container);
        // container found but no datepicker-content panels → should click toggle
        Locator toggleLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-datepicker-toggle")).thenReturn(toggleLocator);
        when(toggleLocator.first()).thenReturn(firstLocator);
        testSubject.openCalendar();
        verify(firstLocator).click();
    }
}
