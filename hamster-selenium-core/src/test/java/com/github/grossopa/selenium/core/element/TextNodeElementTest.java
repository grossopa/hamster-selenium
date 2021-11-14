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

import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link TextNodeElement}
 *
 * @author Jack Yin
 * @since 1.8
 */
class TextNodeElementTest {

    TextNodeElement testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new TextNodeElement(TextNodeType.TEXT, "some text");
    }


    @Test
    void getType() {
        assertEquals(TextNodeType.TEXT, testSubject.getType());
    }

    @Test
    void getText() {
        assertEquals("some text", testSubject.getText());
    }

    @Test
    void testEquals() {
        SimpleEqualsTester equalsTester = new SimpleEqualsTester();
        equalsTester.addEqualityGroup(new TextNodeElement(TextNodeType.COMMENT, "text 1"),
                new TextNodeElement(TextNodeType.COMMENT, "text 1"));
        equalsTester.addEqualityGroup(new TextNodeElement(TextNodeType.COMMENT, "text 2"));
        equalsTester.addEqualityGroup(new TextNodeElement(TextNodeType.TEXT, "text 1"));
        equalsTester.addEqualityGroup(new TextNodeElement(TextNodeType.TEXT, "text 2"));

        equalsTester.testEquals();
    }

    @Test
    void testToString() {
        assertEquals("TextNodeElement{type=TEXT, text='some text'}", testSubject.toString());
    }
}
