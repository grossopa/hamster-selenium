# Browser Driver Manager

## Overview

The BrowserDriverManager is a utility class that helps automatically detect browser versions installed on your system and download the corresponding WebDriver executables. This eliminates the need to manually check browser versions and download compatible WebDriver versions.

## Features

- Detects Chrome browser version
- Detects Edge browser version
- Downloads ChromeDriver matching your Chrome version
- Downloads EdgeDriver matching your Edge version
- Automatically extracts drivers from downloaded ZIP files
- Sets proper permissions for executables on Unix-like systems

## Class Structure

The utility is composed of several classes:

1. **BrowserVersionDetector** - Abstract base class for browser version detection
2. **ChromeVersionDetector** - Chrome browser version detector implementation
3. **EdgeVersionDetector** - Edge browser version detector implementation
4. **BrowserDriverManager** - Main utility class for downloading WebDrivers

## Usage

### Basic Usage

```java
// Get browser versions using the BrowserDriverManager convenience methods
String chromeVersion = BrowserDriverManager.getChromeVersion();
String edgeVersion = BrowserDriverManager.getEdgeVersion();

System.out.println("Chrome version: " + chromeVersion);
System.out.println("Edge version: " + edgeVersion);

// Download drivers to a specific directory
String driverDirectory = "/path/to/webdrivers";
BrowserDriverManager.downloadChromeDriver(chromeVersion, driverDirectory);
BrowserDriverManager.downloadEdgeDriver(edgeVersion, driverDirectory);
```

### Using Detector Classes Directly

```java
// Create detector instances
BrowserVersionDetector chromeDetector = new ChromeVersionDetector();
BrowserVersionDetector edgeDetector = new EdgeVersionDetector();

// Get versions
String chromeVersion = chromeDetector.getVersion();
String edgeVersion = edgeDetector.getVersion();

System.out.println(chromeDetector.getBrowserName() + " version: " + chromeVersion);
System.out.println(edgeDetector.getBrowserName() + " version: " + edgeVersion);
```

### Example Implementation

See [BrowserDriverSetupExample.java](BrowserDriverSetupExample.java) for a complete example:

```java
public class BrowserDriverSetupExample {
    public static void main(String[] args) {
        // Get browser versions using detector classes
        BrowserVersionDetector chromeDetector = new ChromeVersionDetector();
        BrowserVersionDetector edgeDetector = new EdgeVersionDetector();
        
        String chromeVersion = chromeDetector.getVersion();
        String edgeVersion = edgeDetector.getVersion();
        
        // Define where to save drivers
        String driverDirectory = System.getProperty("user.home") + File.separator + "webdrivers";
        new File(driverDirectory).mkdirs();
        
        // Download drivers if browsers are found
        if (chromeVersion != null) {
            BrowserDriverManager.downloadChromeDriver(chromeVersion, driverDirectory);
        }
        
        if (edgeVersion != null) {
            BrowserDriverManager.downloadEdgeDriver(edgeVersion, driverDirectory);
        }
    }
}
```

## Supported Platforms

- Windows
- macOS (Intel and Apple Silicon)
- Linux

## Requirements

- Java 8 or higher
- Internet connection for downloading drivers

## Notes

- The utility detects the major version of browsers (e.g., 138 for Chrome 138.x.x.x)
- Downloaded drivers are automatically extracted from ZIP files
- On macOS, the utility detects architecture (Intel vs Apple Silicon) for proper driver selection
- On Unix-like systems, executable permissions are automatically set
- Existing driver files will be overwritten

## Error Handling

The utility includes basic error handling:
- Network issues during download
- File I/O errors
- Browser not found scenarios

In case of errors, appropriate messages are printed to stderr, and the methods return `false`.

## Integration with Selenium Tests

You can use this utility in your test setup phase to ensure you always have the correct WebDriver versions:

```java
@BeforeAll
public static void setupWebDriver() {
    String chromeVersion = BrowserDriverManager.getChromeVersion();
    if (chromeVersion != null) {
        BrowserDriverManager.downloadChromeDriver(chromeVersion, "./drivers");
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
    }
}
```

## Extending to Other Browsers

To add support for other browsers:

1. Extend the `BrowserVersionDetector` abstract class
2. Implement the `getVersion()` and `getBrowserName()` methods
3. Add a corresponding download method in `BrowserDriverManager`

```java
public class FirefoxVersionDetector extends BrowserVersionDetector {
    @Override
    public String getVersion() {
        // Implementation to detect Firefox version
    }
    
    @Override
    public String getBrowserName() {
        return "Firefox";
    }
}
```

## Limitations

- Requires browsers to be installed in standard locations
- May not work with portable/special browser installations
- Download URLs may change over time (currently uses official sources)