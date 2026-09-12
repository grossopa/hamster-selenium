/*
 * Copyright © 2021 the original author or authors.
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
package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.utils.component.HasInput;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jakarta.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;
import static org.apache.commons.lang3.ObjectUtils.getIfNull;

/**
 * {@code <mat-datepicker>} provides a calendar widget for selecting dates, typically paired with an input field.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/datepicker/overview">
 * https://material.angular.io/components/datepicker/overview</a>
 * @since 1.16
 */
public class MatDatepicker extends AbstractMatComponent implements HasInput<WebComponent> {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Datepicker";

    private final MatOverlayFinder overlayFinder;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatDatepicker(WebElement element, ComponentWebDriver driver, MatConfig config) {
        this(element, driver, config, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     * @param overlayFinder optional, the overlay finder for locating the overlay container
     */
    public MatDatepicker(WebElement element, ComponentWebDriver driver, MatConfig config,
            @Nullable MatOverlayFinder overlayFinder) {
        super(element, driver, config);
        this.overlayFinder = getIfNull(overlayFinder, () -> new MatOverlayFinder(driver, config));
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "datepicker");
    }

    /**
     * Gets the input element associated with the datepicker.
     *
     * @return the input element
     */
    @Override
    public WebComponent getInput() {
        return this.findComponent(By.tagName("input"));
    }

    /**
     * Opens the calendar popup by clicking the toggle button.
     */
    public void openCalendar() {
        Optional<WebComponent> content = tryToFindCalendarContent();
        if (content.isEmpty()) {
            this.findComponent(By.className(config.getCssPrefix() + "datepicker-toggle")).click();
        }
    }

    /**
     * Closes the calendar popup.
     */
    public void closeCalendar() {
        this.sendKeys(org.openqa.selenium.Keys.ESCAPE);
    }

    /**
     * Gets the calendar component from the overlay.
     *
     * @return the calendar component
     */
    public MatCalendar getCalendar() {
        WebComponent content = tryToFindCalendarContent().orElseThrow(
                () -> new IllegalStateException("Calendar is not open. Call openCalendar() first."));
        WebComponent calendar = content.findComponent(By.className(config.getCssPrefix() + "calendar"));
        return new MatCalendar(calendar, driver, config);
    }

    protected Optional<WebComponent> tryToFindCalendarContent() {
        MatOverlayContainer container = overlayFinder.findTopVisibleContainer();
        if (container != null) {
            var panels = container.findComponents(By.className(config.getCssPrefix() + "datepicker-content"));
            return panels.isEmpty() ? Optional.empty() : Optional.of(panels.get(panels.size() - 1));
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatDatepicker that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return overlayFinder.equals(that.overlayFinder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), overlayFinder);
    }
}
