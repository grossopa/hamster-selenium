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

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * The Material UI Rating implementation
 *
 * <p>Ratings provide insight regarding others' opinions and experiences,
 * and can allow the user to submit a rating of their own.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/rating/">
 * https://material-ui.com/components/rating/</a>
 * @since 1.0
 */
public class MuiRating extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Rating";

    /**
     * Constructs an MuiRating instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiRating(WebElement element, ComponentWebDriver driver, MuiConfig config) {
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
     * Gets the current rating value.
     *
     * @return the current rating value
     */
    public double getValue() {
        // editable ratings expose the current value via the checked radio input, including fractional values
        List<WebElement> checkedInputs = element.findElements(By.cssSelector("input:checked"));
        if (!checkedInputs.isEmpty()) {
            String value = checkedInputs.get(0).getAttribute("value");
            return StringUtils.isEmpty(value) ? 0 : Double.parseDouble(value);
        }
        // read-only ratings render without radio inputs, calculate the value based on the filled icons
        List<WebComponent> stars = getStars();
        double precision = getPrecision();
        if (precision < 1) {
            double filledIcons = stars.stream()
                    .filter(star -> star.attributeContains(CLASS, config.getCssPrefix() + "Rating-iconFilled"))
                    .count();
            return filledIcons * precision;
        }
        for (int i = stars.size() - 1; i >= 0; i--) {
            WebComponent star = stars.get(i);
            if (star.attributeContains(CLASS, config.getCssPrefix() + "Rating-iconFilled")) {
                return i + 1d;
            }
        }
        return 0;
    }

    /**
     * Sets the rating value by clicking on the appropriate star.
     *
     * @param value the rating value to set
     */
    public void setValue(int value) {
        setValue((double) value);
    }

    /**
     * Sets the rating value by clicking on the appropriate position, fractional values are supported when the rating
     * has a precision smaller than 1 (e.g. half stars with precision 0.5).
     *
     * @param value the rating value to set
     * @since 1.15
     */
    public void setValue(double value) {
        int maxValue = getMaxValue();
        if (value < 0 || value > maxValue) {
            throw new IllegalArgumentException("Invalid rating value: " + value);
        }
        if (value == 0d) {
            return;
        }
        double precision = getPrecision();
        if (precision >= 1) {
            // parent is a clickable label element
            getStars().get((int) Math.round(value) - 1).findComponent(By2.parent()).click();
            return;
        }
        // for fractional values click the corresponding position of the target star,
        // the mouse move event of the rating will resolve the fractional value
        int starIndex = (int) Math.ceil(value);
        double fraction = value - (starIndex - 1);
        int iconsPerStar = (int) Math.round(1 / precision);
        WebComponent label = getStars().get(starIndex * iconsPerStar - 1).findComponent(By2.parent());
        Dimension size = label.getSize();
        // Actions offset is relative to the element center, hence the additional -0.5 shift
        int xOffset = (int) (size.getWidth() * (fraction - precision / 4 - 0.5));
        driver.createActions().moveToElement(label, xOffset, size.getHeight() / 2).click().perform();
    }

    /**
     * Gets all star components in the rating.
     *
     * @return list of star components
     */
    public List<WebComponent> getStars() {
        return element.findElements(By.className(config.getCssPrefix() + "Rating-icon"))
                .stream()
                .map(driver::mapElement)
                .toList();
    }

    /**
     * Checks if the rating component is read-only.
     *
     * @return true if the rating is read-only, false otherwise
     */
    public boolean isReadOnly() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "-readOnly");
    }

    /**
     * Gets the precision of the rating, e.g. 0.5 for half star ratings.
     *
     * @return the precision, 1 by default for whole star ratings
     * @since 1.15
     */
    public double getPrecision() {
        List<WebComponent> stars = getStars();
        List<WebElement> decimals = element.findElements(By.className(config.getCssPrefix() + "Rating-decimal"));
        if (decimals.isEmpty() || stars.isEmpty()) {
            return 1;
        }
        return (double) decimals.size() / stars.size();
    }

    /**
     * Gets the maximum possible rating value (number of stars).
     *
     * @return the maximum rating value
     * @since 1.15
     */
    public int getMaxValue() {
        return (int) Math.round(getStars().size() * getPrecision());
    }
}
