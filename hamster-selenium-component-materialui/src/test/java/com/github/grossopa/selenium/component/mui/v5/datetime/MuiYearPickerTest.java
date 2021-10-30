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

package com.github.grossopa.selenium.component.mui.v5.datetime;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiYearPicker}
 *
 * @author Jack Yin
 * @since 1.8
 */
class MuiYearPickerTest {

    MuiYearPicker testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    List<WebElement> yearButtonElements = newArrayList();

    @BeforeEach
    void setUp() {
        for (int i = 1900; i < 2100; i++) {
            WebElement yearButtonElement = mock(WebElement.class);
            when(yearButtonElement.getText()).thenReturn(String.valueOf(i));
            yearButtonElements.add(yearButtonElement);
        }
        when(element.findElements(By.className("PrivatePickersYear-yearButton"))).thenReturn(yearButtonElements);
        when(element.findElement(argThat(a -> a.toString()
                .contains(".//button[contains(@class,'PrivatePickersYear-yearButton') and text()=")))).then(a -> {
            String byString = a.getArgument(0).toString();
            String year = byString.split("and text\\(\\)=\"")[1].substring(0, 4);
            return yearButtonElements.stream().filter(element -> element.getText().equals(year)).findFirst()
                    .orElseThrow();
        });

        testSubject = new MuiYearPicker(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("YearPicker", testSubject.getComponentName());
    }

    @Test
    void getYearButtons() {
        assertEquals(200, testSubject.getYearButtons().size());
    }

    @Test
    void select1() {
        testSubject.select(2000);
        verify(yearButtonElements.get(2000 - 1900), times(1)).click();
    }

    @Test
    void select2() {
        testSubject.select("2034");
        verify(yearButtonElements.get(2034 - 1900), times(1)).click();
    }
}
