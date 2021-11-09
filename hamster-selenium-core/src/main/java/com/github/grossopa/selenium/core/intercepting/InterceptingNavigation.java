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

import org.openqa.selenium.WebDriver;

import java.net.URL;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static java.util.Objects.requireNonNull;

/**
 * Intercepts the {@link WebDriver.Navigation} actions with customized handlers.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class InterceptingNavigation implements WebDriver.Navigation {

    private final WebDriver.Navigation navigation;
    private final InterceptingHandler handler;

    /**
     * Constructs an instance with target delegated {@link WebDriver.TargetLocator} instance.
     *
     * @param navigation the navigation to delegate
     * @param handler the handler for before, after and on exception actions.
     */
    public InterceptingNavigation(WebDriver.Navigation navigation, InterceptingHandler handler) {
        requireNonNull(navigation);
        requireNonNull(handler);
        this.navigation = navigation;
        this.handler = handler;
    }

    @Override
    public void back() {
        handler.execute(() -> {
            navigation.back();
            return null;
        }, MethodInfo.create(navigation, NAVIGATION_BACK));
    }

    @Override
    public void forward() {
        handler.execute(() -> {
            navigation.forward();
            return null;
        }, MethodInfo.create(navigation, NAVIGATION_FORWARD));
    }

    @Override
    public void to(String url) {
        handler.execute(() -> {
            navigation.to(url);
            return null;
        }, MethodInfo.create(navigation, NAVIGATION_TO, url));
    }

    @Override
    public void to(URL url) {
        handler.execute(() -> {
            navigation.to(url);
            return null;
        }, MethodInfo.create(navigation, NAVIGATION_TO, url));
    }

    @Override
    public void refresh() {
        handler.execute(() -> {
            navigation.refresh();
            return null;
        }, MethodInfo.create(navigation, NAVIGATION_REFRESH));
    }


}
