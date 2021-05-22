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

package com.github.grossopa.selenium.core.intercepting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static com.github.grossopa.selenium.core.intercepting.InterceptingTestHelper.afterEachVerify;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link InterceptingWebDriver}
 *
 * @author Jack Yin
 * @since 1.4
 */
@SuppressWarnings("deprecation")
class InterceptingWebDriverTest {

    InterceptingWebDriver testSubject;
    RemoteWebDriver driver = mock(RemoteWebDriver.class);
    InterceptingHandler handler = mock(InterceptingHandler.class);

    WebElement element = mock(WebElement.class);
    List<WebElement> elements = Collections.singletonList(element);

    @BeforeEach
    void setUp() {
        when(handler.execute(any(), any())).thenCallRealMethod();
        testSubject = new InterceptingWebDriver(driver, handler);
    }

    @Test
    void get() {
        testSubject.get("http://www.google.com");
        verify(driver, times(1)).get("http://www.google.com");
        afterEachVerify(handler, driver, DRIVER_GET, null, "http://www.google.com");
    }

    @Test
    void getCurrentUrl() {
        when(driver.getCurrentUrl()).thenReturn("http://www.google.com");
        String result = testSubject.getCurrentUrl();
        assertEquals("http://www.google.com", result);
        verify(driver, times(1)).getCurrentUrl();
        afterEachVerify(handler, driver, DRIVER_GET_CURRENT_URL, "http://www.google.com");
    }

    @Test
    void getTitle() {
        when(driver.getTitle()).thenReturn("http://www.google.com");
        String result = testSubject.getTitle();
        assertEquals("http://www.google.com", result);
        verify(driver, times(1)).getTitle();
        afterEachVerify(handler, driver, DRIVER_GET_TITLE, "http://www.google.com");
    }

    @Test
    void findElements() {
        when(driver.findElements(By.id("aa"))).thenReturn(elements);
        List<WebElement> result = testSubject.findElements(By.id("aa"));
        assertEquals(1, result.size());
        verify(driver, times(1)).findElements(By.id("aa"));
        afterEachVerify(handler, driver, DRIVER_FIND_ELEMENTS,
                elements.stream().map(el -> new InterceptingWebElement(el, handler)).collect(Collectors.toList()),
                By.id("aa"));
    }

    @Test
    void findElement() {
        when(driver.findElement(By.id("aa"))).thenReturn(element);
        WebElement result = testSubject.findElement(By.id("aa"));
        assertEquals(new InterceptingWebElement(element, handler), result);
        verify(driver, times(1)).findElement(By.id("aa"));
        afterEachVerify(handler, driver, DRIVER_FIND_ELEMENT, new InterceptingWebElement(element, handler),
                By.id("aa"));
    }

    @Test
    void getPageSource() {
        when(driver.getPageSource()).thenReturn("http://www.google.com");
        String result = testSubject.getPageSource();
        assertEquals("http://www.google.com", result);
        verify(driver, times(1)).getPageSource();
        afterEachVerify(handler, driver, DRIVER_GET_PAGE_SOURCE, "http://www.google.com");
    }

    @Test
    void close() {
        testSubject.close();
        verify(driver, times(1)).close();
        afterEachVerify(handler, driver, DRIVER_CLOSE, null);
    }

    @Test
    void quit() {
        testSubject.quit();
        verify(driver, times(1)).quit();
        afterEachVerify(handler, driver, DRIVER_QUIT, null);
    }

    @Test
    void getWindowHandles() {
        when(driver.getWindowHandles()).thenReturn(newHashSet("http://www.google.com"));
        Set<String> result = testSubject.getWindowHandles();
        assertEquals(newHashSet("http://www.google.com"), result);
        verify(driver, times(1)).getWindowHandles();
        afterEachVerify(handler, driver, DRIVER_GET_WINDOW_HANDLES, newHashSet("http://www.google.com"));
    }

    @Test
    void getWindowHandle() {
        when(driver.getWindowHandle()).thenReturn("http://www.google.com");
        String result = testSubject.getWindowHandle();
        assertEquals("http://www.google.com", result);
        verify(driver, times(1)).getWindowHandle();
        afterEachVerify(handler, driver, DRIVER_GET_WINDOW_HANDLE, "http://www.google.com");
    }

    @Test
    void switchTo() {
        when(driver.switchTo()).thenReturn(mock(WebDriver.TargetLocator.class));
        assertTrue(testSubject.switchTo() instanceof InterceptingTargetLocator);
    }

    @Test
    void navigate() {
        when(driver.navigate()).thenReturn(mock(WebDriver.Navigation.class));
        assertTrue(testSubject.navigate() instanceof InterceptingNavigation);
    }

    @Test
    void manage() {
        WebDriver.Options options = mock(WebDriver.Options.class);
        when(driver.manage()).thenReturn(options);
        assertEquals(options, testSubject.manage());
    }

    @Test
    void getCapabilities() {
        Capabilities capabilities = mock(Capabilities.class);
        when(driver.getCapabilities()).thenReturn(capabilities);
        assertEquals(capabilities, testSubject.getCapabilities());
    }

    @Test
    void executeScript() {
        when(driver.executeScript("script", "a", "r")).thenReturn("http://www.google.com");
        Object result = testSubject.executeScript("script", "a", "r");
        assertEquals("http://www.google.com", result);
        verify(driver, times(1)).executeScript("script", "a", "r");
        afterEachVerify(handler, driver, DRIVER_EXECUTE_SCRIPT, "http://www.google.com", "script",
                new Object[]{"a", "r"});
    }

    @Test
    void executeAsyncScript() {
        when(driver.executeAsyncScript("script", "a", "r")).thenReturn("http://www.google.com");
        Object result = testSubject.executeAsyncScript("script", "a", "r");
        assertEquals("http://www.google.com", result);
        verify(driver, times(1)).executeAsyncScript("script", "a", "r");
        afterEachVerify(handler, driver, DRIVER_EXECUTE_ASYNC_SCRIPT, "http://www.google.com", "script",
                new Object[]{"a", "r"});
    }

    @Test
    void getScreenshotAs() {
        byte[] bs = new byte[]{'a'};
        when(driver.getScreenshotAs(OutputType.BYTES)).thenReturn(bs);
        byte[] result = testSubject.getScreenshotAs(OutputType.BYTES);
        assertEquals(bs, result);
        verify(driver, times(1)).getScreenshotAs(OutputType.BYTES);
        afterEachVerify(handler, driver, DRIVER_GET_SCREENSHOT_AS, bs, OutputType.BYTES);
    }

    @Test
    void getKeyboard() {
        Keyboard keyboard = mock(Keyboard.class);
        when(driver.getKeyboard()).thenReturn(keyboard);
        assertEquals(keyboard, testSubject.getKeyboard());
    }

    @Test
    void getMouse() {
        Mouse mouse = mock(Mouse.class);
        when(driver.getMouse()).thenReturn(mouse);
        assertEquals(mouse, testSubject.getMouse());
    }

    @Test
    void perform() {
        testSubject.perform(null);
        verify(driver, times(1)).perform(any());
    }

    @Test
    void resetInputState() {
        testSubject.resetInputState();
        verify(driver, times(1)).resetInputState();
    }
}
