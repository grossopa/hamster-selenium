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

package com.github.grossopa.selenium.component.mui.v4.feedback;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiSnackbar}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiSnackbarTest {

    MuiSnackbar testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiSnackbar(element, driver, config);
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }

    @Test
    void getContent() {
        when(config.getCssPrefix()).thenReturn("Mui");
        WebElement contentElement = mock(WebElement.class);
        when(element.findElement(By.className("MuiSnackbarContent-root"))).thenReturn(contentElement);
        assertEquals(contentElement, testSubject.getContent().getWrappedElement());
    }

    @Test
    void getComponentName() {
        assertEquals("Snackbar", testSubject.getComponentName());
    }

    @Test
    void startAutoHideCheckNegative() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.startAutoHideCheck());
    }

    @Test
    void startAutoHideCheckNegative2() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.startAutoHideCheck(-1L));
    }

    @Test
    void startAutoHideCheckNegative3() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.startAutoHideCheck(null));
    }

    @Test
    void startAutoHideCheck() {
        WebDriverWait wait = mock(WebDriverWait.class);

        when(element.isDisplayed()).thenReturn(true);

        testSubject = new MuiSnackbar(element, driver, config, 500L);
        when(driver.createWait(500L)).thenReturn(wait);
        when(wait.until(any())).then(answer -> {
            Function<? super WebDriver, Boolean> isTrueFunction = answer.getArgument(0);
            return isTrueFunction.apply(driver);
        });

        WebDriverWait result = testSubject.startAutoHideCheck();
        verify(result, times(1)).until(any());
    }

    @Test
    void startAutoHideCheckNotDisplayed() {
        WebDriverWait wait = mock(WebDriverWait.class);

        when(element.isDisplayed()).thenReturn(false);

        testSubject = new MuiSnackbar(element, driver, config, 500L);
        when(driver.createWait(500L)).thenReturn(wait);
        when(wait.until(any())).then(answer -> {
            Function<? super WebDriver, Boolean> isTrueFunction = answer.getArgument(0);
            return isTrueFunction.apply(driver);
        });

        WebDriverWait result = testSubject.startAutoHideCheck();
        verify(result, times(1)).until(any());
    }

    @Test
    void startAutoHideCheckWithAutoHideDuration() {
        WebDriverWait wait = mock(WebDriverWait.class);

        when(element.isDisplayed()).thenReturn(false);

        testSubject = new MuiSnackbar(element, driver, config);
        when(driver.createWait(800L)).thenReturn(wait);
        when(wait.until(any())).then(answer -> {
            Function<? super WebDriver, Boolean> isTrueFunction = answer.getArgument(0);
            return isTrueFunction.apply(driver);
        });

        WebDriverWait result = testSubject.startAutoHideCheck(800L);
        verify(result, times(1)).until(any());
    }

    @Test
    void getAutoHideDuration() {
        assertNull(testSubject.getAutoHideDuration());
    }

    @Test
    void getAutoHideDurationWithConstructorValue() {
        assertEquals(200L, new MuiSnackbar(element, driver, config, 200L).getAutoHideDuration());
    }

    @Test
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();

        tester.addEqualityGroup(new MuiSnackbar(element1, driver, config, 200L),
                new MuiSnackbar(element1, driver, config, 200L));
        tester.addEqualityGroup(new MuiSnackbar(element1, driver, config, 300L));
        tester.addEqualityGroup(new MuiSnackbar(element2, driver, config, 200L));
        tester.addEqualityGroup(new MuiSnackbar(element2, driver, config, 300L));

        tester.testEquals();
    }

    @Test
    void testToString() {
        testSubject = new MuiSnackbar(element, driver, config, 800L);
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiSnackbar{autoHideDuration=800, element=element-toString}", testSubject.toString());
    }
}
