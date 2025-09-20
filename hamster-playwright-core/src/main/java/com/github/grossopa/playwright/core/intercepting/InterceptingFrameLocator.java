/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.core.intercepting;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

import static com.github.grossopa.playwright.core.intercepting.InterceptingMethods.*;
import static java.util.Objects.requireNonNull;

/**
 * Intercepting the FrameLocator actions with customized handlers.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class InterceptingFrameLocator implements FrameLocator {

    private final FrameLocator frameLocator;
    private final InterceptingHandler handler;

    /**
     * Constructs an instance with target delegated {@link FrameLocator} instance.
     *
     * @param frameLocator the frame locator to delegate
     * @param handler the handler for before, after and on exception actions.
     */
    public InterceptingFrameLocator(FrameLocator frameLocator, InterceptingHandler handler) {
        requireNonNull(frameLocator);
        requireNonNull(handler);
        this.frameLocator = frameLocator;
        this.handler = handler;
    }

    @Override
    public Locator locator(String selector) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.locator(selector), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_LOCATOR, selector));
    }

    @Override
    public Locator locator(String selector, LocatorOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.locator(selector, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_LOCATOR, selector, options));
    }

    @Override
    public Locator locator(Locator selector, LocatorOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.locator(selector, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_LOCATOR, selector, options));
    }

    @Override
    public FrameLocator frameLocator(String selector) {
        return handler.execute(() -> new InterceptingFrameLocator(frameLocator.frameLocator(selector), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_FRAME_LOCATOR, selector));
    }

    @Override
    public Locator getByAltText(String text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByAltText(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_ALT_TEXT, text));
    }

    @Override
    public Locator getByAltText(String text, GetByAltTextOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByAltText(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_ALT_TEXT, text, options));
    }

    @Override
    public Locator getByAltText(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByAltText(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_ALT_TEXT, text));
    }

    @Override
    public Locator getByAltText(Pattern text, GetByAltTextOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByAltText(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_ALT_TEXT, text, options));
    }

    @Override
    public Locator getByLabel(String text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByLabel(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_LABEL, text));
    }

    @Override
    public Locator getByLabel(String text, GetByLabelOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByLabel(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_LABEL, text, options));
    }

    @Override
    public Locator getByLabel(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByLabel(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_LABEL, text));
    }

    @Override
    public Locator getByLabel(Pattern text, GetByLabelOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByLabel(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_LABEL, text, options));
    }

    @Override
    public Locator getByPlaceholder(String text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByPlaceholder(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_PLACEHOLDER, text));
    }

    @Override
    public Locator getByPlaceholder(String text, GetByPlaceholderOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByPlaceholder(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_PLACEHOLDER, text, options));
    }

    @Override
    public Locator getByPlaceholder(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByPlaceholder(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_PLACEHOLDER, text));
    }

    @Override
    public Locator getByPlaceholder(Pattern text, GetByPlaceholderOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByPlaceholder(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_PLACEHOLDER, text, options));
    }

    @Override
    public Locator getByRole(AriaRole role) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByRole(role), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_ROLE, role));
    }

    @Override
    public Locator getByRole(AriaRole role, GetByRoleOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByRole(role, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_ROLE, role, options));
    }

    @Override
    public Locator getByTestId(String testId) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByTestId(testId), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TEST_ID, testId));
    }

    @Override
    public Locator getByTestId(Pattern testId) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByTestId(testId), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TEST_ID, testId));
    }

    @Override
    public Locator getByText(String text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByText(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TEXT, text));
    }

    @Override
    public Locator getByText(String text, GetByTextOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByText(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TEXT, text, options));
    }

    @Override
    public Locator getByText(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByText(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TEXT, text));
    }

    @Override
    public Locator getByText(Pattern text, GetByTextOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByText(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TEXT, text, options));
    }

    @Override
    public Locator getByTitle(String text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByTitle(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TITLE, text));
    }

    @Override
    public Locator getByTitle(String text, GetByTitleOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByTitle(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TITLE, text, options));
    }

    @Override
    public Locator getByTitle(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByTitle(text), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TITLE, text));
    }

    @Override
    public Locator getByTitle(Pattern text, GetByTitleOptions options) {
        return handler.execute(() -> new InterceptingLocator(frameLocator.getByTitle(text, options), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_GET_BY_TITLE, text, options));
    }

    @Deprecated
    @Override
    public FrameLocator first() {
        return handler.execute(() -> new InterceptingFrameLocator(frameLocator.first(), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_FIRST));
    }

    @Deprecated
    @Override
    public FrameLocator last() {
        return handler.execute(() -> new InterceptingFrameLocator(frameLocator.last(), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_LAST));
    }

    @Deprecated
    @Override
    public FrameLocator nth(int index) {
        return handler.execute(() -> new InterceptingFrameLocator(frameLocator.nth(index), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_NTH, index));
    }

    @Override
    public Locator owner() {
        return handler.execute(() -> new InterceptingLocator(frameLocator.owner(), handler),
                MethodInfo.create(frameLocator, FRAME_LOCATOR_OWNER));
    }
}