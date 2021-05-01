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

package com.github.grossopa.selenium.component.mui.lab;

import com.github.grossopa.selenium.component.mui.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.action.CloseOptionsAction;
import com.github.grossopa.selenium.component.mui.action.DefaultOpenOptionsAction;
import com.github.grossopa.selenium.component.mui.action.OpenOptionsAction;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.finder.MuiModalFinder;
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.component.api.Select;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The autocomplete is a normal text input enhanced by a panel of suggested options.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/autocomplete/">https://material-ui.com/components/autocomplete/</a>
 * @since 1.3
 */
public class MuiAutocomplete extends AbstractMuiComponent implements Select {

    public static final String NAME = "Autocomplete";

    private final MuiModalFinder modalFinder;
    private final By optionLocator;
    private final OpenOptionsAction openOptionsAction;
    private final CloseOptionsAction closeOptionsAction;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiAutocomplete(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, null, null, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param optionLocator optional, the locator for finding the option
     */
    public MuiAutocomplete(WebElement element, ComponentWebDriver driver, MuiConfig config,
            @Nullable By optionLocator) {
        this(element, driver, config, optionLocator, null, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param optionLocator optional, the locator for finding the option
     * @param openOptionsAction the action to open the options
     * @param closeOptionsAction the action to close the options
     */
    public MuiAutocomplete(WebElement element, ComponentWebDriver driver, MuiConfig config, @Nullable By optionLocator,
            OpenOptionsAction openOptionsAction, CloseOptionsAction closeOptionsAction) {
        super(element, driver, config);
        this.modalFinder = new MuiModalFinder(driver, config);
        this.optionLocator = defaultIfNull(optionLocator, By.className(config.getCssPrefix() + "Autocomplete-option"));
        this.openOptionsAction = defaultIfNull(openOptionsAction, new DefaultOpenOptionsAction());
        this.closeOptionsAction = defaultIfNull(closeOptionsAction, (component, options, d) -> getInput().sendKeys(Keys.ESCAPE));
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    /**
     * Finds the input component within the Autocomplete.
     *
     * @return the input component within the Autocomplete.
     */
    public WebComponent getInput() {
        return this.findComponent(By.tagName("input"));
    }

    /**
     * Finds the clear button, note the button might be not interactable if it's not shown.
     *
     * @return the clear button
     */
    public MuiButton getClearButton() {
        return findComponentAs(By.className(config.getCssPrefix() + "Autocomplete-clearIndicator"),
                c -> new MuiButton(c, driver, config));
    }

    @Override
    public List<WebComponent> getOptions2() {
        WebComponent overlay = openOptions();
        return overlay.findComponents(optionLocator);
    }

    @Override
    public List<WebComponent> getAllSelectedOptions2() {
        return null;
    }

    @Override
    public WebComponent openOptions() {
        WebComponent overlay = tryGetOverlay();
        if (overlay == null) {
            WebComponent input = getInput();
            input.click();
        }
        overlay = tryGetOverlay();
        if (overlay == null) {
            throw new NoSuchElementException("Cannot locate the options.");
        }
        return overlay;
    }

    @Override
    public void closeOptions() {
        WebComponent overlay = tryGetOverlay();
        if (overlay != null) {
            closeOptionsAction.close(this, overlay.findComponents(optionLocator), driver);
        }
    }

    @Override
    public boolean isMultiple() {
        return false;
    }

    @Override
    public List<WebElement> getOptions() {
        WebComponent overlay = openOptions();
        return overlay.findElements(optionLocator);
    }

    @Override
    public List<WebElement> getAllSelectedOptions() {
        return null;
    }

    @Override
    public WebElement getFirstSelectedOption() {
        return null;
    }

    @Override
    public void selectByVisibleText(String text) {
        List<WebComponent> options = getOptions2();
        for (WebComponent option : options) {
            if (StringUtils.equals(text, option.getText())) {
                option.click();
                return;
            }
        }
    }

    @Override
    public void selectByIndex(int index) {
        WebComponent component = getOptions2().get(index);
        component.click();
    }

    @Override
    public void selectByValue(String value) {
        selectByVisibleText(value);
    }

    @Override
    public void deselectAll() {

    }

    @Override
    public void deselectByValue(String value) {

    }

    @Override
    public void deselectByIndex(int index) {

    }

    @Override
    public void deselectByVisibleText(String text) {

    }

    @Nullable
    private WebComponent tryGetOverlay() {
        List<WebComponent> overlays = modalFinder
                .findOverlays(Set.of(config.getCssPrefix() + "Autocomplete-popper"), false);
        return overlays.isEmpty() ? null : overlays.get(0);
    }

}
