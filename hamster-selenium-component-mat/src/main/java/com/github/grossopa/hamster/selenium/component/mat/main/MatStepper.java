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

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * {@code <mat-stepper>} provides a wizard-like workflow by breaking content into logical steps.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/stepper/overview">
 * https://material.angular.io/components/stepper/overview</a>
 * @since 1.16
 */
public class MatStepper extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Stepper";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatStepper(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "stepper");
    }

    /**
     * Gets all steps.
     *
     * @return the list of steps
     */
    public List<MatStep> getSteps() {
        return this.findComponentsAs(By.className(config.getCssPrefix() + "step"),
                c -> new MatStep(c, driver, config));
    }

    /**
     * Clicks the next button to advance to the next step.
     */
    public void next() {
        this.findComponent(By.className(config.getCssPrefix() + "stepper-next")).click();
    }

    /**
     * Clicks the previous button to go back to the previous step.
     */
    public void previous() {
        this.findComponent(By.className(config.getCssPrefix() + "stepper-previous")).click();
    }

    /**
     * Whether the stepper is in linear mode.
     *
     * @return true if the stepper is linear
     */
    public boolean isLinear() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "stepper-linear");
    }

    /**
     * Whether the stepper is vertical.
     *
     * @return true if the stepper is vertical
     */
    public boolean isVertical() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "stepper-vertical");
    }

    /**
     * Gets the horizontal stepper header for selecting steps.
     *
     * @return the stepper header element
     */
    public WebComponent getHeader() {
        return this.findComponent(By.className(config.getCssPrefix() + "stepper-header"));
    }
}
