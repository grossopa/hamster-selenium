/*
 * Copyright © 2020 the original author or authors.
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

package org.hamster.selenium.component.mui;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.hamster.selenium.component.mui.action.CloseOptionsAction;
import org.hamster.selenium.component.mui.action.OpenOptionsAction;
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.component.mui.exception.OptionNotClosedException;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.hamster.selenium.core.component.api.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * A MUI Select wrapper which supports the Popover-based options.
 * <p>
 * The options are customized via a layer called popover which is in front of the page. hence there are no direct
 * relationship between the component and options. In order to treat them still as one component, additional operations
 * for opening the options are required before selecting an item.
 * </p>
 * <p>
 * And, options show and hide usually requires a wait time for the animation, which introduce a bit more complexity for
 * the framework to allow a wait time before actions such as {@link #getOptions2(Long)}. However, once the options are
 * displayed, next operation doesn't necessarily require a wait time, e.g. another method could be invoked {@link
 * #getOptions2()} and immediately gets all the displayed items.
 * </p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/selects/">
 * https://material-ui.com/components/selects/</a>
 * @since 1.0
 */
public class MuiSelect extends AbstractMuiComponent implements Select {

    /**
     * The default action for opening the options
     */
    public static final OpenOptionsAction DEFAULT_OPEN_OPTIONS_ACTION = (component, driver) -> component.click();

    /**
     * The default action for closing the options
     */
    public static final CloseOptionsAction DEFAULT_CLOSE_OPTIONS_ACTION = (component, options, driver) -> options.get(0)
            .sendKeys(Keys.ESCAPE);

    private final String optionValueAttribute;
    private final By optionsLocator;
    private final OpenOptionsAction openOptionsAction;
    private final CloseOptionsAction closeOptionsAction;

