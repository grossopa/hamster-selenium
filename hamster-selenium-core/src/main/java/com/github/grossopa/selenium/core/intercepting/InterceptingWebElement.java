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

package com.github.grossopa.selenium.core.intercepting;

import com.github.grossopa.selenium.core.component.AbstractDelegatedWebElement;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;

import java.util.List;
import java.util.Objects;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static java.util.Objects.requireNonNull;

/**
 * Intercepting the actions with customized handlers.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class InterceptingWebElement extends AbstractDelegatedWebElement {

    private final InterceptingHandler handler;

    /**
     * Constructs an instance with target delegated {@link WebElement} instance.
     *
     * @param element the element to delegate. it will find the ultimate {@link WebElement} element if given object is
     * actually a {@link WebComponent}. Note it will not try to find the wrapped element if the given element is a
     * {@link WrapsElement}.
     * @param handler the handler for before, after and on exception actions.
     */
    public InterceptingWebElement(WebElement element, InterceptingHandler handler) {
        super(element);
        requireNonNull(handler);
        this.handler = handler;
    }

    @Override
    public void click() {
        handler.execute(() -> {
            super.click();
            return null;
        }, MethodInfo.create(element, ELEMENT_CLICK));
    }

    @Override
    public void submit() {
        handler.execute(() -> {
            super.submit();
            return null;
        }, MethodInfo.create(element, ELEMENT_SUBMIT));
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        handler.execute(() -> {
            super.sendKeys(keysToSend);
            return null;
        }, MethodInfo.create(element, ELEMENT_SEND_KEYS, (Object) keysToSend));
    }

    @Override
    public void clear() {
        handler.execute(() -> {
            super.clear();
            return null;
        }, MethodInfo.create(element, ELEMENT_CLEAR));
    }

    @Override
    public String getTagName() {
        return handler.execute(super::getTagName, MethodInfo.create(element, ELEMENT_GET_TAG_NAME));
    }

    @Override
    public String getAttribute(String name) {
        return handler.execute(() -> super.getAttribute(name), MethodInfo.create(element, ELEMENT_GET_ATTRIBUTE, name));
    }

    @Override
    public boolean isSelected() {
        return handler.execute(super::isSelected, MethodInfo.create(element, ELEMENT_IS_SELECTED));
    }

    @Override
    public boolean isEnabled() {
        return handler.execute(super::isEnabled, MethodInfo.create(element, ELEMENT_IS_SELECTED));
    }

    @Override
    public String getText() {
        return handler.execute(super::getText, MethodInfo.create(element, ELEMENT_GET_TEXT));
    }

    @Override
    public List<WebElement> findElements(By by) {
        return handler.execute(() -> super.findElements(by), MethodInfo.create(element, ELEMENT_FIND_ELEMENTS, by));
    }

    @Override
    public WebElement findElement(By by) {
        return handler.execute(() -> super.findElement(by), MethodInfo.create(element, ELEMENT_FIND_ELEMENT, by));
    }

    @Override
    public boolean isDisplayed() {
        return handler.execute(super::isDisplayed, MethodInfo.create(element, ELEMENT_IS_DISPLAYED));
    }

    @Override
    public Point getLocation() {
        return handler.execute(super::getLocation, MethodInfo.create(element, ELEMENT_GET_LOCATION));
    }

    @Override
    public Dimension getSize() {
        return handler.execute(super::getSize, MethodInfo.create(element, ELEMENT_GET_SIZE));
    }

    @Override
    public Rectangle getRect() {
        return handler.execute(super::getRect, MethodInfo.create(element, ELEMENT_GET_RECT));
    }

    @Override
    public String getCssValue(String propertyName) {
        return handler.execute(() -> super.getCssValue(propertyName),
                MethodInfo.create(element, ELEMENT_GET_CSS_VALUE, propertyName));
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) {
        return handler.execute(() -> super.getScreenshotAs(target),
                MethodInfo.create(element, ELEMENT_GET_SCREENSHOT_AS, target));
    }

    @Override
    public Coordinates getCoordinates() {
        return handler.execute(super::getCoordinates, MethodInfo.create(element, ELEMENT_GET_COORDINATES));
    }

    @Override
    public String getId() {
        return handler.execute(super::getId, MethodInfo.create(element, ELEMENT_GET_ID));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterceptingWebElement)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        InterceptingWebElement that = (InterceptingWebElement) o;
        return handler.equals(that.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), handler);
    }

    @Override
    public String toString() {
        return "InterceptingWebElement{" + "element=" + element + ", handler=" + handler + '}';
    }
}
