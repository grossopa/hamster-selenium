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

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.WebElement;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;
import static org.apache.commons.lang3.Strings.CI;
import static org.openqa.selenium.By.className;

/**
 * The Material UI Stepper implementation
 *
 * <p>Steppers convey progress through numbered steps. It provides a wizard-like workflow.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/steppers/">
 * https://material-ui.com/components/steppers/</a>
 * @since 1.0
 */
public class MuiStepper extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Stepper";

    /**
     * Constructs an MuiStepper instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiStepper(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
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
            String className = step.getDomAttribute(CLASS);
            if (className != null &&
                (config.isSelected(step) ||
                 config.isChecked(step) ||
                 className.contains(config.getCssPrefix() + "Step-active") ||
                 !step.findComponents(className(config.getCssPrefix() + "StepIcon-active")).isEmpty())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets all step components in the stepper.
     *
     * @return list of step components
     */
    public List<WebComponent> getSteps() {
        return element.findElements(className(config.getCssPrefix() + "Step-root"))
                .stream()
                .map(driver::mapElement)
                .toList();
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
        String className = element.getDomAttribute(CLASS);
        return CI.contains(className, config.getCssPrefix() + "Stepper-vertical");
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
                        return step.findComponent(className(config.getCssPrefix() + "StepLabel-label")).getText();
                    } catch (Exception e) {
                        return "";
                    }
                })
                .toList();
    }
}
