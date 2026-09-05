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

import com.github.grossopa.selenium.recorder.model.ScannedElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * The shared support of the recorder examples providing the driver creation, the embedded sample pages and the
 * console output helpers.
 *
 * @author Jack Yin
 * @since 1.15
 */
public final class RecorderExampleSupport {

    private static final String LOGIN_PAGE = """
            <!DOCTYPE html>
            <html lang="en">
            <head><meta charset="UTF-8"><title>User Login</title></head>
            <body>
            <h1>System Login</h1>
            <form id="login-form" action="dashboard.html">
              <input id="username" name="username" type="text"/>
              <input name="password" type="password"/>
              <select id="country" name="country">
                <option value="cn">China</option>
                <option value="de">Germany</option>
              </select>
              <button id="login-button" type="submit">Login</button>
              <button data-testid="reset-button" type="reset">Reset</button>
            </form>
            <a id="help-link" href="dashboard.html">Go to dashboard</a>
            </body>
            </html>
            """;

    private static final String DASHBOARD_PAGE = """
            <!DOCTYPE html>
            <html lang="en">
            <head><meta charset="UTF-8"><title>Dashboard</title></head>
            <body>
            <h1>Dashboard</h1>
            <input id="search-box" name="search" type="text" placeholder="Search"/>
            <button id="refresh-button">Refresh</button>
            <table id="user-table">
              <thead><tr><th>Name</th><th>Age</th></tr></thead>
              <tbody>
              <tr><td>Jack</td><td>30</td></tr>
              <tr><td>Lisa</td><td>25</td></tr>
              </tbody>
            </table>
            <a href="login.html">Back to login</a>
            </body>
            </html>
            """;

    /**
     * private constructor
     */
    private RecorderExampleSupport() {
        throw new AssertionError();
    }

    /**
     * Creates a new Chrome driver. The driver executable is resolved automatically by Selenium Manager.
     *
     * @return the created web driver
     */
    public static WebDriver createDriver() {
        return RemoteWebDriver.builder().addAlternative(new ChromeOptions()).build();
    }

    /**
     * Writes the embedded login and dashboard sample pages to {@code target/recorder-examples} and returns the file
     * url of the login page.
     *
     * @return the file url of the written login page
     */
    public static String writeSamplePages() {
        try {
            Path dir = Path.of("target", "recorder-examples");
            Files.createDirectories(dir);
            Files.writeString(dir.resolve("login.html"), LOGIN_PAGE);
            Files.writeString(dir.resolve("dashboard.html"), DASHBOARD_PAGE);
            return dir.resolve("login.html").toAbsolutePath().toUri().toString();
        } catch (IOException exception) {
            throw new UncheckedIOException("failed to write the sample pages", exception);
        }
    }

    /**
     * Prints the scanned elements with their indexes to the console.
     *
     * @param elements the scanned elements to print
     */
    public static void printScannedElements(List<ScannedElement> elements) {
        System.out.println("Scanned " + elements.size() + " elements:");
        for (ScannedElement element : elements) {
            String type = element.getDetectedComponent() != null ? element.getDetectedComponent().getTypeName()
                    : element.getTagName();
            String attributes = element.getAttributes().entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(", "));
            String locator = element.getBestLocator() != null ? element.getBestLocator().getDescription() : "-";
            System.out.printf(Locale.ROOT, "  [%2d] %-14s %-32s %s%n", element.getIndex(), type, attributes,
                    locator);
        }
    }

    /**
     * Prints the paths of the generated page objects to the console.
     *
     * @param files the generated page object files
     */
    public static void printGeneratedFiles(List<Path> files) {
        System.out.println("Generated " + files.size() + " page object(s):");
        files.forEach(file -> System.out.println("  " + file.toAbsolutePath()));
    }
}
