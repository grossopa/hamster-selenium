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
import com.microsoft.playwright.Locator;

import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * {@code <mat-progress-bar>} is a horizontal progress-bar for indicating progress and activity.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/progress-bar/overview">
 * https://material.angular.io/components/progress-bar/overview</a>
 * @since 1.15
 */
public class MatProgressBar extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "ProgressBar";

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatProgressBar(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "progress-bar");
    }

    /**
     * Gets the min value of the progress bar.
     *
     * @return the min value of the progress bar
     */
    public String getMinValue() {
        return getAttribute("aria-valuemin");
    }

    /**
     * Gets the max value of the progress bar.
     *
     * @return the max value of the progress bar
     */
    public String getMaxValue() {
        return getAttribute("aria-valuemax");
    }

    /**
     * Gets the current value of the progress bar.
     *
     * @return the current value of the progress bar
     */
    public String getValue() {
        return getAttribute("aria-valuenow");
    }

    /**
     * Gets current progress bar mode.
     *
     * @return the current mode
     */
    public Mode getMode() {
        return Mode.valueOf(upperCase(getAttribute("mode")));
    }

    /**
     * The progress bar mode.
     *
     * @author Jack Yin
     * @since 1.15
     */
    public enum Mode {
        /**
         * the determinate mode
         */
        DETERMINATE,

        /**
         * the indeterminate mode
         */
        INDETERMINATE,

        /**
         * the buffer mode
         */
        BUFFER,

        /**
         * the query mode
         */
        QUERY
    }
}
