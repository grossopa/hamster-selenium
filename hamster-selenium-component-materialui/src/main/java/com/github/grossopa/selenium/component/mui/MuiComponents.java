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
import com.github.grossopa.selenium.component.mui.config.MuiSelectConfig;
import com.github.grossopa.selenium.component.mui.v4.core.MuiGrid;
import com.github.grossopa.selenium.component.mui.v4.datadisplay.*;
import com.github.grossopa.selenium.component.mui.v4.exception.InvalidVersionException;
import com.github.grossopa.selenium.component.mui.v4.feedback.MuiBackdrop;
import com.github.grossopa.selenium.component.mui.v4.feedback.MuiDialog;
import com.github.grossopa.selenium.component.mui.v4.feedback.MuiSnackbar;
import com.github.grossopa.selenium.component.mui.v4.inputs.*;
import com.github.grossopa.selenium.component.mui.v4.lab.MuiAutocomplete;
import com.github.grossopa.selenium.component.mui.v4.lab.MuiAutocompleteTagLocators;
import com.github.grossopa.selenium.component.mui.v4.lab.MuiPagination;
import com.github.grossopa.selenium.component.mui.v4.lab.MuiPaginationLocators;
import com.github.grossopa.selenium.component.mui.v4.navigation.*;
import com.github.grossopa.selenium.component.mui.v4.pickers.MuiPickersDialog;
import com.github.grossopa.selenium.component.mui.v4.surfaces.MuiAppBar;
import com.github.grossopa.selenium.component.mui.v4.surfaces.MuiPager;
import com.github.grossopa.selenium.component.mui.v5.inputs.MuiCheckboxV5;
import com.github.grossopa.selenium.component.mui.v5.inputs.MuiSliderV5;
import com.github.grossopa.selenium.component.mui.v5.inputs.MuiSwitchV5;
import com.github.grossopa.selenium.core.component.AbstractComponents;
import com.github.grossopa.selenium.core.component.WebComponent;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
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
     * Creates an instance of {@link MuiComponents} with {@link MuiConfig} for Material UI version v5.
     *
     * @return the newly created instance with {@link MuiConfig} for Material UI version v5
     */
    public static MuiComponents muiV5() {
        MuiConfig config = new MuiConfig();
        config.setVersion(V5);
        return new MuiComponents(config);
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

    ///////////////////////
    // Inputs Components //
    ///////////////////////

    /**
     * Wraps the current {@link WebComponent} to {@link MuiButton} instance.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have css class "MuiButton-root".
     * </p>
     *
     * @return wrapped {@link MuiButton} instance on the given component
     */
    public MuiButton toButton() {
        return create(() -> new MuiButton(component, driver, config), () -> new MuiButton(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiButtonGroup} instance.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have below structure: {@code
     * <div class="MuiButtonGroup-root">
     *     <div class="MuiButton-root ..."></div>
     *     <div class="MuiButton-root ..."></div>
     *     <div class="MuiButton-root ..."></div>
     * </div>
     * }
     * </p>
     *
     * @return wrapped {@link MuiButtonGroup} instance on the given component
     */
    public MuiButtonGroup toButtonGroup() {
        return create(() -> new MuiButtonGroup(component, driver, config),
                () -> new MuiButtonGroup(component, driver, config));
    }


    /**
     * Wraps the current {@link WebComponent} to {@link MuiCheckbox} instance for {@link MuiVersion#V4} and {@link
     * MuiCheckboxV5} for {@link MuiVersion#V5}. Developer should cast the class to {@link MuiCheckboxV5} for additional
     * APIs such as {@link MuiCheckboxV5#isIndeterminate()}.
     *
     * <p>
     * The {@link WebElement} should have css class "MuiCheckbox-root".
     * </p>
     *
     * @return wrapped {@link MuiCheckbox} instance on the given component
     */
    public MuiCheckbox toCheckbox() {
        return create(() -> new MuiCheckbox(component, driver, config),
                () -> new MuiCheckboxV5(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiFab} instance.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have css class "MuiFab-root".
     * </p>
     *
     * @return wrapped {@link MuiFab} instance on the given component
     */
    public MuiFab toFab() {
        return create(() -> new MuiFab(component, driver, config), () -> new MuiFab(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiRadio}.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have css class "MuiRadio-root".
     * </p>
     *
     * @return the wrapped {@link MuiRadio} instance on the given component
     */
    public MuiRadio toRadio() {
        return create(() -> new MuiRadio(component, driver, config), () -> new MuiRadio(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiRadioGroup}.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have below structure: {@code
     * <div class="MuiFormGroup-root">
     *     ....
     *     <span class="MuiRadio-root ..."></span>
     *     <span class="MuiRadio-root ..."></span>
     *     <span class="MuiRadio-root ..."></span>
     * </div>
     * }
     * </p>
     *
     * @return the wrapped {@link MuiRadioGroup} instance on the given component
     */
    public MuiRadioGroup toRadioGroup() {
        return create(() -> new MuiRadioGroup(component, driver, config),
                () -> new MuiRadioGroup(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSelect} instance.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have css class "MuiSelect-root".
     * </p>
     *
     * @param optionLocator the locator for locating the options (NOTE: it is the option element NOT the option
     * container)
     * @return wrapped {@link MuiSelect} instance on the given component
     */
    public MuiSelect toSelect(By optionLocator) {
        return create(() -> new MuiSelect(component, driver, config, MuiSelectConfig.builder(optionLocator).build()),
                () -> new MuiSelect(component, driver, config, MuiSelectConfig.builder(optionLocator).build()));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSelect} instance.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have css class "MuiSelect-root".
     * </p>
     *
     * @param optionLocator the locator for locating the options (NOTE: it is the option element NOT the option
     * container)
     * @param optionValueAttribute if the option value will be marked somewhere in the element. with this attribute it
     * will make sure {@link MuiSelect#selectByValue(String)} and {@link MuiSelect#deselectByValue(String)} works
     * properly.
     * @return wrapped {@link MuiSelect} instance on the given component
     */
    public MuiSelect toSelect(By optionLocator, String optionValueAttribute) {
        MuiSelectConfig selectConfig = MuiSelectConfig.builder(optionLocator).optionValueAttribute(optionValueAttribute)
                .build();
        return create(() -> new MuiSelect(component, driver, config, selectConfig),
                () -> new MuiSelect(component, driver, config, selectConfig));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSelect} instance.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>The {@link WebElement} should have css class "MuiSelect-root".</p>
     *
     * @param optionLocator the locator for locating the options (NOTE: it is the option element NOT the option
     * container)
     * @param configEnrichConsumer allows developer to configure using the builder
     * @return wrapped {@link MuiSelect} instance on the given component
     */
    public MuiSelect toSelect(By optionLocator, Consumer<MuiSelectConfig.MuiSelectConfigBuilder> configEnrichConsumer) {
        MuiSelectConfig.MuiSelectConfigBuilder builder = MuiSelectConfig.builder(optionLocator);
        configEnrichConsumer.accept(builder);
        return create(() -> new MuiSelect(component, driver, config, builder.build()),
                () -> new MuiSelect(component, driver, config, builder.build()));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSlider} for {@link MuiVersion#V4} and {@link MuiSliderV5} for
     * {@link MuiVersion#V5}
     *
     * <p>The {@link WebElement} should have css class "MuiSlider-root".</p>
     *
     * @return the wrapped {@link MuiSlider} instance on the given component
     */
    public MuiSlider toSlider() {
        return create(() -> new MuiSlider(component, driver, config), () -> new MuiSliderV5(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSlider} instance with scale function configured. {@link
     * MuiSlider} for {@link MuiVersion#V4} and {@link MuiSliderV5} for {@link MuiVersion#V5}.
     *
     * <p>The {@link WebElement} should have css class "MuiSlider-root".</p>
     *
     * @param inverseScaleFunction the non-null customized inverse scale function
     * @return the wrapped {@link MuiSlider} instance on the given component
     */
    public MuiSlider toSlider(UnaryOperator<Double> inverseScaleFunction) {
        return create(() -> new MuiSlider(component, driver, config, inverseScaleFunction),
                () -> new MuiSliderV5(component, driver, config, inverseScaleFunction));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiSwitch} instance with scale function configured. {@link
     * MuiSwitch} for {@link MuiVersion#V4} and {@link MuiSwitchV5} for {@link MuiVersion#V5}.
     *
     * <p>The {@link WebElement} should have css class "MuiSwitch-root".</p>
     *
     * @return the wrapped {@link MuiSwitch} instance on the given component
     */
    public MuiSwitch toSwitch() {
        return create(() -> new MuiSwitch(component, driver, config), () -> new MuiSwitchV5(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiTextField}.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>The {@link WebElement} should have css class "MuiTextField-root".</p>
     *
     * @return the wrapped {@link MuiTextField} instance on the given component
     */
    public MuiTextField toTextField() {
        return create(() -> new MuiTextField(component, driver, config),
                () -> new MuiTextField(component, driver, config));
    }

    /////////////////////////////
    // Data Display Components //
    /////////////////////////////

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAvatar}.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>The {@link WebElement} should have css class "MuiAvatar-root".</p>
     *
     * @return the wrapped {@link MuiAvatar} instance on the given component
     */
    public MuiAvatar toAvatar() {
        return create(() -> new MuiAvatar(component, driver, config), () -> new MuiAvatar(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiBadge}.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>The {@link WebElement} should have css class "MuiBadge-root".</p>
     *
     * @return the wrapped {@link MuiBadge} instance on the given component
     */
    public MuiBadge toBadge() {
        return create(() -> new MuiBadge(component, driver, config), () -> new MuiBadge(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiGrid} instance.
     *
     * @return wrapped {@link MuiGrid} instance on the given component
     */
    public MuiGrid toGrid() {
        return new MuiGrid(component, driver, config);
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
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have css class "MuiAutocomplete-root".
     * </p>
     *
     * @return the wrapped {@link MuiAutocomplete} instance on the given component
     */
    public MuiAutocomplete toAutocomplete() {
        return create(() -> new MuiAutocomplete(component, driver, config),
                () -> new MuiAutocomplete(component, driver, config));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAutocomplete} with customized optionLocator.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have css class "MuiAutocomplete-root".
     * </p>
     *
     * @param optionLocator the option locator for finding the option elements
     * @return the wrapped {@link MuiAutocomplete} instance on the given component
     */
    public MuiAutocomplete toAutocomplete(@Nullable By optionLocator) {
        return create(() -> new MuiAutocomplete(component, driver, config, optionLocator),
                () -> new MuiAutocomplete(component, driver, config, optionLocator));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAutocomplete} with customized optionLocator and tagLocators
     * (for multiple selection feature).
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have css class "MuiAutocomplete-root".
     * </p>
     *
     * @param optionLocator the option locator for finding the option elements
     * @param tagLocators the tag locators for finding the selected option elements
     * @return the wrapped {@link MuiAutocomplete} instance on the given component
     */
    public MuiAutocomplete toAutocomplete(@Nullable By optionLocator,
            @Nullable MuiAutocompleteTagLocators tagLocators) {
        return create(() -> new MuiAutocomplete(component, driver, config, optionLocator, tagLocators),
                () -> new MuiAutocomplete(component, driver, config, optionLocator, tagLocators));
    }

    /**
     * Wraps the current {@link WebComponent} to {@link MuiAutocomplete} with customized optionLocator, tagLocators (for
     * multiple selection feature), and actions for opening and closing the options.
     *
     * <p>It supports both Material UI version {@link MuiVersion#V4} and {@link MuiVersion#V5}.</p>
     *
     * <p>
     * The {@link WebElement} should have css class "MuiAutocomplete-root".
     * </p>
     *
     * @param optionLocator the option locator for finding the option elements
     * @param tagLocators the tag locators for finding the selected option elements
     * @param openOptionsAction the customized open options action
     * @param closeOptionsAction the customized close options action
     * @return the wrapped {@link MuiAutocomplete} instance on the given component
     */
    public MuiAutocomplete toAutocomplete(@Nullable By optionLocator, @Nullable MuiAutocompleteTagLocators tagLocators,
            @Nullable OpenOptionsAction openOptionsAction, @Nullable CloseOptionsAction closeOptionsAction) {
        return create(
                () -> new MuiAutocomplete(component, driver, config, optionLocator, tagLocators, openOptionsAction,
                        closeOptionsAction),
                () -> new MuiAutocomplete(component, driver, config, optionLocator, tagLocators, openOptionsAction,
                        closeOptionsAction));
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

    private <T> T create(Supplier<T> v4creatorFunc, Supplier<T> v5creatorFunc) {
        if (config.getVersion() == V4) {
            return v4creatorFunc.get();
        } else if (config.getVersion() == V5) {
            return v5creatorFunc.get();
        }
        throw new InvalidVersionException("Given version is not recognizable. " + config.getVersion());
    }

}
