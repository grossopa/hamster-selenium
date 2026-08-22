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

import com.microsoft.playwright.Locator;

import java.util.List;
import java.util.function.Function;


/**
 * The default implementation of {@link WebComponent}
 *
 * @author Jack Yin
 * @since 1.12
 */
public class DefaultWebComponent extends AbstractDelegatedLocator implements WebComponent {

    protected final ComponentDriver driver;
    /**
     * Constructs with the given locator and driver
     *
     * @param locator the locator instance
     * @param driver the driver instance
     */
    public DefaultWebComponent(Locator locator, ComponentDriver driver) {
        super(locator);
        this.driver = driver;
    }

    @Override
    public List<WebComponent> findComponents(String selector) {
        return locator.locator(selector).all().stream().<WebComponent>map(l -> new DefaultWebComponent(l, driver)).toList();
    }

    @Override
    public WebComponent findComponent(String selector) {
        return new DefaultWebComponent(locator.locator(selector).first(), driver);
    }

    @Override
    public <T> T as(Function<WebComponent, T> mapper) {
        return mapper.apply(this);
    }

    @Override
    public Locator locator() {
        return locator;
    }

    @Override
    public void click() {
        locator.click();
    }

    @Override
    public void hover() {
        locator.hover();
    }

    @Override
    public String textContent() {
        return locator.textContent();
    }

    @Override
    public String innerText() {
        return locator.innerText();
    }

    @Override
    public String innerHTML() {
        return locator.innerHTML();
    }

    @Override
    public String getAttribute(String name) {
        return locator.getAttribute(name);
    }

    @Override
    public boolean isVisible() {
        return locator.isVisible();
    }

    @Override
    public boolean isEnabled() {
        return locator.isEnabled();
    }

    @Override
    public boolean isDisabled() {
        return locator.isDisabled();
    }

    @Override
    public void fill(String value) {
        locator.fill(value);
    }

    @Override
    public ComponentDriver driver() {
        return driver;
    }

    public String getComponentTagName() {
        return "DefaultWebComponent";
    }
}