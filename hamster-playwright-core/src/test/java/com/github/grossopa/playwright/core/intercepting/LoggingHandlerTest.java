package com.github.grossopa.playwright.core.intercepting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoggingHandlerTest {

    private Logger logger;
    private LoggingHandler handler;

    @BeforeEach
    void setUp() {
        logger = mock(Logger.class);
        handler = new LoggingHandler(0L, logger);
    }

    @Test
    void testConstructorWithThreshold() {
        LoggingHandler h = new LoggingHandler(100L);
        assertNotNull(h);
    }

    @Test
    void testConstructorWithThresholdAndLogger() {
        LoggingHandler h = new LoggingHandler(100L, logger);
        assertNotNull(h);
    }

    @Test
    void testOnBefore() {
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        assertDoesNotThrow(() -> handler.onBefore(info));
    }

    @Test
    void testOnAfterWithThresholdMet() {
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        info.executionDone();
        handler.onAfter(info, "result");
        verify(logger).log(eq(Level.INFO), any(java.util.function.Supplier.class));
    }

    @Test
    void testOnAfterWithThresholdNotMet() {
        LoggingHandler highThresholdHandler = new LoggingHandler(10000L, logger);
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        info.executionDone();
        highThresholdHandler.onAfter(info, "result");
        verify(logger, never()).log(eq(Level.INFO), any(java.util.function.Supplier.class));
    }

    @Test
    void testOnException() {
        MethodInfo<Object> info = MethodInfo.create(new Object(), "test");
        info.executionDone();
        Exception ex = new RuntimeException("test error");
        handler.onException(info, ex);
        verify(logger).log(eq(Level.WARNING), any(String.class), eq(ex));
    }
}
