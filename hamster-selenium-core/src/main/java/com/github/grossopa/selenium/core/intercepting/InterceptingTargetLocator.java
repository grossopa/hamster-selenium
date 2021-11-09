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

package com.github.grossopa.selenium.core.intercepting;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static java.util.Objects.requireNonNull;

/**
 * Intercepts the {@link WebDriver.TargetLocator} actions with customized handlers.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class InterceptingTargetLocator implements WebDriver.TargetLocator {

    private final WebDriver.TargetLocator targetLocator;
    private final InterceptingHandler handler;

    /**
     * Constructs an instance with target delegated {@link WebDriver.TargetLocator} instance.
     *
     * @param targetLocator the target locator to delegate
     * @param handler the handler for before, after and on exception actions.
     */
    public InterceptingTargetLocator(WebDriver.TargetLocator targetLocator, InterceptingHandler handler) {
        requireNonNull(targetLocator);
        requireNonNull(handler);
        this.targetLocator = targetLocator;
        this.handler = handler;
    }

    @Override
    public WebDriver frame(int index) {
        return handler.execute(() -> targetLocator.frame(index),
                MethodInfo.create(targetLocator, TARGETLOCATOR_FRAME, index));
    }

    @Override
    public WebDriver frame(String nameOrId) {
        return handler.execute(() -> targetLocator.frame(nameOrId),
                MethodInfo.create(targetLocator, TARGETLOCATOR_FRAME, nameOrId));
    }

    @Override
    public WebDriver frame(WebElement frameElement) {
        return handler.execute(() -> targetLocator.frame(frameElement),
                MethodInfo.create(targetLocator, TARGETLOCATOR_FRAME, frameElement));
    }

    @Override
    public WebDriver parentFrame() {
        return handler
                .execute(targetLocator::parentFrame, MethodInfo.create(targetLocator, TARGETLOCATOR_PARENT_FRAME));
    }

    @Override
    public WebDriver window(String nameOrHandle) {
        return handler.execute(() -> targetLocator.window(nameOrHandle),
                MethodInfo.create(targetLocator, TARGETLOCATOR_WINDOW, nameOrHandle));
    }

    @Override
    public WebDriver newWindow(WindowType typeHint) {
        return handler.execute(() -> targetLocator.newWindow(typeHint),
                MethodInfo.create(targetLocator, TARGETLOCATOR_NEW_WINDOW, typeHint));
    }

    @Override
    public WebDriver defaultContent() {
        return handler.execute(targetLocator::defaultContent,
                MethodInfo.create(targetLocator, TARGETLOCATOR_DEFAULT_CONTENT));
    }

    @Override
    public WebElement activeElement() {
        return handler
                .execute(targetLocator::activeElement, MethodInfo.create(targetLocator, TARGETLOCATOR_ACTIVE_ELEMENT));
    }

    @Override
    public Alert alert() {
        return new InterceptingAlert(targetLocator.alert(), handler);
    }

}
