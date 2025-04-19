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
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Represents the header switch header of the Calendar picker dialog, The GUI structure is "&lt;" "April 2021" "&gt;"
 *
 * @author Jack Yin
 * @since 1.2
 */
public class MuiPickersCalendarHeaderSwitchHeader extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String NAME = "PickersCalendarHeader-switchHeader";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    protected MuiPickersCalendarHeaderSwitchHeader(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    @Override
    public boolean validate() {
        return config.validateByCss(this, config.getCssPrefix() + NAME);
    }

    /**
     * Gets the left button for navigating to past dates
     * <p>
     * Use {@link #clickLeftButton(long)} for click and wait for the animation to be completed
     * </p>
     *
     * @return the left button component
     */
    public MuiButton getLeftButton() {
        WebComponent component = this
                .findComponent(By.className(config.getCssPrefix() + "PickersCalendarHeader-iconButton"));
        return new MuiButton(component, driver, config);
    }

    /**
     * Clicks the left button and wait until the slide animation is completed.
     *
     * @param waitInMillis the wait time in milliseconds
     */
    public void clickLeftButton(long waitInMillis) {
        getLeftButton().click();
        driver.threadSleep(waitInMillis);
    }

    /**
     * Get the middle header component, the container contains the date label for example "April 2021"
     *
     * @return the middle header component
     */
    public WebComponent getMiddleHeader() {
        return this.findComponent(By.className(config.getCssPrefix() + "PickersCalendarHeader-transitionContainer"));
    }

    /**
     * Gets the right button for navigating to future dates
     *
     * @return the right button component
     */
    public MuiButton getRightButton() {
        WebComponent component = this
                .findComponents(By.className(config.getCssPrefix() + "PickersCalendarHeader-iconButton")).get(1);
        return new MuiButton(component, driver, config);
    }

    /**
     * Clicks the left button and wait until the slide animation is completed.
     *
     * @param waitInMillis the wait time in milliseconds
     */
    public void clickRightButton(long waitInMillis) {
        getRightButton().click();
        driver.threadSleep(waitInMillis);
    }
}
