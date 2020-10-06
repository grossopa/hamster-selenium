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

package org.hamster.selenium.component.mui;

import lombok.SneakyThrows;
import org.apache.commons.math3.util.Precision;
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.hamster.selenium.core.component.util.WebComponentUtils;
import org.hamster.selenium.core.locator.By2;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * A MUI Slider wrapper.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/slider/">
 * https://material-ui.com/components/slider/</a>
 * @since 1.0
 */
public class MuiSlider extends AbstractMuiComponent {

    public static final Function<Double, Double> DEFAULT_INVERSE_SCALE_FUNCTION = x -> x;

    private final Function<Double, Double> inverseScaleFunction;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element
     *         the delegated element
     * @param driver
     *         the root driver
     * @param config
     *         the Material UI configuration
     */
    public MuiSlider(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, DEFAULT_INVERSE_SCALE_FUNCTION);
    }

    /**
     * Constructs an instance with the delegated element, root driver and customized scale function.
     *
     * @param element
     *         the delegated element
     * @param driver
     *         the root driver
     * @param config
     *         the Material UI configuration
     * @param inverseScaleFunction
     *         the INVERSE function of the original scale function
     */
    public MuiSlider(WebElement element, ComponentWebDriver driver, MuiConfig config,
            Function<Double, Double> inverseScaleFunction) {
        super(element, driver, config);
        requireNonNull(inverseScaleFunction);
        this.inverseScaleFunction = inverseScaleFunction;
    }

    @Override
    public String getComponentName() {
        return "Slider";
    }

    /**
     * Gets the raw value.
     *
     * @return the raw value in String.
     */
    public String getValue() {
        return element.findElement(By2.attr("type", "hidden").exact().tag("input").build()).getAttribute("value");
    }

    /**
     * Gets value in Integer.
     *
     * @return the value in Integer.
     */
    public Integer getValueInteger() {
        return Double.valueOf(getValue()).intValue();
    }

    /**
     * Gets value in Long.
     *
     * @return the value in Long
     */
    public Long getValueLong() {
        return Double.valueOf(getValue()).longValue();
    }

    /**
     * Gets value in Double.
     *
     * @return the value in double
     */
    public Double getValueDouble() {
        return Double.valueOf(getValue());
    }

    /**
     * Gets raw min value.
     *
     * @return the raw min value.
     */
    public String getMinValue() {
        return getFirstThumb().getAttribute("aria-valuemin");
    }

    /**
     * Gets min value in Integer.
     *
     * @return the min value in Integer.
     */
    public Integer getMinValueInteger() {
        return Double.valueOf(getMinValue()).intValue();
    }

    /**
     * Gets min value in Long.
     *
     * @return the min value in Long
     */
    public Long getMinValueLong() {
        return Double.valueOf(getMinValue()).longValue();
    }

    /**
     * Gets min value in Double.
     *
     * @return the min value in double
     */
    public Double getMinValueDouble() {
        return Double.valueOf(getMinValue());
    }

    public String getMaxValue() {
        return getFirstThumb().getAttribute("aria-valuemax");
    }

    /**
     * Gets max value in Integer.
     *
     * @return the max value in Integer.
     */
    public Integer getMaxValueInteger() {
        return Double.valueOf(getMaxValue()).intValue();
    }

    /**
     * Gets max value in Long.
     *
     * @return the max value in Long
     */
    public Long getMaxValueLong() {
        return Double.valueOf(getMaxValue()).longValue();
    }

    /**
     * Gets max value in Double.
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
    public WebComponent getFirstThumb() {
        return driver.mapElement(element.findElement(config.sliderThumbLocator()));
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
     * Is the slider inverted
     *
     * @return true if the slide has track="inverted" specified.
     */
    public boolean isInverted() {
        return WebComponentUtils.attributeContains(element, "class", config.getCssPrefix() + "Slider-trackInverted");
    }

    /**
     * Move the thumb by value.
     *
     * @param value
     *         the new integer value to set
     * @see #moveThumb(double)
     */
    public void setValue(Integer value) {
        setValue(value.doubleValue());
    }

    /**
     * Move the thumb by value.
     *
     * @param value
     *         the new long value to set
     * @see #moveThumb(double)
     */
    public void setValue(Long value) {
        setValue(value.doubleValue());
    }

    /**
     * Move the thumb by value.
     *
     * @param value
     *         the new double value to set
     * @see #moveThumb(double)
     */
    public void setValue(Double value) {
        Double maxValue = getMaxValueDouble();
        Double minValue = getMinValueDouble();
        if (Precision.compareTo(value, maxValue, 0.0001d) == 1 || Precision.compareTo(value, minValue, 0.0001d) == -1) {
            throw new IllegalArgumentException(
                    String.format("value %.2f is not in the range of %.2f, %.2f", value, minValue, maxValue));
        }

        moveThumb((inverseScaleFunction.apply(value) - minValue) / (maxValue - minValue));
    }

    /**
     * Moves thumb by percentage.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified percentage may not be accurately reflect the real value, an example is that:
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * then it is not possible to accurately move the thumb to a position like 55.5% for value 555 as the only possible
     * locations are 55px and 56px for 550 and 560.
     * </p>
     *
     * @param percentage
     *         the percentage to move to, must between [0.0, 1.0]
     */
    @SneakyThrows
    @SuppressWarnings("squid:S2184")
    public void moveThumb(double percentage) {
        if (Precision.compareTo(percentage, 1, 0.0001d) == 1 || Precision.compareTo(percentage, 0, 0.0001d) == -1) {
            throw new IllegalArgumentException("Percentage must be in range of [0.0, 1.0]");
        }
        WebComponent thumb = getFirstThumb();
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
        if (vertical) {
            actions.moveToElement(element).clickAndHold(thumb)
                    .moveByOffset(0, (int) (start + (end - start) * percentage) - thumbCenter.y).release().perform();
        } else {
            actions.moveToElement(element).clickAndHold(thumb)
                    .moveByOffset((int) (start + (end - start) * percentage) - thumbCenter.x, 0).release().perform();
        }
    }
}
