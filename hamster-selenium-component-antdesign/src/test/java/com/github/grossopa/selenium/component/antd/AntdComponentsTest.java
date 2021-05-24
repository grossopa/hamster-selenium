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

package com.github.grossopa.selenium.component.antd;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.component.antd.general.AntdButton;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.RemoteWebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AntdComponents}
 *
 * @author Jack Yin
 * @since 1.4
 */
class AntdComponentsTest {

    AntdComponents testSubject;
    AntdConfig config = mock(AntdConfig.class);
    WebComponent component = mock(WebComponent.class);
    RemoteWebElement element = mock(RemoteWebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);

    @BeforeEach
    void setUp() {
        when(component.getWrappedElement()).thenReturn(element);
        when(element.getWrappedDriver()).thenReturn(driver);

        testSubject = new AntdComponents();
        testSubject.setContext(component, driver);
    }

    @Test
    void antd() {
        assertNotNull(AntdComponents.antd());
    }

    @Test
    void antdWithConfig() {
        assertSame(config, AntdComponents.antd(config).config);
    }

    @Test
    void toButton() {
        AntdButton button = testSubject.toButton();
        assertEquals(element, button.getWrappedElement());
        assertEquals(driver, button.getWrappedDriver());
    }
}
