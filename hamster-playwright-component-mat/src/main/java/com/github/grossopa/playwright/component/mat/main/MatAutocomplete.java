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
import com.github.grossopa.playwright.component.mat.action.AutocompleteCloseOptionsAction;
import com.github.grossopa.playwright.component.mat.action.AutocompleteOpenOptionsAction;
import com.github.grossopa.playwright.component.mat.action.CloseOptionsAction;
import com.github.grossopa.playwright.component.mat.action.OpenOptionsAction;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.exception.OptionNotClosedException;
import com.github.grossopa.playwright.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.playwright.component.mat.main.sub.MatOption;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;

import jakarta.annotation.Nullable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * {@code <mat-autocomplete>} is a normal text input with a set of suggested options shown below the input as the user
 * types.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/autocomplete/overview">
 * https://material.angular.io/components/autocomplete/overview</a>
 * @since 1.15
 */
public class MatAutocomplete extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Autocomplete";

    private static final OpenOptionsAction DEFAULT_OPEN_ACTION = new AutocompleteOpenOptionsAction();
    private static final CloseOptionsAction DEFAULT_CLOSE_ACTION = new AutocompleteCloseOptionsAction();

    private final MatOverlayFinder overlayFinder;
    private final String optionSelector;
    private final OpenOptionsAction openOptionsAction;
    private final CloseOptionsAction closeOptionsAction;

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatAutocomplete(Locator locator, ComponentDriver driver, MatConfig config) {
        this(locator, driver, config, null, null, null, null);
    }

    /**
     * Constructs an instance with the delegated locator, root driver and customized overlay finder.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     * @param overlayFinder optional, the customized overlay finder for locating the overlay
     */
    public MatAutocomplete(Locator locator, ComponentDriver driver, MatConfig config,
            @Nullable MatOverlayFinder overlayFinder) {
        this(locator, driver, config, overlayFinder, null, null, null);
    }

    /**
     * Constructs an instance with the delegated locator, root driver, customized overlay finder and option selector.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     * @param overlayFinder optional, the customized overlay finder for locating the overlay
     * @param optionSelector optional, the customized selector for finding the options
     */
    public MatAutocomplete(Locator locator, ComponentDriver driver, MatConfig config,
            @Nullable MatOverlayFinder overlayFinder, @Nullable String optionSelector) {
        this(locator, driver, config, overlayFinder, optionSelector, null, null);
    }

    /**
     * Constructs an instance with all customizable attributes.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     * @param overlayFinder optional, the customized overlay finder for locating the overlay
     * @param optionSelector optional, the customized selector for finding the options
     * @param openOptionsAction optional, the action to open the options
     * @param closeOptionsAction optional, the action to close the options
     */
    public MatAutocomplete(Locator locator, ComponentDriver driver, MatConfig config,
            @Nullable MatOverlayFinder overlayFinder, @Nullable String optionSelector,
            @Nullable OpenOptionsAction openOptionsAction, @Nullable CloseOptionsAction closeOptionsAction) {
        super(locator, driver, config);
        this.overlayFinder = overlayFinder == null ? new MatOverlayFinder(driver, config) : overlayFinder;
        this.optionSelector = optionSelector == null ? config.getTagPrefix() + "option" : optionSelector;
        this.openOptionsAction = openOptionsAction == null ? DEFAULT_OPEN_ACTION : openOptionsAction;
        this.closeOptionsAction = closeOptionsAction == null ? DEFAULT_CLOSE_ACTION : closeOptionsAction;
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "autocomplete-trigger");
    }

    /**
     * Gets the inner input element.
     *
     * @return the inner input element
     */
    public WebComponent getInput() {
        return this.findComponent("input." + config.getCssPrefix() + "autocomplete-trigger");
    }

    /**
     * Opens the options panel and returns it.
     *
     * @return the opened options panel
     * @throws NoSuchElementException if the options panel cannot be located
     */
    public WebComponent openOptions() {
        Optional<WebComponent> autocompletePanel = tryToFindAutocompletePanel();
        if (autocompletePanel.isEmpty()) {
            openOptionsAction.open(this, driver);
            waitForPanelRendering();
            autocompletePanel = tryToFindAutocompletePanel();
        }
        return autocompletePanel.orElseThrow(
                () -> new NoSuchElementException("failed to locate the autocomplete panel."));
    }

    /**
     * Waits briefly for the asynchronous rendering of the autocomplete panel into the overlay.
     * The timeout error is swallowed as the subsequent panel lookup decides the final result.
     */
    private void waitForPanelRendering() {
        try {
            driver.page().waitForSelector("." + config.getCssPrefix() + "autocomplete-panel",
                    new Page.WaitForSelectorOptions().setTimeout(3000));
        } catch (PlaywrightException ex) {
            // panel did not show up within the short timeout
        }
    }

    /**
     * Closes the options panel if it is opened.
     *
     * @throws OptionNotClosedException if the options panel is not properly closed
     */
    public void closeOptions() {
        Optional<WebComponent> autocompletePanel = tryToFindAutocompletePanel();
        if (autocompletePanel.isEmpty()) {
            return;
        }

        List<WebComponent> options = autocompletePanel.get().findComponents(optionSelector);
        closeOptionsAction.close(this, options, driver);

        autocompletePanel = tryToFindAutocompletePanel();
        if (autocompletePanel.isPresent() && autocompletePanel.get().isVisible()) {
            throw new OptionNotClosedException("Autocomplete panel is not properly closed.");
        }
    }

    /**
     * Gets the options with the panel opened.
     *
     * @return the option list
     */
    public List<MatOption> getOptions() {
        return openOptions().findComponents(optionSelector).stream().map(
                c -> new MatOption(c, driver, config)).toList();
    }

    /**
     * Gets all selected options.
     *
     * @return the selected option list
     */
    public List<MatOption> getAllSelectedOptions() {
        return getOptions().stream().filter(MatOption::isSelected).toList();
    }

    /**
     * Gets the first selected option.
     *
     * @return the first selected option or null if nothing is selected
     */
    @Nullable
    public MatOption getFirstSelectedOption() {
        List<MatOption> options = getAllSelectedOptions();
        return options.isEmpty() ? null : options.get(0);
    }

    /**
     * Selects the option by its exact visible text.
     *
     * @param text the option text to select
     */
    public void selectByVisibleText(String text) {
        for (MatOption option : getOptions()) {
            if (text.equals(option.innerText())) {
                option.click();
                return;
            }
        }
    }

    /**
     * Selects the option whose visible text contains the given text.
     *
     * @param text the contained option text to select
     */
    public void selectByContainsVisibleText(String text) {
        for (MatOption option : getOptions()) {
            String optionText = option.innerText();
            if (optionText != null && optionText.contains(text)) {
                option.click();
                return;
            }
        }
    }

    /**
     * Selects the option by its index.
     *
     * @param index the option index to select
     */
    public void selectByIndex(int index) {
        getOptions().get(index).click();
    }

    private Optional<WebComponent> tryToFindAutocompletePanel() {
        List<MatOverlayContainer> containers = overlayFinder.findVisibleContainers();
        if (containers.isEmpty()) {
            return Optional.empty();
        }
        MatOverlayContainer container = containers.get(containers.size() - 1);
        List<WebComponent> panels = container.findComponents("." + config.getCssPrefix() + "autocomplete-panel");
        return panels.isEmpty() ? Optional.empty() : Optional.of(panels.get(0));
    }
}
