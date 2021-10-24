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
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.v5.datetime.MuiCalendarPicker;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.selenium.core.util.SeleniumUtils.enrichQuote;

/**
 * The inner calendar view of {@link MuiCalendarPicker} for selecting a date.
 *
 * @author Jack Yin
 * @since 1.8
 */
public class MuiCalendarView extends AbstractMuiComponent {

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
    public boolean validate() {
        return true;
    }

    /**
     * Gets all {@link MuiPickersDay} buttons within calendar selection.
     *
     * @return all {@link MuiPickersDay} buttons within calendar selection.
     */
    public List<MuiPickersDay> getDayButtons() {
        By xpath = By.xpath(
                String.format(".//button[contains(@class,'%sPickersDay') and count(text())>0]", config.getCssPrefix()));
        return this.findComponentsAs(xpath, component -> new MuiPickersDay(component, driver, config));
    }

    /**
     * Selects by date, index starts from 1.
     *
     * @param date the date to be selected.
     */
    public void select(int date) {
        select(String.valueOf(date));
    }

    /**
     * Selects by date string, date string starts from 1.
     *
     * @param date the date string to be selected.
     */
    public void select(String date) {
        this.findComponent(By.xpath(
                String.format(".//button[contains(@class,'%sPickersDay') and text()=%s]", config.getCssPrefix(),
                        enrichQuote(date)))).click();
    }

}
