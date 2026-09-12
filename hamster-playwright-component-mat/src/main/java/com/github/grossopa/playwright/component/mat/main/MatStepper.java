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
package com.github.grossopa.playwright.component.mat.main;

import com.github.grossopa.playwright.component.mat.AbstractMatComponent;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.List;

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
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatStepper(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "stepper");
    }

    /**
     * Gets all steps.
     *
     * @return the list of steps
     */
    public List<MatStep> getSteps() {
        return this.findComponents(config.getTagPrefix() + "step." + config.getCssPrefix() + "step")
                .stream().map(c -> new MatStep(c, driver, config)).toList();
    }

    /**
     * Clicks the next button to advance to the next step.
     */
    public void next() {
        this.findComponent("." + config.getCssPrefix() + "stepper-next").click();
    }

    /**
     * Clicks the previous button to go back to the previous step.
     */
    public void previous() {
        this.findComponent("." + config.getCssPrefix() + "stepper-previous").click();
    }

    /**
     * Whether the stepper is in linear mode.
     *
     * @return true if the stepper is linear
     */
    public boolean isLinear() {
        return attributeContains(CLASS, config.getCssPrefix() + "stepper-linear");
    }

    /**
     * Whether the stepper is vertical.
     *
     * @return true if the stepper is vertical
     */
    public boolean isVertical() {
        return attributeContains(CLASS, config.getCssPrefix() + "stepper-vertical");
    }

    /**
     * Gets the horizontal stepper header for selecting steps.
     *
     * @return the stepper header element
     */
    public WebComponent getHeader() {
        return this.findComponent("." + config.getCssPrefix() + "stepper-header");
    }
}
