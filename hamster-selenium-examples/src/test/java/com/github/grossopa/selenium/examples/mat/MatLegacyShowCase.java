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
package com.github.grossopa.selenium.examples.mat;

import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;

/**
 * Runs all Angular Material v12 (legacy) component test cases and prints a summary report.
 *
 * <p>This is the legacy counterpart of the MDC-based {@link MatShowCase}. It reuses the
 * individual {@code MatLegacyXxxTestCases} classes, each of which inherits test logic from
 * the corresponding MDC test class but targets the v12 documentation site at
 * {@code https://v12.material.angular.dev/} with a legacy {@code MatConfig}.</p>
 *
 * <p>Usage:</p>
 * <pre>{@code
 * // run all legacy tests
 * MatLegacyShowCase.main(new String[]{});
 *
 * // run a single test by name
 * MatLegacyShowCase.main(new String[]{"testButtons"});
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.16
 * @see MatShowCase
 */
@SuppressWarnings("all")
public class MatLegacyShowCase extends MatTestSupport {

    /**
     * Main entry point. Starts the Edge driver, runs all legacy mat component tests and
     * prints a summary report.
     *
     * @param args optional: first argument is the test name filter
     */
    public static void main(String[] args) {
        MatLegacyShowCase runner = new MatLegacyShowCase();
        runner.setUpDriver(EDGE);

        String filter = args.length > 0 ? args[0] : System.getenv("MAT_FILTER");

        // --- Instantiate each legacy test case class ---
        MatLegacyAutocompleteTestCases autocomplete = new MatLegacyAutocompleteTestCases();
        MatLegacyBadgeTestCases badge = new MatLegacyBadgeTestCases();
        MatLegacyBottomSheetTestCases bottomSheet = new MatLegacyBottomSheetTestCases();
        MatLegacyButtonTestCases button = new MatLegacyButtonTestCases();
        MatLegacyButtonToggleTestCases buttonToggle = new MatLegacyButtonToggleTestCases();
        MatLegacyCheckboxTestCases checkbox = new MatLegacyCheckboxTestCases();
        MatLegacyChipListTestCases chipList = new MatLegacyChipListTestCases();
        MatLegacyDialogTestCases dialog = new MatLegacyDialogTestCases();
        MatLegacyExpansionPanelTestCases expansionPanel = new MatLegacyExpansionPanelTestCases();
        MatLegacyFormFieldTestCases formField = new MatLegacyFormFieldTestCases();
        MatLegacyGridTestCases grid = new MatLegacyGridTestCases();
        MatLegacyListTestCases list = new MatLegacyListTestCases();
        MatLegacyMenuItemTestCases menuItem = new MatLegacyMenuItemTestCases();
        MatLegacyProgressBarTestCases progressBar = new MatLegacyProgressBarTestCases();
        MatLegacySlideToggleTestCases slideToggle = new MatLegacySlideToggleTestCases();
        MatLegacySliderTestCases slider = new MatLegacySliderTestCases();
        MatLegacySnackbarTestCases snackbar = new MatLegacySnackbarTestCases();
        MatLegacyNewComponentsTestCases newComponents = new MatLegacyNewComponentsTestCases();

        try {
            // ---- Form Controls ----
            runner.runTestClass("MatLegacyButtonTestCases", () -> {
                runner.runIf(filter, "testButtons", button::testButtons);
            });

            runner.runTestClass("MatLegacyCheckboxTestCases", () -> {
                runner.runIf(filter, "testCheckbox", checkbox::testCheckbox);
            });

            runner.runTestClass("MatLegacySlideToggleTestCases", () -> {
                runner.runIf(filter, "testSliderConfiguration", slideToggle::testSliderConfiguration);
            });

            runner.runTestClass("MatLegacyButtonToggleTestCases", () -> {
                runner.runIf(filter, "testButtonToggleGroup", buttonToggle::testButtonToggleGroup);
            });

            runner.runTestClass("MatLegacySliderTestCases", () -> {
                runner.runIf(filter, "testConfigurableSlider", slider::testConfigurableSlider);
            });

            runner.runTestClass("MatLegacyAutocompleteTestCases", () -> {
                runner.runIf(filter, "testAutocomplete", autocomplete::testAutocomplete);
            });

            // ---- Data Display ----
            runner.runTestClass("MatLegacyBadgeTestCases", () -> {
                runner.runIf(filter, "testBadge", badge::testBadge);
            });

            runner.runTestClass("MatLegacyChipListTestCases", () -> {
                runner.runIf(filter, "testChipList", chipList::testChipList);
            });

            runner.runTestClass("MatLegacyGridTestCases", () -> {
                runner.runIf(filter, "testGrid", grid::testGrid);
            });

            runner.runTestClass("MatLegacyListTestCases", () -> {
                runner.runIf(filter, "testList", list::testList);
                runner.runIf(filter, "testListWithSelection", list::testListWithSelection);
                runner.runIf(filter, "testListWithSingleSelection", list::testListWithSingleSelection);
            });

            runner.runTestClass("MatLegacyProgressBarTestCases", () -> {
                runner.runIf(filter, "testBufferProgressBar", progressBar::testBufferProgressBar);
                runner.runIf(filter, "testConfigurableProgressBar", progressBar::testConfigurableProgressBar);
                runner.runIf(filter, "testIndeterminateProgressBar", progressBar::testIndeterminateProgressBar);
                runner.runIf(filter, "testQueryProgressBar", progressBar::testQueryProgressBar);
            });

            // ---- Layout ----
            runner.runTestClass("MatLegacyExpansionPanelTestCases", () -> {
                runner.runIf(filter, "testExpansionPanel", expansionPanel::testExpansionPanel);
            });

            runner.runTestClass("MatLegacyFormFieldTestCases", () -> {
                runner.runIf(filter, "navigate", formField::navigate);
                runner.runIf(filter, "testAppearance", formField::testAppearance);
                runner.runIf(filter, "testError", formField::testError);
                runner.runIf(filter, "testHints", formField::testHints);
                runner.runIf(filter, "testPrefixSuffix", formField::testPrefixSuffix);
            });

            // ---- Navigation ----
            runner.runTestClass("MatLegacyMenuItemTestCases", () -> {
                runner.runIf(filter, "testMenuWithIcons", menuItem::testMenuWithIcons);
                runner.runIf(filter, "testNestedMenu", menuItem::testNestedMenu);
                runner.runIf(filter, "testNestedMenuComplexActions", menuItem::testNestedMenuComplexActions);
                runner.runIf(filter, "testSelection", menuItem::testSelection);
            });

            // ---- Popups & Modals ----
            runner.runTestClass("MatLegacyDialogTestCases", () -> {
                runner.runIf(filter, "testDialog", dialog::testDialog);
            });

            runner.runTestClass("MatLegacySnackbarTestCases", () -> {
                runner.runIf(filter, "testSliderConfiguration", snackbar::testSliderConfiguration);
            });

            runner.runTestClass("MatLegacyBottomSheetTestCases", () -> {
                runner.runIf(filter, "testBottomSheet", bottomSheet::testBottomSheet);
            });

            // ---- New Components (since 1.16) ----
            runner.runTestClass("MatLegacyNewComponentsTestCases", () -> {
                runner.runIf(filter, "testInput", newComponents::testInput);
                runner.runIf(filter, "testSelect", newComponents::testSelect);
                runner.runIf(filter, "testRadioGroup", newComponents::testRadioGroup);
                runner.runIf(filter, "testCard", newComponents::testCard);
                runner.runIf(filter, "testTabs", newComponents::testTabs);
                runner.runIf(filter, "testStepper", newComponents::testStepper);
                runner.runIf(filter, "testTable", newComponents::testTable);
                runner.runIf(filter, "testPaginator", newComponents::testPaginator);
                runner.runIf(filter, "testSidenav", newComponents::testSidenav);
                runner.runIf(filter, "testTree", newComponents::testTree);
            });

        } finally {
            runner.tearDownAndReport();
        }

        if (runner.anyFailure) {
            System.exit(1);
        }
    }
}
