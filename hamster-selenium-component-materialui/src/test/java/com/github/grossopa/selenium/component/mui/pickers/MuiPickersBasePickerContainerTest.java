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

package com.github.grossopa.selenium.component.mui.pickers;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.surfaces.MuiToolbar;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiPickersBasePickerContainer}
 *
 * @author Jack Yin
 * @since 1.2
 */
class MuiPickersBasePickerContainerTest {

    MuiPickersBasePickerContainer testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiPickersBasePickerContainer(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("PickersBasePicker-container", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.validateByCss(any(), eq("MuiPickersBasePicker-container"))).thenReturn(true);
        assertTrue(testSubject.validate());
        verify(config, times(1)).validateByCss(any(), anyString());
    }

    @Test
    void validateFalse() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.validateByCss(any(), eq("MuiPickersBasePicker-container"))).thenReturn(false);
        assertFalse(testSubject.validate());
        verify(config, times(1)).validateByCss(any(), anyString());
    }

    @Test
    void getToolbar() {
        WebElement toolbarElement = mock(WebElement.class);
        when(config.getRootCss(MuiToolbar.NAME)).thenReturn("MMuiToolbar-root");
        when(element.findElement(By.className("MMuiToolbar-root"))).thenReturn(toolbarElement);

        MuiToolbar toolbar = testSubject.getToolbar();
        assertEquals(toolbarElement, toolbar.getWrappedElement());
    }

    @Test
    void getToolbarButtons() {
        WebElement button1Element = mock(WebElement.class);
        WebElement button2Element = mock(WebElement.class);
        WebElement button3Element = mock(WebElement.class);
        WebComponent button1 = mock(WebComponent.class);
        WebComponent button2 = mock(WebComponent.class);
        WebComponent button3 = mock(WebComponent.class);
        when(button1.getWrappedElement()).thenReturn(button1Element);
        when(button2.getWrappedElement()).thenReturn(button2Element);
        when(button3.getWrappedElement()).thenReturn(button3Element);

        when(config.getRootCss(MuiButton.NAME)).thenReturn("MuiButton-root");
        when(config.getRootCss(MuiToolbar.NAME)).thenReturn("MuiToolbar-root");

        WebElement toolbarElement = mock(WebElement.class);
        MuiToolbar toolbar = new MuiToolbar(toolbarElement, driver, config);
        when(toolbarElement.findElements(By2.className("MuiButton-root")))
                .thenReturn(newArrayList(button1Element, button2Element, button3Element));

        when(element.findElement(By.className("MuiToolbar-root"))).thenReturn(toolbar);

        List<MuiButton> buttons = testSubject.getToolbarButtons();
        assertEquals(3, buttons.size());
        assertArrayEquals(new WebElement[]{button1Element, button2Element, button3Element},
                buttons.stream().map(MuiButton::getWrappedElement).toArray());
    }

    @Test
    void getAsYearSelection() {
        WebElement yearSelectionContainer = mock(WebElement.class);
        when(config.getCssPrefix()).thenReturn("ddd");
        when(element.findElement(By.className("ddd" + MuiPickersYearSelectionContainer.NAME)))
                .thenReturn(yearSelectionContainer);
        MuiPickersYearSelectionContainer container = testSubject.getAsYearSelection();
        assertEquals(yearSelectionContainer, container.getWrappedElement());
    }

    @Test
    void getAsBasic() {
        WebElement switchHeader = mock(WebElement.class);
        WebElement daysHeader = mock(WebElement.class);
        WebElement transitionContainer = mock(WebElement.class);

        when(config.getCssPrefix()).thenReturn("Mui");

        when(element.findElement(By.className("Mui" + MuiPickersCalendarHeaderSwitchHeader.NAME)))
                .thenReturn(switchHeader);
        when(element.findElement(By.className("Mui" + MuiPickersCalendarHeaderDaysHeader.NAME))).thenReturn(daysHeader);
        when(element.findElement(By.className("Mui" + MuiPickersCalendarTransitionContainer.NAME)))
                .thenReturn(transitionContainer);

        MuiPickersBasicPickerViewComponents components = testSubject.getAsBasic();

        assertEquals(switchHeader, components.getSwitchHeader().getWrappedElement());
        assertEquals(daysHeader, components.getDaysHeader().getWrappedElement());
        assertEquals(transitionContainer, components.getTransitionContainer().getWrappedElement());
    }
}
