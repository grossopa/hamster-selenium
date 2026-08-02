package org.hamster.selenium.examples.util;

/**
 * Abstract base class for browser version detection
 */
public abstract class BrowserVersionDetector {
    
    /**
     * Gets the browser version installed on the system
     * 
     * @return Browser version string or null if not found
     */
    public abstract String getVersion();
    
    /**
     * Gets the name of the browser
     * 
     * @return Browser name
     */
    public abstract String getBrowserName();
}