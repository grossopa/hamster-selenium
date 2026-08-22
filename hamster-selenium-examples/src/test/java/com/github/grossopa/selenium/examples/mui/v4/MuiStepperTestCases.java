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

package com.github.grossopa.selenium.examples.mui.v4;

import com.github.grossopa.selenium.component.mui.v4.navigation.MuiStepper;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for MuiStepper component.
 *
 * @author Jack Yin
 * @since 1.0
 * @see MuiStepper
 */
public class MuiStepperTestCases extends AbstractBrowserSupport {

    /**
     * Tests the horizontal linear stepper.
     *
     * <p>Validates:
     * <ul>
     *   <li>Stepper component can be found and validated</li>
     *   <li>Step count is correct</li>
     *   <li>Active step is initially 0</li>
     *   <li>Step labels are correctly retrieved</li>
     *   <li>Stepper is not vertical (horizontal mode)</li>
     *   <li>Navigate through steps using Next/Back buttons</li>
     * </ul>
     * </p>
     */
    @SuppressWarnings("squid:S2925")
    public void testHorizontalLinearStepper() {
        driver.navigate().to("https://v4.mui.com/components/steppers/");

        MuiStepper stepper = driver.findComponent(By.id("HorizontalLinearStepper.js"))
                .findComponent(By2.parent())
                .findComponent(By.className("MuiStepper-root"))
                .as(mui()).toStepper();

        assertTrue(stepper.validate());
        assertFalse(stepper.isVertical());
        assertEquals(3, stepper.getStepCount());
        assertEquals(0, stepper.getActiveStep());

        List<String> labels = stepper.getStepLabels();
        assertEquals(3, labels.size());
        assertEquals("Select campaign settings", labels.get(0));
        assertEquals("Create an ad group", labels.get(1));
        assertEquals("Create an ad", labels.get(2));

        List<WebComponent> steps = stepper.getSteps();
        assertEquals(3, steps.size());

        // click Next to advance to step 1
        driver.findComponent(By.id("HorizontalLinearStepper.js"))
                .findComponent(By2.parent())
                .findComponent(xpathBuilder().anywhereRelative("span").text().contains("Next").parent().build())
                .click();
        driver.threadSleep(500L);
        assertEquals(1, stepper.getActiveStep());

        // click Back to go back to step 0
        driver.findComponent(By.id("HorizontalLinearStepper.js"))
                .findComponent(By2.parent())
                .findComponent(xpathBuilder().anywhereRelative("span").text().contains("Back").parent().build())
                .click();
        driver.threadSleep(500L);
        assertEquals(0, stepper.getActiveStep());
    }

    /**
     * Tests the vertical linear stepper.
     *
     * <p>Validates:
     * <ul>
     *   <li>Stepper is in vertical orientation</li>
     *   <li>Step count and labels are correct</li>
     *   <li>Active step tracking works</li>
     * </ul>
     * </p>
     */
    @SuppressWarnings("squid:S2925")
    public void testVerticalLinearStepper() {
        driver.navigate().to("https://v4.mui.com/components/steppers/");

        MuiStepper stepper = driver.findComponent(By.id("VerticalLinearStepper.js"))
                .findComponent(By2.parent())
                .findComponent(By.className("MuiStepper-root"))
                .as(mui()).toStepper();

        assertTrue(stepper.validate());
        assertTrue(stepper.isVertical());
        assertEquals(3, stepper.getStepCount());
        assertEquals(0, stepper.getActiveStep());

        List<String> labels = stepper.getStepLabels();
        assertEquals(3, labels.size());

        // click Next to advance
        driver.findComponent(By.id("VerticalLinearStepper.js"))
                .findComponent(By2.parent())
                .findComponent(xpathBuilder().anywhereRelative("span").text().contains("Next").parent().build())
                .click();
        driver.threadSleep(500L);
        assertEquals(1, stepper.getActiveStep());
    }

    /**
     * Tests multiple steppers on the same page to verify they are independent.
     */
    public void testMultipleSteppers() {
        driver.navigate().to("https://v4.mui.com/components/steppers/");

        List<MuiStepper> steppers = driver.findComponents(By.className("MuiStepper-root")).stream()
                .map(component -> component.as(mui()).toStepper())
                .toList();

        assertFalse(steppers.isEmpty());

        // verify each stepper can be validated
        for (MuiStepper s : steppers) {
            assertTrue(s.validate());
            assertTrue(s.getStepCount() > 0);
        }
    }

    public static void main(String[] args) {
        MuiStepperTestCases test = new MuiStepperTestCases();
        test.setUpDriver(EDGE);
        test.testHorizontalLinearStepper();
        test.testVerticalLinearStepper();
        test.testMultipleSteppers();
    }
}
