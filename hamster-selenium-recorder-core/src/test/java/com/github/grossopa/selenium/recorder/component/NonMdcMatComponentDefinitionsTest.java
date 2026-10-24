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
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link NonMdcMatComponentDefinitions}
 *
 * @author Jack Yin
 * @since 1.18
 */
class NonMdcMatComponentDefinitionsTest {

    @Test
    void constructor() throws Exception {
        Constructor<NonMdcMatComponentDefinitions> constructor = NonMdcMatComponentDefinitions.class
                .getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> constructor.newInstance());
    }

    final List<MatComponentDefinition> defaults = NonMdcMatComponentDefinitions.defaults();

    @Test
    void testDefaultsNotEmpty() {
        assertFalse(defaults.isEmpty());
        assertTrue(defaults.size() > 30, "Expected at least 30 definitions, got: " + defaults.size());
    }

    @Test
    void testSameSizeAsMdcDefaults() {
        assertEquals(MatComponentDefinitions.defaults().size(), defaults.size(),
                "non-MDC defaults should have the same size as MDC defaults");
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
        var byFactoryMethod = defaults.stream()
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
    void testAllCssClassesUseLegacyPrefix() {
        for (MatComponentDefinition definition : defaults) {
            String css = definition.getRootCssClass();
            assertTrue(css.startsWith("mat-"), "CSS class should start with 'mat-': " + css);
            assertFalse(css.startsWith("mat-mdc-"),
                    "CSS class should NOT start with 'mat-mdc-': " + css + " for " + definition.getComponentName());
        }
    }

    @Test
    void testSpecialConversions() {
        assertHasRootCss("Button", "mat-button");
        assertHasRootCss("Input", "mat-input-element");
        assertHasRootCss("Autocomplete", "mat-autocomplete-trigger");
    }

    @Test
    void testStandardConversions() {
        assertHasRootCss("Card", "mat-card");
        assertHasRootCss("Checkbox", "mat-checkbox");
        assertHasRootCss("Dialog", "mat-dialog-container");
        assertHasRootCss("FormField", "mat-form-field");
        assertHasRootCss("Select", "mat-select");
        assertHasRootCss("Menu", "mat-menu-panel");
        assertHasRootCss("Table", "mat-table");
        assertHasRootCss("Tab", "mat-tab");
        assertHasRootCss("TabGroup", "mat-tab-group");
        assertHasRootCss("List", "mat-list");
        assertHasRootCss("ChipList", "mat-chip-set");
        assertHasRootCss("SlideToggle", "mat-slide-toggle");
        assertHasRootCss("Slider", "mat-slider");
        assertHasRootCss("RadioButton", "mat-radio-button");
        assertHasRootCss("RadioGroup", "mat-radio-group");
        assertHasRootCss("ProgressBar", "mat-progress-bar");
        assertHasRootCss("Paginator", "mat-paginator");
        assertHasRootCss("Datepicker", "mat-datepicker");
        assertHasRootCss("Calendar", "mat-calendar");
        assertHasRootCss("Snackbar", "mat-simple-snack-bar");
    }

    @Test
    void testNonMdcComponentsPreserved() {
        assertHasRootCss("Accordion", "mat-accordion");
        assertHasRootCss("Badge", "mat-badge");
        assertHasRootCss("BottomSheet", "mat-bottom-sheet-container");
        assertHasRootCss("ButtonToggle", "mat-button-toggle");
        assertHasRootCss("ButtonToggleGroup", "mat-button-toggle-group");
        assertHasRootCss("Sidenav", "mat-drawer");
        assertHasRootCss("SidenavContainer", "mat-drawer-container");
        assertHasRootCss("Stepper", "mat-stepper-horizontal");
        assertHasRootCss("Step", "mat-step");
        assertHasRootCss("Tree", "mat-tree");
        assertHasRootCss("TreeNode", "mat-tree-node");
        assertHasRootCss("GridList", "mat-grid-list");
        assertHasRootCss("GridTile", "mat-grid-tile");
    }

    @Test
    void testRootCssClassesUnique() {
        Set<String> cssClasses = defaults.stream().map(MatComponentDefinition::getRootCssClass)
                .collect(Collectors.toSet());
        assertEquals(defaults.size(), cssClasses.size(), "duplicated root CSS classes found");
    }

    @Test
    void testDefinitionsSorted() {
        for (int i = 1; i < defaults.size(); i++) {
            assertTrue(defaults.get(i - 1).getTypeQualifiedName().compareTo(defaults.get(i).getTypeQualifiedName())
                    <= 0, "definitions not sorted by typeQualifiedName");
        }
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
