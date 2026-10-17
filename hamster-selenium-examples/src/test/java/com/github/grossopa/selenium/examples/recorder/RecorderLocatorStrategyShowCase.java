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

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.examples.StartDriverServiceEdge;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * Tests the locator strategies on the local React MUI v5 test app, focusing on verifying the new
 * {@code IndexedSiblingLocatorCandidateStrategy} and the updated {@code TextLocatorCandidateStrategy} priority.
 *
 * <p>Requires the React MUI test app running at {@code http://localhost:5173} (start via {@code npm run dev} in the
 * {@code hamster-recorder-test-app} directory). Run this class as a normal Java application.</p>
 *
 * @author Jack Yin
 * @since 1.17
 */
public class RecorderLocatorStrategyShowCase {

    private static final String BASE_URL = "http://localhost:5173";

    /**
     * Runs the locator strategy verification.
     *
     * @param args the command line arguments, not used
     * @throws IOException if driver resolution fails
     */
    public static void main(String[] args) throws IOException {
        RecorderConfig config = RecorderConfig.builder()
                .framework(ComponentFramework.of("mui"))
                .muiVersion(MuiVersion.V5)
                .keyAttribute("data-testid")
                .outputDir(Path.of("hamster-selenium-examples", "target", "recorder-generated"))
                .basePackage("com.example.pageobjects")
                .build();

        String browserVersion = StartDriverServiceEdge.detectEdgeVersion();
        String driverPath = StartDriverServiceEdge.resolveDriver(browserVersion);
        System.setProperty("webdriver.edge.driver", driverPath);

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new EdgeDriver(options);

        try (RecorderSession session = new RecorderSession(driver, config)) {

            // ===== Locator Strategies Page =====
            System.out.println("===== Locator Strategies Page =====");
            session.getDriver().get(BASE_URL + "/locator-strategies");
            List<ScannedElement> elements = session.scan();

            System.out.println("\n--- All scanned elements with locator candidates ---");
            for (ScannedElement element : elements) {
                String type = element.getDetectedComponent() != null
                        ? element.getDetectedComponent().getTypeName()
                        : element.getTagName();
                System.out.printf(Locale.ROOT, "%n  [%2d] %-14s text=\"%s\" attrs=%s%n",
                        element.getIndex(), type, element.getText(), element.getAttributes());
                for (LocatorCandidate candidate : element.getLocatorCandidates()) {
                    String listMark = candidate.isList() ? " [LIST]" : "";
                    System.out.printf(Locale.ROOT, "       priority=%-3d %-10s %s%s%n",
                            candidate.getPriority(), candidate.getType(), candidate.getValue(), listMark);
                }
            }

            // ===== Section 4: Ancestor + Indexed Sibling =====
            // Buttons inside #user-profile-section: "Edit Profile", "View History"
            // These have no id/name/data-testid, but ancestor has id
            // Note: MUI buttons use CSS text-transform: uppercase, so innerText is uppercase
            System.out.println("\n\n--- Section 4: Ancestor + Indexed Sibling ---");
            selectAndPrint(session, elements, "EDIT PROFILE", "editProfileBtn");
            selectAndPrint(session, elements, "VIEW HISTORY", "viewHistoryBtn");

            // Buttons inside [data-testid="order-section"]: "Place Order", "View Cart"
            selectAndPrint(session, elements, "PLACE ORDER", "placeOrderBtn");
            selectAndPrint(session, elements, "VIEW CART", "viewCartBtn");

            // Buttons inside #settings-section: "Apply", "Reset"
            selectAndPrint(session, elements, "APPLY", "applyBtn");
            selectAndPrint(session, elements, "RESET", "resetBtn");

            // ===== Section 5: Sibling List + Indexed Sibling =====
            // Buttons inside #button-group-test: "Action A" ~ "Action D"
            System.out.println("\n--- Section 5: Sibling List + Indexed Sibling ---");
            selectAndPrint(session, elements, "ACTION A", "actionABtn");
            selectAndPrint(session, elements, "ACTION B", "actionBBtn");
            selectAndPrint(session, elements, "ACTION C", "actionCBtn");
            selectAndPrint(session, elements, "ACTION D", "actionDBtn");

            // ===== Section 6: Text-based =====
            // Buttons with unique text: "Confirm Changes", "Discard Draft", "Learn More"
            System.out.println("\n--- Section 6: Text-based ---");
            selectAndPrint(session, elements, "CONFIRM CHANGES", "confirmChangesBtn");
            selectAndPrint(session, elements, "DISCARD DRAFT", "discardDraftBtn");
            selectAndPrint(session, elements, "LEARN MORE", "learnMoreBtn");

            // ===== Section 7: Mixed Strategy =====
            System.out.println("\n--- Section 7: Mixed Strategy (id > name > text > custom-attr) ---");
            selectById(session, elements, "mixed-1", "primaryActionBtn");
            selectById(session, elements, "mixed-2", "searchQueryField");

            // ===== Section 8: Same Text Degradation =====
            // Multiple siblings with the same text "OK" and "Submit"
            // Text locator should be non-unique and degrade to ancestor/indexed-sibling
            // Uses plain HTML buttons (not MUI) as direct children of ancestor sections
            System.out.println("\n--- Section 8: Same Text Degradation ---");
            System.out.println("  (3 plain buttons with same text 'OK' under #same-text-section)");
            System.out.println("  (3 plain buttons with same text 'Submit' under #same-text-no-ancestor)");
            // Find all "OK" buttons and select them
            List<ScannedElement> okButtons = elements.stream()
                    .filter(e -> "OK".equals(e.getText())).toList();
            for (int i = 0; i < okButtons.size(); i++) {
                ScannedElement okBtn = okButtons.get(i);
                session.select(okBtn.getIndex(), "okBtn" + (i + 1));
                System.out.printf(Locale.ROOT, "  Selected [%2d] \"OK\" #%d as okBtn%d → best locator: %s%n",
                        okBtn.getIndex(), i + 1, i + 1,
                        okBtn.getBestLocator() != null ? okBtn.getBestLocator().getDescription() : "-");
            }
            List<ScannedElement> submitButtons = elements.stream()
                    .filter(e -> "Submit".equals(e.getText())).toList();
            for (int i = 0; i < submitButtons.size(); i++) {
                ScannedElement submitBtn = submitButtons.get(i);
                session.select(submitBtn.getIndex(), "submitBtn" + (i + 1));
                System.out.printf(Locale.ROOT, "  Selected [%2d] \"Submit\" #%d as submitBtn%d → best locator: %s%n",
                        submitBtn.getIndex(), i + 1, i + 1,
                        submitBtn.getBestLocator() != null ? submitBtn.getBestLocator().getDescription() : "-");
            }

            // ===== Generate page objects =====
            System.out.println("\n\n===== Generating Page Objects =====");
            List<Path> files = session.generate();
            RecorderExampleSupport.printGeneratedFiles(files);
        }
    }

