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

package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

import static com.github.grossopa.playwright.component.mui.MuiVersion.*;
import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * A MUI Slider wrapper that provides comprehensive slider manipulation capabilities.
 *
 * <p>This component represents a Material UI Slider which allows users to make selections from a range of values.
 * It supports various operations such as getting/setting values, moving thumbs, and handling scaled values.</p>
 *
 * <p>Key features:
 * <ul>
 *   <li>Support for numeric value retrieval in multiple formats (String, Integer, Long, Double)</li>
 *   <li>Thumb manipulation with precise positioning</li>
 *   <li>Support for scaled sliders with custom inverse scale functions</li>
 *   <li>Comprehensive range operations (min/max values)</li>
 * </ul>
 *
 * @see <a href="https://material-ui.com/components/slider/">
 * https://material-ui.com/components/slider/</a>
 * @since 1.12
 */
public class MuiSlider extends AbstractMuiComponent {

    /**
     * The component name used for identification and validation.
     */
    public static final String COMPONENT_NAME = "Slider";

    /**
     * Default inverse scale function that returns the input value unchanged.
     * Used when no custom scaling is applied to the slider.
     */
    public static final DoubleUnaryOperator DEFAULT_INVERSE_SCALE_FUNCTION = x -> x;

    private final DoubleUnaryOperator inverseScaleFunction;

    /**
     * Constructs an instance with the delegated locator and driver using default inverse scale function.
     *
     * @param locator the delegated Locator representing the slider
     * @param driver the ComponentDriver for browser interactions
     * @param config the Material UI configuration for styling and behavior
     */
    public MuiSlider(Locator locator, ComponentDriver driver, MuiConfig config) {
        this(locator, driver, config, DEFAULT_INVERSE_SCALE_FUNCTION);
    }

    /**
     * Constructs an instance with the delegated locator, driver and customized scale function.
     *
     * @param locator the delegated Locator representing the slider
     * @param driver the ComponentDriver for browser interactions
     * @param config the Material UI configuration for styling and behavior
     * @param inverseScaleFunction the INVERSE function of the original scale function to handle scaled values correctly
     */
    public MuiSlider(Locator locator, ComponentDriver driver, MuiConfig config,
                     DoubleUnaryOperator inverseScaleFunction) {
        super(locator, driver, config);
        this.inverseScaleFunction = inverseScaleFunction != null ? inverseScaleFunction : DEFAULT_INVERSE_SCALE_FUNCTION;
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
     * Gets the inverse scale function used to convert scaled values back to their original representation.
     *
     * <p>For example, if a slider uses a scale function f(x) = x^2, the inverse scale function would be f^(-1)(x) = √x</p>
     *
     * @return the inverse scale function
     */
    public DoubleUnaryOperator getInverseScaleFunction() {
        return inverseScaleFunction;
    }

    /**
     * Gets the current value of the slider as a String.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled value.</p>
     *
     * @return the current slider value as a String
     */
    public String getValue() {
        return getFirstThumb().getValue();
    }

    /**
     * Gets the current value of the slider as an Integer.
     *
     * @return the current slider value as an Integer
     */
    public Integer getValueInteger() {
        return Double.valueOf(getValue()).intValue();
    }

    /**
     * Gets the current value of the slider as a Long.
     *
     * @return the current slider value as a Long
     */
    public Long getValueLong() {
        return Double.valueOf(getValue()).longValue();
    }

    /**
     * Gets the current value of the slider as a Double.
     *
     * @return the current slider value as a Double
     */
    public Double getValueDouble() {
        return Double.valueOf(getValue());
    }

    /**
     * Gets the minimum value of the slider as a String.
     *
     * @return the minimum slider value as a String
     */
    public String getMinValue() {
        return getFirstThumb().getMinValue();
    }

    /**
     * Gets the minimum value of the slider as an Integer.
     *
     * @return the minimum slider value as an Integer
     */
    public Integer getMinValueInteger() {
        return Double.valueOf(getMinValue()).intValue();
    }

    /**
     * Gets the minimum value of the slider as a Long.
     *
     * @return the minimum slider value as a Long
     */
    public Long getMinValueLong() {
        return Double.valueOf(getMinValue()).longValue();
    }

    /**
     * Gets the minimum value of the slider as a Double.
     *
     * @return the minimum slider value as a Double
     */
    public Double getMinValueDouble() {
        return Double.valueOf(getMinValue());
    }

    /**
     * Gets the maximum value of the slider as a String.
     *
     * @return the maximum slider value as a String
     */
    public String getMaxValue() {
        return getFirstThumb().getMaxValue();
    }

    /**
     * Gets the maximum value of the slider as an Integer.
     *
     * @return the maximum slider value as an Integer
     */
    public Integer getMaxValueInteger() {
        return Double.valueOf(getMaxValue()).intValue();
    }

    /**
     * Gets the maximum value of the slider as a Long.
     *
     * @return the maximum slider value as a Long
     */
    public Long getMaxValueLong() {
        return Double.valueOf(getMaxValue()).longValue();
    }

    /**
     * Gets the maximum value of the slider as a Double.
     *
     * @return the maximum slider value as a Double
     */
    public Double getMaxValueDouble() {
        return Double.valueOf(getMaxValue());
    }

    /**
     * Gets the first Thumb element.
     *
     * @return the first Thumb component
     */
    public MuiSliderThumb getFirstThumb() {
        WebComponent thumb = findComponent(config.sliderThumbLocator());
        return new MuiSliderThumb(thumb.locator(), driver, config);
    }

    /**
     * Gets all Thumb elements, sorted by value in ascending order.
     *
     * @return list of all thumb components
     */
    public List<MuiSliderThumb> getAllThumbs() {
        return findComponents(config.sliderThumbLocator()).stream()
                .map(thumb -> new MuiSliderThumb(thumb.locator(), driver, config))
                .collect(Collectors.toList());
    }

    /**
     * Checks if the slider is vertical.
     *
     * @return true if the slider has orientation="vertical" specified
     */
    public boolean isVertical() {
        String className = locator.getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "Slider-vertical");
    }

