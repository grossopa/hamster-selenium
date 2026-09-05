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
package com.github.grossopa.selenium.recorder.monitor;

import com.github.grossopa.selenium.core.intercepting.MethodInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.DRIVER_GET;
import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.ELEMENT_CLICK;
import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.NAVIGATION_TO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link RecordingInterceptingHandler}
 *
 * @author Jack Yin
 * @since 1.15
 */
class RecordingInterceptingHandlerTest {

    AtomicReference<String> currentUrl = new AtomicReference<>("http://localhost/a");
    List<RecorderEvent> events = new ArrayList<>();
    RecorderEventListener listener = events::add;

    RecordingInterceptingHandler testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new RecordingInterceptingHandler(currentUrl::get);
        testSubject.addListener(listener);
        testSubject.initializeUrl(currentUrl.get());
    }

    @Test
    void testInteractionEventWithoutUrlChange() {
        testSubject.onAfter(new MethodInfo<>(new Object(), ELEMENT_CLICK), null);
        assertEquals(1, events.size());
        assertEquals(RecorderEventType.INTERACTION, events.get(0).getType());
        assertEquals(ELEMENT_CLICK, events.get(0).getMethodName());
    }

    @Test
    void testNavigationEventWithUrlChange() {
        currentUrl.set("http://localhost/b");
        testSubject.onAfter(new MethodInfo<>(new Object(), NAVIGATION_TO), null);
        assertEquals(2, events.size());
        assertEquals(RecorderEventType.NAVIGATION, events.get(0).getType());
        assertEquals(RecorderEventType.PAGE_CHANGED, events.get(1).getType());
        assertEquals("http://localhost/b", events.get(1).getUrl());
        assertEquals("http://localhost/b", testSubject.getLastUrl());
    }

    @Test
    void testDriverGetWithUrlChange() {
        currentUrl.set("http://localhost/c");
        testSubject.onAfter(new MethodInfo<>(new Object(), DRIVER_GET), null);
        assertTrue(events.stream().anyMatch(event -> event.getType() == RecorderEventType.PAGE_CHANGED));
    }

    @Test
    void testNoEventForUnknownMethod() {
        testSubject.onBefore(new MethodInfo<>(new Object(), ELEMENT_CLICK));
        testSubject.onAfter(new MethodInfo<>(new Object(), "driver.close"), null);
        assertTrue(events.isEmpty());
    }

    @Test
    void testExceptionEvent() {
        Exception exception = new IllegalStateException("boom");
        testSubject.onException(new MethodInfo<>(new Object(), ELEMENT_CLICK), exception);
        assertEquals(1, events.size());
        assertEquals(RecorderEventType.EXCEPTION, events.get(0).getType());
        assertEquals(exception, events.get(0).getException());
    }

    @Test
    void testUrlSupplierExceptionIsIgnored() {
        RecordingInterceptingHandler handler = new RecordingInterceptingHandler(() -> {
            throw new IllegalStateException("driver not ready");
        });
        handler.addListener(events::add);
        handler.initializeUrl("http://localhost/a");
        handler.onAfter(new MethodInfo<>(new Object(), ELEMENT_CLICK), null);
        assertEquals(1, events.size());
        assertEquals(RecorderEventType.INTERACTION, events.get(0).getType());
        assertEquals("http://localhost/a", handler.getLastUrl());
    }

    @Test
    void testRemoveListener() {
        testSubject.removeListener(listener);
        testSubject.onAfter(new MethodInfo<>(new Object(), ELEMENT_CLICK), null);
        assertTrue(events.isEmpty());
    }
}
