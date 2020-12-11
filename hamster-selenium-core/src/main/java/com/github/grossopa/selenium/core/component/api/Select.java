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

package com.github.grossopa.selenium.core.component.api;

import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ISelect;

import java.util.List;

/**
 * The select provides {@link WebComponent} support on top of {@link
 * org.openqa.selenium.support.ui.ISelect}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface Select extends ISelect {

    /**
     * Returns all options belonging to this select tag
     *
     * @return All options belonging to this select tag
     */
    List<WebComponent> getOptions2();

    /**
     * Returns all options belonging to this select tag
     *
     * @param delayInMillis
     *         the delays in milliseconds
     * @return All options belonging to this select tag
     */
    List<WebComponent> getOptions2(Long delayInMillis);

    /**
     * Returns all selected options belonging to this select tag
     *
     * @return All selected options belonging to this select tag
     */
    List<WebComponent> getAllSelectedOptions2();

    /**
     * Returns all selected options belonging to this select tag
     *
     * @param delayInMillis
     *         the delays in milliseconds
     * @return All selected options belonging to this select tag
     */
    List<WebComponent> getAllSelectedOptions2(Long delayInMillis);

    /**
     * Opens the options list
     *
     * @return the options container
     */
    WebComponent openOptions();

    /**
     * Opens the options list with delays
     *
     * @param delayInMillis
     *         the delays in milliseconds
     * @return the options container
     */
    WebComponent openOptions(Long delayInMillis);

    /**
     * Closes the options list
     */
    void closeOptions();

    /**
     * Closes the options list with delays
     *
     * @param delayInMillis
     *         the delays in milliseconds
     */
    void closeOptions(Long delayInMillis);

    /**
     * deprecated
     *
     * @return All options belonging to this select tag
     * @deprecated favor {@link #getOptions2()}
     */
    @Deprecated(since = "1.0")
    List<WebElement> getOptions();

    /**
     * deprecated
     *
     * @return All selected options belonging to this select tag
     * @deprecated favor {@link #getAllSelectedOptions2()}
     */
    @Deprecated(since = "1.0")
    List<WebElement> getAllSelectedOptions();

    WebElement getFirstSelectedOption(Long delayInMillis);

    void selectByVisibleText(String text, Long delayInMillis);

    void selectByIndex(int index, Long delayInMillis);

    void selectByValue(String value, Long delayInMillis);

    void deselectAll(Long delayInMillis);

    void deselectByValue(String value, Long delayInMillis);

    void deselectByIndex(int index, Long delayInMillis);

    void deselectByVisibleText(String text, Long delayInMillis);
}
