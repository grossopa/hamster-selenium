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

package com.github.grossopa.selenium.component.mui.v4.lab;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.action.CloseOptionsAction;
import com.github.grossopa.selenium.component.mui.action.OpenOptionsAction;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.exception.OptionNotClosedException;
import com.github.grossopa.selenium.component.mui.v4.finder.MuiModalFinder;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiAutocomplete}
 *
 * @author Jack Yin
 * @since 1.3
 */
@SuppressWarnings("deprecation")
class MuiAutocompleteTest {

    MuiAutocomplete testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    WebDriver.TargetLocator targetLocator = mock(WebDriver.TargetLocator.class);
    WebDriverWait driverWait = mock(WebDriverWait.class);

    MuiConfig config = mock(MuiConfig.class);
    By optionLocator = By.id("option");
    MuiAutocompleteTagLocators tagLocators = mock(MuiAutocompleteTagLocators.class);
    OpenOptionsAction openOptionsAction = mock(OpenOptionsAction.class);
    CloseOptionsAction closeOptionsAction = mock(CloseOptionsAction.class);

    WebElement labelElement = mock(WebElement.class);
    WebElement inputElement = mock(WebElement.class);
    WebElement clearButtonElement = mock(WebElement.class);
    WebElement popupButtonElement = mock(WebElement.class);
    WebComponent overlay = mock(WebComponent.class);
    List<WebComponent> options = newArrayList(createOption("option1"), createOption("option2"), createOption("option3"),
            createOption("option4"));
    List<WebElement> visibleTags = newArrayList(createTag("option5"), createTag("option6"), createTag("option7"));

    private WebComponent createOption(String text) {
        WebComponent option = mock(WebComponent.class);
        when(option.getText()).thenReturn(text);
        return option;
    }

    private WebElement createTag(String text) {
        WebElement tag = mock(WebElement.class);
        when(tag.getText()).thenReturn(text);
        when(tag.getAttribute("value")).thenReturn("value " + text);
        WebElement deleteButton = mock(WebElement.class);
        when(tag.findElement(By.className("deleteButton"))).thenReturn(deleteButton);
        doAnswer(answer -> {
            visibleTags.remove(tag);
            return null;
        }).when(deleteButton).click();
        return tag;
    }

