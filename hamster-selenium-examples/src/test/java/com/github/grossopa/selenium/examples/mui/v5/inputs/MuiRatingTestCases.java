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

package com.github.grossopa.selenium.examples.mui.v5.inputs;

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiRating;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiRating}
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiRatingTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic rating.
     *
     * @see <a href="https://mui.com/material-ui/react-rating/#basic-rating">
     * https://mui.com/material-ui/react-rating/#basic-rating</a>
     */
    public void testBasicRating() {
        driver.navigate().to("https://mui.com/material-ui/react-rating/");

        // Test controlled rating
        MuiRating controlledRating = driver.findComponent(By.id("BasicRating.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), 'Controlled')]/following::*[1]"))
                .as(muiV5()).toRating();
        assertTrue(controlledRating.validate());

        // Check initial value
        assertEquals(2.0, controlledRating.getValue(), 0.1);

        // Test setting value
        controlledRating.setValue(4);
        assertEquals(4.0, controlledRating.getValue(), 0.1);

        // Test uncontrolled rating
        MuiRating uncontrolledRating = driver.findComponent(By.id("BasicRating.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), 'Uncontrolled')]/following::*[1]"))
                .as(muiV5()).toRating();
        assertTrue(uncontrolledRating.validate());

        // Test read only rating
        MuiRating readOnlyRating = driver.findComponent(By.id("BasicRating.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), 'Read only')]/following::*[1]"))
                .as(muiV5()).toRating();
        assertTrue(readOnlyRating.validate());
        assertTrue(readOnlyRating.isReadOnly());

        // Test disabled rating
        MuiRating disabledRating = driver.findComponent(By.id("BasicRating.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), 'Disabled')]/following::*[1]"))
                .as(muiV5()).toRating();
        assertTrue(disabledRating.validate());
    }

    /**
     * Tests the rating precision.
     *
     * @see <a href="https://mui.com/material-ui/react-rating/#rating-precision">
     * https://mui.com/material-ui/react-rating/#rating-precision</a>
     */
    public void testRatingPrecision() {
        driver.navigate().to("https://mui.com/material-ui/react-rating/");

        // Test rating with precision of 0.5 (half stars)
        MuiRating halfRating = driver.findComponent(
                        By.xpath("//input[@name='half-rating']/ancestor::span[contains(@class, 'MuiRating-root')]"))
                .as(muiV5()).toRating();
        assertTrue(halfRating.validate());
        assertEquals(0.5, halfRating.getPrecision(), 0.01);
        assertEquals(2.5, halfRating.getValue(), 0.1);

        // Test setting a fractional value
        halfRating.setValue(4.5);
        assertEquals(4.5, halfRating.getValue(), 0.1);

        // Test read only rating with precision (rendered without radio inputs)
        MuiRating readOnlyHalfRating = driver.findComponent(By.xpath("//span[@role='img' and @aria-label='2.5 Stars']"))
                .as(muiV5()).toRating();
        assertTrue(readOnlyHalfRating.validate());
        assertTrue(readOnlyHalfRating.isReadOnly());
        assertEquals(2.5, readOnlyHalfRating.getValue(), 0.1);
    }

    /**
     * Tests the customized rating.
     *
     * @see <a href="https://mui.com/material-ui/react-rating/#customization">
     * https://mui.com/material-ui/react-rating/#customization</a>
     */
    public void testCustomizedRating() {
        driver.navigate().to("https://mui.com/material-ui/react-rating/");

        // Test 10 stars rating
        MuiRating tenStarsRating = driver.findComponent(By.id("CustomizedRating.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), '10 stars')]/following::*[1]"))
                .as(muiV5()).toRating();
        assertTrue(tenStarsRating.validate());

        List<WebComponent> stars = tenStarsRating.getStars();
        assertEquals(10, stars.size());

        tenStarsRating.setValue(8);
        assertEquals(8.0, tenStarsRating.getValue(), 0.1);
    }

    public static void main(String[] args) {
        MuiRatingTestCases test = new MuiRatingTestCases();
        test.setUpDriver(EDGE);
        driver.navigate().to("https://mui.com/material-ui/react-rating/");

        test.testBasicRating();
        test.testRatingPrecision();
        test.testCustomizedRating();
    }
}