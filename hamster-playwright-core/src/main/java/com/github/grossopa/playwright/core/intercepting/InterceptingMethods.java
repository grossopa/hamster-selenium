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

import com.microsoft.playwright.Locator;

/**
 * Defines all intercepting method names for logging and monitoring purpose.
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
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#click()}
     */
    public static final String LOCATOR_CLICK = "locator.click";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#dblclick()}
     */
    public static final String LOCATOR_DBLCLICK = "locator.dblclick";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#fill(String)}
     */
    public static final String LOCATOR_FILL = "locator.fill";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#hover()}
     */
    public static final String LOCATOR_HOVER = "locator.hover";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#innerHTML()}
     */
    public static final String LOCATOR_INNER_HTML = "locator.innerHTML";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#innerText()}
     */
    public static final String LOCATOR_INNER_TEXT = "locator.innerText";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#textContent()}
     */
    public static final String LOCATOR_TEXT_CONTENT = "locator.textContent";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#getAttribute(String)}
     */
    public static final String LOCATOR_GET_ATTRIBUTE = "locator.getAttribute";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#isDisabled()}
     */
    public static final String LOCATOR_IS_DISABLED = "locator.isDisabled";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#isEnabled()}
     */
    public static final String LOCATOR_IS_ENABLED = "locator.isEnabled";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#isVisible()}
     */
    public static final String LOCATOR_IS_VISIBLE = "locator.isVisible";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#selectOption(String[])}
     */
    public static final String LOCATOR_SELECT_OPTION = "locator.selectOption";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#dragTo(Locator)}
     */
    public static final String LOCATOR_DRAG_TO = "locator.dragTo";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#focus()}
     */
    public static final String LOCATOR_FOCUS = "locator.focus";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#blur()}
     */
    public static final String LOCATOR_BLUR = "locator.blur";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#boundingBox()}
     */
    public static final String LOCATOR_BOUNDING_BOX = "locator.boundingBox";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#check()}
     */
    public static final String LOCATOR_CHECK = "locator.check";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#inputValue()}
     */
    public static final String LOCATOR_INPUT_VALUE = "locator.inputValue";
    
    /**
     * Represents the {@link com.microsoft.playwright.Locator#press(String)}
     */
    public static final String LOCATOR_PRESS = "locator.press";
}