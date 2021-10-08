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

package com.github.grossopa.selenium.component.mui.v4.finder;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.core.MuiPopover;
import com.github.grossopa.selenium.component.mui.v4.feedback.MuiDialog;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.github.grossopa.selenium.core.component.util.WebComponentUtils.attributeContains;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * The modal locator for locating all the Modal overlay modules.
 *
 * @author Jack Yin
 * @since 1.1
 */
public class MuiModalFinder {

    private final ComponentWebDriver driver;
    private final MuiConfig config;

    /**
     * Constructs an instance with given root driver.
     *
     * @param driver the root driver
     * @param config the Material-UI configuration
     */
    public MuiModalFinder(ComponentWebDriver driver, MuiConfig config) {
        requireNonNull(driver);
        requireNonNull(config);
        this.driver = driver;
        this.config = config;
    }

    /**
     * Finds the all overlays in the root path. Note all overlays existing will be returned including those with {@code
     * keepMounted=true} and not displayed. Note it will NOT be able to find the Modal component.
     *
     * <p>
     * The possible components types are any of {@link MuiDialog}, MuiDrawer, MuiMenu or MuiPopover.
     * </p>
     *
     * @return all the overlay components.
     */
    public List<WebComponent> findOverlays() {
        return findOverlays(config.getModalClasses(), true);
    }

    /**
     * Finds the all overlays by component name in the root path. Note all overlays existing will be returned including
     * those with {@code keepMounted=true} and not displayed. Note it will NOT be able to find the Modal component.
     *
     * <p>
     * The possible components types are any of {@link MuiDialog}, MuiDrawer, MuiMenu or MuiPopover.
     * </p>
     *
     * @param componentName any of @{@link MuiPopover#COMPONENT_NAME}
     * @return all the overlay components.
     */
    public List<WebComponent> findOverlays(String componentName) {
        return findOverlays(Set.of(config.getRootCss(componentName)), true);
    }

    /**
     * Finds the all visible overlays in the root path. Only visible overlays will be returned those without those with
     * {@code keepMounted=true} and not displayed. Note it will NOT be able to find the Modal component.
     *
     * <p>
     * The possible components types are any of {@link MuiDialog}, MuiDrawer, MuiMenu or MuiPopover.
     * </p>
     *
     * @return all the overlay components.
     */
    public List<WebComponent> findVisibleOverlays() {
        return findOverlays(config.getModalClasses(), false);
    }

    /**
     * Finds the all visible overlays by component name in the root path. Only visible overlays will be returned those
     * without those with {@code keepMounted=true} and not displayed. Note it will NOT be able to find the Modal
     * component.
     *
     * <p>
     * The possible components types are any of {@link MuiDialog}, MuiDrawer, MuiMenu or MuiPopover.
     * </p>
     *
     * @param componentName any of @{@link MuiPopover#COMPONENT_NAME}
     * @return all the overlay components.
     */
    public List<WebComponent> findVisibleOverlays(String componentName) {
        return findOverlays(Set.of(config.getRootCss(componentName)), false);
    }

    /**
     * Finds the top visible overlay. Usually it's the interactive-able overlay.
     *
     * @return the found top overlay component or null if no overlays found.
     */
    @Nullable
    public WebComponent findTopVisibleOverlay() {
        List<WebComponent> overlays = findVisibleOverlays();
        return overlays.isEmpty() ? null : overlays.get(overlays.size() - 1);
    }

    /**
     * Finds the top visible overlay by component name. Usually it's the interactive-able overlay.
     *
     * @param componentName any of @{@link MuiPopover#COMPONENT_NAME}
     * @return the found top overlay component or null if no overlays found.
     */
    @Nullable
    public WebComponent findTopVisibleOverlay(String componentName) {
        List<WebComponent> overlays = findVisibleOverlays(componentName);
        return overlays.isEmpty() ? null : overlays.get(overlays.size() - 1);
    }

    /**
     * Finds the overlays by a list of class names and whether includes hidden ones by invoking {@link
     * WebComponent#isDisplayed()} of found overlays.
     *
     * @param classNames the class names to find
     * @param includeHidden whether includes hidden overlays (for some components they will create hidden overlays even
     * not triggered)
     * @return the found overlays
     */
    public List<WebComponent> findOverlays(Set<String> classNames, boolean includeHidden) {
        List<WebComponent> divComponents = driver.findComponents(By.xpath(config.getOverlayAbsolutePath() + "/div"));
        return divComponents.stream().filter(component -> includeHidden || component.isDisplayed())
                .filter(component -> classNames.stream()
                        .anyMatch(modalClass -> attributeContains(component, "class", modalClass))).collect(toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiModalFinder)) {
            return false;
        }
        MuiModalFinder that = (MuiModalFinder) o;
        return driver.equals(that.driver) && config.equals(that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, config);
    }

    @Override
    public String toString() {
        return "MuiModalFinder{" + "driver=" + driver + ", config=" + config + '}';
    }
}
