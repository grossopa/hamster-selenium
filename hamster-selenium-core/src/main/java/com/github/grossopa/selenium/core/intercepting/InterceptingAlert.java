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

import org.openqa.selenium.Alert;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static java.util.Objects.requireNonNull;

/**
 * Intercepts the {@link org.openqa.selenium.Alert} actions with customized handlers.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class InterceptingAlert implements Alert {

    private final Alert alert;
    private final InterceptingHandler handler;


    /**
     * Constructs an instance with target delegated {@link Alert} instance.
     *
     * @param alert the alert to delegate
     * @param handler the handler for before, after and on exception actions.
     */
    public InterceptingAlert(Alert alert, InterceptingHandler handler) {
        requireNonNull(alert);
        requireNonNull(handler);
        this.alert = alert;
        this.handler = handler;
    }

    @Override
    public void dismiss() {
        handler.execute(() -> {
            alert.dismiss();
            return null;
        }, MethodInfo.create(alert, ALERT_DISMISS));
    }

    @Override
    public void accept() {
        handler.execute(() -> {
            alert.accept();
            return null;
        }, MethodInfo.create(alert, ALERT_ACCEPT));
    }

    @Override
    public String getText() {
        return handler.execute(alert::getText, MethodInfo.create(alert, ALERT_GET_TEXT));
    }

    @Override
    public void sendKeys(String keysToSend) {
        handler.execute(() -> {
            alert.sendKeys(keysToSend);
            return null;
        }, MethodInfo.create(alert, ALERT_SEND_KEYS));
    }
}
