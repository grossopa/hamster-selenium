# Hamster Playwright MUI Components

This module provides Material UI (MUI) component support for Playwright-based test automation, mirroring the functionality of the Selenium-based `hamster-selenium-component-materialui` module.

## Overview

The `hamster-playwright-component-materialui` module provides a component-based abstraction for Material UI components when using Playwright for web automation. It allows you to interact with MUI components in a type-safe, object-oriented manner.

## Structure

### Core Classes

- **MuiComponent**: Interface that all MUI components implement
- **AbstractMuiComponent**: Base class providing common functionality for all MUI components
- **MuiVersion**: Enum representing supported MUI versions (V4, V5, V6)
- **MuiConfig**: Configuration class for customizing MUI component behavior
- **MuiComponents**: Factory class providing conversion methods for all MUI component types

### Component Categories

Components are organized into categories following the Material UI documentation structure:

#### Inputs (`v4.inputs`)
- MuiButton
- MuiTextField
- MuiCheckbox
- MuiSelect
- MuiRadio
- MuiSwitch
- MuiSlider
- MuiFab
- MuiRating
- MuiButtonGroup
- MuiRadioGroup

#### Data Display (`v4.datadisplay`)
- MuiAvatar
- MuiBadge
- MuiChip
- MuiDivider
- MuiList
- MuiListItem
- MuiTooltip

#### Feedback (`v4.feedback`)
- MuiAlert
- MuiBackdrop
- MuiDialog
- MuiSkeleton
- MuiSnackbar
- MuiSnackbarContent

#### Navigation (`v4.navigation`)
- MuiAccordion
- MuiAccordionActions
- MuiAccordionDetails
- MuiAccordionSummary
- MuiBottomNavigation
- MuiBottomNavigationAction
- MuiBreadcrumbs
- MuiDrawer
- MuiLink
- MuiMenu
- MuiMenuItem
- MuiStepper
- MuiTab
- MuiTabs

#### Surfaces (`v4.surfaces`)
- MuiAppBar
- MuiCard
- MuiPaper
- MuiToolbar

#### Core (`v4.core`)
- MuiGrid
- MuiContainer
- MuiBox

#### Lab (`v4.lab`)
- MuiAutocomplete
- MuiPagination

## Usage

### Basic Setup

```java
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.DefaultComponentDriver;
import com.github.grossopa.playwright.component.mui.MuiComponents;
import com.github.grossopa.playwright.component.mui.v4.inputs.MuiButton;
import com.microsoft.playwright.Playwright;

// Create Playwright instance
Playwright playwright = Playwright.create();
ComponentDriver driver = new DefaultComponentDriver(playwright);

// Navigate to your application
driver.navigate("https://your-app.com");

// Create MUI components factory
MuiComponents mui = MuiComponents.mui();
```

### Finding and Interacting with Components

```java
// Find a button component
WebComponent buttonComponent = driver.findComponent(".MuiButton-root");
MuiButton button = buttonComponent.as(mui::toButton);
button.click();

// Find a text field
WebComponent textFieldComponent = driver.findComponent(".MuiTextField-root");
MuiTextField textField = textFieldComponent.as(mui::toTextField);
textField.fill("Hello World");

// Find a checkbox
WebComponent checkboxComponent = driver.findComponent(".MuiCheckbox-root");
MuiCheckbox checkbox = checkboxComponent.as(mui::toCheckbox);
checkbox.click(); // Toggle checkbox
```

### Version-Specific Components

```java
// Use MUI v5 configuration
MuiComponents muiV5 = MuiComponents.muiV5();

// Or use custom configuration
MuiConfig config = new MuiConfig();
config.setVersion(MuiVersion.V5);
config.setCssPrefix("Mui");
MuiComponents muiCustom = MuiComponents.mui(config);
```

## Adding New Components

To add a new MUI component:

1. **Create the component class** in the appropriate category package:

```java
package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

public class MuiNewComponent extends AbstractMuiComponent {

    public static final String COMPONENT_NAME = "NewComponent";

    public MuiNewComponent(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<com.github.grossopa.playwright.component.mui.MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }
}
```

2. **Add a conversion method** to `MuiComponents`:

```java
public MuiNewComponent toNewComponent() {
    return new MuiNewComponent(component.locator(), driver, config);
}
```

3. **Use the component** in your tests:

```java
WebComponent comp = driver.findComponent(".MuiNewComponent-root");
MuiNewComponent newComp = comp.as(mui::toNewComponent);
```

## Differences from Selenium Implementation

1. **Locator vs WebElement**: Playwright uses `Locator` instead of Selenium's `WebElement`
2. **ComponentDriver**: Uses Playwright-based `ComponentDriver` instead of Selenium's `ComponentWebDriver`
3. **No WebComponent wrapping**: Playwright components work directly with `Locator` instances
4. **Simplified API**: Some Selenium-specific methods have been removed or simplified

## Migration from Selenium MUI Components

If you're migrating from the Selenium-based MUI components:

1. Replace `ComponentWebDriver` with `ComponentDriver`
2. Replace `WebElement` parameters with `Locator` parameters
3. Update imports from `com.github.grossopa.selenium.*` to `com.github.grossopa.playwright.*`
4. Adjust any Selenium-specific APIs to their Playwright equivalents

## Current Status

This module provides the foundational structure for MUI component support in Playwright. The following has been implemented:

✅ Module structure and POM configuration
✅ Core interfaces and abstract classes (MuiComponent, AbstractMuiComponent, MuiVersion)
✅ Configuration classes (MuiConfig)
✅ Factory class (MuiComponents) with conversion methods for all component types
✅ Basic component implementations for key components (MuiButton, MuiTextField)
✅ Parent POM integration

The remaining component implementations follow the same pattern as MuiButton and MuiTextField. They can be easily created by copying the template and adjusting the class name and component name.

## Future Enhancements

Potential areas for enhancement:

1. Complete implementation of all component classes with specific functionality
2. Add version-specific implementations (V5, V6) where components differ significantly
3. Add specialized locators and finders for complex components
4. Add action classes for complex interactions (similar to Selenium implementation)
5. Add comprehensive test suite
6. Add documentation with examples for each component type

## License

MIT License - See LICENSE file for details

## Author

Jack Yin

## Since

1.12
