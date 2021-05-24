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

package com.github.grossopa.selenium.core.element;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link CompositeWebElementDecorator}
 *
 * @author Jack Yin
 * @since 1.4
 */
class CompositeWebElementDecoratorTest {

    CompositeWebElementDecorator testSubject;
    List<WebElementDecorator> decoratorList;
    WebElement originalElement = mock(WebElement.class);
    WebElement element1 = mock(WebElement.class);
    WebElement element2 = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);

    @BeforeEach
    void setUp() {
        WebElementDecorator decorator1 = mock(WebElementDecorator.class);
        WebElementDecorator decorator2 = mock(WebElementDecorator.class);
        when(decorator1.decorate(originalElement, driver)).thenReturn(element1);
        when(decorator2.decorate(element1, driver)).thenReturn(element2);

        decoratorList = newArrayList(decorator1, decorator2);
        testSubject = new CompositeWebElementDecorator(decoratorList);
    }

    @Test
    void decorate() {
        assertEquals(element2, testSubject.decorate(originalElement, driver));
    }

    @Test
    void getDecoratorList() {
        assertArrayEquals(decoratorList.toArray(), testSubject.getDecoratorList().toArray());
    }
}
