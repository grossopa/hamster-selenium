/*
 * Copyright © 2021 the original author or authors.
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

package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.component.api.Slider;
import com.github.grossopa.selenium.core.component.util.WebComponentUtils;
import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.function.DoubleConsumer;

import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;
import static java.util.Collections.singletonList;

/**
 * {@code <mat-slider>} allows for the selection of a value from a range via mouse, touch, or keyboard, similar to
 * {@code <input type="range">}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MatSlider extends AbstractMatComponent implements Slider<WebComponent> {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Slider";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatSlider(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    /**
     * Returns the component name.
     *
     * @return the component name
     */
    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "slider");
    }

    /**
     * Gets the raw value.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the raw value in String.
     */
    @Override
    public String getValue() {
        return getDomAttribute("aria-valuenow");
    }

    /**
     * Gets value in Integer.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the value in Integer.
     */
    @Override
    public Integer getValueInteger() {
        return getValueDouble().intValue();
    }

    /**
     * Gets value in Long.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the value in Long
     */
    @Override
    public Long getValueLong() {
        return getValueDouble().longValue();
    }

    /**
     * Gets value in Double.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the value in double
     */
    @Override
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
    @Override
    public String getMinValue() {
        return getDomAttribute("aria-valuemin");
    }

    /**
     * Gets min value in Integer.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the min value in Integer.
     */
    @Override
    public Integer getMinValueInteger() {
        return getMinValueDouble().intValue();
    }

    /**
     * Gets min value in Long.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the min value in Long
     */
    @Override
    public Long getMinValueLong() {
        return getMinValueDouble().longValue();
    }

    /**
     * Gets min value in Double.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the min value in double
     */
    @Override
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
    @Override
    public String getMaxValue() {
        return getDomAttribute("aria-valuemax");
    }

    /**
     * Gets max value in Integer.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the max value in Integer.
     */
    @Override
    public Integer getMaxValueInteger() {
        return getMaxValueDouble().intValue();
    }

    /**
     * Gets max value in Long.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the max value in Long
     */
    @Override
    public Long getMaxValueLong() {
        return getMaxValueDouble().longValue();
    }

    /**
     * Gets max value in Double.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the max value in double
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
    public WebComponent getFirstThumb() {
        return this.findComponent(By.className(config.getCssPrefix() + "slider-thumb"));
    }

    /**
     * Gets the all Thumb elements. they are sorted by the value in ascending order.
     *
     * @return the all Thumb elements.
     */
    @Override
    public List<WebComponent> getAllThumbs() {
        return singletonList(getFirstThumb());
    }

    /**
     * Is the slider vertical.
     *
     * @return true if the slider is vertical
     */
    @Override
    public boolean isVertical() {
        return attributeContains(CLASS, config.getCssPrefix() + "slider-vertical");
    }

    /**
     * Is the slider tracker inverted.
     *
     * @return true if the slide is inverted
     */
    @Override
    public boolean isInverted() {
        return attributeContains(CLASS, config.getCssPrefix() + "slider-axis-inverted");
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
    public void setValue(WebComponent thumb, Integer value) {
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
    public void setValue(WebComponent thumb, Long value) {
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
    public void setValue(WebComponent thumb, Double value) {
        doSetValue(value, percentage -> moveThumb(thumb, percentage));
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

    private void doSetValue(Double value, DoubleConsumer moveThumbAction) {
        Double maxValue = getMaxValueDouble();
        Double minValue = getMinValueDouble();
        if (Precision.compareTo(value, maxValue, 0.0001d) == 1 || Precision.compareTo(value, minValue, 0.0001d) == -1) {
            throw new IllegalArgumentException(
                    String.format("value %.2f is not in the range of %.2f, %.2f", value, minValue, maxValue));
        }

        moveThumbAction.accept((value - minValue) / (maxValue - minValue));
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
     *  @param thumb the thumb component to move
     *
     * @param percentage the percentage to move to, must between [0.0, 1.0]
     */
    @Override
    @SuppressWarnings("java:S2184")
    public void moveThumb(WebComponent thumb, double percentage) {
        if (Precision.compareTo(percentage, 1, 0.0001d) == 1 || Precision.compareTo(percentage, 0, 0.0001d) == -1) {
            throw new IllegalArgumentException("Percentage must be in range of [0.0, 1.0]");
        }
        Rectangle rect = element.findElement(By.className(config.getCssPrefix() + "slider-wrapper")).getRect();

        boolean vertical = isVertical();
        boolean inverted = isInverted();
        double start;
        double end;
        if (vertical) {
            start = rect.y + rect.height;
            end = rect.y;
        } else {
            start = rect.x;
            end = rect.x + rect.width;
        }

        if (vertical != inverted) {
            percentage = 1 - percentage;
        }
        Point thumbCenter = WebComponentUtils.getCenter(thumb.getRect());
        Actions actions = driver.createActions();
        int target = (int) Math.ceil(start + (end - start) * percentage);
        if (vertical) {
            actions.moveToElement(element).clickAndHold(thumb).moveByOffset(0, target - thumbCenter.y).release()
                    .perform();
        } else {
            actions.moveToElement(element).clickAndHold(thumb).moveByOffset(target - thumbCenter.x, 0).release()
                    .perform();
        }
    }
}
