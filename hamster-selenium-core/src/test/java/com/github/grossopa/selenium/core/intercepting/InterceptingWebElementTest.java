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

import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static com.github.grossopa.selenium.core.intercepting.InterceptingTestHelper.afterEachVerify;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link InterceptingWebElement}
 *
 * @author Jack Yin
 * @since 1.4
 */
class InterceptingWebElementTest {

    InterceptingWebElement testSubject;
    RemoteWebElement element = mock(RemoteWebElement.class);
    InterceptingHandler handler = mock(InterceptingHandler.class);

    @BeforeEach
    void setUp() {
        when(handler.execute(any(), any())).thenCallRealMethod();
        testSubject = new InterceptingWebElement(element, handler);
    }

    @Test
    void click() {
        testSubject.click();
        verify(element, times(1)).click();
        afterEachVerify(handler, element, ELEMENT_CLICK, null);
    }

    @Test
    void submit() {
        testSubject.submit();
        verify(element, times(1)).submit();
        afterEachVerify(handler, element, ELEMENT_SUBMIT, null);
    }

    @Test
    void sendKeys() {
        testSubject.sendKeys("some-key");
        verify(element, times(1)).sendKeys("some-key");
        String[] params = new String[]{"some-key"};
        afterEachVerify(handler, element, ELEMENT_SEND_KEYS, null, (Object) params);
    }

    @Test
    void clear() {
        testSubject.clear();
        verify(element, times(1)).clear();
        afterEachVerify(handler, element, ELEMENT_CLEAR, null);
    }

    @Test
    void getTagName() {
        when(element.getTagName()).thenReturn("DIV");
        String result = testSubject.getTagName();
        assertEquals("DIV", result);
        verify(element, times(1)).getTagName();
        afterEachVerify(handler, element, ELEMENT_GET_TAG_NAME, "DIV");
    }

    @Test
    void getAttribute() {
        when(element.getAttribute("some-attr")).thenReturn("some-value");
        String result = testSubject.getAttribute("some-attr");
        assertEquals("some-value", result);
        verify(element, times(1)).getAttribute("some-attr");
        afterEachVerify(handler, element, ELEMENT_GET_ATTRIBUTE, "some-value", "some-attr");
    }

    @Test
    void isSelected() {
        when(element.isSelected()).thenReturn(true);
        boolean result = testSubject.isSelected();
        assertTrue(result);
        verify(element, times(1)).isSelected();
        afterEachVerify(handler, element, ELEMENT_IS_SELECTED, true);
    }

    @Test
    void isEnabled() {
        when(element.isEnabled()).thenReturn(true);
        boolean result = testSubject.isEnabled();
        assertTrue(result);
        verify(element, times(1)).isEnabled();
        afterEachVerify(handler, element, ELEMENT_IS_ENABLED, true);
    }

    @Test
    void getText() {
        when(element.getText()).thenReturn("DIV");
        String result = testSubject.getText();
        assertEquals("DIV", result);
        verify(element, times(1)).getText();
        afterEachVerify(handler, element, ELEMENT_GET_TEXT, "DIV");
    }

    @Test
    void findElements() {
        List<WebElement> resultElements = newArrayList(mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.id("333"))).thenReturn(resultElements);
        List<WebElement> result = testSubject.findElements(By.id("333"));
        assertArrayEquals(resultElements.toArray(),
                result.stream().map(el -> ((InterceptingWebElement) el).getWrappedElement()).toArray());
        afterEachVerify(handler, element, ELEMENT_FIND_ELEMENTS, result, By.id("333"));
    }

    @Test
    void findElement() {
        WebElement resultElement = mock(WebElement.class);
        when(element.findElement(By.id("333"))).thenReturn(resultElement);
        WebElement result = testSubject.findElement(By.id("333"));
        assertEquals(resultElement, ((InterceptingWebElement) result).getWrappedElement());
        afterEachVerify(handler, element, ELEMENT_FIND_ELEMENT, result, By.id("333"));
    }

    @Test
    void isDisplayed() {
        when(element.isDisplayed()).thenReturn(true);
        boolean result = testSubject.isDisplayed();
        assertTrue(result);
        verify(element, times(1)).isDisplayed();
        afterEachVerify(handler, element, ELEMENT_IS_DISPLAYED, true);
    }

    @Test
    void getLocation() {
        Point p = new Point(1, 2);
        when(element.getLocation()).thenReturn(p);
        Point result = testSubject.getLocation();
        assertEquals(p, result);
        verify(element, times(1)).getLocation();
        afterEachVerify(handler, element, ELEMENT_GET_LOCATION, p);
    }

    @Test
    void getSize() {
        Dimension d = new Dimension(1, 2);
        when(element.getSize()).thenReturn(d);
        Dimension result = testSubject.getSize();
        assertEquals(d, result);
        verify(element, times(1)).getSize();
        afterEachVerify(handler, element, ELEMENT_GET_SIZE, d);
    }

    @Test
    void getRect() {
        Rectangle d = new Rectangle(1, 2, 3, 4);
        when(element.getRect()).thenReturn(d);
        Rectangle result = testSubject.getRect();
        assertEquals(d, result);
        verify(element, times(1)).getRect();
        afterEachVerify(handler, element, ELEMENT_GET_RECT, d);
    }

    @Test
    void getCssValue() {
        when(element.getCssValue("ddd")).thenReturn("DIV");
        String result = testSubject.getCssValue("ddd");
        assertEquals("DIV", result);
        verify(element, times(1)).getCssValue("ddd");
        afterEachVerify(handler, element, ELEMENT_GET_CSS_VALUE, "DIV", "ddd");
    }

    @Test
    void getScreenshotAs() {
        byte[] bs = new byte[]{'a'};
        when(element.getScreenshotAs(OutputType.BYTES)).thenReturn(bs);
        byte[] result = testSubject.getScreenshotAs(OutputType.BYTES);
        assertEquals(bs, result);
        verify(element, times(1)).getScreenshotAs(OutputType.BYTES);
        afterEachVerify(handler, element, ELEMENT_GET_SCREENSHOT_AS, bs, OutputType.BYTES);
    }

    @Test
    void testGetter() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(InterceptingWebElement.class);
        Validator validator = ValidatorBuilder.create().with(new GetterMustExistRule()).with(new GetterTester())
                .build();
        validator.validate(pojoClass);
    }

    @Test
    void testEqualsAndHashCode() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        InterceptingHandler handler1 = mock(InterceptingHandler.class);
        InterceptingHandler handler2 = mock(InterceptingHandler.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new InterceptingWebElement(element1, handler1),
                new InterceptingWebElement(element1, handler1));
        tester.addEqualityGroup(new InterceptingWebElement(element2, handler1),
                new InterceptingWebElement(element2, handler1));
        tester.addEqualityGroup(new InterceptingWebElement(element1, handler2),
                new InterceptingWebElement(element1, handler2));
        tester.addEqualityGroup(new InterceptingWebElement(element2, handler2),
                new InterceptingWebElement(element2, handler2));
        tester.testEquals();
    }

    @Test
    void testToString() {
        WebElement element = mock(WebElement.class);
        InterceptingHandler handler = mock(InterceptingHandler.class);
        when(element.toString()).thenReturn("WebElement[aaabbb]");
        when(handler.toString()).thenReturn("InterceptingHandler[cccddd]");
        assertEquals("InterceptingWebElement{element=WebElement[aaabbb], handler=InterceptingHandler[cccddd]}",
                new InterceptingWebElement(element, handler).toString());
    }
}
