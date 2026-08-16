/*
 * Copyright © 2025 the original author or authors.
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

package com.github.grossopa.playwright.core;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.SelectOption;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 *
 *
 * @author Jack Yin
 * @since 1.12
 */
public abstract class AbstractDelegatedLocator implements Locator {

    protected final Locator locator;

    protected AbstractDelegatedLocator(Locator locator) {
        requireNonNull(locator);
        this.locator = locator;
    }

    @Override
    public List<Locator> all() {
        return locator.all();
    }

    @Override
    public List<String> allInnerTexts() {
        return locator.allInnerTexts();
    }

    @Override
    public List<String> allTextContents() {
        return locator.allTextContents();
    }

    @Override
    public void blur() {
        locator.blur();
    }

    @Override
    public void blur(BlurOptions options) {
        locator.blur(options);
    }

    @Override
    public BoundingBox boundingBox() {
        return locator.boundingBox();
    }

    @Override
    public BoundingBox boundingBox(BoundingBoxOptions options) {
        return locator.boundingBox(options);
    }

    @Override
    public void check() {
        locator.check();
    }

    @Override
    public void check(CheckOptions options) {
        locator.check(options);
    }

    @Override
    public void clear() {
        locator.clear();
    }

    @Override
    public void clear(ClearOptions options) {
        locator.clear(options);
    }

    @Override
    public void click() {
        locator.click();
    }

    @Override
    public void click(ClickOptions options) {
        locator.click(options);
    }

    @Override
    public int count() {
        return locator.count();
    }

    @Override
    public void dblclick() {
        locator.dblclick();
    }

    @Override
    public void dblclick(DblclickOptions options) {
        locator.dblclick(options);
    }

    @Override
    public void dispatchEvent(String type, Object eventInit) {
        locator.dispatchEvent(type, eventInit);
    }

    @Override
    public void dispatchEvent(String type) {
        locator.dispatchEvent(type);
    }

    @Override
    public void dispatchEvent(String type, Object eventInit, DispatchEventOptions options) {
        locator.dispatchEvent(type, eventInit, options);
    }

    @Override
    public void dragTo(Locator target) {
        locator.dragTo(target);
    }

    @Override
    public void dragTo(Locator target, DragToOptions options) {
        locator.dragTo(target, options);
    }

    @Override
    public ElementHandle elementHandle() {
        return locator.elementHandle();
    }

    @Override
    public ElementHandle elementHandle(ElementHandleOptions options) {
        return locator.elementHandle(options);
    }

    @Override
    public List<ElementHandle> elementHandles() {
        return locator.elementHandles();
    }

    @Override
    public Object evaluate(String expression, Object arg) {
        return locator.evaluate(expression, arg);
    }

    @Override
    public Object evaluate(String expression) {
        return locator.evaluate(expression);
    }

    @Override
    public Object evaluate(String expression, Object arg, EvaluateOptions options) {
        return locator.evaluate(expression, arg, options);
    }

    @Override
    public Object evaluateAll(String expression) {
        return locator.evaluateAll(expression);
    }

    @Override
    public Object evaluateAll(String expression, Object arg) {
        return locator.evaluateAll(expression, arg);
    }

    @Override
    public JSHandle evaluateHandle(String expression, Object arg) {
        return locator.evaluateHandle(expression, arg);
    }

    @Override
    public JSHandle evaluateHandle(String expression) {
        return locator.evaluateHandle(expression);
    }

    @Override
    public JSHandle evaluateHandle(String expression, Object arg, EvaluateHandleOptions options) {
        return locator.evaluateHandle(expression, arg, options);
    }

    @Override
    public void fill(String value) {
        locator.fill(value);
    }

    @Override
    public void fill(String value, FillOptions options) {
        locator.fill(value, options);
    }

    @Override
    public Locator filter() {
        return locator.filter();
    }

    @Override
    public Locator filter(FilterOptions options) {
        return locator.filter(options);
    }

    @Override
    public Locator first() {
        return locator.first();
    }

    @Override
    public void focus() {
        locator.focus();
    }

    @Override
    public void focus(FocusOptions options) {
        locator.focus(options);
    }

    @Override
    public FrameLocator frameLocator(String selector) {
        return locator.frameLocator(selector);
    }

    @Override
    public String getAttribute(String name) {
        return locator.getAttribute(name);
    }

    @Override
    public String getAttribute(String name, GetAttributeOptions options) {
        return locator.getAttribute(name, options);
    }

    @Override
    public Locator getByAltText(String text) {
        return locator.getByAltText(text);
    }

    @Override
    public Locator getByAltText(String text, GetByAltTextOptions options) {
        return locator.getByAltText(text, options);
    }

    @Override
    public Locator getByAltText(Pattern text) {
        return locator.getByAltText(text);
    }

    @Override
    public Locator getByAltText(Pattern text, GetByAltTextOptions options) {
        return locator.getByAltText(text, options);
    }

