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

package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.action.CloseOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.action.OpenOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.exception.OptionNotClosedException;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.component.api.DelayedSelect;
import com.github.grossopa.selenium.core.component.api.Select;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.core.util.SeleniumUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static com.github.grossopa.hamster.selenium.component.mat.config.MatConfig.ATTR_CLASS;
import static com.github.grossopa.selenium.core.locator.By2.tagName;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The autocomplete is a normal text input enhanced by a panel of suggested options.
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/autocomplete/overview">
 *         https://material.angular.io/components/autocomplete/overview</a>
 * @since 1.6
 */
public class MatAutocomplete extends AbstractMatComponent implements Select, DelayedSelect {

    private final MatOverlayFinder overlayFinder;
    private final By optionLocator;
    private final OpenOptionsAction openOptionsAction;
    private final CloseOptionsAction closeOptionsAction;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatAutocomplete(WebElement element, ComponentWebDriver driver, MatConfig config) {
        this(element, driver, config, null, null, null, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     * @param overlayFinder optional, the overlay finder for locating the overlay container
     */
    public MatAutocomplete(WebElement element, ComponentWebDriver driver, MatConfig config,
                           @Nullable MatOverlayFinder overlayFinder) {
        this(element, driver, config, overlayFinder, null, null, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     * @param overlayFinder optional, the overlay finder for locating the overlay container
     * @param optionLocator optional, the option locator for locating the options within the overlay container
     */
    public MatAutocomplete(WebElement element, ComponentWebDriver driver, MatConfig config,
                           @Nullable MatOverlayFinder overlayFinder, @Nullable By optionLocator) {
        this(element, driver, config, overlayFinder, optionLocator, null, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     * @param overlayFinder optional, the overlay finder for locating the overlay container
     * @param optionLocator optional, the option locator for locating the options within the overlay container
     * @param openOptionsAction optional, the action to open the option locator
     * @param closeOptionsAction optional, the action to close the option locator
     */
    public MatAutocomplete(WebElement element, ComponentWebDriver driver, MatConfig config,
                           @Nullable MatOverlayFinder overlayFinder, @Nullable By optionLocator,
                           @Nullable OpenOptionsAction openOptionsAction,
                           @Nullable CloseOptionsAction closeOptionsAction) {
        super(element, driver, config);
        this.overlayFinder = defaultIfNull(overlayFinder, new MatOverlayFinder(driver, config));
        this.optionLocator = defaultIfNull(optionLocator, tagName(config.getTagPrefix() + "option"));
        this.openOptionsAction = defaultIfNull(openOptionsAction, (c, d) -> ((MatAutocomplete) c).getInput().click());
        this.closeOptionsAction = defaultIfNull(closeOptionsAction,
                (c, ops, d) -> ((MatAutocomplete) c).getInput().sendKeys(Keys.ESCAPE));
    }

    public WebComponent getInput() {
        return this.findComponent(xpathBuilder()
                .anywhereRelative("input").attr(ATTR_CLASS).contains(config.getCssPrefix() + "autocomplete-trigger")
                .build());
    }

    @Override
    public List<WebComponent> getOptions2(Long delayInMillis) {
        return newArrayList(openOptions(delayInMillis).findComponents(optionLocator));
    }

    @Override
    public List<WebComponent> getAllSelectedOptions2(Long delayInMillis) {
        return getOptions2().stream().filter(WebElement::isSelected).collect(toList());
    }

    @Override
    public WebComponent openOptions(Long delayInMillis) {
        Optional<WebComponent> autocompletePanel = tryToFindAutocompletePanel();
        if (autocompletePanel.isEmpty()) {
            openOptionsAction.open(this, driver);
        }

        if (delayInMillis <= 0) {
            autocompletePanel = tryToFindAutocompletePanel();
        } else {
            autocompletePanel = Optional.of(
                    driver.createWait(delayInMillis).until(d -> tryToFindAutocompletePanel().orElse(null)));
        }
        return autocompletePanel
                .orElseThrow(() -> new NoSuchElementException("failed to locate the  autocomplete panel."));
    }

    @Override
    public void closeOptions(Long delayInMillis) {
        Optional<WebComponent> autocompletePanel = tryToFindAutocompletePanel();
        if (autocompletePanel.isEmpty()) {
            return;
        }

        List<WebComponent> options = autocompletePanel.get().findComponents(optionLocator);

        closeOptionsAction.close(this, options, driver);
        if (delayInMillis <= 0) {
            autocompletePanel = tryToFindAutocompletePanel();
            if (autocompletePanel.isPresent() && autocompletePanel.get().isDisplayed()) {
                throw new OptionNotClosedException("Autocomplete panel is not properly closed.");
            }
        } else {
            WebDriverWait wait = driver.createWait(delayInMillis);
            wait.until(d -> {
                Optional<WebComponent> temp = tryToFindAutocompletePanel();
                return temp.isEmpty() || !temp.get().isDisplayed();
            });
        }
    }

    @Override
    public WebElement getFirstSelectedOption(Long delayInMillis) {
        List<WebComponent> components = getAllSelectedOptions2(delayInMillis);
        return components.isEmpty() ? null : components.get(0);
    }

    @Override
    public void selectByVisibleText(String text, Long delayInMillis) {
        List<WebComponent> options = getOptions2(delayInMillis);
        for (WebComponent option : options) {
            if (StringUtils.equals(text, option.getText())) {
                option.click();
                return;
            }
        }
    }

    @Override
    public void selectByIndex(int index, Long delayInMillis) {
        getOptions2(delayInMillis).get(index).click();
    }

    @Override
    public void selectByValue(String value, Long delayInMillis) {
        selectByVisibleText(value, delayInMillis);
    }

    @Override
    public void deselectAll(Long delayInMillis) {
        SeleniumUtils.cleanText(this.getInput());
    }

    @Override
    public void deselectByValue(String value, Long delayInMillis) {
        SeleniumUtils.cleanText(this.getInput());
    }

    @Override
    public void deselectByIndex(int index, Long delayInMillis) {
        SeleniumUtils.cleanText(this.getInput());
    }

    @Override
    public void deselectByVisibleText(String text, Long delayInMillis) {
        SeleniumUtils.cleanText(this.getInput());
    }

    @Override
    public List<WebComponent> getOptions2() {
        return getOptions2(0L);
    }

    @Override
    public List<WebComponent> getAllSelectedOptions2() {
        return getAllSelectedOptions2(0L);
    }

    @Override
    public WebComponent openOptions() {
        return openOptions(0L);
    }

    @Override
    public void closeOptions() {
        closeOptions(0L);
    }

    @Override
    public boolean isMultiple() {
        return false;
    }

    @Override
    public WebElement getFirstSelectedOption() {
        return getFirstSelectedOption(0L);
    }

    @Override
    public void selectByVisibleText(String text) {
        selectByVisibleText(text, 0L);
    }

    @Override
    public void selectByIndex(int i) {
        selectByIndex(i, 0L);
    }

    @Override
    public void selectByValue(String value) {
        selectByValue(value, 0L);
    }

    @Override
    public void deselectAll() {
        deselectAll(0L);
    }

    @Override
    public void deselectByValue(String value) {
        deselectByValue(value, 0L);
    }

    @Override
    public void deselectByIndex(int i) {
        deselectByIndex(i, 0L);
    }

    @Override
    public void deselectByVisibleText(String visibleText) {
        deselectByVisibleText(visibleText, 0L);
    }

    protected Optional<WebComponent> tryToFindAutocompletePanel() {
        MatOverlayContainer container = overlayFinder.findTopVisibleContainer();
        if (container != null) {
            List<WebComponent> panels =
                    container.findComponents(By2.className(config.getCssPrefix() + "autocomplete-panel"));
            return panels.isEmpty() ? Optional.empty() : Optional.of(panels.get(panels.size() - 1));
        }
        return Optional.empty();
    }
}
