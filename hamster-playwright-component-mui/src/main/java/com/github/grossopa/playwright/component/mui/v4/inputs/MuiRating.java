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
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * The Material UI Rating implementation for Playwright.
 *
 * <p>Ratings provide insight regarding others' opinions and experiences,
 * and can allow the user to submit a rating of their own.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/rating/">
 * https://material-ui.com/components/rating/</a>
 * @since 1.12
 */
public class MuiRating extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Rating";

    /**
     * Constructs an MuiRating instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiRating(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
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
     * Gets the current rating value.
     *
     * @return the current rating value (0 if no stars are filled)
     */
    public double getValue() {
        List<WebComponent> stars = getStars();
        for (int i = stars.size() - 1; i >= 0; i--) {
            WebComponent star = stars.get(i);
            String className = star.getAttribute("class");
            if (className != null && className.contains(config.getCssPrefix() + "Rating-iconFilled")) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * Sets the rating value by clicking on the appropriate star.
     *
     * @param value the rating value to set (must be between 0 and max stars)
     * @throws IllegalArgumentException if value is out of valid range
     */
    public void setValue(int value) {
        List<WebComponent> stars = getStars();
        if (value < 0 || value > stars.size()) {
            throw new IllegalArgumentException("Invalid rating value: " + value + ". Must be between 0 and " + stars.size());
        }
        if (value > 0) {
            // Click on the star at the specified position
            // In MUI, each star is wrapped in a clickable label/button
            WebComponent targetStar = stars.get(value - 1);
            targetStar.click();
        }
    }

    /**
     * Gets all star components in the rating.
     *
     * @return list of star WebComponents
     */
    public List<WebComponent> getStars() {
        return findComponents("." + config.getCssPrefix() + "Rating-icon");
    }

    /**
     * Checks if the rating component is read-only.
     *
     * @return true if the rating is read-only, false otherwise
     */
    public boolean isReadOnly() {
        String className = getAttribute("class");
        return className != null && className.contains(config.getCssPrefix() + "-readOnly");
    }

    /**
     * Gets the maximum possible rating value (number of stars).
     *
     * @return the maximum rating value
     */
    public int getMaxValue() {
        return getStars().size();
    }
}
