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

package com.github.grossopa.selenium.component.html;

import com.github.grossopa.selenium.component.html.factory.HtmlFormFieldFactory;
import com.github.grossopa.selenium.component.html.factory.HtmlSelectFactory;
import com.github.grossopa.selenium.component.html.factory.HtmlTableFactory;
import com.github.grossopa.selenium.core.component.AbstractComponents;

/**
 * Contains all HTML components.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class HtmlComponents extends AbstractComponents {

    /**
     * Creates an HTML Form field component from the given {@link org.openqa.selenium.WebElement}.
     *
     * <p>
     * The given element could be any container type but must contain one element with label "&lt;label&gt;" and input
     * element "&lt;input&gt;".
     * </p>
     *
     * @return the created instance of {@link HtmlFormField}
     * @see HtmlFormField
     */
    public HtmlFormField toFormField() {
        return this.component.to(new HtmlFormFieldFactory());
    }

    /**
     * Creates an HTML Table component from the given {@link org.openqa.selenium.WebElement}.
     *
     * <p>
     * The given element must be "&lt;table&gt;" and with "&lt;tr&gt;&lt;th&gt;" and/or "&lt;tr&gt;&lt;td&gt;"
     * </p>
     *
     * @return the created instance of {@link HtmlTable}
     * @see HtmlTable
     */
    public HtmlTable toTable() {
        return this.component.to(new HtmlTableFactory());
    }

    /**
     * Creates an HTML Select component from the given {@link org.openqa.selenium.WebElement}.
     *
     * <p>
     * The given element must be "&lt;select&gt;"
     * </p>
     *
     * @return the created instance of {@link HtmlSelect}
     * @see HtmlSelect
     */
    public HtmlSelect toSelect() {
        return this.component.to(new HtmlSelectFactory());
    }

    /**
     * Returns new instance of this class.
     *
     * <p>
     * Example: {@code HtmlSelect select = driver.findComponent(By.id("cars")).as(html()).toSelect();}
     * </p>
     *
     * @return the instance of {@link HtmlComponents}
     */
    public static HtmlComponents html() {
        return new HtmlComponents();
    }
}
