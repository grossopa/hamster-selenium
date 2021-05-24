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

package com.github.grossopa.selenium.core;

import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.element.WebElementDecorator;
import com.github.grossopa.selenium.core.util.GracefulThreadSleep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link DefaultComponentWebDriver}
 *
 * @author Jack Yin
 * @since 1.0
 */
@SuppressWarnings("deprecation")
class DefaultComponentWebDriverTest {

    DefaultComponentWebDriver testSubject;
    ComponentWebDriver driver = mock(ComponentWebDriver.class);

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
        verify(driver, only()).get("some-url");
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
        verify(driver, only()).executeScript("abc");
    }

    @Test
    void executeAsyncScript() {
        testSubject.executeAsyncScript("abc");
        verify(driver, only()).executeAsyncScript("abc");
    }

    @Test
    void getScreenshotAs() {
        testSubject.getScreenshotAs(OutputType.BASE64);
        verify(driver, only()).getScreenshotAs(OutputType.BASE64);
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
        verify(driver, only()).perform(emptyList());
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

    @Test
    void moveTo() {
        RemoteWebElement element = mock(RemoteWebElement.class);
        testSubject.moveTo(element);
        verify(element, never()).getLocation();
    }

    @Test
    void scrollTo() {
        RemoteWebElement element = mock(RemoteWebElement.class);
        testSubject.scrollTo(element);
        verify(driver, only()).executeScript("arguments[0].scrollIntoView();", element);
    }

    @Test
    void createWait() {
        WebDriverWait wait = testSubject.createWait(100L);
        assertNotNull(wait);
    }

    @Test
    @SuppressWarnings("unchecked")
    void findComponentsAs() {
        WebElement component1 = mock(WebElement.class);
        WebElement component2 = mock(WebElement.class);
        WebElement component3 = mock(WebElement.class);
        Function<WebComponent, WebComponent> mappingFunction = mock(Function.class);
        when(driver.findElements(By.id("ddd"))).thenReturn(newArrayList(component1, component2, component3));
        testSubject.findComponentsAs(By.id("ddd"), mappingFunction);
        verify(mappingFunction, times(3)).apply(any());
    }

    @Test
    @SuppressWarnings("unchecked")
    void findComponentAs() {
        WebElement component1 = mock(WebElement.class);
        Function<WebComponent, WebComponent> mappingFunction = mock(Function.class);
        when(driver.findElement(By.id("ddd"))).thenReturn(component1);
        testSubject.findComponentAs(By.id("ddd"), mappingFunction);
        verify(mappingFunction, times(1)).apply(any());
    }

    @Test
    void threadSleep() {
        GracefulThreadSleep mockThreadSleep = mock(GracefulThreadSleep.class);
        testSubject = new DefaultComponentWebDriver(driver, mockThreadSleep);
        testSubject.threadSleep(3565);
        verify(mockThreadSleep, only()).sleep(3565);
    }

    @Test
    void mapElementDecorator() {
        WebElementDecorator decorator = mock(WebElementDecorator.class);
        GracefulThreadSleep mockThreadSleep = mock(GracefulThreadSleep.class);
        WebElement element = mock(WebElement.class);
        testSubject = new DefaultComponentWebDriver(driver, mockThreadSleep, decorator);
        when(decorator.decorate(element, driver)).then(answer -> new DefaultWebComponent(element, driver));
        WebComponent component = testSubject.mapElement(element);
        assertTrue(component instanceof DefaultWebComponent);
        verify(decorator, times(1)).decorate(any(), any());
    }

    @Test
    void mapElementDecoratorWebComponent() {
        WebElementDecorator decorator = mock(WebElementDecorator.class);
        GracefulThreadSleep mockThreadSleep = mock(GracefulThreadSleep.class);
        WebComponent component = mock(WebComponent.class);
        testSubject = new DefaultComponentWebDriver(driver, mockThreadSleep, decorator);
        WebComponent component2 = testSubject.mapElement(component);
        assertSame(component, component2);
    }


}
