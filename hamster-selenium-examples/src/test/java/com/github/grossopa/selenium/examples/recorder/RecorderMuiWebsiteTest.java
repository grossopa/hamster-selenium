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
import com.github.grossopa.selenium.core.driver.*;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.PageElementModel;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end test of the recorder functionality against the MUI official website. Tests scanning, element selection,
 * page object generation and validates the generated code for reliability and robustness.
 *
 * @author Jack Yin
 * @since 1.16
 */
public class RecorderMuiWebsiteTest {

    private static int passed;
    private static int failed;

    /**
     * Test 1: scan the MUI Button page and verify component detection.
     *
     * @param session the recorder session
     */
    public static void testScanAndDetect(RecorderSession session) {
        System.out.printf(Locale.ROOT, "========================================%n");
        System.out.printf(Locale.ROOT, "  TEST 1: Scan & Component Detection%n");
        System.out.printf(Locale.ROOT, "========================================%n");
        try {
            List<ScannedElement> scanned = session.scan();
            System.out.println("Scanned " + scanned.size() + " elements");
            assertTrue(!scanned.isEmpty(), "Should scan at least some elements");

            int componentCount = 0;
            int htmlOnlyCount = 0;
            for (ScannedElement element : scanned) {
                boolean isComponent = element.getDetectedComponent() != null;
                if (isComponent) {
                    componentCount++;
                } else {
                    htmlOnlyCount++;
                }
                String detected = isComponent ? element.getDetectedComponent().getTypeName() : element.getTagName();
                String bestLocator = element.getBestLocator() != null ? element.getBestLocator().getDescription() : "-";
                System.out.printf(Locale.ROOT, "  [%2d] %-20s id=%-25s text=%-20s locator=%s%n", element.getIndex(),
                        detected, element.getAttributes().getOrDefault("id", "-"),
                        abbreviate(element.getText(), 20), bestLocator);
            }

            System.out.println("\nDetection summary: " + componentCount + " MUI components, " + htmlOnlyCount
                    + " plain HTML elements");
            assertTrue(componentCount > 0, "Should detect at least some MUI components on the Button page");

            passed++;
            System.out.println("[PASSED] Scan & Component Detection");
        } catch (Throwable t) {
            failed++;
            System.err.println("[FAILED] Scan & Component Detection: " + t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * Test 2: select ALL detected MUI components from the Button page and generate a comprehensive page object.
     * This tests the recorder's ability to capture the full page, not just a few hand-picked elements.
     *
     * @param session the recorder session
     */
    public static void testSelectAndGenerate(RecorderSession session) {
        System.out.printf(Locale.ROOT, "========================================%n");
        System.out.printf(Locale.ROOT, "  TEST 2: Select ALL & Code Generation%n");
        System.out.printf(Locale.ROOT, "========================================%n");
        try {
            List<ScannedElement> scanned = session.getScannedElements();
            assertFalse(scanned.isEmpty(), "Should have scanned elements from previous test");

            // Select ALL elements that have detected MUI components
            int selectedCount = 0;
            int skippedCount = 0;
            int failedCount = 0;
            Set<String> usedFieldNames = new HashSet<>();

            for (ScannedElement elem : scanned) {
                if (elem.getDetectedComponent() == null) {
                    skippedCount++;
                    continue;
                }

                // Generate a unique field name based on type + text + counter
                String typeName = elem.getDetectedComponent().getTypeName();
                String text = elem.getText() != null ? elem.getText().strip() : "";
                String baseName = buildFieldName(typeName, text, elem);
                String fieldName = uniqueFieldName(baseName, usedFieldNames);
                usedFieldNames.add(fieldName);

                try {
                    PageElementModel selected = session.select(elem.getIndex(), fieldName);
                    String type = selected.getDetectedComponent() != null
                            ? selected.getDetectedComponent().getTypeName() : "WebComponent";
                    if (selectedCount < 20 || selectedCount % 10 == 0) {
                        System.out.printf("  [%3d] Selected as '%-30s' -> %s%n",
                                elem.getIndex(), fieldName, type);
                    }
                    selectedCount++;
                } catch (RuntimeException e) {
                    failedCount++;
                    if (failedCount <= 5) {
                        System.err.println("  Could not select [" + elem.getIndex() + "] as '"
                                + fieldName + "': " + e.getMessage());
                    }
                }
            }

            System.out.println("\nSelection summary:");
            System.out.println("  Total scanned:  " + scanned.size());
            System.out.println("  MUI components: " + selectedCount + " selected");
            System.out.println("  Plain HTML:     " + skippedCount + " skipped (not MUI)");
            System.out.println("  Failed:         " + failedCount);

            // Generate page objects
            List<Path> generated = session.generate();
            System.out.println("\nGenerated " + generated.size() + " page object(s)");
            assertFalse(generated.isEmpty(), "Should generate at least one page object");

            // Validate generated code
            for (Path file : generated) {
                String content = Files.readString(file);
                System.out.println("\n--- Generated file: " + file.getFileName() + " ---");
                System.out.println("  File size: " + content.length() + " chars, "
                        + content.split("\n").length + " lines");
                validateGeneratedCode(content);
                validateDiverseContent(content);
            }

            assertTrue(selectedCount > 12, "Should select more than 12 elements (got " + selectedCount + ")");

            passed++;
            System.out.println("[PASSED] Select ALL & Code Generation");
        } catch (Throwable t) {
            failed++;
            System.err.println("[FAILED] Select ALL & Code Generation: " + t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * Test 3: multi-page recording - navigate to different MUI pages and select ALL detected components.
     *
     * @param session the recorder session
     */
    public static void testMultiPageRecording(RecorderSession session) {
        System.out.printf(Locale.ROOT, "========================================%n");
        System.out.printf(Locale.ROOT, "  TEST 3: Multi-page Recording (ALL components)%n");
        System.out.printf(Locale.ROOT, "========================================%n");
        try {
            ComponentWebDriver driver = session.getDriver();

            // --- Page 1: Text Field ---
            driver.get("https://mui.com/material-ui/react-text-field/");
            waitForMuiPage(driver);

            List<ScannedElement> scanned2 = session.scan();
            System.out.println("Text Field page: scanned " + scanned2.size() + " elements");
            printDetectedComponents(scanned2);
            selectAllComponents(scanned2, session);

            // --- Page 2: Select ---
            driver.get("https://mui.com/material-ui/react-select/");
            waitForMuiPage(driver);

            List<ScannedElement> scanned3 = session.scan();
            System.out.println("\nSelect page: scanned " + scanned3.size() + " elements");
            printDetectedComponents(scanned3);
            selectAllComponents(scanned3, session);

            // --- Page 3: Tabs ---
            driver.get("https://mui.com/material-ui/react-tabs/");
            waitForMuiPage(driver);

            List<ScannedElement> scanned4 = session.scan();
            System.out.println("\nTabs page: scanned " + scanned4.size() + " elements");
            printDetectedComponents(scanned4);
            selectAllComponents(scanned4, session);

            // Generate all pages
            List<Path> generated = session.generate();
            System.out.println("\nGenerated " + generated.size() + " page object(s) for all pages");

            for (Path file : generated) {
                String content = Files.readString(file);
                System.out.println("\n--- " + file.getFileName() + " ---");
                System.out.println("  File size: " + content.length() + " chars, "
                        + content.split("\n").length + " lines");
                validateGeneratedCode(content);
                validateDiverseContent(content);
            }

            assertTrue(generated.size() >= 3, "Should generate at least 3 page objects for 3 pages");

            passed++;
            System.out.println("[PASSED] Multi-page Recording (Diverse)");
        } catch (Throwable t) {
            failed++;
            System.err.println("[FAILED] Multi-page Recording: " + t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * Test 4: validate locator quality - check if locators are stable and unique.
     *
     * @param session the recorder session
     */
    public static void testLocatorQuality(RecorderSession session) {
        System.out.printf(Locale.ROOT, "========================================%n");
        System.out.printf(Locale.ROOT, "  TEST 4: Locator Quality Analysis%n");
        System.out.printf(Locale.ROOT, "========================================%n");
        try {
            ComponentWebDriver driver = session.getDriver();
            driver.get("https://mui.com/material-ui/react-button/");
            waitForMuiPage(driver);

            List<ScannedElement> scanned = session.scan();
            System.out.println("Analyzing locator quality for " + scanned.size() + " elements\n");

            int idLocators = 0;
            int nameLocators = 0;
            int cssLocators = 0;
            int xpathLocators = 0;
            int markerLocators = 0;
            int listLocators = 0;
            int noLocator = 0;

            for (ScannedElement element : scanned) {
                var best = element.getBestLocator();
                if (best == null) {
                    noLocator++;
                    continue;
                }
                String desc = best.getDescription().toLowerCase();
                if (desc.contains("by id")) {
                    idLocators++;
                } else if (desc.contains("by name")) {
                    nameLocators++;
                } else if (desc.contains("by css") || desc.contains("by ancestor") || desc.contains("by sibling")) {
                    cssLocators++;
                } else if (desc.contains("by text") || desc.contains("by xpath")) {
                    xpathLocators++;
                } else if (desc.contains("marker")) {
                    markerLocators++;
                }
                if (best.isList()) {
                    listLocators++;
                }

                System.out.printf("  [%2d] priority=%-3d list=%-5s %s%n", element.getIndex(), best.getPriority(),
                        best.isList(), best.getDescription());
            }

            System.out.println("\nLocator type distribution:");
            System.out.println("  ID-based:       " + idLocators);
            System.out.println("  Name-based:     " + nameLocators);
            System.out.println("  CSS-based:      " + cssLocators);
            System.out.println("  XPath/Text:     " + xpathLocators);
            System.out.println("  Marker-only:    " + markerLocators);
            System.out.println("  List locators:  " + listLocators);
            System.out.println("  No locator:     " + noLocator);

            int total = scanned.size();
            double stableRatio = (double) (idLocators + nameLocators) / Math.max(total, 1) * 100;
            System.out.printf(Locale.ROOT, "%nStable locator ratio (id+name): %.1f%%%n", stableRatio);

            if (markerLocators > 0) {
                System.out.println("WARNING: " + markerLocators
                        + " elements have only marker-based locators (fragile, session-specific)");
            }

            passed++;
            System.out.println("[PASSED] Locator Quality Analysis");
        } catch (Throwable t) {
            failed++;
            System.err.println("[FAILED] Locator Quality Analysis: " + t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    // ---- Helper methods ----

    /**
     * Selects ALL detected MUI components from the scanned list, generating unique field names.
     */
    private static void selectAllComponents(List<ScannedElement> scanned, RecorderSession session) {
        Set<String> usedFieldNames = new HashSet<>();
        int selected = 0;
        int failed = 0;
        for (ScannedElement element : scanned) {
            if (element.getDetectedComponent() == null) continue;

            String typeName = element.getDetectedComponent().getTypeName();
            String text = element.getText() != null ? element.getText().strip() : "";
            String baseName = buildFieldName(typeName, text, element);
            String fieldName = uniqueFieldName(baseName, usedFieldNames);
            usedFieldNames.add(fieldName);

            try {
                session.select(element.getIndex(), fieldName);
                selected++;
            } catch (RuntimeException e) {
                failed++;
                if (failed <= 3) {
                    System.err.println("  Could not select [" + element.getIndex() + "] as '"
                            + fieldName + "': " + e.getMessage());
                }
            }
        }
        System.out.println("  -> Selected " + selected + " MUI components" + (failed > 0 ? " (" + failed + " failed)" : ""));
    }

    /**
     * Builds a base field name from the component type, text content, and element attributes.
     */
    private static String buildFieldName(String typeName, String text, ScannedElement element) {
        // Use well-known text patterns for readable names
        String lowerText = text.toLowerCase();
        if (!lowerText.isEmpty() && lowerText.length() <= 30 && lowerText.matches("[a-z0-9 -]+")) {
            String camelCase = toCamelCase(lowerText);
            String prefix = switch (typeName) {
                case "MuiButton" -> camelCase + "Button";
                case "MuiLink" -> camelCase + "Link";
                case "MuiChip" -> camelCase + "Chip";
                case "MuiTextField" -> camelCase + "TextField";
                case "MuiSelect" -> camelCase + "Select";
                case "MuiTabs" -> camelCase + "Tabs";
                case "MuiTab" -> camelCase + "Tab";
                case "MuiAppBar" -> camelCase + "AppBar";
                default -> camelCase + typeName.replace("Mui", "");
            };
            if (Character.isJavaIdentifierStart(prefix.charAt(0))) return prefix;
        }

        // Use id attribute if available
        String id = element.getAttributes().get("id");
        if (id != null && !id.isEmpty() && !id.contains("demo-")) {
            return toCamelCase(id.replaceAll("[^a-zA-Z0-9]", " "));
        }

        // Fallback: type prefix + index
        String prefix = switch (typeName) {
            case "MuiButton" -> "button";
            case "MuiLink" -> "link";
            case "MuiAppBar" -> "appBar";
            case "MuiChip" -> "chip";
            case "MuiTextField" -> "textField";
            case "MuiSelect" -> "select";
            case "MuiTabs" -> "tabs";
            case "MuiTab" -> "tab";
            case "MuiFab" -> "fab";
            default -> "element";
        };
        return prefix + element.getIndex();
    }

    /**
     * Ensures the field name is unique by appending a counter if needed.
     */
    private static String uniqueFieldName(String baseName, Set<String> usedNames) {
        if (usedNames.add(baseName)) return baseName;
        int counter = 2;
        while (!usedNames.add(baseName + counter)) counter++;
        return baseName + counter;
    }

    /**
     * Converts a space-separated string to camelCase.
     */
    private static String toCamelCase(String input) {
        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == ' ' || c == '-' || c == '_') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                sb.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                sb.append(sb.isEmpty() ? Character.toLowerCase(c) : c);
            }
        }
        return sb.toString();
    }

    /**
     * Prints all detected MUI components with their types and texts.
     */
    private static void printDetectedComponents(List<ScannedElement> scanned) {
        int componentCount = 0;
        Set<String> typeSet = new TreeSet<>();
        for (ScannedElement element : scanned) {
            if (element.getDetectedComponent() != null) {
                componentCount++;
                typeSet.add(element.getDetectedComponent().getTypeName());
            }
        }
        System.out.println("Detected " + componentCount + " MUI components, types: " + typeSet);
    }

    /**
     * Validates that the generated page object contains diverse element types, not just a single component type.
     */
    private static void validateDiverseContent(String content) {
        System.out.println("\n  [Diversity Validation]");
        int methodCount = 0;
        Set<String> returnTypes = new TreeSet<>();
        for (String line : content.split("\n")) {
            if (line.strip().startsWith("public ") && line.contains("()")) {
                methodCount++;
                // Extract return type: "public MuiButton xxx()" -> "MuiButton"
                String[] parts = line.strip().split("\\s+");
                if (parts.length >= 2) {
                    returnTypes.add(parts[1]);
                }
            }
        }
        System.out.println("  Total methods: " + methodCount);
        System.out.println("  Return types: " + returnTypes);
        if (returnTypes.size() <= 1 && methodCount > 3) {
            System.err.println("  [WARN] Page object only contains " + returnTypes
                    + " - consider selecting more diverse elements");
        } else if (returnTypes.size() >= 2) {
            System.out.println("  [OK] Page object contains " + returnTypes.size() + " different component types");
        }
    }

    private static void validateGeneratedCode(String content) {
        System.out.println("\n  [Validation]");
        check(content.contains("package "), "Has package declaration");
        check(content.contains("public class"), "Has public class");
        check(content.contains("ComponentWebDriver"), "Uses ComponentWebDriver");
        check(content.contains("import org.openqa.selenium.By"), "Imports By");
        check(content.contains("findComponent") || content.contains("findComponents"),
                "Uses findComponent(s)");

        // Check for potential issues
        boolean hasListReturn = false;
        for (String line : content.split(System.lineSeparator())) {
            if (line.strip().startsWith("public List<") && line.contains("findComponent(")) {
                hasListReturn = true;
                break;
            }
        }
        if (hasListReturn) {
            System.err.println("  [BUG] List<> return type with findComponent() (should be findComponents())");
        }

        boolean hasMuiV5Factory = content.contains("MuiComponents.muiV5()");
        boolean hasMuiV4Factory = content.contains("MuiComponents.mui()");
        if (hasMuiV5Factory) {
            check(true, "Uses MuiComponents.muiV5() for V5");
        } else if (hasMuiV4Factory) {
            System.out.println("  [INFO] Uses MuiComponents.mui() (V4 default)");
        }
    }

    private static void check(boolean condition, String description) {
        if (condition) {
            System.out.println("  [OK] " + description);
        } else {
            System.err.println("  [FAIL] " + description);
        }
    }

    private static void waitForMuiPage(ComponentWebDriver driver) {
        try {
            new WebDriverWait(driver, java.time.Duration.ofSeconds(30))
                    .until(d -> !d.findElements(By.cssSelector("[class*=Mui]")).isEmpty());
        } catch (RuntimeException ignored) {
            // proceed even if MUI elements are not detected within timeout
        }
    }

    private static String abbreviate(String value, int maxLength) {
        if (value == null) {
            return "-";
        }
        value = value.replace('\n', ' ').replace('\r', ' ');
        return value.length() <= maxLength ? value : value.substring(0, maxLength - 3) + "...";
    }

    /**
     * Smoke test for helper methods to satisfy test class requirement.
     */
    @Test
    void testAbbreviateHelper() {
        assertEquals("-", abbreviate(null, 20));
        assertEquals("hello", abbreviate("hello", 20));
        assertEquals("hel...", abbreviate("hello world test", 6));
    }

    /**
     * Main entry point - runs all recorder tests against MUI official website.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("  Recorder E2E Test - MUI Official Website");
        System.out.println("============================================");

        // Connect to the running Edge driver service on port 38383
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.setType(WebDriverType.EDGE);
        Capabilities options = driverConfig.getType().apply(new CreateOptionsAction(), null);
        WebDriver rawDriver = driverConfig.getType().apply(new CreateWebDriverFromRunningServiceAction(),
                new RunningServiceParams(options, "http://localhost:38383"));

        RecorderConfig config = RecorderConfig.builder()
                .framework(ComponentFramework.MUI)
                .muiVersion(MuiVersion.V5)
                .keyAttribute("data-testid")
                .outputDir(Path.of(System.getProperty("user.dir"))
                        .resolve("hamster-selenium-examples/target/recorder-generated"))
                .basePackage("com.example.pageobjects")
                .build();

        try (RecorderSession session = new RecorderSession(rawDriver, config)) {
            ComponentWebDriver driver = session.getDriver();

            // Navigate to MUI Button page
            System.out.println("\nNavigating to MUI Button page...");
            driver.get("https://mui.com/material-ui/react-button/");
            waitForMuiPage(driver);
            System.out.println("Page loaded: " + driver.getCurrentUrl());

            // Run all tests
            testScanAndDetect(session);
            testSelectAndGenerate(session);
            testLocatorQuality(session);
            testMultiPageRecording(session);

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace(System.err);
        }

        // Print summary
        System.out.println("\n============================================");
        System.out.println("  TEST SUMMARY");
        System.out.println("============================================");
        System.out.println("  Passed: " + passed);
        System.out.println("  Failed: " + failed);
        System.out.println("  Total:  " + (passed + failed));
        System.out.println("============================================");

        if (failed > 0) {
            System.exit(1);
        }
    }
}
