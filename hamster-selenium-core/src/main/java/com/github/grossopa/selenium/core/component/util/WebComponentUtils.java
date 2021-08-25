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

package com.github.grossopa.selenium.core.component.util;

import com.github.grossopa.selenium.core.component.WebComponent;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.strip;

/**
 * The utils for {@link org.openqa.selenium.WebElement} and {@link WebComponent}.
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
     * Whether the attribute of the element contains the desired value, it uses a single space as splitter. Examples:
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
     * @param element the attribute of the element to evaluate
     * @param attributeName the name of the tested attribute
     * @param attributeValue the desired value of the attribute
     * @return true for found any matches
     */
    public static boolean attributeContains(WebElement element, String attributeName, String attributeValue) {
        return attributeContains(element, attributeName, attributeValue, " ");
    }

    /**
     * Whether the style attribute contains desired value.
     *
     * {@code
     * <div style="display : block; width: 200px; height: 50% ;   ">
     *
     * <ul>
     *   <li>styleContains(element, "display", "block") == true</li>
     *   <li>styleContains(element, "display", "none") == false</li>
     *   <li>styleContains(element, "background-color", "white") == false // require full value to be present</li>
     *   <li>styleContains(element, "WIDTH", "200PX") == true // case insensitive</li>
     * </ul>
     * }
     *
     * @param element the element to find the style from
     * @param styleName the name of the style to find
     * @param styleValue the value of the style to find
     * @return true for found any matches
     */
    public static boolean styleContains(WebElement element, String styleName, String styleValue) {
        return stream(element.getAttribute("style").split(";"))
                .map(str -> stream(str.split(":")).map(String::strip).collect(Collectors.joining(":")))
                .anyMatch(str -> StringUtils.equalsIgnoreCase(str, styleName + ":" + styleValue));
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
     * @param element the attribute of the element to evaluate
     * @param attributeName the name of the tested attribute
     * @param attributeValue the desired value of the attribute
     * @param splitRegex the split String
     * @return true for found any matches
     */
    public static boolean attributeContains(WebElement element, String attributeName, String attributeValue,
            String splitRegex) {
        return stream(element.getAttribute(attributeName).split(splitRegex))
                .anyMatch(css -> strip(css).equals(attributeValue));
    }

    /**
     * Gets the center point of the given rectangle.
     *
     * @param rect the rectangle
     * @return center point
     */
    public static Point getCenter(Rectangle rect) {
        return getCenter(rect.x, rect.y, rect.height, rect.width);
    }

    /**
     * Gets the center point of the given rectangle.
     *
     * @param x x point
     * @param y y point
     * @param height height of the rectangle
     * @param width width of the rectangle
     * @return center point
     */
    public static Point getCenter(int x, int y, int height, int width) {
        return new Point(x + (width >> 1), y + (height >> 1));
    }
}
