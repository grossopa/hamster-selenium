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

package org.hamster.selenium.core.component.util;

import org.openqa.selenium.WebElement;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.strip;

/**
 * The utils for {@link org.openqa.selenium.WebElement} and {@link org.hamster.selenium.core.component.WebComponent}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class WebComponentUtils {

    /**
     * Private constructor
     */
    private WebComponentUtils() {
        throw new AssertionError();
    }

    /**
     * Whether the attribute of the element contains the desired value, it use a single space as splitter. Examples:
     * <p>
     * &lt;div class="attr-1 attr-2 attr-3"&gt;Some text&lt;/div&gt;
     * </p>
     *
     * <ul>
     *   <li>attributeContains(element, "class", "attr-1") == true</li>
     *   <li>attributeContains(element, "class", "attr-4") == false</li>
     *   <li>attributeContains(element, "class", "attr") == false // require full value to be present</li>
     *   <li>attributeContains(element, "class", "ATTR-1") == false // case sensitive</li>
     * </ul>
     *
     * @param element
     *         the attribute of the element to evaluate
     * @param attributeName
     *         the name of the tested attribute
     * @param attributeValue
     *         the desired value of the attribute
     * @return true for found any matches
     */
    public static boolean attributeContains(WebElement element, String attributeName, String attributeValue) {
        return attributeContains(element, attributeName, attributeValue, " ");
    }

    /**
     * Whether the attribute of the element contains the desired value. Examples:
     * <p>
     * &lt;div class="attr-1;attr-2 ; attr-3"&gt;Some text&lt;/div&gt;
     * </p>
     *
     * <ul>
     *   <li>attributeContains(element, "class", "attr-1", ";") == true</li>
     *   <li>attributeContains(element, "class", "attr-4", ";") == false</li>
     *   <li>attributeContains(element, "class", "attr", ";") == false // require full value to be present</li>
     *   <li>attributeContains(element, "class", "ATTR-1", ";") == false // case sensitive</li>
     * </ul>
     *
     * @param element
     *         the attribute of the element to evaluate
     * @param attributeName
     *         the name of the tested attribute
     * @param attributeValue
     *         the desired value of the attribute
     * @param splitRegex
     *         the split String
     * @return true for found any matches
     */
    public static boolean attributeContains(WebElement element, String attributeName, String attributeValue,
            String splitRegex) {
        return stream(element.getAttribute(attributeName).split(splitRegex))
                .anyMatch(css -> strip(css).equals(attributeValue));
    }
}
