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
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.hamster.selenium.core.component.util.WebComponentUtils;
import org.hamster.selenium.core.locator.By2;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A MUI Slider wrapper.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/slider/">
 * https://material-ui.com/components/slider/</a>
 * @since 1.0
 */
public class MuiSlider extends AbstractMuiComponent {

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
        super(element, driver, config);
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
        return Integer.valueOf(getValue());
    }

    /**
     * Gets value in Long.
     *
     * @return the value in Long
     */
    public Long getValueLong() {
        return Long.valueOf(getValue());
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
     * Gets value in BigInteger.
     *
     * @return the value in BigInteger
     */
    public BigInteger getValueBigInteger() {
        return new BigInteger(getValue());
    }

    /**
     * Gets value in BigDecimal.
     *
     * @return the value in BigDecimal
     */
    public BigDecimal getValueBigDecimal() {
        return new BigDecimal(getValue());
    }

    /**
     * Is the slider vertical.
     *
     * @return true if the slider has orientation="vertical" specified.
     */
    public boolean isVertical() {
        return WebComponentUtils.attributeContains(element, "class", config.getCssPrefix() + "Slider-thumb");
    }

    /**
     * Is the slider inverted
     *
     * @return true if the slide has track="inverted" specified.
     */
    public boolean isInverted() {
        return WebComponentUtils.attributeContains(element, "class", config.getCssPrefix() + "Slider-trackInverted");
    }

    @SneakyThrows
    public void moveThumb(double percentage) {
        WebElement thumb = element.findElement(config.sliderThumbLocator());
        Rectangle rect = element.getRect();

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

        if (inverted) {
            double temp = start;
            start = end;
            end = temp;
        }

        Point thumbCenter = WebComponentUtils.getCenter(thumb.getRect());
        Actions actions = new Actions(driver.getDriver());
        if (vertical) {
            actions.clickAndHold(thumb).moveByOffset(0, (int) (start + (end - start) * percentage) - thumbCenter.y)
                    .release().perform();
        } else {
            actions.clickAndHold(thumb).moveByOffset((int) (start + (end - start) * percentage) - thumbCenter.x, 0)
                    .release().perform();
        }


    }


}
