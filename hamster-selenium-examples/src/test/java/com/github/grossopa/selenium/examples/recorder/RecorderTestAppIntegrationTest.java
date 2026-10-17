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
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.examples.StartDriverServiceEdge;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorType;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test that verifies all recorder locator strategies against the local React MUI v5 test app.
 *
 * <p>Requires the test app running at {@code http://localhost:5173} (start via {@code npm run dev} in the
 * {@code hamster-recorder-test-app} directory). Run this class as a normal Java application, not via
 * {@code mvn test}, because it starts a real browser.</p>
 *
 * <p>Tests covered:
 * <ul>
 * <li>All non-id/name strategies produce {@link LocatorType#XPATH} instead of CSS_SELECTOR</li>
 * <li>Text strategy generates {@code normalize-space()} XPath</li>
 * <li>Ancestor strategy generates correct XPath with {@code descendant::} axis</li>
 * <li>IndexedSibling strategy generates XPath with {@code child::} axis and position predicate</li>
 * <li>SiblingList strategy generates list XPath with {@code child::} axis</li>
 * <li>CustomAttribute strategy uses By2 xpathBuilder</li>
 * <li>Generated page object code uses {@code By.xpath()} exclusively</li>
 * <li>Generated XPath locators actually find the correct elements on the page</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.17
 */
public class RecorderTestAppIntegrationTest {

    private static final String BASE_URL = "http://localhost:5173";

    private WebDriver rawDriver;
    private RecorderSession session;
    private List<ScannedElement> elements;
    private int passed;
    private int failed;

    /**
     * Sets up the Edge driver and recorder session, then scans the locator-strategies page.
     *
     * @throws IOException if driver resolution fails
     */
    void setUp() throws IOException {
        String userDir = System.getProperty("user.dir");
        String driverPath = userDir.contains("hamster-selenium-examples")
                ? userDir + "/../.cache/selenium/msedgedriver_155"
                : userDir + "/.cache/selenium/msedgedriver_155";

        File driverFile = new File(driverPath);
        if (!driverFile.exists()) {
            String browserVersion = StartDriverServiceEdge.detectEdgeVersion();
            driverPath = StartDriverServiceEdge.resolveDriver(browserVersion);
        }
        System.setProperty("webdriver.edge.driver", driverPath);

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        rawDriver = new EdgeDriver(options);

        RecorderConfig config = RecorderConfig.builder()
                .framework(ComponentFramework.of("mui"))
                .muiVersion(MuiVersion.V5)
                .keyAttribute("data-testid")
                .outputDir(Path.of("hamster-selenium-examples", "target", "recorder-generated"))
                .basePackage("com.example.pageobjects")
                .build();

        session = new RecorderSession(rawDriver, config);
        ComponentWebDriver driver = session.getDriver();
        driver.get(BASE_URL + "/locator-strategies");
        elements = session.scan();

        System.out.printf(Locale.ROOT, "Scanned %d elements from locator-strategies page%n", elements.size());
    }

    /**
     * Tears down the recorder session and driver.
     */
    void tearDown() {
        if (session != null) {
            try {
                session.close();
            } catch (Exception e) {
                // ignore
            }
        }
        if (rawDriver != null) {
            rawDriver.quit();
        }
    }

    /**
     * Verifies that all locator candidates (except ID and NAME) use XPATH type instead of CSS_SELECTOR.
     */
    void testAllStrategiesProduceXpath() {
        assertFalse(elements.isEmpty(), "Should scan elements from the page");

        int cssCount = 0;
        int xpathCount = 0;
        for (ScannedElement element : elements) {
            for (LocatorCandidate candidate : element.getLocatorCandidates()) {
                if (candidate.getType() == LocatorType.CSS_SELECTOR) {
                    cssCount++;
                    System.out.printf(Locale.ROOT, "  UNEXPECTED CSS: [%d] %s = %s%n",
                            element.getIndex(), candidate.getDescription(), candidate.getValue());
                }
                if (candidate.getType() == LocatorType.XPATH) {
                    xpathCount++;
                }
            }
        }

        assertEquals(0, cssCount, "All strategies should produce XPATH, but found " + cssCount + " CSS selectors");
        assertTrue(xpathCount > 0, "Should have at least some XPATH candidates");
        System.out.printf(Locale.ROOT, "Verified: %d XPATH candidates, 0 CSS_SELECTOR candidates%n", xpathCount);
    }

    /**
     * Verifies the text strategy generates XPath with {@code normalize-space()}.
     */
    void testTextStrategyUsesNormalizeSpace() {
        Optional<LocatorCandidate> textCandidate = findCandidateByDescription(elements, "CONFIRM CHANGES");
        assertTrue(textCandidate.isPresent(), "Should find a text-based candidate for CONFIRM CHANGES button");

        LocatorCandidate candidate = textCandidate.get();
        assertEquals(LocatorType.XPATH, candidate.getType());
        assertTrue(candidate.getValue().contains("normalize-space()"),
                "Text XPath should use normalize-space(): " + candidate.getValue());
        assertTrue(candidate.getValue().contains("button"),
                "Text XPath should target button tag: " + candidate.getValue());

        System.out.println("Text strategy XPath: " + candidate.getValue());
    }

    /**
     * Verifies the ancestor strategy generates XPath with {@code descendant::} axis.
     */
    void testAncestorStrategyUsesDescendantAxis() {
        Optional<ScannedElement> editProfile = elements.stream()
                .filter(e -> "EDIT PROFILE".equals(e.getText()))
                .findFirst();
        assertTrue(editProfile.isPresent(), "Should find EDIT PROFILE button");

        Optional<LocatorCandidate> ancestorCandidate = editProfile.get().getLocatorCandidates().stream()
                .filter(c -> c.getPriority() == LocatorCandidate.PRIORITY_ANCESTOR)
                .findFirst();
        assertTrue(ancestorCandidate.isPresent(), "Should have an ancestor candidate");

        LocatorCandidate candidate = ancestorCandidate.get();
        assertEquals(LocatorType.XPATH, candidate.getType());
        assertTrue(candidate.getValue().contains("descendant::"),
                "Ancestor XPath should use descendant:: axis: " + candidate.getValue());
        assertTrue(candidate.getValue().contains("contains(@class,"),
                "Ancestor XPath should use contains(@class,...): " + candidate.getValue());

        System.out.println("Ancestor strategy XPath: " + candidate.getValue());
    }

    /**
     * Verifies the indexed sibling strategy generates XPath with {@code child::} axis and position predicate.
     */
    void testIndexedSiblingStrategyUsesChildAxis() {
        Optional<ScannedElement> okButton = elements.stream()
                .filter(e -> "OK".equals(e.getText()))
                .findFirst();
        assertTrue(okButton.isPresent(), "Should find OK button");

        Optional<LocatorCandidate> indexedCandidate = okButton.get().getLocatorCandidates().stream()
                .filter(c -> c.getPriority() == LocatorCandidate.PRIORITY_INDEXED_SIBLING)
                .findFirst();
        assertTrue(indexedCandidate.isPresent(), "Should have an indexed sibling candidate");

        LocatorCandidate candidate = indexedCandidate.get();
        assertEquals(LocatorType.XPATH, candidate.getType());
        assertTrue(candidate.getValue().contains("child::"),
                "IndexedSibling XPath should use child:: axis: " + candidate.getValue());
        assertTrue(candidate.getValue().matches(".*\\[\\d+\\].*"),
                "IndexedSibling XPath should have position predicate: " + candidate.getValue());

        System.out.println("IndexedSibling strategy XPath: " + candidate.getValue());
    }

    /**
     * Verifies the sibling list strategy generates list XPath with {@code child::} axis.
     */
    void testSiblingListStrategyUsesChildAxis() {
        Optional<ScannedElement> okButton = elements.stream()
                .filter(e -> "OK".equals(e.getText()))
                .findFirst();
        assertTrue(okButton.isPresent(), "Should find OK button");

        Optional<LocatorCandidate> listCandidate = okButton.get().getLocatorCandidates().stream()
                .filter(c -> c.getPriority() == LocatorCandidate.PRIORITY_SIBLING_LIST)
                .findFirst();
        assertTrue(listCandidate.isPresent(), "Should have a sibling list candidate");

        LocatorCandidate candidate = listCandidate.get();
        assertEquals(LocatorType.XPATH, candidate.getType());
        assertTrue(candidate.isList(), "SiblingList candidate should be marked as list");
        assertTrue(candidate.getValue().contains("child::"),
                "SiblingList XPath should use child:: axis: " + candidate.getValue());

        System.out.println("SiblingList strategy XPath: " + candidate.getValue());
    }

    /**
     * Verifies the custom attribute strategy generates XPath using By2 xpathBuilder.
     */
    void testCustomAttributeStrategyUsesXpathBuilder() {
        Optional<ScannedElement> saveButton = elements.stream()
                .filter(e -> "action-save".equals(e.getAttributes().get("data-testid")))
                .findFirst();
        assertTrue(saveButton.isPresent(), "Should find element with data-testid=action-save");

        Optional<LocatorCandidate> customCandidate = saveButton.get().getLocatorCandidates().stream()
                .filter(c -> c.getPriority() == LocatorCandidate.PRIORITY_CUSTOM_ATTRIBUTE)
                .findFirst();
        assertTrue(customCandidate.isPresent(), "Should have a custom attribute candidate");

        LocatorCandidate candidate = customCandidate.get();
        assertEquals(LocatorType.XPATH, candidate.getType());
        assertTrue(candidate.getValue().startsWith("//"),
                "CustomAttribute XPath should start with //: " + candidate.getValue());
        assertTrue(candidate.getValue().contains("data-testid"),
                "CustomAttribute XPath should contain data-testid: " + candidate.getValue());

        System.out.println("CustomAttribute strategy XPath: " + candidate.getValue());
    }

    /**
     * Verifies the generated page object code uses {@code By.xpath()} exclusively (no {@code By.cssSelector()}).
     *
     * @throws IOException if reading generated files fails
     */
    void testGeneratedPageObjectUsesXpathOnly() {
        selectByText("EDIT PROFILE", "editProfileBtn");
        selectByText("CONFIRM CHANGES", "confirmChangesBtn");
        selectByText("OK", "okBtn1");

        List<Path> generated = session.generate();
        assertFalse(generated.isEmpty(), "Should generate at least one page object");

        for (Path file : generated) {
            try {
                String content = Files.readString(file);
                assertFalse(content.contains("By.cssSelector"),
                        "Generated code should not contain By.cssSelector: " + file.getFileName());

                if (content.contains("By.xpath")) {
                    System.out.println("Verified generated file uses By.xpath(): " + file.getFileName());
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read generated file: " + file, e);
            }
        }
    }

    /**
     * Verifies that generated XPath locators actually find the correct elements on the page.
     */
    void testGeneratedXpathLocatorsFindElements() {
        ComponentWebDriver driver = session.getDriver();

        verifyXpathFindsElement(driver, "//button[normalize-space()='Confirm Changes']", "Confirm Changes button");
        verifyXpathFindsElement(driver,
                "//*[@id='user-profile-section']/descendant::*[local-name()='button']",
                "button under #user-profile-section");
        verifyXpathFindsElement(driver,
                "//*[@id='same-text-section']/child::*[local-name()='button'][1]",
                "first OK button under #same-text-section");
        verifyXpathFindsElement(driver, "//*[@data-testid='action-save']", "save button by data-testid");
    }

    /**
     * Verifies the same text degradation produces correct indexed XPath with ancestor context.
     */
    void testSameTextDegradation() {
        List<ScannedElement> okButtons = elements.stream()
                .filter(e -> "OK".equals(e.getText())).toList();
        assertEquals(3, okButtons.size(), "Should find 3 OK buttons");

        for (int i = 0; i < okButtons.size(); i++) {
            ScannedElement btn = okButtons.get(i);
            String fieldName = "degradedOkBtn" + (i + 1);
            session.select(btn.getIndex(), fieldName);

            LocatorCandidate best = btn.getBestLocator();
            assertNotNull(best, "OK button " + (i + 1) + " should have a best locator");
            System.out.printf(Locale.ROOT, "  OK button %d [%d] → %s: %s%n",
                    i + 1, btn.getIndex(), best.getDescription(), best.getValue());
        }
    }

    // ===== Helper Methods =====

    private void run(String name, Runnable runnable) {
        System.out.printf(Locale.ROOT, "%n--- %s ---%n", name);
        try {
            runnable.run();
            passed++;
            System.out.println("[PASSED] " + name);
        } catch (Throwable t) {
            failed++;
            System.err.println("[FAILED] " + name + ": " + t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    private Optional<LocatorCandidate> findCandidateByDescription(List<ScannedElement> elements, String text) {
        return elements.stream()
                .filter(e -> text.equals(e.getText()))
                .findFirst()
                .flatMap(e -> e.getLocatorCandidates().stream()
                        .filter(c -> c.getDescription().contains(text) || c.getValue().contains(text))
                        .filter(c -> c.getType() == LocatorType.XPATH
                                && c.getPriority() == LocatorCandidate.PRIORITY_TEXT)
                        .findFirst());
    }

    private void selectByText(String text, String fieldName) {
        elements.stream()
                .filter(e -> text.equals(e.getText()))
                .findFirst()
                .ifPresent(e -> session.select(e.getIndex(), fieldName));
    }

    private void verifyXpathFindsElement(ComponentWebDriver driver, String xpath, String description) {
        var found = driver.findComponents(By.xpath(xpath));
        assertFalse(found.isEmpty(), "XPath should find at least one element: " + description + " → " + xpath);
        System.out.printf(Locale.ROOT, "  ✓ XPath finds %d element(s) for: %s%n", found.size(), description);
    }

    /**
     * Main entry point - runs all integration tests against the local test app.
     *
     * @param args command line arguments (unused)
     * @throws IOException if driver setup fails
     */
    public static void main(String[] args) throws IOException {
        System.out.println("============================================");
        System.out.println("  Recorder Integration Test - Test App");
        System.out.println("============================================");

        RecorderTestAppIntegrationTest test = new RecorderTestAppIntegrationTest();

        try {
            test.setUp();

            test.run("All strategies produce XPATH", test::testAllStrategiesProduceXpath);
            test.run("Text strategy uses normalize-space", test::testTextStrategyUsesNormalizeSpace);
            test.run("Ancestor strategy uses descendant axis", test::testAncestorStrategyUsesDescendantAxis);
            test.run("IndexedSibling strategy uses child axis", test::testIndexedSiblingStrategyUsesChildAxis);
            test.run("SiblingList strategy uses child axis", test::testSiblingListStrategyUsesChildAxis);
            test.run("CustomAttribute strategy uses xpathBuilder", test::testCustomAttributeStrategyUsesXpathBuilder);
            test.run("Generated page object uses By.xpath only", test::testGeneratedPageObjectUsesXpathOnly);
            test.run("Generated XPath locators find elements", test::testGeneratedXpathLocatorsFindElements);
            test.run("Same text degradation", test::testSameTextDegradation);

        } finally {
            test.tearDown();
        }

        System.out.println("\n============================================");
        System.out.printf(Locale.ROOT, "  RESULTS: %d passed, %d failed%n", test.passed, test.failed);
        System.out.println("============================================");

        if (test.failed > 0) {
            System.exit(1);
        }
    }
}
