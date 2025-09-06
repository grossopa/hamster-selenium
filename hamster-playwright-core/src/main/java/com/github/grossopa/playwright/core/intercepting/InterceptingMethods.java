/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.core.intercepting;

/**
 * The methods that could be intercepted.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class InterceptingMethods {

    /**
     * private constructor
     */
    private InterceptingMethods() {
        throw new AssertionError();
    }

    /**
     * Represents the {@link com.github.grossopa.playwright.core.ComponentDriver#findComponents(String)}
     */
    public static final String DRIVER_FIND_COMPONENTS = "driver.findComponents";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.ComponentDriver#findComponentAs(String, java.util.function.Function)}
     */
    public static final String DRIVER_FIND_COMPONENT_AS = "driver.findComponentAs";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.ComponentDriver#findComponentsAs(String, java.util.function.Function)}
     */
    public static final String DRIVER_FIND_COMPONENTS_AS = "driver.findComponentsAs";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.ComponentDriver#findComponent(String)}
     */
    public static final String DRIVER_FIND_COMPONENT = "driver.findComponent";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.ComponentDriver#navigate(String)}
     */
    public static final String DRIVER_NAVIGATE = "driver.navigate";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#findComponents(String)}
     */
    public static final String COMPONENT_FIND_COMPONENTS = "component.findComponents";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#findComponent(String)}
     */
    public static final String COMPONENT_FIND_COMPONENT = "component.findComponent";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#click()}
     */
    public static final String COMPONENT_CLICK = "component.click";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#hover()}
     */
    public static final String COMPONENT_HOVER = "component.hover";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#textContent()}
     */
    public static final String COMPONENT_TEXT_CONTENT = "component.textContent";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#innerText()}
     */
    public static final String COMPONENT_INNER_TEXT = "component.innerText";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#innerHTML()}
     */
    public static final String COMPONENT_INNER_HTML = "component.innerHTML";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#getAttribute(String)}
     */
    public static final String COMPONENT_GET_ATTRIBUTE = "component.getAttribute";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#isVisible()}
     */
    public static final String COMPONENT_IS_VISIBLE = "component.isVisible";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#isEnabled()}
     */
    public static final String COMPONENT_IS_ENABLED = "component.isEnabled";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#isDisabled()}
     */
    public static final String COMPONENT_IS_DISABLED = "component.isDisabled";

    /**
     * Represents the {@link com.github.grossopa.playwright.core.WebComponent#fill(String)}
     */
    public static final String COMPONENT_FILL = "component.fill";
}