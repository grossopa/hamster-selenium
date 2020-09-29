/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.core.component;

import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.factory.WebComponentFactory;
import org.hamster.selenium.core.component.util.WebComponentUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The default implementation of {@link WebComponent}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class DefaultWebComponent extends AbstractDelegatedWebElement implements WebComponent {

    protected final ComponentWebDriver driver;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element
     *         the delegated element
     * @param driver
     *         root driver
     */
    public DefaultWebComponent(WebElement element, ComponentWebDriver driver) {
        super(element);
        this.driver = driver;
    }

    @Override
    public WebElement getElement() {
        return this.element;
    }

    @Override
    public List<WebComponent> findComponents(By by) {
        return element.findElements(by).stream().map(e -> new DefaultWebComponent(e, driver)).collect(toList());
    }

    @Override
    public WebComponent findComponent(By by) {
        return new DefaultWebComponent(element.findElement(by), driver);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends WebComponent> T to(WebComponentFactory<T> factory) {
        return (T) factory.apply(element, driver);
    }

    @Override
    public boolean attributeContains(String attributeName, String attributeValue) {
        return WebComponentUtils.attributeContains(this, attributeName, attributeValue);
    }

    @Override
    public <T extends Components> T as(T components) {
        components.setContext(this, driver);
        return components;
    }

}