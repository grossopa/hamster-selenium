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

package com.github.grossopa.selenium.component.mui.v4.inputs;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.function.UnaryOperator;

import static java.util.Arrays.asList;
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

    Actions actions = mock(Actions.class);
    Actions actions1 = mock(Actions.class);
    Actions actions2 = mock(Actions.class);

    By sliderThumbLocator = mock(By.class);

    WebElement thumb1 = mock(WebElement.class);
    String rawValue1 = "160.0";

    WebElement thumb2 = mock(WebElement.class);
    String rawValue2 = "240.0";

    Integer x = 50;
    Integer y = 150;
    Integer height = 400;
    Integer width = 400;
    Double minValue = 100d;
    Double maxValue = 300d;
    boolean vertical = false;

    Integer thumbRadius = 20;

    private double moveByOffsetAnswer(String rawValue, InvocationOnMock answer) {
        int targetX = answer.getArgument(0);
        int targetY = answer.getArgument(1);
        double currentPercentage = (Double.parseDouble(rawValue) - minValue) / (maxValue - minValue);
        double deltaPercentage;
        if (vertical) {
            deltaPercentage = (double) -targetY / (double) height;
        } else {
            deltaPercentage = (double) targetX / (double) width;
        }
        return minValue + (currentPercentage + deltaPercentage) * (maxValue - minValue);
    }

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.sliderThumbLocator()).thenReturn(sliderThumbLocator);
        when(driver.mapElement(any())).then(a -> new DefaultWebComponent(a.getArgument(0), driver));
        when(driver.createActions()).thenReturn(actions);

        when(actions.moveToElement(element)).thenReturn(actions);
        when(actions.clickAndHold(any())).then(a -> {
            MuiSliderThumb thumb = a.getArgument(0);
            if (thumb.getWrappedElement() == thumb1) {
                return actions1;
            } else {
                return actions2;
            }
        });
        when(actions1.moveByOffset(anyInt(), anyInt())).then(a -> {
            double doubleValue = moveByOffsetAnswer(rawValue1, a);
            rawValue1 = String.valueOf((double) Math.round(doubleValue * 100) / 100);
            return actions1;
        });

        when(actions2.moveByOffset(anyInt(), anyInt())).then(a -> {
            double doubleValue = moveByOffsetAnswer(rawValue2, a);
            rawValue2 = String.valueOf((double) Math.round(doubleValue * 100) / 100);
            return actions2;
        });

        when(actions1.release()).thenReturn(actions1);
        when(actions2.release()).thenReturn(actions2);

        when(element.findElement(sliderThumbLocator)).thenReturn(thumb1);
        when(element.findElements(sliderThumbLocator)).thenReturn(asList(thumb1, thumb2));
        when(element.findElement(By2.attrExact("type", "hidden", "input"))).thenReturn(hiddenInput);
        when(element.getRect()).then(a -> new Rectangle(x, y, height, width));
        when(element.getDomAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary");
        when(hiddenInput.getDomAttribute("value")).then(a -> rawValue1);

        when(thumb1.getDomAttribute("aria-valuemin")).then(a -> minValue.toString());
        when(thumb1.getDomAttribute("aria-valuemax")).then(a -> maxValue.toString());
        when(thumb1.getDomAttribute("aria-valuenow")).then(a -> rawValue1);
        when(thumb1.getRect()).then(a -> {
            double currentPercentage = (Double.parseDouble(rawValue1) - minValue) / (maxValue - minValue);

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

        when(thumb2.getDomAttribute("aria-valuemin")).then(a -> minValue.toString());
        when(thumb2.getDomAttribute("aria-valuemax")).then(a -> maxValue.toString());
        when(thumb2.getDomAttribute("aria-valuenow")).then(a -> rawValue2);
        when(thumb2.getRect()).then(a -> {
            double currentPercentage = (Double.parseDouble(rawValue2) - minValue) / (maxValue - minValue);

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
        assertEquals(thumb1, testSubject.getFirstThumb().getWrappedElement());
    }

    @Test
    void isVertical() {
        when(element.getDomAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary MuiSlider-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test
    void isVerticalNegative() {
        when(element.getDomAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary");
        assertFalse(testSubject.isVertical());
    }

    @Test
    void isInverted() {
        when(element.getDomAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary MuiSlider-trackInverted");
        assertTrue(testSubject.isInverted());
    }

    @Test
    void isInvertedNegative() {
        when(element.getDomAttribute("class")).then(a -> "MuiSlider-root MuiSlider-colorPrimary");
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
        assertEquals("70,330,40,40", rectToString(thumb1.getRect()));

        testSubject.moveThumb(0.2);
        assertEquals("140.0", testSubject.getValue());
        assertEquals("110,330,40,40", rectToString(thumb1.getRect()));

        testSubject.moveThumb(1.0);
        assertEquals("300.0", testSubject.getValue());
        assertEquals("430,330,40,40", rectToString(thumb1.getRect()));

        testSubject.moveThumb(0.0);
        assertEquals("100.0", testSubject.getValue());
        assertEquals("30,330,40,40", rectToString(thumb1.getRect()));
    }

    @Test
    void moveThumbNormalVertical() {
        // sets and verify vertical
        isVertical();
        vertical = true;

        testSubject.moveThumb(0.1);
        assertEquals("120.0", testSubject.getValue());
        assertEquals("230,490,40,40", rectToString(thumb1.getRect()));

        testSubject.moveThumb(0.2);
        assertEquals("140.0", testSubject.getValue());
        assertEquals("230,450,40,40", rectToString(thumb1.getRect()));

        testSubject.moveThumb(1.0);
        assertEquals("300.0", testSubject.getValue());
        assertEquals("230,130,40,40", rectToString(thumb1.getRect()));

        testSubject.moveThumb(0.0);
        assertEquals("100.0", testSubject.getValue());
        assertEquals("230,530,40,40", rectToString(thumb1.getRect()));
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

    @Test
    void setValueIntegerByIndex1() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(0, 280);
        assertEquals("390,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueIntegerByIndex2() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(1, 280);
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("390,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueIntegerByRef1() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(new MuiSliderThumb(thumb1, driver, config), 280);
        assertEquals("390,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueIntegerByRef2() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(new MuiSliderThumb(thumb2, driver, config), 280);
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("390,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueLongByIndex1() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(0, 280L);
        assertEquals("390,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueLongByIndex2() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(1, 280L);
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("390,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueLongByRef1() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(new MuiSliderThumb(thumb1, driver, config), 280L);
        assertEquals("390,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueLongByRef2() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(new MuiSliderThumb(thumb2, driver, config), 280L);
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("390,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueDoubleByIndex1() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(0, 280d);
        assertEquals("390,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueDoubleByIndex2() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(1, 280d);
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("390,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueDoubleByRef1() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(new MuiSliderThumb(thumb1, driver, config), 280d);
        assertEquals("390,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void setValueDoubleByRef2() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.setValue(new MuiSliderThumb(thumb2, driver, config), 280d);
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("390,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void moveThumbByIndex1() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.moveThumb(0, 0.9d);
        assertEquals("390,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void moveThumbByIndex2() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.moveThumb(1, 0.9d);
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("390,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void moveThumbByRef1() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.moveThumb(new MuiSliderThumb(thumb1, driver, config), 0.9d);
        assertEquals("390,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void moveThumbByREf2() {
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("310,330,40,40", rectToString(thumb2.getRect()));
        testSubject.moveThumb(new MuiSliderThumb(thumb2, driver, config), 0.9d);
        assertEquals("150,330,40,40", rectToString(thumb1.getRect()));
        assertEquals("390,330,40,40", rectToString(thumb2.getRect()));
    }

    @Test
    void getAllThumbs() {
        assertEquals(2, testSubject.getAllThumbs().size());
        assertEquals(thumb1, testSubject.getAllThumbs().get(0).getWrappedElement());
        assertEquals(thumb2, testSubject.getAllThumbs().get(1).getWrappedElement());
    }

    @Test
    void getInverseScaleFunction() {
        testSubject = new MuiSlider(element, driver, config, x -> x * 3);
        assertEquals(9, testSubject.getInverseScaleFunction().apply(3d).intValue());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        UnaryOperator<Double> inverseFunction1 = mock(UnaryOperator.class);
        UnaryOperator<Double> inverseFunction2 = mock(UnaryOperator.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new MuiSlider(element1, driver, config, inverseFunction1),
                new MuiSlider(element1, driver, config, inverseFunction1));
        tester.addEqualityGroup(new MuiSlider(element1, driver, config, inverseFunction2));
        tester.addEqualityGroup(new MuiSlider(element2, driver, config, inverseFunction1));
        tester.addEqualityGroup(new MuiSlider(element2, driver, config, inverseFunction2));

        tester.testEquals();
    }

    @Test
    @SuppressWarnings("unchecked")
    void testToString() {
        UnaryOperator<Double> inverseFunction = mock(UnaryOperator.class);
        testSubject = new MuiSlider(element, driver, config, inverseFunction);
        when(element.toString()).thenReturn("element-toString");
        when(inverseFunction.toString()).thenReturn("inverseFunction-toString");
        assertEquals("MuiSlider{inverseScaleFunction=inverseFunction-toString, element=element-toString}",
                testSubject.toString());
    }
}
