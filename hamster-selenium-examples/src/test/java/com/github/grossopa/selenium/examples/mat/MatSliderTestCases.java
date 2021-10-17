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

package com.github.grossopa.selenium.examples.mat;

import com.github.grossopa.hamster.selenium.component.mat.main.MatCheckbox;
import com.github.grossopa.hamster.selenium.component.mat.main.MatFormField;
import com.github.grossopa.hamster.selenium.component.mat.main.MatSlider;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;
import java.util.function.Consumer;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static com.github.grossopa.selenium.core.locator.By2.axesBuilder;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static com.github.grossopa.selenium.core.util.SeleniumUtils.cleanText;
import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.By.xpath;

/**
 * Test cases for {@link MatSlider}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MatSliderTestCases extends AbstractBrowserSupport {

    public void testConfigurableSlider() {
        // close cookie alert
        driver.findComponent(xpathBuilder().anywhereRelative("span").text().contains("Ok, Got it").parent().build())
                .click();

        List<WebComponent> containers = driver.findComponent(By.tagName("slider-configurable-example"))
                .findComponents(axesBuilder().child("mat-card").build());
        WebComponent configContainer = containers.get(0);
        WebComponent resultContainer = containers.get(1);

        System.out.println(configContainer.outerHTML());
        // line 1 /mat-form-field[contains(@class,'mat-form-field')][0]
        MatFormField valueField = configContainer.findComponent(xpath(".//section[1]/mat-form-field[1]")).as(mat())
                .toFormField();
        MatFormField minValueField = configContainer.findComponent(xpath(".//section[1]/mat-form-field[2]")).as(mat())
                .toFormField();
        MatFormField maxValueField = configContainer.findComponent(xpath(".//section[1]/mat-form-field[3]")).as(mat())
                .toFormField();
        MatFormField stepSizeField = configContainer.findComponent(xpath(".//section[1]/mat-form-field[4]")).as(mat())
                .toFormField();
        // line 4
        MatCheckbox verticalCheckbox = configContainer.findComponent(xpath(".//section[4]/mat-checkbox[1]")).as(mat())
                .toCheckbox();
        MatCheckbox invertedCheckbox = configContainer.findComponent(xpath(".//section[4]/mat-checkbox[2]")).as(mat())
                .toCheckbox();
        // line 5
        MatCheckbox disabledCheckbox = configContainer.findComponent(xpath(".//section[5]/mat-checkbox[1]")).as(mat())
                .toCheckbox();

        MatSlider targetSlider = resultContainer.findComponent(By.tagName("mat-slider")).as(mat()).toSlider();

        cleanText(valueField.getInput());
        valueField.getInput().sendKeys("50");
        assertEquals(50, targetSlider.getValueInteger());

        cleanText(minValueField.getInput());
        minValueField.getInput().sendKeys("20");
        assertEquals(20, targetSlider.getMinValueInteger());

        cleanText(maxValueField.getInput());
        maxValueField.getInput().sendKeys("120");
        assertEquals(120, targetSlider.getMaxValueInteger());

        // the default width is 284 while value range from 20 - 120, hence the movement will not be always accurate
        Consumer<MatSlider> testingApproximate = slider -> {
            cleanText(stepSizeField.getInput());
            stepSizeField.getInput().sendKeys("1");

            driver.threadSleep(200L);
            slider.moveThumb(1);
            assertTrue(abs(120 - slider.getValueInteger()) < 2);

            driver.threadSleep(200L);
            slider.moveThumb(0);
            assertTrue(abs(20 - slider.getValueInteger()) < 2);

            driver.threadSleep(200L);
            slider.setValue(23);
            assertTrue(abs(23 - slider.getValueInteger()) < 2);

            driver.threadSleep(200L);
            slider.setValue(60);
            assertTrue(abs(60 - slider.getValueInteger()) < 2);

            driver.threadSleep(200L);
            cleanText(stepSizeField.getInput());
            stepSizeField.getInput().sendKeys("10");

            driver.threadSleep(200L);
            // step 10 hence should move to close 120 node
            slider.setValue(118);
            assertTrue(abs(120 - slider.getValueInteger()) < 2);
        };

        testingApproximate.accept(targetSlider);
        // vertical testing
        verticalCheckbox.click();
        testingApproximate.accept(targetSlider);

        // inverted and vertical testing
        invertedCheckbox.click();
        testingApproximate.accept(targetSlider);

        // inverted testing
        verticalCheckbox.click();
        testingApproximate.accept(targetSlider);

        assertTrue(targetSlider.isEnabled());
        disabledCheckbox.click();
        assertFalse(targetSlider.isEnabled());
        disabledCheckbox.click();


        cleanText(minValueField.getInput());
        minValueField.getInput().sendKeys("0");
        assertEquals(0, targetSlider.getMinValueInteger());

        cleanText(maxValueField.getInput());
        maxValueField.getInput().sendKeys("28");
        assertEquals(28, targetSlider.getMaxValueInteger());

        cleanText(stepSizeField.getInput());
        stepSizeField.getInput().sendKeys("1");

        Consumer<MatSlider> testingExact = slider -> {
            driver.threadSleep(200L);
            slider.setValue(13);
            assertEquals(13, slider.getValueInteger());
            driver.threadSleep(200L);
            slider.setValue(15);
            assertEquals(15, slider.getValueInteger());
            driver.threadSleep(200L);
            slider.setValue(28);
            assertEquals(28, slider.getValueInteger());
            driver.threadSleep(200L);
            slider.setValue(0);
            assertEquals(0, slider.getValueInteger());
        };

        testingExact.accept(targetSlider);
        // vertical testing
        verticalCheckbox.click();
        testingExact.accept(targetSlider);

        // inverted and vertical testing
        invertedCheckbox.click();
        testingExact.accept(targetSlider);

        // inverted testing
        verticalCheckbox.click();
        testingExact.accept(targetSlider);
    }

    public static void main(String[] args) {
        MatSliderTestCases test = new MatSliderTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://material.angular.io/components/slider/examples");

        test.testConfigurableSlider();
    }
}
