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
package com.github.grossopa.playwright.core;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.DropPayload;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static org.mockito.Mockito.*;

class AbstractDelegatedLocatorTest {

    /**
     * A minimal concrete subclass that does NOT override any methods from AbstractDelegatedLocator,
     * so that the delegated implementations are tested directly.
     */
    static class TestDelegatedLocator extends AbstractDelegatedLocator {
        TestDelegatedLocator(Locator locator) {
            super(locator);
        }
    }

    private Locator locator;
    private TestDelegatedLocator testSubject;

    @BeforeEach
    void setUp() {
        locator = mock(Locator.class);
        testSubject = new TestDelegatedLocator(locator);
    }

    @Test
    void testAll() {
        testSubject.all();
        verify(locator).all();
    }

    @Test
    void testAllInnerTexts() {
        testSubject.allInnerTexts();
        verify(locator).allInnerTexts();
    }

    @Test
    void testAllTextContents() {
        testSubject.allTextContents();
        verify(locator).allTextContents();
    }

    @Test
    void testBlur() {
        testSubject.blur();
        verify(locator).blur();
    }

    @Test
    void testBlurWithOptions() {
        testSubject.blur(new Locator.BlurOptions());
        verify(locator).blur(any(Locator.BlurOptions.class));
    }

    @Test
    void testBoundingBox() {
        testSubject.boundingBox();
        verify(locator).boundingBox();
    }

    @Test
    void testBoundingBoxWithOptions() {
        testSubject.boundingBox(new Locator.BoundingBoxOptions());
        verify(locator).boundingBox(any(Locator.BoundingBoxOptions.class));
    }

    @Test
    void testCheck() {
        testSubject.check();
        verify(locator).check();
    }

    @Test
    void testCheckWithOptions() {
        testSubject.check(new Locator.CheckOptions());
        verify(locator).check(any(Locator.CheckOptions.class));
    }

    @Test
    void testClear() {
        testSubject.clear();
        verify(locator).clear();
    }

    @Test
    void testClearWithOptions() {
        testSubject.clear(new Locator.ClearOptions());
        verify(locator).clear(any(Locator.ClearOptions.class));
    }

    @Test
    void testClickWithOptions() {
        testSubject.click(new Locator.ClickOptions());
        verify(locator).click(any(Locator.ClickOptions.class));
    }

    @Test
    void testClick() {
        testSubject.click();
        verify(locator).click();
    }

    @Test
    void testFill() {
        testSubject.fill("value");
        verify(locator).fill("value");
    }

    @Test
    void testGetAttribute() {
        testSubject.getAttribute("name");
        verify(locator).getAttribute("name");
    }

    @Test
    void testInnerHTML() {
        testSubject.innerHTML();
        verify(locator).innerHTML();
    }

    @Test
    void testInnerText() {
        testSubject.innerText();
        verify(locator).innerText();
    }

    @Test
    void testIsVisible() {
        testSubject.isVisible();
        verify(locator).isVisible();
    }

    @Test
    void testIsDisabled() {
        testSubject.isDisabled();
        verify(locator).isDisabled();
    }

    @Test
    void testIsEnabled() {
        testSubject.isEnabled();
        verify(locator).isEnabled();
    }

    @Test
    void testTextContent() {
        testSubject.textContent();
        verify(locator).textContent();
    }

    @Test
    void testCount() {
        testSubject.count();
        verify(locator).count();
    }

    @Test
    void testDblclick() {
        testSubject.dblclick();
        verify(locator).dblclick();
    }

    @Test
    void testDblclickWithOptions() {
        testSubject.dblclick(new Locator.DblclickOptions());
        verify(locator).dblclick(any(Locator.DblclickOptions.class));
    }

    @Test
    void testDispatchEventTwoArgs() {
        testSubject.dispatchEvent("click", null);
        verify(locator).dispatchEvent("click", null);
    }

    @Test
    void testDispatchEventOneArg() {
        testSubject.dispatchEvent("click");
        verify(locator).dispatchEvent("click");
    }

    @Test
    void testDispatchEventThreeArgs() {
        testSubject.dispatchEvent("click", null, null);
        verify(locator).dispatchEvent("click", null, null);
    }

    @Test
    void testDragTo() {
        Locator target = mock(Locator.class);
        testSubject.dragTo(target);
        verify(locator).dragTo(target);
    }

