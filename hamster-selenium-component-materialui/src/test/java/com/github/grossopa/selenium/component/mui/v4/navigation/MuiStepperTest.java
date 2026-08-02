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

package com.github.grossopa.selenium.component.mui.v4.navigation;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiStepper}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiStepperTest {

    MuiStepper testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = spy(new MuiStepper(element, driver, config));
        when(config.getCssPrefix()).thenReturn("Mui");
    }

    @Test
    void getComponentName() {
        assertEquals("Stepper", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertTrue(testSubject.versions().contains(V4));
        assertTrue(testSubject.versions().contains(V5));
        assertTrue(testSubject.versions().contains(V6));
    }

    @Test
    void getActiveStep() {
        WebComponent step1 = mock(WebComponent.class);
        WebComponent step2 = mock(WebComponent.class);
        WebComponent step3 = mock(WebComponent.class);
        List<WebComponent> steps = asList(step1, step2, step3);

        when(config.isSelected(step1)).thenReturn(false);
        when(config.isChecked(step1)).thenReturn(false);
        when(step1.getDomAttribute("class")).thenReturn("");

        when(config.isSelected(step2)).thenReturn(false);
        when(config.isChecked(step2)).thenReturn(false);
        when(step2.getDomAttribute("class")).thenReturn("");

        when(config.isSelected(step3)).thenReturn(true);
        when(config.isChecked(step3)).thenReturn(true);
        when(step3.getDomAttribute("class")).thenReturn("Mui-selected");

        doReturn(steps).when(testSubject).getSteps();

        assertEquals(2, testSubject.getActiveStep());
    }

    @Test
    void getActiveStepByCssClass() {
        WebComponent step1 = mock(WebComponent.class);
        WebComponent step2 = mock(WebComponent.class);
        WebComponent step3 = mock(WebComponent.class);
        List<WebComponent> steps = asList(step1, step2, step3);

        when(config.isSelected(step1)).thenReturn(false);
        when(config.isChecked(step1)).thenReturn(false);
        when(step1.getDomAttribute("class")).thenReturn("");
        when(step1.findComponents(any(By.class))).thenReturn(Collections.emptyList());

        when(config.isSelected(step2)).thenReturn(false);
        when(config.isChecked(step2)).thenReturn(false);
        when(step2.getDomAttribute("class")).thenReturn("");
        when(step2.findComponents(any(By.class))).thenReturn(Collections.emptyList());

        when(config.isSelected(step3)).thenReturn(false);
        when(config.isChecked(step3)).thenReturn(false);
        when(step3.getDomAttribute("class")).thenReturn("MuiStep-active");

        doReturn(steps).when(testSubject).getSteps();

        assertEquals(2, testSubject.getActiveStep());
    }

    @Test
    void getActiveStep_none() {
        WebComponent step1 = mock(WebComponent.class);
        WebComponent step2 = mock(WebComponent.class);
        List<WebComponent> steps = asList(step1, step2);

        when(config.isSelected(step1)).thenReturn(false);
        when(config.isChecked(step1)).thenReturn(false);
        when(step1.getDomAttribute("class")).thenReturn("");
        when(step1.findComponents(any(By.class))).thenReturn(Collections.emptyList());

        when(config.isSelected(step2)).thenReturn(false);
        when(config.isChecked(step2)).thenReturn(false);
        when(step2.getDomAttribute("class")).thenReturn("");
        when(step2.findComponents(any(By.class))).thenReturn(Collections.emptyList());

        doReturn(steps).when(testSubject).getSteps();

        assertEquals(-1, testSubject.getActiveStep());
    }

    @Test
    void getSteps() {
        WebElement stepElement1 = mock(WebElement.class);
        WebElement stepElement2 = mock(WebElement.class);
        List<WebElement> stepElements = asList(stepElement1, stepElement2);

        WebComponent stepComponent1 = mock(WebComponent.class);
        WebComponent stepComponent2 = mock(WebComponent.class);

        when(element.findElements(By.className("MuiStep-root"))).thenReturn(stepElements);
        when(driver.mapElement(stepElement1)).thenReturn(stepComponent1);
        when(driver.mapElement(stepElement2)).thenReturn(stepComponent2);

        List<WebComponent> steps = testSubject.getSteps();
        assertEquals(2, steps.size());
        assertEquals(stepComponent1, steps.get(0));
        assertEquals(stepComponent2, steps.get(1));
    }

    @Test
    void getStepCount() {
        WebComponent step1 = mock(WebComponent.class);
        WebComponent step2 = mock(WebComponent.class);
        WebComponent step3 = mock(WebComponent.class);
        List<WebComponent> steps = asList(step1, step2, step3);

        doReturn(steps).when(testSubject).getSteps();

        assertEquals(3, testSubject.getStepCount());
    }

    @Test
    void isVertical() {
        when(element.getDomAttribute("class")).thenReturn("MuiStepper-vertical");
        assertTrue(testSubject.isVertical());

        when(element.getDomAttribute("class")).thenReturn("MuiStepper-horizontal");
        assertFalse(testSubject.isVertical());
    }

    @Test
    void getStepLabels() {
        WebComponent step1 = mock(WebComponent.class);
        WebComponent step2 = mock(WebComponent.class);
        WebComponent step3 = mock(WebComponent.class);
        List<WebComponent> steps = asList(step1, step2, step3);

        WebComponent label1 = mock(WebComponent.class);
        WebComponent label2 = mock(WebComponent.class);

        when(label1.getText()).thenReturn("Step 1");
        when(label2.getText()).thenReturn("Step 2");

        when(step1.findComponent(By.className("MuiStepLabel-label"))).thenReturn(label1);
        when(step2.findComponent(By.className("MuiStepLabel-label"))).thenReturn(label2);
        when(step3.findComponent(By.className("MuiStepLabel-label"))).thenThrow(
                new RuntimeException("Element not found"));

        doReturn(steps).when(testSubject).getSteps();

        List<String> labels = testSubject.getStepLabels();
        assertEquals(3, labels.size());
        assertEquals("Step 1", labels.get(0));
        assertEquals("Step 2", labels.get(1));
        assertEquals("", labels.get(2));
    }
}
