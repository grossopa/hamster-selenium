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

package com.github.grossopa.selenium.component.mui.v4.lab;

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.core.locator.By2;

import java.util.List;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * The locators for locating the previous / next / first / last buttons and page number of each page button.
 *
 * @author Jack Yin
 * @since 1.3
 */
public class MuiPaginationLocators {

    /**
     * THE aria label attribute
     */
    public static final String ARIA_LABEL = "aria-label";

    /**
     * The default previous button finder function
     */
    public static final Function<MuiPagination, MuiButton> DEFAULT_PREVIOUS_BUTTON = pagination -> pagination
            .findComponentAs(By2.attrExact(ARIA_LABEL, "Go to previous page"),
                    c -> new MuiButton(c, pagination.driver(), pagination.config()));

    /**
     * The default next button finder function
     */
    public static final Function<MuiPagination, MuiButton> DEFAULT_NEXT_BUTTON = pagination -> pagination
            .findComponentAs(By2.attrExact(ARIA_LABEL, "Go to next page"),
                    c -> new MuiButton(c, pagination.driver(), pagination.config()));

    /**
     * The default first button finder function
     */
    public static final Function<MuiPagination, MuiButton> DEFAULT_FIRST_BUTTON = pagination -> pagination
            .findComponentAs(By2.attrExact(ARIA_LABEL, "Go to first page"),
                    c -> new MuiButton(c, pagination.driver(), pagination.config()));

    /**
     * The default last button finder function
     */
    public static final Function<MuiPagination, MuiButton> DEFAULT_LAST_BUTTON = pagination -> pagination
            .findComponentAs(By2.attrExact(ARIA_LABEL, "Go to last page"),
                    c -> new MuiButton(c, pagination.driver(), pagination.config()));

    /**
     * The default page buttons finder function
     */
    public static final Function<MuiPagination, List<MuiButton>> DEFAULT_PAGE_BUTTONS = pagination -> pagination
            .findComponentsAs(By2.attrContains(ARIA_LABEL, "Go to page"),
                    c -> new MuiButton(c, pagination.driver(), pagination.config()));

    /**
     * The default page index finder function
     */
    public static final Function<MuiButton, Integer> DEFAULT_PAGE_INDEX = button -> Integer.valueOf(button.getText());

    private final Function<MuiPagination, MuiButton> previousButtonFinder;
    private final Function<MuiPagination, MuiButton> nextButtonFinder;
    private final Function<MuiPagination, MuiButton> firstButtonFinder;
    private final Function<MuiPagination, MuiButton> lastButtonFinder;
    private final Function<MuiPagination, List<MuiButton>> pageButtonsFinder;
    private final Function<MuiButton, Integer> pageIndexFinder;

    /**
     * Constructs an instance with all finders
     *
     * @param previousButtonFinder the finder for locating and finding the previous button
     * @param nextButtonFinder the finder for locating and finding the next button
     * @param firstButtonFinder the finder for locating and finding the first button
     * @param lastButtonFinder the finder for locating and finding the last button
     * @param pageButtonsFinder the finder for locating and find the page buttons
     * @param pageIndexFinder the finder for finding the index number from page button
     */
    public MuiPaginationLocators(Function<MuiPagination, MuiButton> previousButtonFinder,
            Function<MuiPagination, MuiButton> nextButtonFinder, Function<MuiPagination, MuiButton> firstButtonFinder,
            Function<MuiPagination, MuiButton> lastButtonFinder,
            Function<MuiPagination, List<MuiButton>> pageButtonsFinder, Function<MuiButton, Integer> pageIndexFinder) {
        requireNonNull(previousButtonFinder);
        requireNonNull(nextButtonFinder);
        requireNonNull(firstButtonFinder);
        requireNonNull(lastButtonFinder);
        requireNonNull(pageButtonsFinder);
        requireNonNull(pageIndexFinder);

        this.previousButtonFinder = previousButtonFinder;
        this.nextButtonFinder = nextButtonFinder;
        this.firstButtonFinder = firstButtonFinder;
        this.lastButtonFinder = lastButtonFinder;
        this.pageButtonsFinder = pageButtonsFinder;
        this.pageIndexFinder = pageIndexFinder;
    }

    /**
     * Gets finder for locating and finding the previous button
     *
     * @return finder for locating and finding the previous button
     */
    public Function<MuiPagination, MuiButton> getPreviousButtonFinder() {
        return previousButtonFinder;
    }

    /**
     * Gets the finder for locating and finding the next button
     *
     * @return the finder for locating and finding the next button
     */
    public Function<MuiPagination, MuiButton> getNextButtonFinder() {
        return nextButtonFinder;
    }

    /**
     * Gets the finder for locating and finding the first button
     *
     * @return the finder for locating and finding the first button
     */
    public Function<MuiPagination, MuiButton> getFirstButtonFinder() {
        return firstButtonFinder;
    }

    /**
     * Gets the finder for locating and finding the last button
     *
     * @return the finder for locating and finding the last button
     */
    public Function<MuiPagination, MuiButton> getLastButtonFinder() {
        return lastButtonFinder;
    }

    /**
     * Gets the finder for locating and finding the page buttons
     *
     * @return the finder for locating and finding the page buttons
     */
    public Function<MuiPagination, List<MuiButton>> getPageButtonsFinder() {
        return pageButtonsFinder;
    }

    /**
     * the finder for finding the index number from page button
     *
     * @return the finder for finding the index number from page button
     */
    public Function<MuiButton, Integer> getPageIndexFinder() {
        return pageIndexFinder;
    }

    /**
     * Creates a default instance with Mui locators.
     *
     * @return the created locators
     */
    public static MuiPaginationLocators createDefault() {
        return new MuiPaginationLocators(DEFAULT_PREVIOUS_BUTTON, DEFAULT_NEXT_BUTTON, DEFAULT_FIRST_BUTTON,
                DEFAULT_LAST_BUTTON, DEFAULT_PAGE_BUTTONS, DEFAULT_PAGE_INDEX);
    }
}
