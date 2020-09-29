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

package org.hamster.selenium.core.component;

import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.factory.WebComponentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link DefaultWebComponent}
 *
 * @author Jack Yin
 * @since 1.0
 */
class DefaultWebComponentTest {

    DefaultWebComponent testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);

    @BeforeEach
    void setUp() {
        testSubject = new DefaultWebComponent(element, driver);
    }


    @Test
    void getElement() {
        assertEquals(element, testSubject.getElement());
    }

    @Test
    void findComponents() {
        WebElement result1 = mock(WebElement.class);
        WebElement result2 = mock(WebElement.class);
        when(element.findElements(any())).thenReturn(Arrays.asList(result1, result2));
        List<WebComponent> result = testSubject.findComponents(By.id("some-id"));
        assertEquals(result1, result.get(0).getElement());
        assertEquals(result2, result.get(1).getElement());
    }

    @Test
    void findComponent() {
        WebElement result1 = mock(WebElement.class);
        when(element.findElement(any())).thenReturn(result1);
        WebComponent result = testSubject.findComponent(By.id("sss"));
        assertEquals(result1, result.getElement());
    }

    @Test
    @SuppressWarnings("all")
    void to() {
        WebComponentFactory<WebComponent> factory = mock(WebComponentFactory.class);
        testSubject.to(factory);
        verify(factory, only()).apply(eq(element), eq(driver));
    }

    @Test
    void attributeContains() {
        when(element.getAttribute(eq("aaa"))).thenReturn("aaa bbb ccc");
        assertTrue(testSubject.attributeContains("aaa", "bbb"));
        verify(element, only()).getAttribute("aaa");
    }

    @Test
    void as() {
        Components components = mock(Components.class);
        assertEquals(components, testSubject.as(components));
        verify(components, only()).setContext(eq(testSubject), eq(driver));
    }
}