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

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Interactive;

import java.net.URL;
import java.util.Collection;

/**
 * The methods that could be intercepted.
 *
 * @author Jack Yin
 * @since 1.4
 */
public class InterceptingMethods {

    /**
     * private constructor
     */
    private InterceptingMethods() {
        throw new AssertionError();
    }

    /**
     * Represents the {@link org.openqa.selenium.WebElement#click()}
     */
    public static final String ELEMENT_CLICK = "element.click";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#submit()}
     */
    public static final String ELEMENT_SUBMIT = "element.submit";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#sendKeys(CharSequence...)}
     */
    public static final String ELEMENT_SEND_KEYS = "element.sendKeys";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#clear()}
     */
    public static final String ELEMENT_CLEAR = "element.clear";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#getTagName()}
     */
    public static final String ELEMENT_GET_TAG_NAME = "element.getTagName";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#getAttribute(String)}
     */
    public static final String ELEMENT_GET_ATTRIBUTE = "element.getAttribute";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#isSelected()}
     */
    public static final String ELEMENT_IS_SELECTED = "element.isSelected";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#isEnabled()}
     */
    public static final String ELEMENT_IS_ENABLED = "element.isEnabled";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#getText()}
     */
    public static final String ELEMENT_GET_TEXT = "element.getText";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#findElements(By)}
     */
    public static final String ELEMENT_FIND_ELEMENTS = "element.findElements";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#findElement(By)}
     */
    public static final String ELEMENT_FIND_ELEMENT = "element.findElement";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#isDisplayed()}
     */
    public static final String ELEMENT_IS_DISPLAYED = "element.isDisplayed";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#getLocation()}
     */
    public static final String ELEMENT_GET_LOCATION = "element.getLocation";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#getSize()}
     */
    public static final String ELEMENT_GET_SIZE = "element.getSize";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#getRect()}
     */
    public static final String ELEMENT_GET_RECT = "element.getRect";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#getCssValue(String)}
     */
    public static final String ELEMENT_GET_CSS_VALUE = "element.getCssValue";

    /**
     * Represents the {@link org.openqa.selenium.WebElement#getScreenshotAs(OutputType)}
     */
    public static final String ELEMENT_GET_SCREENSHOT_AS = "element.getScreenshotAs";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#get(String)}
     */
    public static final String DRIVER_GET = "driver.get";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#getCurrentUrl()}
     */
    public static final String DRIVER_GET_CURRENT_URL = "driver.getCurrentUrl";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#getTitle()}
     */
    public static final String DRIVER_GET_TITLE = "driver.getTitle";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#findElements(By)}
     */
    public static final String DRIVER_FIND_ELEMENTS = "driver.findElements";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#findElement(By)}
     */
    public static final String DRIVER_FIND_ELEMENT = "driver.findElement";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#getPageSource()}
     */
    public static final String DRIVER_GET_PAGE_SOURCE = "driver.getPageSource";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#close()}
     */
    public static final String DRIVER_CLOSE = "driver.close";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#quit()}
     */
    public static final String DRIVER_QUIT = "driver.quit";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#getWindowHandles()}
     */
    public static final String DRIVER_GET_WINDOW_HANDLES = "driver.getWindowHandles";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver#getWindowHandle()}
     */
    public static final String DRIVER_GET_WINDOW_HANDLE = "driver.getWindowHandle";

    /**
     * Represents the {@link org.openqa.selenium.JavascriptExecutor#executeScript(String, Object...)}
     */
    public static final String DRIVER_EXECUTE_SCRIPT = "driver.executeScript";

    /**
     * Represents the {@link org.openqa.selenium.JavascriptExecutor#executeAsyncScript(String, Object...)}
     */
    public static final String DRIVER_EXECUTE_ASYNC_SCRIPT = "driver.executeAsyncScript";

    /**
     * Represents the {@link org.openqa.selenium.TakesScreenshot#getScreenshotAs(OutputType)}
     */
    public static final String DRIVER_GET_SCREENSHOT_AS = "driver.getScreenshotAs";

    /**
     * Represents the {@link Interactive#perform(Collection)}
     */
    public static final String DRIVER_PERFORM = "driver.perform";

    /**
     * Represents the {@link Interactive#resetInputState()}
     */
    public static final String DRIVER_RESET_INPUT_STATE = "driver.resetInputState";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.TargetLocator#frame(int)} and other overloaded methods.
     */
    public static final String TARGETLOCATOR_FRAME = "targetLocator.frame";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.TargetLocator#parentFrame()}
     */
    public static final String TARGETLOCATOR_PARENT_FRAME = "targetLocator.parentFrame";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.TargetLocator#window(String)}
     */
    public static final String TARGETLOCATOR_WINDOW = "targetLocator.window";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.TargetLocator#newWindow(WindowType)}
     */
    public static final String TARGETLOCATOR_NEW_WINDOW = "targetLocator.newWindow";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.TargetLocator#defaultContent()}
     */
    public static final String TARGETLOCATOR_DEFAULT_CONTENT = "targetLocator.defaultContent";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.TargetLocator#activeElement()}
     */
    public static final String TARGETLOCATOR_ACTIVE_ELEMENT = "targetLocator.activeElement";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.Navigation#back()}
     */
    public static final String NAVIGATION_BACK = "navigation.back";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.Navigation#forward()}
     */
    public static final String NAVIGATION_FORWARD = "navigation.forward";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.Navigation#to(URL)} and other overloaded methods.
     */
    public static final String NAVIGATION_TO = "navigation.to";

    /**
     * Represents the {@link org.openqa.selenium.WebDriver.Navigation#refresh()}.
     */
    public static final String NAVIGATION_REFRESH = "navigation.refresh";

    /**
     * Represents the {@link org.openqa.selenium.Alert#dismiss()}.
     */
    public static final String ALERT_DISMISS = "alert.dismiss";

    /**
     * Represents the {@link org.openqa.selenium.Alert#accept()}.
     */
    public static final String ALERT_ACCEPT = "alert.accept";

    /**
     * Represents the {@link org.openqa.selenium.Alert#getText()}.
     */
    public static final String ALERT_GET_TEXT = "alert.getText";

    /**
     * Represents the {@link org.openqa.selenium.Alert#sendKeys(String)}.
     */
    public static final String ALERT_SEND_KEYS = "alert.sendKeys";


}
