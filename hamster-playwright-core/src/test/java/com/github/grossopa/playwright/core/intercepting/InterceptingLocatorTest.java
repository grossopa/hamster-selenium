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

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterceptingLocatorTest {

    private Locator locator;
    private InterceptingHandler handler;
    private InterceptingLocator interceptingLocator;

    @BeforeEach
    void setUp() {
        locator = mock(Locator.class);
        handler = mock(InterceptingHandler.class);
        when(handler.execute(any(), any())).thenAnswer(invocation -> {
            java.util.function.Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        interceptingLocator = new InterceptingLocator(locator, handler);
    }

    @Test
    void testConstructorWithNullLocator() {
        assertThrows(NullPointerException.class, () -> new InterceptingLocator(null, handler));
    }

    @Test
    void testConstructorWithNullHandler() {
        assertThrows(NullPointerException.class, () -> new InterceptingLocator(locator, null));
    }

    @Test
    void testClick() {
        interceptingLocator.click();
        verify(locator).click();
    }

    @Test
    void testClickWithOptions() {
        Locator.ClickOptions options = new Locator.ClickOptions();
        interceptingLocator.click(options);
        verify(locator).click(options);
    }

    @Test
    void testDblclick() {
        interceptingLocator.dblclick();
        verify(locator).dblclick();
    }

    @Test
    void testFill() {
        interceptingLocator.fill("value");
        verify(locator).fill("value");
    }

    @Test
    void testHover() {
        interceptingLocator.hover();
        verify(locator).hover();
    }

    @Test
    void testInnerHTML() {
        when(locator.innerHTML()).thenReturn("<div>test</div>");
        assertEquals("<div>test</div>", interceptingLocator.innerHTML());
    }

    @Test
    void testInnerText() {
        when(locator.innerText()).thenReturn("text");
        assertEquals("text", interceptingLocator.innerText());
    }

    @Test
    void testTextContent() {
        when(locator.textContent()).thenReturn("content");
        assertEquals("content", interceptingLocator.textContent());
    }

    @Test
    void testGetAttribute() {
        when(locator.getAttribute("class")).thenReturn("my-class");
        assertEquals("my-class", interceptingLocator.getAttribute("class"));
    }

    @Test
    void testIsVisible() {
        when(locator.isVisible()).thenReturn(true);
        assertTrue(interceptingLocator.isVisible());
    }

    @Test
    void testIsEnabled() {
        when(locator.isEnabled()).thenReturn(true);
        assertTrue(interceptingLocator.isEnabled());
    }

    @Test
    void testIsDisabled() {
        when(locator.isDisabled()).thenReturn(true);
        assertTrue(interceptingLocator.isDisabled());
    }

    @Test
    void testFirst() {
        Locator firstLocator = mock(Locator.class);
        when(locator.first()).thenReturn(firstLocator);
        Locator result = interceptingLocator.first();
        assertNotNull(result);
        assertInstanceOf(InterceptingLocator.class, result);
    }

    @Test
    void testLast() {
        Locator lastLocator = mock(Locator.class);
        when(locator.last()).thenReturn(lastLocator);
        Locator result = interceptingLocator.last();
        assertNotNull(result);
        assertInstanceOf(InterceptingLocator.class, result);
    }

    @Test
    void testNth() {
        Locator nthLocator = mock(Locator.class);
        when(locator.nth(2)).thenReturn(nthLocator);
        Locator result = interceptingLocator.nth(2);
        assertNotNull(result);
        assertInstanceOf(InterceptingLocator.class, result);
    }

    @Test
    void testLocator() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(".child")).thenReturn(childLocator);
        Locator result = interceptingLocator.locator(".child");
        assertNotNull(result);
        assertInstanceOf(InterceptingLocator.class, result);
    }

    @Test
    void testFrameLocator() {
        FrameLocator frameLocator = mock(FrameLocator.class);
        when(locator.frameLocator("iframe")).thenReturn(frameLocator);
        FrameLocator result = interceptingLocator.frameLocator("iframe");
        assertNotNull(result);
        assertInstanceOf(InterceptingFrameLocator.class, result);
    }

    @Test
    void testAll() {
        Locator loc1 = mock(Locator.class);
        Locator loc2 = mock(Locator.class);
        when(locator.all()).thenReturn(List.of(loc1, loc2));
        List<Locator> result = interceptingLocator.all();
        assertEquals(2, result.size());
        assertInstanceOf(InterceptingLocator.class, result.get(0));
    }

    @Test
    void testFilter() {
        Locator filterLocator = mock(Locator.class);
        when(locator.filter(any())).thenReturn(filterLocator);
        Locator result = interceptingLocator.filter(null);
        assertNotNull(result);
        assertInstanceOf(InterceptingLocator.class, result);
    }

    @Test
    void testOr() {
        Locator otherLocator = mock(Locator.class);
        when(locator.or(otherLocator)).thenReturn(otherLocator);
        Locator result = interceptingLocator.or(otherLocator);
        assertNotNull(result);
    }

    @Test
    void testAnd() {
        Locator otherLocator = mock(Locator.class);
        when(locator.and(otherLocator)).thenReturn(otherLocator);
        Locator result = interceptingLocator.and(otherLocator);
        assertNotNull(result);
    }

    @Test
    void testGetByAltText() {
        Locator altLocator = mock(Locator.class);
        when(locator.getByAltText("alt")).thenReturn(altLocator);
        Locator result = interceptingLocator.getByAltText("alt");
        assertNotNull(result);
    }

    @Test
    void testGetByLabel() {
        Locator labelLocator = mock(Locator.class);
        when(locator.getByLabel("label")).thenReturn(labelLocator);
        Locator result = interceptingLocator.getByLabel("label");
        assertNotNull(result);
    }

    @Test
    void testGetByPlaceholder() {
        Locator placeholderLocator = mock(Locator.class);
        when(locator.getByPlaceholder("placeholder")).thenReturn(placeholderLocator);
        Locator result = interceptingLocator.getByPlaceholder("placeholder");
        assertNotNull(result);
    }

    @Test
    void testGetByRole() {
        Locator roleLocator = mock(Locator.class);
        when(locator.getByRole(AriaRole.BUTTON)).thenReturn(roleLocator);
        Locator result = interceptingLocator.getByRole(AriaRole.BUTTON);
        assertNotNull(result);
    }

    @Test
    void testGetByText() {
        Locator textLocator = mock(Locator.class);
        when(locator.getByText("text")).thenReturn(textLocator);
        Locator result = interceptingLocator.getByText("text");
        assertNotNull(result);
    }

    @Test
    void testGetByTitle() {
        Locator titleLocator = mock(Locator.class);
        when(locator.getByTitle("title")).thenReturn(titleLocator);
        Locator result = interceptingLocator.getByTitle("title");
        assertNotNull(result);
    }

    @Test
    void testGetByTestId() {
        Locator testIdLocator = mock(Locator.class);
        when(locator.getByTestId("test-id")).thenReturn(testIdLocator);
        Locator result = interceptingLocator.getByTestId("test-id");
        assertNotNull(result);
    }

    @Test
    void testCheck() {
        interceptingLocator.check();
        verify(locator).check();
    }

    @Test
    void testUncheck() {
        interceptingLocator.uncheck();
        verify(locator).uncheck();
    }

    @Test
    void testFocus() {
        interceptingLocator.focus();
        verify(locator).focus();
    }

    @Test
    void testBlur() {
        interceptingLocator.blur();
        verify(locator).blur();
    }

    @Test
    void testPress() {
        interceptingLocator.press("Enter");
        verify(locator).press("Enter");
    }

    @Test
    @SuppressWarnings({"java:S5738", "deprecation"})
    void testType() {
        interceptingLocator.type("text");
        verify(locator).type("text");
    }

    @Test
    void testClear() {
        interceptingLocator.clear();
        verify(locator).clear();
    }

    @Test
    void testSelectOption() {
        interceptingLocator.selectOption("value");
        verify(locator).selectOption("value");
    }

    @Test
    void testInputValue() {
        when(locator.inputValue()).thenReturn("input");
        assertEquals("input", interceptingLocator.inputValue());
    }

    @Test
    void testIsChecked() {
        when(locator.isChecked()).thenReturn(true);
        assertTrue(interceptingLocator.isChecked());
    }

    @Test
    void testIsEditable() {
        when(locator.isEditable()).thenReturn(true);
        assertTrue(interceptingLocator.isEditable());
    }

    @Test
    void testSelectText() {
        interceptingLocator.selectText();
        verify(locator).selectText();
    }

    @Test
    void testSetChecked() {
        interceptingLocator.setChecked(true);
        verify(locator).setChecked(true);
    }

    @Test
    void testTap() {
        interceptingLocator.tap();
        verify(locator).tap();
    }

    @Test
    void testDragTo() {
        Locator target = mock(Locator.class);
        interceptingLocator.dragTo(target);
        verify(locator).dragTo(target);
    }

    @Test
    void testWaitFor() {
        interceptingLocator.waitFor();
        verify(locator).waitFor();
    }

    @Test
    void testScrollIntoViewIfNeeded() {
        interceptingLocator.scrollIntoViewIfNeeded();
        verify(locator).scrollIntoViewIfNeeded();
    }

    @Test
    void testHighlight() {
        interceptingLocator.highlight();
        verify(locator).highlight();
    }

    @Test
    void testCount() {
        when(locator.count()).thenReturn(5);
        assertEquals(5, interceptingLocator.count());
    }

    @Test
    void testAllInnerTexts() {
        when(locator.allInnerTexts()).thenReturn(List.of("text1", "text2"));
        assertEquals(List.of("text1", "text2"), interceptingLocator.allInnerTexts());
    }

    @Test
    void testAllTextContents() {
        when(locator.allTextContents()).thenReturn(List.of("content1", "content2"));
        assertEquals(List.of("content1", "content2"), interceptingLocator.allTextContents());
    }

    @Test
    void testToString() {
        when(locator.toString()).thenReturn("Locator@123");
        assertEquals("Locator@123", interceptingLocator.toString());
    }

    @Test
    void testBoundingBox() {
        interceptingLocator.boundingBox();
        verify(locator).boundingBox();
    }

    @Test
    void testScreenshot() {
        interceptingLocator.screenshot();
        verify(locator).screenshot();
    }

    @Test
    void testElementHandle() {
        interceptingLocator.elementHandle();
        verify(locator).elementHandle();
    }

    @Test
    void testElementHandles() {
        interceptingLocator.elementHandles();
        verify(locator).elementHandles();
    }

    @Test
    void testEvaluate() {
        interceptingLocator.evaluate("expression");
        verify(locator).evaluate("expression");
    }

    @Test
    void testEvaluateAll() {
        interceptingLocator.evaluateAll("expression");
        verify(locator).evaluateAll("expression");
    }

    @Test
    void testEvaluateHandle() {
        interceptingLocator.evaluateHandle("expression");
        verify(locator).evaluateHandle("expression");
    }

    @Test
    void testDispatchEvent() {
        interceptingLocator.dispatchEvent("click");
        verify(locator).dispatchEvent(eq("click"), any());
    }

    @Test
    void testContentFrame() {
        FrameLocator frameLocator = mock(FrameLocator.class);
        when(locator.contentFrame()).thenReturn(frameLocator);
        FrameLocator result = interceptingLocator.contentFrame();
        assertNotNull(result);
    }

    @Test
    void testIsHidden() {
        when(locator.isHidden()).thenReturn(true);
        assertTrue(interceptingLocator.isHidden());
    }

    @Test
    void testAriaSnapshot() {
        interceptingLocator.ariaSnapshot();
        verify(locator).ariaSnapshot(any());
    }

    @Test
    void testPressSequentially() {
        interceptingLocator.pressSequentially("text");
        verify(locator).pressSequentially(eq("text"), any());
    }

    @Test
    void testSetInputFiles() {
        interceptingLocator.setInputFiles(java.nio.file.Paths.get("/tmp/file.txt"));
        verify(locator).setInputFiles(java.nio.file.Paths.get("/tmp/file.txt"));
    }

    // --- Overloaded methods with Options ---

    @Test
    void testDblclickWithOptions() {
        interceptingLocator.dblclick(new Locator.DblclickOptions());
        verify(locator).dblclick(any(Locator.DblclickOptions.class));
    }

    @Test
    void testFillWithOptions() {
        interceptingLocator.fill("v", new Locator.FillOptions());
        verify(locator).fill(eq("v"), any(Locator.FillOptions.class));
    }

    @Test
    void testHoverWithOptions() {
        interceptingLocator.hover(new Locator.HoverOptions());
        verify(locator).hover(any(Locator.HoverOptions.class));
    }

    @Test
    void testInnerHTMLOptions() {
        interceptingLocator.innerHTML(new Locator.InnerHTMLOptions());
        verify(locator).innerHTML(any(Locator.InnerHTMLOptions.class));
    }

    @Test
    void testInnerTextOptions() {
        interceptingLocator.innerText(new Locator.InnerTextOptions());
        verify(locator).innerText(any(Locator.InnerTextOptions.class));
    }

    @Test
    void testTextContentOptions() {
        interceptingLocator.textContent(new Locator.TextContentOptions());
        verify(locator).textContent(any(Locator.TextContentOptions.class));
    }

    @Test
    void testGetAttributeOptions() {
        interceptingLocator.getAttribute("n", new Locator.GetAttributeOptions());
        verify(locator).getAttribute(eq("n"), any(Locator.GetAttributeOptions.class));
    }

    @Test
    void testIsVisibleOptions() {
        interceptingLocator.isVisible(new Locator.IsVisibleOptions());
        verify(locator).isVisible(any(Locator.IsVisibleOptions.class));
    }

    @Test
    void testIsEnabledOptions() {
        interceptingLocator.isEnabled(new Locator.IsEnabledOptions());
        verify(locator).isEnabled(any(Locator.IsEnabledOptions.class));
    }

    @Test
    void testIsDisabledOptions() {
        interceptingLocator.isDisabled(new Locator.IsDisabledOptions());
        verify(locator).isDisabled(any(Locator.IsDisabledOptions.class));
    }

    @Test
    void testCheckOptions() {
        interceptingLocator.check(new Locator.CheckOptions());
        verify(locator).check(any(Locator.CheckOptions.class));
    }

    @Test
    void testFocusOptions() {
        interceptingLocator.focus(new Locator.FocusOptions());
        verify(locator).focus(any(Locator.FocusOptions.class));
    }

    @Test
    void testBlurOptions() {
        interceptingLocator.blur(new Locator.BlurOptions());
        verify(locator).blur(any(Locator.BlurOptions.class));
    }

    @Test
    void testPressOptions() {
        interceptingLocator.press("K", new Locator.PressOptions());
        verify(locator).press(eq("K"), any(Locator.PressOptions.class));
    }

    @Test
    void testInputValueOptions() {
        interceptingLocator.inputValue(new Locator.InputValueOptions());
        verify(locator).inputValue(any(Locator.InputValueOptions.class));
    }

    @Test
    void testIsCheckedOptions() {
        interceptingLocator.isChecked(new Locator.IsCheckedOptions());
        verify(locator).isChecked(any(Locator.IsCheckedOptions.class));
    }

    @Test
    void testIsEditableOptions() {
        interceptingLocator.isEditable(new Locator.IsEditableOptions());
        verify(locator).isEditable(any(Locator.IsEditableOptions.class));
    }

    @Test
    void testSelectTextOptions() {
        interceptingLocator.selectText(new Locator.SelectTextOptions());
        verify(locator).selectText(any(Locator.SelectTextOptions.class));
    }

    @Test
    void testSetCheckedOptions() {
        interceptingLocator.setChecked(true, new Locator.SetCheckedOptions());
        verify(locator).setChecked(eq(true), any(Locator.SetCheckedOptions.class));
    }

    @Test
    void testTapOptions() {
        interceptingLocator.tap(new Locator.TapOptions());
        verify(locator).tap(any(Locator.TapOptions.class));
    }

    @Test
    @SuppressWarnings({"java:S5738", "deprecation"})
    void testTypeOptions() {
        interceptingLocator.type("t", new Locator.TypeOptions());
        verify(locator).type(eq("t"), any(Locator.TypeOptions.class));
    }

    @Test
    void testUncheckOptions() {
        interceptingLocator.uncheck(new Locator.UncheckOptions());
        verify(locator).uncheck(any(Locator.UncheckOptions.class));
    }

    @Test
    void testClearOptions() {
        interceptingLocator.clear(new Locator.ClearOptions());
        verify(locator).clear(any(Locator.ClearOptions.class));
    }

    @Test
    void testDispatchEventOptions() {
        interceptingLocator.dispatchEvent("click", null, new Locator.DispatchEventOptions());
        verify(locator).dispatchEvent(eq("click"), isNull(), any(Locator.DispatchEventOptions.class));
    }

    @Test
    void testDispatchEventTwoArgs() {
        interceptingLocator.dispatchEvent("click", null);
        verify(locator).dispatchEvent("click", null);
    }

    @Test
    void testEvaluateTwoArgs() {
        interceptingLocator.evaluate("e", null);
        verify(locator).evaluate("e", null);
    }

    @Test
    void testEvaluateOptions() {
        interceptingLocator.evaluate("e", null, new Locator.EvaluateOptions());
        verify(locator).evaluate(eq("e"), isNull(), any(Locator.EvaluateOptions.class));
    }

    @Test
    void testEvaluateAllTwoArgs() {
        interceptingLocator.evaluateAll("e", null);
        verify(locator).evaluateAll("e", null);
    }

    @Test
    void testEvaluateHandleTwoArgs() {
        interceptingLocator.evaluateHandle("e", null);
        verify(locator).evaluateHandle("e", null);
    }

    @Test
    void testEvaluateHandleOptions() {
        interceptingLocator.evaluateHandle("e", null, new Locator.EvaluateHandleOptions());
        verify(locator).evaluateHandle(eq("e"), isNull(), any(Locator.EvaluateHandleOptions.class));
    }

    @Test
    void testBoundingBoxOptions() {
        interceptingLocator.boundingBox(new Locator.BoundingBoxOptions());
        verify(locator).boundingBox(any(Locator.BoundingBoxOptions.class));
    }

    @Test
    void testScreenshotOptions() {
        interceptingLocator.screenshot(new Locator.ScreenshotOptions());
        verify(locator).screenshot(any(Locator.ScreenshotOptions.class));
    }

    @Test
    void testElementHandleOptions() {
        interceptingLocator.elementHandle(new Locator.ElementHandleOptions());
        verify(locator).elementHandle(any(Locator.ElementHandleOptions.class));
    }

    @Test
    void testScrollIntoViewIfNeededOptions() {
        interceptingLocator.scrollIntoViewIfNeeded(new Locator.ScrollIntoViewIfNeededOptions());
        verify(locator).scrollIntoViewIfNeeded(any(Locator.ScrollIntoViewIfNeededOptions.class));
    }

    @Test
    void testWaitForOptions() {
        interceptingLocator.waitFor(new Locator.WaitForOptions());
        verify(locator).waitFor(any(Locator.WaitForOptions.class));
    }

    @Test
    void testIsHiddenOptions() {
        interceptingLocator.isHidden(new Locator.IsHiddenOptions());
        verify(locator).isHidden(any(Locator.IsHiddenOptions.class));
    }

    @Test
    void testDragToOptions() {
        Locator target = mock(Locator.class);
        interceptingLocator.dragTo(target, new Locator.DragToOptions());
        verify(locator).dragTo(eq(target), any(Locator.DragToOptions.class));
    }

    @Test
    void testLocatorWithOptions() {
        Locator childLoc = mock(Locator.class);
        when(locator.locator(eq("s"), any())).thenReturn(childLoc);
        interceptingLocator.locator("s", new Locator.LocatorOptions());
        verify(locator).locator(eq("s"), any(Locator.LocatorOptions.class));
    }

    @Test
    void testLocatorWithLocatorArg() {
        Locator other = mock(Locator.class);
        Locator childLoc = mock(Locator.class);
        when(locator.locator(eq(other), any())).thenReturn(childLoc);
        interceptingLocator.locator(other, new Locator.LocatorOptions());
        verify(locator).locator(eq(other), any(Locator.LocatorOptions.class));
    }

    @Test
    void testGetByAltTextStringOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByAltText(eq("a"), any())).thenReturn(r);
        interceptingLocator.getByAltText("a", new Locator.GetByAltTextOptions());
        verify(locator).getByAltText(eq("a"), any(Locator.GetByAltTextOptions.class));
    }

    @Test
    void testGetByAltTextPattern() {
        Locator r = mock(Locator.class);
        when(locator.getByAltText(any(java.util.regex.Pattern.class))).thenReturn(r);
        interceptingLocator.getByAltText(java.util.regex.Pattern.compile("a"));
        verify(locator).getByAltText(any(java.util.regex.Pattern.class));
    }

    @Test
    void testGetByAltTextPatternOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByAltText(any(java.util.regex.Pattern.class), any())).thenReturn(r);
        interceptingLocator.getByAltText(java.util.regex.Pattern.compile("a"), new Locator.GetByAltTextOptions());
        verify(locator).getByAltText(any(java.util.regex.Pattern.class), any(Locator.GetByAltTextOptions.class));
    }

    @Test
    void testGetByLabelStringOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByLabel(eq("l"), any())).thenReturn(r);
        interceptingLocator.getByLabel("l", new Locator.GetByLabelOptions());
        verify(locator).getByLabel(eq("l"), any(Locator.GetByLabelOptions.class));
    }

    @Test
    void testGetByLabelPattern() {
        Locator r = mock(Locator.class);
        when(locator.getByLabel(any(java.util.regex.Pattern.class))).thenReturn(r);
        interceptingLocator.getByLabel(java.util.regex.Pattern.compile("l"));
        verify(locator).getByLabel(any(java.util.regex.Pattern.class));
    }

    @Test
    void testGetByLabelPatternOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByLabel(any(java.util.regex.Pattern.class), any())).thenReturn(r);
        interceptingLocator.getByLabel(java.util.regex.Pattern.compile("l"), new Locator.GetByLabelOptions());
        verify(locator).getByLabel(any(java.util.regex.Pattern.class), any(Locator.GetByLabelOptions.class));
    }

    @Test
    void testGetByPlaceholderStringOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByPlaceholder(eq("p"), any())).thenReturn(r);
        interceptingLocator.getByPlaceholder("p", new Locator.GetByPlaceholderOptions());
        verify(locator).getByPlaceholder(eq("p"), any(Locator.GetByPlaceholderOptions.class));
    }

    @Test
    void testGetByPlaceholderPattern() {
        Locator r = mock(Locator.class);
        when(locator.getByPlaceholder(any(java.util.regex.Pattern.class))).thenReturn(r);
        interceptingLocator.getByPlaceholder(java.util.regex.Pattern.compile("p"));
        verify(locator).getByPlaceholder(any(java.util.regex.Pattern.class));
    }

    @Test
    void testGetByPlaceholderPatternOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByPlaceholder(any(java.util.regex.Pattern.class), any())).thenReturn(r);
        interceptingLocator.getByPlaceholder(java.util.regex.Pattern.compile("p"), new Locator.GetByPlaceholderOptions());
        verify(locator).getByPlaceholder(any(java.util.regex.Pattern.class), any(Locator.GetByPlaceholderOptions.class));
    }

    @Test
    void testGetByRoleOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByRole(eq(AriaRole.BUTTON), any())).thenReturn(r);
        interceptingLocator.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions());
        verify(locator).getByRole(eq(AriaRole.BUTTON), any(Locator.GetByRoleOptions.class));
    }

    @Test
    void testGetByTestIdPattern() {
        Locator r = mock(Locator.class);
        when(locator.getByTestId(any(java.util.regex.Pattern.class))).thenReturn(r);
        interceptingLocator.getByTestId(java.util.regex.Pattern.compile("id"));
        verify(locator).getByTestId(any(java.util.regex.Pattern.class));
    }

    @Test
    void testGetByTextStringOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByText(eq("t"), any())).thenReturn(r);
        interceptingLocator.getByText("t", new Locator.GetByTextOptions());
        verify(locator).getByText(eq("t"), any(Locator.GetByTextOptions.class));
    }

    @Test
    void testGetByTextPattern() {
        Locator r = mock(Locator.class);
        when(locator.getByText(any(java.util.regex.Pattern.class))).thenReturn(r);
        interceptingLocator.getByText(java.util.regex.Pattern.compile("t"));
        verify(locator).getByText(any(java.util.regex.Pattern.class));
    }

    @Test
    void testGetByTextPatternOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByText(any(java.util.regex.Pattern.class), any())).thenReturn(r);
        interceptingLocator.getByText(java.util.regex.Pattern.compile("t"), new Locator.GetByTextOptions());
        verify(locator).getByText(any(java.util.regex.Pattern.class), any(Locator.GetByTextOptions.class));
    }

    @Test
    void testGetByTitleStringOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByTitle(eq("t"), any())).thenReturn(r);
        interceptingLocator.getByTitle("t", new Locator.GetByTitleOptions());
        verify(locator).getByTitle(eq("t"), any(Locator.GetByTitleOptions.class));
    }

    @Test
    void testGetByTitlePattern() {
        Locator r = mock(Locator.class);
        when(locator.getByTitle(any(java.util.regex.Pattern.class))).thenReturn(r);
        interceptingLocator.getByTitle(java.util.regex.Pattern.compile("t"));
        verify(locator).getByTitle(any(java.util.regex.Pattern.class));
    }

    @Test
    void testGetByTitlePatternOptions() {
        Locator r = mock(Locator.class);
        when(locator.getByTitle(any(java.util.regex.Pattern.class), any())).thenReturn(r);
        interceptingLocator.getByTitle(java.util.regex.Pattern.compile("t"), new Locator.GetByTitleOptions());
        verify(locator).getByTitle(any(java.util.regex.Pattern.class), any(Locator.GetByTitleOptions.class));
    }

    @Test
    void testSelectOptionStringArray() {
        interceptingLocator.selectOption(new String[]{"v"});
        verify(locator).selectOption((String[]) any());
    }

    @Test
    void testSelectOptionStringArrayOptions() {
        interceptingLocator.selectOption(new String[]{"v"}, new Locator.SelectOptionOptions());
        verify(locator).selectOption((String[]) any(), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionElementHandle() {
        ElementHandle eh = mock(ElementHandle.class);
        interceptingLocator.selectOption(eh);
        verify(locator).selectOption(eh);
    }

    @Test
    void testSelectOptionElementHandleOptions() {
        ElementHandle eh = mock(ElementHandle.class);
        interceptingLocator.selectOption(eh, new Locator.SelectOptionOptions());
        verify(locator).selectOption(eq(eh), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionStringWithOptions() {
        interceptingLocator.selectOption("v", new Locator.SelectOptionOptions());
        verify(locator).selectOption(eq("v"), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionElementHandleArray() {
        interceptingLocator.selectOption(new ElementHandle[]{mock(ElementHandle.class)});
        verify(locator).selectOption((ElementHandle[]) any());
    }

    @Test
    void testSelectOptionElementHandleArrayOptions() {
        interceptingLocator.selectOption(new ElementHandle[]{mock(ElementHandle.class)}, new Locator.SelectOptionOptions());
        verify(locator).selectOption((ElementHandle[]) any(), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionSelectOptionValue() {
        com.microsoft.playwright.options.SelectOption so = new com.microsoft.playwright.options.SelectOption();
        interceptingLocator.selectOption(so);
        verify(locator).selectOption(so);
    }

    @Test
    void testSelectOptionSelectOptionValueOptions() {
        com.microsoft.playwright.options.SelectOption so = new com.microsoft.playwright.options.SelectOption();
        interceptingLocator.selectOption(so, new Locator.SelectOptionOptions());
        verify(locator).selectOption(eq(so), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionSelectOptionArray() {
        interceptingLocator.selectOption(new com.microsoft.playwright.options.SelectOption[]{});
        verify(locator).selectOption((com.microsoft.playwright.options.SelectOption[]) any());
    }

    @Test
    void testSelectOptionSelectOptionArrayOptions() {
        interceptingLocator.selectOption(new com.microsoft.playwright.options.SelectOption[]{}, new Locator.SelectOptionOptions());
        verify(locator).selectOption((com.microsoft.playwright.options.SelectOption[]) any(), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSetInputFilesPathOptions() {
        interceptingLocator.setInputFiles(java.nio.file.Paths.get("/tmp/f"), new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles(eq(java.nio.file.Paths.get("/tmp/f")), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testSetInputFilesPathArray() {
        interceptingLocator.setInputFiles(new java.nio.file.Path[]{java.nio.file.Paths.get("/tmp/f")});
        verify(locator).setInputFiles((java.nio.file.Path[]) any());
    }

    @Test
    void testSetInputFilesPathArrayOptions() {
        interceptingLocator.setInputFiles(new java.nio.file.Path[]{}, new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles((java.nio.file.Path[]) any(), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testSetInputFilesFilePayload() {
        com.microsoft.playwright.options.FilePayload fp = new com.microsoft.playwright.options.FilePayload("f", "text/plain", new byte[0]);
        interceptingLocator.setInputFiles(fp);
        verify(locator).setInputFiles(fp);
    }

    @Test
    void testSetInputFilesFilePayloadOptions() {
        com.microsoft.playwright.options.FilePayload fp = new com.microsoft.playwright.options.FilePayload("f", "text/plain", new byte[0]);
        interceptingLocator.setInputFiles(fp, new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles(eq(fp), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testSetInputFilesFilePayloadArray() {
        interceptingLocator.setInputFiles(new com.microsoft.playwright.options.FilePayload[]{});
        verify(locator).setInputFiles((com.microsoft.playwright.options.FilePayload[]) any());
    }

    @Test
    void testSetInputFilesFilePayloadArrayOptions() {
        interceptingLocator.setInputFiles(new com.microsoft.playwright.options.FilePayload[]{}, new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles((com.microsoft.playwright.options.FilePayload[]) any(), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testPage() {
        interceptingLocator.page();
        verify(locator).page();
    }

    @Test
    void testPressSequentiallyNoOptions() {
        interceptingLocator.pressSequentially("t", null);
        verify(locator).pressSequentially(eq("t"), isNull());
    }
}
