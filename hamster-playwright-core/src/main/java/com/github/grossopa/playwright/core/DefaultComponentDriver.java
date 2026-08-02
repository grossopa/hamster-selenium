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

/**
 * The default implementation of {@link ComponentDriver}
 *
 * @author Jack Yin
 * @since 1.0
 */
public class DefaultComponentDriver extends AbstractComponentDriver {

    /**
     * Constructs an instance with given non-null {@link Playwright} instance.
     *
     * @param playwright the existing non-null playwright to encapsulate
     */
    public DefaultComponentDriver(Playwright playwright) {
        this(playwright, playwright.chromium().launch());
    }

    /**
     * Constructs an instance with given non-null {@link Playwright} and {@link Browser} instances.
     *
     * @param playwright the existing non-null playwright to encapsulate
     * @param browser the browser instance
     */
    public DefaultComponentDriver(Playwright playwright, Browser browser) {
        this(playwright, browser, browser.newContext());
    }

    /**
     * Constructs an instance with given non-null {@link Playwright}, {@link Browser} and {@link BrowserContext} instances.
     *
     * @param playwright the existing non-null playwright to encapsulate
     * @param browser the browser instance
     * @param context the browser context instance
     */
    public DefaultComponentDriver(Playwright playwright, Browser browser, BrowserContext context) {
        this(playwright, browser, context, context.newPage());
    }

    /**
     * Constructs an instance with given non-null {@link Playwright}, {@link Browser}, {@link BrowserContext} and {@link Page} instances.
     *
     * @param playwright the existing non-null playwright to encapsulate
     * @param browser the browser instance
     * @param context the browser context instance
     * @param page the page instance
     */
    public DefaultComponentDriver(Playwright playwright, Browser browser, BrowserContext context, Page page) {
        super(playwright, browser, context, page);
    }

    @Override
    public WebComponent mapLocator(Object locator) {
        if (locator instanceof WebComponent) {
            return (WebComponent) locator;
        }
        return new DefaultWebComponent((Locator) locator, this);
    }
}