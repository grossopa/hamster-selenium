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

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.List;
import java.util.function.Function;

/**
 * An encapsulated {@link Playwright} instance that supports to get the web element as {@link WebComponent}.
 *
 * <p>This interface provides Playwright-based implementation similar to Selenium's ComponentWebDriver,
 * allowing component-oriented web automation capabilities. Instead of working directly with Playwright's
 * Locator instances, it allows finding and interacting with {@link WebComponent} objects that provide
 * higher-level abstractions for common UI components.</p>
 *
 * <p>Key benefits of using ComponentDriver:
 * <ul>
 *   <li><strong>Component-based approach:</strong> Work with high-level components instead of low-level elements</li>
 *   <li><strong>Type safety:</strong> Find components as specific subtypes using {@link #findComponentAs(String, Function)}</li>
 *   <li><strong>Enhanced utilities:</strong> Built-in support for common operations like scrolling and waiting</li>
 *   <li><strong>Seamless integration:</strong> Full compatibility with standard Playwright operations</li>
 * </ul>
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * ComponentDriver driver = new DefaultComponentDriver(playwright);
 * // Find a component and interact with it
 * WebComponent component = driver.findComponent(".my-component");
 * component.click();
 *
 * // Find a component as a specific type
 * HtmlButton button = driver.findComponentAs("button", HtmlComponents.html()::toButton);
 * button.click();
 * }</pre>
 * </p>
 *
 * @author Jack Yin
 * @since 1.0
 * @see WebComponent
 * @see AbstractComponentDriver
 */
public interface ComponentDriver {

    /**
     * Finds all elements within the current page using the given selector and encapsulate the {@link com.microsoft.playwright.Locator} list
     * into {@link WebComponent}.
     *
     * @param selector The locating selector to use
     * @return A list of all {@link WebComponent}s, or an empty list if nothing matches
     * @see com.microsoft.playwright.Page#locator(String)
     */
    List<WebComponent> findComponents(String selector);

    /**
     * Find the first {@link com.microsoft.playwright.Locator} using the given method and encapsulate it into {@link T} which is sub type of
     * WebComponent.
     *
     * @param selector The locating selector
     * @param mappingFunction the mapping function to convert {@link WebComponent} to {@link T}.
     * @param <T> the target type
     * @return The first matching element on the current page
     * @see com.microsoft.playwright.Page#locator(String)
     */
    <T> T findComponentAs(String selector, Function<WebComponent, T> mappingFunction);

    /**
     * Finds all elements within the current page using the given selector and encapsulate the {@link com.microsoft.playwright.Locator} list
     * into {@link T} which is sub type of WebComponent.
     *
     * @param selector The locating selector to use
     * @param mappingFunction the mapping function to convert {@link WebComponent} to {@link T}.
     * @param <T> the target type
     * @return A list of all {@link WebComponent}s, or an empty list if nothing matches
     * @see com.microsoft.playwright.Page#locator(String)
     */
    <T> List<T> findComponentsAs(String selector, Function<WebComponent, T> mappingFunction);

    /**
     * Find the first {@link com.microsoft.playwright.Locator} using the given method and encapsulate it into {@link WebComponent}.
     *
     * @param selector The locating selector
     * @return The first matching element on the current page
     * @see com.microsoft.playwright.Page#locator(String)
     */
    WebComponent findComponent(String selector);

    /**
     * Maps a given {@link com.microsoft.playwright.Locator} to {@link WebComponent}.
     *
     * @param locator the locator instance to map
     * @return the mapped {@link WebComponent} instance
     */
    WebComponent mapLocator(Object locator);

    /**
     * Gets the underlying {@link Playwright} instance.
     *
     * @return the playwright instance
     */
    Playwright playwright();

    /**
     * Gets the underlying {@link Browser} instance.
     *
     * @return the browser instance
     */
    Browser browser();

    /**
     * Gets the underlying {@link BrowserContext} instance.
     *
     * @return the browser context instance
     */
    BrowserContext context();

    /**
     * Gets the underlying {@link Page} instance.
     *
     * @return the page instance
     */
    Page page();

    /**
     * Navigates to the given URL.
     *
     * @param url the URL to navigate to
     */
    void navigate(String url);
}