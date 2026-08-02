/*
 * Copyright © 2023 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.playwright.component.html;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link HtmlComponents}
 *
 * @author Jack Yin
 * @since 1.12
 */
class HtmlComponentsTest {

    HtmlComponents testSubject;
    ComponentDriver driver = mock(ComponentDriver.class);
    WebComponent component = mock(WebComponent.class);

    @BeforeEach
    void setUp() {
        testSubject = new HtmlComponents(driver);
        testSubject.setContext(component, driver);
    }

    @Test
    void testConstructor() {
        HtmlComponents instance = new HtmlComponents(driver);
        assertNotNull(instance);
    }

    @Test
    void html() {
        HtmlComponents instance = HtmlComponents.html(driver);
        assertNotNull(instance);
    }

    @Test
    void formField() {
        HtmlFormField result = testSubject.formField();
        assertNotNull(result);
        assertEquals(HtmlFormField.class, result.getClass());
    }

    @Test
    void select() {
        HtmlSelect result = testSubject.select();
        assertNotNull(result);
        assertEquals(HtmlSelect.class, result.getClass());
    }

    @Test
    void table() {
        HtmlTable result = testSubject.table();
        assertNotNull(result);
        assertEquals(HtmlTable.class, result.getClass());
    }

    @Test
    void getComponent() {
        assertEquals(component, testSubject.getComponent());
    }

    @Test
    void getDriver() {
        assertEquals(driver, testSubject.getDriver());
    }
}
