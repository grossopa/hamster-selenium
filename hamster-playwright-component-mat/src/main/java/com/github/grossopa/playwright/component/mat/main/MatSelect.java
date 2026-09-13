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
import com.github.grossopa.playwright.component.mat.main.sub.MatOption;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * {@code <mat-select>} is a form control for selecting a value from a list of options, displayed in a floating panel.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/select/overview">
 * https://material.angular.io/components/select/overview</a>
 * @since 1.16
 */
public class MatSelect extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Select";

    private final MatOverlayFinder overlayFinder;

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatSelect(Locator locator, ComponentDriver driver, MatConfig config) {
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
    public MatSelect(Locator locator, ComponentDriver driver, MatConfig config,
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
        return attributeContains(CLASS, config.getComponentCssPrefix() + "select");
    }

    /**
     * Gets the selected value text displayed in the trigger.
     *
     * @return the selected value text
     */
    public String getSelectedValue() {
        return this.findComponent("." + config.getComponentCssPrefix() + "select-value").innerText();
    }

    /**
     * Gets the trigger element.
     *
     * @return the trigger element
     */
    public WebComponent getTrigger() {
        return this.findComponent("." + config.getComponentCssPrefix() + "select-trigger");
    }

    /**
     * Opens the select options panel by clicking the trigger.
     */
    public void openOptions() {
        if (tryToFindSelectPanel().isEmpty()) {
            this.getTrigger().click();
        }
    }

    /**
     * Closes the select options panel by pressing Escape.
     */
    public void closeOptions() {
        if (tryToFindSelectPanel().isPresent()) {
            driver.page().keyboard().press("Escape");
            driver.page().waitForSelector("." + config.getComponentCssPrefix() + "select-panel",
                    new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
        }
    }

    /**
     * Gets all options from the opened panel.
     *
     * @return the list of options
     */
    public List<MatOption> getOptions() {
        WebComponent panel = tryToFindSelectPanel().orElseThrow(
                () -> new IllegalStateException("Select panel is not open. Call openOptions() first."));
        return panel.findComponents(config.getTagPrefix() + "option")
                .stream().map(c -> new MatOption(c, driver, config)).toList();
    }

    /**
     * Selects an option by its visible text.
     *
     * @param text the visible text to match
     */
    public void selectByVisibleText(String text) {
        openOptions();
        List<MatOption> options = getOptions();
        for (MatOption option : options) {
            if (text.equals(option.innerText())) {
                option.click();
                return;
            }
        }
    }

    /**
     * Selects an option by index.
     *
     * @param index the zero-based index
     */
    public void selectByIndex(int index) {
        openOptions();
        getOptions().get(index).click();
    }

    /**
     * Whether the select is in multiple selection mode.
     *
     * @return true if multiple selection is enabled
     */
    public boolean isMultiple() {
        return attributeContains(CLASS, config.getComponentCssPrefix() + "select-multiple");
    }

    protected Optional<WebComponent> tryToFindSelectPanel() {
        // MDC renders the select panel inline within the component rather than in the
        // body-level cdk-overlay-container; search the full page first
        String panelSelector = "." + config.getComponentCssPrefix() + "select-panel";
        List<WebComponent> pagePanels = driver.findComponents(panelSelector);
        List<WebComponent> visiblePanels = pagePanels.stream().filter(WebComponent::isVisible).toList();
        if (!visiblePanels.isEmpty()) {
            return Optional.of(visiblePanels.get(visiblePanels.size() - 1));
        }
        // fall back to the legacy body-level overlay container lookup
        try {
            MatOverlayContainer container = overlayFinder.findTopVisibleContainer();
            if (container != null) {
                List<WebComponent> panels = container.findComponents(panelSelector);
                return panels.isEmpty() ? Optional.empty() : Optional.of(panels.get(panels.size() - 1));
            }
        } catch (Exception ex) {
            // no visible overlay container
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatSelect that)) {
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
