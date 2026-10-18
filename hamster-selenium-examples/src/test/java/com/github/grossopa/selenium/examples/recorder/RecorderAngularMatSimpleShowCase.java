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
package com.github.grossopa.selenium.examples.recorder;

import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

/**
 * The example of recording a local Angular Material test application using ChromeDriver directly. It opens the test
 * app running at {@code http://localhost:4200}, scans multiple pages, selects elements by id and generates page
 * objects into {@code target/recorder-generated}.
 *
 * <p>Requires the Angular Material test app running at {@code http://localhost:4200} (start via {@code ng serve} in
 * the {@code hamster-recorder-test-app-mat} directory). Run this class as a normal Java application.</p>
 *
 * @author Jack Yin
 * @since 1.17
 * @see RecorderSession
 */
public class RecorderAngularMatSimpleShowCase {

    private static final String BASE_URL = "http://localhost:4200";

    /**
     * Runs the example.
     *
     * @param args the command line arguments, not used
     */
    public static void main(String[] args) {
        RecorderConfig config = RecorderConfig.builder()
                .framework(ComponentFramework.MAT)
                .keyAttribute("data-testid")
                .extraSelector("table")
                .outputDir(Path.of("hamster-selenium-examples", "target", "recorder-generated"))
                .basePackage("com.example.pageobjects")
                .build();

        // Use ChromeDriver directly with Selenium Manager
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);

        try (RecorderSession session = new RecorderSession(driver, config)) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // ===== Page 1: Inputs =====
            System.out.println("\n===== Recording Inputs Page =====");
            session.getDriver().get(BASE_URL + "/inputs");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btn-contained")));
            List<ScannedElement> inputs = session.scan();
            RecorderExampleSupport.printScannedElements(inputs);

            selectById(session, inputs, "btn-contained", "containedButton");
            selectById(session, inputs, "btn-outlined", "outlinedButton");
            selectById(session, inputs, "btn-text", "textButton");
            selectById(session, inputs, "btn-disabled", "disabledButton");
            selectById(session, inputs, "btn-with-icon", "sendButton");
            selectById(session, inputs, "icon-btn-add", "addIconButton");
            selectById(session, inputs, "fab-primary", "addFab");
            selectById(session, inputs, "fab-extended", "extendedFab");
            selectById(session, inputs, "tf-standard", "standardTextField");
            selectById(session, inputs, "tf-outlined", "outlinedTextField");
            selectById(session, inputs, "tf-filled", "filledTextField");
            selectById(session, inputs, "tf-password", "passwordField");
            selectById(session, inputs, "tf-error", "errorTextField");
            selectById(session, inputs, "tf-multiline", "multilineTextField");
            selectById(session, inputs, "select-framework", "frameworkSelect");
            selectById(session, inputs, "select-size", "sizeSelect");
            selectById(session, inputs, "checkbox-basic", "basicCheckbox");
            selectById(session, inputs, "checkbox-disabled", "disabledCheckbox");
            selectById(session, inputs, "switch-basic", "basicSwitch");
            selectById(session, inputs, "switch-disabled", "disabledSwitch");
            selectById(session, inputs, "slider-basic-input", "basicSlider");

