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

package com.github.grossopa.selenium.core.element;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents a text element for either text or comments.
 *
 * @author Jack Yin
 * @since 1.8
 */
public class TextNodeElement {
    private final TextNodeType type;
    private final String text;

    /**
     * Constructs an instance with the node name, type and text
     *
     * @param type the type of the node
     * @param text the node text
     */
    public TextNodeElement(TextNodeType type, String text) {
        requireNonNull(type);
        requireNonNull(text);
        this.type = type;
        this.text = text;
    }

    /**
     * Gets the type of the text node.
     *
     * @return the type of the text node.
     */
    public TextNodeType getType() {
        return type;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextNodeElement)) {
            return false;
        }
        TextNodeElement that = (TextNodeElement) o;
        return type == that.type && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, text);
    }

    @Override
    public String toString() {
        return "TextNodeElement{" + "type=" + type + ", text='" + text + '\'' + '}';
    }
}
