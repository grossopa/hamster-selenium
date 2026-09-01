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

import com.github.grossopa.selenium.core.intercepting.InterceptingHandler;
import com.github.grossopa.selenium.core.intercepting.MethodInfo;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.DRIVER_GET;
import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.ELEMENT_CLICK;
import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.ELEMENT_SEND_KEYS;
import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.ELEMENT_SUBMIT;
import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.NAVIGATION_BACK;
import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.NAVIGATION_FORWARD;
import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.NAVIGATION_TO;
import static java.util.Objects.requireNonNull;

/**
 * An {@link InterceptingHandler} implementation for the runtime real-time monitoring. It watches the intercepted
 * driver and element actions, emits interaction and navigation events to the registered listeners and detects url
 * changes so that the page could be re-classified.
 *
 * @author Jack Yin
 * @since 1.15
 * @see RecorderEventListener
 * @see RecorderEvent
 */
public class RecordingInterceptingHandler implements InterceptingHandler {

    private static final Set<String> INTERACTION_METHODS = Set.of(ELEMENT_CLICK, ELEMENT_SEND_KEYS, ELEMENT_SUBMIT);

    private static final Set<String> NAVIGATION_METHODS = Set.of(DRIVER_GET, NAVIGATION_TO, NAVIGATION_BACK,
            NAVIGATION_FORWARD);

    private final List<RecorderEventListener> listeners = new CopyOnWriteArrayList<>();
    private final Supplier<String> currentUrlSupplier;
    private String lastUrl;

    /**
     * Constructs an instance with the supplier providing the current url for change detection.
     *
     * @param currentUrlSupplier the supplier of the current url, must not be null
     */
    public RecordingInterceptingHandler(Supplier<String> currentUrlSupplier) {
        this.currentUrlSupplier = requireNonNull(currentUrlSupplier);
    }

    /**
     * Registers a listener to receive the recorder events.
     *
     * @param listener the listener to add
     */
    public void addListener(RecorderEventListener listener) {
        listeners.add(requireNonNull(listener));
    }

    /**
     * Removes a registered listener.
     *
     * @param listener the listener to remove
     */
    public void removeListener(RecorderEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onBefore(MethodInfo<?> methodInfo) {
        // nothing to do before the action
    }

    @Override
    public void onAfter(MethodInfo<?> methodInfo, Object resultValue) {
        String methodName = methodInfo.getName();
        if (INTERACTION_METHODS.contains(methodName)) {
            fireEvent(new RecorderEvent(RecorderEventType.INTERACTION, methodName, lastUrl, null));
            checkUrlChange(methodName);
        } else if (NAVIGATION_METHODS.contains(methodName)) {
            fireEvent(new RecorderEvent(RecorderEventType.NAVIGATION, methodName, lastUrl, null));
            checkUrlChange(methodName);
        }
    }

    @Override
    public void onException(MethodInfo<?> methodInfo, Exception exception) {
        fireEvent(new RecorderEvent(RecorderEventType.EXCEPTION, methodInfo.getName(), lastUrl, exception));
    }

    /**
     * Initializes the remembered url so that only real url changes trigger page changed events afterwards.
     *
     * @param url the initial url
     */
    public void initializeUrl(String url) {
        this.lastUrl = url;
    }

    /**
     * Gets the last known url.
     *
     * @return the last known url
     */
    public String getLastUrl() {
        return lastUrl;
    }

    private void checkUrlChange(String methodName) {
        String newUrl = null;
        try {
            newUrl = currentUrlSupplier.get();
        } catch (RuntimeException exception) {
            // the driver may be in a transient state, ignore and keep the last url
        }
        if (newUrl != null && !newUrl.equals(lastUrl)) {
            lastUrl = newUrl;
            fireEvent(new RecorderEvent(RecorderEventType.PAGE_CHANGED, methodName, newUrl, null));
        }
    }

    private void fireEvent(RecorderEvent event) {
        for (RecorderEventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
