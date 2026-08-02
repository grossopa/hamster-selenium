# Hamster Playwright Core

On top of Playwright (web browser automation tool) for providing component-based abstraction of Html DOM with interaction
APIs for automating the web pages built by modern front-end frameworks such as Material UI.

It depends on Playwright 1.52.0 and JDK 11.

## How to Use

Add dependency to your project:

```xml
<dependency>
  <groupId>com.github.grossopa</groupId>
  <artifactId>hamster-playwright-core</artifactId>
  <version>1.12.0-SNAPSHOT</version>
</dependency>
```

Create a ComponentDriver from existing Playwright:

```java
Playwright playwright = Playwright.create();
ComponentDriver driver = new DefaultComponentDriver(playwright);
```

Locate the element root by CSS selector and convert them by using as() and toSelect or other methods:

```java
WebComponent component = driver.findComponent(".MuiSelect-root");
MuiSelect select = component.as(MuiComponents.mui()).toSelect(".MuiMenuItem-root");
```

## Shortened Call Chains

To reduce verbosity when working with Material UI components, you can now use utility methods that combine finding and conversion in a single call:

```java
// Instead of writing:
MuiButton button = driver.findComponent(".MuiButton-root")
                         .as(MuiComponents.muiV5())
                         .toButton();

// You can write:
MuiButton button = MuiComponentFinder.findMuiButton(driver, ".MuiButton-root");
```

## License

https://mit-license.org/