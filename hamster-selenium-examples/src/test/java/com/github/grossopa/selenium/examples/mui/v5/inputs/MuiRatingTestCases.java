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
        WebComponent controlledContainer = driver.findComponent(By.id("BasicRating.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), 'Controlled')]"))
                .findComponent(By2.parent());

        MuiRating controlledRating = controlledContainer.findComponent(By.className("MuiRating-root"))
                .as(muiV5()).toRating();
        assertTrue(controlledRating.validate());

        // Check initial value
        assertEquals(2.0, controlledRating.getValue(), 0.1);

        // Test setting value
        controlledRating.setValue(4);
        assertEquals(4.0, controlledRating.getValue(), 0.1);

        // Test uncontrolled rating
        WebComponent uncontrolledContainer = driver.findComponent(By.id("BasicRating.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), 'Uncontrolled')]/following::*[1]"));

        MuiRating uncontrolledRating = uncontrolledContainer.findComponent(By.className("MuiRating-root"))
                .as(muiV5()).toRating();
        assertTrue(uncontrolledRating.validate());

        // Test read only rating
        WebComponent readOnlyContainer = driver.findComponent(By.id("BasicRating.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), 'Read only')]/following::*[1]"));

        MuiRating readOnlyRating = readOnlyContainer.findComponent(By.className("MuiRating-root"))
                .as(muiV5()).toRating();
        assertTrue(readOnlyRating.validate());
        assertTrue(readOnlyRating.isReadOnly());

        // Test disabled rating
        WebComponent disabledContainer = driver.findComponent(By.id("BasicRating.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), 'Disabled')]/following::*[1]"));

        MuiRating disabledRating = disabledContainer.findComponent(By.className("MuiRating-root"))
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

        WebComponent precisionContainer = driver.findComponent(By.id("HoverFeedback.js"))
                .findComponent(By2.parent());

        MuiRating precisionRating = precisionContainer.findComponent(By.className("MuiRating-root"))
                .as(muiV5()).toRating();
        assertTrue(precisionRating.validate());

        // Test setting half-star value
        precisionRating.setValue(3);
        assertEquals(3.0, precisionRating.getValue(), 0.1);
    }

    /**
     * Tests the different sizes of rating.
     *
     * @see <a href="https://mui.com/material-ui/react-rating/#sizes">
     * https://mui.com/material-ui/react-rating/#sizes</a>
     */
    public void testRatingSizes() {
        driver.navigate().to("https://mui.com/material-ui/react-rating/");

        List<MuiRating> sizeRatings = driver.findComponent(By.id("Sizes.js"))
                .findComponent(By2.parent())
                .findComponentsAs(By.className("MuiRating-root"), c -> c.as(muiV5()).toRating());

        assertEquals(3, sizeRatings.size());
        sizeRatings.forEach(rating -> assertTrue(rating.validate()));
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
        WebComponent tenStarsContainer = driver.findComponent(By.id("Customization.js"))
                .findComponent(By2.parent())
                .findComponent(By.xpath(".//*[contains(text(), '10 stars')]/following::*[1]"));

        MuiRating tenStarsRating = tenStarsContainer.findComponent(By.className("MuiRating-root"))
                .as(muiV5()).toRating();
        assertTrue(tenStarsRating.validate());

        List<WebComponent> stars = tenStarsRating.getStars();
        assertEquals(10, stars.size());
    }

    public static void main(String[] args) {
        MuiRatingTestCases test = new MuiRatingTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/material-ui/react-rating/");

        test.testBasicRating();
        test.testRatingPrecision();
        test.testRatingSizes();
        test.testCustomizedRating();
    }
}