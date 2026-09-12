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
package com.github.grossopa.playwright.component.mat.main;

import com.github.grossopa.playwright.component.mat.AbstractMatComponent;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.utils.component.HasInput;
import com.microsoft.playwright.Locator;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatDatepicker(Locator locator, ComponentDriver driver, MatConfig config) {
        this(locator, driver, config, null);
    }

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     * @param overlayFinder optional, the overlay finder for locating the overlay container
     */
    public MatDatepicker(Locator locator, ComponentDriver driver, MatConfig config,
            @Nullable MatOverlayFinder overlayFinder) {
        super(locator, driver, config);
        this.overlayFinder = (overlayFinder != null) ? overlayFinder : new MatOverlayFinder(driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "datepicker");
    }

    /**
     * Gets the input element associated with the datepicker.
     *
     * @return the input element
     */
    @Override
    public WebComponent getInput() {
        return this.findComponent("input");
    }

    /**
     * Opens the calendar popup by clicking the toggle button.
     */
    public void openCalendar() {
        Optional<WebComponent> content = tryToFindCalendarContent();
        if (content.isEmpty()) {
            this.findComponent("." + config.getCssPrefix() + "datepicker-toggle").click();
        }
    }

    /**
     * Closes the calendar popup.
     */
    public void closeCalendar() {
        driver.page().keyboard().press("Escape");
    }

    /**
     * Gets the calendar component from the overlay.
     *
     * @return the calendar component
     */
    public MatCalendar getCalendar() {
        WebComponent content = tryToFindCalendarContent().orElseThrow(
                () -> new IllegalStateException("Calendar is not open. Call openCalendar() first."));
        WebComponent calendar = content.findComponent("." + config.getCssPrefix() + "calendar");
        return new MatCalendar(calendar.locator(), driver, config);
    }

    protected Optional<WebComponent> tryToFindCalendarContent() {
        MatOverlayContainer container = overlayFinder.findTopVisibleContainer();
        if (container != null) {
            List<WebComponent> panels = container.findComponents("." + config.getCssPrefix() + "datepicker-content");
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
