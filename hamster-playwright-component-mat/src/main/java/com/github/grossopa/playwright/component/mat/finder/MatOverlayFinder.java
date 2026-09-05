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
package com.github.grossopa.playwright.component.mat.finder;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.main.MatOverlayContainer;
import com.github.grossopa.playwright.core.ComponentDriver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Finds the overlay containers that host the Angular Material overlay contents such as autocomplete panels and
 * menus.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class MatOverlayFinder {

    protected final ComponentDriver driver;

    protected final MatConfig config;

    /**
     * Constructs an instance with the root driver and configuration.
     *
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatOverlayFinder(ComponentDriver driver, MatConfig config) {
        this.driver = requireNonNull(driver);
        this.config = requireNonNull(config);
    }

    /**
     * Gets the Material UI Angular configuration.
     *
     * @return the Material UI Angular configuration
     */
    public MatConfig getConfig() {
        return config;
    }

    /**
     * Finds all overlay containers.
     *
     * @return the list of all overlay containers
     */
    public List<MatOverlayContainer> findContainers() {
        return driver.findComponentsAs("xpath=" + config.getOverlayAbsolutePath() + String.format(
                "/div[contains(@class,'%soverlay-container')]", config.getCdkPrefix()),
                c -> new MatOverlayContainer(c, driver, config));
    }

    /**
     * Finds all visible overlay containers.
     *
     * @return the list of all visible overlay containers
     */
    public List<MatOverlayContainer> findVisibleContainers() {
        return findContainers().stream().filter(MatOverlayContainer::isVisible).toList();
    }

    /**
     * Finds the top visible overlay container.
     *
     * @return the top visible overlay container
     * @throws NoSuchElementException if there is no visible overlay container
     */
    public MatOverlayContainer findTopVisibleContainer() {
        List<MatOverlayContainer> containers = findVisibleContainers();
        if (containers.isEmpty()) {
            throw new NoSuchElementException("failed to find any visible overlay container.");
        }
        return containers.get(containers.size() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatOverlayFinder)) {
            return false;
        }
        MatOverlayFinder that = (MatOverlayFinder) o;
        return driver.equals(that.driver) && config.equals(that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, config);
    }

    @Override
    public String toString() {
        return "MatOverlayFinder{" + "driver=" + driver + ", config=" + config + '}';
    }
}
