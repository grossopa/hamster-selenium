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
                builder.anywhere().attr("some-attr").contains("some-value").build());
    }

    @Test
    void testBuilder2() {
        assertEquals(By2.xpath("//div[contains(@some-attr,\"some-value\")]"),
                builder.anywhere("div").attr("some-attr").contains("some-value").build());
    }

    @Test
    void testBuilder3() {
        assertEquals(By2.xpath(".//*[contains(@some-attr,\"some-value\")]"),
                builder.anywhereRelative().attr("some-attr").contains("some-value").build());
    }

    @Test
    void testBuilder4() {
        assertEquals(By2.xpath(".//div[contains(@some-attr,\"some-value\")]"),
                builder.anywhereRelative("div").attr("some-attr").contains("some-value").build());
    }

    @Test
    void testBuilder5() {
        assertEquals(By2.xpath("./*[contains(@some-attr,\"some-value\")]"),
                builder.relative().attr("some-attr").contains("some-value").build());
    }

    @Test
    void testBuilder6() {
        assertEquals(By2.xpath("./div[contains(@some-attr,\"some-value\")]"),
                builder.relative("div").attr("some-attr").contains("some-value").build());
    }

    @Test
    void testBuilder7() {
        assertEquals(By2.xpath("*[contains(@some-attr,\"some-value\")]"),
                builder.empty().attr("some-attr").contains("some-value").build());
    }

    @Test
    void testBuilder8() {
        assertEquals(By2.xpath("div[contains(@some-attr,\"some-value\")]"),
                builder.empty("div").attr("some-attr").contains("some-value").build());
    }

    @Test
    void testBuilder9() {
        assertEquals(By2.xpath("*[contains(text(),\"some-value\")]"),
                builder.empty().text().contains("some-value").build());
    }

    @Test
    void testBuilder10() {
        assertEquals(By2.xpath("div[contains(name(),\"some-tag\")]"),
                builder.empty("div").name().contains("some-tag").build());
    }

    @Test
    void testBuilder11() {
        assertEquals(By2.xpath("div[@some-attr=\"some-value\"]"),
                builder.empty("div").attr("some-attr").exact("some-value").build());
    }

    @Test
    void testBuilder12() {
        assertEquals(By2.xpath("div[matches(@some-attr,\"[0-9]\")]"),
                builder.empty("div").attr("some-attr").matches("[0-9]").build());
    }

    @Test
    void testBuilder13() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,\"startchar\")]"),
                builder.empty("div").attr("some-attr").startsWith("startchar").build());
    }

    @Test
    void testBuilder14() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,\"start'char\")]"),
                builder.empty("div").attr("some-attr").startsWith("start'char").build());
    }

    @Test
    void testBuilder15() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").build());
    }

    @Test
    void testBuilder16() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/following::*"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").following().build());
    }

    @Test
    void testBuilder17() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/following::span"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").following("span").build());
    }

    @Test
    void testBuilder18() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/ancestor::*"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").ancestor().build());
    }

    @Test
    void testBuilder19() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/ancestor::span"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").ancestor("span").build());
    }

    @Test
    void testBuilder20() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/child::*"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").child().build());
    }

    @Test
    void testBuilder21() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/child::span"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").child("span").build());
    }

    @Test
    void testBuilder22() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/descendant::*"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").descendant().build());
    }

    @Test
    void testBuilder23() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/descendant::span"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").descendant("span").build());
    }

    @Test
    void testBuilder24() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/following-sibling::*"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").followingSibling().build());
    }

    @Test
    void testBuilder25() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/following-sibling::span"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").followingSibling("span").build());
    }

    @Test
    void testBuilder26() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/preceding-sibling::*"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").precedingSibling().build());
    }

    @Test
    void testBuilder27() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/preceding-sibling::span"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").precedingSibling("span").build());
    }

    @Test
    void testBuilder28() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/preceding::*"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").preceding().build());
    }

    @Test
    void testBuilder29() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/preceding::span"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").preceding("span").build());
    }

    @Test
    void testBuilder30() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/parent::*"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").parent().build());
    }

    @Test
    void testBuilder31() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]/parent::span"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").parent("span").build());
    }

    @Test
    void testBuilder32() {
        assertEquals(By2.xpath("div[starts-with(@some-attr,'start\"char')]"
                        + "/preceding::span/following-sibling::tag[matches(@data-value,\"some-pattern\")]"),
                builder.empty("div").attr("some-attr").startsWith("start\"char").preceding("span").axes()
                        .followingSibling("tag").attr("data-value").matches("some-pattern").build());
    }

    @Test
    void testBuilder33() {
        assertEquals(By2.xpath("./div"), builder.relative("div").build());
    }

    @Test
    void testBuilder34() {
        assertEquals(By2.xpath("div[not(starts-with(@some-attr,'start\"char'))]"
                        + "/preceding::span/following-sibling::tag[not(matches(@data-value,\"some-pattern\"))]"),
                builder.empty("div").attr("some-attr").not().startsWith("start\"char").preceding("span").axes()
                        .followingSibling("tag").attr("data-value").not().matches("some-pattern").build());
    }

    @Test
    void testXpathString1() {
        assertEquals("//*[contains(text(),\"abc\")]", builder.anywhere().text().contains("abc").xpathString());
    }

    @Test
    void testXpathString2() {
        assertEquals("//*[contains(text(),\"abc\")]/ancestor::*",
                builder.anywhere().text().contains("abc").ancestor().xpathString());
    }

}
