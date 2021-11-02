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
import com.github.grossopa.selenium.component.mui.v5.datetime.func.EnglishMonthStringFunction;
import com.github.grossopa.selenium.component.mui.v5.datetime.func.MonthStringFunction;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.WebElement;

import java.time.Month;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.core.consts.HtmlConstants.BUTTON;
import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static java.util.Objects.requireNonNull;

/**
 * Selects the Month.
 *
 * @author Jack Yin
 * @since 1.8
 */
public class MuiMonthPicker extends AbstractMuiComponent {

    private final MonthStringFunction monthStringFunction;

    /**
     * the component name
     */
    public static final String COMPONENT_NAME = "MonthPicker";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiMonthPicker(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
        this.monthStringFunction = EnglishMonthStringFunction.getInstance();
    }

    /**
     * Constructs an instance with the delegated element, root driver and month converter function.
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param monthToStringFunction the function to convert Month to string
     */
    public MuiMonthPicker(WebElement element, ComponentWebDriver driver, MuiConfig config,
            MonthStringFunction monthToStringFunction) {
        super(element, driver, config);
        requireNonNull(monthToStringFunction);
        this.monthStringFunction = monthToStringFunction;
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V5);
    }

    /**
     * Gets the current component name
     *
     * @return the current component name
     */
    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Finds all month buttons, the count is always 12
     *
     * @return all found month buttons
     */
    public List<WebComponent> getMonthButtons() {
        return this.findComponents(
                xpathBuilder().anywhereRelative(BUTTON).attr(CLASS).contains("PrivatePickersMonth-root").build());
    }

    /**
     * Finds the first selected month button.
     *
     * @return the first selected month button.
     */
    public WebComponent getFirstSelectedMonthButton() {
        return this.findComponent(
                xpathBuilder().anywhereRelative(BUTTON).attr(CLASS).contains(config.getCssPrefix() + "-selected")
                        .build());
    }

    /**
     * Selects the month
     *
     * @param month the month to select
     */
    public void select(Month month) {
        String monthString = monthStringFunction.monthToString(month);
        this.findComponent(xpathBuilder().anywhereRelative(BUTTON).text().contains(monthString).build()).click();
    }

    /**
     * Gets the month to String function
     *
     * @return the month to String function
     */
    public MonthStringFunction getMonthStringFunction() {
        return monthStringFunction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiMonthPicker)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MuiMonthPicker that = (MuiMonthPicker) o;
        return Objects.equals(monthStringFunction, that.monthStringFunction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), monthStringFunction);
    }

    @Override
    public String toString() {
        return "MuiMonthPicker{" + "monthStringFunction=" + monthStringFunction + ", element=" + element + '}';
    }
}
