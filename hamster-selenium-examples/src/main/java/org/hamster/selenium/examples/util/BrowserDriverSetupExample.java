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

package org.hamster.selenium.examples.util;

import java.io.File;

/**
 * Example showing how to use the BrowserDriverManager to automatically
 * detect browser versions and download corresponding WebDrivers.
 * 
 * @author Jack Yin
 * @since 1.15.0
 */
@SuppressWarnings("all")
public class BrowserDriverSetupExample {
    
    public static void main(String[] args) {
        System.out.println("=== Browser Driver Setup Example ===");
        
        // Get browser versions using the new detector classes
        BrowserVersionDetector chromeDetector = new ChromeVersionDetector();
        BrowserVersionDetector edgeDetector = new EdgeVersionDetector();
        
        String chromeVersion = chromeDetector.getVersion();
        String edgeVersion = edgeDetector.getVersion();
        
        System.out.println("Detected browser versions:");
        System.out.println("  " + chromeDetector.getBrowserName() + ": " + (chromeVersion != null ? chromeVersion : "Not found"));
        System.out.println("  " + edgeDetector.getBrowserName() + ": " + (edgeVersion != null ? edgeVersion : "Not found"));
        
        // Define where to save drivers
        String driverDirectory = System.getProperty("user.home") + File.separator + "webdrivers";
        new File(driverDirectory).mkdirs(); // Create directory if it doesn't exist
        
        System.out.println("\nDriver directory: " + driverDirectory);
        
        // Download ChromeDriver if Chrome is installed
        if (chromeVersion != null) {
            System.out.println("\nDownloading ChromeDriver for version " + chromeVersion + "...");
            boolean success = BrowserDriverManager.downloadChromeDriver(chromeVersion, driverDirectory);
            if (success) {
                System.out.println("ChromeDriver downloaded successfully!");
            } else {
                System.out.println("Failed to download ChromeDriver.");
            }
        } else {
            System.out.println("\nSkipping ChromeDriver download - Chrome not found.");
        }
        
        // Download EdgeDriver if Edge is installed
        if (edgeVersion != null) {
            System.out.println("\nDownloading EdgeDriver for version " + edgeVersion + "...");
            boolean success = BrowserDriverManager.downloadEdgeDriver(edgeVersion, driverDirectory);
            if (success) {
                System.out.println("EdgeDriver downloaded successfully!");
            } else {
                System.out.println("Failed to download EdgeDriver.");
            }
        } else {
            System.out.println("\nSkipping EdgeDriver download - Edge not found.");
        }
        
        System.out.println("\n=== Setup Complete ===");
        System.out.println("You can find your drivers in: " + driverDirectory);
    }
}