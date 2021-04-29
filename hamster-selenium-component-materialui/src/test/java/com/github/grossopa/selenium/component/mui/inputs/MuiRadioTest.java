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

package com.github.grossopa.selenium.component.mui.inputs;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiRadio}
 *
 * @author Chenyu Wang
 * @since 1.0
 */
public class MuiRadioTest {

    MuiRadio testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement radio = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.getIsCheckedCss()).thenReturn("checked");
        when(config.getIsDisabledCss()).thenReturn("disabled");

        when(element.findElement(eq(By.className(".MuiRadio-root")))).thenReturn(radio);
        testSubject = new MuiRadio(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("Radio", testSubject.getComponentName());
    }

    @Test
    void isSelected() {
        when(config.isChecked(eq(testSubject))).thenReturn(true);
        assertTrue(testSubject.isSelected());
    }

    @Test
    void isSelectedNegative() {
        when(config.isChecked(eq(testSubject))).thenReturn(false);
        assertFalse(testSubject.isSelected());
    }

    @Test
    void isEnabled() {
        when(config.isSelected(any())).then(answer -> {
            WebComponent component = answer.getArgument(0);
            assertEquals(component.getWrappedElement(), radio);
            return true;
        });
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledNegative() {
        when(config.isSelected(any())).then(answer -> {
            WebComponent component = answer.getArgument(0);
            assertEquals(component.getWrappedElement(), radio);
            return false;
        });
        assertTrue(testSubject.isEnabled());
    }
}
