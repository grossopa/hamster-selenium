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

package com.github.grossopa.selenium.component.mui;

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiTextField;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;

/**
 * A utility class that provides shortcuts for finding Material UI components with reduced call chains.
 *
 * <p>This utility helps reduce the verbosity of finding Material UI components by providing
 * convenient methods that combine finding and conversion in a single call.</p>
 *
 * <p>Example usage:
 * <pre>{@code
 * // Instead of writing:
 * MuiButton button = driver.findComponent(By.className("MuiButton-root"))
 *                          .as(MuiComponents.muiV5())
 *                          .toButton();
 *
 * // You can write:
 * MuiButton button = MuiComponentFinder.findMuiButton(driver, By.className("MuiButton-root"));
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.4
 */
public class MuiComponentFinder {

    private MuiComponentFinder() {
        // Private constructor to prevent instantiation
    }

    /**
     * Finds a Material UI Button component using the given locating mechanism.
     * This is a shortcut method that reduces the call chain for finding MUI buttons.
     *
     * <p>Instead of writing:
     * {@code MuiButton button = driver.findComponent(locator).as(MuiComponents.muiV5()).toButton();}
     * You can write:
     * {@code MuiButton button = MuiComponentFinder.findMuiButton(driver, locator);}
     * </p>
     *
     * @param driver The ComponentWebDriver instance to use for finding elements
     * @param by The locating mechanism to use
     * @return The MuiButton component
     */
    public static MuiButton findMuiButton(ComponentWebDriver driver, By by) {
        WebComponent component = driver.findComponent(by);
        return component.as(MuiComponents.muiV5()).toButton();
    }

    /**
     * Finds a Material UI TextField component using the given locating mechanism.
     * This is a shortcut method that reduces the call chain for finding MUI text fields.
     *
     * <p>Instead of writing:
     * {@code MuiTextField textField = driver.findComponent(locator).as(MuiComponents.muiV5()).toTextField();}
     * You can write:
     * {@code MuiTextField textField = MuiComponentFinder.findMuiTextField(driver, locator);}
     * </p>
     *
     * @param driver The ComponentWebDriver instance to use for finding elements
     * @param by The locating mechanism to use
     * @return The MuiTextField component
     */
    public static MuiTextField findMuiTextField(ComponentWebDriver driver, By by) {
        WebComponent component = driver.findComponent(by);
        return component.as(MuiComponents.muiV5()).toTextField();
    }

    /**
     * Finds a Material UI component of the specified type using the given locating mechanism.
     * This is a generic shortcut method that reduces the call chain for finding any MUI component.
     *
     * @param driver The ComponentWebDriver instance to use for finding elements
     * @param by The locating mechanism to use
     * @param componentFunction The function to convert a WebComponent to the desired MUI component
     * @param <T> The type of MUI component to find
     * @return The requested MUI component
     */
    public static <T> T findMuiComponent(ComponentWebDriver driver, By by, java.util.function.Function<WebComponent, T> componentFunction) {
        WebComponent component = driver.findComponent(by);
        return componentFunction.apply(component);
    }
}
