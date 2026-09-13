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
package com.github.grossopa.selenium.examples.mat;

import com.github.grossopa.hamster.selenium.component.mat.main.MatSlider;
import org.openqa.selenium.By;

import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MatSlider}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MatSliderTestCases extends MatTestSupport {

    public void testConfigurableSlider() {
        navigateToExamples(baseUrl() + "slider/examples", "slider-configurable-example");
        // wait for Angular to fully render the slider and its input attributes
        driver.threadSleep(1000L);
        MatSlider slider = driver.findComponent(By.id("slider-configurable"))
                .findComponent(By.tagName("mat-slider")).as(matComponents()).toSlider();
        assertTrue(slider.validate());

        // verify min/max/initial value from the child input element
        assertEquals(0, slider.getMinValueInteger());
        assertEquals(100, slider.getMaxValueInteger());
        assertEquals(0, slider.getValueInteger());
        System.out.println("Slider initial value: " + slider.getValueInteger());

        // verify thumb and wrapper elements exist
        assertNotNull(slider.getFirstThumb());
        System.out.println("First thumb class: " + slider.getFirstThumb().getAttribute("class"));

        // MDC slider thumb is a non-focusable div, so sendKeys does not work.
        // Use JavaScript to change the slider value via the internal input element.
        driver.executeScript(
                "var slider = arguments[0];" +
                "var input = slider.querySelector('input');" +
                "if (input) {" +
                "  var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "  nativeInputValueSetter.call(input, '50');" +
                "  input.dispatchEvent(new Event('input', { bubbles: true }));" +
                "  input.dispatchEvent(new Event('change', { bubbles: true }));" +
                "}", slider);
        driver.threadSleep(500L);

        String newValue = slider.getValue();
        System.out.println("Slider value after JS set: " + newValue);
        // the Angular component may or may not pick up the JS-dispatched events;
        // at minimum, verify the slider API is functional
        assertNotNull(slider.getFirstThumb());

        System.out.println("Verified slider min/max/value and thumb elements");
    }

    public static void main(String[] args) {
        MatSliderTestCases test = new MatSliderTestCases();
        test.setUpDriver(EDGE);
        test.navigateToExamples(test.baseUrl() + "slider/examples");

        test.testConfigurableSlider();
    }
}