    @Override
    public Locator getByLabel(String text) {
        return locator.getByLabel(text);
    }

    @Override
    public Locator getByLabel(String text, GetByLabelOptions options) {
        return locator.getByLabel(text, options);
    }

    @Override
    public Locator getByLabel(Pattern text) {
        return locator.getByLabel(text);
    }

    @Override
    public Locator getByLabel(Pattern text, GetByLabelOptions options) {
        return locator.getByLabel(text, options);
    }

    @Override
    public Locator getByPlaceholder(String text) {
        return locator.getByPlaceholder(text);
    }

    @Override
    public Locator getByPlaceholder(String text, GetByPlaceholderOptions options) {
        return locator.getByPlaceholder(text, options);
    }

    @Override
    public Locator getByPlaceholder(Pattern text) {
        return locator.getByPlaceholder(text);
    }

    @Override
    public Locator getByPlaceholder(Pattern text, GetByPlaceholderOptions options) {
        return locator.getByPlaceholder(text, options);
    }

    @Override
    public Locator getByRole(AriaRole role) {
        return locator.getByRole(role);
    }

    @Override
    public Locator getByRole(AriaRole role, GetByRoleOptions options) {
        return locator.getByRole(role, options);
    }

    @Override
    public Locator getByTestId(String testId) {
        return locator.getByTestId(testId);
    }

    @Override
    public Locator getByTestId(Pattern testId) {
        return locator.getByTestId(testId);
    }

    @Override
    public Locator getByText(String text) {
        return locator.getByText(text);
    }

    @Override
    public Locator getByText(String text, GetByTextOptions options) {
        return locator.getByText(text, options);
    }

    @Override
    public Locator getByText(Pattern text) {
        return locator.getByText(text);
    }

    @Override
    public Locator getByText(Pattern text, GetByTextOptions options) {
        return locator.getByText(text, options);
    }

    @Override
    public Locator getByTitle(String text) {
        return locator.getByTitle(text);
    }

    @Override
    public Locator getByTitle(String text, GetByTitleOptions options) {
        return locator.getByTitle(text, options);
    }

    @Override
    public Locator getByTitle(Pattern text) {
        return locator.getByTitle(text);
    }

    @Override
    public Locator getByTitle(Pattern text, GetByTitleOptions options) {
        return locator.getByTitle(text, options);
    }

    @Override
    public void highlight() {
        locator.highlight();
    }

    @Override
    public void hover() {
        locator.hover();
    }

    @Override
    public void hover(HoverOptions options) {
        locator.hover(options);
    }

    @Override
    public String innerHTML() {
        return locator.innerHTML();
    }

    @Override
    public String innerHTML(InnerHTMLOptions options) {
        return locator.innerHTML(options);
    }

    @Override
    public String innerText() {
        return locator.innerText();
    }

    @Override
    public String innerText(InnerTextOptions options) {
        return locator.innerText(options);
    }

    @Override
    public String inputValue() {
        return locator.inputValue();
    }

    @Override
    public String inputValue(InputValueOptions options) {
        return locator.inputValue(options);
    }

    @Override
    public boolean isChecked() {
        return locator.isChecked();
    }

    @Override
    public boolean isChecked(IsCheckedOptions options) {
        return locator.isChecked(options);
    }

    @Override
    public boolean isDisabled() {
        return locator.isDisabled();
    }

    @Override
    public boolean isDisabled(IsDisabledOptions options) {
        return locator.isDisabled(options);
    }

    @Override
    public boolean isEditable() {
        return locator.isEditable();
    }

    @Override
    public boolean isEditable(IsEditableOptions options) {
        return locator.isEditable(options);
    }

    @Override
    public boolean isEnabled() {
        return locator.isEnabled();
    }

    @Override
    public boolean isEnabled(IsEnabledOptions options) {
        return locator.isEnabled(options);
    }

    @Override
    public boolean isHidden() {
        return locator.isHidden();
    }

    @Override
    public boolean isHidden(IsHiddenOptions options) {
        return locator.isHidden(options);
    }

    @Override
    public boolean isVisible() {
        return locator.isVisible();
    }

    @Override
    public boolean isVisible(IsVisibleOptions options) {
        return locator.isVisible(options);
    }

    @Override
    public Locator last() {
        return locator.last();
    }

    @Override
    public Locator locator(String selector) {
        return locator.locator(selector);
    }

    @Override
    public Locator locator(String selector, LocatorOptions options) {
        return locator.locator(selector, options);
    }

    @Override
    public Locator nth(int index) {
        return locator.nth(index);
    }

    @Override
    public Page page() {
        return locator.page();
    }

    @Override
    public void press(String key) {
        locator.press(key);
    }

    @Override
    public void press(String key, PressOptions options) {
        locator.press(key, options);
    }

    @Override
    public byte[] screenshot() {
        return locator.screenshot();
    }

    @Override
    public byte[] screenshot(ScreenshotOptions options) {
        return locator.screenshot(options);
    }

