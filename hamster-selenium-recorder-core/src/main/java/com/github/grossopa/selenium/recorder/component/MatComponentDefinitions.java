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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The registry of the known Angular Material component definitions that map the Angular Material root CSS classes to
 * the hamster selenium component types and the {@code MatComponents} factory methods.
 *
 * <p>The component definitions are auto-discovered via reflection on {@link MatComponents} class. All public no-arg
 * factory methods returning a {@link MatComponent} subtype are automatically included. The root CSS class is derived
 * from the type name using kebab-case conversion with the {@code mat-mdc-} prefix. Components whose root CSS class
 * does not follow the auto-derivation rule are maintained in {@link #MDC_CSS_OVERRIDES}.</p>
 *
 * <p>Angular Material has two CSS prefix groups:
 * <ul>
 * <li>MDC-based components (default) use the {@code mat-mdc-} prefix, e.g. {@code mat-mdc-card}.</li>
 * <li>Non-MDC components use the legacy {@code mat-} prefix, e.g. {@code mat-accordion}. These are maintained in
 * {@link #NON_MDC_DEFINITIONS}.</li>
 * </ul>
 *
 * <p>Components whose factory methods require arguments (e.g. {@code toAutocomplete}) are maintained manually in
 * {@link #MANUAL_OVERRIDES}.</p>
 *
 * @author Jack Yin
 * @since 1.17
 * @see MatComponentDefinition
 * @see MatComponentDetector
 */
public class MatComponentDefinitions {

    private static final String MDC_PREFIX = "mat-mdc-";

    /**
     * Return types to exclude from auto-discovery. These components have no-arg factory methods but need custom CSS
     * class mappings that differ from the auto-derived values, so they are maintained manually in
     * {@link #MANUAL_OVERRIDES}.
     */
    private static final Set<String> EXCLUDE_RETURN_TYPES = Set.of(
            "com.github.grossopa.hamster.selenium.component.mat.main.MatAutocomplete");

    /**
     * MDC components whose root CSS class does not match the auto-derived value ({@code mat-mdc-} + kebab-case name).
     * The key is the simple type name (e.g. "MatButton"), the value is the actual root CSS class.
     */
    private static final Map<String, String> MDC_CSS_OVERRIDES = Map.ofEntries(
            Map.entry("MatAutocomplete", "mat-mdc-autocomplete-trigger"),
            Map.entry("MatButton", "mat-mdc-button-base"),
            Map.entry("MatChipList", "mat-mdc-chip-set"),
            Map.entry("MatDatepicker", "mat-mdc-datepicker"),
            Map.entry("MatDialog", "mat-mdc-dialog-container"),
            Map.entry("MatInput", "mat-mdc-input-element"),
            Map.entry("MatMenu", "mat-mdc-menu-panel"),
            Map.entry("MatSelect", "mat-mdc-select"),
            Map.entry("MatSnackbar", "mat-mdc-simple-snack-bar"),
            Map.entry("MatTable", "mat-mdc-table"));

    /**
     * Manual overrides for components that need custom handling. This includes components whose auto-derived CSS class
     * differs from the actual root CSS class (e.g. {@code MatAutocomplete} uses {@code mat-mdc-autocomplete-trigger})
     * and components that should not be auto-discovered to avoid duplicate definitions.
     */
    private static final List<MatComponentDefinition> MANUAL_OVERRIDES = List.of(
            new MatComponentDefinition("Autocomplete", "MatAutocomplete",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatAutocomplete", "toAutocomplete", false,
                    "mat-mdc-autocomplete-trigger"));

    /**
     * Non-MDC components that use the legacy {@code mat-} CSS prefix instead of {@code mat-mdc-}. These components
     * never adopted the MDC-based naming in Angular Material v15+.
     */
    private static final List<MatComponentDefinition> NON_MDC_DEFINITIONS = List.of(
            new MatComponentDefinition("Accordion", "MatAccordion",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatAccordion", "toAccordion", false,
                    "mat-accordion"),
            new MatComponentDefinition("Badge", "MatBadge",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatBadge", "toBadge", false, "mat-badge"),
            new MatComponentDefinition("BottomSheet", "MatBottomSheet",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatBottomSheet", "toBottomSheet", false,
                    "mat-bottom-sheet-container"),
            new MatComponentDefinition("ButtonToggle", "MatButtonToggle",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatButtonToggle", "toButtonToggle",
                    false, "mat-button-toggle"),
            new MatComponentDefinition("ButtonToggleGroup", "MatButtonToggleGroup",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatButtonToggleGroup",
                    "toButtonToggleGroup", false, "mat-button-toggle-group"),
            new MatComponentDefinition("GridList", "MatGridList",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatGridList", "toGridList", false,
                    "mat-grid-list"),
            new MatComponentDefinition("GridTile", "MatGridTile",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatGridTile", "toGridTile", false,
                    "mat-grid-tile"),
            new MatComponentDefinition("Sidenav", "MatSidenav",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatSidenav", "toSidenav", false,
                    "mat-drawer"),
            new MatComponentDefinition("SidenavContainer", "MatSidenavContainer",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatSidenavContainer",
                    "toSidenavContainer", false, "mat-drawer-container"),
            new MatComponentDefinition("Step", "MatStep",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatStep", "toStep", false, "mat-step"),
            new MatComponentDefinition("Stepper", "MatStepper",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatStepper", "toStepper", false,
                    "mat-stepper-horizontal"),
            new MatComponentDefinition("Tree", "MatTree",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatTree", "toTree", false, "mat-tree"),
            new MatComponentDefinition("TreeNode", "MatTreeNode",
                    "com.github.grossopa.hamster.selenium.component.mat.main.MatTreeNode", "toTreeNode", false,
                    "mat-tree-node"));

    /**
     * private constructor
     */
    private MatComponentDefinitions() {
        throw new AssertionError();
    }

    /**
     * Gets the default definitions covering the Angular Material components supported by {@code MatComponents}. The
     * definitions include auto-discovered MDC components, manual CSS overrides and non-MDC component definitions.
     *
     * @return the default component definitions
     */
    public static List<MatComponentDefinition> defaults() {
        List<MatComponentDefinition> result = new ArrayList<>();

        // Collect type names that are handled by manual overrides to avoid duplicates
        Set<String> overriddenTypeNames = new java.util.HashSet<>();
        MANUAL_OVERRIDES.stream().map(MatComponentDefinition::getTypeName).forEach(overriddenTypeNames::add);
        NON_MDC_DEFINITIONS.stream().map(MatComponentDefinition::getTypeName).forEach(overriddenTypeNames::add);

        // Auto-discover from MatComponents no-arg factory methods
        for (Method method : MatComponents.class.getMethods()) {
            if (method.getName().startsWith("to") && method.getParameterCount() == 0) {
                Class<?> returnType = method.getReturnType();
                if (MatComponent.class.isAssignableFrom(returnType)
                        && !EXCLUDE_RETURN_TYPES.contains(returnType.getName())
                        && !overriddenTypeNames.contains(returnType.getSimpleName())) {
                    result.add(toDefinition(method));
                }
            }
        }

        // Add manual overrides
        result.addAll(MANUAL_OVERRIDES);

        // Add non-MDC definitions
        result.addAll(NON_MDC_DEFINITIONS);

        result.sort(Comparator.comparing(MatComponentDefinition::getTypeQualifiedName));
        return List.copyOf(result);
    }

    private static MatComponentDefinition toDefinition(Method method) {
        Class<?> returnType = method.getReturnType();
        String typeName = returnType.getSimpleName();
        String componentName = typeName.substring("Mat".length());
        String fqn = returnType.getName();

        String rootCssClass = MDC_CSS_OVERRIDES.get(typeName);
        if (rootCssClass == null) {
            rootCssClass = MDC_PREFIX + toKebabCase(componentName);
        }

        return new MatComponentDefinition(componentName, typeName, fqn, method.getName(), false, rootCssClass);
    }

    /**
     * Converts a camelCase name to kebab-case. For example "ButtonToggle" becomes "button-toggle", "BottomSheet"
     * becomes "bottom-sheet".
     *
     * @param name the camelCase name
     * @return the kebab-case result
     */
    static String toKebabCase(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    sb.append('-');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
