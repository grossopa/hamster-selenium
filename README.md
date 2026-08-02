# Hamster Selenium

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=grossopa_hamster-selenium&metric=alert_status)](https://sonarcloud.io/dashboard?id=grossopa_hamster-selenium)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=grossopa_hamster-selenium&metric=coverage)](https://sonarcloud.io/dashboard?id=grossopa_hamster-selenium)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.grossopa/hamster-selenium-core)](https://central.sonatype.com/search?q=com.github.grossopa+hamster)

A Java component-based web automation testing framework for modern front-end frameworks. Hamster Selenium provides a **component-oriented DOM abstraction layer** on top of both Selenium WebDriver and Playwright, enabling you to interact with Material UI, Angular Material, Ant Design, and plain HTML components through type-safe, object-oriented APIs — without manually handling low-level DOM details.

## Why Hamster Selenium

### The Problem

Modern front-end frameworks like Material UI render components as deeply nested `<div>` structures with complex CSS classes and attributes. When testing these with raw Selenium or Playwright:

- You must manually locate deeply nested elements across multiple DOM layers
- Complex components like Select, Autocomplete, and DatePicker involve multi-step interactions (open overlay → locate options → click → close overlay)
- Different versions of the same component may have different DOM structures, requiring separate adaptations
- Test code becomes cluttered with low-level locators, reducing readability and maintainability

### The Solution

Hamster Selenium encapsulates these complex DOM operations into **type-safe Java objects**:

- **One-liner component lookup and conversion** — no more manual DOM nesting traversal
- **Ready-to-use component APIs** — semantic methods like `select.selectByValue()`, `autocomplete.searchAndSelect()`
- **Automatic multi-version support** — a single codebase works with both Material UI v4 and v5
- **Full native API compatibility** — `ComponentWebDriver` extends `WebDriver` for seamless replacement

## Supported Front-End Frameworks

| Framework | Selenium Module | Playwright Module | Status |
|---|---|---|---|
| Plain HTML | `hamster-selenium-component-html` | `hamster-playwright-component-html` | Stable |
| Material UI (React) v4/v5 | `hamster-selenium-component-materialui` | `hamster-playwright-component-materialui` | Stable |
| Angular Material | `hamster-selenium-component-mat` | — | Stable |
| Ant Design | `hamster-selenium-component-antdesign` | — | Early Stage |

## Quick Start

### 1. Add Maven Dependency

Choose the automation engine and UI framework you are using:

**Selenium + Material UI:**
```xml
<dependency>
  <groupId>com.github.grossopa</groupId>
  <artifactId>hamster-selenium-component-materialui</artifactId>
  <version>1.14.0</version>
</dependency>
```

**Playwright + Material UI:**
```xml
<dependency>
  <groupId>com.github.grossopa</groupId>
  <artifactId>hamster-playwright-component-materialui</artifactId>
  <version>1.14.0</version>
</dependency>
```

**Selenium + HTML:**
```xml
<dependency>
  <groupId>com.github.grossopa</groupId>
  <artifactId>hamster-selenium-component-html</artifactId>
  <version>1.14.0</version>
</dependency>
```

**Selenium + Angular Material:**
```xml
<dependency>
  <groupId>com.github.grossopa</groupId>
  <artifactId>hamster-selenium-component-mat</artifactId>
  <version>1.14.0</version>
</dependency>
```

### 2. Create a Component-Aware Driver

**Selenium:**
```java
// Wrap an existing WebDriver
ComponentWebDriver driver = new DefaultComponentWebDriver(webDriver);
```

**Playwright:**
```java
// Wrap an existing Playwright instance
ComponentDriver driver = new DefaultComponentDriver(playwright);
```

### 3. Locate and Interact with Components

#### Material UI Components (Selenium)

```java
// Find a Select component and choose an option
WebComponent selectRoot = driver.findComponent(By.className("MuiSelect-root"));
MuiSelect select = selectRoot.as(MuiComponents.mui())
    .toSelect(By.className("MuiMenuItem-root"));
select.selectByValue("option-value");

// Find a button and click it
WebComponent buttonRoot = driver.findComponent(By.className("MuiButton-root"));
MuiButton button = buttonRoot.as(MuiComponents.mui()).toButton();
button.click();

// Use MuiComponentFinder for a shortened call chain
MuiButton button = MuiComponentFinder.findMuiButton(
    driver, By.className("MuiButton-root"));
button.click();
```

#### Material UI Components (Playwright)

```java
// Find a button and interact
WebComponent buttonRoot = driver.findComponent(".MuiButton-root");
MuiButton button = buttonRoot.as(MuiComponents.mui()::toButton);
button.click();

// Find a TextField and type into it
WebComponent textFieldRoot = driver.findComponent(".MuiTextField-root");
MuiTextField textField = textFieldRoot.as(MuiComponents.mui()::toTextField);
textField.fill("Hello World");

// Find a Checkbox and toggle it
WebComponent checkboxRoot = driver.findComponent(".MuiCheckbox-root");
MuiCheckbox checkbox = checkboxRoot.as(MuiComponents.mui()::toCheckbox);
if (!checkbox.isChecked()) {
    checkbox.click();
}
```

#### HTML Components

**Selenium:**
```java
// Work with an HTML table
WebComponent tableRoot = driver.findComponent(By.id("customers"));
HtmlTable table = new HtmlTable(tableRoot, driver);
List<HtmlTableRow> rows = table.getDataRows();
String firstCell = rows.get(0).getCell(0).innerText();

// Work with an HTML Select
HtmlSelect select = new HtmlSelect(
    driver.findElement(By.id("cars")), driver);
select.selectByValue("audi");
```

**Playwright:**
```java
// Work with an HTML table
WebComponent tableRoot = driver.findComponent("#customers");
HtmlTable table = tableRoot.as(HtmlComponents.html(driver)::table);
List<HtmlTableRow> rows = table.getDataRows();

// Work with an HTML Select
WebComponent selectRoot = driver.findComponent("#cars");
HtmlSelect select = selectRoot.as(HtmlComponents.html(driver)::select);
select.selectByValue("audi");
```

#### Angular Material Components

```java
// Work with Mat Autocomplete
WebComponent autoRoot = driver.findComponent(
    By.className("mat-autocomplete-trigger"));
MatAutocomplete autocomplete = autoRoot.as(MatComponents.mat())
    .toAutocomplete();
autocomplete.searchAndSelect("option text");

// Work with Mat Dialog
WebComponent dialogRoot = driver.findComponent(
    By.className("mat-dialog-container"));
MatDialog dialog = dialogRoot.as(MatComponents.mat()).toDialog();
dialog.close();
```

## Supported Material UI Components

### Inputs
Button, ButtonGroup, Checkbox, Fab, Radio, RadioGroup, Rating, Select, Slider, Switch, TextField

### Data Display
Avatar, Badge, Chip, Divider, List, ListItem, Tooltip

### Feedback
Alert, Backdrop, Dialog, Skeleton, Snackbar, SnackbarContent

### Navigation
Accordion (+Actions/Details/Summary), BottomNavigation (+Action), Breadcrumbs, Drawer, Link, Menu, MenuItem, Stepper, Tab (+ScrollButton), Tabs

### Surfaces
AppBar, Card, Toolbar, Pager (Selenium only), Paper (Playwright only)

> Note: `MuiPager` (Selenium) is a pagination component, while `MuiPaper` (Playwright) is a surface container — they are different components.

### Core
Grid, Modal, Popover (Selenium) / Box, Container, Grid (Playwright)

### Lab
Autocomplete, Pagination

### Date/Time Pickers (V5 Only)
CalendarPicker, DatePickerFormField, MonthPicker, YearPicker

> For the full list, see [Wiki: Supported Material UI Components](https://github.com/grossopa/hamster-selenium/wiki/Supported-Material-UI-Components)

## Supported Angular Material Components

Accordion, Autocomplete, Badge, BottomSheet, Button, ButtonToggle, ButtonToggleGroup, Checkbox, ChipList, Dialog, ExpansionPanel, FormField, GridList, GridTile, List, Menu (+MenuItem), OverlayContainer, ProgressBar, SelectionList, SlideToggle, Slider, Snackbar

## Supported Ant Design Components

| Component | Selenium | Playwright | Status |
|---|---|---|---|
| AntdButton | ✅ | — | Early Stage |

## Supported HTML Components

| Component | Selenium | Playwright |
|---|---|---|
| HtmlSelect | ✅ | ✅ |
| HtmlTable (+HtmlTableRow) | ✅ | ✅ |
| HtmlFormField | ✅ | ✅ |

## Advanced APIs

### Shortcut Component Lookup

Use `findComponentAs()` to locate and convert a component in a single call:

```java
// Selenium — find and convert in one step
MuiButton button = driver.findComponentAs(
    By.className("MuiButton-root"), MuiComponents.mui()::toButton);
button.click();

// Playwright
MuiButton button = driver.findComponentAs(
    ".MuiButton-root", MuiComponents.mui()::toButton);
button.click();
```

Alternatively, use `MuiComponentFinder` for an even shorter call:

```java
MuiButton button = MuiComponentFinder.findMuiButton(
    driver, By.className("MuiButton-root"));
```

### Intercepting Layer

The framework provides an intercepting layer that allows you to hook into driver and element operations for logging, debugging, or custom behavior:

- **Selenium**: `InterceptingWebDriver`, `InterceptingWebElement` — wrap `ComponentWebDriver` and `WebComponent` with custom interceptors
- **Playwright**: `InterceptingPlaywright`, `InterceptingLocator` — wrap `ComponentDriver` and `WebComponent` with custom interceptors

This is useful for adding automatic wait-and-retry logic, performance tracking, or detailed operation logging without modifying component code.

## Material UI Version Compatibility

The framework automatically adapts to different Material UI versions:

```java
// Material UI v4 (default)
MuiComponents mui = MuiComponents.mui();

// Material UI v5
MuiComponents muiV5 = MuiComponents.muiV5();

// Custom configuration
MuiConfig config = new MuiConfig();
config.setVersion(MuiVersion.V5);
MuiComponents muiCustom = MuiComponents.mui(config);
```

## Examples

The project includes comprehensive example code that can be run directly:

- `hamster-selenium-examples` — Selenium-based examples
- `hamster-playwright-examples` — Playwright-based examples

Examples cover HTML tables/forms, the full range of Material UI components (buttons, forms, dialogs, navigation, etc.), and real-world interaction scenarios.

## Requirements

- **JDK**: 11 or higher
- **Selenium**: 4.27.0 (Selenium modules)
- **Playwright**: 1.52.0 (Playwright modules)

## License

[MIT License](https://mit-license.org/)
