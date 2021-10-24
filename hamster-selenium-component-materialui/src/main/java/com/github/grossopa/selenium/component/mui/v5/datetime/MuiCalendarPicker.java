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
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.v5.datetime.sub.MuiCalendarView;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

/**
 * The sub-component for user to be able to select date, month and year.
 *
 * @author Jack Yin
 * @since 1.8
 */
public class MuiCalendarPicker extends AbstractMuiComponent {

    /**
     * the component name
     */
    public static final String COMPONENT_NAME = "CalendarPicker";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiCalendarPicker(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
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
                By.xpath("./div[1]/div[1]//div[contains(@class,'PrivatePickersFadeTransitionGroup-root')][1]"));
    }

    /**
     * Gets the year label, e.g. 2021
     *
     * @return the year label container
     */
    public WebComponent getYearLabel() {
        return this.findComponent(
                By.xpath("./div[1]/div[1]//div[contains(@class,'PrivatePickersFadeTransitionGroup-root')][2]"));
    }

    /**
     * Gets the switch button that can do from Calendar to Year switching.
     *
     * @return the switch button that can do from Calendar to Year switching.
     */
    public MuiButton getSwitchButton() {
        WebComponent component = this.findComponent(By.xpath("./div[1]/div[1]//button"));
        return new MuiButton(component, driver, config);
    }

    /**
     * Gets the previous button "&lt;".
     *
     * @return the previous button
     */
    public MuiButton getPreviousMonthButton() {
        WebComponent component = this.findComponent(By.xpath("./div[1]/div[2]//button[1]"));
        return new MuiButton(component, driver, config);
    }

    /**
     * Gets the next button "&gt;"
     *
     * @return the next button
     */
    public MuiButton getNextMonthButton() {
        WebComponent component = this.findComponent(By.xpath("./div[1]/div[2]//button[2]"));
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
     * @return the main view as year picker,
     */
    public MuiYearPicker getYearPicker() {
        WebComponent component = this.findComponent(
                        By.className(config.getCssPrefix() + "CalendarPicker-viewTransitionContainer"))
                .findComponent(By.className(config.getCssPrefix() + "YearPicker-root"));
        return new MuiYearPicker(component, driver, config);
    }

    /**
     * Selects the date.
     *
     * @param date the date to select
     */
    public void setDate(LocalDate date) {
        int year = date.getYear();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();

        if (year != parseInt(this.getYearLabel().getText())) {
            this.getSwitchButton().click();
            // for animation
            driver.threadSleep(500L);
            this.getYearPicker().select(year);
        }


    }
}
