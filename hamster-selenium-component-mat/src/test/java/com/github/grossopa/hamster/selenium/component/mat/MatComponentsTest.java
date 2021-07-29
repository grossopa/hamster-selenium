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

package com.github.grossopa.hamster.selenium.component.mat;

import com.github.grossopa.hamster.selenium.component.mat.action.CloseOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.action.OpenOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatComponents}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatComponentsTest {

    MatComponents testSubject;
    WebElement element = mock(WebElement.class);
    WebComponent component = mock(WebComponent.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);


    @BeforeEach
    void setUp() {
        testSubject = new MatComponents();
        when(component.getWrappedElement()).thenReturn(element);
        testSubject.setContext(component, driver);
    }

    @Test
    void mat() {
        assertDoesNotThrow((ThrowingSupplier<MatComponents>) MatComponents::mat);
    }

    @Test
    void testMat() {
        assertDoesNotThrow(() -> MatComponents.mat(config));
    }

    @Test
    void toAutocomplete() {
        assertEquals(element, testSubject.toAutocomplete().getWrappedElement());
    }

    @Test
    void toAutocomplete2() {
        assertEquals(element, testSubject.toAutocomplete(mock(MatOverlayFinder.class)).getWrappedElement());
    }

    @Test
    void toAutocomplete3() {
        assertEquals(element,
                testSubject.toAutocomplete(mock(MatOverlayFinder.class), mock(By.class)).getWrappedElement());
    }

    @Test
    void toAutocomplete4() {
        assertEquals(element,
                testSubject.toAutocomplete(mock(MatOverlayFinder.class), mock(By.class), mock(OpenOptionsAction.class),
                        mock(CloseOptionsAction.class)).getWrappedElement());
    }

    @Test
    void toBadge() {
        assertEquals(element, testSubject.toBadge().getWrappedElement());
    }

    @Test
    void toBottomSheet() {
        assertEquals(element, testSubject.toBottomSheet().getWrappedElement());
    }

    @Test
    void toButton() {
        assertEquals(element, testSubject.toButton().getWrappedElement());
    }
}
