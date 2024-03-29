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
import com.github.grossopa.selenium.component.mui.exception.DatePickerNotClosedException;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiTextField;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.v5.datetime.MuiCalendarPicker.ViewType.DAY;
import static com.github.grossopa.selenium.component.mui.v5.datetime.MuiCalendarPicker.ViewType.YEAR;
import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static com.github.grossopa.selenium.core.util.SeleniumUtils.executeIgnoringStaleElementReference;
import static com.github.grossopa.selenium.core.util.SeleniumUtils.isNotDisplayed;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * The date picker text box with a popup on desktop.
 *
 * @author Jack Yin
 * @see <a href="https://mui.com/components/date-picker/#basic-usage">
 * https://mui.com/components/date-picker/#basic-usage</a>
 * @since 1.8
 */
public class MuiDatePickerFormField extends MuiTextField {

    private final List<MuiCalendarPicker.ViewType> views;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiDatePickerFormField(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, List.of(YEAR, DAY));
    }

    public MuiDatePickerFormField(WebElement element, ComponentWebDriver driver, MuiConfig config,
            List<MuiCalendarPicker.ViewType> views) {
        super(element, driver, config);
        requireNonNull(views);
        if (views.isEmpty()) {
            throw new IllegalArgumentException("view type is mandatory");
        }

        this.views = List.copyOf(views.stream().distinct().collect(toList()));
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V5);
    }

    /**
     * Gets the date selection icon button
     *
     * @return the date selection icon button
     */
    public WebComponent getDateButton() {
        return this.findComponent(xpathBuilder().anywhereRelative("div").attr(CLASS)
                .contains(config.getCssPrefix() + "InputAdornment-root").child("button").build());
    }

    /**
     * Expands the calendar picker
     *
     * @return the popup calendar picker
     */
    public MuiCalendarPicker openCalendarPicker() {
        return openCalendarPicker(0L);
    }

    /**
     * Expands the calendar picker, with the delays for animation.
     *
     * @param delayInMillis the delays in milliseconds
     * @return the popup calendar picker
     */
    public MuiCalendarPicker openCalendarPicker(long delayInMillis) {
        WebComponent componentDialog = tryLocatePickerDialog();
        if (componentDialog == null) {
            getDateButton().click();
            if (delayInMillis > 0L) {
                componentDialog = driver.createWait(delayInMillis)
                        .until(d -> executeIgnoringStaleElementReference(this::tryLocatePickerDialog, null));
            } else {
                componentDialog = tryLocatePickerDialog();
            }
        }

        return new MuiCalendarPicker(componentDialog, driver, config, this.views);
    }

    /**
     * Closes the displayed picker.
     */
    public void closePicker() {
        closePicker(0L);
    }

    /**
     * Closes the displayed picker with delay for animation.
     *
     * @param delayInMillis the delay in milliseconds
     */
    public void closePicker(long delayInMillis) {
        WebComponent componentDialog = tryLocatePickerDialog();
        if (isNotDisplayed(componentDialog)) {
            return;
        }

        // mute sonar null alert: https://jira.sonarsource.com/browse/SONARJAVA-3502
        requireNonNull(componentDialog);
        componentDialog.sendKeys(Keys.ESCAPE);

        if (delayInMillis > 0L) {
            driver.createWait(delayInMillis).until(d -> isNotDisplayed(tryLocatePickerDialog()));
            componentDialog = null;
        } else {
            componentDialog = tryLocatePickerDialog();
        }

        if (!isNotDisplayed(componentDialog)) {
            throw new DatePickerNotClosedException("Date picker popup is not properly closed.");
        }
    }

    /**
     * Sets the date by operating the component to expand the date picker and do the actions to select the date. If the
     * year does not include the corresponding value such as Year, so the year part of the date will be ignored.
     *
     * @param date the new date to set.
     */
    public void setDate(LocalDate date) {
        this.setDate(date, 0L);
    }

    /**
     * Sets the date by operating the component to expand the date picker and do the actions to select the date. If the
     * year does not include the corresponding value such as Year, so the year part of the date will be ignored.
     *
     * @param date the new date to set.
     * @param delayInMillis to delay the actions following animations by milliseconds. recommended value is 500.
     */
    public void setDate(LocalDate date, long delayInMillis) {
        this.openCalendarPicker(delayInMillis).setDate(date, delayInMillis);
    }

    /**
     * Gets the current specified views of the date picker.
     *
     * @return the current specified views of the date picker.
     */
    public List<MuiCalendarPicker.ViewType> getViews() {
        return views;
    }

    /**
     * Tries to locate the picker dialog
     */
    private WebComponent tryLocatePickerDialog() {
        List<WebComponent> componentList = this.findComponents(By.xpath(
                String.format("%s/div[@role='dialog']//div[contains(@class,'%s')]", config.getOverlayAbsolutePath(),
                        config.getCssPrefix() + "CalendarPicker-root")));

        if (componentList.isEmpty()) {
            return null;
        } else {
            return componentList.get(componentList.size() - 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiDatePickerFormField)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MuiDatePickerFormField that = (MuiDatePickerFormField) o;
        return Objects.equals(views, that.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), views);
    }

    @Override
    public String toString() {
        return "MuiDatePickerFormField{" + "views=" + join(views, ",") + ", element=" + element + '}';
    }
}
