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

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * The result of a page identification, either a matched existing {@link PageModel} or a suggestion to create a new
 * page with the given key and display name.
 *
 * @author Jack Yin
 * @since 1.15
 * @see PageIdentificationStrategy
 */
public class PageIdentification {

    private final PageModel matchedPage;
    private final String suggestedKey;
    private final String suggestedName;

    private PageIdentification(@Nullable PageModel matchedPage, @Nullable String suggestedKey,
            @Nullable String suggestedName) {
        this.matchedPage = matchedPage;
        this.suggestedKey = suggestedKey;
        this.suggestedName = suggestedName;
    }

    /**
     * Creates an identification that matches an existing page.
     *
     * @param page the matched existing page, must not be null
     * @return the identification representing the matched page
     */
    public static PageIdentification matched(PageModel page) {
        return new PageIdentification(requireNonNull(page), null, null);
    }

    /**
     * Creates an identification that suggests creating a new page.
     *
     * @param key the key of the suggested new page, must not be null
     * @param name the display name of the suggested new page, must not be null
     * @return the identification representing the suggestion of a new page
     */
    public static PageIdentification newPage(String key, String name) {
        return new PageIdentification(null, requireNonNull(key), requireNonNull(name));
    }

    /**
     * Whether an existing page is matched.
     *
     * @return true if an existing page is matched, false if a new page is suggested
     */
    public boolean isMatched() {
        return matchedPage != null;
    }

    /**
     * Gets the matched existing page, null if a new page is suggested.
     *
     * @return the matched existing page, null if a new page is suggested
     */
    @Nullable
    public PageModel getMatchedPage() {
        return matchedPage;
    }

    /**
     * Gets the key of the suggested new page, null if an existing page is matched.
     *
     * @return the key of the suggested new page
     */
    @Nullable
    public String getSuggestedKey() {
        return suggestedKey;
    }

    /**
     * Gets the display name of the suggested new page, null if an existing page is matched.
     *
     * @return the display name of the suggested new page
     */
    @Nullable
    public String getSuggestedName() {
        return suggestedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageIdentification that)) {
            return false;
        }
        return Objects.equals(matchedPage, that.matchedPage) && Objects.equals(suggestedKey, that.suggestedKey)
                && Objects.equals(suggestedName, that.suggestedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchedPage, suggestedKey, suggestedName);
    }

    @Override
    public String toString() {
        return "PageIdentification{" + "matchedPage=" + matchedPage + ", suggestedKey='" + suggestedKey + '\''
                + ", suggestedName='" + suggestedName + '\'' + '}';
    }
}