    private static void selectAndPrint(RecorderSession session, List<ScannedElement> elements, String text,
            String fieldName) {
        elements.stream()
                .filter(e -> text.equals(e.getText()))
                .findFirst()
                .ifPresentOrElse(
                        e -> {
                            session.select(e.getIndex(), fieldName);
                            System.out.printf(Locale.ROOT, "  Selected [%2d] \"%s\" as %s → best locator: %s%n",
                                    e.getIndex(), text, fieldName,
                                    e.getBestLocator() != null ? e.getBestLocator().getDescription() : "-");
                        },
                        () -> System.out.println("  [SKIP] Element with text '" + text + "' not found"));
    }

    private static void selectById(RecorderSession session, List<ScannedElement> elements, String id,
            String fieldName) {
        elements.stream()
                .filter(e -> id.equals(e.getAttributes().get("id")))
                .findFirst()
                .ifPresentOrElse(
                        e -> {
                            session.select(e.getIndex(), fieldName);
                            System.out.printf(Locale.ROOT, "  Selected [%2d] id=\"%s\" as %s → best locator: %s%n",
                                    e.getIndex(), id, fieldName,
                                    e.getBestLocator() != null ? e.getBestLocator().getDescription() : "-");
                        },
                        () -> System.out.println("  [SKIP] Element with id '" + id + "' not found"));
    }
}
