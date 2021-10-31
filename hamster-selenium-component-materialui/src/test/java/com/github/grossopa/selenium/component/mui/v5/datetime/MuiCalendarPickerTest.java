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
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiCalendarPicker}
 *
 * @author Jack Yin
 * @since 1.8
 */
class MuiCalendarPickerTest {

    MuiCalendarPicker testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement monthLabelElement = mock(WebElement.class);
    WebElement yearLabelElement = mock(WebElement.class);
    WebElement switchButtonElement = mock(WebElement.class);
    WebElement previousMonthButtonElement = mock(WebElement.class);
    WebElement nextMonthButtonElement = mock(WebElement.class);

    WebElement calendarViewElement = mock(WebElement.class);
    WebElement yearPickerElement = mock(WebElement.class);
    List<WebElement> dayButtons = newArrayList();

    String currentYear = "2023";

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");

        when(element.findElement(By.xpath(
                "./div[1]/div[1]//div[contains(@class,'PrivatePickersFadeTransitionGroup-root')][1]"))).thenReturn(
                monthLabelElement);

        when(element.findElement(By.xpath(
                "./div[1]/div[1]//div[contains(@class,'PrivatePickersFadeTransitionGroup-root')][2]"))).thenReturn(
                yearLabelElement);

        when(element.findElement(By.xpath("./div[1]/div[1]//button"))).thenReturn(switchButtonElement);
        when(element.findElement(By.xpath("./div[1]/div[2]//button[1]"))).thenReturn(previousMonthButtonElement);
        when(element.findElement(By.xpath("./div[1]/div[2]//button[2]"))).thenReturn(nextMonthButtonElement);
        when(element.findElement(By.className("MuiCalendarPicker-viewTransitionContainer"))).thenReturn(
                calendarViewElement);
        when(element.findElement(By.className("MuiYearPicker-root"))).thenReturn(yearPickerElement);

        doAnswer(a -> currentYear).when(yearLabelElement).getText();

        MuiYearPickerTest yearPickerTest = new MuiYearPickerTest();
        yearPickerTest.element = yearPickerElement;
        yearPickerTest.setUp();
        yearPickerTest.yearButtonElements.forEach(btn -> doAnswer(a -> {
            currentYear = btn.getText();
            return null;
        }).when(btn).click());

        for (int i = 0; i < 30; i++) {
            WebElement dayButtonElement = mock(WebElement.class);
            when(dayButtonElement.getText()).thenReturn(String.valueOf(i + 1));
            dayButtons.add(dayButtonElement);
        }

        when(calendarViewElement.findElements(
                By.xpath(".//button[contains(@class,'MuiPickersDay-root') and count(text())>0]"))).thenReturn(
                dayButtons);
        when(calendarViewElement.findElements(
                By.xpath(".//button[contains(@class,'MuiPickersDay-root') and contains(@class,'Mui-selected')]"))).then(
                a -> dayButtons.stream().filter(WebElement::isSelected).collect(toList()));
        when(calendarViewElement.findElement(
                By.xpath(".//button[contains(@class,'MuiPickersDay-root') and contains(@class,'Mui-selected')]"))).then(
                a -> dayButtons.stream().filter(WebElement::isSelected).findFirst().orElseThrow());
        when(calendarViewElement.findElement(
                argThat(a -> a.toString().contains("button[contains(@class,'MuiPickersDay-root') and text()=")))).then(
                a -> {
                    String day = a.getArgument(0).toString().split("and text\\(\\)=\"")[1].split("\"")[0];
                    return dayButtons.stream().filter(btn -> btn.getText().equals(day)).findFirst().orElseThrow();
                });

        doAnswer(a -> {
            String currentMonth = monthLabelElement.getText().toUpperCase(Locale.ROOT);
            String newMonth = capitalize(
                    Month.of(Month.valueOf(currentMonth).getValue() - 1).toString().toLowerCase(Locale.ROOT));
            when(monthLabelElement.getText()).thenReturn(newMonth);
            return null;
        }).when(previousMonthButtonElement).click();

        doAnswer(a -> {
            String currentMonth = monthLabelElement.getText().toUpperCase(Locale.ROOT);
            String newMonth = capitalize(
                    Month.of(Month.valueOf(currentMonth).getValue() + 1).toString().toLowerCase(Locale.ROOT));
            when(monthLabelElement.getText()).thenReturn(newMonth);
            return null;
        }).when(nextMonthButtonElement).click();

