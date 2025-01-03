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

package com.github.grossopa.selenium.core.component;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.factory.WebComponentFactory;
import com.github.grossopa.selenium.core.component.util.WebComponentUtils;
import com.github.grossopa.selenium.core.element.NoOpWebElementDecorator;
import com.github.grossopa.selenium.core.element.TextNodeElement;
import com.github.grossopa.selenium.core.element.WebElementDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static com.github.grossopa.selenium.core.util.SeleniumUtils.findChildTextNodes;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The default implementation of {@link WebComponent}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class DefaultWebComponent extends AbstractDelegatedWebElement implements WebComponent {

    protected final ComponentWebDriver driver;
    protected final WebElementDecorator decorator;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver root driver
     */
    public DefaultWebComponent(WebElement element, ComponentWebDriver driver) {
        this(element, driver, null);
    }

    /**
     * Constructs an instance with the delegated element, root driver and
     *
     * @param element the delegated element
     * @param driver root driver
     * @param decorator the decorator for decorating the found sub {@link WebElement} instances
     */
    public DefaultWebComponent(WebElement element, ComponentWebDriver driver, @Nullable WebElementDecorator decorator) {
        super(element);
        this.driver = driver;
        this.decorator = defaultIfNull(decorator, new NoOpWebElementDecorator());
    }

    @Override
    public ComponentWebDriver driver() {
        return driver;
    }

    @Override
    public List<WebComponent> findComponents(By by) {
        return element.findElements(by).stream().map(e -> {
            WebElement decoratedElement = decorator.decorate(e, driver);
            return new DefaultWebComponent(decoratedElement, driver);
        }).collect(toList());
    }

    @Override
    public WebComponent findComponent(By by) {
        WebElement result = element.findElement(by);
        return new DefaultWebComponent(decorator.decorate(result, driver), driver);
    }

    @Override
    public <T extends WebComponent> List<T> findComponentsAs(By by, Function<WebComponent, T> mappingFunction) {
        return findComponents(by).stream().map(mappingFunction).collect(toList());
    }

    @Override
    public <T extends WebComponent> T findComponentAs(By by, Function<WebComponent, T> mappingFunction) {
        return mappingFunction.apply(findComponent(by));
    }

    @Override
    public <T extends WebComponent> T to(WebComponentFactory<T> factory) {
        return factory.apply(element, driver);
    }

    @Override
    public boolean attributeContains(String attributeName, String attributeValue) {
        return WebComponentUtils.attributeContains(element, attributeName, attributeValue);
    }

    @Override
    public boolean styleContains(String styleName, String styleValue) {
        return WebComponentUtils.styleContains(element, styleName, styleValue);
    }

    @Override
    public boolean isFocused() {
        return element.equals(driver.switchTo().activeElement());
    }

    @Override
    public String outerHTML() {
        return element.getDomAttribute("outerHTML");
    }

    @Override
    public boolean validate() {
        // always return true as this simply wraps the inner element
        return true;
    }

    @Override
    public String getId() {
        return ((RemoteWebElement) element).getId();
    }

    /**
     * Gets the text and comment nodes.
     *
     * @return the text and comment nodes.
     */
    @Override
    public List<TextNodeElement> findTextNodes() {
        return findChildTextNodes(driver, element, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultWebComponent)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DefaultWebComponent that = (DefaultWebComponent) o;
        return driver.equals(that.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), driver);
    }

    @Override
    public <T extends Components> T as(T components) {
        components.setContext(this, driver);
        return components;
    }

    @Override
    public String toString() {
        return "DefaultWebComponent{element=" + element + '}';
    }
}
