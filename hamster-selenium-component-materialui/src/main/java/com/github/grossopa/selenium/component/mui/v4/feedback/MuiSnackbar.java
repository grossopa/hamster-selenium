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

package com.github.grossopa.selenium.component.mui.v4.feedback;

import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;

import java.util.Objects;

import static com.github.grossopa.selenium.core.util.SeleniumUtils.executeIgnoringStaleElementReference;

/**
 * Snackbars provide brief messages about app processes. The component is also known as a toast.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/snackbars/">https://material-ui.com/components/snackbars/</a>
 * @since 1.0
 */
public class MuiSnackbar extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Snackbar";

    private final Long autoHideDuration;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiSnackbar(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param autoHideDuration optional, the autoHideDuration in milliseconds
     */
    public MuiSnackbar(WebElement element, ComponentWebDriver driver, MuiConfig config, Long autoHideDuration) {
        super(element, driver, config);
        this.autoHideDuration = autoHideDuration;
    }

    /**
     * Gets the inner {@link MuiSnackbarContent} element.
     *
     * <p>
     * Note that the result would be null if the content is customized.
     * </p>
     *
     * @return the inner {@link MuiSnackbarContent} element.
     */
    public MuiSnackbarContent getContent() {
        WebComponent component = this.findComponent(By.className(config.getCssPrefix() + "SnackbarContent-root"));
        return new MuiSnackbarContent(component, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }


    /**
     * Kicks off the check for the snackbar to disappear automatically.
     *
     * @return the wait for waiting this to be disappeared
     */
    public WebDriverWait startAutoHideCheck() {
        return startAutoHideCheck(autoHideDuration);
    }

    /**
     * Kicks off the check for the snackbar to disappear automatically. throws {@link IllegalArgumentException} when the
     * autoHideDuration is null or &lt;= 0.
     *
     * @param autoHideDuration the expected auto hide duration
     * @return the wait for waiting this to be disappeared
     */
    public WebDriverWait startAutoHideCheck(Long autoHideDuration) {
        if (autoHideDuration == null || autoHideDuration <= 0) {
            throw new IllegalArgumentException("Invalid autoHideDuration value: " + autoHideDuration);
        }
        WebDriverWait wait = driver.createWait(autoHideDuration);
        wait.until(d -> executeIgnoringStaleElementReference(() -> !this.isDisplayed(), true));
        return wait;
    }

    /**
     * Gets the autoHideDuration in Milliseconds.
     *
     * @return the autoHideDuration in Milliseconds.
     */
    @Nullable
    public Long getAutoHideDuration() {
        return this.autoHideDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiSnackbar)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MuiSnackbar that = (MuiSnackbar) o;
        return Objects.equals(autoHideDuration, that.autoHideDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), autoHideDuration);
    }

    @Override
    public String toString() {
        return "MuiSnackbar{" + "autoHideDuration=" + autoHideDuration + ", element=" + element + '}';
    }
}
