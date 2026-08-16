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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Stops a running Edge Driver Service by killing the process that occupies the configured port.
 *
 * <p>This utility is designed to be run independently from {@link StartDriverServiceEdge}.
 * It detects the operating system and uses the appropriate system command to find and
 * terminate the driver process:</p>
 * <ul>
 *   <li>macOS / Linux: {@code lsof -ti :PORT} + {@code kill}</li>
 *   <li>Windows: {@code netstat -ano} + {@code taskkill /F /PID}</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.0
 * @see StartDriverServiceEdge
 */
public class StopDriverServiceEdge {

    /**
     * Default port number for the Edge Driver Service. Must match the port used by
     * {@link StartDriverServiceEdge#PORT}.
     */
    public static final int DEFAULT_PORT = 38383;

    /**
     * Stops the driver service running on the specified port.
     *
     * @param port the port number to check and kill
     * @return {@code true} if a process was found and terminated, {@code false} if no
     *         process was running on the given port
     * @throws IOException          if the system command execution fails
     * @throws InterruptedException if the thread is interrupted while waiting for the process
     */
    public static boolean stopDriver(int port) throws IOException, InterruptedException {
        String osName = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH);
        boolean isWindows = osName.contains("win");

        if (isWindows) {
            return stopDriverWindows(port);
        } else {
            return stopDriverUnix(port);
        }
    }

    /**
     * Stops the driver service on the default port {@value DEFAULT_PORT}.
     *
     * @return {@code true} if a process was terminated
     * @throws IOException          if the system command execution fails
     * @throws InterruptedException if the thread is interrupted
     */
    public static boolean stopDriver() throws IOException, InterruptedException {
        return stopDriver(DEFAULT_PORT);
    }

    // =====================================================================
    // Unix (macOS / Linux) implementation
    // =====================================================================

    private static boolean stopDriverUnix(int port) throws IOException, InterruptedException {
        // Find PID(s) listening on the port
        ProcessBuilder pb = new ProcessBuilder("lsof", "-ti", ":" + port);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        String pids;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            pids = sb.toString().trim();
        }
        process.waitFor();

        if (pids.isEmpty()) {
            System.out.println("[INFO] No driver process found on port " + port);
            return false;
        }

        // Kill each PID
        for (String pidStr : pids.split("\\n")) {
            String pid = pidStr.trim();
            if (!pid.isEmpty()) {
                System.out.println("[INFO] Killing process " + pid + " on port " + port);
                new ProcessBuilder("kill", pid).start().waitFor();
            }
        }

        System.out.println("[INFO] EdgeDriver service stopped");
        return true;
    }

    // =====================================================================
    // Windows implementation
    // =====================================================================

    private static boolean stopDriverWindows(int port) throws IOException, InterruptedException {
        // Find PID using netstat
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "netstat -ano | findstr :" + port);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        String pid = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Format: TCP  0.0.0.0:38383  0.0.0.0:0  LISTENING  12345
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 5 && line.contains("LISTENING")) {
                    pid = parts[parts.length - 1];
                    break;
                }
            }
        }
        process.waitFor();

        if (pid == null || pid.isEmpty()) {
            System.out.println("[INFO] No driver process found on port " + port);
            return false;
        }

        // Kill the PID
        System.out.println("[INFO] Killing process " + pid + " on port " + port);
        new ProcessBuilder("taskkill", "/F", "/PID", pid).start().waitFor();
        System.out.println("[INFO] EdgeDriver service stopped");
        return true;
    }

    // =====================================================================
    // Main entry point
    // =====================================================================

    /**
     * Main entry point. Stops the Edge Driver Service running on the default port
     * ({@value DEFAULT_PORT}), or on a custom port specified as the first argument.
     *
     * @param args optional: first argument is the port number (e.g. {@code "38383"})
     * @throws IOException          if the system command execution fails
     * @throws InterruptedException if the thread is interrupted
     */
    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("[WARN] Invalid port number: " + args[0] + ", using default " + DEFAULT_PORT);
            }
        }

        boolean stopped = stopDriver(port);
        if (!stopped) {
            System.out.println("[INFO] No running driver service to stop.");
        }
    }
}
