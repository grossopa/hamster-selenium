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
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatOption;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.component.api.DelayedSelect;
import com.github.grossopa.selenium.core.component.api.Select;
import com.github.grossopa.selenium.core.locator.By2;
import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.grossopa.hamster.selenium.component.mat.config.MatConfig.ATTR_CLASS;
import static com.github.grossopa.selenium.core.locator.By2.tagName;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.openqa.selenium.Keys.ESCAPE;

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
                (c, ops, d) -> ((MatAutocomplete) c).getInput().sendKeys(ESCAPE));
    }

    public WebComponent getInput() {
        return this.findComponent(xpathBuilder()
                .anywhereRelative("input").attr(ATTR_CLASS).contains(config.getCssPrefix() + "autocomplete-trigger")
                .build());
    }

    @Override
    public List<WebComponent> getOptions2(Long delayInMillis) {
        Optional<WebComponent> autocompletePanel = tryToFindAutocompletePanel();
        if (autocompletePanel.isEmpty()) {
            openOptionsAction.open(this, driver);
        }


        if (delayInMillis == 0) {
            autocompletePanel = tryToFindAutocompletePanel();
            return autocompletePanel.isEmpty() ? newArrayList() : autocompletePanel.get().findComponents(optionLocator);

        } else {
            return null;
//            return new ArrayList<>(driver.createWait(delayInMillis).until(d -> {
//                List<MatOption> foundOptions = tryFindOptions();
//                if (foundOptions.isEmpty()) {
//                    // let's keep waiting
//                    return null;
//                } else {
//                    return foundOptions;
//                }
//            }));
        }
    }

    @Override
    public List<WebComponent> getAllSelectedOptions2(Long delayInMillis) {
        return null;
    }

    @Override
    public WebComponent openOptions(Long delayInMillis) {
        return null;
    }

    @Override
    public void closeOptions(Long delayInMillis) {

    }

    @Override
    public WebElement getFirstSelectedOption(Long delayInMillis) {
        return null;
    }

    @Override
    public void selectByVisibleText(String text, Long delayInMillis) {

    }

    @Override
    public void selectByIndex(int index, Long delayInMillis) {

    }

    @Override
    public void selectByValue(String value, Long delayInMillis) {

    }

    @Override
    public void deselectAll(Long delayInMillis) {

    }

    @Override
    public void deselectByValue(String value, Long delayInMillis) {

    }

    @Override
    public void deselectByIndex(int index, Long delayInMillis) {

    }

    @Override
    public void deselectByVisibleText(String text, Long delayInMillis) {

    }

    @Override
    public List<WebComponent> getOptions2() {
        return null;
    }

    @Override
    public List<WebComponent> getAllSelectedOptions2() {
        return null;
    }

    @Override
    public WebComponent openOptions() {
        return null;
    }

    @Override
    public void closeOptions() {

    }

    @Override
    public boolean isMultiple() {
        return false;
    }

    @Override
    public WebElement getFirstSelectedOption() {
        return null;
    }

    @Override
    public void selectByVisibleText(String s) {

    }

    @Override
    public void selectByIndex(int i) {

    }

    @Override
    public void selectByValue(String s) {

    }

    @Override
    public void deselectAll() {

    }

    @Override
    public void deselectByValue(String s) {

    }

    @Override
    public void deselectByIndex(int i) {

    }

    @Override
    public void deselectByVisibleText(String s) {

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
