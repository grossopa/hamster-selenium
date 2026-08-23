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

package com.github.grossopa.selenium.component.mui.action;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.openqa.selenium.Keys.ESCAPE;

/**
 * Default close options actions by sending the ESCAPE key press event.
 *
 * <p>This implementation sends the ESCAPE key to the first option in the list to close the dropdown.
 * It is designed to work with Material UI components like Select and Autocomplete.</p>
 *
 * @author Jack Yin
 * @since 1.3
 */
public class DefaultCloseOptionsAction implements CloseOptionsAction {

    /**
     * Closes the options by sending the ESCAPE key press event to the first option in the list.
     *
     * @param parentOptionContainer the parent option container to operate onto
     * @param options the options list to operate onto
     * @param driver current Web Driver instance
     * @throws IllegalArgumentException if options is null or empty
     * @throws NullPointerException if any parameter is null
     */
    @Override
    public void close(WebComponent parentOptionContainer, List<? extends WebComponent> options,
            ComponentWebDriver driver) {
        requireNonNull(parentOptionContainer, "parentOptionContainer must not be null");
        requireNonNull(options, "options must not be null");
        requireNonNull(driver, "driver must not be null");
        
        if (options.isEmpty()) {
            throw new IllegalArgumentException("options list must not be empty");
        }
        
        options.get(0).sendKeys(ESCAPE);
    }
}
