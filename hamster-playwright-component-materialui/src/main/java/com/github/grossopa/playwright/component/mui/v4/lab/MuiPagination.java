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

package com.github.grossopa.playwright.component.mui.v4.lab;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.*;
import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * Pagination component allows users to select a specific page from a range of pages.
 *
 * <p>Pagination is used to split large sets of content into smaller chunks across multiple pages.
 * It supports various display variants and navigation controls.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/pagination/">
 * https://material-ui.com/components/pagination/</a>
 * @since 1.12
 */
public class MuiPagination extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Pagination";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiPagination(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<com.github.grossopa.playwright.component.mui.MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Gets all page buttons.
     *
     * @return list of page button WebComponents
     */
    public List<WebComponent> getPages() {
        return findComponents("[role=\"button\"]:not([aria-label])");
    }

    /**
     * Gets the total number of pages.
     *
     * @return the page count
     */
    public int getPageCount() {
        return getPages().size();
    }

    /**
     * Gets the currently selected page number.
     *
     * @return the current page number (1-based)
     */
    public int getCurrentPage() {
        List<WebComponent> pages = getPages();
        for (int i = 0; i < pages.size(); i++) {
            String ariaCurrent = pages.get(i).getAttribute("aria-current");
            if ("true".equals(ariaCurrent)) {
                return i + 1;
            }
        }
        return 1; // default
    }

    /**
     * Navigates to a specific page.
     *
     * @param pageNumber the page number to navigate to (1-based)
     * @throws IndexOutOfBoundsException if page number is out of range
     */
    public void goToPage(int pageNumber) {
        List<WebComponent> pages = getPages();
        if (pageNumber < 1 || pageNumber > pages.size()) {
            throw new IndexOutOfBoundsException(
                    "Page number " + pageNumber + " is out of bounds. Available pages: " + pages.size());
        }
        pages.get(pageNumber - 1).click();
    }

    /**
     * Clicks the next page button.
     */
    public void nextPage() {
        WebComponent nextButton = findComponent("[aria-label=\"Go to next page\"]");
        if (nextButton != null) {
            nextButton.click();
        }
    }

    /**
     * Clicks the previous page button.
     */
    public void previousPage() {
        WebComponent prevButton = findComponent("[aria-label=\"Go to previous page\"]");
        if (prevButton != null) {
            prevButton.click();
        }
    }

    /**
     * Clicks the first page button.
     */
    public void firstPage() {
        WebComponent firstButton = findComponent("[aria-label=\"Go to first page\"]");
        if (firstButton != null) {
            firstButton.click();
        }
    }

    /**
     * Clicks the last page button.
     */
    public void lastPage() {
        WebComponent lastButton = findComponent("[aria-label=\"Go to last page\"]");
        if (lastButton != null) {
            lastButton.click();
        }
    }

    /**
     * Checks if the pagination uses circular navigation.
     *
     * @return true if circular, false otherwise
     */
    public boolean isCircular() {
        String className = getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "Pagination-circular");
    }

    /**
     * Gets the pagination variant.
     *
     * @return the variant ("text", "outlined", or "default")
     */
    public String getVariant() {
        String className = getAttribute(CLASS);
        if (className != null) {
            if (className.contains(config.getCssPrefix() + "Pagination-outlined")) return "outlined";
            if (className.contains(config.getCssPrefix() + "Pagination-text")) return "text";
        }
        return "default";
    }

    /**
     * Gets the pagination size.
     *
     * @return the size ("small", "medium", or "large")
     */
    public String getSize() {
        String className = getAttribute(CLASS);
        if (className != null) {
            if (className.contains(config.getCssPrefix() + "Pagination-sizeSmall")) return "small";
            if (className.contains(config.getCssPrefix() + "Pagination-sizeLarge")) return "large";
        }
        return "medium"; // default
    }

    /**
     * Checks if there is a next page available.
     *
     * @return true if next page exists, false if on last page
     */
    public boolean hasNextPage() {
        WebComponent nextButton = findComponent("[aria-label=\"Go to next page\"]");
        return nextButton != null && !"true".equals(nextButton.getAttribute("disabled"));
    }

    /**
     * Checks if there is a previous page available.
     *
     * @return true if previous page exists, false if on first page
     */
    public boolean hasPreviousPage() {
        WebComponent prevButton = findComponent("[aria-label=\"Go to previous page\"]");
        return prevButton != null && !"true".equals(prevButton.getAttribute("disabled"));
    }
}
