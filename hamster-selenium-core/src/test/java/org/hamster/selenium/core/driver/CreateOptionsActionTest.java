/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.core.driver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariOptions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests for {@link CreateOptionsAction}
 *
 * @author Jack Yin
 * @since 1.0
 */
class CreateOptionsActionTest {

    CreateOptionsAction testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new CreateOptionsAction();
    }


    @Test
    void applyChrome() {
        assertEquals(ChromeOptions.class, testSubject.applyChrome(null).getClass());
    }

    @Test
    void applyEdge() {
        assertEquals(EdgeOptions.class, testSubject.applyEdge(null).getClass());
    }

    @Test
    void applyFirefox() {
        assertEquals(FirefoxOptions.class, testSubject.applyFirefox(null).getClass());
    }

    @Test
    void applyIE() {
        assertEquals(InternetExplorerOptions.class, testSubject.applyIE(null).getClass());
    }

    @Test
    void applyOpera() {
        assertEquals(OperaOptions.class, testSubject.applyOpera(null).getClass());
    }

    @Test
    void applySafari() {
        assertEquals(SafariOptions.class, testSubject.applySafari(null).getClass());
    }
}