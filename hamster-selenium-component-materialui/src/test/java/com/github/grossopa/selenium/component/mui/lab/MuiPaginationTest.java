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

package com.github.grossopa.selenium.component.mui.lab;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests for {@link MuiPagination}
 *
 * @author Jack Yin
 * @since 1.3
 */
@SuppressWarnings("unchecked")
class MuiPaginationTest {

    MuiPagination testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);
    MuiPaginationLocators locators = mock(MuiPaginationLocators.class);
    Function<MuiPagination, MuiButton> previousButtonFinder = mock(Function.class);
    Function<MuiPagination, MuiButton> nextButtonFinder = mock(Function.class);
    Function<MuiPagination, MuiButton> firstButtonFinder = mock(Function.class);
    Function<MuiPagination, MuiButton> lastButtonFinder = mock(Function.class);
    Function<MuiPagination, List<MuiButton>> pageButtonsFinder = mock(Function.class);
    Function<MuiButton, Integer> pageIndexFinder = button -> Integer.valueOf(button.getText());

    List<MuiButton> pageButtons = newArrayList();
    List<MuiButton> all = newArrayList();
    List<WebElement> allElements = newArrayList();

    MuiButton previousButton = mock(MuiButton.class);
    MuiButton nextButton = mock(MuiButton.class);
    MuiButton firstButton = mock(MuiButton.class);
    MuiButton lastButton = mock(MuiButton.class);

    private MuiButton createPageButton(int index) {
        MuiButton button = mock(MuiButton.class);
        when(button.getText()).thenReturn(String.valueOf(index));
        WebElement element = mock(WebElement.class);
        when(element.getText()).thenReturn(String.valueOf(index));
        when(button.getWrappedElement()).thenReturn(element);
        return button;
    }

    private void enrichPageButton(MuiButton button, MuiButton... buttonsWhenClick) {
        doAnswer(answer -> {
            pageButtons.clear();
            pageButtons.addAll(newArrayList(buttonsWhenClick));
            when(element.findElements(By2.attr("aria-current", "true").exact().anyDepthChild().build()))
                    .thenReturn(singletonList(button));
            return null;
        }).when(button).click();
    }

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            all.add(createPageButton(i + 1));
            allElements.add(all.get(i).getWrappedElement());
        }

        when(element.findElements(By2.attr("aria-current", "true").exact().anyDepthChild().build()))
                .thenReturn(singletonList(all.get(0)));
        pageButtons.add(all.get(0));
        pageButtons.add(all.get(1));
        pageButtons.add(all.get(2));
        pageButtons.add(all.get(3));
        pageButtons.add(all.get(4));
        pageButtons.add(all.get(9));

        enrichPageButton(all.get(0), all.get(0), all.get(1), all.get(2), all.get(3), all.get(4), all.get(9));
        enrichPageButton(all.get(1), all.get(0), all.get(1), all.get(2), all.get(3), all.get(4), all.get(9));
        enrichPageButton(all.get(2), all.get(0), all.get(1), all.get(2), all.get(3), all.get(4), all.get(9));
        enrichPageButton(all.get(3), all.get(0), all.get(1), all.get(2), all.get(3), all.get(4), all.get(9));
        enrichPageButton(all.get(4), all.get(0), all.get(3), all.get(4), all.get(5), all.get(9));
        enrichPageButton(all.get(5), all.get(0), all.get(4), all.get(5), all.get(6), all.get(9));
        enrichPageButton(all.get(6), all.get(0), all.get(5), all.get(6), all.get(7), all.get(8), all.get(9));
        enrichPageButton(all.get(7), all.get(0), all.get(5), all.get(6), all.get(7), all.get(8), all.get(9));
        enrichPageButton(all.get(8), all.get(0), all.get(5), all.get(6), all.get(7), all.get(8), all.get(9));
        enrichPageButton(all.get(9), all.get(0), all.get(5), all.get(6), all.get(7), all.get(8), all.get(9));

        when(locators.getPreviousButtonFinder()).thenReturn(previousButtonFinder);
        when(locators.getNextButtonFinder()).thenReturn(nextButtonFinder);
        when(locators.getFirstButtonFinder()).thenReturn(firstButtonFinder);
        when(locators.getLastButtonFinder()).thenReturn(lastButtonFinder);
        when(locators.getPageButtonsFinder()).thenReturn(pageButtonsFinder);
        when(locators.getPageIndexFinder()).thenReturn(pageIndexFinder);

        testSubject = new MuiPagination(element, driver, config, locators);

        when(pageButtonsFinder.apply(testSubject)).thenReturn(pageButtons);
        when(previousButtonFinder.apply(testSubject)).thenReturn(previousButton);
        when(nextButtonFinder.apply(testSubject)).thenReturn(nextButton);
        when(firstButtonFinder.apply(testSubject)).thenReturn(firstButton);
        when(lastButtonFinder.apply(testSubject)).thenReturn(lastButton);
    }

    @Test
    void getComponentName() {
        assertEquals("Pagination", testSubject.getComponentName());
    }

    @Test
    void isEnabledTrue() {
        WebElement buttonElement = mock(WebElement.class);
        when(config.isDisabled(argThat(c -> c.getWrappedElement() == buttonElement))).thenReturn(false);
        when(element.findElement(By2.attr("aria-current", "true").exact().anyDepthChild().build()))
                .thenReturn(buttonElement);
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledFalse() {
        WebElement buttonElement = mock(WebElement.class);
        when(config.isDisabled(argThat(c -> c.getWrappedElement() == buttonElement))).thenReturn(true);
        when(element.findElement(By2.attr("aria-current", "true").exact().anyDepthChild().build()))
                .thenReturn(buttonElement);
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void pageButtons() {
        assertEquals(pageButtons, testSubject.pageButtons());
    }

    @Test
    void previousButton() {
        assertEquals(previousButton, testSubject.previousButton());
    }

    @Test
    void nextButton() {
        assertEquals(nextButton, testSubject.nextButton());
    }

    @Test
    void firstButton() {
        assertEquals(firstButton, testSubject.firstButton());
    }

    @Test
    void lastButton() {
        assertEquals(lastButton, testSubject.lastButton());
    }

    @Test
    void setPageIndex1() {
        testSubject.setPageIndex(1);
        assertEquals(1, testSubject.getCurrentPageIndex());
    }

    @Test
    void setPageIndex2() {
        testSubject.setPageIndex(9);
        assertEquals(9, testSubject.getCurrentPageIndex());
        verify(all.get(9), never()).click();
        verify(all.get(8), times(1)).click();
        verify(all.get(7), never()).click();
        verify(all.get(6), times(1)).click();
        verify(all.get(5), times(1)).click();
        verify(all.get(4), times(1)).click();
        verify(all.get(3), never()).click();
        verify(all.get(2), never()).click();
        verify(all.get(1), never()).click();
        verify(all.get(0), never()).click();
    }

    @Test
    void setPageIndex3() {
        testSubject.setPageIndex(11);
        assertEquals(9, testSubject.getCurrentPageIndex());
    }

    @Test
    void setPageIndex4() {
        testSubject.setPageIndex(10);
        assertEquals(10, testSubject.getCurrentPageIndex());

        verify(all.get(9), times(1)).click();
        verify(all.get(8), never()).click();
        verify(all.get(7), never()).click();
        verify(all.get(6), never()).click();
        verify(all.get(5), never()).click();
        verify(all.get(4), never()).click();
        verify(all.get(3), never()).click();
        verify(all.get(2), never()).click();
        verify(all.get(1), never()).click();
        verify(all.get(0), never()).click();
    }

    @Test
    void getCurrentPageIndexNotExists() {
        when(element.findElements(By2.attr("aria-current", "true").exact().anyDepthChild().build()))
                .thenReturn(emptyList());
        assertEquals(-1, testSubject.getCurrentPageIndex());
    }

    @Test
    void getLocators() {
        assertEquals(locators, testSubject.getLocators());
    }
}
