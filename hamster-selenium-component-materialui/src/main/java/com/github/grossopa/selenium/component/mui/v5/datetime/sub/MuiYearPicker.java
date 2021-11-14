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
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.core.util.SeleniumUtils.enrichQuote;

/**
 * Selects the year.
 *
 * @author Jack Yin
 * @since 1.8
 */
public class MuiYearPicker extends AbstractMuiComponent {

    /**
     * the component name
     */
    public static final String COMPONENT_NAME = "YearPicker";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiYearPicker(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
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
     * Gets all year buttons.
     *
     * @return all year buttons.
     */
    public List<WebComponent> getYearButtons() {
        return this.findComponents(By.className("PrivatePickersYear-yearButton"));
    }

    /**
     * Selects by year number
     *
     * @param year the year number to select
     */
    public void select(int year) {
        select(String.valueOf(year));
    }

    /**
     * Selects by year string
     *
     * @param year the year string to select
     */
    public void select(String year) {
        WebComponent button = this.findComponent(By.xpath(
                String.format(".//button[contains(@class,'PrivatePickersYear-yearButton') and text()=%s]",
                        enrichQuote(year))));
        driver.moveTo(button);
        button.click();
    }
}
