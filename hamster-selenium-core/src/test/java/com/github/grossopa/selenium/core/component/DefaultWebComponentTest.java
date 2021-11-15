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

package com.github.grossopa.selenium.core.component;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.factory.WebComponentFactory;
import com.github.grossopa.selenium.core.element.TextNodeElement;
import com.github.grossopa.selenium.core.element.TextNodeType;
import com.github.grossopa.selenium.core.element.WebElementDecorator;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link DefaultWebComponent}
 *
 * @author Jack Yin
 * @since 1.0
 */
class DefaultWebComponentTest {

    DefaultWebComponent testSubject;
    RemoteWebElement element = mock(RemoteWebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);

    @BeforeEach
    void setUp() {
        testSubject = new DefaultWebComponent(element, driver);
    }

    @Test
    void getWrappedElement() {
        assertEquals(element, testSubject.getWrappedElement());
    }

    @Test
    void findComponents() {
        WebElement result1 = mock(WebElement.class);
        WebElement result2 = mock(WebElement.class);
        when(element.findElements(any())).thenReturn(Arrays.asList(result1, result2));
        List<WebComponent> result = testSubject.findComponents(By.id("some-id"));
        assertEquals(result1, result.get(0).getWrappedElement());
        assertEquals(result2, result.get(1).getWrappedElement());
    }

    @Test
    void findComponent() {
        WebElement result1 = mock(WebElement.class);
        when(element.findElement(any())).thenReturn(result1);
        WebComponent result = testSubject.findComponent(By.id("sss"));
        assertEquals(result1, result.getWrappedElement());
    }

    @Test
    @SuppressWarnings("all")
    void to() {
        WebComponentFactory<WebComponent> factory = mock(WebComponentFactory.class);
        testSubject.to(factory);
        verify(factory, only()).apply(element, driver);
    }

    @Test
    void attributeContains() {
        when(element.getAttribute("aaa")).thenReturn("aaa bbb ccc");
        assertTrue(testSubject.attributeContains("aaa", "bbb"));
        verify(element, only()).getAttribute("aaa");
    }

    @Test
    void as() {
        Components components = mock(Components.class);
        assertEquals(components, testSubject.as(components));
        verify(components, only()).setContext(testSubject, driver);
    }

    @Test
    @SuppressWarnings("unchecked")
    void findComponentsAs() {
        WebElement component1 = mock(WebElement.class);
        WebElement component2 = mock(WebElement.class);
        WebElement component3 = mock(WebElement.class);
        Function<WebComponent, WebComponent> mappingFunction = mock(Function.class);
        when(element.findElements(By.id("ddd"))).thenReturn(newArrayList(component1, component2, component3));
        testSubject.findComponentsAs(By.id("ddd"), mappingFunction);
        verify(mappingFunction, times(3)).apply(any());
    }

    @Test
    @SuppressWarnings("unchecked")
    void findComponentAs() {
        WebElement component1 = mock(WebElement.class);
        Function<WebComponent, WebComponent> mappingFunction = mock(Function.class);
        when(element.findElement(By.id("ddd"))).thenReturn(component1);
        testSubject.findComponentAs(By.id("ddd"), mappingFunction);
        verify(mappingFunction, times(1)).apply(any());
    }

    @Test
    void isFocusedTrue() {
        WebDriver.TargetLocator targetLocator = mock(WebDriver.TargetLocator.class);
        when(driver.switchTo()).thenReturn(targetLocator);
        when(targetLocator.activeElement()).thenReturn(element);

        assertTrue(testSubject.isFocused());
    }

    @Test
    void isFocusedFalse() {
        WebDriver.TargetLocator targetLocator = mock(WebDriver.TargetLocator.class);
        when(driver.switchTo()).thenReturn(targetLocator);
        when(targetLocator.activeElement()).thenReturn(null);

        assertFalse(testSubject.isFocused());
    }

    @Test
    void outerHTML() {
        when(element.getAttribute("outerHTML")).thenReturn("some-outer-html");
        assertEquals("some-outer-html", testSubject.outerHTML());
    }

    @Test
    void driver() {
        assertEquals(driver, testSubject.driver());
    }

    @Test
    void validate() {
        assertTrue(testSubject.validate());
    }

    @Test
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        ComponentWebDriver driver1 = mock(ComponentWebDriver.class);
        ComponentWebDriver driver2 = mock(ComponentWebDriver.class);
        WebElementDecorator decorator = mock(WebElementDecorator.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new DefaultWebComponent(element1, driver1, decorator),
                new DefaultWebComponent(element1, driver1, decorator));
        tester.addEqualityGroup(new DefaultWebComponent(element1, driver2, decorator));
        tester.addEqualityGroup(new DefaultWebComponent(element2, driver1, decorator));
        tester.addEqualityGroup(new DefaultWebComponent(element2, driver2, decorator));

        tester.testEquals();
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-string");
        assertEquals("DefaultWebComponent{element=element-string}", testSubject.toString());
    }

    @Test
    void styleContains() {
        when(element.getAttribute("style")).thenReturn("display:block; background-color : black");
        assertTrue(testSubject.styleContains("background-color", "black"));
        assertFalse(testSubject.styleContains("background-color", "white"));
    }

    @Test
    void getId() {
        when(element.getId()).thenReturn("abc");
        assertEquals("abc", testSubject.getId());
    }

    @Test
    void findTextNodes() {
        List<Object> childNodesResult = newArrayList();

        //@formatter:off
        when(driver.executeScript(""
                + "var nodes = arguments[0].childNodes;"
                + "var result = [];"
                + "for (var i = 0; i < nodes.length; i++) {"
                + "  if (nodes[i].nodeName === '#text' || nodes[i].nodeName === '#comment') {"
                + "    result.push({nodeName:nodes[i].nodeName, nodeType:nodes[i].nodeType, "
                + "nodeValue:nodes[i].nodeValue, textContent:nodes[i].textContent, "
                + "wholeText:nodes[i].wholeText, data:nodes[i].data});"
                + "  }"
                + "}"
                + "return result;", element)).thenReturn(childNodesResult);
        //@formatter:on

        Map<String, Object> map1 = ImmutableMap.of("nodeName", "#text", "nodeType", 3, "nodeValue", "some value",
                "textContent", "some value", "wholeText", "some value", "data", "some value");

        Map<String, Object> map2 = ImmutableMap.of("nodeName", "#comment", "nodeType", 8, "nodeValue", "some comment\n",
                "textContent", "some comment\n", "wholeText", "some comment\n", "data", "some comment\n");

        childNodesResult.add(map1);
        childNodesResult.add(map2);

        List<TextNodeElement> textNodeElements = testSubject.findTextNodes();

        assertEquals(2, textNodeElements.size());
        assertEquals(TextNodeType.TEXT, textNodeElements.get(0).getType());
        assertEquals("some value", textNodeElements.get(0).getText());

        assertEquals(TextNodeType.COMMENT, textNodeElements.get(1).getType());
        assertEquals("some comment", textNodeElements.get(1).getText());
    }
}
