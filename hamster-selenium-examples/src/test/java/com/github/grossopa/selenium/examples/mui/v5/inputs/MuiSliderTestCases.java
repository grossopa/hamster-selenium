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

package com.github.grossopa.selenium.examples.mui.v5.inputs;

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiSlider;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiSliderThumb;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiSlider}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiSliderTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic continuous sliders
     *
     * @see <a href="https://mui.com/components/slider/#continuous-sliders">
     * https://mui.com/components/slider/#continuous-sliders</a>
     */
    public void testContinuousSliders() {
        List<MuiSlider> sliderList = driver.findComponent(By.id("ContinuousSlider.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiSlider-root"), c -> c.as(muiV5()).toSlider());
        MuiSlider slider = sliderList.get(0);
        assertTrue(slider.validate());

        assertEquals(0, slider.getMinValueInteger());
        assertEquals(100, slider.getMaxValueInteger());

        slider.setValue(0);
        assertEquals(0, slider.getValueInteger());
        slider.setValue(100);
        assertEquals(100, slider.getValueInteger());
        slider.setValue(50);
        assertEquals(50, slider.getValueInteger());
        slider.moveThumb(0.8);
        assertEquals(80, slider.getValueInteger());

        MuiSlider disabledSlider = sliderList.get(1);
        assertTrue(disabledSlider.validate());
        assertFalse(disabledSlider.isEnabled());
        assertEquals(30, disabledSlider.getValueInteger());
    }

    /**
     * Tests the basic discrete sliders.
     *
     * @see <a href="https://mui.com/components/slider/#discrete-sliders">
     * https://mui.com/components/slider/#discrete-sliders</a>
     */
    public void testDiscreteSliders() {
        MuiSlider slider = driver.findComponent(By.id("DiscreteSlider.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiSlider-root")).as(muiV5()).toSlider();
        assertTrue(slider.validate());
        assertEquals(10, slider.getMinValueInteger());
        assertEquals(110, slider.getMaxValueInteger());
        assertEquals(30, slider.getValueInteger());

        slider.moveThumb(0.78);
        assertEquals(90, slider.getValueInteger());
        slider.moveThumb(0.03);
        assertEquals(10, slider.getValueInteger());
        slider.setValue(72);
        assertEquals(70, slider.getValueInteger());
    }

    /**
     * to restrict the value to be selected.
     *
     * @see <a href="https://mui.com/components/slider/#restricted-values">
     * https://mui.com/components/slider/#restricted-values</a>
     */
    public void testRestrictedValues() {
        MuiSlider slider = driver.findComponent(By.id("DiscreteSliderValues.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiSlider-root")).as(muiV5()).toSlider();

        assertTrue(slider.validate());
        assertEquals(0, slider.getMinValueInteger());
        assertEquals(100, slider.getMaxValueInteger());
        assertEquals(20, slider.getValueInteger());
        slider.setValue(98);
        assertEquals(100, slider.getValueInteger());
    }

    /**
     * To select a range instead of a single value
     *
     * @see <a href="https://mui.com/components/slider/#range-slider">
     * https://mui.com/components/slider/#range-slider</a>
     */
    public void testRangeSlider() {
        MuiSlider slider = driver.findComponent(By.id("RangeSlider.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiSlider-root")).as(muiV5()).toSlider();
        assertTrue(slider.validate());
        assertEquals(0, slider.getMinValueInteger());
        assertEquals(100, slider.getMaxValueInteger());

        slider.setValue(0, 90);
        // some buffer time for switching the indexes
        driver.threadSleep(100L);
        // note the order of thumbs will revert, so the first 0 and second 0 are actually different thumbs
        slider.setValue(0, 30);

        List<MuiSliderThumb> thumbs = slider.getAllThumbs();
        assertEquals(2, thumbs.size());
        // note the order of thumbs will revert
        assertEquals("30", thumbs.get(0).getValue());
        assertEquals("90", thumbs.get(1).getValue());
    }

    /**
     * To test the vertical one if that works
     *
     * @see <a href="https://mui.com/components/slider/#vertical-sliders">
     * https://mui.com/components/slider/#vertical-sliders</a>
     */
    public void testVerticalSliders() {
        List<MuiSlider> sliderList = driver.findComponent(By.id("VerticalSlider.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiSlider-root"), c -> c.as(muiV5()).toSlider());
        sliderList.forEach(slider -> assertTrue(slider.validate()));
        assertEquals(3, sliderList.size());

        MuiSlider slider1 = sliderList.get(0);
        assertEquals(0, slider1.getMinValueInteger());
        assertEquals(100, slider1.getMaxValueInteger());
        slider1.setValue(10);
        assertEquals(10, slider1.getValueInteger());
        slider1.moveThumb(1);
        assertEquals(100, slider1.getValueInteger());
        slider1.moveThumb(0);
        assertEquals(0, slider1.getValueInteger());

        MuiSlider slider2 = sliderList.get(1);
        assertFalse(slider2.isEnabled());

        MuiSlider slider3 = sliderList.get(2);
        slider3.setValue(0, 20);
        slider3.setValue(1, 80);

        List<MuiSliderThumb> thumbs = slider3.getAllThumbs();
        assertEquals(2, thumbs.size());
        assertEquals("20", thumbs.get(0).getValue());
        assertEquals("80", thumbs.get(1).getValue());

        slider3.moveThumb(0, 0.1d);
        slider3.moveThumb(1, 0.9d);
        thumbs = slider3.getAllThumbs();
        assertEquals("10", thumbs.get(0).getValue());
        assertEquals("90", thumbs.get(1).getValue());
    }

    /**
     * Test the turned off track (no difference on actions).
     *
     * @see <a href="https://mui.com/components/slider/#removed-track">
     * https://mui.com/components/slider/#removed-track</a>
     */
    public void testRemovedTrack() {
        List<MuiSlider> sliderList = driver.findComponent(By.id("TrackFalseSlider.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiSlider-root"), c -> c.as(muiV5()).toSlider());
        sliderList.forEach(slider -> assertTrue(slider.validate()));
        assertEquals(2, sliderList.size());

        MuiSlider slider2 = sliderList.get(1);
        slider2.moveThumb(0, 0.2);
        slider2.moveThumb(1, 0.4);
        slider2.moveThumb(2, 0.6);

        List<MuiSliderThumb> thumbs = slider2.getAllThumbs();
        assertEquals("20", thumbs.get(0).getValue());
        assertEquals("40", thumbs.get(1).getValue());
        assertEquals("60", thumbs.get(2).getValue());
    }

    /**
     * Inverted track
     *
     * @see <a href="https://mui.com/components/slider/#inverted-track">
     * https://mui.com/components/slider/#inverted-track</a>
     */
    public void testInvertedTrack() {
        List<MuiSlider> sliderList = driver.findComponent(By.id("TrackInvertedSlider.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiSlider-root"), c -> c.as(muiV5()).toSlider());
        sliderList.forEach(slider -> assertTrue(slider.validate()));
        assertEquals(2, sliderList.size());

        MuiSlider slider1 = sliderList.get(0);
        slider1.moveThumb(0.4);
        assertEquals(40, slider1.getValueInteger());
        slider1.setValue(56);
        assertEquals(56, slider1.getValueInteger());

        MuiSlider slider2 = sliderList.get(1);
        slider2.moveThumb(0, 0.15);
        slider2.moveThumb(1, 0.85);

        List<MuiSliderThumb> thumbs = slider2.getAllThumbs();
        assertEquals("15", thumbs.get(0).getValue());
        assertEquals("85", thumbs.get(1).getValue());
    }

    /**
     * Tests scale with a function.
     *
     * @see <a href="https://mui.com/components/slider/#non-linear-scale">
     * https://mui.com/components/slider/#non-linear-scale</a>
     */
    public void testNonLinearScale() {
        // x -> Math.log(x) / Math.log(2) is inverse function of Math.pow(2, x)
        MuiSlider slider = driver.findComponent(By.id("NonLinearSlider.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiSlider-root")).as(muiV5()).toSlider(x -> Math.log(x) / Math.log(2));
        assertTrue(slider.validate());
        assertEquals(32, slider.getMinValueInteger());
        assertEquals(1073741824, slider.getMaxValueInteger());

        slider.setValue(32);
        assertEquals(32, slider.getValueInteger());
        slider.setValue(1073741824);
        assertEquals(1073741824, slider.getValueInteger());
        slider.setValue(8192);
        assertEquals(8192, slider.getValueInteger());
        slider.setValue(2097152);
        assertEquals(2097152, slider.getValueInteger());
    }

    public static void main(String[] args) {
        MuiSliderTestCases test = new MuiSliderTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/slider/");

        test.testContinuousSliders();
        test.testDiscreteSliders();
        test.testRestrictedValues();
        test.testRangeSlider();
        test.testVerticalSliders();
        test.testRemovedTrack();
        test.testInvertedTrack();
        test.testNonLinearScale();

    }
}
