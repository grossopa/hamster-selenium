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

package com.github.grossopa.selenium.component.mui.v4.core;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.openqa.selenium.Keys.ESCAPE;

/**
 * Tests for {@link MuiModal}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiModalTest {

    MuiModal testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiModal(element, driver, config) {

            @Override
            public String getComponentName() {
                return "Some";
            }
        };
    }

    @Test
    void close() {
        Actions actions = mock(Actions.class);
        when(driver.createActions()).thenReturn(actions);
        when(actions.sendKeys(ESCAPE)).thenReturn(actions);

        testSubject.close();

        verify(actions, times(1)).perform();
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }

    @Test
    void closeWithWait() {
        Actions actions = mock(Actions.class);
        when(driver.createActions()).thenReturn(actions);
        when(actions.sendKeys(ESCAPE)).thenReturn(actions);

        WebDriverWait wait = mock(WebDriverWait.class);
        when(driver.createWait(anyLong())).thenReturn(wait);
        testSubject.close(800L);

        verify(actions, times(1)).perform();
        verify(wait, times(1)).until(any());
    }

    @Test
    void closeWithWaitPositive() {
        Actions actions = mock(Actions.class);
        when(driver.createActions()).thenReturn(actions);
        when(actions.sendKeys(ESCAPE)).thenReturn(actions);
        when(element.isDisplayed()).thenReturn(true);

        WebDriverWait wait = mock(WebDriverWait.class);
        when(driver.createWait(anyLong())).thenReturn(wait);
        testSubject.close(50L);

        verify(actions, times(1)).perform();
        verify(wait, times(1)).until(any());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("{element=element-toString}", testSubject.toString());
    }
}
