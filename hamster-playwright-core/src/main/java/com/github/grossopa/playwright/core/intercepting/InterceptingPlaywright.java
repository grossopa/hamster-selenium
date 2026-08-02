/*
 * Copyright © 2025 the original author or authors.
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

package com.github.grossopa.playwright.core.intercepting;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Selectors;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Intercepting wrapper for {@link Playwright} instance with customized handlers.
 *
 * @since 1.12
 */
public class InterceptingPlaywright implements Playwright {

    private final Playwright playwright;
    private final InterceptingHandler handler;

    /**
     * Constructs an instance with target delegated {@link Playwright} instance.
     *
     * @param playwright the playwright to delegate
     * @param handler the handler for before, after and on exception actions.
     */
    public InterceptingPlaywright(Playwright playwright, InterceptingHandler handler) {
        requireNonNull(playwright);
        requireNonNull(handler);
        this.playwright = playwright;
        this.handler = handler;
    }

    /**
     * Creates an instance with target delegated {@link Playwright} instance and default {@link LoggingHandler}.
     *
     * @param playwright the playwright to delegate
     * @return the created instance
     */
    public static InterceptingPlaywright create(Playwright playwright) {
        return new InterceptingPlaywright(playwright, new LoggingHandler(0L));
    }

    @Override
    public BrowserType chromium() {
        return handler.execute(playwright::chromium,
                MethodInfo.create(playwright, "playwright.chromium"));
    }

    @Override
    public BrowserType firefox() {
        return handler.execute(playwright::firefox,
                MethodInfo.create(playwright, "playwright.firefox"));
    }

    @Override
    public BrowserType webkit() {
        return handler.execute(playwright::webkit,
                MethodInfo.create(playwright, "playwright.webkit"));
    }

    @Override
    public APIRequest request() {
        return handler.execute(playwright::request,
                MethodInfo.create(playwright, "playwright.request"));
    }

    @Override
    public Selectors selectors() {
        return handler.execute(playwright::selectors,
                MethodInfo.create(playwright, "playwright.selectors"));
    }

    @Override
    public void close() {
        handler.execute((Supplier<Void>) () -> {
                    playwright.close();
                    return null;
                }, 
                MethodInfo.create(playwright, "playwright.close"));
    }

    @Override
    public String toString() {
        return playwright.toString();
    }
}