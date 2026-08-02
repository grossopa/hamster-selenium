package com.github.grossopa.playwright.core.intercepting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterceptingHandlerTest {

    private InterceptingHandler handler;

    @BeforeEach
    void setUp() {
        handler = mock(InterceptingHandler.class, CALLS_REAL_METHODS);
    }

    @Test
    void testExecuteSuccess() {
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        String result = handler.execute(() -> "success", info);

        assertEquals("success", result);
        assertNotNull(info.getEndTimeInMillis());
    }

    @Test
    void testExecuteWithException() {
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        RuntimeException ex = new RuntimeException("test error");

        doThrow(ex).when(handler).onException(any(), any());

        assertThrows(RuntimeException.class, () -> handler.execute(() -> {
            throw ex;
        }, info));
    }
}
