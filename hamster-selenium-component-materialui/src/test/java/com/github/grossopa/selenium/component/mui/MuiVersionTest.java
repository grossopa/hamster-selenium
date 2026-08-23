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
package com.github.grossopa.selenium.component.mui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MuiVersionTest {

    @Test
    void v4Apply() {
        MuiVersion.Func<String, String> func = new MuiVersion.Func<>() {
            @Override
            public String applyV4(String input) {
                return "v4-" + input;
            }

            @Override
            public String applyV5(String input) {
                return "v5-" + input;
            }

            @Override
            public String applyV6(String input) {
                return "v6-" + input;
            }
        };

        assertEquals("v4-test", MuiVersion.V4.apply(func, "test"));
    }

    @Test
    void v5Apply() {
        MuiVersion.Func<String, String> func = new MuiVersion.Func<>() {
            @Override
            public String applyV4(String input) {
                return "v4-" + input;
            }

            @Override
            public String applyV5(String input) {
                return "v5-" + input;
            }

            @Override
            public String applyV6(String input) {
                return "v6-" + input;
            }
        };

        assertEquals("v5-test", MuiVersion.V5.apply(func, "test"));
    }

    @Test
    void v6Apply() {
        MuiVersion.Func<String, String> func = new MuiVersion.Func<>() {
            @Override
            public String applyV4(String input) {
                return "v4-" + input;
            }

            @Override
            public String applyV5(String input) {
                return "v5-" + input;
            }

            @Override
            public String applyV6(String input) {
                return "v6-" + input;
            }
        };

        assertEquals("v6-test", MuiVersion.V6.apply(func, "test"));
    }

    @Test
    void values() {
        assertEquals(3, MuiVersion.values().length);
    }

    @Test
    void valueOf() {
        assertEquals(MuiVersion.V4, MuiVersion.valueOf("V4"));
        assertEquals(MuiVersion.V5, MuiVersion.valueOf("V5"));
        assertEquals(MuiVersion.V6, MuiVersion.valueOf("V6"));
    }
}
