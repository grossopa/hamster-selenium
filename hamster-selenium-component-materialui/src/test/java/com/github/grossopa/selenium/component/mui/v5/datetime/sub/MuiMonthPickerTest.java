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

package com.github.grossopa.selenium.component.mui.v5.datetime.sub;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v5.datetime.func.EnglishMonthStringFunction;
import com.github.grossopa.selenium.component.mui.v5.datetime.func.MonthStringFunction;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiMonthPicker}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiMonthPickerTest {

    MuiMonthPicker testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    List<WebElement> buttonElements;

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");

        buttonElements = Arrays.stream(Month.values())
                .map(m -> StringUtils.capitalize(m.toString().toLowerCase(Locale.ROOT))).map(m -> {
                    WebElement monthElement = mock(WebElement.class);
                    when(monthElement.getText()).thenReturn(m);
                    return element;
                }).collect(Collectors.toList());

        when(element.findElements(By.xpath(".//button[contains(@class,\"PrivatePickersMonth-root\")]"))).thenReturn(
                buttonElements);

        testSubject = new MuiMonthPicker(element, driver, config);
    }

    @Test
    void version() {
        assertArrayEquals(new Object[]{V5}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        assertEquals("MonthPicker", testSubject.getComponentName());
    }

    @Test
    void getMonthButtons() {
        List<WebComponent> monthButtons = testSubject.getMonthButtons();
        assertEquals(12, monthButtons.size());
    }

    @Test
    void getFirstSelectedMonthButton() {
        when(element.findElement(By.xpath(".//button[contains(@class,\"Mui-selected\")]"))).thenReturn(
                buttonElements.get(7));
        assertEquals(buttonElements.get(7), testSubject.getFirstSelectedMonthButton().getWrappedElement());
    }

    @Test
    void select() {
        when(element.findElement(By.xpath(".//button[contains(text(),\"Jan\")]"))).thenReturn(buttonElements.get(0));
        testSubject.select(Month.JANUARY);
        verify(buttonElements.get(0), times(1)).click();
    }


    @Test
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        MonthStringFunction function1 = mock(MonthStringFunction.class);
        MonthStringFunction function2 = mock(MonthStringFunction.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new MuiMonthPicker(element1, driver, config),
                new MuiMonthPicker(element1, driver, config));
        tester.addEqualityGroup(new MuiMonthPicker(element2, driver, config));
        tester.addEqualityGroup(new MuiMonthPicker(element1, driver, config, function1));
        tester.addEqualityGroup(new MuiMonthPicker(element2, driver, config, function1));
        tester.addEqualityGroup(new MuiMonthPicker(element1, driver, config, function2));
        tester.addEqualityGroup(new MuiMonthPicker(element2, driver, config, function2));

        tester.testEquals();
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element");
        MonthStringFunction function = mock(MonthStringFunction.class);
        when(function.toString()).thenReturn("month-string-function");
        testSubject = new MuiMonthPicker(element, driver, config, function);
        assertEquals("MuiMonthPicker{monthStringFunction=month-string-function, element=element}",
                testSubject.toString());
    }

    @Test
    void getMonthStringFunction() {
        assertSame(EnglishMonthStringFunction.getInstance(), testSubject.getMonthStringFunction());
    }

    @Test
    void getMonthStringFunction2() {
        MonthStringFunction function = mock(MonthStringFunction.class);
        testSubject = new MuiMonthPicker(element, driver, config, function);
        assertSame(function, testSubject.getMonthStringFunction());
    }
}
