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

package com.github.grossopa.selenium.component.html;

import com.github.grossopa.selenium.component.html.factory.HtmlSelectFactory;
import com.github.grossopa.selenium.component.html.factory.HtmlTableFactory;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link HtmlComponents}
 *
 * @author Jack Yin
 * @since 1.0
 */
class HtmlComponentsTest {

    HtmlComponents testSubject;
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    WebElement element = mock(WebElement.class);
    WebComponent component = mock(WebComponent.class);

    @BeforeEach
    void setUp() {
        when(component.getWrappedElement()).thenReturn(element);
        testSubject = new HtmlComponents();
    }


    @Test
    void toFormField() {
        when(component.to(any())).thenReturn(new HtmlFormField(element, driver));
        testSubject.setContext(component, driver);
        assertEquals(HtmlFormField.class, testSubject.toFormField().getClass());
    }

    @Test
    void toTable() {
        when(element.getTagName()).thenReturn("table");
        HtmlTable htmlTable = new HtmlTable(element, driver);
        when(component.to(any(HtmlTableFactory.class))).thenReturn(htmlTable);
        testSubject.setContext(component, driver);
        assertEquals(HtmlTable.class, testSubject.toTable().getClass());
    }

    @Test
    void toSelect() {
        when(element.getTagName()).thenReturn("select");
        HtmlSelect htmlSelect = new HtmlSelect(element, driver);
        when(component.to(any(HtmlSelectFactory.class))).thenReturn(htmlSelect);
        testSubject.setContext(component, driver);
        assertEquals(HtmlSelect.class, testSubject.toSelect().getClass());
    }

    @Test
    void html() {
        assertNotNull(HtmlComponents.html());
    }
}