    @Override
    public void scrollIntoViewIfNeeded() {
        locator.scrollIntoViewIfNeeded();
    }

    @Override
    public void scrollIntoViewIfNeeded(ScrollIntoViewIfNeededOptions options) {
        locator.scrollIntoViewIfNeeded(options);
    }

    @Override
    public List<String> selectOption(String values) {
        return locator.selectOption(values);
    }

    @Override
    public List<String> selectOption(String values, SelectOptionOptions options) {
        return locator.selectOption(values, options);
    }

    @Override
    public List<String> selectOption(ElementHandle values) {
        return locator.selectOption(values);
    }

    @Override
    public List<String> selectOption(ElementHandle values, SelectOptionOptions options) {
        return locator.selectOption(values, options);
    }

    @Override
    public List<String> selectOption(String[] values) {
        return locator.selectOption(values);
    }

    @Override
    public List<String> selectOption(String[] values, SelectOptionOptions options) {
        return locator.selectOption(values, options);
    }

    @Override
    public List<String> selectOption(SelectOption values) {
        return locator.selectOption(values);
    }

    @Override
    public List<String> selectOption(SelectOption values, SelectOptionOptions options) {
        return locator.selectOption(values, options);
    }

    @Override
    public List<String> selectOption(ElementHandle[] values) {
        return locator.selectOption(values);
    }

    @Override
    public List<String> selectOption(ElementHandle[] values, SelectOptionOptions options) {
        return locator.selectOption(values, options);
    }

    @Override
    public List<String> selectOption(SelectOption[] values) {
        return locator.selectOption(values);
    }

    @Override
    public List<String> selectOption(SelectOption[] values, SelectOptionOptions options) {
        return locator.selectOption(values, options);
    }

    @Override
    public void selectText() {
        locator.selectText();
    }

    @Override
    public void selectText(SelectTextOptions options) {
        locator.selectText(options);
    }

    @Override
    public void setChecked(boolean checked) {
        locator.setChecked(checked);
    }

    @Override
    public void setChecked(boolean checked, SetCheckedOptions options) {
        locator.setChecked(checked, options);
    }

    @Override
    public void setInputFiles(Path files) {
        locator.setInputFiles(files);
    }

    @Override
    public void setInputFiles(Path files, SetInputFilesOptions options) {
        locator.setInputFiles(files, options);
    }

    @Override
    public void setInputFiles(Path[] files) {
        locator.setInputFiles(files);
    }

    @Override
    public void setInputFiles(Path[] files, SetInputFilesOptions options) {
        locator.setInputFiles(files, options);
    }

    @Override
    public void setInputFiles(FilePayload files) {
        locator.setInputFiles(files);
    }

    @Override
    public void setInputFiles(FilePayload files, SetInputFilesOptions options) {
        locator.setInputFiles(files, options);
    }

    @Override
    public void setInputFiles(FilePayload[] files) {
        locator.setInputFiles(files);
    }

    @Override
    public void setInputFiles(FilePayload[] files, SetInputFilesOptions options) {
        locator.setInputFiles(files, options);
    }

    @Override
    public void tap() {
        locator.tap();
    }

    @Override
    public void tap(TapOptions options) {
        locator.tap(options);
    }

    @Override
    public String textContent() {
        return locator.textContent();
    }

    @Override
    public String textContent(TextContentOptions options) {
        return locator.textContent(options);
    }

    @Override
    public void type(String text) {
        locator.type(text);
    }

    @Override
    public void type(String text, TypeOptions options) {
        locator.type(text, options);
    }

    @Override
    public void uncheck() {
        locator.uncheck();
    }

    @Override
    public void uncheck(UncheckOptions options) {
        locator.uncheck(options);
    }

    @Override
    public void waitFor() {
        locator.waitFor();
    }

    @Override
    public void waitFor(WaitForOptions options) {
        locator.waitFor(options);
    }

    @Override
    public Locator and(Locator locator) {
        return this.locator.and(locator);
    }

    @Override
    public String ariaSnapshot() {
        return locator.ariaSnapshot();
    }

    @Override
    public String ariaSnapshot(AriaSnapshotOptions options) {
        return locator.ariaSnapshot(options);
    }

    @Override
    public FrameLocator contentFrame() {
        return locator.contentFrame();
    }

    @Override
    public Locator locator(Locator selectorOrLocator) {
        return locator.locator(selectorOrLocator);
    }

    @Override
    public Locator locator(Locator selectorOrLocator, LocatorOptions options) {
        return locator.locator(selectorOrLocator, options);
    }

    @Override
    public Locator or(Locator locator) {
        return this.locator.or(locator);
    }

    @Override
    public void pressSequentially(String text) {
        locator.pressSequentially(text);
    }

    @Override
    public void pressSequentially(String text, PressSequentiallyOptions options) {
        locator.pressSequentially(text, options);
    }
}
