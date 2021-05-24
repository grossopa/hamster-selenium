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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static com.github.grossopa.selenium.core.intercepting.InterceptingTestHelper.afterEachVerify;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link InterceptingNavigation}
 *
 * @author Jack Yin
 * @since 1.4
 */
class InterceptingNavigationTest {

    InterceptingNavigation testSubject;
    WebDriver.Navigation navigation = mock(WebDriver.Navigation.class);
    InterceptingHandler handler = mock(InterceptingHandler.class);

    @BeforeEach
    void setUp() {
        when(handler.execute(any(), any())).thenCallRealMethod();
        testSubject = new InterceptingNavigation(navigation, handler);
    }

    @Test
    void back() {
        testSubject.back();
        verify(navigation, times(1)).back();
        afterEachVerify(handler, navigation, NAVIGATION_BACK, null);
    }

    @Test
    void forward() {
        testSubject.forward();
        verify(navigation, times(1)).forward();
        afterEachVerify(handler, navigation, NAVIGATION_FORWARD, null);
    }

    @Test
    void to() {
        testSubject.to("http://www.apple.com");
        verify(navigation, times(1)).to("http://www.apple.com");
        afterEachVerify(handler, navigation, NAVIGATION_TO, null, "http://www.apple.com");
    }

    @Test
    void toUrl() throws MalformedURLException {
        testSubject.to(new URL("http://www.apple.com"));
        verify(navigation, times(1)).to(new URL("http://www.apple.com"));
        afterEachVerify(handler, navigation, NAVIGATION_TO, null, new URL("http://www.apple.com"));
    }

    @Test
    void refresh() {
        testSubject.refresh();
        verify(navigation, times(1)).refresh();
        afterEachVerify(handler, navigation, NAVIGATION_REFRESH, null);
    }


}
