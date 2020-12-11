/*
 * Copyright © 2020 the original author or authors.
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

package com.github.grossopa.selenium.component.mui;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.util.WebComponentUtils;
import lombok.SneakyThrows;
import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * A MUI Slider wrapper.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/slider/">
 * https://material-ui.com/components/slider/</a>
 * @since 1.0
 */
public class MuiSlider extends AbstractMuiComponent {

    public static final UnaryOperator<Double> DEFAULT_INVERSE_SCALE_FUNCTION = x -> x;

    private final UnaryOperator<Double> inverseScaleFunction;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiSlider(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, DEFAULT_INVERSE_SCALE_FUNCTION);
    }

    /**
     * Constructs an instance with the delegated element, root driver and customized scale function.
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param inverseScaleFunction the INVERSE function of the original scale function
     */
    public MuiSlider(WebElement element, ComponentWebDriver driver, MuiConfig config,
            UnaryOperator<Double> inverseScaleFunction) {
        super(element, driver, config);
        requireNonNull(inverseScaleFunction);
        this.inverseScaleFunction = inverseScaleFunction;
    }

    @Override
    public String getComponentName() {
        return "Slider";
    }


    /**
     * Gets the inverse scale function
     *
     * @return the inverse scale function
     */
    public Function<Double, Double> getInverseScaleFunction() {
        return inverseScaleFunction;
    }

    /**
     * Gets the raw value.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the raw value in String.
     */
    public String getValue() {
        return getFirstThumb().getAttribute("aria-valuenow");
    }

    /**
     * Gets value in Integer.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the value in Integer.
     */
    public Integer getValueInteger() {
        return Double.valueOf(getValue()).intValue();
    }

    /**
     * Gets value in Long.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the value in Long
     */
    public Long getValueLong() {
        return Double.valueOf(getValue()).longValue();
    }

    /**
     * Gets value in Double.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the value in double
     */
    public Double getValueDouble() {
        return Double.valueOf(getValue());
    }

    /**
     * Gets raw min value.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the raw min value.
     */
    public String getMinValue() {
        return getFirstThumb().getMinValue();
    }

    /**
     * Gets min value in Integer.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the min value in Integer.
     */
    public Integer getMinValueInteger() {
        return Double.valueOf(getMinValue()).intValue();
    }

    /**
     * Gets min value in Long.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the min value in Long
     */
    public Long getMinValueLong() {
        return Double.valueOf(getMinValue()).longValue();
    }

    /**
     * Gets min value in Double.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the min value in double
     */
    public Double getMinValueDouble() {
        return Double.valueOf(getMinValue());
    }

    /**
     * Gets raw max value.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the raw max value.
     */
    public String getMaxValue() {
        return getFirstThumb().getMaxValue();
    }

    /**
     * Gets max value in Integer.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the max value in Integer.
     */
    public Integer getMaxValueInteger() {
        return Double.valueOf(getMaxValue()).intValue();
    }

    /**
     * Gets max value in Long.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the max value in Long
     */
    public Long getMaxValueLong() {
        return Double.valueOf(getMaxValue()).longValue();
    }

    /**
     * Gets max value in Double.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the max value in double
     */
    public Double getMaxValueDouble() {
        return Double.valueOf(getMaxValue());
    }

    /**
     * Gets the first Thumb element.
     *
     * @return the first Thumb element.
     */
    public MuiSliderThumb getFirstThumb() {
        return new MuiSliderThumb(driver.mapElement(element.findElement(config.sliderThumbLocator())), driver, config);
    }

    /**
     * Gets the all Thumb elements. they are sorted by the value in ascending order.
     *
     * @return the all Thumb elements.
     */
    public List<MuiSliderThumb> getAllThumbs() {
        return element.findElements(config.sliderThumbLocator()).stream()
                .map(ele -> new MuiSliderThumb(driver.mapElement(ele), driver, config)).collect(toList());
    }

    /**
     * Is the slider vertical.
     *
     * @return true if the slider has orientation="vertical" specified.
     */
    public boolean isVertical() {
        return WebComponentUtils.attributeContains(element, "class", config.getCssPrefix() + "Slider-vertical");
    }

    /**
     * Is the slider tracker inverted.
     *
     * @return true if the slide has track="inverted" specified.
     */
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
    @SneakyThrows
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
}
