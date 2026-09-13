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

import static com.github.grossopa.utils.consts.HtmlConstants.TEXTAREA;

/**
 * The HTML textarea component wrapping {@code <textarea>}.
 *
 * <p>
 * Provides semantic operations for textarea elements including getting/setting values, appending text,
 * clearing content, and querying attributes like rows, cols, placeholder, and max length.
 * </p>
 *
 * <p>
 * Note: Uses {@link Locator#inputValue()} to retrieve the current value (DOM property) rather than
 * {@link Locator#getAttribute(String)} which returns the static HTML attribute.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * HtmlTextArea textArea = driver.findComponentAs("#description", HtmlComponents.html(driver)::textArea);
 * textArea.setValue("Hello World");
 * textArea.appendText("\nSecond line");
 * String value = textArea.getValue();
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.15
 */
public class HtmlTextArea extends DefaultWebComponent {

    /**
     * Constructs an instance with the given locator and driver.
     *
     * @param locator the locator pointing to the textarea element
     * @param driver the component driver
     */
    public HtmlTextArea(Locator locator, ComponentDriver driver) {
        super(locator, driver);
    }

    @Override
    public String getComponentTagName() {
        return TEXTAREA;
    }

    /**
     * Validates that the current locator points to a {@code <textarea>} element.
     *
     * @return true if the element tag name is "textarea" (case-insensitive)
     */
    public boolean validate() {
        return TEXTAREA.equalsIgnoreCase(locator.evaluate("el => el.tagName").toString());
    }

    /**
     * Gets the current value of the textarea (the DOM property, not the HTML attribute).
     *
     * @return the current textarea value
     */
    public String getValue() {
        return locator.inputValue();
    }

    /**
     * Sets the value of the textarea, replacing any existing content.
     *
     * @param value the value to set
     */
    public void setValue(String value) {
        locator.fill(value);
    }

    /**
     * Appends text to the end of the current textarea value.
     *
     * @param text the text to append
     */
    public void appendText(String text) {
        locator.pressSequentially(text);
    }

    /**
     * Clears the textarea content.
     */
    @Override
    public void clear() {
        locator.clear();
    }

    /**
     * Gets the placeholder text of the textarea.
     *
     * @return the placeholder attribute value, or null if not set
     */
    public String getPlaceholder() {
        return locator.getAttribute("placeholder");
    }

    /**
     * Gets the number of visible text rows of the textarea.
     *
     * @return the rows attribute value as integer, or -1 if not set
     */
    public int getRows() {
        String rows = locator.getAttribute("rows");
        return rows != null ? Integer.parseInt(rows) : -1;
    }

    /**
     * Gets the number of visible text columns of the textarea.
     *
     * @return the cols attribute value as integer, or -1 if not set
     */
    public int getCols() {
        String cols = locator.getAttribute("cols");
        return cols != null ? Integer.parseInt(cols) : -1;
    }

    /**
     * Gets the maximum number of characters allowed in the textarea.
     *
     * @return the maxlength attribute value as integer, or -1 if not set
     */
    public int getMaxLength() {
        String maxLength = locator.getAttribute("maxlength");
        return maxLength != null ? Integer.parseInt(maxLength) : -1;
    }

    /**
     * Checks whether this textarea is read-only.
     *
     * @return true if the textarea has the readonly attribute
     */
    public boolean isReadOnly() {
        return locator.getAttribute("readonly") != null;
    }

    /**
     * Checks whether this textarea is required.
     *
     * @return true if the textarea has the required attribute
     */
    public boolean isRequired() {
        return locator.getAttribute("required") != null;
    }
}
