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

package com.github.grossopa.selenium.component.mui;

import com.github.grossopa.selenium.component.mui.action.CloseOptionsAction;
import com.github.grossopa.selenium.component.mui.action.OpenOptionsAction;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.datadisplay.*;
import com.github.grossopa.selenium.component.mui.feedback.MuiBackdrop;
import com.github.grossopa.selenium.component.mui.feedback.MuiDialog;
import com.github.grossopa.selenium.component.mui.feedback.MuiSnackbar;
import com.github.grossopa.selenium.component.mui.inputs.*;
import com.github.grossopa.selenium.component.mui.lab.MuiAutocomplete;
import com.github.grossopa.selenium.component.mui.lab.MuiAutocompleteTagLocators;
import com.github.grossopa.selenium.component.mui.lab.MuiPagination;
import com.github.grossopa.selenium.component.mui.lab.MuiPaginationLocators;
import com.github.grossopa.selenium.component.mui.navigation.*;
import com.github.grossopa.selenium.component.mui.pickers.MuiPickersDialog;
import com.github.grossopa.selenium.component.mui.surfaces.MuiAppBar;
import com.github.grossopa.selenium.component.mui.surfaces.MuiPager;
import com.github.grossopa.selenium.core.component.AbstractComponents;
import com.github.grossopa.selenium.core.component.WebComponent;
import lombok.Getter;
import org.openqa.selenium.By;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

import static java.util.Objects.requireNonNull;

