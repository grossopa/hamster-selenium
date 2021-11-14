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

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.v5.datetime.MuiCalendarPicker;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.core.util.SeleniumUtils.enrichQuote;

/**
 * The inner calendar view of {@link MuiCalendarPicker} for selecting a date.
 *
 * @author Jack Yin
 * @since 1.8
 */
@SuppressWarnings("java:S2160")
public class MuiCalendarView extends AbstractMuiComponent {

    private final Function<WebComponent, MuiPickersDay> pickersDayFunction = component -> new MuiPickersDay(component,
            driver, config);

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiCalendarView(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        // not an official Mui component, it is just a div.
        return "";
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V5);
    }

    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Gets all {@link MuiPickersDay} buttons within calendar selection.
     *
     * @return all {@link MuiPickersDay} buttons within calendar selection.
     */
    public List<MuiPickersDay> getDayButtons() {
        By xpath = By.xpath(String.format(".//button[contains(@class,'%sPickersDay-root') and count(text())>0]",
                config.getCssPrefix()));
        return this.findComponentsAs(xpath, pickersDayFunction);
    }

    /**
     * Get the first selected day button
     *
     * @return the first selected button
     */
    public MuiPickersDay getFirstSelectedDay() {
        By xpath = By.xpath(
                String.format(".//button[contains(@class,'%sPickersDay-root') and contains(@class,'%s-selected')]",
                        config.getCssPrefix(), config.getCssPrefix()));

        return this.findComponentAs(xpath, pickersDayFunction);
    }

    /**
     * Gets all selected day buttons
     *
     * @return all selected day buttons
     */
    public List<MuiPickersDay> getSelectedDays() {
        By xpath = By.xpath(
                String.format(".//button[contains(@class,'%sPickersDay-root') and contains(@class,'%s-selected')]",
                        config.getCssPrefix(), config.getCssPrefix()));
        return this.findComponentsAs(xpath, pickersDayFunction);
    }

    /**
     * Selects by date, index starts from 1.
     *
     * @param day the day to be selected.
     */
    public void select(int day) {
        select(String.valueOf(day));
    }

    /**
     * Selects by date string, date string starts from 1.
     *
     * @param day the day string to be selected.
     */
    public void select(String day) {
        this.findComponent(By.xpath(
                String.format(".//button[contains(@class,'%sPickersDay-root') and text()=%s]", config.getCssPrefix(),
                        enrichQuote(day)))).click();
    }

}
