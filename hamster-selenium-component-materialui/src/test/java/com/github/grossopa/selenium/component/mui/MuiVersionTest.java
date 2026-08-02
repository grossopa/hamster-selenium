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
