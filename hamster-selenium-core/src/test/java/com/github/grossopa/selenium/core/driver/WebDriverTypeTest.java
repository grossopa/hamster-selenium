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

package com.github.grossopa.selenium.core.driver;

import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link WebDriverType}
 *
 * @author Jack Yin
 * @since 1.0
 */
class WebDriverTypeTest {

    @Test
    void applyChrome() {
        assertEquals("abcChrome", WebDriverType.CHROME.apply(new TestAction(), "abc"));
    }

    @Test
    void applyEdge() {
        assertEquals("abcEdge", WebDriverType.EDGE.apply(new TestAction(), "abc"));
    }

    @Test
    void applyFirefox() {
        assertEquals("abcFirefox", WebDriverType.FIREFOX.apply(new TestAction(), "abc"));
    }

    @Test
    void applyIE() {
        assertEquals("abcIE", WebDriverType.IE.apply(new TestAction(), "abc"));
    }

    @Test
    void applySafari() {
        assertEquals("abcSafari", WebDriverType.SAFARI.apply(new TestAction(), "abc"));
    }

    @Test
    void applyOpera() {
        assertEquals("abcOpera", WebDriverType.OPERA.apply(new TestAction(), "abc"));
    }

    static class TestAction implements WebDriverType.WebDriverTypeFunction<String, String> {

        @Nullable
        @Override
        public String applyChrome(@Nullable String input) {
            return input + "Chrome";
        }

        @Nullable
        @Override
        public String applyEdge(@Nullable String input) {
            return input + "Edge";
        }

        @Nullable
        @Override
        public String applyFirefox(@Nullable String input) {
            return input + "Firefox";
        }

        @Nullable
        @Override
        public String applyIE(@Nullable String input) {
            return input + "IE";
        }

        @Nullable
        @Override
        public String applyOpera(@Nullable String input) {
            return input + "Opera";
        }

        @Nullable
        @Override
        public String applySafari(@Nullable String input) {
            return input + "Safari";
        }
    }

}
