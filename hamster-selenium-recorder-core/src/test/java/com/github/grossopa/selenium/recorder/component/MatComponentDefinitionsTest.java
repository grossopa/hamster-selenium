/*
 * Copyright © 2021 the original author or authors.
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
package com.github.grossopa.selenium.recorder.component;

import com.github.grossopa.hamster.selenium.component.mat.MatComponent;
import com.github.grossopa.hamster.selenium.component.mat.MatComponents;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link MatComponentDefinitions}
 *
 * @author Jack Yin
 * @since 1.17
 */
class MatComponentDefinitionsTest {

    @Test
    void constructor() throws Exception {
        Constructor<MatComponentDefinitions> constructor = MatComponentDefinitions.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> constructor.newInstance());
    }

    final List<MatComponentDefinition> defaults = MatComponentDefinitions.defaults();

    @Test
    void testDefaultsNotEmpty() {
        assertFalse(defaults.isEmpty());
        assertTrue(defaults.size() > 30, "Expected at least 30 definitions, got: " + defaults.size());
    }

    @Test
    void testFactoryMethodsExistOnMatComponents() {
        for (MatComponentDefinition definition : defaults) {
            List<Method> methods = Arrays.stream(MatComponents.class.getMethods())
                    .filter(method -> method.getName().equals(definition.getFactoryMethodName()))
                    .toList();
            assertFalse(methods.isEmpty(),
                    "factory method not found on MatComponents: " + definition.getFactoryMethodName());
        }
    }

    @Test
    void testAllNoArgFactoryMethodsDiscovered() {
        Map<String, MatComponentDefinition> byFactoryMethod = defaults.stream()
                .collect(Collectors.toMap(MatComponentDefinition::getFactoryMethodName, d -> d, (a, b) -> a));

        for (Method method : MatComponents.class.getMethods()) {
            if (!method.getName().startsWith("to")) {
                continue;
            }
            if (method.getParameterCount() != 0) {
                continue;
            }
            if (!MatComponent.class.isAssignableFrom(method.getReturnType())) {
                continue;
            }
            assertTrue(byFactoryMethod.containsKey(method.getName()),
                    "auto-discovered method missing from defaults: " + method.getName());
        }
    }

    @Test
    void testManualOverrides() {
        // Autocomplete is manually overridden with requiresArgs=false and custom CSS class
        assertHasDefinition("Autocomplete", false);
        assertHasRootCss("Autocomplete", "mat-mdc-autocomplete-trigger");

        // Select and Datepicker are auto-discovered with requiresArgs=false
        assertHasDefinition("Select", false);
        assertHasDefinition("Datepicker", false);
    }

    @Test
    void testNonMdcDefinitions() {
        // Non-MDC components should use mat- prefix
        assertHasRootCss("Accordion", "mat-accordion");
        assertHasRootCss("Badge", "mat-badge");
        assertHasRootCss("BottomSheet", "mat-bottom-sheet-container");
        assertHasRootCss("ButtonToggle", "mat-button-toggle");
        assertHasRootCss("ButtonToggleGroup", "mat-button-toggle-group");
        assertHasRootCss("Sidenav", "mat-drawer");
        assertHasRootCss("SidenavContainer", "mat-drawer-container");
        assertHasRootCss("Stepper", "mat-stepper-horizontal");
        assertHasRootCss("Tree", "mat-tree");
        assertHasRootCss("TreeNode", "mat-tree-node");
    }

    @Test
    void testMdcCssOverrides() {
        // MDC components with non-standard root CSS classes
        assertHasRootCss("Button", "mat-mdc-button-base");
        assertHasRootCss("ChipList", "mat-mdc-chip-set");
        assertHasRootCss("Dialog", "mat-mdc-dialog-container");
        assertHasRootCss("Input", "mat-mdc-input-element");
        assertHasRootCss("Menu", "mat-mdc-menu-panel");
        assertHasRootCss("Snackbar", "mat-mdc-simple-snack-bar");
        assertHasRootCss("Table", "mat-mdc-table");
    }

    @Test
    void testAutoDerivedMdcCss() {
        // MDC components where root CSS follows auto-derivation
        assertHasRootCss("Calendar", "mat-mdc-calendar");
        assertHasRootCss("Card", "mat-mdc-card");
        assertHasRootCss("Checkbox", "mat-mdc-checkbox");
        assertHasRootCss("FormField", "mat-mdc-form-field");
        assertHasRootCss("List", "mat-mdc-list");
        assertHasRootCss("Paginator", "mat-mdc-paginator");
        assertHasRootCss("ProgressBar", "mat-mdc-progress-bar");
        assertHasRootCss("RadioButton", "mat-mdc-radio-button");
        assertHasRootCss("RadioGroup", "mat-mdc-radio-group");
        assertHasRootCss("Slider", "mat-mdc-slider");
        assertHasRootCss("SlideToggle", "mat-mdc-slide-toggle");
        assertHasRootCss("Tab", "mat-mdc-tab");
        assertHasRootCss("TabGroup", "mat-mdc-tab-group");
    }

    @Test
    void testRootCssClassesUnique() {
        Set<String> cssClasses = defaults.stream().map(MatComponentDefinition::getRootCssClass)
                .collect(Collectors.toSet());
        assertEquals(defaults.size(), cssClasses.size(), "duplicated root CSS classes found");
    }

    @Test
    void testToKebabCase() {
        assertEquals("button", MatComponentDefinitions.toKebabCase("Button"));
        assertEquals("button-toggle", MatComponentDefinitions.toKebabCase("ButtonToggle"));
        assertEquals("bottom-sheet", MatComponentDefinitions.toKebabCase("BottomSheet"));
        assertEquals("form-field", MatComponentDefinitions.toKebabCase("FormField"));
        assertEquals("tab-group", MatComponentDefinitions.toKebabCase("TabGroup"));
        assertEquals("sidenav-container", MatComponentDefinitions.toKebabCase("SidenavContainer"));
        assertEquals("progress-bar", MatComponentDefinitions.toKebabCase("ProgressBar"));
    }

    @Test
    void testDefinitionsSorted() {
        for (int i = 1; i < defaults.size(); i++) {
            assertTrue(defaults.get(i - 1).getTypeQualifiedName().compareTo(defaults.get(i).getTypeQualifiedName())
                    <= 0, "definitions not sorted by typeQualifiedName");
        }
    }

    private void assertHasDefinition(String componentName, boolean requiresArgs) {
        MatComponentDefinition def = findByName(componentName);
        assertNotNull(def, "definition not found for: " + componentName);
        assertEquals(requiresArgs, def.isRequiresArgs(), "requiresArgs mismatch for: " + componentName);
    }

    private void assertHasRootCss(String componentName, String expectedCss) {
        MatComponentDefinition def = findByName(componentName);
        assertNotNull(def, "definition not found for: " + componentName);
        assertEquals(expectedCss, def.getRootCssClass(), "rootCssClass mismatch for: " + componentName);
    }

    private MatComponentDefinition findByName(String componentName) {
        return defaults.stream().filter(d -> d.getComponentName().equals(componentName)).findFirst().orElse(null);
    }
}