        testSubject = new MuiCalendarPicker(element, driver, config);
    }

    private void mockYearView(boolean isDisplayed) {
        when(element.findElements(By.className("MuiYearPicker-root"))).thenReturn(
                isDisplayed ? List.of(yearLabelElement) : newArrayList());
    }

    @Test
    void version() {
        assertArrayEquals(new Object[]{V5}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        assertEquals("CalendarPicker", testSubject.getComponentName());
    }

    @Test
    void getMonthLabel() {
        assertEquals(monthLabelElement, testSubject.getMonthLabel().getWrappedElement());
    }

    @Test
    void getYearLabel() {
        assertEquals(yearLabelElement, testSubject.getYearLabel().getWrappedElement());
    }

    @Test
    void getSwitchButton() {
        assertEquals(switchButtonElement, testSubject.getSwitchButton().getWrappedElement());
    }

    @Test
    void getPreviousMonthButton() {
        assertEquals(previousMonthButtonElement, testSubject.getPreviousMonthButton().getWrappedElement());
    }

    @Test
    void getNextMonthButton() {
        assertEquals(nextMonthButtonElement, testSubject.getNextMonthButton().getWrappedElement());
    }

    @Test
    void getCalendarView() {
        assertEquals(calendarViewElement, testSubject.getCalendarView().getWrappedElement());
    }

    @Test
    void getYearPicker() {
        assertEquals(yearPickerElement, testSubject.getYearPicker().getWrappedElement());
    }

    @Test
    void setDateSameYearMonth() {
        when(monthLabelElement.getText()).thenReturn("October");

        testSubject.setDate(LocalDate.of(2023, Month.OCTOBER, 23));

        assertEquals("2023", yearLabelElement.getText());
        assertEquals("October", monthLabelElement.getText());
        verify(dayButtons.get(22), times(1)).click();
    }

    @Test
    void setDateDifferentMonthPrevious() {
        when(monthLabelElement.getText()).thenReturn("October");

        testSubject.setDate(LocalDate.of(2023, Month.FEBRUARY, 23));

        assertEquals("2023", yearLabelElement.getText());
        assertEquals("February", monthLabelElement.getText());
        verify(dayButtons.get(22), times(1)).click();
    }

    @Test
    void setDateDifferentMonthNext() {
        when(monthLabelElement.getText()).thenReturn("October");

        testSubject.setDate(LocalDate.of(2023, Month.DECEMBER, 23));

        assertEquals("2023", yearLabelElement.getText());
        assertEquals("December", monthLabelElement.getText());
        verify(dayButtons.get(22), times(1)).click();
    }

    @Test
    void setDateDifferentYear() {
        when(monthLabelElement.getText()).thenReturn("October");

        testSubject.setDate(LocalDate.of(2046, Month.OCTOBER, 1));

        assertEquals("2046", yearLabelElement.getText());
        assertEquals("October", monthLabelElement.getText());
        verify(dayButtons.get(0), times(1)).click();
    }

    @Test
    void changeView1() {
        mockYearView(true);
        testSubject.changeView(MuiCalendarPicker.ViewType.CALENDAR);
        verify(switchButtonElement, times(1)).click();
        verify(driver, times(1)).threadSleep(0L);
    }

    @Test
    void changeView2() {
        mockYearView(true);
        testSubject.changeView(MuiCalendarPicker.ViewType.YEAR);
        verify(switchButtonElement, never()).click();
        verify(driver, never()).threadSleep(0L);
    }

    @Test
    void changeView3() {
        mockYearView(false);
        testSubject.changeView(MuiCalendarPicker.ViewType.YEAR);
        verify(switchButtonElement, times(1)).click();
        verify(driver, times(1)).threadSleep(0L);
    }

    @Test
    void changeView4() {
        mockYearView(false);
        testSubject.changeView(MuiCalendarPicker.ViewType.CALENDAR);
        verify(switchButtonElement, never()).click();
        verify(driver, never()).threadSleep(0L);
    }

    @Test
    void changeView11() {
        mockYearView(true);
        testSubject.changeView(MuiCalendarPicker.ViewType.CALENDAR, 1234L);
        verify(switchButtonElement, times(1)).click();
        verify(driver, times(1)).threadSleep(1234L);
    }

    @Test
    void changeView12() {
        mockYearView(true);
        testSubject.changeView(MuiCalendarPicker.ViewType.YEAR, 1234L);
        verify(switchButtonElement, never()).click();
        verify(driver, never()).threadSleep(1234L);
    }

    @Test
    void changeView13() {
        mockYearView(false);
        testSubject.changeView(MuiCalendarPicker.ViewType.YEAR, 1234L);
        verify(switchButtonElement, times(1)).click();
        verify(driver, times(1)).threadSleep(1234L);
    }

    @Test
    void changeView14() {
        mockYearView(false);
        testSubject.changeView(MuiCalendarPicker.ViewType.CALENDAR, 1234L);
        verify(switchButtonElement, never()).click();
        verify(driver, never()).threadSleep(1234L);
    }

    @Test
    void getCurrentView1() {
        mockYearView(true);
        assertEquals(MuiCalendarPicker.ViewType.YEAR, testSubject.getCurrentView());
    }

    @Test
    void getCurrentView2() {
        mockYearView(false);
        assertEquals(MuiCalendarPicker.ViewType.CALENDAR, testSubject.getCurrentView());
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        Function strToMonthFunction1 = mock(Function.class);
        Function strToMonthFunction2 = mock(Function.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new MuiCalendarPicker(element1, driver, config),
                new MuiCalendarPicker(element1, driver, config));
        tester.addEqualityGroup(new MuiCalendarPicker(element2, driver, config));
        tester.addEqualityGroup(new MuiCalendarPicker(element1, driver, config, strToMonthFunction1),
                new MuiCalendarPicker(element1, driver, config, strToMonthFunction1));
        tester.addEqualityGroup(new MuiCalendarPicker(element1, driver, config, strToMonthFunction2));

        tester.testEquals();
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element");
        assertEquals("MuiCalendarPicker{stringToMonthFunction=EnglishStringToMonthFunction{MONTHS=[Jan, "
                + "Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec]}, element=element}", testSubject.toString());
    }
}
