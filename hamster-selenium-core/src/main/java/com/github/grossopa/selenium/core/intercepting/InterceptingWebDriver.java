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

package com.github.grossopa.selenium.core.intercepting;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.core.intercepting.InterceptingMethods.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * Intercepts the driver actions with customized handlers.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class InterceptingWebDriver
        implements WebDriver, HasCapabilities, Interactive, TakesScreenshot, JavascriptExecutor {

    private final WebDriver driver;
    private final InterceptingHandler handler;

    /**
     * Constructs an instance with target delegated {@link WebElement} instance.
     *
     * @param driver the driver to delegate
     * @param handler the handler for before, after and on exception actions.
     */
    public InterceptingWebDriver(WebDriver driver, InterceptingHandler handler) {
        requireNonNull(handler);
        requireNonNull(driver);
        this.driver = driver;
        this.handler = handler;
    }

    @Override
    public void get(String url) {
        handler.execute(() -> {
            driver.get(url);
            return null;
        }, MethodInfo.create(driver, DRIVER_GET, url));
    }

    @Override
    public String getCurrentUrl() {
        return handler.execute(driver::getCurrentUrl, MethodInfo.create(driver, DRIVER_GET_CURRENT_URL));
    }

    @Override
    public String getTitle() {
        return handler.execute(driver::getTitle, MethodInfo.create(driver, DRIVER_GET_TITLE));
    }

    @Override
    public List<WebElement> findElements(By by) {
        return handler.execute(
                () -> driver.findElements(by).stream().map(element -> new InterceptingWebElement(element, handler))
                        .collect(toList()), MethodInfo.create(driver, DRIVER_FIND_ELEMENTS, by));
    }

    @Override
    public WebElement findElement(By by) {
        return handler.execute(() -> new InterceptingWebElement(driver.findElement(by), handler),
                MethodInfo.create(driver, DRIVER_FIND_ELEMENT, by));
    }

    @Override
    public String getPageSource() {
        return handler.execute(driver::getPageSource, MethodInfo.create(driver, DRIVER_GET_PAGE_SOURCE));
    }

    @Override
    public void close() {
        handler.execute(() -> {
            driver.close();
            return null;
        }, MethodInfo.create(driver, DRIVER_CLOSE));
    }

    @Override
    public void quit() {
        handler.execute(() -> {
            driver.quit();
            return null;
        }, MethodInfo.create(driver, DRIVER_QUIT));
    }

    @Override
    public Set<String> getWindowHandles() {
        return handler.execute(driver::getWindowHandles, MethodInfo.create(driver, DRIVER_GET_WINDOW_HANDLES));
    }

    @Override
    public String getWindowHandle() {
        return handler.execute(driver::getWindowHandle, MethodInfo.create(driver, DRIVER_GET_WINDOW_HANDLE));
    }

    @Override
    public TargetLocator switchTo() {
        return new InterceptingTargetLocator(driver.switchTo(), handler);
    }

    @Override
    public Navigation navigate() {
        return new InterceptingNavigation(driver.navigate(), handler);
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    @Override
    public Capabilities getCapabilities() {
        return ((HasCapabilities) driver).getCapabilities();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return handler.execute(() -> ((JavascriptExecutor) driver).executeScript(script, args),
                MethodInfo.create(driver, DRIVER_EXECUTE_SCRIPT, script, args));
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return handler.execute(() -> ((JavascriptExecutor) driver).executeAsyncScript(script, args),
                MethodInfo.create(driver, DRIVER_EXECUTE_ASYNC_SCRIPT, script, args));
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return handler.execute(() -> ((TakesScreenshot) driver).getScreenshotAs(target),
                MethodInfo.create(driver, DRIVER_GET_SCREENSHOT_AS, target));
    }

    @Override
    public void perform(Collection<Sequence> actions) {
        handler.execute(() -> {
            ((Interactive) driver).perform(actions);
            return null;
        }, MethodInfo.create(driver, DRIVER_PERFORM, actions));
    }

    @Override
    public void resetInputState() {
        handler.execute(() -> {
            ((Interactive) driver).resetInputState();
            return null;
        }, MethodInfo.create(driver, DRIVER_RESET_INPUT_STATE));
    }


}