            // ===== Page 2: Data Display =====
            System.out.println("\n===== Recording Data Display Page =====");
            session.getDriver().get(BASE_URL + "/data-display");
            // Note: Avatar, Badge, Chip, List, Divider are display-only elements without interactive roles.
            // The scanner only finds interactive elements (button, a, input, select, textarea, [role=button], etc.).
            // Only the table is scannable on this page (added via extraSelector).
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("users-table")));
            List<ScannedElement> dataDisplay = session.scan();
            RecorderExampleSupport.printScannedElements(dataDisplay);

            selectById(session, dataDisplay, "users-table", "usersTable");

            // ===== Page 3: Feedback =====
            System.out.println("\n===== Recording Feedback Page =====");
            session.getDriver().get(BASE_URL + "/feedback");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btn-open-dialog")));
            // Note: Dialog content, Alert cards, and Progress indicators are not interactive.
            // Only the trigger buttons are scannable.
            List<ScannedElement> feedback = session.scan();
            RecorderExampleSupport.printScannedElements(feedback);

            selectById(session, feedback, "btn-open-dialog", "openDialogButton");
            selectById(session, feedback, "btn-open-snackbar", "openSnackbarButton");
            selectById(session, feedback, "btn-open-bottom-sheet", "openBottomSheetButton");

            // ===== Page 4: Navigation =====
            System.out.println("\n===== Recording Navigation Page =====");
            session.getDriver().get(BASE_URL + "/navigation");
            // Note: Tabs, Accordion, Stepper are complex components whose wrapper elements are not directly
            // interactive. Only their inner buttons/links are scannable.
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btn-open-menu")));
            List<ScannedElement> navigation = session.scan();
            RecorderExampleSupport.printScannedElements(navigation);

            selectById(session, navigation, "btn-open-menu", "openMenuButton");
            selectById(session, navigation, "stepper-next", "stepperNextButton");
            selectById(session, navigation, "stepper-back", "stepperBackButton");

            // ===== Page 5: Surfaces =====
            System.out.println("\n===== Recording Surfaces Page =====");
            session.getDriver().get(BASE_URL + "/surfaces");
            // Note: Cards, Paper, and Toolbar wrapper elements are not interactive.
            // Only the buttons inside them are scannable.
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("card-btn-like")));
            List<ScannedElement> surfaces = session.scan();
            RecorderExampleSupport.printScannedElements(surfaces);

            selectById(session, surfaces, "card-btn-like", "likeButton");
            selectById(session, surfaces, "card-btn-share", "shareButton");
            selectById(session, surfaces, "toolbar-btn-fav", "toolbarFavoriteButton");
            selectById(session, surfaces, "toolbar-btn-share", "toolbarShareButton");

            // ===== Page 6: Locator Strategies =====
            System.out.println("\n===== Recording Locator Strategies Page =====");
            session.getDriver().get(BASE_URL + "/locator-strategies");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("unique-submit-btn")));
            List<ScannedElement> locators = session.scan();
            RecorderExampleSupport.printScannedElements(locators);

            selectById(session, locators, "unique-submit-btn", "submitBtn");
            selectById(session, locators, "unique-cancel-btn", "cancelBtn");
            selectById(session, locators, "unique-username", "usernameField");
            selectById(session, locators, "unique-email", "emailField");
            selectByTestId(session, locators, "action-save", "saveAction");
            selectByTestId(session, locators, "action-delete", "deleteAction");
            selectByTestId(session, locators, "status-label", "statusLabel");
            selectById(session, locators, "mixed-1", "primaryActionBtn");
            selectById(session, locators, "mixed-2", "searchQueryField");
            selectById(session, locators, "mixed-3", "termsCheckbox");

            // ===== Page 7: HTML Elements =====
            System.out.println("\n===== Recording HTML Elements Page =====");
            session.getDriver().get(BASE_URL + "/html-elements");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("html-input-name")));
            List<ScannedElement> html = session.scan();
            RecorderExampleSupport.printScannedElements(html);

            selectById(session, html, "html-input-name", "nameInput");
            selectById(session, html, "html-input-email", "emailInput");
            selectById(session, html, "html-input-password", "passwordInput");
            selectById(session, html, "html-textarea", "bioTextarea");
            selectById(session, html, "html-checkbox-terms", "termsCheckbox");
            selectById(session, html, "html-submit-btn", "submitBtn");
            selectById(session, html, "html-reset-btn", "resetBtn");
            selectById(session, html, "html-select-country", "countrySelect");
            selectById(session, html, "html-select-multi", "skillsSelect");
            selectById(session, html, "html-table-products", "productsTable");
            selectById(session, html, "html-link-home", "homeLink");
            selectById(session, html, "html-link-external", "externalLink");

            // ===== Page 8: Dynamic Route =====
            System.out.println("\n===== Recording Dynamic Route Page =====");
            session.getDriver().get(BASE_URL + "/users/1");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btn-save-entity")));
            // Note: Chip elements are display-only; only buttons are interactive on this page.
            List<ScannedElement> dynamic = session.scan();
            RecorderExampleSupport.printScannedElements(dynamic);

            selectById(session, dynamic, "btn-save-entity", "saveEntityBtn");
            selectById(session, dynamic, "btn-delete-entity", "deleteEntityBtn");

            // ===== Generate all page objects =====
            System.out.println("\n===== Generating Page Objects =====");
            List<Path> files = session.generate();
            RecorderExampleSupport.printGeneratedFiles(files);

            // ===== Print page summary =====
            System.out.println("\n===== Page Summary =====");
            session.getPages().forEach(page ->
                    System.out.printf("  Page: %s (%d elements)%n", page.getName(), page.getElements().size()));
        }
    }

    private static void selectById(RecorderSession session, List<ScannedElement> elements, String id,
            String fieldName) {
        elements.stream().filter(element -> id.equals(element.getAttributes().get("id"))).findFirst().ifPresentOrElse(
                element -> session.select(element.getIndex(), fieldName),
                () -> System.out.println("  [SKIP] Element with id '" + id + "' not found"));
    }

    private static void selectByTestId(RecorderSession session, List<ScannedElement> elements, String testId,
            String fieldName) {
        elements.stream()
                .filter(element -> testId.equals(element.getAttributes().get("data-testid")))
                .findFirst().ifPresentOrElse(
                        element -> session.select(element.getIndex(), fieldName),
                        () -> System.out.println("  [SKIP] Element with data-testid '" + testId + "' not found"));
    }
}
