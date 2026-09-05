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
package com.github.grossopa.selenium.recorder.config;

/**
 * The front-end component frameworks that the recorder supports for component detection and page object generation.
 *
 * <p>The user selects the target framework when starting a recorder session. The framework determines which
 * component library (e.g. {@code hamster-selenium-component-materialui}) the generated page objects depend on.</p>
 *
 * @author Jack Yin
 * @since 1.15
 */
public enum ComponentFramework {

    /**
     * Material UI, backed by {@code hamster-selenium-component-materialui}.
     */
    MUI("Material UI"),

    /**
     * Plain HTML, backed by {@code hamster-selenium-component-html}.
     */
    HTML("HTML"),

    /**
     * Angular Material, reserved for future support.
     */
    MAT("Angular Material"),

    /**
     * Ant Design, reserved for future support.
     */
    ANTD("Ant Design");

    private final String displayName;

    ComponentFramework(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the framework.
     *
     * @return the display name of the framework
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Parses the framework from a case-insensitive name.
     *
     * @param name the framework name such as "mui" or "HTML"
     * @return the matched {@link ComponentFramework} instance
     * @throws IllegalArgumentException if the given name does not match any framework
     */
    public static ComponentFramework of(String name) {
        for (ComponentFramework framework : values()) {
            if (framework.name().equalsIgnoreCase(name)) {
                return framework;
            }
        }
        throw new IllegalArgumentException("Unknown component framework: " + name);
    }
}