    @BeforeEach
    @SuppressWarnings({"unchecked", "rawtypes"})
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.getOverlayAbsolutePath()).thenReturn("/html/body/some/app");

        when(driver.switchTo()).thenReturn(targetLocator);

        when(targetLocator.activeElement()).thenReturn(inputElement);

        when(element.findElement(By.className("Mui" + MuiAutocomplete.INPUT_NAME))).thenReturn(inputElement);
        when(element.findElement(By.tagName("label"))).thenReturn(labelElement);
        when(element.findElement(By.tagName("input"))).thenReturn(inputElement);
        when(element.findElement(By.className("MuiAutocomplete-clearIndicator"))).thenReturn(clearButtonElement);
        when(element.findElement(By.className("MuiAutocomplete-popupIndicator"))).thenReturn(popupButtonElement);

        when(inputElement.findElements(By.className("MuiAutocomplete-tag"))).thenReturn(visibleTags);

        when(tagLocators.getLabelFinder()).thenReturn(WebElement::getText);
        when(tagLocators.getValueFinder()).thenReturn(element -> element.getAttribute("value"));
        when(tagLocators.getDeleteButtonLocator()).thenReturn(By.className("deleteButton"));

        when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(singletonList(overlay));
        when(driver.createWait(anyLong())).thenReturn(driverWait);
        when(driverWait.until(any())).then(answer -> {
            Function function = answer.getArgument(0);
            return function.apply(driver);
        });

        when(overlay.isDisplayed()).thenReturn(true);
        when(overlay.getAttribute("class")).thenReturn("MuiAutocomplete-popper some-other-class");
        when(overlay.findComponents(optionLocator)).thenReturn(options);

        testSubject = new MuiAutocomplete(element, driver, config, optionLocator, tagLocators, openOptionsAction,
                closeOptionsAction);
    }

    @Test
    void constructor1() {
        MuiAutocomplete autocomplete = new MuiAutocomplete(element, driver, config);
        assertNotNull(autocomplete.getOptionLocator());
        assertNotNull(autocomplete.getTagLocators());
        assertNotNull(autocomplete.getOpenOptionsAction());
        assertNotNull(autocomplete.getCloseOptionsAction());
    }

    @Test
    void constructor2() {
        MuiAutocomplete autocomplete = new MuiAutocomplete(element, driver, config, optionLocator);
        assertNotNull(autocomplete.getTagLocators());
        assertNotNull(autocomplete.getOpenOptionsAction());
        assertNotNull(autocomplete.getCloseOptionsAction());
    }

    @Test
    void constructor3() {
        MuiAutocomplete autocomplete = new MuiAutocomplete(element, driver, config, optionLocator, tagLocators);
        assertNotNull(autocomplete.getOpenOptionsAction());
        assertNotNull(autocomplete.getCloseOptionsAction());
    }

    @Test
    void getComponentName() {
        assertEquals("Autocomplete", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5}, testSubject.versions().toArray());
    }

    @Test
    void isEnabledTrue() {
        when(config.isDisabled(argThat(component -> component.getWrappedElement() == inputElement))).thenReturn(false);
        assertTrue(testSubject.isEnabled());
    }


    @Test
    void isEnabledFalse() {
        when(config.isDisabled(argThat(component -> component.getWrappedElement() == inputElement))).thenReturn(true);
        assertFalse(testSubject.isEnabled());
    }

    @Test
    void getLabel() {
        assertEquals(labelElement, testSubject.getLabel().getWrappedElement());
    }

    @Test
    void getInput() {
        assertEquals(inputElement, testSubject.getInput().getWrappedElement());
    }

    @Test
    void getClearButton() {
        assertEquals(clearButtonElement, testSubject.getClearButton().getWrappedElement());
    }

    @Test
    void getPopupButton() {
        assertEquals(popupButtonElement, testSubject.getPopupButton().getWrappedElement());
    }

    @Test
    void getOptions2() {
        assertEquals(options, testSubject.getOptions2());
    }

    @Test
    void getAllSelectedOptions2() {
        assertArrayEquals(visibleTags.toArray(),
                testSubject.getAllSelectedOptions2().stream().map(WebComponent::getWrappedElement).toArray());
    }

    @Test
    void openOptions() {
        assertEquals(overlay, testSubject.openOptions());
        verify(openOptionsAction, never()).open(any(), any());
    }

    @Test
    void openOptionsNotFocused() {
        when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(emptyList());
        doAnswer(answer -> {
            when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(singletonList(overlay));
            return null;
        }).when(openOptionsAction).open(any(), any());

        when(targetLocator.activeElement()).thenReturn(null);
        assertEquals(overlay, testSubject.openOptions());
        verify(openOptionsAction, times(1)).open(any(), any());
        verify(driver, times(1)).moveTo(
                argThat(element -> ((WebComponent) element).getWrappedElement() == inputElement));
    }

    @Test
    void openOptionsTryFocus() {
        assertEquals(overlay, testSubject.openOptions());
        verify(openOptionsAction, never()).open(any(), any());
    }

    @Test
    void openOptionsFirstTimeNull() {
        boolean[] firstTimeAccess = new boolean[1];
        when(driver.findComponents(By.xpath("/html/body/some/app/div"))).then(answer -> {
            if (firstTimeAccess[0]) {
                return singletonList(overlay);
            } else {
                firstTimeAccess[0] = true;
                return emptyList();
            }
        });

        assertEquals(overlay, testSubject.openOptions());
        verify(openOptionsAction, times(1)).open(any(), any());
    }

    @Test
    void openOptionsAlwaysNull() {
        when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(emptyList());
        assertThrows(NoSuchElementException.class, () -> testSubject.openOptions());
    }

    @Test
    void openOptionsWithDelays() {
        when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(emptyList());
        doAnswer(answer -> {
            when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(singletonList(overlay));
            return null;
        }).when(openOptionsAction).open(any(), any());
        testSubject.openOptions(1000L);
        verify(driverWait, times(1)).until(any());
    }

    @Test
    void closeOptions() {
        doAnswer(answer -> {
            when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(emptyList());
            return null;
        }).when(closeOptionsAction).close(any(), any(), any());
        testSubject.closeOptions();
        verify(closeOptionsAction, times(1)).close(any(), any(), any());
    }

    @Test
    void closeOptionsNotFoundOverlay() {
        when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(emptyList());
        testSubject.closeOptions();
        verify(closeOptionsAction, never()).close(any(), any(), any());
    }

    @Test
    void closeOptionsSuccessHidden() {
        when(overlay.isDisplayed()).thenReturn(false);
        assertDoesNotThrow(() -> testSubject.closeOptions());
    }

    @Test
    void closeOptionsSuccessFailed() {
        when(overlay.isDisplayed()).thenReturn(true);
        assertThrows(OptionNotClosedException.class, () -> testSubject.closeOptions());
    }

    @Test
    void closeOptionsWithDelays() {
        doAnswer(answer -> {
            when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(emptyList());
            return null;
        }).when(closeOptionsAction).close(any(), any(), any());
        testSubject.closeOptions(1000L);
        verify(closeOptionsAction, times(1)).close(any(), any(), any());
        verify(driverWait, times(1)).until(any());
    }

    @Test
    void isMultiple() {
        assertTrue(testSubject.isMultiple());
    }

    @Test
    void getOptions() {
        assertArrayEquals(options.toArray(), testSubject.getOptions().toArray());
    }

    @Test
    void getAllSelectedOptions() {
        assertArrayEquals(visibleTags.toArray(), testSubject.getAllSelectedOptions().stream()
                .map(element -> ((WebComponent) element).getWrappedElement()).toArray());
    }

    @Test
    void getFirstSelectedOption() {
        assertEquals("option5", testSubject.getFirstSelectedOption().getText());
    }

    @Test
    void getFirstSelectedOptionNull() {
        visibleTags.clear();
        assertNull(testSubject.getFirstSelectedOption());
    }

    @Test
    void selectByVisibleText() {
        testSubject.selectByVisibleText("option2");
        verify(options.get(1), times(1)).click();
    }

    @Test
    void selectByVisibleTextNone() {
        testSubject.selectByVisibleText("333");
        verify(options.get(0), never()).click();
        verify(options.get(1), never()).click();
        verify(options.get(2), never()).click();
        verify(options.get(3), never()).click();
    }

    @Test
    void selectByIndex() {
        testSubject.selectByIndex(3);
        verify(options.get(3), times(1)).click();
    }

    @Test
    void selectByValue() {
        testSubject.selectByValue("option2");
        verify(options.get(1), times(1)).click();
    }

    @Test
    void selectByValueWithDelays() {
        testSubject.selectByValue("option2", 1000L);
        verify(options.get(1), times(1)).click();
    }

    @Test
    void selectByValueNone() {
        testSubject.selectByValue("333");
        verify(options.get(0), never()).click();
        verify(options.get(1), never()).click();
        verify(options.get(2), never()).click();
        verify(options.get(3), never()).click();
    }

    @Test
    void deselectAll() {
        assertFalse(testSubject.getAllSelectedOptions2().isEmpty());
        testSubject.deselectAll();
        assertTrue(testSubject.getAllSelectedOptions2().isEmpty());
    }

    @Test
    void deselectAllWithDelays() {
        assertFalse(testSubject.getAllSelectedOptions2().isEmpty());
        testSubject.deselectAll(1000L);
        assertTrue(testSubject.getAllSelectedOptions2().isEmpty());
    }

    @Test
    void deselectAllWithDisabled() {
        WebElement disabledTag = createTag("option8");
        when(config.isDisabled(argThat(component -> component.getWrappedElement() == disabledTag))).thenReturn(true);
        visibleTags.add(0, disabledTag);
        assertFalse(testSubject.getAllSelectedOptions2().isEmpty());
        testSubject.deselectAll();
        assertEquals(1, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByValue() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByValue("value option6");
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByValueWithDelays() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByValue("value option6", 1000L);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByValueNotExists() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByValue("value 6666666");
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByIndex() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByIndex(2);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByIndexWithDelays() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByIndex(2, 1000L);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByVisibleText() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByVisibleText("option7");
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByVisibleTextWithDelays() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByVisibleText("option7", 1000L);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByVisibleTextNotExists() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByVisibleText("dddddd");
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void isNoOptions() {
        WebComponent noOptions = mock(WebComponent.class);
        when(overlay.findComponents(By.className("MuiAutocomplete-noOptions"))).thenReturn(singletonList(noOptions));
        assertTrue(testSubject.isNoOptions());
    }

    @Test
    void isNoOptionsFalse() {
        when(overlay.findComponents(By.className("MuiAutocomplete-noOptions"))).thenReturn(emptyList());
        assertFalse(testSubject.isNoOptions());
    }

    @Test
    void getModalFinder() {
        assertEquals(MuiModalFinder.class, testSubject.getModalFinder().getClass());
    }

    @Test
    void getOptionLocator() {
        assertEquals(optionLocator, testSubject.getOptionLocator());
    }

    @Test
    void getOpenOptionsAction() {
        assertEquals(openOptionsAction, testSubject.getOpenOptionsAction());
    }

    @Test
    void getCloseOptionsAction() {
        assertEquals(closeOptionsAction, testSubject.getCloseOptionsAction());
    }

    @Test
    void getCloseOptionsActionTrigger() {
        MuiAutocomplete autocomplete = new MuiAutocomplete(element, driver, config);
        when(element.findElement(By.tagName("input"))).thenReturn(inputElement);
        autocomplete.getCloseOptionsAction().close(new DefaultWebComponent(inputElement, driver), null, driver);
        verify(inputElement, times(1)).sendKeys(Keys.ESCAPE);
    }

    @Test
    void getTagLocators() {
        assertEquals(tagLocators, testSubject.getTagLocators());
    }

    @Test
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        By optionLocator1 = mock(By.class);
        OpenOptionsAction openOptionsAction1 = mock(OpenOptionsAction.class);
        CloseOptionsAction closeOptionsAction1 = mock(CloseOptionsAction.class);
        MuiAutocompleteTagLocators tagLocators1 = mock(MuiAutocompleteTagLocators.class);

        WebElement element2 = mock(WebElement.class);
        By optionLocator2 = mock(By.class);
        OpenOptionsAction openOptionsAction2 = mock(OpenOptionsAction.class);
        CloseOptionsAction closeOptionsAction2 = mock(CloseOptionsAction.class);
        MuiAutocompleteTagLocators tagLocators2 = mock(MuiAutocompleteTagLocators.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(
                new MuiAutocomplete(element1, driver, config, optionLocator1, tagLocators1, openOptionsAction1,
                        closeOptionsAction1),
                new MuiAutocomplete(element1, driver, config, optionLocator1, tagLocators1, openOptionsAction1,
                        closeOptionsAction1));
        tester.addEqualityGroup(
                new MuiAutocomplete(element2, driver, config, optionLocator1, tagLocators1, openOptionsAction1,
                        closeOptionsAction1));
        tester.addEqualityGroup(
                new MuiAutocomplete(element1, driver, config, optionLocator2, tagLocators1, openOptionsAction1,
                        closeOptionsAction1));
        tester.addEqualityGroup(
                new MuiAutocomplete(element1, driver, config, optionLocator1, tagLocators2, openOptionsAction1,
                        closeOptionsAction1));
        tester.addEqualityGroup(
                new MuiAutocomplete(element1, driver, config, optionLocator1, tagLocators1, openOptionsAction2,
                        closeOptionsAction1));
        tester.addEqualityGroup(
                new MuiAutocomplete(element1, driver, config, optionLocator1, tagLocators1, openOptionsAction1,
                        closeOptionsAction2));
        tester.testEquals();
    }

    @Test
    void testToString() {
        WebElement element = mock(WebElement.class);
        By optionLocator = mock(By.class);
        OpenOptionsAction openOptionsAction = mock(OpenOptionsAction.class);
        CloseOptionsAction closeOptionsAction = mock(CloseOptionsAction.class);
        MuiAutocompleteTagLocators tagLocators = mock(MuiAutocompleteTagLocators.class);

        when(driver.toString()).thenReturn("driver-toString");
        when(config.toString()).thenReturn("config-toString");
        when(element.toString()).thenReturn("element-toString");
        when(optionLocator.toString()).thenReturn("optionLocator-toString");
        when(openOptionsAction.toString()).thenReturn("openOptionsAction-toString");
        when(closeOptionsAction.toString()).thenReturn("closeOptionsAction-toString");
        when(tagLocators.toString()).thenReturn("tagLocators-toString");

        testSubject = new MuiAutocomplete(element, driver, config, optionLocator, tagLocators, openOptionsAction,
                closeOptionsAction);

        assertEquals("MuiAutocomplete{modalFinder=MuiModalFinder{driver=driver-toString, "
                + "config=config-toString}, optionLocator=optionLocator-toString, "
                + "openOptionsAction=openOptionsAction-toString, closeOptionsAction=closeOptionsAction-toString, "
                + "tagLocators=tagLocators-toString}", testSubject.toString());
    }

    @Test
    void isLoadingPositive() {
        WebComponent loading = mock(WebComponent.class);
        when(loading.isDisplayed()).thenReturn(true);
        when(overlay.findComponents(By.className("MuiAutocomplete-loading"))).thenReturn(singletonList(loading));
        assertTrue(testSubject.isLoading());
    }


    @Test
    void isLoadingNegativeNoOverlay() {
        when(driver.findComponents(By.xpath("/html/body/some/app/div"))).thenReturn(emptyList());
        assertFalse(testSubject.isLoading());
    }

    @Test
    void isLoadingNegativeNoLoading() {
        WebComponent loading = mock(WebComponent.class);
        when(loading.isDisplayed()).thenReturn(false);
        when(overlay.findComponents(By.className("MuiAutocomplete-loading"))).thenReturn(emptyList());
        assertFalse(testSubject.isLoading());
    }

    @Test
    void isLoadingNegativeLoadingNotDisplayed() {
        WebComponent loading = mock(WebComponent.class);
        when(loading.isDisplayed()).thenReturn(false);
        when(overlay.findComponents(By.className("MuiAutocomplete-loading"))).thenReturn(singletonList(loading));
        assertFalse(testSubject.isLoading());
    }
}
