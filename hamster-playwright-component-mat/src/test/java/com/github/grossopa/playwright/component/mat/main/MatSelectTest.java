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

class MatSelectTest {

    MatSelect testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = mock(MatConfig.class);
    MatOverlayFinder overlayFinder = mock(MatOverlayFinder.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        testSubject = new MatSelect(locator, driver, config, overlayFinder);
    }

    @Test
    void getComponentName() {
        assertEquals("Select", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-mdc-select");
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
        assertEquals("MatSelect{overlayFinder=overlayFinder, locator=inner-locator}", testSubject.toString());
    }

    @Test
    void componentConstructor() {
        WebComponent component = mock(WebComponent.class);
        when(component.locator()).thenReturn(locator);
        assertInstanceOf(MatSelect.class, new MatSelect(component, driver, config, overlayFinder));
    }

    @Test
    void getSelectedValue() {
        Locator valueLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-select-value")).thenReturn(valueLocator);
        when(valueLocator.first()).thenReturn(firstLocator);
        when(firstLocator.innerText()).thenReturn("Option A");
        assertEquals("Option A", testSubject.getSelectedValue());
    }

    @Test
    void getTrigger() {
        Locator triggerLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-select-trigger")).thenReturn(triggerLocator);
        when(triggerLocator.first()).thenReturn(firstLocator);
        assertNotNull(testSubject.getTrigger());
    }

    @Test
    void openOptionsNotOpen() {
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of());
        when(overlayFinder.findTopVisibleContainer()).thenThrow(new NoSuchElementException("no overlay"));
        Locator triggerLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-select-trigger")).thenReturn(triggerLocator);
        when(triggerLocator.first()).thenReturn(firstLocator);
        testSubject.openOptions();
        verify(firstLocator).click();
    }

    @Test
    void openOptionsAlreadyOpen() {
        WebComponent panel = mock(WebComponent.class);
        when(panel.isVisible()).thenReturn(true);
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of(panel));
        testSubject.openOptions();
        verify(locator, never()).locator(".mat-mdc-select-trigger");
    }

    @Test
    void closeOptionsPanelOpen() {
        WebComponent panel = mock(WebComponent.class);
        when(panel.isVisible()).thenReturn(true);
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of(panel));
        Page page = mock(Page.class);
        Keyboard keyboard = mock(Keyboard.class);
        when(driver.page()).thenReturn(page);
        when(page.keyboard()).thenReturn(keyboard);
        testSubject.closeOptions();
        verify(keyboard).press("Escape");
    }

    @Test
    void closeOptionsPanelNotOpen() {
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of());
        when(overlayFinder.findTopVisibleContainer()).thenThrow(new NoSuchElementException("no overlay"));
        testSubject.closeOptions();
        verify(driver, never()).page();
    }

    @Test
    void getOptions() {
        WebComponent panel = mock(WebComponent.class);
        when(panel.isVisible()).thenReturn(true);
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of(panel));
        when(config.getTagPrefix()).thenReturn("mat-");
        Locator option1 = mock(Locator.class);
        Locator option2 = mock(Locator.class);
        WebComponent optComp1 = new com.github.grossopa.playwright.core.DefaultWebComponent(option1, driver);
        WebComponent optComp2 = new com.github.grossopa.playwright.core.DefaultWebComponent(option2, driver);
        when(panel.findComponents("mat-option")).thenReturn(List.of(optComp1, optComp2));
        assertEquals(2, testSubject.getOptions().size());
    }

    @Test
    void getOptionsNotOpen() {
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of());
        when(overlayFinder.findTopVisibleContainer()).thenThrow(new NoSuchElementException("no overlay"));
        assertThrows(IllegalStateException.class, () -> testSubject.getOptions());
    }

    @Test
    void selectByVisibleText() {
        WebComponent panel = mock(WebComponent.class);
        when(panel.isVisible()).thenReturn(true);
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of(panel));
        when(config.getTagPrefix()).thenReturn("mat-");
        Locator option1Loc = mock(Locator.class);
        Locator option2Loc = mock(Locator.class);
        WebComponent optComp1 = new com.github.grossopa.playwright.core.DefaultWebComponent(option1Loc, driver);
        WebComponent optComp2 = new com.github.grossopa.playwright.core.DefaultWebComponent(option2Loc, driver);
        when(panel.findComponents("mat-option")).thenReturn(List.of(optComp1, optComp2));
        when(option1Loc.innerText()).thenReturn("Option A");
        when(option2Loc.innerText()).thenReturn("Option B");
        testSubject.selectByVisibleText("Option B");
        verify(option2Loc).click();
    }

    @Test
    void selectByIndex() {
        WebComponent panel = mock(WebComponent.class);
        when(panel.isVisible()).thenReturn(true);
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of(panel));
        when(config.getTagPrefix()).thenReturn("mat-");
        Locator option1Loc = mock(Locator.class);
        WebComponent optComp1 = new com.github.grossopa.playwright.core.DefaultWebComponent(option1Loc, driver);
        when(panel.findComponents("mat-option")).thenReturn(List.of(optComp1));
        testSubject.selectByIndex(0);
        verify(option1Loc).click();
    }

    @Test
    void isMultipleTrue() {
        when(locator.getAttribute("class")).thenReturn("mat-mdc-select mat-mdc-select-multiple");
        assertTrue(testSubject.isMultiple());
    }

    @Test
    void isMultipleFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-mdc-select");
        assertFalse(testSubject.isMultiple());
    }

    @Test
    void equalsSameInstance() {
        assertEquals(testSubject, testSubject);
    }

    @Test
    void equalsDifferentInstance() {
        MatSelect other = new MatSelect(locator, driver, config, overlayFinder);
        assertNotEquals(testSubject, other);
    }

    @Test
    void hashCodeConsistent() {
        assertEquals(testSubject.hashCode(), testSubject.hashCode());
    }

    @Test
    void tryToFindSelectPanelOverlayFallback() {
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of());
        MatOverlayContainer container = mock(MatOverlayContainer.class);
        Locator panelLocator = mock(Locator.class);
        when(container.findComponents(".mat-mdc-select-panel"))
                .thenReturn(List.of(new com.github.grossopa.playwright.core.DefaultWebComponent(panelLocator, driver)));
        when(panelLocator.isVisible()).thenReturn(true);
        when(overlayFinder.findTopVisibleContainer()).thenReturn(container);
        // openOptions should not click trigger because panel is found via overlay
        testSubject.openOptions();
        verify(locator, never()).locator(".mat-mdc-select-trigger");
    }

    @Test
    void tryToFindSelectPanelOverlayEmpty() {
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of());
        MatOverlayContainer container = mock(MatOverlayContainer.class);
        when(container.findComponents(".mat-mdc-select-panel")).thenReturn(List.of());
        when(overlayFinder.findTopVisibleContainer()).thenReturn(container);
        // panel not found anywhere → openOptions should click trigger
        Locator triggerLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-select-trigger")).thenReturn(triggerLocator);
        when(triggerLocator.first()).thenReturn(firstLocator);
        testSubject.openOptions();
        verify(firstLocator).click();
    }

    @Test
    void tryToFindSelectPanelOverlayException() {
        when(driver.findComponents(".mat-mdc-select-panel")).thenReturn(List.of());
        when(overlayFinder.findTopVisibleContainer()).thenThrow(new RuntimeException("overlay error"));
        // overlay finder throws → should fallback and click trigger
        Locator triggerLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(".mat-mdc-select-trigger")).thenReturn(triggerLocator);
        when(triggerLocator.first()).thenReturn(firstLocator);
        testSubject.openOptions();
        verify(firstLocator).click();
    }
}
