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

package com.github.grossopa.playwright.component.html;

import com.github.grossopa.playwright.core.AbstractComponents;
import com.github.grossopa.playwright.core.ComponentDriver;

/**
 * The HTML components factory collection.
 *
 * @author Jack Yin
 * @since 1.12
 */
public class HtmlComponents extends AbstractComponents {

    private final ComponentDriver driver;

    /**
     * Creates an instance with the given driver
     *
     * @param driver the component driver instance
     */
    public HtmlComponents(ComponentDriver driver) {
        this.driver = driver;
    }

    /**
     * Gets the form field factory
     *
     * @return the form field factory
     */
    public HtmlFormField formField() {
        return new HtmlFormField(component, driver);
    }

    /**
     * Gets the select factory
     *
     * @return the select factory
     */
    public HtmlSelect select() {
        return new HtmlSelect(component, driver);
    }

    /**
     * Gets the table factory
     *
     * @return the table factory
     */
    public HtmlTable table() {
        return new HtmlTable(component, driver);
    }
}