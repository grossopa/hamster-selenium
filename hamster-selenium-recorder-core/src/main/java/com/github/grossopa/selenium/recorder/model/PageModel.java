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
package com.github.grossopa.selenium.recorder.model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The model of a page collecting the elements selected by the user. All user interactions and DOM changes that stay
 * within the same page (identified by the page identification strategy, e.g. the same context path) are accumulated
 * into one page model and finally generated as one page object class.
 *
 * @author Jack Yin
 * @since 1.15
 * @see PageElementModel
 */
public class PageModel {

    private String name;
    private final String pageKey;
    private final List<PageElementModel> elements = new ArrayList<>();

    /**
     * Constructs an instance with the page name and page key.
     *
     * @param name the display name of the page, also the base of the generated class name, must not be null
     * @param pageKey the key identifying this page, e.g. the normalized context path, must not be null
     */
    public PageModel(String name, String pageKey) {
        this.name = requireNonNull(name);
        this.pageKey = requireNonNull(pageKey);
    }

    /**
     * Gets the display name of the page.
     *
     * @return the display name of the page
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the display name of the page.
     *
     * @param name the new page name to set
     */
    public void setName(String name) {
        this.name = requireNonNull(name);
    }

    /**
     * Gets the key identifying this page, e.g. the normalized context path.
     *
     * @return the key identifying this page
     */
    public String getPageKey() {
        return pageKey;
    }

    /**
     * Gets the selected elements of this page.
     *
     * @return the selected elements of this page
     */
    public List<PageElementModel> getElements() {
        return elements;
    }

    /**
     * Adds a selected element to this page, the element is ignored if a field with the same name already exists.
     *
     * @param element the element to add
     * @return true if the element is added, false if a field with the same name already exists
     */
    public boolean addElement(PageElementModel element) {
        requireNonNull(element);
        if (hasField(element.getFieldName())) {
            return false;
        }
        return elements.add(element);
    }

    /**
     * Checks whether a field with the given name already exists in this page.
     *
     * @param fieldName the field name to check
     * @return true if the field exists
     */
    public boolean hasField(String fieldName) {
        return elements.stream().anyMatch(e -> e.getFieldName().equals(fieldName));
    }

    @Override
    public String toString() {
        return "PageModel{" + "name='" + name + '\'' + ", pageKey='" + pageKey + '\'' + ", elements=" + elements.size()
                + '}';
    }
}