    @Test
    void testDragToWithOptions() {
        Locator target = mock(Locator.class);
        testSubject.dragTo(target, new Locator.DragToOptions());
        verify(locator).dragTo(eq(target), any(Locator.DragToOptions.class));
    }

    @Test
    void testElementHandle() {
        testSubject.elementHandle();
        verify(locator).elementHandle();
    }

    @Test
    void testElementHandleWithOptions() {
        testSubject.elementHandle(new Locator.ElementHandleOptions());
        verify(locator).elementHandle(any(Locator.ElementHandleOptions.class));
    }

    @Test
    void testElementHandles() {
        testSubject.elementHandles();
        verify(locator).elementHandles();
    }

    @Test
    void testEvaluateTwoArgs() {
        testSubject.evaluate("expr", null);
        verify(locator).evaluate("expr", null);
    }

    @Test
    void testEvaluateOneArg() {
        testSubject.evaluate("expr");
        verify(locator).evaluate("expr");
    }

    @Test
    void testEvaluateThreeArgs() {
        testSubject.evaluate("expr", null, null);
        verify(locator).evaluate("expr", null, null);
    }

    @Test
    void testEvaluateAllOneArg() {
        testSubject.evaluateAll("expr");
        verify(locator).evaluateAll("expr");
    }

    @Test
    void testEvaluateAllTwoArgs() {
        testSubject.evaluateAll("expr", null);
        verify(locator).evaluateAll("expr", null);
    }

    @Test
    void testEvaluateHandleTwoArgs() {
        testSubject.evaluateHandle("expr", null);
        verify(locator).evaluateHandle("expr", null);
    }

    @Test
    void testEvaluateHandleOneArg() {
        testSubject.evaluateHandle("expr");
        verify(locator).evaluateHandle("expr");
    }

    @Test
    void testEvaluateHandleThreeArgs() {
        testSubject.evaluateHandle("expr", null, null);
        verify(locator).evaluateHandle("expr", null, null);
    }

    @Test
    void testFillWithOptions() {
        testSubject.fill("v", new Locator.FillOptions());
        verify(locator).fill(eq("v"), any(Locator.FillOptions.class));
    }

    @Test
    void testFilter() {
        testSubject.filter();
        verify(locator).filter();
    }

    @Test
    void testFilterWithOptions() {
        testSubject.filter(new Locator.FilterOptions());
        verify(locator).filter(any(Locator.FilterOptions.class));
    }

    @Test
    void testFirst() {
        testSubject.first();
        verify(locator).first();
    }

    @Test
    void testFocus() {
        testSubject.focus();
        verify(locator).focus();
    }

    @Test
    void testFocusWithOptions() {
        testSubject.focus(new Locator.FocusOptions());
        verify(locator).focus(any(Locator.FocusOptions.class));
    }

    @Test
    void testFrameLocator() {
        testSubject.frameLocator("iframe");
        verify(locator).frameLocator("iframe");
    }

    @Test
    void testGetAttributeWithOptions() {
        testSubject.getAttribute("name", new Locator.GetAttributeOptions());
        verify(locator).getAttribute(eq("name"), any(Locator.GetAttributeOptions.class));
    }

    @Test
    void testGetByAltTextString() {
        testSubject.getByAltText("alt");
        verify(locator).getByAltText("alt");
    }

    @Test
    void testGetByAltTextStringWithOptions() {
        testSubject.getByAltText("alt", new Locator.GetByAltTextOptions());
        verify(locator).getByAltText(eq("alt"), any(Locator.GetByAltTextOptions.class));
    }

    @Test
    void testGetByAltTextPattern() {
        testSubject.getByAltText(Pattern.compile("alt"));
        verify(locator).getByAltText(any(Pattern.class));
    }

    @Test
    void testGetByAltTextPatternWithOptions() {
        testSubject.getByAltText(Pattern.compile("alt"), new Locator.GetByAltTextOptions());
        verify(locator).getByAltText(any(Pattern.class), any(Locator.GetByAltTextOptions.class));
    }

    @Test
    void testGetByLabelString() {
        testSubject.getByLabel("label");
        verify(locator).getByLabel("label");
    }

    @Test
    void testGetByLabelStringWithOptions() {
        testSubject.getByLabel("label", new Locator.GetByLabelOptions());
        verify(locator).getByLabel(eq("label"), any(Locator.GetByLabelOptions.class));
    }

