/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.core;

import com.microsoft.playwright.*;

import java.util.List;
import java.util.function.Function;

/**
 * The abstract implementation of {@link ComponentDriver} with generic type support.
 *
 * <p>This class provides a base implementation for ComponentDriver that encapsulates
 * a standard Playwright and adds component-based interaction capabilities.
 * It serves as a foundation for concrete implementations that may add browser-specific
 * functionality or other customizations.</p>
 *
 * <p>Key features of this implementation include:
 * <ul>
 *   <li>Component-based element finding through {@link #findComponent(String)} and {@link #findComponents(String)}</li>
 *   <li>Utility methods for common web interactions</li>
 * </ul>
 *
 * @since 1.12
 * @see ComponentDriver
 * @see WebComponent
 */
public abstract class AbstractComponentDriver implements ComponentDriver {

    protected final Playwright playwright;
    protected final Browser browser;
    protected final BrowserContext context;
    protected final Page page;

    /**
     * Constructs an instance with given non-null {@link Playwright}, {@link Browser}, {@link BrowserContext} and {@link Page} instances.
     *
     * @param playwright the Playwright instance
     * @param browser the Browser instance
     * @param context the BrowserContext instance
     * @param page the Page instance
     */
    protected AbstractComponentDriver(Playwright playwright, Browser browser, BrowserContext context, Page page) {
        this.playwright = playwright;
        this.browser = browser;
        this.context = context;
        this.page = page;
    }

    @Override
    public List<WebComponent> findComponents(String selector) {
        return page.locator(selector).all().stream().map(this::mapLocator).toList();
    }

    @Override
    public <T> T findComponentAs(String selector, Function<WebComponent, T> mappingFunction) {
        return mappingFunction.apply(this.findComponent(selector));
    }

    @Override
    public <T> List<T> findComponentsAs(String selector, Function<WebComponent, T> mappingFunction) {
        return findComponents(selector).stream().map(mappingFunction).toList();
    }

    @Override
    public WebComponent findComponent(String selector) {
        return mapLocator(page.locator(selector));
    }

    @Override
    public Playwright playwright() {
        return playwright;
    }

    @Override
    public Browser browser() {
        return browser;
    }

    @Override
    public BrowserContext context() {
        return context;
    }

    @Override
    public Page page() {
        return page;
    }

    @Override
    public void navigate(String url) {
        page.navigate(url);
    }

    @Override
    public void navigate(String url, long timeoutInMillis) {
        page.navigate(url, new Page.NavigateOptions().setTimeout(timeoutInMillis));
    }
}