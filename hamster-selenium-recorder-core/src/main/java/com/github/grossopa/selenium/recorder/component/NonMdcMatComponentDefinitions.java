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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * The registry of Angular Material legacy (non-MDC, v12-v14) component definitions that map the {@code mat-*} root CSS
 * classes to the hamster selenium component types and the {@code MatComponents} factory methods.
 *
 * <p>The definitions are derived from {@link MatComponentDefinitions#defaults()} by converting the MDC-based CSS
 * classes ({@code mat-mdc-*}) to their legacy equivalents ({@code mat-*}). Components whose MDC and legacy CSS class
 * names differ beyond a simple prefix change (e.g. {@code mat-mdc-button-base} to {@code mat-button}) are maintained in
 * {@link #SPECIAL_CSS_CONVERSIONS}.</p>
 *
 * @author Jack Yin
 * @since 1.18
 * @see MatComponentDefinition
 * @see MatComponentDetector
 * @see MatComponentDefinitions
 */
public class NonMdcMatComponentDefinitions {

    private static final String MDC_PREFIX = "mat-mdc-";
    private static final String LEGACY_PREFIX = "mat-";

    /**
     * Special CSS class conversions for components whose MDC and legacy root CSS classes differ beyond a simple
     * {@code mat-mdc-} to {@code mat-} prefix change. The key is the MDC root CSS class, the value is the legacy root
     * CSS class.
     */
    private static final Map<String, String> SPECIAL_CSS_CONVERSIONS = Map.ofEntries(
            Map.entry("mat-mdc-button-base", "mat-button"),
            Map.entry("mat-mdc-input-element", "mat-input-element"),
            Map.entry("mat-mdc-autocomplete-trigger", "mat-autocomplete-trigger"));

    /**
     * private constructor
     */
    private NonMdcMatComponentDefinitions() {
        throw new AssertionError();
    }

    /**
     * Gets the legacy (non-MDC) definitions covering all Angular Material components supported by
     * {@code MatComponents}. Every definition uses the {@code mat-} CSS prefix matching the Angular Material v12-v14
     * DOM structure.
     *
     * @return the legacy component definitions
     */
    public static List<MatComponentDefinition> defaults() {
        List<MatComponentDefinition> result = new ArrayList<>();
        for (MatComponentDefinition mdcDef : MatComponentDefinitions.defaults()) {
            result.add(convertToNonMdc(mdcDef));
        }
        result.sort(Comparator.comparing(MatComponentDefinition::getTypeQualifiedName));
        return List.copyOf(result);
    }

    private static MatComponentDefinition convertToNonMdc(MatComponentDefinition mdcDef) {
        String mdcCss = mdcDef.getRootCssClass();

        // Check special conversions first (e.g. mat-mdc-button-base → mat-button)
        String converted = SPECIAL_CSS_CONVERSIONS.get(mdcCss);
        if (converted != null) {
            return new MatComponentDefinition(mdcDef.getComponentName(), mdcDef.getTypeName(),
                    mdcDef.getTypeQualifiedName(), mdcDef.getFactoryMethodName(), mdcDef.isRequiresArgs(), converted);
        }

        // Standard conversion: mat-mdc-X → mat-X
        if (mdcCss.startsWith(MDC_PREFIX)) {
            String legacyCss = LEGACY_PREFIX + mdcCss.substring(MDC_PREFIX.length());
            return new MatComponentDefinition(mdcDef.getComponentName(), mdcDef.getTypeName(),
                    mdcDef.getTypeQualifiedName(), mdcDef.getFactoryMethodName(), mdcDef.isRequiresArgs(), legacyCss);
        }

        // Already uses mat- prefix (non-MDC definitions like mat-accordion, mat-badge, etc.)
        return mdcDef;
    }
}
