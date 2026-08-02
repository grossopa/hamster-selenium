package com.github.grossopa.playwright.core.intercepting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MethodInfoTest {

    @Test
    void testConstructor() {
        Object source = new Object();
        MethodInfo<Object> info = new MethodInfo<>(source, "testMethod", "param1", "param2");

        assertEquals(source, info.getSource());
        assertEquals("testMethod", info.getName());
        assertArrayEquals(new Object[]{"param1", "param2"}, info.getParams());
        assertNotNull(info.getStartTimeInMillis());
        assertNull(info.getEndTimeInMillis());
    }

    @Test
    void testConstructorWithNullSource() {
        assertThrows(NullPointerException.class, () -> new MethodInfo<>(null, "test"));
    }

    @Test
    void testConstructorWithNullName() {
        var object = new Object();
        assertThrows(NullPointerException.class, () -> new MethodInfo<>(object, null));
    }

    @Test
    void testConstructorWithNullParams() {
        var object  =new Object();
        assertThrows(NullPointerException.class, () -> new MethodInfo<>(object, "test", (Object[]) null));
    }

    @Test
    void testExecutionDone() {
        MethodInfo<Object> info = new MethodInfo<>(new Object(), "test");
        assertNull(info.getEndTimeInMillis());

        info.executionDone();
        assertNotNull(info.getEndTimeInMillis());
    }

    @Test
    void testGetTimeElapsedInMillis() {
        MethodInfo<Object> info = new MethodInfo<>(new Object(), "test");
        assertNull(info.getTimeElapsedInMillis());

        info.executionDone();
        assertNotNull(info.getTimeElapsedInMillis());
        assertTrue(info.getTimeElapsedInMillis() >= 0);
    }

    @Test
    void testCreate() {
        Object source = new Object();
        MethodInfo<Object> info = MethodInfo.create(source, "method", "arg1");

        assertEquals(source, info.getSource());
        assertEquals("method", info.getName());
        assertArrayEquals(new Object[]{"arg1"}, info.getParams());
    }
}
