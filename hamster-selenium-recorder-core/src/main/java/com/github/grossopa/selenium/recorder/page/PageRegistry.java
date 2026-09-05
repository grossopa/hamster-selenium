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
package com.github.grossopa.selenium.recorder.page;

import com.github.grossopa.selenium.recorder.model.PageModel;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * The registry holding all collected {@link PageModel} instances and the current page. It applies the configured
 * {@link PageIdentificationStrategy} to classify urls into pages and allows the user to explicitly open a new page or
 * switch to an existing page, overriding the strategy decision.
 *
 * @author Jack Yin
 * @since 1.15
 * @see PageIdentificationStrategy
 * @see PageModel
 */
public class PageRegistry {

    private final List<PageModel> pages = new ArrayList<>();
    private PageIdentificationStrategy strategy;
    private PageModel currentPage;

    /**
     * Constructs an instance with the page identification strategy.
     *
     * @param strategy the strategy for identifying which page a url belongs to, must not be null
     */
    public PageRegistry(PageIdentificationStrategy strategy) {
        this.strategy = requireNonNull(strategy);
    }

    /**
     * Classifies the given url into an existing page or a new page using the configured strategy and sets it as the
     * current page. If the strategy suggests a new page, the page is created automatically.
     *
     * @param url the url to classify
     * @return the page that the url is classified into
     */
    public PageModel classify(String url) {
        PageIdentification identification = strategy.identify(url, pages);
        if (identification.isMatched()) {
            currentPage = identification.getMatchedPage();
        } else {
            PageModel newPage = new PageModel(identification.getSuggestedName(), identification.getSuggestedKey());
            pages.add(newPage);
            currentPage = newPage;
        }
        return currentPage;
    }

    /**
     * Creates a new page with the given name and the given page key and sets it as the current page, overriding the
     * strategy decision.
     *
     * @param name the display name of the new page
     * @param pageKey the key of the new page, e.g. the current context path
     * @return the created page
     */
    public PageModel newPage(String name, String pageKey) {
        PageModel page = new PageModel(name, pageKey);
        pages.add(page);
        currentPage = page;
        return page;
    }

    /**
     * Switches the current page to the existing page with the given name, overriding the strategy decision.
     *
     * @param name the name of the existing page to use
     * @return the matched page
     * @throws IllegalArgumentException if no page with the given name exists
     */
    public PageModel usePage(String name) {
        PageModel page = findPage(name)
                .orElseThrow(() -> new IllegalArgumentException("No page found with name: " + name));
        currentPage = page;
        return page;
    }

    /**
     * Finds a page by its display name.
     *
     * @param name the page name to search
     * @return the matched page, empty if not found
     */
    public Optional<PageModel> findPage(String name) {
        return pages.stream().filter(page -> page.getName().equals(name)).findFirst();
    }

    /**
     * Gets the current page, null if no page is classified yet.
     *
     * @return the current page, null if not classified yet
     */
    @Nullable
    public PageModel getCurrentPage() {
        return currentPage;
    }

    /**
     * Gets all collected pages.
     *
     * @return all collected pages
     */
    public List<PageModel> getPages() {
        return pages;
    }

    /**
     * Gets the page identification strategy.
     *
     * @return the page identification strategy
     */
    public PageIdentificationStrategy getStrategy() {
        return strategy;
    }

    /**
     * Sets the page identification strategy, allowing the user to replace it at runtime.
     *
     * @param strategy the new strategy to set
     */
    public void setStrategy(PageIdentificationStrategy strategy) {
        this.strategy = requireNonNull(strategy);
    }
}
