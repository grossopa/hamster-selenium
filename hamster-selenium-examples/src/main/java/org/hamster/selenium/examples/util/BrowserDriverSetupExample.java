package org.hamster.selenium.examples.util;

import java.io.File;

/**
 * Example showing how to use the BrowserDriverManager to automatically
 * detect browser versions and download corresponding WebDrivers.
 * 
 * @author Assistant
 */
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