#!/usr/bin/env python3
"""
Script to generate all MUI Playwright component files from Selenium MUI components
"""

import os
import re

# Define component structure
components = {
    # Input components
    "inputs": [
        ("MuiButton", ["V4", "V5", "V6"]),
        ("MuiTextField", ["V4", "V5", "V6"]),
        ("MuiCheckbox", ["V4", "V5", "V6"]),
        ("MuiSelect", ["V4", "V5", "V6"]),
        ("MuiRadio", ["V4", "V5", "V6"]),
        ("MuiSwitch", ["V4", "V5", "V6"]),
        ("MuiSlider", ["V4", "V5", "V6"]),
        ("MuiFab", ["V4", "V5", "V6"]),
        ("MuiRating", ["V4", "V5", "V6"]),
        ("MuiButtonGroup", ["V4", "V5", "V6"]),
        ("MuiRadioGroup", ["V4", "V5", "V6"]),
    ],
    # Data Display components
    "datadisplay": [
        ("MuiAvatar", ["V4", "V5", "V6"]),
        ("MuiBadge", ["V4", "V5", "V6"]),
        ("MuiChip", ["V4", "V5", "V6"]),
        ("MuiDivider", ["V4", "V5", "V6"]),
        ("MuiList", ["V4", "V5", "V6"]),
        ("MuiListItem", ["V4", "V5", "V6"]),
        ("MuiTooltip", ["V4", "V5", "V6"]),
    ],
    # Feedback components
    "feedback": [
        ("MuiAlert", ["V4", "V5", "V6"]),
        ("MuiBackdrop", ["V4", "V5", "V6"]),
        ("MuiDialog", ["V4", "V5", "V6"]),
        ("MuiSkeleton", ["V4", "V5", "V6"]),
        ("MuiSnackbar", ["V4", "V5", "V6"]),
        ("MuiSnackbarContent", ["V4", "V5", "V6"]),
    ],
    # Navigation components
    "navigation": [
        ("MuiAccordion", ["V4", "V5", "V6"]),
        ("MuiAccordionActions", ["V4", "V5", "V6"]),
        ("MuiAccordionDetails", ["V4", "V5", "V6"]),
        ("MuiAccordionSummary", ["V4", "V5", "V6"]),
        ("MuiBottomNavigation", ["V4", "V5", "V6"]),
        ("MuiBottomNavigationAction", ["V4", "V5", "V6"]),
        ("MuiBreadcrumbs", ["V4", "V5", "V6"]),
        ("MuiDrawer", ["V4", "V5", "V6"]),
        ("MuiLink", ["V4", "V5", "V6"]),
        ("MuiMenu", ["V4", "V5", "V6"]),
        ("MuiMenuItem", ["V4", "V5", "V6"]),
        ("MuiStepper", ["V4", "V5", "V6"]),
        ("MuiTab", ["V4", "V5", "V6"]),
        ("MuiTabs", ["V4", "V5", "V6"]),
    ],
    # Surface components
    "surfaces": [
        ("MuiAppBar", ["V4", "V5", "V6"]),
        ("MuiCard", ["V4", "V5", "V6"]),
        ("MuiPaper", ["V4", "V5", "V6"]),
        ("MuiToolbar", ["V4", "V5", "V6"]),
    ],
    # Core components
    "core": [
        ("MuiGrid", ["V4", "V5", "V6"]),
        ("MuiContainer", ["V4", "V5", "V6"]),
        ("MuiBox", ["V4", "V5", "V6"]),
    ],
    # Lab components
    "lab": [
        ("MuiAutocomplete", ["V4", "V5", "V6"]),
        ("MuiPagination", ["V4", "V5", "V6"]),
    ],
}

base_path = "/Users/jack/source/hamster-selenium/hamster-playwright-component-mui/src/main/java/com/github/grossopa/playwright/component/mui/v4"

def generate_component(component_name, versions, category):
    """Generate a single component file"""
    
    version_imports = []
    version_enum_values = []
    
    for v in versions:
        version_imports.append(f"import static com.github.grossopa.playwright.component.mui.MuiVersion.{v};")
        version_enum_values.append(v)
    
    version_import_str = "\n".join(version_imports)
    version_enum_str = ", ".join(version_enum_values)
    
    content = f'''/*
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

package com.github.grossopa.playwright.component.mui.v4.{category};

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

{version_import_str}

/**
 * The Material UI {component_name.replace("Mui", "")} implementation for Playwright.
 *
 * @author Jack Yin
 * @since 1.12
 */
public class {component_name} extends AbstractMuiComponent {{

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "{component_name.replace("Mui", "")}";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the Playwright locator
     * @param driver the component driver
     * @param config the Material UI configuration
     */
    public {component_name}(Locator locator, ComponentDriver driver, MuiConfig config) {{
        super(locator, driver, config);
    }}

    @Override
    public Set<com.github.grossopa.playwright.component.mui.MuiVersion> versions() {{
        return EnumSet.of({version_enum_str});
    }}

    @Override
    public String getComponentName() {{
        return COMPONENT_NAME;
    }}
}}
'''
    
    return content

# Generate all components
for category, comps in components.items():
    category_path = os.path.join(base_path, category)
    os.makedirs(category_path, exist_ok=True)
    
    for comp_name, versions in comps:
        file_path = os.path.join(category_path, f"{comp_name}.java")
        content = generate_component(comp_name, versions, category)
        
        with open(file_path, 'w') as f:
            f.write(content)
        
        print(f"Created: {file_path}")

print("\nAll components generated successfully!")
