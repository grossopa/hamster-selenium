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
package com.github.grossopa.selenium.recorder.component;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import org.openqa.selenium.WebElement;

import java.util.Optional;

/**
 * Detects whether a DOM element belongs to a known component of the selected component library, e.g. whether an
 * element is a Material UI button by checking the {@code MuiButton-root} css class. The detection is used during the
 * guided selection so that the generated page object could reference the component types directly.
 *
 * @author Jack Yin
 * @since 1.15
 * @see DetectedComponent
 */
public interface ComponentDetector {

    /**
     * Detects the component type of the given element.
     *
     * @param element the element to detect
     * @param driver the driver for additional lookups such as ancestor css classes
     * @return the detected component, empty if the element does not belong to any known component
     */
    Optional<DetectedComponent> detect(WebElement element, ComponentWebDriver driver);
}
