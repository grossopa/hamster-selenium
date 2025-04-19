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

package com.github.grossopa.selenium.component.mui.v4.finder;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiModalFinder}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiModalFinderTest {

    MuiModalFinder testSubject;
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiModalFinder(driver, config);
    }


    @Test
    void testEquals() {
        ComponentWebDriver driver1 = mock(ComponentWebDriver.class);
        ComponentWebDriver driver2 = mock(ComponentWebDriver.class);
        MuiConfig config1 = mock(MuiConfig.class);
        MuiConfig config2 = mock(MuiConfig.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new MuiModalFinder(driver1, config1), new MuiModalFinder(driver1, config1));
        tester.addEqualityGroup(new MuiModalFinder(driver1, config2));
        tester.addEqualityGroup(new MuiModalFinder(driver2, config1));
        tester.addEqualityGroup(new MuiModalFinder(driver2, config2));

        tester.testEquals();
    }

    @Test
    void testToString() {
        when(driver.toString()).thenReturn("driver-toString");
        when(config.toString()).thenReturn("config-toString");
        assertEquals("MuiModalFinder{driver=driver-toString, config=config-toString}", testSubject.toString());
    }
}
