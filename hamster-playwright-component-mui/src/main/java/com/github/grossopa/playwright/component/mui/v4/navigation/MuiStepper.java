/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.component.mui.v4.navigation;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Steppers display progress through a sequence of logical and numbered steps.
 *
 * <p>They may also be used for navigation. Steppers can display a feedback on the steps 
 * via an optional step label and description.</p>
 *
 * @author Jack Yin
 * @since 1.12
 */
public class MuiStepper extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Stepper";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiStepper(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<com.github.grossopa.playwright.component.mui.MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Gets the currently active step index.
     *
     * @return the zero-based index of the active step, or -1 if no step is active
     */
    public int getActiveStep() {
        List<WebComponent> steps = getSteps();
        for (int i = 0; i < steps.size(); i++) {
            WebComponent step = steps.get(i);
            String className = step.getAttribute(CLASS);
            if (className != null &&
                (config.isSelected(step) ||
                 config.isChecked(step) ||
                 className.contains(config.getCssPrefix() + "Step-active") ||
                 !step.findComponents("." + config.getCssPrefix() + "StepIcon-active").isEmpty())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets all step components in the stepper.
     *
     * @return list of step WebComponents
     */
    public List<WebComponent> getSteps() {
        return findComponents("." + config.getCssPrefix() + "Step-root");
    }

    /**
     * Gets the total number of steps.
     *
     * @return the number of steps
     */
    public int getStepCount() {
        return getSteps().size();
    }

    /**
     * Checks if the stepper is in vertical orientation.
     *
     * @return true if the stepper is vertical, false if horizontal
     */
    public boolean isVertical() {
        String className = getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "Stepper-vertical");
    }

    /**
     * Gets the step labels.
     *
     * @return list of step label texts
     */
    public List<String> getStepLabels() {
        return getSteps().stream()
                .map(step -> {
                    try {
                        WebComponent label = step.findComponent("." + config.getCssPrefix() + "StepLabel-label");
                        return label != null ? label.innerText() : "";
                    } catch (Exception e) {
                        return "";
                    }
                })
                .collect(Collectors.toList());
    }
}
