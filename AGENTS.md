# Hamster Selenium — Developer Guide

> Internal development guide for AI agents and project contributors. For user documentation, see [README.md](README.md).

## Project Overview

A Java component-based web automation testing framework supporting both Selenium WebDriver and Playwright engines. Provides type-safe component abstractions for Material UI (React v4/v5), Angular Material, Ant Design, and plain HTML.

## Tech Stack

- **Language**: Java 11 (source/target)
- **Build**: Maven multi-module (11 sub-modules)
- **Core Dependencies**: Selenium WebDriver 4.27.0, Playwright 1.52.0, Apache Commons Lang3 3.18.0
- **Testing**: JUnit Jupiter 5.13.4, Mockito, OpenPojo 0.9.1, Byte Buddy
- **Quality**: JaCoCo 0.8.12, PITest 1.5.2 (mutation testing), SonarCloud
- **Publishing**: Maven Central (central-publishing-maven-plugin)
- **License**: MIT

## JDK Constraints

**MUST use JDK 11 or JDK 17 for compilation and testing. JDK 21 is NOT supported.**

- `maven.compiler.source` and `maven.compiler.target` are both `11`
- `maven-javadoc-plugin` uses `<source>8</source>`
- `surefire 2.22.2` crashes on JDK 21 (VM fork crash due to Byte Buddy incompatibility)
- Local test command example:
  ```bash
  JAVA_HOME=/usr/local/Cellar/openjdk@17/17.0.20/libexec/openjdk.jdk/Contents/Home \
  PATH="$JAVA_HOME/bin:$PATH" \
  mvn test -pl <module> -f /Users/jack/source/hamster-selenium/pom.xml
  ```

## Project Structure

```
hamster-selenium/                          # Root POM (aggregator)
├── hamster-utils/                         # General utilities (com.github.grossopa.utils)
├── hamster-selenium-core/                 # Selenium core: ComponentWebDriver, WebComponent, intercepting, driver factory
├── hamster-selenium-component-html/       # HTML component library (HtmlSelect, HtmlTable, HtmlFormField)
├── hamster-selenium-component-materialui/ # Material UI v4/v5 components (MuiSelect, MuiButton, MuiDialog, etc.)
├── hamster-selenium-component-mat/        # Angular Material components (MatSelect, MatAutocomplete, MatDialog, etc.)
├── hamster-selenium-component-antdesign/  # Ant Design components (AntdButton, etc.) — early stage
├── hamster-selenium-examples/             # Selenium integration/E2E examples (test scope)
├── hamster-playwright-core/               # Playwright core: ComponentDriver, WebComponent, intercepting
├── hamster-playwright-component-html/     # Playwright HTML components
├── hamster-playwright-component-materialui/ # Playwright Material UI v4 components
└── hamster-playwright-examples/           # Playwright integration/E2E examples
```

## Module Dependency Chain

```
hamster-utils
  └── hamster-selenium-core
        ├── hamster-selenium-component-html
        ├── hamster-selenium-component-materialui
        ├── hamster-selenium-component-mat
        ├── hamster-selenium-component-antdesign
        └── hamster-selenium-examples
  └── hamster-playwright-core
        ├── hamster-playwright-component-html
        ├── hamster-playwright-component-materialui
        └── hamster-playwright-examples
```

## Architecture Patterns

### Core Abstraction Layers

| Concept | Selenium | Playwright |
|---|---|---|
| Driver | `ComponentWebDriver` (extends `WebDriver`) | `ComponentDriver` (wraps `Playwright`) |
| Element | `WebComponent` (extends `WebElement`) | `WebComponent` (wraps `Locator`) |
| Components | `Components` / `AbstractComponents` | `Components` / `AbstractComponents` |
| Intercepting | `InterceptingWebDriver`, `InterceptingWebElement` | `InterceptingPlaywright`, `InterceptingLocator` |

### Component Design Pattern

Every UI component library follows this pattern:
1. **Interface** (e.g., `MuiComponent`) — defines the component contract
2. **Abstract base** (e.g., `AbstractMuiComponent`) — shared behavior, wraps `WebComponent` + `ComponentWebDriver`
3. **Concrete components** (e.g., `MuiButton`, `MuiSelect`) — specific UI component implementations
4. **Components factory** (e.g., `MuiComponents`) — provides `toXxx()` factory methods using `create()` pattern with version-specific suppliers
5. **Config** (e.g., `MuiConfig`) — configurable behavior (version, locators, timeouts)

### Version Support Pattern (Material UI)

Components use a dual-supplier `create()` pattern to support multiple MUI versions:
```java
public MuiCheckbox toCheckbox() {
    return create(
        () -> new MuiCheckbox(component, driver, config),      // V4 supplier
        () -> new MuiCheckboxV5(component, driver, config)      // V5 supplier
    );
}
```

### Component Categories (Material UI)

