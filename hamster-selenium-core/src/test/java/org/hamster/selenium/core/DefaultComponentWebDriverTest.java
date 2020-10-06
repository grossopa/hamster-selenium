/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.core;

import org.hamster.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link DefaultComponentWebDriver}
 *
 * @author Jack Yin
 * @since 1.0
 */
class DefaultComponentWebDriverTest {

    DefaultComponentWebDriver testSubject;
    RemoteWebDriver driver = mock(RemoteWebDriver.class);

    @BeforeEach
    void setUp() {
        testSubject = new DefaultComponentWebDriver(driver);
    }


    @Test
    void findComponents() {
        WebElement mockElement1 = mock(WebElement.class);
        WebElement mockElement2 = mock(WebElement.class);
        when(driver.findElements(any())).thenReturn(Arrays.asList(mockElement1, mockElement2));
        List<WebComponent> result = testSubject.findComponents(By.id("some"));
        assertEquals(mockElement1, result.get(0).getWrappedElement());
        assertEquals(mockElement2, result.get(1).getWrappedElement());
    }

    @Test
    void findComponent() {
        WebElement mockElement1 = mock(WebElement.class);
        when(driver.findElement(any())).thenReturn(mockElement1);
        WebComponent component = testSubject.findComponent(By.id("some"));
        assertEquals(mockElement1, component.getWrappedElement());
    }

    @Test
    void get() {
        testSubject.get("some-url");
        verify(driver, only()).get(eq("some-url"));
    }

    @Test
    void getCurrentUrl() {
        testSubject.getCurrentUrl();
        verify(driver, only()).getCurrentUrl();
    }

    @Test
    void getTitle() {
        testSubject.getTitle();
        verify(driver, only()).getTitle();
    }

    @Test
    void findElements() {
        WebElement mockElement1 = mock(WebElement.class);
        WebElement mockElement2 = mock(WebElement.class);
        when(driver.findElements(any())).thenReturn(Arrays.asList(mockElement1, mockElement2));
        List<WebElement> result = testSubject.findElements(By.id("some"));
        assertEquals(mockElement1, result.get(0));
        assertEquals(mockElement2, result.get(1));
    }

    @Test
    void findElement() {
        WebElement mockElement1 = mock(WebElement.class);
        when(driver.findElement(any())).thenReturn(mockElement1);
        WebElement component = testSubject.findElement(By.id("some"));
        assertEquals(mockElement1, component);
    }

    @Test
    void mapElement() {
        WebElement element = mock(WebElement.class);
        WebComponent component = testSubject.mapElement(element);
        assertEquals(element, component.getWrappedElement());
    }

    @Test
    void getPageSource() {
        testSubject.getPageSource();
        verify(driver, only()).getPageSource();
    }

    @Test
    void close() {
        testSubject.close();
        verify(driver, only()).close();
    }

    @Test
    void quit() {
        testSubject.quit();
        verify(driver, only()).quit();
    }

    @Test
    void getWindowHandles() {
        testSubject.getWindowHandles();
        verify(driver, only()).getWindowHandles();
    }

    @Test
    void getWindowHandle() {
        testSubject.getWindowHandle();
        verify(driver, only()).getWindowHandle();
    }

    @Test
    void switchTo() {
        testSubject.switchTo();
        verify(driver, only()).switchTo();
    }

    @Test
    void navigate() {
        testSubject.navigate();
        verify(driver, only()).navigate();
    }

    @Test
    void manage() {
        testSubject.manage();
        verify(driver, only()).manage();
    }

    @Test
    void getCapabilities() {
        testSubject.getCapabilities();
        verify(driver, only()).getCapabilities();
    }

    @Test
    void executeScript() {
        testSubject.executeScript("abc");
        verify(driver, only()).executeScript(eq("abc"));
    }

    @Test
    void executeAsyncScript() {
        testSubject.executeAsyncScript("abc");
        verify(driver, only()).executeAsyncScript(eq("abc"));
    }

    @Test
    void getScreenshotAs() {
        testSubject.getScreenshotAs(OutputType.BASE64);
        verify(driver, only()).getScreenshotAs(eq(OutputType.BASE64));
    }

    @Test
    void getKeyboard() {
        testSubject.getKeyboard();
        verify(driver, only()).getKeyboard();
    }

    @Test
    void getMouse() {
        testSubject.getMouse();
        verify(driver, only()).getMouse();
    }

    @Test
    void perform() {
        testSubject.perform(emptyList());
        verify(driver, only()).perform(eq(emptyList()));
    }

    @Test
    void resetInputState() {
        testSubject.resetInputState();
        verify(driver, only()).resetInputState();
    }

    @Test
    void getDriver() {
        assertEquals(driver, testSubject.getWrappedDriver());
    }

    @Test
    void getWrappedDriver() {
        assertEquals(driver, testSubject.getWrappedDriver());
    }
}