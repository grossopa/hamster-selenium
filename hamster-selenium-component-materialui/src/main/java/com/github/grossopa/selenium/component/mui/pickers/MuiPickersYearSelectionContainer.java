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

package com.github.grossopa.selenium.component.mui.pickers;

import com.github.grossopa.selenium.component.mui.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The container for year only selection list
 *
 * @author Jack Yin
 * @since 1.2
 */
public class MuiPickersYearSelectionContainer extends AbstractMuiComponent {

    public static final String NAME = "PickersYearSelection-container";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    protected MuiPickersYearSelectionContainer(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    /**
     * Gets the full year list, note it could be large as seems the Picker by default populates all possible values from
     * year 1900 to 2100.
     *
     * @return the full year list component
     */
    public List<MuiPickersYear> getYearList() {
        return this.findComponentsAs(By.className(config.getRootCss(MuiPickersYear.NAME)),
                component -> new MuiPickersYear(component, driver, config));
    }

    /**
     * Gets the selected year.
     *
     * @return the selected year, null if nothing selected.
     */
    @Nullable
    public MuiPickersYear getSelectedYear() {
        return getYearList().stream().filter(MuiPickersYear::isSelected).findAny().orElse(null);
    }

    /**
     * Selects by year text
     *
     * @param year the year text to select
     */
    public void select(String year) {
        WebComponent component = this.findComponent(By2.textExact(year));
        driver.scrollTo(component);
        driver.moveTo(component);
        component.click();
    }
}
