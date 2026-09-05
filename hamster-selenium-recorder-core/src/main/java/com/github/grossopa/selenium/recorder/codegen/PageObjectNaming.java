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
package com.github.grossopa.selenium.recorder.codegen;

import java.util.Locale;

/**
 * Utility for deriving valid Java class names from page names.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class PageObjectNaming {

    /**
     * The suffix appended to generated page object class names if not present.
     */
    public static final String PAGE_SUFFIX = "Page";

    /**
     * private constructor
     */
    private PageObjectNaming() {
        throw new AssertionError();
    }

    /**
     * Derives a valid Java class name from the given page name by capitalizing each alphanumeric segment and appending
     * {@value #PAGE_SUFFIX} if not present, e.g. "user login" to "UserLoginPage".
     *
     * @param pageName the page name to derive the class name from
     * @return the derived class name
     */
    public static String toClassName(String pageName) {
        StringBuilder builder = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : pageName.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                builder.append(capitalizeNext ? Character.toUpperCase(c) : c);
                capitalizeNext = false;
            } else {
                capitalizeNext = true;
            }
        }
        String className = builder.isEmpty() ? PAGE_SUFFIX : builder.toString();
        if (Character.isDigit(className.charAt(0))) {
            className = PAGE_SUFFIX + className;
        }
        if (!className.endsWith(PAGE_SUFFIX)) {
            className = className + PAGE_SUFFIX;
        }
        return className;
    }

    /**
     * Derives a valid Java method/field name from the given name by keeping the first segment lower case, e.g. "Submit
     * Button" to "submitButton".
     *
     * @param name the name to derive the method name from
     * @return the derived method name
     */
    public static String toMethodName(String name) {
        String className = toClassName(name);
        if (className.endsWith(PAGE_SUFFIX) && className.length() > PAGE_SUFFIX.length()) {
            className = className.substring(0, className.length() - PAGE_SUFFIX.length());
        }
        return className.substring(0, 1).toLowerCase(Locale.ROOT) + className.substring(1);
    }
}
