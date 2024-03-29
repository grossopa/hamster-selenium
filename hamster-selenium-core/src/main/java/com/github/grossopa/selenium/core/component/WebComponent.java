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

package com.github.grossopa.selenium.core.component;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.factory.WebComponentFactory;
import com.github.grossopa.selenium.core.element.TextNodeElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;

import java.util.List;
import java.util.function.Function;

/**
 * Wrapper of an found {@link WebElement} to provide factory methods that to be able to convert itself to a
 * WebComponent.
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface WebComponent extends WrapsElement, WebElement, TakesScreenshot, Locatable, WrapsDriver {

    /**
     * Gets the web driver
     *
     * @return the web driver
     */
    ComponentWebDriver driver();

    /**
     * Find all elements within the current context using the given mechanism and encapsulate the {@link WebElement}
     * list into {@link WebComponent}.
     * <p>
     * When using xpath be aware that webdriver follows standard conventions: a search prefixed with "//" will search
     * the entire document, not just the children of this current node. Use ".//" to limit your search to the children
     * of this WebElement.
     * </p>
     * <p>
     * This method is affected by the 'implicit wait' times in force at the time of execution. When implicitly waiting,
     * this method will return as soon as there are more than 0 items in the found collection, or will return an empty
     * list if the timeout is reached.
     * </p>
     *
     * @param by The locating mechanism to use
     * @return A list of all {@link WebComponent}s, or an empty list if nothing matches.
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    List<WebComponent> findComponents(By by);

    /**
     * Find the first {@link WebComponent} using the given method and encapsulate it into {@link WebComponent}.
     * <p>
     * See the note in {@link #findElements(By)} about finding via XPath. This method is affected by the 'implicit wait'
     * times in force at the time of execution. The findElement(..) invocation will return a matching row, or try again
     * repeatedly until the configured timeout is reached.
     * </p>
     * <p>
     * findElement should not be used to look for non-present elements, use {@link #findComponents(By)} and assert zero
     * length response instead.
     * </p>
     *
     * @param by The locating mechanism
     * @return The first matching element on the current context.
     * @throws NoSuchElementException If no matching elements are found
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    WebComponent findComponent(By by);

    /**
     * Find all elements within the current context using the given mechanism and encapsulate the {@link WebElement}
     * list into {@link T}, which is a sub type of {@link WebComponent}.
     * <p>
     * See the note in {@link #findElements(By)} about finding via XPath. This method is affected by the 'implicit wait'
     * times in force at the time of execution. The findElement(..) invocation will return a matching row, or try again
     * repeatedly until the configured timeout is reached.
     * </p>
     * <p>
     * findElement should not be used to look for non-present elements, use {@link #findComponents(By)} and assert zero
     * length response instead.
     * </p>
     *
     * @param by The locating mechanism
     * @param mappingFunction mapping functions to convert component to instance of type {@link T}
     * @param <T> the target component type
     * @return A list of all {@link WebComponent}s, or an empty list if nothing matches.
     */
    <T extends WebComponent> List<T> findComponentsAs(By by, Function<WebComponent, T> mappingFunction);

    /**
     * Find the first {@link WebComponent} using the given method and encapsulate it into {@link T}, which is a sub type
     * of {@link WebComponent}.
     * <p>
     * See the note in {@link #findElements(By)} about finding via XPath. This method is affected by the 'implicit wait'
     * times in force at the time of execution. The findElement(..) invocation will return a matching row, or try again
     * repeatedly until the configured timeout is reached.
     * </p>
     * <p>
     * findElement should not be used to look for non-present elements, use {@link #findComponents(By)} and assert zero
     * length response instead.
     * </p>
     *
     * @param by The locating mechanism
     * @param mappingFunction mapping functions to convert component to instance of type {@link T}
     * @param <T> the target component type
     * @return The first matching element on the current context.
     * @throws NoSuchElementException If no matching elements are found
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    <T extends WebComponent> WebComponent findComponentAs(By by, Function<WebComponent, T> mappingFunction);

    /**
     * deprecated, in favor of {@link #findComponents(By)}
     *
     * @param by The locating mechanism to use
     * @return A list of all {@link WebElement}s, or an empty list if nothing matches
     * @deprecated in favor of {@link #findComponents(By)}
     */
    @Deprecated(since = "1.0")
    List<WebElement> findElements(By by);

    /**
     * deprecated, in favor of {@link #findComponent(By)}
     *
     * @param by The locating mechanism to use
     * @return A list of all {@link WebElement}s, or an empty list if nothing matches
     * @deprecated in favor of {@link #findComponent(By)}
     */
    @Deprecated(since = "1.0")
    WebElement findElement(By by);

    /**
     * As a list of components.
     * <br>
     * For example, {@code HtmlTable table = driver.findComponent(By.id("customers")).as(HtmlComponents.html()).toTable();}
     *
     * @param components the components instance
     * @param <T> the components type
     * @return the created instance of {@link Components} with {@link Components#setContext(WebComponent,
     * ComponentWebDriver)} invoked.
     */
    <T extends Components> T as(T components);

    /**
     * Converts the current web component to particular component constructed by the factory.
     *
     * @param factory the factory instance to build the Component.
     * @param <T> the component type
     * @return the built {@link WebComponent} instance
     */
    <T extends WebComponent> T to(WebComponentFactory<T> factory);

    /**
     * Whether the attribute has the value, it uses space as the splitter.
     *
     * @param attributeName the name of the attribute
     * @param attributeValue the expected containing value of the attribute
     * @return true for contains, false otherwise
     */
    boolean attributeContains(String attributeName, String attributeValue);

    /**
     * WHether the style contains the name and value, e.g. display:block.
     *
     * @param styleName the name of the style
     * @param styleValue the value of the style
     * @return true for contains, false otherwise
     */
    boolean styleContains(String styleName, String styleValue);

    /**
     * Whether the underlying element is currently focused.
     *
     * @return true if the underlying element is currently focused.
     */
    boolean isFocused();

    /**
     * Gets the outerHTML of the current component
     *
     * @return outerHTML attribute
     */
    String outerHTML();

    /**
     * Validates whether current wrapped {@link WebElement} is expected and controllable by current instance.
     *
     * @return whether current wrapped {@link WebElement} is expected and controllable by current instance.
     */
    boolean validate();

    /**
     * Gets the element id
     *
     * @return the element id
     */
    String getId();

    /**
     * Gets the text and comment nodes.
     *
     * @return the text and comment nodes.
     */
    List<TextNodeElement> findTextNodes();
}
