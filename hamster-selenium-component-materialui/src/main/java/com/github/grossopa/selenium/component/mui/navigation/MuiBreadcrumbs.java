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

package com.github.grossopa.selenium.component.mui.navigation;

import com.github.grossopa.selenium.component.mui.exception.BreadcrumbsAlreadyExpandedException;
import com.github.grossopa.selenium.component.mui.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Breadcrumbs allow users to make selections from a range of values.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/breadcrumbs/">
 * https://material-ui.com/components/breadcrumbs/</a>
 * @since 1.0
 */
public class MuiBreadcrumbs extends AbstractMuiComponent {

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiBreadcrumbs(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "Breadcrumbs";
    }

    /**
     * Finds the breadcrumb item by index. note if it's collapsed then only includes the visible items.
     *
     * @param index the index of the breadcrumb
     * @return the found item
     */
    public WebComponent getItemAt(int index) {
        return this.getItems().get(index);
    }

    /**
     * Finds the breadcrumb items. note if it's collapsed then only includes the visible items.
     *
     * @return the found items
     */
    public List<WebComponent> getItems() {
        return this.findComponents(getItemContainersLocator()).stream()
                .map(component -> component.findComponent(By.xpath(".//*"))).collect(toList());
    }

    /**
     * Whether one of the item is MuiTouchRipple-root. Due to lack of information of other items it requires a click
     * event on the TouchRipple item in order to expand other hidden items.
     *
     * @return true if the breadcrumb is collapsed
     */
    public boolean isCollapsed() {
        return !this.findComponents(getTouchRippleLocator()).isEmpty();
    }

    /**
     * Finds all TouchRipples components and click each one. (Usually should be only one component). Throws {@link
     * BreadcrumbsAlreadyExpandedException} when there is no such TouchRipple component.
     */
    public void expand() {
        List<WebComponent> touchRipples = this.findComponents(getTouchRippleLocator());
        if (touchRipples.isEmpty()) {
            throw new BreadcrumbsAlreadyExpandedException(String.format("breadcrumb %s is already expanded.", this));
        }

        touchRipples.forEach(touchRipple -> touchRipple.findComponent(By.xpath("parent::*")).click());
    }

    /**
     * Finds all separators.
     *
     * @return all separators
     */
    public List<WebComponent> getSeparators() {
        return this.findComponents(getSeparatorLocator());
    }

    private By getSeparatorLocator() {
        return By2.className(config.getCssPrefix() + "Breadcrumbs-separator");
    }

    private By getTouchRippleLocator() {
        return By2.className(config.getCssPrefix() + "TouchRipple-root");
    }

    private By getItemContainersLocator() {
        return By2.className(config.getCssPrefix() + "Breadcrumbs-li");
    }
}
