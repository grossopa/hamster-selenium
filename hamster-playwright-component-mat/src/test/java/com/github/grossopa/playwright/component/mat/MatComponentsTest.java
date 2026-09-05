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
package com.github.grossopa.playwright.component.mat;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.playwright.component.mat.main.*;
import com.github.grossopa.playwright.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatComponents}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatComponentsTest {

    MatComponents testSubject;
    ComponentDriver driver = mock(ComponentDriver.class);
    WebComponent component = mock(WebComponent.class);
    Locator locator = mock(Locator.class);
    MatConfig config = new MatConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MatComponents();
        testSubject.setContext(component, driver);
        when(component.locator()).thenReturn(locator);
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(new MatComponents());
    }

    @Test
    void testConfigConstructor() {
        assertNotNull(new MatComponents(config));
    }

    @Test
    void configConstructorNull() {
        assertThrows(NullPointerException.class, () -> new MatComponents(null));
    }

    @Test
    void mat() {
        assertNotNull(MatComponents.mat());
    }

    @Test
    void matWithConfig() {
        assertNotNull(MatComponents.mat(config));
    }

    @Test
    void getComponent() {
        assertSame(component, testSubject.getComponent());
    }

    @Test
    void getDriver() {
        assertSame(driver, testSubject.getDriver());
    }

    @Test
    void toAutocomplete() {
        assertInstanceOf(MatAutocomplete.class, testSubject.toAutocomplete());
    }

    @Test
    void toAutocompleteWithOverlayFinder() {
        MatOverlayFinder overlayFinder = mock(MatOverlayFinder.class);
        assertInstanceOf(MatAutocomplete.class, testSubject.toAutocomplete(overlayFinder));
    }

    @Test
    void toAutocompleteWithOptionSelector() {
        assertInstanceOf(MatAutocomplete.class, testSubject.toAutocomplete(null, ".custom-option"));
    }

    @Test
    void toAutocompleteWithAllAttributes() {
        assertInstanceOf(MatAutocomplete.class, testSubject.toAutocomplete(null, null, (c, d) -> {
        }, (c, o, d) -> {
        }));
    }

    @Test
    void toBadge() {
        assertInstanceOf(MatBadge.class, testSubject.toBadge());
    }

    @Test
    void toBottomSheet() {
        assertInstanceOf(MatBottomSheet.class, testSubject.toBottomSheet());
    }

    @Test
    void toButton() {
        assertInstanceOf(MatButton.class, testSubject.toButton());
    }

    @Test
    void toButtonToggleGroup() {
        assertInstanceOf(MatButtonToggleGroup.class, testSubject.toButtonToggleGroup());
    }

    @Test
    void toButtonToggle() {
        assertInstanceOf(MatButtonToggle.class, testSubject.toButtonToggle());
    }

    @Test
    void toCheckbox() {
        assertInstanceOf(MatCheckbox.class, testSubject.toCheckbox());
    }

    @Test
    void toChipList() {
        assertInstanceOf(MatChipList.class, testSubject.toChipList());
    }

    @Test
    void toDialog() {
        assertInstanceOf(MatDialog.class, testSubject.toDialog());
    }

    @Test
    void toAccordion() {
        assertInstanceOf(MatAccordion.class, testSubject.toAccordion());
    }

    @Test
    void toFormField() {
        assertInstanceOf(MatFormField.class, testSubject.toFormField());
    }

    @Test
    void toGridList() {
        assertInstanceOf(MatGridList.class, testSubject.toGridList());
    }

    @Test
    void toGridTile() {
        assertInstanceOf(MatGridTile.class, testSubject.toGridTile());
    }

    @Test
    void toList() {
        assertInstanceOf(MatList.class, testSubject.toList());
    }

    @Test
    void toSelectionList() {
        assertInstanceOf(MatSelectionList.class, testSubject.toSelectionList());
    }

    @Test
    void toMenuItem() {
        assertInstanceOf(MatMenuItem.class, testSubject.toMenuItem());
    }

    @Test
    void toProgressBar() {
        assertInstanceOf(MatProgressBar.class, testSubject.toProgressBar());
    }

    @Test
    void toSlider() {
        assertInstanceOf(MatSlider.class, testSubject.toSlider());
    }

    @Test
    void toSlideToggle() {
        assertInstanceOf(MatSlideToggle.class, testSubject.toSlideToggle());
    }

    @Test
    void toSnackbar() {
        assertInstanceOf(MatSnackbar.class, testSubject.toSnackbar());
    }
}
