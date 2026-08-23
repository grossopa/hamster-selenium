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
package org.hamster.selenium.examples.util;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Browser Driver Manager Utility
 * 
 * This utility helps to check browser versions and download corresponding WebDriver executables.
 * 
 * @author Jack Yin
 * @since 1.15.0
 */
@SuppressWarnings("all")
public class BrowserDriverManager {
    
    // WebDriver download URLs
    private static final String CHROME_DRIVER_BASE_URL = "https://chromedriver.storage.googleapis.com/";
    private static final String EDGE_DRIVER_BASE_URL = "https://msedgedriver.microsoft.com/";
    
    /**
     * Checks Chrome browser version on the system
     * 
     * @return Chrome version string or null if not found
     */
    public static String getChromeVersion() {
        ChromeVersionDetector detector = new ChromeVersionDetector();
        return detector.getVersion();
    }
    
    /**
     * Checks Edge browser version on the system
     * 
     * @return Edge version string or null if not found
     */
    public static String getEdgeVersion() {
        EdgeVersionDetector detector = new EdgeVersionDetector();
        return detector.getVersion();
    }
    
    /**
     * Downloads ChromeDriver for the specified version
     * 
     * @param version Chrome major version
     * @param destinationPath Path to save the downloaded driver
     * @return true if successful, false otherwise
     */
    public static boolean downloadChromeDriver(String version, String destinationPath) {
        try {
            // Get the latest matching chromedriver version
            String downloadUrl = CHROME_DRIVER_BASE_URL + "LATEST_RELEASE_" + version;
            String latestVersion = readStringFromUrl(downloadUrl);
            
            String os = System.getProperty("os.name").toLowerCase();
            String driverFile;
            if (os.contains("win")) {
                driverFile = "chromedriver_win32.zip";
            } else if (os.contains("mac")) {
                String arch = System.getProperty("os.arch").toLowerCase();
                if (arch.contains("aarch64") || arch.contains("arm")) {
                    driverFile = "chromedriver_mac_arm64.zip"; // M1/M2 Macs
                } else {
                    driverFile = "chromedriver_mac64.zip"; // Intel Macs
                }
            } else {
                driverFile = "chromedriver_linux64.zip";
            }
            
            String fullDownloadUrl = CHROME_DRIVER_BASE_URL + latestVersion + "/" + driverFile;
            return downloadAndExtractFile(fullDownloadUrl, destinationPath, "chromedriver", latestVersion);
        } catch (Exception e) {
            System.err.println("Error downloading ChromeDriver: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Downloads EdgeDriver for the specified version
     * 
     * @param version Edge major version
     * @param destinationPath Path to save the downloaded driver
     * @return true if successful, false otherwise
     */
    public static boolean downloadEdgeDriver(String version, String destinationPath) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String driverFile;
            if (os.contains("win")) {
                driverFile = "edgedriver_win64.zip";
            } else if (os.contains("mac")) {
                String arch = System.getProperty("os.arch").toLowerCase();
                if (arch.contains("aarch64") || arch.contains("arm")) {
                    driverFile = "edgedriver_mac64_m1.zip"; // M1/M2 Macs
                } else {
                    driverFile = "edgedriver_mac64.zip"; // Intel Macs
                }
            } else {
                driverFile = "edgedriver_linux64.zip";
            }
            
            String fullDownloadUrl = EDGE_DRIVER_BASE_URL + version + "/" + driverFile;
            return downloadAndExtractFile(fullDownloadUrl, destinationPath, "msedgedriver", version);
        } catch (Exception e) {
            System.err.println("Error downloading EdgeDriver: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Helper method to read string content from URL
     * 
     * @param urlString URL to read from
     * @return Content as string
     * @throws IOException if an I/O error occurs
     */
    private static String readStringFromUrl(String urlString) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }
    
    /**
     * Helper method to download and extract file from URL
     * 
     * @param urlString URL to download from
     * @param destinationPath Path to save the file
     * @param executableName Name of the executable file
     * @param version Version of the driver
     * @return true if successful, false otherwise
     * @throws IOException if an I/O error occurs
     */
    private static boolean downloadAndExtractFile(String urlString, String destinationPath, String executableName, String version) throws IOException {
        URL url = new URL(urlString);
        String os = System.getProperty("os.name").toLowerCase();
        String finalExecutableName = executableName;
        
        // Add extension for Windows
        if (os.contains("win")) {
            finalExecutableName += ".exe";
        }
        
        // Add version number to the executable name
        String versionedExecutableName = finalExecutableName;
        if (version != null && !version.isEmpty()) {
            int dotIndex = version.indexOf('.');
            String majorVersion = (dotIndex != -1) ? version.substring(0, dotIndex) : version;
            versionedExecutableName = finalExecutableName + "_" + majorVersion;
        }
        
        Path zipPath = Paths.get(destinationPath, executableName + ".zip");
        Path executablePath = Paths.get(destinationPath, versionedExecutableName);
        
        // Check if the executable already exists
        if (Files.exists(executablePath)) {
            System.out.println("Driver file already exists: " + executablePath.toString());
            System.out.println("Skipping download...");
            return true;
        }
        
        // Download the zip file
        try (InputStream in = url.openStream()) {
            Files.copy(in, zipPath, StandardCopyOption.REPLACE_EXISTING);
        }
        
        // Extract the executable
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath.toFile()))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                // Look for the executable file (not directory)
                if (!zipEntry.isDirectory() && (zipEntry.getName().equals(executableName) || 
                      zipEntry.getName().equals(executableName + ".exe") ||
                      zipEntry.getName().endsWith("/" + executableName) ||
                      zipEntry.getName().endsWith("\\" + executableName) ||
                      zipEntry.getName().endsWith("/" + executableName + ".exe") ||
                      zipEntry.getName().endsWith("\\" + executableName + ".exe"))) {
                    
                    // Extract the file
                    try (FileOutputStream fos = new FileOutputStream(executablePath.toFile())) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                    break;
                }
            }
        }
        
        // Make the file executable (non-Windows)
        if (!os.contains("win")) {
            executablePath.toFile().setExecutable(true, false);
        } else {
            // For Windows, ensure the file has execute permissions
            executablePath.toFile().setExecutable(true, false);
        }
        
        // Clean up the zip file
        Files.deleteIfExists(zipPath);
        
        System.out.println("Downloaded and extracted driver to: " + executablePath.toString());
        return true;
    }
    
    /**
     * Main method for testing the BrowserDriverManager
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Checking browser versions...");
        
        String chromeVersion = getChromeVersion();
        if (chromeVersion != null) {
            System.out.println("Chrome version: " + chromeVersion);
        } else {
            System.out.println("Chrome not found or error occurred");
        }
        
        String edgeVersion = getEdgeVersion();
        if (edgeVersion != null) {
            System.out.println("Edge version: " + edgeVersion);
        } else {
            System.out.println("Edge not found or error occurred");
        }
        
        // Example usage:
        // downloadChromeDriver(chromeVersion, "/path/to/save/driver");
        downloadEdgeDriver(edgeVersion, "/Users/jack/software/webdrivers/");
    }
}
