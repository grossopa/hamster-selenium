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
package com.github.grossopa.selenium.component.antd.navigation;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AntdSteps}
 *
 * @author Jack Yin
 * @since 1.15
 */
class AntdStepsTest {

    AntdSteps testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    AntdConfig config = mock(AntdConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getPrefixCls()).thenReturn("sss");
        testSubject = new AntdSteps(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("steps", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("sss-steps sss-steps-horizontal");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getDomAttribute("class")).thenReturn("sss-ddd sss-steps-horizontal");
        assertFalse(testSubject.validate());
    }

    @Test
    void getSteps() {
        WebElement step1 = mock(WebElement.class);
        WebElement step2 = mock(WebElement.class);
        when(element.findElements(By.className("sss-steps-item"))).thenReturn(List.of(step1, step2));
        assertEquals(2, testSubject.getSteps().size());
    }

    @Test
    void getCurrentStep() {
        WebElement step1 = mock(WebElement.class);
        WebElement step2 = mock(WebElement.class);
        when(step1.getDomAttribute("class")).thenReturn("sss-steps-item sss-steps-item-finish");
        when(step2.getDomAttribute("class")).thenReturn("sss-steps-item sss-steps-item-process");
        when(element.findElements(By.className("sss-steps-item"))).thenReturn(List.of(step1, step2));
        assertEquals(1, testSubject.getCurrentStep());
    }

    @Test
    void getCurrentStepNegative() {
        WebElement step1 = mock(WebElement.class);
        when(step1.getDomAttribute("class")).thenReturn("sss-steps-item sss-steps-item-wait");
        when(element.findElements(By.className("sss-steps-item"))).thenReturn(List.of(step1));
        assertEquals(-1, testSubject.getCurrentStep());
    }
}
