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
package com.github.grossopa.selenium.component.mui;

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiTextField;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiComponentFinderTest {

    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    WebComponent webComponent = mock(WebComponent.class);
    By by = By.id("test");

    @BeforeEach
    void setUp() {
        when(driver.findComponent(by)).thenReturn(webComponent);
    }

    @Test
    void testPrivateConstructor() throws Exception {
        java.lang.reflect.Constructor<MuiComponentFinder> constructor = MuiComponentFinder.class.getDeclaredConstructor();
        assertFalse(constructor.canAccess(null));
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void findMuiButton() {
        MuiComponents muiComponents = mock(MuiComponents.class);
        MuiButton muiButton = mock(MuiButton.class);
        when(webComponent.as(any())).thenReturn(muiComponents);
        when(muiComponents.toButton()).thenReturn(muiButton);

        MuiButton result = MuiComponentFinder.findMuiButton(driver, by);
        assertEquals(muiButton, result);
    }

    @Test
    void findMuiTextField() {
        MuiComponents muiComponents = mock(MuiComponents.class);
        MuiTextField muiTextField = mock(MuiTextField.class);
        when(webComponent.as(any())).thenReturn(muiComponents);
        when(muiComponents.toTextField()).thenReturn(muiTextField);

        MuiTextField result = MuiComponentFinder.findMuiTextField(driver, by);
        assertEquals(muiTextField, result);
    }

    @Test
    @SuppressWarnings("unchecked")
    void findMuiComponent() {
        Function<WebComponent, String> func = mock(Function.class);
        when(func.apply(webComponent)).thenReturn("result");

        String result = MuiComponentFinder.findMuiComponent(driver, by, func);
        assertEquals("result", result);
    }
}
