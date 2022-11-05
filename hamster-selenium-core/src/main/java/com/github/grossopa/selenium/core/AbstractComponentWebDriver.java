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
import com.github.grossopa.selenium.core.util.GracefulThreadSleep;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The abstract implementation of {@link ComponentWebDriver} with generic type support.
 *
 * @author Jack Yin
 * @since 1.4
 */
public abstract class AbstractComponentWebDriver implements ComponentWebDriver {

    protected final WebDriver driver;
    protected final GracefulThreadSleep threadSleep;

    /**
     * Constructs an instance with given non-null {@link WebDriver} and {@link GracefulThreadSleep} instances.
     *
     * @param driver the existing non-null driver to encapsulate
     * @param threadSleep optional, the graceful thread sleep instance
     */
    protected AbstractComponentWebDriver(WebDriver driver, @Nullable GracefulThreadSleep threadSleep) {
        requireNonNull(driver);
        this.driver = driver;
        this.threadSleep = defaultIfNull(threadSleep, new GracefulThreadSleep());
    }

    @Override
    public List<WebComponent> findComponents(By by) {
        return driver.findElements(by).stream().map(this::mapElement).collect(toList());
    }

    @Override
    public <T> T findComponentAs(By by, Function<WebComponent, T> mappingFunction) {
        return mappingFunction.apply(this.findComponent(by));
    }

    @Override
    public <T> List<T> findComponentsAs(By by, Function<WebComponent, T> mappingFunction) {
        return findComponents(by).stream().map(mappingFunction).collect(toList());
    }

    @Override
    public WebComponent findComponent(By by) {
        return mapElement(driver.findElement(by));
    }

    @Override
    public void get(String url) {
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public Actions createActions() {
        return new Actions(this);
    }

    @Override
    @SuppressWarnings("java:S6212")
    public WebDriverWait createWait(long waitInMilliseconds) {
        WebDriverWait wait = new WebDriverWait(this, Duration.ofMillis(waitInMilliseconds));
        wait.withTimeout(Duration.ofMillis(waitInMilliseconds));
        return wait;
    }

    @Override
    public void moveTo(WebElement element) {
        createActions().moveToElement(element).perform();
    }

    @Override
    public void scrollTo(WebElement element) {
        executeScript("arguments[0].scrollIntoView();", element);
    }

    @Override
    public void threadSleep(long millis) {
        threadSleep.sleep(millis);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
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
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeAsyncScript(script, args);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) {
        return ((TakesScreenshot) driver).getScreenshotAs(target);
    }

    @Override
    public void perform(Collection<Sequence> actions) {
        ((Interactive) driver).perform(actions);
    }

    @Override
    public void resetInputState() {
        ((Interactive) driver).resetInputState();
    }

    @Override
    public WebDriver getWrappedDriver() {
        return driver;
    }
}
