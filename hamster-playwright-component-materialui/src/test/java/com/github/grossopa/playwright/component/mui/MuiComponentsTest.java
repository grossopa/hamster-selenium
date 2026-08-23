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
import com.github.grossopa.playwright.component.mui.v4.feedback.MuiAlert;
import com.github.grossopa.playwright.component.mui.v4.feedback.MuiDialog;
import com.github.grossopa.playwright.component.mui.v4.feedback.MuiSnackbar;
import com.github.grossopa.playwright.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.playwright.component.mui.v4.inputs.MuiSelect;
import com.github.grossopa.playwright.component.mui.v4.inputs.MuiSlider;
import com.github.grossopa.playwright.component.mui.v4.lab.MuiAutocomplete;
import com.github.grossopa.playwright.component.mui.v4.lab.MuiPagination;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiAccordionActions;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiAccordionDetails;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiAccordionSummary;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiBottomNavigation;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiBottomNavigationAction;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiBreadcrumbs;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiLink;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiStepper;
import com.github.grossopa.playwright.component.mui.v4.navigation.MuiTabs;
import com.github.grossopa.playwright.component.mui.v4.surfaces.MuiCard;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiComponents}
 *
 * @author Jack Yin
 * @since 1.12
 */
class MuiComponentsTest {

    MuiComponents testSubject;
    ComponentDriver driver = mock(ComponentDriver.class);
    WebComponent component = mock(WebComponent.class);
    Locator locator = mock(Locator.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiComponents();
        testSubject.setContext(component, driver);
        when(component.locator()).thenReturn(locator);
    }

    @Test
    void testDefaultConstructor() {
        MuiComponents instance = new MuiComponents();
        assertNotNull(instance);
    }

    @Test
    void testConfigConstructor() {
        MuiConfig config = new MuiConfig();
        MuiComponents instance = new MuiComponents(config);
        assertNotNull(instance);
    }

    @Test
    void mui() {
        MuiComponents instance = MuiComponents.mui();
        assertNotNull(instance);
    }

    @Test
    void muiV5() {
        MuiComponents instance = MuiComponents.muiV5();
        assertNotNull(instance);
    }

    @Test
    void toButtonReturnsCorrectType() {
        assertInstanceOf(MuiButton.class, testSubject.toButton());
    }

    @Test
    void toSelectReturnsCorrectType() {
        assertInstanceOf(MuiSelect.class, testSubject.toSelect());
    }

    @Test
    void toSliderReturnsCorrectType() {
        assertInstanceOf(MuiSlider.class, testSubject.toSlider());
    }

    @Test
    void toDialogReturnsCorrectType() {
        assertInstanceOf(MuiDialog.class, testSubject.toDialog());
    }

    @Test
    void toAlertReturnsCorrectType() {
        assertInstanceOf(MuiAlert.class, testSubject.toAlert());
    }

    @Test
    void toSnackbarReturnsCorrectType() {
        assertInstanceOf(MuiSnackbar.class, testSubject.toSnackbar());
    }

    @Test
    void toCardReturnsCorrectType() {
        assertInstanceOf(MuiCard.class, testSubject.toCard());
    }

    @Test
    void toAutocompleteReturnsCorrectType() {
        assertInstanceOf(MuiAutocomplete.class, testSubject.toAutocomplete());
    }

    @Test
    void toPaginationReturnsCorrectType() {
        assertInstanceOf(MuiPagination.class, testSubject.toPagination());
    }

    @Test
    void toTabsReturnsCorrectType() {
        assertInstanceOf(MuiTabs.class, testSubject.toTabs());
    }

    @Test
    void toBottomNavigationReturnsCorrectType() {
        assertInstanceOf(MuiBottomNavigation.class, testSubject.toBottomNavigation());
    }

    @Test
    void toBottomNavigationActionReturnsCorrectType() {
        assertInstanceOf(MuiBottomNavigationAction.class, testSubject.toBottomNavigationAction());
    }

    @Test
    void toBreadcrumbsReturnsCorrectType() {
        assertInstanceOf(MuiBreadcrumbs.class, testSubject.toBreadcrumbs());
    }

    @Test
    void toLinkReturnsCorrectType() {
        assertInstanceOf(MuiLink.class, testSubject.toLink());
    }

    @Test
    void toStepperReturnsCorrectType() {
        assertInstanceOf(MuiStepper.class, testSubject.toStepper());
    }

    @Test
    void toAccordionActionsReturnsCorrectType() {
        assertInstanceOf(MuiAccordionActions.class, testSubject.toAccordionActions());
    }

    @Test
    void toAccordionDetailsReturnsCorrectType() {
        assertInstanceOf(MuiAccordionDetails.class, testSubject.toAccordionDetails());
    }

    @Test
    void toAccordionSummaryReturnsCorrectType() {
        assertInstanceOf(MuiAccordionSummary.class, testSubject.toAccordionSummary());
    }

