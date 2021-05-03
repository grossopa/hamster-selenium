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

package com.github.grossopa.selenium.core.driver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriverBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link CreateWebDriverAction}
 *
 * @author Jack Yin
 * @since 1.0
 */
@SuppressWarnings("ALL")
class CreateWebDriverActionTest {

    CreateWebDriverAction testSubject;
    RemoteWebDriverBuilder builder = mock(RemoteWebDriverBuilder.class);
    CreateWebDriverParams params = mock(CreateWebDriverParams.class);
    WebDriver webDriver = mock(WebDriver.class);

    @BeforeEach
    void setUp() {
        when(builder.addAlternative(any())).thenReturn(builder);
        when(builder.withDriverService(any())).thenReturn(builder);
        when(builder.build()).thenReturn(webDriver);
        testSubject = new CreateWebDriverAction() {
            @Override
            protected RemoteWebDriverBuilder getBuilder() {
                return builder;
            }
        };
    }

    private void verifyAll() {
        verify(builder, times(1)).addAlternative(any());
        verify(builder, times(1)).withDriverService(any());
        verify(params, times(1)).getOptions();
        verify(params, times(1)).getDriverService();
    }

    @Test
    void applyChrome() {
        assertEquals(webDriver, testSubject.applyChrome(params));
        verifyAll();

    }

    @Test
    void applyEdge() {
        assertEquals(webDriver, testSubject.applyEdge(params));
        verifyAll();
    }

    @Test
    void applyFirefox() {
        assertEquals(webDriver, testSubject.applyFirefox(params));
        verifyAll();
    }

    @Test
    void applyIE() {
        assertEquals(webDriver, testSubject.applyIE(params));
        verifyAll();
    }

    @Test
    void applyOpera() {
        assertEquals(webDriver, testSubject.applyOpera(params));
        verifyAll();
    }

    @Test
    void applySafari() {
        assertEquals(webDriver, testSubject.applySafari(params));
        verifyAll();
    }

    @Test
    void getBuilder() {
        assertNotNull(new CreateWebDriverAction().getBuilder());
    }
}
