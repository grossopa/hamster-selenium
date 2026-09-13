/*
 * Copyright © 2021 the original author or authors.
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

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.DefaultWebComponent;
import com.microsoft.playwright.Locator;

/**
 * The HTML checkbox component wrapping {@code <input type="checkbox">}.
 *
 * <p>
 * Provides semantic operations for checkbox elements including check, uncheck, toggle and status query.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * HtmlCheckbox checkbox = driver.findComponentAs("#agree", HtmlComponents.html(driver)::checkbox);
 * checkbox.check();
 * assertTrue(checkbox.isChecked());
 * checkbox.toggle();
 * assertFalse(checkbox.isChecked());
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.15
 */
public class HtmlCheckbox extends DefaultWebComponent {

    /**
     * Constructs an instance with the given locator and driver.
     *
     * @param locator the locator pointing to the checkbox element
     * @param driver the component driver
     */
    public HtmlCheckbox(Locator locator, ComponentDriver driver) {
        super(locator, driver);
    }

    @Override
    public String getComponentTagName() {
        return "input";
    }

    /**
     * Validates that the current locator points to a {@code <input type="checkbox">} element.
     *
     * @return true if the element is an input with type "checkbox"
     */
    public boolean validate() {
        return "input".equalsIgnoreCase(locator.evaluate("el => el.tagName").toString())
                && "checkbox".equalsIgnoreCase(locator.evaluate("el => el.type").toString());
    }

    /**
     * Checks whether this checkbox is currently checked.
     *
     * @return true if the checkbox is checked
     */
    @Override
    public boolean isChecked() {
        return locator.isChecked();
    }

    /**
     * Checks this checkbox. Does nothing if already checked.
     */
    @Override
    public void check() {
        locator.check();
    }

    /**
     * Unchecks this checkbox. Does nothing if already unchecked.
     */
    @Override
    public void uncheck() {
        locator.uncheck();
    }

    /**
     * Toggles the checked state of this checkbox.
     */
    public void toggle() {
        if (isChecked()) {
            uncheck();
        } else {
            check();
        }
    }
}
