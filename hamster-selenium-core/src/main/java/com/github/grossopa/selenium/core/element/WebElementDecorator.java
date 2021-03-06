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

package com.github.grossopa.selenium.core.element;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Decorates the generated {@link org.openqa.selenium.WebElement} generated from {@link
 * org.openqa.selenium.remote.RemoteWebDriver} and another {@link org.openqa.selenium.WebElement}. the decorator will
 * applied all elements that derived from the root driver.
 *
 * <p>
 * Please note if the returned new {@link org.openqa.selenium.WebElement} implements {@link
 * org.openqa.selenium.WrapsElement} it will be unwrapped in many cases hence suggesting not to implement it.
 * </p>
 *
 * @author Jack Yin
 * @since 1.4
 */
@FunctionalInterface
public interface WebElementDecorator {

    /**
     * Decorates the original element.
     *
     * @param originalElement the target element to decorate
     * @param driver the root driver
     * @return decorated web element
     */
    WebElement decorate(WebElement originalElement, WebDriver driver);
}
