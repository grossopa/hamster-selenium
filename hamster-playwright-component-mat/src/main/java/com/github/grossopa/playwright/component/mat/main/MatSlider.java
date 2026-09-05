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
import com.microsoft.playwright.options.BoundingBox;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * {@code <mat-slider>} allows for the selection of a value from a range via mouse, touch, or keyboard, similar to
 * {@code <input type="range">}.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/slider/overview">
 * https://material.angular.io/components/slider/overview</a>
 * @since 1.15
 */
public class MatSlider extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Slider";

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatSlider(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "slider");
    }

    /**
     * Gets the raw value.
     *
     * @return the raw value in String
     */
    public String getValue() {
        return getAttribute("aria-valuenow");
    }

    /**
     * Gets value in Integer.
     *
     * @return the value in Integer
     */
    public Integer getValueInteger() {
        return getValueDouble().intValue();
    }

    /**
     * Gets value in Long.
     *
     * @return the value in Long
     */
    public Long getValueLong() {
        return getValueDouble().longValue();
    }

    /**
     * Gets value in Double.
     *
     * @return the value in Double
     */
    public Double getValueDouble() {
        return Double.valueOf(getValue());
    }

    /**
     * Gets raw min value.
     *
     * @return the raw min value
     */
    public String getMinValue() {
        return getAttribute("aria-valuemin");
    }

    /**
     * Gets min value in Integer.
     *
     * @return the min value in Integer
     */
    public Integer getMinValueInteger() {
        return getMinValueDouble().intValue();
    }

    /**
     * Gets min value in Long.
     *
     * @return the min value in Long
     */
    public Long getMinValueLong() {
        return getMinValueDouble().longValue();
    }

    /**
     * Gets min value in Double.
     *
     * @return the min value in Double
     */
    public Double getMinValueDouble() {
        return Double.valueOf(getMinValue());
    }

    /**
     * Gets raw max value.
     *
     * @return the raw max value
     */
    public String getMaxValue() {
        return getAttribute("aria-valuemax");
    }

    /**
     * Gets max value in Integer.
     *
     * @return the max value in Integer
     */
    public Integer getMaxValueInteger() {
        return getMaxValueDouble().intValue();
    }

    /**
     * Gets max value in Long.
     *
     * @return the max value in Long
     */
    public Long getMaxValueLong() {
        return getMaxValueDouble().longValue();
    }

    /**
     * Gets max value in Double.
     *
     * @return the max value in Double
     */
    public Double getMaxValueDouble() {
        return Double.valueOf(getMaxValue());
    }

    /**
     * Gets the first thumb element.
     *
     * @return the first thumb element
     */
    public WebComponent getFirstThumb() {
        return this.findComponent("." + config.getCssPrefix() + "slider-thumb");
    }

    /**
     * Gets all thumb elements.
     *
     * @return all thumb elements
     */
    public List<WebComponent> getAllThumbs() {
        return singletonList(getFirstThumb());
    }

    /**
     * Is the slider vertical.
     *
     * @return true if the slider is vertical
     */
    public boolean isVertical() {
        return attributeContains(CLASS, config.getCssPrefix() + "slider-vertical");
    }

    /**
     * Is the slider tracker inverted.
     *
     * @return true if the slider is inverted
     */
    public boolean isInverted() {
        return attributeContains(CLASS, config.getCssPrefix() + "slider-axis-inverted");
    }

    /**
     * Moves the thumb to the specified value by clicking on the calculated position of the slider track.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value.</p>
     *
     * @param value the new value to set
     */
    public void setValue(double value) {
        double min = getMinValueDouble();
        double max = getMaxValueDouble();
        double percentage = (value - min) / (max - min);
        BoundingBox box = locator().boundingBox();
        double x;
        double y;
        if (isVertical()) {
            x = box.x + box.width / 2;
            y = box.y + box.height * (1 - percentage);
        } else {
            x = box.x + box.width * percentage;
            y = box.y + box.height / 2;
        }
        driver.page().mouse().click(x, y);
    }

    /**
     * Moves the thumb to the specified value by clicking on the calculated position of the slider track.
     *
     * @param value the new value to set
     */
    public void setValue(Integer value) {
        setValue(value.doubleValue());
    }

    /**
     * Moves the thumb to the specified value by clicking on the calculated position of the slider track.
     *
     * @param value the new value to set
     */
    public void setValue(Long value) {
        setValue(value.doubleValue());
    }
}
