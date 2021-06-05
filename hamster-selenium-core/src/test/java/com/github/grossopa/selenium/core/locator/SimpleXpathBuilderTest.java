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

package com.github.grossopa.selenium.core.locator;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link SimpleXpathBuilder}
 *
 * @author Jack Yin
 * @since 1.5
 */
class SimpleXpathBuilderTest {

    SimpleXpathBuilder.PrefixBuilder builder = new SimpleXpathBuilder.PrefixBuilder();

    @Test
    void testConstructor() {
        boolean asserted = false;
        Constructor<?> constructor = SimpleXpathBuilder.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            assertEquals(AssertionError.class, e.getCause().getClass());
            asserted = true;
        }

        assertTrue(asserted);
    }

    @Test
    void testBuilder1() {
        assertEquals(By2.xpath("//*[contains(@some-attr,\"some-value\")]"),
                builder.anywhere().attr("some-attr").contains("some-value"));
    }

    @Test
    void testBuilder2() {
        assertEquals(By2.xpath("//div[contains(@some-attr,\"some-value\")]"),
                builder.anywhere("div").attr("some-attr").contains("some-value"));
    }

    @Test
    void testBuilder3() {
        assertEquals(By2.xpath(".//*[contains(@some-attr,\"some-value\")]"),
                builder.anywhereRelative().attr("some-attr").contains("some-value"));
    }

    @Test
    void testBuilder4() {
        assertEquals(By2.xpath(".//div[contains(@some-attr,\"some-value\")]"),
                builder.anywhereRelative("div").attr("some-attr").contains("some-value"));
    }

    @Test
    void testBuilder5() {
        assertEquals(By2.xpath("./*[contains(@some-attr,\"some-value\")]"),
                builder.relative().attr("some-attr").contains("some-value"));
    }

    @Test
    void testBuilder6() {
        assertEquals(By2.xpath("./div[contains(@some-attr,\"some-value\")]"),
                builder.relative("div").attr("some-attr").contains("some-value"));
    }

    @Test
    void testBuilder7() {
        assertEquals(By2.xpath("*[contains(@some-attr,\"some-value\")]"),
                builder.empty().attr("some-attr").contains("some-value"));
    }

    @Test
    void testBuilder8() {
        assertEquals(By2.xpath("div[contains(@some-attr,\"some-value\")]"),
                builder.empty("div").attr("some-attr").contains("some-value"));
    }

    @Test
    void testBuilder9() {
        assertEquals(By2.xpath("*[contains(text(),\"some-value\")]"), builder.empty().text().contains("some-value"));
    }

    @Test
    void testBuilder10() {
        assertEquals(By2.xpath("div[contains(name(),\"some-tag\")]"), builder.empty("div").name().contains("some-tag"));
    }

    @Test
    void testBuilder11() {
        assertEquals(By2.xpath("div[@some-attr=\"some-value\"]"),
                builder.empty("div").attr("some-attr").exact("some-value"));
    }

    @Test
    void testBuilder12() {
        assertEquals(By2.xpath("div[matches(@some-attr,\"[0-9]\")]"),
                builder.empty("div").attr("some-attr").matches("[0-9]"));
    }

    @Test
    void testBuilder13() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,\"startchar\")]"),
                builder.empty("div").attr("some-attr").startsWith("startchar"));
    }

    @Test
    void testBuilder14() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,\"start'char\")]"),
                builder.empty("div").attr("some-attr").startsWith("start'char"));
    }

    @Test
    void testBuilder15() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]"),
                builder.empty("div").attr("some-attr").startsWith("start\"char"));
    }

}
