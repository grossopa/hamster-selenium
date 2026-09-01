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

import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Factory for creating the {@link ComponentDetector} instances of the user selected component framework.
 *
 * @author Jack Yin
 * @since 1.15
 * @see ComponentDetector
 */
public class ComponentDetectors {

    /**
     * private constructor
     */
    private ComponentDetectors() {
        throw new AssertionError();
    }

    /**
     * Creates the component detectors for the framework configured in the given recorder configuration. For Material
     * UI both the MUI css based detector and the plain HTML tag based detector are provided so that e.g. a native
     * table is still recognized.
     *
     * @param config the recorder configuration with the selected framework, must not be null
     * @return the component detectors of the selected framework, empty if the framework is not supported yet
     */
    public static List<ComponentDetector> forFramework(RecorderConfig config) {
        requireNonNull(config);
        ComponentFramework framework = config.getFramework();
        return switch (framework) {
            case MUI -> List.of(new MuiComponentDetector(), new HtmlComponentDetector());
            case HTML -> List.of(new HtmlComponentDetector());
            default -> List.of();
        };
    }
}
