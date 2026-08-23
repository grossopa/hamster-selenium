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
package com.github.grossopa.playwright.core.intercepting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.FilePayload;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import static com.github.grossopa.playwright.core.intercepting.InterceptingMethods.*;
import static java.util.Objects.requireNonNull;

/**
 * Intercepting the Locator actions with customized handlers.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class InterceptingLocator implements Locator {

    private final Locator locator;
    private final InterceptingHandler handler;

    /**
     * Constructs an instance with target delegated {@link Locator} instance.
     *
     * @param locator the locator to delegate
     * @param handler the handler for before, after and on exception actions.
     */
    public InterceptingLocator(Locator locator, InterceptingHandler handler) {
        requireNonNull(locator);
        requireNonNull(handler);
        this.locator = locator;
        this.handler = handler;
    }

    @Override
    public void click(ClickOptions options) {
        handler.execute(() -> {
            locator.click(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_CLICK, options));
    }

    @Override
    public void click() {
        handler.execute(() -> {
            locator.click();
            return null;
        }, MethodInfo.create(locator, LOCATOR_CLICK));
    }

    @Override
    public void dblclick(DblclickOptions options) {
        handler.execute(() -> {
            locator.dblclick(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_DBLCLICK, options));
    }

    @Override
    public void dblclick() {
        handler.execute(() -> {
            locator.dblclick();
            return null;
        }, MethodInfo.create(locator, LOCATOR_DBLCLICK));
    }

    @Override
    public void dragTo(Locator target, DragToOptions options) {
        handler.execute(() -> {
            locator.dragTo(target, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_DRAG_TO, target, options));
    }

    @Override
    public void dragTo(Locator target) {
        handler.execute(() -> {
            locator.dragTo(target);
            return null;
        }, MethodInfo.create(locator, LOCATOR_DRAG_TO, target));
    }

    @Override
    public void fill(String value, FillOptions options) {
        handler.execute(() -> {
            locator.fill(value, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_FILL, value, options));
    }

    @Override
    public void fill(String value) {
        handler.execute(() -> {
            locator.fill(value);
            return null;
        }, MethodInfo.create(locator, LOCATOR_FILL, value));
    }

    @Override
    public void hover(HoverOptions options) {
        handler.execute(() -> {
            locator.hover(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_HOVER, options));
    }

    @Override
    public void hover() {
        handler.execute(() -> {
            locator.hover();
            return null;
        }, MethodInfo.create(locator, LOCATOR_HOVER));
    }

    @Override
    public String innerHTML(InnerHTMLOptions options) {
        return handler.execute(() -> locator.innerHTML(options),
                MethodInfo.create(locator, LOCATOR_INNER_HTML, options));
    }

    @Override
    public String innerHTML() {
        return handler.execute(locator::innerHTML,
                MethodInfo.create(locator, LOCATOR_INNER_HTML));
    }

    @Override
    public String innerText(InnerTextOptions options) {
        return handler.execute(() -> locator.innerText(options),
                MethodInfo.create(locator, LOCATOR_INNER_TEXT, options));
    }

    @Override
    public String innerText() {
        return handler.execute(locator::innerText,
                MethodInfo.create(locator, LOCATOR_INNER_TEXT));
    }

    @Override
    public boolean isDisabled(IsDisabledOptions options) {
        return handler.execute(() -> locator.isDisabled(options),
                MethodInfo.create(locator, LOCATOR_IS_DISABLED, options));
    }

    @Override
    public boolean isDisabled() {
        return handler.execute(locator::isDisabled,
                MethodInfo.create(locator, LOCATOR_IS_DISABLED));
    }

    @Override
    public boolean isEnabled(IsEnabledOptions options) {
        return handler.execute(() -> locator.isEnabled(options),
                MethodInfo.create(locator, LOCATOR_IS_ENABLED, options));
    }

    @Override
    public boolean isEnabled() {
        return handler.execute(locator::isEnabled,
                MethodInfo.create(locator, LOCATOR_IS_ENABLED));
    }

    @Override
    public boolean isVisible(IsVisibleOptions options) {
        return handler.execute(() -> locator.isVisible(options),
                MethodInfo.create(locator, LOCATOR_IS_VISIBLE, options));
    }

    @Override
    public boolean isVisible() {
        return handler.execute(locator::isVisible,
                MethodInfo.create(locator, LOCATOR_IS_VISIBLE));
    }

    @Override
    public String textContent(TextContentOptions options) {
        return handler.execute(() -> locator.textContent(options),
                MethodInfo.create(locator, LOCATOR_TEXT_CONTENT, options));
    }

    @Override
    public String textContent() {
        return handler.execute(locator::textContent,
                MethodInfo.create(locator, LOCATOR_TEXT_CONTENT));
    }

    @Override
    public String getAttribute(String name, GetAttributeOptions options) {
        return handler.execute(() -> locator.getAttribute(name, options),
                MethodInfo.create(locator, LOCATOR_GET_ATTRIBUTE, name, options));
    }

    @Override
    public String getAttribute(String name) {
        return handler.execute(() -> locator.getAttribute(name),
                MethodInfo.create(locator, LOCATOR_GET_ATTRIBUTE, name));
    }

    @Override
    public List<String> selectOption(String[] values, SelectOptionOptions options) {
        return handler.execute(() -> locator.selectOption(values, options),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, values, options));
    }

    @Override
    public List<String> selectOption(String[] values) {
        return handler.execute(() -> locator.selectOption(values),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, (Object) values));
    }

    @Override
    public List<String> selectOption(ElementHandle[] values, SelectOptionOptions options) {
        return handler.execute(() -> locator.selectOption(values, options),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, values, options));
    }

    @Override
    public List<String> selectOption(ElementHandle[] values) {
        return handler.execute(() -> locator.selectOption(values),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, (Object) values));
    }

    @Override
    public BoundingBox boundingBox(BoundingBoxOptions options) {
        return handler.execute(() -> locator.boundingBox(options),
                MethodInfo.create(locator, LOCATOR_BOUNDING_BOX, options));
    }

    @Override
    public BoundingBox boundingBox() {
        return handler.execute(locator::boundingBox,
                MethodInfo.create(locator, LOCATOR_BOUNDING_BOX));
    }

    @Override
    public void check(CheckOptions options) {
        handler.execute(() -> {
            locator.check(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_CHECK, options));
    }

    @Override
    public void check() {
        handler.execute(() -> {
            locator.check();
            return null;
        }, MethodInfo.create(locator, LOCATOR_CHECK));
    }

    @Override
    public void focus(FocusOptions options) {
        handler.execute(() -> {
            locator.focus(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_FOCUS, options));
    }

    @Override
    public void focus() {
        handler.execute(() -> {
            locator.focus();
            return null;
        }, MethodInfo.create(locator, LOCATOR_FOCUS));
    }

    @Override
    public void blur(BlurOptions options) {
        handler.execute(() -> {
            locator.blur(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_BLUR, options));
    }

    @Override
    public void blur() {
        handler.execute(() -> {
            locator.blur();
            return null;
        }, MethodInfo.create(locator, LOCATOR_BLUR));
    }

    @Override
    public void press(String key, PressOptions options) {
        handler.execute(() -> {
            locator.press(key, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_PRESS, key, options));
    }

    @Override
    public void pressSequentially(String text, PressSequentiallyOptions options) {
        handler.execute(() -> {
            locator.pressSequentially(text, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_PRESS_SEQUENTIALLY, text, options));
    }

    @Override
    public void press(String key) {
        handler.execute(() -> {
            locator.press(key);
            return null;
        }, MethodInfo.create(locator, LOCATOR_PRESS, key));
    }

    @Override
    public String inputValue(InputValueOptions options) {
        return handler.execute(() -> locator.inputValue(options),
                MethodInfo.create(locator, LOCATOR_INPUT_VALUE, options));
    }

    @Override
    public String inputValue() {
        return handler.execute(locator::inputValue,
                MethodInfo.create(locator, LOCATOR_INPUT_VALUE));
    }

    @Override
    public boolean isChecked(IsCheckedOptions options) {
        return handler.execute(() -> locator.isChecked(options),
                MethodInfo.create(locator, LOCATOR_IS_CHECKED, options));
    }

    @Override
    public boolean isChecked() {
        return handler.execute(locator::isChecked,
                MethodInfo.create(locator, LOCATOR_IS_CHECKED));
    }

    @Override
    public boolean isEditable(IsEditableOptions options) {
        return handler.execute(() -> locator.isEditable(options),
                MethodInfo.create(locator, LOCATOR_IS_EDITABLE, options));
    }

    @Override
    public boolean isEditable() {
        return handler.execute(locator::isEditable,
                MethodInfo.create(locator, LOCATOR_IS_EDITABLE));
    }

    @Override
    public List<String> selectOption(ElementHandle value, SelectOptionOptions options) {
        return handler.execute(() -> locator.selectOption(value, options),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, value, options));
    }

    @Override
    public List<String> selectOption(ElementHandle value) {
        return handler.execute(() -> locator.selectOption(value),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, value));
    }

    @Override
    public List<String> selectOption(String value, SelectOptionOptions options) {
        return handler.execute(() -> locator.selectOption(value, options),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, value, options));
    }

    @Override
    public List<String> selectOption(String value) {
        return handler.execute(() -> locator.selectOption(value),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, value));
    }

    @Override
    public List<String> selectOption(com.microsoft.playwright.options.SelectOption[] values, SelectOptionOptions options) {
        return handler.execute(() -> locator.selectOption(values, options),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, values, options));
    }

    @Override
    public List<String> selectOption(com.microsoft.playwright.options.SelectOption[] values) {
        return handler.execute(() -> locator.selectOption(values),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, (Object) values));
    }

    @Override
    public List<String> selectOption(com.microsoft.playwright.options.SelectOption value, SelectOptionOptions options) {
        return handler.execute(() -> locator.selectOption(value, options),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, value, options));
    }

    @Override
    public List<String> selectOption(com.microsoft.playwright.options.SelectOption value) {
        return handler.execute(() -> locator.selectOption(value),
                MethodInfo.create(locator, LOCATOR_SELECT_OPTION, value));
    }

    @Override
    public void selectText(SelectTextOptions options) {
        handler.execute(() -> {
            locator.selectText(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SELECT_TEXT, options));
    }

    @Override
    public void selectText() {
        handler.execute(() -> {
            locator.selectText();
            return null;
        }, MethodInfo.create(locator, LOCATOR_SELECT_TEXT));
    }

    @Override
    public void setChecked(boolean checked, SetCheckedOptions options) {
        handler.execute(() -> {
            locator.setChecked(checked, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_CHECKED, checked, options));
    }

    @Override
    public void setChecked(boolean checked) {
        handler.execute(() -> {
            locator.setChecked(checked);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_CHECKED, checked));
    }

    @Override
    public void setInputFiles(Path file, SetInputFilesOptions options) {
        handler.execute(() -> {
            locator.setInputFiles(file, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_INPUT_FILES, file, options));
    }

    @Override
    public void setInputFiles(Path file) {
        handler.execute(() -> {
            locator.setInputFiles(file);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_INPUT_FILES, file));
    }

    @Override
    public void setInputFiles(Path[] files, SetInputFilesOptions options) {
        handler.execute(() -> {
            locator.setInputFiles(files, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_INPUT_FILES, files, options));
    }

    @Override
    public void setInputFiles(Path[] files) {
        handler.execute(() -> {
            locator.setInputFiles(files);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_INPUT_FILES, (Object) files));
    }

    @Override
    public void setInputFiles(FilePayload files, SetInputFilesOptions options) {
        handler.execute(() -> {
            locator.setInputFiles(files, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_INPUT_FILES, files, options));
    }

    @Override
    public void setInputFiles(FilePayload files) {
        handler.execute(() -> {
            locator.setInputFiles(files);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_INPUT_FILES, files));
    }

    @Override
    public void setInputFiles(FilePayload[] files, SetInputFilesOptions options) {
        handler.execute(() -> {
            locator.setInputFiles(files, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_INPUT_FILES, files, options));
    }

    @Override
    public void setInputFiles(FilePayload[] files) {
        handler.execute(() -> {
            locator.setInputFiles(files);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SET_INPUT_FILES, (Object) files));
    }

    @Override
    public void tap(TapOptions options) {
        handler.execute(() -> {
            locator.tap(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_TAP, options));
    }

    @Override
    public void tap() {
        handler.execute(() -> {
            locator.tap();
            return null;
        }, MethodInfo.create(locator, LOCATOR_TAP));
    }

    /**
     * @deprecated use {@link #fill(String, FillOptions)} instead.
     */
    @Deprecated(since = "1.12.0")
    @Override
    public void type(String text, TypeOptions options) {
        handler.execute(() -> {
            locator.type(text, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_TYPE, text, options));
    }

    /**
     * @deprecated use {@link #fill(String)} instead.
     */
    @Deprecated(since = "1.12.0")
    @Override
    public void type(String text) {
        handler.execute(() -> {
            locator.type(text);
            return null;
        }, MethodInfo.create(locator, LOCATOR_TYPE, text));
    }

    @Override
    public void uncheck(UncheckOptions options) {
        handler.execute(() -> {
            locator.uncheck(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_UNCHECK, options));
    }

    @Override
    public void uncheck() {
        handler.execute(() -> {
            locator.uncheck();
            return null;
        }, MethodInfo.create(locator, LOCATOR_UNCHECK));
    }

    @Override
    public void clear(ClearOptions options) {
        handler.execute(() -> {
            locator.clear(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_CLEAR, options));
    }

    @Override
    public void clear() {
        handler.execute(() -> {
            locator.clear();
            return null;
        }, MethodInfo.create(locator, LOCATOR_CLEAR));
    }

    @Override
    public void dispatchEvent(String type, Object eventInit, DispatchEventOptions options) {
        handler.execute(() -> {
            locator.dispatchEvent(type, eventInit, options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_DISPATCH_EVENT, type, eventInit, options));
    }

    @Override
    public void dispatchEvent(String type, Object eventInit) {
        handler.execute(() -> {
            locator.dispatchEvent(type, eventInit);
            return null;
        }, MethodInfo.create(locator, LOCATOR_DISPATCH_EVENT, type, eventInit));
    }

    @Override
    public Object evaluate(String expression, Object arg, EvaluateOptions options) {
        return handler.execute(() -> locator.evaluate(expression, arg, options),
                MethodInfo.create(locator, LOCATOR_EVALUATE, expression, arg, options));
    }

    @Override
    public Object evaluate(String expression, Object arg) {
        return handler.execute(() -> locator.evaluate(expression, arg),
                MethodInfo.create(locator, LOCATOR_EVALUATE, expression, arg));
    }

    @Override
    public Object evaluate(String expression) {
        return handler.execute(() -> locator.evaluate(expression),
                MethodInfo.create(locator, LOCATOR_EVALUATE, expression));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> evaluateAll(String expression, Object arg) {
        return (List<Object>) handler.execute(() -> locator.evaluateAll(expression, arg),
                MethodInfo.create(locator, LOCATOR_EVALUATE_ALL, expression, arg));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> evaluateAll(String expression) {
        return (List<Object>) handler.execute(() -> locator.evaluateAll(expression),
                MethodInfo.create(locator, LOCATOR_EVALUATE_ALL, expression));
    }

    @Override
    public Locator filter(FilterOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.filter(options), handler),
                MethodInfo.create(locator, LOCATOR_FILTER, options));
    }

    @Override
    public Locator first() {
        return handler.execute(() -> new InterceptingLocator(locator.first(), handler),
                MethodInfo.create(locator, LOCATOR_FIRST));
    }

    @Override
    public FrameLocator frameLocator(String frameSelector) {
        return handler.execute(() -> new InterceptingFrameLocator(locator.frameLocator(frameSelector), handler),
                MethodInfo.create(locator, LOCATOR_FRAME_LOCATOR));
    }

    @Override
    public Locator last() {
        return handler.execute(() -> new InterceptingLocator(locator.last(), handler),
                MethodInfo.create(locator, LOCATOR_LAST));
    }

    @Override
    public Locator nth(int index) {
        return handler.execute(() -> new InterceptingLocator(this.locator.nth(index), handler),
                MethodInfo.create(locator, LOCATOR_OR, index));
    }

    @Override
    public Locator or(Locator other) {
        return handler.execute(() -> new InterceptingLocator(this.locator.or(other), handler),
                MethodInfo.create(locator, LOCATOR_OR, other));
    }

    @Override
    public Locator locator(String selector, LocatorOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.locator(selector, options), handler),
                MethodInfo.create(locator, LOCATOR_LOCATOR, selector, options));
    }

    @Override
    public Locator locator(Locator selectorOrLocator, LocatorOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.locator(selectorOrLocator, options), handler),
                MethodInfo.create(locator, LOCATOR_LOCATOR, selectorOrLocator, options));
    }

    @Override
    public Locator locator(String selector) {
        return handler.execute(() -> new InterceptingLocator(locator.locator(selector), handler),
                MethodInfo.create(locator, LOCATOR_LOCATOR, selector));
    }

    @Override
    public Locator getByAltText(String text, GetByAltTextOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByAltText(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_ALT_TEXT, text, options));
    }

    @Override
    public Locator getByAltText(String text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByAltText(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_ALT_TEXT, text));
    }

    @Override
    public Locator getByAltText(Pattern text, GetByAltTextOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByAltText(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_ALT_TEXT, text, options));
    }

    @Override
    public Locator getByAltText(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByAltText(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_ALT_TEXT, text));
    }

    @Override
    public Locator getByLabel(String text, GetByLabelOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByLabel(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_LABEL, text, options));
    }

    @Override
    public Locator getByLabel(String text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByLabel(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_LABEL, text));
    }

    @Override
    public Locator getByLabel(Pattern text, GetByLabelOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByLabel(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_LABEL, text, options));
    }

    @Override
    public Locator getByLabel(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByLabel(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_LABEL, text));
    }

    @Override
    public Locator getByPlaceholder(String text, GetByPlaceholderOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByPlaceholder(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_PLACEHOLDER, text, options));
    }

    @Override
    public Locator getByPlaceholder(String text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByPlaceholder(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_PLACEHOLDER, text));
    }

    @Override
    public Locator getByPlaceholder(Pattern text, GetByPlaceholderOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByPlaceholder(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_PLACEHOLDER, text, options));
    }

    @Override
    public Locator getByPlaceholder(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByPlaceholder(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_PLACEHOLDER, text));
    }

    @Override
    public Locator getByRole(AriaRole role, GetByRoleOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByRole(role, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_ROLE, role, options));
    }

    @Override
    public Locator getByRole(AriaRole role) {
        return handler.execute(() -> new InterceptingLocator(locator.getByRole(role), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_ROLE, role));
    }

    @Override
    public Locator getByTestId(String testId) {
        return handler.execute(() -> new InterceptingLocator(locator.getByTestId(testId), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TEST_ID, testId));
    }

    @Override
    public Locator getByTestId(Pattern testId) {
        return handler.execute(() -> new InterceptingLocator(locator.getByTestId(testId), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TEST_ID, testId));
    }

    @Override
    public Locator getByText(String text, GetByTextOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByText(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TEXT, text, options));
    }

    @Override
    public Locator getByText(String text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByText(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TEXT, text));
    }

    @Override
    public Locator getByText(Pattern text, GetByTextOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByText(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TEXT, text, options));
    }

    @Override
    public Locator getByText(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByText(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TEXT, text));
    }

    @Override
    public Locator getByTitle(String text, GetByTitleOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByTitle(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TITLE, text, options));
    }

    @Override
    public Locator getByTitle(String text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByTitle(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TITLE, text));
    }

    @Override
    public Locator getByTitle(Pattern text, GetByTitleOptions options) {
        return handler.execute(() -> new InterceptingLocator(locator.getByTitle(text, options), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TITLE, text, options));
    }

    @Override
    public Locator getByTitle(Pattern text) {
        return handler.execute(() -> new InterceptingLocator(locator.getByTitle(text), handler),
                MethodInfo.create(locator, LOCATOR_GET_BY_TITLE, text));
    }

    @Override
    public List<Locator> all() {
        return handler.execute(() -> locator.all().stream().<Locator>map(l -> new InterceptingLocator(l, handler))
                        .toList(),
                MethodInfo.create(locator, LOCATOR_ALL));
    }

    @Override
    public void highlight() {
        handler.execute(() -> {
            locator.highlight();
            return null;
        }, MethodInfo.create(locator, LOCATOR_HIGHLIGHT));
    }

    @Override
    public byte[] screenshot(ScreenshotOptions options) {
        return handler.execute(() -> locator.screenshot(options),
                MethodInfo.create(locator, LOCATOR_SCREENSHOT, options));
    }

    @Override
    public byte[] screenshot() {
        return handler.execute(locator::screenshot,
                MethodInfo.create(locator, LOCATOR_SCREENSHOT));
    }

    @Override
    public String toString() {
        return locator.toString();
    }

    @Override
    public int count() {
        return locator.count();
    }

    @Override
    public List<String> allInnerTexts() {
        return handler.execute(locator::allInnerTexts,
                MethodInfo.create(locator, LOCATOR_ALL_INNER_TEXTS));
    }

    @Override
    public List<String> allTextContents() {
        return handler.execute(locator::allTextContents,
                MethodInfo.create(locator, LOCATOR_ALL_TEXT_CONTENTS));
    }

    @Override
    public Locator and(Locator other) {
        return handler.execute(() -> new InterceptingLocator(locator.and(other), handler),
                MethodInfo.create(locator, LOCATOR_AND, other));
    }

    @Override
    public String ariaSnapshot(AriaSnapshotOptions options) {
        return handler.execute(() -> locator.ariaSnapshot(options),
                MethodInfo.create(locator, LOCATOR_ARIA_SNAPSHOT, options));
    }

    @Override
    public ElementHandle elementHandle(ElementHandleOptions options) {
        return handler.execute(() -> locator.elementHandle(options),
                MethodInfo.create(locator, LOCATOR_ELEMENT_HANDLE, options));
    }

    @Override
    public ElementHandle elementHandle() {
        return handler.execute(locator::elementHandle,
                MethodInfo.create(locator, LOCATOR_ELEMENT_HANDLE));
    }

    @Override
    public List<ElementHandle> elementHandles() {
        return handler.execute(locator::elementHandles,
                MethodInfo.create(locator, LOCATOR_ELEMENT_HANDLES));
    }

    @Override
    public FrameLocator contentFrame() {
        return handler.execute(() -> new InterceptingFrameLocator(locator.contentFrame(), handler),
            MethodInfo.create(locator, LOCATOR_CONTENT_FRAME));
    }

    @Override
    public JSHandle evaluateHandle(String expression, Object arg, EvaluateHandleOptions options) {
        return handler.execute(() -> locator.evaluateHandle(expression, arg, options),
                MethodInfo.create(locator, LOCATOR_EVALUATE_HANDLE, expression, arg, options));
    }

    @Override
    public JSHandle evaluateHandle(String expression, Object arg) {
        return handler.execute(() -> locator.evaluateHandle(expression, arg),
                MethodInfo.create(locator, LOCATOR_EVALUATE_HANDLE, expression, arg));
    }

    @Override
    public JSHandle evaluateHandle(String expression) {
        return handler.execute(() -> locator.evaluateHandle(expression),
                MethodInfo.create(locator, LOCATOR_EVALUATE_HANDLE, expression));
    }

    @Override
    public void scrollIntoViewIfNeeded(ScrollIntoViewIfNeededOptions options) {
        handler.execute(() -> {
            locator.scrollIntoViewIfNeeded(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_SCROLL_INTO_VIEW_IF_NEEDED, options));
    }

    @Override
    public void scrollIntoViewIfNeeded() {
        handler.execute(() -> {
            locator.scrollIntoViewIfNeeded();
            return null;
        }, MethodInfo.create(locator, LOCATOR_SCROLL_INTO_VIEW_IF_NEEDED));
    }

    @Override
    public void waitFor(WaitForOptions options) {
        handler.execute(() -> {
            locator.waitFor(options);
            return null;
        }, MethodInfo.create(locator, LOCATOR_WAIT_FOR, options));
    }

    @Override
    public void waitFor() {
        handler.execute(() -> {
            locator.waitFor();
            return null;
        }, MethodInfo.create(locator, LOCATOR_WAIT_FOR));
    }

    @Override
    public Page page() {
        //TODO
        return locator.page();
    }

    @Override
    public boolean isHidden(IsHiddenOptions options) {
        return handler.execute(() -> locator.isHidden(options),
                MethodInfo.create(locator, LOCATOR_IS_HIDDEN, options));
    }

    @Override
    public boolean isHidden() {
        return handler.execute(locator::isHidden,
                MethodInfo.create(locator, LOCATOR_IS_HIDDEN));
    }
}
