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
package com.github.grossopa.selenium.recorder.component;

import java.util.List;

/**
 * The registry of the known Material UI component definitions that map the MUI root css classes to the hamster
 * selenium component types and the {@code MuiComponents} factory methods. The definitions reuse the same root css
 * naming convention as {@code MuiConfig.getRootCss(componentName)}.
 *
 * @author Jack Yin
 * @since 1.15
 * @see MuiComponentDefinition
 * @see MuiComponentDetector
 */
public class MuiComponentDefinitions {

    private static final String INPUTS = "com.github.grossopa.selenium.component.mui.v4.inputs.";
    private static final String DATADISPLAY = "com.github.grossopa.selenium.component.mui.v4.datadisplay.";
    private static final String FEEDBACK = "com.github.grossopa.selenium.component.mui.v4.feedback.";
    private static final String NAVIGATION = "com.github.grossopa.selenium.component.mui.v4.navigation.";
    private static final String SURFACES = "com.github.grossopa.selenium.component.mui.v4.surfaces.";
    private static final String CORE = "com.github.grossopa.selenium.component.mui.v4.core.";
    private static final String LAB = "com.github.grossopa.selenium.component.mui.v4.lab.";

    /**
     * private constructor
     */
    private MuiComponentDefinitions() {
        throw new AssertionError();
    }

    /**
     * Gets the default definitions covering the Material UI components supported by {@code MuiComponents}.
     *
     * @return the default component definitions
     */
    public static List<MuiComponentDefinition> defaults() {
        return List.of(
                // Inputs Components
                new MuiComponentDefinition("Button", "MuiButton", INPUTS + "MuiButton", "toButton", false),
                new MuiComponentDefinition("ButtonGroup", "MuiButtonGroup", INPUTS + "MuiButtonGroup", "toButtonGroup",
                        false),
                new MuiComponentDefinition("Checkbox", "MuiCheckbox", INPUTS + "MuiCheckbox", "toCheckbox", false),
                new MuiComponentDefinition("Fab", "MuiFab", INPUTS + "MuiFab", "toFab", false),
                new MuiComponentDefinition("Radio", "MuiRadio", INPUTS + "MuiRadio", "toRadio", false),
                new MuiComponentDefinition("RadioGroup", "MuiRadioGroup", INPUTS + "MuiRadioGroup", "toRadioGroup",
                        false),
                new MuiComponentDefinition("Select", "MuiSelect", INPUTS + "MuiSelect", "toSelect", true),
                new MuiComponentDefinition("Slider", "MuiSlider", INPUTS + "MuiSlider", "toSlider", false),
                new MuiComponentDefinition("Switch", "MuiSwitch", INPUTS + "MuiSwitch", "toSwitch", false),
                new MuiComponentDefinition("TextField", "MuiTextField", INPUTS + "MuiTextField", "toTextField", false),
                new MuiComponentDefinition("Rating", "MuiRating", INPUTS + "MuiRating", "toRating", false),
                // Data Display Components
                new MuiComponentDefinition("Avatar", "MuiAvatar", DATADISPLAY + "MuiAvatar", "toAvatar", false),
                new MuiComponentDefinition("Badge", "MuiBadge", DATADISPLAY + "MuiBadge", "toBadge", false),
                new MuiComponentDefinition("Chip", "MuiChip", DATADISPLAY + "MuiChip", "toChip", false),
                new MuiComponentDefinition("Divider", "MuiDivider", DATADISPLAY + "MuiDivider", "toDivider", false),
                new MuiComponentDefinition("List", "MuiList", DATADISPLAY + "MuiList", "toList", false),
                new MuiComponentDefinition("ListItem", "MuiListItem", DATADISPLAY + "MuiListItem", "toListItem",
                        false),
                // Feedback Components
                new MuiComponentDefinition("Backdrop", "MuiBackdrop", FEEDBACK + "MuiBackdrop", "toBackdrop", false),
                new MuiComponentDefinition("Dialog", "MuiDialog", FEEDBACK + "MuiDialog", "toDialog", false),
                new MuiComponentDefinition("Snackbar", "MuiSnackbar", FEEDBACK + "MuiSnackbar", "toSnackbar", false),
                new MuiComponentDefinition("CircularProgress", "MuiCircularProgress", FEEDBACK + "MuiCircularProgress",
                        "toCircularProgress", false),
                new MuiComponentDefinition("LinearProgress", "MuiLinearProgress", FEEDBACK + "MuiLinearProgress",
                        "toLinearProgress", false),
                // Navigation Components
                new MuiComponentDefinition("Accordion", "MuiAccordion", NAVIGATION + "MuiAccordion", "toAccordion",
                        false),
                new MuiComponentDefinition("BottomNavigation", "MuiBottomNavigation", NAVIGATION
                        + "MuiBottomNavigation", "toBottomNavigation", false),
                new MuiComponentDefinition("Breadcrumbs", "MuiBreadcrumbs", NAVIGATION + "MuiBreadcrumbs",
                        "toBreadcrumbs", false),
                new MuiComponentDefinition("Link", "MuiLink", NAVIGATION + "MuiLink", "toLink", false),
                new MuiComponentDefinition("Menu", "MuiMenu", NAVIGATION + "MuiMenu", "toMenu", false),
                new MuiComponentDefinition("Tabs", "MuiTabs", NAVIGATION + "MuiTabs", "toTabs", false),
                new MuiComponentDefinition("Stepper", "MuiStepper", NAVIGATION + "MuiStepper", "toStepper", false),
                // Surfaces Components
                new MuiComponentDefinition("AppBar", "MuiAppBar", SURFACES + "MuiAppBar", "toAppBar", false),
                new MuiComponentDefinition("Pager", "MuiPager", SURFACES + "MuiPager", "toPager", false),
                // Core Components
                new MuiComponentDefinition("Grid", "MuiGrid", CORE + "MuiGrid", "toGrid", false),
                // Lab Components
                new MuiComponentDefinition("Autocomplete", "MuiAutocomplete", LAB + "MuiAutocomplete",
                        "toAutocomplete", false),
                new MuiComponentDefinition("Pagination", "MuiPagination", LAB + "MuiPagination", "toPagination",
                        false));
    }
}
