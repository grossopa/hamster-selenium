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

package com.github.grossopa.selenium.component.mui.v4.inputs;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiRadioGroup}
 *
 * @author Chenyu Wang
 * @since 1.0
 */
class MuiRadioGroupTest {
    MuiRadioGroup testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiRadioGroup(element, driver, config);
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }


    @Test
    void validate() {
        when(config.getRootCss("FormGroup")).thenReturn("MuiFormGroup-root");
        when(element.getDomAttribute("class")).thenReturn("MuiFormGroup-root");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(config.getRootCss("FormGroup")).thenReturn("MuiFormGroup-root");
        when(element.getDomAttribute("class")).thenReturn("MuiFormGroup-root-123");
        assertFalse(testSubject.validate());
    }

    @Test
    void getComponentName() {
        assertEquals("RadioGroup", testSubject.getComponentName());
    }

    @Test
    void getRadios() {
        when(config.radioLocator()).thenReturn(By.cssSelector(".MuiRadio-root"));
        when(element.findElements(config.radioLocator())).thenReturn(
                asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class)));
        assertEquals(3, testSubject.getRadios().size());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiRadioGroup{element=element-toString}", testSubject.toString());
    }
}
