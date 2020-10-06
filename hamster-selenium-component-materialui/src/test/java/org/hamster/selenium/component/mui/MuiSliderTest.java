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

import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.DefaultWebComponent;
import org.hamster.selenium.core.locator.By2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiSlider}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiSliderTest {

    MuiSlider testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);
    WebElement hiddenInput = mock(WebElement.class);
    WebElement thumb = mock(WebElement.class);
    Actions actions = mock(Actions.class);
    By sliderThumbLocator = mock(By.class);

    String rawValue = "160.0";

    Integer x = 50;
    Integer y = 150;
    Integer height = 400;
    Integer width = 400;
    Double minValue = 100d;
    Double maxValue = 300d;
    boolean inverted = false;
    boolean vertical = false;

    Integer thumbRadius = 20;

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.sliderThumbLocator()).thenReturn(sliderThumbLocator);
        when(driver.mapElement(any())).then(a -> new DefaultWebComponent(a.getArgument(0), driver));
        when(driver.createActions()).thenReturn(actions);

        when(actions.moveToElement(eq(element))).thenReturn(actions);
        when(actions.clickAndHold(any())).thenReturn(actions);
        when(actions.moveByOffset(anyInt(), anyInt())).then(a -> {
            int targetX = a.getArgument(0);
            int targetY = a.getArgument(1);
            double currentPercentage = (Double.parseDouble(rawValue) - minValue) / (maxValue - minValue);
            double deltaPercentage;
            if (vertical) {
                deltaPercentage = (double) -targetY / (double) height;
            } else {
                deltaPercentage = (double) targetX / (double) width;
            }
            double doubleValue = minValue + (currentPercentage + deltaPercentage) * (maxValue - minValue);
            rawValue = String.valueOf((double) Math.round(doubleValue * 100) / 100);
            return actions;
        });

        when(actions.release()).thenReturn(actions);

        when(element.findElement(sliderThumbLocator)).thenReturn(thumb);
        when(element.findElement(By2.attr("type", "hidden").exact().tag("input").build())).thenReturn(hiddenInput);
        when(element.getRect()).then(a -> new Rectangle(x, y, height, width));
        when(element.getAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary");
        when(hiddenInput.getAttribute("value")).then(a -> rawValue);
        when(thumb.getAttribute("aria-valuemin")).then(a -> minValue.toString());
        when(thumb.getAttribute("aria-valuemax")).then(a -> maxValue.toString());
        when(thumb.getRect()).then(a -> {
            double currentPercentage = (Double.parseDouble(rawValue) - minValue) / (maxValue - minValue);

            int thumbX;
            int thumbY;
            if (vertical) {
                thumbX = x + width / 2 - thumbRadius;
                thumbY = (int) Math.round(y + height * (1 - currentPercentage)) - thumbRadius;
            } else {
                thumbX = (int) Math.round(x + width * currentPercentage) - thumbRadius;
                thumbY = y + height / 2 - thumbRadius;
            }

            return new Rectangle(thumbX, thumbY, thumbRadius << 1, thumbRadius << 1);
        });

        testSubject = new MuiSlider(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("Slider", testSubject.getComponentName());
    }

    @Test
    void getValue() {
        assertEquals("160.0", testSubject.getValue());
    }

    @Test
    void getValueInteger() {
        assertEquals(160, testSubject.getValueInteger());
    }

    @Test
    void getValueLong() {
        assertEquals(160L, testSubject.getValueLong());
    }

    @Test
    void getValueDouble() {
        assertEquals(160d, testSubject.getValueDouble());
    }

    @Test
    void getMinValue() {
        assertEquals(minValue.toString(), testSubject.getMinValue());
    }

    @Test
    void getMinValueInteger() {
        assertEquals(minValue.intValue(), testSubject.getMinValueInteger());
    }

    @Test
    void getMinValueLong() {
        assertEquals(minValue.longValue(), testSubject.getMinValueLong());
    }

    @Test
    void getMinValueDouble() {
        assertEquals(minValue, testSubject.getMinValueDouble());
    }

    @Test
    void getMaxValue() {
        assertEquals(maxValue.toString(), testSubject.getMaxValue());
    }

    @Test
    void getMaxValueInteger() {
        assertEquals(maxValue.intValue(), testSubject.getMaxValueInteger());
    }

    @Test
    void getMaxValueLong() {
        assertEquals(maxValue.longValue(), testSubject.getMaxValueLong());
    }

    @Test
    void getMaxValueDouble() {
        assertEquals(maxValue, testSubject.getMaxValueDouble());
    }

    @Test
    void getFirstThumb() {
        assertEquals(thumb, testSubject.getFirstThumb().getWrappedElement());
    }

    @Test
    void isVertical() {
        when(element.getAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary MuiSlider-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test
    void isVerticalNegative() {
        when(element.getAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary");
        assertFalse(testSubject.isVertical());
    }

    @Test
    void isInverted() {
        when(element.getAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary MuiSlider-trackInverted");
        assertTrue(testSubject.isInverted());
    }

    @Test
    void isInvertedNegative() {
        when(element.getAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary");
        assertFalse(testSubject.isInverted());
    }

    @Test
    void setValueInteger() {
        testSubject.setValue(160);
        assertEquals("160.0", testSubject.getValue());

        testSubject.setValue(240);
        assertEquals("240.0", testSubject.getValue());

        testSubject.setValue(100);
        assertEquals("100.0", testSubject.getValue());

        testSubject.setValue(300);
        assertEquals("300.0", testSubject.getValue());
    }

    @Test
    void setValueLong() {
        testSubject.setValue(160L);
        assertEquals("160.0", testSubject.getValue());

        testSubject.setValue(240L);
        assertEquals("240.0", testSubject.getValue());

        testSubject.setValue(100L);
        assertEquals("100.0", testSubject.getValue());

        testSubject.setValue(300L);
        assertEquals("300.0", testSubject.getValue());
    }

    @Test
    void setValueDouble() {
        testSubject.setValue(160D);
        assertEquals("160.0", testSubject.getValue());

        testSubject.setValue(240D);
        assertEquals("240.0", testSubject.getValue());

        testSubject.setValue(100D);
        assertEquals("100.0", testSubject.getValue());

        testSubject.setValue(300D);
        assertEquals("300.0", testSubject.getValue());
    }

    @Test
    void setValueIntegerOutBoundRight() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(301));
    }

    @Test
    void setValueIntegerOutBoundLeft() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(99));
    }

    @Test
    void moveThumbNormal() {
        testSubject.moveThumb(0.1);
        assertEquals("120.0", testSubject.getValue());
        assertEquals("70,330,40,40", rectToString(thumb.getRect()));

        testSubject.moveThumb(0.2);
        assertEquals("140.0", testSubject.getValue());
        assertEquals("110,330,40,40", rectToString(thumb.getRect()));

        testSubject.moveThumb(1.0);
        assertEquals("300.0", testSubject.getValue());
        assertEquals("430,330,40,40", rectToString(thumb.getRect()));

        testSubject.moveThumb(0.0);
        assertEquals("100.0", testSubject.getValue());
        assertEquals("30,330,40,40", rectToString(thumb.getRect()));
    }

    @Test
    void moveThumbNormalVertical() {
        // sets and verify vertical
        isVertical();
        vertical = true;

        testSubject.moveThumb(0.1);
        assertEquals("120.0", testSubject.getValue());
        assertEquals("230,490,40,40", rectToString(thumb.getRect()));

        testSubject.moveThumb(0.2);
        assertEquals("140.0", testSubject.getValue());
        assertEquals("230,450,40,40", rectToString(thumb.getRect()));

        testSubject.moveThumb(1.0);
        assertEquals("300.0", testSubject.getValue());
        assertEquals("230,130,40,40", rectToString(thumb.getRect()));

        testSubject.moveThumb(0.0);
        assertEquals("100.0", testSubject.getValue());
        assertEquals("230,530,40,40", rectToString(thumb.getRect()));
    }

    @Test
    void moveThumbOutBoundLeft() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.moveThumb(-0.1));
    }

    @Test
    void moveThumbOutBoundRight() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.moveThumb(1.2));
    }

    private String rectToString(Rectangle rect) {
        return rect.x + "," + rect.y + "," + rect.width + "," + rect.height;
    }
}