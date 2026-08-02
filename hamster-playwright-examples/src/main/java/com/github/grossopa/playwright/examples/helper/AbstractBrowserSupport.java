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

package com.github.grossopa.playwright.examples.helper;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.DefaultComponentDriver;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.util.ArrayList;
import java.util.List;

/**
 * The parent class of managing the driver
 *
 * @author Jack Yin
 * @since 1.12
 */
public abstract class AbstractBrowserSupport {

    protected static ComponentDriver driver;

    private final List<TestResult> testResults = new ArrayList<>();

    /**
     * Internal record for tracking individual test results.
     */
    private static class TestResult {
        final String name;
        final boolean passed;
        final long elapsedMs;
        final String errorMessage;

        TestResult(String name, boolean passed, long elapsedMs, String errorMessage) {
            this.name = name;
            this.passed = passed;
            this.elapsedMs = elapsedMs;
            this.errorMessage = errorMessage;
        }
    }

    public void setUpDriver() {
        if (driver == null) {
            Playwright playwright = Playwright.create();
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            driver = new DefaultComponentDriver(playwright, browser);
        }
    }

    /**
     * Prints the overall test result summary and closes the driver.
     *
     * <p>This method replaces the former {@code tearDownDriver} to reflect its expanded
     * responsibility: reporting aggregated test results before shutting down the browser.</p>
     */
    public void tearDownAndReport() {
        printSummary();
        if (driver != null) {
            driver.playwright().close();
        }
    }

    /**
     * Runs a single test method with start/end logging and elapsed time measurement.
     * Results are recorded for the final summary report.
     *
     * @param testName    the display name of the test
     * @param testMethod  the test logic to execute
     */
    protected void runTest(String testName, Runnable testMethod) {
        System.out.println(">>> START: " + testName);
        long startTime = System.currentTimeMillis();
        try {
            testMethod.run();
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("<<< END:   " + testName + " [PASSED] (" + elapsed + "ms)");
            testResults.add(new TestResult(testName, true, elapsed, null));
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - startTime;
            System.err.println("<<< END:   " + testName + " [FAILED] (" + elapsed + "ms) - " + e.getMessage());
            testResults.add(new TestResult(testName, false, elapsed, e.getMessage()));
            throw e;
        }
    }

    /**
     * Runs an entire test class block with start/end logging.
     *
     * @param className  the display name of the test class
     * @param testBlock  the block containing all test invocations
     */
    protected void runTestClass(String className, Runnable testBlock) {
        System.out.println("========================================");
        System.out.println("  Test Class: " + className);
        System.out.println("========================================");
        long startTime = System.currentTimeMillis();
        try {
            testBlock.run();
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("========================================");
            System.out.println("  " + className + " COMPLETED (" + elapsed + "ms)");
            System.out.println("========================================");
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - startTime;
            System.err.println("========================================");
            System.err.println("  " + className + " FAILED (" + elapsed + "ms)");
            System.err.println("========================================");
            throw e;
        }
    }

    /**
     * Prints a summary table of all recorded test results.
     *
     * <p>Uses {@link System#out} directly to preserve table formatting and alignment,
     * since {@link java.util.logging.Logger} would prepend a timestamp/class prefix to every line.</p>
     */
    private void printSummary() {
        if (testResults.isEmpty()) {
            System.out.println("[INFO] No test results to report.");
            return;
        }

        long totalElapsed = 0;
        int passed = 0;
        int failed = 0;

        // Find the longest test name for dynamic alignment
        int maxNameLen = testResults.stream()
                .mapToInt(r -> r.name.length())
                .max()
                .orElse(20);
        int nameWidth = Math.max(maxNameLen, 20);

        // Build format templates
        String rowFmt   = "  %-" + nameWidth + "s  [%-6s]  (%dms)";
        String totalFmt = "  Total: %d  |  Passed: %d  |  Failed: %d  |  Time: %dms";

        int innerWidth = nameWidth + 30;
        String border   = "  " + "=".repeat(innerWidth);

        System.out.println();
        System.out.println(border);
        System.out.println("  TEST SUMMARY REPORT");
        System.out.println(border);

        for (TestResult result : testResults) {
            totalElapsed += result.elapsedMs;
            String status = result.passed ? "PASSED" : "FAILED";
            if (result.passed) {
                passed++;
            } else {
                failed++;
            }

            String line = String.format(rowFmt, result.name, status, result.elapsedMs);
            if (result.passed) {
                System.out.println(line);
            } else {
                System.out.println(line + "  !! " + result.errorMessage);
            }
        }

        System.out.println(border);
        System.out.println(String.format(totalFmt, testResults.size(), passed, failed, totalElapsed));
        System.out.println(border);
    }

    public static void main(String[] args) {

    }
}