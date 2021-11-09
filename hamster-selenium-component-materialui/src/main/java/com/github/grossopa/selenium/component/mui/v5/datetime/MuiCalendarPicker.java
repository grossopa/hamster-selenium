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

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.v5.datetime.func.EnglishMonthStringFunction;
import com.github.grossopa.selenium.component.mui.v5.datetime.func.MonthStringFunction;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiCalendarView;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiMonthPicker;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiYearPicker;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.v5.datetime.MuiCalendarPicker.ViewType.*;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.join;
import static org.openqa.selenium.By.xpath;

/**
 * The sub-component for user to be able to select date, month and year.
 *
 * @author Jack Yin
 * @since 1.8
 */
@SuppressWarnings("java:S2160")
public class MuiCalendarPicker extends AbstractMuiComponent {

    /**
     * The view types when calendar view is enabled
     */
    private static final List<MuiCalendarPicker.ViewType> CALENDAR_VIEW_TYPES = List.of(YEAR, DAY);

    /**
     * the component name
     */
    public static final String COMPONENT_NAME = "CalendarPicker";

    private final List<MuiCalendarPicker.ViewType> views;
    private final MonthStringFunction monthStringFunction;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param views the view type sequence, defined by e.g. views={['year', 'day']}
     */
    public MuiCalendarPicker(WebElement element, ComponentWebDriver driver, MuiConfig config,
            List<MuiCalendarPicker.ViewType> views) {
        this(element, driver, config, views, EnglishMonthStringFunction.getInstance());
    }

    /**
     * Constructs an instance with the delegated element, root driver and the string to month function to convert the
     * displayed month string to actual {@link Month}.
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param views the view type sequence, defined by e.g. views={['year', 'day']}
     * @param monthStringFunction the monthStringFunction to convert
     */
    public MuiCalendarPicker(WebElement element, ComponentWebDriver driver, MuiConfig config,
            List<MuiCalendarPicker.ViewType> views, MonthStringFunction monthStringFunction) {
        super(element, driver, config);
        this.views = List.copyOf(views.stream().distinct().collect(toList()));
        this.monthStringFunction = monthStringFunction;
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V5);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Gets the month label, e.g. October in English.
     *
     * @return the month label container
     */
    public WebComponent getMonthLabel() {
        return this.findComponent(
                xpath("./div[1]/div[1]//div[contains(@class,'PrivatePickersFadeTransitionGroup-root')][1]"));
    }

    /**
     * Gets the year label, e.g. 2021
     *
     * @return the year label container
     */
    public WebComponent getYearLabel() {
        return this.findComponent(
                xpath("./div[1]/div[1]//div[contains(@class,'PrivatePickersFadeTransitionGroup-root')][2]"));
    }

    /**
     * Gets the switch button that can do from Calendar to Year switching.
     *
     * @return the switch button that can do from Calendar to Year switching.
     */
    public MuiButton getSwitchButton() {
        WebComponent component = this.findComponent(xpath("./div[1]/div[1]//button"));
        return new MuiButton(component, driver, config);
    }

    /**
     * Gets the previous button "&lt;".
     *
     * @return the previous button
     */
    public MuiButton getPreviousMonthButton() {
        WebComponent component = this.findComponent(xpath("./div[1]/div[2]//button[1]"));
        return new MuiButton(component, driver, config);
    }

    /**
     * Gets the next button "&gt;"
     *
     * @return the next button
     */
    public MuiButton getNextMonthButton() {
        WebComponent component = this.findComponent(xpath("./div[1]/div[2]//button[2]"));
        return new MuiButton(component, driver, config);
    }

    /**
     * Gets the main view as calendar view. It will not work when year selection is expanded.
     *
     * @return the main view as calendar view.
     */
    public MuiCalendarView getCalendarView() {
        WebComponent component = this.findComponent(
                By.className(config.getCssPrefix() + "CalendarPicker-viewTransitionContainer"));
        return new MuiCalendarView(component, driver, config);
    }

    /**
     * Gets the main view as year picker, It will not work when calendar selection is expanded.
     *
     * @return the main view as year picker.
     */
    public MuiYearPicker getYearPicker() {
        WebComponent component = this.findComponent(By.className(config.getCssPrefix() + "YearPicker-root"));
        return new MuiYearPicker(component, driver, config);
    }

