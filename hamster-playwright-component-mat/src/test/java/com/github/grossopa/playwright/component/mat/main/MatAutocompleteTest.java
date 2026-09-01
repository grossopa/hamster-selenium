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

import com.github.grossopa.playwright.component.mat.action.CloseOptionsAction;
import com.github.grossopa.playwright.component.mat.action.OpenOptionsAction;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.exception.OptionNotClosedException;
import com.github.grossopa.playwright.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MatAutocomplete}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatAutocompleteTest {

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    MatOverlayFinder overlayFinder = mock(MatOverlayFinder.class);

    Locator containerLocator = mock(Locator.class);
    Locator panelLocator = mock(Locator.class);
    Locator panelItem = mock(Locator.class);
    Locator optionsLocator = mock(Locator.class);
    Locator option1 = mock(Locator.class);
    Locator option2 = mock(Locator.class);

    MatOverlayContainer container;

    MatAutocomplete testSubject;

    @BeforeEach
    void setUp() {
        container = new MatOverlayContainer(containerLocator, driver, config);
        when(containerLocator.locator(".mat-autocomplete-panel")).thenReturn(panelLocator);
        when(panelLocator.all()).thenReturn(List.of(panelItem));
        when(panelItem.locator("mat-option")).thenReturn(optionsLocator);
        when(optionsLocator.all()).thenReturn(List.of(option1, option2));
        testSubject = new MatAutocomplete(locator, driver, config, overlayFinder);
    }

    @Test
    void getComponentName() {
        assertEquals("Autocomplete", testSubject.getComponentName());
    }

    @Test
    void componentName() {
        assertEquals("Autocomplete", MatAutocomplete.COMPONENT_NAME);
    }

    @Test
    void defaultConstructor() {
        assertInstanceOf(MatAutocomplete.class, new MatAutocomplete(locator, driver, config));
    }

    @Test
    void constructorWithOptionSelector() {
        assertInstanceOf(MatAutocomplete.class, new MatAutocomplete(locator, driver, config, overlayFinder, ".opt"));
    }

    @Test
    void constructorWithAllAttributes() {
        assertInstanceOf(MatAutocomplete.class, new MatAutocomplete(locator, driver, config, overlayFinder, ".opt",
                mock(OpenOptionsAction.class), mock(CloseOptionsAction.class)));
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-autocomplete-trigger");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-input");
        assertFalse(testSubject.validate());
    }

    @Test
    void getInput() {
        Locator inputLocator = mock(Locator.class);
        when(locator.locator("input.mat-autocomplete-trigger")).thenReturn(inputLocator);
        when(inputLocator.first()).thenReturn(mock(Locator.class));
        assertNotNull(testSubject.getInput());
    }

    @Test
    void openOptionsAlreadyOpened() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        assertNotNull(testSubject.openOptions());
    }

    @Test
    void openOptionsWithOpenAction() {
        OpenOptionsAction openAction = mock(OpenOptionsAction.class);
        testSubject = new MatAutocomplete(locator, driver, config, overlayFinder, null, openAction, null);
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(), List.of(container));
        when(driver.page()).thenReturn(mock(Page.class));

        assertNotNull(testSubject.openOptions());

        verify(openAction).open(testSubject, driver);
    }

    @Test
    void openOptionsFailed() {
        OpenOptionsAction openAction = mock(OpenOptionsAction.class);
        testSubject = new MatAutocomplete(locator, driver, config, overlayFinder, null, openAction, null);
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of());
        when(driver.page()).thenReturn(mock(Page.class));

        assertThrows(NoSuchElementException.class, () -> testSubject.openOptions());
    }

    @Test
    void closeOptionsWhenNotOpened() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of());
        assertDoesNotThrow(() -> testSubject.closeOptions());
    }

    @Test
    void closeOptionsSuccess() {
        CloseOptionsAction closeAction = mock(CloseOptionsAction.class);
        testSubject = new MatAutocomplete(locator, driver, config, overlayFinder, null, null, closeAction);
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(panelLocator.all()).thenReturn(List.of(panelItem), List.of());

        assertDoesNotThrow(() -> testSubject.closeOptions());

        verify(closeAction).close(eq(testSubject), anyList(), eq(driver));
    }

    @Test
    void closeOptionsFailed() {
        CloseOptionsAction closeAction = mock(CloseOptionsAction.class);
        testSubject = new MatAutocomplete(locator, driver, config, overlayFinder, null, null, closeAction);
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(panelItem.isVisible()).thenReturn(true);

        assertThrows(OptionNotClosedException.class, () -> testSubject.closeOptions());
    }

    @Test
    void getOptions() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        assertEquals(2, testSubject.getOptions().size());
    }

    @Test
    void getAllSelectedOptions() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(option1.getAttribute("class")).thenReturn("mat-option mat-selected");
        when(option2.getAttribute("class")).thenReturn("mat-option");
        assertEquals(1, testSubject.getAllSelectedOptions().size());
    }

    @Test
    void getAllSelectedOptionsEmpty() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(option1.getAttribute("class")).thenReturn("mat-option");
        when(option2.getAttribute("class")).thenReturn("mat-option");
        assertTrue(testSubject.getAllSelectedOptions().isEmpty());
    }

    @Test
    void getFirstSelectedOption() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(option1.getAttribute("class")).thenReturn("mat-option mat-selected");
        assertNotNull(testSubject.getFirstSelectedOption());
    }

    @Test
    void getFirstSelectedOptionNull() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(option1.getAttribute("class")).thenReturn("mat-option");
        when(option2.getAttribute("class")).thenReturn("mat-option");
        assertNull(testSubject.getFirstSelectedOption());
    }

    @Test
    void selectByVisibleText() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(option1.innerText()).thenReturn("One");
        when(option2.innerText()).thenReturn("Two");

        testSubject.selectByVisibleText("Two");

        verify(option2).click();
    }

    @Test
    void selectByVisibleTextNotFound() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(option1.innerText()).thenReturn("One");
        when(option2.innerText()).thenReturn("Two");

        testSubject.selectByVisibleText("Three");

        verify(option1, never()).click();
        verify(option2, never()).click();
    }

    @Test
    void selectByContainsVisibleText() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(option1.innerText()).thenReturn("Apple Juice");
        when(option2.innerText()).thenReturn("Orange Juice");

        testSubject.selectByContainsVisibleText("Orange");

        verify(option2).click();
    }

    @Test
    void selectByContainsVisibleTextNullText() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));
        when(option1.innerText()).thenReturn(null);

        testSubject.selectByContainsVisibleText("Orange");

        verify(option1, never()).click();
    }

    @Test
    void selectByIndex() {
        when(overlayFinder.findVisibleContainers()).thenReturn(List.of(container));

        testSubject.selectByIndex(1);

        verify(option2).click();
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatAutocomplete"));
    }
}
