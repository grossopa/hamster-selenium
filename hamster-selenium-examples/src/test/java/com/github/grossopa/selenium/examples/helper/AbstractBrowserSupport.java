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
package com.github.grossopa.selenium.examples.helper;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.DefaultComponentWebDriver;
import com.github.grossopa.selenium.core.driver.*;
import com.github.grossopa.selenium.core.intercepting.InterceptingWebDriver;
import com.github.grossopa.selenium.core.intercepting.LoggingHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;


/**
 * The parent class of managing the driver
 *
 * @author Jack Yin
 * @since 1.0
 */
public abstract class AbstractBrowserSupport {

    public static final String EXECUTABLE_PATH = "D://software/drivers/chromedriver-84.exe";

    protected static ComponentWebDriver driver;

    /** Whether any test method has failed so far. */
    protected boolean anyFailure = false;

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

    public void setUpDriver(WebDriverType type) {
        if (driver == null) {
            DriverConfig config = new DriverConfig();
            config.setDriverExecutablePath(EXECUTABLE_PATH);
            config.setDriverVersion("85");
            config.setType(type);

            Capabilities options = config.getType().apply(new CreateOptionsAction(), null);
            WebDriver temp = config.getType().apply(new CreateWebDriverFromRunningServiceAction(),
                    new RunningServiceParams(options, "http://localhost:38383"));
            driver = new DefaultComponentWebDriver(new InterceptingWebDriver(temp, new LoggingHandler(0L)));
        }
    }

    /**
     * Navigates to an examples page of the archived Angular Material v12 documentation site and
     * waits until the page is rendered.
     *
     * @param url the examples page url
     */
    protected void navigateToExamples(String url) {
        driver.navigate().to(url);
        waitForExamplesPageRendered();
    }

    /**
     * Waits until any Material component is rendered on the current page. The archived doc site
     * occasionally fails to bootstrap; in that case the page is refreshed once and polled again.
     */
    protected void waitForExamplesPageRendered() {
        for (int attempt = 0; attempt < 2; attempt++) {
            for (int i = 0; i < 60; i++) {
                if (!driver.findElements(By.cssSelector("[class*=mat-]")).isEmpty()) {
                    return;
                }
                driver.threadSleep(250L);
            }
            driver.navigate().refresh();
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
        } catch (Throwable e) {
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
        }
    }

    /**
     * Runs a single test, catching any throwable so that subsequent tests still execute.
     * Sets {@link #anyFailure} to {@code true} on failure.
     *
     * @param name       the display name of the test
     * @param test       the test logic to execute
     */
    protected void run(String name, Runnable test) {
        try {
            runTest(name, test);
        } catch (Throwable ex) {
            anyFailure = true;
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Conditionally runs a single test when the given filter matches (or is {@code null}).
     *
     * @param filter     the active filter, or {@code null} to run everything
     * @param name       the display name of the test
     * @param test       the test logic to execute
     */
    protected void runIf(String filter, String name, Runnable test) {
        if (filter == null || filter.equals(name)) {
            run(name, test);
        }
    }

    /**
     * Returns whether any test method has failed so far.
     *
     * @return {@code true} if at least one test has failed
     */
    protected boolean hasFailures() {
        return anyFailure;
    }

    /**
     * Prints the overall test result summary and quits the driver.
     */
    public void tearDownAndReport() {
        printSummary();
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Prints a summary table of all recorded test results.
     */
    private void printSummary() {
        if (testResults.isEmpty()) {
            System.out.println("[INFO] No test results to report.");
            return;
        }

        long totalElapsed = 0;
        int passed = 0;
        int failed = 0;

        int maxNameLen = testResults.stream().mapToInt(r -> r.name.length()).max().orElse(20);
        int nameWidth = Math.max(maxNameLen, 20);

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

        // Print failed tests section
        List<TestResult> failedResults = testResults.stream().filter(r -> !r.passed).toList();
        if (!failedResults.isEmpty()) {
            System.out.println();
            System.out.println(border);
            System.out.println("  FAILED TESTS (" + failedResults.size() + ")");
            System.out.println(border);
            for (TestResult result : failedResults) {
                System.out.println("  - " + result.name + " (" + result.elapsedMs + "ms)");
                System.out.println("    " + result.errorMessage);
            }
            System.out.println(border);
        }
    }
}