/**
 * Contains the definition of Material UI components.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiComponents extends AbstractComponents {

    @Getter
    private final MuiConfig config;

    /**
     * Constructs an instance with default {@link MuiConfig}.
     */
    public MuiComponents() {
        this(new MuiConfig());
    }

    /**
     * Constructs an instance with provided {@link MuiConfig}.
     *
     * @param config the MUI configuration instance
     */
    public MuiComponents(MuiConfig config) {
        this.config = requireNonNull(config);
    }

    /**
     * Creates an instance of {@link MuiComponents} with default {@link MuiConfig}.
     *
     * @return the newly created instance with default {@link MuiConfig}.
     */
    public static MuiComponents mui() {
        return new MuiComponents();
    }

    /**
     * Creates an instance of {@link MuiComponents} with given {@link MuiConfig}.
     *
     * @param config the config instance
     * @return the instance of {@link MuiComponents} with given {@link MuiConfig}.
     */
    public static MuiComponents mui(MuiConfig config) {
        return new MuiComponents(config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiButton} instance.
     *
     * @return wrapped {@link MuiButton} instance on the given component
     */
    public MuiButton toButton() {
        return new MuiButton(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiFab} instance.
     *
     * @return wrapped {@link MuiFab} instance on the given component
     */
    public MuiFab toFab() {
        return new MuiFab(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiButtonGroup} instance.
     *
     * @return wrapped {@link MuiButtonGroup} instance on the given component
     */
    public MuiButtonGroup toButtonGroup() {
        return new MuiButtonGroup(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiCheckbox} instance.
     *
     * @return wrapped {@link MuiCheckbox} instance on the given component
     */
    public MuiCheckbox toCheckbox() {
        return new MuiCheckbox(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSelect} instance.
     *
     * @param optionLocator the locator for locating the options (NOTE: it is the option element NOT the option
     * container)
     * @return wrapped {@link MuiSelect} instance on the given component
     */
    public MuiSelect toSelect(By optionLocator) {
        requireNonNull(optionLocator);
        return new MuiSelect(component, driver, config, optionLocator);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSelect} instance.
     *
     * @param optionLocator the locator for locating the options (NOTE: it is the option element NOT the option
     * container)
     * @param optionValueAttribute if the option value will be marked somewhere in the element. with this attribute it
     * will make sure {@link MuiSelect#selectByValue(String)} and {@link MuiSelect#deselectByValue(String)} works
     * properly.
     * @return wrapped {@link MuiSelect} instance on the given component
     */
    public MuiSelect toSelect(By optionLocator, String optionValueAttribute) {
        requireNonNull(optionLocator);
        requireNonNull(optionValueAttribute);
        return new MuiSelect(component, driver, config, optionLocator, optionValueAttribute);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSlider} instance.
     *
     * @return the wrapped {@link MuiSlider} instance on the given component
     */
    public MuiSlider toSlider() {
        return new MuiSlider(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSlider} instance with scale function configured.
     *
     * @param inverseScaleFunction the non-null customized inverse scale function
     * @return the wrapped {@link MuiSlider} instance on the given component
     */
    public MuiSlider toSlider(UnaryOperator<Double> inverseScaleFunction) {
        return new MuiSlider(component, driver, config, inverseScaleFunction);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSwitch}.
     *
     * @return the wrapped {@link MuiSwitch} instance on the given component
     */
    public MuiSwitch toSwitch() {
        return new MuiSwitch(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiTextField}.
     *
     * @return the wrapped {@link MuiTextField} instance on the given component
     */
    public MuiTextField toTextField() {
        return new MuiTextField(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiRadio}.
     *
     * @return the wrapped {@link MuiRadio} instance on the given component
     */
    public MuiRadio toRadio() {
        return new MuiRadio(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiRadio}.
     *
     * @return the wrapped {@link MuiRadio} instance on the given component
     */
    public MuiRadioGroup toRadioGroup() {
        return new MuiRadioGroup(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiLink}.
     *
     * @return the wrapped {@link MuiLink} instance on the given component
     */
    public MuiLink toLink() {
        return new MuiLink(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiBreadcrumbs}.
     *
     * @return the wrapped {@link MuiBreadcrumbs} instance on the given component
     */
    public MuiBreadcrumbs toBreadcrumbs() {
        return new MuiBreadcrumbs(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiBottomNavigation}.
     *
     * @return the wrapped {@link MuiBottomNavigation} instance on the given component
     */
    public MuiBottomNavigation toBottomNavigation() {
        return new MuiBottomNavigation(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiTabs}.
     *
     * @return the wrapped {@link MuiTabs} instance on the given component
     */
    public MuiTabs toTabs() {
        return new MuiTabs(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAvatar}.
     *
     * @return the wrapped {@link MuiAvatar} instance on the given component
     */
    public MuiAvatar toAvatar() {
        return new MuiAvatar(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiBadge}.
     *
     * @return the wrapped {@link MuiBadge} instance on the given component
     */
    public MuiBadge toBadge() {
        return new MuiBadge(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiChip}.
     *
     * @return the wrapped {@link MuiChip} instance on the given component
     */
    public MuiChip toChip() {
        return new MuiChip(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiDivider}.
     *
     * @return the wrapped {@link MuiDivider} instance on the given component
     */
    public MuiDivider toDivider() {
        return new MuiDivider(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiList}.
     *
     * @return the wrapped {@link MuiList} instance on the given component
     */
    public MuiList toList() {
        return new MuiList(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiListItem}.
     *
     * @return the wrapped {@link MuiListItem} instance on the given component
     */
    public MuiListItem toListItem() {
        return new MuiListItem(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAppBar}.
     *
     * @return the wrapped {@link MuiAppBar} instance on the given component
     */
    public MuiAppBar toAppBar() {
        return new MuiAppBar(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiPager}.
     *
     * @return the wrapped {@link MuiPager} instance on the given component
     */
    public MuiPager toPager() {
        return new MuiPager(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiMenu}.
     *
     * @return the wrapped {@link MuiMenu} instance on the given component
     */
    public MuiMenu toMenu() {
        return new MuiMenu(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiBackdrop}.
     *
     * @return the wrapped {@link MuiBackdrop} instance on the given component
     */
    public MuiBackdrop toBackdrop() {
        return new MuiBackdrop(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiDialog}.
     *
     * @return the wrapped {@link MuiDialog} instance on the given component
     */
    public MuiDialog toDialog() {
        return new MuiDialog(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSnackbar}.
     *
     * @return the wrapped {@link MuiSnackbar} instance on the given component
     */
    public MuiSnackbar toSnackbar() {
        return new MuiSnackbar(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSnackbar}.
     *
     * @param autoHideDuration the autoHideDuration, should be a little longer than the value set in the component.
     * @return the wrapped {@link MuiSnackbar} instance on the given component
     */
    public MuiSnackbar toSnackbar(long autoHideDuration) {
        return new MuiSnackbar(component, driver, config, autoHideDuration);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAccordion}.
     *
     * @return the wrapped {@link MuiAccordion} instance on the given component
     */
    public MuiAccordion toAccordion() {
        return new MuiAccordion(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiPickersDialog}.
     *
     * @return the wrapped {@link MuiPickersDialog} instance on the given component
     */
    public MuiPickersDialog toPickersDialog() {
        return new MuiPickersDialog(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAutocomplete}.
     *
     * @return the wrapped {@link MuiAutocomplete} instance on the given component
     */
    public MuiAutocomplete toAutocomplete() {
        return new MuiAutocomplete(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAutocomplete} with customized optionLocator
     *
     * @param optionLocator the option locator for finding the option elements
     * @return the wrapped {@link MuiAutocomplete} instance on the given component
     */
    public MuiAutocomplete toAutocomplete(@Nullable By optionLocator) {
        return new MuiAutocomplete(component, driver, config, optionLocator);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAutocomplete} with customized optionLocator
     *
     * @param optionLocator the option locator for finding the option elements
     * @param tagLocators the tag locators for finding the selected option elements
     * @return the wrapped {@link MuiAutocomplete} instance on the given component
     */
    public MuiAutocomplete toAutocomplete(@Nullable By optionLocator,
            @Nullable MuiAutocompleteTagLocators tagLocators) {
        return new MuiAutocomplete(component, driver, config, optionLocator, tagLocators);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAutocomplete} with customized optionLocator
     *
     * @param optionLocator the option locator for finding the option elements
     * @param tagLocators the tag locators for finding the selected option elements
     * @param openOptionsAction the customized open options action
     * @param closeOptionsAction the customized close options action
     * @return the wrapped {@link MuiAutocomplete} instance on the given component
     */
    public MuiAutocomplete toAutocomplete(@Nullable By optionLocator, @Nullable MuiAutocompleteTagLocators tagLocators,
            @Nullable OpenOptionsAction openOptionsAction, @Nullable CloseOptionsAction closeOptionsAction) {
        return new MuiAutocomplete(component, driver, config, optionLocator, tagLocators, openOptionsAction,
                closeOptionsAction);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiPagination}.
     *
     * @return the wrapped {@link MuiPagination} instance on the given component
     */
    public MuiPagination toPagination() {
        return new MuiPagination(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiPagination} with customized button locators
     *
     * @param locators the customized locators for pagination buttons and index
     * @return the wrapped {@link MuiPagination} instance on the given component
     */
    public MuiPagination toPagination(@Nullable MuiPaginationLocators locators) {
        return new MuiPagination(component, driver, config, locators);
    }


}
