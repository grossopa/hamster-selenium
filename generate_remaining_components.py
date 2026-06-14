#!/usr/bin/env python3
"""
Script to generate all remaining MUI Playwright component files.

This script generates all component classes that are referenced in MuiComponents.java
but haven't been created yet.

Usage:
    python3 generate_remaining_components.py
"""

import os

# Component definitions matching MuiComponents.java
components = {
    "inputs": [
        "MuiCheckbox",
        "MuiSelect", 
        "MuiRadio",
        "MuiSwitch",
        "MuiSlider",
        "MuiFab",
        "MuiRating",
        "MuiButtonGroup",
        "MuiRadioGroup",
    ],
    "datadisplay": [
        "MuiAvatar",
        "MuiBadge",
        "MuiChip",
        "MuiDivider",
        "MuiList",
        "MuiListItem",
        "MuiTooltip",
    ],
    "feedback": [
        "MuiAlert",
        "MuiBackdrop",
        "MuiDialog",
        "MuiSkeleton",
        "MuiSnackbar",
        "MuiSnackbarContent",
    ],
    "navigation": [
        "MuiAccordion",
        "MuiAccordionActions",
        "MuiAccordionDetails",
        "MuiAccordionSummary",
        "MuiBottomNavigation",
        "MuiBottomNavigationAction",
        "MuiBreadcrumbs",
        "MuiDrawer",
        "MuiLink",
        "MuiMenu",
        "MuiMenuItem",
        "MuiStepper",
        "MuiTab",
        "MuiTabs",
    ],
    "surfaces": [
        "MuiAppBar",
        "MuiCard",
        "MuiPaper",
        "MuiToolbar",
    ],
    "core": [
        "MuiGrid",
        "MuiContainer",
        "MuiBox",
    ],
    "lab": [
        "MuiAutocomplete",
        "MuiPagination",
    ],
}

base_path = "/Users/jack/source/hamster-selenium/hamster-playwright-component-mui/src/main/java/com/github/grossopa/playwright/component/mui/v4"

def generate_component(component_name, category):
    """Generate a single component file"""
    
    # Extract simple name (remove Mui prefix for display)
    simple_name = component_name.replace("Mui", "")
    
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

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * The Material UI {simple_name} implementation for Playwright.
 *
 * @author Jack Yin
 * @since 1.12
 */
public class {component_name} extends AbstractMuiComponent {{

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "{simple_name}";

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
        return EnumSet.of(V4, V5, V6);
    }}

    @Override
    public String getComponentName() {{
        return COMPONENT_NAME;
    }}
}}
'''
    
    return content

def main():
    print("Generating remaining MUI Playwright components...\n")
    
    total_count = 0
    
    for category, comp_list in components.items():
        category_path = os.path.join(base_path, category)
        os.makedirs(category_path, exist_ok=True)
        
        print(f"Processing category: {category}")
        
        for comp_name in comp_list:
            file_path = os.path.join(category_path, f"{comp_name}.java")
            
            # Skip if file already exists
            if os.path.exists(file_path):
                print(f"  ✓ Already exists: {comp_name}")
                continue
            
            content = generate_component(comp_name, category)
            
            with open(file_path, 'w') as f:
                f.write(content)
            
            print(f"  ✓ Created: {comp_name}")
            total_count += 1
        
        print()
    
    print(f"\n✅ Successfully generated {total_count} component files!")
    print(f"All components are now available in: {base_path}")

if __name__ == "__main__":
    main()
