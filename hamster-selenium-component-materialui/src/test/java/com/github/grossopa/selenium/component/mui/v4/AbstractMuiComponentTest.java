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

package com.github.grossopa.selenium.component.mui.v4;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AbstractMuiComponent}
 *
 * @author Jack Yin
 * @since 1.0
 */
class AbstractMuiComponentV4Test {

    AbstractMuiComponent testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new AbstractMuiComponent(element, driver, config) {

            @Override
            public String getComponentName() {
                return "MockComponent";
            }
        };
    }

    @Test
    void versions() {
        assertArrayEquals(EnumSet.of(V4).toArray(), testSubject.versions().toArray());
    }

    @Test
    void isEnabled() {
        when(config.isDisabled(any())).thenReturn(false);
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledNegative() {
        when(config.isDisabled(any())).thenReturn(true);
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void validate() {
        when(config.validateComponentByCss(any(), any())).thenReturn(true);
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(config.validateComponentByCss(any(), any())).thenReturn(false);
        assertFalse(testSubject.validate());
    }

    @Test
    void getComponentName() {
        assertEquals("MockComponent", testSubject.getComponentName());
    }

    @Test
    void config() {
        assertEquals(config, testSubject.config());
    }

    @Test
    void testEquals() {
        MuiConfig config1 = mock(MuiConfig.class);
        MuiConfig config2 = mock(MuiConfig.class);
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new SimpleMuiComponent(element1, driver, config1),
                new SimpleMuiComponent(element1, driver, config1));
        tester.addEqualityGroup(new SimpleMuiComponent(element1, driver, config2));
        tester.addEqualityGroup(new SimpleMuiComponent(element2, driver, config1));
        tester.addEqualityGroup(new SimpleMuiComponent(element2, driver, config2));

        tester.testEquals();
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-tostring");
        assertEquals("{element=element-tostring}", testSubject.toString());
    }
}

class SimpleMuiComponent extends AbstractMuiComponent {

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public SimpleMuiComponent(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "a";
    }
}
