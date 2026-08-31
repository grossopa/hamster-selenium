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
package com.github.grossopa.selenium.component.antd.dataentry;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link AntdSelect}
 *
 * @author Jack Yin
 * @since 1.15
 */
class AntdSelectTest {

    AntdSelect testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    AntdConfig config = mock(AntdConfig.class);

    By popupLocator = By.xpath("/html/body/div");
    WebComponent popupContainer = mock(WebComponent.class);

    @BeforeEach
    void setUp() {
        when(config.getPrefixCls()).thenReturn("sss");
        when(config.getPopupContainerLocator()).thenReturn(popupLocator);
        when(driver.findComponent(popupLocator)).thenReturn(popupContainer);
        testSubject = new AntdSelect(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("select", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-single");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getDomAttribute("class")).thenReturn("sss-ddd");
        assertFalse(testSubject.validate());
    }

    @Test
    void isOptionsOpenTrue() {
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-open");
        assertTrue(testSubject.isOptionsOpen());
    }

    @Test
    void isOptionsOpenFalse() {
        when(element.getDomAttribute("class")).thenReturn("sss-select");
        assertFalse(testSubject.isOptionsOpen());
    }

    @Test
    void isEnabledFalseWhenDisabled() {
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-disabled");
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void getSelectText() {
        WebElement selectionItem = mock(WebElement.class);
        when(element.findElement(By.className("sss-select-selection-item"))).thenReturn(selectionItem);
        when(selectionItem.getText()).thenReturn("some text");
        assertEquals("some text", testSubject.getSelectText());
    }

    @Test
    void openOptionsAlreadyOpen() {
        WebComponent dropdown = mock(WebComponent.class);
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-open");
        when(popupContainer.findComponents(By.className("sss-select-dropdown"))).thenReturn(List.of(dropdown));
        assertSame(dropdown, testSubject.openOptions());
        verify(element, never()).click();
    }

    @Test
    void openOptionsClicksWhenClosed() {
        WebComponent dropdown = mock(WebComponent.class);
        when(element.getDomAttribute("class")).thenReturn("sss-select");
        when(popupContainer.findComponents(By.className("sss-select-dropdown"))).thenReturn(List.of(dropdown));
        assertSame(dropdown, testSubject.openOptions());
        verify(element).click();
    }

    @Test
    void openOptionsSkipsHiddenDropdown() {
        WebComponent hiddenDropdown = mock(WebComponent.class);
        WebComponent visibleDropdown = mock(WebComponent.class);
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-open");
        when(hiddenDropdown.attributeContains("class", "sss-select-dropdown-hidden")).thenReturn(true);
        when(popupContainer.findComponents(By.className("sss-select-dropdown"))).thenReturn(
                List.of(hiddenDropdown, visibleDropdown));
        assertSame(visibleDropdown, testSubject.openOptions());
    }

    @Test
    void openOptionsThrowsWhenNoDropdown() {
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-open");
        when(popupContainer.findComponents(By.className("sss-select-dropdown"))).thenReturn(List.of());
        assertThrows(NoSuchElementException.class, () -> testSubject.openOptions());
    }

    @Test
    void closeOptionsWhenOpen() {
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-open");
        testSubject.closeOptions();
        verify(element).sendKeys(Keys.ESCAPE);
    }

    @Test
    void closeOptionsWhenClosed() {
        when(element.getDomAttribute("class")).thenReturn("sss-select");
        testSubject.closeOptions();
        verify(element, never()).sendKeys(Keys.ESCAPE);
    }

    @SuppressWarnings("unchecked")
    @Test
    void getOptions() {
        WebComponent dropdown = mock(WebComponent.class);
        AntdSelectOption option = mock(AntdSelectOption.class);
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-open");
        when(popupContainer.findComponents(By.className("sss-select-dropdown"))).thenReturn(List.of(dropdown));
        when(dropdown.findComponentsAs(eq(By.className("sss-select-item-option")), any(Function.class))).thenReturn(
                List.of(option));
        List<AntdSelectOption> options = testSubject.getOptions();
        assertEquals(1, options.size());
        assertSame(option, options.get(0));
    }

    @SuppressWarnings("unchecked")
    @Test
    void selectOption() {
        WebComponent dropdown = mock(WebComponent.class);
        AntdSelectOption option = mock(AntdSelectOption.class);
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-open");
        when(popupContainer.findComponents(By.className("sss-select-dropdown"))).thenReturn(List.of(dropdown));
        when(dropdown.findComponentsAs(eq(By.className("sss-select-item-option")), any(Function.class))).thenReturn(
                List.of(option));
        when(option.getText()).thenReturn("target");
        testSubject.selectOption("target");
        verify(option).click();
    }

    @SuppressWarnings("unchecked")
    @Test
    void selectOptionNotFound() {
        WebComponent dropdown = mock(WebComponent.class);
        AntdSelectOption option = mock(AntdSelectOption.class);
        when(element.getDomAttribute("class")).thenReturn("sss-select sss-select-open");
        when(popupContainer.findComponents(By.className("sss-select-dropdown"))).thenReturn(List.of(dropdown));
        when(dropdown.findComponentsAs(eq(By.className("sss-select-item-option")), any(Function.class))).thenReturn(
                List.of(option));
        when(option.getText()).thenReturn("other");
        assertThrows(NoSuchElementException.class, () -> testSubject.selectOption("target"));
    }
}
