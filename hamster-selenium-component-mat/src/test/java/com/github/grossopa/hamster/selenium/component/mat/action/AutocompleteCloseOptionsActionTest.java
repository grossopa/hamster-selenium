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

package com.github.grossopa.hamster.selenium.component.mat.action;

import com.github.grossopa.hamster.selenium.component.mat.main.MatAutocomplete;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.*;
import static org.openqa.selenium.Keys.ESCAPE;

/**
 * Tests for {@link AutocompleteCloseOptionsAction}
 *
 * @author Jack Yin
 * @since 1.6
 */
class AutocompleteCloseOptionsActionTest {

    AutocompleteCloseOptionsAction testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new AutocompleteCloseOptionsAction();
    }


    @Test
    void close() {
        WebComponent inputElement = mock(WebComponent.class);
        MatAutocomplete autocomplete = mock(MatAutocomplete.class);
        when(autocomplete.getInput()).thenReturn(inputElement);

        List<WebComponent> options = newArrayList();
        ComponentWebDriver driver = mock(ComponentWebDriver.class);
        testSubject.close(autocomplete, options, driver);

        verify(inputElement, times(1)).sendKeys(ESCAPE);
    }
}
