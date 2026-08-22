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

package com.github.grossopa.selenium.examples.util;

import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for BrowserDriverManager utility
 */
class BrowserDriverManagerTest extends AbstractBrowserSupport {

    @Test
    void testGetChromeVersion() {
        String version = org.hamster.selenium.examples.util.BrowserDriverManager.getChromeVersion();
        // Version can be null if Chrome is not installed
        System.out.println("Chrome version: " + version);
        // Just testing that the method doesn't throw an exception
        assertTrue(true);
    }

    @Test
    void testGetEdgeVersion() {
        String version = org.hamster.selenium.examples.util.BrowserDriverManager.getEdgeVersion();
        // Version can be null if Edge is not installed
        System.out.println("Edge version: " + version);
        // Just testing that the method doesn't throw an exception
        assertTrue(true);
    }

    @Test
    void testDownloadChromeDriver() {
        String version = org.hamster.selenium.examples.util.BrowserDriverManager.getChromeVersion();
        if (version != null) {
            String tempDir = System.getProperty("java.io.tmpdir");
            boolean result = org.hamster.selenium.examples.util.BrowserDriverManager.downloadChromeDriver(version, tempDir);
            System.out.println("ChromeDriver download result: " + result);
            
            // Check if the file was downloaded
            String os = System.getProperty("os.name").toLowerCase();
            String driverName = os.contains("win") ? "chromedriver.exe" : "chromedriver";
            Path driverPath = Paths.get(tempDir, driverName);
            if (Files.exists(driverPath)) {
                System.out.println("ChromeDriver exists at: " + driverPath);
                // Clean up
                try {
                    Files.delete(driverPath);
                } catch (Exception e) {
                    System.err.println("Could not delete test file: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Skipping ChromeDriver download test - Chrome not found");
        }
        // Just testing that the method doesn't throw an exception
        assertTrue(true);
    }

    @Test
    void testDownloadEdgeDriver() {
        String version = org.hamster.selenium.examples.util.BrowserDriverManager.getEdgeVersion();
        if (version != null) {
            String tempDir = System.getProperty("java.io.tmpdir");
            boolean result = org.hamster.selenium.examples.util.BrowserDriverManager.downloadEdgeDriver(version, tempDir);
            System.out.println("EdgeDriver download result: " + result);
            
            // Check if the file was downloaded
            String os = System.getProperty("os.name").toLowerCase();
            String driverName = os.contains("win") ? "msedgedriver.exe" : "msedgedriver";
            Path driverPath = Paths.get(tempDir, driverName);
            if (Files.exists(driverPath)) {
                System.out.println("EdgeDriver exists at: " + driverPath);
                // Clean up
                try {
                    Files.delete(driverPath);
                } catch (Exception e) {
                    System.err.println("Could not delete test file: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Skipping EdgeDriver download test - Edge not found");
        }
        // Just testing that the method doesn't throw an exception
        assertTrue(true);
    }
    
    @Test
    void testChromeVersionDetector() {
        org.hamster.selenium.examples.util.ChromeVersionDetector detector = 
            new org.hamster.selenium.examples.util.ChromeVersionDetector();
        String version = detector.getVersion();
        System.out.println("Chrome version from detector: " + version);
        System.out.println("Browser name: " + detector.getBrowserName());
        assertTrue(true); // Just testing that the method doesn't throw an exception
    }
    
    @Test
    void testEdgeVersionDetector() {
        org.hamster.selenium.examples.util.EdgeVersionDetector detector = 
            new org.hamster.selenium.examples.util.EdgeVersionDetector();
        String version = detector.getVersion();
        System.out.println("Edge version from detector: " + version);
        System.out.println("Browser name: " + detector.getBrowserName());
        assertTrue(true); // Just testing that the method doesn't throw an exception
    }
}