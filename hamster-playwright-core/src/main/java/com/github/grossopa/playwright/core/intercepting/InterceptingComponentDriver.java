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

package com.github.grossopa.playwright.core.intercepting;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.*;

import java.util.List;
import java.util.function.Function;

import static com.github.grossopa.playwright.core.intercepting.InterceptingMethods.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * An intercepting implementation of {@link ComponentDriver} that wraps another ComponentDriver and provides
 * interception capabilities.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class InterceptingComponentDriver implements ComponentDriver {

    private final ComponentDriver driver;
    private final InterceptingHandler handler;

    /**
     * Constructs an instance with given driver and handler.
     *
     * @param driver the driver to wrap
     * @param handler the handler to use for interception
     */
    public InterceptingComponentDriver(ComponentDriver driver, InterceptingHandler handler) {
        requireNonNull(driver);
        requireNonNull(handler);
        this.driver = driver;
        this.handler = handler;
    }

    @Override
    public List<WebComponent> findComponents(String selector) {
        return handler.execute(() -> driver.findComponents(selector).stream()
                        .map(c -> new InterceptingWebComponent(c, handler)).collect(toList()),
                MethodInfo.create(driver, DRIVER_FIND_COMPONENTS, selector));
    }

    @Override
    public <T> T findComponentAs(String selector, Function<WebComponent, T> mappingFunction) {
        return handler.execute(() -> driver.findComponentAs(selector, mappingFunction),
                MethodInfo.create(driver, DRIVER_FIND_COMPONENT_AS, selector, mappingFunction));
    }

    @Override
    public <T> List<T> findComponentsAs(String selector, Function<WebComponent, T> mappingFunction) {
        return handler.execute(() -> driver.findComponentsAs(selector, mappingFunction),
                MethodInfo.create(driver, DRIVER_FIND_COMPONENTS_AS, selector, mappingFunction));
    }

    @Override
    public WebComponent findComponent(String selector) {
        return handler.execute(() -> new InterceptingWebComponent(driver.findComponent(selector), handler),
                MethodInfo.create(driver, DRIVER_FIND_COMPONENT, selector));
    }

    @Override
    public WebComponent mapLocator(Object locator) {
        return driver.mapLocator(locator);
    }

    @Override
    public Playwright playwright() {
        return driver.playwright();
    }

    @Override
    public Browser browser() {
        return driver.browser();
    }

    @Override
    public BrowserContext context() {
        return driver.context();
    }

    @Override
    public Page page() {
        return driver.page();
    }

    @Override
    public void navigate(String url) {
        handler.execute(() -> {
            driver.navigate(url);
            return null;
        }, MethodInfo.create(driver, DRIVER_NAVIGATE, url));
    }
}