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

import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;

import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;

/**
 * Runs all Angular Material (mat) component test cases and prints a summary report.
 *
 * <p>This is the Selenium counterpart of the Playwright {@code MatShowCase}. It reuses
 * the individual {@code MatXxxTestCases} classes, each of which targets a specific
 * component on the archived v12 material.angular.io documentation site.</p>
 *
 * <p>Usage:</p>
 * <pre>{@code
 * // run all tests
 * MatShowCase.main(new String[]{});
 *
 * // run a single test by name
 * MatShowCase.main(new String[]{"testButtons"});
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.15
 */
@SuppressWarnings("all")
public class MatShowCase extends AbstractBrowserSupport {

    /**
     * Main entry point. Starts the Edge driver, runs all mat component tests and
     * prints a summary report.
     *
     * <p>Optional first argument or {@code MAT_FILTER} environment variable to run
     * a single test by name (e.g. {@code "testButtons"}).</p>
     *
     * @param args optional: first argument is the test name filter
     */
    public static void main(String[] args) {
        MatShowCase runner = new MatShowCase();
        runner.setUpDriver(EDGE);

        String filter = args.length > 0 ? args[0] : System.getenv("MAT_FILTER");

        // --- Instantiate each test case class (they share the static driver) ---
        MatAutocompleteTestCases autocomplete = new MatAutocompleteTestCases();
        MatBadgeTestCases badge = new MatBadgeTestCases();
        MatBottomSheetTestCases bottomSheet = new MatBottomSheetTestCases();
        MatButtonTestCases button = new MatButtonTestCases();
        MatButtonToggleTestCases buttonToggle = new MatButtonToggleTestCases();
        MatCheckboxTestCases checkbox = new MatCheckboxTestCases();
        MatChipListTestCases chipList = new MatChipListTestCases();
        MatDialogTestCases dialog = new MatDialogTestCases();
        MatExpansionPanelTestCases expansionPanel = new MatExpansionPanelTestCases();
        MatFormFieldTestCases formField = new MatFormFieldTestCases();
        MatGridTestCases grid = new MatGridTestCases();
        MatListTestCases list = new MatListTestCases();
        MatMenuItemTestCases menuItem = new MatMenuItemTestCases();
        MatProgressBarTestCases progressBar = new MatProgressBarTestCases();
        MatSlideToggleTestCases slideToggle = new MatSlideToggleTestCases();
        MatSliderTestCases slider = new MatSliderTestCases();
        MatSnackbarTestCases snackbar = new MatSnackbarTestCases();

        try {
            // ---- Form Controls ----
            runner.runTestClass("MatButtonTestCases", () -> {
                runner.runIf(filter, "testButtons", button::testButtons);
            });

            runner.runTestClass("MatCheckboxTestCases", () -> {
                runner.runIf(filter, "testCheckbox", checkbox::testCheckbox);
            });

            runner.runTestClass("MatSlideToggleTestCases", () -> {
                runner.runIf(filter, "testSliderConfiguration", slideToggle::testSliderConfiguration);
            });

            runner.runTestClass("MatButtonToggleTestCases", () -> {
                runner.runIf(filter, "testButtonToggleGroup", buttonToggle::testButtonToggleGroup);
            });

            runner.runTestClass("MatSliderTestCases", () -> {
                runner.runIf(filter, "testConfigurableSlider", slider::testConfigurableSlider);
            });

            runner.runTestClass("MatAutocompleteTestCases", () -> {
                runner.runIf(filter, "testAutocomplete", autocomplete::testAutocomplete);
            });

            // ---- Data Display ----
            runner.runTestClass("MatBadgeTestCases", () -> {
                runner.runIf(filter, "testBadge", badge::testBadge);
            });

            runner.runTestClass("MatChipListTestCases", () -> {
                runner.runIf(filter, "testChipList", chipList::testChipList);
            });

            runner.runTestClass("MatGridTestCases", () -> {
                runner.runIf(filter, "testGrid", grid::testGrid);
            });

            runner.runTestClass("MatListTestCases", () -> {
                runner.runIf(filter, "testList", list::testList);
                runner.runIf(filter, "testListWithSelection", list::testListWithSelection);
                runner.runIf(filter, "testListWithSingleSelection", list::testListWithSingleSelection);
            });

            runner.runTestClass("MatProgressBarTestCases", () -> {
                runner.runIf(filter, "testBufferProgressBar", progressBar::testBufferProgressBar);
                runner.runIf(filter, "testConfigurableProgressBar", progressBar::testConfigurableProgressBar);
                runner.runIf(filter, "testIndeterminateProgressBar", progressBar::testIndeterminateProgressBar);
                runner.runIf(filter, "testQueryProgressBar", progressBar::testQueryProgressBar);
            });

            // ---- Layout ----
            runner.runTestClass("MatExpansionPanelTestCases", () -> {
                runner.runIf(filter, "testExpansionPanel", expansionPanel::testExpansionPanel);
            });

            runner.runTestClass("MatFormFieldTestCases", () -> {
                runner.runIf(filter, "navigate", formField::navigate);
                runner.runIf(filter, "testAppearance", formField::testAppearance);
                runner.runIf(filter, "testError", formField::testError);
                runner.runIf(filter, "testHints", formField::testHints);
                runner.runIf(filter, "testPrefixSuffix", formField::testPrefixSuffix);
            });

            // ---- Navigation ----
            runner.runTestClass("MatMenuItemTestCases", () -> {
                runner.runIf(filter, "testMenuWithIcons", menuItem::testMenuWithIcons);
                runner.runIf(filter, "testNestedMenu", menuItem::testNestedMenu);
                runner.runIf(filter, "testNestedMenuComplexActions", menuItem::testNestedMenuComplexActions);
                runner.runIf(filter, "testSelection", menuItem::testSelection);
            });

            // ---- Popups & Modals ----
            runner.runTestClass("MatDialogTestCases", () -> {
                runner.runIf(filter, "testDialog", dialog::testDialog);
            });

            runner.runTestClass("MatSnackbarTestCases", () -> {
                runner.runIf(filter, "testSliderConfiguration", snackbar::testSliderConfiguration);
            });

            runner.runTestClass("MatBottomSheetTestCases", () -> {
                runner.runIf(filter, "testBottomSheet", bottomSheet::testBottomSheet);
            });

        } finally {
            runner.tearDownAndReport();
        }

        if (runner.anyFailure) {
            System.exit(1);
        }
    }
}
