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

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.internal.HasIdentity;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A simple delegation of {@link WebElement}
 *
 * @author Jack Yin
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public abstract class AbstractDelegatedWebElement
        implements WrapsElement, WebElement, HasIdentity, TakesScreenshot, Locatable, WrapsDriver {

    protected WebElement element;

    /**
     * Constructs an instance with target delegated {@link WebElement} instance.
     *
     * @param element
     *         the element to delegate. it will find the ultimate {@link WebElement} element if given object is actually
     *         a {@link WebComponent}.
     */
    protected AbstractDelegatedWebElement(WebElement element) {
        requireNonNull(element);
        WebElement targetElement = element;

        while (targetElement instanceof WebComponent) {
            targetElement = ((WebComponent) targetElement).getWrappedElement();
        }

        this.element = targetElement;
    }

    @Override
    public void click() {
        element.click();
    }

    @Override
    public void submit() {
        element.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        element.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        element.clear();
    }

    @Override
    public String getTagName() {
        return element.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return element.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return element.isEnabled();
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return element.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return element.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return element.getLocation();
    }

    @Override
    public Dimension getSize() {
        return element.getSize();
    }

    @Override
    public Rectangle getRect() {
        return element.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return element.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) {
        return element.getScreenshotAs(target);
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) element).getCoordinates();
    }

    @Override
    public String getId() {
        return ((HasIdentity) element).getId();
    }

    @Override
    public WebDriver getWrappedDriver() {
        return ((WrapsDriver) element).getWrappedDriver();
    }

    @Override
    public String toString() {
        return element.toString();
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }
}
