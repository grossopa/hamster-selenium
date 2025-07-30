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

package com.github.grossopa.selenium.component.mui.v4.inputs;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.api.Slider;
import com.github.grossopa.selenium.core.component.util.WebComponentUtils;
import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Objects;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

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
 * </p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/slider/">
 * https://material-ui.com/components/slider/</a>
 * @since 1.0
 */
public class MuiSlider extends AbstractMuiComponent implements Slider<MuiSliderThumb> {

    /**
     * The component name used for identification and validation.
     */
    public static final String COMPONENT_NAME = "Slider";

    /**
     * Default inverse scale function that returns the input value unchanged.
     * Used when no custom scaling is applied to the slider.
     */
    public static final UnaryOperator<Double> DEFAULT_INVERSE_SCALE_FUNCTION = x -> x;

    private final UnaryOperator<Double> inverseScaleFunction;

    /**
     * Constructs an instance with the delegated element and root driver using default inverse scale function.
     *
     * @param element the delegated WebElement representing the slider
     * @param driver the root ComponentWebDriver for browser interactions
     * @param config the Material UI configuration for styling and behavior
     */
    public MuiSlider(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, DEFAULT_INVERSE_SCALE_FUNCTION);
    }

    /**
     * Constructs an instance with the delegated element, root driver and customized scale function.
     *
     * @param element the delegated WebElement representing the slider
     * @param driver the root ComponentWebDriver for browser interactions
     * @param config the Material UI configuration for styling and behavior
     * @param inverseScaleFunction the INVERSE function of the original scale function to handle scaled values correctly
     */
    public MuiSlider(WebElement element, ComponentWebDriver driver, MuiConfig config,
            UnaryOperator<Double> inverseScaleFunction) {
        super(element, driver, config);
        requireNonNull(inverseScaleFunction);
        this.inverseScaleFunction = inverseScaleFunction;
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
    public Function<Double, Double> getInverseScaleFunction() {
        return inverseScaleFunction;
    }

    /**
     * Gets the current value of the slider as a String.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled value.
     * For example, when the position is at 50%, min={0}, max={6}, and scale={(x) =&gt; x ** 10}, 
     * then it should return <b>59049</b></p>
     *
     * @return the current slider value as a String
     * @see #getValueInteger()
     * @see #getValueLong()
     * @see #getValueDouble()
     */
    @Override
    public String getValue() {
        return getFirstThumb().getValue();
    }

    /**
     * Gets the current value of the slider as an Integer.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled value.
     * For example, when the position is at 50%, min={0}, max={6}, and scale={(x) =&gt; x ** 10}, 
     * then it should return <b>59049</b></p>
     *
     * @return the current slider value as an Integer
     * @see #getValue()
     * @see #getValueLong()
     * @see #getValueDouble()
     */
    @Override
    public Integer getValueInteger() {
        return Double.valueOf(getValue()).intValue();
    }

    /**
     * Gets the current value of the slider as a Long.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled value.
     * For example, when the position is at 50%, min={0}, max={6}, and scale={(x) =&gt; x ** 10}, 
     * then it should return <b>59049</b></p>
     *
     * @return the current slider value as a Long
     * @see #getValue()
     * @see #getValueInteger()
     * @see #getValueDouble()
     */
    @Override
    public Long getValueLong() {
        return Double.valueOf(getValue()).longValue();
    }

    /**
     * Gets the current value of the slider as a Double.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled value.
     * For example, when the position is at 50%, min={0}, max={6}, and scale={(x) =&gt; x ** 10}, 
     * then it should return <b>59049</b></p>
     *
     * @return the current slider value as a Double
     * @see #getValue()
     * @see #getValueInteger()
     * @see #getValueLong()
     */
    @Override
    public Double getValueDouble() {
        return Double.valueOf(getValue());
    }

    /**
     * Gets the minimum value of the slider as a String.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled minimum value.
     * For example, when min={2}, max={8}, and scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the minimum slider value as a String
     * @see #getMinValueInteger()
     * @see #getMinValueLong()
     * @see #getMinValueDouble()
     */
    @Override
    public String getMinValue() {
        return getFirstThumb().getMinValue();
    }

    /**
     * Gets the minimum value of the slider as an Integer.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled minimum value.
     * For example, when min={2}, max={8}, and scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the minimum slider value as an Integer
     * @see #getMinValue()
     * @see #getMinValueLong()
     * @see #getMinValueDouble()
     */
    @Override
    public Integer getMinValueInteger() {
        return Double.valueOf(getMinValue()).intValue();
    }

    /**
     * Gets the minimum value of the slider as a Long.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled minimum value.
     * For example, when min={2}, max={8}, and scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the minimum slider value as a Long
     * @see #getMinValue()
     * @see #getMinValueInteger()
     * @see #getMinValueDouble()
     */
    @Override
    public Long getMinValueLong() {
        return Double.valueOf(getMinValue()).longValue();
    }

    /**
     * Gets the minimum value of the slider as a Double.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled minimum value.
     * For example, when min={2}, max={8}, and scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the minimum slider value as a Double
     * @see #getMinValue()
     * @see #getMinValueInteger()
     * @see #getMinValueLong()
     */
    public Double getMinValueDouble() {
        return Double.valueOf(getMinValue());
    }

    /**
     * Gets the maximum value of the slider as a String.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled maximum value.
     * For example, when the position is at 50%, min={0}, max={6}, and scale={(x) =&gt; x ** 10}, 
     * then it should return <b>60466176</b></p>
     *
     * @return the maximum slider value as a String
     * @see #getMaxValueInteger()
     * @see #getMaxValueLong()
     * @see #getMaxValueDouble()
     */
    @Override
    public String getMaxValue() {
        return getFirstThumb().getMaxValue();
    }

    /**
     * Gets the maximum value of the slider as an Integer.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled maximum value.
     * For example, when the position is at 50%, min={0}, max={6}, and scale={(x) =&gt; x ** 10}, 
     * then it should return <b>60466176</b></p>
     *
     * @return the maximum slider value as an Integer
     * @see #getMaxValue()
     * @see #getMaxValueLong()
     * @see #getMaxValueDouble()
     */
    @Override
    public Integer getMaxValueInteger() {
        return Double.valueOf(getMaxValue()).intValue();
    }

    /**
     * Gets the maximum value of the slider as a Long.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled maximum value.
     * For example, when the position is at 50%, min={0}, max={6}, and scale={(x) =&gt; x ** 10}, 
     * then it should return <b>60466176</b></p>
     *
     * @return the maximum slider value as a Long
     * @see #getMaxValue()
     * @see #getMaxValueInteger()
     * @see #getMaxValueDouble()
     */
    @Override
    public Long getMaxValueLong() {
        return Double.valueOf(getMaxValue()).longValue();
    }

    /**
     * Gets the maximum value of the slider as a Double.
     *
     * <p>If the slider is configured with a scale function, this method returns the scaled maximum value.
     * For example, when the position is at 50%, min={0}, max={6}, and scale={(x) =&gt; x ** 10}, 
     * then it should return <b>60466176</b></p>
     *
     * @return the maximum slider value as a Double
     * @see #getMaxValue()
     * @see #getMaxValueInteger()
     * @see #getMaxValueLong()
     */
    @Override
    public Double getMaxValueDouble() {
        return Double.valueOf(getMaxValue());
    }

    /**
     * Gets the first Thumb element.
     *
     * @return the first Thumb element.
     */
    @Override
    public MuiSliderThumb getFirstThumb() {
        return createSliderThumb(findComponent(config.sliderThumbLocator()));
    }

    /**
     * Gets the all Thumb elements. they are sorted by the value in ascending order.
     *
     * @return the all Thumb elements.
     */
    @Override
    public List<MuiSliderThumb> getAllThumbs() {
        return element.findElements(config.sliderThumbLocator()).stream().map(this::createSliderThumb)
                .collect(toList());
    }

    /**
     * Is the slider vertical.
     *
     * @return true if the slider has orientation="vertical" specified.
     */
    @Override
    public boolean isVertical() {
        return WebComponentUtils.attributeContains(element, "class", config.getCssPrefix() + "Slider-vertical");
    }

    /**
     * Is the slider tracker inverted.
     *
     * @return true if the slide has track="inverted" specified.
     */
    @Override
    public boolean isInverted() {
        return WebComponentUtils.attributeContains(element, "class", config.getCssPrefix() + "Slider-trackInverted");
    }

    /**
     * Move the first thumb to the specified value in Integer.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param value the new integer value to set
     * @see #moveThumb(double)
     */
    @Override
    public void setValue(Integer value) {
        setValue(value.doubleValue());
    }

    /**
     * Move the target thumb to the specified value in Integer. Note the MUI thumb is always ordered by value in
     * ascending order.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param index the thumb index
     * @param value the new integer value to set
     * @see #moveThumb(double)
     */
    @Override
    public void setValue(int index, Integer value) {
        setValue(index, value.doubleValue());
    }

    /**
     * Move the target thumb to the specified value in Integer.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param thumb the target thumb to move
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    @Override
    public void setValue(MuiSliderThumb thumb, Integer value) {
        setValue(thumb, value.doubleValue());
    }

    /**
     * Move the first thumb by value.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param value the new long value to set
     * @see #moveThumb(double)
     */
    @Override
    public void setValue(Long value) {
        setValue(value.doubleValue());
    }

    /**
     * Move the target thumb to the specified value in long. Note the MUI thumb is always ordered by value in ascending
     * order.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param index the thumb index
     * @param value the new integer value to set
     * @see #moveThumb(double)
     */
    @Override
    public void setValue(int index, Long value) {
        setValue(index, value.doubleValue());
    }

    /**
     * Move the target thumb to the specified value in long.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param thumb the target thumb to move
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    @Override
    public void setValue(MuiSliderThumb thumb, Long value) {
        setValue(thumb, value.doubleValue());
    }

    /**
     * Move the first thumb to the specified value in double.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    @Override
    public void setValue(Double value) {
        doSetValue(value, this::moveThumb);
    }

    /**
     * Move the target thumb to the specified value in double. Note the MUI thumb is always ordered by value in
     * ascending order.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param index the thumb index
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    @Override
    public void setValue(int index, Double value) {
        doSetValue(value, percentage -> moveThumb(index, percentage));
    }

    /**
     * Move the target thumb to the specified value in double.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param thumb the target thumb to move
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    @Override
    public void setValue(MuiSliderThumb thumb, Double value) {
        doSetValue(value, percentage -> moveThumb(thumb, percentage));
    }

    private void doSetValue(Double value, DoubleConsumer moveThumbAction) {
        Double val = inverseScaleFunction.apply(value);
        Double maxValue = inverseScaleFunction.apply(getMaxValueDouble());
        Double minValue = inverseScaleFunction.apply(getMinValueDouble());
        if (Precision.compareTo(val, maxValue, 0.0001d) == 1 || Precision.compareTo(val, minValue, 0.0001d) == -1) {
            throw new IllegalArgumentException(
                    String.format("value %.2f is not in the range of %.2f, %.2f", val, minValue, maxValue));
        }

        moveThumbAction.accept((val - minValue) / (maxValue - minValue));
    }

    /**
     * Moves the first thumb to the desired location in percentage.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified percentage may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately move the thumb to a position like 55.5% for value 555 as the only possible
     * locations are 55px and 56px for 550 and 560.
     * </p>
     *
     * @param percentage the percentage to move to, must between [0.0, 1.0]
     */
    @Override
    public void moveThumb(double percentage) {
        moveThumb(getFirstThumb(), percentage);
    }

    /**
     * Moves the thumb to the desired location in percentage.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified percentage may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately move the thumb to a position like 55.5% for value 555 as the only possible
     * locations are 55px and 56px for 550 and 560.
     * </p>
     *
     * @param index the index of the thumbs
     * @param percentage the percentage to move to, must between [0.0, 1.0]
     */
    @Override
    public void moveThumb(int index, double percentage) {
        moveThumb(getAllThumbs().get(index), percentage);
    }

    /**
     * Moves the thumb to the desired location in percentage.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified percentage may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately move the thumb to a position like 55.5% for value 555 as the only possible
     * locations are 55px and 56px for 550 and 560.
     * </p>
     *
     * @param thumb the thumb component to move
     * @param percentage the percentage to move to, must between [0.0, 1.0]
     */
    @Override
    @SuppressWarnings("squid:S2184")
    public void moveThumb(MuiSliderThumb thumb, double percentage) {
        if (Precision.compareTo(percentage, 1, 0.0001d) == 1 || Precision.compareTo(percentage, 0, 0.0001d) == -1) {
            throw new IllegalArgumentException("Percentage must be in range of [0.0, 1.0]");
        }
        Rectangle rect = element.getRect();
        boolean vertical = isVertical();
        double start;
        double end;
        if (vertical) {
            start = rect.y + rect.height;
            end = rect.y;
        } else {
            start = rect.x;
            end = rect.x + rect.width;
        }

        Point thumbCenter = WebComponentUtils.getCenter(thumb.getRect());
        Actions actions = driver.createActions();
        int target = (int) Math.round(start + (end - start) * percentage);

        if (vertical) {
            actions.moveToElement(element).clickAndHold(thumb).moveByOffset(0, target - thumbCenter.y).release()
                    .perform();
        } else {
            actions.moveToElement(element).clickAndHold(thumb).moveByOffset(target - thumbCenter.x, 0).release()
                    .perform();
        }
    }

    protected MuiSliderThumb createSliderThumb(WebElement thumbElement) {
        return new MuiSliderThumb(thumbElement, driver, config);
    }

    @Override
    @SuppressWarnings("java:S6212")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiSlider)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MuiSlider muiSlider = (MuiSlider) o;
        return inverseScaleFunction.equals(muiSlider.inverseScaleFunction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), inverseScaleFunction);
    }

    @Override
    public String toString() {
        return "MuiSlider{" + "inverseScaleFunction=" + inverseScaleFunction + ", element=" + element + '}';
    }
}
