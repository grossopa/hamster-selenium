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
import com.github.grossopa.selenium.component.mui.v5.datetime.func.EnglishMonthStringFunction;
import com.github.grossopa.selenium.component.mui.v5.datetime.func.MonthStringFunction;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiYearPickerTest;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.v5.datetime.MuiCalendarPicker.ViewType.*;
import static com.github.grossopa.selenium.core.consts.HtmlConstants.BUTTON;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.junit.jupiter.api.Assertions.*;
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
    WebElement monthPickerElement = mock(WebElement.class);
    List<WebElement> dayButtons = newArrayList();
    List<MuiCalendarPicker.ViewType> views = newArrayList();
    List<WebElement> buttonElements;

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
        when(element.findElement(By.className("MuiMonthPicker-root"))).thenReturn(monthPickerElement);

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

        buttonElements = Arrays.stream(Month.values())
                .map(m -> StringUtils.capitalize(m.toString().toLowerCase(Locale.ROOT))).map(m -> {
                    WebElement monthElement = mock(WebElement.class);
                    when(monthElement.getText()).thenReturn(m);
                    return element;
                }).collect(Collectors.toList());

        when(element.findElements(By.xpath(".//button[contains(@class,\"PrivatePickersMonth-root\")]"))).thenReturn(
                buttonElements);

        views = newArrayList(YEAR, DAY);
        testSubject = new MuiCalendarPicker(element, driver, config, views);
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
    void getMonthPicker() {
        assertEquals(monthPickerElement, testSubject.getMonthPicker().getWrappedElement());
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
        testSubject.changeView(DAY);
        verify(switchButtonElement, times(1)).click();
        verify(driver, times(1)).threadSleep(0L);
    }

    @Test
    void changeView2() {
        mockYearView(true);
        testSubject.changeView(YEAR);
        verify(switchButtonElement, never()).click();
        verify(driver, never()).threadSleep(0L);
    }

    @Test
    void changeView3() {
        mockYearView(false);
        testSubject.changeView(YEAR);
        verify(switchButtonElement, times(1)).click();
        verify(driver, times(1)).threadSleep(0L);
    }

    @Test
    void changeView4() {
        mockYearView(false);
        testSubject.changeView(DAY);
        verify(switchButtonElement, never()).click();
        verify(driver, never()).threadSleep(0L);
    }

    @Test
    void changeView11() {
        mockYearView(true);
        testSubject.changeView(DAY, 1234L);
        verify(switchButtonElement, times(1)).click();
        verify(driver, times(1)).threadSleep(1234L);
    }

    @Test
    void changeView12() {
        mockYearView(true);
        testSubject.changeView(YEAR, 1234L);
        verify(switchButtonElement, never()).click();
        verify(driver, never()).threadSleep(1234L);
    }

    @Test
    void changeView13() {
        mockYearView(false);
        testSubject.changeView(YEAR, 1234L);
        verify(switchButtonElement, times(1)).click();
        verify(driver, times(1)).threadSleep(1234L);
    }

    @Test
    void changeView14() {
        mockYearView(false);
        testSubject.changeView(DAY, 1234L);
        verify(switchButtonElement, never()).click();
        verify(driver, never()).threadSleep(1234L);
    }

    @Test
    void getCurrentView1() {
        mockYearView(true);
        assertEquals(YEAR, testSubject.getCurrentView());
    }

    @Test
    void getCurrentView2() {
        mockYearView(false);
        assertEquals(DAY, testSubject.getCurrentView());
    }


    @Test
    void setDate() {
        when(monthLabelElement.getText()).thenReturn("October");

        testSubject.setDate(LocalDate.of(2012, Month.DECEMBER, 1));
        verify(nextMonthButtonElement, times(2)).click();
        verify(previousMonthButtonElement, never()).click();
        assertEquals("2012", currentYear);
        verify(dayButtons.get(0), times(1)).click();
    }

    @Test
    void setDateWithDelays() {
        when(monthLabelElement.getText()).thenReturn("October");

        testSubject.setDate(LocalDate.of(2012, Month.DECEMBER, 1), 800L);
        verify(nextMonthButtonElement, times(2)).click();
        verify(previousMonthButtonElement, never()).click();
        assertEquals("2012", currentYear);
        verify(dayButtons.get(0), times(1)).click();

        verify(driver, times(4)).threadSleep(800L);
    }

    @Test
    void setDateYear() {
        views = newArrayList(YEAR);
        testSubject = new MuiCalendarPicker(element, driver, config, views);
        when(monthLabelElement.getText()).thenReturn("October");

        testSubject.setDate(LocalDate.of(2012, Month.DECEMBER, 1), 800L);
        verify(nextMonthButtonElement, never()).click();
        verify(previousMonthButtonElement, never()).click();

        assertEquals("2012", currentYear);
        verify(monthPickerElement, never()).findElements(any());
        verify(dayButtons.get(0), never()).click();

        // one for year, one for overall delay
        verify(driver, times(2)).threadSleep(800L);
    }

    @Test
    void setDateMonth() {
        views = newArrayList(MONTH);
        testSubject = new MuiCalendarPicker(element, driver, config, views);
        when(monthLabelElement.getText()).thenReturn("October");
        WebElement monthButtonElement = mock(WebElement.class);
        when(monthPickerElement.findElement(
                xpathBuilder().anywhereRelative(BUTTON).text().contains("Dec").build())).thenReturn(monthButtonElement);

        testSubject.setDate(LocalDate.of(2012, Month.DECEMBER, 1), 800L);
        verify(nextMonthButtonElement, never()).click();
        verify(previousMonthButtonElement, never()).click();

        assertEquals("2023", currentYear);
        verify(monthButtonElement, times(1)).click();
        verify(dayButtons.get(0), never()).click();

        // one for month, one for overall delay
        verify(driver, times(2)).threadSleep(800L);
    }

    @Test
    void setDateDay() {
        views = newArrayList(DAY);
        testSubject = new MuiCalendarPicker(element, driver, config, views);
        when(monthLabelElement.getText()).thenReturn("October");
        WebElement monthButtonElement = mock(WebElement.class);
        when(monthPickerElement.findElement(
                xpathBuilder().anywhereRelative(BUTTON).text().contains("Dec").build())).thenReturn(monthButtonElement);

        testSubject.setDate(LocalDate.of(2012, Month.DECEMBER, 1), 800L);

        verify(monthButtonElement, never()).click();
        verify(dayButtons.get(0), times(1)).click();

        // one for day, one for overall delay
        verify(driver, times(2)).threadSleep(800L);
    }

    @Test
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        MonthStringFunction strToMonthFunction1 = mock(MonthStringFunction.class);
        MonthStringFunction strToMonthFunction2 = mock(MonthStringFunction.class);

        List<MuiCalendarPicker.ViewType> views1 = newArrayList(YEAR);
        List<MuiCalendarPicker.ViewType> views2 = newArrayList(MONTH);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(new MuiCalendarPicker(element1, driver, config, views1),
                new MuiCalendarPicker(element1, driver, config, views1));
        tester.addEqualityGroup(new MuiCalendarPicker(element1, driver, config, views2));

        tester.addEqualityGroup(new MuiCalendarPicker(element2, driver, config, views1));
        tester.addEqualityGroup(new MuiCalendarPicker(element1, driver, config, views1, strToMonthFunction1),
                new MuiCalendarPicker(element1, driver, config, views1, strToMonthFunction1));
        tester.addEqualityGroup(new MuiCalendarPicker(element1, driver, config, views1, strToMonthFunction2));

        tester.addEqualityGroup(new MuiCalendarPicker(element2, driver, config, views2));
        tester.addEqualityGroup(new MuiCalendarPicker(element1, driver, config, views2, strToMonthFunction1),
                new MuiCalendarPicker(element1, driver, config, views2, strToMonthFunction1));
        tester.addEqualityGroup(new MuiCalendarPicker(element1, driver, config, views2, strToMonthFunction2));

        tester.testEquals();
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element");
        assertEquals(
                "MuiCalendarPicker{views=[YEAR,DAY], monthStringFunction=EnglishStringToMonthFunction{MONTHS=[Jan, "
                        + "Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec]}, element=element}",
                testSubject.toString());
    }

    @Test
    void getViews() {
        assertArrayEquals(views.toArray(), testSubject.getViews().toArray());
        assertNotSame(views, testSubject.getViews());
    }

    @Test
    void getMonthStringFunction() {
        testSubject = new MuiCalendarPicker(element, driver, config, views);
        assertSame(EnglishMonthStringFunction.getInstance(), testSubject.getMonthStringFunction());
    }

    @Test
    void getMonthStringFunctionDefault() {
        MonthStringFunction strToMonthFunction = mock(MonthStringFunction.class);
        testSubject = new MuiCalendarPicker(element, driver, config, views, strToMonthFunction);
        assertEquals(strToMonthFunction, testSubject.getMonthStringFunction());
    }
}
