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

package com.github.grossopa.selenium.core;

import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

/**
 * An encapsulated {@link WebDriver} instance that supports to get the web element as {@link WebComponent}.
 *
 * @author Jack Yin
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public interface ComponentWebDriver
        extends WrapsDriver, WebDriver, JavascriptExecutor, HasInputDevices, HasCapabilities, Interactive,
        TakesScreenshot {

    /**
     * Finds all elements within the current page using the given mechanism and encapsulate the {@link WebElement} list
     * into {@link WebComponent}.
     * <p>
     * This method is affected by the 'implicit wait' times in force at the time of execution. When implicitly waiting,
     * this method will return as soon as there are more than 0 items in the found collection, or will return an empty
     * list if the timeout is reached.
     * </p>
     *
     * @param by The locating mechanism to use
     * @return A list of all {@link WebComponent}s, or an empty list if nothing matches
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    List<WebComponent> findComponents(By by);

    /**
     * Find the first {@link WebElement} using the given method and encapsulate it into {@link T} which is sub type of
     * WebComponent.
     * <p>
     * This method is affected by the 'implicit wait' times in force at the time of execution. The findElement(..)
     * invocation will return a matching row, or try again repeatedly until the configured timeout is reached.
     * </p>
     * <p>
     * findElement should not be used to look for non-present elements, use {@link #findComponents(By)} and assert zero
     * length response instead.
     * </p>
     *
     * @param by The locating mechanism
     * @param mappingFunction the mapping function to convert {@link WebComponent} to {@link T}.
     * @param <T> the target type
     * @return The first matching element on the current page
     * @throws NoSuchElementException If no matching elements are found
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    <T extends WebComponent> T findComponentAs(By by, Function<WebComponent, T> mappingFunction);

    /**
     * Finds all elements within the current page using the given mechanism and encapsulate the {@link WebElement} list
     * into {@link T} which is sub type of WebComponent.
     * <p>
     * This method is affected by the 'implicit wait' times in force at the time of execution. When implicitly waiting,
     * this method will return as soon as there are more than 0 items in the found collection, or will return an empty
     * list if the timeout is reached.
     * </p>
     *
     * @param by The locating mechanism to use
     * @param mappingFunction the mapping function to convert {@link WebComponent} to {@link T}.
     * @param <T> the target type
     * @return A list of all {@link WebComponent}s, or an empty list if nothing matches
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    <T extends WebComponent> List<T> findComponentsAs(By by, Function<WebComponent, T> mappingFunction);

    /**
     * Find the first {@link WebElement} using the given method and encapsulate it into {@link WebComponent}.
     * <p>
     * This method is affected by the 'implicit wait' times in force at the time of execution. The findElement(..)
     * invocation will return a matching row, or try again repeatedly until the configured timeout is reached.
     * </p>
     * <p>
     * findElement should not be used to look for non-present elements, use {@link #findComponents(By)} and assert zero
     * length response instead.
     * </p>
     *
     * @param by The locating mechanism
     * @return The first matching element on the current page
     * @throws NoSuchElementException If no matching elements are found
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    WebComponent findComponent(By by);

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
     * Maps a given {@link WebElement} to {@link WebComponent}.
     *
     * @param element the element instance to map
     * @return the mapped {@link WebComponent} instance
     */
    WebComponent mapElement(WebElement element);

    /**
     * A shortcut for {@code new Actions(driver);}
     *
     * @return the created Actions instance
     */
    Actions createActions();

    /**
     * A shortcut to create new instance of {@link WebDriverWait} with milliseconds.
     *
     * @param timeOutInMilliseconds the timeout in milliseconds
     * @return the created {@link WebDriverWait} instance.
     */
    WebDriverWait createWait(long timeOutInMilliseconds);

    /**
     * Move mouse to the element. shortcut of {@code createActions().moveToElement(element).perform()}
     *
     * @param element the element to move to.
     */
    void moveTo(WebElement element);

    /**
     * A neat solution for scrolling to an element
     *
     * @param element the element to scroll to
     */
    void scrollTo(WebElement element);

    /**
     * Sleeps current thread in millisecond
     *
     * @param millis the time to sleep in millisecond
     */
    void threadSleep(long millis);
}
