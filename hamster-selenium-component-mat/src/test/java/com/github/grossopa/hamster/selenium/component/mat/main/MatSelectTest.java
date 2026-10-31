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
package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.exception.OptionNotClosedException;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatOption;
import com.github.grossopa.hamster.selenium.core.util.SimpleEqualsTester;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatSelectTest {

    MatSelect testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);
    MatOverlayFinder overlayFinder = mock(MatOverlayFinder.class);
    WebDriverWait wait = mock(WebDriverWait.class);
    MatOverlayContainer overlayContainer = mock(MatOverlayContainer.class);

    private void mockPagePanelPresent() {
        WebComponent panel = mock(WebComponent.class);
        when(panel.isDisplayed()).thenReturn(true);
        when(driver.findComponents(By.className("mat-mdc-select-panel"))).thenReturn(newArrayList(panel));
    }

    private void mockPagePanelNotPresent() {
        when(driver.findComponents(By.className("mat-mdc-select-panel"))).thenReturn(newArrayList());
    }

    private void mockOverlayPanelPresent() {
        WebComponent panel = mock(WebComponent.class);
        when(panel.isDisplayed()).thenReturn(true);
        when(overlayContainer.findComponents(By.className("mat-mdc-select-panel"))).thenReturn(newArrayList(panel));
        when(overlayFinder.findTopVisibleContainer()).thenReturn(overlayContainer);
    }

    private void mockNoPanelAnywhere() {
        mockPagePanelNotPresent();
        when(overlayFinder.findTopVisibleContainer()).thenReturn(null);
    }

    private MatOption createOption(String text) {
        MatOption option = mock(MatOption.class);
        when(option.getText()).thenReturn(text);
        return option;
    }

    @SuppressWarnings("unchecked")
    private void mockPanelWithOptions(List<MatOption> options) {
        mockPagePanelPresent();
        WebComponent panel = driver.findComponents(By.className("mat-mdc-select-panel")).get(0);
        doReturn(options).when(panel).findComponentsAs(eq(By.tagName("mat-option")), any());
    }

    @BeforeEach
    @SuppressWarnings("all")
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-mdc-");
        when(config.getTagPrefix()).thenReturn("mat-");
        when(driver.createWait(anyLong())).thenReturn(wait);
        when(wait.until(any())).then(answer -> {
            Function func = answer.getArgument(0);
            return func.apply(driver);
        });
        testSubject = new MatSelect(element, driver, config, overlayFinder);
    }

    @Test
    void constructor1() {
        assertDoesNotThrow(() -> new MatSelect(element, driver, config));
    }

    @Test
    void getComponentName() {
        assertEquals("Select", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-select");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-other");
        assertFalse(testSubject.validate());
    }

    @Test
    void getSelectedValue() {
        WebElement valueElement = mock(WebElement.class);
        when(valueElement.getText()).thenReturn("Selected Option");
        when(element.findElement(By.className("mat-mdc-select-value"))).thenReturn(valueElement);

        assertEquals("Selected Option", testSubject.getSelectedValue());
    }

    @Test
    void getTrigger() {
        WebElement triggerElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-select-trigger"))).thenReturn(triggerElement);

        WebComponent result = testSubject.getTrigger();
        assertNotNull(result);
        assertEquals(triggerElement, result.getWrappedElement());
    }

    @Test
    void openOptionsPanelAlreadyPresent() {
        mockPagePanelPresent();
        testSubject.openOptions();
        verify(element, never()).click();
    }

    @Test
    void openOptionsPanelNotPresent() {
        mockNoPanelAnywhere();
        WebElement triggerElement = mock(WebElement.class);
        when(element.findElement(By.className("mat-mdc-select-trigger"))).thenReturn(triggerElement);

        testSubject.openOptions();
        verify(triggerElement, times(1)).click();
    }

    @Test
    void closeOptionsPanelNotPresent() {
        mockNoPanelAnywhere();
        testSubject.closeOptions();
        verify(element, never()).sendKeys(any(Keys.class));
    }

    @Test
    void closeOptionsPanelPresentAndClosed() {
        mockPagePanelPresent();
        doAnswer(invocation -> {
            mockPagePanelNotPresent();
            return null;
        }).when(element).sendKeys(Keys.ESCAPE);

        testSubject.closeOptions();
        verify(element, times(1)).sendKeys(Keys.ESCAPE);
    }

    @Test
    void closeOptionsPanelPresentButNotClosed() {
        // Use a single panel mock that stays displayed across all calls
        WebComponent panel = mock(WebComponent.class);
        when(panel.isDisplayed()).thenReturn(true);
        when(driver.findComponents(By.className("mat-mdc-select-panel"))).thenReturn(newArrayList(panel));
        // Simulate real WebDriverWait behavior: throws TimeoutException when condition not met
        doThrow(new TimeoutException("timed out")).when(wait).until(any());

        assertThrows(OptionNotClosedException.class, () -> testSubject.closeOptions());
    }

    @Test
    void getOptions() {
        MatOption option1 = createOption("Option 1");
        MatOption option2 = createOption("Option 2");
        mockPanelWithOptions(newArrayList(option1, option2));

        List<MatOption> result = testSubject.getOptions();
        assertEquals(2, result.size());
        assertEquals("Option 1", result.get(0).getText());
    }

    @Test
    void getOptionsPanelNotOpen() {
        mockNoPanelAnywhere();
        assertThrows(IllegalStateException.class, () -> testSubject.getOptions());
    }

    @Test
    void selectByVisibleText() {
        MatOption option1 = createOption("Option 1");
        MatOption option2 = createOption("Option 2");
        mockPanelWithOptions(newArrayList(option1, option2));

        testSubject.selectByVisibleText("Option 2");
        verify(option2, times(1)).click();
        verify(option1, never()).click();
    }

    @Test
    void selectByVisibleTextNotFound() {
        MatOption option1 = createOption("Option 1");
        mockPanelWithOptions(newArrayList(option1));

        testSubject.selectByVisibleText("Non-existent");
        verify(option1, never()).click();
    }

    @Test
    void selectByIndex() {
        MatOption option1 = createOption("Option 1");
        MatOption option2 = createOption("Option 2");
        mockPanelWithOptions(newArrayList(option1, option2));

        testSubject.selectByIndex(1);
        verify(option2, times(1)).click();
    }

    @Test
    void isMultipleTrue() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-select mat-mdc-select-multiple");
        assertTrue(testSubject.isMultiple());
    }

    @Test
    void isMultipleFalse() {
        when(element.getDomAttribute("class")).thenReturn("mat-mdc-select");
        assertFalse(testSubject.isMultiple());
    }

    @Test
    void tryToFindSelectPanelFromPageLevel() {
        mockPagePanelPresent();
        assertTrue(testSubject.tryToFindSelectPanel().isPresent());
    }

    @Test
    void tryToFindSelectPanelFromOverlay() {
        mockPagePanelNotPresent();
        mockOverlayPanelPresent();
        assertTrue(testSubject.tryToFindSelectPanel().isPresent());
    }

    @Test
    void tryToFindSelectPanelNotFound() {
        mockNoPanelAnywhere();
        assertTrue(testSubject.tryToFindSelectPanel().isEmpty());
    }

    @Test
    void tryToFindSelectPanelOverlayNull() {
        mockPagePanelNotPresent();
        when(overlayFinder.findTopVisibleContainer()).thenReturn(null);
        assertTrue(testSubject.tryToFindSelectPanel().isEmpty());
    }

    @Test
    void testEquals() {
        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(
                new MatSelect(element, driver, config, overlayFinder),
                new MatSelect(element, driver, config, overlayFinder));
        tester.addEqualityGroup(
                new MatSelect(mock(WebElement.class), driver, config, overlayFinder));
        tester.addEqualityGroup(
                new MatSelect(element, mock(ComponentWebDriver.class), config, overlayFinder));
        tester.addEqualityGroup(
                new MatSelect(element, driver, mock(MatConfig.class), overlayFinder));
        tester.addEqualityGroup(
                new MatSelect(element, driver, config, mock(MatOverlayFinder.class)));
        tester.testEquals();
    }

    @Test
    void testHashCode() {
        MatSelect select1 = new MatSelect(element, driver, config, overlayFinder);
        MatSelect select2 = new MatSelect(element, driver, config, overlayFinder);
        assertEquals(select1.hashCode(), select2.hashCode());
    }

    @Test
    void testToString() {
        when(overlayFinder.toString()).thenReturn("overlayFinder");
        when(element.toString()).thenReturn("inner-element");
        assertEquals("MatSelect{overlayFinder=overlayFinder, element=inner-element}", testSubject.toString());
    }
}