Components are organized following the Material UI documentation structure:
- `inputs/` — Button, ButtonGroup, Checkbox, Fab, Radio, RadioGroup, Rating, Select, Slider, Switch, TextField
- `datadisplay/` — Avatar, Badge, Chip, Divider, List, ListItem, Tooltip
- `feedback/` — Alert, Backdrop, Dialog, Skeleton, Snackbar, SnackbarContent
- `navigation/` — Accordion (+Actions/Details/Summary), BottomNavigation, Breadcrumbs, Drawer, Link, Menu, MenuItem, Stepper, Tab, Tabs
- `surfaces/` — AppBar, Card, Pager, Toolbar
- `core/` — Grid, Modal, Popover (Selenium) / Box, Container, Grid (Playwright)
- `lab/` — Autocomplete, Pagination (Selenium only)
- `v5/datetime/` — CalendarPicker, DatePickerFormField, MonthPicker, YearPicker (V5 only)

## Build Commands

```bash
# Full build with tests
mvn clean verify

# Build with coverage report (JaCoCo XML)
mvn clean verify -Pcoverage

# Build single module
mvn test -pl hamster-selenium-core

# Build single module with dependencies
mvn test -pl hamster-selenium-component-materialui -am

# Release (includes source JAR, Javadoc JAR, GPG signing)
mvn release:prepare release:perform

# Mutation testing (PITest)
mvn org.pitest:pitest-maven:mutationCoverage -pl <module>

# SonarCloud analysis
mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Pcoverage
```

## Code Conventions

### License Header

Every `.java` file MUST start with the MIT License header:
```java
/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 * ...
 */
```

### Javadoc

- All public interfaces, classes, and methods MUST have Javadoc
- Javadoc includes `@author Jack Yin` and `@since` tags
- Include `{@link}` references to related types
- Include code examples in `{@code ...}` blocks within `<pre>` tags
- Each `package-info.java` should describe the package purpose

### Naming Conventions

- **Module names**: `hamster-{framework}-component-{ui-framework}` using full names (e.g., `materialui` NOT `mui`)
- **Package names**: `com.github.grossopa.{framework}.component.{short-name}` (e.g., `mui`, `mat`, `antd`, `html`)
- **Component prefix**: Matches the UI framework — `Mui` for Material UI, `Mat` for Angular Material, `Html` for HTML, `Antd` for Ant Design
- **Factory methods**: `toXxx()` pattern (e.g., `toButton()`, `toSelect()`, `toDialog()`)
- **Static factory**: `XxxComponents.xxx()` (e.g., `MuiComponents.mui()`, `HtmlComponents.html()`)

### Component Implementation Rules

- Components MUST extend the framework-specific base interface (e.g., `MuiComponent`, `MatComponent`)
- Components MUST extend `AbstractMuiComponent` / `AbstractMatComponent` etc. for shared behavior
- Factory methods in `XxxComponents` MUST use the `create(supplierV4, supplierV5)` pattern for version-aware components
- Components identify their target DOM element by CSS class (e.g., `MuiButton-root`, `MatSelect-root`)
- Config classes use Builder pattern where appropriate (e.g., `MuiSelectConfig.builder()`)

### Testing Rules

- Use JUnit Jupiter 5 (`@Test` from `org.junit.jupiter.api.Test`)
- Use Mockito for mocking (`mock()`, `when()`, `verify()`)
- Use OpenPojo for POJO/bean validation tests
- Tests MUST run on JDK 11 or 17, NOT JDK 21
- Each component class should have a corresponding test class (e.g., `MuiButton` → `MuiButtonTest`)
- Test class naming: `{ClassName}Test.java`

### Package Structure per Module

```
src/main/java/.../
├── {Prefix}Component.java          # Component interface
├── {Prefix}Components.java         # Factory/entry point class
├── Abstract{Prefix}Component.java  # Abstract base implementation
├── config/                         # Configuration classes
├── exception/                      # Custom exceptions
├── action/                         # Action strategies (open/close options)
├── finder/                         # Specialized locators/finders
├── v4/                             # Version-specific implementations
│   ├── inputs/
│   ├── datadisplay/
│   ├── feedback/
│   ├── navigation/
│   ├── surfaces/
│   ├── core/
│   └── lab/
└── v5/                             # V5-specific implementations (if applicable)
```

## CI/CD

- **Platform**: GitHub Actions (`.github/workflows/build.yml`)
- **Triggers**: push to `main`, `develop`, `playwright-ai`; PRs
- **Runtime**: Ubuntu latest, JDK 11 (Temurin)
- **Pipeline**: `mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Pcoverage`
- **SonarCloud**: Organization `grossopa`, project key `grossopa_hamster-selenium`
- **Publishing**: Maven Central via `central-publishing-maven-plugin`

## Key Constraints for AI Agents

1. **Never use JDK 21** for compilation or testing — surefire will crash
2. **Never use `mui` as module name suffix** — always use `materialui` (full name)
3. **Always add MIT License header** to new Java files
4. **Always add Javadoc** to public APIs (interfaces, classes, methods)
5. **Always follow the `create()` dual-supplier pattern** when adding version-specific MUI components
6. **Always maintain the component category package structure** (inputs, datadisplay, feedback, navigation, surfaces, core, lab)
7. **Examples modules** (`hamster-selenium-examples`, `hamster-playwright-examples`) are excluded from Sonar analysis and coverage
8. **Selenium and Playwright are parallel implementations** — maintain API symmetry between them where possible
