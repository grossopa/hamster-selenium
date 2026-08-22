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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterceptingFrameLocatorTest {

    private FrameLocator frameLocator;
    private InterceptingHandler handler;
    private InterceptingFrameLocator interceptingFrameLocator;

    @BeforeEach
    void setUp() {
        frameLocator = mock(FrameLocator.class);
        handler = mock(InterceptingHandler.class);
        when(handler.execute(any(), any())).thenAnswer(invocation -> {
            java.util.function.Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        interceptingFrameLocator = new InterceptingFrameLocator(frameLocator, handler);
    }

    @Test
    void testConstructorWithNullFrameLocator() {
        assertThrows(NullPointerException.class, () -> new InterceptingFrameLocator(null, handler));
    }

    @Test
    void testConstructorWithNullHandler() {
        assertThrows(NullPointerException.class, () -> new InterceptingFrameLocator(frameLocator, null));
    }

    @Test
    void testLocator() {
        Locator locator = mock(Locator.class);
        when(frameLocator.locator(".selector")).thenReturn(locator);
        Locator result = interceptingFrameLocator.locator(".selector");
        assertNotNull(result);
        assertInstanceOf(InterceptingLocator.class, result);
    }

    @Test
    void testFrameLocator() {
        FrameLocator childFrameLocator = mock(FrameLocator.class);
        when(frameLocator.frameLocator("iframe")).thenReturn(childFrameLocator);
        FrameLocator result = interceptingFrameLocator.frameLocator("iframe");
        assertNotNull(result);
        assertInstanceOf(InterceptingFrameLocator.class, result);
    }

    @Test
    void testGetByAltText() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByAltText("alt")).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByAltText("alt");
        assertNotNull(result);
    }

    @Test
    void testGetByLabel() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByLabel("label")).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByLabel("label");
        assertNotNull(result);
    }

    @Test
    void testGetByPlaceholder() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByPlaceholder("placeholder")).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByPlaceholder("placeholder");
        assertNotNull(result);
    }

    @Test
    void testGetByRole() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByRole(AriaRole.BUTTON)).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByRole(AriaRole.BUTTON);
        assertNotNull(result);
    }

    @Test
    void testGetByText() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByText("text")).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByText("text");
        assertNotNull(result);
    }

    @Test
    void testGetByTitle() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByTitle("title")).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByTitle("title");
        assertNotNull(result);
    }

    @Test
    void testGetByTestId() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByTestId("test-id")).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByTestId("test-id");
        assertNotNull(result);
    }

    @Test
    void testFirst() {
        FrameLocator firstLocator = mock(FrameLocator.class);
        when(frameLocator.first()).thenReturn(firstLocator);
        FrameLocator result = interceptingFrameLocator.first();
        assertNotNull(result);
        assertInstanceOf(InterceptingFrameLocator.class, result);
    }

    @Test
    void testLast() {
        FrameLocator lastLocator = mock(FrameLocator.class);
        when(frameLocator.last()).thenReturn(lastLocator);
        FrameLocator result = interceptingFrameLocator.last();
        assertNotNull(result);
        assertInstanceOf(InterceptingFrameLocator.class, result);
    }

    @Test
    void testNth() {
        FrameLocator nthLocator = mock(FrameLocator.class);
        when(frameLocator.nth(2)).thenReturn(nthLocator);
        FrameLocator result = interceptingFrameLocator.nth(2);
        assertNotNull(result);
        assertInstanceOf(InterceptingFrameLocator.class, result);
    }

    @Test
    void testOwner() {
        Locator ownerLocator = mock(Locator.class);
        when(frameLocator.owner()).thenReturn(ownerLocator);
        Locator result = interceptingFrameLocator.owner();
        assertNotNull(result);
        assertInstanceOf(InterceptingLocator.class, result);
    }

    // --- Overloaded methods with Options and Pattern ---

    @Test
    void testLocatorWithOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.locator(eq(".s"), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.locator(".s", new FrameLocator.LocatorOptions());
        assertNotNull(result);
    }

    @Test
    void testLocatorWithLocatorArg() {
        Locator selectorLocator = mock(Locator.class);
        Locator resultLocator = mock(Locator.class);
        when(frameLocator.locator(eq(selectorLocator), any())).thenReturn(resultLocator);
        Locator result = interceptingFrameLocator.locator(selectorLocator, new FrameLocator.LocatorOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByAltTextStringOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByAltText(eq("a"), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByAltText("a", new FrameLocator.GetByAltTextOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByAltTextPattern() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByAltText(any(java.util.regex.Pattern.class))).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByAltText(java.util.regex.Pattern.compile("a"));
        assertNotNull(result);
    }

    @Test
    void testGetByAltTextPatternOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByAltText(any(java.util.regex.Pattern.class), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByAltText(java.util.regex.Pattern.compile("a"), new FrameLocator.GetByAltTextOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByLabelStringOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByLabel(eq("l"), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByLabel("l", new FrameLocator.GetByLabelOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByLabelPattern() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByLabel(any(java.util.regex.Pattern.class))).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByLabel(java.util.regex.Pattern.compile("l"));
        assertNotNull(result);
    }

    @Test
    void testGetByLabelPatternOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByLabel(any(java.util.regex.Pattern.class), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByLabel(java.util.regex.Pattern.compile("l"), new FrameLocator.GetByLabelOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByPlaceholderStringOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByPlaceholder(eq("p"), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByPlaceholder("p", new FrameLocator.GetByPlaceholderOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByPlaceholderPattern() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByPlaceholder(any(java.util.regex.Pattern.class))).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByPlaceholder(java.util.regex.Pattern.compile("p"));
        assertNotNull(result);
    }

    @Test
    void testGetByPlaceholderPatternOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByPlaceholder(any(java.util.regex.Pattern.class), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByPlaceholder(java.util.regex.Pattern.compile("p"), new FrameLocator.GetByPlaceholderOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByRoleOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByRole(eq(AriaRole.BUTTON), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByTestIdPattern() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByTestId(any(java.util.regex.Pattern.class))).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByTestId(java.util.regex.Pattern.compile("id"));
        assertNotNull(result);
    }

    @Test
    void testGetByTextStringOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByText(eq("t"), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByText("t", new FrameLocator.GetByTextOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByTextPattern() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByText(any(java.util.regex.Pattern.class))).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByText(java.util.regex.Pattern.compile("t"));
        assertNotNull(result);
    }

    @Test
    void testGetByTextPatternOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByText(any(java.util.regex.Pattern.class), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByText(java.util.regex.Pattern.compile("t"), new FrameLocator.GetByTextOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByTitleStringOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByTitle(eq("t"), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByTitle("t", new FrameLocator.GetByTitleOptions());
        assertNotNull(result);
    }

    @Test
    void testGetByTitlePattern() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByTitle(any(java.util.regex.Pattern.class))).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByTitle(java.util.regex.Pattern.compile("t"));
        assertNotNull(result);
    }

    @Test
    void testGetByTitlePatternOptions() {
        Locator locator = mock(Locator.class);
        when(frameLocator.getByTitle(any(java.util.regex.Pattern.class), any())).thenReturn(locator);
        Locator result = interceptingFrameLocator.getByTitle(java.util.regex.Pattern.compile("t"), new FrameLocator.GetByTitleOptions());
        assertNotNull(result);
    }
}
