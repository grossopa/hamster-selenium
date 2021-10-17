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

package com.github.grossopa.selenium.component.mui.v4.pickers;

import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The transition container for days selection, a transition container should contain the current month dates from 1 to
 * 30 for example.
 *
 * @author Jack Yin
 * @since 1.2
 */
public class MuiPickersCalendarTransitionContainer extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "PickersCalendar-transitionContainer";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    protected MuiPickersCalendarTransitionContainer(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Finds the current days button list
     *
     * @return the current days button list
     */
    public List<MuiPickersDay> getDayList() {
        return this.findComponentsAs(By.className(config.getCssPrefix() + MuiPickersDay.NAME),
                c -> new MuiPickersDay(c, driver, config));
    }

    /**
     * Finds the current selected day
     *
     * @return the selected day component or null if nothing selected
     */
    @Nullable
    public MuiPickersDay getSelectedDay() {
        return getDayList().stream().filter(MuiPickersDay::isSelected).findAny().orElse(null);
    }

    /**
     * Selects a day by locating by date string and clicking
     *
     * @param day the day in string to select
     */
    public void select(String day) {
        // Not perfect solution
        WebComponent component = this.findComponent(By2.textExact(day)).findComponent(By2.parent())
                .findComponent(By2.parent());
        component.click();
    }

    @Override
    public String toString() {
        return "MuiPickersCalendarTransitionContainer{" + "element=" + element + '}';
    }
}