    @Test
    void testGetByLabelPattern() {
        testSubject.getByLabel(Pattern.compile("label"));
        verify(locator).getByLabel(any(Pattern.class));
    }

    @Test
    void testGetByLabelPatternWithOptions() {
        testSubject.getByLabel(Pattern.compile("label"), new Locator.GetByLabelOptions());
        verify(locator).getByLabel(any(Pattern.class), any(Locator.GetByLabelOptions.class));
    }

    @Test
    void testGetByPlaceholderString() {
        testSubject.getByPlaceholder("ph");
        verify(locator).getByPlaceholder("ph");
    }

    @Test
    void testGetByPlaceholderStringWithOptions() {
        testSubject.getByPlaceholder("ph", new Locator.GetByPlaceholderOptions());
        verify(locator).getByPlaceholder(eq("ph"), any(Locator.GetByPlaceholderOptions.class));
    }

    @Test
    void testGetByPlaceholderPattern() {
        testSubject.getByPlaceholder(Pattern.compile("ph"));
        verify(locator).getByPlaceholder(any(Pattern.class));
    }

    @Test
    void testGetByPlaceholderPatternWithOptions() {
        testSubject.getByPlaceholder(Pattern.compile("ph"), new Locator.GetByPlaceholderOptions());
        verify(locator).getByPlaceholder(any(Pattern.class), any(Locator.GetByPlaceholderOptions.class));
    }

    @Test
    void testGetByRole() {
        testSubject.getByRole(AriaRole.BUTTON);
        verify(locator).getByRole(AriaRole.BUTTON);
    }

