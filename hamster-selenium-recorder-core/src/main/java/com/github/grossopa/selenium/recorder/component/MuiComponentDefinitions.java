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

import com.github.grossopa.selenium.component.mui.MuiComponent;
import com.github.grossopa.selenium.component.mui.MuiComponents;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * The registry of the known Material UI component definitions that map the MUI root css classes to the hamster
 * selenium component types and the {@code MuiComponents} factory methods. The definitions reuse the same root css
 * naming convention as {@code MuiConfig.getRootCss(componentName)}.
 *
 * <p>The component definitions are auto-discovered via reflection on {@link MuiComponents} class. All public no-arg
 * factory methods returning a {@link MuiComponent} subtype are automatically included. Components whose factory
 * methods require arguments (e.g. {@code toSelect(By)}) are maintained manually in
 * {@link #REQUIRES_ARGS_OVERRIDES}.</p>
 *
 * @author Jack Yin
 * @since 1.15
 * @see MuiComponentDefinition
 * @see MuiComponentDetector
 */
public class MuiComponentDefinitions {

    /**
     * Return types to exclude from auto-discovery. These are internal utility types that implement
     * {@link MuiComponent} but are not actual UI components detectable by css class.
     */
    private static final Set<String> EXCLUDE_RETURN_TYPES = Set.of(
            "com.github.grossopa.selenium.component.mui.v4.finder.MuiModalFinder");

    /**
     * Manual overrides for components whose factory methods require additional arguments. These cannot be
     * auto-discovered since they have no zero-parameter factory method on {@link MuiComponents}.
     */
    private static final List<MuiComponentDefinition> REQUIRES_ARGS_OVERRIDES = List.of(
            new MuiComponentDefinition("Select", "MuiSelect",
                    "com.github.grossopa.selenium.component.mui.v4.inputs.MuiSelect", "toSelect", true));

    /**
     * private constructor
     */
    private MuiComponentDefinitions() {
        throw new AssertionError();
    }

    /**
     * Gets the default definitions covering the Material UI components supported by {@code MuiComponents}. The
     * definitions are auto-discovered via reflection on all public no-arg factory methods of {@link MuiComponents}
     * that return a {@link MuiComponent} subtype, supplemented by manual overrides for components requiring
     * additional arguments.
     *
     * @return the default component definitions
     */
    public static List<MuiComponentDefinition> defaults() {
        List<MuiComponentDefinition> result = new ArrayList<>();

        // Auto-discover from MuiComponents no-arg factory methods
        for (Method method : MuiComponents.class.getMethods()) {
            if (method.getName().startsWith("to") && method.getParameterCount() == 0) {
                Class<?> returnType = method.getReturnType();
                if (MuiComponent.class.isAssignableFrom(returnType)
                        && !EXCLUDE_RETURN_TYPES.contains(returnType.getName())) {
                    result.add(toDefinition(method));
                }
            }
        }

        // Add manual overrides for components requiring args
        result.addAll(REQUIRES_ARGS_OVERRIDES);

        result.sort(Comparator.comparing(MuiComponentDefinition::getTypeQualifiedName));
        return List.copyOf(result);
    }

    private static MuiComponentDefinition toDefinition(Method method) {
        Class<?> returnType = method.getReturnType();
        String typeName = returnType.getSimpleName();
        String componentName = typeName.substring("Mui".length());
        String fqn = returnType.getName();
        return new MuiComponentDefinition(componentName, typeName, fqn, method.getName(), false);
    }
}
