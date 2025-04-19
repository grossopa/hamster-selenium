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

package com.github.grossopa.selenium.core;

import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.element.NoOpWebElementDecorator;
import com.github.grossopa.selenium.core.element.WebElementDecorator;
import com.github.grossopa.selenium.core.util.GracefulThreadSleep;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The default implementation of {@link ComponentWebDriver}
 *
 * @author Jack Yin
 * @since 1.0
 */
public class DefaultComponentWebDriver extends AbstractComponentWebDriver {

    protected final WebElementDecorator decorator;

    /**
     * Constructs an instance with given non-null {@link WebDriver} instance.
     *
     * @param driver the existing non-null driver to encapsulate
     */
    public DefaultComponentWebDriver(WebDriver driver) {
        this(driver, null, null);
    }

    /**
     * Constructs an instance with given non-null {@link WebDriver} and {@link GracefulThreadSleep} instances.
     *
     * @param driver the existing non-null driver to encapsulate
     * @param threadSleep the graceful thread sleep instance
     */
    public DefaultComponentWebDriver(WebDriver driver, GracefulThreadSleep threadSleep) {
        this(driver, threadSleep, null);
    }

    /**
     * Constructs an instance with given non-null {@link WebDriver},  {@link GracefulThreadSleep} and {@link
     * WebElementDecorator} for decorating the found elements.
     *
     * @param driver the existing non-null driver to encapsulate
     * @param threadSleep the graceful thread sleep instance
     * @param decorator optional, the decorator for decorating the found {@link WebElement}
     */
    public DefaultComponentWebDriver(WebDriver driver, GracefulThreadSleep threadSleep, WebElementDecorator decorator) {
        super(driver, threadSleep);
        this.decorator = defaultIfNull(decorator, new NoOpWebElementDecorator());
    }

    @Override
    public WebComponent mapElement(WebElement element) {
        // if given element is already a web component then do nothing and return.
        if (element instanceof WebComponent) {
            return (WebComponent) element;
        }
        WebElement decoratedElement = decorator.decorate(element, driver);
        return new DefaultWebComponent(decoratedElement, this, this.decorator);
    }


}