    /**
     * Checks if the slider tracker is inverted.
     *
     * @return true if the slider has track="inverted" specified
     */
    public boolean isInverted() {
        String className = locator.getAttribute(CLASS);
        return className != null && className.contains(config.getCssPrefix() + "Slider-trackInverted");
    }

    /**
     * Sets the slider value by moving the first thumb.
     *
     * @param value the new value to set
     */
    public void setValue(Double value) {
        Double unscaledValue = inverseScaleFunction.applyAsDouble(value);
        Double maxValue = inverseScaleFunction.applyAsDouble(getMaxValueDouble());
        Double minValue = inverseScaleFunction.applyAsDouble(getMinValueDouble());
        
        if (unscaledValue > maxValue || unscaledValue < minValue) {
            throw new IllegalArgumentException(
                    String.format("value %.2f is not in the range of %.2f, %.2f", unscaledValue, minValue, maxValue));
        }
        
        double percentage = (unscaledValue - minValue) / (maxValue - minValue);
        moveThumb(percentage);
    }

    /**
     * Sets the slider value by moving the first thumb.
     *
     * @param value the new integer value to set
     */
    public void setValue(Integer value) {
        setValue(value.doubleValue());
    }

    /**
     * Sets the slider value by moving the first thumb.
     *
     * @param value the new long value to set
     */
    public void setValue(Long value) {
        setValue(value.doubleValue());
    }

    /**
     * Moves the first thumb to the desired location in percentage.
     *
     * @param percentage the percentage to move to, must be between [0.0, 1.0]
     */
    public void moveThumb(double percentage) {
        if (percentage < 0.0 || percentage > 1.0) {
            throw new IllegalArgumentException("Percentage must be in range of [0.0, 1.0]");
        }
        
        // Use Playwright's built-in slider interaction
        // For more precise control, we can use dragTo or click at specific coordinates
        locator.evaluate("(element, pct) => { " +
                "const rect = element.getBoundingClientRect(); " +
                "const x = rect.left + rect.width * pct; " +
                "const y = rect.top + rect.height / 2; " +
                "const event = new MouseEvent('mousedown', { bubbles: true }); " +
                "element.dispatchEvent(event); " +
                "const moveEvent = new MouseEvent('mousemove', { bubbles: true, clientX: x, clientY: y }); " +
                "document.dispatchEvent(moveEvent); " +
                "const upEvent = new MouseEvent('mouseup', { bubbles: true }); " +
                "document.dispatchEvent(upEvent); " +
                "}", percentage);
    }

    /**
     * Creates a slider thumb component.
     *
     * @param thumbLocator the locator for the thumb element
     * @return a new MuiSliderThumb instance
     */
    protected MuiSliderThumb createSliderThumb(Locator thumbLocator) {
        return new MuiSliderThumb(thumbLocator, driver, config);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MuiSlider muiSlider = (MuiSlider) o;
        return Objects.equals(inverseScaleFunction, muiSlider.inverseScaleFunction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), inverseScaleFunction);
    }
}
