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

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * The pagination component should have a list of buttons for user to navigate between different pages. Note not all
 * page buttons are shown.
 *
 * <br>
 * Other optional buttons:
 * <ul>
 *     <li>Previous / Next buttons: navigate to previous / next of current page</li>
 *     <li>First / Last buttons: navigate to first / last page</li>
 * </ul>
 *
 * @param <B> the middle button type
 * @param <P> the previous / next button type
 * @param <F> the first / last button type
 * @author Jack Yin
 * @since 1.3
 */
@SuppressWarnings("unused")
public interface Pagination<B extends WebElement, P extends WebElement, F extends WebElement> {

    /**
     * Gets all page buttons
     *
     * @return all page buttons
     */
    List<B> pageButtons();

    /**
     * Gets the previous button for navigating to current - 1 page.
     *
     * @return the previous button for navigating to current - 1 page.
     * @throws NoSuchElementException if button not found
     */
    P previousButton();

    /**
     * Gets the next button for navigating to current + 1 page.
     *
     * @return the previous button for navigating to current + 1 page.
     * @throws NoSuchElementException if button not found
     */
    P nextButton();

    /**
     * Gets the first button for navigating to first usually index 1 page.
     *
     * @return the first button for navigating to first usually index 1 page.
     * @throws NoSuchElementException if button not found
     */
    F firstButton();

    /**
     * Gets the last button for navigating to first usually index 1 page.
     *
     * @return the last button for navigating to first usually index 1 page.
     * @throws NoSuchElementException if button not found
     */
    F lastButton();

    /**
     * Selects the page by index (1-based)
     *
     * @param index the index to be selected
     */
    void setPageIndex(int index);

    /**
     * Gets current selected page index, -1 if nothing selected
     *
     * @return current selected page index, -1 if nothing selected
     */
    int getCurrentPageIndex();
}