    @Test
    void muiWithConfig() {
        MuiConfig config = new MuiConfig();
        MuiComponents instance = MuiComponents.mui(config);
        assertNotNull(instance);
    }

    // Inputs Components

    @Test
    void toButton() {
        assertNotNull(testSubject.toButton());
    }

    @Test
    void toTextField() {
        assertNotNull(testSubject.toTextField());
    }

    @Test
    void toCheckbox() {
        assertNotNull(testSubject.toCheckbox());
    }

    @Test
    void toSelect() {
        assertNotNull(testSubject.toSelect());
    }

    @Test
    void toRadio() {
        assertNotNull(testSubject.toRadio());
    }

    @Test
    void toSwitch() {
        assertNotNull(testSubject.toSwitch());
    }

    @Test
    void toSlider() {
        assertNotNull(testSubject.toSlider());
    }

    @Test
    void toFab() {
        assertNotNull(testSubject.toFab());
    }

    @Test
    void toRating() {
        assertNotNull(testSubject.toRating());
    }

    @Test
    void toButtonGroup() {
        assertNotNull(testSubject.toButtonGroup());
    }

    @Test
    void toRadioGroup() {
        assertNotNull(testSubject.toRadioGroup());
    }

    // Data Display Components

    @Test
    void toAvatar() {
        assertNotNull(testSubject.toAvatar());
    }

    @Test
    void toBadge() {
        assertNotNull(testSubject.toBadge());
    }

    @Test
    void toChip() {
        assertNotNull(testSubject.toChip());
    }

    @Test
    void toDivider() {
        assertNotNull(testSubject.toDivider());
    }

    @Test
    void toList() {
        assertNotNull(testSubject.toList());
    }

    @Test
    void toListItem() {
        assertNotNull(testSubject.toListItem());
    }

    @Test
    void toTooltip() {
        assertNotNull(testSubject.toTooltip());
    }

    // Feedback Components

    @Test
    void toAlert() {
        assertNotNull(testSubject.toAlert());
    }

    @Test
    void toBackdrop() {
        assertNotNull(testSubject.toBackdrop());
    }

    @Test
    void toDialog() {
        assertNotNull(testSubject.toDialog());
    }

    @Test
    void toSkeleton() {
        assertNotNull(testSubject.toSkeleton());
    }

    @Test
    void toSnackbar() {
        assertNotNull(testSubject.toSnackbar());
    }

    @Test
    void toSnackbarContent() {
        assertNotNull(testSubject.toSnackbarContent());
    }

    // Navigation Components

    @Test
    void toAccordion() {
        assertNotNull(testSubject.toAccordion());
    }

    @Test
    void toDrawer() {
        assertNotNull(testSubject.toDrawer());
    }

    @Test
    void toMenu() {
        assertNotNull(testSubject.toMenu());
    }

    @Test
    void toMenuItem() {
        assertNotNull(testSubject.toMenuItem());
    }

    @Test
    void toTab() {
        assertNotNull(testSubject.toTab());
    }

    @Test
    void toTabs() {
        assertNotNull(testSubject.toTabs());
    }

    @Test
    void toBottomNavigation() {
        assertNotNull(testSubject.toBottomNavigation());
    }

    @Test
    void toBottomNavigationAction() {
        assertNotNull(testSubject.toBottomNavigationAction());
    }

    @Test
    void toBreadcrumbs() {
        assertNotNull(testSubject.toBreadcrumbs());
    }

    @Test
    void toLink() {
        assertNotNull(testSubject.toLink());
    }

    @Test
    void toStepper() {
        assertNotNull(testSubject.toStepper());
    }

    @Test
    void toAccordionActions() {
        assertNotNull(testSubject.toAccordionActions());
    }

    @Test
    void toAccordionDetails() {
        assertNotNull(testSubject.toAccordionDetails());
    }

    @Test
    void toAccordionSummary() {
        assertNotNull(testSubject.toAccordionSummary());
    }

    // Surface Components

    @Test
    void toAppBar() {
        assertNotNull(testSubject.toAppBar());
    }

    @Test
    void toCard() {
        assertNotNull(testSubject.toCard());
    }

    @Test
    void toPaper() {
        assertNotNull(testSubject.toPaper());
    }

    @Test
    void toToolbar() {
        assertNotNull(testSubject.toToolbar());
    }

    // Core Components

    @Test
    void toGrid() {
        assertNotNull(testSubject.toGrid());
    }

    @Test
    void toContainer() {
        assertNotNull(testSubject.toContainer());
    }

    @Test
    void toBox() {
        assertNotNull(testSubject.toBox());
    }

    // Lab Components

    @Test
    void toAutocomplete() {
        assertNotNull(testSubject.toAutocomplete());
    }

    @Test
    void toPagination() {
        assertNotNull(testSubject.toPagination());
    }
}
