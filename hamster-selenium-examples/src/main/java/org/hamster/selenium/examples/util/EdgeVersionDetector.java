package org.hamster.selenium.examples.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Edge browser version detector
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