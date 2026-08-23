/*
 * Copyright © 2021 the original author or authors.
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
package com.github.grossopa.playwright.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractComponentsTest {

    private TestComponents components;

    static class TestComponents extends AbstractComponents {
        // concrete implementation for testing
    }

    @BeforeEach
    void setUp() {
        components = new TestComponents();
    }

    @Test
    void testSetContext() {
        WebComponent component = mock(WebComponent.class);
        ComponentDriver driver = mock(ComponentDriver.class);

        components.setContext(component, driver);

        assertEquals(component, components.getComponent());
        assertEquals(driver, components.getDriver());
    }

    @Test
    void testSetContextWithNullComponent() {
        ComponentDriver driver = mock(ComponentDriver.class);
        assertThrows(NullPointerException.class, () -> components.setContext(null, driver));
    }

    @Test
    void testSetContextWithNullDriver() {
        WebComponent component = mock(WebComponent.class);
        assertThrows(NullPointerException.class, () -> components.setContext(component, null));
    }

    @Test
    void testGetComponent() {
        assertNull(components.getComponent());
    }

    @Test
    void testGetDriver() {
        assertNull(components.getDriver());
    }
}
