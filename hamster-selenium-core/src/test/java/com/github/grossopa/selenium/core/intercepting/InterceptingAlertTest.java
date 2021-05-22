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
import org.openqa.selenium.Alert;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static com.github.grossopa.selenium.core.intercepting.InterceptingTestHelper.afterEachVerify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link InterceptingAlert}
 *
 * @author Jack Yin
 * @since 1.4
 */
class InterceptingAlertTest {

    InterceptingAlert testSubject;
    private final Alert alert = mock(Alert.class);
    private final InterceptingHandler handler = mock(InterceptingHandler.class);

    @BeforeEach
    void setUp() {
        when(handler.execute(any(), any())).thenCallRealMethod();
        testSubject = new InterceptingAlert(alert, handler);
    }

    @Test
    void dismiss() {
        testSubject.dismiss();
        verify(alert, times(1)).dismiss();
        afterEachVerify(handler, alert, ALERT_DISMISS, null);
    }

    @Test
    void accept() {
        testSubject.accept();
        verify(alert, times(1)).accept();
        afterEachVerify(handler, alert, ALERT_ACCEPT, null);
    }

    @Test
    void getText() {
        when(alert.getText()).thenReturn("aaa");
        assertEquals("aaa", testSubject.getText());
        verify(alert, times(1)).getText();
        afterEachVerify(handler, alert, ALERT_GET_TEXT, "aaa");
    }

    @Test
    void sendKeys() {
        testSubject.sendKeys("somekey");
        verify(alert, times(1)).sendKeys("somekey");
        afterEachVerify(handler, alert, ALERT_SEND_KEYS, null, "somekey");
    }


}
