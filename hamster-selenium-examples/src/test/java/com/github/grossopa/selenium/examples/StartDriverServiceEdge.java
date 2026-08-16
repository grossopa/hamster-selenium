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

package com.github.grossopa.selenium.examples;

import com.github.grossopa.selenium.core.driver.CreateDriverServiceAction;
import com.github.grossopa.selenium.core.driver.DriverConfig;
import com.github.grossopa.selenium.core.driver.WebDriverType;
import org.openqa.selenium.remote.service.DriverService;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Cross-platform Edge driver starter with auto browser version detection and driver download.
 *
 * <p>Supports macOS (Intel / Apple Silicon), Windows (x64 / x86), and Linux (x64).
 * Automatically detects the installed Edge browser version, resolves the matching
 * EdgeDriver, and downloads it from the official Microsoft CDN if not present locally.</p>
 *
 * <p>Driver binaries are cached under {@code .cache/selenium/} (relative to the working
 * directory) and excluded from version control via {@code .gitignore}.</p>
 *
 * @author Jack Yin
 * @since 1.0
 */
@SuppressWarnings("all")
public class StartDriverServiceEdge {

    /**
     * Directory where downloaded Edge WebDriver executables are stored.
     */
    public static final String DRIVER_DIR = ".cache/selenium";

    /**
     * Port number for the Edge Driver Service.
     */
    public static final int PORT = 38383;

    /**
     * Microsoft Edge WebDriver CDN base URL.
     */
    private static final String EDGE_DRIVER_CDN = "https://msedgedriver.microsoft.com";

    // =====================================================================
    // OS detection
    // =====================================================================

    /**
     * Supported operating system families.
     */
    enum OsFamily {
        MAC, WINDOWS, LINUX
    }

