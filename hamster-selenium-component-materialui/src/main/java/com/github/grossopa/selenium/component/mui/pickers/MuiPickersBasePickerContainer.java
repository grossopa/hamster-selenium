/*
 * Copyright © 2020 the original author or authors.
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
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.surfaces.MuiToolbar;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * The Date Picker picker container
 *
 * @author Jack Yin
 * @since 1.2
 */
public class MuiPickersBasePickerContainer extends AbstractMuiComponent {

    public static final String NAME = "PickersBasePicker-container";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver  the root driver
     * @param config  the Material UI configuration
     */
    public MuiPickersBasePickerContainer(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    /**
     * Overrides the default behaviour as the Date Picker root is actually a picker container
     *
     * @return true if the wrapped element is picker container
     */
    @Override
    public boolean validate() {
        return config.validateByCss(this, config.getCssPrefix() + NAME);
    }

    /**
     * Finds the Toolbar that contains the date buttons
     *
     * @return the toolbar
     */
    public MuiToolbar getToolbar() {
        WebComponent toolbar = this.findComponent(By.className(config.getRootCss(MuiToolbar.NAME)));
        return new MuiToolbar(toolbar, driver, config);
    }

    /**
     * Finds the toolbar buttons, note the result could be various based on the different settings of the dates.
     *
     * @return the buttons within the top side toolbar
     */
    public List<MuiButton> getToolbarButtons() {
        return getToolbar().findComponentsAs(By2.className(config.getRootCss(MuiButton.NAME)),
                c -> new MuiButton(c, driver, config));
    }

    /**
     * Tries to get the selection component as year selection.
     *
     * @return the instance of year selection
     */
    public MuiPickersYearSelectionContainer getAsYearSelection() {
        WebComponent component = this
                .findComponent(By.className(config.getCssPrefix() + MuiPickersYearSelectionContainer.NAME));
        return new MuiPickersYearSelectionContainer(component.getWrappedElement(), driver, config);
    }

    /**
     * Tries to get the selection component as basic selection. a basic selection should include a month switch header,
     * a days header and days of the month for selection.
     *
     * @return the basic components instance with all 3 sub components in
     */
    public MuiPickersBasicPickerViewComponents getAsBasic() {
        MuiPickersCalendarHeaderSwitchHeader switchHeader = this.findComponentAs(
                By.className(config.getCssPrefix() + MuiPickersCalendarHeaderSwitchHeader.NAME),
                c -> new MuiPickersCalendarHeaderSwitchHeader(c.getWrappedElement(), driver, config));
        MuiPickersCalendarHeaderDaysHeader daysHeader = this.findComponentAs(
                By.className(config.getCssPrefix() + MuiPickersCalendarHeaderDaysHeader.NAME),
                c -> new MuiPickersCalendarHeaderDaysHeader(c.getWrappedElement(), driver, config));
        MuiPickersCalendarTransitionContainer transitionContainer = this.findComponentAs(
                By.className(config.getCssPrefix() + MuiPickersCalendarTransitionContainer.NAME),
                c -> new MuiPickersCalendarTransitionContainer(c.getWrappedElement(), driver, config));

        return new MuiPickersBasicPickerViewComponents(switchHeader, daysHeader, transitionContainer);
    }

}
