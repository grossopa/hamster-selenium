/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.component.mui;

import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.component.mui.v4.inputs.*;
import com.github.grossopa.playwright.core.AbstractComponents;
import com.github.grossopa.playwright.core.WebComponent;

/**
 * Contains the definition of Material UI components for Playwright.
 *
 * <p>This class serves as the main entry point for creating and working with Material UI components
 * in Playwright-based automation tests. It provides factory methods for creating all supported MUI 
 * component types.</p>
 *
 * <p>The class is organized by component categories following the Material UI documentation structure:
 * <ul>
 *   <li><strong>Inputs:</strong> Buttons, TextFields, Selects, Checkboxes, Switches, Sliders, etc.</li>
 *   <li><strong>Data Display:</strong> Lists, Tables, Chips, Avatars, Icons, etc.</li>
 *   <li><strong>Feedback:</strong> Dialogs, Snackbars, Backdrops, Progress indicators, etc.</li>
 *   <li><strong>Navigation:</strong> Tabs, Menus, Drawers, Breadcrumbs, etc.</li>
 *   <li><strong>Layout:</strong> Grids, Containers, Paper, etc.</li>
 *   <li><strong>Surfaces:</strong> AppBars, Toolbars, Cards, etc.</li>
 * </ul>
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * // Create MUI components instance
 * MuiComponents mui = MuiComponents.mui();
 * 
 * // Find and interact with a button
 * WebComponent component = driver.findComponent(".MuiButton-root");
 * MuiButton button = component.as(mui::toButton);
 * button.click();
 * 
 * // Work with text fields
 * MuiTextField textField = component.as(mui::toTextField);
 * textField.fill("Hello World");
 * }</pre>
 * </p>
 *
 * @author Jack Yin
 * @since 1.12
 * @see MuiConfig
 * @see MuiVersion
 * @see WebComponent
 */