    /**
     * Gets the main view as the month picker, It will not work if the month view is not shown.
     *
     * @return the main view as month picker.
     */
    public MuiMonthPicker getMonthPicker() {
        WebComponent component = this.findComponent(By.className(config.getCssPrefix() + "MonthPicker-root"));
        return new MuiMonthPicker(component, driver, config, monthStringFunction);
    }

    /**
     * Selects the date.
     *
     * @param date the date to select
     */
    public void setDate(LocalDate date) {
        setDate(date, 0);
    }

    /**
     * Selects the date.
     *
     * @param date the date to select
     * @param delayInMillis the delaying in milliseconds for switching between year and calendar view, suggested value
     * is 500.
     */
    public void setDate(LocalDate date, long delayInMillis) {
        if (CALENDAR_VIEW_TYPES.equals(views)) {
            setDateInCalendarView(date, delayInMillis);
        } else {
            views.forEach(view -> {
                driver.threadSleep(delayInMillis);
                if (view == YEAR) {
                    this.getYearPicker().select(date.getYear());
                } else if (view == MONTH) {
                    this.getMonthPicker().select(date.getMonth());
                } else {
                    this.getCalendarView().select(date.getDayOfMonth());
                }
            });
        }
        driver.threadSleep(delayInMillis);
    }

    private void setDateInCalendarView(LocalDate date, long delayInMillis) {
        int year = date.getYear();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();

        if (year != parseInt(this.getYearLabel().getText())) {
            changeView(YEAR, delayInMillis);
            this.getYearPicker().select(year);
            changeView(DAY, delayInMillis);
        }

        Month current = monthStringFunction.stringToMonth(this.getMonthLabel().getText());
        while (current.getValue() > month.getValue()) {
            this.getPreviousMonthButton().click();
            driver.threadSleep(delayInMillis);
            current = monthStringFunction.stringToMonth(this.getMonthLabel().getText());
        }

        while (current.getValue() < month.getValue()) {
            this.getNextMonthButton().click();
            driver.threadSleep(delayInMillis);
            current = monthStringFunction.stringToMonth(this.getMonthLabel().getText());
        }

        this.getCalendarView().select(day);
    }

    /**
     * Switches the view based on the view type. Does nothing if it's already displayed.
     *
     * @param viewType the view type to switch to
     */
    public void changeView(ViewType viewType) {
        changeView(viewType, 0);
    }

    /**
     * Switches the view based on the view type. Does nothing if it's already displayed.
     *
     * @param viewType the view type to switch to
     * @param delayInMillis the delays for animation, suggested value is 500
     */
    public void changeView(ViewType viewType, long delayInMillis) {
        if (viewType != getCurrentView()) {
            this.getSwitchButton().click();
            driver.threadSleep(delayInMillis);
        }
    }

    /**
     * Tries to detect current view type by inner container.
     *
     * @return the current view type
     */
    public ViewType getCurrentView() {
        List<WebComponent> yearPicker = this.findComponents(By.className(config.getCssPrefix() + "YearPicker-root"));
        if (yearPicker.isEmpty()) {
            return DAY;
        } else {
            return YEAR;
        }
    }

    /**
     * Gets the specified views of the picker.
     *
     * @return the specified views of the picker.
     */
    public List<ViewType> getViews() {
        return views;
    }

    /**
     * Gets the month string function.
     *
     * @return the month string function.
     */
    public MonthStringFunction getMonthStringFunction() {
        return monthStringFunction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiCalendarPicker)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MuiCalendarPicker that = (MuiCalendarPicker) o;
        return views.equals(that.views) && monthStringFunction.equals(that.monthStringFunction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), views, monthStringFunction);
    }

    @Override
    public String toString() {
        return "MuiCalendarPicker{" + "views=[" + join(views, ",") + "], monthStringFunction=" + monthStringFunction
                + ", element=" + element + '}';
    }

    /**
     * The view type of the calendar picker, either CalendarView {@link MuiCalendarPicker#getCalendarView()} or
     * YearPicker {@link MuiCalendarPicker#getYearPicker()}.
     *
     * @author Jack Yin
     * @since 1.8
     */
    public enum ViewType {
        /**
         * {@link MuiCalendarPicker#getCalendarView()} is available when {@link #getCurrentView()} is this.
         */
        DAY,
        /**
         * {@link MuiCalendarPicker#getYearPicker()} is available when {@link #getCurrentView()} is this.
         */
        YEAR,
        /**
         * {@link MuiCalendarPicker#getMonthPicker()} is available when {@link #getCurrentView()} is this.
         */
        MONTH
    }
}
