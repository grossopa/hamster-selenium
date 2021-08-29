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

package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.action.AutocompleteCloseOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.action.AutocompleteOpenOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.exception.OptionNotClosedException;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatOption;
import com.github.grossopa.hamster.selenium.core.util.SimpleEqualsTester;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

/**
 * Tests for {@link MatAutocomplete}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatAutocompleteTest {

    MatAutocomplete testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    MatOverlayFinder overlayFinder = mock(MatOverlayFinder.class);
    WebDriverWait wait = mock(WebDriverWait.class);

    MatOverlayContainer overlayContainer = mock(MatOverlayContainer.class);
    By optionLocator = mock(By.class);
    WebComponent autocompletePanel = mock(WebComponent.class);
    AutocompleteOpenOptionsAction openOptionsAction = mock(AutocompleteOpenOptionsAction.class);
    AutocompleteCloseOptionsAction closeOptionsAction = mock(AutocompleteCloseOptionsAction.class);

    List<WebComponent> options = newArrayList();
    WebElement inputElement = mock(WebElement.class);

    private void mockAutocompletePanelPresent() {
        when(overlayContainer.findComponents(By2.className("mat-autocomplete-panel"))).thenReturn(
                newArrayList(autocompletePanel));
        when(autocompletePanel.isDisplayed()).thenReturn(true);
        when(overlayFinder.findTopVisibleContainer()).thenReturn(overlayContainer);
    }

    private void mockAutocompletePanelPresentWithDelays() {
        this.mockAutocompletePanelNotFound1();
        doAnswer(answer -> {
            this.mockAutocompletePanelPresent();
            return null;
        }).when(openOptionsAction).open(any(), any());
    }

    private void mockAutocompletePanelNotFound1() {
        when(overlayContainer.findComponents(By2.className("mat-autocomplete-panel"))).thenReturn(newArrayList());
        when(overlayFinder.findTopVisibleContainer()).thenReturn(overlayContainer);
    }

    private void mockAutocompletePanelNotFound2() {
        when(overlayFinder.findTopVisibleContainer()).thenReturn(null);
    }

    private void mockAutocompletePanelNotFound3() {
        when(overlayContainer.findComponents(By2.className("mat-autocomplete-panel"))).thenReturn(
                newArrayList(autocompletePanel));
        when(autocompletePanel.isDisplayed()).thenReturn(false);
        when(overlayFinder.findTopVisibleContainer()).thenReturn(overlayContainer);
    }

    private MatOption createOption(String text, boolean isSelected) {
        WebElement element = mock(WebElement.class);
        MatOption option = mock(MatOption.class);
        when(option.isSelected()).thenReturn(isSelected);
        when(option.getText()).thenReturn(text);
        when(option.getWrappedElement()).thenReturn(element);
        when(element.isSelected()).thenReturn(isSelected);
        when(element.getText()).thenReturn(text);
        return option;
    }

    private void mockAutocompletePanelCloseSuccessful1() {
        this.mockAutocompletePanelPresent();
        doAnswer(answer -> {
            this.mockAutocompletePanelNotFound1();
            return null;
        }).when(closeOptionsAction).close(any(), any(), any());
    }

    private void mockAutocompletePanelCloseSuccessful2() {
        this.mockAutocompletePanelPresent();
        doAnswer(answer -> {
            this.mockAutocompletePanelNotFound2();
            return null;
        }).when(closeOptionsAction).close(any(), any(), any());
    }

    private void mockAutocompletePanelCloseSuccessful3() {
        this.mockAutocompletePanelPresent();
        doAnswer(answer -> {
            this.mockAutocompletePanelNotFound3();
            return null;
        }).when(closeOptionsAction).close(any(), any(), any());
    }

    private void mockAutocompletePanelCloseUnsuccessful1() {
        this.mockAutocompletePanelPresent();
        doAnswer(answer -> {
            this.mockAutocompletePanelPresent();
            return null;
        }).when(closeOptionsAction).close(any(), any(), any());
    }

    private void mockOptionsList() {
        options.add(createOption("Option 1", false));
        options.add(createOption("Option 2", false));
        options.add(createOption("Option 3", true));
        options.add(createOption("Option 4", false));

        when(autocompletePanel.findComponentsAs(eq(optionLocator), any())).thenReturn(options);
    }

    @BeforeEach
    @SuppressWarnings("all")
    void setUp() {
        when(config.getCssPrefix()).thenReturn("mat-");
        when(driver.createWait(anyLong())).thenReturn(wait);
        when(wait.until(any())).then(answer -> {
            Function func = answer.getArgument(0);
            return func.apply(driver);
        });

        when(element.findElement(By.xpath(".//input[contains(@class,\"mat-autocomplete-trigger\")]"))).thenReturn(
                inputElement);

        testSubject = new MatAutocomplete(element, driver, config, overlayFinder, optionLocator, openOptionsAction,
                closeOptionsAction);
    }

    @Test
    void constructor1() {
        assertDoesNotThrow(() -> new MatAutocomplete(element, driver, config));
    }

    @Test
    void constructor2() {
        assertDoesNotThrow(() -> new MatAutocomplete(element, driver, config, overlayFinder));
    }

    @Test
    void constructor3() {
        assertDoesNotThrow(() -> new MatAutocomplete(element, driver, config, overlayFinder, optionLocator));
    }

    @Test
    void getComponentName() {
        assertEquals("Autocomplete", testSubject.getComponentName());
    }

    @Test
    void getInput() {
        WebComponent component = testSubject.getInput();
        assertEquals(inputElement, component.getWrappedElement());
    }

    @Test
    void getOptions2() {
        this.mockAutocompletePanelPresent();
        this.mockOptionsList();
        List<WebComponent> options = testSubject.getOptions2();
        assertEquals(4, options.size());
    }

    @Test
    void getOptions2WithDelays() {
        this.mockAutocompletePanelPresentWithDelays();
        this.mockOptionsList();
        List<WebComponent> options = testSubject.getOptions2(1000L);
        assertEquals(4, options.size());
    }

    @Test
    void getAllSelectedOptions2() {
        this.mockAutocompletePanelPresent();
        this.mockOptionsList();
        List<WebComponent> selectedOption = testSubject.getAllSelectedOptions2();
        assertEquals(1, selectedOption.size());
        assertEquals("Option 3", selectedOption.get(0).getText());
    }

    @Test
    void getAllSelectedOptions2WithDelays() {
        this.mockAutocompletePanelPresentWithDelays();
        this.mockOptionsList();
        List<WebComponent> selectedOption = testSubject.getAllSelectedOptions2(1000L);
        assertEquals(1, selectedOption.size());
        assertEquals("Option 3", selectedOption.get(0).getText());
    }

    @Test
    void openOptions1Successful() {
        this.mockAutocompletePanelPresent();
        WebComponent result = testSubject.openOptions();
        assertEquals(autocompletePanel, result);
        verify(openOptionsAction, never()).open(any(), any());
    }

    @Test
    void openOptions2Successful() {
        this.mockAutocompletePanelNotFound1();
        doAnswer(answer -> {
            this.mockAutocompletePanelPresent();
            return null;
        }).when(openOptionsAction).open(any(), any());
        WebComponent result = testSubject.openOptions();
        assertEquals(autocompletePanel, result);
    }

    @Test
    void openOptions3Successful() {
        this.mockAutocompletePanelNotFound2();
        doAnswer(answer -> {
            this.mockAutocompletePanelPresent();
            return null;
        }).when(openOptionsAction).open(any(), any());
        WebComponent result = testSubject.openOptions();
        assertEquals(autocompletePanel, result);
    }

    @Test
    void openOptions4Failed() {
        this.mockAutocompletePanelNotFound2();
        assertThrows(NoSuchElementException.class, () -> testSubject.openOptions());
    }

    @Test
    void openOptionsWithDelays1Successful() {
        this.mockAutocompletePanelNotFound2();
        doAnswer(answer -> {
            this.mockAutocompletePanelPresent();
            return null;
        }).when(openOptionsAction).open(any(), any());

        WebComponent result = testSubject.openOptions(1000L);
        assertEquals(autocompletePanel, result);
    }

    @Test
    void openOptions2Failed() {
        this.mockAutocompletePanelNotFound2();
        reset(wait);
        when(wait.until(any())).then(answer -> {
            throw new TimeoutException("");
        });
        assertThrows(TimeoutException.class, () -> testSubject.openOptions(1000L));
    }

    @Test
    void closeOptions1AlreadyClosed() {
        this.mockAutocompletePanelNotFound1();
        testSubject.closeOptions();
        assertTrue(overlayContainer.findComponents(By2.className("mat-autocomplete-panel")).isEmpty());
        verify(closeOptionsAction, never()).close(any(), any(), any());
    }

    @Test
    void closeOptionsSuccessful1() {
        this.mockAutocompletePanelCloseSuccessful1();
        testSubject.closeOptions();
        assertTrue(overlayContainer.findComponents(By2.className("mat-autocomplete-panel")).isEmpty());
        verify(closeOptionsAction, times(1)).close(any(), any(), any());
    }

    @Test
    void closeOptionsSuccessful2() {
        this.mockAutocompletePanelCloseSuccessful2();
        testSubject.closeOptions();
        assertNull(overlayFinder.findTopVisibleContainer());
        verify(closeOptionsAction, times(1)).close(any(), any(), any());
    }

    @Test
    void closeOptionsSuccessful3() {
        this.mockAutocompletePanelCloseSuccessful3();
        testSubject.closeOptions();
        assertFalse(autocompletePanel.isDisplayed());
        verify(closeOptionsAction, times(1)).close(any(), any(), any());
    }


    @Test
    void closeOptionsSuccessful2WithDelays() {
        this.mockAutocompletePanelCloseSuccessful2();
        testSubject.closeOptions(1000L);
        assertNull(overlayFinder.findTopVisibleContainer());
        verify(closeOptionsAction, times(1)).close(any(), any(), any());
    }

    @Test
    void closeOptionsSuccessful3WithDelays() {
        this.mockAutocompletePanelCloseSuccessful3();
        testSubject.closeOptions(1000L);
        assertFalse(autocompletePanel.isDisplayed());
        verify(closeOptionsAction, times(1)).close(any(), any(), any());
    }

    @Test
    void closeOptionsSuccessful4WithDelays() {
        this.mockAutocompletePanelCloseSuccessful3();
        testSubject.closeOptions(1000L);
        assertFalse(autocompletePanel.isDisplayed());
        verify(closeOptionsAction, times(1)).close(any(), any(), any());
    }

    @Test
    void closeOptionsUnsuccessful1WithDelays() {
        this.mockAutocompletePanelCloseUnsuccessful1();
        testSubject.closeOptions(1000L);
        assertTrue(autocompletePanel.isDisplayed());
        verify(closeOptionsAction, times(1)).close(any(), any(), any());
    }


    @Test
    void closeOptionsUnsuccessful1() {
        this.mockAutocompletePanelPresent();
        assertThrows(OptionNotClosedException.class, () -> testSubject.closeOptions());
    }

    @Test
    void getFirstSelectedOption() {
        this.mockAutocompletePanelPresent();
        this.mockOptionsList();
        WebElement element = testSubject.getFirstSelectedOption();
        assertEquals("Option 3", element.getText());
    }

    @Test
    void getFirstSelectedOptionWithDelays() {
        this.mockAutocompletePanelPresentWithDelays();
        this.mockOptionsList();
        WebElement element = testSubject.getFirstSelectedOption(1000L);
        assertEquals("Option 3", element.getText());
    }

    @Test
    void getFirstSelectedOptionNull() {
        this.mockAutocompletePanelPresent();
        WebElement element = testSubject.getFirstSelectedOption();
        assertNull(element);
    }

    @Test
    void selectByVisibleText() {
        this.mockAutocompletePanelPresent();
        this.mockOptionsList();
        testSubject.selectByVisibleText("Option 2");
        verify(options.get(1), times(1)).click();
    }

    @Test
    void selectByVisibleTextNoSuchOption() {
        this.mockAutocompletePanelPresent();
        this.mockOptionsList();
        testSubject.selectByVisibleText("Option 55");
        verify(options.get(1), never()).click();
    }

    @Test
    void selectByVisibleTextWithDelays() {
        this.mockAutocompletePanelPresentWithDelays();
        this.mockOptionsList();
        testSubject.selectByVisibleText("Option 2", 1000L);
        verify(options.get(1), times(1)).click();
    }

    @Test
    void selectByIndex() {
        this.mockAutocompletePanelPresent();
        this.mockOptionsList();
        testSubject.selectByIndex(1);
        verify(options.get(1), times(1)).click();
    }

    @Test
    void selectByIndexNoSuchOption() {
        this.mockAutocompletePanelPresent();
        this.mockOptionsList();
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.selectByIndex(6));
    }

    @Test
    void selectByIndexWithDelays() {
        this.mockAutocompletePanelPresentWithDelays();
        this.mockOptionsList();
        testSubject.selectByIndex(1, 1000L);
        verify(options.get(1), times(1)).click();
    }

    @Test
    void selectByValue() {
        this.mockAutocompletePanelPresent();
        this.mockOptionsList();
        testSubject.selectByValue("Option 2");
        verify(options.get(1), times(1)).click();
    }

    @Test
    void selectByValueNoSuchOption() {
        this.mockAutocompletePanelPresent();
        this.mockOptionsList();
        testSubject.selectByValue("33333");
        verify(options.get(1), never()).click();
    }

    @Test
    void selectByValueWithDelays() {
        this.mockAutocompletePanelPresentWithDelays();
        this.mockOptionsList();
        testSubject.selectByValue("Option 2", 1000L);
        verify(options.get(1), times(1)).click();
    }

    @Test
    void deselectAll() {
        when(inputElement.getAttribute("value")).thenReturn("Option 2");
        testSubject.deselectAll();
        verify(inputElement, times(8)).sendKeys(BACK_SPACE);
    }

    @Test
    void deselectAllWithDelays() {
        when(inputElement.getAttribute("value")).thenReturn("Option 2");
        testSubject.deselectAll(1000L);
        verify(inputElement, times(8)).sendKeys(BACK_SPACE);
    }

    @Test
    void deselectByValue() {
        when(inputElement.getAttribute("value")).thenReturn("Option 2");
        testSubject.deselectByValue(null);
        verify(inputElement, times(8)).sendKeys(BACK_SPACE);
    }

    @Test
    void deselectByValueWithDelays() {
        when(inputElement.getAttribute("value")).thenReturn("Option 2");
        testSubject.deselectByValue(null, 1000L);
        verify(inputElement, times(8)).sendKeys(BACK_SPACE);
    }

    @Test
    void deselectByIndex() {
        when(inputElement.getAttribute("value")).thenReturn("Option 2");
        testSubject.deselectByIndex(1);
        verify(inputElement, times(8)).sendKeys(BACK_SPACE);
    }

    @Test
    void deselectByIndexWithDelays() {
        when(inputElement.getAttribute("value")).thenReturn("Option 2");
        testSubject.deselectByIndex(1, 1000L);
        verify(inputElement, times(8)).sendKeys(BACK_SPACE);
    }

    @Test
    void deselectByVisibleText() {
        when(inputElement.getAttribute("value")).thenReturn("Option 2");
        testSubject.deselectByVisibleText(null);
        verify(inputElement, times(8)).sendKeys(BACK_SPACE);
    }

    @Test
    void deselectByVisibleTextWithDelays() {
        when(inputElement.getAttribute("value")).thenReturn("Option 2");
        testSubject.deselectByVisibleText(null, 1000L);
        verify(inputElement, times(8)).sendKeys(BACK_SPACE);
    }

    @Test
    void isMultiple() {
        assertFalse(testSubject.isMultiple());
    }

    @Test
    void testEquals() {
        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(
                new MatAutocomplete(element, driver, config, overlayFinder, optionLocator, openOptionsAction,
                        closeOptionsAction),
                new MatAutocomplete(element, driver, config, overlayFinder, optionLocator, openOptionsAction,
                        closeOptionsAction));
        tester.addEqualityGroup(
                new MatAutocomplete(mock(WebElement.class), driver, config, overlayFinder, optionLocator,
                        openOptionsAction, closeOptionsAction));
        tester.addEqualityGroup(
                new MatAutocomplete(element, mock(ComponentWebDriver.class), config, overlayFinder, optionLocator,
                        openOptionsAction, closeOptionsAction));
        tester.addEqualityGroup(
                new MatAutocomplete(element, driver, mock(MatConfig.class), overlayFinder, optionLocator,
                        openOptionsAction, closeOptionsAction));
        tester.addEqualityGroup(new MatAutocomplete(element, driver, config, null, optionLocator, openOptionsAction,
                closeOptionsAction));
        tester.addEqualityGroup(new MatAutocomplete(element, driver, config, overlayFinder, null, openOptionsAction,
                closeOptionsAction));
        tester.testEquals();
    }

    @Test
    void testToString() {
        when(overlayFinder.toString()).thenReturn("overlayFinder");
        when(optionLocator.toString()).thenReturn("optionLocator");
        when(openOptionsAction.toString()).thenReturn("openOptionsAction");
        when(closeOptionsAction.toString()).thenReturn("closeOptionsAction");
        when(element.toString()).thenReturn("element");

        assertEquals("MatAutocomplete{overlayFinder=overlayFinder, optionLocator=optionLocator, "
                        + "openOptionsAction=openOptionsAction, closeOptionsAction=closeOptionsAction, element=element}",
                testSubject.toString());
    }

    @Test
    void validate() {
        when(config.getCssPrefix()).thenReturn("mat-");
        when(element.getAttribute("class")).thenReturn("mat-autocomplete-trigger");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(config.getCssPrefix()).thenReturn("mat-");
        when(element.getAttribute("class")).thenReturn("mat-autocomplete-trigger-some-other");
        assertFalse(testSubject.validate());
    }
}
