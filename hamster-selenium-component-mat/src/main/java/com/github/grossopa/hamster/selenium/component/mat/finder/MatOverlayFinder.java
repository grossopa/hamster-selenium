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

package com.github.grossopa.hamster.selenium.component.mat.finder;

import com.github.grossopa.hamster.selenium.component.mat.main.MatOverlayContainer;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.openqa.selenium.By.xpath;

/**
 * Locates the global overlays.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatOverlayFinder {

    private final ComponentWebDriver driver;
    private final MatConfig config;

    /**
     * Constructs an instance with given config.
     *
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatOverlayFinder(ComponentWebDriver driver, MatConfig config) {
        requireNonNull(driver);
        requireNonNull(config);
        this.driver = driver;
        this.config = config;
    }

    /**
     * Gets the configuration
     *
     * @return the configuration
     */
    public MatConfig getConfig() {
        return config;
    }

    public List<MatOverlayContainer> findContainers() {
        return driver.findComponentsAs(
                xpath(config.getOverlayAbsolutePath() +
                        String.format("/div[contains(@class,'%soverlay-container')]", config.getCdkPrefix())),
                element -> new MatOverlayContainer(element, driver, config));
    }

    public List<MatOverlayContainer> findVisibleContainers() {
        return findContainers().stream().filter(WebComponent::isDisplayed).collect(toList());
    }

    @Nullable
    public MatOverlayContainer findTopVisibleContainer() {
        List<MatOverlayContainer> containers = findContainers();
        return containers.isEmpty() ? null : containers.get(containers.size() - 1);
    }
}
