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

package com.github.grossopa.selenium.core.component.api;

import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * The delayed select that all the actions should wait for some time after being triggered.
 *
 * @author Jack Yin
 * @since 1.2
 */
public interface DelayedSelect {

    /**
     * Returns all options belonging to this select tag
     *
     * @param delayInMillis the delays in milliseconds
     * @return All options belonging to this select tag
     */
    List<WebComponent> getOptions2(Long delayInMillis);

    /**
     * Returns all selected options belonging to this select tag
     *
     * @param delayInMillis the delays in milliseconds
     * @return All selected options belonging to this select tag
     */
    List<WebComponent> getAllSelectedOptions2(Long delayInMillis);

    /**
     * Opens the options list with delays
     *
     * @param delayInMillis the delays in milliseconds
     * @return the options container
     */
    WebComponent openOptions(Long delayInMillis);

    /**
     * Closes the options list with delays
     *
     * @param delayInMillis the delays in milliseconds
     */
    void closeOptions(Long delayInMillis);

    /**
     * Get first selected option with delays
     *
     * @param delayInMillis the delay in milliseconds
     * @return the selected option element
     */
    WebElement getFirstSelectedOption(Long delayInMillis);

    /**
     * Selects the option by visible text with delays
     *
     * @param text the text to be found
     * @param delayInMillis the delay in milliseconds
     */
    void selectByVisibleText(String text, Long delayInMillis);

    /**
     * Selects the option by option index with delays
     *
     * @param index the index for locating the element
     * @param delayInMillis the delays in milliseconds
     */
    void selectByIndex(int index, Long delayInMillis);

    /**
     * Selects the option by the value attribute with delays
     *
     * @param value the value of the element, note it might differ with the native html select for rich UI component
     * @param delayInMillis the delays in milliseconds
     */
    void selectByValue(String value, Long delayInMillis);

    /**
     * Deselects all options by clicking selected items and with delays
     *
     * @param delayInMillis the delays in milliseconds
     */
    void deselectAll(Long delayInMillis);

    /**
     * Deselects the option by value and with delays
     *
     * @param value the value of the element to be deselected, note it might differ with the native html select for
     * right UI component
     * @param delayInMillis the delays in milliseconds
     */
    void deselectByValue(String value, Long delayInMillis);

    /**
     * Deselects the option by the index and with delays
     *
     * @param index the index of element to be deselected
     * @param delayInMillis the delays in milliseconds
     */
    void deselectByIndex(int index, Long delayInMillis);

    /**
     * Deselects the option by visible text and with delays
     *
     * @param text the text of element ot be deselected
     * @param delayInMillis the delays in milliseconds
     */
    void deselectByVisibleText(String text, Long delayInMillis);
}
