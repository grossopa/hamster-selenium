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

package com.github.grossopa.selenium.component.mui.locator;

import com.github.grossopa.selenium.component.mui.feedback.MuiDialog;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * Locates the global {@link MuiDialog}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiDialogLocator {

    private final ComponentWebDriver driver;
    private final MuiConfig config;

    /**
     * Constructs an instance with given root driver.
     *
     * @param driver
     *         the root driver
     * @param config
     *         the Material-UI configuration
     */
    public MuiDialogLocator(ComponentWebDriver driver, MuiConfig config) {
        requireNonNull(driver);
        requireNonNull(config);
        this.driver = driver;
        this.config = config;
    }

    /**
     * Finds all dialogs which includes both visible and invisible dialogs.
     *
     * @return all founded dialogs
     */
    public List<MuiDialog> findAllDialogs() {
        return driver.findComponents(By.xpath(
                String.format("/html/body/div[contains(@class, '%s')]", config.getCssPrefix() + "Dialog-root")))
                .stream().map(component -> new MuiDialog(component, driver, config)).collect(toList());
    }

    /**
     * Finds all visible dialogs.
     *
     * @return all founded visible dialogs.
     */
    public List<MuiDialog> findVisibleDialogs() {
        return findAllDialogs().stream().filter(WebElement::isDisplayed).collect(toList());
    }
}