    @Test
    void testGetByRoleWithOptions() {
        testSubject.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions());
        verify(locator).getByRole(eq(AriaRole.BUTTON), any(Locator.GetByRoleOptions.class));
    }

    @Test
    void testGetByTestIdString() {
        testSubject.getByTestId("id");
        verify(locator).getByTestId("id");
    }

    @Test
    void testGetByTestIdPattern() {
        testSubject.getByTestId(Pattern.compile("id"));
        verify(locator).getByTestId(any(Pattern.class));
    }

    @Test
    void testGetByTextString() {
        testSubject.getByText("text");
        verify(locator).getByText("text");
    }

    @Test
    void testGetByTextStringWithOptions() {
        testSubject.getByText("text", new Locator.GetByTextOptions());
        verify(locator).getByText(eq("text"), any(Locator.GetByTextOptions.class));
    }

    @Test
    void testGetByTextPattern() {
        testSubject.getByText(Pattern.compile("text"));
        verify(locator).getByText(any(Pattern.class));
    }

    @Test
    void testGetByTextPatternWithOptions() {
        testSubject.getByText(Pattern.compile("text"), new Locator.GetByTextOptions());
        verify(locator).getByText(any(Pattern.class), any(Locator.GetByTextOptions.class));
    }

    @Test
    void testGetByTitleString() {
        testSubject.getByTitle("title");
        verify(locator).getByTitle("title");
    }

    @Test
    void testGetByTitleStringWithOptions() {
        testSubject.getByTitle("title", new Locator.GetByTitleOptions());
        verify(locator).getByTitle(eq("title"), any(Locator.GetByTitleOptions.class));
    }

    @Test
    void testGetByTitlePattern() {
        testSubject.getByTitle(Pattern.compile("title"));
        verify(locator).getByTitle(any(Pattern.class));
    }

    @Test
    void testGetByTitlePatternWithOptions() {
        testSubject.getByTitle(Pattern.compile("title"), new Locator.GetByTitleOptions());
        verify(locator).getByTitle(any(Pattern.class), any(Locator.GetByTitleOptions.class));
    }

    @Test
    void testHighlight() {
        testSubject.highlight();
        verify(locator).highlight();
    }

    @Test
    void testHoverWithOptions() {
        testSubject.hover(new Locator.HoverOptions());
        verify(locator).hover(any(Locator.HoverOptions.class));
    }

    @Test
    void testInnerHTMLOptions() {
        testSubject.innerHTML(new Locator.InnerHTMLOptions());
        verify(locator).innerHTML(any(Locator.InnerHTMLOptions.class));
    }

    @Test
    void testInnerTextOptions() {
        testSubject.innerText(new Locator.InnerTextOptions());
        verify(locator).innerText(any(Locator.InnerTextOptions.class));
    }

    @Test
    void testInputValue() {
        testSubject.inputValue();
        verify(locator).inputValue();
    }

    @Test
    void testInputValueWithOptions() {
        testSubject.inputValue(new Locator.InputValueOptions());
        verify(locator).inputValue(any(Locator.InputValueOptions.class));
    }

    @Test
    void testIsChecked() {
        testSubject.isChecked();
        verify(locator).isChecked();
    }

    @Test
    void testIsCheckedWithOptions() {
        testSubject.isChecked(new Locator.IsCheckedOptions());
        verify(locator).isChecked(any(Locator.IsCheckedOptions.class));
    }

    @Test
    void testIsDisabledWithOptions() {
        testSubject.isDisabled(new Locator.IsDisabledOptions());
        verify(locator).isDisabled(any(Locator.IsDisabledOptions.class));
    }

    @Test
    void testIsEditable() {
        testSubject.isEditable();
        verify(locator).isEditable();
    }

    @Test
    void testIsEditableWithOptions() {
        testSubject.isEditable(new Locator.IsEditableOptions());
        verify(locator).isEditable(any(Locator.IsEditableOptions.class));
    }

    @Test
    void testIsEnabledWithOptions() {
        testSubject.isEnabled(new Locator.IsEnabledOptions());
        verify(locator).isEnabled(any(Locator.IsEnabledOptions.class));
    }

    @Test
    void testIsHidden() {
        testSubject.isHidden();
        verify(locator).isHidden();
    }

    @Test
    void testIsHiddenWithOptions() {
        testSubject.isHidden(new Locator.IsHiddenOptions());
        verify(locator).isHidden(any(Locator.IsHiddenOptions.class));
    }

    @Test
    void testIsVisibleWithOptions() {
        testSubject.isVisible(new Locator.IsVisibleOptions());
        verify(locator).isVisible(any(Locator.IsVisibleOptions.class));
    }

    @Test
    void testLast() {
        testSubject.last();
        verify(locator).last();
    }

    @Test
    void testLocatorString() {
        testSubject.locator(".sel");
        verify(locator).locator(".sel");
    }

    @Test
    void testLocatorStringWithOptions() {
        testSubject.locator(".sel", new Locator.LocatorOptions());
        verify(locator).locator(eq(".sel"), any(Locator.LocatorOptions.class));
    }

    @Test
    void testNth() {
        testSubject.nth(3);
        verify(locator).nth(3);
    }

    @Test
    void testPage() {
        testSubject.page();
        verify(locator).page();
    }

    @Test
    void testPress() {
        testSubject.press("Enter");
        verify(locator).press("Enter");
    }

    @Test
    void testPressWithOptions() {
        testSubject.press("Enter", new Locator.PressOptions());
        verify(locator).press(eq("Enter"), any(Locator.PressOptions.class));
    }

    @Test
    void testScreenshot() {
        testSubject.screenshot();
        verify(locator).screenshot();
    }

    @Test
    void testScreenshotWithOptions() {
        testSubject.screenshot(new Locator.ScreenshotOptions());
        verify(locator).screenshot(any(Locator.ScreenshotOptions.class));
    }

    @Test
    void testScrollIntoViewIfNeeded() {
        testSubject.scrollIntoViewIfNeeded();
        verify(locator).scrollIntoViewIfNeeded();
    }

    @Test
    void testScrollIntoViewIfNeededWithOptions() {
        testSubject.scrollIntoViewIfNeeded(new Locator.ScrollIntoViewIfNeededOptions());
        verify(locator).scrollIntoViewIfNeeded(any(Locator.ScrollIntoViewIfNeededOptions.class));
    }

    @Test
    void testSelectOptionString() {
        testSubject.selectOption("val");
        verify(locator).selectOption("val");
    }

    @Test
    void testSelectOptionStringWithOptions() {
        testSubject.selectOption("val", new Locator.SelectOptionOptions());
        verify(locator).selectOption(eq("val"), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionElementHandle() {
        ElementHandle eh = mock(ElementHandle.class);
        testSubject.selectOption(eh);
        verify(locator).selectOption(eh);
    }

    @Test
    void testSelectOptionElementHandleWithOptions() {
        ElementHandle eh = mock(ElementHandle.class);
        testSubject.selectOption(eh, new Locator.SelectOptionOptions());
        verify(locator).selectOption(eq(eh), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionStringArray() {
        testSubject.selectOption(new String[]{"a"});
        verify(locator).selectOption((String[]) any());
    }

    @Test
    void testSelectOptionStringArrayWithOptions() {
        testSubject.selectOption(new String[]{"a"}, new Locator.SelectOptionOptions());
        verify(locator).selectOption((String[]) any(), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionSelectOption() {
        SelectOption so = new SelectOption();
        testSubject.selectOption(so);
        verify(locator).selectOption(so);
    }

    @Test
    void testSelectOptionSelectOptionWithOptions() {
        SelectOption so = new SelectOption();
        testSubject.selectOption(so, new Locator.SelectOptionOptions());
        verify(locator).selectOption(eq(so), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionElementHandleArray() {
        testSubject.selectOption(new ElementHandle[]{mock(ElementHandle.class)});
        verify(locator).selectOption((ElementHandle[]) any());
    }

    @Test
    void testSelectOptionElementHandleArrayWithOptions() {
        testSubject.selectOption(new ElementHandle[]{mock(ElementHandle.class)}, new Locator.SelectOptionOptions());
        verify(locator).selectOption((ElementHandle[]) any(), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionSelectOptionArray() {
        testSubject.selectOption(new SelectOption[]{new SelectOption()});
        verify(locator).selectOption((SelectOption[]) any());
    }

    @Test
    void testSelectOptionSelectOptionArrayWithOptions() {
        testSubject.selectOption(new SelectOption[]{new SelectOption()}, new Locator.SelectOptionOptions());
        verify(locator).selectOption((SelectOption[]) any(), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectText() {
        testSubject.selectText();
        verify(locator).selectText();
    }

    @Test
    void testSelectTextWithOptions() {
        testSubject.selectText(new Locator.SelectTextOptions());
        verify(locator).selectText(any(Locator.SelectTextOptions.class));
    }

    @Test
    void testSetChecked() {
        testSubject.setChecked(true);
        verify(locator).setChecked(true);
    }

    @Test
    void testSetCheckedWithOptions() {
        testSubject.setChecked(true, new Locator.SetCheckedOptions());
        verify(locator).setChecked(eq(true), any(Locator.SetCheckedOptions.class));
    }

    @Test
    void testSetInputFilesPath() {
        testSubject.setInputFiles(Paths.get("/tmp/f"));
        verify(locator).setInputFiles(Paths.get("/tmp/f"));
    }

    @Test
    void testSetInputFilesPathWithOptions() {
        testSubject.setInputFiles(Paths.get("/tmp/f"), new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles(eq(Paths.get("/tmp/f")), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testSetInputFilesPathArray() {
        testSubject.setInputFiles(new Path[]{Paths.get("/tmp/f")});
        verify(locator).setInputFiles((Path[]) any());
    }

    @Test
    void testSetInputFilesPathArrayWithOptions() {
        testSubject.setInputFiles(new Path[]{Paths.get("/tmp/f")}, new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles((Path[]) any(), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testSetInputFilesFilePayload() {
        FilePayload fp = new FilePayload("f", "text/plain", new byte[0]);
        testSubject.setInputFiles(fp);
        verify(locator).setInputFiles(fp);
    }

    @Test
    void testSetInputFilesFilePayloadWithOptions() {
        FilePayload fp = new FilePayload("f", "text/plain", new byte[0]);
        testSubject.setInputFiles(fp, new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles(eq(fp), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testSetInputFilesFilePayloadArray() {
        testSubject.setInputFiles(new FilePayload[]{new FilePayload("f", "text/plain", new byte[0])});
        verify(locator).setInputFiles((FilePayload[]) any());
    }

    @Test
    void testSetInputFilesFilePayloadArrayWithOptions() {
        testSubject.setInputFiles(new FilePayload[]{new FilePayload("f", "text/plain", new byte[0])}, new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles((FilePayload[]) any(), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testTap() {
        testSubject.tap();
        verify(locator).tap();
    }

    @Test
    void testTapWithOptions() {
        testSubject.tap(new Locator.TapOptions());
        verify(locator).tap(any(Locator.TapOptions.class));
    }

    @Test
    void testTextContentWithOptions() {
        testSubject.textContent(new Locator.TextContentOptions());
        verify(locator).textContent(any(Locator.TextContentOptions.class));
    }

    @Test
    void testType() {
        testSubject.type("t");
        verify(locator).type("t");
    }

    @Test
    void testTypeWithOptions() {
        testSubject.type("t", new Locator.TypeOptions());
        verify(locator).type(eq("t"), any(Locator.TypeOptions.class));
    }

    @Test
    void testUncheck() {
        testSubject.uncheck();
        verify(locator).uncheck();
    }

    @Test
    void testUncheckWithOptions() {
        testSubject.uncheck(new Locator.UncheckOptions());
        verify(locator).uncheck(any(Locator.UncheckOptions.class));
    }

    @Test
    void testWaitFor() {
        testSubject.waitFor();
        verify(locator).waitFor();
    }

    @Test
    void testWaitForWithOptions() {
        testSubject.waitFor(new Locator.WaitForOptions());
        verify(locator).waitFor(any(Locator.WaitForOptions.class));
    }

    @Test
    void testAnd() {
        Locator other = mock(Locator.class);
        testSubject.and(other);
        verify(locator).and(other);
    }

    @Test
    void testAriaSnapshot() {
        testSubject.ariaSnapshot();
        verify(locator).ariaSnapshot();
    }

    @Test
    void testAriaSnapshotWithOptions() {
        testSubject.ariaSnapshot(new Locator.AriaSnapshotOptions());
        verify(locator).ariaSnapshot(any(Locator.AriaSnapshotOptions.class));
    }

    @Test
    void testContentFrame() {
        testSubject.contentFrame();
        verify(locator).contentFrame();
    }

    @Test
    void testLocatorWithLocator() {
        Locator other = mock(Locator.class);
        testSubject.locator(other);
        verify(locator).locator(other);
    }

    @Test
    void testLocatorWithLocatorAndOptions() {
        Locator other = mock(Locator.class);
        testSubject.locator(other, new Locator.LocatorOptions());
        verify(locator).locator(eq(other), any(Locator.LocatorOptions.class));
    }

    @Test
    void testOr() {
        Locator other = mock(Locator.class);
        testSubject.or(other);
        verify(locator).or(other);
    }

    @Test
    void testPressSequentially() {
        testSubject.pressSequentially("text");
        verify(locator).pressSequentially("text");
    }

    @Test
    void testPressSequentiallyWithOptions() {
        testSubject.pressSequentially("text", new Locator.PressSequentiallyOptions());
        verify(locator).pressSequentially(eq("text"), any(Locator.PressSequentiallyOptions.class));
    }

    // =====================================================================
    // Playwright 1.64.0 new methods
    // =====================================================================

    @Test
    void testHighlightWithOptions() {
        testSubject.highlight(new Locator.HighlightOptions());
        verify(locator).highlight(any(Locator.HighlightOptions.class));
    }

    @Test
    void testWithin() {
        Locator inner = mock(Locator.class);
        testSubject.within(inner);
        verify(locator).within(inner);
    }

    @Test
    void testWaitForFunctionExpressionOnly() {
        testSubject.waitForFunction("expr");
        verify(locator).waitForFunction("expr");
    }

    @Test
    void testWaitForFunctionTwoArgs() {
        testSubject.waitForFunction("expr", null);
        verify(locator).waitForFunction("expr", null);
    }

    @Test
    void testWaitForFunctionThreeArgs() {
        testSubject.waitForFunction("expr", null, new Locator.WaitForFunctionOptions());
        verify(locator).waitForFunction(eq("expr"), isNull(), any(Locator.WaitForFunctionOptions.class));
    }

    @Test
    void testVisible() {
        testSubject.visible();
        verify(locator).visible();
    }

    @Test
    void testNormalize() {
        testSubject.normalize();
        verify(locator).normalize();
    }

    @Test
    void testHideHighlight() {
        testSubject.hideHighlight();
        verify(locator).hideHighlight();
    }

    @Test
    void testDrop() {
        DropPayload payload = new DropPayload();
        testSubject.drop(payload);
        verify(locator).drop(payload);
    }

    @Test
    void testDropWithOptions() {
        DropPayload payload = new DropPayload();
        testSubject.drop(payload, new Locator.DropOptions());
        verify(locator).drop(eq(payload), any(Locator.DropOptions.class));
    }

    @Test
    void testDescription() {
        testSubject.description();
        verify(locator).description();
    }

    @Test
    void testDescribe() {
        testSubject.describe("name");
        verify(locator).describe("name");
    }

    // =====================================================================
    // Previously missing overload
    // =====================================================================

    @Test
    void testHover() {
        testSubject.hover();
        verify(locator).hover();
    }
}