public class MuiComponents extends AbstractComponents {

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
        this.config = config;
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
        config.setVersion(MuiVersion.V5);
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
     * Converts the current component to {@link MuiButton}.
     *
     * @return the converted {@link MuiButton} instance
     */
    public MuiButton toButton() {
        return new MuiButton(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiTextField}.
     *
     * @return the converted {@link MuiTextField} instance
     */
    public MuiTextField toTextField() {
        return new MuiTextField(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiCheckbox}.
     *
     * @return the converted {@link MuiCheckbox} instance
     */
    public MuiCheckbox toCheckbox() {
        return new MuiCheckbox(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiSelect}.
     *
     * @return the converted {@link MuiSelect} instance
     */
    public MuiSelect toSelect() {
        return new MuiSelect(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiRadio}.
     *
     * @return the converted {@link MuiRadio} instance
     */
    public MuiRadio toRadio() {
        return new MuiRadio(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiSwitch}.
     *
     * @return the converted {@link MuiSwitch} instance
     */
    public MuiSwitch toSwitch() {
        return new MuiSwitch(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiSlider}.
     *
     * @return the converted {@link MuiSlider} instance
     */
    public MuiSlider toSlider() {
        return new MuiSlider(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiFab}.
     *
     * @return the converted {@link MuiFab} instance
     */
    public MuiFab toFab() {
        return new MuiFab(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiRating}.
     *
     * @return the converted {@link MuiRating} instance
     */
    public MuiRating toRating() {
        return new MuiRating(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiButtonGroup}.
     *
     * @return the converted {@link MuiButtonGroup} instance
     */
    public MuiButtonGroup toButtonGroup() {
        return new MuiButtonGroup(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiRadioGroup}.
     *
     * @return the converted {@link MuiRadioGroup} instance
     */
    public MuiRadioGroup toRadioGroup() {
        return new MuiRadioGroup(component.locator(), driver, config);
    }

    /////////////////////////////
    // Data Display Components //
    /////////////////////////////

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiAvatar}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiAvatar} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiAvatar toAvatar() {
        return new com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiAvatar(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiBadge}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiBadge} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiBadge toBadge() {
        return new com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiBadge(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiChip}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiChip} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiChip toChip() {
        return new com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiChip(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiDivider}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiDivider} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiDivider toDivider() {
        return new com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiDivider(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiList}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiList} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiList toList() {
        return new com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiList(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiListItem}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiListItem} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiListItem toListItem() {
        return new com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiListItem(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiTooltip}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiTooltip} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiTooltip toTooltip() {
        return new com.github.grossopa.playwright.component.mui.v4.datadisplay.MuiTooltip(component.locator(), driver, config);
    }

    ////////////////////////
    // Feedback Components //
    ////////////////////////

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiAlert}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiAlert} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.feedback.MuiAlert toAlert() {
        return new com.github.grossopa.playwright.component.mui.v4.feedback.MuiAlert(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiBackdrop}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiBackdrop} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.feedback.MuiBackdrop toBackdrop() {
        return new com.github.grossopa.playwright.component.mui.v4.feedback.MuiBackdrop(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiDialog}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiDialog} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.feedback.MuiDialog toDialog() {
        return new com.github.grossopa.playwright.component.mui.v4.feedback.MuiDialog(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiSkeleton}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiSkeleton} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.feedback.MuiSkeleton toSkeleton() {
        return new com.github.grossopa.playwright.component.mui.v4.feedback.MuiSkeleton(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiSnackbar}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiSnackbar} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.feedback.MuiSnackbar toSnackbar() {
        return new com.github.grossopa.playwright.component.mui.v4.feedback.MuiSnackbar(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiSnackbarContent}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.feedback.MuiSnackbarContent} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.feedback.MuiSnackbarContent toSnackbarContent() {
        return new com.github.grossopa.playwright.component.mui.v4.feedback.MuiSnackbarContent(component.locator(), driver, config);
    }

    //////////////////////////
    // Navigation Components //
    //////////////////////////

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiAccordion}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiAccordion} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.navigation.MuiAccordion toAccordion() {
        return new com.github.grossopa.playwright.component.mui.v4.navigation.MuiAccordion(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiDrawer}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiDrawer} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.navigation.MuiDrawer toDrawer() {
        return new com.github.grossopa.playwright.component.mui.v4.navigation.MuiDrawer(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiMenu}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiMenu} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.navigation.MuiMenu toMenu() {
        return new com.github.grossopa.playwright.component.mui.v4.navigation.MuiMenu(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiMenuItem}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiMenuItem} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.navigation.MuiMenuItem toMenuItem() {
        return new com.github.grossopa.playwright.component.mui.v4.navigation.MuiMenuItem(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiTab}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiTab} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.navigation.MuiTab toTab() {
        return new com.github.grossopa.playwright.component.mui.v4.navigation.MuiTab(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiTabs}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.navigation.MuiTabs} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.navigation.MuiTabs toTabs() {
        return new com.github.grossopa.playwright.component.mui.v4.navigation.MuiTabs(component.locator(), driver, config);
    }

    /////////////////////////
    // Surface Components //
    /////////////////////////

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.surfaces.MuiAppBar}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.surfaces.MuiAppBar} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.surfaces.MuiAppBar toAppBar() {
        return new com.github.grossopa.playwright.component.mui.v4.surfaces.MuiAppBar(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.surfaces.MuiCard}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.surfaces.MuiCard} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.surfaces.MuiCard toCard() {
        return new com.github.grossopa.playwright.component.mui.v4.surfaces.MuiCard(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.surfaces.MuiPaper}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.surfaces.MuiPaper} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.surfaces.MuiPaper toPaper() {
        return new com.github.grossopa.playwright.component.mui.v4.surfaces.MuiPaper(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.surfaces.MuiToolbar}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.surfaces.MuiToolbar} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.surfaces.MuiToolbar toToolbar() {
        return new com.github.grossopa.playwright.component.mui.v4.surfaces.MuiToolbar(component.locator(), driver, config);
    }

    //////////////////////
    // Core Components //
    //////////////////////

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.core.MuiGrid}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.core.MuiGrid} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.core.MuiGrid toGrid() {
        return new com.github.grossopa.playwright.component.mui.v4.core.MuiGrid(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.core.MuiContainer}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.core.MuiContainer} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.core.MuiContainer toContainer() {
        return new com.github.grossopa.playwright.component.mui.v4.core.MuiContainer(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.core.MuiBox}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.core.MuiBox} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.core.MuiBox toBox() {
        return new com.github.grossopa.playwright.component.mui.v4.core.MuiBox(component.locator(), driver, config);
    }

    /////////////////////
    // Lab Components //
    /////////////////////

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.lab.MuiAutocomplete}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.lab.MuiAutocomplete} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.lab.MuiAutocomplete toAutocomplete() {
        return new com.github.grossopa.playwright.component.mui.v4.lab.MuiAutocomplete(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link com.github.grossopa.playwright.component.mui.v4.lab.MuiPagination}.
     *
     * @return the converted {@link com.github.grossopa.playwright.component.mui.v4.lab.MuiPagination} instance
     */
    public com.github.grossopa.playwright.component.mui.v4.lab.MuiPagination toPagination() {
        return new com.github.grossopa.playwright.component.mui.v4.lab.MuiPagination(component.locator(), driver, config);
    }
}
