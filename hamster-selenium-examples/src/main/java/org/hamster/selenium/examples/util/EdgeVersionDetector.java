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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Edge browser version detector
 * @author Jack Yin
 * @since 1.15.0
 */
@SuppressWarnings("all")
public class EdgeVersionDetector extends BrowserVersionDetector {
    
    @Override
    public String getVersion() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Process process;
            
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec(
                    new String[]{"cmd.exe", "/c", "reg", "query", 
                                "HKEY_CURRENT_USER\\Software\\Microsoft\\Edge\\BLBeacon", "/v", "version"});
            } else if (os.contains("mac")) {
                process = Runtime.getRuntime().exec(
                    new String[]{"/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge", "--version"});
            } else {
                process = Runtime.getRuntime().exec("microsoft-edge --version");
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile("Microsoft Edge (\\d+\\.\\d+\\.\\d+\\.\\d+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return matcher.group(1); // Return the full version
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting Edge version: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public String getBrowserName() {
        return "Edge";
    }
}