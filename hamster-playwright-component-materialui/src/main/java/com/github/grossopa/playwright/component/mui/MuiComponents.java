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
package com.github.grossopa.playwright.component.mui;

import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.component.mui.v4.core.*;
import com.github.grossopa.playwright.component.mui.v4.datadisplay.*;
import com.github.grossopa.playwright.component.mui.v4.feedback.*;
import com.github.grossopa.playwright.component.mui.v4.inputs.*;
import com.github.grossopa.playwright.component.mui.v4.lab.*;
import com.github.grossopa.playwright.component.mui.v4.navigation.*;
import com.github.grossopa.playwright.component.mui.v4.surfaces.*;
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

    // Inputs Components

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

    // Data Display Components

    /**
     * Converts the current component to {@link MuiAvatar}.
     *
     * @return the converted {@link MuiAvatar} instance
     */
    public MuiAvatar toAvatar() {
        return new MuiAvatar(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiBadge}.
     *
     * @return the converted {@link MuiBadge} instance
     */
    public MuiBadge toBadge() {
        return new MuiBadge(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiChip}.
     *
     * @return the converted {@link MuiChip} instance
     */
    public MuiChip toChip() {
        return new MuiChip(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiDivider}.
     *
     * @return the converted {@link MuiDivider} instance
     */
    public MuiDivider toDivider() {
        return new MuiDivider(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiList}.
     *
     * @return the converted {@link MuiList} instance
     */
    public MuiList toList() {
        return new MuiList(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiListItem}.
     *
     * @return the converted {@link MuiListItem} instance
     */
    public MuiListItem toListItem() {
        return new MuiListItem(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiTooltip}.
     *
     * @return the converted {@link MuiTooltip} instance
     */
    public MuiTooltip toTooltip() {
        return new MuiTooltip(component.locator(), driver, config);
    }

    // Feedback Components

    /**
     * Converts the current component to {@link MuiAlert}.
     *
     * @return the converted {@link MuiAlert} instance
     */
    public MuiAlert toAlert() {
        return new MuiAlert(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiBackdrop}.
     *
     * @return the converted {@link MuiBackdrop} instance
     */
    public MuiBackdrop toBackdrop() {
        return new MuiBackdrop(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiDialog}.
     *
     * @return the converted {@link MuiDialog} instance
     */
    public MuiDialog toDialog() {
        return new MuiDialog(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiSkeleton}.
     *
     * @return the converted {@link MuiSkeleton} instance
     */
    public MuiSkeleton toSkeleton() {
        return new MuiSkeleton(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiSnackbar}.
     *
     * @return the converted {@link MuiSnackbar} instance
     */
    public MuiSnackbar toSnackbar() {
        return new MuiSnackbar(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiSnackbarContent}.
     *
     * @return the converted {@link MuiSnackbarContent} instance
     */
    public MuiSnackbarContent toSnackbarContent() {
        return new MuiSnackbarContent(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiCircularProgress}.
     *
     * @return the converted {@link MuiCircularProgress} instance
     * @since 1.15.0
     */
    public MuiCircularProgress toCircularProgress() {
        return new MuiCircularProgress(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiLinearProgress}.
     *
     * @return the converted {@link MuiLinearProgress} instance
     * @since 1.15.0
     */
    public MuiLinearProgress toLinearProgress() {
        return new MuiLinearProgress(component.locator(), driver, config);
    }

    // Navigation Components

    /**
     * Converts the current component to {@link MuiAccordion}.
     *
     * @return the converted {@link MuiAccordion} instance
     */
    public MuiAccordion toAccordion() {
        return new MuiAccordion(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiDrawer}.
     *
     * @return the converted {@link MuiDrawer} instance
     */
    public MuiDrawer toDrawer() {
        return new MuiDrawer(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiMenu}.
     *
     * @return the converted {@link MuiMenu} instance
     */
    public MuiMenu toMenu() {
        return new MuiMenu(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiMenuItem}.
     *
     * @return the converted {@link MuiMenuItem} instance
     */
    public MuiMenuItem toMenuItem() {
        return new MuiMenuItem(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiTab}.
     *
     * @return the converted {@link MuiTab} instance
     */
    public MuiTab toTab() {
        return new MuiTab(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiTabs}.
     *
     * @return the converted {@link MuiTabs} instance
     */
    public MuiTabs toTabs() {
        return new MuiTabs(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiBottomNavigation}.
     *
     * @return the converted {@link MuiBottomNavigation} instance
     * @since 1.14.0
     */
    public MuiBottomNavigation toBottomNavigation() {
        return new MuiBottomNavigation(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiBottomNavigationAction}.
     *
     * @return the converted {@link MuiBottomNavigationAction} instance
     * @since 1.14.0
     */
    public MuiBottomNavigationAction toBottomNavigationAction() {
        return new MuiBottomNavigationAction(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiBreadcrumbs}.
     *
     * @return the converted {@link MuiBreadcrumbs} instance
     * @since 1.14.0
     */
    public MuiBreadcrumbs toBreadcrumbs() {
        return new MuiBreadcrumbs(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiLink}.
     *
     * @return the converted {@link MuiLink} instance
     * @since 1.14.0
     */
    public MuiLink toLink() {
        return new MuiLink(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiStepper}.
     *
     * @return the converted {@link MuiStepper} instance
     * @since 1.14.0
     */
    public MuiStepper toStepper() {
        return new MuiStepper(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiAccordionActions}.
     *
     * @return the converted {@link MuiAccordionActions} instance
     * @since 1.14.0
     */
    public MuiAccordionActions toAccordionActions() {
        return new MuiAccordionActions(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiAccordionDetails}.
     *
     * @return the converted {@link MuiAccordionDetails} instance
     * @since 1.14.0
     */
    public MuiAccordionDetails toAccordionDetails() {
        return new MuiAccordionDetails(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiAccordionSummary}.
     *
     * @return the converted {@link MuiAccordionSummary} instance
     * @since 1.14.0
     */
    public MuiAccordionSummary toAccordionSummary() {
        return new MuiAccordionSummary(component.locator(), driver, config);
    }

    // Surface Components

    /**
     * Converts the current component to {@link MuiAppBar}.
     *
     * @return the converted {@link MuiAppBar} instance
     */
    public MuiAppBar toAppBar() {
        return new MuiAppBar(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiCard}.
     *
     * @return the converted {@link MuiCard} instance
     */
    public MuiCard toCard() {
        return new MuiCard(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiPaper}.
     *
     * @return the converted {@link MuiPaper} instance
     */
    public MuiPaper toPaper() {
        return new MuiPaper(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiToolbar}.
     *
     * @return the converted {@link MuiToolbar} instance
     */
    public MuiToolbar toToolbar() {
        return new MuiToolbar(component.locator(), driver, config);
    }

    // Core Components

    /**
     * Converts the current component to {@link MuiGrid}.
     *
     * @return the converted {@link MuiGrid} instance
     */
    public MuiGrid toGrid() {
        return new MuiGrid(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiContainer}.
     *
     * @return the converted {@link MuiContainer} instance
     */
    public MuiContainer toContainer() {
        return new MuiContainer(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiBox}.
     *
     * @return the converted {@link MuiBox} instance
     */
    public MuiBox toBox() {
        return new MuiBox(component.locator(), driver, config);
    }

    // Lab Components

    /**
     * Converts the current component to {@link MuiAutocomplete}.
     *
     * @return the converted {@link MuiAutocomplete} instance
     */
    public MuiAutocomplete toAutocomplete() {
        return new MuiAutocomplete(component.locator(), driver, config);
    }

    /**
     * Converts the current component to {@link MuiPagination}.
     *
     * @return the converted {@link MuiPagination} instance
     */
    public MuiPagination toPagination() {
        return new MuiPagination(component.locator(), driver, config);
    }
}
