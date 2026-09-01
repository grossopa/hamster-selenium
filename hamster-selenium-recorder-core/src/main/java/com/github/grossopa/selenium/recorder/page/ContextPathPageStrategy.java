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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * The default page identification strategy based on the context path of the url. The query and fragment parts are
 * ignored, pure number path segments are folded into {@code {id}} so that e.g. {@code /user/123} and
 * {@code /user/456} are classified into the same page, and all SPA route changes under the same context path belong
 * to the same page.
 *
 * @author Jack Yin
 * @since 1.15
 * @see PageIdentificationStrategy
 */
public class ContextPathPageStrategy implements PageIdentificationStrategy {

    /**
     * The placeholder for folded number path segments.
     */
    public static final String ID_SEGMENT = "{id}";

    /**
     * The page name for the root path.
     */
    public static final String ROOT_PAGE_NAME = "Home";

    @Override
    public PageIdentification identify(String currentUrl, List<PageModel> existingPages) {
        String key = toPageKey(currentUrl);
        Optional<PageModel> matched = existingPages.stream().filter(page -> page.getPageKey().equals(key))
                .findFirst();
        return matched.map(PageIdentification::matched)
                .orElseGet(() -> PageIdentification.newPage(key, toPageName(key)));
    }

    /**
     * Normalizes the given url into a page key by keeping only the context path with number segments folded.
     *
     * @param url the url to normalize
     * @return the normalized page key, e.g. "/user/{id}"
     */
    public String toPageKey(String url) {
        String path = extractPath(url);
        String[] segments = path.split("/");
        List<String> normalized = new ArrayList<>();
        for (String segment : segments) {
            if (segment.isBlank()) {
                continue;
            }
            normalized.add(segment.chars().allMatch(Character::isDigit) ? ID_SEGMENT : segment);
        }
        if (normalized.isEmpty()) {
            return "/";
        }
        return "/" + String.join("/", normalized);
    }

    /**
     * Derives a human readable page name from the given page key, e.g. "/user/login" to "UserLogin".
     *
     * @param pageKey the page key to derive the name from
     * @return the derived page name
     */
    public String toPageName(String pageKey) {
        if ("/".equals(pageKey)) {
            return ROOT_PAGE_NAME;
        }
        StringBuilder builder = new StringBuilder();
        for (String segment : pageKey.split("/")) {
            if (!segment.isBlank() && !segment.startsWith("{")) {
                builder.append(capitalize(segment));
            }
        }
        return builder.isEmpty() ? ROOT_PAGE_NAME : builder.toString();
    }

    private String extractPath(String url) {
        try {
            URI uri = URI.create(url);
            String path = uri.getPath();
            return path == null || path.isBlank() ? "/" : path;
        } catch (IllegalArgumentException exception) {
            return "/";
        }
    }

    private String capitalize(String segment) {
        return segment.substring(0, 1).toUpperCase(Locale.ROOT) + segment.substring(1);
    }
}