    /**
     * Detects the current operating system.
     *
     * @return the detected {@link OsFamily}
     * @throws UnsupportedOperationException if the OS is not supported
     */
    static OsFamily detectOs() {
        String osName = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH);
        if (osName.contains("mac") || osName.contains("darwin")) {
            return OsFamily.MAC;
        } else if (osName.contains("win")) {
            return OsFamily.WINDOWS;
        } else if (osName.contains("linux")) {
            return OsFamily.LINUX;
        }
        throw new UnsupportedOperationException("Unsupported OS: " + System.getProperty("os.name"));
    }

    /**
     * Checks whether the current machine uses an ARM (Apple Silicon / aarch64) architecture.
     *
     * @return {@code true} if ARM-based
     */
    static boolean isArm() {
        String arch = System.getProperty("os.arch", "").toLowerCase(Locale.ENGLISH);
        return arch.contains("aarch64") || arch.contains("arm");
    }

    // =====================================================================
    // Edge browser discovery
    // =====================================================================

    /**
     * Well-known Edge browser paths per platform.
     */
    private static final String[] EDGE_PATHS_MAC = {
            "/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge"
    };

    private static final String[] EDGE_PATHS_WINDOWS = {
            System.getenv("ProgramFiles(x86)") + "\\Microsoft\\Edge\\Application\\msedge.exe",
            System.getenv("ProgramFiles") + "\\Microsoft\\Edge\\Application\\msedge.exe",
            System.getenv("LOCALAPPDATA") + "\\Microsoft\\Edge\\Application\\msedge.exe"
    };

    private static final String[] EDGE_PATHS_LINUX = {
            "/usr/bin/microsoft-edge",
            "/usr/bin/microsoft-edge-stable"
    };

    /**
     * Locates the installed Microsoft Edge browser executable on the current machine.
     *
     * @return the absolute path to the Edge browser binary
     * @throws IOException if Edge cannot be found
     */
    static String findEdgePath() throws IOException {
        OsFamily os = detectOs();
        String[] candidates = switch (os) {
            case MAC -> EDGE_PATHS_MAC;
            case WINDOWS -> EDGE_PATHS_WINDOWS;
            case LINUX -> EDGE_PATHS_LINUX;
        };

        for (String path : candidates) {
            if (path != null && new File(path).exists()) {
                return path;
            }
        }
        throw new IOException("Microsoft Edge not found. Searched: " + String.join(", ", candidates));
    }

    /**
     * Detects the installed Microsoft Edge browser version.
     *
     * <p>Executes the Edge binary with {@code --version} and parses the output.
     * Example output: {@code Microsoft Edge 130.0.2849.68}</p>
     *
     * @return the full version string (e.g. {@code "130.0.2849.68"})
     * @throws IOException if the browser is not installed or the version cannot be determined
     */
    public static String detectEdgeVersion() throws IOException {
        String edgePath = findEdgePath();
        System.out.println("[INFO] Edge binary: " + edgePath);

        ProcessBuilder pb = new ProcessBuilder(edgePath, "--version");
        pb.redirectErrorStream(true);
        Process process = pb.start();

        String output;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            output = reader.readLine();
        }

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Failed to detect Edge version, exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while detecting Edge version", e);
        }

        if (output == null || !output.contains("Edge")) {
            throw new IOException("Unexpected Edge version output: " + output);
        }

        // Parse "Microsoft Edge 139.0.3405.102" -> "139.0.3405.102"
        String version = output.replaceAll(".*Edge\\s+", "").trim();
        System.out.println("[INFO] Detected Edge version: " + version);
        return version;
    }

    // =====================================================================
    // Driver resolution & download
    // =====================================================================

    /**
     * Returns the platform suffix for the EdgeDriver download URL.
     *
     * <p>Examples: {@code mac64_m1}, {@code mac64}, {@code win64}, {@code win32}, {@code linux64}</p>
     *
     * @return the platform suffix
     */
    static String getDriverPlatform() {
        OsFamily os = detectOs();
        boolean arm = isArm();
        return switch (os) {
            case MAC -> arm ? "mac64_m1" : "mac64";
            case WINDOWS -> arm ? "win64" : (System.getProperty("sun.arch.data.model", "64").equals("64") ? "win64" : "win32");
            case LINUX -> "linux64";
        };
    }

    /**
     * Returns the expected driver binary name inside the downloaded zip.
     *
     * @return {@code "msedgedriver.exe"} on Windows, {@code "msedgedriver"} otherwise
     */
    static String getDriverBinaryName() {
        return detectOs() == OsFamily.WINDOWS ? "msedgedriver.exe" : "msedgedriver";
    }

    /**
     * Returns the local driver file name (including extension on Windows).
     *
     * @param majorVersion the major version number
     * @return the file name, e.g. {@code "msedgedriver_139"} or {@code "msedgedriver_139.exe"}
     */
    static String getDriverFileName(String majorVersion) {
        String suffix = detectOs() == OsFamily.WINDOWS ? ".exe" : "";
        return "msedgedriver_" + majorVersion + suffix;
    }

    /**
     * Resolves the matching EdgeDriver for the given browser version. If the driver already
     * exists locally it is reused; otherwise it is automatically downloaded from the
     * official Microsoft Edge driver CDN.
     *
     * @param browserVersion the full browser version (e.g. {@code "139.0.3405.102"})
     * @return the absolute path to the resolved driver executable
     * @throws IOException if the download or extraction fails
     */
    public static String resolveDriver(String browserVersion) throws IOException {
        String majorVersion = browserVersion.split("\\.")[0];
        String driverFileName = getDriverFileName(majorVersion);
        String driverPath = DRIVER_DIR + File.separator + driverFileName;

        // 1. check if driver already exists
        File driverFile = new File(driverPath);
        if (driverFile.exists() && driverFile.canExecute()) {
            String existingVersion = getDriverVersion(driverPath);
            if (browserVersion.startsWith(existingVersion.split("\\.")[0])) {
                System.out.println("[INFO] Matching driver found: " + driverPath + " (" + existingVersion + ")");
                return driverPath;
            }
            System.out.println("[INFO] Driver version mismatch, re-downloading...");
        }

        // 2. ensure driver directory exists
        File parentDir = new File(DRIVER_DIR);
        if (!parentDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            parentDir.mkdirs();
        }

        // 3. download matching driver
        System.out.println("[INFO] Downloading EdgeDriver " + browserVersion + "...");
        return downloadDriver(browserVersion, driverPath);
    }

    /**
     * Reads the version string from an installed driver binary.
     *
     * @param driverPath path to the driver executable
     * @return the version string, or {@code "unknown"} if it cannot be determined
     */
    static String getDriverVersion(String driverPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(driverPath, "--version");
            pb.redirectErrorStream(true);
            Process process = pb.start();
            String output;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                output = reader.readLine();
            }
            process.waitFor();
            // e.g. "MSEdgeDriver 139.0.3405.102 ..."
            if (output != null) {
                return output.replaceAll(".*MSEdgeDriver\\s+", "").trim().split("\\s+")[0];
            }
        } catch (Exception e) {
            System.err.println("[WARN] Cannot read driver version: " + e.getMessage());
        }
        return "unknown";
    }

    /**
     * Downloads the EdgeDriver zip from the Microsoft CDN and extracts the driver binary.
     *
     * @param browserVersion the full browser version
     * @param targetPath     the target path for the extracted driver binary
     * @return the absolute path to the extracted driver
     * @throws IOException if the download or extraction fails
     */
    static String downloadDriver(String browserVersion, String targetPath) throws IOException {
        String platform = getDriverPlatform();
        String binaryName = getDriverBinaryName();
        String downloadUrl = EDGE_DRIVER_CDN + "/" + browserVersion + "/edgedriver_" + platform + ".zip";

        System.out.println("[INFO] Download URL: " + downloadUrl);

        // download zip to temp directory
        Path tempDir = Files.createTempDirectory("edgedriver_");
        Path zipFile = tempDir.resolve("edgedriver.zip");

        try (InputStream in = new URL(downloadUrl).openStream()) {
            Files.copy(in, zipFile, StandardCopyOption.REPLACE_EXISTING);
        }

        // extract driver binary
        boolean extracted = false;
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                // Match "msedgedriver" or "msedgedriver.exe" (may be in a subdirectory)
                if (entryName.equals(binaryName) || entryName.endsWith("/" + binaryName)
                        || entryName.equals("msedgedriver") || entryName.endsWith("/msedgedriver")) {
                    Files.copy(zis, Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
                    extracted = true;
                    break;
                }
                zis.closeEntry();
            }
        }

        // cleanup temp
        Files.deleteIfExists(zipFile);
        Files.deleteIfExists(tempDir);

        if (!extracted) {
            throw new IOException(binaryName + " not found in downloaded archive");
        }

        // make executable
        File targetFile = new File(targetPath);
        //noinspection ResultOfMethodCallIgnored
        targetFile.setExecutable(true);
        System.out.println("[INFO] Driver installed to: " + targetPath);
        return targetPath;
    }

    // =====================================================================
    // Main entry point
    // =====================================================================

    /**
     * Main entry point. Detects the Edge browser version, ensures the matching driver
     * is installed, then starts the Edge Driver Service.
     *
     * <p>A JVM shutdown hook is registered to automatically stop the driver service
     * via {@link StopDriverServiceEdge} when the application exits.</p>
     *
     * @param args command line arguments (not used)
     * @throws IOException if browser detection, driver download, or service start fails
     */
    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException {
        // Register shutdown hook to auto-stop driver on JVM exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                StopDriverServiceEdge.stopDriver(PORT);
            } catch (Exception e) {
                System.err.println("[WARN] Failed to stop driver: " + e.getMessage());
            }
        }));

        // Step 1: detect browser version
        String browserVersion = detectEdgeVersion();

        // Step 2: resolve (check + auto-download) matching driver
        String driverPath = resolveDriver(browserVersion);

        // Step 3: start driver service
        DriverConfig config = new DriverConfig();
        config.setDriverExecutablePath(driverPath);
        config.setDriverVersion(browserVersion);
        config.setType(WebDriverType.EDGE);
        config.setPort(PORT);

        DriverService driverService = config.getType().apply(new CreateDriverServiceAction(), config);
        driverService.start();
        System.out.println("[INFO] EdgeDriver service started on port " + PORT);
    }
}
