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

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Provides the composition of multiple decorators into one decorator. the decorator will be executed in order.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class CompositeWebElementDecorator implements WebElementDecorator {

    private final List<WebElementDecorator> decoratorList;

    /**
     * Constructs an instance with the given inner decorators.
     *
     * @param decoratorList the decorator list to be wrapped
     */
    public CompositeWebElementDecorator(List<WebElementDecorator> decoratorList) {
        this.decoratorList = decoratorList;
    }


    @Override
    public WebElement decorate(WebElement originalElement, WebDriver driver) {
        WebElement result = originalElement;
        for (WebElementDecorator decorator : decoratorList) {
            result = decorator.decorate(result, driver);
        }
        return result;
    }

    /**
     * Gets a copy list of decorator list.
     *
     * @return the copy list of decorator list.
     */
    public List<WebElementDecorator> getDecoratorList() {
        return newArrayList(decoratorList);
    }
}
