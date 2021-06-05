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
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiPaginationLocators}
 *
 * @author Jack Yin
 * @since 1.3
 */
class MuiPaginationLocatorsTest {

    MuiPaginationLocators testSubject;
    Function<MuiPagination, MuiButton> previousButtonFinder = p -> mock(MuiButton.class);
    Function<MuiPagination, MuiButton> nextButtonFinder = p -> mock(MuiButton.class);
    Function<MuiPagination, MuiButton> firstButtonFinder = p -> mock(MuiButton.class);
    Function<MuiPagination, MuiButton> lastButtonFinder = p -> mock(MuiButton.class);
    Function<MuiPagination, List<MuiButton>> pageButtonsFinder = p -> {
        MuiButton button1 = mock(MuiButton.class);
        MuiButton button2 = mock(MuiButton.class);
        return newArrayList(button1, button2);
    };
    Function<MuiButton, Integer> pageIndexFinder = b -> 1;

    MuiPagination pagination = mock(MuiPagination.class);

    WebElement previousButtonElement = mock(WebElement.class);
    WebComponent previousButtonComponent = mock(WebComponent.class);
    WebElement nextButtonElement = mock(WebElement.class);
    WebComponent nextButtonComponent = mock(WebComponent.class);
    WebElement firstButtonElement = mock(WebElement.class);
    WebComponent firstButtonComponent = mock(WebComponent.class);
    WebElement lastButtonElement = mock(WebElement.class);
    WebComponent lastButtonComponent = mock(WebComponent.class);

    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(previousButtonComponent.getWrappedElement()).thenReturn(previousButtonElement);
        when(nextButtonComponent.getWrappedElement()).thenReturn(nextButtonElement);
        when(firstButtonComponent.getWrappedElement()).thenReturn(firstButtonElement);
        when(lastButtonComponent.getWrappedElement()).thenReturn(lastButtonElement);

        when(pagination.driver()).thenReturn(driver);
        when(pagination.config()).thenReturn(config);

        testSubject = new MuiPaginationLocators(previousButtonFinder, nextButtonFinder, firstButtonFinder,
                lastButtonFinder, pageButtonsFinder, pageIndexFinder);
    }


    @Test
    void getPreviousButtonFinder() {
        assertEquals(previousButtonFinder, testSubject.getPreviousButtonFinder());
    }

    @Test
    void getNextButtonFinder() {
        assertEquals(nextButtonFinder, testSubject.getNextButtonFinder());
    }

    @Test
    void getFirstButtonFinder() {
        assertEquals(firstButtonFinder, testSubject.getFirstButtonFinder());
    }

    @Test
    void getLastButtonFinder() {
        assertEquals(lastButtonFinder, testSubject.getLastButtonFinder());
    }

    @Test
    void getPageButtonsFinder() {
        assertEquals(pageButtonsFinder, testSubject.getPageButtonsFinder());
    }

    @Test
    void getPageIndexFinder() {
        assertEquals(pageIndexFinder, testSubject.getPageIndexFinder());
    }

    @Test
    void createDefault() {
        MuiPaginationLocators locators = MuiPaginationLocators.createDefault();
        assertEquals(MuiPaginationLocators.DEFAULT_FIRST_BUTTON, locators.getFirstButtonFinder());
        assertEquals(MuiPaginationLocators.DEFAULT_LAST_BUTTON, locators.getLastButtonFinder());
        assertEquals(MuiPaginationLocators.DEFAULT_PREVIOUS_BUTTON, locators.getPreviousButtonFinder());
        assertEquals(MuiPaginationLocators.DEFAULT_NEXT_BUTTON, locators.getNextButtonFinder());
        assertEquals(MuiPaginationLocators.DEFAULT_PAGE_BUTTONS, locators.getPageButtonsFinder());
        assertEquals(MuiPaginationLocators.DEFAULT_PAGE_INDEX, locators.getPageIndexFinder());
    }

    @Test
    void testDefaultPreviousButton() {
        when(pagination.findComponentAs(eq(By2.attrExact("aria-label", "Go to previous page")), any())).then(answer -> {
            Function<WebComponent, MuiButton> arg2 = answer.getArgument(1);
            return arg2.apply(previousButtonComponent);
        });

        assertEquals(previousButtonElement,
                MuiPaginationLocators.DEFAULT_PREVIOUS_BUTTON.apply(pagination).getWrappedElement());
    }

    @Test
    void testDefaultNextButton() {
        when(pagination.findComponentAs(eq(By2.attrExact("aria-label", "Go to next page")), any())).then(answer -> {
            Function<WebComponent, MuiButton> arg2 = answer.getArgument(1);
            return arg2.apply(nextButtonComponent);
        });

        assertEquals(nextButtonElement,
                MuiPaginationLocators.DEFAULT_NEXT_BUTTON.apply(pagination).getWrappedElement());
    }

    @Test
    void testDefaultFirstButton() {
        when(pagination.findComponentAs(eq(By2.attrExact("aria-label", "Go to first page")), any())).then(answer -> {
            Function<WebComponent, MuiButton> arg2 = answer.getArgument(1);
            return arg2.apply(firstButtonComponent);
        });

        assertEquals(firstButtonElement,
                MuiPaginationLocators.DEFAULT_FIRST_BUTTON.apply(pagination).getWrappedElement());
    }

    @Test
    void testDefaultLastButton() {
        when(pagination.findComponentAs(eq(By2.attrExact("aria-label", "Go to last page")), any())).then(answer -> {
            Function<WebComponent, MuiButton> arg2 = answer.getArgument(1);
            return arg2.apply(lastButtonComponent);
        });

        assertEquals(lastButtonElement,
                MuiPaginationLocators.DEFAULT_LAST_BUTTON.apply(pagination).getWrappedElement());
    }

    @Test
    void testDefaultPageButtons() {
        when(pagination.findComponentsAs(eq(By2.attrContains("aria-label", "Go to page")), any())).then(answer -> {
            Function<WebComponent, MuiButton> arg2 = answer.getArgument(1);
            return singletonList(arg2.apply(lastButtonComponent));
        });

        assertEquals(lastButtonElement,
                MuiPaginationLocators.DEFAULT_PAGE_BUTTONS.apply(pagination).get(0).getWrappedElement());
    }
}
