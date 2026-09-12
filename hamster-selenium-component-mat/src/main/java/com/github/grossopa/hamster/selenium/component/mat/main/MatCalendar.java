/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * {@code <mat-calendar>} is the calendar widget used within {@link MatDatepicker} for date selection.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/datepicker/overview">
 * https://material.angular.io/components/datepicker/overview</a>
 * @since 1.16
 */
public class MatCalendar extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Calendar";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatCalendar(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "calendar");
    }

    /**
     * Gets the current period label (e.g. "SEP 2023").
     *
     * @return the period label text
     */
    public String getPeriodLabel() {
        return this.findComponent(By.className(config.getCssPrefix() + "calendar-period-label")).getText();
    }

    /**
     * Navigates to the previous month.
     */
    public void previousMonth() {
        this.findComponent(By.className(config.getCssPrefix() + "calendar-previous-button")).click();
    }

    /**
     * Navigates to the next month.
     */
    public void nextMonth() {
        this.findComponent(By.className(config.getCssPrefix() + "calendar-next-button")).click();
    }

    /**
     * Gets all date cells in the current calendar view.
     *
     * @return the list of date cell elements
     */
    public java.util.List<WebComponent> getDateCells() {
        return this.findComponents(By.className(config.getCssPrefix() + "calendar-body-cell"));
    }

    /**
     * Selects a date cell by its text content (day number).
     *
     * @param dayText the day text to match (e.g. "15")
     */
    public void selectDate(String dayText) {
        var cells = getDateCells();
        for (WebComponent cell : cells) {
            if (dayText.equals(cell.getText())) {
                cell.click();
                return;
            }
        }
    }

    /**
     * Checks if a specific date is selected.
     *
     * @param dayText the day text to check (e.g. "15")
     * @return true if the date is selected, false otherwise
     */
    public boolean isDateSelected(String dayText) {
        var cells = getDateCells();
        for (WebComponent cell : cells) {
            if (dayText.equals(cell.getText())) {
                String classAttr = cell.getAttribute(CLASS);
                return classAttr.contains(config.getCssPrefix() + "calendar-body-selected");
            }
        }
        return false;
    }
}
