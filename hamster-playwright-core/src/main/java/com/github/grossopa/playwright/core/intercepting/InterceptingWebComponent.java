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
import com.microsoft.playwright.Locator;

import java.util.List;
import java.util.function.Function;

import static com.github.grossopa.playwright.core.intercepting.InterceptingMethods.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * An intercepting implementation of {@link WebComponent} that wraps another WebComponent and provides
 * interception capabilities.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class InterceptingWebComponent implements WebComponent {

    private final WebComponent component;
    private final InterceptingHandler handler;

    /**
     * Constructs an instance with given component and handler.
     *
     * @param component the component to wrap
     * @param handler the handler to use for interception
     */
    public InterceptingWebComponent(WebComponent component, InterceptingHandler handler) {
        requireNonNull(component);
        requireNonNull(handler);
        this.component = component;
        this.handler = handler;
    }

    @Override
    public List<WebComponent> findComponents(String selector) {
        return handler.execute(() -> component.findComponents(selector).stream()
                        .map(c -> new InterceptingWebComponent(c, handler)).collect(toList()),
                MethodInfo.create(component, COMPONENT_FIND_COMPONENTS, selector));
    }

    @Override
    public WebComponent findComponent(String selector) {
        return handler.execute(() -> new InterceptingWebComponent(component.findComponent(selector), handler),
                MethodInfo.create(component, COMPONENT_FIND_COMPONENT, selector));
    }

    @Override
    public <T> T as(Function<WebComponent, T> mapper) {
        return component.as(mapper);
    }

    @Override
    public Locator locator() {
        return component.locator();
    }

    @Override
    public void click() {
        handler.execute(() -> {
            component.click();
            return null;
        }, MethodInfo.create(component, COMPONENT_CLICK));
    }

    @Override
    public void hover() {
        handler.execute(() -> {
            component.hover();
            return null;
        }, MethodInfo.create(component, COMPONENT_HOVER));
    }

    @Override
    public String textContent() {
        return handler.execute(component::textContent,
                MethodInfo.create(component, COMPONENT_TEXT_CONTENT));
    }

    @Override
    public String innerText() {
        return handler.execute(component::innerText,
                MethodInfo.create(component, COMPONENT_INNER_TEXT));
    }

    @Override
    public String innerHTML() {
        return handler.execute(component::innerHTML,
                MethodInfo.create(component, COMPONENT_INNER_HTML));
    }

    @Override
    public String getAttribute(String name) {
        return handler.execute(() -> component.getAttribute(name),
                MethodInfo.create(component, COMPONENT_GET_ATTRIBUTE, name));
    }

    @Override
    public boolean isVisible() {
        return handler.execute(component::isVisible,
                MethodInfo.create(component, COMPONENT_IS_VISIBLE));
    }

    @Override
    public boolean isEnabled() {
        return handler.execute(component::isEnabled,
                MethodInfo.create(component, COMPONENT_IS_ENABLED));
    }

    @Override
    public boolean isDisabled() {
        return handler.execute(component::isDisabled,
                MethodInfo.create(component, COMPONENT_IS_DISABLED));
    }

    @Override
    public void fill(String value) {
        handler.execute(() -> {
            component.fill(value);
            return null;
        }, MethodInfo.create(component, COMPONENT_FILL, value));
    }

    @Override
    public ComponentDriver driver() {
        return new InterceptingComponentDriver(component.driver(), handler);
    }
}