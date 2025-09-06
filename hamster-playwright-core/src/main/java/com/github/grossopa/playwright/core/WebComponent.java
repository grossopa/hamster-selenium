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

package com.github.grossopa.playwright.core;

import com.microsoft.playwright.Locator;

import java.util.List;
import java.util.function.Function;

/**
 * Represents a web component that can be interacted with in a Playwright-based automation framework.
 *
 * <p>This interface provides a component-oriented abstraction over Playwright's {@link Locator}, allowing
 * for higher-level interactions with web UI components. It enables a more object-oriented approach to
 * web automation by encapsulating element interactions within component objects.</p>
 *
 * <p>WebComponent instances can be converted to more specific component types using the {@link #as} method
 * with appropriate component mappers. This allows for specialized behavior based on the actual UI component
 * being represented.</p>
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface WebComponent {

    /**
     * Finds all nested components within the current component using the given selector.
     *
     * @param selector The CSS selector or XPath expression to locate elements
     * @return A list of WebComponent instances representing the found elements
     */
    List<WebComponent> findComponents(String selector);

    /**
     * Finds the first nested component within the current component using the given selector.
     *
     * @param selector The CSS selector or XPath expression to locate an element
     * @return A WebComponent instance representing the found element
     */
    WebComponent findComponent(String selector);

    /**
     * Maps this component to a more specific component type using the provided mapper function.
     *
     * <p>This method allows for conversion to specialized component types such as buttons, text fields,
     * selects, etc. The mapper function typically comes from component-specific factory classes.</p>
     *
     * <p>Example usage:
     * <pre>{@code
     * MuiButton button = component.as(MuiComponents.muiV5())::toButton);
     * MuiSelect select = component.as(MuiComponents.muiV5())::toSelect);
     * }</pre>
     * </p>
     *
     * @param mapper The function that converts this WebComponent to a more specific type
     * @param <T> The target component type
     * @return The converted component instance
     */
    <T> T as(Function<WebComponent, T> mapper);

    /**
     * Gets the underlying Playwright Locator for this component.
     *
     * @return The Locator instance representing this component
     */
    Locator locator();

    /**
     * Clicks on the component.
     */
    void click();

    /**
     * Hovers over the component.
     */
    void hover();

    /**
     * Gets the text content of the component.
     *
     * @return The text content
     */
    String textContent();

    /**
     * Gets the inner text of the component.
     *
     * @return The inner text
     */
    String innerText();

    /**
     * Gets the inner HTML of the component.
     *
     * @return The inner HTML
     */
    String innerHTML();

    /**
     * Gets the value of the specified attribute.
     *
     * @param name The attribute name
     * @return The attribute value, or null if the attribute is not present
     */
    String getAttribute(String name);

    /**
     * Checks if the component is visible.
     *
     * @return true if the component is visible, false otherwise
     */
    boolean isVisible();

    /**
     * Checks if the component is enabled.
     *
     * @return true if the component is enabled, false otherwise
     */
    boolean isEnabled();

    /**
     * Checks if the component is disabled.
     *
     * @return true if the component is disabled, false otherwise
     */
    boolean isDisabled();

    /**
     * Fills the component with the specified value (typically for input elements).
     *
     * @param value The value to fill
     */
    void fill(String value);

    /**
     * Gets the component driver that manages this component.
     *
     * @return The ComponentDriver instance
     */
    ComponentDriver driver();
}