    /**
     * Constructs an instance with the delegated element and root driver.
     *
     * <p>both Open option and Close option are default.</p>
     *
     * @param element
     *         the delegated element
     * @param driver
     *         the root driver
     * @param config
     *         the Material UI configuration
     * @param optionsLocator
     *         the locator for finding the options
     */
    public MuiSelect(WebElement element, ComponentWebDriver driver, MuiConfig config, By optionsLocator) {
        this(element, driver, config, optionsLocator, "data-value", DEFAULT_OPEN_OPTIONS_ACTION,
                DEFAULT_CLOSE_OPTIONS_ACTION);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element
     *         the delegated element
     * @param driver
     *         the root driver
     * @param config
     *         the Material UI configuration
     * @param optionsLocator
     *         the locator for finding the options
     * @param optionValueAttribute
     *         the value attribute of each option item
     */
    public MuiSelect(WebElement element, ComponentWebDriver driver, MuiConfig config, By optionsLocator,
            String optionValueAttribute) {
        this(element, driver, config, optionsLocator, optionValueAttribute, DEFAULT_OPEN_OPTIONS_ACTION,
                DEFAULT_CLOSE_OPTIONS_ACTION);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element
     *         the delegated element
     * @param driver
     *         the root driver
     * @param config
     *         the Material UI configuration
     * @param optionsLocator
     *         the locator for finding the options
     * @param optionValueAttribute
     *         the value attribute of each option item
     * @param openOptionsAction
     *         the action to open the options
     * @param closeOptionsAction
     *         the action to close the options
     */
    public MuiSelect(WebElement element, ComponentWebDriver driver, MuiConfig config, By optionsLocator,
            String optionValueAttribute, OpenOptionsAction openOptionsAction, CloseOptionsAction closeOptionsAction) {
        super(element, driver, config);
        this.optionValueAttribute = requireNonNull(optionValueAttribute);
        this.optionsLocator = requireNonNull(optionsLocator);
        this.openOptionsAction = requireNonNull(openOptionsAction);
        this.closeOptionsAction = requireNonNull(closeOptionsAction);
    }

    @Override
    public String getComponentName() {
        return "Select";
    }

    @Override
    public boolean isMultiple() {
        return false;
    }

    @Override
    public List<WebComponent> getOptions2() {
        return getOptions2(0L);
    }

    @Override
    public List<WebComponent> getOptions2(Long delayInMillis) {
        WebComponent container = this.openOptions(delayInMillis);
        return container.findComponents(optionsLocator);
    }

    @Override
    public List<WebComponent> getAllSelectedOptions2() {
        return getOptions2().stream().filter(config::isSelected).collect(toList());
    }

    @Override
    public List<WebComponent> getAllSelectedOptions2(Long delayInMillis) {
        return getOptions2(delayInMillis).stream().filter(config::isSelected).collect(toList());
    }

    @Override
    @Deprecated
    public List<WebElement> getOptions() {
        return new ArrayList<>(getOptions2());
    }

    @Override
    @Deprecated
    public List<WebElement> getAllSelectedOptions() {
        return new ArrayList<>(getAllSelectedOptions2());
    }

    @Override
    public WebComponent openOptions() {
        return openOptions(0L);
    }

    @Override
    public WebComponent openOptions(Long delayInMillis) {
        List<WebComponent> components = driver.findComponents(config.popoverLocator());
        if (components.size() != 0) {
            return components.get(0);
        }

        this.openOptionsAction.open(this, driver);

        WebComponent container;
        if (delayInMillis > 0L) {
            WebDriverWait wait = new WebDriverWait(driver, 0L);
            wait.withTimeout(Duration.ofMillis(delayInMillis));
            container = driver.mapElement(wait.until(visibilityOfElementLocated(config.popoverLocator())));
        } else {
            container = driver.findComponent(config.popoverLocator());
        }
        return container;
    }

    @Override
    public void closeOptions() {
        closeOptions(0L);
    }

    @Override
    public void closeOptions(Long delayInMillis) {
        List<WebComponent> components = driver.findComponents(config.popoverLocator());
        if (components.isEmpty() || !components.get(0).isDisplayed()) {
            return;
        }

        List<WebComponent> options = getOptions2();
        closeOptionsAction.close(this, options, driver);

        if (delayInMillis > 0L) {
            WebDriverWait wait = new WebDriverWait(driver, 0L);
            wait.withTimeout(Duration.ofMillis(delayInMillis));
            wait.until(invisibilityOfElementLocated(config.popoverLocator()));
        } else {
            List<WebComponent> containers = driver.findComponents(config.popoverLocator());
            if (!containers.isEmpty() && containers.get(0).isDisplayed()) {
                throw new OptionNotClosedException("Option Popover is not properly closed " + config.popoverLocator());
            }
        }
    }

    @Override
    public WebElement getFirstSelectedOption() {
        return getOptions2().stream().filter(config::isSelected).findFirst().orElse(null);
    }

    @Override
    public WebElement getFirstSelectedOption(Long delayInMillis) {
        return getOptions2(delayInMillis).stream().filter(config::isSelected).findFirst().orElse(null);
    }

    @Override
    public void selectByVisibleText(String text) {
        selectByVisibleText(text, 0L);
    }

    @Override
    public void selectByVisibleText(String text, Long delayInMillis) {
        getOptions2(delayInMillis).stream()
                .filter(option -> !config.isSelected(option) && StringUtils.equals(text, option.getText()))
                .forEach(WebComponent::click);
    }

    @Override
    public void selectByIndex(int index) {
        selectByIndex(index, 0L);
    }

    @Override
    public void selectByIndex(int index, Long delayInMillis) {
        WebComponent component = getOptions2(delayInMillis).get(index);
        if (!config.isSelected(component)) {
            component.click();
        }
    }

    @Override
    public void selectByValue(String value) {
        getOptions2().stream().filter(option -> !config.isSelected(option) && StringUtils
                .equals(value, option.getAttribute(optionValueAttribute))).forEach(WebComponent::click);
    }

    @Override
    public void selectByValue(String value, Long delayInMillis) {
        getOptions2(delayInMillis).stream().filter(option -> !config.isSelected(option) && StringUtils
                .equals(value, option.getAttribute(optionValueAttribute))).forEach(WebComponent::click);
    }

    @Override
    public void deselectAll() {
        deselectAll(0L);
    }

    @Override
    public void deselectAll(Long delayInMillis) {
        getOptions2(delayInMillis).stream().filter(config::isSelected).forEach(WebComponent::click);
    }


    @Override
    public void deselectByValue(String value) {
        getOptions2().stream().filter(option -> config.isSelected(option) && StringUtils
                .equals(value, option.getAttribute(optionValueAttribute))).forEach(WebComponent::click);
    }

    @Override
    public void deselectByValue(String value, Long delayInMillis) {
        getOptions2(delayInMillis).stream().filter(option -> config.isSelected(option) && StringUtils
                .equals(value, option.getAttribute(optionValueAttribute))).forEach(WebComponent::click);
    }

    @Override
    public void deselectByIndex(int index) {
        deselectByIndex(index, 0L);
    }

    @Override
    public void deselectByIndex(int index, Long delayInMillis) {
        WebComponent component = getOptions2(delayInMillis).get(index);
        if (config.isSelected(component)) {
            component.click();
        }
    }

    @Override
    public void deselectByVisibleText(String text) {
        deselectByVisibleText(text, 0L);
    }

    @Override
    public void deselectByVisibleText(String text, Long delayInMillis) {
        getOptions2(delayInMillis).stream()
                .filter(option -> config.isSelected(option) && StringUtils.equals(text, option.getText()))
                .forEach(WebComponent::click);
    }

    /**
     * Gets option value attribute.
     *
     * @return the option value attribute
     */
    public String getOptionValueAttribute() {
        return optionValueAttribute;
    }

    /**
     * Gets options locator.
     *
     * @return the options locator
     */
    public By getOptionsLocator() {
        return optionsLocator;
    }

    /**
     * Gets open options action.
     *
     * @return the open options action
     */
    public OpenOptionsAction getOpenOptionsAction() {
        return openOptionsAction;
    }

    /**
     * Gets close options action.
     *
     * @return the close options action
     */
    public CloseOptionsAction getCloseOptionsAction() {
        return closeOptionsAction;
    }
}
