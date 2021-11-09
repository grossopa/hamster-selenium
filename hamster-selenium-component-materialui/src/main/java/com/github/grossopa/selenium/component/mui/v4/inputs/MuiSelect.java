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

package com.github.grossopa.selenium.component.mui.v4.inputs;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.config.MuiSelectConfig;
import com.github.grossopa.selenium.component.mui.v4.core.MuiPopover;
import com.github.grossopa.selenium.component.mui.exception.OptionNotClosedException;
import com.github.grossopa.selenium.component.mui.v4.finder.MuiModalFinder;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.component.api.DelayedSelect;
import com.github.grossopa.selenium.core.component.api.Select;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

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
public class MuiSelect extends AbstractMuiComponent implements Select, DelayedSelect {

    /**
     * the component name
     */
    public static final String COMPONENT_NAME = "Select";

    private final MuiModalFinder modalFinder;
    private final MuiSelectConfig selectConfig;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param selectConfig the component configuration class
     */
    public MuiSelect(WebElement element, ComponentWebDriver driver, MuiConfig config, MuiSelectConfig selectConfig) {
        super(element, driver, config);
        requireNonNull(selectConfig);
        this.modalFinder = new MuiModalFinder(driver, config);
        this.selectConfig = selectConfig;
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
        return container.findComponents(selectConfig.getOptionsLocator());
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
    public WebComponent openOptions() {
        return openOptions(0L);
    }

    @Override
    public WebComponent openOptions(Long delayInMillis) {
        WebComponent component = modalFinder.findTopVisibleOverlay(MuiPopover.COMPONENT_NAME);
        if (component != null) {
            return component;
        }

        this.selectConfig.getOpenOptionsAction().open(this, driver);

        WebComponent container;
        if (delayInMillis > 0L) {
            WebDriverWait wait = driver.createWait(delayInMillis);
            container = driver.mapElement(
                    wait.until(d -> modalFinder.findTopVisibleOverlay(MuiPopover.COMPONENT_NAME)));
        } else {
            container = modalFinder.findTopVisibleOverlay(MuiPopover.COMPONENT_NAME);
        }
        return container;
    }

    @Override
    public void closeOptions() {
        closeOptions(0L);
    }

    @Override
    public void closeOptions(Long delayInMillis) {
        WebComponent component = modalFinder.findTopVisibleOverlay(MuiPopover.COMPONENT_NAME);
        if (component == null) {
            return;
        }

        List<WebComponent> options = getOptions2();
        selectConfig.getCloseOptionsAction().close(this, options, driver);

        if (delayInMillis > 0L) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(delayInMillis));
            wait.withTimeout(Duration.ofMillis(delayInMillis));
            wait.until(d -> modalFinder.findTopVisibleOverlay(MuiPopover.COMPONENT_NAME) == null);
        } else {
            WebComponent closedComponents = modalFinder.findTopVisibleOverlay(MuiPopover.COMPONENT_NAME);
            if (closedComponents != null && closedComponents.isDisplayed()) {
                throw new OptionNotClosedException("Option Popover is not properly closed.");
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
        doFilterAndAction(getOptions2(delayInMillis),
                option -> !config.isSelected(option) && StringUtils.equals(text, option.getText()));
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
        selectByValue(value, 0L);
    }

    @Override
    public void selectByValue(String value, Long delayInMillis) {
        doFilterAndAction(getOptions2(delayInMillis), option -> !config.isSelected(option) && StringUtils.equals(value,
                option.getAttribute(selectConfig.getOptionValueAttribute())));
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
        deselectByValue(value, 0L);
    }

    @Override
    public void deselectByValue(String value, Long delayInMillis) {
        doFilterAndAction(getOptions2(delayInMillis), option -> config.isSelected(option) && StringUtils.equals(value,
                option.getAttribute(selectConfig.getOptionValueAttribute())));
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
        doFilterAndAction(getOptions2(delayInMillis),
                option -> config.isSelected(option) && StringUtils.equals(text, option.getText()));
    }

    private void doFilterAndAction(List<WebComponent> options, Predicate<WebComponent> isTrue) {
        for (WebComponent option : options) {
            if (isTrue.test(option)) {
                option.click();
                if (!selectConfig.isMultiple()) {
                    return;
                }
            }
        }
    }

    /**
     * Gets the select configuration
     *
     * @return the select configuration
     */
    public MuiSelectConfig selectConfig() {
        return selectConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiSelect)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MuiSelect muiSelect = (MuiSelect) o;
        return Objects.equals(modalFinder, muiSelect.modalFinder) && Objects.equals(selectConfig,
                muiSelect.selectConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), modalFinder, selectConfig);
    }

    @Override
    public String toString() {
        return "MuiSelect{" + "modalFinder=" + modalFinder + ", selectConfig=" + selectConfig + ", element=" + element
                + '}';
    }
}
