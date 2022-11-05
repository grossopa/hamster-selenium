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

package com.github.grossopa.selenium.component.mui.v4.lab;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.action.CloseOptionsAction;
import com.github.grossopa.selenium.component.mui.action.OpenOptionsAction;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.exception.OptionNotClosedException;
import com.github.grossopa.selenium.component.mui.v4.finder.MuiModalFinder;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
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
import java.util.*;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The autocomplete is a normal text input enhanced by a panel of suggested options.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/autocomplete/">https://material-ui.com/components/autocomplete/</a>
 * @since 1.3
 */
public class MuiAutocomplete extends AbstractMuiComponent implements Select, DelayedSelect {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Autocomplete";

    /**
     * The inner input component name
     */
    public static final String INPUT_NAME = "Autocomplete-inputRoot";

    private final MuiModalFinder modalFinder;
    private final By optionLocator;
    private final OpenOptionsAction openOptionsAction;
    private final CloseOptionsAction closeOptionsAction;
    private final MuiAutocompleteTagLocators tagLocators;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiAutocomplete(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, null, null, null, null);
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
        this(element, driver, config, optionLocator, null, null, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param optionLocator optional, the locator for finding the option
     * @param tagLocators optional for the multiple value tag locators
     */
    public MuiAutocomplete(WebElement element, ComponentWebDriver driver, MuiConfig config, @Nullable By optionLocator,
            @Nullable MuiAutocompleteTagLocators tagLocators) {
        this(element, driver, config, optionLocator, tagLocators, null, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param optionLocator optional, the locator for finding the option
     * @param tagLocators optional for the multiple value tag locators
     * @param openOptionsAction the action to open the options
     * @param closeOptionsAction the action to close the options
     */
    public MuiAutocomplete(WebElement element, ComponentWebDriver driver, MuiConfig config, @Nullable By optionLocator,
            @Nullable MuiAutocompleteTagLocators tagLocators, @Nullable OpenOptionsAction openOptionsAction,
            @Nullable CloseOptionsAction closeOptionsAction) {
        super(element, driver, config);
        this.modalFinder = new MuiModalFinder(driver, config);
        this.optionLocator = defaultIfNull(optionLocator, By.className(config.getCssPrefix() + "Autocomplete-option"));
        this.tagLocators = defaultIfNull(tagLocators, MuiAutocompleteTagLocators.chipLocators(config));
        this.openOptionsAction = defaultIfNull(openOptionsAction, (component, d) -> getInput().click());
        this.closeOptionsAction = defaultIfNull(closeOptionsAction,
                (component, options, d) -> getInput().sendKeys(Keys.ESCAPE));
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5);
    }

    @Override
    public boolean isEnabled() {
        return !config.isDisabled(this.findComponent(By.className(config.getCssPrefix() + INPUT_NAME)));
    }

    /**
     * Whether the AutoComplete is readonly or not.
     *
     * @return the AutoComplete is readonly or not.
     */
    public boolean isReadOnly() {
        return SeleniumUtils.isTrueAttribute(getInput(), "readonly");
    }

    /**
     * Whether the autocomplete is loading.
     *
     * @return true if the autocomplete is loading the data from backend.
     */
    public boolean isLoading() {
        WebComponent overlay = this.tryLocateOverlay();
        if (overlay != null) {
            List<WebComponent> loadings = overlay.findComponents(
                    By.className(config.getCssPrefix() + "Autocomplete-loading"));
            return !loadings.isEmpty() && loadings.get(0).isDisplayed();
        }
        return false;
    }

    /**
     * Finds the input component within the Autocomplete.
     *
     * @return the input component within the Autocomplete.
     */
    public WebComponent getLabel() {
        return this.findComponent(By.tagName("label"));
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

    /**
     * Finds the popup button, note the button might be not interactable if it's not shown.
     *
     * @return the popup button
     */
    public MuiButton getPopupButton() {
        return findComponentAs(By.className(config.getCssPrefix() + "Autocomplete-popupIndicator"),
                c -> new MuiButton(c, driver, config));
    }

    @Override
    public List<WebComponent> getOptions2() {
        WebComponent overlay = openOptions();
        return overlay.findComponents(optionLocator);
    }

    @Override
    public List<WebComponent> getOptions2(Long delayInMillis) {
        WebComponent overlay = openOptions(delayInMillis);
        return overlay.findComponents(optionLocator);
    }

    /**
     * The selected options within the body, this method will only work under Multiple values model.
     *
     * @return the selected {@link MuiAutocompleteTag} components.
     */
    @Override
    public List<WebComponent> getAllSelectedOptions2() {
        return new ArrayList<>(getVisibleTags());
    }

    /**
     * Gets the selected Options within the body, however the delay is not necessary as the tags are always displaying.
     * use {@link #getAllSelectedOptions2()} instead.
     *
     * @param delayInMillis no-use, the delays in milliseconds
     * @return the found tags
     */
    @Override
    public List<WebComponent> getAllSelectedOptions2(Long delayInMillis) {
        return getAllSelectedOptions2();
    }


    @Override
    public WebComponent openOptions() {
        return openOptions(0L);
    }

    @Override
    public WebComponent openOptions(Long delayInMillis) {
        WebComponent overlay = tryLocateOverlay();
        if (overlay != null) {
            return overlay;
        }

        WebComponent input = this.getInput();
        if (!input.isFocused()) {
            // Make sure the input is focused so we have only one overlay there
            driver.moveTo(input);
        }

        this.openOptionsAction.open(this, driver);

        if (delayInMillis > 0L) {
            WebDriverWait wait = driver.createWait(delayInMillis);
            overlay = wait.until(d -> tryLocateOverlay());
        } else {
            overlay = tryLocateOverlay();
        }

        if (overlay == null) {
            throw new NoSuchElementException("Failed to open overlay for element:" + this);
        }
        return overlay;
    }

    @Override
    public void closeOptions() {
        closeOptions(0L);
    }

    @Override
    public void closeOptions(Long delayInMillis) {
        WebComponent overlay = tryLocateOverlay();
        if (overlay == null) {
            return;
        }

        closeOptionsAction.close(this, getOptions2(), driver);
        if (delayInMillis > 0L) {
            WebDriverWait wait = driver.createWait(delayInMillis);
            wait.until(d -> tryLocateOverlay() == null);
        } else {
            overlay = tryLocateOverlay();
            if (overlay != null && overlay.isDisplayed()) {
                throw new OptionNotClosedException("Autocomplete Popover is not properly closed");
            }
        }
    }

    @Override
    public boolean isMultiple() {
        return true;
    }

    @Override
    public WebElement getFirstSelectedOption() {
        return getFirstSelectedOption(0L);
    }

    @Override
    public WebElement getFirstSelectedOption(Long delayInMillis) {
        List<WebComponent> selectedOptions = getAllSelectedOptions2(delayInMillis);
        return selectedOptions.isEmpty() ? null : selectedOptions.get(0);
    }

    @Override
    public void selectByVisibleText(String text) {
        selectByVisibleText(text, 0L);
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
    public void selectByIndex(int index) {
        selectByIndex(index, 0L);
    }

    @Override
    public void selectByIndex(int index, Long delayInMillis) {
        WebComponent component = getOptions2(delayInMillis).get(index);
        component.click();
    }

    @Override
    public void selectByValue(String value) {
        selectByVisibleText(value);
    }

    @Override
    public void selectByValue(String value, Long delayInMillis) {
        selectByVisibleText(value, delayInMillis);
    }

    @Override
    @SuppressWarnings("java:S6212")
    public void deselectAll() {
        int index = 0;
        List<MuiAutocompleteTag> options;
        // refreshes the visible tags for each while loop as all components will be recreated after removing an element.
        while ((options = getVisibleTags()).size() > index) {
            if (!options.get(index).isEnabled()) {
                // skip disabled
                index++;
                continue;
            }
            options.get(index).getDeleteButton().click();
        }
    }


    @Override
    public void deselectAll(Long delayInMillis) {
        deselectAll();
    }

    @Override
    public void deselectByValue(String value) {
        List<MuiAutocompleteTag> options = getVisibleTags();
        for (MuiAutocompleteTag option : options) {
            if (StringUtils.equals(value, option.getValue())) {
                option.getDeleteButton().click();
                // return here as all components will be recreated after removing the element that caused
                // the options are not valid after deletion. A potential side effect is that the deselect action
                // will only remove one element (e.g. duplicated value).
                return;
            }
        }
    }


    @Override
    public void deselectByValue(String value, Long delayInMillis) {
        deselectByValue(value);
    }

    @Override
    public void deselectByIndex(int index) {
        List<MuiAutocompleteTag> options = getVisibleTags();
        options.get(index).getDeleteButton().click();
    }

    @Override
    public void deselectByIndex(int index, Long delayInMillis) {
        deselectByIndex(index);
    }

    @Override
    public void deselectByVisibleText(String text) {
        List<MuiAutocompleteTag> options = getVisibleTags();
        for (MuiAutocompleteTag option : options) {
            if (StringUtils.equals(text, option.getLabel())) {
                option.getDeleteButton().click();
                // return here as all components will be recreated after removing the element that caused
                // the options are not valid after deletion. A potential side effect is that the deselect action
                // will only remove one element (e.g. duplicated visible text).
                return;
            }
        }
    }

    @Override
    public void deselectByVisibleText(String text, Long delayInMillis) {
        deselectByVisibleText(text);
    }

    /**
     * Whether there is no options.
     *
     * @return true if there is no options and "No Options" is shown.
     */
    public boolean isNoOptions() {
        WebComponent overlay = this.openOptions();
        List<WebComponent> noOptions = overlay.findComponents(
                By.className(config.getCssPrefix() + "Autocomplete-noOptions"));
        return !noOptions.isEmpty();
    }

    @Nullable
    private WebComponent tryLocateOverlay() {
        List<WebComponent> overlays = modalFinder.findOverlays(Set.of(config.getCssPrefix() + "Autocomplete-popper"),
                false);
        if (overlays.isEmpty()) {
            // try to locate the next sibling if disablePortal is set to true
            overlays = this.findComponents(By2.axesBuilder().followingSibling("div").attr(CLASS)
                            .contains(config.getCssPrefix() + "Autocomplete-popperDisablePortal").build()
                    //        By.xpath("following-sibling::div[contains(@class,\"MuiAutocomplete-popperDisablePortal\")]")
            );
        }
        return overlays.isEmpty() ? null : overlays.get(0);
    }

    private List<MuiAutocompleteTag> getVisibleTags() {
        return this.findComponent(By.className(config.getCssPrefix() + INPUT_NAME))
                .findComponentsAs(By.className(config.getCssPrefix() + MuiAutocompleteTag.COMPONENT_NAME),
                        c -> new MuiAutocompleteTag(c, driver, config, tagLocators));
    }

    /**
     * Gets the modal finder for locating the popup modal / overlay.
     *
     * @return the modal finder instance.
     */
    public MuiModalFinder getModalFinder() {
        return modalFinder;
    }

    /**
     * Gets the option locator.
     *
     * @return the option locator.
     */
    public By getOptionLocator() {
        return optionLocator;
    }

    /**
     * Gets the action for open the options.
     *
     * @return the action for open the options.
     */
    public OpenOptionsAction getOpenOptionsAction() {
        return openOptionsAction;
    }

    /**
     * Gets the action for closing the options.
     *
     * @return the action for closing the options.
     */
    public CloseOptionsAction getCloseOptionsAction() {
        return closeOptionsAction;
    }

    /**
     * Gets the locators for tag component.
     *
     * @return the locators for tag component.
     */
    public MuiAutocompleteTagLocators getTagLocators() {
        return tagLocators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiAutocomplete)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MuiAutocomplete that = (MuiAutocomplete) o;
        return modalFinder.equals(that.modalFinder) && optionLocator.equals(that.optionLocator)
                && openOptionsAction.equals(that.openOptionsAction) && closeOptionsAction.equals(
                that.closeOptionsAction) && tagLocators.equals(that.tagLocators);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), modalFinder, optionLocator, openOptionsAction, closeOptionsAction,
                tagLocators);
    }

    @Override
    public String toString() {
        return "MuiAutocomplete{" + "modalFinder=" + modalFinder + ", optionLocator=" + optionLocator
                + ", openOptionsAction=" + openOptionsAction + ", closeOptionsAction=" + closeOptionsAction
                + ", tagLocators=" + tagLocators + '}';
    }
}
