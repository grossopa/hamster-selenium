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

package com.github.grossopa.selenium.component.mui.v4.core;

import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.core.util.SeleniumUtils.executeIgnoringStaleElementReference;
import static org.openqa.selenium.Keys.ESCAPE;

/**
 * The modal component provides a solid foundation for creating dialogs, popovers, lightboxes, or whatever else.
 *
 * @author Jack Yin
 * @since 1.1
 */
public abstract class MuiModal extends AbstractMuiComponent {

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    protected MuiModal(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    /**
     * Closes current Modal
     */
    public void close() {
        this.close(0L);
    }

    /**
     * Closes the current Modal and wait until it invisible or detached from DOM.
     *
     * @param waitInMilliseconds the max time to wait
     */
    public void close(long waitInMilliseconds) {
        driver.createActions().sendKeys(ESCAPE).perform();
        if (waitInMilliseconds > 0) {
            driver.createWait(waitInMilliseconds)
                    .until(driver -> executeIgnoringStaleElementReference(() -> !this.isDisplayed(), true));
        }
    }

    @Override
    public String toString() {
        return "MuiModal{" + "element=" + element + '}';
    }
}
