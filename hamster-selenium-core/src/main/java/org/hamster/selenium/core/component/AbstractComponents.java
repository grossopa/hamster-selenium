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

import static java.util.Objects.requireNonNull;

/**
 * The default abstraction implemention of {@link Components} to require a valid {@link WebComponent} and {@link
 * ComponentWebDriver} as the necessary parameter to construct a customized component.
 *
 * <p>
 * Developer should inherit this class and provide their factory methods for building different customized components,
 * also in order to build the solution in a graceful way, developer could also define a static method to construct an
 * instance of itself. e.g.
 * <p></><code>
 * public HtmlFormField toFormField() { return this.component.to(new HtmlFormFieldFactory()); }
 * <br>
 * public HtmlTable toTable() { return this.component.to(new HtmlTableFactory()); }
 * <br>
 * public HtmlSelect toSelect() { return this.component.to(new HtmlSelectFactory()); }
 * <br>
 * public static HtmlComponents html() { return new HtmlComponents(); }
 * </code>
 * </p>
 * So the usage will be:
 * <p>
 * <code>HtmlTable table = driver.findComponent(By.id("customers")).as(html()).toTable();</code>
 * </p>
 *
 * @author Jack Yin
 * @since 1.0
 */
public abstract class AbstractComponents implements Components {

    protected WebComponent component;
    protected ComponentWebDriver driver;

    @Override
    public void setContext(WebComponent component, ComponentWebDriver driver) {
        this.component = requireNonNull(component);
        this.driver = requireNonNull(driver);
    }

    /**
     * Gets the target component to be converted
     *
     * @return the target component to be converted
     */
    public WebComponent getComponent() {
        return component;
    }

    /**
     * Gets the current root driver
     *
     * @return the current root driver
     */
    public ComponentWebDriver getDriver() {
        return driver;
    }
}
